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
 * @author waits
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * return suite to junit
     *
     * @return test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(GameDataManagerImplCtorFailureTests.class);
        suite.addTestSuite(GameDataManagerImplFailureTests.class);
        suite.addTestSuite(GameDataUtilityFailureTests.class);

        return suite;
    }
}
