package com.capstonewebui.client;

import java.util.ArrayList;

import com.capstonewebui.shared.LocationObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.*;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LocationCreationForm extends AbsolutePanel {

	private MapWidget map;
	private MapOptions mapOpt;
	private Marker mark;
	private TextBox longitudeTB;
	private TextBox latitudeTB;
	private TextBox descriptionTB;
	private TextBox nameTB;
	private FlexTable worldBuilderGrid = new FlexTable();
	private HorizontalPanel mainPanel = new HorizontalPanel();
	private VerticalPanel mapPanel = new VerticalPanel();
	private CheckBox lockedCB;
	private TextBox discoveryRadiusTB;
	private FlexTable dependentLocationsFlexTable;

	public LocationCreationForm() {
		this.setVisible(false);
		this.setSize("800px", "800px");
		this.add(assemblePanels());
	}

	private HorizontalPanel assemblePanels() {

		worldBuilderGrid.setStyleName("centered");

		addLabelToPanel();
		addSecondColumnContent();
		addNavigationButtons();
		addAvailableLocationsHeader();
		setUpMapPanel();

		worldBuilderGrid.setSize("400px", "400px");
		mainPanel.add(worldBuilderGrid);
		mainPanel.add(mapPanel);

		return mainPanel;

	}

	public void resizeMap() {
		map.triggerResize();
	}

	public void resetMarker() {
		mark.close();
	}

	public void buildMapUI() {
		HorizontalPanel mapControlPanel = new HorizontalPanel();

		mapControlPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		LatLng arizonaStateUniversity = LatLng.newInstance(33.453760,
				-112.072978);
		mapOpt = MapOptions.newInstance();
		mapOpt.setCenter(arizonaStateUniversity);
		mapOpt.setZoom(4);
		map = new MapWidget(mapOpt);
		map.setSize("350px", "350px");

		map.addClickHandler(new selectMapLocation());

	}

	private void setUpMapPanel() {
		mapPanel = new VerticalPanel();
		mapPanel.setSize("400px", "400px");
		buildMapUI();
		mapPanel.add(map);
	}

	private void addLabelToPanel() {
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

	// second column is a mix of textBoxes, checkboxes, and list
	private void addSecondColumnContent() {

		TextBox locationIDTB = new TextBox();
		locationIDTB.setEnabled(false);

		worldBuilderGrid.setWidget(0, 1, locationIDTB);

		nameTB = new TextBox();
		worldBuilderGrid.setWidget(1, 1, nameTB);

		descriptionTB = new TextBox();
		worldBuilderGrid.setWidget(2, 1, descriptionTB);

		longitudeTB = new TextBox();

		FlowPanel longitudeContainer = new FlowPanel();
		longitudeContainer.add(longitudeTB);
		worldBuilderGrid.setWidget(3, 1, longitudeContainer);

		latitudeTB = new TextBox();
		worldBuilderGrid.setWidget(4, 1, latitudeTB);

		discoveryRadiusTB = new TextBox();
		discoveryRadiusTB.setText("meters");
		worldBuilderGrid.setWidget(5, 1, discoveryRadiusTB);

		lockedCB = new CheckBox();
		worldBuilderGrid.setWidget(6, 1, lockedCB);

	}

	// this consists "id", "longitude/latitude", and "edit/add" buttons
	private void addAvailableLocationsHeader() {
		dependentLocationsFlexTable = new FlexTable();
		worldBuilderGrid.getFlexCellFormatter().setColSpan(9, 0, 2);
		worldBuilderGrid.setWidget(9, 0, dependentLocationsFlexTable);

		dependentLocationsFlexTable.getColumnFormatter().setWidth(0, "26px");
		dependentLocationsFlexTable.getColumnFormatter().setWidth(1, "125px");
		dependentLocationsFlexTable.getColumnFormatter().setWidth(2, "81px");
		dependentLocationsFlexTable.getRowFormatter().addStyleName(0,
				"locationListHeader");
		dependentLocationsFlexTable.setText(0, 0, "Locations");
		dependentLocationsFlexTable.setText(0, 1, "Unlock");
		dependentLocationsFlexTable.setText(0, 2, "Retire");
	}

	private void clearPanel() {
		nameTB.setText("");
		descriptionTB.setText("");
		longitudeTB.setText("");
		latitudeTB.setText("");
		lockedCB.setValue(false);

		byte dependentTableSize = (byte) dependentLocationsFlexTable
				.getRowCount();

		for (int i = 1; i < dependentTableSize; i++) {
			dependentLocationsFlexTable.removeRow(i);
		}

	}

	private void addNavigationButtons() {
		Button acceptButton = new Button("Accept");
		Button discardButton = new Button("Discard");

		FlowPanel navigationButtonsContainer = new FlowPanel();
		navigationButtonsContainer.add(acceptButton);
		navigationButtonsContainer.add(discardButton);

		// adds listeners
		discardButton.addClickHandler(new CancelLocationHandler());
		acceptButton.addClickHandler(new SaveLocationHandler());

		navigationButtonsContainer
				.setStyleName("locationCreationButtonsContainer");
		this.add(navigationButtonsContainer);
	}

	public void update(LocationObject location,
			ArrayList<LocationObject> allLocations) {
		clearFields();
		longitudeTB.setText(location.getLongitude());
		latitudeTB.setText(location.getLatitude());
		descriptionTB.setText(location.getLocationDescription());
		nameTB.setText(location.getLocationName());

		updateAvailableLocations(allLocations);
	}

	class selectMapLocation implements ClickMapHandler {
		@Override
		public void onEvent(ClickMapEvent event) {
			LatLng selectedPoint = event.getMouseEvent().getLatLng();

			if (mark != null) {
				mark.setPosition(selectedPoint);
				mark.setMap(map);
			} else {
				MarkerOptions options = MarkerOptions.newInstance();
				mark = Marker.newInstance(options);
				mark.setPosition(selectedPoint);
				mark.setMap(map);
			}
			// map.addOverlay(new Marker(selectedPoint));

			// map.getInfoWindow().open(selectedPoint,new
			// InfoWindowContent(nameTB.getText()));

			// radius circle

			String selectedPointString = selectedPoint.toString();
			selectedPointString = selectedPointString.replace(")", "");
			selectedPointString = selectedPointString.replace("(", "");
			String selectedPointStringArray[] = selectedPointString.split(",");

			longitudeTB.setText(selectedPointStringArray[1]);
			latitudeTB.setText(selectedPointStringArray[0]);
		}
	}

	private void buildDepedency(LocationObject location) {
		ArrayList<String> locationsToRetireArray = new ArrayList<String>();
		ArrayList<String> locationsToUnlockArray = new ArrayList<String>();
		byte rowCount = (byte) dependentLocationsFlexTable.getRowCount();

		for (int i = 1; i < rowCount; i++) {

			System.out.println("collumn " + i + "has: "
					+ dependentLocationsFlexTable.getCellCount(i));

			CheckBox cb = (CheckBox) dependentLocationsFlexTable
					.getWidget(i, 1);
			if (cb.getValue() == true) {
				locationsToUnlockArray.add(dependentLocationsFlexTable.getText(
						i, 0));
			}

			cb = (CheckBox) dependentLocationsFlexTable.getWidget(i, 2);
			if (cb.getValue() == true) {
				locationsToRetireArray.add(dependentLocationsFlexTable.getText(
						i, 0));
			}

		}

		location.setLocationToUnlock(locationsToUnlockArray);
		location.setLocationToRetire(locationsToRetireArray);
	}

	private void clearFields() {
		nameTB.setText("");
		descriptionTB.setText("");
		descriptionTB.setText("");
		nameTB.setText("");
		lockedCB.setValue(false);

		for (int i = 1; i < dependentLocationsFlexTable.getRowCount(); i++) {
			dependentLocationsFlexTable.removeAllRows();
			addAvailableLocationsHeader();
		}
	}

	private void updateAvailableLocations(ArrayList<LocationObject> locations) {
		int locationSize = locations.size();
		byte vacantRow = 1;
		for (int i = 1; i <= locationSize; i++) {

			if (locations.get(i - 1).getLocationName()
					.compareTo(nameTB.getText()) != 0) {
				dependentLocationsFlexTable.setText(vacantRow, 0, locations
						.get(i - 1).getLocationName());
				CheckBox unlock = new CheckBox();
				CheckBox retire = new CheckBox();
				dependentLocationsFlexTable.setWidget(vacantRow, 1, unlock);
				dependentLocationsFlexTable.setWidget(vacantRow, 2, retire);
				vacantRow = (byte) (vacantRow + 1);
			}
		}
	}

	public void generateNewLocation(ArrayList<LocationObject> locations) {
		clearPanel();
		updateAvailableLocations(locations);
	}

	class SaveLocationHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			CapstoneWebUI.locationCreationPanel.setVisible(false);
			CapstoneWebUI.worldCreationForm.setVisible(true);
			CapstoneWebUI.worldCreationForm.resizeMap();

			LocationObject location = new LocationObject();
			location.setLocationName(nameTB.getText());
			location.setLocationDescription(descriptionTB.getText());
			location.setLongitude(longitudeTB.getText());
			location.setLatitude(latitudeTB.getText());
			location.setLocked(lockedCB.getValue());
			location.setDisoveryRadius(discoveryRadiusTB.getValue());
			buildDepedency(location);

			CapstoneWebUI.worldCreationForm.addLocation(location);
		}
	}

	class CancelLocationHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			CapstoneWebUI.locationCreationPanel.setVisible(false);
			CapstoneWebUI.worldCreationForm.setVisible(true);
		}

	}

}
