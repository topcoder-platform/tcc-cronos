/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import com.cronos.timetracker.user.AccountStatus;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>AccountStatus</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestAccountStatusAccuracy extends TestCase {

    /**
     * Represents the AccountStatus instance for test.
     */
    private AccountStatus status = new AccountStatus();

    /**
     * Test constructor.
     *
     */
    public void testAccountStatus() {
        assertNotNull("Should not be null.", status);
    }

    /**
     * Test method getDescription.
     *
     */
    public void testGetDescription() {
        assertNull("The original value should be null.", status.getDescription());
    }

    /**
     * Test method setDescription.
     *
     */
    public void testSetDescription_1() {

        status.setDescription("des");

        assertEquals("Equal is expected.", "des", status.getDescription());
    }

    /**
     * Test method setDescription.
     *
     */
    public void testSetDescription_2() {

        status.setDescription("des");

        assertEquals("Equal is expected.", "des", status.getDescription());

        status.setChanged(false);

        status.setDescription("des");

        assertFalse("False is expected.", status.isChanged());
    }

    /**
     * Test method setDescription.
     *
     */
    public void testSetDescription_3() {

        status.setDescription("des");

        assertEquals("Equal is expected.", "des", status.getDescription());

        status.setChanged(false);

        status.setDescription("ddddd ");

        assertTrue("True is expected.", status.isChanged());
    }
}
