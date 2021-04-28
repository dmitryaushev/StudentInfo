package com.luxoft.studentinfo;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import com.luxoft.studentinfo.model.Folder;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.ModelManager;

public class AddGroupAction extends Action implements IWorkbenchAction {

	public final static String ID = "com.luxoft.studentInfo.addGroup";
	private final IWorkbenchWindow _window;

	public AddGroupAction(IWorkbenchWindow window) {
		_window = window;
		setId(ID);
		setText("&Add group");
	}

	@Override
	public void dispose() {
	}

	@Override
	public void run() {
		AddGroupDialog dialog = new AddGroupDialog(_window.getShell());
		if (dialog.open() == Window.OK) {
			String name = dialog.getName();
			
			Group root = ModelManager.getInstance().getStateModel().getRoot();
			Folder folder = (Folder) root.getEntries()[0];
			Group group = new Group(folder, name);
			folder.addEntry(group);
			
			ViewManager.getInstance().getTreeViewer().refresh();
		}
	}
}
