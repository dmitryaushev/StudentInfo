package com.luxoft.studentinfo.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.luxoft.studentinfo.Application;
import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.util.IImageKeys;
import com.luxoft.studentinfo.view.ViewManager;

public class DeleteAction extends Action implements ISelectionListener, IWorkbenchAction {

	public final static String ID = "com.luxoft.studentInfo.delete";
	private final IWorkbenchWindow _window;
	private IStructuredSelection selection;

	public DeleteAction(IWorkbenchWindow window) {
		_window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText("&Delete");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.DELETE));
		window.getSelectionService().addSelectionListener(this);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
		selection = (IStructuredSelection) incoming;
	}

	@Override
	public void run() {
		Entry entry = (Entry) selection.getFirstElement();
		if (entry != null) {
			entry.getParent().removeEntry(entry);
			ViewManager.getInstance().getTreeViewer().refresh();
		}
	}
}
