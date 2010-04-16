/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.payment.PaymentType;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.ContestNotFoundException;

/**
 * <p>
 * Unit tests for <code>PayByBillingAccountAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PayByBillingAccountActionUnitTest extends BaseStrutsSpringTestCase {

    /**
     * The instance used in testing.
     */
    private PayByBillingAccountAction instance;

    /**
     * Prepares the action proxy for use with struts.
     *
     * @return the prepared proxy
     * @throws Exception if an error occurs preparing the proxy
     */
    private ActionProxy prepareActionProxy() throws Exception {
        ActionProxy proxy = getActionProxy("/mockPayByBillingAccountAction");
        assertTrue("action is of wrong type", proxy.getAction() instanceof PayByBillingAccountAction);

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
        instance = new MockPayByBillingAccountAction();
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
        TestHelper.assertSuperclass(instance.getClass().getSuperclass(), PayContestAction.class);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for prepare. The <code>tcPurchaseOrderPaymentData</code> field should be initialized.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_prepare_Accuracy() throws Exception {
        instance = new PayByBillingAccountAction();
        instance.prepare();
        assertNotNull("tcPurchaseOrderPaymentData should not be null", TestHelper.getInstanceField(instance,
            "tcPurchaseOrderPaymentData"));
    }

    /**
     * Accuracy test for processPayment. The payment should be processed successfully for the studio
     * competition and correct payment result should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processPayment_Accuracy1() throws Exception {
        StudioCompetition comp = new StudioCompetition();
        comp.setId(1);
        MockContestServiceFacade.addStudioCompetition(comp);
        instance.setContestId(comp.getId());
        instance.setProjectId(0);
        PaymentResult result = instance.processPayment();
        assertNotNull("result should not be null", result);
        assertEquals("reference number is wrong", "ref1", result.getReferenceNumber());
    }

    /**
     * Accuracy test for processPayment. The payment should be processed successfully for the software
     * competition and correct payment result should be returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processPayment_Accuracy2() throws Exception {
        SoftwareCompetition comp = new SoftwareCompetition();
        comp.setId(1);
        MockContestServiceFacade.addSoftwareCompetition(comp);
        instance.setContestId(0);
        instance.setProjectId(comp.getId());
        PaymentResult result = instance.processPayment();
        assertNotNull("result should not be null", result);
        assertEquals("reference number is wrong", "ref2", result.getReferenceNumber());
    }

    /**
     * Failure test for processPayment. The studio competition wasn't found, so ContestNotFoundException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processPayment_Failure1() throws Exception {
        try {
            instance.setContestId(1);
            instance.setProjectId(0);
            instance.processPayment();
            fail("ContestNotFoundException is expected");
        } catch (ContestNotFoundException e) {
            // success
        }
    }

    /**
     * Failure test for processPayment. The software competition wasn't found, so ContestServiceException is
     * expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_processPayment_Failure2() throws Exception {
        try {
            instance.setContestId(0);
            instance.setProjectId(1);
            instance.processPayment();
            fail("ContestServiceException is expected");
        } catch (ContestServiceException e) {
            // success
        }
    }

    /**
     * Accuracy test for getPoNumber. Verifies the returned value is correct.
     */
    @Test
    public void test_getPoNumber_Accuracy() {
        instance = new PayByBillingAccountAction();
        instance.prepare();
        assertNull("incorrect default value", instance.getPoNumber());
        instance.setPoNumber("a");
        assertEquals("incorrect value after setting", "a", instance.getPoNumber());
    }

    /**
     * Accuracy Test for setPoNumber. Verifies the assigned value is correct.
     */
    @Test
    public void test_setPoNumber_Accuracy() {
        instance.setPoNumber("a");
        assertEquals("incorrect value after setting", "a", instance.getPoNumber());
    }

    /**
     * Failure test for setPoNumber method in struts environment. The value for the setter is null, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPoNumber_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByBillingAccountAction action = (PayByBillingAccountAction) proxy.getAction();

        TestHelper.getFieldValues().put("poNumber", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "poNumber",
            "poNumber cannot be null and must be between 1 and 255 characters");
    }

    /**
     * Failure test for setPoNumber method in struts environment. The value for the setter has length less
     * than 1, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPoNumber_Empty() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByBillingAccountAction action = (PayByBillingAccountAction) proxy.getAction();

        TestHelper.getFieldValues().put("poNumber", " ");

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "poNumber",
            "poNumber cannot be null and must be between 1 and 255 characters");
    }

    /**
     * Failure test for setPoNumber method in struts environment. The value for the setter has length that is
     * too long, so validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPoNumber_TooLong() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByBillingAccountAction action = (PayByBillingAccountAction) proxy.getAction();

        TestHelper.getFieldValues().put("poNumber", TestHelper.STRING_OVER_255);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "poNumber",
            "poNumber cannot be null and must be between 1 and 255 characters");
    }

    /**
     * Accuracy test for getClientId. Verifies the returned value is correct.
     */
    @Test
    public void test_getClientId_Accuracy() {
        instance = new PayByBillingAccountAction();
        instance.prepare();
        assertEquals("incorrect default value", 0, instance.getClientId());
        instance.setClientId(5);
        assertEquals("incorrect value after setting", 5, instance.getClientId());
    }

    /**
     * Accuracy Test for setClientId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setClientId_Accuracy() {
        instance.setClientId(5);
        assertEquals("incorrect value after setting", 5, instance.getClientId());
    }

    /**
     * Failure test for setClientId method in struts environment. The value for the setter is 0, so validation
     * should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setClientId_Zero() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByBillingAccountAction action = (PayByBillingAccountAction) proxy.getAction();

        TestHelper.getFieldValues().put("clientId", 0);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "clientId", "clientId must be > 0");
    }

    /**
     * Accuracy test for getProjectId. Verifies the returned value is correct.
     */
    @Test
    public void test_getProjectId_Accuracy() {
        instance = new PayByBillingAccountAction();
        instance.prepare();
        assertEquals("incorrect default value", 0, instance.getProjectId());
        instance.setProjectId(5);
        assertEquals("incorrect value after setting", 5, instance.getProjectId());
    }

    /**
     * Accuracy Test for setProjectId. Verifies the assigned value is correct.
     */
    @Test
    public void test_setProjectId_Accuracy() {
        instance.setProjectId(5);
        assertEquals("incorrect value after setting", 5, instance.getProjectId());
    }

    /**
     * Failure test for setProjectId method in struts environment. The value for the setter is 0, so
     * validation should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setProjectId_Zero() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByBillingAccountAction action = (PayByBillingAccountAction) proxy.getAction();

        TestHelper.getFieldValues().put("projectId", 0);

        // run the action and make sure validation worked properly
        assertEquals("wrong action result returned", "success", proxy.execute());
        assertTrue("there should be field errors", action.hasFieldErrors());
        assertEquals("wrong number of field errors returned", 2, action.getFieldErrors().size());

        assertEquals("wrong message in field errors", "projectId must be > 0", action.getFieldErrors().get(
            "projectId").get(0));
        assertEquals("wrong message in field errors",
            "Either projectId or contestId must be > 0", action.getFieldErrors().get("contestIdProjectId")
                .get(0));
    }

    /**
     * Accuracy test for getType. Verifies the returned value is correct.
     */
    @Test
    public void test_getType_Accuracy() {
        instance = new PayByBillingAccountAction();
        instance.prepare();
        assertEquals("incorrect default value", null, instance.getType());
        instance.setType(PaymentType.TCPurchaseOrder);
        assertEquals("incorrect value after setting", PaymentType.TCPurchaseOrder, instance.getType());
    }

    /**
     * Accuracy Test for setType. Verifies the assigned value is correct.
     */
    @Test
    public void test_setType_Accuracy() {
        instance.setType(PaymentType.TCPurchaseOrder);
        assertEquals("incorrect value after setting", PaymentType.TCPurchaseOrder, instance.getType());
    }

    /**
     * Failure test for setType method in struts environment. The value for the setter is null, so validation
     * should fail.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setType_Null() throws Exception {
        ActionProxy proxy = prepareActionProxy();
        PayByBillingAccountAction action = (PayByBillingAccountAction) proxy.getAction();

        TestHelper.getFieldValues().put("type", null);

        // run the action and make sure validation worked properly
        TestHelper.assertInvalidField(proxy, action, "type", "type cannot be null");
    }
}
