# Unified Payment example
This is an example that shows how you can use the [CloudRail Unified Payment API](https://cloudrail.com/integrations/interfaces/Payment;platformId=Java) to integrate different Online Payment services in one application. It is a simple command-line program that makes it possible to list charges and refunds and to add and refund charges.

## Prerequisites

You need to have developer credentials for the services you want to use. Instructions on how they can be acquired can be found on our [Unified Payment API site](https://cloudrail.com/integrations/interfaces/Payment;platformId=Java). You also need a CloudRail API key that you can [get fro free here](https://cloudrail.com/signup).

Find the following piece of code in your *Main.java* file, and enter your developer credentials and CloudRail App Key.

```java
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
```

## Running the Program

Upon starting the program, it will output all possible command-line commands. You can show this help again at any time by entering "help". you can list charges by entering "charges" and get a charge's refunds by entering "refunds [chargeId]". Calling "newCharge [amount] [currency]" will create a new charge (currency has to be a 3-letter currency code). Calling "refund [chargeId] will fully refund the charge, while calling "refund [chargeId] [amount]" will only refund the specified amount.