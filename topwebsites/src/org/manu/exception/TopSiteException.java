package org.manu.exception;

public class TopSiteException extends Exception {

	public TopSiteException(String string) {
		super(string);
	}

	public TopSiteException(Exception e) {
		super(e);
	}

}
