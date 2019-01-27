package ca.ece.ubc.cpen221.feature_functions;

import java.util.*;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.staff.YelpRestaurant;
import ca.ece.ubc.cpen221.mp5.staff.YelpReview;
import ca.ece.ubc.cpen221.mp5.staff.YelpUser;

public class LatitudeFeature implements FeatureFunction {

	
	

	@Override
	public double getFeature(RestaurantDB rdb, String restaurant_id) {
		Map<String,YelpRestaurant> restaurants_map = rdb.getRestaurantMap();
		return restaurants_map.get(restaurant_id).getLatitude();
	}

}
