# Unified Social Interaction example

This is a simple example that shows how you can easily post updates to different social networks using the [CloudRail Unified Social Interaction API](https://cloudrail.com/integrations/interfaces/Social;platformId=Java).

## Prerequisites
You need to have developer credentials for the services you want to use. Instructions on how they can be acquired can be found on our [Unified Social Interaction API site](https://cloudrail.com/integrations/interfaces/Social;serviceIds=Facebook%2CFacebookPage%2CTwitter). You also need a CloudRail API key that you can [get fro free here](https://cloudrail.com/signup).

Find the following piece of code in your *Main.java* file, and enter your developer credentials and CloudRail App Key. Change the port if needed.

```java
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
```

## Running the Program
The program command-line arguments. The first argument will be used to determine the used service. Possible inputs are "facebook", "facebookpage" and "twitter". The program than posts a update to this network using the following arguments, or a standard sentence if none are provided.