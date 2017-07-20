package org.manu.website.views;

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

}
