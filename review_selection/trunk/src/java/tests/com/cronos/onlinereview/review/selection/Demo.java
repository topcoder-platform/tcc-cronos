/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.cronos.onlinereview.review.selection.impl.DefaultReviewApplicationProcessor;
import com.cronos.onlinereview.review.selection.impl.DefaultReviewBoardApplicationListener;
import com.cronos.onlinereview.review.selection.impl.RatingBasedSelectionAlgorithm;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.ReviewApplication;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.ReviewerStatisticsManager;
import com.topcoder.management.resource.StatisticsType;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * This is demo of component Review Selection.
 * </p>
 *
 * @author xianwenchen
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // load config
        ConfigManager.getInstance().add("processor_config.xml");
        ConfigManager.getInstance().add("db_connection_factory.xml");
        ConfigManager.getInstance().add("search_bundle_manager.xml");
        executeFile("test_files/clear_db.sql");
        executeFile("test_files/init_db.sql");
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        // remove the namespace
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
     * Demo the usage of updateReviewAssignmentStatistics(ReviewApplication[]).
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDemoUpdateReviewAssignmentStatistics() throws Exception {
        DefaultReviewApplicationProcessor processor = new DefaultReviewApplicationProcessor();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("processor_config").getChild("valid_config");
        processor.configure(config);
        ReviewApplication[] applications = new ReviewApplication[] {new ReviewApplication()};
        processor.updateReviewAssignmentStatistics(applications);
    }

    /**
     * Demo the usage of updateUnassignedReviewersStatistics(ReviewApplication[]).
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDemoUpdateUnassignedReviewersStatistics() throws Exception {
        DefaultReviewApplicationProcessor processor = new DefaultReviewApplicationProcessor();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("processor_config").getChild("valid_config");
        processor.configure(config);
        ReviewerStatisticsManager reviewerStatisticsManager = mock(ReviewerStatisticsManager.class);
        setFieldValue(processor, "reviewerStatisticsManager", reviewerStatisticsManager);

        ReviewApplication application1 = new ReviewApplication();
        application1.setProjectId(1);
        application1.setReviewerId(1);
        application1.setAcceptPrimary(false);
        application1.setApplicationDate(new Date());

        ReviewApplication application2 = new ReviewApplication();
        application2.setProjectId(1);
        application2.setReviewerId(2);
        application2.setAcceptPrimary(false);
        application2.setApplicationDate(new Date());

        ReviewApplication application3 = new ReviewApplication();
        application3.setProjectId(1);
        application3.setReviewerId(3);
        application3.setAcceptPrimary(true);
        application3.setApplicationDate(new Date());

        // mocking
        double eligibilityPoints = 1.0;
        ReviewerStatistics statistics = new ReviewerStatistics();
        statistics.setStatisticsType(StatisticsType.AVERAGE);
        statistics.setCoverage(0.9);
        statistics.setAccuracy(0.9);
        statistics.setTimelineReliability(0.9);
        statistics.setEligibilityPoints(eligibilityPoints);

        when(reviewerStatisticsManager.getReviewerStatisticsByCompetitionType(3, 1)).thenReturn(statistics);

        ReviewApplication[] applications = new ReviewApplication[] {application1, application2, application3};
        processor.updateUnassignedReviewersStatistics(applications);

        // assert
        assertEquals("Eligibility points should be changed.", eligibilityPoints
            + DefaultReviewApplicationProcessor.DEFAULT_PRIMARY_ELIGIBILITY_POINTS,
            statistics.getEligibilityPoints(), 0.00001);
        verify(reviewerStatisticsManager, times(1)).update(statistics);
    }


    /**
     * Demo the usage of applicationReceived(ReviewApplication application).
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDemoApplicationReceived() throws Exception {
        DefaultReviewBoardApplicationListener listener = new DefaultReviewBoardApplicationListener();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("listener_config").getChild("valid_config1");
        listener.configure(config);
        String registrationPhaseOperator = "registrationPhaseOperator";
        setFieldValue(listener, "registrationPhaseOperator", registrationPhaseOperator);
        PhaseManager phaseManager = mock(PhaseManager.class);
        setFieldValue(listener, "phaseManager", phaseManager);

        ReviewApplication application = new ReviewApplication();
        application.setProjectId(1);

        // mock values
        Project project = new Project(new Date(), new DefaultWorkdays());
        Phase phase = new Phase(project,1L);
        phase.setPhaseType(new PhaseType(1,DefaultReviewBoardApplicationListener.DEFAULT_REGISTRATION_PHASE_NAME));

        // mocking
        when(phaseManager.getPhases(1)).thenReturn(project);
        when(phaseManager.canEnd(phase)).thenReturn(true);
        listener.applicationReceived(application);

        // phase should be ended
        verify(phaseManager, times(1)).end(phase, registrationPhaseOperator);
    }

    /**
     * Demo the usage of selectReviewers(ReviewApplication[]).
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDemoSelectReviewers() throws Exception {
        RatingBasedSelectionAlgorithm algorithm = new RatingBasedSelectionAlgorithm();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("selection_algorithm_config").getChild("valid_config1");
        algorithm.configure(config);
        ReviewerStatisticsManager reviewerStatisticsManager = mock(ReviewerStatisticsManager.class);
        setFieldValue(algorithm, "reviewerStatisticsManager", reviewerStatisticsManager);

        ReviewApplication application1 = new ReviewApplication();
        application1.setProjectId(1);
        application1.setReviewerId(1);
        application1.setAcceptPrimary(false);
        application1.setApplicationDate(new Date());

        ReviewApplication application2 = new ReviewApplication();
        application2.setProjectId(1);
        application2.setReviewerId(2);
        application2.setAcceptPrimary(false);
        application2.setApplicationDate(new Date());

        ReviewApplication application3 = new ReviewApplication();
        application3.setProjectId(1);
        application3.setReviewerId(3);
        application3.setAcceptPrimary(true);
        application3.setApplicationDate(new Date());
        ReviewApplication[] applications = new ReviewApplication[] {application1, application2, application3};

        // mocking
        double eligibilityPoints = 1.0;
        ReviewerStatistics statistics = new ReviewerStatistics();
        statistics.setStatisticsType(StatisticsType.AVERAGE);
        statistics.setCoverage(0.9);
        statistics.setAccuracy(0.9);
        statistics.setTimelineReliability(0.9);
        statistics.setEligibilityPoints(eligibilityPoints);
        when(reviewerStatisticsManager.getReviewerStatisticsByCompetitionType(1, 1)).thenReturn(statistics);
        when(reviewerStatisticsManager.getReviewerStatisticsByCompetitionType(2, 1)).thenReturn(statistics);
        when(reviewerStatisticsManager.getReviewerStatisticsByCompetitionType(3, 1)).thenReturn(statistics);

        ReviewSelectionResult result = algorithm.selectReviewers(applications);

        // 0.9 - 0.01 * 3 = 0.87
        assertEquals("The primary reviewer id of the result is incorrect.", 3, result.getPrimaryReviewer().getReviewerId());
        assertEquals("The secondary reviewer count of the result is incorrect.", 2, result.getSecondaryReviewers().length);
        assertEquals("The secondary reviewer id of the result is incorrect.", 1, result.getSecondaryReviewers()[0].getReviewerId());
        assertEquals("The secondary reviewer id of the result is incorrect.", 2, result.getSecondaryReviewers()[1].getReviewerId());
    }

    /**
     * <p>
     * Sets to object field value given fieldValue.
     * </p>
     *
     * @param object the object to set field value
     * @param fieldName the field name
     * @param fieldValue the field value to be set
     * @throws Exception if any error occurs
     */
    private void setFieldValue(Object object, String fieldName, Object fieldValue) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, fieldValue);
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
}
