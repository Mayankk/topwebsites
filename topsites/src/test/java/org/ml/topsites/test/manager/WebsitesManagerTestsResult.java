package org.ml.topsites.test.manager;

import java.util.ArrayList;
import java.util.List;

import org.ml.topsites.website.views.WebsiteViews;
import org.ml.topsites.website.views.WebsiteViewsImpl;

public class WebsitesManagerTestsResult {
	public static List<WebsiteViews> buildTop5List1() {
		List<WebsiteViews> websiteViews = new ArrayList<>();
		WebsiteViews view = 
				new WebsiteViewsImpl("www.google.com.au", 
						"2016-01-13",
						172220397);
		websiteViews.add(view);

		view = 
				new WebsiteViewsImpl("www.facebook.com", 
						"2016-01-13",
						118506019);
		websiteViews.add(view);

		view = 
				new WebsiteViewsImpl("www.youtube.com", 
						"2016-01-13",
						68487810);
		websiteViews.add(view);

		view = 
				new WebsiteViewsImpl("www.google.com", 
						"2016-01-13",
						29203671);
		websiteViews.add(view);

		view = 
				new WebsiteViewsImpl("mail.live.com", 
						"2016-01-13",
						24772355);
		websiteViews.add(view);
		return websiteViews;
	}

	public static List<WebsiteViews> buildTopList2() {
		List<WebsiteViews> websiteViews = new ArrayList<>();
		WebsiteViews view = 
				new WebsiteViewsImpl("www.google.com.au", 
						"2016-01-13",
						172220397);
		websiteViews.add(view);

		view = 
				new WebsiteViewsImpl("www.facebook.com", 
						"2016-01-13",
						118506019);
		websiteViews.add(view);

		return websiteViews;
	}

	public static List<WebsiteViews> buildTopWebsiteViewsExclusionNoEndDate() {
		List<WebsiteViews> websiteViews = new ArrayList<>();
		WebsiteViews view = 
				new WebsiteViewsImpl("www.google.com", 
						"2017-01-01",
						29203671);
		websiteViews.add(view);

		return websiteViews;
	}

	public static List<WebsiteViews> buildTopWebsiteViewsExclusionStartEndDate() {
		List<WebsiteViews> websiteViews = new ArrayList<>();
		WebsiteViews view = 
				new WebsiteViewsImpl("www.facebook.com", 
						"2016-03-13",
						172220397);
		websiteViews.add(view);

		view = 
				new WebsiteViewsImpl("www.youtube.com", 
						"2016-03-13",
						69064107);
		websiteViews.add(view);

		view = 
				new WebsiteViewsImpl("www.wikipedia.org", 
						"2016-03-13",
						16015926);
		websiteViews.add(view);

		return websiteViews;
	}
}
