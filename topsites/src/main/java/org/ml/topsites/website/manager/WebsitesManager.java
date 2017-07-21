package org.ml.topsites.website.manager;

import java.sql.SQLException;
import java.util.List;

import org.ml.topsites.exception.TopSiteException;
import org.ml.topsites.website.views.WebsiteExclusion;
import org.ml.topsites.website.views.WebsiteViews;

/**
 * Website Manager interface for handling all APIs related to the website 
 * @author mkurra
 *
 */
public interface WebsitesManager{
	
	/**
	 * Fetch the required views of the top websites for the given date
	 * @param count
	 * @param date
	 * @return
	 * @throws TopSiteException
	 */
	List<WebsiteViews> getTopWebsiteViews(Integer count, 
										  String date) 
										throws TopSiteException;
	
	/**
	 * fetch the websites exclusions for the given website. If given website is empty, 
	 * then fetch all the websites exclusions
	 * @param website
	 * @return
	 * @throws TopSiteException
	 */
	List<WebsiteExclusion> getWebsiteExclusions(String website) throws TopSiteException;
	
	/**
	 * Update the websites exclusion table with the contents of the location URL. 
	 * @param location
	 * @throws TopSiteException
	 */
	void updateWebsiteExclusions(String location) throws TopSiteException;
	
	/**
	 * Update the websites views table with the contents of the location file. 
	 * Only unique & new websites will be inserted
	 * @param location
	 * @param separator - to separate the columns
	 * @throws SQLException
	 */
	void updateWebsiteViews(String location, String separator) 
							throws TopSiteException;

	/**
	 * get the particular website views for the given start and end dates
	 * @param website
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws TopSiteException
	 */
	List<WebsiteViews> getWebsiteViewsForDates(String website, 
			String startDate, String endDate)
			throws TopSiteException;
}
