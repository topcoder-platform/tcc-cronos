/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This test case aggregates all stress test cases.
 *
 * @author TopCoder
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * Aggregates all Stress test cases.
     *
     * @return the aggregated stress test cases
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        return suite;
    }
}