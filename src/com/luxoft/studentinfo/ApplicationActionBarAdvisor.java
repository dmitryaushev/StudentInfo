package com.luxoft.studentinfo;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	
	private IWorkbenchAction _exitAction;
	private IWorkbenchAction _aboutAction;
	private IWorkbenchAction _editStudentAction;
	
	private IWorkbenchAction _openAction;
	private IWorkbenchAction _saveAction;
	private IWorkbenchAction _deleteAction;
	private IWorkbenchAction _addAction;
	
	private IWorkbenchAction _infoAction;
	
	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}
	
	@Override
	protected void makeActions(IWorkbenchWindow window) {
		_exitAction = ActionFactory.QUIT.create(window);
		register(_exitAction);
		_aboutAction = ActionFactory.ABOUT.create(window);
		register(_aboutAction);
		_editStudentAction = new EditStudentAction(window);
		register(_editStudentAction);
		
		_openAction = new OpenAction(window);
		register(_openAction);
		_saveAction = new SaveAction(window);
		register(_saveAction);
		_deleteAction = new DeleteAction(window);
		register(_deleteAction);
		_addAction = new AddAction(window);
		register(_deleteAction);
		
		_infoAction = new InfoAction(window);
		register(_infoAction);
	}
	
	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager("&File", "file");
		fileMenu.add(_exitAction);
		
		MenuManager editMenu = new MenuManager("&Edit", "edit");
		editMenu.add(_editStudentAction);
		
		MenuManager helpMenu = new MenuManager("&Help", "help");
		helpMenu.add(_aboutAction);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
	}
	
	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolBarManager = new ToolBarManager(coolBar.getStyle());
		coolBar.add(toolBarManager);
		toolBarManager.add(_openAction);
		toolBarManager.add(_saveAction);
		toolBarManager.add(_deleteAction);
		toolBarManager.add(_addAction);
		toolBarManager.add(_infoAction);
	}

}

