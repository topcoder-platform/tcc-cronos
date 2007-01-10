/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.user.UserSearchBuilder;

/**
 * <p>
 * Failure unit test cases for the UserSearchBuilder class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class UserSearchBuilderFailureTests extends TestCase {

    /**
     * <p>
     * The UserSearchBuilder instance used for testing.
     * </p>
     */
    private UserSearchBuilder builder = null;

    /**
     * <p>
     * Creates UserSearchBuilder instance.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        builder = new UserSearchBuilder();
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
     * Tests hasEmail(String) for failure. Passes empty sring, IllegalArgumentException is expected.
     * </p>
     */
    public void testHasEmail1() {
        try {
            builder.hasEmail(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests hasFirstName(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testHasFirstName1() {
        try {
            builder.hasFirstName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests hasLastName(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testHasLastName1() {
        try {
            builder.hasLastName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests hasPassword(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testHasPassword1() {
        try {
            builder.hasPassword(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests hasPassword(String) for failure. The algorithm is not set, IllegalStateException is expected.
     * </p>
     */
    public void testHasPassword2() {
        try {
            builder.hasPassword("abc");
            fail("IllegalStateException should be thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests hasPhoneNumber(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testHasPhoneNumber1() {
        try {
            builder.hasPhoneNumber(" ");
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
     * Tests hasUsername(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testHasUsername1() {
        try {
            builder.hasUsername(" ");
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
