package com.springer.plugin;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;

/**
 * API DOC 配置数据
 *
 * @author chenjianhui
 * @create 2018/04/24
 **/
@State(
    name = "com.springer.plugin.GeneratorSetting",
    storages = {@Storage(
        id = "other",
        file = "$APP_CONFIG$/api-generator.xml"
    )}
)
public class GeneratorSetting implements PersistentStateComponent<Element> {

    public static final String SERVER_KEY = "serverPath";

    public static final String USERNAME_KEY = "username";

    public static final String PASSWORD_KEY = "password";

    public static final String GROUP_KEY = "groupKey";

    public static final String UID_KEY = "uidKey";

    public static final String PROJECT_KEY = "projectKey";

    public static final String CATEGORY_KEY = "categoryKey";

    public static final String SOURCE_PATH_KEY = "sourcePathKey";

    public static final String CLASS_PATH_KEY = "classPathKey";

    private String serverPath;

    private String sourcePath;

    private String classpath;

    private String username;

    private String password;

    private String group;

    private String uid;

    private String project;

    public String getGroup() {
        if(group == null){
            return "";
        }
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getProject() {
        if(project == null){
            return "";
        }
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCategory() {
        if(category == null){
            return "";
        }
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;

    public static GeneratorSetting getInstance() {
        return ServiceManager.getService(GeneratorSetting.class);
    }

    @Nullable
    @Override
    public Element getState() {
        Element element = new Element(GeneratorSetting.class.getSimpleName());
        element.setAttribute(SERVER_KEY, this.getServerPath());
        element.setAttribute(USERNAME_KEY, this.getUsername());
        element.setAttribute(PASSWORD_KEY, this.getPassword());
        element.setAttribute(SOURCE_PATH_KEY, this.getSourcePath());
        element.setAttribute(CLASS_PATH_KEY, this.getClasspath());
        element.setAttribute(UID_KEY, String.valueOf(this.getUid()));
        element.setAttribute(GROUP_KEY, this.getGroup());
        element.setAttribute(PROJECT_KEY, this.getProject());
        element.setAttribute(CATEGORY_KEY, this.getCategory());
        System.out.println("getState: " + element);
        return element;
    }

    @Override
    public void loadState(Element state) {
        System.out.println("loadState: " + state);
        this.setServerPath(state.getAttributeValue(SERVER_KEY));
        this.setUsername(state.getAttributeValue(USERNAME_KEY));
        this.setPassword(state.getAttributeValue(PASSWORD_KEY));
        this.setClasspath(state.getAttributeValue(CLASS_PATH_KEY));
        this.setSourcePath(state.getAttributeValue(SOURCE_PATH_KEY));
        this.setGroup(state.getAttributeValue(GROUP_KEY));
        this.setProject(state.getAttributeValue(PROJECT_KEY));
        this.setCategory(state.getAttributeValue(CATEGORY_KEY));
        this.setUid(state.getAttributeValue(CATEGORY_KEY));
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
