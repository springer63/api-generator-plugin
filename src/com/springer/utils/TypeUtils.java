/*
 *
 *  * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 *  * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 *  * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 *  * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 *  * Vestibulum commodo. Ut rhoncus gravida arcu.
 *
 */

package com.springer.utils;

import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Type;
import com.sun.tools.javac.tree.JCTree;

import java.lang.reflect.Field;

/**
 * ${DESCRIPTION}
 *
 * @author chenjianhui
 * @create 2018/04/26
 **/
public class TypeUtils {


    /**
     * 判断一个类型是否是集合类型
     * @param type
     * @return
     */
    public static boolean isCollecttion(Type type){
        return isCollecttion(type.typeName());
    }

    /**
     * 判断一个类型是否是集合类型
     * @param typeName
     * @return
     */
    public static boolean isCollecttion(String typeName){
        if(typeName == null){
            return false;
        }
        if(typeName.endsWith("Collection")){
            return true;
        }
        if(typeName.endsWith("List")){
            return true;
        }
        if(typeName.endsWith("Set")){
            return true;
        }
        return false;
    }

    public static boolean isPrimitive(Type type) {
        if(type.typeName().equals("String")){
            return true;
        }
        if(type.typeName().equals("Integer") || type.typeName().equals("int")){
            return true;
        }
        if(type.typeName().equals("Long") || type.typeName().equals("long")){
            return true;
        }
        if(type.typeName().equals("Boolean") || type.typeName().equals("boolean")){
            return true;
        }
        if(type.typeName().equals("Float") || type.typeName().equals("float")){
            return true;
        }
        if(type.typeName().equals("Double") || type.typeName().equals("double")){
            return true;
        }
        if(type.typeName().equals("Integer") || type.typeName().equals("int")){
            return true;
        }
        if(type.typeName().equals("Short") || type.typeName().equals("short")){
            return true;
        }
        if(type.typeName().equals("Character") || type.typeName().equals("char")){
            return true;
        }
        if(type.typeName().equals("Date")){
            return true;
        }
        return false;
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     * @param object
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object object, String fieldName){
        Field field = getDeclaredField(object, fieldName);
        if (field == null){
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        field.setAccessible(true);
        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     * @param object
     * @param filedName
     * @return
     */
    public static Field getDeclaredField(Object object, String filedName){
        for(Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()){
            try {
                return superClass.getDeclaredField(filedName);
            } catch (NoSuchFieldException e) {
                //Field 不在当前类定义, 继续向上转型
            }
        }
        return null;
    }


    /**
     * 获取属性类型的泛型
     * @param fieldDoc
     * @return
     */
    public static com.sun.tools.javac.code.Type.ClassType getFieldType(FieldDoc fieldDoc){
        Object sym = TypeUtils.getFieldValue(fieldDoc, "sym");
        com.sun.tools.javac.code.Type.ClassType type = (com.sun.tools.javac.code.Type.ClassType) TypeUtils.getFieldValue(sym, "type");
        return type;
    }

    /**
     * 获取方法返回结果类型的泛型
     * @param methodDoc
     * @return
     */
    public static com.sun.tools.javac.code.Type.ClassType getReturnType(MethodDoc methodDoc){
        JCTree.JCMethodDecl tree = (JCTree.JCMethodDecl) TypeUtils.getFieldValue(methodDoc, "tree");
        com.sun.tools.javac.code.Type.ClassType returnType = (com.sun.tools.javac.code.Type.ClassType) tree.restype.type;
        return returnType;
    }

    /**
     * 获取类型的泛型
     * @param type
     * @return
     */
    public static com.sun.tools.javac.code.Type.ClassType getParamType(com.sun.tools.javac.code.Type.ClassType type){
        com.sun.tools.javac.util.List<com.sun.tools.javac.code.Type> types = type.allparams();
        if(types == null || types.size() == 0){
            return null;
        }
        return (com.sun.tools.javac.code.Type.ClassType) types.get(0);
    }

}
