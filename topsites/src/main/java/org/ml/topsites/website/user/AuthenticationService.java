package org.ml.topsites.website.user;

import java.nio.charset.Charset;
import java.util.Base64;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ml.topsites.exception.TopSiteException;
import org.ml.topsites.user.manager.UserManagerFactory;


public class AuthenticationService {
	private static Logger logger =
			LogManager.getLogger(AuthenticationService.class);
	public static final String AUTHENTICATION_HEADER = "Authorization";
	private static final String BASIC_KEY = "Basic";

	public boolean authenticate(String authCredentials) {
		String[] usernameAndPassword = 
				getUserNameAndPassword(authCredentials);
		if(usernameAndPassword==null){
			return false;
		}
		
		final String username = 
				usernameAndPassword[0];
		final String password = 
				usernameAndPassword[1];

		boolean authenticationStatus = true;
		try {
			authenticationStatus = 
					UserManagerFactory.getUserManager()
						.validateLogin(username,password);
		} catch (TopSiteException e) {
			logger.error(e.getMessage(),e);
		}

		return authenticationStatus;
	}

	public static String[] getUserNameAndPassword(String authorization)
			{
		String[] values =null;
		if (authorization != null && authorization.startsWith(BASIC_KEY)) {
			// Authorization: Basic base64credentials
			String base64Credentials = 
					authorization.substring(BASIC_KEY.length()).trim();
			String credentials = 
					new String(Base64.getDecoder().decode(base64Credentials),
							Charset.forName("UTF-8"));
			// credentials = username:password
			values = credentials.split(":",2);
		}

		return values;
	}

}
