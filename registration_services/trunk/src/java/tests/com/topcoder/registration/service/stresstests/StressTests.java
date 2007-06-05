/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all stress cases for Registration Services 1.0.
 * </p>
 *
 * @author Hacker_QC
 * @version 1.0
 */
public class StressTests extends TestCase {

	/**
	 * <p>
	 * Creates a test suite of the stress tests.
	 * </p>
	 *
	 * @return a test suite of the tests contained in this class.
	 */
	public static Test suite() {
		final TestSuite suite = new TestSuite();
		
        suite.addTestSuite(RegistrationServicesStressTest.class);
		
		return suite;
	}
}
