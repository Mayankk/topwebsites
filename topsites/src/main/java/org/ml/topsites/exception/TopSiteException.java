package org.ml.topsites.exception;

/**
 * Top Site Exception to wrap any other exceptions in the application
 * @author mkurra
 *
 */
public class TopSiteException extends Exception {

	public TopSiteException(String string) {
		super(string);
	}

	public TopSiteException(Exception e) {
		super(e);
	}

}
