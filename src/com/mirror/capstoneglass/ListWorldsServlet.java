package com.mirror.capstoneglass;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.mirror.Mirror;
import com.google.api.services.mirror.Mirror.Timeline;
import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.TimelineItem;
import com.google.glassware.AuthUtil;

import java.io.IOException;
import java.security.GeneralSecurityException;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;


public class ListWorldsServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{	 
		
		String msg = "";
		
		
		DatastoreService dss = DatastoreServiceFactory.getDatastoreService();
		
/*	
		//Add Entity to Datastore
		Entity w = new Entity("World", "BrickYardTour1");
		w.setProperty("user_id", "EXAMPLE");
		w.setProperty("name", "Brick Yard Sample Tour 1");
		w.setProperty("description", "This is a sample your available to all users. It presents the features and resources available to CIDSE students.");
		ArrayList<String> a = new ArrayList<String>();
		a.add("loc1");
		a.add("loc2");
		w.setProperty("unlocked_locations", a);
		
		dss.put(w);
		
		Entity w2 = new Entity("World", "BrickYardTour3");
		w2.setProperty("user_id", "lincoln.turley@gmail.com");
		w2.setProperty("name", "Brick Yard Sample Tour 3");
		w2.setProperty("description", "This is a sample your available to all users. It presents the features and resources available to CIDSE students.");
		ArrayList<String> b = new ArrayList<String>();
		b.add("loc5");
		b.add("loc6");
		w2.setProperty("unlocked_locations", b);
		
		dss.put(w2);
		
//*/
		
		
		
		//Query for all World entities in the datastore 
		Query qall = new Query("World");
		qall.addSort("name", SortDirection.ASCENDING);	//add sotring
		PreparedQuery pqall = dss.prepare(qall);
		
		msg += "<br><br>List of ALL worlds: " + Integer.toString(pqall.countEntities()) + "<br>\n";
		for (Entity e : pqall.asIterable())
		{
			Key k1 = e.getKey();
			System.out.println(k1.getName());
			msg += "<a href='runworld?world_id=" + e.getKey().getName() + "'>" + e.getProperty("name").toString() + "</a><br />";

		}
	
		
/*		
		//Query for a specific entity using Keys 
		Key k = KeyFactory.createKey("World", "BrickYardTour3");
		
		try
		{
			Entity ee = dss.get(k);
			msg += "<br><br>Filtered list of worlds on key: " + "<br>\n";
			msg += ee.getProperty("name") + "<br>\n";
		}
		catch (EntityNotFoundException err)
		{
			msg += "Enable to Retrieve Entity from Key<br>";
		}
		
		//Additional method
		
		Query qkey = new Query("World");
		Collection<Key> key_list = new ArrayList<Key>();
		key_list.add(KeyFactory.createKey("World", "BrickYardTour1"));
		key_list.add(KeyFactory.createKey("World", "BrickYardTour3"));
		
		Filter f = new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.IN, key_list);
		qkey.setFilter(f);
		PreparedQuery pqkey = dss.prepare(qkey);
		
		msg += "<br><br>List worlds based on key: " + Integer.toString(pqkey.countEntities()) + "<br>\n";
		for (Entity e : pqkey.asIterable())
		{
			msg += e.getProperty("name") + "<br>\n";
		}
		
		msg += "<br><br>\n";
		
//*/
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().println(
				"<html>\n" +
				"<head><meta http-equv=\"refresh\"content=\"3;url=/index.html\"></head>\n" +
				"<body>\n" +
				"\t<h1>World List</h1><br />\n" +
				"\tClick on a link below to begin a tour. <br /><br />\n" +
				msg + "</body></html>");
		}
}
