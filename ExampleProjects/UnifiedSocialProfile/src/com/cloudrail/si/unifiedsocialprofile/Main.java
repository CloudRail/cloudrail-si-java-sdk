package com.cloudrail.si.unifiedsocialprofile;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.interfaces.Profile;
import com.cloudrail.si.servicecode.commands.awaitCodeRedirect.LocalReceiver;
import com.cloudrail.si.services.Facebook;
import com.cloudrail.si.services.GitHub;
import com.cloudrail.si.services.GooglePlus;
import com.cloudrail.si.services.Heroku;
import com.cloudrail.si.services.Instagram;
import com.cloudrail.si.services.LinkedIn;
import com.cloudrail.si.services.MicrosoftLive;
import com.cloudrail.si.services.ProductHunt;
import com.cloudrail.si.services.Slack;
import com.cloudrail.si.services.Twitter;
import com.cloudrail.si.services.Yahoo;
import com.cloudrail.si.types.DateOfBirth;

public class Main {

	public static void main(String[] args) {
		Profile service = initService(args[0]);
		
		service.login();
		String name = service.getFullName();
		String mail = service.getEmail();
		DateOfBirth dob = service.getDateOfBirth();
		String dobString = dob.getDay() + "." + dob.getMonth() + "." + dob.getYear();
		String id = service.getIdentifier();
		
		System.out.println("Logged in to " + args[0]);
		System.out.println("logged in as " + name + " (email: " + mail + "), born " + dobString);
		System.out.println("unique identifier (can be used for social login): " + id);
	}
	
	
	
	
	
	private static Profile initService(String serviceName) {
		CloudRail.setAppKey("[Your CloudRail Key]");
		int port = 8082;
		String redirectUri = "http://localhost:" + port + "/";
		
		Profile service = null;

		switch(serviceName.toLowerCase()) {
		    case "facebook":
		    		service = new Facebook(
		    			    new LocalReceiver(port),
		    			    "[Facebook Client Identifier]",
		    			    "[Facebook Client Secret]",
		    			    redirectUri,
		    			    "someState"
		    			);
		    		break;
		    case "github":
		    		service = new GitHub(
		    			    new LocalReceiver(port),
		    			    "[GitHub Client Identifier]",
		    			    "[GitHub Client Secret]",
		    			    redirectUri,
		    			    "someState"
		    			);
		    		break;
		    case "googleplus": 
		    		service = new GooglePlus(
		    			    new LocalReceiver(port),
		    			    "[Google Plus Client Identifier]",
		    			    "[Google Plus Client Secret]",
		    			    redirectUri,
		    			    "someState"
		    			);
		    		break;
		    case "heroku":
		    		service = new Heroku(
		    			    new LocalReceiver(port),
		    			    "[Heroku Client Identifier]",
		    			    "[Heroku Client Secret]",
		    			    redirectUri,
		    			    "someState"
		    			);
		    		break;
		    case "instagram":
		    		service = new Instagram(
		    			    new LocalReceiver(port),
		    			    "[Instagram Client Identifier]",
		    			    "[Instagram Client Secret]",
		    			    redirectUri,
		    			    "someState"
		    			);
		    		break;
		    case "linkedin":
		    		service = new LinkedIn(
		    			    new LocalReceiver(port),
		    			    "[LinkedIn Client Identifier]",
		    			    "[LinkedIn Client Secret]",
		    			    redirectUri,
		    			    "someState"
		    			);
		    		break;
		    case "producthunt":
		    		service = new ProductHunt(
		    			    new LocalReceiver(port),
		    			    "[Product Hunt Client Identifier]",
		    			    "[Product Hunt Client Secret]",
		    			    redirectUri,
		    			    "someState"
		    			);
		    		break;
		    case "slack":
		    		service = new Slack(
		    			    new LocalReceiver(port),
		    			    "[Slack Client Identifier]",
		    			    "[Slack Client Secret]",
		    			    redirectUri,
		    			    "someState"
		    			);
		    		break;
		    case "twitter":
		    		service = new Twitter(
		    			    new LocalReceiver(port),
		    			    "[Twitter Client Identifier]",
		    			    "[Twitter Client Secret]",
		    			    redirectUri
		    			);
		    		break;
		    case "microsoftlive":
		    		service = new MicrosoftLive(
		    			    new LocalReceiver(port),
		    			    "[Windows Live Client Identifier]",
		    			    "[Windows Live Client Secret]",
		    			    redirectUri,
		    			    "someState"
		    			);
		    		break;
		    case "yahoo":
		    		service = new Yahoo(
		    			    new LocalReceiver(port),
		    			    "[Yahoo Client Identifier]",
		    			    "[Yahoo Client Secret]",
		    			    redirectUri,
		    			    "someState"
		    			);
		    		break;
		}

		return service;
	}
}
