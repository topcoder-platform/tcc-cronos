/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the Sponsor class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class SponsorTest extends TestCase {

    /**
     * <p>
     * The Sponsor instance to test.
     * </p>
     */
    private Sponsor sponsor = null;

    /**
     * <p>
     * Creates the test Sponsor instance.
     * </p>
     */
    protected void setUp() {
        sponsor = new Sponsor(1234);
    }

    /**
     * <p>
     * Tests the Sponsor(long id) constructor with a valid positive argument.
     * The newly created instance should not be null. The return value of the
     * getId() method should be equal to the constructor argument.
     * </p>
     */
    public void testCtorWithValidPositiveArg() {
        long id = 85837850;
        Sponsor s = new Sponsor(id);
        assertNotNull("The Sponsor instance should not be null", s);
        assertEquals("The ID is incorrect", id, s.getId());
    }

    /**
     * <p>
     * Tests the Sponsor(long id) constructor with a valid positive argument.
     * The newly created instance should not be null. The return value of the
     * getId() method should be equal to the constructor argument.
     * </p>
     */
    public void testCtorWithValidNegativeArg() {
        long id = -85837850;
        Sponsor s = new Sponsor(id);
        assertNotNull("The Sponsor instance should not be null", s);
        assertEquals("The ID is incorrect", id, s.getId());
    }

    /**
     * <p>
     * Tests the setFax(String fax) with a valid non-null non-empty string
     * argument. The return value of the getFax() method should be equal to the
     * method argument.
     * </p>
     */
    public void testSetFaxWithValidNonNullNonEmptyArg() {
        String fax = "012-345-6789";
        sponsor.setFax(fax);
        assertEquals("The fax is incorrect", fax, sponsor.getFax());
    }

    /**
     * <p>
     * Tests the setFax(String fax) with a valid null argument. The return value
     * of the getFax() method should be null.
     * </p>
     */
    public void testSetFaxWithValidNullArg() {
        sponsor.setFax(null);
        assertNull("The fax should be null", sponsor.getFax());
    }

    /**
     * <p>
     * Tests the setFax(String fax) with a valid empty string argument. The
     * return value of the getFax() method should be equal to the method
     * argument.
     * </p>
     */
    public void testSetFaxWithValidEmptyArg() {
        String fax = " ";
        sponsor.setFax(fax);
        assertEquals("The fax is incorrect", fax, sponsor.getFax());
    }

    /**
     * <p>
     * Tests the setPaymentPref(String paymentPref) with a valid non-null
     * non-empty string argument. The return value of the getPaymentPref()
     * method should be equal to the method argument.
     * </p>
     */
    public void testSetPaymentPrefWithValidNonNullNonEmptyArg() {
        String paymentPref = "wire transfer";
        sponsor.setPaymentPref(paymentPref);
        assertEquals("The payment pref is incorrect", paymentPref, sponsor.getPaymentPref());
    }

    /**
     * <p>
     * Tests the setPaymentPref(String paymentPref) with a valid null argument.
     * The return value of the getPaymentPref() method should be null.
     * </p>
     */
    public void testSetPaymentPrefWithValidNullArg() {
        sponsor.setPaymentPref(null);
        assertNull("The payment pref should be null", sponsor.getPaymentPref());
    }

    /**
     * <p>
     * Tests the setPaymentPref(String paymentPref) with a valid empty string
     * argument. The return value of the getPaymentPref() method should be equal
     * to the method argument.
     * </p>
     */
    public void testSetPaymentPrefWithValidEmptyArg() {
        String paymentPref = " ";
        sponsor.setPaymentPref(paymentPref);
        assertEquals("The payment pref is incorrect", paymentPref, sponsor.getPaymentPref());
    }

    /**
     * <p>
     * Tests the setPaymentPref(String paymentPref) with a valid non-null
     * non-empty string argument. The return value of the getPaymentPref()
     * method should be equal to the method argument.
     * </p>
     */
    public void testSetApprovedWithValidNonNullNonEmptyArg() {
        sponsor.setApproved(Sponsor.APPROVED_TRUE);
        assertEquals("The approved flag is incorrect", Sponsor.APPROVED_TRUE, sponsor.getApproved());
    }

    /**
     * <p>
     * Tests the setPaymentPref(String paymentPref) with a valid null argument.
     * The return value of the getPaymentPref() method should be null.
     * </p>
     */
    public void testSetApprovedWithValidNullArg() {
        sponsor.setApproved(Sponsor.APPROVED_UNDECIDED);
        assertNull("The approved flag should be null", sponsor.getApproved());
    }

    /**
     * <p>
     * Tests the setPaymentPref(String paymentPref) with a valid empty string
     * argument. The return value of the getPaymentPref() method should be equal
     * to the method argument.
     * </p>
     */
    public void testSetApprovedWithValidEmptyArg() {
        String approved = " ";
        sponsor.setApproved(approved);
        assertEquals("The approved flag is incorrect", approved, sponsor.getApproved());
    }

    /**
     * <p>
     * Tests that the Sponsor.APPROVED_TRUE constant is equal to "true".
     * </p>
     */
    public void testApprovedTrueConstant() {
        assertEquals("APPROVED_TRUE is incorrect", Sponsor.APPROVED_TRUE, "true");
    }

    /**
     * <p>
     * Tests that the Sponsor.APPROVED_FALSE constant is equal to "false".
     * </p>
     */
    public void testApprovedFalseConstant() {
        assertEquals("APPROVED_FALSE is incorrect", Sponsor.APPROVED_FALSE, "false");
    }

    /**
     * <p>
     * Tests that the Sponsor.APPROVED_UNDECIDED constant is null.
     * </p>
     */
    public void testApprovedUndecidedConstant() {
        assertNull("APPROVED_UNDECIDED should be null", Sponsor.APPROVED_UNDECIDED);
    }

    /**
     * <p>
     * Tests that Sponsor is a subclass of User.
     * </p>
     */
    public void testInheritance() {
        assertTrue("Sponsor should be a subclass of User", sponsor instanceof User);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(SponsorTest.class);
    }

}
