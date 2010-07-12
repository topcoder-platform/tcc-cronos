/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.direct.ProjectBudget;

/**
 * <p>
 * Unit test for <code>ProjectBudgetModifyingAction</code> class.
 * </p>
 * @author zju_jay
 * @version 1.0
 */
public class ProjectBudgetModifyingActionTest extends BaseStrutsSpringTestCase {
    /**
     * Represents the action mapping name of the tested action.
     */
    private static final String ACTION_NAME = "/ProjectBudgetModifyingAction";

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
        request.setParameter("billingProjectId", "1");
        request.setParameter("changedAmount", "1.0");
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

        ProjectBudgetModifyingAction action = (ProjectBudgetModifyingAction) proxy.getAction();
        assertTrue("Should return a ProjectBudget", action.getResult() instanceof ProjectBudget);
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * Negative billingProjectId should be validated.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_Fail_NegativebillingProjectId() throws Exception {
        request.setParameter("billingProjectId", "-1");

        ActionProxy proxy = getActionProxy(ACTION_NAME);
        assertEquals("Should return input", Action.INPUT, proxy.execute());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * Zero billingProjectId should be validated.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_Fail_ZerobillingProjectId() throws Exception {
        request.setParameter("billingProjectId", "0");

        ActionProxy proxy = getActionProxy(ACTION_NAME);
        assertEquals("Should return input", Action.INPUT, proxy.execute());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method. Empty billingProjectId should be validated.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_Fail_NobillingProjectId() throws Exception {
        request.removeParameter("billingProjectId");

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
        TestHelper.assertSuperclass(ProjectBudgetModifyingAction.class,
            BaseDirectStrutsAction.class);
    }

    /**
     * <p>
     * Tests <code>ProjectBudgetModifyingAction()</code> method.
     * </p>
     * <p>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtor_Accuracy() {
        assertNotNull("instance should be created all the time.",
            new ProjectBudgetModifyingAction());
    }

    /**
     * <p>
     * Tests <code>getBillingProjectId()</code> method.
     * </p>
     * <p>
     * It should return 0, by default.
     * </p>
     */
    public void testGetBillingProjectId_default() {
        ProjectBudgetModifyingAction instance = new ProjectBudgetModifyingAction();
        assertEquals("Should be default to 0", 0, instance.getBillingProjectId());
    }

    /**
     * <p>
     * Tests <code>setBillingProjectId(long)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetBillingProjectId() {
        ProjectBudgetModifyingAction instance = new ProjectBudgetModifyingAction();
        long value = 1;
        instance.setBillingProjectId(value);

        assertEquals("Incorrect return value.", value, instance.getBillingProjectId());
    }

    /**
     * <p>
     * Tests <code>getChangedAmount()</code> method.
     * </p>
     * <p>
     * It should return 0, by default.
     * </p>
     */
    public void testGetChangedAmount_default() {
        ProjectBudgetModifyingAction instance = new ProjectBudgetModifyingAction();
        assertEquals("Should be default to 0", 0d, instance.getChangedAmount());
    }

    /**
     * <p>
     * Tests <code>setChangedAmount(double)</code> method.
     * </p>
     * <p>
     * Any value is valid, simply set internally.
     * </p>
     */
    public void testSetChangedAmount() {
        ProjectBudgetModifyingAction instance = new ProjectBudgetModifyingAction();
        double value = 1;
        instance.setChangedAmount(value);

        assertEquals("Incorrect return value.", value, instance.getChangedAmount());
    }

}
