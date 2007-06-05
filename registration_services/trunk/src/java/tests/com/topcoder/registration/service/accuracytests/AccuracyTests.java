/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.registration.service.accuracytests;
 
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * 
 * @author TopCoder
 * @version 1.0
 */		
public class AccuracyTests extends TestCase {
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(RegistrationInfoImplTest.class);
        suite.addTestSuite(RegistrationPositionImplTest.class);
        suite.addTestSuite(RegistrationResultImplTest.class);
        suite.addTestSuite(RegistrationServicesImplTest.class);
        suite.addTestSuite(RemovalResultImplTest.class);
        return suite;
    }

}