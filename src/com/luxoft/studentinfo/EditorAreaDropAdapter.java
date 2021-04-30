package com.luxoft.studentinfo;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorInputTransfer;

public class EditorAreaDropAdapter extends DropTargetAdapter {

	private final IWorkbenchWindow window;
	private static EditorInputTransfer.EditorInputData [] inputs;

	public EditorAreaDropAdapter(IWorkbenchWindow window) {
		this.window = window;
	}
	
	@Override
	public void dragEnter(DropTargetEvent event) {
		event.detail = DND.DROP_COPY;
	}
	
	@Override
	public void dragOperationChanged(DropTargetEvent event) {
		event.detail = DND.DROP_COPY;
	}
	
	@Override
	public void drop(DropTargetEvent event) {
		Display display = window.getShell().getDisplay();
		IWorkbenchPage page = window.getActivePage();
		if (page != null) {
			display.asyncExec(() -> handleDrop(page, event));
		}
	}

	public void handleDrop(IWorkbenchPage page, DropTargetEvent event) {
		if (EditorInputTransfer.getInstance().isSupportedType(event.currentDataType)) {
			for (int i = 0; i < inputs.length; i++) {
				IEditorInput editorInput = inputs[i].input;
				String editorId = inputs[i].editorId;
				openEditor(page, editorInput, editorId);
			}
		}
	}

	private IEditorPart openEditor(IWorkbenchPage page, IEditorInput editorInput, String editorId) {
		IEditorPart result;
		try {
			IEditorRegistry editorRegistry = PlatformUI.getWorkbench().getEditorRegistry();
			IEditorDescriptor editorDescriptor = editorRegistry.findEditor(editorId);
			if (editorDescriptor != null && !editorDescriptor.isOpenExternal()) {
				result = page.openEditor(editorInput, editorId);
			} else {
				result = null;
			}
		} catch (PartInitException e) {
			result = null;
		}
		return result;
	}
	
	public static void setInputs(EditorInputTransfer.EditorInputData [] inputDatas) {
		inputs = inputDatas;
	}
}
