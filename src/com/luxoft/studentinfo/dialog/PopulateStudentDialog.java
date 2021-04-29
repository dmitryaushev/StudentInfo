package com.luxoft.studentinfo.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
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

import com.luxoft.studentinfo.model.Student;
import com.luxoft.studentinfo.util.ValidationService;
import com.luxoft.studentinfo.view.ViewManager;

public class PopulateStudentDialog extends Dialog {

	private Text nameText;
	private Text groupText;
	private Text adressText;
	private Text cityText;
	private Text resultText;
	private Text photoNameText;
	private Button button;

	private String name;
	private String group;
	private String adress;
	private String city;
	private String result;
	private String photoPath;

	private Student student;
	private String studentGroupName;

	public PopulateStudentDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout(2, false));

		Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText("Name");
		nameText = new Text(composite, SWT.BORDER);
		Label groupLabel = new Label(composite, SWT.NONE);
		groupLabel.setText("Group");
		groupText = new Text(composite, SWT.BORDER);
		Label adressLabel = new Label(composite, SWT.NONE);
		adressLabel.setText("Adress");
		adressText = new Text(composite, SWT.BORDER);
		Label cityLabel = new Label(composite, SWT.NONE);
		cityLabel.setText("City");
		cityText = new Text(composite, SWT.BORDER);
		Label resultLabel = new Label(composite, SWT.NONE);
		resultText = new Text(composite, SWT.BORDER);
		resultLabel.setText("Result");
		Label photoNameLabel = new Label(composite, SWT.NONE);
		photoNameLabel.setText("Photo name");
		photoNameText = new Text(composite, SWT.BORDER);
		photoNameText.setEditable(false);
		button = new Button(composite, SWT.PUSH);
		button.setText("Upload Photo");

		addListeners(parent);

		if (student != null) {
			nameText.setText(student.getName());
			groupText.setText(student.getGroup().getName());
			adressText.setText(student.getAdress());
			cityText.setText(student.getCity());
			resultText.setText(String.valueOf(student.getResult()));
			photoNameText.setText(student.getPhotoPath());
		}
		if (studentGroupName != null) {
			groupText.setText(studentGroupName);
			groupText.setEditable(false);
		}
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
		try {
			ValidationService.validateInput(name, group, adress, city, result);
			ValidationService.validatePhoto(photoPath);
			super.okPressed();
		} catch (Exception e) {
			MessageDialog.openError(ViewManager.getInstance().getTreeViewer().getControl().getShell(), "Error",
					e.getMessage());
		}
	}

	private void saveInput() {
		name = nameText.getText();
		group = groupText.getText();
		adress = adressText.getText();
		city = cityText.getText();
		result = resultText.getText();
		photoPath = photoNameText.getText();
	}

	private void addListeners(Composite parent) {
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				FileDialog dialog = new FileDialog(parent.getShell(), SWT.OPEN);
				dialog.setFilterExtensions(new String[] { "*.png", "*.jpg", "*.jpeg" });

				photoPath = dialog.open();
				if (photoPath != null) {
					photoNameText.setText(photoPath);
				}

			}
		});
		nameText.addListener(SWT.KeyUp, listener -> validateInput(nameText));
		cityText.addListener(SWT.KeyUp, listener -> validateInput(cityText));
		resultText.addListener(SWT.KeyUp, event -> {
			String result = resultText.getText();
			String[] chars = result.split("");
			for (String s : chars) {
				if (!s.matches("^[1-9]\\d*$")) {
					result = result.replace(s, "");
					resultText.setText(result);
					resultText.setSelection(result.length());
				}
			}
		});
	}

	private void validateInput(Text text) {
		String input = text.getText();
		String[] chars = input.split("");
		for (String s : chars) {
			if (!s.matches("^[\\p{L} ]+$")) {
				input = input.replace(s, "");
				text.setText(input);
				text.setSelection(input.length());
			}
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

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setStudentGroupName(String studentGroupName) {
		this.studentGroupName = studentGroupName;
	}
}
