package com.topcoder.management.team;

public class UnknownEntityException extends TeamManagerException {

	public UnknownEntityException(String message) {
		super(message);
	}

	public UnknownEntityException(String message, Throwable cause) {
		super(message, cause);
	}

}
