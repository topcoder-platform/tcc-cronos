/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report;

import junit.framework.TestCase;


/**
 * This class contains the unit tests for {@link FilterType}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FilterTypeTest extends TestCase {

    /**
     * This method tests {@link FilterType#getType()} for correctness.
     */
    public void testGetType() {
        assertEquals("EQUALITY", FilterType.EQUALITY.getType());
        assertEquals("RANGE", FilterType.RANGE.getType());
        assertEquals("IN", FilterType.IN.getType());
    }

}
