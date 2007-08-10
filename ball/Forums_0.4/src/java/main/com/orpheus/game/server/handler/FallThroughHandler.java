/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import org.w3c.dom.Element;

/**
 * <p>This is a custom implementation of {@link Handler} interface which does nothing and always results in successful
 * execution of a handler. The purpose of this handler is to allow to handle the requests which do not involve any
 * handler but at the same time are not accessible directly. Currently the <code>Front Controller</code> component does
 * not allow to configure a single request processing chain without assigning at least 1 handler to it and this handler
 * is provided to bypass this limitation.</p>
 *
 * <p>Currently the <code>Message Polling</code> service is expected to be publicly available (due to issues with
 * plugins) but it can not be accessed directly. Therefore this handler is combined with <code>MessagePollResult</code>
 * into a single request processing chaning allowing the plugin requests to reach the <code>Message Polling</code>
 * service.</p>
 *
 * @link https://software.topcoder.com/forum/c_forum_message.jsp?f=23083612&r=25982531
 *
 * @author isv
 * @version 1.0
 */
public class FallThroughHandler implements Handler {

    /**
     * <p>Constructs new <code>FallThroughHandler</code> instance. This implementation does nothing.</p>
     */
    public FallThroughHandler() {
    }

    /**
     * <p>Constructs new <code>FallThroughHandler</code> instance. This implementation does nothing.</p>
     *
     * @param handlerElement an <code>Element</code> supplying the configuration for this handler. Ignored.
     */
    public FallThroughHandler(Element handlerElement) {
    }

    /**
     * <p>Process the user request. This implementation does nothing and always returns <code>true</code> which
     * corresponds to successful execution of this handler.</p>
     *
     * @param context the <code>ActionContext</code> object. Ignored.
     * @return <code>null</code> always.
     */
    public String execute(ActionContext context) {
        return null;
    }
}
