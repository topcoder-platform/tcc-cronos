/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure tests suite.
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * <p>
     * Aggregates all failure test cases.
     * </p>
     * 
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(RemoveMemberPhotoActionFailureTest.class);
        suite.addTestSuite(UploadMemberPhotoActionFailureTest.class);

        return suite;
    }
}
