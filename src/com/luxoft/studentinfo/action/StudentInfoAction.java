package com.luxoft.studentinfo.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import com.luxoft.studentinfo.model.Student;
import com.luxoft.studentinfo.view.InfoEditor;
import com.luxoft.studentinfo.view.InfoEditorInput;

public class StudentInfoAction extends Action implements ISelectionListener, IWorkbenchAction {

	public final static String ID = "com.luxoft.studentinfo.studentInfo";
	private final IWorkbenchWindow window;
	private IStructuredSelection selection;

	public StudentInfoAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText("&Open student");
		setToolTipText("Info of selected student");
		window.getSelectionService().addSelectionListener(this);
	}

	@Override
	public void dispose() {
		window.getSelectionService().removeSelectionListener(this);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
		if (incoming instanceof IStructuredSelection) {
			selection = (IStructuredSelection) incoming;
			setEnabled(selection.getFirstElement() instanceof Student);
		} else {
			setEnabled(false);
		}
	}

	@Override
	public void run() {
		Object item = selection.getFirstElement();
		Student student = (Student) item;
		IWorkbenchPage page = window.getActivePage();
		InfoEditorInput input = new InfoEditorInput(student);
		try {
			page.openEditor(input, InfoEditor.ID);
		} catch (PartInitException e) {
		}
	}
}