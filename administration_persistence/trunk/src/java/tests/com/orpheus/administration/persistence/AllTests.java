/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.orpheus.administration.persistence.accuracytests.AccuracyTests;
import com.orpheus.administration.persistence.failuretests.FailureTests;
import com.orpheus.administration.persistence.stresstests.StressTests;

/**
 * <p>This test case aggregates all test cases for the <i>Administation Persistence</i> component.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class AllTests extends TestCase {
    /**
     * Returns all test cases for the <i>Administation Persistence</i> component.
     *
     * @return all test cases for the <i>Administation Persistence</i> component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //unit tests
        suite.addTest(UnitTests.suite());

        //accuracy tests
        suite.addTest(AccuracyTests.suite());

        //failure tests
        suite.addTest(FailureTests.suite());

        //stress tests
        suite.addTest(StressTests.suite());

        return suite;
    }
}
