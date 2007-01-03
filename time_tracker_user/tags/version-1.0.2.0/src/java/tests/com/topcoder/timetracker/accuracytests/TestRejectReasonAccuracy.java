/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import com.cronos.timetracker.common.RejectReason;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>RejectReason</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestRejectReasonAccuracy extends TestCase {

    /**
     * Represents the RejectReason instance for test.
     */
    private static RejectReason target = new RejectReason();

    /**
     * Test constructor.
     *
     */
    public void testRejectReason() {
        assertNotNull("The RejectReason instance should be created.", target);
    }

    /**
     * Test method getCompanyId.
     *
     */
    public void testGetCompanyId() {
        assertEquals("Equal is expected.", 0, target.getCompanyId());
    }

    /**
     * Test method setCompanyId.
     *
     */
    public void testSetCompanyId() {

        target.setCompanyId(1);

        assertEquals("Equal is expected.", 1, target.getCompanyId());
    }

    /**
     * Test method setCompanyId.
     *
     */
    public void testSetCompanyId_2() {

        target.setCompanyId(1);

        assertEquals("Equal is expected.", 1, target.getCompanyId());

        target.setChanged(false);

        target.setCompanyId(10);

        assertTrue("True is expected.", target.isChanged());
    }


    /**
     * Test method getDescription.
     *
     */
    public void testDescription() {
        assertNull("Null is expected.", target.getDescription());
    }

    /**
     * test method setDescription.
     *
     */
    public void testSetBody() {
        target.setDescription("des");

        assertEquals("Equal is expected.", "des", target.getDescription());
    }

    /**
     * test method setDescription.
     *
     */
    public void testSetBody_2() {
        target.setDescription("des");

        assertEquals("Equal is expected.", "des", target.getDescription());

        target.setChanged(false);

        target.setDescription("de");

        assertTrue("True is expected.", target.isChanged());
    }

    /**
     * Test method isActive.
     *
     */
    public void testIsActive() {
        assertFalse("False is expected.", target.isActive());
    }

    /**
     * Test method setActive.
     *
     */
    public void testSetActive() {

        target.setActive(true);

        assertTrue("True is expected.", target.isActive());
    }

    /**
     * Test method setActive.
     *
     */
    public void testSetActive_2() {

        target.setActive(true);

        assertTrue("True is expected.", target.isActive());

        target.setChanged(false);

        target.setActive(false);

        assertTrue("True is expected.", target.isChanged());

    }
}
