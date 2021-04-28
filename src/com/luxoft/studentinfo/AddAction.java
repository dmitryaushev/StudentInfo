package com.luxoft.studentinfo;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.model.Folder;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.ModelManager;
import com.luxoft.studentinfo.model.Student;

public class AddAction extends Action implements IWorkbenchAction {

	public final static String ID = "com.luxoft.studentInfo.add";
	private final IWorkbenchWindow _window;

	public AddAction(IWorkbenchWindow window) {
		_window = window;
		setId(ID);
		setText("&Add student");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.ADD));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		AddStudentDialog dialog = new AddStudentDialog(_window.getShell());
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
			student.setPhotoPath(photoPath);

			Group group;
			Group root = ModelManager.getInstance().getStateModel().getRoot();
			Folder folder = (Folder) root.getEntries()[0];
			Entry[] groups = folder.getEntries();

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
			ViewManager.getInstance().getTreeViewer().refresh();
		}
	}
}
