/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * Unit test for <code>ContestsLinkingAction</code> class.
 * </p>
 * @author zju_jay
 * @version 1.0
 */
public class ContestsLinkingActionTest extends BaseStrutsSpringTestCase {
    /**
     * Represents the action mapping name of the tested action.
     */
    private static final String ACTION_NAME = "/ContestsLinkingAction";

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
        request.setParameter("parentProjectIds", "1");
        request.setParameter("childProjectIds", new String[] {"2", "3" });
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

        ContestsLinkingAction action = (ContestsLinkingAction) proxy.getAction();

        long[] parentIds = action.getParentProjectIds();
        assertEquals("length should be one.", 1, parentIds.length);

        long[] childIds = action.getChildProjectIds();
        assertEquals("length should be two.", 2, childIds.length);
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
     * Tests the <code>executeAction()</code> method. Null parentProjectIds is acceptable.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_Accuracy_NullParentProjectIds() throws Exception {
        request.removeParameter("parentProjectIds");

        ActionProxy proxy = getActionProxy(ACTION_NAME);
        assertEquals("Should return success", Action.SUCCESS, proxy.execute());

        ContestsLinkingAction action = (ContestsLinkingAction) proxy.getAction();
        long[] parentIds = action.getParentProjectIds();
        assertNull("parentProjectIds should be null.", parentIds);
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * If ParentProjectIds contains non-positive elements, validation fails.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_Fail_NonPositiveParentProjectIds() throws Exception {
        request.setParameter("parentProjectIds", new String[] {"1", "0" });

        ActionProxy proxy = getActionProxy(ACTION_NAME);
        assertEquals("Should return input", Action.INPUT, proxy.execute());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method. Null childProjectIds is acceptable.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_Accuracy_NullchildProjectIds() throws Exception {
        request.removeParameter("childProjectIds");

        ActionProxy proxy = getActionProxy(ACTION_NAME);
        assertEquals("Should return success", Action.SUCCESS, proxy.execute());

        ContestsLinkingAction action = (ContestsLinkingAction) proxy.getAction();
        long[] childIds = action.getChildProjectIds();
        assertNull("childProjectIds should be null.", childIds);
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * If ChildProjectIds contains non-positive elements, validation fails.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_Fail_NonPositiveChildProjectIds() throws Exception {
        request.setParameter("childProjectIds", new String[] {"1", "0" });

        ActionProxy proxy = getActionProxy(ACTION_NAME);
        assertEquals("Should return input", Action.INPUT, proxy.execute());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * If DirectServiceFacade is not injected, IllegalStateException should be thrown.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_Fail_NullDirectServiceFacade() throws Exception {
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
        TestHelper.assertSuperclass(ContestsLinkingAction.class, BaseDirectStrutsAction.class);
    }

    /**
     * <p>
     * Tests <code>ContestsLinkingAction()</code> method.
     * </p>
     * <p>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtor_Accuracy() {
        assertNotNull("instance should be created all the time.", new ContestsLinkingAction());
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
        ContestsLinkingAction instance = new ContestsLinkingAction();
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
        ContestsLinkingAction instance = new ContestsLinkingAction();
        long value = 1;
        instance.setContestId(value);

        assertEquals("Incorrect return value.", value, instance.getContestId());
    }

    /**
     * <p>
     * Tests <code>getParentProjectIds()</code> method.
     * </p>
     * <p>
     * It should return null, by default.
     * </p>
     */
    public void testGetParentProjectIds_default() {
        ContestsLinkingAction instance = new ContestsLinkingAction();
        assertEquals("Should be default to null", null, instance.getParentProjectIds());
    }

    /**
     * <p>
     * Tests <code>setParentProjectIds(long[])</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetParentProjectIds() {
        ContestsLinkingAction instance = new ContestsLinkingAction();
        long[] value = null;
        instance.setParentProjectIds(value);

        assertEquals("Incorrect return value.", value, instance.getParentProjectIds());
    }

    /**
     * <p>
     * Tests <code>getChildProjectIds()</code> method.
     * </p>
     * <p>
     * It should return null, by default.
     * </p>
     */
    public void testGetChildProjectIds_default() {
        ContestsLinkingAction instance = new ContestsLinkingAction();
        assertEquals("Should be default to null", null, instance.getChildProjectIds());
    }

    /**
     * <p>
     * Tests <code>setChildProjectIds(long[])</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetChildProjectIds() {
        ContestsLinkingAction instance = new ContestsLinkingAction();
        long[] value = new long[] {1 };
        instance.setChildProjectIds(value);

        assertEquals("Incorrect return value.", value, instance.getChildProjectIds());
    }
}
