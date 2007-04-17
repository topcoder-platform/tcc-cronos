/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import junit.framework.TestCase;

/**
 * <p>This test case contains failure tests for <code>ContactFilterFactory</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ContactFilterFactoryTestExp extends TestCase {
    /**
     * <p>
     * Test method <code>createCreatedInFilter()</code>.
     * </p>
     */
    public void testCreateCreatedInFilter() {
        try {
            ContactFilterFactory.createCreatedInFilter(null, null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createModifiedInFilter()</code>.
     * </p>
     */
    public void testCreateModifiedInFilter() {
        try {
            ContactFilterFactory.createModifiedInFilter(null, null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createCreatedByFilter()</code>.
     * </p>
     */
    public void testCreateCreatedByFilter() {
        try {
            ContactFilterFactory.createCreatedByFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createModifiedByFilter()</code>.
     * </p>
     */
    public void testCreateModifiedByFilter() {
        try {
            ContactFilterFactory.createModifiedByFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createEmailAddressFilter()</code>.
     * </p>
     */
    public void testCreateCityFilter() {
        try {
            ContactFilterFactory.createEmailAddressFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createPhoneNumberFilter()</code>.
     * </p>
     */
    public void testCreateZipCodeFilter() {
        try {
            ContactFilterFactory.createPhoneNumberFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createTypeFilter()</code>.
     * </p>
     */
    public void testCreateTypeFilter() {
        try {
            ContactFilterFactory.createTypeFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createEntityIDFilter()</code>.
     * </p>
     */
    public void testCreateEntityIDFilter() {
        try {
            ContactFilterFactory.createTypeFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>andFilter()</code>.
     * </p>
     */
    public void testAndFilter() {
        try {
            ContactFilterFactory.andFilter(null, null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>orFilter()</code>.
     * </p>
     */
    public void testOrFilter() {
        try {
            ContactFilterFactory.orFilter(null, null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>notFilter()</code>.
     * </p>
     */
    public void testNotFilter() {
        try {
            ContactFilterFactory.notFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
}
