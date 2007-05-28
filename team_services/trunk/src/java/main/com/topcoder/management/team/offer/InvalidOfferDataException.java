package com.topcoder.management.team.offer;

public class InvalidOfferDataException extends OfferManagerException {

	public InvalidOfferDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidOfferDataException(String message) {
		super(message);
	}
}
