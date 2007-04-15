/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All failure test cases.
 * @author waits
 * @since Apr 8, 2007
 * @version 1.0
 */
public class FailureTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for com.cronos.im.ajax.failuretests");
		suite.addTestSuite(IMAjaxSupportUtilityFailureTests.class);
		suite.addTestSuite(ReadManagerUserMessageHandlerFailureTests.class);
		suite.addTestSuite(AcceptChatRequestHandlerFailureTests.class);
		suite.addTestSuite(ChangeManagerStatusHandlerFailureTests.class);
		suite.addTestSuite(ReadClientSessionMessageHandlerFailureTests.class);
		suite.addTestSuite(ReadManagerSessionMessageHandlerFailureTests.class);
		suite.addTestSuite(PostTextMessageHandlerFailureTests.class);
		suite.addTestSuite(RequestHandlerManagerFailureTests.class);
		suite.addTestSuite(ReadClientUserMessageHandlerFailureTests.class);
		suite.addTestSuite(IMAjaxSupportServletFailureTests.class);
		return suite;
	}

}

