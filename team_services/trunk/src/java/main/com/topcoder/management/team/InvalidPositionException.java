package com.topcoder.management.team;

public class InvalidPositionException extends TeamManagerException {

	public InvalidPositionException(String message) {
		super(message);
	}

	public InvalidPositionException(String message, Throwable cause) {
		super(message, cause);
	}

}
