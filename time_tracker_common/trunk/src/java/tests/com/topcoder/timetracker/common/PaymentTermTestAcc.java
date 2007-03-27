/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * Accuracy test for <code>PaymentTerm</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
  */
public class PaymentTermTestAcc extends BaseBaseTestCase {
    /**
     * <p>
     * Test constructor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testPaymentTerm() throws Exception {
        PaymentTerm paymentTerm = new PaymentTerm();
        assertNotNull("PaymentTerm should be instantiated", paymentTerm);
        assertNull("The description should be null initially", paymentTerm.getDescription());
        assertEquals("The term should be -1 initially", -1, paymentTerm.getTerm());
        assertFalse("The active status should be false initially", paymentTerm.isActive());
    }

    /**
     * <p>
     * Assert the methods related to term.
     * </p>
     */
    private void assertTerm() {
        PaymentTerm paymentTerm = new PaymentTerm();
        assertEquals("The term should be -1 initially", -1, paymentTerm.getTerm());

        paymentTerm.setTerm(-2);
        assertEquals("The term should be set as -2", -2, paymentTerm.getTerm());
        assertFalse("The changed status should be false", paymentTerm.isChanged());

        paymentTerm.setTerm(3);
        assertEquals("The term should be set as 3", 3, paymentTerm.getTerm());
        assertTrue("The changed status should be true", paymentTerm.isChanged());
    }
    /**
     * <p>
     * Test method <code>getTerm</code>.
     * </p>
     */
    public void testGetTerm() {
        this.assertTerm();
    }

    /**
     * <p>
     * Test method <code>setTerm</code>.
     * </p>
     */
    public void testSetTerm() {
        this.assertTerm();
    }

    /**
     * <p>
     * Assert the methods related to description.
     * </p>
     */
    private void assertDescription() {
        PaymentTerm paymentTerm = new PaymentTerm();
        assertNull("The description should be null initially", paymentTerm.getDescription());

        paymentTerm.setDescription(null);
        assertNull("The description should be set as null", paymentTerm.getDescription());
        assertFalse("The changed status should be false", paymentTerm.isChanged());

        paymentTerm.setDescription(" ");
        assertEquals("The description should be set as empty", " ", paymentTerm.getDescription());
        assertFalse("The changed status should be false", paymentTerm.isChanged());

        paymentTerm.setDescription(this.getStringWithLength65());
        assertEquals("The description should be set as a string with length 65", 65,
             paymentTerm.getDescription().length());
        assertFalse("The changed status should be false", paymentTerm.isChanged());

        paymentTerm.setDescription("desc");
        assertEquals("The description should be set as 'desc'", "desc", paymentTerm.getDescription());
        assertTrue("The changed status should be true", paymentTerm.isChanged());
    }
    /**
     * <p>
     * Test method <code>getDescription()</code>.
     * </p>
     */
    public void testGetDescription() {
        this.assertDescription();
    }

    /**
     * <p>
     * Test method <code>setDescription()</code>.
     * </p>
     */
    public void testSetDescription() {
        this.assertDescription();
    }

    /**
     * <p>
     * Assert the methods related to active.
     * </p>
     */
    private void assertActive() {
        PaymentTerm paymentTerm = new PaymentTerm();
        assertFalse("The active status should be false initially", paymentTerm.isActive());

        paymentTerm.setActive(false);
        assertFalse("The active status should be set as false", paymentTerm.isActive());
        assertFalse("The changed status should be false", paymentTerm.isChanged());

        paymentTerm.setActive(true);
        assertTrue("The active status should be set as true", paymentTerm.isActive());
        assertTrue("The changed status should be true", paymentTerm.isChanged());
    }
    /**
     * <p>
     * Test method <code>isActive()</code>.
     * </p>
     */
    public void testIsActive() {
        this.assertActive();
    }

    /**
     * <p>
     * Test method <code>setActive()</code>.
     * </p>
     */
    public void testSetActive() {
        this.assertActive();
    }
}
