/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.web.common.dao.CoderTypeDAO;
import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.DemographicQuestionDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.common.model.CoderType;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.DemographicQuestion;
import com.topcoder.web.common.model.SecretQuestion;
import com.topcoder.web.common.model.TimeZone;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for ViewProfileCompletenessAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ViewProfileCompletenessActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents ViewProfileCompletenessAction instance for testing.
     * </p>
     */
    private ViewProfileCompletenessAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        MockFactory.initDAOs();
        action = (ViewProfileCompletenessAction) getBean("viewProfileCompletenessAction");
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
     * Tests ViewProfileCompletenessAction constructor.
     * </p>
     * <p>
     * ViewProfileCompletenessAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("ViewProfileCompletenessAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#checkInitialization() method with valid fields.
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
     * Tests ViewProfileCompletenessAction#checkInitialization() method with null demographicQuestionDAO.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_DemographicQuestionDAO() throws Exception {
        action.setDemographicQuestionDAO(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#checkInitialization() method with null countryDAO.
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
     * Tests ViewProfileCompletenessAction#checkInitialization() method with null timeZoneDAO.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_TimeZoneDAO() throws Exception {
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
     * Tests ViewProfileCompletenessAction#checkInitialization() method with null coderTypeDAO.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_CoderTypeDAO() throws Exception {
        action.setCoderTypeDAO(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#processOutputData(User) method with valid fields and passed user with not
     * null first name.
     * </p>
     * <p>
     * Displayed user should be changed and tab should be changed. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData_Contact() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        MockFactory.createUserInSession(action);
        com.topcoder.web.common.model.User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        action.processOutputData(user);
        assertEquals("Displayed user should be changed.", user, action.getDisplayedUser());
        assertEquals("Tab should be changed", CurrentTab.CONTACT_TAB, action.getCurrentTab());
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#processOutputData(User) method with valid fields and passed user with not
     * null demographic responses.
     * </p>
     * <p>
     * Displayed user should be changed and tab should be changed. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData_Demographic() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        MockFactory.createUserInSession(action);
        User user = new User();
        user.setDemographicResponses(MockFactory.getDemographicResponses());
        action.processOutputData(user);
        assertEquals("Displayed user should be changed.", user, action.getDisplayedUser());
        assertEquals("Tab should be changed", CurrentTab.DEMOGRAPHIC_TAB, action.getCurrentTab());
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#processOutputData(User) method with valid fields and passed user with data
     * for account tab.
     * </p>
     * <p>
     * Displayed user should be changed and tab should be changed. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData_Account() throws Exception {
        action.setServletRequest(MockFactory.createServletRequest());
        MockFactory.createUserInSession(action);
        User user = new User();
        user.setHandle("handle");
        user.setSecretQuestion(new SecretQuestion());
        action.processOutputData(user);
        assertEquals("Displayed user should be changed.", user, action.getDisplayedUser());
        assertEquals("Tab should be changed", CurrentTab.ACCOUNT_TAB, action.getCurrentTab());
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#performAdditionalTasks(User) method with valid fields and passed user.
     * </p>
     * <p>
     * Questions should be set successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPerformAdditionalTasks() throws Exception {
        com.topcoder.web.common.model.User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        action.performAdditionalTasks(user);
        assertEquals("Questions should be set successfully.", 4, action.getDemographicQuestions().size());
        assertEquals("Values should be changed successfully.", 3, action.getCountries().size());
        assertEquals("Values should be changed successfully.", 3, action.getRepresentationCountries().size());
        assertEquals("Values should be changed successfully.", 2, action.getTimezones().size());
        assertEquals("Values should be changed successfully.", 2, action.getCoderTypes().size());
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#performAdditionalTasks(User) method with valid fields and passed user, but
     * exception occurred in underlying DAO.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPerformAdditionalTasks_Exception() throws Exception {
        com.topcoder.web.common.model.User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        when(action.getDemographicQuestionDAO().getQuestions()).thenThrow(new RuntimeException("just for testing."));
        try {
            action.performAdditionalTasks(user);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#getCountryDAO() method.
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
     * Tests ViewProfileCompletenessAction#setCountryDAO(CountryDAO) method.
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
     * Tests ViewProfileCompletenessAction#getTimeZoneDAO() method.
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
     * Tests ViewProfileCompletenessAction#setTimeZoneDAO(TimeZoneDAO) method.
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
     * Tests ViewProfileCompletenessAction#getDemographicQuestionDAO() method.
     * </p>
     * <p>
     * demographicQuestionDAO should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetDemographicQuestionDAO() {
        DemographicQuestionDAO demographicQuestionDAO = MockFactory.getDemographicQuestionDAO();
        action.setDemographicQuestionDAO(demographicQuestionDAO);
        assertSame("getDemographicQuestionDAO() doesn't work properly.", demographicQuestionDAO,
                action.getDemographicQuestionDAO());
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#setDemographicQuestionDAO(DemographicQuestionDAO) method.
     * </p>
     * <p>
     * demographicQuestionDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetDemographicQuestionDAO() {
        DemographicQuestionDAO demographicQuestionDAO = MockFactory.getDemographicQuestionDAO();
        action.setDemographicQuestionDAO(demographicQuestionDAO);
        assertSame("setDemographicQuestionDAO() doesn't work properly.", demographicQuestionDAO,
                action.getDemographicQuestionDAO());
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#getCoderTypeDAO() method.
     * </p>
     * <p>
     * coderTypeDAO should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCoderTypeDAO() {
        CoderTypeDAO coderTypeDAO = MockFactory.getCoderTypeDAO();
        action.setCoderTypeDAO(coderTypeDAO);
        assertSame("getCoderTypeDAO() doesn't work properly.", coderTypeDAO, action.getCoderTypeDAO());
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#setCoderTypeDAO(CoderTypeDAO) method.
     * </p>
     * <p>
     * coderTypeDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCoderTypeDAO() {
        CoderTypeDAO coderTypeDAO = MockFactory.getCoderTypeDAO();
        action.setCoderTypeDAO(coderTypeDAO);
        assertSame("setCoderTypeDAO() doesn't work properly.", coderTypeDAO, action.getCoderTypeDAO());
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#getCountries() method.
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
     * Tests ViewProfileCompletenessAction#setCountries(List) method.
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
     * Tests ViewProfileCompletenessAction#getRepresentationCountries() method.
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
     * Tests ViewProfileCompletenessAction#setRepresentationCountries(List) method.
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
     * Tests ViewProfileCompletenessAction#getTimezones() method.
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
     * Tests ViewProfileCompletenessAction#setTimezones(List) method.
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

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#getDemographicQuestions() method.
     * </p>
     * <p>
     * demographicQuestions should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetDemographicQuestions() {
        List<DemographicQuestion> demographicQuestions = new ArrayList<DemographicQuestion>();
        action.setDemographicQuestions(demographicQuestions);
        assertSame("getDemographicQuestions() doesn't work properly.", demographicQuestions,
                action.getDemographicQuestions());
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#setDemographicQuestions(List) method.
     * </p>
     * <p>
     * demographicQuestions should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetDemographicQuestions() {
        List<DemographicQuestion> demographicQuestions = new ArrayList<DemographicQuestion>();
        action.setDemographicQuestions(demographicQuestions);
        assertSame("setDemographicQuestions() doesn't work properly.", demographicQuestions,
                action.getDemographicQuestions());
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#getCoderTypes() method.
     * </p>
     * <p>
     * coderTypes should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCoderTypes() {
        List<CoderType> coderTypes = new ArrayList<CoderType>();
        action.setCoderTypes(coderTypes);
        assertSame("getCoderTypes() doesn't work properly.", coderTypes, action.getCoderTypes());
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#setCoderTypes(List) method.
     * </p>
     * <p>
     * coderTypes should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCoderTypes() {
        List<CoderType> coderTypes = new ArrayList<CoderType>();
        action.setCoderTypes(coderTypes);
        assertSame("setCoderTypes() doesn't work properly.", coderTypes, action.getCoderTypes());
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#getCurrentTab() method.
     * </p>
     * <p>
     * currentTab should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCurrentTab() {
        CurrentTab currentTab = CurrentTab.ACCOUNT_TAB;
        action.setCurrentTab(currentTab);
        assertSame("getCurrentTab() doesn't work properly.", currentTab, action.getCurrentTab());
    }

    /**
     * <p>
     * Tests ViewProfileCompletenessAction#setCurrentTab(CurrentTab) method.
     * </p>
     * <p>
     * currentTab should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCurrentTab() {
        CurrentTab currentTab = CurrentTab.CONTACT_TAB;
        action.setCurrentTab(currentTab);
        assertSame("setCurrentTab() doesn't work properly.", currentTab, action.getCurrentTab());
    }
}
