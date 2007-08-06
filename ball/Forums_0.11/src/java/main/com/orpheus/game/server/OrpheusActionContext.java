/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server;

import com.topcoder.web.frontcontroller.ActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * An <code>ActionContext</code> implementation that permits the request object it
 * wraps to be changed.  This is intended to be used only by replacing the original
 * request with another that wraps it; inserting an unrelated request may have
 * unexpected results
 */
public class OrpheusActionContext extends ActionContext {

    /**
     * An ActionContext that provides most of the underlying behavior of this context
     */
    private final ActionContext innerContext;

    /**
     * The request object that will be returned by this context
     */
    private HttpServletRequest request;

    /**
     * Initializes a new <code>OrpheusActionContext</code> based on the specified one
     *
     * @param  context another <code>ActionContext</code> providing the base data for
     *         this one, including the request object that this context will initially
     *         provide
     */
    OrpheusActionContext(ActionContext context) {
        super(context.getRequest(), context.getResponse());
        innerContext = context;
        request = context.getRequest();
    }

    /**
     * {@inheritDoc}
     */
    public Object getAttribute(String key) {
        return innerContext.getAttribute(key);
    }

    /**
     * {@inheritDoc}
     */
    public Map getAttributes() {
        return innerContext.getAttributes();
    }

    /**
     * {@inheritDoc}.  The request object to be provided can be altered via
     * {@link #setRequest(HttpServletRequest request)}
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Sets the request object that this context should subsequently provide via its
     * {@link #getRequest()} method.  This method should be exercised only with
     * great care.
     *
     * @param  request the <code>HttpServletRequest</code> to be provided
     */
    public void setRequest(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("The request must not be null");
        } else {
            this.request = request;
        }
    }

    /**
     * {@inheritDoc}
     */
    public HttpServletResponse getResponse() {
        return innerContext.getResponse();
    }

    /**
     * {@inheritDoc}
     */
    public void setAttribute(String key, Object value) {
        innerContext.setAttribute(key, value);
    }
}

