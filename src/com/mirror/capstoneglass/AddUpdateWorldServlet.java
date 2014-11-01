package com.mirror.capstoneglass;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.*;


public class AddUpdateWorldServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{	 
		/***
		 * This method will be used to add entities to the datatore during testing. 
		 * TODO: Add a doPost method that can be used by other web pages to add/update Worlds
		 */
		String msg = "";
		
		DatastoreService dss = DatastoreServiceFactory.getDatastoreService();
		
		ArrayList<String> a = new ArrayList<String>();
		a.add("bankofamerica");
		
		Entity w = createEntity("BrickYardTour1", "EXAMPLE", "Brick Yard Sample Tour 1"
				, "This is a sample your available to all users. It presents the features and resources available to CIDSE students."
				, a);
		
		
		dss.put(w);

		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().println(
				"<html>\n" +
				"<head><meta http-equv=\"refresh\"content=\"3;url=/index.html\"></head>\n" +
				"<body>\n" +
				"\t<h1>World List</h1><br />\n" +
				"\tWorld(s) has been added/updated. <br /><br />\n" +
				msg + "</body></html>");
	}
	
	public Entity createEntity(String world_id, String user_id, String name, String description, ArrayList<String> unlocked_locations)
	{
		Entity w = new Entity("World", world_id);
		w.setProperty("world_id", world_id);
		w.setProperty("user_id", user_id);
		w.setProperty("name", name);
		w.setProperty("description", description);
		w.setProperty("unlocked_locations", unlocked_locations);
		
		return w;
	}
}
