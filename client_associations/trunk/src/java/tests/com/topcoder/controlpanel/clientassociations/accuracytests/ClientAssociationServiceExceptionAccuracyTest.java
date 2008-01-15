/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.accuracytests;

import junit.framework.TestCase;

import com.topcoder.controlpanel.clientassociations.ClientAssociationServiceException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Accuracy test cases for all <code>ClientAssociationServiceException</code>.
 * </p>
 *
 * @author restarter
 * @version 1.0
 */
public class ClientAssociationServiceExceptionAccuracyTest extends TestCase {
	/**
	 * <p>
	 * The message used for testing.
	 * </p>
	 */
	private String message = "testing message";

	/**
	 * <p>
	 * The cause of the exception.
	 * </p>
	 */
	private Throwable cause = new IllegalArgumentException();

	/**
	 * <p>
	 * The ExceptionData of the exception.
	 * </p>
	 */
	private ExceptionData exceptionData = new ExceptionData();

	/**
	 * <p>
	 * Accuracy Tests for <code>ClientAssociationServiceException(String)</code>.
	 * </p>
	 */
	public void testCtor1() {
		ClientAssociationServiceException exception = new ClientAssociationServiceException(
				message);
		assertEquals("ClientAssociationServiceException construction failed.",
				message, exception.getMessage());
	}

	/**
	 * <p>
	 * Accuracy Tests for
	 * <code>ClientAssociationServiceException(String, Throwable)</code>.
	 * </p>
	 */
	public void testCtor2() {
		ClientAssociationServiceException exception = new ClientAssociationServiceException(
				message, cause);
		assertEquals("ClientAssociationServiceException construction failed.",
				message, exception.getMessage());
		assertEquals("ClientAssociationServiceException construction failed.",
				cause, exception.getCause());
	}

	/**
	 * <p>
	 * Accuracy Tests for
	 * <code>ClientAssociationServiceException(String, ExceptionData)</code>.
	 * </p>
	 */
	public void testCtor3() {
		ClientAssociationServiceException exception = new ClientAssociationServiceException(
				message, exceptionData);
		assertEquals("ClientAssociationServiceException construction failed.",
				message, exception.getMessage());
	}

	/**
	 * <p>
	 * Accuracy Tests for
	 * <code>ClientAssociationServiceException(String, Throwable, ExceptionData)</code>.
	 * </p>
	 */
	public void testCtor4() {
		ClientAssociationServiceException exception = new ClientAssociationServiceException(
				message, cause, exceptionData);
		assertEquals("ClientAssociationServiceException construction failed.",
				message, exception.getMessage());
	}
}
