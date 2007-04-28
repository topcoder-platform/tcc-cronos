/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.project.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.project.searchfilters.NotFilter;

/**
 * Failure test for {@link com.topcoder.timetracker.project.searchfilters.NotFilter} class.
 *
 * @author kr00tki
 * @version 1.1
 */
public class NotFilterFailureTest extends TestCase {

    /**
     * Tests the {@link NotFilter#NotFilter(com.topcoder.timetracker.project.searchfilters.Filter)} constructor
     * failure. Checks if IAE is thrown when filter is <code>null</code>.
     */
    public void testNotFilter_Null() {
        try {
            new NotFilter(null);
            fail("Null filter, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

}
