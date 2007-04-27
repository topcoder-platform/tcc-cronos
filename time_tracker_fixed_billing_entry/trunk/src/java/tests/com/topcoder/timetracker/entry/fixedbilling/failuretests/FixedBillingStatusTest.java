/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class FixedBillingStatusTest extends TestCase {
    /**
     * Failure test for <code>{@link FixedBillingStatus#setDescription(String)}</code>.
     */
    public void testMethodSetDescription_String() {
        FixedBillingStatus status = new FixedBillingStatus();
        try {
            status.setDescription(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            status.setDescription(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(FixedBillingStatusTest.class);
    }
}
