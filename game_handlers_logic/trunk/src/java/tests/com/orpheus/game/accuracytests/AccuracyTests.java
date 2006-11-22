/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.orpheus.game.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(GameDataConfigurationExceptionAccuracyTest.class);
        suite.addTestSuite(ActiveGamesHandlerAccuracyTest.class);
        suite.addTestSuite(GameDetailHandlerAccuracyTest.class);
        suite.addTestSuite(LeaderBoardHandlerAccuracyTest.class);
        suite.addTestSuite(SlotValidationHandlerAccuracyTest.class);
        suite.addTestSuite(TestDomainHandlerAccuracyTest.class);
        suite.addTestSuite(TestTargetObjectHandlerAccuracyTest.class);
        suite.addTestSuite(UnlockedDomainsHandlerAccuracyTest.class);
        suite.addTestSuite(UpcomingDomainsHandlerAccuracyTest.class);
        suite.addTestSuite(UserGamesHandlerAccuracyTest.class);
        
        return suite;
    }

}
