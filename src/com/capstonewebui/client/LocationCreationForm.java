package com.capstonewebui.client;

import java.util.ArrayList;

import com.capstonewebui.shared.LocationObject;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MapClickHandler.MapClickEvent;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;

public class LocationCreationForm extends AbsolutePanel{

	
	private MapWidget map;
	private TextBox longitudeTB;
	private TextBox latitudeTB;
	private TextBox descriptionTB;
	private TextBox nameTB;
	private FlexTable worldBuilderGrid;
	private CheckBox lockedCB;
	private PopupPanel mapPanel;
	private ListBox locationsToRetire;
	private FlexTable locationsToUnlock;
	
	public LocationCreationForm() {
		this.setVisible(false);
		this.setSize("800px", "800px");
		
		worldBuilderGrid = new FlexTable();
		worldBuilderGrid.setStyleName("centered");

		addLabelToPanel();
		addSecondColumnContent();
		addThirdColumn();
		
		
		this.add(worldBuilderGrid);
		addAvailableLocationsHeader();
		setUpMapPanel();
		addNavigationButtons();
		
	}
	
	public void buildMapUI()
	{
	    
	    Button closeButton = new Button("Close");
	    closeButton.addClickHandler(new CloseButtonHandler());
	    
	    final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
	    HorizontalPanel mapControlPanel = new HorizontalPanel();
	    
	    mapControlPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
	    LatLng cawkerCity = LatLng.newInstance(39.509, -98.434);
	    map = new MapWidget(cawkerCity, 2);
	    map.setSize("600px", "600px");
	    // Add some controls for the zoom level
	    map.addControl(new LargeMapControl());

	    mapControlPanel.add(closeButton);
	    dock.addNorth(map, 600);
	    dock.addEast(mapControlPanel, 200);
	  
	    
	    // Add the map to the HTML host page
	    mapPanel.add(dock);
	    
	    map.addMapClickHandler(new selectMapLocation());
	    
	}
	
	private void setUpMapPanel()
	{
		mapPanel = new PopupPanel();
		mapPanel.setSize("800px", "800px");
	}
	
	private void addLabelToPanel()
	{
		worldBuilderGrid.setText(0, 0, "Location ID: ");
		worldBuilderGrid.setText(1, 0, "Name: ");
		worldBuilderGrid.setText(2, 0, "description: ");
		worldBuilderGrid.setText(3, 0, "Longitude: ");
		worldBuilderGrid.setText(4, 0, "Latitude: ");
		worldBuilderGrid.setText(5, 0, "Discovery Radius: ");
		worldBuilderGrid.setText(6, 0, "Locked: ");
		worldBuilderGrid.setText(7, 0, "");
		worldBuilderGrid.setText(8, 0, "Dependencies: ");
	}
	
	
	//second column is a mix of textBoxes, checkboxes, and list
	private void addSecondColumnContent()
	{

		TextBox locationIDTB = new TextBox();
		locationIDTB.setEnabled(false);
		
		worldBuilderGrid.setWidget(0, 1, locationIDTB);
		
		nameTB = new TextBox();
		worldBuilderGrid.setWidget(1, 1, nameTB);
		
		descriptionTB = new TextBox();
		worldBuilderGrid.setWidget(2, 1, descriptionTB);
		
		
		longitudeTB = new TextBox();
		
		FlowPanel longitudeContainer = new FlowPanel();
		Button map2Button = new Button("->");
		longitudeContainer.add(longitudeTB);
		longitudeContainer.add(map2Button);
		worldBuilderGrid.setWidget(3, 1, longitudeContainer);
		map2Button.addClickHandler(new MapLauncherHandler());
		
		
		latitudeTB = new TextBox();
		worldBuilderGrid.setWidget(4, 1, latitudeTB);
		
		TextBox discoveryRadiusTB = new TextBox();
		discoveryRadiusTB.setText("meters");
		worldBuilderGrid.setWidget(5, 1, discoveryRadiusTB);

		
		
		lockedCB = new CheckBox();
		worldBuilderGrid.setWidget(6, 1, lockedCB);
		
	}
	
	//this consists "id", "longitude/latitude", and "edit/add" buttons
	private void addAvailableLocationsHeader()
	{
		locationsToUnlock = new FlexTable();
		worldBuilderGrid.getFlexCellFormatter().setColSpan(9, 0, 2);
		worldBuilderGrid.setWidget(9,0, locationsToUnlock);
		
		locationsToUnlock.getColumnFormatter().setWidth(0, "26px");
		locationsToUnlock.getColumnFormatter().setWidth(1, "125px");
		locationsToUnlock.getColumnFormatter().setWidth(2, "81px");
		locationsToUnlock.getRowFormatter().addStyleName(0, "locationListHeader");
		locationsToUnlock.setText(0, 0, "Locations");
		locationsToUnlock.setText(0, 1, "Unlock");
		locationsToUnlock.setText(0, 2, "Retire");
	}
	
	//third column houses optional option for each field.
	private void addThirdColumn()
	{	
		Button map2Button = new Button("->");
	}
	
	private void clearPanel()
	{
		nameTB.setText("");
		descriptionTB.setText("");
		longitudeTB.setText("");
		latitudeTB.setText("");
	}
	
	private void addNavigationButtons()
	{
		Button acceptButton = new Button("Accept");
		Button discardButton = new Button("Discard");
		
		
		FlowPanel navigationButtonsContainer= new FlowPanel();
		navigationButtonsContainer.add(acceptButton);
		navigationButtonsContainer.add(discardButton);
		
		//adds listeners
		discardButton.addClickHandler(new CancelLocationHandler());
		acceptButton.addClickHandler(new SaveLocationHandler());
		
		navigationButtonsContainer.setStyleName("locationCreationButtonsContainer");
		this.add(navigationButtonsContainer);
	}
	
	public void update(LocationObject location, ArrayList<LocationObject> allLocations)
	{
		clearFields();
		longitudeTB.setText(location.getLongitude());
		latitudeTB.setText(location.getLatitude());
		descriptionTB.setText(location.getLocationDescription());
		nameTB.setText(location.getLocationName());
		
		updateAvailableLocations(allLocations);
	}
	
	class selectMapLocation implements MapClickHandler
	{

		@Override
		public void onClick(MapClickEvent event) {
			map.clearOverlays();
			LatLng selectedPoint = event.getLatLng();
			map.addOverlay(new Marker(selectedPoint));
			

	    	map.getInfoWindow().open(selectedPoint,
	    			new InfoWindowContent(nameTB.getText()));
			
			//radius circle
			
			String selectedPointString = selectedPoint.toString();
			selectedPointString = selectedPointString.replace(")", "");
			selectedPointString = selectedPointString.replace("(", "");
			String selectedPointStringArray[] = selectedPointString.split(",");
			
			longitudeTB.setText(selectedPointStringArray[1]);
			latitudeTB.setText(selectedPointStringArray[0]);
			
		}
		
	}
	
	private void buildDepedency(LocationObject location)
	{
		String locationsToRetireString = "{";
		String locationsToUnlockString = "{";
		String locationName = "";
		byte rowCount = (byte) locationsToUnlock.getRowCount();
		
		if(rowCount == 1)
		{
			locationsToUnlockString = "{}";
			locationsToRetireString = "{}";
		}
		
		for(int i = 1; i < rowCount; i++)
		{
			
			
			System.out.println("collumn " + i + "has: " + locationsToUnlock.getCellCount(i));
			
			CheckBox cb = (CheckBox) locationsToUnlock.getWidget(i, 1);
			if(cb.getValue() == true)
			{
				if(locationsToUnlockString.compareTo("{") == 0)
					locationsToUnlockString = locationsToUnlockString + locationsToUnlock.getText(i, 0);
				else
					locationsToUnlockString = locationsToUnlockString + "," + locationsToUnlock.getText(i, 0);

			}
			
			
			cb = (CheckBox) locationsToUnlock.getWidget(i, 2);
			if(cb.getValue() == true)
			{
				
				if(locationsToRetireString.compareTo("{") == 0)
					locationsToRetireString = locationsToRetireString + locationsToUnlock.getText(i, 0);
				else
					locationsToRetireString = locationsToRetireString + "," + locationsToUnlock.getText(i, 0);
			}
			
			if(i == rowCount - 1)
			{
				locationsToUnlockString = locationsToUnlockString + "}";
				locationsToRetireString = locationsToRetireString + "}";
			}
		}
		
		location.setLocationToUnlock(locationsToUnlockString);
		location.setLocationToRetire(locationsToRetireString);
	}
	
	
	
	private void clearFields()
	{
		nameTB.setText("");
		descriptionTB.setText("");
		descriptionTB.setText("");
		nameTB.setText("");
		lockedCB.setValue(false);
		
		for(int i = 1; i < locationsToUnlock.getRowCount(); i++)
		{
			locationsToUnlock.removeAllRows();
			addAvailableLocationsHeader();
		}
	}
	
	private void updateAvailableLocations(ArrayList<LocationObject> locations)
	{
		int locationSize = locations.size();
		byte vacantRow = 1;
		for(int i = 1; i <= locationSize; i++ )
		{
			
			if(locations.get(i-1).getLocationName().compareTo(nameTB.getText()) != 0)
			{
				locationsToUnlock.setText(vacantRow,0,locations.get(i-1).getLocationName());
				CheckBox unlock = new CheckBox();
				CheckBox retire = new CheckBox();
				locationsToUnlock.setWidget(vacantRow, 1, unlock);
				locationsToUnlock.setWidget(vacantRow, 2, retire);
				vacantRow = (byte) (vacantRow + 1);
			}
		}
	}

	public void generateNewLocation(ArrayList<LocationObject> locations)
	{
		clearPanel();
		updateAvailableLocations(locations);
	}

	
	class MapLauncherHandler implements ClickHandler
	{

		@Override
		public void onClick(ClickEvent event) {
			mapPanel.center();
		}
		
	}
	
	class CloseButtonHandler implements ClickHandler
	{

		@Override
		public void onClick(ClickEvent event) {
			mapPanel.hide();
		}
	}	
	
	class SaveLocationHandler implements ClickHandler
	{

		@Override
		public void onClick(ClickEvent event) {
			CapstoneWebUI.locationCreationPanel.setVisible(false);
			CapstoneWebUI.worldCreationForm.setVisible(true);
			
			LocationObject location = new LocationObject();
			location.setLocationName(nameTB.getText());
			location.setLocationDescription(descriptionTB.getText());
			location.setLongitude(longitudeTB.getText());
			location.setLatitude(latitudeTB.getText());
			location.setLocked(lockedCB.getValue());
			buildDepedency(location);

			CapstoneWebUI.worldCreationForm.addLocation(location);
		}
	}
	
	class CancelLocationHandler implements ClickHandler
	{

		@Override
		public void onClick(ClickEvent event) {
			CapstoneWebUI.locationCreationPanel.setVisible(false);
			CapstoneWebUI.worldCreationForm.setVisible(true);
		}
		
	}
	
	
}
