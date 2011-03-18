/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Create unit tests suite.
     * @return unit tests suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        // suite.addTest(XXX.suite());
        suite.addTest(HelperTest.suite());
        suite.addTest(DemoTest.suite());

        suite.addTest(MemberInformationTest.suite());

        suite.addTest(MemberInformationRetrievalExceptionTest.suite());
        suite.addTest(MemberPhotoRemovalExceptionTest.suite());
        suite.addTest(MemberPhotoUploadExceptionTest.suite());
        suite.addTest(MemberPhotoListingExceptionTest.suite());

        suite.addTest(MemberPhotoRemovalServletTest.suite());
        suite.addTest(MemberPhotoListServletTest.suite());
        suite.addTest(MemberPhotoUploadServletTest.suite());

        return suite;
    }

}
