package com.capstonewebui.client;

import com.google.gwt.maps.client.Maps;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;

public class LocationCreationForm extends AbsolutePanel{

	
	private Grid worldBuilderGrid;
	
	public LocationCreationForm() {
		this.setVisible(false);
		this.setSize("800px", "800px");
		
		worldBuilderGrid = new Grid(10,3);
		worldBuilderGrid.setStyleName("centered");

		addLabelToPanel();
		addSecondColumnContent();
		addThirdColumn();
		
		this.add(worldBuilderGrid);
		
	}
	
	private void addLabelToPanel()
	{
		worldBuilderGrid.setText(0, 0, "Location ID: ");
		worldBuilderGrid.setText(1, 0, "Name: ");
		worldBuilderGrid.setText(2, 0, "description: ");
		worldBuilderGrid.setText(3, 0, "Longitude: ");
		worldBuilderGrid.setText(4, 0, "Latitude: ");
		worldBuilderGrid.setText(5, 0, "Discovery Radius: ");
		worldBuilderGrid.setText(6, 0, "Visited: ");
		worldBuilderGrid.setText(7, 0, "Locked: ");
		worldBuilderGrid.setText(8, 0, "Location To Unlock: ");
		worldBuilderGrid.setText(9, 0, "Location To Retire: ");
	}
	
	
	//second column is a mix of textBoxes, checkboxes, and list
	private void addSecondColumnContent()
	{

		TextBox locationIDTB = new TextBox();
		
		worldBuilderGrid.setWidget(0, 1, locationIDTB);
		
		TextBox nameTB = new TextBox();
		worldBuilderGrid.setWidget(1, 1, nameTB);
		
		TextBox descriptionTB = new TextBox();
		worldBuilderGrid.setWidget(2, 1, descriptionTB);
		
		TextBox longitudeTB = new TextBox();
		worldBuilderGrid.setWidget(3, 1, longitudeTB);
		
		TextBox latitudeTB = new TextBox();
		worldBuilderGrid.setWidget(4, 1, latitudeTB);
		
		TextBox discoveryRadiusTB = new TextBox();
		worldBuilderGrid.setWidget(5, 1, discoveryRadiusTB);

		
		CheckBox visitedCB = new CheckBox();
		worldBuilderGrid.setWidget(6, 1, visitedCB);
		
		CheckBox lockedCB = new CheckBox();
		worldBuilderGrid.setWidget(7, 1, lockedCB);
		
		TextBox locationToUnlockTB = new TextBox();
		worldBuilderGrid.setWidget(8, 1, locationToUnlockTB);
		
		TextBox locationToRetireTB = new TextBox();
		worldBuilderGrid.setWidget(9, 1, locationToRetireTB);
	}
	
	//third column houses optional option for each field.
	private void addThirdColumn()
	{
		Button map1Button = new Button("->");
		worldBuilderGrid.setWidget(3, 2, map1Button);
		
		Button map2Button = new Button("->");
		worldBuilderGrid.setWidget(4, 2, map2Button);
	}
	
	public void submit()
	{
		
	}
	

}
