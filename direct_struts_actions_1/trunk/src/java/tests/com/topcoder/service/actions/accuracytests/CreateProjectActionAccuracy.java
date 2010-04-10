/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.accuracytests;

import com.topcoder.service.actions.CreateProjectAction;
import com.topcoder.service.project.ProjectData;

import junit.framework.TestCase;

import java.lang.reflect.Method;


/**
 * <p>
 * Accuracy test for <code>CreateProjectAction</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CreateProjectActionAccuracy extends TestCase {
    /**
     * Accuracy test for <code>executeAction()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testExecuteActionAccuracy() throws Exception {
        CreateProjectAction createProjectAction = new CreateProjectAction();

        createProjectAction.prepare();
        createProjectAction.setProjectServiceFacade(new MockProjectServiceFacade());
        createProjectAction.setProjectName("Test Project");
        createProjectAction.setProjectDescription("Test");

        Method m = createProjectAction.getClass()
                                      .getDeclaredMethod("executeAction",
                new Class[0]);
        m.setAccessible(true);

        // invoke.
        m.invoke(createProjectAction, new Object[0]);

        // verify the result
        Object result = createProjectAction.getResult();

        assertTrue("incorrect return type", result instanceof ProjectData);
    }
}
