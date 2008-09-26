/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author zhengjuyu
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * <p>
     * Returns the suite of failure test cases.
     * </p>
     * @return the suite of failure test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(MockJiveForumServiceFailure.suite());

        return suite;
    }
}
