/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test for SideBySideStatistics class.
 * </p>
 *
 * @author pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class SideBySideStatisticsUnitTests extends TestCase {
    /**
     * ReviewerStatistics instance for testing.
     */
    private final SideBySideStatistics sideBySideStatistics = new SideBySideStatistics();

    /**
     * Accuracy test for all getters and setters.
     *
     * @throws Exception for JUnit.
     */
    public void testGettersAndSetters() throws Exception {
        TestsHelper.assertBasicGetterSetterBehavior(sideBySideStatistics, SideBySideStatistics.class);
    }
}
