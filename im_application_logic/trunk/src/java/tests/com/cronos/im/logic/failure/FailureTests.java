/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.failure;

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
public class FailureTests extends TestCase {

    /**
     * All unit tests.
     * @return all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(IMServiceHandlerFailureTests.class);
        suite.addTestSuite(MessagePoolDetectorFailureTests.class);
        suite.addTestSuite(SessionStatusEventListenerFailureTests.class);
        suite.addTestSuite(UserSessionEventListenerFailureTests.class);
        suite.addTestSuite(UserStatusEventListenerFailureTests.class);
        return suite;
    }

}
