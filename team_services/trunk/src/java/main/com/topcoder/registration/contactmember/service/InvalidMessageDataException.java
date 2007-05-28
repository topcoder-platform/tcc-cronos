package com.topcoder.registration.contactmember.service;

public class InvalidMessageDataException extends ContactMemberServiceException {

	public InvalidMessageDataException(String message) {
		super(message);
	}

	public InvalidMessageDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
