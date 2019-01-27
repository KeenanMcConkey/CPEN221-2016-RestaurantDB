package ca.ece.ubc.cpen221.mp5.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import ca.ece.ubc.cpen221.feature_functions.FeatureFunction;
import ca.ece.ubc.cpen221.feature_functions.LatitudeFeature;
import ca.ece.ubc.cpen221.feature_functions.LongitudeFeature;
import ca.ece.ubc.cpen221.feature_functions.MeanRatingFeature;
import ca.ece.ubc.cpen221.feature_functions.PriceScaleFeature;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.least_squares_regression.LeastSquaresRegression;
import ca.ece.ubc.cpen221.mp5.statlearning.Algorithms;

public class AlgorithmsTests {

	RestaurantDB db = new RestaurantDB(
			"/Users/glynfinck/Documents/workspace1/f16-mp5-KeenanMcConkey/data/restaurants.json",
			"/Users/glynfinck/Documents/workspace1/f16-mp5-KeenanMcConkey/data/reviews.json",
			"/Users/glynfinck/Documents/workspace1/f16-mp5-KeenanMcConkey/data/users.json");

	@Test
	public void testGetBestPredictorRangeOfOutputs1() {
		String user_id = "fL8ujZ89qTyhbjr1Qz5aSg";
		FeatureFunction price = new MeanRatingFeature();
		List<FeatureFunction> list = new LinkedList<FeatureFunction>();
		list.add(new MeanRatingFeature());
		list.add(new LongitudeFeature());
		list.add(new PriceScaleFeature());
		list.add(new LatitudeFeature());
		LeastSquaresRegression function = Algorithms.getBestPredictor(user_id, db, list);

		for (String s : db.getRestaurantMap().keySet()) {
			assertTrue(function.lsrf(db, s) <= 5 && function.lsrf(db, s) >= 0);
		}
	}
	
	

	@Test
	public void testGetPredictorRangeOfOutputs1() {
		String user_id = "fL8ujZ89qTyhbjr1Qz5aSg";
		FeatureFunction lon = new LongitudeFeature();
		FeatureFunction lat = new LatitudeFeature();
		FeatureFunction mr = new MeanRatingFeature();
		FeatureFunction ps = new LongitudeFeature();
		LeastSquaresRegression function1 = Algorithms.getPredictor(user_id, db, lon);
		LeastSquaresRegression function2 = Algorithms.getPredictor(user_id, db, lat);
		LeastSquaresRegression function3 = Algorithms.getPredictor(user_id, db, mr);
		LeastSquaresRegression function4 = Algorithms.getPredictor(user_id, db, ps);

		for (String s : db.getRestaurantMap().keySet()) {
			assertTrue(function1.lsrf(db, s) <= 5 && function1.lsrf(db, s) >= 0);
		}
		for (String s : db.getRestaurantMap().keySet()) {
			assertTrue(function2.lsrf(db, s) <= 5 && function2.lsrf(db, s) >= 0);
		}
		for (String s : db.getRestaurantMap().keySet()) {
			assertTrue(function3.lsrf(db, s) <= 5 && function3.lsrf(db, s) >= 0);
		}
		for (String s : db.getRestaurantMap().keySet()) {
			assertTrue(function4.lsrf(db, s) <= 5 && function4.lsrf(db, s) >= 0);
		}
	}

	@Test
	public void testCaclulateRSquared() {
		assertTrue(9.0 == Algorithms.calculateR2(2.0, 2.0, 6.0));
	}

	@Test
	public void testGetMeanX() {
		String user_id = "fL8ujZ89qTyhbjr1Qz5aSg";
		assertTrue(db.getUserMap().get(user_id).getAverage_stars() < 3.866279069768
				&& db.getUserMap().get(user_id).getAverage_stars() > 3.866279069767);
	}

}
