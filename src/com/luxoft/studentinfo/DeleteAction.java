package com.luxoft.studentinfo;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.internal.e4.compatibility.SelectionService;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.ModelManager;
import com.luxoft.studentinfo.model.Student;

public class DeleteAction extends Action implements ISelectionListener, IWorkbenchAction {

	public final static String ID = "com.luxoft.studentInfo.delete";
	private final IWorkbenchWindow _window;
	private IStructuredSelection selection;

	public DeleteAction(IWorkbenchWindow window) {
		_window = window;
		setId(ID);
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
