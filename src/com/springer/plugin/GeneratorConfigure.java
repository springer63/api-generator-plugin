package com.springer.plugin;

import com.intellij.openapi.options.SearchableConfigurable;
import com.springer.rpc.DocRpc;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * API 文档生成器配置
 *
 * @author chenjianhui
 * @create 2018/04/20
 **/
public class GeneratorConfigure implements SearchableConfigurable {

    public static final String PLUGIN_NAME = "ApiGenerator";

    private GeneratorForm form = new GeneratorForm();

    private GeneratorSetting setting = GeneratorSetting.getInstance();
    @Nls
    @Override
    public String getDisplayName() {
        return PLUGIN_NAME;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        String password = setting.getPassword() != null ? setting.getPassword() : "";
        String username = setting.getUsername() != null ? setting.getUsername() : "";
        String server = setting.getServerPath() != null ? setting.getServerPath() : "";
        form.usernameField.setText(username);
        form.serverField.setText(server);
        form.passwordField.setText(password);
        form.classpathText.setText(setting.getClasspath() == null ? "" : setting.getClasspath());
        form.sourcePathText.setText(setting.getSourcePath() == null ? "" : setting.getClasspath());
        return form.mainPanel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply(){
        this.setting.setPassword(this.form.passwordField.getText());
        this.setting.setUsername(this.form.usernameField.getText());
        this.setting.setServerPath(this.form.serverField.getText());
        this.setting.setSourcePath(this.form.sourcePathText.getText());
        this.setting.setClasspath(this.form.classpathText.getText());
        int uid = DocRpc.login(setting.getUsername(), setting.getPassword());
        this.setting.setUid(String.valueOf(uid));
    }

    @Override
    public void reset() {
        this.form.usernameField.setText(this.setting.getUsername());
        this.form.serverField.setText(this.setting.getServerPath());
        this.form.passwordField.setText(this.setting.getPassword());
        this.form.classpathText.setText(this.setting.getClasspath());
        this.form.sourcePathText.setText(this.setting.getSourcePath());
    }

    @NotNull
    @Override
    public String getId() {
        return PLUGIN_NAME;
    }
}
