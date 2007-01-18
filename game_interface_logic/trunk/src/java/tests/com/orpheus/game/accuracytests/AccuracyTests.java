/*
 * Copyright (C) 2006 TopCoder Inc., All rights reserved.
 */
package com.orpheus.game.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(GameDataUtilityTests.class);
        suite.addTestSuite(JndiLookupDesignationTests.class);
        suite.addTestSuite(BaseGameDataManagerTests.class);
        suite.addTestSuite(GameDataExceptionTests.class);
        suite.addTestSuite(GameDataManagerImplTests.class);
        return suite;
    }
}
