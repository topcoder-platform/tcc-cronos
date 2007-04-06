/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.accuracytests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 
 * Accuracy test cases.
 * 
 * @author waits
 * @since Apr 1, 2007
 * @version 1.0
 */
public class AccuracyTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for com.topcoder.timetracker.entry.base.accuracytests");
		suite.addTestSuite(InformixCutoffTimeDaoAccuracyTests.class);
		suite.addTestSuite(EntryDelegateAccuracyTests.class);
		suite.addTestSuite(EntrySessionBeanAccuracyTests.class);
		return suite;
	}

}

