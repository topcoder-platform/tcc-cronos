/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.failuretests;

import org.w3c.dom.Element;

import com.cronos.im.ajax.handler.AbstractRequestHandler;
import com.cronos.im.ajax.handler.ChangeManagerStatusHandler;

/**
 *
 * Failure test case for the class ChangeManagerStatusHandler.
 * @author waits
 * @since Apr 7, 2007
 * @version 1.0
 */
public class ChangeManagerStatusHandlerFailureTests extends BaseHandlerTestSupport {

	/**
	 * create ChangeManagerStatus element
	 */
	protected Element createElement() throws Exception {
		return TestHelper.getElementFromString("<request type=\"ChangeManagerStatus\"><status>live</status></request>");
	}

	/**
	 * Create ChangeManagerStatusHandler instance.
	 */
	protected AbstractRequestHandler createHandler() {
		return new ChangeManagerStatusHandler();
	}

}

