package com.capstonewebui.client;

import com.capstonewebui.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CapstoneWebUI implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		//Login Panel
			//widget declarations
		final AbsolutePanel logInPanel = new AbsolutePanel();
		TextBox userNameTextBox = new TextBox();
		PasswordTextBox passwordTextBox = new PasswordTextBox();
		Grid grid = new Grid(3, 2);
		
		
			//adding widgets to login Panel
		grid.setWidget(0, 0, new Label("UserName"));
		grid.setWidget(0, 1, userNameTextBox);
		grid.setWidget(1, 1, passwordTextBox);
		grid.setWidget(1, 0, new Label("password"));
		logInPanel.setSize("800px", "800px");
		
		Button logInButton = new Button("Log In");
		Button forgotPasswordButton = new Button("Forgot Password");
		grid.setWidget(2, 0, forgotPasswordButton);
		grid.setWidget(2, 1, logInButton);
		grid.setStyleName("centered");
		grid.addStyleName("bordered");
		
		logInPanel.add(grid);
		
		//Menu Option
		final AbsolutePanel menuPanel = new AbsolutePanel();
		menuPanel.setVisible(true);
		
		Grid menuGrid = new Grid(5, 1);
		menuGrid.setStyleName("centered");
		Button buildWorldButton = new Button("Build World");
		
		Button manageWorldsButton = new Button("Manage Worlds");
		manageWorldsButton.setStyleName("alignedCenter");
		Button interactButton = new Button("Interact With Players");
		interactButton.setStyleName("alignedCenter");
		Button tutorialButton = new Button("Tutorial");
		tutorialButton.setStyleName("alignedCenter");
		menuGrid.getCellFormatter();
		
		menuGrid.setWidget(0,0, buildWorldButton);
		menuGrid.setWidget(1,0, manageWorldsButton);
		menuGrid.setWidget(2, 0, interactButton);
		menuGrid.setWidget(3,0, tutorialButton);
		
		
		menuPanel.add(menuGrid);
		menuPanel.setVisible(false);
		
		//attempt at centering cell contents
		//HTMLTable.CellFormatter formatter = menuGrid.getCellFormatter();
		 //formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		 //formatter.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		 //formatter.setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		 //formatter.setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		
		//worldBuilderPanel Creation
		
		
		final MapUI worldBuilderPanel = new MapUI();
		
		
		
		RootPanel.get("contentPanel").add(logInPanel);
		RootPanel.get("contentPanel").add(menuPanel);
		RootPanel.get("contentPanel").add(worldBuilderPanel);
		
		//test button

		/*
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();
		*/

		// Create the popup dialog box
		/*
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		
		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				
				//setting div to be visible;
				//DOM.getElementById("logInPanel").getStyle().setVisibility(Visibility.HIDDEN);
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});
		
		*/

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				//sendNameToServer();
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
				logInPanel.setVisible(false);
				menuPanel.setVisible(true);
				/*
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
					*/
				}

				/*
				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
						
						
						
			}
			*/
		}

	
		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		//sendButton.addClickHandler(handler);
		logInButton.addClickHandler(handler);
		buildWorldButton.addClickHandler(new ClickHandler()
		{
			
			@Override
			public void onClick(ClickEvent event) {
				menuPanel.setVisible(false);
				worldBuilderPanel.setVisible(true);
				
			}
		});
		//nameField.addKeyUpHandler(handler);
		
	
	}
	

	
}
