package com.topcoder.project.service;

import com.topcoder.util.errorhandling.BaseRuntimeException;

public class ProjectServicesException extends BaseRuntimeException {

	public ProjectServicesException(String message) {
		super(message);
	}

	public ProjectServicesException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
