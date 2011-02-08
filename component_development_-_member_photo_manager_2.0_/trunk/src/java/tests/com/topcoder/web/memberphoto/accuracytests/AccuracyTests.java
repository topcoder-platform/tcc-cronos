/*
 * Copyright (c) 2011, TopCoder, Inc. All rights reserved
 */
package com.topcoder.web.memberphoto.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class AccuracyTests extends TestCase {

    /**
     * @return all Accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ImageAccuracyTest.class);
        suite.addTestSuite(MemberImageAccuracyTest.class);

        suite.addTestSuite(MemberPhotoManagerImplAccuracyTest.class);
        suite.addTestSuite(JPAMemberPhotoDAOAccuracyTest.class);

        return suite;
    }
}
