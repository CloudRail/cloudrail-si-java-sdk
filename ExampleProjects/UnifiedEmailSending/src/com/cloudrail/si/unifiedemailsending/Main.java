package com.cloudrail.si.unifiedemailsending;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.interfaces.Email;
import com.cloudrail.si.services.MailJet;
import com.cloudrail.si.services.SendGrid;

public class Main {

	public static void main(String[] args) {
		CloudRail.setAppKey("[Your CloudRail Key]");
		
		List<Email> services = new LinkedList<Email>();
		
		MailJet mailjet = new MailJet(null, "[Mailjet API Key]", "[Mailjet API Private Key]");
		SendGrid sendgrid = new SendGrid(null, "[SendGrid API Key]");

		List<String> recipients = new ArrayList<String>();
		recipients.add("[recipient address]");
		String sender = "[sender address]";
		
		services.add(mailjet);
		services.add(sendgrid);
		
		for (Email service : services) {
			try {
				service.sendEmail(
						sender,
						"CloudRailTest",
						recipients,
						"CloudRail is awesome",
						"Hi there,\\n\\nGo ahead and try it yourself!",
						"<strong>Hi there,<br/><br/>Go ahead and try it yourself!</strong><br/>Sent with " + service.toString(),
						null,
						null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
