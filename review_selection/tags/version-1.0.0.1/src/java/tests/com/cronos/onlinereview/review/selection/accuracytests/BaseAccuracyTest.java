/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.accuracytests;

import static org.mockito.Mockito.mock;

import java.io.File;
import java.lang.reflect.Field;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.selection.ReviewApplicationProcessor;
import com.cronos.onlinereview.review.selection.impl.WorkloadFactorCalculator;
import com.topcoder.apps.review.rboard.RBoardApplicationBean;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.ApplicationsManager;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.resource.ReviewerStatisticsManager;

/**
 * This is base Accuracy test case for this component.
 * @author sokol
 * @version 1.0
 */
public class BaseAccuracyTest extends TestCase {

    /**
     * <p>
     * Represents directory for files used by Accuracy tests.
     * </p>
     */
    protected final static String ACCURACY_DIR =
            System.getProperty("user.dir") + File.separator + "test_files" + File.separator + "accuracy"
                    + File.separator;

    /**
     * <p>
     * Represents delta value for comparing double.
     * </p>
     */
    protected final static double DELTA = 0.0001;

    /**
     * <p>
     * Represents ConfigurationObject instance for testing.
     * </p>
     */
    protected ConfigurationObject config;

    /**
     * <p>
     * Represents ReviewerStatisticsManager instance for testing.
     * </p>
     */
    protected ReviewerStatisticsManager reviewerStatisticsManager;

    /**
     * <p>
     * Represents ProjectManager instance for testing.
     * </p>
     */
    protected ProjectManager projectManager;

    /**
     * <p>
     * Represents PhaseManager instance for testing.
     * </p>
     */
    protected PhaseManager phaseManager;

    /**
     * <p>
     * Represents ReviewApplicationProcessor instance for testing.
     * </p>
     */
    protected ReviewApplicationProcessor reviewApplicationProcessor;

    /**
     * <p>
     * Represents MockRBoardApplicationBean instance for testing.
     * </p>
     */
    protected RBoardApplicationBean rboardApplicationBean;

    /**
     * <p>
     * Represents WorkloadFactorCalculator instance for testing.
     * </p>
     */
    protected WorkloadFactorCalculator workloadFactorCalculator;

    /**
     * <p>
     * Represents ApplicationsManager instance for testing.
     * </p>
     */
    protected ApplicationsManager reviewApplicationsManager;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        reviewerStatisticsManager = mock(ReviewerStatisticsManager.class);
        projectManager = mock(ProjectManager.class);
        phaseManager = mock(PhaseManager.class);
        reviewApplicationProcessor = mock(ReviewApplicationProcessor.class);
        rboardApplicationBean = mock(RBoardApplicationBean.class);
        workloadFactorCalculator = mock(WorkloadFactorCalculator.class);
        reviewApplicationsManager = mock(ApplicationsManager.class);
    }
    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void tearDown() throws Exception {
        config = null;
        reviewerStatisticsManager = null;
        projectManager = null;
        phaseManager = null;
        reviewApplicationProcessor = null;
        rboardApplicationBean = null;
        workloadFactorCalculator = null;
        reviewApplicationsManager = null;
    }

    /**
     * <p>
     * Retrieves field value from object.
     * </p>
     * @param object the object to get field value from
     * @param fieldName the field name
     * @return field value retrieved from given object
     * @throws Exception if any error occurs
     */
    protected Object getFieldValue(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    /**
     * <p>
     * Sets to object field value given fieldValue.
     * </p>
     * @param object the object to set field value
     * @param fieldName the field name
     * @param fieldValue the field value to be set
     * @throws Exception if any error occurs
     */
    protected void setFieldValue(Object object, String fieldName, Object fieldValue) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, fieldValue);
    }
}
