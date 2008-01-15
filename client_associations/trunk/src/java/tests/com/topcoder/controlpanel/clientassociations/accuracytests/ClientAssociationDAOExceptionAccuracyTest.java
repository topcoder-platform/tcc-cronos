/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.accuracytests;

import junit.framework.TestCase;

import com.topcoder.controlpanel.clientassociations.dao.ClientAssociationDAOException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Accuracy test cases for all <code>ClientAssociationDAOException</code>.
 * </p>
 *
 * @author restarter
 * @version 1.0
 */
public class ClientAssociationDAOExceptionAccuracyTest extends TestCase {
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
	 * Accuracy Tests for <code>ClientAssociationDAOException(String)</code>.
	 * </p>
	 */
	public void testCtor1() {
		ClientAssociationDAOException exception = new ClientAssociationDAOException(
				message);
		assertEquals("ClientAssociationDAOException construction failed.",
				message, exception.getMessage());
	}

	/**
	 * <p>
	 * Accuracy Tests for
	 * <code>ClientAssociationDAOException(String, Throwable)</code>.
	 * </p>
	 */
	public void testCtor2() {
		ClientAssociationDAOException exception = new ClientAssociationDAOException(
				message, cause);
		assertEquals("ClientAssociationDAOException construction failed.",
				message, exception.getMessage());
		assertEquals("ClientAssociationDAOException construction failed.",
				cause, exception.getCause());
	}

	/**
	 * <p>
	 * Accuracy Tests for
	 * <code>ClientAssociationDAOException(String, ExceptionData)</code>.
	 * </p>
	 */
	public void testCtor3() {
		ClientAssociationDAOException exception = new ClientAssociationDAOException(
				message, exceptionData);
		assertEquals("ClientAssociationDAOException construction failed.",
				message, exception.getMessage());
	}

	/**
	 * <p>
	 * Accuracy Tests for
	 * <code>ClientAssociationDAOException(String, Throwable, ExceptionData)</code>.
	 * </p>
	 */
	public void testCtor4() {
		ClientAssociationDAOException exception = new ClientAssociationDAOException(
				message, cause, exceptionData);
		assertEquals("ClientAssociationDAOException construction failed.",
				message, exception.getMessage());
	}
}
