/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import junit.framework.TestCase;

import com.topcoder.web.common.HibernateUtils;

/**
 * Unit test cases for {@link PaymentHistoryAction} class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PaymentHistoryActionTest extends TestCase {

    /**
     * An instance of PaymentHistoryAction used for testing.
     */
    private PaymentHistoryAction instance;

    /**
     * Sets up the test environment.
     */
    public void setUp() {
        instance = new PaymentHistoryAction();
    }

    /**
     * Tears down the test environment.
     */
    public void tearDown() {
        instance = null;
    }

    /**
     * Accuracy test case for {@link PaymentHistoryAction#checkConfiguration()} method.
     */
    public void testCheckConfiguration() {
        instance = (PaymentHistoryAction) TestHelper.setFieldsInBaseAction(instance);
        instance.setDefaultSortColumn(1);
        instance.checkConfiguration();
    }

    /**
     * Failure test case for {@link PaymentHistoryAction#checkConfiguration()} method.
     */
    public void testCheckConfigurationFail() {
        instance = (PaymentHistoryAction) TestHelper.setFieldsInBaseAction(instance);
        instance.setDefaultSortColumn(1);
        instance.setProblemPaymentTypeId(-1);
        try {
            instance.checkConfiguration();
            fail("Exception should be thrown");
        } catch (UserDocumentationManagementActionsConfigurationException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link PaymentHistoryAction#PaymentHistoryAction()} constructor. Checks for null.
     */
    public void testPaymentHistoryAction() {
        assertNotNull("Error instantiating the class", instance);
    }

    /**
     * Accuracy test case for {@link PaymentHistoryAction#execute()} method.
     * @throws Exception
     *             to JUnit
     */
    public void testExecute() throws Exception {
        instance = (PaymentHistoryAction) TestHelper.setFieldsInBaseAction(instance);
        instance.setCharityPaymentTypeId(1);
        instance.setProblemPaymentTypeId(2);
        instance.setFullList(false);
        HibernateUtils.begin();

        assertTrue("Error executing PaymentHistoryAction", "success".equals(instance.execute()));
        TestHelper.closeHibernateSession();
        TestHelper.clearDatabase();
    }

    /**
     * Accuracy test case for {@link PaymentHistoryAction#execute()} method. This test case concentrates on the sort
     * method.
     * @throws Exception
     *             to JUnit
     */
    public void testExecute1() throws Exception {
        instance = (PaymentHistoryAction) TestHelper.setFieldsInBaseAction(instance);
        instance.setCharityPaymentTypeId(1);
        instance.setProblemPaymentTypeId(2);
        HibernateUtils.begin();

        instance.setSortColumn(1);
        assertTrue("Error executing PaymentHistoryAction", "success".equals(instance.execute()));

        instance.setSortColumn(3);
        assertTrue("Error executing PaymentHistoryAction", "success".equals(instance.execute()));

        instance.setSortColumn(4);
        assertTrue("Error executing PaymentHistoryAction", "success".equals(instance.execute()));

        instance.setSortColumn(6);
        assertTrue("Error executing PaymentHistoryAction", "success".equals(instance.execute()));

        instance.setSortColumn(5);
        assertTrue("Error executing PaymentHistoryAction", "success".equals(instance.execute()));

        assertNotNull("Error getting payments", instance.getPayments());

        TestHelper.closeHibernateSession();
        TestHelper.clearDatabase();
    }
}
