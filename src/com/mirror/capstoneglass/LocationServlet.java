package com.mirror.capstoneglass;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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
import com.google.glassware.AuthUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
//import java.time.format.DateTimeFormatter;

public class LocationServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
	    
		//get access to Mirror API
				Mirror mirror = getMirror(req);
				Timeline timeline = mirror.timeline();
			
			//request latest location
			Location location = mirror.locations().get("latest").execute();
	  
		
		String s1 = location.getTimestamp().toString();
		
		DateTime test = location.getTimestamp();
		
		Long unixx = test.getValue();
		
		Date time2 = new Date(unixx);
		
		SimpleDateFormat sdf= new SimpleDateFormat("HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("America/Phoenix"));
		String time = sdf.format(time2);
		
		/* Timestamp of the program */
		Date date = new Date();
		Long unix2 = date.getTime();
		String time_now = sdf.format(unix2);
		
		
		
		String html = "<article><section><p>"+
				"Timestamp: " + time +
				"</p><p>"+
				"Timestamp Now: " + time_now +
				"</p><p>"+
				"Lat: " + location.getLatitude() +
				"</p><p>"+
				"Long: " + location.getLongitude() +
				"</p><p>"+
				"Accuracy: " + location.getAccuracy() + " meters" +
				"</p></section></article>";
		
		//create timeline card
		TimelineItem timelineItem = new TimelineItem()
				.setHtml(html)
				.setDisplayTime(new DateTime(new Date()))
				.setNotification(new NotificationConfig().setLevel("Default"));
		
		List<MenuItem> menuItemList = new ArrayList<MenuItem>();
		//allows users to pin the card to the left of the timeline
		//starts default navigation application for glass
		timelineItem.setLocation(new Location()
								.setLatitude(33.4230944)
								.setLongitude(-111.9376497)
								.setAddress("House of Tricks 114 E 7th St Tempe, AZ 85281")
								.setDisplayName("House of Tricks"));
		
		menuItemList.add(new MenuItem().setAction("NAVIGATE"));	
		menuItemList.add(new MenuItem().setAction("DELETE"));
		
		
		//sets menu item array to card
		timelineItem.setMenuItems(menuItemList);
		
		timeline.insert(timelineItem).execute();
		
		//print out results
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().println(
				"<html><head>" +
				"<meta http-equv=\"refresh\"content=\"3;url=/index.html\">" +
				"</head>" +
				"<body>A Location card has been inserted to your timeline!<br><br></body></html>");
		
		

		/*String html2 = "<article><figure><img src='glass://map?w=240&h=360&marker=0;" +
				"33.421756,-111.911729&marker=0;" +
				"33.416168,-111.915763&marker=0;" +
				"33.417851,-111.910742&marker=0;" +
				"33.419356,-111.917179'" +
				"height='360' width='240'></figure><section><div class='text-auto-size'>" +
				"<p>Multiple Markers</p></div></section></article>";*/
		
		String html2 = "<article><figure>" +
				"<h1>List of Worlds</h1>" +
				"</article></figure>";
		
  		TimelineItem timelineItem2 = new TimelineItem()
  				.setHtml(html2)
  				.setDisplayTime(new DateTime(new Date()))
  				.setNotification(new NotificationConfig().setLevel("Default"));
  		List<MenuItem> menuItemList2 = new ArrayList<MenuItem>();
  		menuItemList2.add(new MenuItem().setAction("DELETE"));
  		timelineItem2.setMenuItems(menuItemList2);
  		
  		
  		
  		
  		String html3 = "<article><figure>" +
				"<h1>Location 1</h1>" +
				"</article></figure>";

  		TimelineItem timelineItem3 = new TimelineItem()
  				.setHtml(html3)
  				.setDisplayTime(new DateTime(new Date()))
  				.setNotification(new NotificationConfig().setLevel("Default"));
  		List<MenuItem> menuItemList3 = new ArrayList<MenuItem>();
  		menuItemList3.add(new MenuItem().setAction("DELETE"));
  		timelineItem3.setMenuItems(menuItemList3);
  		
  		String html4 = "<article><figure>" +
				"<h1>Location 2</h1>" +
				"</article></figure>";

  		TimelineItem timelineItem4 = new TimelineItem()
  				.setHtml(html4)
  				.setDisplayTime(new DateTime(new Date()))
  				.setNotification(new NotificationConfig().setLevel("Default"));
  		List<MenuItem> menuItemList4 = new ArrayList<MenuItem>();
  		menuItemList4.add(new MenuItem().setAction("DELETE"));
  		timelineItem4.setMenuItems(menuItemList4);
  		
  		
  	//sets bundled item
  		timelineItem2.setBundleId("2");
  		timelineItem3.setBundleId("2");
  		timelineItem4.setBundleId("2");
  		timelineItem2.setIsBundleCover(true);
  		
  		
  		timeline.insert(timelineItem2).execute();
  		timeline.insert(timelineItem3).execute();
  		timeline.insert(timelineItem4).execute();
	 
	}
	
	//allows access to the Mirror API
		public static Mirror getMirror(HttpServletRequest req) throws IOException{
			//get credential of client
			Credential credential = AuthUtil.getCredential(req);
			
			//build access to Mirror API
			return new Mirror.Builder(new UrlFetchTransport(), new JacksonFactory(), credential)
			.setApplicationName("Hello Glass2!").build();
			
		}
}
