/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.messaging.accuracytests;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Accuracy test cases for the component.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * <p>
     * Returns the consolidated test suite of all accuracy tests for the
     * component.
     * </p>
     *
     * @return the consolidated test suite of all accuracy tests for the
     *         component.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(TestMessageBoardManagementException.class);
        suite.addTestSuite(TestMessageBoardConfigurationException.class);
        suite.addTestSuite(TestMessageBoardPersistenceException.class);
        suite.addTestSuite(TestEntityNotFoundException.class);

        suite.addTestSuite(TestBasicEntityData.class);
        suite.addTestSuite(TestCommonEntityData.class);
        suite.addTestSuite(TestResponse.class);
        suite.addTestSuite(TestMessageAttribute.class);
        suite.addTestSuite(TestErrorMessagesCache.class);
        suite.addTestSuite(TestMessage.class);
        suite.addTestSuite(TestThread.class);

        return suite;
    }

}
