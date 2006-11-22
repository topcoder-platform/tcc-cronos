/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.orpheus.game.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author Zulander
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

	/**
	 * Accuracy test suites.
	 * @return accuracy test suites.
	 */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        
        suite.addTestSuite(AttributeScopeAccuracyTest.class);
        suite.addTestSuite(GameOperationConfigExceptionAccuracyTest.class);
        suite.addTestSuite(GameOperationLogicUtilityAccuracyTest.class);
        suite.addTestSuite(KeySubmissionHandlerAccuracyTest.class);
        suite.addTestSuite(MessageHandlerAccuracyTest.class);
        suite.addTestSuite(MessagePollResultAccuracyTest.class);
        suite.addTestSuite(PluginDownloadHandlerAccuracyTest.class);
        suite.addTestSuite(PuzzleRenderingHandlerAccuracyTest.class);
        suite.addTestSuite(PuzzleSolutionHandlerAccuracyTest.class);
        suite.addTestSuite(RegisterGameHandlerAccuracyTest.class);
        suite.addTestSuite(WinnerDataHandlerAccuracyTest.class);
        
        return suite;
    }

}
