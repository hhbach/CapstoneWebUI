package com.capstonewebui.client;

import com.capstonewebui.shared.LocationObject;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("greet")
public interface DatabaseHandler extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;	
	String storeData(LocationObject object) throws IllegalArgumentException;
}
