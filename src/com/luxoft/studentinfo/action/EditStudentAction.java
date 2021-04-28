package com.luxoft.studentinfo.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

public class EditStudentAction extends Action implements ISelectionListener, IWorkbenchAction {
	
	public final static String ID = "com.luxoft.studentInfo.editStudent";
	private final IWorkbenchWindow _window;
	private IStructuredSelection _selection;
	
	public EditStudentAction(IWorkbenchWindow window) {
		_window = window;
		setId(ID);
		setText("&Edit student");
		window.getSelectionService().addSelectionListener(this);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
