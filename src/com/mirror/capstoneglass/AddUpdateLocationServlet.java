package com.mirror.capstoneglass;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.*;
import com.tour.capstoneglass.Location;



public class AddUpdateLocationServlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{	 
		/***
		 * This method will be used to add entities to the datatore during testing. 
		 * TODO: Add a doPost method that can be used by other web pages to add/update locations
		 */
		String msg = "";
		
		DatastoreService dss = DatastoreServiceFactory.getDatastoreService();
		
		//createEntity(loc_id, name, description, latitude, longitude, unlock_threshold, visited, locked, locations_to_unlock)
		
		
		ArrayList<String> aUnlock = new ArrayList<String>();
		aUnlock.add("decisiontheater");
		
		ArrayList<String> aRetire = new ArrayList<String>();
		aRetire.add("bankofamerica");
		
		
		Entity l1 = Location.createEntity("bankofamerica", "Bank of America"
				, "Bank of America is an American multinational banking and financial services corporation headquartered in Charlotte, North Carolina. It is the second largest bank holding company in the United States by assets. As of 2013, Bank of America is the twenty-first largest company in the United States by total revenue. In 2010, Forbes listed Bank of America as the third biggest company in the world."
				, 33.423727, -111.939762, 10, false, true, aUnlock, aRetire);
		
		ArrayList<String> bUnlock = new ArrayList<String>();
		bUnlock.add("ubreakifix");
		ArrayList<String> bRetire = new ArrayList<String>();
		bRetire.add("decisiontheater");
		Entity l2 = Location.createEntity("decisiontheater", "Decision Theater"
				, "The Decision Theater Network actively engages researchers and leaders to visualize solutions to complex problems. The Network provides the latest expertise in collaborative, computing and display technologies for data visualization, modeling, and simulation. The Network addresses cross-disciplinary local, national and international issues by drawing on Arizona State University’s diverse academic and research capabilities."
				, 33.424085, -111.939100, 10, false, true, bUnlock, bRetire);
		
		
		ArrayList<String> cRetire = new ArrayList<String>();
		cRetire.add("ubreakifix");
		Entity l3 = Location.createEntity("ubreakifix", "U Break I fix"
				, "uBreakiFix Tempe provides Electronics Repair Services to local customers in Tempe, Arizona and surrounding areas. Repair services include: iPhone Repair, iPod Repair, Smartphone Repair, Computer Repair, iPad Repair, Tablet Repair and Game Console Repair. Diagnostic Services are always free and you'll also receive $5 off your repair or a free screen protector by leaving us a review on Google+ Local."
				, 33.423774, -111.939585, 10, false, true, new ArrayList<String>(), cRetire);

		//Add Entity to Datastore
		
		
		List<Entity> eList = Arrays.asList(l1,l2, l3);
		dss.put(eList);

		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().println(
				"<html>\n" +
				"<head><meta http-equv=\"refresh\"content=\"3;url=/index.html\"></head>\n" +
				"<body>\n" +
				"\t<h1>Location Update</h1><br />\n" +
				"\tLocation(" + Integer.toString(eList.size()) + ") have been added/updated. <br /><br />\n" +
				msg + "</body></html>");
		}
	
/*	
	public Entity createEntity(String loc_id, String name, String description
			, double latitude, double longitude, int unlock_threshold, 
			boolean visited, boolean locked, ArrayList<String> locations_to_unlock)//Add locations to retire???
	
	{
		Entity e = new Entity("Location", loc_id);
		e.setProperty("loc_id", loc_id);
		e.setProperty("name", name);
		e.setProperty("description", description);
		e.setProperty("latitude", latitude);
		e.setProperty("longitude", longitude);
		e.setProperty("unlock_threshold", unlock_threshold);
		e.setProperty("visited", false);
		e.setProperty("locked", true);
		e.setProperty("locations_to_unlock", locations_to_unlock);
		
		return e;
		
	}
	*/
}
