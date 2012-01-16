/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author BlackMagic
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * <p>
     * All unit test suite.
     * </p>
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(CreateGroupActionAccTest.class);
        suite.addTestSuite(CreateUpdateGroupActionAccTest.class);
        suite.addTestSuite(DeleteGroupActionAccTest.class);
        suite.addTestSuite(SearchGroupActionAccTest.class);
        suite.addTestSuite(SearchUserActionAccTest.class);
        suite.addTestSuite(UpdateGroupActionAccTest.class);
        suite.addTestSuite(ViewGroupActionAccTest.class);

        return suite;
    }

}
