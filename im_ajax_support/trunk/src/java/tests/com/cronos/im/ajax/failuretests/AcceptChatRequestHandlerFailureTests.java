/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;

import org.w3c.dom.Element;

import com.cronos.im.ajax.handler.AbstractRequestHandler;
import com.cronos.im.ajax.handler.AcceptChatRequestHandler;



/**
 * Failure Test cases for the class AcceptChatRequestHandler.
 *
 * @author waits
 * @version 1.0
 * @since Apr 6, 2007
 */
public class AcceptChatRequestHandlerFailureTests extends BaseHandlerTestSupport {

	/**
	 * create acceptChatRequest element.
	 */
	protected Element createElement() throws Exception{
		return TestHelper.getElementFromString("<request type=\"AcceptChatRequest\"><user_id>1</user_id><session_id>2</session_id></request>");
	}

	/**
	 * create instance.
	 * @throws AbstractRequestHandler it would be AcceptChatRequestHandler.
	 */
	protected AbstractRequestHandler createHandler() {
		return new AcceptChatRequestHandler();
	}
}
