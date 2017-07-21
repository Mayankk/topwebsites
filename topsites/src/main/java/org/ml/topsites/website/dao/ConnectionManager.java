package org.ml.topsites.website.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ml.topsites.util.ConfigProperties;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ConnectionManager {
	private static Logger logger =
			LogManager.getLogger(ConnectionManager.class);
	private static MysqlDataSource dataSource;
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			
			dataSource = new MysqlDataSource();
			dataSource.setURL(ConfigProperties.fetchProperty(ConfigProperties.DATABASE_URL_PROP));
			dataSource.setUser(ConfigProperties.fetchProperty(ConfigProperties.DB_USERNAME_PROP));
			dataSource.setPassword(ConfigProperties.fetchProperty(ConfigProperties.DB_PASSWORD_PROP));
			
	    } catch (Exception ex) {
	    	logger.error(ex.getMessage(),ex);
	    }
	}

	public static Connection getConnection() throws SQLException{
		logger.info("getConnection");
		Connection conn = 
				dataSource.getConnection();
		if(conn==null){
			throw new SQLException("Connection null");
		}
		return conn;
	}
	
	public static void main(String args[]) throws SQLException{
		getConnection();
	}
}
