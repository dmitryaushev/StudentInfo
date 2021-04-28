package com.luxoft.studentinfo;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewLayout;

import com.luxoft.studentinfo.view.GroupView;

public class Perspective implements IPerspectiveFactory {

	@Override	
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);
		layout.addView(GroupView.ID, IPageLayout.LEFT, 0.3f, layout.getEditorArea());
		IViewLayout contactsView = layout.getViewLayout(GroupView.ID);
	    contactsView.setCloseable(false);
	}
}
