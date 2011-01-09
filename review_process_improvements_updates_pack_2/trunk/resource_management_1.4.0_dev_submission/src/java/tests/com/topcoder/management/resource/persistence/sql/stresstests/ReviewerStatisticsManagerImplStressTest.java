/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.resource.persistence.sql.stresstests;

import java.sql.Date;

import junit.framework.TestCase;

import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.ReviewerStatisticsManagerImpl;
import com.topcoder.management.resource.SideBySideStatistics;
import com.topcoder.management.resource.StatisticsType;

/**
 * <p>
 * Stress test case for <code>ReviewerStatisticsManagerImpl</code>.
 * </p>
 * 
 * @author morehappiness
 * @version 1.3
 */
public class ReviewerStatisticsManagerImplStressTest extends TestCase {

    /**
     * Represents the <code>ReviewerStatisticsManagerImpl</code> instance for testing.
     */
    private ReviewerStatisticsManagerImpl manager = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * <ol>
     * <li>Loads the configuration files.</li>
     * <li>Prepares the data in database.</li>
     * <li>Creates the persistence instance.</li>
     * </ol>
     */
    protected void setUp() throws Exception {
        StressTestsHelper.cleanConfiguration();
        StressTestsHelper.loadConfiguration();
        StressTestsHelper.cleanDatabase();
        StressTestsHelper.executeSQLFile("test_files/stresstests/init.sql");
        manager = new ReviewerStatisticsManagerImpl(ReviewerStatisticsManagerImpl.class.getName());
    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     * <ol>
     * <li>Cleans the data in database.</li>
     * <li>Cleans the configuration.</li>
     * </ol>
     */
    protected void tearDown() throws Exception {
        // StressTestsHelper.cleanDatabase();
        StressTestsHelper.cleanConfiguration();
    }

    /**
     * Stress Test on the persistent methods for 100 times.
     * 
     * @throws Exception
     *             any exception to JUnit.
     */
    public void testStress() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            ReviewerStatistics rs = new ReviewerStatistics();
            rs.setReviewerId(1);
            rs.setProjectId(1);
            rs.setCompetitionTypeId(1);
            rs.setAccuracy(0.65);
            rs.setModificationUser("topcoder");
            rs.setModificationTimestamp(new Date(0));
            rs.setCreationUser("topcoder");
            rs.setCreationTimestamp(new Date(0));
            rs.setStatisticsType(StatisticsType.AVERAGE);
            // create an instance of ReviewerStatistics
            rs = manager.create(rs);
            assertEquals("Should be the same", true, rs.getId() > 0);

            // update ReviewerStatistics
            rs.setAccuracy(0.85);
            assertEquals("Should be the same", 0.85, manager.update(rs).getAccuracy());

            // retrieve ReviewerStatistics
            assertEquals("Should be the same", rs.getId(), manager.retrieve(rs.getId()).getId());

            // get reviewer statistics by competition type
            assertEquals("Should be the same", rs.getId(), manager.getReviewerStatisticsByCompetitionType(1, 1).getId());

            // create another instance
            ReviewerStatistics anotherRS = new ReviewerStatistics();
            anotherRS.setReviewerId(2);
            anotherRS.setProjectId(1);
            anotherRS.setCompetitionTypeId(1);
            anotherRS.setAccuracy(0.8);
            anotherRS.setModificationUser("topcoder");
            anotherRS.setModificationTimestamp(new Date(0));
            anotherRS.setCreationUser("topcoder");
            anotherRS.setCreationTimestamp(new Date(0));
            anotherRS.setStatisticsType(StatisticsType.AVERAGE);
            anotherRS = manager.create(anotherRS);

            // get Side By Side Statistics
            SideBySideStatistics sideBySideStatistics = manager.getSideBySideStatistics(1, 2, 1);
            assertEquals("Should be the same", 0, sideBySideStatistics.getFirstReviewerStatistics().size());
            assertEquals("Should be the same", 0, sideBySideStatistics.getSecondReviewerStatistics().size());

            // delete ReviewerStatistics
            assertEquals("Should be the same", true, manager.delete(rs.getId()));
         // delete ReviewerStatistics
            assertEquals("Should be the same", true, manager.delete(anotherRS.getId()));
        }

        long dure = System.currentTimeMillis() - startTime;
        System.out.println("ApplicationsManagerImpl#createProject(Project, String) tested for 100 times, " + "takes "
            + Long.toString(dure) + " ms.");
    }

}
