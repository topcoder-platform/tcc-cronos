/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * @author myxgyy
 * @version 1.1
 */
public class AccuracyTests extends TestCase {

    /**
     * Return all accuracy test cases.
     * @return all accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(UserServiceBeanAccTestsV11.suite());
        return suite;
    }

}
