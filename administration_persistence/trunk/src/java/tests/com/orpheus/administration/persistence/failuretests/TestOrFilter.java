/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;
import java.util.ArrayList;
import java.util.List;

import com.orpheus.administration.persistence.impl.filter.OrFilter;
import com.orpheus.administration.persistence.impl.filter.GreaterThanFilter;

import junit.framework.TestCase;
/**
 * Unit tests for OrFilter class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestOrFilter extends TestCase {

    /**
     * Tests OrFilter(Filter augend, Filter addend) method with null Filter augend,
     * Expected IllegalArgumentException.
     */
    public void testOrFilter_NullAugend() {
        try {
        	new OrFilter(null, new GreaterThanFilter("Greater", "1"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests OrFilter(Filter augend, Filter addend) method with null Filter addend,
     * Expected IllegalArgumentException.
     */
    public void testOrFilter_NullAddend() {
        try {
        	new OrFilter(new GreaterThanFilter("Greater", "1"), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests OrFilter(List filters) method with null List filters,
     * Expected IllegalArgumentException.
     */
    public void testOrFilter_NullFilters() {
        try {
        	new OrFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests OrFilter(List filters) method with empty List filters,
     * Expected IllegalArgumentException.
     */
    public void testOrFilter_EmptyFilters() {
        try {
        	new OrFilter(new ArrayList());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
    
    /**
     * Tests OrFilter(List filters) method with null with List filters,
     * Expected IllegalArgumentException.
     */
    public void testOrFilter_NullWithFilters() {
    	List list = new ArrayList();
    	list.add(null);
        try {
        	new OrFilter(list);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
    
    /**
     * Tests OrFilter(List filters) method with invalid List filters,
     * Expected IllegalArgumentException.
     */
    public void testOrFilter_InvalidFilters() {
    	List list = new ArrayList();
    	list.add("invalid");
        try {
        	new OrFilter(list);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}