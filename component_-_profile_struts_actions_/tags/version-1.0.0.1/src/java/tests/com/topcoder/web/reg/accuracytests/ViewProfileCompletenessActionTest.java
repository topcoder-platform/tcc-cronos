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

import com.topcoder.web.common.dao.CoderTypeDAO;
import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.DemographicQuestionDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.reg.actions.profile.BaseDisplayProfileAction;
import com.topcoder.web.reg.actions.profile.CurrentTab;
import com.topcoder.web.reg.actions.profile.ViewProfileCompletenessAction;

/**
 * <p>
 * Accuracy tests for the {@link ViewProfileCompletenessAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class ViewProfileCompletenessActionTest {
    /** Represents the coderTypeDAO used to test again. */
    private CoderTypeDAO coderTypeDAOValue;

    /** Represents the coderTypes used to test again. */
    private List coderTypesValue;

    /** Represents the countries used to test again. */
    private List countriesValue;

    /** Represents the countryDAO used to test again. */
    private CountryDAO countryDAOValue;

    /** Represents the currentTab used to test again. */
    private CurrentTab currentTabValue;

    /** Represents the demographicQuestionDAO used to test again. */
    private DemographicQuestionDAO demographicQuestionDAOValue;

    /** Represents the demographicQuestions used to test again. */
    private List demographicQuestionsValue;

    /** Represents the representationCountries used to test again. */
    private List representationCountriesValue;

    /** Represents the timeZoneDAO used to test again. */
    private TimeZoneDAO timeZoneDAOValue;

    /** Represents the timezones used to test again. */
    private List timezonesValue;

    /** Represents the instance used to test again. */
    private ViewProfileCompletenessAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new ViewProfileCompletenessAction();
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
     * Accuracy test for {@link ViewProfileCompletenessAction#ViewProfileCompletenessAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testViewProfileCompletenessAction() throws Exception {
        new ViewProfileCompletenessAction();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link ViewProfileCompletenessAction#getCoderTypeDAO()}
     * </p>
     * <p>
     * The value of <code>coderTypeDAO</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getCoderTypeDAO() throws Exception {
        assertNull("Old value", testInstance.getCoderTypeDAO());
    }

    /**
     * <p>
     * Accuracy test {@link ViewProfileCompletenessAction#setCoderTypeDAO(CoderTypeDAO)}.
     * </p>
     * <p>
     * The value of <code>coderTypeDAO</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setCoderTypeDAO() throws Exception {
        testInstance.setCoderTypeDAO(coderTypeDAOValue);
        assertEquals("New value", coderTypeDAOValue, testInstance.getCoderTypeDAO());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewProfileCompletenessAction#getCoderTypes()}
     * </p>
     * <p>
     * The value of <code>coderTypes</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getCoderTypes() throws Exception {
        assertNull("Old value", testInstance.getCoderTypes());
    }

    /**
     * <p>
     * Accuracy test {@link ViewProfileCompletenessAction#setCoderTypes(List)}.
     * </p>
     * <p>
     * The value of <code>coderTypes</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setCoderTypes() throws Exception {
        testInstance.setCoderTypes(coderTypesValue);
        assertEquals("New value", coderTypesValue, testInstance.getCoderTypes());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewProfileCompletenessAction#getCountries()}
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
     * Accuracy test {@link ViewProfileCompletenessAction#setCountries(List)}.
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
     * Accuracy test for {@link ViewProfileCompletenessAction#getCountryDAO()}
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
     * Accuracy test {@link ViewProfileCompletenessAction#setCountryDAO(CountryDAO)}.
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
     * Accuracy test for {@link ViewProfileCompletenessAction#getCurrentTab()}
     * </p>
     * <p>
     * The value of <code>currentTab</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getCurrentTab() throws Exception {
        assertNull("Old value", testInstance.getCurrentTab());
    }

    /**
     * <p>
     * Accuracy test {@link ViewProfileCompletenessAction#setCurrentTab(CurrentTab)}.
     * </p>
     * <p>
     * The value of <code>currentTab</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setCurrentTab() throws Exception {
        testInstance.setCurrentTab(currentTabValue);
        assertEquals("New value", currentTabValue, testInstance.getCurrentTab());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewProfileCompletenessAction#getDemographicQuestionDAO()}
     * </p>
     * <p>
     * The value of <code>demographicQuestionDAO</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getDemographicQuestionDAO() throws Exception {
        assertNull("Old value", testInstance.getDemographicQuestionDAO());
    }

    /**
     * <p>
     * Accuracy test {@link ViewProfileCompletenessAction#setDemographicQuestionDAO(DemographicQuestionDAO)}.
     * </p>
     * <p>
     * The value of <code>demographicQuestionDAO</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setDemographicQuestionDAO() throws Exception {
        testInstance.setDemographicQuestionDAO(demographicQuestionDAOValue);
        assertEquals("New value", demographicQuestionDAOValue, testInstance.getDemographicQuestionDAO());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewProfileCompletenessAction#getDemographicQuestions()}
     * </p>
     * <p>
     * The value of <code>demographicQuestions</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getDemographicQuestions() throws Exception {
        assertNull("Old value", testInstance.getDemographicQuestions());
    }

    /**
     * <p>
     * Accuracy test {@link ViewProfileCompletenessAction#setDemographicQuestions(List)}.
     * </p>
     * <p>
     * The value of <code>demographicQuestions</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setDemographicQuestions() throws Exception {
        testInstance.setDemographicQuestions(demographicQuestionsValue);
        assertEquals("New value", demographicQuestionsValue, testInstance.getDemographicQuestions());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewProfileCompletenessAction#getRepresentationCountries()}
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
     * Accuracy test {@link ViewProfileCompletenessAction#setRepresentationCountries(List)}.
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
     * Accuracy test for {@link ViewProfileCompletenessAction#getTimeZoneDAO()}
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
     * Accuracy test {@link ViewProfileCompletenessAction#setTimeZoneDAO(TimeZoneDAO)}.
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
     * Accuracy test for {@link ViewProfileCompletenessAction#getTimezones()}
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
     * Accuracy test {@link ViewProfileCompletenessAction#setTimezones(List)}.
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

    /**
     * <p>
     * Accuracy test for {@link ViewProfileCompletenessAction#performAdditionalTasks()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_performAdditionalTasks() throws Exception {

    }
}