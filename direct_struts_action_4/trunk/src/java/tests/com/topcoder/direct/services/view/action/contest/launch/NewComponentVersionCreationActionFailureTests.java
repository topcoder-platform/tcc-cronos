/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for NewComponentVersionCreationAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class NewComponentVersionCreationActionFailureTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(NewComponentVersionCreationActionFailureTests.class);
    }

    /**
     * <p>
     * Tests NewComponentVersionCreationAction#executeAction() for failure.
     * It tests the case that when directServiceFacade is null and expects IllegalStateException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testExecuteAction_NullDirectServiceFacade() throws Exception {
        NewComponentVersionCreationAction instance = new NewComponentVersionCreationAction();
        instance.setModel(new AggregateDataModel());
        try {
            instance.executeAction();
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

}