/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for EditNameAndContactAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class EditNameAndContactActionFailureTests extends TestCase {
    /**
     * <p>
     * The EditNameAndContactAction instance for testing.
     * </p>
     */
    private EditNameAndContactAction instance;

    /**
     * <p>
     * The CountryDAO instance for testing.
     * </p>
     */
    private CountryDAO countryDAO;

    /**
     * <p>
     * The TimeZoneDAO instance for testing.
     * </p>
     */
    private TimeZoneDAO timeZoneDAO;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new EditNameAndContactAction();
        countryDAO = mock(CountryDAO.class);
        timeZoneDAO = mock(TimeZoneDAO.class);
        instance.setCountryDAO(countryDAO);
        instance.setTimeZoneDAO(timeZoneDAO);
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(EditNameAndContactActionFailureTests.class);
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#performAdditionalTasks(User) for failure.
     * Expects ProfileActionException.
     * </p>
     */
    public void testPerformAdditionalTasks_ProfileActionException() {
        when(countryDAO.getCountries()).thenThrow(new RuntimeException("error"));
        try {
            instance.performAdditionalTasks(new User());
            fail("ProfileActionException expected.");
        } catch (ProfileActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#checkInitialization() for failure.
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
     * Tests EditNameAndContactAction#checkInitialization() for failure.
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