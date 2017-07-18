# Unified Points Of Interest example
This is a simple example that shows how you can use the [CloudRail Unified Points of Interest API](https://cloudrail.com/integrations/interfaces/PointsOfInterest;platformId=Java) to use different Points Of Interest services in one application.

## Prerequisites

You need to have developer credentials for the services you want to use. Instructions on how they can be acquired can be found on our [Unified Points Of Interest API site](https://cloudrail.com/integrations/interfaces/PointsOfInterest;serviceIds=Foursquare%2CGooglePlaces%2CYelp). You also need a CloudRail API key that you can [get fro free here](https://cloudrail.com/signup).

Find the following piece of code in your *Main.java* file, and enter your developer credentials and CloudRail App Key. Also, change the coordinates if you want.

```java
Double lat = 49.4871663;
Double lng =  8.4640606;

CloudRail.setAppKey("[Your CloudRail Key]");

Foursquare foursquare = new Foursquare(
      null,
      "[Foursquare Client Identifier]",
      "[Foursquare Client Secret]"
  );
GooglePlaces googleplaces = new GooglePlaces(
      null,
      "[Places API Key]"
  );
Yelp yelp = new Yelp(
      null,
      "[Yelp Consumer Key]",
      "[Yelp Consumer Secret]",
      "[Yelp Token]",
      "[Yelp Token Secret]"
  );
```

## Running the Program
From each of the POI services, the program will receive a list of places categorized as restaurant that are within 2500m of the specified coordinates. It then combines these lists into one single list sorted by distance and displays it. Each list entry will contain the name of the Service it originated from.