/**
 * Copyright (C) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.buildutility.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author mgmg
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * Aggregate all stress tests.
     *
     * @return
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(BuildScriptGeneratorImplStressTest.suite());

        return suite;
    }
}
