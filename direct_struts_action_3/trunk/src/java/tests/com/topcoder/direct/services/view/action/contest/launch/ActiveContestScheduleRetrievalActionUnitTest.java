/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.facade.direct.ContestSchedule;
import com.topcoder.service.facade.direct.DirectServiceFacadeException;

/**
 * <p>
 * Unit tests for <code>ActiveContestScheduleRetrievalAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ActiveContestScheduleRetrievalActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private ActiveContestScheduleRetrievalAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     * @throws Exception if an error occurs preparing the proxy
     */
    private ActionProxy prepareActionProxy() throws Exception {
        ActionProxy proxy = getActionProxy("/activeContestScheduleRetrievalAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof ActiveContestScheduleRetrievalAction);

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
        instance = new ActiveContestScheduleRetrievalAction();
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
     * Accuracy test for executeAction. The contest schedule should be retrieved properly.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Accuracy() throws Exception {
        // prepare the instance for studio contest
        instance.setDirectServiceFacade(new MockDirectServiceFacade());
        instance.setContestId(1);
        instance.setStudio(true);

        // execute the action
        instance.executeAction();

        // validate that correct data is in model
        Object result = instance.getModel().getData("result");
        assertNotNull("result should not be null", result);
        assertTrue("result is not correct type", result instanceof ContestSchedule);

        // validate that contest schedule is correct
        ContestSchedule schedule = (ContestSchedule) result;
        assertEquals("wrong contest ID for schedule", 1, schedule.getContestId());
        assertEquals("wrong value for isStudio", true, schedule.isStudio());

        // prepare the instance for software contest
        instance.setContestId(2);
        instance.setStudio(false);

        // execute the action
        instance.executeAction();

        // validate that correct data is in model
        result = instance.getModel().getData("result");
        assertNotNull("result should not be null", result);
        assertTrue("result is not correct type", result instanceof ContestSchedule);

        // validate that contest schedule is correct
        schedule = (ContestSchedule) result;
        assertEquals("wrong contest ID for schedule", 2, schedule.getContestId());
        assertEquals("wrong value for isStudio", false, schedule.isStudio());

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
     * Failure test for executeAction. An error occurred getting the contest,
     * so DirectServiceFacadeException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_DirectServiceFacadeException() throws Exception {
        try {
            instance.setDirectServiceFacade(new MockDirectServiceFacade());
            instance.setContestId(999);
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
     * Accuracy test for getContestId. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestId_Accuracy() {
        instance = new ActiveContestScheduleRetrievalAction();
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
     * Failure test for setContestId method in struts environment. The value for contestId is not positive, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setContestId_ContestIdNotPositive() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestScheduleRetrievalAction action = (ActiveContestScheduleRetrievalAction) proxy.getAction();

        TestHelper.getFieldValues().put("contestId", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestId", "contestId must be > 0");
    }

    /**
     * Accuracy test for isStudio. Verifies the returned value is correct.
     */
    @Test
    public void test_isStudio_Accuracy() {
        instance = new ActiveContestScheduleRetrievalAction();
        assertEquals("incorrect default value", false, instance.isStudio());
        instance.setStudio(true);
        assertEquals("incorrect value after setting", true, instance.isStudio());
    }

    /**
     * Accuracy Test for setStudio. Verifies the assigned value is correct.
     */
    @Test
    public void test_setStudio_Accuracy() {
        instance.setStudio(true);
        assertEquals("incorrect value after setting", true, instance.isStudio());
    }
}
