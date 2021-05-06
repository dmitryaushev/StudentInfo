package com.luxoft.studentinfo.action;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.luxoft.studentinfo.model.Student;
import com.luxoft.studentinfo.view.InfoEditor;
import com.luxoft.studentinfo.view.InfoEditorInput;

public class OpenStudentInfoHandler extends AbstractHandler {
	
	public final static String ID = "com.luxoft.studentinfo.OpenStudentInfo";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		
		Student student = (Student) selection.getFirstElement();
		IWorkbenchPage page = window.getActivePage();
		InfoEditorInput input = new InfoEditorInput(student);
		try {
			page.openEditor(input, InfoEditor.ID);
		} catch (PartInitException e) {
		}
		return null;
	}

}
