package com.luxoft.studentinfo;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
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

	public InfoEditor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		createInputComposite(parent);
		createPhotoComposite(parent);
	}

	@Override
	public void setFocus() {
	}
	
	private void createInputComposite(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		Label nameLabel = new Label(composite, SWT.NONE);
		Text nameText = new Text(composite, SWT.BORDER);
		Label groupLabel = new Label(composite, SWT.NONE);
		Text groupText = new Text(composite, SWT.BORDER);
		Label adressLabel = new Label(composite, SWT.NONE);
		Text adressText = new Text(composite, SWT.BORDER);
		Label cityLabel = new Label(composite, SWT.NONE);
		Text cityText = new Text(composite, SWT.BORDER);
		Label resultLabel = new Label(composite, SWT.NONE);
		Text resultText = new Text(composite, SWT.BORDER);

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
	
	private void createPhotoComposite(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());

		Image image = new Image(parent.getDisplay(),
				student.getPhotoPath());	
		Canvas canvas = new Canvas(composite, SWT.NONE);
		canvas.addPaintListener(listener -> {
			if (image != null) {
				listener.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 3, 10, 200, 200);
			}
		});
	}
}
