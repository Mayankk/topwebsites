package org.ml.topsites.test.manager;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ml.topsites.exception.TopSiteException;
import org.ml.topsites.exception.TopSiteValidationException;
import org.ml.topsites.rest.RestClient;
import org.ml.topsites.rest.RestMethod;
import org.ml.topsites.util.ConfigProperties;
import org.ml.topsites.website.manager.WebsitesManager;
import org.ml.topsites.website.manager.WebsitesManagerFactory;
import org.ml.topsites.website.views.WebsiteExclusion;
import org.ml.topsites.website.views.WebsiteExclusionImpl;
import org.ml.topsites.website.views.WebsiteViews;

public class WebsitesManagerTest {
	private static WebsitesManager manager = 
			WebsitesManagerFactory.getWebsitesManager();

	@BeforeClass
	public static void testLoadWebsitesAndExclusionData(){
		try{
			manager.updateWebsiteViews(ConfigProperties.fetchProperty(ConfigProperties.WEBSITE_VIEWS_FILE_PROP),
					ConfigProperties.fetchProperty(ConfigProperties.WEBSITE_VIEWS_FILE_SEPARATOR_PROP));			

			manager.updateWebsiteExclusions(ConfigProperties.fetchProperty(ConfigProperties.WEBSITES_EXCLUSION_URL_PROP));
			//If no exception, assumed to be done
			//next step validating topwebsites list & getExclusions would validate the loading
			Assert.assertEquals(true,true);
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void testGetExclusions(){
		try{
			List<WebsiteExclusion> actualExc = 
					manager.getWebsiteExclusions(null);
			
			List<WebsiteExclusionImpl> expectedExc = 
					RestClient.makeWebServiceCall(RestMethod.GET,
							ConfigProperties.fetchProperty(ConfigProperties.WEBSITES_EXCLUSION_URL_PROP), 
							null,
							new TypeReference<List<WebsiteExclusionImpl>>(){});
			
			boolean isEqual = 
					expectedExc.equals(actualExc);
			if(!isEqual){
				Assert.fail("Expected list: "+expectedExc + 
						", Actual List "+ actualExc);
			}else{
				Assert.assertEquals(isEqual, true);
			}
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testGetTopWebsiteViewsNoExclusion1(){
		try {
			List<WebsiteViews> actualWebsiteViews = 
					manager.getTopWebsiteViews(5, "2016-01-13");

			List<WebsiteViews> expectedWebsiteViews  = 
					WebsitesManagerTestsResult.buildTop5List1();

			boolean isEqual = 
					actualWebsiteViews.equals(expectedWebsiteViews);
			if(!isEqual){
				Assert.fail("Expected list: "+expectedWebsiteViews + 
						", Actual List "+ actualWebsiteViews);
			}else{
				Assert.assertEquals(isEqual, true);
			}

		} catch (TopSiteException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testGetTopWebsiteViewsNoExclusion2(){
		try {
			List<WebsiteViews> actualWebsiteViews = 
					manager.getTopWebsiteViews(2, "2016-01-13");

			List<WebsiteViews> expectedWebsiteViews  = 
					WebsitesManagerTestsResult.buildTopList2();

			boolean isEqual = 
					actualWebsiteViews.equals(expectedWebsiteViews);
			if(!isEqual){
				Assert.fail("Expected list: "+expectedWebsiteViews + 
						", Actual List "+ actualWebsiteViews);
			}else{
				Assert.assertEquals(isEqual, true);
			}

		} catch (TopSiteException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testGetTopWebsiteViewsExclusionNoEndDate(){
		try {
			List<WebsiteViews> actualWebsiteViews = 
					manager.getTopWebsiteViews(2, "2017-01-01");

			List<WebsiteViews> expectedWebsiteViews  = 
					WebsitesManagerTestsResult.buildTopWebsiteViewsExclusionNoEndDate();

			boolean isEqual = 
					actualWebsiteViews.equals(expectedWebsiteViews);
			if(!isEqual){
				Assert.fail("Expected list: "+expectedWebsiteViews + 
						", Actual List "+ actualWebsiteViews);
			}else{
				Assert.assertEquals(isEqual, true);
			}

		} catch (TopSiteException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testGetTopWebsiteViewsExclusionStartAndEndDate(){
		try {
			List<WebsiteViews> actualWebsiteViews = 
					manager.getTopWebsiteViews(5, "2016-03-13");

			List<WebsiteViews> expectedWebsiteViews  = 
					WebsitesManagerTestsResult.buildTopWebsiteViewsExclusionStartEndDate();

			boolean isEqual = 
					actualWebsiteViews.equals(expectedWebsiteViews);
			if(!isEqual){
				Assert.fail("Expected list: "+expectedWebsiteViews + 
						", Actual List "+ actualWebsiteViews);
			}else{
				Assert.assertEquals(isEqual, true);
			}

		} catch (TopSiteException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testGetTopWebsiteViewsDateInvalid(){
		try {
			List<WebsiteViews> actualWebsiteViews = 
					manager.getTopWebsiteViews(2, "2016-01");

			Assert.fail("Date Validation Exception expected");

		} catch (TopSiteValidationException e) {
			Assert.assertEquals(e.getMessage(),"Invalid date : 2016-01");
		}catch (TopSiteException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testGetTopWebsiteViewsCountInvalid(){
		try {
			List<WebsiteViews> actualWebsiteViews = 
					manager.getTopWebsiteViews(-2, "2016-01-13");

			Assert.fail("Count Validation Exception expected");

		} catch (TopSiteValidationException e) {
			Assert.assertEquals(e.getMessage(),"No. of websites to be fetched must be greater than 0");
		}catch (TopSiteException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testGetTopWebsiteViewsDateEmpty(){
		try {
			List<WebsiteViews> actualWebsiteViews = 
					manager.getTopWebsiteViews(5, null);

			Assert.fail("Count Validation Exception expected");

		} catch (TopSiteValidationException e) {
			Assert.assertEquals(e.getMessage(),"Date must not be empty");
		}catch (TopSiteException e) {
			Assert.fail(e.getMessage());
		}
	}
}
