package com.capstonewebui.client;

import com.capstonewebui.shared.LocationObject;
import com.capstonewebui.shared.WorldObject;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import java.util.ArrayList;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.LatLngBounds;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerOptions;

public class WorldCreationForm extends FlowPanel {

	private HorizontalPanel mainPanel = new HorizontalPanel();
	private HorizontalPanel namePanel = new HorizontalPanel();
	private HorizontalPanel descriptionPanel = new HorizontalPanel();
	private VerticalPanel leftFormPanel = new VerticalPanel();
	private VerticalPanel rightMapPanel = new VerticalPanel();
	private HorizontalPanel discardSavePanel = new HorizontalPanel();
	private FlexTable locationsFlexTable = new FlexTable();
	private VerticalPanel mapPanel = new VerticalPanel();
	private HorizontalPanel addLocationPanel = new HorizontalPanel();

	private ArrayList<LocationObject> locationsArray;
	private TextBox nameTextBox = new TextBox();
	private TextArea descriptionTextBox = new TextArea();
	private Button addLocationButton = new Button("Add a Location");
	private Button discardButton = new Button("Discard");
	private Button saveButton = new Button("Save");
	private Label nameLabel = new Label("Name:");
	private Label descriptionLabel = new Label("Description:");
	private ArrayList<String> deleteQueue = new ArrayList<String>();
	private ArrayList<Marker> markersArray = new ArrayList<Marker>();
	private MapWidget map;
	private MapOptions mapOpt;

	public WorldCreationForm() {

		locationsArray = new ArrayList<LocationObject>();
		this.setVisible(false);
		this.add(assemblePanels());

		this.addStyleName("worldCreationPanel");
		mainPanel.addStyleName("mainPanel");
		leftFormPanel.addStyleName("leftFormPanel");
		rightMapPanel.addStyleName("rightFormPanel");
		mapPanel.addStyleName("mapPanel");
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
		locationsFlexTable.getRowFormatter().addStyleName(0,
				"locationListHeader");

		saveButton.addClickHandler(new SaveButtonHandler());
		discardButton.addClickHandler(new DiscardButtonHandler());
		addLocationButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				CapstoneWebUI.worldCreationForm.setVisible(false);
				CapstoneWebUI.locationCreationPanel.setVisible(true);
				CapstoneWebUI.locationCreationPanel
						.generateNewLocation(locationsArray);
				CapstoneWebUI.locationCreationPanel.resizeMap();
				CapstoneWebUI.locationCreationPanel.resetMarker();
			}
		});

	}

	public void resizeMap() {
		map.triggerResize();
	}

	private HorizontalPanel assemblePanels() {
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

		buildMap();
		mapPanel.add(map);

		rightMapPanel.add(mapPanel);
		rightMapPanel.add(discardSavePanel);
		mainPanel.add(rightMapPanel);

		return mainPanel;
	}

	private void clearPanel() {
		deleteQueue = new ArrayList<String>();
		locationsArray = new ArrayList<LocationObject>();
		nameTextBox.setText("");
		descriptionTextBox.setText("");

		for (int i = 1; i <= locationsFlexTable.getRowCount() - 1; i++) {
			locationsFlexTable.removeRow(i);
		}
	}

	private void assembleFlexTable() {
		locationsFlexTable.setText(0, 0, "#");
		locationsFlexTable.setText(0, 1, "Unlocks");
		locationsFlexTable.setText(0, 2, "Retireds");
		locationsFlexTable.setText(0, 3, "");
		locationsFlexTable.setText(0, 4, "");
	}

	private boolean addLocationToArray(LocationObject location) // verify that
																// the new
																// location is
																// unique before
																// adding it
	{
		boolean found = false;

		for (int i = 0; i < locationsArray.size(); i++) {
			if (locationsArray.get(i).getLocationName()
					.compareTo(location.getLocationName()) == 0) {
				locationsArray.set(i, location);
				found = true;
			}
		}
		if (!found)
			locationsArray.add(location);
		return found;

	}

	private void updateLocationInFlexTable(LocationObject location) // update a
																	// longittude/latitude
																	// in flex
																	// table
	{
		byte locationsCount = (byte) locationsFlexTable.getRowCount();
		for (int i = 0; i < locationsCount; i++) {
			if (locationsFlexTable.getText(i, 0).compareTo(
					location.getLocationName()) == 0) {
				locationsFlexTable.setText(i, 1, location.getLocationToUnlock()
						.toString());
				locationsFlexTable.setText(i, 2, location.getLocationToRetire()
						.toString());
				// locationsFlexTable.setText(row, column, text)
			}
		}

	}

	public void addLocation(final LocationObject location) {

		if (addLocationToArray(location)) // if location is already found in the
											// array
		{
			System.out.println("Exists in Flex Table");
			updateLocationInFlexTable(location);
		} else {
			System.out.println("Does not exist in Flex Table");
			int row = locationsFlexTable.getRowCount();
			locationsFlexTable.setText(row, 0, location.getLocationName());
			
			if(location.getLocationToUnlock() != null)
				locationsFlexTable.setText(row, 1, location.getLocationToUnlock().toString());
			
			if(location.getLocationToRetire() != null)
				locationsFlexTable.setText(row, 2, location.getLocationToRetire().toString());
			Button deleteButton = new Button("Delete");
			Button editButton = new Button("Edit");

			deleteButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					deleteQueue.add(location.getLocationName());
					removeLocation(location.getLocationName());
					/*
					 * CapstoneWebUI.databaseService.deleteLocation(location.
					 * getLocationName(), new AsyncCallback<String>() { public
					 * void onFailure(Throwable caught) { } public void
					 * onSuccess(String result) {
					 * removeLocation(location.getLocationName()); }
					 * 
					 * });
					 */
				}
			});

			editButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					editLocation(location.getLocationName());

				}
			});
			locationsFlexTable.setWidget(row, 3, editButton);
			locationsFlexTable.setWidget(row, 4, deleteButton);

		}

		LatLng locationLatLng = LatLng.newInstance(
				Double.parseDouble(location.getLatitude()),
				Double.parseDouble(location.getLongitude()));
		System.out.println("lat: " + locationLatLng.getLatitude() + "  long:"
				+ locationLatLng.getLongitude());

		MarkerOptions options = MarkerOptions.newInstance();
		options.setMap(map);
		Marker mark = Marker.newInstance(options);
		mark.setPosition(locationLatLng);

		mark.setMap(map);
		markersArray.add(mark);
	}

	private void removeLocation(String a) {
		int rowNumber = -1;
		for (int i = 1; i < locationsFlexTable.getRowCount(); i++) {
			if (a.compareTo(locationsFlexTable.getText(i, 0)) == 0) {
				rowNumber = i;
			}
		}
		if (rowNumber != -1 && rowNumber < locationsFlexTable.getRowCount()) {
			locationsFlexTable.removeRow(rowNumber);
			locationsArray.remove(rowNumber - 1);
		}
	}

	private void editLocation(String a) {
		int rowNumber = -1;
		for (int i = 1; i < locationsFlexTable.getRowCount(); i++) {
			if (a.compareTo(locationsFlexTable.getText(i, 0)) == 0) {
				rowNumber = i;
			}
		}

		if (rowNumber != 0 && rowNumber < locationsFlexTable.getRowCount()) {
			CapstoneWebUI.locationCreationPanel.update(
					locationsArray.get(rowNumber - 1), locationsArray);
			CapstoneWebUI.worldCreationForm.setVisible(false);
			CapstoneWebUI.locationCreationPanel.setVisible(true);
		}

	}

	private void buildMap() {
		LatLng arizonaStateUniversity = LatLng.newInstance(33.453760,
				-112.072978);
		mapOpt = MapOptions.newInstance();

		mapOpt.setCenter(arizonaStateUniversity);
		mapOpt.setZoom(4);
		mapOpt.setScaleControl(true);
		mapOpt.setOverviewMapControl(true);
		map = new MapWidget(mapOpt); // John
		map.setCenter(arizonaStateUniversity);
		map.setSize("380px", "380px");
	}

	class SaveButtonHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			WorldObject mWorld = new WorldObject();
			mWorld.setWorldName(nameTextBox.getText());
			mWorld.setWorldDescription(descriptionTextBox.getText());
			mWorld.setUserID("lincoln.turley@gmail.com");

			System.out.println("Locations Queued to be saved:"
					+ locationsArray.size());

			for (int i = 0; i < locationsArray.size(); i++) {
				locationsArray.get(i).setWorld(nameTextBox.getText());
				CapstoneWebUI.databaseService.storeLocation(
						locationsArray.get(i), new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								System.out.println(caught);
							}

							public void onSuccess(String result) {
								System.out.println("saved!");
							}
						});
			}

			CapstoneWebUI.databaseService.storeWorld(mWorld,
					new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							System.out.println(caught);
						}

						public void onSuccess(String result) {
							CapstoneWebUI.worldCreationForm.setVisible(false);
							CapstoneWebUI.menuPanel.setVisible(true);
							clearPanel();
						}
					});

			byte deleteQueueSize = (byte) deleteQueue.size();
			for (int i = 0; i < deleteQueueSize; i++) {
				final String locationName = deleteQueue.get(i);

				CapstoneWebUI.databaseService.deleteLocation(locationName,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
							}

							public void onSuccess(String result) {
								// removeLocation(locationName);
								System.out.println("Successfully deleted: "
										+ locationName);
							}

						});
			}

		}

	}

	class DiscardButtonHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			CapstoneWebUI.worldCreationForm.setVisible(false);
			CapstoneWebUI.menuPanel.setVisible(true);
			clearPanel();
		}

	}

	// takes a string that has the world name and world description as an input.
	// The world name and world input are comma seperated;
	// Example: "worldName, world descrption"
	public void loadWorldInformation(String worldInfo) {
		clearPanel();

		System.out.println("The world Info is " + worldInfo);

		String[] worldInfoString = worldInfo.split(",");

		nameTextBox.setText(worldInfoString[0]);
		// descriptionTextBox.setText(worldInfoString[1]);

	}

	public void generateNewWorld() {
		clearPanel();
		debugPrintLocationsArray();
		// locationsArray.clear();
	}

	private void debugPrintLocationsArray() {
		if (locationsArray != null) {
			byte numLocs = (byte) locationsArray.size();
			System.out.println(numLocs);
			for (byte i = 0; i < numLocs; i++) {
				System.out.println(locationsArray.get(i));
			}
		} else
			System.out.println("locations array is null");
	}

	// load the locations for a world
	// The input is a list of locations such that it is seperated by ','.
	// example: "house,work,gym"
	public void loadLocations(String locations) {
		locationsArray = new ArrayList<LocationObject>();
		String[] locationsNameArray = locations.split(",");

		for (byte i = 0; i < locationsNameArray.length; i++) {
			final String location = locationsNameArray[i];
			CapstoneWebUI.databaseService.getLocation(locationsNameArray[i],
					new AsyncCallback<LocationObject>() {
						public void onFailure(Throwable caught) {
							System.out.println(caught);
						}
						@Override
						public void onSuccess(LocationObject result) {
							addLocation(result);
						}
					});
		}
	}

	private ArrayList<String> ArrayStringToArrayList(String ArrayString) {
		/**
		 * this function will take a an arrayString and return an arrayList
		 */

		ArrayList<String> a = new ArrayList<String>();
		if (!ArrayString.contains("null")) {
			String tempString = ArrayString.replace(" ", ""); // get rid of all
																// spaces;
			tempString = tempString.replace("[", ""); // get rid of [
			tempString = tempString.replace("]", "");

			String[] tempArray = tempString.split(","); // split the words by
														// ','

			for (int i = 0; i < tempArray.length; i++) {
				a.add(tempArray[i]);
			}
		}
		return a;
	}
}
