/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * <p>
     * Run test suits.
     * </p>
     * @return test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(MemberInformationAccuracyTest.class);
        suite.addTestSuite(MemberPhotoRemovalServletAccuracyTest.class);
        suite.addTestSuite(MemberPhotoUploadServletAccuracyTest.class);
        suite.addTestSuite(MemberPhotoListServletAccuracyTest.class);
        return suite;
    }
}
