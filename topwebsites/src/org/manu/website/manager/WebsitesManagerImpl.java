package org.manu.website.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.manu.exception.TopSiteException;
import org.manu.exception.TopSiteValidationException;
import org.manu.file.FileUtils;
import org.manu.rest.RestClient;
import org.manu.rest.RestMethod;
import org.manu.util.ConfigProperties;
import org.manu.util.Utils;
import org.manu.website.dao.WebsiteDao;
import org.manu.website.dao.WebsiteDaoImpl;
import org.manu.website.views.WebsiteExclusion;
import org.manu.website.views.WebsiteExclusionImpl;
import org.manu.website.views.WebsiteViews;
import org.manu.website.views.WebsiteViewsImpl;

/**
 * Website Manager implementation for handling all APIs related to the website 
 * @author mkurra
 *
 */
public class WebsitesManagerImpl implements WebsitesManager{
	private WebsiteDao dao = new WebsiteDaoImpl();
	private static Logger logger =
			LogManager.getLogger(WebsitesManagerImpl.class);

	@Override
	public List<WebsiteViews> getWebsiteViewsForDates(String website,
			String startDate,
			String endDate)
					throws TopSiteException{
		logger.info("getWebsiteViewsForDates");
		if(Utils.isNullOrEmpty(website)){
			logger.info("getWebsiteViewsForDates invalid website: "
					+ website);
			throw new TopSiteValidationException("No. of "
					+ "websites to be fetched must be greater than 0");
		}
		try{
			if(null!=startDate)
				LocalDate.parse(startDate);
			if(null!=endDate)
				LocalDate.parse(endDate);
		}catch(RuntimeException e){
			logger.info("getWebsiteViewsForDates invalid startDate/endDate "
					+ startDate + " - "+ endDate);
			throw new TopSiteValidationException("Invalid dates : " 
					+ startDate + " - "+ endDate);
		}
		List<WebsiteViews> websiteViews = null;

		try{
			websiteViews = 
					dao.getWebsiteViewsForDates(website,
							startDate, endDate);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new TopSiteException(e);
		}
		logger.debug("websiteViews : " + websiteViews);
		return websiteViews;
	}

	@Override
	public List<WebsiteViews> getTopWebsiteViews(Integer count,
								String date) 
					throws TopSiteException{
		logger.info("getTopWebsiteViews : " + date);

		//default to 5 top websites if count is null
		if(count==null){
			count = 5;
		}else{
			if(count<=0){
				logger.info("getTopWebsiteViews invalid count: " + count);
				throw new TopSiteValidationException("No. of"
						+ " websites to be fetched must be greater than 0");
			}
		}

		if(Utils.isNullOrEmpty(date)){
			logger.info("getTopWebsiteViews empty date: " + date);
			throw new TopSiteValidationException("Date must not be empty");
		}
		try{
			LocalDate.parse(date);
		}catch(Exception e){
			logger.info("getTopWebsiteViews invalid date: " + date);
			throw new TopSiteValidationException("Invalid date : " + date);
		}
		List<WebsiteViews> websiteViews = null;

		try{
			websiteViews = dao.getTopWebsiteViews(count, date);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new TopSiteException(e);
		}
		logger.debug("websiteViews : " + websiteViews);
		return websiteViews;
	}

	@Override
	public List<WebsiteExclusion> getWebsiteExclusions(String website) 
			throws TopSiteException{
		logger.info("getWebsiteExclusions");
		try {
			return dao.getWebSiteExclusions(website);
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new TopSiteException(e);
		}
	}

	@Override
	public void updateWebsiteExclusions(String location) 
			throws TopSiteException{
		logger.info("updateWebsiteExclusions using location: " + location);
		List<? extends WebsiteExclusion> exclusions = null;
		try {
			exclusions = RestClient.makeWebServiceCall(RestMethod.GET,
					location, null, 
					new TypeReference<List<WebsiteExclusionImpl>>(){});

			dao.updateWebsiteExclusions(exclusions);
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new TopSiteException(e);
		}
	}

	@Override
	public void updateWebsiteViews(String location, 
			String separator) throws TopSiteException {

		try{
			List<String> content = 
					FileUtils.readFileLines(location);
			List<WebsiteViews> websiteViews = new ArrayList<>();
			for(String line: content){
				logger.debug("content : "+ line);
				StringTokenizer tokenizer = 
						new StringTokenizer(line,separator);
				String date = tokenizer.nextToken();
				String website = tokenizer.nextToken();
				long visits = 0;
				try{
					visits = Long.valueOf(tokenizer.nextToken());
				}catch(RuntimeException e){
					//skip the invalid rows
					logger.warn("skipped line");
					continue;
				}

				WebsiteViews views = 
						new WebsiteViewsImpl(website,
								date,visits);
				websiteViews.add(views);
			}
			dao.updateWebsites(websiteViews);
			dao.updateWebsiteViews(websiteViews);
		}catch(IOException | SQLException e){
			logger.error(e.getMessage(),e);
			throw new TopSiteException(e);
		}
	}

	public static void main(String args[]){
		try {
			new WebsitesManagerImpl().updateWebsiteViews(ConfigProperties.fetchProperty(ConfigProperties.WEBSITE_VIEWS_FILE_PROP),
					ConfigProperties.fetchProperty(ConfigProperties.WEBSITE_VIEWS_FILE_SEPARATOR_PROP));

			new WebsitesManagerImpl().updateWebsiteExclusions(ConfigProperties.fetchProperty(ConfigProperties.WEBSITES_EXCLUSION_URL_PROP));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
