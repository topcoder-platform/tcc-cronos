/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import com.topcoder.project.phases.template.persistence.UnitTestXmlPhaseTemplatePersistence;
import com.topcoder.project.phases.template.startdategenerator.UnitTestRelativeWeekTimeStartDateGenerator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author flying2hk
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * Return the aggregated unit tests.
     * </p>
     * @return the aggregated unit tests
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(UnitTestPhaseTemplateException.class);
        suite.addTestSuite(UnitTestConfigurationException.class);
        suite.addTestSuite(UnitTestPersistenceException.class);
        suite.addTestSuite(UnitTestPhaseGenerationException.class);
        suite.addTestSuite(UnitTestStartDateGenerationException.class);
        suite.addTestSuite(UnitTestRelativeWeekTimeStartDateGenerator.class);
        suite.addTestSuite(UnitTestXmlPhaseTemplatePersistence.class);
        suite.addTestSuite(UnitTestDefaultPhaseTemplate.class);
        suite.addTestSuite(Demo.class);
        return suite;
    }

}
