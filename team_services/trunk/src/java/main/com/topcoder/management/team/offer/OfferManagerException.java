package com.topcoder.management.team.offer;

import com.topcoder.util.errorhandling.BaseRuntimeException;

public class OfferManagerException extends BaseRuntimeException {

	public OfferManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public OfferManagerException(String message) {
		super(message);
	}

}
