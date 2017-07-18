package com.cloudrail.si.unifiedpayment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.interfaces.Payment;
import com.cloudrail.si.services.PayPal;
import com.cloudrail.si.services.Stripe;
import com.cloudrail.si.types.Address;
import com.cloudrail.si.types.Charge;
import com.cloudrail.si.types.CreditCard;
import com.cloudrail.si.types.Refund;

public class Main {
	private static BufferedReader in;
	private static Payment service;

	public static void main(String[] args) {
		CloudRail.setAppKey("[Your CloudRail Key]");

		PayPal paypal = new PayPal(
		    null,
		    true,
		    "[PayPal Client Identifier]",
		    "[PayPal Client Secret]"
		);

		Stripe stripe = new Stripe(
		    null,
		    "[Stripe Secret Key]"
		);
		
		service = null;
		switch(args[0]) {
		    case "paypal": service = paypal; break;
		    case "stripe": service = stripe; break;
		}
		
		showHelp();
		in = new BufferedReader(new InputStreamReader(System.in));
		getNextUserCommand();
	}

	private static void getNextUserCommand() {
		try {
			List<String> input = new LinkedList<String>(Arrays.asList(in.readLine().split(" ")));
			String cmd = input.remove(0);
			switch (cmd) {
				case "help":
					showHelp();
					break;
				case "charges":
					showCharges();
					break;
				case "refunds":
					showRefunds(input.get(0));
					break;
				case "newCharge":
					createCharge(Long.parseLong(input.get(0)), input.get(1));
					break;
				case "refund":
					refund(input);
					break;
				case "exit":
					System.exit(0);;
				default:
					System.out.println("Unknown command. Try entering \"help\".\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		getNextUserCommand();
	}

	private static void showHelp() {
		  System.out.println("Possible commands:");
		  System.out.println("\"help\" displays this help.");
		  System.out.println("\"charges\" displays a list of charges");
		  System.out.println("\"refunds [chargeId]\" displays a list of refunds for this charge");
		  System.out.println("\"newCharge [amount] [currency]\" creates a new charge");
		  System.out.println("\"refund [chargeId] [amount]\" refunds the specified amount of that charge. Skip [amount] for a full refund.");
		  System.out.println("");
	}

	private static void showCharges() {
		Date date = new Date();
		Long now = date.getTime();
		Long oneYearAgo = now - 1000*3600*24*365;
		
		List<Charge> charges = service.listCharges(oneYearAgo, now, null);
		
		for (Charge c : charges) {
			System.out.println(c.getId() + ":   " + c.getAmount() / 100.0 + c.getCurrency());
		}
		System.out.println("");
	}

	private static void showRefunds(String chargeId) {
		List<Refund> refunds = service.getRefundsForCharge(chargeId);
		for (Refund r : refunds) {
			System.out.println("refunded " + r.getAmount() / 100.0 + r.getCurrency() + " on " + new Date(r.getCreated()*1000).toString());
		}
		if (refunds.size() == 0) {
			System.out.println("no refunds");
		}
		System.out.println("");
	}

	private static void createCharge(long amount, String currency) {
		Address address = new Address();
		CreditCard source = new CreditCard("123", 10L, 2025L, "4242424242424242", "visa", "Jon", "Doe", address);
		service.createCharge(amount, currency, source);
	}

	private static void refund(List<String> input) {
		if (input.size() == 1) {
			service.refundCharge(input.get(0));
			System.out.println("charge refunded\n");
		} else {
			service.partiallyRefundCharge(input.get(0), Long.parseLong(input.get(1)));
			System.out.println("charge partially refunded\n");
		}
	}
}
