/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author still
 * @version 1.0
 */
public class StressTests extends TestCase {
	/** The stress test suite
	 *
	 * @return stress test suite
	 */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(new TestSuite(GameInterfaceLogicStressTest.class));
        return suite;
    }
}
