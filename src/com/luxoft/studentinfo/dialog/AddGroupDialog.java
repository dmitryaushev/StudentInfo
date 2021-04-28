package com.luxoft.studentinfo.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddGroupDialog extends Dialog {
	
	private Text nameText;
	private String name;

	public AddGroupDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout(2, false));
		
		Label nameLabel = new Label(composite, SWT.NONE);
		nameText = new Text(composite, SWT.BORDER);
		
		nameLabel.setText("Name");
		
		return composite;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Add new group");
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(300, 150);
	}
	
	@Override
	protected void okPressed() {
		name = nameText.getText();
		super.okPressed();
	}

	public String getName() {
		return name;
	}
}
