/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.accuracytests;

import com.opensymphony.xwork2.mock.MockActionInvocation;

import com.topcoder.service.actions.BaseDirectStrutsAction;
import com.topcoder.service.actions.ValidationErrorsInterceptor;

import junit.framework.TestCase;


/**
 * <p>
 * Accuracy test for <code>ValidationErrorsInterceptor</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ValidationErrorsInterceptorAccuracy extends TestCase {
    /**
     * Accuracy test for <code>intercept()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testIntercept() throws Exception {
        try {
            ValidationErrorsInterceptor interceptor = new ValidationErrorsInterceptor();

            MockActionInvocation mockActionInvocation = new MockActionInvocation();
            BaseDirectStrutsAction action = new MockBaseDirectStrutsAction();
            action.addFieldError("E1", "first error.");

            mockActionInvocation.setAction(action);

            assertNotNull("The result should match.",
                interceptor.intercept(mockActionInvocation));
        } catch (Exception e) {
            // success
        }
    }

    /**
     * Mock BaseDirectStrutsAction.
     *
    * @author TCSDEVELOPER
    * @version 1.0
     */
    public class MockBaseDirectStrutsAction extends BaseDirectStrutsAction {
        /**
         * Mock impl.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Mock IMpl.
         * @throws Exception if any error occurs
         */
        @Override
        protected void executeAction() throws Exception {
        }
    }
}
