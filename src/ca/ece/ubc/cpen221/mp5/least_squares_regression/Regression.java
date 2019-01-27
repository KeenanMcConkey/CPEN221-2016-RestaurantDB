package ca.ece.ubc.cpen221.mp5.least_squares_regression;

import java.util.Map;

import ca.ece.ubc.cpen221.feature_functions.FeatureFunction;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.staff.YelpRestaurant;
import ca.ece.ubc.cpen221.mp5.staff.YelpReview;
import ca.ece.ubc.cpen221.mp5.staff.YelpUser;

/* This class is an implementation of LeastSquaresRegression and represents
 * a least squares regression function based on inputed values specifically,
 * a feature function which would change the function in terms of its accuracy.
 * 
 */
public class Regression implements LeastSquaresRegression {
	
	final double Sxx;
	final double Syy;
	final double Sxy;
	final double mean_x;
	final double mean_y;
	final FeatureFunction f;
	final String user_id;
	
	/**
	 * Constructor for the Regression class.
	 * 
	 * @param Sxx
	 *            Sxx to be set.
	 * @param Syy
	 *            Syy to be set.
	 * @param Sxy
	 *            Sxy to be set.
	 * @param mean_x
	 *            mean_x to be set.
	 * @param mean_y
	 *            mean_y to be set.
	 * @param user_id
	 *            user_id to be set.
	 * @param f
	 *            feature function to be set.
	 */
	public Regression(double Sxx,double Syy,double Sxy,double mean_x,double mean_y,String user_id,FeatureFunction f){
		this.Sxx = Sxx;
		this.Syy = Syy;
		this.Sxy = Sxy;
		this.mean_x = mean_x;
		this.mean_y = mean_y;
		this.f = f;
		this.user_id = user_id;
	}
	@Override
	public double lsrf(RestaurantDB db, String restaurant_id) {
		Map<String,YelpRestaurant> restaurant_map = db.getRestaurantMap();
		for(YelpUser u : db.getUserReviews().keySet()){
			if(u.getID() == user_id){
				for(YelpReview r : db.getUserReviews().get(u)){
					if(r.getBusiness_id() == restaurant_id){
						return r.getStars();
					}
				}
			}
		}
		double b = Sxy/Syy;
		double a = mean_y - b*mean_x;
		double x = f.getFeature(db, restaurant_id);
		return b*x + a;
	}

}
