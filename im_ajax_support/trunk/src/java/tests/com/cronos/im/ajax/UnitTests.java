/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.cronos.im.ajax;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.cronos.im.ajax.handler.AbstractRequestHandlerUnitTests;
import com.cronos.im.ajax.handler.AcceptChatRequestHandlerUnitTests;
import com.cronos.im.ajax.handler.ChangeManagerStatusHandlerUnitTests;
import com.cronos.im.ajax.handler.PostTextMessageHandlerUnitTests;
import com.cronos.im.ajax.handler.ReadClientSessionMessageHandlerUnitTests;
import com.cronos.im.ajax.handler.ReadClientUserMessageHandlerUnitTests;
import com.cronos.im.ajax.handler.ReadManagerSessionMessageHandlerUnitTests;
import com.cronos.im.ajax.handler.ReadManagerUserMessageHandlerUnitTests;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(IMAjaxConfigurationExceptionUnitTests.class);
        suite.addTestSuite(IMAjaxSupportUtilityUnitTests.class);
        suite.addTestSuite(RequestHandlerManagerUnitTests.class);
        suite.addTestSuite(IMAjaxSupportServletUnitTests.class);
        suite.addTestSuite(IMHelperUnitTests.class);

        suite.addTestSuite(AbstractRequestHandlerUnitTests.class);
        suite.addTestSuite(AcceptChatRequestHandlerUnitTests.class);
        suite.addTestSuite(ChangeManagerStatusHandlerUnitTests.class);
        suite.addTestSuite(PostTextMessageHandlerUnitTests.class);
        suite.addTestSuite(ReadClientSessionMessageHandlerUnitTests.class);
        suite.addTestSuite(ReadClientUserMessageHandlerUnitTests.class);
        suite.addTestSuite(ReadManagerSessionMessageHandlerUnitTests.class);
        suite.addTestSuite(ReadManagerUserMessageHandlerUnitTests.class);

        suite.addTestSuite(Demo.class);

        return suite;
    }

}
