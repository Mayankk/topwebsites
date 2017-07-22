package org.ml.topsites.listener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ml.topsites.exception.TopSiteException;
import org.ml.topsites.util.ConfigProperties;
import org.ml.topsites.website.manager.WebsitesManager;
import org.ml.topsites.website.manager.WebsitesManagerFactory;

/**
 * Class to handle the application startup operations
 * @author mkurra
 *
 */
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
		} catch (TopSiteException e) {
			logger.warn(e.getMessage(),e);
		}
		try {
			logger.info("updateWebsiteViews");
			manager.updateWebsiteViews(ConfigProperties.fetchProperty(ConfigProperties.WEBSITE_VIEWS_FILE_PROP),
					ConfigProperties.fetchProperty(ConfigProperties.WEBSITE_VIEWS_FILE_SEPARATOR_PROP));
			logger.info("executed updateWebsiteViews");
		} catch (TopSiteException e) {
			logger.warn(e.getMessage(),e);
		}
	}
}
