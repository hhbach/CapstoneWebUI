package com.mirror.capstoneglass;

import java.io.IOException;
import java.util.Date;

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
import com.google.appengine.api.datastore.*;
import com.google.glassware.AuthUtil;
import com.tour.capstoneglass.*;

public class RunWorldServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{
		//retrieves the name from the url string
		//EXAMPLE: https://capstoneglassapi.appspot.com/startworld?world_id=XXXXXXXX
		//WILL PRINT OUT: Hello Alex!
		
				String world_id = req.getParameter("world_id");
				
				if(!world_id.isEmpty() && world_id != null)
				{
					String msg = "You have started the " + world_id + " Tour!<br />\n";
					World w = new World(world_id);
					
					if (w!=null)
					{
						msg += w.toString(true) + "<br>\n";
						
						//initially unlocked locations.
						msg += "Initial Unlocked locations <br>\n";
						for (Location l : w.unlocked_locations)
						{
							msg += l.toString(true) + "<br>\n";
						}
						
						//all locations referenced in world.
						msg += "Initial Unlocked locations <br>\n";
						for (Location l : w.all_locations.values())
						{
							msg += l.toString(true) + "<br>\n";
						}
						
					}
					
					
					
/*					
					//get access to Mirror API
					Mirror mirror = getMirror(req);
					Timeline timeline = mirror.timeline();
					
					//create timeline card
					TimelineItem timelineItem = new TimelineItem()
							.setText("Hello " + custom_name + "!")
							.setDisplayTime(new DateTime(new Date()))
							.setNotification(new NotificationConfig().setLevel("Default"));
					
					timeline.insert(timelineItem).execute();
//*/					
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
}
