package com.capstonewebui.server;

import java.util.ArrayList;

import com.capstonewebui.client.DatabaseHandler;
import com.capstonewebui.shared.LocationObject;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class DatabaseHandlerImpl extends RemoteServiceServlet implements
DatabaseHandler {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6830493241011313788L;

	@Override
	public String greetServer(String name) throws IllegalArgumentException {
		return "hello Albert";
		

	}
	
	private Entity createEntity(LocationObject location)
	{
		Entity w = new Entity("Location ID", "filler id");
		w.setProperty("Location Name", location.locationName);
		w.setProperty("Location Description", location.locationDescription);
		w.setProperty("Latitude",location.latitude);
		w.setProperty("Longitude", location.longitude);
		w.setProperty("Disovery Radius", location.disoveryRadius);
		w.setProperty("Locked", location.locked);
		w.setProperty("Visited", location.visited);
		w.setProperty("Locations to Unlock", location.locationToUnlock);
		w.setProperty("Locations to Lock", location.locationToRetire);
		
		return w;
	}

	@Override
	public String storeData(LocationObject object)
			throws IllegalArgumentException {
		
		
		ArrayList<String> a = new ArrayList<String>();
		a.add("bankofamerica");
		DatastoreService dss = DatastoreServiceFactory.getDatastoreService();
		Entity w = createEntity(object);
		dss.put(w);
		
		return null;
	}

}
