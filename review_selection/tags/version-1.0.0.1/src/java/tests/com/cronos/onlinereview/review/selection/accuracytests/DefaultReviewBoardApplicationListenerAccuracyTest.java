/**
 *
 */
package com.cronos.onlinereview.review.selection.accuracytests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.onlinereview.review.selection.impl.DefaultReviewBoardApplicationListener;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.project.ReviewApplication;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.util.config.ConfigManager;

/**
 * This class contains Accuracy tests for DefaultReviewBoardApplicationListener.
 * @author sokol
 * @version 1.0
 */
public class DefaultReviewBoardApplicationListenerAccuracyTest extends BaseAccuracyTest {

    /**
     * <p>
     * Represents DefaultReviewBoardApplicationListener instance for testing.
     * </p>
     */
    private DefaultReviewBoardApplicationListener listener;

    /**
     * Creates suite that aggregates all Accuracy test cases for DefaultReviewBoardApplicationListener.
     * @return Test suite that aggregates all Accuracy test cases for DefaultReviewBoardApplicationListener
     */
    public static Test suite() {
        return new TestSuite(DefaultReviewBoardApplicationListenerAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        listener = new DefaultReviewBoardApplicationListener();
        // load config
        ConfigManager.getInstance().add("accuracy/listener_config.xml");
        ConfigurationFileManager manager = new ConfigurationFileManager(ACCURACY_DIR + "conf.properties");
        config = manager.getConfiguration("listener_config").getChild("valid_config1");
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
        listener = null;
    }

    /**
     * <p>
     * Tests DefaultReviewBoardApplicationListener constructor.
     * </p>
     * <p>
     * DefaultReviewBoardApplicationListener instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("DefaultReviewBoardApplicationListener instance should be created successfully.", listener);
    }

    /**
     * <p>
     * Tests {@link DefaultReviewBoardApplicationListener#configure(com.topcoder.configuration.ConfigurationObject)}
     * with valid arguments passed.
     * </p>
     * <p>
     * DefaultReviewBoardApplicationListener instance should be configured successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testConfigure() throws Exception {
        listener.configure(config);
        assertEquals("registrationPhaseOperator should be set.", "registration phase operator", getFieldValue(
                listener, "registrationPhaseOperator"));
    }

    /**
     * <p>
     * Tests
     * {@link DefaultReviewBoardApplicationListener#applicationReceived(com.topcoder.management.project.ReviewApplication)}
     * with valid arguments passed.
     * </p>
     * <p>
     * Registration phase should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testApplicationReceived() throws Exception {
        listener.configure(config);
        String registrationPhaseOperator = "registrationPhaseOperator";
        setFieldValue(listener, "registrationPhaseOperator", registrationPhaseOperator);
        setFieldValue(listener, "phaseManager", phaseManager);
        setFieldValue(listener, "reviewApplicationsManager", reviewApplicationsManager);
        setFieldValue(listener, "reviewersNumber", 1);
        // mock values
        ReviewApplication application = new ReviewApplication();
        application.setAcceptPrimary(true);
        long projectId = 1;
        Project project = new Project(new Date(), new DefaultWorkdays());
                //new Project(new ProjectCategory(1, "name", new ProjectType(1, "name")), new ProjectStatus(1, "name"));
        Phase phase = new Phase(project,1L);
        phase.setPhaseType(new PhaseType(1,DefaultReviewBoardApplicationListener.DEFAULT_REGISTRATION_PHASE_NAME));
        application.setProjectId(projectId);
        // mocking
        when(reviewApplicationsManager.getAllApplications(projectId)).thenReturn(new ReviewApplication[]{application});
        when(phaseManager.getPhases(projectId)).thenReturn(project);
        when(phaseManager.canEnd(phase)).thenReturn(true);
        listener.applicationReceived(application);
        // phase should be ended
        verify(phaseManager, times(1)).end(phase, registrationPhaseOperator);

    }
    /**
     * <p>
     * Tests
     * {@link DefaultReviewBoardApplicationListener#applicationReceived(com.topcoder.management.project.ReviewApplication)}
     * with not enough reviewers for phase passed.
     * </p>
     * <p>
     * Registration phase should be not be ended as not enough reviewers. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testApplicationReceived_NotEnoughReviewers() throws Exception {
        listener.configure(config);
        String registrationPhaseOperator = "registrationPhaseOperator";
        setFieldValue(listener, "registrationPhaseOperator", registrationPhaseOperator);
        setFieldValue(listener, "phaseManager", phaseManager);
        setFieldValue(listener, "reviewApplicationsManager", reviewApplicationsManager);
        setFieldValue(listener, "reviewersNumber", 2);
        // mock values
        ReviewApplication application = new ReviewApplication();
        application.setAcceptPrimary(true);
        long projectId = 1;
        Project project = new Project(new Date(), new DefaultWorkdays());
        Phase phase = new Phase(project,1L);
        phase.setPhaseType(new PhaseType(1,DefaultReviewBoardApplicationListener.DEFAULT_REGISTRATION_PHASE_NAME));
        application.setProjectId(projectId);
        // mocking
        when(reviewApplicationsManager.getAllApplications(projectId)).thenReturn(new ReviewApplication[]{application});
        when(phaseManager.getPhases(projectId)).thenReturn(project);
        when(phaseManager.canEnd(phase)).thenReturn(true);
        listener.applicationReceived(application);
        // phase should be ended
        verify(phaseManager, times(0)).end(phase, registrationPhaseOperator);
        verify(phaseManager, times(0)).getPhases(projectId);
    }
    /**
     * <p>
     * Tests
     * {@link DefaultReviewBoardApplicationListener#applicationReceived(com.topcoder.management.project.ReviewApplication)}
     * with valid arguments passed, but phase is already ended.
     * </p>
     * <p>
     * Registration phase should be not be ended as it's already ended. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testApplicationReceived_NoNeedToEnd() throws Exception {
        listener.configure(config);
        String registrationPhaseOperator = "registrationPhaseOperator";
        setFieldValue(listener, "registrationPhaseOperator", registrationPhaseOperator);
        setFieldValue(listener, "phaseManager", phaseManager);
        setFieldValue(listener, "reviewApplicationsManager", reviewApplicationsManager);
        setFieldValue(listener, "reviewersNumber", 1);
        // mock values
        ReviewApplication application = new ReviewApplication();
        application.setAcceptPrimary(true);
        long projectId = 1;
        Project project = new Project(new Date(), new DefaultWorkdays());
        Phase phase = new Phase(project,1L);
        phase.setPhaseType(new PhaseType(1,DefaultReviewBoardApplicationListener.DEFAULT_REGISTRATION_PHASE_NAME));
        application.setProjectId(projectId);
        // mocking
        when(reviewApplicationsManager.getAllApplications(projectId)).thenReturn(new ReviewApplication[]{application});
        when(phaseManager.getPhases(projectId)).thenReturn(project);
        when(phaseManager.canEnd(phase)).thenReturn(false);
        listener.applicationReceived(application);
        // phase should be ended
        verify(phaseManager, times(0)).end(phase, registrationPhaseOperator);

    }
}
