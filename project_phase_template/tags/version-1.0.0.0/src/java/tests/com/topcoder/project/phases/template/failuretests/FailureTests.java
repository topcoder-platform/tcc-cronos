/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.project.phases.template.failuretests;

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
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(XmlPhaseTemplatePersistenceFailureTest.class);
        suite.addTestSuite(RelativeWeekTimeStartDateGeneratorFailureTest.class);
        suite.addTestSuite(DefaultPhaseTemplateFailureTest.class);
        return suite;
    }
}