package com.luxoft.studentinfo.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.luxoft.studentinfo.dialog.PopulateStudentDialog;
import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.model.Folder;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.ModelManager;
import com.luxoft.studentinfo.model.Student;
import com.luxoft.studentinfo.util.IImageKeys;
import com.luxoft.studentinfo.view.ViewManager;

public class AddStudentHandler extends AbstractHandler {
	
	public final static String ID = "com.luxoft.studentinfo.AddStudent";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		PopulateStudentDialog dialog = new PopulateStudentDialog(window.getShell(), "Add new student");
		if (selection != null && selection.getFirstElement() instanceof Group) {
			Group group = (Group) selection.getFirstElement();
			dialog.setStudentGroupName(group.getName());
		}
		if (dialog.open() == Window.OK) {

			String name = dialog.getName();
			String groupName = dialog.getGroup();
			String adress = dialog.getAdress();
			String city = dialog.getCity();
			String result = dialog.getResult();
			String photoPath = dialog.getPhotoPath();

			Student student = new Student();
			student.setName(name);
			student.setAdress(adress);
			student.setCity(city);
			student.setResult(Integer.valueOf(result));
			if (photoPath.isEmpty()) {
				student.setPhotoPath(IImageKeys.DEFAULT_PHOTO);
			} else {
				student.setPhotoPath(photoPath);				
			}

			Group group;
			Folder folder = ModelManager.getInstance().getStateModel().getFolder();
			Entry[] groups = folder.getEntries();

			for (Entry e : groups) {
				if (e.getName().equals(groupName)) {
					group = (Group) e;
					student.setGroup(group);
					group.addEntry(student);
					ViewManager.getInstance().getTreeViewer().refresh();
					return null;
				}
			}
			group = new Group(folder, groupName);
			folder.addEntry(group);
			student.setGroup(group);
			group.addEntry(student);
			ViewManager.getInstance().getTreeViewer().refresh();
		}
		return null;
	}

}
