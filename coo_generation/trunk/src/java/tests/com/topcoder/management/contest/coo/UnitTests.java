/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.contest.coo.dependencyextractor.DotNetComponentDependencyExtractorTest;
import com.topcoder.management.contest.coo.dependencyextractor.JavaComponentDependencyExtractorTest;
import com.topcoder.management.contest.coo.impl.BaseCOOReportSerializerTest;
import com.topcoder.management.contest.coo.impl.BaseDBConnectorTest;
import com.topcoder.management.contest.coo.impl.ConfigurationExceptionTest;
import com.topcoder.management.contest.coo.impl.DefaultCOOReportGeneratorTest;
import com.topcoder.management.contest.coo.impl.componentmanager.DBComponentManagerTest;
import com.topcoder.management.contest.coo.impl.contestdataretriever.DBContestDataRetrieverTest;
import com.topcoder.management.contest.coo.serializer.PDFCOOReportSerializerTest;
import com.topcoder.management.contest.coo.serializer.XMLCOOReportSerializerTest;

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
     * <P>
     * Aggregates all Unit test cases.
     * </p>
     *
     * @return a test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(DotNetComponentDependencyExtractorTest.class);
        suite.addTestSuite(JavaComponentDependencyExtractorTest.class);

        suite.addTestSuite(ComponentDependencyExtractorExceptionTest.class);
        suite.addTestSuite(InvalidContestCategoryExceptionTest.class);
        suite.addTestSuite(ComponentTest.class);
        suite.addTestSuite(ComponentDependencyTest.class);
        suite.addTestSuite(COOReportTest.class);
        suite.addTestSuite(COOReportSerializerExceptionTest.class);
        suite.addTestSuite(COOReportGeneratorExceptionTest.class);
        suite.addTestSuite(ContestDataTest.class);
        suite.addTestSuite(ContestDataRetrieverExceptionTest.class);
        suite.addTestSuite(ComponentManagerExceptionTest.class);

        suite.addTestSuite(BaseCOOReportSerializerTest.class);
        suite.addTestSuite(BaseDBConnectorTest.class);
        suite.addTestSuite(ConfigurationExceptionTest.class);
        suite.addTestSuite(DefaultCOOReportGeneratorTest.class);

        suite.addTestSuite(DBComponentManagerTest.class);
        suite.addTestSuite(DBContestDataRetrieverTest.class);

        suite.addTestSuite(PDFCOOReportSerializerTest.class);
        suite.addTestSuite(XMLCOOReportSerializerTest.class);
        
        suite.addTestSuite(HelperTest.class);
//        suite.addTestSuite(Demo.class);
        return suite;
    }
}
