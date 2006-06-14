/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.accuracytests;

import java.util.Date;

import com.cronos.timetracker.common.TimeTrackerBean;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>TimeTrackerBean </code>.s
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestTimeTrackerBeanAccuracy extends TestCase {

    /**
     * Represents the TimeTrackerBean instance for test.
     */
    private static TimeTrackerBean test = new MyTimeTrackerBean();

    /**
     * Test method getCreatetioinDate.
     *
     */
    public void testGetCreationDate() {
        assertNull("Should be null.", test.getCreationDate());
    }

    /**
     * Test method setCreateDate.
     *
     */
    public void testSetCreationDate() {
        Date date = new Date();

        test.setCreationDate(date);

        assertEquals("Equal is expected.", date, test.getCreationDate());
    }

    /**
     * test method getModificationDate.
     *
     */
    public void testGetModificationDate() {
        assertNull("Should be null.", test.getModificationDate());
    }

    /**
     * test method setModificationDate.
     *
     */
    public void testSetModificationDate() {
        Date date = new Date();

        test.setModificationDate(date);

        assertEquals("Equal is expected.", date, test.getModificationDate());
    }

    /**
     * Test method getCreationUser.
     *
     */
    public void testGetCreationUser() {
        assertNull("Should be null.", test.getCreationUser());
    }

    /**
     * Test method setCreationUser.
     *
     */
    public void testSetCreationUser() {
        test.setCreationUser("create");

        assertEquals("Equal is expected.", "create", test.getCreationUser());
    }

    /**
     * Test method getModificationUser.
     *
     */
    public void testGetModificationUser() {
        assertNull("Should be null.", test.getModificationUser());
    }

    /**
     * test method setModificationUser.
     *
     */
    public void testSetModificationUser() {
        test.setModificationUser("m");
        assertEquals("Equal is expected.", "m", test.getModificationUser());
    }

    /**
     * test method getId.
     *
     */
    public void testGetId() {
        assertEquals("Equal is expected.", 0, test.getId());
    }

    /**
     * test method setId.
     *
     */
    public void testSetId() {
        test.setId(10);

        assertEquals("equal is expected.", 10, test.getId());
    }

    /**
     * test method isChanged.
     *
     */
    public void testIsChanged() {
        assertFalse("false is expected.", test.isChanged());
    }

    /**
     * Test method setChanged.
     *
     */
    public void testSetChanged() {
        test.setChanged(true);

        assertTrue("True is expected.", test.isChanged());
    }

}
