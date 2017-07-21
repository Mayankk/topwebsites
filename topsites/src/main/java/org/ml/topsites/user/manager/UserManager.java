package org.ml.topsites.user.manager;

import org.ml.topsites.exception.TopSiteException;

/**
 * User Manager interface for handling all APIs related to the user 
 * @author mkurra
 *
 */
public interface UserManager{
	
	/**
	 * validate the user login
	 * @return
	 * @throws TopSiteException
	 */
	boolean validateLogin(String username,
						  String password) 
						  throws TopSiteException;
	
}
