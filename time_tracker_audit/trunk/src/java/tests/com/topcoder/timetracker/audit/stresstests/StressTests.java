/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all stress cases for Time Tracker Audit 3.2.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
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
		
		suite.addTestSuite(ValueContainerStressTest.class);
        suite.addTestSuite(PersistenceStressTest.class);
		
		return suite;
	}
}
