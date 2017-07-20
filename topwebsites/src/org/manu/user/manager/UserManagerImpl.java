package org.manu.user.manager;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.manu.exception.TopSiteException;
import org.manu.website.user.User;
import org.manu.website.user.dao.UserDao;
import org.manu.website.user.dao.UserDaoImpl;

/**
 * User Manager implementation for handling all APIs related to the users 
 * @author mkurra
 *
 */
public class UserManagerImpl implements UserManager{
	private UserDao dao = new UserDaoImpl();
	private static Logger logger =
				LogManager.getLogger(UserManagerImpl.class);
	@Override
	public boolean validateLogin(String username,
			  					 String password)
			  					throws TopSiteException {
		logger.info("validateLogin: "+username );
		MessageDigest digest =null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(),e);
			throw new TopSiteException(e);
		}
		byte[] mdbytes = 
				digest.digest(password.getBytes(StandardCharsets.UTF_8));
		//convert the byte to hex format 
		StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<mdbytes.length;i++) {
    	  hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
    	}
		User user=null;
		try {
			user = dao.fetchUser(username);
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new TopSiteException(e);
		}
		if(null!=user && 
				user.getPassword().equalsIgnoreCase(hexString.toString()))
			return true;
		
		
		logger.info("Failed validateLogin: "+username );
		return false;
	}
	
	
}