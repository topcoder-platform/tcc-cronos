/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.logic.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.TestResult;

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
        suite.addTest(IMLoggerTest.suite());
        suite.addTest(IMServiceHandlerTest.suite());
        suite.addTest(MessagePoolDetectorTest.suite());
        suite.addTest(SessionStatusEventListenerTest.suite());
        suite.addTest(UserSessionEventListenerTest.suite());
        suite.addTest(UserStatusEventListenerTest.suite());
        return suite;
    }

}
