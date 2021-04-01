package com.luxoft.studentinfo;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class SaveAction extends Action implements IWorkbenchAction {
	
	public final static String ID = "com.luxoft.studentInfo.save";
	private final IWorkbenchWindow _window;
	
	public SaveAction(IWorkbenchWindow window) {
		_window = window;
		setId(ID);
		setText("&Save");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("com.luxoft.studentInfo", IImageKeys.SAVE));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
