/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.mock;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.web.reg.actions.profile.BaseProfileAction;

/**
 * <p>
 * This is mock authorization interceptor that makes login and set required mock DAOs.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MockInterceptor extends AbstractInterceptor {

    /**
     * <p>
     * Intercepts action invocation and provides needed mock managers before invocation.
     * </p>
     * @param actionInvocation the action invocation
     * @return actionInvocation.invoke()
     * @throws Exception if any error occurs
     */
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        MockFactory.initDAOs();
        BaseProfileAction action = (BaseProfileAction) actionInvocation.getAction();
        MockFactory.createUserInSession(action);
        // clear field errors
        action.clearFieldErrors();
        return actionInvocation.invoke();
    }
}
