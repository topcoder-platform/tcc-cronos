/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;

import org.w3c.dom.Element;

import com.cronos.im.ajax.handler.AbstractRequestHandler;
import com.cronos.im.ajax.handler.ReadManagerUserMessageHandler;

/**
 * Failure test cases for the class ReadManagerUserMessageHandler.
 *
 * @author waits
 * @version 1.0
 * @since Apr 6, 2007
 */
public class ReadManagerUserMessageHandlerFailureTests extends BaseHandlerTestSupport {

	/**
	 * Create ReadManagerUserMessage instance.
	 */
	protected Element createElement() throws Exception {
		return TestHelper.getElementFromString("<request type=\"ReadManagerUserMessage\"></request>");
	}

	/**
	 * Create ReadManagerUserMessageHandler instance.
	 */
	protected AbstractRequestHandler createHandler() {
		return new ReadManagerUserMessageHandler();
	}

}

