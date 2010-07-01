/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.facade.direct.ContestNotFoundException;
import com.topcoder.service.facade.direct.ContestReceiptData;

/**
 * <p>
 * Unit tests for <code>ContestReceiptSendingAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestReceiptSendingActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private ContestReceiptSendingAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     * @throws Exception if an error occurs preparing the proxy
     */
    private ActionProxy prepareActionProxy() throws Exception {
        ActionProxy proxy = getActionProxy("/contestReceiptSendingAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof ContestReceiptSendingAction);

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
        instance = new ContestReceiptSendingAction();
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
     * Accuracy test for executeAction. The contest receipt data should be stored in the model and receipts
     * should be sent. A studio contest is used in this test case.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Accuracy1() throws Exception {
        // prepare the instance for studio contest
        instance.setDirectServiceFacade(new MockDirectServiceFacade());
        instance.setContestId(1);
        instance.setStudio(true);
        instance.setAdditionalEmailAddresses("user1@topcoder.com; user2@aol.com, user3@gc.com ");

        // execute the action
        instance.executeAction();

        // validate that correct data is in model
        Object result = instance.getModel().getData("result");
        assertNotNull("result should not be null", result);
        assertTrue("result is not correct type", result instanceof ContestReceiptData);

        // validate that receipt data is correct
        ContestReceiptData receipt = (ContestReceiptData) result;
        assertEquals("The contest ID is wrong for the receipt", 1, receipt.getContestId());
        assertEquals("The contest name is wrong for the receipt", "studio comp1", receipt.getContestName());

        // validate that email was sent to recipients
        assertEquals("wrong number of email addresses", 3,
            MockDirectServiceFacade.getadditionalEmailAddresses().length);
        assertEquals("wrong email address for first value", "user1@topcoder.com", MockDirectServiceFacade
            .getadditionalEmailAddresses()[0]);
        assertEquals("wrong email address for second value", " user2@aol.com", MockDirectServiceFacade
            .getadditionalEmailAddresses()[1]);
        assertEquals("wrong email address for third value", " user3@gc.com ", MockDirectServiceFacade
            .getadditionalEmailAddresses()[2]);
    }

    /**
     * Accuracy test for executeAction. The contest receipt data should be stored in the model and receipts
     * should be sent. A software contest is used in this test case.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_Accuracy2() throws Exception {
        // prepare the instance for software contest, use null for additional email addresses
        instance.setDirectServiceFacade(new MockDirectServiceFacade());
        instance.setContestId(1);
        instance.setStudio(false);
        instance.setAdditionalEmailAddresses(null);

        // execute the action
        instance.executeAction();

        // validate that correct data is in model
        Object result = instance.getModel().getData("result");
        assertNotNull("result should not be null", result);
        assertTrue("result is not correct type", result instanceof ContestReceiptData);

        // validate that receipt data is correct
        ContestReceiptData receipt = (ContestReceiptData) result;
        assertEquals("The contest ID is wrong for the receipt", 1, receipt.getContestId());
        assertEquals("The contest name is wrong for the receipt", "software comp1", receipt.getContestName());

        // no additional email addresses should be present
        assertNull("No additional email addresses should be present", MockDirectServiceFacade
            .getadditionalEmailAddresses());

        // use empty string for additional email addresses
        instance.setAdditionalEmailAddresses("  ");

        // execute the action
        instance.executeAction();

        // validate that receipt data is correct
        receipt = (ContestReceiptData) result;
        assertEquals("The contest ID is wrong for the receipt", 1, receipt.getContestId());
        assertEquals("The contest name is wrong for the receipt", "software comp1", receipt.getContestName());

        // no additional email addresses should be present
        assertNull("No additional email addresses should be present", MockDirectServiceFacade
            .getadditionalEmailAddresses());

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
     * Failure test for executeAction. The contest could not be found,
     * so ContestNotFoundException is expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_executeAction_ContestNotFoundException() throws Exception {
        try {
            instance.setDirectServiceFacade(new MockDirectServiceFacade());
            instance.setContestId(999);
            instance.executeAction();
            fail("ContestNotFoundException is expected");
        } catch (ContestNotFoundException e) {
            // make sure exception was stored in model
            Object result = instance.getResult();
            assertNotNull("result from model shouldn't be null", result);
            assertTrue("result from model is not correct type", result instanceof ContestNotFoundException);
        }
    }

    /**
     * Accuracy test for getContestId. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestId_Accuracy() {
        instance = new ContestReceiptSendingAction();
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
        ContestReceiptSendingAction action = (ContestReceiptSendingAction) proxy.getAction();

        TestHelper.getFieldValues().put("contestId", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "contestId", "contestId must be > 0");
    }

    /**
     * Accuracy test for isStudio. Verifies the returned value is correct.
     */
    @Test
    public void test_isStudio_Accuracy() {
        instance = new ContestReceiptSendingAction();
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
     * Accuracy test for getAdditionalEmailAddresses. Verifies the returned value is correct.
     */
    @Test
    public void test_getAdditionalEmailAddresses_Accuracy() {
        instance = new ContestReceiptSendingAction();
        assertEquals("incorrect default value", null, instance.getAdditionalEmailAddresses());
        instance.setAdditionalEmailAddresses("a@aol.com ; b@aol.com");
        assertEquals("incorrect value after setting", "a@aol.com ; b@aol.com",
            instance.getAdditionalEmailAddresses());
    }

    /**
     * Accuracy Test for setAdditionalEmailAddresses. Verifies the assigned value is correct.
     */
    @Test
    public void test_setAdditionalEmailAddresses_Accuracy() {
        instance.setAdditionalEmailAddresses("a@aol.com");
        assertEquals("incorrect value after setting", "a@aol.com", instance.getAdditionalEmailAddresses());

        // null is allowed
        instance.setAdditionalEmailAddresses(null);
        assertNull("incorrect value after setting", instance.getAdditionalEmailAddresses());

        // empty string is allowed
        instance.setAdditionalEmailAddresses("  ");
        assertEquals("incorrect value after setting", "  ", instance.getAdditionalEmailAddresses());
    }

    /**
     * Failure test for setAdditionalEmailAddresses method in struts environment. The value exceeds 4096 chars, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setAdditionalEmailAddresses_TooLong() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ContestReceiptSendingAction action = (ContestReceiptSendingAction) proxy.getAction();

        StringBuilder tooLong = new StringBuilder();
        for (int i = 0; i < 4097; ++i) {
            tooLong.append("a");
        }
        TestHelper.getFieldValues().put("additionalEmailAddresses", tooLong);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "additionalEmailAddresses",
            "additionalEmailAddresses must not exceed 4096 chars");
    }

    /**
     * Failure test for setAdditionalEmailAddresses method in struts environment. The value contains invalid email
     * addresses, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setAdditionalEmailAddresses_InvalidEmail() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ContestReceiptSendingAction action = (ContestReceiptSendingAction) proxy.getAction();

        TestHelper.getFieldValues().put("additionalEmailAddresses", "fail, sam@topcoder.com; a@aol@com");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "additionalEmailAddresses",
            "The following email addresses were invalid: fail,a@aol@com");
    }

    /**
     * Failure test for setAdditionalEmailAddresses method in struts environment. The value contains empty email
     * address and invalid email address, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setAdditionalEmailAddresses_EmptyEmail() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        ContestReceiptSendingAction action = (ContestReceiptSendingAction) proxy.getAction();

        TestHelper.getFieldValues().put("additionalEmailAddresses", ".nowhere.com ; j@aol.com;  ;f@aol.com");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "additionalEmailAddresses",
            "The following email addresses were invalid: .nowhere.com,[empty]");
    }


}
