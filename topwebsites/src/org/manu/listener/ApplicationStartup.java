package org.manu.listener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.manu.exception.TopSiteException;
import org.manu.util.ConfigProperties;
import org.manu.website.manager.WebsitesManager;
import org.manu.website.manager.WebsitesManagerFactory;

public class ApplicationStartup implements Runnable {
	private static Logger logger =
			LogManager.getLogger(ApplicationStartup.class);

	@Override
	public void run(){
		logger.info("do following tasks:");
		WebsitesManager manager = 
				WebsitesManagerFactory.getWebsitesManager();
		try {
			logger.info("updateWebsiteExclusions");
			manager.updateWebsiteExclusions(ConfigProperties.fetchProperty(ConfigProperties.WEBSITES_EXCLUSION_URL_PROP));
			logger.info("executed updateWebsiteExclusions");

			logger.info("updateWebsiteViews");
			manager.updateWebsiteViews(ConfigProperties.fetchProperty(ConfigProperties.WEBSITE_VIEWS_FILE_PROP),
					ConfigProperties.fetchProperty(ConfigProperties.WEBSITE_VIEWS_FILE_SEPARATOR_PROP));
			logger.info("executed updateWebsiteViews");
		} catch (TopSiteException e) {
			logger.error(e.getMessage(),e);
		}
	}
}