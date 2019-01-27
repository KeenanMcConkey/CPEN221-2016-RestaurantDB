package ca.ece.ubc.cpen221.mp5;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.javafx.scene.paint.GradientUtils.Parser;
import com.sun.org.apache.bcel.internal.classfile.Method;

import ca.ece.ubc.cpen221.mp5.staff.YelpData;
import ca.ece.ubc.cpen221.mp5.staff.YelpRestaurant;
import ca.ece.ubc.cpen221.mp5.staff.YelpReview;
import ca.ece.ubc.cpen221.mp5.staff.YelpUser;
import ca.ece.ubc.cpen221.mp5.statlearning.Algorithms;
import jdk.nashorn.internal.runtime.ParserException;

/**
 * This class represents the Restaurant Database. RestaurantDB loads the JSON
 * data from given files and stores that data in memory to be accessed by 
 * other classes, specifically the RestaurantDBSever.
 */
public class RestaurantDB implements YelpDatabase {
	
 	 // Internal Representation:
     //	 A Restaurant Database is represented by three Maps. Each Map stores
	 //  key-value pairs that map Name strings to YelpData objects. These YelpData
	 //  objects are processed from JSON files when the RestaurantDB object is created.
	 // 
	 //  These three maps are:
	 //	  1. A map for YelpRestaurants
	 //	  2. A map for YelpReviews
	 //   3. A map for YelpUsers
	 //
	 // Representation Invariant:
	 //	 1. JSON files must exist.
	 //	 2. All internal maps must not be null.
	 //  3. Internal maps cannot contain null keys or values.
	 //
	 // Abstraction Function:
	 //	 [3 Maps] -> [A Restaurant Database]
	
	private final HashMap<String, YelpRestaurant> internal_restaurants;
	private final HashMap<String, YelpReview> internal_reviews;
	private final HashMap<String, YelpUser> internal_users;
	
	// Maps reviews to corresponding users and restaurants
	private final HashMap<YelpRestaurant, HashSet<YelpReview>> restaurant_reviews;
	private final HashMap<YelpUser, HashSet<YelpReview>> user_reviews;
	
	/**
	 * Create a database from the Yelp dataset given the names of three files:
	 * <ul>
	 * <li>One that contains data about the restaurants;</li>
	 * <li>One that contains reviews of the restaurants;</li>
	 * <li>One that contains information about the users that submitted reviews.
	 * </li>
	 * </ul>
	 * The files contain data in JSON format.
	 *
	 * @param restaurantJSONfilename
	 *            the filename for the restaurant data
	 * @param reviewsJSONfilename
	 *            the filename for the reviews
	 * @param usersJSONfilename
	 *            the filename for the users
	 */
	public RestaurantDB(String restaurantJSONfilename, String reviewsJSONfilename, String usersJSONfilename) {
		System.out.println("Initializing Restaurant Database...");
		System.out.print("Reading JSON data...");
		internal_restaurants = readJSONRestaurants(restaurantJSONfilename);
		internal_reviews = readJSONReviews(reviewsJSONfilename);
		internal_users = readJSONUsers(usersJSONfilename);
		System.out.println(" Done");
		System.out.print("Mapping reviews to restaurants...");
		restaurant_reviews = mapReviewsToRestaurants(internal_restaurants, internal_reviews);
		System.out.println(" Done");
		System.out.print("Mapping reviews to users...");
		user_reviews = mapReviewsToUsers(internal_users, internal_reviews);
		System.out.println(" Done");
	}
	
	/**
	 * Read JSON data file for restaurants into a map. Creates a new 
	 * YelpRestaurant for each restaurant processed in the data.
	 * 
	 * @param JSONfilename name of the JSON formatted data file to process
	 * @return the mapped restaurant names to YelpRestaurant objects
	 */
	private static HashMap<String, YelpRestaurant> readJSONRestaurants(String JSONfilename) {
		HashMap<String, YelpRestaurant> mRestaurants = new HashMap<String, YelpRestaurant>();
		try {
			Stream<String> stream = Files.lines(Paths.get(JSONfilename));
			stream.map(RestaurantDB::getYelpRestaurant)
					.forEach(yelpRestaurant -> mRestaurants.put(yelpRestaurant.getID(), yelpRestaurant));
			stream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return mRestaurants;
	}
	
	/**
	 * Return a new YelpRestaurant created from the given text data. Parses
	 * through the text and creates a YelpRestaurant based on the data fields.
	 * 
	 * @param jText the JSON formatted data for one object in text form
	 * @return a new YelpRestaurant created from the JSON text
	 */
	private static YelpRestaurant getYelpRestaurant(String jText) {
		YelpRestaurant restaurant = new YelpRestaurant();	
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(jText);
			JSONObject jsonObject = (JSONObject) obj;
			
			restaurant.setOpen((Boolean) jsonObject.get("open"));
			restaurant.setUrl((String) jsonObject.get("url"));
			restaurant.setLongitude((Double) jsonObject.get("longitude"));
			restaurant.setNeighborhoods(formatJSONstrings((JSONArray) jsonObject.get("neighborhoods")));
			restaurant.setID((String) jsonObject.get("business_id"));
			restaurant.setName((String) jsonObject.get("name"));
			restaurant.setCategories(formatJSONstrings((JSONArray) jsonObject.get("categories")));
			restaurant.setState((String) jsonObject.get("state"));
			restaurant.setType((String) jsonObject.get("type"));
			restaurant.setStars((Double) jsonObject.get("stars"));
			restaurant.setCity((String) jsonObject.get("city"));
			restaurant.setFull_address((String) jsonObject.get("full_address"));
			restaurant.setReview_count((Long) jsonObject.get("review_count"));
			restaurant.setPhoto_url((String) jsonObject.get("photo_url"));
			restaurant.setSchools(formatJSONstrings((JSONArray) jsonObject.get("schools")));
			restaurant.setLatitude((Double) jsonObject.get("latitude"));
			restaurant.setPrice((Long) jsonObject.get("price"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return restaurant;
	}
	
	/**
	 * Read JSON data file for reviews into a map. Creates a new 
	 * YelpReview for each review processed in the data.
	 * 
	 * @param JSONfilename name of the JSON formatted data file to process
	 * @return the mapped review names to YelpReview objects
	 */
	private HashMap<String, YelpReview> readJSONReviews(String JSONfilename) {
		HashMap<String, YelpReview> mReviews = new HashMap<String, YelpReview>();
		try {
			Stream<String> stream = Files.lines(Paths.get(JSONfilename));
			stream.map(RestaurantDB::getYelpReview)
					.forEach(yelpReview -> mReviews.put(yelpReview.getID(), yelpReview));	
			stream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return mReviews;
	}
	
	/**
	 * Return a new YelpReview created from the given text data. Parses
	 * through the text and creates a YelpReview based on the data fields.
	 * 
	 * @param jText the JSON formatted data for one object in text form
	 * @return a new YelpReview created from the JSON text
	 */
	private static YelpReview getYelpReview(String jText) {
		YelpReview review = new YelpReview();
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(jText);
			JSONObject jsonObject = (JSONObject) obj;
			
			review.setType((String) jsonObject.get("type"));
			review.setBusiness_id((String) jsonObject.get("business_id"));
			review.setID((String) jsonObject.get("review_id"));
			review.setText((String) jsonObject.get("text"));
			review.setStars((Long) jsonObject.get("stars"));
			review.setUser_id((String) jsonObject.get("user_id"));
			review.setDate((String) jsonObject.get("date"));
			review.setVotes(formatVotes((JSONObject) jsonObject.get("votes")));
		} catch(ParseException e) {
			e.printStackTrace();
		}
		return review;
	}
	
	/**
	 * Read JSON data file for restaurants into a map. Creates a new 
	 * YelpUser for each user processed in the data.
	 * 
	 * @param JSONfilename name of the JSON formatted data file to process
	 * @return the mapped review names to YelpReview objects
	 */
	private HashMap<String, YelpUser> readJSONUsers(String JSONfilename) {
		HashMap<String, YelpUser> mUsers = new HashMap<String, YelpUser>();	
		try {
			Stream<String> stream = Files.lines(Paths.get(JSONfilename));
			stream.map(RestaurantDB::getYelpUser)
					.forEach(yelpUser -> mUsers.put(yelpUser.getID(), yelpUser));	
			stream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return mUsers;
	}
	
	/**
	 * Return a new YelpUsercreated from the given text data. Parses
	 * through the text and creates a YelpUser based on the data fields.
	 * 
	 * @param jText the JSON formatted data for one object in text form
	 * @return a new YelpUser created from the JSON text
	 */
	private static YelpUser getYelpUser(String jText) {
		YelpUser user = new YelpUser();
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(jText);
			JSONObject jsonObject = (JSONObject) obj;
			
			user.setUrl((String) jsonObject.get("url"));
			user.setReview_count((Long) jsonObject.get("review_count"));
			user.setType((String) jsonObject.get("type"));
			user.setID((String) jsonObject.get("user_id"));
			user.setName((String) jsonObject.get("name"));
			user.setAverage_stars((Double) jsonObject.get("average_stars"));
			user.setVotes(formatVotes((JSONObject) jsonObject.get("votes")));
		} catch(ParseException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * Format a JSONArray into a strings.
	 * 
	 * @param jsonArr a JSONArray to read
	 * @return that JSONArray in string form
	 */
	private static ArrayList<String> formatJSONstrings(JSONArray jsonArr) {
		ArrayList<String> strs = new ArrayList<String>();
		Iterator<String> itr = jsonArr.iterator();
		
		while(itr.hasNext()) {
			strs.add(itr.next());
		}
		return strs;
	}
	
	/**
	 * Format the votes field of a JSONObject into a mapped form.
	 * 
	 * @param votesObject the JSONObject to get vote data from
	 * @return that vote data in mapped form
	 */
	private static HashMap<String, Long> formatVotes(JSONObject votesObject) {
		HashMap<String, Long> votes = new HashMap<String, Long>();
		votes.put("cool", (Long) votesObject.get("cool"));
		votes.put("useful", (Long) votesObject.get("useful"));
		votes.put("funny", (Long) votesObject.get("funny"));
		return votes;
	}
	
	/**
	 * Map the all the reviews stored in the database to the restaurants they
	 * are associated with and returns that map.
	 * 
	 * @param restaurants the YelpRestaurants in the database
	 * @param reviews the YelpReviews in the database
	 * @return the given reviews mapped to their corresponding restaurants
	 */
	private HashMap<YelpRestaurant, HashSet<YelpReview>> 
		mapReviewsToRestaurants(HashMap<String, YelpRestaurant> restaurants, HashMap<String, YelpReview> reviews) {
		
		HashMap<YelpRestaurant, HashSet<YelpReview>> mappedReviews = new HashMap<YelpRestaurant, HashSet<YelpReview>>();
		
		for (Map.Entry<String, YelpRestaurant> restaurantEntry : restaurants.entrySet()) { // Put a new list of reviews
			mappedReviews.put(restaurantEntry.getValue(), new HashSet<YelpReview>());
		}
		for (Map.Entry<String, YelpReview> reviewEntry : reviews.entrySet()) {
			YelpReview review = reviewEntry.getValue();
			YelpRestaurant restaurant = restaurants.get(reviewEntry.getValue().getBusiness_id());
			mappedReviews.get(restaurant).add(review);
		}
		return mappedReviews;
	}

	/**
	 * Map the all the reviews stored in the database to the users they
	 * are associated with and returns that map.
	 * 
	 * @param restaurants the YelpUsers in the database
	 * @param reviews the YelpReviews in the database
	 * @return the given users mapped to their corresponding restaurants
	 */
	private HashMap<YelpUser, HashSet<YelpReview>> 
		mapReviewsToUsers(HashMap<String, YelpUser> users, HashMap<String, YelpReview> reviews) {
	
		HashMap<YelpUser, HashSet<YelpReview>> mappedReviews = new HashMap<YelpUser, HashSet<YelpReview>>();
		
		for (Map.Entry<String, YelpUser> userEntry : users.entrySet()) { // Put a new list of reviews
			mappedReviews.put(userEntry.getValue(), new HashSet<YelpReview>());
		}
		for (Map.Entry<String, YelpReview> reviewEntry : reviews.entrySet()) {
			YelpReview review = reviewEntry.getValue();
			YelpUser user = users.get(reviewEntry.getValue().getUser_id());
			mappedReviews.get(user).add(review);
		}
		return mappedReviews;
	}
	
	@Override
	public HashMap<String, YelpRestaurant> getRestaurantMap() {
		HashMap<String, YelpRestaurant> copy = new HashMap<String, YelpRestaurant>();
		copy.putAll(internal_restaurants);
		return copy;
 	}
	
	@Override
	public HashMap<String, YelpReview> getReviewMap() {
		HashMap<String, YelpReview> copy = new HashMap<String, YelpReview>();
		copy.putAll(internal_reviews);
		return copy;
	}
	
	@Override
	public HashMap<String, YelpUser> getUserMap() {
		HashMap<String, YelpUser> copy = new HashMap<String, YelpUser>();
		copy.putAll(internal_users);
		return copy;
	}
	
	/**
	 * Returns a copy of the map of restaurant to review map stored in this 
	 * database
	 * 
	 * @return copied restaurant to review map
	 */
	public HashMap<YelpRestaurant, HashSet<YelpReview>> getRestaurantReviews() {
		HashMap<YelpRestaurant, HashSet<YelpReview>> copy = new HashMap<YelpRestaurant, HashSet<YelpReview>> ();
		copy.putAll(restaurant_reviews);
		return copy;
	}
	
	/**
	 * Returns a copy of the map of user to review map stored in this 
	 * database
	 * 
	 * @return copied user to review map
	 */
	public HashMap<YelpUser, HashSet<YelpReview>> getUserReviews() {
		HashMap<YelpUser, HashSet<YelpReview>> copy = new HashMap<YelpUser, HashSet<YelpReview>> ();
		copy.putAll(user_reviews);
		return copy;
	}
	
	@Override
	public boolean addUser(String userName) {
		if (! internal_users.containsKey(userName)) {
			YelpUser user = new YelpUser();
			String randID = Integer.toString(new Random().nextInt(1000));
			user.setUrl("http://www.yelp.com/user_details?userid="+randID);
			user.setReview_count(0);
			user.setType("user");
			user.setID(randID);
			user.setName(userName);
			user.setAverage_stars(0.0);
			user.setVotes(new HashMap<String, Long>());
			internal_users.put(userName, user);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean addRestaurant(String restaurantName, Double longitude, Double latitude) {
		YelpRestaurant restaurant = new YelpRestaurant();
		String randID = Integer.toString(new Random().nextInt(1000));
		restaurant.setOpen(true);
		restaurant.setUrl("http://www.yelp.com/biz/"+randID);
		restaurant.setLongitude(longitude);
		restaurant.setNeighborhoods(new ArrayList<String>());
		restaurant.setID(randID);
		restaurant.setName(restaurantName);
		restaurant.setCategories(new ArrayList<String>());
		restaurant.setState("");
		restaurant.setType("business");
		restaurant.setStars(0);
		restaurant.setCity("");
		restaurant.setFull_address("");
		restaurant.setReview_count(0);
		restaurant.setPhoto_url("");
		restaurant.setSchools(new ArrayList<String>());
		restaurant.setLatitude(latitude);
		restaurant.setPrice(0);
		internal_restaurants.put(restaurantName, restaurant);
		return true;
	}
	
	@Override
	public boolean addReview(String reviewName, String userName, String restaurantName) {
		YelpReview review = new YelpReview();
		String randID = Integer.toString(new Random().nextInt(1000));
		review.setType("review");
		review.setBusiness_id(restaurantName);
		review.setVotes(new HashMap<String, Long>());
		review.setID(randID);
		review.setText("");
		review.setStars(0);
		review.setUser_id(userName);
		review.setDate("");
		internal_reviews.put(reviewName, review);
		return true;
	}
	
}
