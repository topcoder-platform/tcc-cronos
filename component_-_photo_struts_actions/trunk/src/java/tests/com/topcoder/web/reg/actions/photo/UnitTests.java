/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author mumujava
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * Aggregates all test cases.
     * </p>
     *
     * @return All test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(MemberPhotoRemovalExceptionTest.suite());
        suite.addTest(MemberPhotoActionConfigurationExceptionTest.suite());
        suite.addTest(MemberPhotoUploadExceptionTest.suite());

        suite.addTest(RemoveMemberPhotoActionTest.suite());
        suite.addTest(UploadMemberPhotoActionTest.suite());
        suite.addTest(BaseMemberPhotoActionTest.suite());
        return suite;
    }

}
