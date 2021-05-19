package com.luxoft.studentinfo.handler;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.Student;
import com.luxoft.studentinfo.view.InfoEditorInput;
import com.luxoft.studentinfo.view.ViewManager;

public class DeleteHandler extends AbstractHandler {

	public final static String ID = "com.luxoft.studentinfo.Delete";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		Iterator<Entry> i = selection.iterator();
		while (i.hasNext()) {
			Entry entry = i.next();
			entry.getParent().removeEntry(entry);
			ViewManager.getInstance().getTreeViewer().refresh();
			if (entry instanceof Group) {
				List<Entry> entries = ((Group) entry).getEntries();
				for(Entry e : entries) {
					closeEditor(e);
				}
			}
			if (entry instanceof Student) {
				closeEditor(entry);
			}
		}
		return null;
	}
	
	private void closeEditor(Entry entry) {
		Student student = (Student) entry;
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart part = page.findEditor(new InfoEditorInput(student));
		page.closeEditor(part, false);
	}

}
