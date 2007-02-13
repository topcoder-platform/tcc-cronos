/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.timetracker.entry.expense.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;


/**
 * <p>
 * This class contains the accuracy unit tests for CommonInfo.java.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class CommonInfoTest extends TestCase {
    /**
     * <p>
     * Represents the CommonInfo instance for testing.
     * </p>
     */
    private CommonInfo commonInfo;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    protected void setUp() {
        commonInfo = new ExpenseEntryStatus();
    }

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(CommonInfoTest.class);
    }

    /**
     * <p>
     * Tests constructor.
     * </p>
     */
    public void testConstructorAccuracy() {
        assertEquals("By default, the ID should be -1.", -1, commonInfo.getId());
    }

    /**
     * <p>
     * Tests constructor.
     * </p>
     */
    public void testConstructorOneAccuracy() {
        commonInfo = new ExpenseEntryStatus(10);
        assertEquals("The constructor does not do well", 10, commonInfo.getId());
    }

    /**
     * <p>
     * Tests accuracy of getId() method.
     * </p>
     */
    public void testGetIdAccuracy() {
        commonInfo = new ExpenseEntryStatus(10);
        assertEquals("the ID should be correctly got.", 10, commonInfo.getId());
    }

    /**
     * <p>
     * Tests accuracy of setId() method.
     * </p>
     */
    public void testSetIdAccuracy() {
        commonInfo.setId(100);
        assertEquals("the ID should be correctly set.", 100, commonInfo.getId());
    }

    /**
     * <p>
     * Tests accuracy of getDescription() method. Default is null.
     * </p>
     */
    public void testGetDescriptionAccuracy() {
        assertNull("The descroption should be correctly got.", commonInfo.getDescription());
    }

    /**
     * <p>
     * Tests accuracy of setDescription() method.
     * </p>
     */
    public void testSetDescriptionAccuracy() {
        commonInfo.setDescription("foo");

        assertEquals("The description should be correctly set.", "foo", commonInfo.getDescription());
    }

    /**
     * <p>
     * Tests accuracy of getCreationDate() method. Default is null.
     * </p>
     */
    public void testGetCreationDateAccuracy() {
        assertNull("The creation date should be correctly got.", commonInfo.getCreationDate());
    }

    /**
     * <p>
     * Tests accuracy of setCreationDate() method.
     * </p>
     */
    public void testSetCreationDateAccuracy() {
        Date date = new Date();

        commonInfo.setCreationDate(date);

        assertEquals("The creation date should be correctly set.", date, commonInfo.getCreationDate());
    }

    /**
     * <p>
     * Tests accuracy of getCreationUser() method. Default is null.
     * </p>
     */
    public void testGetCreationUserAccuracy() {
        assertNull("The creation user should be correctly got.", commonInfo.getCreationUser());
    }

    /**
     * <p>
     * Tests accuracy of setCreationUser() method.
     * </p>
     */
    public void testSetCreationUserAccuracy() {
        commonInfo.setCreationUser("foo");

        assertEquals("The creation user should be correctly set.", "foo", commonInfo.getCreationUser());
    }

    /**
     * <p>
     * Tests accuracy of getModificationDate() method. Default is null.
     * </p>
     */
    public void testGetModificationDateAccuracy() {
        assertNull("The modification date should be correctly got.", commonInfo.getModificationDate());
    }

    /**
     * <p>
     * Tests accuracy of setModificationDate() method.
     * </p>
     */
    public void testSetModificationDateAccuracy() {
        Date date = new Date();

        commonInfo.setModificationDate(date);

        assertEquals("The modification date should be correctly set.", date, commonInfo.getModificationDate());
    }

    /**
     * <p>
     * Tests accuracy of getModificationUser() method. Default is null.
     * </p>
     */
    public void testGetModificationUserAccuracy() {
        assertNull("The modification user should be correctly got.", commonInfo.getModificationUser());
    }

    /**
     * <p>
     * Tests accuracy of setModificationUser() method.
     * </p>
     */
    public void testSetModificationUserAccuracy() {
        commonInfo.setModificationUser("foo");

        assertEquals("The modification user should be correctly set.", "foo", commonInfo.getModificationUser());
    }
}
