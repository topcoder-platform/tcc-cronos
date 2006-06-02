/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>
 * Tests functionality and error cases of <code>BasicInfo</code> class. A mock sub-class is used for testing.
 * </p>
 *
 * <p>
 * 2006-4-24: Moving test cases for getter and setter methods of description from V1Dot1BasicInfoUnitTest class to
 * V1Dot1CommonInfoUnitTest class for bug fix for TT-1976 by Xuchen.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1BasicInfoUnitTest extends TestCase {
    /** Represents the <code>BasicInfo</code> instance used in tests. */
    private BasicInfo info;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        info = new V1Dot1MockBasicInfo();
    }

    /**
     * <p>
     * Cleans up the test environment. The test instance is disposed.
     * </p>
     */
    protected void tearDown() {
        info = null;
    }

    /**
     * <p>
     * Tests constructor <code>BasicInfo(int)</code> when the given ID is -1. Expect IllegalArgumentException.
     * </p>
     */
    public void testBasicInfoIntInvalid() {
        try {
            new V1Dot1MockBasicInfo(-1);
            fail("The ID is -1, should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of constructor <code>BasicInfo()</code>. The ID should be -1.
     * </p>
     */
    public void testBasicInfoAccuracy() {
        assertEquals("By default, the ID is -1.", -1, info.getId());
    }

    /**
     * <p>
     * Tests <code>setId</code> when the given ID is -1. Expect IllegalArgumentException.
     * </p>
     */
    public void testSetIdInvalid() {
        try {
            info.setId(-1);
            fail("The ID is -1, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>setCreationDate</code> when the given date is <code>null</code>. Expect NullPointerException.
     * </p>
     */
    public void testSetCreationDateNull() {
        try {
            info.setCreationDate(null);
            fail("The creation date is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>setCreationUser</code> when the given user is <code>null</code>. Expect NullPointerException.
     * </p>
     */
    public void testSetCreationUserNull() {
        try {
            info.setCreationUser(null);
            fail("The creation user is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>setCreationUser</code> when the given user is empty string. Expect IllegalArgumentException.
     * </p>
     */
    public void testSetCreationUserEmpty() {
        try {
            info.setCreationUser("  ");
            fail("The creation user is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>setModificationDate</code> when the given date is <code>null</code>. Expect NullPointerException.
     * </p>
     */
    public void testSetModificationDateNull() {
        try {
            info.setModificationDate(null);
            fail("The modification date is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>setModificationUser</code> when the given user is <code>null</code>. Expect NullPointerException.
     * </p>
     */
    public void testSetModificationUserNull() {
        try {
            info.setModificationUser(null);
            fail("The modification user is null, should throw NullPointerException.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>setModificationUser</code> when the given user is empty string. Expect IllegalArgumentException.
     * </p>
     */
    public void testSetModificationUserEmpty() {
        try {
            info.setModificationUser("  ");
            fail("The modification user is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>getId</code> when the ID is specified in the constructor. The ID should be correct.
     * </p>
     */
    public void testGetIdAccuracy() {
        for (int i = 0; i < 2; ++i) {
            info = new V1Dot1MockBasicInfo(i);

            assertEquals("The ID should be correct.", i, info.getId());
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>setId</code>. The ID should be correct.
     * </p>
     */
    public void testSetIdAccuracy() {
        for (int i = 0; i < 2; ++i) {
            info.setId(i);

            assertEquals("The ID should be correct.", i, info.getId());
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>getCreationDate</code> for default value. By default, the creation date is
     * <code>null</code>.
     * </p>
     */
    public void testGetCreationDateAccuracy() {
        assertNull("By default, the creation date is null.", info.getCreationDate());
    }

    /**
     * <p>
     * Tests accuracy of <code>setCreationDate</code>. The creation date should be correct.
     * </p>
     */
    public void testSetCreationDateAccuracy() {
        Date date = new Date();

        info.setCreationDate(date);

        assertEquals("The creation date should be correct.", date, info.getCreationDate());
    }

    /**
     * <p>
     * Tests accuracy of <code>getCreationUser</code> for default value. By default, the creation user is
     * <code>null</code>.
     * </p>
     */
    public void testGetCreationUserAccuracy() {
        assertNull("By default, the creation user is null.", info.getCreationUser());
    }

    /**
     * <p>
     * Tests accuracy of <code>setCreationUser</code>. The creation user should be correct.
     * </p>
     */
    public void testSetCreationUserAccuracy() {
        info.setCreationUser("Create");

        assertEquals("The creation user should be correct.", "Create", info.getCreationUser());
    }

    /**
     * <p>
     * Tests accuracy of <code>getModificationDate</code> for default value. By default, the modification date is
     * <code>null</code>.
     * </p>
     */
    public void testGetModificationDateAccuracy() {
        assertNull("By default, the modification date is null.", info.getModificationDate());
    }

    /**
     * <p>
     * Tests accuracy of <code>setModificationDate</code>. The modification date should be correct.
     * </p>
     */
    public void testSetModificationDateAccuracy() {
        Date date = new Date();

        info.setModificationDate(date);

        assertEquals("The modification date should be correct.", date, info.getModificationDate());
    }

    /**
     * <p>
     * Tests accuracy of <code>getModificationUser</code> for default value. By default, the modification user is
     * <code>null</code>.
     * </p>
     */
    public void testGetModificationUserAccuracy() {
        assertNull("By default, the modification user is null.", info.getModificationUser());
    }

    /**
     * <p>
     * Tests accuracy of <code>setModificationUser</code>. The modification user should be correct.
     * </p>
     */
    public void testSetModificationUserAccuracy() {
        info.setModificationUser("Modify");

        assertEquals("The modification user should be correct.", "Modify", info.getModificationUser());
    }
}
