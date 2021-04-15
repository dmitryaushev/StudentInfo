package com.luxoft.studentinfo;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class DeleteAction extends Action implements IWorkbenchAction {
	
	public final static String ID = "com.luxoft.studentInfo.delete";
	private final IWorkbenchWindow _window;
	
	public DeleteAction(IWorkbenchWindow window) {
		_window = window;
		setId(ID);
		setText("&Delete");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.DELETE));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
