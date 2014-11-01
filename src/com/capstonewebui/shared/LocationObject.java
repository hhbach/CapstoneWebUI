package com.capstonewebui.shared;

import java.io.Serializable;

public class LocationObject implements Serializable{

	public String locationId;
	public String locationName;
	public String locationDescription;
	public String longitude;
	public String latitude;
	public String disoveryRadius;
	public boolean  visited;
	public boolean locked;
	public String[] locationToUnlock;
	public String[] locationToRetire;
}
