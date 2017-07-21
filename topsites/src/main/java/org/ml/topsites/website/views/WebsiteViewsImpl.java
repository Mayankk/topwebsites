package org.ml.topsites.website.views;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (count ^ (count >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((website == null) ? 0 : website.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebsiteViewsImpl other = (WebsiteViewsImpl) obj;
		if (count != other.count)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (website == null) {
			if (other.website != null)
				return false;
		} else if (!website.equals(other.website))
			return false;
		return true;
	}

}
