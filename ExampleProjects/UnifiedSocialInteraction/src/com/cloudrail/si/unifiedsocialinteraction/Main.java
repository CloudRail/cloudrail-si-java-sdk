package com.cloudrail.si.unifiedsocialinteraction;

import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.interfaces.Social;
import com.cloudrail.si.servicecode.commands.awaitCodeRedirect.LocalReceiver;
import com.cloudrail.si.services.Facebook;
import com.cloudrail.si.services.FacebookPage;
import com.cloudrail.si.services.Twitter;

public class Main {
	public static void main(String[] args) {
		int port = 8082;
		Social service = null;
		
		CloudRail.setAppKey("[Your CloudRail Key]");
		
		Facebook facebook = new Facebook(
		    new LocalReceiver(port),
		    "[Facebook Client Identifier]",
		    "[Facebook Client Secret]",
		    "http://localhost:" + port + "/",
		    "someState"
		);
		FacebookPage facebookpage = new FacebookPage(
		    new LocalReceiver(port),
		    "[Facebook Page Identifier]",
		    "[Facebook Client Identifier]",
		    "[Facebook Client Secret]",
		    "http://localhost:" + port + "/",
		    "someState"
		);
		Twitter twitter = new Twitter(
		    new LocalReceiver(port),
		    "[Twitter Client Identifier]",
		    "[Twitter Client Secret]",
		    "http://localhost:" + port + "/"
		);
		
		switch(args[0]) {
		    case "facebook": service = facebook; break;
		    case "facebookpage": service = facebookpage; break;
		    case "twitter": service = twitter; break;
		}

		service.login();
		String update = "CloudRail is awesome!";
		if (args.length > 1) {
			List<String> argsList = new LinkedList<String>(Arrays.asList(args));
			argsList.remove(0);
			update = String.join(" ", argsList);
		}
				
		service.postUpdate(update);
	}
}
