package ca.ece.ubc.cpen221.mp5;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for RestaurantDB;
 */
public class RestaurantDBTest {
	
	private static final String RSTS_DEF = "data/restaurants.json";
	private static final String RVWS_DEF = "data/reviews.json";
	private static final String USRS_DEF = "data/users.json";
	
	/**
	 * Test initializing a new database
	 */
	@Test
	public void testCreateRestaurantDB() {
		RestaurantDB db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
		assertTrue(db.getRestaurantMap() != null);
		assertTrue(db.getReviewMap() != null);
		assertTrue(db.getUserMap() != null);
		assertTrue(db.getRestaurantReviews() != null);
		assertTrue(db.getUserReviews() != null);
	}
	
	/**
	 * Test getting a restaurant map
	 */
	@Test
	public void testGetRestaurantMap() {
		RestaurantDB db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
		System.out.println(db.getRestaurantMap());
	}
	
	/**
	 * Test getting a review map
	 */
	@Test
	public void testGetReviewMap() {
		RestaurantDB db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
		System.out.println(db.getReviewMap());
	}
	
	/**
	 * Test getting a user map
	 */
	@Test
	public void testGetUserMap() {
		RestaurantDB db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
		System.out.println(db.getUserMap());
	}
	
	/**
	 * Test getting all the reviews mapped to a restaurant
	 */
	@Test
	public void testGetRestaurantReviews() {
		RestaurantDB db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
		System.out.println(db.getRestaurantReviews());
	}
	
	/**
	 * Test getting all the reviews mapped to a user
	 */
	@Test
	public void testGetUserReviews() {
		RestaurantDB db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
		System.out.println(db.getUserReviews());
	}
	
	/**
	 * Test adding a new user
	 */
	@Test
	public void testAddUser() {
		String newUser = "Keenan M.";
		RestaurantDB db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
		db.addUser(newUser);
		assertTrue(db.getUserMap().containsKey(newUser));
	}
	
	/**
	 * Test adding a new restaurant
	 */
	@Test
	public void testAddRestaurant() {
		String newRestaurant = "TestRestaurant";
		RestaurantDB db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
		db.addRestaurant(newRestaurant, 20.0, 20.0);
		assertTrue(db.getRestaurantMap().containsKey(newRestaurant));
	}
	
	/**
	 * Test adding a new review
	 */
	@Test
	public void testAddReview() {
		String newUser = "Keenan M.";
		String newReview = "TestReview";
		String newRestaurant = "TestRestaurant";
		RestaurantDB db = new RestaurantDB(RSTS_DEF, RVWS_DEF, USRS_DEF);
		db.addUser(newUser);
		db.addRestaurant(newRestaurant, 20.0, 20.0);
		db.addReview(newReview, newUser, newRestaurant);
		assertTrue(db.getReviewMap().containsKey(newReview));
	}
	
}
