/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test for StatisticsType class.
 * </p>
 *
 * @author pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class StatisticsTypeUnitTests extends TestCase {
    /**
     * Accuracy test for the method <code>getName()</code>.
     */
    public void testStatisticsType_getName1() {
        assertEquals("HISTORY", StatisticsType.HISTORY.getName());
    }

    /**
     * Accuracy test for the method <code>getName()</code>.
     */
    public void testStatisticsType_getName2() {
        assertEquals("AVERAGE", StatisticsType.AVERAGE.getName());
    }
}
