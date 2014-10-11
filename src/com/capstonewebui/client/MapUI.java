package com.capstonewebui.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class MapUI extends AbsolutePanel{

	private Grid worldBuilderGrid;
	
	MapUI()
	{

		this.setVisible(false);
		this.setSize("800px", "800px");
		
		Grid worldBuilderGrid = new Grid(3,3);
		worldBuilderGrid.setStyleName("centered");

		Button addPOIButton = new Button("Add New Point");
		
		worldBuilderGrid.setWidget(0, 1, addPOIButton);
		worldBuilderGrid.setText(1, 1, "Location: ");
		worldBuilderGrid.setText(0, 0, "Please select a Point");
		
		//this.add(worldBuilderGrid);
		
		
		Maps.loadMapsApi("", "2", false, new Runnable() {
			
			@Override
			public void run() {
				buildMap();
			}
		});
		
	}
	
	private void buildMap()
	{
	    // Open a map centered on Cawker City, KS USA
	    LatLng cawkerCity = LatLng.newInstance(39.509, -98.434);

	    final MapWidget map = new MapWidget(cawkerCity, 2);
	    map.setSize("100%", "100%");
	    // Add some controls for the zoom level
	    map.addControl(new LargeMapControl());

	    // Add a marker
	    map.addOverlay(new Marker(cawkerCity));

	    // Add an info window to highlight a point of interest
	    map.getInfoWindow().open(map.getCenter(),
	        new InfoWindowContent("World's Largest Ball of Sisal Twine"));

	    final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
	    dock.addNorth(map, 300);
	    dock.addEast(worldBuilderGrid, 400);

	    // Add the map to the HTML host page
	    this.add(dock);

		
		/*
		 // Open a map centered on Cawker City, KS USA
	    LatLng cawkerCity = LatLng.newInstance(39.509, -98.434);

	    final MapWidget map = new MapWidget(cawkerCity, 2);
	    map.setSize("100%", "100%");
	    // Add some controls for the zoom level
	    map.addControl(new LargeMapControl());
	    
	    // Add a marker
	    map.addOverlay(new Marker(cawkerCity));

	    // Add an info window to highlight a point of interest
	    map.getInfoWindow().open(map.getCenter(),
	        new InfoWindowContent("World's Largest Ball of Sisal Twine"));


	    //one panel for the map and one panel for the forms due to issues
	    //where one grid not showing up without being added to panel first;
	    AbsolutePanel mapPanel = new AbsolutePanel();
	    AbsolutePanel worldBuilderPanel = new AbsolutePanel();
	    worldBuilderPanel.setSize("400px", "400px");
	    worldBuilderPanel.add(worldBuilderGrid);
	    mapPanel.setSize("400px","400px");
	    mapPanel.add(map);
	    mapPanel.setStyleName("leftIndent");
	    //this.add(mapPanel);
	    this.add(map);
	    this.add(worldBuilderPanel);
	    
	    //worldBuilderGrid.setWidget(0,0 , mapPanel);
	    */

	}
	

}
