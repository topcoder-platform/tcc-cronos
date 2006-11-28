/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;
import com.orpheus.administration.persistence.impl.filter.NullFilter;

import junit.framework.TestCase;
/**
 * Unit tests for NullFilter class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestNullFilter extends TestCase {
    /**
     * Setup the environment for each testcase.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Setup the environment for each testcase.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests NullFilter(String name) method with null String name,
     * Expected IllegalArgumentException.
     */
    public void testNullFilter_NullName() {
        try {
            new NullFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
    /**
     * Tests NullFilter(String name) method with empty String name,
     * Expected IllegalArgumentException.
     */
    public void testNullFilter_EmptyName() {
        try {
            new NullFilter(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    
}