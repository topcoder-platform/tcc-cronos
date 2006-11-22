/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * Unit test suite.
     *
     * @return unit test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ActiveGamesHandlerTest.class);
        suite.addTestSuite(GameDetailHandlerTest.class);
        suite.addTestSuite(LeaderBoardHandlerTest.class);
        suite.addTestSuite(SlotValidationHandlerTest.class);
        suite.addTestSuite(TestDomainHandlerTest.class);
        suite.addTestSuite(TestTargetObjectHandlerTest.class);
        suite.addTestSuite(UnlockedDomainsHandlerTest.class);
        suite.addTestSuite(UpcomingDomainsHandlerTest.class);
        suite.addTestSuite(UserGamesHandlerTest.class);
        return suite;
    }

}
