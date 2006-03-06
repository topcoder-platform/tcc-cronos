/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.apps.screening.applications.specification.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * 
 * @author nhzp339
 * @version 1.0
 */
public class StressSuite extends TestCase {
	/**
	 * <P>
	 * Aggregate the test suite.
	 * </p>
	 * 
	 * @return The test suite.
	 */
	public static Test suite() {
		final TestSuite suite = new TestSuite();
		suite.addTestSuite(StressTests.class);
		return suite;
	}
}