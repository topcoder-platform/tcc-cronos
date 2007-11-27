/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.lang.reflect.Field;

import com.topcoder.timetracker.common.TimeTrackerBean;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>UserStatus</code>.
 *
 * @author enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserStatusTest extends TestCase {

    /** Unit under test. */
    private UserStatus userStatus;

    /**
     * <p>
     * Returns the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(UserStatusTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        userStatus = new UserStatus();
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        userStatus = null;

        super.tearDown();
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: the object is created properly.
     */
    public void testUserStatusAccuracy() {
        assertNotNull("The object is not created properly", new UserStatus());
    }

    /**
     * Test <code>getDescription</code> for accuracy. Condition: normal. Expect: the returned value is as
     * expected.
     */
    public void testGetDescriptionAccuracy() {
        userStatus.setDescription("testDescription");

        assertEquals("The returned value is not as expected", "testDescription", userStatus.getDescription());
    }

    /**
     * Test <code>setDescription</code> for accuracy. Condition: normal. Expect: the fields are set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetDescriptionAccuracy() throws Exception {
        userStatus.setDescription("testDescription");

        // check the description fields
        Field descriptionField = UserStatus.class.getDeclaredField("description");
        descriptionField.setAccessible(true);
        assertEquals("The field is not set as expected", "testDescription", descriptionField.get(userStatus));

        // check the changed fields
        Field changedField = TimeTrackerBean.class.getDeclaredField("changed");
        changedField.setAccessible(true);
        assertEquals("The field is not set as expected", Boolean.TRUE, (Boolean) changedField.get(userStatus));
    }

    /**
     * Test <code>setDescription</code> for failure. Condition: description is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testSetDescriptionNull() {
        try {
            userStatus.setDescription(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>setDescription</code> for failure. Condition: description is empty string. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testSetDescriptionEmptyString() {
        try {
            userStatus.setDescription("  \t");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>isActive</code> for accuracy. Condition: normal. Expect: the returned value is as expected.
     */
    public void testIsActiveAccuracy() {
        userStatus.setActive(true);

        assertEquals("Returned value is not as expected", true, userStatus.isActive());
    }

    /**
     * Test <code>setActive</code> for accuracy. Condition: normal. Expect: the fields are set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetActiveAccuracy() throws Exception {
        userStatus.setActive(true);

        // check the active fields
        Field activeField = UserStatus.class.getDeclaredField("active");
        activeField.setAccessible(true);
        assertEquals("The field is not set as expected", Boolean.TRUE, (Boolean) activeField.get(userStatus));

        // check the changed fields
        Field changedField = TimeTrackerBean.class.getDeclaredField("changed");
        changedField.setAccessible(true);
        assertEquals("The field is not set as expected", Boolean.TRUE, (Boolean) changedField.get(userStatus));
    }

    /**
     * Test <code>getCompanyId</code> for accuracy. Condition: normal. Expect: the returned value is as expected.
     */
    public void testGetCompanyIdAccuracy() {
        userStatus.setCompanyId(123);

        assertEquals("Returned value is not as expected", 123, userStatus.getCompanyId());
    }

    /**
     * Test <code>setCompanyId</code> for accuracy. Condition: normal. Expect: the fields are set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetCompanyIdAccuracy() throws Exception {
        userStatus.setCompanyId(123);

        // check the companyId fields
        Field companyIdField = UserStatus.class.getDeclaredField("companyId");
        companyIdField.setAccessible(true);
        assertEquals("The field is not set as expected", new Long(123), (Long) companyIdField.get(userStatus));

        // check the changed fields
        Field changedField = TimeTrackerBean.class.getDeclaredField("changed");
        changedField.setAccessible(true);
        assertEquals("The field is not set as expected", Boolean.TRUE, (Boolean) changedField.get(userStatus));
    }

    /**
     * Test <code>setCompanyId</code> for failure. Condition: companyId is negative. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetCompanyIdNegative() throws Exception {
        try {
            userStatus.setCompanyId(-5);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
