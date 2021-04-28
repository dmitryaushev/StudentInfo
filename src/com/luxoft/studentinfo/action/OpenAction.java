package com.luxoft.studentinfo.action;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.luxoft.studentinfo.Application;
import com.luxoft.studentinfo.util.IImageKeys;

public class OpenAction extends Action implements IWorkbenchAction{
	
	public final static String ID = "com.luxoft.studentInfo.open";
	private final IWorkbenchWindow _window;
	
	public OpenAction(IWorkbenchWindow window) {
		_window = window;
		setId(ID);
		setText("&Add");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.OPEN));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
