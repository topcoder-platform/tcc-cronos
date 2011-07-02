/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.profilecompleteness.actions.mock;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.web.reg.profilecompleteness.actions.BaseUnitTest;
import com.topcoder.web.reg.profilecompleteness.actions.CompleteProfileAction;

/**
 * <p>
 * This is mock authorization interceptor that makes login and set required mock DAOs.
 * </p>
 * @author sokol
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
        CompleteProfileAction action = (CompleteProfileAction) actionInvocation.getAction();
        // prepare CompleteProfileAction action DAOS
        MockFactory.initDAOs();
        // set session attribute
        BaseUnitTest.setSessionAttribute(ServletActionContext.getRequest(),
                (String) BaseUnitTest.getFieldValue("incomingRequestURIKey", action, CompleteProfileAction.class),
                ServletActionContext.getRequest().getContextPath() + MockFactory.REDIRECTED_URL);
        // before action
        if (actionInvocation.getProxy().getActionName().startsWith("getUserProfile")) {
            MockFactory.populateCompleteProfileAction(action, false);
        }
        // clear field errors
        action.clearFieldErrors();
        String result = actionInvocation.invoke();
        // after action
        if (result == null && actionInvocation.getProxy().getActionName().startsWith("complete")) {
            // set user's fields that were modified
            MockFactory.updateFields(action);
        }
        return result;
    }
}
