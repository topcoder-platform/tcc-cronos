/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Test suite for the failure tests.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(BaseActionFailureTests.suite());
        suite.addTest(DeleteGroupActionFailureTests.suite());
        suite.addTest(SearchUserActionFailureTests.suite());
        suite.addTest(CreateGroupActionFailureTests.suite());
        suite.addTest(SessionAwareBaseActionFailureTests.suite());
        suite.addTest(ViewGroupActionFailureTests.suite());
        suite.addTest(UpdateGroupActionFailureTests.suite());
        suite.addTest(SearchGroupActionFailureTests.suite());
        suite.addTest(CreateUpdateGroupActionFailureTests.suite());

        return suite;
    }

}
