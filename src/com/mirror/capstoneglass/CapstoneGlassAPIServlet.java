package com.mirror.capstoneglass;

import java.io.IOException;
import java.util.ArrayList;
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
import com.google.api.services.mirror.model.Location;
import com.google.api.services.mirror.model.MenuItem;
import com.google.api.services.mirror.model.NotificationConfig;
import com.google.api.services.mirror.model.TimelineItem;
import com.google.glassware.AuthUtil;

@SuppressWarnings("serial")
public class CapstoneGlassAPIServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		//gets access to Mirror API
		Mirror mirror = getMirror(req);
		
		//get access to the timeline
		Timeline timeline = mirror.timeline();
		
		//create the html layout for the card
		String html = "<article><section><p class=\"text-auto-size\">" +
				"Hello World!<br>" +
				"This is a card created from the Mirror API service." +
				"</p></section></article>";
		
		//create timeline card
		TimelineItem timelineItem = new TimelineItem()
				.setHtml(html)
				.setDisplayTime(new DateTime(new Date()))
				.setNotification(new NotificationConfig().setLevel("Default"));
		
		/**************************************************************************/
		//add menu items on the card
		/*List<MenuItem> menuItemList = new ArrayList<MenuItem>();
		menuItemList.add(new MenuItem().setAction("READ ALOUD"));
		timelineItem.setSpeakableText("This is speakable text");
		//allows users to pin the card to the left of the timeline
		menuItemList.add(new MenuItem().setAction("TOGGLE_PINNED"));
		//starts default navigation application for glass
		timelineItem.setLocation(new Location()
								.setLatitude(33.425716)
								.setLongitude(-111.93284)
								.setAddress("Sun Devil Stadium, East Veterans Way, Tempe, AZ")
								.setDisplayName("Sun Devil Stadium"));
		
		menuItemList.add(new MenuItem().setAction("NAVIGATE"));	
		menuItemList.add(new MenuItem().setAction("DELETE"));
		
		//allows users to send a response back to the server for more processing
		//menuItemList.add(new MenuItem().setAction("REPLY"));	
		
		
		//sets menu item array to card
		//timelineItem.setMenuItems(menuItemList);*/
		/**************************************************************************/
		
		//insert the card to the timeline
		timeline.insert(timelineItem).execute();
		
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().println(
				"<html><head>" +
				"<meta http-equv=\"refresh\"content=\"3;url=/index.html\"/>" +
				"</head>" +
				"<body>A card has been inserted to your timeline1.<br/></body></html>");
		
		
		
		/* Grouping/Stacking Multiple Cards Together (bundle) */
		/*
		 * timelineItem.setBundleId("UniqueBundleID");
		 * timelineItem.setIsBundleCover("true");
		 */
		
		
		
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
