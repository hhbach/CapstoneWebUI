package com.mirror.capstoneglass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tour.capstoneglass.*;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.mirror.Mirror;
import com.google.api.services.mirror.Mirror.Timeline;
import com.google.api.services.mirror.model.Location;
import com.google.api.services.mirror.model.MenuItem;
import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.TimelineItem;
import com.google.appengine.api.datastore.*;
import com.google.glassware.AuthUtil;



public class RunWorldServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{
		//pushes inital bundled card with all currently unlock locations and a map of all unlocked locations
		
				String world_id = req.getParameter("world_id");
				
				if(!world_id.isEmpty() && world_id != null)
				{
					String msg = "You have started the " + world_id + " Tour!<br />\n";
					World w = new World(world_id);
					
					if (w!=null)
					{
					
						Mirror mirror = getMirror(req);
						Timeline timeline = mirror.timeline();
						
						TimelineItem worldcard = createWorldCard(w);
						
						
						//initially unlocked locations.
						for (com.tour.capstoneglass.Location l : w.unlocked_locations)
						{
							TimelineItem locationcard = createLocationCard(l);
							locationcard.setBundleId(w.world_id);
							timeline.insert(locationcard).execute();
						}
						
						
					}
					
					
						
					//print out results
					resp.setContentType("text/html; charset=utf-8");
					resp.getWriter().println(
							"<html><head>" +
							"<meta http-equv=\"refresh\"content=\"3;url=/index.html\">" +
							"</head>" +
							"<body>" + msg + "</body></html>");
					
				}
				else
				{
					//print out results
					resp.setContentType("text/html; charset=utf-8");
					resp.getWriter().println(
							"<html><head>" +
							"<meta http-equv=\"refresh\"content=\"3;url=/index.html\">" +
							"</head>" +
							"<body>The url parameter 'world_id' is Empty. Please Try again.<br></body></html>");
				
				}
	}
	
	
	public TimelineItem createLocationCard(com.tour.capstoneglass.Location loc){
		double latitude = 0.0; //this needs to be the current location of glass
		double longitude = 0.0; //this needs to be the current location of glass
		
		TimelineItem timelineItem = new TimelineItem()
		.setHtml(loc.toCard(latitude, longitude))
		.setDisplayTime(new DateTime(new Date()))
		.setNotification(new NotificationConfig().setLevel("Default"));

		List<MenuItem> menuItemList = new ArrayList<MenuItem>();
		timelineItem.setLocation(new Location()
						.setLatitude(loc.latitude)
						.setLongitude(loc.longitude)
						.setDisplayName(loc.name));

		menuItemList.add(new MenuItem().setAction("NAVIGATE"));	
		menuItemList.add(new MenuItem().setAction("DELETE"));
		
		return timelineItem;
	}
	
	public TimelineItem createWorldCard(World w){
		TimelineItem timelineItem = new TimelineItem()
		.setHtml(w.toCard())
		.setDisplayTime(new DateTime(new Date()))
		.setNotification(new NotificationConfig().setLevel("Default"));

		List<MenuItem> menuItemList = new ArrayList<MenuItem>();
		menuItemList.add(new MenuItem().setAction("DELETE"));
		
		return timelineItem;
	}
	
	//allows access to the Mirror API
	public Mirror getMirror(HttpServletRequest req) throws IOException{
		//get credential of client
		Credential credential = AuthUtil.getCredential(req);
		
		//build access to Mirror API
		return new Mirror.Builder(new UrlFetchTransport(), new JacksonFactory(), credential)
		.setApplicationName("Hello Glass!").build();
		
	}
}
