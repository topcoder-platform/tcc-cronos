/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all failure test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Create failure tests suite.
     *
     * @return failure tests suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(MemberInformationFailureTest.suite());
        suite.addTest(MemberPhotoListServletFailureTest.suite());
        suite.addTest(MemberPhotoRemovalServletFailureTest.suite());
        suite.addTest(MemberPhotoUploadServletFailureTest.suite());

        return suite;
    }
}