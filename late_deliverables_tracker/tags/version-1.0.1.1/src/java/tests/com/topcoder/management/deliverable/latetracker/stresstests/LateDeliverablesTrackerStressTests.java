/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.deliverable.latetracker.stresstests;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.dumbster.smtp.SimpleSmtpServer;
import com.topcoder.management.deliverable.latetracker.LateDeliverablesTracker;
import com.topcoder.management.deliverable.latetracker.processors.LateDeliverableProcessorImpl;
import com.topcoder.management.deliverable.latetracker.retrievers.LateDeliverablesRetrieverImpl;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * Stress test case of the LateDeliverablesTracker.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class LateDeliverablesTrackerStressTests extends BaseStressTest {
    private LateDeliverablesTracker instance;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    public void setUp() throws Exception {
        super.setUp();

        LateDeliverablesRetrieverImpl retriever = new LateDeliverablesRetrieverImpl();
        retriever.configure(StressTestUtil.getConfig(LateDeliverablesRetrieverImpl.class));

        LateDeliverableProcessorImpl processor = new LateDeliverableProcessorImpl();
        processor.configure(StressTestUtil.getConfig(LateDeliverableProcessorImpl.class));

        instance = new LateDeliverablesTracker(retriever, processor, LogFactory.getLog("stress_tests_logger"));
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(LateDeliverablesTrackerStressTests.class);

        return suite;
    }

    /**
     * <p>
     * Stress test for method <code>execute()</code>.
     * </p>
     * <p>
     * The test will run in the following environment:
     * <li>The database has 4000 projects, including 2000 active projects.</li>
     * </p>
     * The active project
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_execute() throws Exception {
        for (int i = 0; i < testCount; i++) {
            // set server
            SimpleSmtpServer server = SimpleSmtpServer.start();
            int projectsCount = 2;
            int subCount = 1000;
            try {
                StressTestUtil.prepareProjectData(projectsCount, subCount, con);

                instance.execute();

                assertLateDeliverables(projectsCount, subCount);

                assertEmail(server);

                StressTestUtil.clearDataBase(con);
                StressTestUtil.setUpDataBase(con);
            } finally {
                server.stop();

                System.out.println("Run test: test_execute for " + testCount + " times takes "
                    + (new Date().getTime() - start) + "ms");
            }
        }
    }

    /**
     * <p>
     * Asserts the result of the LateDeliverableData.
     * </p>
     *
     * @throws SQLException
     *             to JUnit
     */
    private void assertLateDeliverables(int projectCount, int subCount) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM late_deliverable");

            int insertedRecordsCount = 0;
            while (rs.next()) {
                insertedRecordsCount++;

                assertEquals("Should be equal", insertedRecordsCount * 3 + 2, rs.getLong(2)); // project phase id

                assertEquals("Should be equal", insertedRecordsCount * (subCount + 1) + subCount, rs.getLong(3)); // resource
                                                                                                                  // id

                assertEquals("Should be equal", 2, rs.getLong(4)); // deliverable id

                assertFalse("Should be false", rs.getBoolean(7)); // forgive ind
            }

            assertEquals("Should have " + projectCount + " records in the late_deliverable table.", projectCount,
                insertedRecordsCount);
            System.out.println("The number of records inserted into late_deliverable table is " + insertedRecordsCount);
        } finally {
            StressTestUtil.closeStatement(stmt);
            stmt = null;
        }
    }

}
