# Unified Social Profile example

This is a simple example that shows how you can use the [CloudRail Unified Social Profile API](https://cloudrail.com/integrations/interfaces/Profile;platformId=Java) to integrate the Profile services of different social setworks in one application. This informatin could also be used to create web services using a social login feature.

## Prerequisites
You need to have developer credentials for the services you want to use. Instructions on how they can be acquired can be found on our [Unified Social Profile API site](https://cloudrail.com/integrations/interfaces/Profile). You also need a CloudRail API key that you can [get fro free here](https://cloudrail.com/signup).

Find the following piece of code in your *Main.java* file, and enter your developer credentials and CloudRail App Key. Also change the port if needed.

```java
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
```

## Running the Program
The program requires the name of the Service you want to use as command-line argument. It then logs in to this service and displays different profile information.