package org.ml.topsites.website.user.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ml.topsites.util.JDBCUtil;
import org.ml.topsites.util.SQLConstants;
import org.ml.topsites.util.Utils;
import org.ml.topsites.website.dao.ConnectionManager;
import org.ml.topsites.website.user.User;
import org.ml.topsites.website.user.UserImpl;

import com.mysql.jdbc.PreparedStatement;

/**
 * User data access object implementation related to all queries related to user
 * @author mkurra
 *
 */
public class UserDaoImpl implements UserDao{
	private static Logger logger =
			LogManager.getLogger(UserDaoImpl.class);
	
	@Override
	public User fetchUser(String username) throws SQLException{
		logger.info("fetchUser: "+username);
		if(Utils.isNullOrEmpty(username)){
			return null;
		}

		User user = null;
		String sql = 
				" select HEX(user_password) from " +SQLConstants.USER_TABLE +
					" where user_name = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectionManager.getConnection();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, username);

			rs = ps.executeQuery();

			while(rs.next()){
				String password = 
						rs.getString(1);
				user = new UserImpl(username,password);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw e;
		}finally{
			try {
				JDBCUtil.close(rs, ps, conn);
			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return user;
	}
}
