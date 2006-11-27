/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.orpheus.game;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(BaseGameDataManagerTests.class);
        suite.addTestSuite(GameDataExceptionTests.class);
        suite.addTestSuite(GameDataManagerConfigurationExceptionTests.class);
        suite.addTestSuite(GameDataManagerImplTests.class);
        suite.addTestSuite(GameDataUtilityTests.class);
        suite.addTestSuite(Demo.class);

        return suite;
    }
}
