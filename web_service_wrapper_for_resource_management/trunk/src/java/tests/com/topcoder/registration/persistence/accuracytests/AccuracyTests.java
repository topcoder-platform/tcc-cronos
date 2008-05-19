/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * @author waits
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

	/**
	 * All the accuracy test cases.
	 * @return Test to JUnit
	 */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(RegistrationEntitiesToResourceConverterImplAccTests.class);
        suite.addTestSuite(ResourceManagerServiceClientAccTests.class);
        return suite;
    }

}
