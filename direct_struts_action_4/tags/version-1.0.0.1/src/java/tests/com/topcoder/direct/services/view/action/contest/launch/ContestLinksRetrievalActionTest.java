/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.management.project.Project;

/**
 * <p>
 * Unit test for <code>ContestLinksRetrievalAction</code> class.
 * </p>
 * @author zju_jay
 * @version 1.0
 */
public class ContestLinksRetrievalActionTest extends BaseStrutsSpringTestCase {
    /**
     * Represents the action mapping name of the tested action.
     */
    private static final String ACTION_NAME = "/ContestLinksRetrievalAction";

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        request.setParameter("contestId", "1");
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testExecuteAction_Accuracy() throws Exception {

        ActionProxy proxy = getActionProxy(ACTION_NAME);
        assertEquals("Should return success", Action.SUCCESS, proxy.execute());

        ContestLinksRetrievalAction action = (ContestLinksRetrievalAction) proxy.getAction();

        Object result = action.getResult();
        assertTrue("Should return a map", result instanceof Map);

        Map < String, Object > map = (Map < String, Object >) result;
        List < Project > parentProjects = (List < Project >) map.get("parentProjects");
        assertTrue("should return parentProjects.", parentProjects.size() == 1
            && parentProjects.get(0).getTcDirectProjectName().equals("parent"));

        List < Project > childProjects = (List < Project >) map.get("childProjects");
        assertTrue("should return childProjects.", childProjects.size() == 1
            && childProjects.get(0).getTcDirectProjectName().equals("child"));
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
        TestHelper
            .assertSuperclass(ContestLinksRetrievalAction.class, BaseDirectStrutsAction.class);
    }

    /**
     * <p>
     * Tests <code>ContestLinksRetrievalAction()</code> method.
     * </p>
     * <p>
     * Instance should be created successfully.
     * </p>
     */
    public void testCtor_Accuracy() {
        assertNotNull("instance should be created all the time.", new ContestLinksRetrievalAction());
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
        ContestLinksRetrievalAction instance = new ContestLinksRetrievalAction();
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
        ContestLinksRetrievalAction instance = new ContestLinksRetrievalAction();
        long value = 1;
        instance.setContestId(value);

        assertEquals("Incorrect return value.", value, instance.getContestId());
    }

}
