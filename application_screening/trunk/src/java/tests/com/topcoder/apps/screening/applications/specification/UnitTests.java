/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * UnitTests.java
 */
package com.topcoder.apps.screening.applications.specification;

import com.topcoder.apps.screening.applications.specification.impl.DefaultSubmissionValidatorFactoryTest;
import com.topcoder.apps.screening.applications.specification.impl.formatters.TextValidationOutputFormatterTest;
import com.topcoder.apps.screening.applications.specification.impl.formatters.XMLValidationOutputFormatterTest;
import com.topcoder.apps.screening.applications.specification.impl.validators.ActivityDiagramPathReportGeneratorTest;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultActivityDiagramNamingValidatorTest;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultActivityDiagramValidatorTest;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultUseCaseDiagramNamingValidatorTest;
import com.topcoder.apps.screening.applications.specification.impl.validators.DefaultUseCaseDiagramValidatorTest;
import com.topcoder.apps.screening.applications.specification.impl.validators.ActivityDiagramValidatorTest;
import com.topcoder.apps.screening.applications.specification.impl.validators.UseCaseDiagramValidatorTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(new TestSuite(ValidationOutputTypeTest.class));
        suite.addTest(new TestSuite(ValidationOutputTest.class));
        suite.addTest(new TestSuite(FormatterExceptionTest.class));
        suite.addTest(new TestSuite(ConfigurationExceptionTest.class));
        suite.addTest(new TestSuite(SubmissionTest.class));
        suite.addTest(new TestSuite(UseCaseDiagramValidatorTest.class));
        suite.addTest(new TestSuite(DefaultUseCaseDiagramValidatorTest.class));
        suite.addTest(new TestSuite(DefaultUseCaseDiagramNamingValidatorTest.class));
        suite.addTest(new TestSuite(DefaultActivityDiagramValidatorTest.class));
        suite.addTest(new TestSuite(DefaultActivityDiagramNamingValidatorTest.class));
        suite.addTest(new TestSuite(ActivityDiagramPathReportGeneratorTest.class));
        suite.addTest(new TestSuite(ActivityDiagramValidatorTest.class));
        suite.addTest(new TestSuite(SubmissionValidatorTest.class));
        suite.addTest(new TestSuite(XMLValidationOutputFormatterTest.class));
        suite.addTest(new TestSuite(TextValidationOutputFormatterTest.class));
        suite.addTest(new TestSuite(DefaultSubmissionValidatorFactoryTest.class));
        suite.addTest(new TestSuite(ValidationManagerTest.class));   
        suite.addTest(new TestSuite(Demo.class));
        return suite;
    }
}
