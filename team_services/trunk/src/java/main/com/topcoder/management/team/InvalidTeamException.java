package com.topcoder.management.team;

public class InvalidTeamException extends TeamManagerException {

	public InvalidTeamException(String message) {
		super(message);
	}

	public InvalidTeamException(String message, Throwable cause) {
		super(message, cause);
	}

}
