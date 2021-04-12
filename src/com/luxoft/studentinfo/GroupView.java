package com.luxoft.studentinfo;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;

import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.Student;

public class GroupView extends ViewPart {
	
	public static final String ID = "com.luxoft.studentInfo.GroupView";
	private TreeViewer treeViewer;
	private IAdapterFactory adapterFactory = new AdapterFactory();

	public GroupView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		getSite().setSelectionProvider(treeViewer);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Entry.class);
		treeViewer.setLabelProvider(new WorkbenchLabelProvider());
		treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
		treeViewer.setInput(createGroup());
	}

	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();		
	}
	
	@Override
	public void dispose() {
		Platform.getAdapterManager().unregisterAdapters(adapterFactory);
		super.dispose();
	}
	
	private Group createGroup() {
		Group root = new Group(null, "root");
		
		Group folder = new Group(root, "Folder");
		root.addEntry(folder);
		
		Group group1 = new Group(folder, "Group 1");
		folder.addEntry(group1);
		group1.addEntry(new Student("dima", group1, "ukraine", "kiev", 1));
		group1.addEntry(new Student("anya", group1, "ukraine", "kherson", 2));
		
		Group group2 = new Group(folder, "Group 2");
		folder.addEntry(group2);
		group2.addEntry(new Student("denis", group2, "ukraine", "khemelnitskiy", 3));
		group2.addEntry(new Student("lera", group2, "ukraine", "kiev", 34));
		
		return root;
	}

}
