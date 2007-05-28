package com.topcoder.registration.contactmember.service;

import com.topcoder.util.errorhandling.BaseRuntimeException;

public class ContactMemberServiceException extends BaseRuntimeException {

	public ContactMemberServiceException(String message) {
		super(message);
	}

	public ContactMemberServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
