package com.luxoft.studentinfo;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;

import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.ModelManager;
import com.luxoft.studentinfo.model.Student;

public class GroupView extends ViewPart {

	public static final String ID = "com.luxoft.studentInfo.GroupView";
	private TreeViewer treeViewer;
	private IAdapterFactory adapterFactory = new AdapterFactory();

	private Action infoAction = new InfoAction(PlatformUI.getWorkbench().getWorkbenchWindows()[0]);
	private Action deleteAction = new DeleteAction(PlatformUI.getWorkbench().getWorkbenchWindows()[0]);
	private Action addAction = new AddAction(PlatformUI.getWorkbench().getWorkbenchWindows()[0]);
	private Action addGroupAction = new AddGroupAction(PlatformUI.getWorkbench().getWorkbenchWindows()[0]);

	public GroupView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		treeViewer = ViewManager.getInstance().createTreeViewer(parent);
		getSite().setSelectionProvider(treeViewer);
		Platform.getAdapterManager().registerAdapters(adapterFactory, Entry.class);
		treeViewer.setLabelProvider(new WorkbenchLabelProvider());
		treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
		setGroupsToTreeViewer();
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

		createContextManeu();
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

	private void setGroupsToTreeViewer() {
		Group root = ModelManager.getInstance().getStateModel().getRoot();
		treeViewer.setInput(root);
	}

	private void createContextManeu() {
		
		addAction.setImageDescriptor(null);
		deleteAction.setImageDescriptor(null);
		
		MenuManager menuManager = new MenuManager();
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(listener -> {
			IStructuredSelection selection = treeViewer.getStructuredSelection();
			Entry entry = (Entry) selection.getFirstElement();
			if (entry instanceof Student) {
				deleteAction.setText("Delete student");
				menuManager.add(infoAction);
				menuManager.add(deleteAction);
			} else if(entry instanceof Group){
				deleteAction.setText("Delete group");
				menuManager.add(addAction);
				menuManager.add(deleteAction);
			} else {
				menuManager.add(addGroupAction);
				deleteAction.setText("Delete folder");
				menuManager.add(deleteAction);
			}
		});
		Menu menu = menuManager.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, treeViewer);
	}
}
