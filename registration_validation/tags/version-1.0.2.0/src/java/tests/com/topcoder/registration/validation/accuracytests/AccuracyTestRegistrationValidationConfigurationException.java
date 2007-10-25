/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.accuracytests;

import junit.framework.TestCase;

import com.topcoder.registration.validation.RegistrationValidationConfigurationException;

/**
 * This class contains unit tests for
 * <code>RegistrationValidationConfigurationException</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestRegistrationValidationConfigurationException extends TestCase {
	/**
	 * An exception message.
	 */
	private static final String MESSAGE = "Something went horrible wrong";

	/**
	 * Checks if the message is set correctly.
	 */
	public void testRegistrationValidationConfigurationExceptionString() {
		RegistrationValidationConfigurationException ex = new RegistrationValidationConfigurationException(
				MESSAGE);
		assertEquals("message", MESSAGE, ex.getMessage());
		assertNull("cause", ex.getCause());
	}

	/**
	 * Checks if the message and the cause are set correctly.
	 */
	public void testRegistrationValidationConfigurationExceptionStringException() {
		Exception cause = new Exception();
		RegistrationValidationConfigurationException ex = new RegistrationValidationConfigurationException(
				MESSAGE, cause);
		assertTrue("message", ex.getMessage().startsWith(MESSAGE));
		assertEquals("cause", cause, ex.getCause());
	}
}