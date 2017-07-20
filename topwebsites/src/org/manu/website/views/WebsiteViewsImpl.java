package org.manu.website.views;

public class WebsiteViewsImpl implements WebsiteViews{
	private String website;
	private String date;
	private long count;
	private int id;

	public WebsiteViewsImpl(String website, 
			int id) {
		super();
		this.website = website;
		this.id = id;
	}

	public WebsiteViewsImpl(String website, 
			String date,
			long count) {
		super();
		this.website = website;
		this.date = date;
		this.count = count;
	}
	public WebsiteViewsImpl(String website, 
			String date,
			long count,
			int id) {
		super();
		this.website = website;
		this.date = date;
		this.count = count;
		this.id = id;
	}

	@Override
	public String getWebsite() {
		return website;
	}

	@Override
	public String getDate() {
		return date;
	}

	@Override
	public long getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "WebsiteViewsImpl [website=" + website + ", date=" + date + ", count=" + count + "]";
	}

	@Override
	public int getId() {
		return id;
	}

}
