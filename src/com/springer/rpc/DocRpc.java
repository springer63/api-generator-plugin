/*
 *
 *  * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 *  * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 *  * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 *  * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 *  * Vestibulum commodo. Ut rhoncus gravida arcu.
 *
 */

package com.springer.rpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.springer.doc.ApiDoc;
import com.springer.plugin.GeneratorSetting;
import com.springer.utils.HttpUtils;
import com.springer.utils.ResponseWrap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chenjianhui
 * @create 2018/04/27
 **/
public class DocRpc {

    private static final String API_PATH_LOGIN = "/api/user/login";

    private static final String API_PATH_GROUP = "/api/group/list";

    private static final String API_PATH_PROJECT = "/api/project/list";

    private static final String API_PATH_CATEGORY = "/api/project/get";

    private static final String API_PATH_INTERFACE_ADD = "/api/interface/add";

    private static final String API_PATH_INTERFACE_UP = "/api/interface/up";

    private static boolean isLogin = false;

    private static HttpUtils httpUtils = HttpUtils.getInstance();

     private static GeneratorSetting setting = GeneratorSetting.getInstance();

    private static String rootPath = setting.getServerPath();

    /**
     * 登录接口管理服务
     * @param username
     * @param password
     * @return
     */
    public static int login(String username, String password){
        ResponseWrap wrap = httpUtils.post(rootPath + API_PATH_LOGIN).addParameter("email", username).addParameter("password", password).execute();
        String result = wrap.getString();
        JsonNode node;
        try {
            node = HttpUtils.getObjMapper().readTree(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int errcode = node.get("errcode").asInt();
        if(errcode != 0){
            throw new RuntimeException(node.get("errmsg").asText());
        }
        return node.get("data").get("uid").asInt();
    }

    /**
     * 获取管理分组
     * @return
     */
    public static Map<Integer, String> listGroup(){
        if(!isLogin){
            login(setting.getUsername(), setting.getPassword());
            isLogin = true;
        }
        JsonNode node = httpUtils.get(rootPath + API_PATH_GROUP).execute().getJsonNode();
        int errcode = node.get("errcode").asInt();
        if(errcode != 0){
            throw new RuntimeException(node.get("errmsg").asText());
        }
        Iterator<JsonNode> data = node.get("data").iterator();
        Map<Integer, String> groups = new HashMap<>(10);
        while (data.hasNext()){
            JsonNode next = data.next();
            groups.put(next.get("_id").asInt(), next.get("group_name").asText());
        }
        return groups;
    }

    /**
     * 获取对应分组的项目列表
     * @param groupId
     * @return
     */
    public static Map<Integer, String> listProject(String groupId){
        JsonNode node = httpUtils.get(rootPath + API_PATH_PROJECT).addParameter("group_id", groupId).execute().getJsonNode();
        int errcode = node.get("errcode").asInt();
        if(errcode != 0){
            throw new RuntimeException(node.get("errmsg").asText());
        }
        Iterator<JsonNode> data = node.get("data").get("list").iterator();
        Map<Integer, String> groups = new HashMap<>(10);
        while (data.hasNext()){
            JsonNode next = data.next();
            groups.put(next.get("_id").asInt(), next.get("name").asText());
        }
        return groups;
    }

    /**
     * 获取对应项目的接口分类类表
     * @param projectId
     * @return
     */
    public static Map<Integer, String> listCategory(String projectId){
        JsonNode node = httpUtils.get(rootPath + API_PATH_CATEGORY).addParameter("id", projectId).execute().getJsonNode();
        int errcode = node.get("errcode").asInt();
        if(errcode != 0){
            throw new RuntimeException(node.get("errmsg").asText());
        }
        Iterator<JsonNode> data = node.get("data").get("cat").iterator();
        Map<Integer, String> groups = new HashMap<>(10);
        while (data.hasNext()){
            JsonNode next = data.next();
            groups.put(next.get("_id").asInt(), next.get("name").asText());
        }
        return groups;
    }

    /**
     * 提交接口数据
     * @param apiDoc
     */
    public static void postDoc(ApiDoc apiDoc) {
        JsonNode node = httpUtils.post(rootPath + API_PATH_INTERFACE_ADD).setParameterJson(apiDoc).execute().getJsonNode();
        int errcode = node.get("errcode").asInt();
        if(errcode == 0){
            System.out.println("API构建成功");
        }else{
            System.out.println("API构建失败： " + node.get("errmsg").asText());
            node = httpUtils.post(rootPath + API_PATH_INTERFACE_UP).setParameterJson(apiDoc).execute().getJsonNode();
            System.out.println(node.toString());
        }
    }
}
