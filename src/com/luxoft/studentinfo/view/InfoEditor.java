package com.luxoft.studentinfo.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.model.Folder;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.ModelManager;
import com.luxoft.studentinfo.model.Student;
import com.luxoft.studentinfo.util.IImageKeys;
import com.luxoft.studentinfo.util.ValidationService;

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
	private boolean isSave = true;

	public InfoEditor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		String newName = nameText.getText();
		String newGroup = groupText.getText();
		String newAdress = adressText.getText();
		String newCity = cityText.getText();
		String newResult = resultText.getText();
		try {
			ValidationService.validateInput(newName, newGroup, newAdress, newCity, newResult);
			setPartName(newName);
			populateStudent(newName, newGroup, newAdress, newCity, newResult);
			ViewManager.getInstance().getTreeViewer().refresh();
			isDirty = false;
			firePropertyChange(PROP_DIRTY);
		} catch (IllegalArgumentException e) {
			MessageDialog.openError(parent.getShell(), "Empty field", e.getMessage());
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
		redoStack = new LinkedList<>();
		createInputComposite();
		createPhotoComposite();
		addListeners(nameText);
		addListeners(groupText);
		addListeners(adressText);
		addListeners(cityText);
		addListeners(resultText);

		String defaultState = String.format("%s/%s/%s/%s/%s", student.getName(), student.getGroup().getName(),
				student.getAdress(), student.getCity(), student.getResult());
		undoStack.add(0, defaultState);
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
		groupLabel.setText("Group");
		groupLabel.setLayoutData(labelData);
		groupText.setText(student.getGroup().getName());
		groupText.setLayoutData(textData);
		adressLabel.setText("Adress");
		adressLabel.setLayoutData(labelData);
		adressText.setText(student.getAdress());
		adressText.setLayoutData(textData);
		cityLabel.setText("City");
		cityLabel.setLayoutData(labelData);
		cityText.setText(student.getCity());
		cityText.setLayoutData(textData);
		resultLabel.setText("Result");
		resultLabel.setLayoutData(labelData);
		resultText.setText(String.valueOf(student.getResult()));
		resultText.setLayoutData(textData);
	}

	private void createPhotoComposite() {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());

		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(student.getPhotoPath());
		} catch (FileNotFoundException e) {
		}	
		Image image;
		if (fileInputStream != null) {
			image = new Image(parent.getDisplay(), fileInputStream);
		} else {
			image = new Image(parent.getDisplay(), IImageKeys.DEFAULT_PHOTO);
		}
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

	private void addListeners(Text text) {
		text.addModifyListener(listener -> {
			if (!student.getName().equals(nameText.getText())
					|| !student.getGroup().getName().equals(groupText.getText())
					|| !student.getAdress().equals(adressText.getText())
					|| !student.getCity().equals(cityText.getText())
					|| student.getResult() != Integer.valueOf(resultText.getText())) {
				isDirty = true;
			} else {
				isDirty = false;
			}
			if (isSave) {
				String state = String.format("%s/%s/%s/%s/%s", nameText.getText(), groupText.getText(),
						adressText.getText(), cityText.getText(), resultText.getText());
				undoStack.add(0, state);
			}			
			firePropertyChange(PROP_DIRTY);
		});
		text.addKeyListener(new KeyAdapter() {
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
			String[] state = undoStack.remove(0).split("/");
			isSave = false;
			nameText.setText(state[0]);
			groupText.setText(state[1]);
			adressText.setText(state[2]);
			cityText.setText(state[3]);
			isSave = true;
			resultText.setText(state[4]);
		}
	}

	private void redo() {
		if (redoStack.size() > 0) {
			String[] state = redoStack.remove(0).split("/");
			isSave = false;
			nameText.setText(state[0]);
			groupText.setText(state[1]);
			adressText.setText(state[2]);
			cityText.setText(state[3]);
			isSave = true;
			resultText.setText(state[4]);
		}
	}
	
	private void populateStudent(String newName, String newGroup, String newAdress, String newCity, String newResult) {
		student.setName(newName);
		student.setAdress(newAdress);
		student.setCity(newCity);
		student.setResult(Integer.valueOf(newResult));
		if (!student.getGroup().getName().equals(newGroup)) {
			student.getParent().removeEntry(student);
			Folder folder = ModelManager.getInstance().getStateModel().getFolder();
			Entry[] entries = folder.getEntries();
			for(int i = 0; i < entries.length; i++) {
				if (entries[i].getName().equals(newGroup)) {
					Group group = (Group) entries[i];
					student.setGroup(group);
					group.addEntry(student);
					break;
				}
				if (i == entries.length - 1) {
					Group group = new Group(folder, newGroup);
					folder.addEntry(group);
					student.setGroup(group);
					group.addEntry(student);
				}
			}
		}
	}
}
