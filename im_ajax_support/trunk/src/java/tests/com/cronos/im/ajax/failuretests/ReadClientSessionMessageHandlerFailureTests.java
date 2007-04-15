/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;

import org.w3c.dom.Element;

import com.cronos.im.ajax.handler.AbstractRequestHandler;
import com.cronos.im.ajax.handler.ReadClientSessionMessageHandler;

/**
 * Failure test cases for the class ReadClientSessionMessageHandler.
 *
 * @author waits
 * @version 1.0
 * @since Apr 6, 2007
 */
public class ReadClientSessionMessageHandlerFailureTests extends BaseHandlerTestSupport {

	/**
	 * create ReadClientSessionMessage element.
	 */
	protected Element createElement() throws Exception {
		return TestHelper.getElementFromString("<request type=\"ReadClientSessionMessage\"><session_id>2</session_id></request>");
	}

	/**
	 * Create ReadClientSessionMessageHandler instance.
	 */
	protected AbstractRequestHandler createHandler() {
		return new ReadClientSessionMessageHandler();
	}

}

