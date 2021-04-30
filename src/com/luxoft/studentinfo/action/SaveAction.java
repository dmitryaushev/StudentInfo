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

public class SaveAction extends Action implements IWorkbenchAction {
	
	public final static String ID = "com.luxoft.studentInfo.save";
	private final IWorkbenchWindow _window;
	
	public SaveAction(IWorkbenchWindow window) {
		_window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText("&Save");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.SAVE));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		FileDialog dialog = new FileDialog(_window.getShell(), SWT.SAVE);
		dialog.setFilterExtensions(new String[] {"*.txt"});
		dialog.setFileName("file.txt");
		String path = dialog.open();
		if (path != null) {
			FileManager.saveToFile(path);			
		}
	}
}
