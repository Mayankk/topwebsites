package org.ml.topsites.exception;

/**
 * Top sites Validation Exception - to be thrown in case for validation errors
 * @author mkurra
 *
 */
public class TopSiteValidationException extends TopSiteException {

	public TopSiteValidationException(String string) {
		super(string);
	}

	public TopSiteValidationException(Exception e) {
		super(e);
	}

}
