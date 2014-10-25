package com.capstonewebui.client;

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
	private TextBox nameTB;
	private Grid worldBuilderGrid;
	private PopupPanel mapPanel;
	public LocationCreationForm() {
		this.setVisible(false);
		this.setSize("800px", "800px");
		
		worldBuilderGrid = new Grid(10,3);
		worldBuilderGrid.setStyleName("centered");

		addLabelToPanel();
		addSecondColumnContent();
		addThirdColumn();
		addNavigationButtons();
		
		this.add(worldBuilderGrid);
		
		setUpMapPanel();
		
		


		
	}
	public void buildMapUI()
	{
		 // Open a map centered on Cawker City, KS USA
	    LatLng cawkerCity = LatLng.newInstance(39.509, -98.434);
	    
	    Button closeButton = new Button("Close");
	    closeButton.addClickHandler(new CloseButtonHandler());
	    
	    map = new MapWidget(cawkerCity, 2);
	    final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
	    HorizontalPanel mapControlPanel = new HorizontalPanel();
	    
	    mapControlPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
	    
	    
	    map.setSize("600px", "600px");
	    // Add some controls for the zoom level
	    map.addControl(new LargeMapControl());

	    // Add a marker
	    

	    // Add an info window to highlight a point of interest
	    //map.getInfoWindow().open(map.getCenter(),
	        //new InfoWindowContent("World's Largest Ball of Sisal Twine"));

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
		//mapPanel.setText("Remote Procedure Call");
		//mapPanel.setAnimationEnabled(true);
		
		//Label selectLocationLabel = new Label("Please select a location: ");
		
	/*
	   Maps.loadMapsApi("", "2", false, new Runnable() {
		      public void run() {
		        //buildMapUI();
		      }
		    });
		   */
		
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
		worldBuilderGrid.setText(8, 0, "Locations To Unlock: ");
		worldBuilderGrid.setText(9, 0, "Locations To Retire: ");
	}
	
	
	//second column is a mix of textBoxes, checkboxes, and list
	private void addSecondColumnContent()
	{

		TextBox locationIDTB = new TextBox();
		locationIDTB.setEnabled(false);
		
		worldBuilderGrid.setWidget(0, 1, locationIDTB);
		
		nameTB = new TextBox();
		worldBuilderGrid.setWidget(1, 1, nameTB);
		
		TextBox descriptionTB = new TextBox();
		worldBuilderGrid.setWidget(2, 1, descriptionTB);
		
		longitudeTB = new TextBox();
		worldBuilderGrid.setWidget(3, 1, longitudeTB);
		
		latitudeTB = new TextBox();
		worldBuilderGrid.setWidget(4, 1, latitudeTB);
		
		TextBox discoveryRadiusTB = new TextBox();
		worldBuilderGrid.setWidget(5, 1, discoveryRadiusTB);

		
		CheckBox visitedCB = new CheckBox();
		worldBuilderGrid.setWidget(6, 1, visitedCB);
		
		CheckBox lockedCB = new CheckBox();
		worldBuilderGrid.setWidget(7, 1, lockedCB);
		
		ListBox locationToUnlockTB = new ListBox();
		worldBuilderGrid.setWidget(8, 1, locationToUnlockTB);
		
		ListBox locationToRetireTB = new ListBox();
		worldBuilderGrid.setWidget(9, 1, locationToRetireTB);
	}
	
	//third column houses optional option for each field.
	private void addThirdColumn()
	{	
		Button map2Button = new Button("->");
		worldBuilderGrid.setWidget(4, 2, map2Button);
		worldBuilderGrid.setText(5, 2, " meters");
		map2Button.addClickHandler(new MapLauncherHandler());
	}
	
	private void addNavigationButtons()
	{
		Button acceptButton = new Button("Accept");
		Button discardButton = new Button("Discard");
		
		
		AbsolutePanel navigationButtonsContainer= new AbsolutePanel();
		navigationButtonsContainer.add(acceptButton);
		navigationButtonsContainer.add(discardButton);
		
		navigationButtonsContainer.setStyleName("navigationButtonsContainer");
		this.add(navigationButtonsContainer);
	}
	public void submit()
	{
		
	}
	
	class selectMapLocation implements MapClickHandler
	{

		@Override
		public void onClick(MapClickEvent event) {
			//longitudeTB.setText((event.getLatLng().toString()));
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
			
			longitudeTB.setText(selectedPointStringArray[0]);
			latitudeTB.setText(selectedPointStringArray[1]);
			
		}
		
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
}
