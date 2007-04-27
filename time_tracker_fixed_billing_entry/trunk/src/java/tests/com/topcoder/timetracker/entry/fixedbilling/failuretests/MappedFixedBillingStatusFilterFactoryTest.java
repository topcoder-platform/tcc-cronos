/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;
import com.topcoder.timetracker.entry.fixedbilling.filterfactory.MappedFixedBillingStatusFilterFactory;

/**
 * Failure test for
 * <code>{@link com.topcoder.timetracker.entry.fixedbilling.filterfactory.MappedFixedBillingStatusFilterFactory}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class MappedFixedBillingStatusFilterFactoryTest extends TestCase {
    /**
     * <p>
     * Represents the MappedFixedBillingStatusFilterFactory for testing.
     * </p>
     */
    private MappedFixedBillingStatusFilterFactory factory;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        factory = new MappedFixedBillingStatusFilterFactory();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        factory = null;
    }

    /**
     * Failure test for
     * <code>{@link MappedFixedBillingStatusFilterFactory#createDescriptionFilter(String,StringMatchType)}</code>.
     */
    public void testMethodCreateDescriptionFilter_IAE() {
        try {
            factory.createDescriptionFilter(null, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            factory.createDescriptionFilter(null, StringMatchType.ENDS_WITH);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            factory.createDescriptionFilter("test", null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            factory.createDescriptionFilter(" ", StringMatchType.ENDS_WITH);
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
        return new TestSuite(MappedFixedBillingStatusFilterFactoryTest.class);
    }
}
