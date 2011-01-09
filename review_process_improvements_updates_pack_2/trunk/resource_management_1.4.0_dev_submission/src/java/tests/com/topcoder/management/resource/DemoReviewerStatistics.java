/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * <p>
 * Demo test for ReviewApplication classes.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.4
 * @since 1.4
 */
public class DemoReviewerStatistics extends TestCase {
    /**
     * File contains sql statement to initial database.
     */
    private static final String INIT_DB_SQL = "test_files/InitDB.sql";

    /**
     * File contains sql statement to clear database.
     */
    private static final String CLEAR_DB_SQL = "test_files/ClearDB.sql";

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // load config.xml
        ConfigManager.getInstance().add("config.xml");

        executeSQL(CLEAR_DB_SQL);

        executeSQL(INIT_DB_SQL);
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        executeSQL(CLEAR_DB_SQL);

        // remove all namespace.
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator<String> iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace(iter.next());
        }
    }

    /**
     * Demo test for ReviewerStatisticsManagerImpl class.
     *
     * @throws Exception for JUnit
     */
    public void testDemo() throws Exception {
        // create an instance of ReviewerStatisticsManagerImpl
        ReviewerStatisticsManagerImpl manager = new ReviewerStatisticsManagerImpl(ReviewerStatisticsManagerImpl.class
            .getName());

        ReviewerStatistics rs = new ReviewerStatistics();
        // set properties of ReviewerStatistics
        rs.setReviewerId(1);
        rs.setProjectId(1);
        rs.setCompetitionTypeId(1);
        rs.setAccuracy(0.65);
        rs.setModificationUser("topcoder");
        rs.setModificationTimestamp(new Date());
        rs.setCreationUser("topcoder");
        rs.setCreationTimestamp(new Date());
        // create an instance of ReviewerStatistics
        rs = manager.create(rs);
        // rs has new ID provided by review_application_id_seq + 1

        // create another instance
        ReviewerStatistics anotherRS = new ReviewerStatistics();
        anotherRS.setReviewerId(2);
        anotherRS.setProjectId(1);
        anotherRS.setCompetitionTypeId(1);
        anotherRS.setAccuracy(0.8);
        anotherRS.setModificationUser("topcoder");
        anotherRS.setModificationTimestamp(new Date());
        anotherRS.setCreationUser("topcoder");
        anotherRS.setCreationTimestamp(new Date());
        anotherRS = manager.create(anotherRS);
        // anotherRS has new ID equal to rs ID plus 1
        assertEquals(anotherRS.getId(), rs.getId() + 1);

        // update ReviewerStatistics
        rs.setAccuracy(0.85);
        rs = manager.update(rs);
        // accuracy is set to 0.85

        // retrieve ReviewerStatistics
        ReviewerStatistics rs2 = manager.retrieve(rs.getId());
        // rs2 has the same content as rs
        assertEquals(rs.getId(), rs2.getId());

        // delete rs
        assertTrue(manager.delete(rs.getId()));
        // rs should be deleted

        // create an instance of ReviewerStatistics
        rs = manager.create(rs);

        // get reviewer statistics by competition type
        ReviewerStatistics rs3 = manager.getReviewerStatisticsByCompetitionType(1, 1);
        // rs3 should be the same as rs, its reviewer id is 1 and competition type id is 1.
        assertEquals(rs3.getReviewerId(), 1);
        assertEquals(rs3.getCompetitionTypeId(), 1);

        // get Side By Side Statistics
        SideBySideStatistics rs4 = manager.getSideBySideStatistics(1, 2, 1);
        // an array with 1 elements is returned, one is for reviewer with
        // id=1, the other is for reviewer with id=2
        assertEquals(rs4.getFirstReviewerStatistics().get(0).getReviewerId(), 1);
        assertEquals(rs4.getSecondReviewerStatistics().get(0).getReviewerId(), 2);

        // get Reviewer Average Statistics
        ReviewerStatistics[] rs5 = manager.getReviewerAverageStatistics(2);
        // an array with 1 element is returned, average statistics for
        // reviewer 2, with competition type=1
        assertEquals(1, rs5.length);
        assertEquals(2, rs5[0].getReviewerId());
        assertEquals(1, rs5[0].getCompetitionTypeId());

        // get Reviewer history Statistics
        ReviewerStatistics[] rs6 = manager.getReviewerStatistics(2);
        // an array with 1 elements is returned, all elements have
        // reviewer id=2 and competition type id=1;
        assertEquals(1, rs5.length);
        assertEquals(2, rs5[0].getReviewerId());
        assertEquals(1, rs5[0].getCompetitionTypeId());
    }

    /**
     * Executes a sql batch contains in a file.
     *
     * @param file the file contains the sql statements.
     *
     * @throws Exception pass to JUnit.
     */
    private void executeSQL(String file) throws Exception {
        // get db connection
        Connection connection = new DBConnectionFactoryImpl(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").createConnection();
        Statement statement = connection.createStatement();

        // get sql statements and add to statement
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;

        while ((line = in.readLine()) != null) {
            if (line.trim().length() != 0) {
                statement.addBatch(line);
            }
        }

        statement.executeBatch();
    }
}
