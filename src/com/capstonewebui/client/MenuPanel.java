package com.capstonewebui.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;

public class MenuPanel extends AbsolutePanel{

	public MenuPanel() {
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
		
		MyHandler handler = new MyHandler();
		buildWorldButton.addClickHandler(handler);
		this.add(menuGrid);
		this.setVisible(false);
	}
	
	
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

		
		
		private void sendNameToServer() {
			CapstoneWebUI.menuPanel.setVisible(false);
			CapstoneWebUI.worldBuilderPanel.setVisible(true);
			
			}

		}

}
