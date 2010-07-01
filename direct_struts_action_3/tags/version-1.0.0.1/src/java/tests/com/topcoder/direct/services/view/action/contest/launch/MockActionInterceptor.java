/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * <p>
 * The mock action interceptor used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockActionInterceptor extends AbstractInterceptor {

    /**
     * Intercepts the action and clears any field errors.
     *
     * @param actionInvocation the action invocation instance to intercept
     *
     * @return the action result after executing action
     *
     * @throws Exception if some error occurs during method execution
     */
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ((AbstractAction) actionInvocation.getAction()).clearFieldErrors();
        return actionInvocation.invoke();
    }
}
