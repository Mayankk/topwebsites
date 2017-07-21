package org.ml.topsites.rest.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.ml.topsites.exception.TopSiteValidationException;
import org.ml.topsites.website.manager.WebsitesManager;
import org.ml.topsites.website.manager.WebsitesManagerFactory;
import org.ml.topsites.website.views.WebsiteViews;

/**
 * Class for handling the rest calls for fetching websites related data
 * @author mkurra
 *
 */

@Path("/websites")
public class WebsitesAPI {
	private static Logger logger =
				LogManager.getLogger(WebsitesAPI.class);
	private static ObjectMapper oMapper = new ObjectMapper();

	@GET
	@Path("topsites/{date}")
	@Produces( { MediaType.APPLICATION_JSON })
	public Response topSites(@PathParam("date") String date,
							 @QueryParam("count") Integer count){
		WebsitesManager manager = 
				WebsitesManagerFactory.getWebsitesManager();
		Response response = null;
		
		logger.info("topSites: date- " + date);
		try {
			List<WebsiteViews> websiteViewsList = 
					manager.getTopWebsiteViews(count, date);
			String result =
					oMapper.writeValueAsString(websiteViewsList);
			logger.debug(result);
			response = Response.ok(result).build();
		} catch (TopSiteValidationException ve) {
			logger.warn("topSites: date- " + date);
			response = 
				Response.status(Response.Status.NOT_ACCEPTABLE)
					.entity(new Message(ve.getMessage())).build();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			response = Response.serverError().build();
		}
		return response;
	}
	
	@GET
	@Path("website/{website}")
	@Produces( { MediaType.APPLICATION_JSON })
	public Response getWebsiteData(@PathParam("website") String website,
									@QueryParam("startDate") String startDate,
							 		@QueryParam("endDate") String endDate){
		WebsitesManager manager = 
				WebsitesManagerFactory.getWebsitesManager();
		Response response = null;
		
		logger.info("getWebsiteData: date- " + website);
		try {
			List<WebsiteViews> websiteViewsList = 
					manager.getWebsiteViewsForDates(website, 
							startDate, endDate);
			String result =
					oMapper.writeValueAsString(websiteViewsList);
			logger.debug(result);
			response = Response.ok(result).build();
		} catch (TopSiteValidationException ve) {
			logger.warn("getWebsiteData: website- " + website);
			response = 
				Response.status(Response.Status.NOT_ACCEPTABLE)
					.entity(new Message(ve.getMessage())).build();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			response = Response.serverError().build();
		}
		return response;
	}
}
