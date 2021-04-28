package com.luxoft.studentinfo.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import com.luxoft.studentinfo.dialog.PopulateStudentDialog;
import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.model.Folder;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.ModelManager;
import com.luxoft.studentinfo.model.Student;
import com.luxoft.studentinfo.view.InfoEditor;
import com.luxoft.studentinfo.view.ViewManager;

public class EditStudentAction extends Action implements ISelectionListener, IWorkbenchAction {

	public final static String ID = "com.luxoft.studentInfo.editStudent";
	private final IWorkbenchWindow _window;
	private IStructuredSelection _selection;

	public EditStudentAction(IWorkbenchWindow window) {
		_window = window;
		setId(ID);
		setText("&Edit student");
		window.getSelectionService().addSelectionListener(this);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
		if (incoming instanceof IStructuredSelection) {
			_selection = (IStructuredSelection) incoming;
			setEnabled(_selection.getFirstElement() instanceof Student);
		} else {
			setEnabled(false);
		}
	}

	@Override
	public void run() {
		PopulateStudentDialog dialog = new PopulateStudentDialog(_window.getShell());
		Student student = (Student) _selection.getFirstElement();
		dialog.setStudent(student);
		if (dialog.open() == Window.OK) {
			String name = dialog.getName();
			String groupName = dialog.getGroup();
			String adress = dialog.getAdress();
			String city = dialog.getCity();
			String result = dialog.getResult();
			String photoPath = dialog.getPhotoPath();

			student.setName(name);
			student.setAdress(adress);
			student.setCity(city);
			student.setResult(Integer.valueOf(result));
			student.setPhotoPath(photoPath);

			String studentGroupName = student.getGroup().getName();
			if (!studentGroupName.equals(groupName)) {
				Group group;
				Group root = ModelManager.getInstance().getStateModel().getRoot();
				Folder folder = (Folder) root.getEntries()[0];
				Entry[] groups = folder.getEntries();
				for (Entry e : groups) {
					if (e.getName().equals(studentGroupName)) {
						e.removeEntry(student);
					}
				}
				for (Entry e : groups) {
					if (e.getName().equals(groupName)) {
						group = (Group) e;
						student.setGroup(group);
						group.addEntry(student);
						ViewManager.getInstance().getTreeViewer().refresh();
						return;
					}
				}
				group = new Group(folder, groupName);
				folder.addEntry(group);
				student.setGroup(group);
				group.addEntry(student);
			}
			
			ViewManager.getInstance().getTreeViewer().refresh();
		}
	}

}
