/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
package com.orpheus.plugin.firefox.failuretests;

import junit.framework.TestCase;

import java.util.Calendar;

import com.orpheus.plugin.firefox.OrpheusServer;
import com.orpheus.plugin.firefox.FirefoxExtensionHelper;


/**
 * Tests the failure cases for the OrpheusServer class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class OrpheusServerTest extends TestCase {
    /** The server to be tested. */
    private OrpheusServer server;

    /**
     * Sets up the OrpheusServer for testing.
     */
    protected void setUp() throws Exception {
        server = new OrpheusServer("http://www.google.com", "some", Calendar.getInstance(), 1,
                new FirefoxExtensionHelper());
    }

    /**
     * Test the setPollTime method with a negative parameter.
     */
    public void testSetPollTimeNegative() throws Exception {
        try {
            server.setPollTime(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
        }
    }
}
