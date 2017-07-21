package org.ml.topsites.website.user.dao;

import java.sql.SQLException;

import org.ml.topsites.website.user.User;

/**
 * User data access object interface related to all queries related to user
 * @author mkurra
 *
 */
public interface UserDao {

	/**
	 * Fetch User for given userName
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	User fetchUser(String username) throws SQLException;
}
