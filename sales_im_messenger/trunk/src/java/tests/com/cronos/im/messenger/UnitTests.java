/**
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.messenger;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author marius_neo
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // Exceptions
        suite.addTestSuite(MessengerExceptionTestCase.class);
        suite.addTestSuite(MessageTrackerExceptionTestCase.class);
        suite.addTestSuite(UserIDRetrieverExceptionTestCase.class);
        suite.addTestSuite(ChatMessageFormattingExceptionTestCase.class);
        suite.addTestSuite(FormatterConfigurationExceptionTestCase.class);

        // Utility classes
        suite.addTestSuite(HelperTestCase.class);
        suite.addTestSuite(DateFormatTestCase.class);
        suite.addTestSuite(FormatterLoaderTestCase.class);
        suite.addTestSuite(ChatMessageFormatterImplTestCase.class);

        // XML Messages
        suite.addTestSuite(XMLMessageHelperTestCase.class);
        suite.addTestSuite(EnterChatMessageTestCase.class);
        suite.addTestSuite(SessionUnavailableMessageTestCase.class);
        suite.addTestSuite(PresenceMessageTestCase.class);
        suite.addTestSuite(AskForChatMessageTestCase.class);
        suite.addTestSuite(ChatMessageTestCase.class);

        // Classes from com.cronos.im.messenger.formatterimpl package
        suite.addTestSuite(ChatMessageTrackerImplTestCase.class);
        suite.addTestSuite(MessengerImplTestCase.class);
        suite.addTestSuite(UserIDRetrieverImplTestCase.class);

        // The demo for the component
        suite.addTestSuite(DemoTestCase.class);

        return suite;
    }

}
