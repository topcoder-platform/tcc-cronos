/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the Player class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class PlayerTest extends TestCase {

    /**
     * <p>
     * The Player instance to test.
     * </p>
     */
    private Player player = null;

    /**
     * <p>
     * Creates the test Player instance.
     * </p>
     */
    protected void setUp() {
        player = new Player(1234);
    }

    /**
     * <p>
     * Tests the Player(long id) constructor with a valid positive argument. The
     * newly created instance should not be null. The return value of the
     * getId() method should be equal to the constructor argument.
     * </p>
     */
    public void testCtorWithValidPositiveArg() {
        long id = 85837850;
        Player p = new Player(id);
        assertNotNull("The Player instance should not be null", p);
        assertEquals("The ID is incorrect", id, p.getId());
    }

    /**
     * <p>
     * Tests the Player(long id) constructor with a valid positive argument. The
     * newly created instance should not be null. The return value of the
     * getId() method should be equal to the constructor argument.
     * </p>
     */
    public void testCtorWithValidNegativeArg() {
        long id = -85837850;
        Player p = new Player(id);
        assertNotNull("The Player instance should not be null", p);
        assertEquals("The ID is incorrect", id, p.getId());
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
        player.setPaymentPref(paymentPref);
        assertEquals("The payment pref is incorrect", paymentPref, player.getPaymentPref());
    }

    /**
     * <p>
     * Tests the setPaymentPref(String paymentPref) with a valid null argument.
     * The return value of the getPaymentPref() method should be null.
     * </p>
     */
    public void testSetPaymentPrefWithValidNullArg() {
        player.setPaymentPref(null);
        assertNull("The payment pref should be null", player.getPaymentPref());
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
        player.setPaymentPref(paymentPref);
        assertEquals("The payment pref is incorrect", paymentPref, player.getPaymentPref());
    }

    /**
     * <p>
     * Tests that Player is a subclass of User.
     * </p>
     */
    public void testInheritance() {
        assertTrue("Player should be a subclass of User", player instanceof User);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(PlayerTest.class);
    }

}
