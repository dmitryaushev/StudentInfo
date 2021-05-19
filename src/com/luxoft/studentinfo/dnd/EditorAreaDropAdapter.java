package com.luxoft.studentinfo.dnd;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorInputTransfer;

import com.luxoft.studentinfo.view.InfoEditor;

public class EditorAreaDropAdapter extends DropTargetAdapter {

	@Override
	public void dragEnter(DropTargetEvent event) {
		event.detail = DND.DROP_COPY;
	}

	@Override
	public void drop(DropTargetEvent event) {
		if (event.data != null) {
			EditorInputTransfer.EditorInputData[] editorInputs = (EditorInputTransfer.EditorInputData[]) event.data;
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			for (int i = 0; i < editorInputs.length; i++) {
				IEditorInput editorInput = editorInputs[i].input;
				//String editorId = editorInputs[i].editorId;
				try {
					page.openEditor(editorInput, InfoEditor.ID);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
