package com.luxoft.studentinfo.dnd;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.ui.part.EditorInputTransfer;

import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.Student;
import com.luxoft.studentinfo.view.InfoEditor;
import com.luxoft.studentinfo.view.InfoEditorInput;
import com.luxoft.studentinfo.view.ViewManager;

public class EditorAreaDragAdapter extends DragSourceAdapter {

	@Override
	public void dragSetData(DragSourceEvent event) {
		if (EditorInputTransfer.getInstance().isSupportedType(event.dataType)) {
			List<Student> students = getStudents();
			EditorInputTransfer.EditorInputData[] inputs = new EditorInputTransfer.EditorInputData[students.size()];
			if (students.size() > 0) {
				for (int i = 0; i < students.size(); i++) {
					inputs[i] = EditorInputTransfer.createEditorInputData(InfoEditor.ID,
							new InfoEditorInput(students.get(i)));
				}
				event.data = inputs;
				return;
			}
		}
		event.doit = false;
	}
	
	private List<Student> getStudents() {
		ITreeSelection selection = ViewManager.getInstance().getTreeViewer().getStructuredSelection();
		ArrayList<Student> students = new ArrayList<>();
		if (!selection.isEmpty()) {
			for (Object object : selection.toArray()) {
				if (object instanceof Student) {
					Student student = (Student) object;
					students.add(student);
				} else if (object instanceof Group) {
					List<Entry> entries = ((Group) object).getEntries();
					for(Entry entry : entries) {
						Student student = (Student) entry;
						students.add(student);
					}			
				} else {
					students.clear();
					break;
				}
			}
		}
		return students;
	}
}
