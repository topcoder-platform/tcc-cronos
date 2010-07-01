/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.facade.direct.ContestPlan;
import com.topcoder.service.facade.direct.DirectServiceFacadeException;

/**
 * <p>
 * Unit tests for <code>GamePlanRetrievalAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GamePlanRetrievalActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private GamePlanRetrievalAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     * @throws Exception if an error occurs preparing the proxy
     */
    private ActionProxy prepareActionProxy() throws Exception {
        ActionProxy proxy = getActionProxy("/gamePlanRetrievalAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof GamePlanRetrievalAction);

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
        TestHelper.setupEnvironment();
        instance = new GamePlanRetrievalAction();
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
     */
    @Test
    public void testInheritance() {
        assertTrue("The inheritance is wrong", instance instanceof BaseDirectStrutsAction);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    @Test
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for executeAction. The correct game plan data should be stored in model.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void test_executeAction_Accuracy() throws Exception {
        instance.setDirectServiceFacade(new MockDirectServiceFacade());
        instance.setProjectId(1);

        // execute the action
        instance.executeAction();

        // validate that correct data is in model
        Object result = instance.getModel().getData("result");
        assertNotNull("result should not be null", result);
        assertTrue("result is not correct type", result instanceof List<?>);
        List<ContestPlan> data = (List<ContestPlan>) result;

        assertEquals("The number of contests is wrong", 3, data.size());
        for (int i = 1; i <= 3; ++i) {
            assertEquals("The name is wrong for contest #" + i, "contest" + i, data.get(i - 1).getName());
        }
    }

    /**
     * Failure test for executeAction. The <code>DirectServiceFacade</code> hasn't been injected,
     * so IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_MissingFacade() throws Exception {
        try {
            instance.executeAction();
            fail("IllegalStateException is expected");
        } catch (IllegalStateException e) {
            // make sure exception was stored in model
            Object result = instance.getResult();
            assertNotNull("result from model shouldn't be null", result);
            assertTrue("result from model is not correct type", result instanceof IllegalStateException);
        }
    }

    /**
     * Failure test for executeAction. There were errors fetching the game plan data,
     * so DirectServiceFacadeException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_ErrorFetchingData() throws Exception {
        try {
            instance.setDirectServiceFacade(new MockDirectServiceFacade());
            instance.setProjectId(999);
            instance.executeAction();
            fail("DirectServiceFacadeException is expected");
        } catch (DirectServiceFacadeException e) {
            // make sure exception was stored in model
            Object result = instance.getResult();
            assertNotNull("result from model shouldn't be null", result);
            assertTrue("result from model is not correct type", result instanceof DirectServiceFacadeException);
        }
    }

    /**
     * Accuracy test for getProjectId. Verifies the returned value is correct.
     */
    @Test
    public void test_getProjectId_Accuracy() {
        instance = new GamePlanRetrievalAction();
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
     * Failure test for setProjectId method in struts environment. The value for projectId is not positive, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setProjectId_ProjectIdNotPositive() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        GamePlanRetrievalAction action = (GamePlanRetrievalAction) proxy.getAction();

        TestHelper.getFieldValues().put("projectId", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "projectId",
            "projectId must be > 0");
    }
}
