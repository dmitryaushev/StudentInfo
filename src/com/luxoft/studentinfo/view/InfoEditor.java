package com.luxoft.studentinfo.view;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.luxoft.studentinfo.model.Student;

public class InfoEditor extends EditorPart {

	public static final String ID = "com.luxoft.studentInfo.InfoEditor";
	private Student student;
	private boolean isDirty;

	private Composite parent;
	private Text nameText;
	private Text groupText;
	private Text adressText;
	private Text cityText;
	private Text resultText;
	private Canvas canvas;

	private List<String> undoStack;
	private List<String> redoStack;

	public InfoEditor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		String newName = nameText.getText();
		if (!newName.trim().isEmpty()) {
			student.setName(newName);
			setPartName(newName);
			ViewManager.getInstance().getTreeViewer().refresh();
			isDirty = false;
			firePropertyChange(PROP_DIRTY);
		} else {
			MessageDialog.openError(parent.getShell(), "Empty name", "Name should not be empty");
			monitor.setCanceled(true);
		}
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		setPartName(input.getName());
		student = ((InfoEditorInput) input).getStudent();
	}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		undoStack = new LinkedList<>();
		undoStack.add(0, student.getName());
		redoStack = new LinkedList<>();
		createInputComposite();
		createPhotoComposite();
		addListeners();
	}

	@Override
	public void setFocus() {
		parent.setFocus();
	}

	private void createInputComposite() {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		Label nameLabel = new Label(composite, SWT.NONE);
		nameText = new Text(composite, SWT.BORDER);
		Label groupLabel = new Label(composite, SWT.NONE);
		groupText = new Text(composite, SWT.BORDER);
		Label adressLabel = new Label(composite, SWT.NONE);
		adressText = new Text(composite, SWT.BORDER);
		Label cityLabel = new Label(composite, SWT.NONE);
		cityText = new Text(composite, SWT.BORDER);
		Label resultLabel = new Label(composite, SWT.NONE);
		resultText = new Text(composite, SWT.BORDER);

		GridData labelData = new GridData();
		GridData textData = new GridData(GridData.FILL, GridData.CENTER, true, false);

		labelData.verticalIndent = 10;
		labelData.horizontalIndent = 15;
		labelData.widthHint = 50;
		textData.verticalIndent = 10;
		textData.horizontalIndent = 5;
		textData.widthHint = 110;

		nameLabel.setText("Name");
		nameLabel.setLayoutData(labelData);
		nameText.setText(student.getName());
		nameText.setLayoutData(textData);
		nameText.setSelection(nameText.getText().length());
		groupLabel.setText("Group");
		groupLabel.setLayoutData(labelData);
		groupText.setText(student.getGroup().getName());
		groupText.setLayoutData(textData);
		groupText.setEditable(false);
		adressLabel.setText("Adress");
		adressLabel.setLayoutData(labelData);
		adressText.setText(student.getAdress());
		adressText.setLayoutData(textData);
		adressText.setEditable(false);
		cityLabel.setText("City");
		cityLabel.setLayoutData(labelData);
		cityText.setText(student.getCity());
		cityText.setLayoutData(textData);
		cityText.setEditable(false);
		resultLabel.setText("Result");
		resultLabel.setLayoutData(labelData);
		resultText.setText(String.valueOf(student.getResult()));
		resultText.setLayoutData(textData);
		resultText.setEditable(false);
	}

	private void createPhotoComposite() {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());

		Image image = new Image(parent.getDisplay(), student.getPhotoPath());
		canvas = new Canvas(composite, SWT.NONE);
		canvas.addPaintListener(listener -> {
			if (image != null) {
				listener.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 3, 10, 200, 200);
			}
		});
	}

	public void update() {
		setPartName(student.getName());

		nameText.setText(student.getName());
		groupText.setText(student.getGroup().getName());
		adressText.setText(student.getAdress());
		cityText.setText(student.getCity());
		resultText.setText(String.valueOf(student.getResult()));

		Image image = new Image(parent.getDisplay(), student.getPhotoPath());
		canvas.addPaintListener(listener -> {
			if (image != null) {
				listener.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 3, 10, 200, 200);
			}
		});
		canvas.redraw();

	}

	private void addListeners() {
		nameText.addModifyListener(listener -> {
			if (!student.getName().equals(nameText.getText())) {
				isDirty = true;
			} else {
				isDirty = false;
			}
			undoStack.add(0, nameText.getText());
			firePropertyChange(PROP_DIRTY);
		});
		nameText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				boolean isCtrlPressed = (e.stateMask & SWT.CTRL) > 0;
				if (isCtrlPressed) {
					boolean isShiftPressed = (e.stateMask & SWT.SHIFT) > 0;
					if (!isShiftPressed && e.keyCode == 'z') {
						undo();
					} else if (isShiftPressed && e.keyCode == 'z') {
						redo();
					}
				}
			}
		});
	}

	private void undo() {
		if (undoStack.size() > 1) {
			redoStack.add(0, undoStack.remove(0));
			nameText.setText(undoStack.remove(0));
		}
		nameText.setSelection(nameText.getText().length());
	}

	private void redo() {
		if (redoStack.size() > 0) {
			nameText.setText(redoStack.remove(0));
		}
		nameText.setSelection(nameText.getText().length());
	}
}
