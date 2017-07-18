package com.cloudrail.si.unifiedsmssending;

import java.util.LinkedList;
import java.util.List;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.interfaces.SMS;
import com.cloudrail.si.services.Nexmo;
import com.cloudrail.si.services.Twilio;

public class Main {

	public static void main(String[] args) {
		CloudRail.setAppKey("[Your CloudRail Key]");
		
		List<SMS> services = new LinkedList<SMS>();

		Nexmo nexmo = new Nexmo(null, "[Nexmo API Key]", "[Nexmo API Secret]");
		services.add(nexmo);

		Twilio twilio = new Twilio(null, "[Twilio Account SID]", "[Twilio Auth Token]");
		services.add(twilio);

		for (SMS service : services) {
			try {
				service.sendSMS("[Sending Number]", "[Receiving Number]", "Hello from " + service.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
