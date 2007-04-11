/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.failuretests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author waits
 * @since Mar 29, 2007
 * @version 1.0
 */
public class FailureTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for com.cronos.im.persistence.failuretests");
		suite.addTestSuite(InformixRoleCategoryPersistenceFailureTests.class);
		suite.addTestSuite(InformixEntityStatusTrackerFailureTests.class);
		suite.addTestSuite(UnregisteredChatUserProfileInformixPersistenceFailureTests.class);
		suite.addTestSuite(InformixProfileKeyManagerFailureTests.class);
		suite.addTestSuite(RegisteredChatUserProfileInformixPersistenceFailuretests.class);
		suite.addTestSuite(CategoryFailureTests.class);
		return suite;
	}

}

