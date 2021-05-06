package com.luxoft.studentinfo.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.luxoft.studentinfo.util.FileManager;

public class SaveHandler extends AbstractHandler {
	
	public final static String ID = "com.luxoft.studentinfo.Save";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		FileDialog dialog = new FileDialog(window.getShell(), SWT.SAVE);
		dialog.setFilterExtensions(new String[] {"*.txt"});
		dialog.setFileName("file.txt");
		String path = dialog.open();
		if (path != null) {
			FileManager.saveToFile(path);			
		}
		return null;
	}

}
