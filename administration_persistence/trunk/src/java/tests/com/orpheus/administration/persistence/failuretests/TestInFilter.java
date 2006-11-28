/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;
import java.util.ArrayList;
import java.util.List;

import com.orpheus.administration.persistence.impl.filter.InFilter;

import junit.framework.TestCase;
/**
 * Unit tests for InFilter class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestInFilter extends TestCase {
	
	/**
	 * list of values used to test.
	 */
	private List list = new ArrayList();

    /**
     * Tests InFilter(String name, List values) method with null String name,
     * Expected IllegalArgumentException.
     */
    public void testInFilter_NullName() {
    	list.add("v");
        try {
        	new InFilter(null, list);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
    /**
     * Tests InFilter(String name, List values) method with empty String name,
     * Expected IllegalArgumentException.
     */
    public void testInFilter_EmptyName() {
    	list.add("v");
        try {
        	new InFilter(" ", list);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests InFilter(String name, List values) method with null List values,
     * Expected IllegalArgumentException.
     */
    public void testInFilter_NullValues() {
        try {
        	new InFilter("in", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests InFilter(String name, List values) method with empty List values,
     * Expected IllegalArgumentException.
     */
    public void testInFilter_EmptyValues() {
        try {
        	new InFilter("in", list);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
    
    /**
     * Tests InFilter(String name, List values) method with empty List values,
     * Expected IllegalArgumentException.
     */
    public void testInFilter_InvalidValues() {
    	list.add(new Object());
        try {
        	new InFilter("in", list);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

}