/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.common.TimeTrackerBean;

/**
 * <p>
 * Failure unit test cases for the non-abstract public methods of TimeTrackerBean class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class TimeTrackerBeanFailureTests extends TestCase {
    /**
     * <p>
     * The TimeTrackerBean instance used for testing.
     * </p>
     */
    private TimeTrackerBean bean = null;

    /**
     * <p>
     * Creates TimeTrackerBean instance.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        bean = new MockTimeTrackerBean();
    }

    /**
     * <p>
     * Tests setCreationDate(Date) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetCreationDate1() {
        try {
            bean.setCreationDate(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setModificationDate(Date) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetModificationDate1() {
        try {
            bean.setModificationDate(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setCreationUser(String) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetCreationUser1() {
        try {
            bean.setCreationUser(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests that setCreationUser(String) correctly processes empty string.
     * </p>
     */
    public void testSetCreationUser2() {
        bean.setCreationUser(" ");
    }

    /**
     * <p>
     * Tests setModificationUser(String) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetModificationUser1() {
        try {
            bean.setModificationUser(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests that setModificationUser(String) correctly processes empty string.
     * </p>
     */
    public void testSetModificationUser2() {
        bean.setModificationUser(" ");
    }

    /**
     * <p>
     * Tests setId(long) for failure. Passes 0, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetId1() {
        try {
            bean.setId(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setId(long) for failure. Passes negative value, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetId2() {
        try {
            bean.setId(-3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests that equals(Object) correctly compares with null object and instance of another class.
     * </p>
     */
    public void testEquals1() {
        assertFalse(bean.equals(null));
        assertFalse(bean.equals(Boolean.TRUE));
    }

    /**
     * <p>
     * Tests that hashCode() correctly returns hash for the empty bean.
     * </p>
     */
    public void testHashCode1() {
        bean.hashCode();
    }
}
