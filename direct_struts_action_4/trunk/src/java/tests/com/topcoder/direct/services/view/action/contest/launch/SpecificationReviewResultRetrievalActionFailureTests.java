/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for SpecificationReviewResultRetrievalAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class SpecificationReviewResultRetrievalActionFailureTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(SpecificationReviewResultRetrievalActionFailureTests.class);
    }

    /**
     * <p>
     * Tests SpecificationReviewResultRetrievalAction#executeAction() for failure.
     * It tests the case that when directServiceFacade is null and expects IllegalStateException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testExecuteAction_NullDirectServiceFacade() throws Exception {
        SpecificationReviewResultRetrievalAction instance = new SpecificationReviewResultRetrievalAction();
        instance.setModel(new AggregateDataModel());
        try {
            instance.executeAction();
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

}