package com.springer.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * GeneratorAction
 *
 * @author chenjianhui
 * @create 2018/04/24
 **/
public class GeneratorAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiMethod psiMethod = this.getPsiMethodFromContext(e);
        PsiClass containingClass = psiMethod.getContainingClass();
        VirtualFile virtualFile = ((PsiJavaFileImpl) containingClass.getParent()).getViewProvider().getVirtualFile();
        SelectDialog dialog = new SelectDialog(virtualFile.getPath(), psiMethod.getName());
        dialog.pack();
        dialog.setVisible(true);
    }


    /**
     * 获取对应的接口方法
     * @param e
     * @return
     */
    private PsiMethod getPsiMethodFromContext(AnActionEvent e) {
        PsiElement elementAt = this.getPsiElement(e);
        PsiMethod psiMethod = PsiTreeUtil.getContextOfType(elementAt, PsiMethod.class);
        return psiMethod;
    }

    /**
     * 获取对应的编辑元素
     * @param e
     * @return
     */
    private PsiElement getPsiElement(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if(psiFile != null && editor != null) {
            int offset = editor.getCaretModel().getOffset();
            return psiFile.findElementAt(offset);
        } else {
            e.getPresentation().setEnabled(false);
            return null;
        }
    }
}
