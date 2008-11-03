/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.confluence.entities.ComponentTypeTests;
import com.topcoder.confluence.entities.ConfluenceCatalogTests;
import com.topcoder.confluence.entities.ConfluencePageCreationResultTests;
import com.topcoder.confluence.entities.PageTests;
import com.topcoder.confluence.impl.DefaultConfluenceManagerTest1;
import com.topcoder.confluence.impl.DefaultConfluenceManagerTest2;

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
     * Aggregates all Unit test cases.
     * </p>
     *
     * @return test cases suit
     */
    public static Test suite() {

        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ConfluenceAuthenticationFailedExceptionTests.class);

        suite.addTestSuite(ConfluenceConfigurationExceptionTests.class);

        suite.addTestSuite(ConfluenceConnectionExceptionTests.class);

        suite.addTestSuite(ConfluenceManagerExceptionTests.class);

        suite.addTestSuite(ConfluenceNotAuthorizedExceptionTests.class);

        suite.addTestSuite(HelperTests.class);

        suite.addTestSuite(ComponentTypeTests.class);

        suite.addTestSuite(ConfluenceCatalogTests.class);

        suite.addTestSuite(ConfluencePageCreationResultTests.class);

        suite.addTestSuite(PageTests.class);

        suite.addTestSuite(DefaultConfluenceManagerTest1.class);

        suite.addTestSuite(DefaultConfluenceManagerTest2.class);

        suite.addTestSuite(Demo.class);

        return suite;
    }
}