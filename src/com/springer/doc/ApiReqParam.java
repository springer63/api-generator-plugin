package com.springer.doc;

/**
 * ${DESCRIPTION}
 *
 * @author chenjianhui
 * @create 2018/04/25
 **/
public class ApiReqParam {

    /**
     * 参数ID
     */
    private Integer _id;

    /**
     * 参数名
     */
    private String name;

    /**
     * 参数类型
     */
    private String type = "text";

    /**
     * 参数描述
     */
    private String desc;

    /**
     * 参数值
     */
    private String value;

    /**
     * 参数示例
     */
    private String example;

    private boolean required = true;


}
