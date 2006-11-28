/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;

import com.orpheus.administration.persistence.impl.RSSSearchCriteriaTranslator;

import junit.framework.TestCase;


/**
 * Unit tests for RSSSearchCriteriaTranslator class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestRSSSearchCriteriaTranslator extends TestCase {
    /**
     * Tests assembleSearchCriteriaDTO(SearchCriteria searchCriteria) method with null
     * SearchCriteria searchCriteria, Expected IllegalArgumentException.
     */
    public void testAssembleSearchCriteriaDTO_NullSearchCriteria() {
        try {
            new RSSSearchCriteriaTranslator().assembleSearchCriteriaDTO(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
