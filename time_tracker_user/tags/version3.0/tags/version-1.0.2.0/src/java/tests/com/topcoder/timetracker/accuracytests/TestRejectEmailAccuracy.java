/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import com.cronos.timetracker.common.RejectEmail;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>RejectEmail</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestRejectEmailAccuracy extends TestCase {

    /**
     * Represents the RejectEmail instance for test.
     */
    private static RejectEmail target = new RejectEmail();

    /**
     * Test constructor.
     *
     */
    public void testRejectEmail() {
        assertNotNull("The RejectEmail instance should be created.", target);
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
     * Test method getBody.
     *
     */
    public void testGetBody() {
        target = new RejectEmail();
        assertNull("Null is expected.", target.getBody());
    }

    /**
     * test method setBody.
     *
     */
    public void testSetBody() {
        target.setBody("body");

        assertEquals("Equal is expected.", "body", target.getBody());
    }

    /**
     * test method setBody.
     *
     */
    public void testSetBody_2() {
        target.setBody("body");

        assertEquals("Equal is expected.", "body", target.getBody());

        target.setChanged(false);

        target.setBody("body2");

        assertTrue("true is expected.", target.isChanged());
    }

}
