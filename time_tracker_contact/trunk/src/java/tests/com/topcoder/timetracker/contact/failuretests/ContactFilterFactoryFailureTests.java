/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.failuretests;

import com.topcoder.timetracker.contact.ContactFilterFactory;

import junit.framework.TestCase;


/**
 * <p>Failure test cases for the class ContactFilterFactory.</p>
 * 
 * @author waits
 * @version 1.0
 * @since Apr 11, 2007
 */
public class ContactFilterFactoryFailureTests extends TestCase {
    /**
     * <p>
     * Test method <code>createCreatedInFilter()</code>.
     * </p>
     */
    public void testCreateCreatedInFilter_nullValues() {
        try {
            ContactFilterFactory.createCreatedInFilter(null, null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createModifiedInFilter()</code>. with
     * null values, iae expected.
     * </p>
     */
    public void testCreateModifiedInFilter_nullValues() {
        try {
            ContactFilterFactory.createModifiedInFilter(null, null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createCreatedByFilter()</code> with null
     * values, iae expected.
     * </p>
     */
    public void testCreateCreatedByFilter_nullValue() {
        try {
            ContactFilterFactory.createCreatedByFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createModifiedByFilter()</code> 
     * with null values, iae expected.
     * </p>
     */
    public void testCreateModifiedByFilter_nullValue() {
        try {
            ContactFilterFactory.createModifiedByFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createEmailAddressFilter()</code> with null value
     * , iae expected.
     * </p>
     */
    public void testCreateCityFilter_nullValue() {
        try {
            ContactFilterFactory.createEmailAddressFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createPhoneNumberFilter()</code> with null value,
     * iae expected.
     * </p>
     */
    public void testCreateZipCodeFilter_nullValue() {
        try {
            ContactFilterFactory.createPhoneNumberFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createTypeFilter()</code> with null value,
     * iae expected.
     * </p>
     */
    public void testCreateTypeFilter_nullValue() {
        try {
            ContactFilterFactory.createTypeFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createEntityIDFilter()</code> with null value,
     * iae expected.
     * </p>
     */
    public void testCreateEntityIDFilter_nullValue() {
        try {
            ContactFilterFactory.createTypeFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>andFilter()</code> with null values,
     * iae expected.
     * </p>
     */
    public void testAndFilter_nullValues() {
        try {
            ContactFilterFactory.andFilter(null, null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>orFilter()</code> with null value,
     * iae expected.
     * </p>
     */
    public void testOrFilter_nullValue() {
        try {
            ContactFilterFactory.orFilter(null, null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>notFilter()</code> with null value, iae expected.
     * </p>
     */
    public void testNotFilter_nullValue() {
        try {
            ContactFilterFactory.notFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
}
