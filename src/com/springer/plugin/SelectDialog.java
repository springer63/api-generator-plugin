/*
 *
 *  * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 *  * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 *  * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 *  * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 *  * Vestibulum commodo. Ut rhoncus gravida arcu.
 *
 */

package com.springer.plugin;

import com.springer.doc.ApiDoc;
import com.springer.doc.ApiDocBuilder;
import com.springer.rpc.DocRpc;

import javax.swing.*;
import java.awt.event.*;
import java.util.Map;

/**
 * 选择接口所属分类对话框
 *
 * @author chenjianhui
 */
public class SelectDialog extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox groupBox;
    private JComboBox projectBox;
    private JComboBox categoryBox;

    private String filePath;

    private String methodName;

    public SelectDialog(String filePath, String methodName) {
        this.filePath = filePath;
        this.methodName = methodName;
        setContentPane(contentPane);
        setModal(true);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        Map<Integer, String> group = DocRpc.listGroup();
        group.entrySet().stream().forEach(e -> groupBox.addItem(e));
        groupBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                Map.Entry<Integer, String> p = (Map.Entry<Integer, String>) groupBox.getSelectedItem();
                Map<Integer, String> project = DocRpc.listProject(p.getKey().toString());
                project.entrySet().stream().forEach(n -> projectBox.addItem(n));
            }
        });
        projectBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                Map.Entry<Integer, String> p = (Map.Entry<Integer, String>) e.getItem();
                Map<Integer, String> category = DocRpc.listCategory(p.getKey().toString());
                category.entrySet().stream().forEach(n -> categoryBox.addItem(n));
            }
        });
    }

    private void onOK() {
        GeneratorSetting setting = GeneratorSetting.getInstance();
        Map.Entry<Integer, String> group = (Map.Entry<Integer, String>) this.groupBox.getSelectedItem();
        Map.Entry<Integer, String> project = (Map.Entry<Integer, String>) this.projectBox.getSelectedItem();
        Map.Entry<Integer, String> category = (Map.Entry<Integer, String>) this.categoryBox.getSelectedItem();
        setting.setGroup(group.getKey().toString());
        setting.setProject(project.getKey().toString());
        setting.setCategory(category.getKey().toString());
        ApiDoc apiDoc = ApiDocBuilder.build(setting.getClasspath(), setting.getSourcePath(), this.filePath, this.methodName);
        apiDoc.setProject_id(Integer.parseInt(setting.getProject()));
        apiDoc.setCatid(Integer.parseInt(setting.getCategory()));
        DocRpc.postDoc(apiDoc);
        dispose();
    }

    private void onCancel() {
        dispose();
    }



}
