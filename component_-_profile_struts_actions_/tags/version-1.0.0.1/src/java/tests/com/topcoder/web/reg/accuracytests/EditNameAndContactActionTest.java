/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.reg.actions.profile.EditNameAndContactAction;
import com.topcoder.web.reg.actions.profile.ViewNameAndContactAction;

/**
 * <p>
 * Accuracy tests for the {@link EditNameAndContactAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class EditNameAndContactActionTest {
    /** Represents the countries used to test again. */
    private List countriesValue;

    /** Represents the countryDAO used to test again. */
    private CountryDAO countryDAOValue;

    /** Represents the representationCountries used to test again. */
    private List representationCountriesValue;

    /** Represents the timeZoneDAO used to test again. */
    private TimeZoneDAO timeZoneDAOValue;

    /** Represents the timezones used to test again. */
    private List timezonesValue;

    /** Represents the instance used to test again. */
    private EditNameAndContactAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new EditNameAndContactAction();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link EditNameAndContactAction#EditNameAndContactAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testEditNameAndContactAction() throws Exception {
        new EditNameAndContactAction();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link EditNameAndContactAction#getCountries()}
     * </p>
     * <p>
     * The value of <code>countries</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getCountries() throws Exception {
        assertNull("Old value", testInstance.getCountries());
    }

    /**
     * <p>
     * Accuracy test {@link EditNameAndContactAction#setCountries(List)}.
     * </p>
     * <p>
     * The value of <code>countries</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setCountries() throws Exception {
        testInstance.setCountries(countriesValue);
        assertEquals("New value", countriesValue, testInstance.getCountries());
    }

    /**
     * <p>
     * Accuracy test for {@link EditNameAndContactAction#getCountryDAO()}
     * </p>
     * <p>
     * The value of <code>countryDAO</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getCountryDAO() throws Exception {
        assertNull("Old value", testInstance.getCountryDAO());
    }

    /**
     * <p>
     * Accuracy test {@link EditNameAndContactAction#setCountryDAO(CountryDAO)}.
     * </p>
     * <p>
     * The value of <code>countryDAO</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setCountryDAO() throws Exception {
        testInstance.setCountryDAO(countryDAOValue);
        assertEquals("New value", countryDAOValue, testInstance.getCountryDAO());
    }

    /**
     * <p>
     * Accuracy test for {@link EditNameAndContactAction#getRepresentationCountries()}
     * </p>
     * <p>
     * The value of <code>representationCountries</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getRepresentationCountries() throws Exception {
        assertNull("Old value", testInstance.getRepresentationCountries());
    }

    /**
     * <p>
     * Accuracy test {@link EditNameAndContactAction#setRepresentationCountries(List)}.
     * </p>
     * <p>
     * The value of <code>representationCountries</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setRepresentationCountries() throws Exception {
        testInstance.setRepresentationCountries(representationCountriesValue);
        assertEquals("New value", representationCountriesValue, testInstance.getRepresentationCountries());
    }

    /**
     * <p>
     * Accuracy test for {@link EditNameAndContactAction#getTimeZoneDAO()}
     * </p>
     * <p>
     * The value of <code>timeZoneDAO</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getTimeZoneDAO() throws Exception {
        assertNull("Old value", testInstance.getTimeZoneDAO());
    }

    /**
     * <p>
     * Accuracy test {@link EditNameAndContactAction#setTimeZoneDAO(TimeZoneDAO)}.
     * </p>
     * <p>
     * The value of <code>timeZoneDAO</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setTimeZoneDAO() throws Exception {
        testInstance.setTimeZoneDAO(timeZoneDAOValue);
        assertEquals("New value", timeZoneDAOValue, testInstance.getTimeZoneDAO());
    }

    /**
     * <p>
     * Accuracy test for {@link EditNameAndContactAction#getTimezones()}
     * </p>
     * <p>
     * The value of <code>timezones</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getTimezones() throws Exception {
        assertNull("Old value", testInstance.getTimezones());
    }

    /**
     * <p>
     * Accuracy test {@link EditNameAndContactAction#setTimezones(List)}.
     * </p>
     * <p>
     * The value of <code>timezones</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setTimezones() throws Exception {
        testInstance.setTimezones(timezonesValue);
        assertEquals("New value", timezonesValue, testInstance.getTimezones());
    }

}