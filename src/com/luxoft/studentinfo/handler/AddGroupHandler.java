package com.luxoft.studentinfo.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.luxoft.studentinfo.dialog.AddGroupDialog;
import com.luxoft.studentinfo.model.Folder;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.ModelManager;
import com.luxoft.studentinfo.view.ViewManager;

public class AddGroupHandler extends AbstractHandler {
	
	public final static String ID = "com.luxoft.studentinfo.AddGroup";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		AddGroupDialog dialog = new AddGroupDialog(window.getShell());
		if (dialog.open() == Window.OK) {
			String name = dialog.getName();
			
			Folder folder = ModelManager.getInstance().getStateModel().getFolder();
			Group group = new Group(folder, name);
			folder.addEntry(group);
			
			ViewManager.getInstance().getTreeViewer().refresh();
		}
		return null;
	}

}
