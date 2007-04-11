/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.accuracytests;

import java.util.Arrays;
import java.util.Comparator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AccuracyTests extends TestCase {

    /**
     * Assembly all the accuracy tests.
     *
     * @return the test suite
     */
    public static Test suite() {
        // represents the test suite
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(InformixRatePersistenceAccuracyTest.class);
        suite.addTestSuite(RateEjbAccuracyTest.class);
        suite.addTestSuite(RateManagerAccuracyTest.class);
        suite.addTestSuite(RateAccuracyTest.class);
        try {
        RateAccuracyTest.class.getDeclaredField("");
        
        } catch (Exception e) {
            
        }
        
        Integer[] c = new Integer[100];
        Arrays.sort(c, new Comparator() {
            public int compare(Object i, Object j) {
                return 0;
            }
        });
        return suite;
    }

}
