package org.manu.website.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.manu.util.JDBCUtil;
import org.manu.util.SQLConstants;
import org.manu.util.Utils;
import org.manu.website.views.WebsiteExclusion;
import org.manu.website.views.WebsiteExclusionImpl;
import org.manu.website.views.WebsiteViews;
import org.manu.website.views.WebsiteViewsImpl;


/**
 * Website Data access object implementation for handling all
 * queries related to the website tables 
 * @author mkurra
 *
 */
public class WebsiteDaoImpl implements WebsiteDao{
	private static Logger logger =
				LogManager.getLogger(WebsiteDaoImpl.class);
	private Map<String,WebsiteViews> sites = new HashMap<>();
	
	@Override
	public List<WebsiteViews> getWebsiteViewsForDates(String website,
												 	  String startDate,
												 	  String endDate)
												throws SQLException{
		logger.info("getWebsiteViewsForDates");
		logger.info("website: " + website);
		logger.info("startDate: " + startDate);
		logger.info("endDate: " + endDate);
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" select website, record_date, count, wv.id from ")
			.append(SQLConstants.WEBSITE_TABLE).append(" w, ")
			.append(SQLConstants.WEBSITE_VIEWS_TABLE).append(" wv ")
			.append(" where w.id = wv.website_id ")
			.append(" and w.website like CONCAT('%',?,'%')");
		
		if(!Utils.isNullOrEmpty(startDate)){
			queryBuilder.append(" and record_date >= ? ");
		}
		if(!Utils.isNullOrEmpty(endDate)){
			queryBuilder.append(" and record_date <= ? ");
		}
		queryBuilder.append(" order by record_date desc");
		Connection conn = null;
		PreparedStatement ps = null;
		
		conn = ConnectionManager.getConnection();
		ps = (PreparedStatement) conn.prepareStatement(queryBuilder.toString());
		int i=1;
		ps.setString(i++, website);
		if(!Utils.isNullOrEmpty(startDate)){
			ps.setDate(i++, Date.valueOf(startDate));
		}
		if(!Utils.isNullOrEmpty(endDate)){
			ps.setDate(i++, Date.valueOf(endDate));
		}
		
		return getWebsiteViews(ps,conn);	
		
	}
	
	@Override
	public List<WebsiteViews> getTopWebsiteViews(int topCount,
												 String date)
												throws SQLException{
		logger.info("getTopWebsiteViews:" + date);
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" select website, record_date, count, wv.id from ")
			.append(SQLConstants.WEBSITE_TABLE).append(" w, ")
			.append(SQLConstants.WEBSITE_VIEWS_TABLE).append(" wv ")
			.append(" where w.id = wv.website_id and record_date = ? ")
			.append(" and 0 = (select count(*) from website_exclusions ")
			.append(" where record_date >= start_date and ")
			.append(" website like CONCAT('%',host,'%') ")
			.append(" and (end_date is null or end_date >= record_date )) ")
			.append(" order by count desc LIMIT ? ");
		Connection conn = null;
		PreparedStatement ps = null;
		
		conn = ConnectionManager.getConnection();
		ps = (PreparedStatement) conn.prepareStatement(queryBuilder.toString());
		ps.setDate(1, Date.valueOf(date));
		ps.setInt(2, topCount);
		
		return getWebsiteViews(ps,conn);	
	}
	
	private List<WebsiteViews> getWebsiteViews(PreparedStatement ps,
											   Connection conn)
												throws SQLException{
		List<WebsiteViews> topWebsiteViews = new ArrayList<>();

		ResultSet rs = null;
		try {
			rs = ps.executeQuery();

			while(rs.next()){
				int i=1;
				String website = rs.getString(i++);
				String date = rs.getString(i++);
				long views = rs.getLong(i++);
				int id = rs.getInt(i++);

				topWebsiteViews.add(new WebsiteViewsImpl(website,
						 				date,views,id));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw e;
		}finally{
			try {
				JDBCUtil.close(rs,ps,conn);
			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
			}
		}

		return topWebsiteViews;
	}

	public Map<String,WebsiteViews> getWebsites() throws SQLException{
		logger.info("getWebsites");
		if(!Utils.isNullOrEmpty(sites)){
			return sites;
		}

		String getQuery = 
				" select website, id from " +SQLConstants.WEBSITE_TABLE;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectionManager.getConnection();
			ps = (PreparedStatement) conn.prepareStatement(getQuery);

			rs = ps.executeQuery();

			while(rs.next()){
				int i=1;
				String website = rs.getString(i++);
				int id = rs.getInt(i++);

				sites.put(website,
						new WebsiteViewsImpl(website,id));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw e;
		}finally{
			try {
				JDBCUtil.close(rs,ps,conn);
			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return sites;
	}

	@Override
	public List<WebsiteExclusion> getWebSiteExclusions(String website) 
									throws SQLException {
		logger.info("getWebSiteExclusions");
		
		List<WebsiteExclusion> websiteExclusions = new ArrayList<>();
		StringBuilder sqlBuilder = 
				new StringBuilder();
		sqlBuilder.append(" select host, start_date, end_date from ")
			      .append(SQLConstants.WEBSITE_EXCLUSION_TABLE);

		if(!Utils.isNullOrEmpty(website))
			sqlBuilder.append(" and website = ? ");

		String sql = sqlBuilder.toString();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectionManager.getConnection();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			if(!Utils.isNullOrEmpty(website))
				ps.setString(1,website);			
			rs = ps.executeQuery();

			while(rs.next()){
				int i=1;
				String ws = rs.getString(i++);
				Date startDate = rs.getDate(i++);
				Date endDate = rs.getDate(i++);

				websiteExclusions.add(new WebsiteExclusionImpl(ws,
						startDate.toString(),
						endDate!=null?endDate.toString():null));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw e;
		}finally{
			try {
				JDBCUtil.close(rs,ps, conn);
			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
			}
		}
		logger.debug(websiteExclusions);
		return websiteExclusions;
	}

	@Override
	public void updateWebsiteExclusions
			(List<? extends WebsiteExclusion> exclusions)
					throws SQLException {
		logger.info("updateWebsiteExclusions: " + exclusions);
		StringBuilder queryBuilder = 
				new StringBuilder();
		queryBuilder.append(" insert into ")
			.append(SQLConstants.WEBSITE_EXCLUSION_TABLE) 
			.append("(host,start_date,end_date) ")
			.append(" values (? , ?, ?) ");

		String truncateQuery =
				"truncate "+SQLConstants.WEBSITE_EXCLUSION_TABLE;

		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		try {
			conn = ConnectionManager.getConnection();
			ps1 = (PreparedStatement)conn.prepareStatement(truncateQuery);
			ps1.executeUpdate();

			String insertQuery = queryBuilder.toString();
			ps2 = (PreparedStatement) conn.prepareStatement(insertQuery);
			for(WebsiteExclusion exclusion : exclusions){
				ps2.setString(1, exclusion.getHost());
				ps2.setDate(2, Date.valueOf(exclusion.getExcludedSince()));
				ps2.setDate(3, exclusion.getExcludedTill()!=null?
						Date.valueOf(exclusion.getExcludedTill()):null);
				ps2.addBatch();
			}
			ps2.executeBatch();

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw e;
		}finally{
			try {
				JDBCUtil.close(ps1, conn);
				JDBCUtil.close(ps2);
			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}

	@Override
	public void updateWebsites(List<WebsiteViews> websiteViews) 
						throws SQLException {
		
		logger.info("updateWebsites: " + websiteViews);
		//reset the cache
		sites.clear();
		
		Set<String> websites = new HashSet<>();
		StringBuilder queryBuilder = 
				new StringBuilder();
		queryBuilder.append(" insert into ")
				  .append(SQLConstants.WEBSITE_TABLE)
				  .append(" (website) ")
				  .append(" values (?) "); 
		
		Map<String,WebsiteViews> websitesMap = getWebsites();
		//save only the new & unique websites in the website table
		for(WebsiteViews website : websiteViews){
			String url = website.getWebsite();
			if(websitesMap.get(url)==null){
				websites.add(url);
			}
		}

		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();

			String insertQuery = queryBuilder.toString();
			ps = (PreparedStatement) conn.prepareStatement(insertQuery);
			for(String website : websites){
				ps.setString(1, website);
				ps.addBatch();
			}
			ps.executeBatch();

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw e;
		}finally{
			try {
				JDBCUtil.close(ps, conn);
			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}

	@Override
	public void updateWebsiteViews(List<WebsiteViews> websiteViews) 
					throws SQLException {
		//TODO:reset autoincrement
		StringBuilder queryBuilder = 
				new StringBuilder();
		queryBuilder.append(" insert into ")
				  .append(SQLConstants.WEBSITE_VIEWS_TABLE)
				  .append(" (website_id,count,record_date) ")
				  .append(" values (? , ?, ?) ");

		String truncateQuery =
				"truncate "+SQLConstants.WEBSITE_VIEWS_TABLE;

		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		try {
			conn = ConnectionManager.getConnection();
			ps1 = (PreparedStatement)conn.prepareStatement(truncateQuery);
			ps1.executeUpdate();

			String insertQuery = queryBuilder.toString();
			ps2 = (PreparedStatement) conn.prepareStatement(insertQuery);
			Map<String,WebsiteViews> websites = getWebsites();
			for(WebsiteViews website : websiteViews){
				String url = website.getWebsite();
				WebsiteViews websiteObj = websites.get(url);
				ps2.setInt(1, websiteObj.getId());
				ps2.setLong(2, website.getCount());
				ps2.setDate(3, Date.valueOf(website.getDate()));
				ps2.addBatch();
			}
			ps2.executeBatch();

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw e;
		}finally{
			try {
				JDBCUtil.close(ps1, conn);
				JDBCUtil.close(ps2);
			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}
	
	public static void main(String args[]){
		WebsiteDaoImpl impl = new WebsiteDaoImpl();
		List<WebsiteViews> views = null;
		try {
			views = impl.getTopWebsiteViews(5, "2016-03-13");
		
			System.out.println(views);
	
			List<WebsiteExclusion> exclusion = 
					impl.getWebSiteExclusions(null);
			System.out.println(exclusion);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
