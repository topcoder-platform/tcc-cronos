/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all stress test cases.
 * </p>
 *
 * @author KLW
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * aggregates all stress test cases.
     *
     * @return aggregates all stress test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(DigitalRunEntitiesStressTests.class);

        return suite;
    }
}
