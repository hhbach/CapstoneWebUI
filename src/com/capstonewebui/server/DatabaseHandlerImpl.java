package com.capstonewebui.server;

import java.util.ArrayList;
import java.util.Map;

import com.capstonewebui.client.DatabaseHandler;
import com.capstonewebui.shared.LocationObject;
import com.capstonewebui.shared.WorldObject;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gwt.maps.client.impl.GeocoderImpl.LocationsCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class DatabaseHandlerImpl extends RemoteServiceServlet implements
DatabaseHandler {

	private static final long serialVersionUID = -6830493241011313788L;

	@Override
	public String greetServer(String name) throws IllegalArgumentException {
		return "hello Albert";
	}
	
	
	private Entity createWorldEntity(WorldObject world)
	{
		Entity w = new Entity("world", "tester");
		w.setProperty("Name", world.getWorldName());
		w.setProperty("Description", world.getWorldDescription());
		return w;
	}
	
	private Entity createLocationEntity(LocationObject location)
	{
		Entity w = new Entity("location", location.getLocationName());
		w.setProperty("Name", location.getLocationName());
		w.setProperty("LocationDescription", location.getLocationDescription());
		w.setProperty("Latitude",location.getLatitude());
		w.setProperty("Longitude", location.getLongitude());
		w.setProperty("DisoverRadius", location.getDisoveryRadius());
		w.setProperty("Locked", location.isLocked());
		w.setProperty("Visited", location.isLocked());
		w.setProperty("LocationsToUnlock", location.getLocationToUnlock());
		w.setProperty("LocationsToLock", location.getLocationToRetire());
		w.setProperty("World", location.getWorld());
		return w;
	}

	@Override
	public String storeLocation(LocationObject object)
			throws IllegalArgumentException {
		DatastoreService dss = DatastoreServiceFactory.getDatastoreService();
		Entity w = createLocationEntity(object);
		dss.put(w);
		
		return null;
	}
	
	@Override
	public String storeWorld(WorldObject world) 
			throws IllegalArgumentException {
		DatastoreService dss = DatastoreServiceFactory.getDatastoreService();
		Entity w = createWorldEntity(world);
		dss.put(w);
		return null;
	}

	
	@Override
	public String getWorlds() throws IllegalArgumentException {
		DatastoreService dss = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("location").addSort("Name", SortDirection.ASCENDING);
		
		
		PreparedQuery pq = dss.prepare(q);
		
		for (Entity result : pq.asIterable()) {
			String locations = (String) result.getProperty("Name");
			System.out.println(locations);
		}
		
		System.out.println("done");
		return null;
	}



}
