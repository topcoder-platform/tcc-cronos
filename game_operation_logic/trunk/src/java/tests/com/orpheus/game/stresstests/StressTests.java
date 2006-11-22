/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.orpheus.game.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class StressTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(MessageHandlerStressTest.class);
        suite.addTestSuite(KeySubmissionHandlerStressTest.class);
        suite.addTestSuite(PluginDownloadHandlerStressTests.class);
        suite.addTestSuite(PuzzleRenderingHandlerStressTests.class);
        suite.addTestSuite(PuzzleSolutionHandlerStressTest.class);
        suite.addTestSuite(RegisterGameHandlerStressTest.class);
        return suite;
    }
}

