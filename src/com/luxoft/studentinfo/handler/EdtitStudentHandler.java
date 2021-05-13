package com.luxoft.studentinfo.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.luxoft.studentinfo.dialog.PopulateStudentDialog;
import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.model.Folder;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.ModelManager;
import com.luxoft.studentinfo.model.Student;
import com.luxoft.studentinfo.view.InfoEditor;
import com.luxoft.studentinfo.view.InfoEditorInput;
import com.luxoft.studentinfo.view.ViewManager;

public class EdtitStudentHandler extends AbstractHandler {
	
	public final static String ID = "com.luxoft.studentinfo.EditStudent";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		Student student = (Student) selection.getFirstElement();
		InfoEditor editor = (InfoEditor) page.findEditor(new InfoEditorInput(student));
		
		PopulateStudentDialog dialog = new PopulateStudentDialog(window.getShell());
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
				Folder folder = ModelManager.getInstance().getStateModel().getFolder();
				Entry[] groups = folder.getEntries();
				for (Entry e : groups) {
					if (e.getName().equals(studentGroupName)) {
						e.removeEntry(student);
						break;
					}
				}
				for(int i = 0; i < groups.length; i++) {
					if (groups[i].getName().equals(groupName)) {
						group = (Group) groups[i];
						student.setGroup(group);
						group.addEntry(student);
						break;
					}
					if (i == groups.length - 1) {
						group = new Group(folder, groupName);
						folder.addEntry(group);
						student.setGroup(group);
						group.addEntry(student);
					}
				}
			}			
			ViewManager.getInstance().getTreeViewer().refresh();
			if (editor != null) {
				editor.update();				
			}
		}
		return null;
	}

}
