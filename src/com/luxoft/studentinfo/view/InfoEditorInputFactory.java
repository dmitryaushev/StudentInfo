package com.luxoft.studentinfo.view;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.model.Folder;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.ModelManager;
import com.luxoft.studentinfo.model.Student;

public class InfoEditorInputFactory implements IElementFactory {
	
	public static final String ID = "com.luxoft.studentinfo.InfoEditorInput";

	@Override
	public IAdaptable createElement(IMemento memento) {
		String name = memento.getString("studentName");
		String groupName = memento.getString("studentGroup");
		Student student = null;
		
		Folder folder = ModelManager.getInstance().getStateModel().getFolder();
		Entry[] entries = folder.getEntries();
		for(Entry entry : entries) {
			if (entry.getName().equals(groupName)) {
				Group group = (Group) entry;
				for(Entry e : group.getEntries()) {
					if (e.getName().equals(name)) {
						student = (Student) e;						
					}
				}
			}
		}
		return new InfoEditorInput(student);
	}

}
