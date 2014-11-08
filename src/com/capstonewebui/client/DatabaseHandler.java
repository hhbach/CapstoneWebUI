package com.capstonewebui.client;

import com.capstonewebui.shared.LocationObject;
import com.capstonewebui.shared.WorldObject;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("greet")
public interface DatabaseHandler extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;	
	String storeLocation(LocationObject object) throws IllegalArgumentException;
	String storeWorld(WorldObject world) throws IllegalArgumentException;
	String getWorlds() throws IllegalArgumentException;
	String getLocations() throws IllegalArgumentException;
}
