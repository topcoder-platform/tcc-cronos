/**
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax.accuracytests;

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
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AbstractRequestHandlerAccuracyTests.class);
        suite.addTestSuite(AcceptChatRequestHandlerAccuracyTests.class);
        suite.addTestSuite(ChangeManagerStatusHandlerAccuracyTests.class);
        suite.addTestSuite(IMAjaxSupportServletAccuracyTests.class);
        suite.addTestSuite(IMAjaxSupportUtilityAccuracyTests.class);
        suite.addTestSuite(PostTextMessageHandlerAccuracyTests.class);
        suite.addTestSuite(ReadClientSessionMessageHandlerAccuracyTests.class);
        suite.addTestSuite(ReadClientUserMessageHandlerAccuracyTests.class);
        suite.addTestSuite(ReadManagerSessionMessageHandlerAccuracyTests.class);
        suite.addTestSuite(ReadManagerUserMessageHandlerAccuracyTests.class);
        suite.addTestSuite(RequestHandlerManagerAccuracyTests.class);

        return suite;
    }
}
