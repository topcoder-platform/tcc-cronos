/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.cronos.timetracker.company.CompanySearchBuilder;

/**
 * <p>
 * Failure unit test cases for the CompanySearchBuilder class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class CompanySearchBuilderFailureTests extends TestCase {

    /**
     * <p>
     * The CompanySearchBuilder instance used for testing.
     * </p>
     */
    private CompanySearchBuilder builder = null;

    /**
     * <p>
     * Creates CompanySearchBuilder instance.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        builder = new CompanySearchBuilder();
    }

    /**
     * <p>
     * Tests buildFilter() for failure. No criteria has been set, IllegalStateException is expected.
     * </p>
     */
    public void testBuildFilter1() {
        try {
            builder.buildFilter();
            fail("IllegalStateException should be thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createdByUser(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testCreatedByUser1() {
        try {
            builder.createdByUser(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createdWithinDateRange(Date, Date) for failure. Passes nulls, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testCreatedWithinDateRange1() {
        try {
            builder.createdWithinDateRange(null, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createdWithinDateRange(Date, Date) for failure. Passes endDate less than startDate,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreatedWithinDateRange2() {
        try {
            builder.createdWithinDateRange(new Date(8111111), new Date(1111111));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests hasCity(String) for failure. Passes empty sring, IllegalArgumentException is expected.
     * </p>
     */
    public void testHasCity1() {
        try {
            builder.hasCity(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests hasCompanyName(String) for failure. Passes empty sring, IllegalArgumentException is expected.
     * </p>
     */
    public void testHasCompanyName1() {
        try {
            builder.hasCompanyName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests hasContactEmail(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testHasContactEmail1() {
        try {
            builder.hasContactEmail(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests hasContactFirstName(String) for failure. Passes empty string, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testHasContactFirstName1() {
        try {
            builder.hasContactFirstName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests hasContactLastName(String) for failure. Passes empty string, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testHasContactLastName1() {
        try {
            builder.hasContactLastName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests hasContactPhoneNumber(String) for failure. Passes empty string, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testHasContactPhoneNumber1() {
        try {
            builder.hasContactPhoneNumber(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests hasState(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testHasState1() {
        try {
            builder.hasState(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests hasStreetAddress(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testHasStreetAddress1() {
        try {
            builder.hasStreetAddress(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests hasZipCode(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testHasZipCode1() {
        try {
            builder.hasZipCode(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests modifiedByUser(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testModifiedByUser1() {
        try {
            builder.modifiedByUser(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests modifiedWithinDateRange(Date, Date) for failure. Passes nulls, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testModifiedWithinDateRange1() {
        try {
            builder.modifiedWithinDateRange(null, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests modifiedWithinDateRange(Date, Date) for failure. Passes endDate less than startDate,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testModifiedWithinDateRange2() {
        try {
            builder.modifiedWithinDateRange(new Date(8111111), new Date(1111111));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
