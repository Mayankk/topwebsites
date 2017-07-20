package org.manu.exception;

public class TopSiteValidationException extends TopSiteException {

	public TopSiteValidationException(String string) {
		super(string);
	}

	public TopSiteValidationException(Exception e) {
		super(e);
	}

}
