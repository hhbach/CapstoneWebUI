package com.mirror.capstoneglass;

import java.io.IOException;
import java.util.Date;
import com.google.appengine.api.ThreadManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.google.api.services.mirror.model.Location;
import com.google.api.services.mirror.model.MenuItem;
import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.TimelineItem;
import com.google.appengine.api.datastore.*;
import com.google.glassware.AuthUtil;
import com.tour.capstoneglass.*;

public class RunWorldServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{
		//EXAMPLE: https://capstoneglassapi.appspot.com/runworld?world_id=XXXXXXXX		
				
		String world_id = req.getParameter("world_id");
				
				if(!world_id.isEmpty() && world_id != null)
				{
					String msg = "You have started the " + world_id + " Tour!<br />\n";
					World w = new World(world_id);
					
					String id ="";
					if (w!=null)
					{
						Mirror mirror = getMirror(req);
						Timeline timeline = mirror.timeline();
						
						TimelineItem worldcard = Card.createWorldCard(w);
						worldcard.setBundleId(w.world_id);
						worldcard.setIsBundleCover(true);
						timeline.insert(worldcard).execute();
						
						
						//initially unlocked locations.
						for (com.tour.capstoneglass.Location l : w.unlocked_locations)
						{
							TimelineItem locationcard = Card.createLocationCard(l);
							locationcard.setBundleId(w.world_id);
							timeline.insert(locationcard).execute();
						}
						
						TimelineItem mapcard = Card.createMapCard(w);
						mapcard.setBundleId(w.world_id);
						timeline.insert(mapcard).execute();
						
						
						
						CheckLocations check  = new CheckLocations(id, w, timeline);
						Thread thread = ThreadManager.createBackgroundThread(check);
							  
						thread.start();
						
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
	//allows access to the Mirror API
	public Mirror getMirror(HttpServletRequest req) throws IOException{
		//get credential of client
		Credential credential = AuthUtil.getCredential(req);
		
		//build access to Mirror API
		return new Mirror.Builder(new UrlFetchTransport(), new JacksonFactory(), credential)
		.setApplicationName("Hello Glass!").build();
		
	}
	
	

	private class CheckLocations implements Runnable{
		String id;
		DatastoreService dss;
		Key k;
		double currLat;
		double currLon;
		String timeStamp;
		int radius = 5;
		World w; 
		Timeline timeline;
		
		CheckLocations(String id, World w, Timeline timeline){
			this.id = "capstoneglass2014@gmail.com";
			dss  = DatastoreServiceFactory.getDatastoreService();
			k = KeyFactory.createKey("Location Update", this.id);
			this.w = w;
			this.timeline = timeline;
		}
		
		private boolean isNearby(double currLat, double currLon,com.tour.capstoneglass.Location location) {
			boolean isNearby;
			double latitude = location.latitude;
			double longitude = location.longitude;
			double distance = Distance.getDistance(currLat, currLon, latitude,longitude);
			if(distance <= radius){
			isNearby = true;
			}
			else{
			isNearby = false;
			}
			return isNearby;
			}
		
		

		
		@Override
		public void run() {
			// TODO Auto-generated method 
			try {
			      while (true) {
			    	  Entity e = dss.get(k);
			    	  currLat = (double)e.getProperty("Latitude");
			    	  currLon = (double)e.getProperty("Longitude");
			    	  timeStamp = (String)e.getProperty("Timestamp");
			    	  pushLiveCard(timeStamp, currLat, currLon, timeline);
			    	  
//			        for(Location loc : w.unlocked_locations){
//			        	isNearby(currLat, currLon, loc);
//			        }
			        Thread.sleep(5000);
			      }
			    } catch (InterruptedException ex) {
			      throw new RuntimeException("Interrupted in loop:", ex);
			    }catch (EntityNotFoundException ex){
			    	 throw new RuntimeException("Entity Not Found:", ex);
			    }
		}
		
		public void pushLiveCard(String time, double latitude, double longitude, Timeline timeline){
			String html = "<article><section><p>"+
					"Timestamp: " + time +
					"</p><p>"+
					"Lat: " + String.valueOf(latitude) +
					"</p><p>"+
					"Long: " + String.valueOf(longitude) +
					"</p></section></article>";
			
			//create timeline card
			TimelineItem timelineItem = new TimelineItem()
					.setHtml(html)
					.setDisplayTime(new DateTime(new Date()))
					.setNotification(new NotificationConfig().setLevel("Default"));
			
			List<MenuItem> menuItemList = new ArrayList<MenuItem>();
			//allows users to pin the card to the left of the timeline
			//starts default navigation application for glass
			menuItemList.add(new MenuItem().setAction("DELETE"));
			
			
			//sets menu item array to card
			timelineItem.setMenuItems(menuItemList);
			
			try{
			timeline.insert(timelineItem).execute();
			}catch(Exception ex){
				
			}
		}
		
	}
}
