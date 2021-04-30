package com.luxoft.studentinfo.action;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.luxoft.studentinfo.Application;
import com.luxoft.studentinfo.util.FileManager;
import com.luxoft.studentinfo.util.IImageKeys;
import com.luxoft.studentinfo.view.ViewManager;

public class OpenAction extends Action implements IWorkbenchAction{
	
	public final static String ID = "com.luxoft.studentInfo.open";
	private final IWorkbenchWindow _window;
	
	public OpenAction(IWorkbenchWindow window) {
		_window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText("&Open");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.OPEN));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		FileDialog dialog = new FileDialog(_window.getShell(), SWT.OPEN);
		dialog.setFilterExtensions(new String[] {"*.txt"});
		String path = dialog.open();
		if (path != null) {
			FileManager.readFromFile(path);
			ViewManager.getInstance().getTreeViewer().refresh();		
		}
	}
}
