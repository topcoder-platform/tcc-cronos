/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(RegistrationEntitiesToResourceConverterImplFailureTests.class);
        suite.addTestSuite(ResourceManagerRegistrationPersistenceFailureTests.class);
        suite.addTestSuite(ResourceManagerServiceBeanFailureTests.class);
        suite.addTestSuite(ResourceManagerServiceClientFailureTests.class);
        return suite;
    }

}
