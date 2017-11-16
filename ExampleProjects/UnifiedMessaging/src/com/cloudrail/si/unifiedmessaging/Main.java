package com.cloudrail.si.unifiedmessaging;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.interfaces.Messaging;
import com.cloudrail.si.types.Message;
import com.cloudrail.si.types.MessageButton;
import com.cloudrail.si.types.MessageItem;
import com.cloudrail.si.services.FacebookMessenger;
import com.cloudrail.si.services.Line;
import com.cloudrail.si.services.Telegram;
import com.cloudrail.si.services.Viber;

public class Main {
	static Messaging service;

	public static void main(String[] args) throws IOException {
		CloudRail.setAppKey("[Your CloudRail Key]");

		switch(args[0]) {
		case "facebook":
			service = new FacebookMessenger(null, "[Bot Token]");
			break;
		case "line":
			service = new Line(null, "[Bot Token]");
			break;
		case "telegram":
			service = new Telegram(null, "[Bot Token]", "[Webhook URL]");
			break;
		case "viber":
			service = new Viber(null, "[Bot Token]", "[Webhook URL]", "[Bot Name]");
			break;
		}

		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		server.createContext("/", new MyHandler());
		server.start();
	}

	static class MyHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			try {
				System.out.println("received message");

				String response = "ok";
				t.sendResponseHeaders(200, response.getBytes().length);
				t.getResponseBody().write(response.getBytes());

				new Thread(new Runnable() {
					@Override
					public void run() {
						InputStream is = t.getRequestBody();
						List<Message> messages = service.parseReceivedMessages(is);
						for (Message message : messages) {
							String chat = message.getChatId();
							if (message.getAttachments() != null && message.getAttachments().size() > 0) {
								service.sendMessage(chat, "You send a media message / an attachment");
							} else if (message.getMessageText() != null && message.getMessageText().trim().toLowerCase().equals("send a photo")) {
								service.sendImage(message.getChatId(), "here's an image", "https://webhooks.cloudrail.com/home/ubuntu/server/media/cr_logo.jpg", null, null, null);
							} else if (message.getMessageText() != null && message.getMessageText().trim().toLowerCase().equals("send a video")) {
								service.sendVideo(message.getChatId(), "here's a video", "https://webhooks.cloudrail.com/home/ubuntu/server/media/SmallVideo.mp4", null, null, 0);
							} else if (message.getMessageText() != null && message.getMessageText().trim().toLowerCase().equals("send a file")) {
								service.sendFile(chat, "here's a general file", "https://webhooks.cloudrail.com/home/ubuntu/server/media/cr.pdf", null, null, null, 0);
							} else if (message.getMessageText() != null && message.getMessageText().trim().toLowerCase().equals("give me a choice")) {
								List<MessageButton> buttons1 = new ArrayList<MessageButton>();
								buttons1.add(new MessageButton("Yay!", "postback", null, "Yay!"));
								buttons1.add(new MessageButton("That's cool!", "postback", null, "That's cool!"));
								List<MessageButton> buttons2 = new ArrayList<MessageButton>();
								buttons2.add(new MessageButton("Nay!", "postback", null, "Nay!"));
								buttons2.add(new MessageButton("Why would I do that?", "postback", null, "Why would I do that?"));
								
								List<MessageItem> mItems = new ArrayList<MessageItem>();
								mItems.add(new MessageItem("CloudRail", "Implement all services with one common code", "https://webhooks.cloudrail.com/home/ubuntu/server/media/cr_logo.jpg", buttons1));
								mItems.add(new MessageItem("REST APIs", "Bother with a different API for every service", "https://webhooks.cloudrail.com/home/ubuntu/server/media/code.jpg", buttons2));
								
								service.sendCarousel(chat, mItems);
							} else {
								service.sendMessage(chat, "You send a message at time " + message.getSendAt() + " with ID " + message.getMessageId() + " and content \"" + message.getMessageText() + "\"");
							}
						}
					}
				}).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
