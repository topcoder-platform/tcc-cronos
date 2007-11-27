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
 * Unit test for <code>UserType</code>.
 *
 * @author enefem21
 * @version 3.2.1
 * @since 3.2.1
 */
public class UserTypeTest extends TestCase {

    /** Unit under test. */
    private UserType userType;

    /**
     * <p>
     * Returns the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(UserTypeTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        userType = new UserType();
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        userType = null;

        super.tearDown();
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: the object is created properly.
     */
    public void testUserTypeAccuracy() {
        assertNotNull("The object is not created properly", new UserType());
    }

    /**
     * Test <code>getDescription</code> for accuracy. Condition: normal. Expect: the returned value is as
     * expected.
     */
    public void testGetDescriptionAccuracy() {
        userType.setDescription("testDescription");

        assertEquals("The returned value is not as expected", "testDescription", userType.getDescription());
    }

    /**
     * Test <code>setDescription</code> for accuracy. Condition: normal. Expect: the fields are set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetDescriptionAccuracy() throws Exception {
        userType.setDescription("testDescription");

        // check the description fields
        Field descriptionField = UserType.class.getDeclaredField("description");
        descriptionField.setAccessible(true);
        assertEquals("The field is not set as expected", "testDescription", descriptionField.get(userType));

        // check the changed fields
        Field changedField = TimeTrackerBean.class.getDeclaredField("changed");
        changedField.setAccessible(true);
        assertEquals("The field is not set as expected", Boolean.TRUE, (Boolean) changedField.get(userType));
    }

    /**
     * Test <code>setDescription</code> for failure. Condition: description is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testSetDescriptionNull() {
        try {
            userType.setDescription(null);
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
            userType.setDescription("  \t");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>isActive</code> for accuracy. Condition: normal. Expect: the returned value is as expected.
     */
    public void testIsActiveAccuracy() {
        userType.setActive(true);

        assertEquals("Returned value is not as expected", true, userType.isActive());
    }

    /**
     * Test <code>setActive</code> for accuracy. Condition: normal. Expect: the fields are set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetActiveAccuracy() throws Exception {
        userType.setActive(true);

        // check the active fields
        Field activeField = UserType.class.getDeclaredField("active");
        activeField.setAccessible(true);
        assertEquals("The field is not set as expected", Boolean.TRUE, (Boolean) activeField.get(userType));

        // check the changed fields
        Field changedField = TimeTrackerBean.class.getDeclaredField("changed");
        changedField.setAccessible(true);
        assertEquals("The field is not set as expected", Boolean.TRUE, (Boolean) changedField.get(userType));
    }

    /**
     * Test <code>getCompanyId</code> for accuracy. Condition: normal. Expect: the returned value is as expected.
     */
    public void testGetCompanyIdAccuracy() {
        userType.setCompanyId(123);

        assertEquals("Returned value is not as expected", 123, userType.getCompanyId());
    }

    /**
     * Test <code>setCompanyId</code> for accuracy. Condition: normal. Expect: the fields are set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetCompanyIdAccuracy() throws Exception {
        userType.setCompanyId(123);

        // check the companyId fields
        Field companyIdField = UserType.class.getDeclaredField("companyId");
        companyIdField.setAccessible(true);
        assertEquals("The field is not set as expected", new Long(123), (Long) companyIdField.get(userType));

        // check the changed fields
        Field changedField = TimeTrackerBean.class.getDeclaredField("changed");
        changedField.setAccessible(true);
        assertEquals("The field is not set as expected", Boolean.TRUE, (Boolean) changedField.get(userType));
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
            userType.setCompanyId(-5);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
