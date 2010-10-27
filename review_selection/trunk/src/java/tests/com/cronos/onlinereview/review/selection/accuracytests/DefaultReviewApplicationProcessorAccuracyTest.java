/**
 *
 */
package com.cronos.onlinereview.review.selection.accuracytests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.onlinereview.review.selection.impl.DefaultReviewApplicationProcessor;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.ReviewApplication;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.util.config.ConfigManager;

/**
 * This class contains Accuracy tests for DefaultReviewApplicationProcessor.
 * @author sokol
 * @version 1.0
 */
public class DefaultReviewApplicationProcessorAccuracyTest extends BaseAccuracyTest {

    /**
     * <p>
     * Represents DefaultReviewApplicationProcessor instance for testing.
     * </p>
     */
    private DefaultReviewApplicationProcessor processor;

    /**
     * Creates suite that aggregates all Accuracy test cases for DefaultReviewApplicationProcessor.
     * @return Test suite that aggregates all Accuracy test cases for DefaultReviewApplicationProcessor
     */
    public static Test suite() {
        return new TestSuite(DefaultReviewApplicationProcessorAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        processor = new DefaultReviewApplicationProcessor();
        // load config
        ConfigManager.getInstance().add("accuracy/processor_config.xml");
        ConfigurationFileManager manager = new ConfigurationFileManager(ACCURACY_DIR + "conf.properties");
        config = manager.getConfiguration("processor_config").getChild("valid_config1");
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
        processor = null;
    }

    /**
     * <p>
     * Tests DefaultReviewApplicationProcessor constructor.
     * </p>
     * <p>
     * DefaultReviewApplicationProcessor instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("DefaultReviewApplicationProcessor instance should be created successfully.", processor);
    }

    /**
     * <p>
     * Tests {@link DefaultReviewApplicationProcessor#configure(com.topcoder.configuration.ConfigurationObject)} with
     * valid arguments passed.
     * </p>
     * <p>
     * DefaultReviewApplicationProcessor instance should be configured successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testConfigure() throws Exception {
        processor.configure(config);
        assertEquals("primaryEligibilityPoints should be set.",
                DefaultReviewApplicationProcessor.DEFAULT_PRIMARY_ELIGIBILITY_POINTS, getFieldValue(processor,
                        "primaryEligibilityPoints"));
    }

    /**
     * <p>
     * Tests {@link DefaultReviewApplicationProcessor#updateReviewAssignmentStatistics(ReviewApplication[])} with valid
     * arguments passed.
     * </p>
     * <p>
     * Do nothing. No exception is expected.
     * </p>
     */
    public void testUpdateReviewAssignmentStatistics() {
        processor.configure(config);
        ReviewApplication[] applications = new ReviewApplication[] {new ReviewApplication()};
        processor.updateReviewAssignmentStatistics(applications);
    }

    /**
     * <p>
     * Tests {@link DefaultReviewApplicationProcessor#updateUnassignedReviewersStatistics(ReviewApplication[])} with
     * valid arguments passed.
     * </p>
     * <p>
     * Reviewers eligibility point should be updated. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testUpdateUnassignedReviewersStatistics() throws Exception {
        processor.configure(config);
        setFieldValue(processor, "reviewerStatisticsManager", reviewerStatisticsManager);
        setFieldValue(processor, "projectManager", projectManager);
        // mock values
        long projectId = 1;
        int projectTypeId = 1;
        long reviewerId = 1;
        ReviewApplication application = new ReviewApplication();
        application.setAcceptPrimary(true);
        application.setProjectId(projectId);
        application.setReviewerId(reviewerId);
        ReviewApplication[] applications = new ReviewApplication[] {application};
        Project project =
                new Project(new ProjectCategory(1, "projectCategoryName", new ProjectType(projectTypeId,
                        "projectTypeName")), new ProjectStatus(1, "projectStatusName"));
        ReviewerStatistics statistics = new ReviewerStatistics();
        double eligibilityPoints = 1.0;
        statistics.setEligibilityPoints(eligibilityPoints);
        // mocking
        when(projectManager.getProject(projectId)).thenReturn(project);
        when(reviewerStatisticsManager.getReviewerStatisticsByCompetitionType(reviewerId, projectTypeId)).thenReturn(
                statistics);
        processor.updateUnassignedReviewersStatistics(applications);
        // assert
        assertEquals("Eligibility points should be changed.", eligibilityPoints
                + DefaultReviewApplicationProcessor.DEFAULT_PRIMARY_ELIGIBILITY_POINTS, statistics
                .getEligibilityPoints(), DELTA);
        verify(reviewerStatisticsManager, times(1)).update(statistics);
    }

    /**
     * <p>
     * Tests {@link DefaultReviewApplicationProcessor#updateUnassignedReviewersStatistics(ReviewApplication[])} with
     * valid arguments passed, but no primary reviewers presented.
     * </p>
     * <p>
     * Reviewers eligibility point should be not be changed as there is no primary reviewer in list. No exception is
     * expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testUpdateUnassignedReviewersStatistics_NoPrimary() throws Exception {
        processor.configure(config);
        setFieldValue(processor, "reviewerStatisticsManager", reviewerStatisticsManager);
        setFieldValue(processor, "projectManager", projectManager);
        // mock values
        long projectId = 1;
        int projectTypeId = 1;
        long reviewerId = 1;
        ReviewApplication application = new ReviewApplication();
        application.setAcceptPrimary(false);
        application.setProjectId(projectId);
        application.setReviewerId(reviewerId);
        ReviewApplication[] applications = new ReviewApplication[] {application};
        Project project =
            new Project(new ProjectCategory(1, "projectCategoryName", new ProjectType(projectTypeId,
                    "projectTypeName")), new ProjectStatus(1, "projectStatusName"));
        ReviewerStatistics statistics = new ReviewerStatistics();
        double eligibilityPoints = 1.0;
        statistics.setEligibilityPoints(eligibilityPoints);

        // mocking
        when(projectManager.getProject(projectId)).thenReturn(project);
        when(reviewerStatisticsManager.getReviewerStatisticsByCompetitionType(reviewerId, projectTypeId)).thenReturn(
                statistics);
        processor.updateUnassignedReviewersStatistics(applications);
        // assert
        assertEquals("Eligibility points should be the same.", eligibilityPoints, statistics.getEligibilityPoints(),
                DELTA);
        verify(reviewerStatisticsManager, times(0)).getReviewerStatisticsByCompetitionType(reviewerId, projectTypeId);
        verify(reviewerStatisticsManager, times(0)).update(statistics);
    }
}
