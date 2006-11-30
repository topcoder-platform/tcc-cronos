/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.accuracytests.impl;

import com.orpheus.user.persistence.impl.Sponsor;
import com.orpheus.user.persistence.impl.User;
import junit.framework.TestCase;


/**
 * Accuracy test cases for <code>Sponsor</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class SponsorAccTests extends TestCase {

    /**
     * <p>
     * Accuracy test of the constructor.
     * </p>
     */
    public void testConstructor() {
        Sponsor sponsor = new Sponsor(1);

        assertEquals("Not the expected ID.", 1, sponsor.getId());
    }

    /**
     * <p>
     * Test if Sponsor extends the right class.
     * </p>
     */
    public void testInheritance() {
        Sponsor sponsor = new Sponsor(1);
        assertTrue("Should extends User class.", sponsor instanceof User);
    }

    /**
     * <p>
     * Accuracy test of the <code>setPaymentPref()</code> method
     * </p>
     */
    public void testSetPaymentPref() {
        Sponsor sponsor = new Sponsor(1);

        sponsor.setPaymentPref("test");
        assertEquals("Not the expected payment pref.", "test", sponsor.getPaymentPref());
    }

    /**
     * <p>
     * Accuracy test of the <code>setApproved()</code> method
     * </p>
     */
    public void testSetApproved() {
        Sponsor sponsor = new Sponsor(1);

        sponsor.setApproved("test");
        assertEquals("Not the expected value.", "test", sponsor.getApproved());
    }

    /**
     * <p>
     * Accuracy test of the <code>setFax()</code> method
     * </p>
     */
    public void testSetFax() {
        Sponsor sponsor = new Sponsor(1);

        sponsor.setFax("123456789");
        assertEquals("Not the expected value.", "123456789", sponsor.getFax());
    }
}
