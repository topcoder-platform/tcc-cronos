/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;

import org.w3c.dom.Element;

import com.cronos.im.ajax.handler.AbstractRequestHandler;
import com.cronos.im.ajax.handler.ReadManagerSessionMessageHandler;

/**
 * Failure test cases for the class ReadManagerSessionMessageHandler.
 *
 * @author waits
 * @version 1.0
 * @since Apr 6, 2007
 */
public class ReadManagerSessionMessageHandlerFailureTests extends BaseHandlerTestSupport {

	/**
	 * Create ReadManagerSessionMessage instance.
	 */
	protected Element createElement() throws Exception {
		return TestHelper.getElementFromString("<request type=\"ReadManagerSessionMessage\"><session_id>2</session_id></request>");
	}

	/**
	 * create ReadManagerSessionMessageHandler instance.
	 */
	protected AbstractRequestHandler createHandler() {
		return new ReadManagerSessionMessageHandler();
	}

}

