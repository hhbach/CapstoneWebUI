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
import com.google.glassware.AuthUtil;

public class HelloWorldServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{
		//retrieves the name from the url string
		//EXAMPLE: https://capstoneglassapi.appspot.com/helloworldform?custom_name=alex
		//WILL PRINT OUT: Hello Alex!
		
				String custom_name = req.getParameter("custom_name");
				
				if(!custom_name.isEmpty() && custom_name != null)
				{
					String message = "Hello " + custom_name + "!";
					
					//get access to Mirror API
					Mirror mirror = getMirror(req);
					Timeline timeline = mirror.timeline();
					
					//create timeline card
					TimelineItem timelineItem = new TimelineItem()
							.setText("Hello " + custom_name + "!")
							.setDisplayTime(new DateTime(new Date()))
							.setNotification(new NotificationConfig().setLevel("Default"));
					
					timeline.insert(timelineItem).execute();
					
					//print out results
					resp.setContentType("text/html; charset=utf-8");
					resp.getWriter().println(
							"<html><head>" +
							"<meta http-equv=\"refresh\"content=\"3;url=/index.html\">" +
							"</head>" +
							"<body>A card has been inserted to your timeline!<br><br>" +
							"The Text is: <strong>Hello " + custom_name + "!</strong></body></html>");
					
				}
				else
				{
					//print out results
					resp.setContentType("text/html; charset=utf-8");
					resp.getWriter().println(
							"<html><head>" +
							"<meta http-equv=\"refresh\"content=\"3;url=/index.html\">" +
							"</head>" +
							"<body>The url parameter 'custom_name' is Empty. Please Try again.<br></body></html>");
				
				}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//retrieves the string text from a FORM on THIS page (doesn't exist right now)
		String custom_name = req.getParameter("custom_name");
		
		if(!custom_name.isEmpty() && custom_name != null){
			String message = "Hello " + custom_name + "!";
			
			//get access to Mirror API
			Mirror mirror = getMirror(req);
			Timeline timeline = mirror.timeline();
			
			//create timeline card
			TimelineItem timelineItem = new TimelineItem()
					.setText("Hello World!")
					.setDisplayTime(new DateTime(new Date()))
					.setNotification(new NotificationConfig().setLevel("Default"));
			
			timeline.insert(timelineItem).execute();
			
			//print out results
			resp.setContentType("text/plain; charset=utf-8");
			resp.getWriter().println(
					"<html><head>" +
					"<meta http-equv=\"refresh\"content=\"3;url=/index.html\">" +
					"</head>" +
					"<body>A card has been inserted to your timeline2.<br><br>" +
					"The Text is: <strong>Hello World </strong>.</body></html>");
			
		}else{
			//print out results
			resp.setContentType("text/plain; charset=utf-8");
			resp.getWriter().println(
					"<html><head>" +
					"<meta http-equv=\"refresh\"content=\"3;url=/index.html\">" +
					"</head>" +
					"<body>The form is Empty. Please Try again.<br></body></html>");
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
