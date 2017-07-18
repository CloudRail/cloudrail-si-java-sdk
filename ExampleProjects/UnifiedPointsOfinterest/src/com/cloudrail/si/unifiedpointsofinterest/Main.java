package com.cloudrail.si.unifiedpointsofinterest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.interfaces.PointsOfInterest;
import com.cloudrail.si.services.Foursquare;
import com.cloudrail.si.services.GooglePlaces;
import com.cloudrail.si.services.Yelp;
import com.cloudrail.si.types.Location;
import com.cloudrail.si.types.POI;

public class Main {

	public static void main(String[] args) {
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

		List<PointsOfInterest> services = new ArrayList<PointsOfInterest>();
		List<String> serviceNames = new ArrayList<String>();
		
		serviceNames.add("foursquare  ");
		serviceNames.add("googleplaces");
		serviceNames.add("yelp        ");

		services.add(foursquare);
		services.add(googleplaces);
		services.add(yelp);
		
		List<String> categories = new ArrayList<String>();
		categories.add("restaurant");
		
		List<POIWrapper> allPois = new ArrayList<POIWrapper>();

		for (int i = 0; i < services.size(); i++) {
			List<POI> pois = services.get(i).getNearbyPOIs(lat, lng, 2500L, "", categories);
			for (POI poi : pois) {
				Location loc = poi.getLocation();
				allPois.add(new POIWrapper(poi, distFrom(lat, lng, loc.getLatitude(), loc.getLongitude()), serviceNames.get(i)));
			}
		}
		allPois.sort(new Comparator<POIWrapper>() {
			@Override
			public int compare(POIWrapper p1, POIWrapper p2) {
				return p1.getDist() - p2.getDist();
			}
		});
		
		for (POIWrapper p : allPois) {
			System.out.println(p.getDist() + "m\t  " + p.getService() + "   " + p.getPoi().getName());
		}
	}

	static int distFrom(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371.0;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		return Math.toIntExact(Math.round(dist * 1000));
	}
}