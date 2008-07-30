package net.shu_cream.eclipse.font;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

public class ToLargeAction implements IEditorActionDelegate {

    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
    }

    public void run(IAction action) {
        FontPlugin.getDefault().toLarge();
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }

}
