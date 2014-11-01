package com.capstonewebui.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class LoginPanel extends AbsolutePanel {

	private TextBox userNameTextBox;
	
	public LoginPanel() {
		userNameTextBox = new TextBox();
		PasswordTextBox passwordTextBox = new PasswordTextBox();
		Grid grid = new Grid(3, 2);
		
		
			//adding widgets to login Panel
		grid.setWidget(0, 0, new Label("UserName"));
		grid.setWidget(0, 1, userNameTextBox);
		grid.setWidget(1, 1, passwordTextBox);
		grid.setWidget(1, 0, new Label("password"));
		this.setSize("800px", "800px");
		
		Button logInButton = new Button("Log In");
		Button forgotPasswordButton = new Button("Forgot Password");
		grid.setWidget(2, 0, forgotPasswordButton);
		grid.setWidget(2, 1, logInButton);
		grid.setStyleName("centered");
		grid.addStyleName("bordered");
		
		this.add(grid);
		
		MyHandler handler = new MyHandler();
		logInButton.addClickHandler(handler);
	}
	
	class MyHandler implements ClickHandler, KeyUpHandler {
		/**
		 * Fired when the user clicks on the sendButton.
		 */
		public void onClick(ClickEvent event) {
			sendNameToServer();
		}

		/**
		 * Fired when the user types in the nameField.
		 */
		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				//sendNameToServer();
			}
		}

		/**
		 * Send the name from the nameField to the server and wait for a response.
		 */
		
		
		private void sendNameToServer() {
			// First, we validate the input.
			CapstoneWebUI.logInPanel.setVisible(false);
			CapstoneWebUI.menuPanel.setVisible(true);
			
			/*CapstoneWebUI.databaseService.storeData("sup albert", new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								userNameTextBox.setText("error");
							}

							public void onSuccess(String result) {
							userNameTextBox.setText(result);
							}
						});
			
			/*
			/*
			errorLabel.setText("");
			String textToServer = nameField.getText();
			if (!FieldVerifier.isValidName(textToServer)) {
				errorLabel.setText("Please enter at least four characters");
				return;
				*/
			}

		}

}
