/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.onlinereview.external.accuracytests;


import com.cronos.onlinereview.external.accuracytests.impl.ExternalProjectImplAccuracyTest;
import com.cronos.onlinereview.external.accuracytests.impl.ExternalUserImplAccuracyTest;
import com.cronos.onlinereview.external.impl.DBProjectRetrievalAccuracyTest;
import com.cronos.onlinereview.external.impl.DBUserRetrievalAccuracyTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(RatingInfoAccuracyTest.class);
        suite.addTestSuite(RatingTypeAccuracyTest.class);
        suite.addTestSuite(DBProjectRetrievalAccuracyTest.class);
        suite.addTestSuite(ExternalProjectImplAccuracyTest.class);
        suite.addTestSuite(ExternalUserImplAccuracyTest.class);
        suite.addTestSuite(DBUserRetrievalAccuracyTest.class);
        
        return suite;
    }
}
