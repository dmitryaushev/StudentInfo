package com.luxoft.studentinfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddStudentDialog extends Dialog {

	private Text nameText;
	private Text groupText;
	private Text adressText;
	private Text cityText;
	private Text resultText;

	private String name;
	private String group;
	private String adress;
	private String city;
	private String result;
	private String photoPath;

	private String uploadPhotoPath;
	private String uploadPhotoName;
	private String photoName;

	public AddStudentDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
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

		Label photoNameLabel = new Label(composite, SWT.NONE);
		Text photoNameText = new Text(composite, SWT.BORDER);
		photoNameText.setEditable(false);

		Button button = new Button(composite, SWT.PUSH);
		button.setText("Upload Photo");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				FileDialog dialog = new FileDialog(parent.getShell(), SWT.OPEN);
				dialog.setFilterExtensions(new String[] { "*.png", "*.jpg", "*.jpeg" });

				uploadPhotoPath = dialog.open();
				uploadPhotoName = dialog.getFileName();

				photoNameText.setText(uploadPhotoName);

			}
		});

		nameLabel.setText("Name");
		groupLabel.setText("Group");
		adressLabel.setText("Adress");
		cityLabel.setText("City");
		resultLabel.setText("Result");
		photoNameLabel.setText("Photo name");

		return composite;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Add new student");
	}

	@Override
	protected Point getInitialSize() {
		return new Point(300, 350);
	}

	@Override
	protected void okPressed() {
		saveInput();
		copyPhoto();
		super.okPressed();
	}

	private void saveInput() {
		name = nameText.getText();
		group = groupText.getText();
		adress = adressText.getText();
		city = cityText.getText();
		result = resultText.getText();
	}

	private void copyPhoto() {

		String[] arr = uploadPhotoName.split("\\.");
		String extension = arr[arr.length - 1];

		photoName = String.format("%s-%s-%s-%s.%s", name, group, adress, city, extension);
		photoPath = String.format("%s%sphotos%s%s", System.getProperty("user.dir"), File.separator, File.separator,
				photoName);

		Path src = Paths.get(uploadPhotoPath);
		Path dest = Paths.get(photoPath);

		try {
			Files.copy(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public String getGroup() {
		return group;
	}

	public String getAdress() {
		return adress;
	}

	public String getCity() {
		return city;
	}

	public String getResult() {
		return result;
	}

	public String getPhotoPath() {
		return photoPath;
	}

}
