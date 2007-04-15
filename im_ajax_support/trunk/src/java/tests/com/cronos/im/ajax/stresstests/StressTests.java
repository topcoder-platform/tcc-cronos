/*
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all stress test cases.</p>
 *
 * @author 80x86
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * Returns the test suite of all stress test cases.
     * </p>
     *
     * @return a test suite of all stress test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AcceptChatRequestHandlerStressTests.class);
        suite.addTestSuite(ChangeManagerStatusHandlerStressTests.class);
        suite.addTestSuite(IMAjaxSupportServletStressTests.class);
        suite.addTestSuite(PostTextMessageHandlerStressTests.class);
        suite.addTestSuite(ReadClientSessionMessageHandlerStressTests.class);
        suite.addTestSuite(ReadClientUserMessageHandlerStressTests.class);
        suite.addTestSuite(ReadManagerSessionMessageHandlerStressTests.class);
        suite.addTestSuite(ReadManagerUserMessageHandlerStressTests.class);
        suite.addTestSuite(RequestHandlerManagerStressTests.class);
        return suite;
    }
}
