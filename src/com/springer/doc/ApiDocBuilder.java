package com.springer.doc;

import com.springer.utils.HttpUtils;
import com.springer.utils.TypeUtils;
import com.sun.javadoc.*;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type.ClassType;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javadoc.DocEnv;
import com.sun.tools.javadoc.Main;

import java.util.*;

import static com.springer.utils.RequestWrap.HTTP_METHOD_GET;

/**
 * ${DESCRIPTION}
 *
 * @author chenjianhui
 * @create 2018/04/24
 **/
public class ApiDocBuilder {

    private static RootDoc ROOT_DOC;

    private static DocEnv DOC_ENV;

    private static ClassDoc CLASS_DOC;

    /**
     * 获取DOC文档根对象
     * @param root
     * @return
     */
    public static boolean start(RootDoc root) {
        ROOT_DOC = root;
        CLASS_DOC = ROOT_DOC.classes()[0];
        DOC_ENV = (DocEnv) TypeUtils.getFieldValue(CLASS_DOC, "env");
        return true;
    }


    /**
     * 构建ApiDoc模型
     * @param classpath 源码文件及依赖库的class路径
     * @param sourcePath 源文件路径
     * @param methodName 接口方法名
     * @return
     */
    public static ApiDoc build(String classpath, String sourcePath, String filePath, String methodName){
        ApiDoc apiDoc = null;
        readDoc(classpath, sourcePath, filePath);
        ClassDoc classDoc = ROOT_DOC.classes()[0];
        MethodDoc methodDoc = null;
        for (MethodDoc d : classDoc.methods()){
            if(methodName.equals(d.name())){
                methodDoc = d;
                break;
            }
        }
        if(methodDoc == null){
            return apiDoc;
        }
        apiDoc = buildDoc(methodDoc);
        return apiDoc;
    }

    /**
     * 构建ApiDoc模型
     * @param methodDoc
     * @return
     */
    private static ApiDoc buildDoc(MethodDoc methodDoc) {
        ApiDoc apiDoc = new ApiDoc();
        String title = methodDoc.commentText();
        String path = buildReqPath(methodDoc);
        String method = buildReqMethod(methodDoc);
        ApiResBody resBody = buildResBody(methodDoc);
        ApiResBody reqBody = buildReqBody(methodDoc);
        apiDoc.setTitle(title);
        apiDoc.setDesc("<p>" + title + "</>");
        apiDoc.setMarkdown(title);
        apiDoc.setPath(path);
        apiDoc.setMethod(method);
        try {
            apiDoc.setReq_body_other(HttpUtils.getObjMapper().writeValueAsString(reqBody));
            apiDoc.setRes_body(HttpUtils.getObjMapper().writeValueAsString(resBody));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiDoc;
    }

    /**
     *  构建Api接口路径模型
     * @param methodDoc
     * @return
     */
    private static String buildReqPath(MethodDoc methodDoc) {
        StringBuffer path = new StringBuffer();
        ClassDoc classDoc = methodDoc.containingClass();
        Optional<AnnotationDesc> rm = Arrays.asList(classDoc.annotations()).stream().filter(e -> e.annotationType().name().endsWith("Mapping")).findFirst();
        if(rm.isPresent()){
            Optional<AnnotationDesc.ElementValuePair> value = Arrays.asList(rm.get().elementValues()).stream().filter(a -> a.element().name().equals("value")).findFirst();
            if(value.isPresent()){
                AnnotationValue[] avs = (AnnotationValue[]) value.get().value().value();
                path.append(avs[0].value());
            }
        }
        rm = Arrays.asList(methodDoc.annotations()).stream().filter(e -> e.annotationType().name().endsWith("Mapping")).findFirst();
        if(rm.isPresent()){
            Optional<AnnotationDesc.ElementValuePair> value = Arrays.asList(rm.get().elementValues()).stream().filter(a -> a.element().name().equals("value")).findFirst();
            if(value.isPresent()){
                AnnotationValue[] avs = (AnnotationValue[]) value.get().value().value();
                path.append(avs[0].value());
            }
        }
        return path.toString();
    }

    /**
     * 构建Api接口请求方法
     * @param methodDoc
     * @return
     */
    private static String buildReqMethod(MethodDoc methodDoc) {
        String method = HTTP_METHOD_GET;
        Optional<AnnotationDesc> rm = Arrays.asList(methodDoc.annotations()).stream().filter(e -> e.annotationType().name().endsWith("Mapping")).findFirst();
        if(rm.isPresent()){
            Optional<AnnotationDesc.ElementValuePair> value = Arrays.asList(rm.get().elementValues()).stream().filter(a -> a.element().name().equals("method")).findFirst();
            if(value.isPresent()){
                method = value.get().value().toString();
                method = method.substring(method.lastIndexOf('.'));
            }
        }
        return method;
    }

    /**
     * 构建接口响应体
     * @param methodDoc
     * @return
     */
    private static ApiResBody buildResBody(MethodDoc methodDoc) {
        ClassType returnType = getReturnType(methodDoc);
        ClassDoc returnClassDoc = getClassDoc(returnType);
        ApiResBody body = buildBody(returnClassDoc);
        ClassType paramType = getParamType(returnType);
        if(paramType == null){
            return body;
        }
        ApiResBody data = body.getProperties().get("data");
        ClassDoc paramClassDoc = getClassDoc(paramType);
        if(TypeUtils.isCollecttion(paramClassDoc.typeName())){
            data.setType("array");
            ClassDoc child = getClassDoc(getParamType(paramType));
            data.setItems(buildBody(child));
        }else{
            data = buildBody(paramClassDoc);
        }
        body.getProperties().put("data", data);
        return body;
    }

    /**
     * 构建接口请求体
     * @param methodDoc
     * @return
     */
    private static ApiResBody buildReqBody(MethodDoc methodDoc) {
        Parameter[] parameters = methodDoc.parameters();
        Optional<Parameter> form = Arrays.asList(parameters).stream().filter(p -> p.type().typeName().endsWith("Form")).findFirst();
        if(form.isPresent()){
            ClassDoc classDoc = form.get().type().asClassDoc();
            return buildBody(classDoc);
        }
        return null;
    }

    /**
     *
     * @param bodyClass
     * @return
     */
    private static ApiResBody buildBody(ClassDoc bodyClass){
        ApiResBody body = new ApiResBody();
        FieldDoc[] fields = bodyClass.fields(false);
        List<String> required = new ArrayList<>(10);
        body.setType("object");
        body.setDescription("");
        body.setRequired(required);
        Map<String, ApiResBody> properties = new HashMap<>(10);
        body.setProperties(properties);
        for (FieldDoc f : fields){
            if(f.isFinal() || f.isStatic()){
                continue;
            }
            if(f.name().equals("hash")){
                continue;
            }
            ApiResBody p;
            if(TypeUtils.isPrimitive(f.type())){
                p = new ApiResBody();
                p.setType(getParamType(f));
                p.setDescription(f.commentText());
            }else if(TypeUtils.isCollecttion(f.type())){
                p = new ApiResBody();
                p.setDescription(f.commentText());
                p.setType("array");
                ApiResBody items = buildBody(getClassDoc(getParamType(getFieldType(f))));
                p.setItems(items);
            }else{
                p = buildBody(f.type().asClassDoc());
            }
            properties.put(f.name(), p);
            if(isRequired(f)){
                required.add(f.name());
            }
        }
        return body;
    }

    private static boolean isRequired(FieldDoc f) {
        AnnotationDesc[] annotations = f.annotations();
        for (AnnotationDesc a : annotations){
            if(a.annotationType().name().endsWith("NotNull")){
                return true;
            }
            if(a.annotationType().name().endsWith("NotBlack")){
                return true;
            }
            if(a.annotationType().name().endsWith("NotEmpty")){
                return true;
            }
        }
        return false;
    }

    private static String getParamType(FieldDoc f) {
        String typeName = f.type().typeName().toLowerCase();
        if(typeName.endsWith("string")){
            return "string";
        }
        if(typeName.endsWith("boolean")){
            return "boolean";
        }
        return "number";
    }


    /**
     * 从源码文件读取DOC
     * -doclet 指定自己的docLet类名
     * -classpath 参数指定 源码文件及依赖库的class位置，不提供也可以执行，但无法获取到完整的注释信息(比如annotation)
     * -encoding 指定源码文件的编码格式
     *
     * @param classpath 源码文件及依赖库的class路径
     * @param sourcePath 源文件路径
     */
    private static void readDoc(String classpath, String sourcePath, String filePath){
        String path = ApiDocBuilder.class.getClassLoader().getResource("/").getPath();
        classpath = classpath + ";" + System.getProperty("java.class.path");
        Main.execute(ApiDocBuilder.class.getClassLoader(), new String[]{
                "-docletpath", ApiDocBuilder.class.getClassLoader().getResource("/").getPath(),
                "-doclet", ApiDocBuilder.class.getName(),
                "-classpath", classpath,
                "-sourcepath", sourcePath,
                "-encoding", "utf-8",
                filePath
        });
    }

   /* public static void main(String[] args) throws JsonProcessingException {
        System.out.println(ApiDocBuilder.DOC_ENV);
        System.out.println(ApiDocBuilder.class.getClassLoader().getResource("/"));
        readDoc("E:\\IdeaProjects\\idea-plugin\\out\\production\\idea-plugin", "E:\\IdeaProjects\\idea-plugin\\src", "E:\\IdeaProjects\\idea-plugin\\src\\com\\springer\\doc\\ApiQueryPath.java" );
        ClassDoc classDoc = ROOT_DOC.classes()[0];
        MethodDoc methodDoc = classDoc.methods()[0];
        ApiResBody body = buildResBody(methodDoc);
        System.out.println(HttpUtils.getObjMapper().writeValueAsString(body));

    }*/

    /**
     * 获取属性类型的泛型
     * @param fieldDoc
     * @return
     */
    public static ClassType getFieldType(FieldDoc fieldDoc){
        Object sym = TypeUtils.getFieldValue(fieldDoc, "sym");
        ClassType type = (ClassType) TypeUtils.getFieldValue(sym, "type");
        return type;
    }

    /**
     * 获取方法返回结果类型的泛型
     * @param methodDoc
     * @return
     */
    public static ClassType getReturnType(MethodDoc methodDoc){
        JCTree.JCMethodDecl tree = (JCTree.JCMethodDecl) TypeUtils.getFieldValue(methodDoc, "tree");
        ClassType returnType = (ClassType) tree.restype.type;
        return returnType;
    }

    public static ClassType getParamType(ClassType type){
        com.sun.tools.javac.util.List<com.sun.tools.javac.code.Type> types = type.allparams();
        if(types == null || types.size() == 0){
            return null;
        }
        return (ClassType) types.get(0);
    }

    public static ClassDoc getClassDoc(ClassType type){
        return DOC_ENV.getClassDoc((Symbol.ClassSymbol) type.tsym);
    }


}
