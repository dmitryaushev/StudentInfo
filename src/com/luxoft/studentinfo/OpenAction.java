package com.luxoft.studentinfo;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class OpenAction extends Action implements IWorkbenchAction{
	
	public final static String ID = "com.luxoft.studentInfo.open";
	private final IWorkbenchWindow _window;
	
	public OpenAction(IWorkbenchWindow window) {
		_window = window;
		setId(ID);
		setText("&Add");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("com.luxoft.studentInfo", IImageKeys.OPEN));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
