/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.accuracytests;

import com.topcoder.registration.validation.OperationResultImpl;

import junit.framework.TestCase;

/**
 * This class contains unit tests for <code>OperationResultImpl</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestOperationResultImpl extends TestCase {

	/**
	 * The error instance used to test.
	 */
	private String[] errors;

	/**
	 * The OperationResultImpl instance used to test.
	 */
	private OperationResultImpl or;

	/**
	 * Set Up the test environment before testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		errors = new String[] { "err", "2" };
		or = new OperationResultImpl(false, errors);
	}

	/**
	 * Clean up the test environment after testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Function test : Tests <code>OperationResultImpl()</code> method for
	 * accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testOperationResultImpl1Accuracy() throws Exception {
		assertNotNull("Null is not allowed.", new OperationResultImpl());
		assertTrue("value is not equals", new OperationResultImpl().isSuccessful());
	}

	/**
	 * Function test : Tests
	 * <code>OperationResultImpl(boolean successful, String[] errors)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testOperationResultImpl2Accuracy() throws Exception {
		assertFalse("value is not equals", or.isSuccessful());
		assertEquals("value is not equals", 2, or.getErrors().length);
	}

	/**
	 * Function test : Tests <code>setSuccessful(boolean successful)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetSuccessfulAccuracy() throws Exception {
		assertFalse("value is not equals", or.isSuccessful());
		or.setErrors(new String[0]);
		or.setSuccessful(true);
		assertTrue("value is not equals", or.isSuccessful());
	}

	/**
	 * Function test : Tests <code>getErrors()</code> method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetErrorsAccuracy() throws Exception {
		assertEquals("value is not equals", 2, or.getErrors().length);
	}

	/**
	 * Function test : Tests <code>setErrors(String[] errors)</code> method
	 * for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetErrorsAccuracy() throws Exception {
		String[] e2 = new String[] { "w" };
		or.setErrors(e2);
		assertEquals("value is not equals", 1, or.getErrors().length);
	}

}