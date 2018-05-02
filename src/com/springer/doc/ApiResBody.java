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
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chenjianhui
 * @create 2018/04/25
 **/
public class ApiResBody {

    private String type;

    private String defaultValue;

    private String description;

    private ApiResBody items;

    private List<String> required;

    private Map<String, ApiResBody> properties;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }

    public Map<String, ApiResBody> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, ApiResBody> properties) {
        this.properties = properties;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public ApiResBody getItems() {
        return items;
    }

    public void setItems(ApiResBody items) {
        this.items = items;
    }
}
