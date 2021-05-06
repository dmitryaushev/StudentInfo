package com.luxoft.studentinfo.view;

import java.util.ArrayList;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.EditorInputTransfer;
import org.eclipse.ui.part.ViewPart;

import com.luxoft.studentinfo.EditorAreaDropAdapter;
import com.luxoft.studentinfo.action.StudentInfoAction;
import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.ModelManager;
import com.luxoft.studentinfo.model.Student;

public class GroupView extends ViewPart {

	public static final String ID = "com.luxoft.studentInfo.GroupView";
	private TreeViewer treeViewer;
	private IAdapterFactory adapterFactory = new AdapterFactory();

	private Action infoAction = new StudentInfoAction(PlatformUI.getWorkbench().getWorkbenchWindows()[0]);

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
		treeViewer.addDoubleClickListener(event -> {

			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			Object selectedObject = selection.getFirstElement();
			ITreeContentProvider provider = (ITreeContentProvider) treeViewer.getContentProvider();
			if (!provider.hasChildren(selectedObject) && selectedObject instanceof Student) {
				infoAction.run();
			} else if (treeViewer.getExpandedState(selectedObject)) {
				treeViewer.collapseToLevel(selectedObject, AbstractTreeViewer.ALL_LEVELS);
			} else {
				treeViewer.expandToLevel(selectedObject, 1);
			}
		});

		setGroupsToTreeViewer();
		createContextMenu();
		initDragAndDrop(treeViewer);
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
	
	protected void initDragAndDrop(StructuredViewer viewer) {
		int operations = DND.DROP_COPY;
		Transfer[] transfers = new Transfer[] {EditorInputTransfer.getInstance()};
		DragSourceListener listener = new DragSourceAdapter() {
			public void dragSetData(DragSourceEvent event) {
				if (EditorInputTransfer.getInstance().isSupportedType(event.dataType)) {
					Student[] students = getStudents();
					EditorInputTransfer.EditorInputData [] inputs = new EditorInputTransfer.EditorInputData[students.length];
					if (students.length > 0) {
						for (int i = 0; i < students.length; i++) {
							inputs[i] = EditorInputTransfer.createEditorInputData(InfoEditor.ID, new InfoEditorInput(students[i]));
						}
						event.data = inputs;
						EditorAreaDropAdapter.setInputs(inputs);
						return;
					}
				}
				event.doit = false;
			}
			public void dragFinished(DragSourceEvent event) {}
			public void dragStart(DragSourceEvent event) {
				super.dragStart(event);
			}
		};
		viewer.addDragSupport(operations, transfers, listener);
	}
	
	private Student[] getStudents() {
		ITreeSelection selection = treeViewer.getStructuredSelection();
		ArrayList<Student> students = new ArrayList<>();
		if (!selection.isEmpty()) {
			for (Object object : selection.toArray()) {
				if (object instanceof Student) {
					Student student = (Student) object;
					students.add(student);
				} else {
					students.clear();
					break;
				}
			}
		}
		return students.toArray(new Student[] {});
	}

	private void setGroupsToTreeViewer() {
		Group root = ModelManager.getInstance().getStateModel().getRoot();
		treeViewer.setInput(root);
	}

	private void createContextMenu() {		
		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, treeViewer);
	}
}
