package com.luxoft.studentinfo;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
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

	private Action infoAction = new InfoAction(PlatformUI.getWorkbench().getWorkbenchWindows()[0]);

	public GroupView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.NONE | SWT.MULTI | SWT.V_SCROLL);
		getSite().setSelectionProvider(treeViewer);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Entry.class);
		treeViewer.setLabelProvider(new WorkbenchLabelProvider());
		treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
		treeViewer.setInput(createGroup());
		treeViewer.addDoubleClickListener(event -> {
			
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			Object selectedObject = selection.getFirstElement();
			ITreeContentProvider provider = (ITreeContentProvider) treeViewer.getContentProvider();
			if (!provider.hasChildren(selectedObject)) {
				infoAction.run();
			} else if (treeViewer.getExpandedState(selectedObject)) {
				treeViewer.collapseToLevel(selectedObject, AbstractTreeViewer.ALL_LEVELS);
			} else {
				treeViewer.expandToLevel(selectedObject, 1);
			}
		});
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
		group1.addEntry(new Student("dima", group1, "ukraine", "kiev", 1,
				"C:\\Users\\DAushev\\eclipse-workspace_3\\com.luxoft.studentInfo\\photos\\dima.jpg"));
		group1.addEntry(new Student("anya", group1, "ukraine", "kherson", 2,
				"C:\\Users\\DAushev\\eclipse-workspace_3\\com.luxoft.studentInfo\\photos\\anya.jpg"));

		Group group2 = new Group(folder, "Group 2");
		folder.addEntry(group2);
		group2.addEntry(new Student("denis", group2, "ukraine", "khemelnitskiy", 3,
				"C:\\Users\\DAushev\\eclipse-workspace_3\\com.luxoft.studentInfo\\photos\\denis.jpg"));
		group2.addEntry(new Student("lera", group2, "ukraine", "kiev", 34,
				"C:\\Users\\DAushev\\eclipse-workspace_3\\com.luxoft.studentInfo\\photos\\lera.jpg"));

		return root;
	}

}
