/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.facade.direct.DirectServiceFacadeException;
import com.topcoder.service.project.StudioCompetition;

/**
 * <p>
 * Unit tests for <code>ActiveContestScheduleExtensionAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ActiveContestScheduleExtensionActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private ActiveContestScheduleExtensionAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     * @throws Exception if an error occurs preparing the proxy
     */
    private ActionProxy prepareActionProxy() throws Exception {
        ActionProxy proxy = getActionProxy("/activeContestScheduleExtensionAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof ActiveContestScheduleExtensionAction);

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
        instance = new ActiveContestScheduleExtensionAction();
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
     * Accuracy test for executeAction. The contest schedule should be extended properly.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Accuracy() throws Exception {
        // prepare the instance
        instance.setDirectServiceFacade(new MockDirectServiceFacade());
        instance.setContestId(1);
        instance.setStudio(true);
        instance.setRegistrationExtension(10);
        instance.setMilestoneExtension(20);
        instance.setSubmissionExtension(30);

        // execute the action
        instance.executeAction();

        // validate that correct data is in model
        Object result = instance.getModel().getData("result");
        assertNull("result should be null", result);

        // validate that contest was extended
        StudioCompetition comp = MockContestServiceFacade.getStudioCompetitions().get(Long.valueOf(1));
        String extensionData = comp.getNotes();
        assertNotNull("extension data should be present in notes", extensionData);
        assertEquals("extension data is wrong",
            "extendRegistrationBy=10 extendMilestoneBy=20 extendSubmissionBy=30", extensionData);
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
        instance = new ActiveContestScheduleExtensionAction();
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
        ActiveContestScheduleExtensionAction action = (ActiveContestScheduleExtensionAction) proxy.getAction();

        TestHelper.getFieldValues().put("contestId", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestId", "contestId must be > 0");
    }

    /**
     * Accuracy test for isStudio. Verifies the returned value is correct.
     */
    @Test
    public void test_isStudio_Accuracy() {
        instance = new ActiveContestScheduleExtensionAction();
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

    /**
     * Accuracy test for getRegistrationExtension. Verifies the returned value is correct.
     */
    @Test
    public void test_getRegistrationExtension_Accuracy() {
        instance = new ActiveContestScheduleExtensionAction();
        assertEquals("incorrect default value", 0, instance.getRegistrationExtension());
        instance.setRegistrationExtension(1);
        assertEquals("incorrect value after setting", 1, instance.getRegistrationExtension());
    }

    /**
     * Accuracy Test for setRegistrationExtension. Verifies the assigned value is correct.
     */
    @Test
    public void test_setRegistrationExtension_Accuracy() {
        instance.setRegistrationExtension(1);
        assertEquals("incorrect value after setting", 1, instance.getRegistrationExtension());
    }

    /**
     * Failure test for setRegistrationExtension method in struts environment. The value for
     * registrationExtension is not positive, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setRegistrationExtension_RegistrationExtensionNotPositive() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestScheduleExtensionAction action = (ActiveContestScheduleExtensionAction) proxy.getAction();

        TestHelper.getFieldValues().put("registrationExtension", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "registrationExtension", "registrationExtension must be > 0");
    }

    /**
     * Accuracy test for getMilestoneExtension. Verifies the returned value is correct.
     */
    @Test
    public void test_getMilestoneExtension_Accuracy() {
        instance = new ActiveContestScheduleExtensionAction();
        assertEquals("incorrect default value", 0, instance.getMilestoneExtension());
        instance.setMilestoneExtension(1);
        assertEquals("incorrect value after setting", 1, instance.getMilestoneExtension());
    }

    /**
     * Accuracy Test for setMilestoneExtension. Verifies the assigned value is correct.
     */
    @Test
    public void test_setMilestoneExtension_Accuracy() {
        instance.setMilestoneExtension(1);
        assertEquals("incorrect value after setting", 1, instance.getMilestoneExtension());
    }

    /**
     * Failure test for setMilestoneExtension method in struts environment. The value for
     * milestoneExtension is not positive, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setMilestoneExtension_MilestoneExtensionNotPositive() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestScheduleExtensionAction action = (ActiveContestScheduleExtensionAction) proxy.getAction();

        TestHelper.getFieldValues().put("milestoneExtension", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "milestoneExtension", "milestoneExtension must be > 0");
    }

    /**
     * Accuracy test for getSubmissionExtension. Verifies the returned value is correct.
     */
    @Test
    public void test_getSubmissionExtension_Accuracy() {
        instance = new ActiveContestScheduleExtensionAction();
        assertEquals("incorrect default value", 0, instance.getSubmissionExtension());
        instance.setSubmissionExtension(1);
        assertEquals("incorrect value after setting", 1, instance.getSubmissionExtension());
    }

    /**
     * Accuracy Test for setSubmissionExtension. Verifies the assigned value is correct.
     */
    @Test
    public void test_setSubmissionExtension_Accuracy() {
        instance.setSubmissionExtension(1);
        assertEquals("incorrect value after setting", 1, instance.getSubmissionExtension());
    }

    /**
     * Failure test for setSubmissionExtension method in struts environment. The value for
     * submissionExtension is not positive, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setSubmissionExtension_SubmissionExtensionNotPositive() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ActiveContestScheduleExtensionAction action = (ActiveContestScheduleExtensionAction) proxy.getAction();

        TestHelper.getFieldValues().put("submissionExtension", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "submissionExtension", "submissionExtension must be > 0");
    }

}
