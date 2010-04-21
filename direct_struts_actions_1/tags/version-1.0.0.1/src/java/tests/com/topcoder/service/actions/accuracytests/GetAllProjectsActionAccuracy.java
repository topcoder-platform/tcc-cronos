/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.accuracytests;

import com.topcoder.service.actions.GetAllBillingProjectsAction;
import com.topcoder.service.actions.GetAllProjectsAction;

import junit.framework.TestCase;

import java.lang.reflect.Method;

import java.util.List;


/**
 * <p>
 * Accuracy test for <code>GetAllProjectsAction</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetAllProjectsActionAccuracy extends TestCase {
    /**
     * Accuracy test for <code>executeAction()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testExecuteActionAccuracy() throws Exception {
        GetAllProjectsAction action = new GetAllProjectsAction();

        action.prepare();
        action.setProjectServiceFacade(new MockProjectServiceFacade());

        Method m = action.getClass()
                         .getDeclaredMethod("executeAction", new Class[0]);
        m.setAccessible(true);

        // invoke.
        m.invoke(action, new Object[0]);

        // verify the result
        Object result = action.getResult();
        assertTrue("incorrect return type", result instanceof List<?>);
    }
}
