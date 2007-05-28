package com.topcoder.registration.contactmember.service;

public class MessagePersistenceException extends ContactMemberServiceException {

	public MessagePersistenceException(String message) {
		super(message);
	}

	public MessagePersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

}
