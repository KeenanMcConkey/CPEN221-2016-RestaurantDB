package ca.ece.ubc.cpen221.feature_functions;

import java.util.Map;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.staff.YelpRestaurant;

public class MeanRatingFeature implements FeatureFunction {


	@Override
	public double getFeature(RestaurantDB rdb, String restaurant_id) {
		Map<String,YelpRestaurant> restaurants_map = rdb.getRestaurantMap();
		return restaurants_map.get(restaurant_id).getStars();
	}


}
