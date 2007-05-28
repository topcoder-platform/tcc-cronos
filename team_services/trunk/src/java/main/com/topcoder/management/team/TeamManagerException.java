package com.topcoder.management.team;

import com.topcoder.util.errorhandling.BaseException;

public class TeamManagerException extends BaseException {

	public TeamManagerException(String message) {
		super(message);
	}

	public TeamManagerException(String message, Throwable cause) {
		super(message, cause);
	}
}
