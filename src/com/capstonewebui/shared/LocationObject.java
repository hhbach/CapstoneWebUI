package com.capstonewebui.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class LocationObject implements Serializable{

	private String locationId;
	private String locationName;
	private String locationDescription;
	private String longitude;
	private String latitude;
	private String disoveryRadius;
	private String world;
	private boolean  visited;
	private boolean locked;
	private ArrayList<String> locationToUnlock;
	private ArrayList<String>  locationToRetire;
	
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getLocationDescription() {
		return locationDescription;
	}
	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getDisoveryRadius() {
		return disoveryRadius;
	}
	public void setDisoveryRadius(String disoveryRadius) {
		this.disoveryRadius = disoveryRadius;
	}
	public String getWorld() {
		return world;
	}
	public void setWorld(String world) {
		this.world = world;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public ArrayList<String> getLocationToUnlock() {
		return locationToUnlock;
	}
	public void setLocationToUnlock(ArrayList<String> locationToUnlock) {
		this.locationToUnlock = locationToUnlock;
	}
	public ArrayList<String> getLocationToRetire() {
		return locationToRetire;
	}
	public void setLocationToRetire(ArrayList<String> locationToRetire) {
		this.locationToRetire = locationToRetire;
	}
}
