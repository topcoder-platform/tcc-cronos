/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.failuretests;

import com.topcoder.timetracker.company.Company;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link Company}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class CompanyFailureTests extends TestCase {

    /**
     * <p>
     * Represents the Company instance.
     * </p>
     */
    private Company company = new Company();

    /**
     * <p>
     * Failure test for <code>{@link Company#setAddress(com.topcoder.timetracker.contact.Address)}</code> method.
     * </p>
     */
    public void testSetAddress_NullAddress() {
        try {
            company.setAddress(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Company#setContact(com.topcoder.timetracker.contact.Contact)}</code> method.
     * </p>
     */
    public void testSetContact_NullContact() {
        try {
            company.setContact(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Company#setCompanyName(String)}</code> method.
     * </p>
     */
    public void testSetCompanyName_NullCompanyName() {
        try {
            company.setCompanyName(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Company#setCompanyName(String)}</code> method.
     * </p>
     */
    public void testSetCompanyName_EmptyCompanyName() {
        try {
            company.setCompanyName("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Company#setCompanyName(String)}</code> method.
     * </p>
     */
    public void testSetCompanyName_TrimmedEmptyCompanyName() {
        try {
            company.setCompanyName("  ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Company#setPassCode(String)}</code> method.
     * </p>
     */
    public void testSetPassCode_NullPassCode() {
        try {
            company.setPassCode(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Company#setPassCode(String)}</code> method.
     * </p>
     */
    public void testSetPassCode_EmptyPassCode() {
        try {
            company.setPassCode("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link Company#setPassCode(String)}</code> method.
     * </p>
     */
    public void testSetPassCode_TrimmedEmptyPassCode() {
        try {
            company.setPassCode("  ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
