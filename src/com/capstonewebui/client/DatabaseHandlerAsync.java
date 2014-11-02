package com.capstonewebui.client;

import com.capstonewebui.shared.LocationObject;
import com.capstonewebui.shared.WorldObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DatabaseHandlerAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	
	void storeLocation(LocationObject object, AsyncCallback<String> callback) 
			throws IllegalArgumentException;
	
	void storeWorld(WorldObject world,AsyncCallback<String> callback) 
			throws IllegalArgumentException;
	
	void getWorlds(AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
