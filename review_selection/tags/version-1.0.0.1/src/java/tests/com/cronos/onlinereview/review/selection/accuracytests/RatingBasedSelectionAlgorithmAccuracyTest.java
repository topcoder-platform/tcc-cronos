/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.accuracytests;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestSuite;
import static org.mockito.Mockito.*;

import com.cronos.onlinereview.review.selection.ReviewSelectionResult;
import com.cronos.onlinereview.review.selection.impl.RatingBasedSelectionAlgorithm;
import com.cronos.onlinereview.review.selection.impl.workloadfactorcalculator.DefaultWorkloadFactorCalculator;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.ReviewApplication;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.StatisticsType;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * This class contains Accuracy tests for RatingBasedSelectionAlgorithm.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class RatingBasedSelectionAlgorithmAccuracyTest extends BaseAccuracyTest {

    /**
     * <p>
     * Represents RatingBasedSelectionAlgorithm instance for testing.
     * </p>
     */
    private RatingBasedSelectionAlgorithm algorithm;

    /**
     * <p>
     * Creates suite that aggregates all Accuracy test cases for RatingBasedSelectionAlgorithm.
     * </p>
     * @return Test suite that aggregates all Accuracy test cases for RatingBasedSelectionAlgorithm
     */
    public static Test suite() {
        return new TestSuite(RatingBasedSelectionAlgorithmAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        algorithm = new RatingBasedSelectionAlgorithm();
        ConfigurationFileManager manager = new ConfigurationFileManager(ACCURACY_DIR + "conf.properties");
        config = manager.getConfiguration("selection_algorithm_config").getChild("valid_config1");
        // load config
        ConfigManager.getInstance().add("accuracy/selection_algorithm_config.xml");
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        // remove the namespace
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator <?> iter = cm.getAllNamespaces(); iter.hasNext();) {
            String nameSpace = (String) iter.next();
            if (nameSpace.equals("com.topcoder.util.log")) {
                continue;
            }
            cm.removeNamespace(nameSpace);
        }
        algorithm = null;
    }

    /**
     * <p>
     * Tests RatingBasedSelectionAlgorithm constructor.
     * </p>
     * <p>
     * RatingBasedSelectionAlgorithm instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("RatingBasedSelectionAlgorithm instance should be created successfully.", algorithm);
    }

    /**
     * <p>
     * Tests {@link RatingBasedSelectionAlgorithm#configure(com.topcoder.configuration.ConfigurationObject)} method
     * with valid arguments passed.
     * </p>
     * <p>
     * RatingBasedSelectionAlgorithm should be configured successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testConfigure() throws Exception {
        algorithm.configure(config);
        assertTrue("Fields should be set successfully.", checkFields());
    }

    /**
     * <p>
     * Tests {@link RatingBasedSelectionAlgorithm#selectReviewers(com.topcoder.management.project.ReviewApplication[])}
     * with default argument values for this component passed.
     * </p>
     * <p>
     * ReviewSelectionResult should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSelectReviewers() throws Exception {
        algorithm.configure(config);
        String rboardDataSourceName = "rboardDataSourceName";
        setFieldValue(algorithm, "reviewerStatisticsManager", reviewerStatisticsManager);
        setFieldValue(algorithm, "projectManager", projectManager);
        setFieldValue(algorithm, "phaseManager", phaseManager);
        setFieldValue(algorithm, "reviewApplicationProcessor", reviewApplicationProcessor);
        setFieldValue(algorithm, "rboardApplicationBean", rboardApplicationBean);
        setFieldValue(algorithm, "rboardDataSourceName", rboardDataSourceName);
        setFieldValue(algorithm, "workloadFactorCalculator", new DefaultWorkloadFactorCalculator());
        ReviewApplication[] reviewApplications = createReviewerApplications();
        // mock values
        long projectId = 1;
        int projectTypeId = 1;
        long activeReviewCount = 2;
        ReviewerStatistics[] reviewerStatistics = createReviewerStatistics();
        Project project =
                new Project(new ProjectCategory(1, "projectCategoryName", new ProjectType(projectTypeId,
                        "projectTypeName")), new ProjectStatus(1, "projectStatusName"));
        com.topcoder.project.phases.Project phaseProject =
                new com.topcoder.project.phases.Project(new Date(), new DefaultWorkdays());
        Phase phase = new Phase(phaseProject, 1L);
        phase.setId(1L);
        phase.setPhaseType(new PhaseType(1, RatingBasedSelectionAlgorithm.DEFAULT_REVIEW_PHASE_NAME));
        // mocking
        when(projectManager.getProject(projectId)).thenReturn(project);
        for (int i = 0; i < reviewApplications.length; i++) {
            ReviewApplication application = reviewApplications[i];
            when(
                    reviewerStatisticsManager.getReviewerStatisticsByCompetitionType(application.getReviewerId(),
                            projectTypeId)).thenReturn(reviewerStatistics[i]);
            when(rboardApplicationBean.getApplicationDelay(rboardDataSourceName, application.getReviewerId()))
                    .thenReturn(activeReviewCount);
        }
        when(phaseManager.getPhases(projectId)).thenReturn(phaseProject);
        ReviewSelectionResult result = algorithm.selectReviewers(reviewApplications);
        assertEquals("First application should be primary.", 1, result.getPrimaryReviewer().getId());
        assertEquals("Second application should be first secondary.", 2, result.getSecondaryReviewers()[0].getId());
        assertEquals("Third application should be second secondary.", 3, result.getSecondaryReviewers()[1].getId());
        // primary
        verify(rboardApplicationBean,times(1)).createRBoardApplication(anyString(), anyLong(), anyLong(), anyInt(), anyInt(),  (Timestamp) anyObject(), anyInt(), eq(true));
        // secondary
        verify(rboardApplicationBean,times(2)).createRBoardApplication(anyString(), anyLong(), anyLong(), anyInt(), anyInt(),  (Timestamp) anyObject(), anyInt(), eq(false));
        verify(reviewApplicationProcessor,times(1)).updateReviewAssignmentStatistics((ReviewApplication[]) anyObject());
        verify(reviewApplicationProcessor,times(1)).updateUnassignedReviewersStatistics((ReviewApplication[]) anyObject());
    }

    /**
     * <p>
     * Creates reviewer statistics for this component.
     * </p>
     * @return ReviewerStatistics array with default values for this component
     */
    private ReviewerStatistics[] createReviewerStatistics() {
        ReviewerStatistics statistics1 = new ReviewerStatistics();
        double accuracy = 0.9;
        statistics1.setAccuracy(accuracy);
        double coverage = 0.9;
        statistics1.setCoverage(coverage);
        statistics1.setStatisticsType(StatisticsType.AVERAGE);
        double timelineReliability = 0.9;
        statistics1.setTimelineReliability(timelineReliability);
        statistics1.setId(1);
        double eligibilityPoints = 10;
        statistics1.setEligibilityPoints(eligibilityPoints);
        ReviewerStatistics statistics2 = new ReviewerStatistics();
        statistics2.setAccuracy(accuracy - 0.1);
        statistics2.setCoverage(coverage - 0.1);
        statistics2.setTimelineReliability(timelineReliability - 0.1);
        statistics2.setStatisticsType(StatisticsType.AVERAGE);
        statistics2.setEligibilityPoints(eligibilityPoints);
        ReviewerStatistics statistics3 = new ReviewerStatistics();
        statistics3.setAccuracy(accuracy - 0.2);
        statistics3.setCoverage(coverage - 0.2);
        statistics3.setTimelineReliability(timelineReliability - 0.2);
        statistics3.setStatisticsType(StatisticsType.AVERAGE);
        ReviewerStatistics statistics4 = new ReviewerStatistics();
        statistics4.setAccuracy(accuracy - 0.3);
        statistics4.setCoverage(coverage - 0.3);
        statistics4.setTimelineReliability(timelineReliability - 0.3);
        statistics4.setStatisticsType(StatisticsType.AVERAGE);
        return new ReviewerStatistics[] {statistics1, statistics2, statistics3, statistics4};
    }

    /**
     * Creates default reviewer applications for this component.
     * @return ReviewApplication array with default values for this component
     */
    private ReviewApplication[] createReviewerApplications() {
        ReviewApplication application1 = new ReviewApplication();
        application1.setId(1);
        application1.setAcceptPrimary(true);
        application1.setProjectId(1);
        application1.setReviewerId(1);
        application1.setApplicationDate(new Date());
        ReviewApplication application2 = new ReviewApplication();
        application2.setAcceptPrimary(true);
        application2.setId(2);
        application2.setProjectId(1);
        application2.setReviewerId(2);
        application2.setApplicationDate(new Date());
        ReviewApplication application3 = new ReviewApplication();
        application3.setAcceptPrimary(false);
        application3.setId(3);
        application3.setProjectId(1);
        application3.setReviewerId(3);
        application3.setApplicationDate(new Date());
        ReviewApplication application4 = new ReviewApplication();
        application4.setAcceptPrimary(false);
        application4.setId(4);
        application4.setProjectId(1);
        application4.setReviewerId(4);
        application4.setApplicationDate(new Date());
        return new ReviewApplication[] {application1, application2, application3, application4};
    }

    /**
     * <p>
     * Checks if all fields set successfully.
     * </p>
     * @return true, if all fields set successfully, false otherwise
     * @throws Exception if any error occurs
     */
    private boolean checkFields() throws Exception {
        return getFieldValue(algorithm, "reviewerStatisticsManager") != null
                && getFieldValue(algorithm, "projectManager") != null
                && getFieldValue(algorithm, "phaseManager") != null
                && getFieldValue(algorithm, "reviewApplicationProcessor") != null
                && getFieldValue(algorithm, "rboardApplicationBean") != null
                && getFieldValue(algorithm, "workloadFactorCalculator") != null
                && getFieldValue(algorithm, "log") != null
                && getFieldValue(algorithm, "rboardDataSourceName").equals("rboardDataSource");
    }
}
