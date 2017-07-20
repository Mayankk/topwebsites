package org.manu.website.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.manu.website.views.WebsiteExclusion;
import org.manu.website.views.WebsiteViews;

/**
 * Website Data access object interface for handling all queries related to the website tables 
 * @author mkurra
 *
 */
public interface WebsiteDao {
	
	/**
	 * Fetch the required count of the top website for the date from the website views table
	 * @param count
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	List<WebsiteViews> getTopWebsiteViews(int count, 
									String date) throws SQLException;
	
	/**
	 * Update the websites table with the latest list. Only unique & new websites will be inserted
	 * @param websiteViews
	 * @throws SQLException
	 */
	void updateWebsites(List<WebsiteViews> websiteViews) throws SQLException;
	
	/**
	 * Update the website views for the given dates 
	 * @param websiteViews
	 * @throws SQLException
	 */
	void updateWebsiteViews(List<WebsiteViews> websiteViews) throws SQLException;
	
	/**
	 * update websites exclusions for the given list
	 * @param exclusions
	 * @throws SQLException
	 */
	void updateWebsiteExclusions(List<? extends WebsiteExclusion> exclusions) throws SQLException;
	
	/**
	 * fetch the websites exclusions for the given website. If given website is empty, 
	 * then fetch all the websites exclusions
	 * @param website
	 * @return websites exclusion list
	 * @throws SQLException
	 */
	List<WebsiteExclusion> getWebSiteExclusions(String website) throws SQLException;
	
	/**
	 * fetch all the websites ingested till date
	 * @return websites map
	 * @throws SQLException
	 */
	Map<String,WebsiteViews> getWebsites() throws SQLException;

	List<WebsiteViews> getWebsiteViewsForDates(String website, 
			String startDate, String endDate) throws SQLException;
}
