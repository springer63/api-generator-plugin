/*
 *
 *  * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 *  * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 *  * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 *  * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 *  * Vestibulum commodo. Ut rhoncus gravida arcu.
 *
 */
package com.springer.doc;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chenjianhui
 * @create 2018/04/25
 **/
public class ApiDoc {

    private Integer _id;

    private int _v;

    private int catid;

    private int index;

    private int uid;

    private int project_id;

    private int edit_uid;

    private String username;

    private String method;

    private String desc;

    private String markdown;

    private String path;

    private String type = "static";

    private String title;

    private String res_body_type = "json";

    private String res_body;

    private String status = "done";

    private String req_body_type = "json";

    private boolean api_opened = false;

    private boolean req_body_is_json_schema = true;

    private boolean res_body_is_json_schema = true;

    private long add_time = System.currentTimeMillis() / 1000;

    private long up_time = System.currentTimeMillis() / 1000;

    private List<ApiReqParam> req_body_form;

    private String req_body_other;

    private List<ApiReqParam> req_headers;

    private List<ApiReqParam> req_params;

    private List<ApiReqParam> req_query;

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public int get_v() {
        return _v;
    }

    public void set_v(int _v) {
        this._v = _v;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getEdit_uid() {
        return edit_uid;
    }

    public void setEdit_uid(int edit_uid) {
        this.edit_uid = edit_uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRes_body_type() {
        return res_body_type;
    }

    public void setRes_body_type(String res_body_type) {
        this.res_body_type = res_body_type;
    }

    public String getRes_body() {
        return res_body;
    }

    public void setRes_body(String res_body) {
        this.res_body = res_body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReq_body_type() {
        return req_body_type;
    }

    public void setReq_body_type(String req_body_type) {
        this.req_body_type = req_body_type;
    }

    public boolean isApi_opened() {
        return api_opened;
    }

    public void setApi_opened(boolean api_opened) {
        this.api_opened = api_opened;
    }

    public boolean isReq_body_is_json_schema() {
        return req_body_is_json_schema;
    }

    public void setReq_body_is_json_schema(boolean req_body_is_json_schema) {
        this.req_body_is_json_schema = req_body_is_json_schema;
    }

    public boolean isRes_body_is_json_schema() {
        return res_body_is_json_schema;
    }

    public void setRes_body_is_json_schema(boolean res_body_is_json_schema) {
        this.res_body_is_json_schema = res_body_is_json_schema;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public long getUp_time() {
        return up_time;
    }

    public void setUp_time(long up_time) {
        this.up_time = up_time;
    }

    public List<ApiReqParam> getReq_body_form() {
        return req_body_form;
    }

    public void setReq_body_form(List<ApiReqParam> req_body_form) {
        this.req_body_form = req_body_form;
    }

    public List<ApiReqParam> getReq_headers() {
        return req_headers;
    }

    public void setReq_headers(List<ApiReqParam> req_headers) {
        this.req_headers = req_headers;
    }

    public List<ApiReqParam> getReq_params() {
        return req_params;
    }

    public void setReq_params(List<ApiReqParam> req_params) {
        this.req_params = req_params;
    }

    public List<ApiReqParam> getReq_query() {
        return req_query;
    }

    public void setReq_query(List<ApiReqParam> req_query) {
        this.req_query = req_query;
    }

    public String getReq_body_other() {
        return req_body_other;
    }

    public void setReq_body_other(String req_body_other) {
        this.req_body_other = req_body_other;
    }
}
