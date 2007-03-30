/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author mittu
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * Aggregates all failure test suites.
     *
     * @return aggregation of all failure test suites.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AskForChatMessageFailure.class);
        suite.addTestSuite(ChatMessageFailure.class);
        suite.addTestSuite(ChatMessageFormatterImplFailure.class);
        suite.addTestSuite(ChatMessageTrackerImplFailure.class);
        suite.addTestSuite(DateFormatContextFailure.class);
        suite.addTestSuite(EnterChatMessageFailure.class);
        suite.addTestSuite(FormatterLoaderFailure.class);
        suite.addTestSuite(MessengerImplFailure.class);
        suite.addTestSuite(PresenceMessageFailure.class);
        suite.addTestSuite(SessionUnAvailableMessageFailure.class);
        suite.addTestSuite(UserIDRetrieverImplFailure.class);

        return suite;
    }

}