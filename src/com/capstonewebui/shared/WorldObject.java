package com.capstonewebui.shared;

import java.io.Serializable;

public class WorldObject implements Serializable {

	String worldName;
	String worldDescription;
	String userID;
	public String getWorldName() {
		return worldName;
	}
	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}
	public String getWorldDescription() {
		return worldDescription;
	}
	public void setWorldDescription(String worldDescription) {
		this.worldDescription = worldDescription;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID1) {
		this.userID = userID1;
	}
}
