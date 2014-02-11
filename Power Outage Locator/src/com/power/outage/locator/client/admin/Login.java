package com.power.outage.locator.client.admin;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Login extends Composite {
	private TextBox textBoxUsername;
	private PasswordTextBox textBoxPassword;
	private Label lblErrorMsg;

	private Button btnSignIn;

	public Login() {

		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);

		Label lblLoginToYour = new Label("Sign in to your account");
		lblLoginToYour.setStyleName("gwt-Label-Login");
		verticalPanel.add(lblLoginToYour);

		FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);
		flexTable.setWidth("345px");

		Label lblUsername = new Label("Username:");
		lblUsername.setStyleName("gwt-Label-Login");
		flexTable.setWidget(0, 0, lblUsername);

		textBoxUsername = new TextBox();
		flexTable.setWidget(0, 1, textBoxUsername);

		Label lblPassword = new Label("Password:");
		lblPassword.setStyleName("gwt-Label-Login");
		flexTable.setWidget(1, 0, lblPassword);

		textBoxPassword = new PasswordTextBox();
		flexTable.setWidget(1, 1, textBoxPassword);

		lblErrorMsg = new Label("");
		lblErrorMsg.setStyleName("gwt-Login-ErrorMsg");
		flexTable.setWidget(2, 1, lblErrorMsg);
		btnSignIn = new Button("Sign In");
		flexTable.setWidget(3, 1, btnSignIn);
	}

	public Label getLblErrorMsg() {
		return lblErrorMsg;
	}

	public TextBox getTextBoxUsername() {
		return textBoxUsername;
	}

	public PasswordTextBox getTextBoxPassword() {
		return textBoxPassword;
	}

	public Button getBtnSignIn() {
		return btnSignIn;
	}
}
