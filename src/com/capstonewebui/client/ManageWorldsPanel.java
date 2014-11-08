package com.capstonewebui.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;

public class ManageWorldsPanel extends AbsolutePanel{

	private Grid mGrid;
	private ListBox worldsList;
	public ManageWorldsPanel() {
		mGrid = new Grid(3,3);
		mGrid.setStyleName("centered");
		this.setSize("800px", "800px");
		this.setVisible(false);
		//addFirstRowContent();
		addSecondRowContent();
		
		this.add(mGrid);
		
	}
	
	public void addWorldsToList(String worldString)
	{
		
		String[] world = worldString.split(",");
		worldsList = new ListBox();
		
		for(int i = 0; i < world.length; i++)
		{
			worldsList.addItem(world[i]);
		}
		worldsList.setVisibleItemCount(10);
		
		mGrid.setWidget(0, 0, worldsList);
	}
	
	private void addSecondRowContent()
	{
	
		Button editWorld = new Button("Edit");
		Button deleteWorld = new Button("Delete");
		editWorld.setStyleName("floatTop");
		deleteWorld.setStyleName("floatTop");
		mGrid.setWidget(0, 1, editWorld);
		mGrid.setWidget(0, 2, deleteWorld);
		
	deleteWorld.addClickHandler(new DeleteListHandler());
	editWorld.addClickHandler(new EditListHandler());
	}
	
	private void getWorlds(String[] worlds)
	{
		for(int i = 0; i < worlds.length; i++)
		{
			worldsList.addItem(worlds[i]);
		}
	}
	
	
	class DeleteListHandler implements ClickHandler, KeyUpHandler {

		public void onClick(ClickEvent event) {

			deleteWorld();
		}
		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				
			}
		}
		private void deleteWorld() {
			
				mGrid.setText(1, 1, "You have deleted " + Integer.toString( + worldsList.getSelectedIndex()));
			}
		}
	
	class EditListHandler implements ClickHandler, KeyUpHandler {

		public void onClick(ClickEvent event) {

			deleteWorld();
		}
		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				
			}
		}
		private void deleteWorld() {
				mGrid.setText(1, 1, "You have edited " + Integer.toString( + worldsList.getSelectedIndex()));
			}

		}
	



}
