/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import com.topcoder.management.resource.persistence.PersistenceResourceManagerTest;
import com.topcoder.management.resource.persistence.ResourcePersistenceExceptionTest;
import com.topcoder.management.resource.search.NotificationFilterBuilderTest;
import com.topcoder.management.resource.search.NotificationTypeFilterBuilderTest;
import com.topcoder.management.resource.search.ResourceFilterBuilderTest;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilderTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This test case aggregates all Unit test cases.
 *
 * @author kinfkong
 * @author pvmagacho
 * @version 1.4
 * @since 1.1
 */
public class UnitTests extends TestCase {
    /**
     * Tests suites.
     *
     * @return returns the unit test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // Unit tests for the package: com.topcoder.management.resource
        suite.addTestSuite(AuditableResourceStructureTest.class);
        suite.addTestSuite(IdAlreadySetExceptionTest.class);
        suite.addTestSuite(NotificationTest.class);
        suite.addTestSuite(NotificationTypeTest.class);
        suite.addTestSuite(ResourceRoleTest.class);
        suite.addTestSuite(ResourceTest.class);

        // Unit tests for the package: com.topcoder.management.resource.persistence
        suite.addTestSuite(ResourcePersistenceExceptionTest.class);
        suite.addTestSuite(PersistenceResourceManagerTest.class);

        // Unit tests for the package: com.topcoder.management.resource.search
        suite.addTestSuite(NotificationFilterBuilderTest.class);
        suite.addTestSuite(NotificationTypeFilterBuilderTest.class);
        suite.addTestSuite(ResourceFilterBuilderTest.class);
        suite.addTestSuite(ResourceRoleFilterBuilderTest.class);

        // Unit tests for the demo
        suite.addTestSuite(Demo.class);

        // Unit tests for the helper
        suite.addTestSuite(HelperTest.class);

        // Unit tests for the review statistics - since 1.4
        suite.addTestSuite(ReviewerStatisticsConfigurationExceptionUnitTests.class);
        suite.addTestSuite(ReviewerStatisticsManagerExceptionUnitTests.class);
        suite.addTestSuite(ReviewerStatisticsPersistenceExceptionUnitTests.class);
        suite.addTestSuite(ReviewerStatisticsUnitTests.class);
        suite.addTestSuite(SideBySideStatisticsUnitTests.class);
        suite.addTestSuite(StatisticsTypeUnitTests.class);
        suite.addTestSuite(ReviewerStatisticsManagerImplUnitTests.class);
        suite.addTestSuite(DemoReviewerStatistics.class);

        return suite;
    }
}
