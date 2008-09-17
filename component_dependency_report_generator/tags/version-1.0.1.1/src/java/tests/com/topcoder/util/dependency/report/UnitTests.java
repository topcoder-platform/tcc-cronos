/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.dependency.report.impl.CsvDependencyReportGeneratorTestAcc;
import com.topcoder.util.dependency.report.impl.CsvDependencyReportGeneratorTestExp;
import com.topcoder.util.dependency.report.impl.HtmlDependencyReportGeneratorTestAcc;
import com.topcoder.util.dependency.report.impl.HtmlDependencyReportGeneratorTestExp;
import com.topcoder.util.dependency.report.impl.XmlDependencyReportGeneratorTestAcc;
import com.topcoder.util.dependency.report.impl.XmlDependencyReportGeneratorTestExp;
import com.topcoder.util.dependency.report.utility.DependencyReportGeneratorUtilityTestAcc;
import com.topcoder.util.dependency.report.utility.DependencyReportGeneratorUtilityTestExp;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Gets all Unit test cases.
     * </p>
     *
     * @return All Unit test cases.
     */
    public static Test suite() {

        final TestSuite suite = new TestSuite();

        suite.addTestSuite(DependencyReportGenerationExceptionUnitTests.class);
        suite.addTestSuite(CircularComponentDependencyExceptionUnitTests.class);
        suite.addTestSuite(ComponentIdNotFoundExceptionUnitTests.class);
        suite.addTestSuite(DependencyReportConfigurationExceptionUnitTests.class);

        suite.addTestSuite(HelperUnitTests.class);

        suite.addTestSuite(BaseDependencyReportGeneratorTestAcc.class);
        suite.addTestSuite(BaseDependencyReportGeneratorTestExp.class);

        suite.addTestSuite(CsvDependencyReportGeneratorTestAcc.class);
        suite.addTestSuite(CsvDependencyReportGeneratorTestExp.class);

        suite.addTestSuite(HtmlDependencyReportGeneratorTestAcc.class);
        suite.addTestSuite(HtmlDependencyReportGeneratorTestExp.class);

        suite.addTestSuite(XmlDependencyReportGeneratorTestAcc.class);
        suite.addTestSuite(XmlDependencyReportGeneratorTestExp.class);

        suite.addTestSuite(DependencyReportGeneratorUtilityTestAcc.class);
        suite.addTestSuite(DependencyReportGeneratorUtilityTestExp.class);

        suite.addTestSuite(Demo.class);

        return suite;
    }

}
