package org.ml.topsites.website.views;

public class WebsiteExclusionImpl implements WebsiteExclusion{
	private String host;
	private String excludedSince;
	private String excludedTill;
	
	public WebsiteExclusionImpl(String host, 
							String excludedSince, 
							String excludedTill) {
		super();
		this.host = host;
		this.excludedSince = excludedSince;
		this.excludedTill = excludedTill;
	}
	public WebsiteExclusionImpl() {
		super();
	}
	@Override
	public String getHost() {
		return host;
	}

	@Override
	public String getExcludedSince() {
		return excludedSince;
	}

	@Override
	public String getExcludedTill() {
		if(null!=excludedTill)
			return excludedTill;
		
		return null;
	}
	
	@Override
	public String toString() {
		return "WebsiteExclusionImpl [host=" + host + ", excludedSince=" + excludedSince + ", excludedTill="
				+ excludedTill + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((excludedSince == null) ? 0 : excludedSince.hashCode());
		result = prime * result + ((excludedTill == null) ? 0 : excludedTill.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
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
		WebsiteExclusionImpl other = (WebsiteExclusionImpl) obj;
		if (excludedSince == null) {
			if (other.excludedSince != null)
				return false;
		} else if (!excludedSince.equals(other.excludedSince))
			return false;
		if (excludedTill == null) {
			if (other.excludedTill != null)
				return false;
		} else if (!excludedTill.equals(other.excludedTill))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		return true;
	}

}
