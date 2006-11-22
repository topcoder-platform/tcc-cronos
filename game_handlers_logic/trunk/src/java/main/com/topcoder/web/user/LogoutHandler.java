/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.user;

import org.w3c.dom.Element;

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * <code>LogoutHandler</code> implements the <code>Handler</code> interface
 * from the FrontController component, this handler simply logs the user out by
 * invalidating the user's session.
 * </p>
 * <p>
 * No matter the user has already logged in or not, this handler will always
 * invalidate the session.
 * </p>
 * <p>
 * <strong>Thread-safety: </strong>This class is thread-safe since it is
 * stateless.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class LogoutHandler implements Handler {

    /**
     * <p>
     * Empty constructor.
     * </p>
     *
     */
    public LogoutHandler() {

    }

    /**
     * <p>
     * Empty constructor.
     * </p>
     * <p>
     * NOTE: the argument is simply ignored and not checked.
     * </p>
     *
     *
     *
     * @param handlerElement
     *            the xml Element object, ignored by this ctor.
     */
    public LogoutHandler(Element handlerElement) {

    }

    /**
     * <p>
     * Process the logout request, and simply logs user out by invalidating the
     * session no matter the user has logged in or not.
     * </p>
     *
     *
     * @param context
     *            the ActionContext object wrapping the context info.
     * @return null always.
     * @throws IllegalArgumentException
     *             if the context is null.
     * @throws HandlerExecutionException
     *             this exception is never thrown in this method.
     */
    public String execute(ActionContext context) {
        Helper.checkNull(context, "context");
        context.getRequest().getSession(true).invalidate();
        return null;
    }
}
