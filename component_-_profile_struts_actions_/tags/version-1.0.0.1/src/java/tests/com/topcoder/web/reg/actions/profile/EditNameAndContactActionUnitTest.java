/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.TimeZone;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for EditNameAndContactAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EditNameAndContactActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents EditNameAndContactAction instance for testing.
     * </p>
     */
    private EditNameAndContactAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        MockFactory.initDAOs();
        action = (EditNameAndContactAction) getBean("editNameAndContactAction");
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        MockFactory.resetDAOs();
        action = null;
    }

    /**
     * <p>
     * Tests EditNameAndContactAction constructor.
     * </p>
     * <p>
     * EditNameAndContactAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("EditNameAndContactAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#performAdditionalTasks() method with valid fields and passed user.
     * </p>
     * <p>
     * Values should be changed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPerformAdditionalTasks() throws Exception {
        com.topcoder.web.common.model.User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        action.performAdditionalTasks(user);
        assertEquals("Values should be changed successfully.", 3, action.getCountries().size());
        assertEquals("Values should be changed successfully.", 3, action.getRepresentationCountries().size());
        assertEquals("Values should be changed successfully.", 2, action.getTimezones().size());
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#checkInitialization() method with valid fields.
     * </p>
     * <p>
     * check initialization should pass successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization() throws Exception {
        action.checkInitialization();
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#checkInitialization() method with null countryDAO.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_CountryDAO() throws Exception {
        action.setCountryDAO(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#checkInitialization() method with null timezoneDAO.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_TimezoneDAO() throws Exception {
        action.setTimeZoneDAO(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#performAdditionalTasks() method with valid fields and passed user, but underlying
     * exception occurred.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPerformAdditionalTasks_Exception() throws Exception {
        com.topcoder.web.common.model.User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        when(action.getCountryDAO().getCountries()).thenThrow(new RuntimeException("just for testing."));
        try {
            action.performAdditionalTasks(user);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#getCountryDAO() method.
     * </p>
     * <p>
     * countryDAO should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCountryDAO() {
        CountryDAO countryDAO = MockFactory.getCountryDAO();
        action.setCountryDAO(countryDAO);
        assertSame("getCountryDAO() doesn't work properly.", countryDAO, action.getCountryDAO());
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#setCountryDAO(CountryDAO) method.
     * </p>
     * <p>
     * countryDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCountryDAO() {
        CountryDAO countryDAO = MockFactory.getCountryDAO();
        action.setCountryDAO(countryDAO);
        assertSame("setCountryDAO() doesn't work properly.", countryDAO, action.getCountryDAO());
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#getTimeZoneDAO() method.
     * </p>
     * <p>
     * timeZoneDAO should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetTimeZoneDAO() {
        TimeZoneDAO timeZoneDAO = MockFactory.getTimeZoneDAO();
        action.setTimeZoneDAO(timeZoneDAO);
        assertSame("getTimeZoneDAO() doesn't work properly.", timeZoneDAO, action.getTimeZoneDAO());
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#setTimeZoneDAO(TimeZoneDAO) method.
     * </p>
     * <p>
     * timeZoneDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetTimeZoneDAO() {
        TimeZoneDAO timeZoneDAO = MockFactory.getTimeZoneDAO();
        action.setTimeZoneDAO(timeZoneDAO);
        assertSame("setTimeZoneDAO() doesn't work properly.", timeZoneDAO, action.getTimeZoneDAO());
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#getCountries() method.
     * </p>
     * <p>
     * countries should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCountries() {
        List<Country> countries = new ArrayList<Country>();
        action.setCountries(countries);
        assertSame("getCountries() doesn't work properly.", countries, action.getCountries());
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#setCountries(List) method.
     * </p>
     * <p>
     * countries should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCountries() {
        List<Country> countries = new ArrayList<Country>();
        action.setCountries(countries);
        assertSame("setCountries() doesn't work properly.", countries, action.getCountries());
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#getRepresentationCountries() method.
     * </p>
     * <p>
     * representationCountries should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetRepresentationCountries() {
        List<Country> representationCountries = new ArrayList<Country>();
        action.setRepresentationCountries(representationCountries);
        assertSame("getRepresentationCountries() doesn't work properly.", representationCountries,
                action.getRepresentationCountries());
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#setRepresentationCountries(List) method.
     * </p>
     * <p>
     * representationCountries should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetRepresentationCountries() {
        List<Country> representationCountries = new ArrayList<Country>();
        action.setRepresentationCountries(representationCountries);
        assertSame("setRepresentationCountries() doesn't work properly.", representationCountries,
                action.getRepresentationCountries());
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#getTimezones() method.
     * </p>
     * <p>
     * timezones should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetTimezones() {
        List<TimeZone> timezones = new ArrayList<TimeZone>();
        action.setTimezones(timezones);
        assertSame("getTimezones() doesn't work properly.", timezones, action.getTimezones());
    }

    /**
     * <p>
     * Tests EditNameAndContactAction#setTimezones(List) method.
     * </p>
     * <p>
     * timezones should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetTimezones() {
        List<TimeZone> timezones = new ArrayList<TimeZone>();
        action.setTimezones(timezones);
        assertSame("setTimezones() doesn't work properly.", timezones, action.getTimezones());
    }
}
