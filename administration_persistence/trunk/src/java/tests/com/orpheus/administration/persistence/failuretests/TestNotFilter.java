/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;
import com.orpheus.administration.persistence.impl.filter.NotFilter;

import junit.framework.TestCase;
/**
 * Unit tests for NotFilter class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestNotFilter extends TestCase {

    /**
     * Tests NotFilter(Filter filterToNegate) method with null Filter filterToNegate,
     * Expected IllegalArgumentException.
     */
    public void testNotFilter_NullFilterToNegate() {
        try {
        	new NotFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}