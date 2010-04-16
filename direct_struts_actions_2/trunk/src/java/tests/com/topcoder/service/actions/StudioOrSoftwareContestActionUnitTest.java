/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;

/**
 * <p>
 * Unit tests for <code>StudioOrSoftwareContestAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StudioOrSoftwareContestActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private StudioOrSoftwareContestAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     * @throws Exception if an error occurs preparing the proxy
     */
    private ActionProxy prepareActionProxy() throws Exception {
        ActionProxy proxy = getActionProxy("/mockPayByCreditCardAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof PayByCreditCardAction);

        TestHelper.injectSessionMapIntoProxy(proxy);

        return proxy;
    }

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        TestHelper.cleanupEnvironment();
        instance = new MockStudioOrSoftwareContestAction();
        instance.prepare();
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        TestHelper.cleanupEnvironment();
        instance = null;
    }

    /**
     * Tests the class inheritance to make sure it is correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testInheritance() throws Exception {
        TestHelper.assertSuperclass(instance.getClass().getSuperclass(), ContestAction.class);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for executeAction method. No exception should occur.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Accuracy1() throws Exception {
        instance.executeAction();
    }

    /**
     * Failure test for executeAction method in struts environment. The project ID and contest ID are both
     * set, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_ProjectIdAndContestIdBothSet() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("projectId", 1);
        TestHelper.getFieldValues().put("contestId", 1);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestIdProjectId",
            "Either projectId or contestId must be > 0");
    }

    /**
     * Failure test for executeAction method in struts environment. Neither project ID or contest ID are set,
     * so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_ProjectIdAndContestIdNeitherSet() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("projectId", 0);
        TestHelper.getFieldValues().put("contestId", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestIdProjectId",
            "Either projectId or contestId must be > 0");
    }

    /**
     * Accuracy test for isStudioCompetition. Should return true since contest is a studio competition.
     */
    @Test
    public void test_isStudioCompetition_Accuracy1() {
        instance.setContestId(1);
        instance.setProjectId(0);
        assertTrue("wrong value returned from method", instance.isStudioCompetition());
    }

    /**
     * Accuracy test for isStudioCompetition. Should return false since contest is not a studio competition.
     */
    @Test
    public void test_isStudioCompetition_Accuracy2() {
        assertFalse("wrong value returned from method", instance.isStudioCompetition());
    }

    /**
     * Accuracy test for getProjectId. Verifies the returned value is correct.
     */
    @Test
    public void test_getProjectId_Accuracy() {
        instance = new MockStudioOrSoftwareContestAction();
        instance.prepare();
        assertEquals("incorrect default value", 0, instance.getProjectId());
        instance.setProjectId(1);
        assertEquals("incorrect value after setting", 1, instance.getProjectId());
    }

    /**
     * Accuracy Test for setProjectId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setProjectId_Accuracy() {
        instance.setProjectId(1);
        assertEquals("incorrect value after setting", 1, instance.getProjectId());
    }

    /**
     * Failure test for setProjectId method in struts environment. The value for the setter is less than 0, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setProjectId_LessThanZero() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("projectId", -1);
        TestHelper.getFieldValues().put("contestId", 0);

        // run the action and make sure validation worked properly
        assertEquals("wrong action result returned", "success", proxy.execute());
        assertTrue("there should be field errors", action.hasFieldErrors());
        assertEquals("wrong number of field errors returned", 2, action.getFieldErrors().size());

        assertEquals("wrong message in field errors", "projectId must be >= 0", action.getFieldErrors().get(
            "projectId").get(0));
        assertEquals("wrong message in field errors", "Either projectId or contestId must be > 0", action
            .getFieldErrors().get("contestIdProjectId").get(0));
    }

    /**
     * Accuracy test for getContestId. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestId_Accuracy() {
        instance = new MockStudioOrSoftwareContestAction();
        instance.prepare();
        assertEquals("incorrect default value", 0, instance.getContestId());
        instance.setContestId(1);
        assertEquals("incorrect value after setting", 1, instance.getContestId());
    }

    /**
     * Accuracy Test for setContestId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContestId_Accuracy() {
        instance.setContestId(1);
        assertEquals("incorrect value after setting", 1, instance.getContestId());
    }

    /**
     * Failure test for setContestId method in struts environment. The value for the setter is less than 0, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestId_LessThanZero() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByCreditCardAction action = (PayByCreditCardAction) proxy.getAction();

        TestHelper.getFieldValues().put("projectId", 0);
        TestHelper.getFieldValues().put("contestId", -1);

        // run the action and make sure validation worked properly
        assertEquals("wrong action result returned", "success", proxy.execute());
        assertTrue("there should be field errors", action.hasFieldErrors());
        assertEquals("wrong number of field errors returned", 2, action.getFieldErrors().size());

        assertEquals("wrong message in field errors", "contestId must be >= 0", action.getFieldErrors().get(
            "contestId").get(0));
        assertEquals("wrong message in field errors", "Either projectId or contestId must be > 0", action
            .getFieldErrors().get("contestIdProjectId").get(0));
    }

}
