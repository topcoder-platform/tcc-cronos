/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

import com.topcoder.util.dependency.extractor.BuildFileParsingHelperTest;
import com.topcoder.util.dependency.extractor.BuildFileProvisionExceptionTest;
import com.topcoder.util.dependency.extractor.DotNetDependenciesEntryExtractorTest;
import com.topcoder.util.dependency.extractor.JavaDependenciesEntryExtractorTest;
import com.topcoder.util.dependency.extractor.MultipleFormatDependenciesEntryExtractorTest;
import com.topcoder.util.dependency.extractor.PathListTest;
import com.topcoder.util.dependency.extractor.fileprovider.LocalBuildFileProviderTest;
import com.topcoder.util.dependency.extractor.fileprovider.ZipBuildFileProviderTest;
import com.topcoder.util.dependency.extractor.utility.ComponentDependencyExtractorUtilityTest;
import com.topcoder.util.dependency.persistence.BinaryFileDependenciesEntryPersistenceTest;
import com.topcoder.util.dependency.persistence.DefaultXmlDependenciesEntryPersistenceTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
     * Returns all unit tests.
     * </p>
     *
     * @return all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // package dependency
        suite.addTest(ComponentDependencyConfigurationExceptionTest.suite());
        suite.addTest(ComponentDependencyExceptionTest.suite());
        suite.addTest(DependenciesEntryExtractionExceptionTest.suite());
        suite.addTest(DependenciesEntryPersistenceExceptionTest.suite());
        suite.addTest(BaseDependenciesEntryExtractorTest.suite());
        suite.addTest(ComponentDependencyTest.suite());
        suite.addTest(ComponentLanguageTest.suite());
        suite.addTest(ComponentTest.suite());
        suite.addTest(DependenciesEntryTest.suite());
        suite.addTest(DependencyCategoryTest.suite());
        suite.addTest(DependencyTypeTest.suite());
        suite.addTest(UtilsTest.suite());
        // package dependency.extractor
        suite.addTest(BuildFileParsingHelperTest.suite());
        suite.addTest(BuildFileProvisionExceptionTest.suite());
        suite.addTest(DotNetDependenciesEntryExtractorTest.suite());
        suite.addTest(JavaDependenciesEntryExtractorTest.suite());
        suite.addTest(MultipleFormatDependenciesEntryExtractorTest.suite());
        suite.addTest(PathListTest.suite());
        // package dependency.extractor.fileprovider
        suite.addTest(LocalBuildFileProviderTest.suite());
        suite.addTest(ZipBuildFileProviderTest.suite());
        // package dependency.extractor.utility
        suite.addTest(ComponentDependencyExtractorUtilityTest.suite());
        // package dependency.persistence
        suite.addTest(BinaryFileDependenciesEntryPersistenceTest.suite());
        suite.addTest(DefaultXmlDependenciesEntryPersistenceTest.suite());
        // demo
        suite.addTest(Demo.suite());

        return suite;
    }

}
