/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;
import com.topcoder.timetracker.entry.fixedbilling.filterfactory.MappedBaseFilterFactory;

/**
 * Failure test for
 * <code>{@link com.topcoder.timetracker.entry.fixedbilling.filterfactory.MappedBaseFilterFactory}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class MappedBaseFilterFactoryTest extends TestCase {
    /**
     * <p>
     * Represents the MappedBaseFilterFactory for testing.
     * </p>
     */
    private MappedBaseFilterFactory factory;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        factory = new MappedBaseFilterFactory();
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
     * Failure test for <code>{@link MappedBaseFilterFactory#createCreationDateFilter(Date,Date)}</code>.
     */
    public void testMethodCreateCreationDateFilter_IAE() {
        try {
            factory.createCreationDateFilter(null, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            factory.createCreationDateFilter(new Date(99999), new Date(88888));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link MappedBaseFilterFactory#createCreationUserFilter(String,StringMatchType)}</code>.
     */
    public void testMethodCreateCreationUserFilter_String_IAE() {
        try {
            factory.createCreationUserFilter(null, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            factory.createCreationUserFilter("test", null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            factory.createCreationUserFilter(" ", null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link MappedBaseFilterFactory#createModificationUserFilter(String,StringMatchType)}</code>.
     */
    public void testMethodCreateModificationUserFilter_IAE() {
        try {
            factory.createModificationUserFilter(null, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            factory.createModificationUserFilter("test", null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            factory.createModificationUserFilter(" ", null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link MappedBaseFilterFactory#createModificationDateFilter(Date,Date)}</code>.
     */
    public void testMethodCreateModificationDateFilter_Date_Date() {
        try {
            factory.createModificationDateFilter(null, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            factory.createModificationDateFilter(new Date(99999), new Date(88888));
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
        return new TestSuite(MappedBaseFilterFactoryTest.class);
    }
}
