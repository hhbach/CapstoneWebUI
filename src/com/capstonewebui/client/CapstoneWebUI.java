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
	//private final GreetingServiceAsync greetingService = GWT
			//.create(GreetingService.class);

	static final DatabaseHandlerAsync databaseService = GWT.create(DatabaseHandler.class);
	/**
	 * This is the entry point method.
	 */
	
	static MenuPanel menuPanel;
	static LoginPanel logInPanel;
	static LocationCreationForm locationCreationPanel;
	static ManageWorldsPanel worldManagerPanel;
	static WorldCreationForm worldCreationForm;
	public void onModuleLoad() {
		
		//Login Panel
		logInPanel = new LoginPanel();
		
		//Menu Option
		menuPanel = new MenuPanel();
	
		
		locationCreationPanel = new LocationCreationForm();
		worldManagerPanel = new ManageWorldsPanel();
		worldCreationForm = new WorldCreationForm();
		
		RootPanel.get("contentPanel").add(worldCreationForm);
		
		RootPanel.get("contentPanel").add(logInPanel);
		RootPanel.get("contentPanel").add(menuPanel);
		//logInPanel.setVisible(false);
		RootPanel.get("contentPanel").add(locationCreationPanel);
		
		//locationCreationPanel.setVisible(true);
		//worldBuilderPanel.setVisible(true);
		//RootPanel.get("contentPanel").add(worldManagerPanel);
	
	}
	

	
}
