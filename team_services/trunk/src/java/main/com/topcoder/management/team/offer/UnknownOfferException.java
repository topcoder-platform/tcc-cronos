package com.topcoder.management.team.offer;

public class UnknownOfferException extends OfferManagerException {

	public UnknownOfferException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownOfferException(String message) {
		super(message);
	}

}
