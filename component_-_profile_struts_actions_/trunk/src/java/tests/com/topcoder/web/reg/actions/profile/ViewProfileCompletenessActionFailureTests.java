/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.topcoder.web.common.dao.CoderTypeDAO;
import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.DemographicQuestionDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for ViewProfileCompletenessAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class ViewProfileCompletenessActionFailureTests extends TestCase {
    /**
     * <p>
     * The ViewProfileCompletenessAction instance for testing.
     * </p>
     */

    private ViewProfileCompletenessAction instance;

    /**
     * <p>
     * The CoderTypeDAO instance for testing.
     * </p>
     */
    private CoderTypeDAO coderTypeDAO;;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new ViewProfileCompletenessAction();
        coderTypeDAO = mock(CoderTypeDAO.class);
        instance.setCoderTypeDAO(coderTypeDAO);
        CountryDAO countryDAO = mock(CountryDAO.class);
        instance.setCountryDAO(countryDAO);
        TimeZoneDAO timeZoneDAO = mock(TimeZoneDAO.class);
        instance.setTimeZoneDAO(timeZoneDAO);
        DemographicQuestionDAO demographicQuestionDAO = mock(DemographicQuestionDAO.class);
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
        return new TestSuite(ViewProfileCompletenessActionFailureTests.class);
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#performAdditionalTasks(User) for failure.
     * Expects ProfileActionException.
     * </p>
     */
    public void testPerformAdditionalTasks_ProfileActionException() {
        when(coderTypeDAO.getCoderTypes()).thenThrow(new RuntimeException("error"));
        try {
            instance.performAdditionalTasks(new User());
            fail("ProfileActionException expected.");
        } catch (ProfileActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#checkInitialization() for failure.
     * It tests the case that when coderTypeDAO is null and expects ProfileActionConfigurationException.
     * </p>
     */
    public void testCheckInitialization_NullCoderTypeDAO() {
        instance.setCoderTypeDAO(null);
        try {
            instance.checkInitialization();
            fail("ProfileActionConfigurationException expected.");
        } catch (ProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#checkInitialization() for failure.
     * It tests the case that when demographicQuestionDAO is null and expects ProfileActionConfigurationException.
     * </p>
     */
    public void testCheckInitialization_NullDemographicQuestionDAO() {
        instance.setDemographicQuestionDAO(null);
        try {
            instance.checkInitialization();
            fail("ProfileActionConfigurationException expected.");
        } catch (ProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#checkInitialization() for failure.
     * It tests the case that when countryDAO is null and expects ProfileActionConfigurationException.
     * </p>
     */
    public void testCheckInitialization_NullCountryDAO() {
        instance.setCountryDAO(null);
        try {
            instance.checkInitialization();
            fail("ProfileActionConfigurationException expected.");
        } catch (ProfileActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#checkInitialization() for failure.
     * It tests the case that when timeZoneDAO is null and expects ProfileActionConfigurationException.
     * </p>
     */
    public void testCheckInitialization_NullTimeZoneDAO() {
        instance.setTimeZoneDAO(null);
        try {
            instance.checkInitialization();
            fail("ProfileActionConfigurationException expected.");
        } catch (ProfileActionConfigurationException e) {
            //good
        }
    }

}