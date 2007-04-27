/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.ManagerFactory;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.entry.fixedbilling.ManagerFactory}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class ManagerFactoryTest extends TestCase {
    /**
     * Failure test for <code>{@link ManagerFactory#getFixedBililngStatusManager()}</code>.
     */
    public void testMethodGetFixedBililngStatusManager() {
        try {
            ManagerFactory.getFixedBililngStatusManager();
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link ManagerFactory#getFixedBillingEntryManager()}</code>.
     */
    public void testMethodGetFixedBillingEntryManager() {
        try {
            ManagerFactory.getFixedBillingEntryManager();
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ManagerFactoryTest.class);
    }
}
