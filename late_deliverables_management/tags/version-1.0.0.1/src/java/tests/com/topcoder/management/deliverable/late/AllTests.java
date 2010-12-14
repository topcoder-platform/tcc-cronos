/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.late;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.deliverable.late.accuracytests.AccuracyTests;
import com.topcoder.management.deliverable.late.failuretests.FailureTests;
import com.topcoder.management.deliverable.late.stresstests.StressTests;

/**
 * Unit tests of this components.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AllTests extends TestCase {

    /**
     * <p> Gets test suite. </p>
     *
     * @return test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(UnitTests.suite());
        suite.addTest(FailureTests.suite());
        suite.addTest(new JUnit4TestAdapter(AccuracyTests.class));
        suite.addTest(StressTests.suite());
        return suite;
    }

}
