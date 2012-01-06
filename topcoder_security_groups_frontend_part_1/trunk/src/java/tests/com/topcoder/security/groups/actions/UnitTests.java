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
 * @author hanshuai
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * All unit test suite.
     * </p>
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(BaseActionTest.suite());
        suite.addTest(CreateGroupActionTest.suite());
        suite.addTest(CreateUpdateGroupActionTest.suite());
        suite.addTest(DeleteGroupActionTest.suite());
        suite.addTest(SearchGroupActionTest.suite());
        suite.addTest(SearchUserActionTest.suite());
        suite.addTest(SecurityGroupsActionConfigurationExceptionTest.suite());
        suite.addTest(SecurityGroupsActionExceptionTest.suite());
        suite.addTest(SecurityGroupsActionValidationExceptionTest.suite());
        suite.addTest(SessionAwareBaseActionTest.suite());
        suite.addTest(UpdateGroupActionTest.suite());
        suite.addTest(ViewGroupActionTest.suite());

        suite.addTest(DemoTest.suite());

        return suite;
    }

}
