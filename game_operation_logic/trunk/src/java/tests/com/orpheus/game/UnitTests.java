/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.orpheus.game;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.orpheus.game.result.MessagePollResultTest;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        
        suite.addTestSuite(KeySubmissionHandlerTest.class);
        suite.addTestSuite(MessageHandlerTest.class);
        suite.addTestSuite(PluginDownloadHandlerTest.class);
        suite.addTestSuite(PuzzleRenderingHandlerTest.class);
        suite.addTestSuite(PuzzleSolutionHandlerTest.class);
        suite.addTestSuite(KeySubmissionHandlerTest.class);
        suite.addTestSuite(RegisterGameHandlerTest.class);
        suite.addTestSuite(WinnerDataHandlerTest.class);
        suite.addTestSuite(MessagePollResultTest.class);
        suite.addTestSuite(GameOperationLogicUtilityTest.class);
        suite.addTestSuite(GameOperationConfigExceptionTest.class); 
        suite.addTestSuite(AttributeScopeTest.class);
        suite.addTestSuite(Demo.class);
        return suite;
    }

}
