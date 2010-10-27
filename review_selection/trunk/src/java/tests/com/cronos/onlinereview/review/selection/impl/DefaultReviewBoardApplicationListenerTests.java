/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.selection.ReviewBoardApplicationListenerConfigurationException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.ReviewApplication;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.util.config.ConfigManager;

/**
 * Unit test cases for class DefaultReviewBoardApplicationListener.
 *
 * @author xianwenchen
 * @version 1.0
 */
public class DefaultReviewBoardApplicationListenerTests extends TestCase {
    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // load config
        ConfigManager.getInstance().add("db_connection_factory.xml");
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
     * Tests constructor: DefaultReviewBoardApplicationListener(). No exception is expected.
     */
    public void testCtor() {
        new DefaultReviewBoardApplicationListener();
    }

    /**
     * Tests configure(ConfigurationObject) with valid config. No exception is expected.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigure1() throws Exception {
        DefaultReviewBoardApplicationListener listener = new DefaultReviewBoardApplicationListener();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("listener_config").getChild("valid_config1");
        listener.configure(config);
    }

    /**
     * Tests configure(ConfigurationObject) with valid config. No exception is expected.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigure2() throws Exception {
        DefaultReviewBoardApplicationListener listener = new DefaultReviewBoardApplicationListener();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("listener_config").getChild("valid_config2");
        listener.configure(config);
    }

    /**
     * Tests configure(ConfigurationObject) with null config. IllegalArgumentException should be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigureWithNullConfig() throws Exception {
        try {
            DefaultReviewBoardApplicationListener listener = new DefaultReviewBoardApplicationListener();
            listener.configure(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests configure(ConfigurationObject) with invalid config. ReviewBoardApplicationListenerConfigurationException
     * should be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigureWithInvalidConfig1() throws Exception {
        try {
            DefaultReviewBoardApplicationListener listener = new DefaultReviewBoardApplicationListener();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("listener_config").getChild("invalid_config1");
            listener.configure(config);
            fail("Expect ReviewBoardApplicationListenerConfigurationException.");
        } catch (ReviewBoardApplicationListenerConfigurationException e) {
            // expect
        }
    }

    /**
     * Tests configure(ConfigurationObject) with invalid config. ReviewBoardApplicationListenerConfigurationException
     * should be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigureWithInvalidConfig2() throws Exception {
        try {
            DefaultReviewBoardApplicationListener listener = new DefaultReviewBoardApplicationListener();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("listener_config").getChild("invalid_config2");
            listener.configure(config);
            fail("Expect ReviewBoardApplicationListenerConfigurationException.");
        } catch (ReviewBoardApplicationListenerConfigurationException e) {
            // expect
        }
    }

    /**
     * Tests configure(ConfigurationObject) with invalid config. ReviewBoardApplicationListenerConfigurationException
     * should be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigureWithInvalidConfig3() throws Exception {
        try {
            DefaultReviewBoardApplicationListener listener = new DefaultReviewBoardApplicationListener();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("listener_config").getChild("invalid_config3");
            listener.configure(config);
            fail("Expect ReviewBoardApplicationListenerConfigurationException.");
        } catch (ReviewBoardApplicationListenerConfigurationException e) {
            // expect
        }
    }

    /**
     * Tests applicationReceived(ReviewApplication application). In the config, MockApplicationsManagerImpl is used as
     * the reviewApplicationsManager of application. And for projectId 1, it will return 2 secondary reviewers and 1
     * primary reviewer. So the registration phase will be ended after invoking applicationReceived. No exception is
     * expected.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationReceived1() throws Exception {
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
     * Tests applicationReceived(ReviewApplication application). In the config, MockApplicationsManagerImpl is used as
     * the reviewApplicationsManager of application. And for projectId 2, it will return 3 secondary reviewers without
     * primary reviewer. So the registration phase will NOT be ended after invoking applicationReceived. No exception
     * is expected.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationReceived2() throws Exception {
        DefaultReviewBoardApplicationListener listener = new DefaultReviewBoardApplicationListener();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("listener_config").getChild("valid_config1");
        listener.configure(config);

        String registrationPhaseOperator = "registrationPhaseOperator";
        setFieldValue(listener, "registrationPhaseOperator", registrationPhaseOperator);
        PhaseManager phaseManager = mock(PhaseManager.class);
        setFieldValue(listener, "phaseManager", phaseManager);

        ReviewApplication application = new ReviewApplication();
        application.setProjectId(2);

        // mock values
        Project project = new Project(new Date(), new DefaultWorkdays());
        Phase phase = new Phase(project,1L);
        phase.setPhaseType(new PhaseType(1,DefaultReviewBoardApplicationListener.DEFAULT_REGISTRATION_PHASE_NAME));

        // mocking
        when(phaseManager.getPhases(2)).thenReturn(project);
        when(phaseManager.canEnd(phase)).thenReturn(true);
        listener.applicationReceived(application);

        // phase should be ended
        verify(phaseManager, times(0)).end(phase, registrationPhaseOperator);
    }

    /**
     * Tests applicationReceived(ReviewApplication application). In the config, MockApplicationsManagerImpl is used as
     * the reviewApplicationsManager of application. And for projectId 3, it will return 1 reviewers without primary
     * reviewer. So the registration phase will NOT be ended after invoking applicationReceived. No exception is
     * expected.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationReceived3() throws Exception {
        DefaultReviewBoardApplicationListener listener = new DefaultReviewBoardApplicationListener();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("listener_config").getChild("valid_config1");
        listener.configure(config);

        String registrationPhaseOperator = "registrationPhaseOperator";
        setFieldValue(listener, "registrationPhaseOperator", registrationPhaseOperator);
        PhaseManager phaseManager = mock(PhaseManager.class);
        setFieldValue(listener, "phaseManager", phaseManager);

        ReviewApplication application = new ReviewApplication();
        application.setProjectId(3);

        // mock values
        Project project = new Project(new Date(), new DefaultWorkdays());
        Phase phase = new Phase(project,1L);
        phase.setPhaseType(new PhaseType(1,DefaultReviewBoardApplicationListener.DEFAULT_REGISTRATION_PHASE_NAME));

        // mocking
        when(phaseManager.getPhases(3)).thenReturn(project);
        when(phaseManager.canEnd(phase)).thenReturn(true);
        listener.applicationReceived(application);

        // phase should be ended
        verify(phaseManager, times(0)).end(phase, registrationPhaseOperator);
    }

    /**
     * Tests applicationReceived(ReviewApplication application) with null application. IllegalArgumentException should
     * be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationReceivedWithNullApplication() throws Exception {
        try {
            DefaultReviewBoardApplicationListener listener = new DefaultReviewBoardApplicationListener();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("listener_config").getChild("valid_config1");
            listener.configure(config);

            listener.applicationReceived(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests applicationReceived(ReviewApplication application) with invalid state. IllegalStateException should be
     * thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testApplicationReceivedWithInvalidState() throws Exception {
        try {
            DefaultReviewBoardApplicationListener listener = new DefaultReviewBoardApplicationListener();
            ReviewApplication application = new ReviewApplication();
            listener.applicationReceived(application);
            fail("Expect IllegalStateException.");
        } catch (IllegalStateException e) {
            // expect
        }
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
}
