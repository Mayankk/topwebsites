package org.manu.website.manager;

/**
 * singleton factory class for returning WebsiteViewManager
 * @author mkurra 
 *
 */
public class WebsitesManagerFactory {
	private static WebsitesManager instance;
	
	public static WebsitesManager getWebsitesManager(){
		if(null==instance){
			synchronized (WebsitesManagerFactory.class) {
				if(null==instance){
					instance = new WebsitesManagerImpl();
				}
			}
		}
		return instance;
	}
}
