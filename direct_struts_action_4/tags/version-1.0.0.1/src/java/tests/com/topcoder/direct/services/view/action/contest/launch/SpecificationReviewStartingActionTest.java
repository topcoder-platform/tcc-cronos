/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * Unit test for <code>SpecificationReviewStartingAction</code> class.
 * </p>
 * @author zju_jay
 * @version 1.0
 */
public class SpecificationReviewStartingActionTest extends BaseStrutsSpringTestCase {
    /**
     * Represents the action mapping name of the tested action.
     */
    private static final String ACTION_NAME = "/SpecificationReviewStartingAction";

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        request.getSession().setAttribute("user", new TCSubject(1L));
        request.setParameter("contestId", "1");
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_Accuracy() throws Exception {
        ActionProxy proxy = getActionProxy(ACTION_NAME);
        assertEquals("Should return success", Action.SUCCESS, proxy.execute());

        SpecificationReviewStartingAction action =
            (SpecificationReviewStartingAction) proxy.getAction();
        assertNull("Should be no result and exception.", action.getResult());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * Negative contestId should be validated.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_Fail_NegativeContestId() throws Exception {
        request.setParameter("contestId", "-1");

        ActionProxy proxy = getActionProxy(ACTION_NAME);
        assertEquals("Should return input", Action.INPUT, proxy.execute());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * Zero contestId should be validated.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_Fail_ZeroContestId() throws Exception {
        request.setParameter("contestId", "0");

        ActionProxy proxy = getActionProxy(ACTION_NAME);
        assertEquals("Should return input", Action.INPUT, proxy.execute());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method. Empty contestId should be validated.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_Fail_NoContestId() throws Exception {
        request.removeParameter("contestId");

        ActionProxy proxy = getActionProxy(ACTION_NAME);
        assertEquals("Should return input", Action.INPUT, proxy.execute());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * If ContestServiceFacade is not injected, IllegalStateException should be thrown.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_Fail_NullContestServiceFacade() throws Exception {
        try {
            ActionProxy proxy = getActionProxy(ACTION_NAME + "_NULL");
            proxy.execute();

            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // good
        }
    }

    /**
     * Tests the class inheritance to make sure it is correct.
     * @throws Exception to JUnit
     */
    public void testInheritance() throws Exception {
        TestHelper.assertSuperclass(SpecificationReviewStartingAction.class,
            BaseDirectStrutsAction.class);
    }

    /**
     * <p>
     * Tests <code>SpecificationReviewStartingAction()</code> method.
     * </p>
     * <p>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtor_Accuracy() {
        assertNotNull("instance should be created all the time.",
            new SpecificationReviewStartingAction());
    }

    /**
     * <p>
     * Tests <code>getContestId()</code> method.
     * </p>
     * <p>
     * It should return 0, by default.
     * </p>
     */
    public void testGetContestId_default() {
        SpecificationReviewStartingAction instance = new SpecificationReviewStartingAction();
        assertEquals("Should be default to 0", 0, instance.getContestId());
    }

    /**
     * <p>
     * Tests <code>setContestId(long)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetContestId() {
        SpecificationReviewStartingAction instance = new SpecificationReviewStartingAction();
        long value = 1;
        instance.setContestId(value);

        assertEquals("Incorrect return value.", value, instance.getContestId());
    }

}
