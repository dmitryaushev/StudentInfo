package com.luxoft.studentinfo;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	@Override	
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.addView(GroupView.ID, IPageLayout.LEFT, 0.3f, layout.getEditorArea());
		//layout.addStandaloneView(GroupView.ID, false, IPageLayout.LEFT, 1.0f, layout.getEditorArea());
	}
}
