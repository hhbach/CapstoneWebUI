package com.capstonewebui.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.maps.client.overlay.Marker;

public class WorldCreationForm extends AbsolutePanel {
	
	private HorizontalPanel mainPanel = new HorizontalPanel();
	private HorizontalPanel namePanel = new HorizontalPanel();
	private HorizontalPanel descriptionPanel = new HorizontalPanel();
	private VerticalPanel leftFormPanel = new VerticalPanel();
	private VerticalPanel rightMapPanel = new VerticalPanel();
	private HorizontalPanel discardSavePanel = new HorizontalPanel();
	private FlexTable locationsFlexTable = new FlexTable();
	private HorizontalPanel mapPanel = new HorizontalPanel();
	private HorizontalPanel addLocationPanel = new HorizontalPanel();

	private TextBox nameTextBox = new TextBox();
	private TextArea descriptionTextBox = new TextArea();
	private Button addLocationButton = new Button("Add a Location");
	private Button discardButton = new Button("Discard");
	private Button saveButton = new Button("Save");
	private Button editDeleteButton;
	private Label nameLabel = new Label("Name:");
	private Label descriptionLabel = new Label("Description:");
	private ArrayList<String> locations = new ArrayList<String>();
	private MapWidget map;
	
	public WorldCreationForm() {
		this.setVisible(false);
		//MyHandler handler = new MyHandler();
		//addLocationButton.addClickHandler(handler);
		this.add(assemblePanels());
		
		this.addStyleName("worldCreationPanel");
		mainPanel.addStyleName("mainPanel");
		leftFormPanel.addStyleName("leftFormPanel");
		rightMapPanel.addStyleName("rightFormPanel");
		namePanel.addStyleName("namePanel");
		descriptionPanel.addStyleName("descriptionPanel");
		descriptionTextBox.addStyleName("descriptionTextArea");
		nameTextBox.addStyleName("nameTextBox");
		locationsFlexTable.addStyleName("locationsFlexTable");
		addLocationButton.addStyleName("addLocationButton");
		addLocationPanel.addStyleName("addLocationPanel");
		discardButton.addStyleName("discardButton");
		discardSavePanel.addStyleName("discardSavePanel");
		saveButton.addStyleName("saveButton");
		locationsFlexTable.getColumnFormatter().setWidth(0, "26px");
		locationsFlexTable.getColumnFormatter().setWidth(1, "125px");
		locationsFlexTable.getColumnFormatter().setWidth(2, "81px");
		locationsFlexTable.getRowFormatter().addStyleName(0, "locationListHeader");

		addLocationButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				CapstoneWebUI.worldCreationForm.setVisible(false);
				CapstoneWebUI.locationCreationPanel.setVisible(true);
				map.removeFromParent();
				map = null;
			}
		});
		
		

	}
	
	private HorizontalPanel assemblePanels(){
		assembleFlexTable();
		
		namePanel.add(nameLabel);
		namePanel.add(nameTextBox);
		
		discardSavePanel.add(discardButton);
		discardSavePanel.add(saveButton);
		
		descriptionPanel.add(descriptionLabel);
		descriptionPanel.add(descriptionTextBox);
		
		addLocationPanel.add(addLocationButton);
		
		leftFormPanel.add(namePanel);
		leftFormPanel.add(descriptionPanel);
		leftFormPanel.add(addLocationPanel);
		leftFormPanel.add(locationsFlexTable);
		mainPanel.add(leftFormPanel);
		
		//rightMapPanel.add(Google map)
		
		Maps.loadMapsApi("", "2", false, new Runnable() {
		      public void run() {
		        mapPanel.add(buildMap());
		        CapstoneWebUI.locationCreationPanel.buildMapUI();
		        //buildMap().checkResizeAndCenter();
		      }
		});
		
		
		rightMapPanel.add(mapPanel);
		rightMapPanel.add(discardSavePanel);
		mainPanel.add(rightMapPanel);
		
		return mainPanel;
	}
	
	private void assembleFlexTable(){
		locationsFlexTable.setText(0, 0, "#");
		locationsFlexTable.setText(0, 1, "Location");
		locationsFlexTable.setText(0, 2, "Add/Edit");
	}
	
	
	private MapWidget buildMap() {
	    // Open a map centered on Cawker City, KS USA
	    LatLng brickyardEng = LatLng.newInstance(33.4236007, -111.9395375);

	    map = new MapWidget(brickyardEng, 2);
	    map.setSize("380px", "380px");
	    // Add some controls for the zoom level
	    map.addControl(new LargeMapControl());

	    // Add a marker
	    map.addOverlay(new Marker(brickyardEng));


	    return map;
	  }
	

}
