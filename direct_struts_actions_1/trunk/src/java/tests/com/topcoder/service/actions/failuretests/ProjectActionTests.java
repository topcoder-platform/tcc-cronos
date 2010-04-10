/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import junit.framework.TestCase;

import com.topcoder.service.actions.CreateProjectAction;
import com.topcoder.service.actions.ProjectAction;


/**
 * Failure cases of ProjectAction.
 *
 * @author onsky
 * @version 1.0
 */
public class ProjectActionTests extends TestCase {
    /**
     * <p>Represents ProjectAction instance for testing.</p>
     */
    private ProjectAction instance;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new CreateProjectAction();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Failure test for method setProjectServiceFacade.
     */
    public void test_setProjectServiceFacade() {
        try {
            instance.setProjectServiceFacade(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
