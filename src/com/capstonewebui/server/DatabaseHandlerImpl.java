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
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gwt.maps.client.impl.GeocoderImpl.LocationsCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.appengine.api.datastore.Query.FilterPredicate;

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
		Entity w = new Entity("world", world.getWorldName());
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
	public String getLocations() throws IllegalArgumentException {
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


	@Override
	public String getWorlds() throws IllegalArgumentException {
		
		String worlds = "";
		DatastoreService dss = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("world").addSort("Name", SortDirection.ASCENDING);
		
		
		PreparedQuery pq = dss.prepare(q);
		
		for (Entity result : pq.asIterable()) {
			
			String world = (String) result.getProperty("Name");
			if(worlds.compareTo("") == 0)
				worlds = world;
			else
				worlds = worlds + "," + world;
		}
		return worlds;
	}


	@Override
	public String getWorld(String worldName) throws IllegalArgumentException {
		
		String locations = "";
		DatastoreService dss = DatastoreServiceFactory.getDatastoreService();
		Filter worldFilter =   new FilterPredicate("World",FilterOperator.EQUAL, worldName);
		Query q = new Query("location").addSort("Name", SortDirection.ASCENDING);
		q.setFilter(worldFilter);
		PreparedQuery pq = dss.prepare(q);
		for (Entity result : pq.asIterable()) {
			
			if(locations.compareTo("") == 0)
				locations = (String)result.getProperty("Name");
			else
				locations = locations + "," + (String)result.getProperty("Name");
		}
		
		return locations;
	}


	@Override
	public String getLocation(String locationName) throws IllegalArgumentException {
		String locations = "";
		DatastoreService dss = DatastoreServiceFactory.getDatastoreService();
		Filter worldFilter =   new FilterPredicate("Name",FilterOperator.EQUAL, locationName);
		Query q = new Query("location").addSort("Name", SortDirection.ASCENDING);
		q.setFilter(worldFilter);
		PreparedQuery pq = dss.prepare(q);
		
		return pq.asSingleEntity().toString();
	}



}
