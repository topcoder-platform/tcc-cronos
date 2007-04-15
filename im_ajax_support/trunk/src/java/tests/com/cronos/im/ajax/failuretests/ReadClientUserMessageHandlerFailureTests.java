/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;

import org.w3c.dom.Element;

import com.cronos.im.ajax.handler.AbstractRequestHandler;
import com.cronos.im.ajax.handler.ReadClientUserMessageHandler;

/**
 * Failure test cases for the class ReadClientUserMessageHandler.
 *
 * @author waits
 * @version 1.0
 * @since Apr 6, 2007
 */
public class ReadClientUserMessageHandlerFailureTests extends BaseHandlerTestSupport {

	/**
	 * create ReadClientUserMessage instance.
	 */
	protected Element createElement() throws Exception {
		return TestHelper.getElementFromString("<request type=\"ReadClientUserMessage\"><session_id>2</session_id></request>");
	}

	/**
	 * Create ReadClientUserMessageHandler instance.
	 */
	protected AbstractRequestHandler createHandler() {
		return new ReadClientUserMessageHandler();
	}

}

