package org.ml.topsites.user.manager;

/**
 * singleton factory class for returning WebsiteViewManager
 * @author mkurra 
 *
 */
public class UserManagerFactory {
	private static UserManager instance;
	
	public static UserManager getUserManager(){
		if(null==instance){
			synchronized (UserManagerFactory.class) {
				if(null==instance){
					instance = new UserManagerImpl();
				}
			}
		}
		return instance;
	}
}
