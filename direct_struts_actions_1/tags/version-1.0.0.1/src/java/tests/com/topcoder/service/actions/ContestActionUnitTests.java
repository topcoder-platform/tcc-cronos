/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.facade.contest.ContestServiceFacade;

/**
 * <p>
 * Unit test for <code>{@link ContestAction}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class ContestActionUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>ContestAction</code> instance.
     * </p>
     */
    private ContestAction contestAction;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ContestActionUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        contestAction = new ContestAction() {

            /**
             * <p>
             * Represents the unique serial version id.
             * </p>
             */
            private static final long serialVersionUID = -4633766887769883177L;

            @Override
            protected void executeAction() throws Exception {
            }
        };

        contestAction.prepare();
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        contestAction = null;

        super.tearDown();
    }

    /**
     * <p>
     * Tests the <code>ContestAction()</code> constructor.
     * </p>
     * <p>
     * The constructor simply do nothing, instance should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("instance should not null", contestAction);

        assertTrue("invalid super class.", contestAction instanceof BaseDirectStrutsAction);
    }

    /**
     * <p>
     * Tests the <code>getContestServiceFacade()</code> method.
     * </p>
     * <p>
     * By default, The contestServiceFacade is null.
     * </p>
     */
    public void testGetContestServiceFacade_default() {
        assertNull("The contestServiceFacade should be null", contestAction.getContestServiceFacade());
    }

    /**
     * <p>
     * Tests the <code>setContestServiceFacade(ContestServiceFacade)</code> method.
     * </p>
     * <p>
     * If the contestServiceFacade is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetContestServiceFacade_null() {
        try {
            contestAction.setContestServiceFacade(null);

            fail("If the contestServiceFacade is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>setContestServiceFacade(ContestServiceFacade)</code> method.
     * </p>
     * <p>
     * If the contestServiceFacade is not null, should set internally.
     * </p>
     */
    public void testSetContestServiceFacade_accuracy() {
        ContestServiceFacade contestServiceFacade = new MockContestServiceFacade();
        contestAction.setContestServiceFacade(contestServiceFacade);

        assertSame("The contestServiceFacade is not set.", contestServiceFacade, contestAction
                .getContestServiceFacade());
    }

}
