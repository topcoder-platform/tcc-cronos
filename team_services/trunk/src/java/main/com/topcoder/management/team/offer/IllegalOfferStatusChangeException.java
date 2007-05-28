package com.topcoder.management.team.offer;

public class IllegalOfferStatusChangeException extends OfferManagerException {

	public IllegalOfferStatusChangeException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalOfferStatusChangeException(String message) {
		super(message);
	}

}
