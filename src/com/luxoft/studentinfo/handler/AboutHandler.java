package com.luxoft.studentinfo.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public class AboutHandler extends AbstractHandler {

	public final static String ID = "com.luxoft.studentinfo.About";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		String about = "Program for students registry.\n" + 
				"Capabilities:\n" + 
				" - Create a group.\n" + 
				" - Create a student.\n" +
				" - Edit a student.\n" +
				" - Save state to file.\n" +
				" - Restore state from file.\n" +
				" - Drag and Drop from tree to editor";
		MessageDialog.openInformation(HandlerUtil.getActiveShell(event), "About", about);
		return null;
	}

}
