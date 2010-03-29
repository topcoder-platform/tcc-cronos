/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all stress test cases.
 * </p>
 * @author moon.river
 * @version 1.1
 */
public class StressTests extends TestCase {

    /**
     * Return all stress test cases.
     * @return all stress test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(UserServiceBeanTestsV11.suite());
        return suite;
    }

}
