/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.company.Company;

/**
 * <p>
 * Failure unit test cases for the Company class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class CompanyFailureTests extends TestCase {
    /**
     * <p>
     * The Company instance used for testing.
     * </p>
     */
    private Company company = null;

    /**
     * <p>
     * Creates Company instance.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        company = new Company();
    }

    /**
     * <p>
     * Tests setAddress(Address) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAddress1() {
        try {
            company.setAddress(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setAlgorithmName(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAlgorithmName1() {
        try {
            company.setAlgorithmName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setAlgorithmName(String) for failure. Passes name that is not contained in encryption repository,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAlgorithmName2() {
        try {
            company.setAlgorithmName("abc");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setCompanyName(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetCompanyName1() {
        try {
            company.setCompanyName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setContact(Contact) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetContact1() {
        try {
            company.setContact(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setPasscode(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetPasscode1() {
        try {
            company.setPasscode(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setPasscode(String) for failure. Passes passcode without specifying algorithm name,
     * IllegalStateException is expected.
     * </p>
     */
    public void testSetPasscode2() {
        try {
            company.setPasscode("abc");
            fail("IllegalStateException should be thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
