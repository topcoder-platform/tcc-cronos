/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.failuretests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All failure test cases.
 * @author waits
 * @since Apr 11, 2007
 * @version 1.0
 */
public class FailureTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for com.topcoder.timetracker.contact.failuretests");
		suite.addTestSuite(CountryFailureTests.class);
		suite.addTestSuite(AddressFilterFactoryFailureTests.class);
		suite.addTestSuite(ContactFailureTests.class);
		suite.addTestSuite(InformixAddressDAOFailureTests.class);
		suite.addTestSuite(ContactBeanFailureTests.class);
		suite.addTestSuite(ContactManagerLocalDelegateFailureTests.class);
		suite.addTestSuite(ContactFilterFactoryFailureTests.class);
		suite.addTestSuite(StateFailureTests.class);
		suite.addTestSuite(InformixContactDAOFailureTests.class);
		suite.addTestSuite(AddressBeanFailureTests.class);
		suite.addTestSuite(AddressFailureTests.class);
		suite.addTestSuite(AddressManagerLocalDelegateFailureTests.class);
		return suite;
	}

}

