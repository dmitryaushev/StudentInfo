package com.luxoft.studentinfo.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.view.ViewManager;

public class DeleteHandler extends AbstractHandler {
	
	public final static String ID = "com.luxoft.studentinfo.Delete";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		Entry entry = (Entry) selection.getFirstElement();
		if (entry != null) {
			entry.getParent().removeEntry(entry);
			ViewManager.getInstance().getTreeViewer().refresh();
		}
		return null;
	}

}
