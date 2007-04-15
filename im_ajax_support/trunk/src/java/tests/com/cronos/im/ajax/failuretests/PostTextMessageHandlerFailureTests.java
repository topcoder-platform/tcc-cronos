/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;

import org.w3c.dom.Element;

import com.cronos.im.ajax.handler.AbstractRequestHandler;
import com.cronos.im.ajax.handler.PostTextMessageHandler;

/**
 * Failure test cases for the class PostTextMessageHandler.
 *
 * @author waits
 * @version 1.0
 * @since Apr 6, 2007
 */
public class PostTextMessageHandlerFailureTests extends BaseHandlerTestSupport {

	/**
	 * Create PostTextMessage instance.
	 */
	protected Element createElement() throws Exception {
		return TestHelper.getElementFromString("<request type=\"PostTextMessage\"><session_id>2</session_id><chat_text>Hello, this is ivern.</chat_text></request>");
	}

	/**
	 * Create PostTextMessageHandler instance.
	 */
	protected AbstractRequestHandler createHandler() {
		return new PostTextMessageHandler();
	}

}

