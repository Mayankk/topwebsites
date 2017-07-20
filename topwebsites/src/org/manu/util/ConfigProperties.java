package org.manu.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ConfigProperties {
	private static Logger logger =
			LogManager.getLogger(ConfigProperties.class);
	private static Properties prop = null;
	public static final String DATABASE_URL_PROP="database";
	public static final String DB_USERNAME_PROP="user";
	public static final String DB_PASSWORD_PROP="password";
	public static final String WEBSITE_VIEWS_FILE_PROP="websites_views_file";
	public static final String WEBSITE_VIEWS_FILE_SEPARATOR_PROP="websites_views_file_separator";
	public static final String WEBSITES_EXCLUSION_URL_PROP="websites_exclusion_URL";
	
	public static String fetchProperty(String propertyName){
		logger.debug("fetchProperty: " + propertyName);
		if(Utils.isNullOrEmpty(propertyName)){
			return null;
		}
		
		//fetch the properties only once and then cache it
		if(prop==null){
			logger.info("loading properties from config properties file");
			prop = new Properties();
			InputStream input = null;
	
			try {
	
				ClassLoader classLoader = 
						Thread.currentThread().getContextClassLoader();
				input = classLoader.getResourceAsStream("config.xml");
				//input = new FileInputStream("config.xml");
	
				// load a properties file
				prop.loadFromXML(input);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						logger.error(e.getMessage(),e);
					}
				}
			}
		}

		return prop.getProperty(propertyName);
	}
	
	public static void main(String args[]){
		System.out.println(fetchProperty(DATABASE_URL_PROP));
	}
}
