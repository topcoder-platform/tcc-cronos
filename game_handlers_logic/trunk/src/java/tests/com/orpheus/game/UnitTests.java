/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * Unit test suite.
     *
     * @return unit test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(GameDataHelperUnitTest.suite());
        suite.addTest(ActiveGamesHandlerUnitTest.suite());
        suite.addTest(GameDataConfigurationExceptionUnitTest.suite());
        suite.addTest(GameDetailHandlerUnitTest.suite());
        suite.addTest(ImplementationHelperUnitTest.suite());
        suite.addTest(LeaderBoardHandlerUnitTest.suite());
        suite.addTest(SlotValidationHandlerUnitTest.suite());
        suite.addTest(TestDomainHandlerUnitTest.suite());
        suite.addTest(TestTargetObjectHandlerUnitTest.suite());
        suite.addTest(UnlockedDomainsHandlerUnitTest.suite());
        suite.addTest(UpcomingDomainsHandlerUnitTest.suite());
        suite.addTest(UserGamesHandlerUnitTest.suite());
        suite.addTest(DemoTest.suite());
        return suite;
    }

}
