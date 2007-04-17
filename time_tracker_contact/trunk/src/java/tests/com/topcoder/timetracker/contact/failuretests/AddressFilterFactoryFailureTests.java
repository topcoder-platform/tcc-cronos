/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.failuretests;

import com.topcoder.timetracker.contact.AddressFilterFactory;

import junit.framework.TestCase;


/**
 * <p>Failure test cases for the class Address.</p>
 * 
 * @author waits
 * @version 1.0
 * @since Apr 11, 2007
 */
public class AddressFilterFactoryFailureTests extends TestCase {
    /**
     * <p>
     * Test method <code>createCreatedInFilter()</code>.
     * </p>
     */
    public void testCreateCreatedInFilter() {
        try {
            AddressFilterFactory.createCreatedInFilter(null, null);
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
            AddressFilterFactory.createModifiedInFilter(null, null);
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
            AddressFilterFactory.createCreatedByFilter(null);
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
            AddressFilterFactory.createModifiedByFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createCityFilter()</code>.
     * </p>
     */
    public void testCreateCityFilter() {
        try {
            AddressFilterFactory.createCityFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createStateIDFilter()</code>.
     * </p>
     */
    public void testCreateStateIDFilter() {
        try {
            AddressFilterFactory.createStateIDFilter(-1);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createZipCodeFilter()</code>.
     * </p>
     */
    public void testCreateZipCodeFilter() {
        try {
            AddressFilterFactory.createZipCodeFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createCountryIDFilter()</code>.
     * </p>
     */
    public void testCreateCountryIDFilter() {
        try {
            AddressFilterFactory.createCountryIDFilter(-1);
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
            AddressFilterFactory.createTypeFilter(null);
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
            AddressFilterFactory.createTypeFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createCountryNameFilter()</code>.
     * </p>
     */
    public void testCreateCountryNameFilter() {
        try {
            AddressFilterFactory.createCountryNameFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test method <code>createStateNameFilter()</code>.
     * </p>
     */
    public void testCreateStateNameFilter() {
        try {
            AddressFilterFactory.createStateNameFilter(null);
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
            AddressFilterFactory.andFilter(null, null);
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
            AddressFilterFactory.orFilter(null, null);
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
            AddressFilterFactory.notFilter(null);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
}
