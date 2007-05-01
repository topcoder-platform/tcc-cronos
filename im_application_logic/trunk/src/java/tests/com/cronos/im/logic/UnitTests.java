/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * All unit tests.
     * @return all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(IMConfigurationExceptionUnitTests.class);
        suite.addTestSuite(IMHelperUnitTests.class);
        suite.addTestSuite(IMLoggerUnitTests.class);
        suite.addTestSuite(MessagePoolDetectorUnitTests.class);
        suite.addTestSuite(IMServiceHandlerUnitTests.class);
        suite.addTestSuite(SessionStatusEventListenerUnitTests.class);
        suite.addTestSuite(UserSessionEventListenerUnitTests.class);
        suite.addTestSuite(UserStatusEventListenerUnitTests.class);
        suite.addTestSuite(Demo.class);

        return suite;
    }

}
