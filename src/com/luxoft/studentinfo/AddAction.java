package com.luxoft.studentinfo;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class AddAction extends Action implements IWorkbenchAction {
	
	public final static String ID = "com.luxoft.studentInfo.add";
	private final IWorkbenchWindow _window;
	
	public AddAction(IWorkbenchWindow window) {
		_window = window;
		setId(ID);
		setText("&Add");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.ADD));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
