/**
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved UnitTests.java
 */
package com.cronos.im.messenger.accuracytests;


import com.cronos.im.messenger.accuracytests.formatterimpl.ChatMessageFormatterImplAccuracyTest;
import com.cronos.im.messenger.accuracytests.impl.ChatMessageTrackerImplAccuracyTest;
import com.cronos.im.messenger.accuracytests.impl.MessengerImplAccuracyTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
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

        suite.addTestSuite(AskForChatMessageAccuracyTest.class);
        suite.addTestSuite(ChatMessageAccuracyTest.class);
        suite.addTestSuite(ChatMessageFormattingExceptionAccuracyTest.class);
        suite.addTestSuite(DateFormatContextAccuracyTest.class);
        suite.addTestSuite(EnterChatMessageAccuracyTest.class);
        suite.addTestSuite(FormatterConfigurationExceptionAccuracyTest.class);
        suite.addTestSuite(FormatterLoaderAccuracyTest.class);
        suite.addTestSuite(MessageTrackerExceptionAccuracyTest.class);
        suite.addTestSuite(MessengerExceptionAccuracyTest.class);
        suite.addTestSuite(PresenceMessageAccuracyTest.class);
        suite.addTestSuite(SessionUnavailableMessageAccuracyTest.class);
        suite.addTestSuite(UserIDRetrieverExceptionAccuracyTest.class);        
        suite.addTestSuite(ChatMessageTrackerImplAccuracyTest.class);
        suite.addTestSuite(ChatMessageFormatterImplAccuracyTest.class);
        suite.addTestSuite(MessengerImplAccuracyTest.class);
        suite.addTestSuite(UserIDRetrieverExceptionAccuracyTest.class);
        
        return suite;
    }
}
