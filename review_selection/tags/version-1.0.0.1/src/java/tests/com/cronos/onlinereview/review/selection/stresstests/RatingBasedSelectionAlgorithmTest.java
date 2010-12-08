/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.stresstests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.selection.ReviewSelectionResult;
import com.cronos.onlinereview.review.selection.impl.DefaultReviewApplicationProcessor;
import com.cronos.onlinereview.review.selection.impl.RatingBasedSelectionAlgorithm;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.management.project.ReviewApplication;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.ReviewerStatisticsManager;
import com.topcoder.management.resource.StatisticsType;
import com.topcoder.util.config.ConfigManager;

/**
 * Stress tests for <code>RatingBasedSelectionAlgorithm</code>.
 *
 * @author moon.river
 * @version 1.0
 */
public class RatingBasedSelectionAlgorithmTest extends TestCase {

    /**
     * Instance to test.
     */
    private RatingBasedSelectionAlgorithm instance;

    /**
     * The statistics manager.
     */
    private ReviewerStatisticsManager statManager;

    /**
     * Sets up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        ConfigManager.getInstance().add("stress/selection_algorithm_config.xml");
        ConfigManager.getInstance().add("stress/db_connection_factory.xml");
        ConfigManager.getInstance().add("stress/search_bundle_manager.xml");
        instance = new RatingBasedSelectionAlgorithm();
        ConfigurationFileManager manager = new ConfigurationFileManager("stress/conf.properties");
        ConfigurationObject config = manager.getConfiguration("selection_algorithm_config").getChild("valid_config1");
        instance.configure(config);

        Field field = RatingBasedSelectionAlgorithm.class.getDeclaredField("reviewerStatisticsManager");
        try {
            field.setAccessible(true);
            statManager = (ReviewerStatisticsManager) field.get(instance);
        } finally {
            field.setAccessible(false);
        }

        field = RatingBasedSelectionAlgorithm.class.getDeclaredField("reviewApplicationProcessor");
        try {
            field.setAccessible(true);
            DefaultReviewApplicationProcessor reviewApplicationProcessor = (DefaultReviewApplicationProcessor) field.get(instance);
            Field field1 = DefaultReviewApplicationProcessor.class.getDeclaredField("reviewerStatisticsManager");
            try {
                field1.setAccessible(true);
                field1.set(reviewApplicationProcessor, statManager);
            } finally {
                field1.setAccessible(false);
            }
        } finally {
            field.setAccessible(false);
        }
        executeFile("test_files/stress/clear_db.sql");
        executeFile("test_files/stress/init_db.sql");
    }


    /**
     * executes a file.
     * @param string the file name
     * @throws Exception to invoker
     */
    private static void executeFile(String string) throws Exception {
        Class.forName("com.informix.jdbc.IfxDriver");

        Properties props = new Properties();
        props.load(new FileInputStream("test_files/stress/test.properties"));

        Connection conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("user"), props.getProperty("password"));

        BufferedReader br = new BufferedReader(new FileReader(string));
        String line = "";
        while (br.ready()) {
            line += " " + br.readLine();
            if (line.endsWith(";")) {
                PreparedStatement ps = conn.prepareStatement(line);
                try {
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw e;
                }
                line = "";
            }
        }
        conn.close();
    }

    /**
     * Cleans up the environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        executeFile("test_files/stress/clear_db.sql");
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator<?> iter = cm.getAllNamespaces(); iter.hasNext();) {
            String nameSpace = (String) iter.next();
            if (nameSpace.equals("com.topcoder.util.log")) {
                continue;
            }
            cm.removeNamespace(nameSpace);
        }
    }

    /**
     * Test method for {@link com.cronos.onlinereview.review.selection.impl.RatingBasedSelectionAlgorithm
     * #selectReviewers(com.topcoder.management.project.ReviewApplication[])}.
     * @throws Exception to JUnit
     */
    public void testSelectReviewers_1() throws Exception {
        // prepare the reviewer/project etc in database
        // the reviewer with id 500 has the best coff for primary position
        long expctedPrimary = 500;
        // the reviewers with id 100 and 900 has the best coff for secondary position
        long expectedSecondary1 = 100;
        long expectedSecondary2 = 900;

        // create 1000 reviewer statistics for testing
        for (int i = 0; i < 1000; i++) {
            ReviewerStatistics rs = new ReviewerStatistics();
            rs.setAccuracy(0.5);
            rs.setCompetitionTypeId(1);
            rs.setCoverage(0.5);
            rs.setReviewerId(i);
            rs.setStatisticsType(StatisticsType.AVERAGE);
            rs.setTimelineReliability(0.6);
            if (i == expctedPrimary) {
                rs.setAccuracy(1);
                rs.setCoverage(1);
                rs.setTimelineReliability(1);
            } else if (i == expectedSecondary1) {
                rs.setAccuracy(1);
            } else if (i == expectedSecondary2) {
                rs.setCoverage(1);
            }
            statManager.create(rs);
        }

        // create 1000 review applications
        ReviewApplication[] apps = new ReviewApplication[1000];
        for (int i = 0; i < 1000; i++) {
            apps[i] = new ReviewApplication();
            apps[i].setAcceptPrimary(true);
            apps[i].setApplicationDate(new Date());
            apps[i].setId(i);
            apps[i].setProjectId(1);
            apps[i].setReviewerId(i);
        }

        ReviewSelectionResult result = instance.selectReviewers(apps);
        assertEquals("The primary is wrong.", apps[500].getId(), result.getPrimaryReviewer().getId());
        assertEquals("The secondary is wrong.", apps[100].getId(), result.getSecondaryReviewers()[0].getId());
        assertEquals("The secondary is wrong.", apps[900].getId(), result.getSecondaryReviewers()[1].getId());
    }

    /**
     * Test method for {@link com.cronos.onlinereview.review.selection.impl.RatingBasedSelectionAlgorithm
     * #selectReviewers(com.topcoder.management.project.ReviewApplication[])}.
     * @throws Exception to JUnit
     */
    public void testSelectReviewers_2() throws Exception {
        // prepare the reviewer/project etc in database

        // create 1000 reviewer statistics for testing
        for (int i = 0; i < 1000; i++) {
            ReviewerStatistics rs = new ReviewerStatistics();
            rs.setAccuracy(0.5);
            rs.setCompetitionTypeId(1);
            rs.setCoverage(0.5);
            rs.setReviewerId(i);
            rs.setStatisticsType(StatisticsType.AVERAGE);
            rs.setTimelineReliability(0.6);
            statManager.create(rs);
        }

        // create 1000 review applications
        ReviewApplication[] apps = new ReviewApplication[1000];
        for (int i = 0; i < 1000; i++) {
            apps[i] = new ReviewApplication();
            apps[i].setAcceptPrimary(true);
            apps[i].setApplicationDate(new Date());
            apps[i].setId(i);
            apps[i].setProjectId(1);
            apps[i].setReviewerId(i);
        }

        ReviewSelectionResult result = instance.selectReviewers(apps);
        assertEquals("The primary is wrong.", apps[0].getId(), result.getPrimaryReviewer().getId());
        assertEquals("The secondary is wrong.", apps[1].getId(), result.getSecondaryReviewers()[0].getId());
        assertEquals("The secondary is wrong.", apps[2].getId(), result.getSecondaryReviewers()[1].getId());
    }

}
