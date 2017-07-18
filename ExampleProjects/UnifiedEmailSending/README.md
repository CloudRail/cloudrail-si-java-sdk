# Unified Email Sending example

This is a simple example that shows how you can use the [CloudRail Unified Email Sending API](https://cloudrail.com/integrations/interfaces/Email;platformId=Java) to integrate different Email Sending services in one application.

## Prerequisites

You need to have developer credentials for the services you want to use. Instructions on how they can be acquired can be found on our [Unified Email Sending API site](https://cloudrail.com/integrations/interfaces/Email;platformId=Java). You also need a CloudRail API key that you can [get fro free here](https://cloudrail.com/signup).

Find the following piece of code in your *Main.java* file, enter your developer credentials and CloudRail App Key and sending and receiving Email addresses.

```java
CloudRail.setAppKey("[Your CloudRail Key]");

List<Email> services = new LinkedList<Email>();

MailJet mailjet = new MailJet(null, "[Mailjet API Key]", "[Mailjet API Private Key]");
SendGrid sendgrid = new SendGrid(null, "[SendGrid API Key]");

List<String> recipients = new ArrayList<String>();
recipients.add("[recipient address]");
String sender = "[sender address]";
```