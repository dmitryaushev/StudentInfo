package com.luxoft.studentinfo;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class ViewManager {

	private static ViewManager viewManager;
	private TreeViewer treeViewer;
	
	public static ViewManager getInstance() {
		if (viewManager == null) {
			viewManager = new ViewManager();
		}
		return viewManager;
	}
	
	public TreeViewer createTreeViewer(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.NONE | SWT.MULTI | SWT.V_SCROLL);
		return treeViewer;
	}
	
	public TreeViewer getTreeViewer() {
		return treeViewer;
	}
}
