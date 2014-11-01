package com.capstonewebui.client;

import com.capstonewebui.shared.LocationObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DatabaseHandlerAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	
	void storeData(LocationObject object, AsyncCallback<String> callback) 
			throws IllegalArgumentException;
}
