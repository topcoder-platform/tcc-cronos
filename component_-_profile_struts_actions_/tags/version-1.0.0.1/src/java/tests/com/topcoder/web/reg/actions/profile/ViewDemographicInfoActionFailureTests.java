/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.topcoder.web.common.dao.DemographicQuestionDAO;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for ViewDemographicInfoAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class ViewDemographicInfoActionFailureTests extends TestCase {
    /**
     * <p>
     * The ViewDemographicInfoAction instance for testing.
     * </p>
     */
    private ViewDemographicInfoAction instance;

    /**
     * <p>
     * The DemographicQuestionDAO instance for testing.
     * </p>
     */
    private DemographicQuestionDAO demographicQuestionDAO;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new ViewDemographicInfoAction();
        demographicQuestionDAO = mock(DemographicQuestionDAO.class);
        instance.setDemographicQuestionDAO(demographicQuestionDAO);

    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ViewDemographicInfoActionFailureTests.class);
    }

    /**
     * <p>
     * Tests ViewDemographicInfoAction#performAdditionalTasks(User) for failure.
     * Expects ProfileActionException.
     * </p>
     */
    public void testPerformAdditionalTasks_ProfileActionException() {
        when(demographicQuestionDAO.getQuestions()).thenThrow(new RuntimeException("error"));
        try {
            instance.performAdditionalTasks(new User());
            fail("ProfileActionException expected.");
        } catch (ProfileActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ViewDemographicInfoAction#checkInitialization() for failure.
     * It tests the case that when demographicQuestionDAO is null and expects ProfileActionConfigurationException.
     * </p>
     */
    public void testcheckInitialization_NullDemographicQuestionDAO() {
        instance.setDemographicQuestionDAO(null);
        try {
            instance.checkInitialization();
            fail("ProfileActionConfigurationException expected.");
        } catch (ProfileActionConfigurationException e) {
            //good
        }
    }

}