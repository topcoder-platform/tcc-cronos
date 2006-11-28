/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;
import com.orpheus.administration.persistence.impl.SearchCriteriaDTOImpl;

import junit.framework.TestCase;
/**
 * Unit tests for SearchCriteriaDTOImpl class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestSearchCriteriaDTOImpl extends TestCase {

    /**
     * Tests SearchCriteriaDTOImpl(Filter searchFilter) method with null Filter searchFilter,
     * Expected IllegalArgumentException.
     */
    public void testSearchCriteriaDTOImpl_NullSearchFilter() {
        try {
            new SearchCriteriaDTOImpl(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    
}