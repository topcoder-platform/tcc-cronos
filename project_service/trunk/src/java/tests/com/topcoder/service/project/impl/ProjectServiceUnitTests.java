/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.impl;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.service.BaseUnitTestCase;

/**
 * <p>
 * Unit test for Remote EJB, Local EJB and WebService.
 * </p>
 *
 * <p>
 * It aggregates the test cases from:
 *   <ul>
 *    <li><code>ProjectServiceRemoteTestAcc</code></li>
 *    <li><code>ProjectServiceRemoteTestExp</code></li>
 *    <li><code>ProjectServiceLocalTestAcc</code></li>
 *    <li><code>ProjectServiceLocalTestExp</code></li>
 *    <li><code>ProjectServiceWSTestAcc</code></li>
 *    <li><code>ProjectServiceWSTestExp</code></li>
 *   </ul>
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class ProjectServiceUnitTests extends BaseUnitTestCase {

    /**
     * <p>
     * Aggregates all Unit tests for <code>ProjectServiceBean</code>.
     * </p>
     *
     * @return Test suite aggregating all Unit tests for <code>ProjectServiceBean</code>.
     */
    public static Test suite() {

        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ProjectServiceRemoteTestAcc.class);
        suite.addTestSuite(ProjectServiceRemoteTestExp.class);

        suite.addTestSuite(ProjectServiceLocalTestAcc.class);
        suite.addTestSuite(ProjectServiceLocalTestExp.class);

        suite.addTestSuite(ProjectServiceWSTestAcc.class);
        suite.addTestSuite(ProjectServiceWSTestExp.class);

        /**
         * <p>
         * <code>TestSetup</code> used to set up and tear down the test suite.
         * </p>
         *
         * @author TCSDEVELOPER
         * @version 1.1
         */
        TestSetup wrapper = new TestSetup(suite) {

                /**
                 * <p>Clear the J2SE <code>EntityManager</code>. Clear the DB.</p>
                 */
                protected void setUp() throws Exception {
                    getEntityManager().clear();
                    executeScript("/clean.sql");
                }

                /**
                 * <p>Clear the DB. Clear the J2SE <code>EntityManager</code>.</p>
                 */
                protected void tearDown() throws Exception {
                    executeScript("/clean.sql");
                    getEntityManager().clear();
                }
            };

        return wrapper;
    }
}
