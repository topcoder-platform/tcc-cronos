/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.orpheus.game;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.orpheus.game.accuracytests.AccuracyTests;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(AccuracyTests.suite());
        return suite;
    }

}
