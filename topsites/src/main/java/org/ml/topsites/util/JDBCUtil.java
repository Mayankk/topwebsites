package org.ml.topsites.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC utility class
 * @author mkurra
 *
 */
public class JDBCUtil {
	/**
	 * Util method to close the connection, resultset and the preparedstatement
	 * @param rs
	 * @param ps
	 * @param conn
	 * @throws SQLException
	 */
	public static void close(ResultSet rs,
			PreparedStatement ps, 
			Connection conn) 
					throws SQLException{

		if(rs!=null){
			rs.close();
		}

		if(ps!=null){
			ps.close();
		}

		if(null!=conn){
			conn.close();
		}
	}

	public static void close(PreparedStatement ps, 
			Connection conn)
					throws SQLException{
		close(null,ps,conn);
	}

	public static void close(PreparedStatement ps)
			throws SQLException{
		close(null,ps,null);
	}


}
