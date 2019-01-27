package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ca.ece.ubc.cpen221.feature_functions.FeatureFunction;
import ca.ece.ubc.cpen221.feature_functions.LatitudeFeature;
import ca.ece.ubc.cpen221.feature_functions.LongitudeFeature;
import ca.ece.ubc.cpen221.feature_functions.MeanRatingFeature;
import ca.ece.ubc.cpen221.feature_functions.PriceScaleFeature;
import ca.ece.ubc.cpen221.mp5.*;
import ca.ece.ubc.cpen221.mp5.least_squares_regression.LeastSquaresRegression;
import ca.ece.ubc.cpen221.mp5.least_squares_regression.Regression;
import ca.ece.ubc.cpen221.mp5.staff.Centroid;
import ca.ece.ubc.cpen221.mp5.staff.YelpRestaurant;
import ca.ece.ubc.cpen221.mp5.staff.YelpReview;
import ca.ece.ubc.cpen221.mp5.staff.YelpUser;

public class Algorithms {

	/**
	 * Returns the minimum longitude value as double from a set of restaurants.
	 * Requires that the input is not null.
	 * 
	 * @param internal_restaurants
	 *            a map from business id's (Strings) to the YelpRestaurant
	 *            associated with them.
	 * @return the minimum longitude value.
	 */
	public static double getMinLong(Map<String, YelpRestaurant> internal_restaurants) {

		double max = 0;

		for (String s : internal_restaurants.keySet()) {
			if (max == 0) {
				max = internal_restaurants.get(s).getLongitude();
			} else if (internal_restaurants.get(s).getLongitude() < max) {
				max = internal_restaurants.get(s).getLongitude();
			}

		}

		return max;
	}

	/**
	 * Returns the minimum latitude value as double from a set of restaurants.
	 * Requires that the input is not null.
	 * 
	 * @param internal_restaurants
	 *            a map from business id's (Strings) to the YelpRestaurant
	 *            associated with them.
	 * @return the minimum latitude value.
	 */
	public static double getMinLat(Map<String, YelpRestaurant> internal_restaurants) {
		double min = 0;

		for (String s : internal_restaurants.keySet()) {
			if (min == 0) {
				min = internal_restaurants.get(s).getLatitude();
			} else if (internal_restaurants.get(s).getLatitude() < min) {
				min = internal_restaurants.get(s).getLatitude();
			}

		}

		return min;
	}

	/**
	 * Returns the minimum price scale value as double from a set of
	 * restaurants. Requires that the input is not null.
	 * 
	 * @param internal_restaurants
	 *            a map from business id's (Strings) to the YelpRestaurant
	 *            associated with them.
	 * @return the minimum price scale value.
	 */
	public static double getMinPriceScale(Map<String, YelpRestaurant> internal_restaurants) {
		double min = 0;

		for (String s : internal_restaurants.keySet()) {
			if (min == 0) {
				min = internal_restaurants.get(s).getPrice();
			} else if (internal_restaurants.get(s).getPrice() < min) {
				min = internal_restaurants.get(s).getPrice();
			}

		}

		return min;
	}

	/**
	 * Returns the minimum mean rating value as double from a set of
	 * restaurants. Requires that the input is not null.
	 * 
	 * @param internal_restaurants
	 *            a map from business id's (Strings) to the YelpRestaurant
	 *            associated with them.
	 * @return the minimum mean rating value.
	 */
	public static double getMinMeanRating(RestaurantDB db, Map<String, YelpRestaurant> internal_restaurants) {
		double min = 0;

		for (String s : internal_restaurants.keySet()) {
			if (min == 0) {
				min = internal_restaurants.get(s).getMeanRating(db);
			} else if (internal_restaurants.get(s).getMeanRating(db) < min) {
				min = internal_restaurants.get(s).getMeanRating(db);
			}

		}

		return min;
	}

	/**
	 * Returns the maximum longitude value as double from a set of restaurants.
	 * Requires that the input is not null.
	 * 
	 * @param internal_restaurants
	 *            a map from business id's (Strings) to the YelpRestaurant
	 *            associated with them.
	 * @return the maximum longitude value.
	 */
	public static double getMaxLong(Map<String, YelpRestaurant> internal_restaurants) {

		double max = 0;

		for (String s : internal_restaurants.keySet()) {
			if (max == 0) {
				max = internal_restaurants.get(s).getLongitude();
			} else if (internal_restaurants.get(s).getLongitude() > max) {
				max = internal_restaurants.get(s).getLongitude();

			}

		}

		return max;
	}

	/**
	 * Returns the maximum latitude value as double from a set of restaurants.
	 * Requires that the input is not null.
	 * 
	 * @param internal_restaurants
	 *            a map from business id's (Strings) to the YelpRestaurant
	 *            associated with them.
	 * @return the maximum latitude value.
	 */
	public static double getMaxLat(Map<String, YelpRestaurant> internal_restaurants) {
		double max = 0;

		for (String s : internal_restaurants.keySet()) {
			if (max == 0) {
				max = internal_restaurants.get(s).getLatitude();
			} else if (internal_restaurants.get(s).getLatitude() > max) {
				max = internal_restaurants.get(s).getLatitude();
			}

		}

		return max;
	}

	/**
	 * Returns the maximum price scale value as double from a set of
	 * restaurants. Requires that the input is not null.
	 * 
	 * @param internal_restaurants
	 *            a map from business id's (Strings) to the YelpRestaurant
	 *            associated with them.
	 * @return the maximum price scale value.
	 */
	public static double getMaxPriceScale(Map<String, YelpRestaurant> internal_restaurants) {
		double max = 0;

		for (String s : internal_restaurants.keySet()) {
			if (max == 0) {
				max = internal_restaurants.get(s).getPrice();
			} else if (internal_restaurants.get(s).getPrice() > max) {
				max = internal_restaurants.get(s).getPrice();
			}

		}

		return max;
	}

	/**
	 * Returns the maximum mean rating value as double from a set of
	 * restaurants. Requires that the input is not null.
	 * 
	 * @param internal_restaurants
	 *            a map from business id's (Strings) to the YelpRestaurant
	 *            associated with them.
	 * @return the maximum mean rating value.
	 */
	public static double getMaxMeanRating(RestaurantDB db, Map<String, YelpRestaurant> internal_restaurants) {
		double max = 0;

		for (String s : internal_restaurants.keySet()) {
			if (max == 0) {
				max = internal_restaurants.get(s).getMeanRating(db);
			} else if (internal_restaurants.get(s).getMeanRating(db) > max) {
				max = internal_restaurants.get(s).getMeanRating(db);
			}

		}

		return max;
	}

	/**
	 * Scales the value inputed based on the max values within a range. Requires
	 * that max, min and val all be the same sign (i.e all negative "-" or all
	 * positive "+")
	 * 
	 * @param max
	 *            the max value within the range.
	 * @param min
	 *            the min value within the range.
	 * @param val
	 *            the value that you want to scale.
	 * @return the scaled value as a double.
	 */
	public static double scaleValue(double max, double min, double val) {
		if (val <= 0 && max <= 0 && min <= 0) {
			val = Math.abs(val);
			max = Math.abs(max);
			min = Math.abs(min);
			return (val - max) / (min - max);
		} else
			return (val - min) / (max - min);
	}

	/**
	 * Returns a RandomLongitude value for the restaurant database. The value
	 * returned will be in the range of the max value in the database and the
	 * min value in the database. Requires that db and rn not be null.
	 * 
	 * @param db
	 *            the restaurant database
	 * @param rn
	 *            the random object to be used
	 * @return the random longitude.
	 */
	public static double getRandomLongitude(RestaurantDB db, Random rn) {
		double minimum = getMinLong(db.getRestaurantMap());
		double maximum = getMaxLong(db.getRestaurantMap());
		double n = maximum - minimum + 1;
		double rand = rn.nextInt() % n;
		double randomNum = minimum + rand;
		return randomNum;
	}

	/**
	 * Returns a RandomLongitude value for the restaurant database. The value
	 * returned will be in the range of the max value in the database and the
	 * min value in the database. Requires that db and rn not be null.
	 * 
	 * @param db
	 *            the restaurant database
	 * @param rn
	 *            the random object to be used
	 * @return the random latitude.
	 */
	public static double getRandomLatitude(RestaurantDB db, Random rn) {
		double minimum = getMinLat(db.getRestaurantMap());
		double maximum = getMaxLat(db.getRestaurantMap());
		double n = maximum - minimum + 1;
		double rand = rn.nextInt() % n;
		double randomNum = minimum + rand;

		return randomNum;
	}

	/**
	 * Returns a RandomLongitude value for the restaurant database. The value
	 * returned will be in the range of the max value in the database and the
	 * min value in the database. Requires that db and rn not be null.
	 * 
	 * @param db
	 *            the restaurant database
	 * @param rn
	 *            the random object to be used
	 * @return the random price scale.
	 */
	public static double getRandomPriceScale(RestaurantDB db, Random rn) {

		long n = 4 - 1 + 1;
		long rand = rn.nextInt() % n;
		long randomNum = 1 + rand;

		return randomNum;
	}

	/**
	 * Returns a RandomLongitude value for the restaurant database. The value
	 * returned will be in the range of the max value in the database and the
	 * min value in the database. Requires that db and rn not be null.
	 * 
	 * @param db
	 *            the restaurant database
	 * @param rn
	 *            the random object to be used
	 * @return the random mean rating.
	 */
	public static double getRandomMeanRating(RestaurantDB db, Random rn) {

		double minimum = getMinMeanRating(db, db.getRestaurantMap());
		double maximum = getMaxMeanRating(db, db.getRestaurantMap());
		double n = maximum - minimum + 1;
		double rand = rn.nextInt() % n;
		double randomNum = minimum + rand;

		return randomNum;
	}

	/**
	 * Returns a copy of the inputed set of Centroids. Requires that the input
	 * not be null.
	 * 
	 * @param centroids
	 *            the inputed set of centroids you want to be copied
	 * @return the copy of the inputed set of centroids.
	 */
	public static Set<Centroid> copyCentroidSet(Set<Centroid> centroids) {
		Set<Centroid> newSet = new HashSet<Centroid>();

		for (Centroid c : centroids) {
			newSet.add(c);
		}
		return newSet;
	}

	/**
	 * Gets the Syy value for a user in a database. It does this based on y
	 * being the rating. Requires that the inputs not be null.
	 * 
	 * @param user_id
	 *            the user id.
	 * @param db
	 *            the database.
	 * @param f
	 *            the feature function.
	 * @return the Syy value based on the formula for Syy.
	 */
	private static double getSyyRating(String user_id, RestaurantDB db) {
		Map<String, YelpRestaurant> restaurants_map = db.getRestaurantMap();
		Map<YelpUser, HashSet<YelpReview>> user_reviews = db.getUserReviews();

		double average = db.getUserMap().get(user_id).getAverage_stars();
		double sum = 0;
		for (YelpUser u : user_reviews.keySet()) {
			if (u.getID().equals(user_id)) {
				for (YelpReview review : user_reviews.get(u)) {
					sum = sum + ((review.getStars() - average) * (review.getStars() - average));
				}
			}
		}
		return sum;
	}

	/**
	 * Gets the Sxx value for a user in a database. It does this based on x
	 * being the feature function. Requires that the inputs not be null.
	 * 
	 * @param user_id
	 *            the user id.
	 * @param db
	 *            the database.
	 * @param f
	 *            the feature function.
	 * @return the Sxx value based on the formula for Sxx.
	 */
	private static double getSxxFeatureFunction(String user_id, RestaurantDB db, FeatureFunction f) {
		Map<YelpUser, HashSet<YelpReview>> user_reviews = db.getUserReviews();

		double average = 0;
		for (YelpUser u : user_reviews.keySet()) {
			if (u.getID().equals(user_id)) {
				for (YelpReview review : user_reviews.get(u)) {
					average = average + f.getFeature(db, review.getBusiness_id());
				}
				average = average / user_reviews.get(u).size();
			}
		}
		double sum = 0;
		for (YelpUser u : user_reviews.keySet()) {
			if (u.getID().equals(user_id)) {
				for (YelpReview review : user_reviews.get(u)) {
					sum = sum + ((f.getFeature(db, review.getBusiness_id()) - average)
							* (f.getFeature(db, review.getBusiness_id()) - average));
				}
			}
		}
		return sum;
	}

	/**
	 * Gets the Sxy value for a user in a database. It does this based on y
	 * being rating and x being the feature function. Requires that the inputs
	 * not be null.
	 * 
	 * @param user_id
	 *            the user id.
	 * @param db
	 *            the database.
	 * 
	 * @param f
	 *            the feature function.
	 * @return the Sxy value based on the formula for Sxy.
	 */
	private static double getSxy(String user_id, RestaurantDB db, FeatureFunction f) {
		Map<String, YelpRestaurant> restaurants_map = db.getRestaurantMap();
		Map<YelpUser, HashSet<YelpReview>> user_reviews = db.getUserReviews();

		double average_rating = db.getUserMap().get(user_id).getAverage_stars();
		double average_feature = 0;
		for (YelpUser u : user_reviews.keySet()) {
			if (u.getID().equals(user_id)) {
				for (YelpReview review : user_reviews.get(u)) {
					average_feature = average_feature + f.getFeature(db, review.getBusiness_id());
				}
				average_feature = average_feature / user_reviews.get(u).size();
			}
		}

		double sum = 0;
		for (YelpUser u : user_reviews.keySet()) {
			if (u.getID().equals(user_id)) {
				for (YelpReview review : user_reviews.get(u)) {
					sum = sum + ((f.getFeature(db, review.getBusiness_id()) - average_feature)
							* (review.getStars() - average_rating));
				}
			}
		}
		return sum;
	}

	/**
	 * Gets the average rating for a certain user based on the users past
	 * reviews. Requires that the inputs not be null.
	 * 
	 * @param user_id
	 *            the user id as a string
	 * @param db
	 *            the restaurant database
	 * @return the mean value based on the parameters
	 */
	public static double getMeanY(String user_id, RestaurantDB db) {
		return db.getUserMap().get(user_id).getAverage_stars();
	}

	/**
	 * Gets the mean value given a certain feature function a restaurant
	 * database and a specific user id. Requires that the inputs not be null.
	 * 
	 * @param user_id
	 *            the user id as a string
	 * @param db
	 *            the restaurant database
	 * @param f
	 *            the feature function
	 * @return the mean value based on the parameters
	 */
	public static double getMeanX(String user_id, RestaurantDB db, FeatureFunction f) {
		Map<YelpUser, HashSet<YelpReview>> user_reviews = db.getUserReviews();

		double average_feature = 0;
		for (YelpUser u : user_reviews.keySet()) {
			if (u.getID().equals(user_id)) {
				for (YelpReview review : user_reviews.get(u)) {
					average_feature = average_feature + f.getFeature(db, review.getBusiness_id());
				}
				average_feature = average_feature / user_reviews.get(u).size();
			}
		}
		return average_feature;
	}

	/**
	 * Returns the R squared value as a double given Sxx,Syy and Sxy.
	 * 
	 * @param Sxx
	 *            the Sxx value.
	 * @param Syy
	 *            the Syy value.
	 * @param Sxy
	 *            the Sxy value.
	 * @return the R squared value as a double
	 */
	public static double calculateR2(double Sxx, double Syy, double Sxy) {
		return ((Sxy * Sxy)) / (Sxx * Syy);
	}

	/**
	 * Use k-means clustering to compute k clusters for the restaurants in the
	 * database.
	 *
	 * @param db
	 *            the Restaurant Database
	 * @return a list of the different sets of restaurants that are related to
	 *         each other
	 */
	public static List<Set<YelpRestaurant>> kMeansClustering(int k, RestaurantDB db) {
		if (k <= 0) {
			throw new IllegalArgumentException();
		}
		Set<Centroid> centroids = new HashSet<Centroid>();
		int i = 0;
		// Generates k random centroids

		while (i < k) {
			Random rand = new Random();
			if (centroids.add(new Centroid(getRandomLongitude(db, rand), getRandomLatitude(db, rand),
					getRandomMeanRating(db, rand), getRandomPriceScale(db, rand)))) {
				System.out.println("Centroid added!");
				i++;
			}
		}
		System.out.println(centroids.size());
		Map<String, YelpRestaurant> restaurant_map = db.getRestaurantMap();
		Set<YelpRestaurant> restaurants = new HashSet<YelpRestaurant>();

		for (String s : restaurant_map.keySet()) {
			restaurants.add(restaurant_map.get(s));
		}

		int count = 0;
		Set<Centroid> oldSet = copyCentroidSet(centroids);
		do {
			oldSet = copyCentroidSet(centroids);
			// Clears the set of restaurants part of each centroid
			for (Centroid c : centroids) {
				c.clearRestaurants();
			}
			// iterated through each restaurant
			for (YelpRestaurant r : restaurants) {
				double shortest_dist = 0;
				Centroid closest = new Centroid();
				// System.out.println("Centroid : ");
				// finds the shortest distance to a centroid from the set of
				// centroids
				for (Centroid c : centroids) {
					if (shortest_dist == 0) {
						closest = c;
						shortest_dist = r.compare(db, c);
					} else {
						if (r.compare(db, c) < shortest_dist) {
							// System.out.println("HI");
							closest = c;
							shortest_dist = r.compare(db, c);
						}
					}
				}
				// finds the centroid that is equal to the closest centroid
				// above and add the restaurant to it
				for (Centroid c : centroids) {
					if (c.equals(closest)) {
						c.addRestaurant(r);
					}
				}
				shortest_dist = 0;
			}

			/*
			 * for (YelpRestaurant r : restaurants) { double shortest_distance =
			 * 0; Centroid closest = null; //System.out.println("Restaurant : "
			 * + r.getName()); for (Centroid c : centroids) { if (closest ==
			 * null && shortest_distance == 0) { closest = c; shortest_distance
			 * = r.compare(db,c); continue; } else { if (c.compare(db,r) <
			 * closest.compare(db, r)) { closest = c;
			 * //System.out.println("HI!"); shortest_distance = r.compare(db,
			 * closest); }
			 * 
			 * } //System.out.println("Shortest Distance : " +
			 * shortest_distance); } shortest_distance = 0; for (Centroid c1 :
			 * centroids) { if (closest.equals(c1)) { c1.addRestaurant(r); } } }
			 */

			// changes the centroids based on the restaurants contained in them
			for (Centroid c3 : centroids) {
				c3.findNewCentroidForCurrentRestaurants(db);
			}
			count++;
		} while (count < 20);

		List<Set<YelpRestaurant>> output = new LinkedList<Set<YelpRestaurant>>();

		for (Centroid c4 : centroids) {

			output.add(c4.getRestaurants());

		}
		System.out.println(centroids.size());
		return output;
	}

	/**
	 * Given multiple clusters of restaurants as a List of Sets of
	 * YelpRestaurants convert the restaurants into a JSON file as a string and
	 * return it. Requires that the input not be null.
	 * 
	 * @param clusters
	 *            the clusters of YelpRestaurants that you would like to convert
	 *            to JSON text
	 * @return a string containing all of the JSON objects in their text format
	 */
	public static String convertClustersToJSON(List<Set<YelpRestaurant>> clusters) {
		String output = "";
		for (Set<YelpRestaurant> set : clusters) {
			for (YelpRestaurant r : set) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("open", r.isOpen());
				jsonObject.put("url", r.getUrl());
				jsonObject.put("longitude", r.getLongitude());
				jsonObject.put("neighborhoods", r.getNeighborhoods());
				jsonObject.put("business_id", r.getID());
				jsonObject.put("name", r.getName());
				jsonObject.put("categories", r.getCategories());
				jsonObject.put("state", r.getState());
				jsonObject.put("type", r.getType());
				jsonObject.put("stars", r.getStars());
				jsonObject.put("city", r.getCity());
				jsonObject.put("full_address", r.getFull_address());
				jsonObject.put("review_count", r.getReview_count());
				jsonObject.put("photo_url", r.getPhoto_url());
				jsonObject.put("schools", r.getSchools());
				jsonObject.put("latitude", r.getLatitude());
				jsonObject.put("price", (long) r.getPrice());
				output = output + jsonObject.toJSONString() + "\n";
			}

		}
		return output;

	}

	/**
	 * Return a user rating prediction function
	 * 
	 * @param user_id
	 *            the id of the user we are interested in
	 * @param db
	 *            the database object that represents the yelp dataset
	 * @param featureFunction
	 *            that returns the feature we want to use in making predictions
	 * @return
	 */
	public static LeastSquaresRegression getPredictor(String user_id, RestaurantDB db,
			FeatureFunction featureFunction) {
		double Syy = getSyyRating(user_id, db);
		double Sxx = getSxxFeatureFunction(user_id, db, featureFunction);
		double Sxy = getSxy(user_id, db, featureFunction);
		double mean_x = getMeanX(user_id, db, featureFunction);
		double mean_y = getMeanY(user_id, db);
		return new Regression(Sxx, Syy, Sxy, mean_x, mean_y, user_id, featureFunction);
	}

	/**
	 * Returns the best predictor for a user's rating
	 * 
	 * @param user_id
	 *            the user id for the user we are interested in
	 * @param db
	 *            the database object that represents the yelp dataset
	 * @param featureFunctionList
	 *            is a list of feature functions from which the best is selected
	 * @return the best prediction function
	 */
	public static LeastSquaresRegression getBestPredictor(String user_id, RestaurantDB db,
			List<FeatureFunction> featureFunctionList) {
		Map<FeatureFunction, Double> featureFunctionMap = new HashMap<FeatureFunction, Double>();
		double Sxx = 0;
		double Syy = 0;
		double Sxy = 0;
		double mean_x = 0;
		double mean_y = 0;
		for (FeatureFunction f : featureFunctionList) {
			featureFunctionMap.put(f, calculateR2(getSxxFeatureFunction(user_id, db, f), getSyyRating(user_id, db),
					getSxy(user_id, db, f)));
		}
		double max = 0;
		for (FeatureFunction f : featureFunctionMap.keySet()) {
			if (max == 0) {
				max = featureFunctionMap.get(f);
			} else {
				if (featureFunctionMap.get(f) > max) {
					max = featureFunctionMap.get(f);
				}
			}
		}
		FeatureFunction best = null;
		for (FeatureFunction f : featureFunctionMap.keySet()) {
			if (featureFunctionMap.get(f) == max) {
				best = f;
				Syy = getSyyRating(user_id, db);
				Sxx = getSxxFeatureFunction(user_id, db, f);
				Sxy = getSxy(user_id, db, f);
				mean_x = getMeanX(user_id, db, f);
				mean_y = getMeanY(user_id, db);
			}
		}
		return new Regression(Sxx, Syy, Sxy, mean_x, mean_y, user_id, best);
	}
}
