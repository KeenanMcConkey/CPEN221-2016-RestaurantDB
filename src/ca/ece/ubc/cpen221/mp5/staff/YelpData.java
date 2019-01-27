package ca.ece.ubc.cpen221.mp5.staff;

/**
 * YelpData includes all of the abstract data types extractable from the Yelp 
 * Academic Dataset, i.e. Restaurants, Reviews, and Users.
 */
public interface YelpData {

	/**
	 * Return the ID associated with this data
	 * 
	 * @return the ID number for this data
	 */
	public String getID();
	

}
