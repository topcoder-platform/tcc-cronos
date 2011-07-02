/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.profilecompleteness.actions;

import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.topcoder.reg.profilecompleteness.filter.impl.UserIdRetriever;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.CoderTypeDAO;
import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.DemographicQuestionDAO;
import com.topcoder.web.common.dao.StateDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.profilecompleteness.actions.mock.MockFactory;

/**
 * <p>
 * This class aggregates all parameters getter/setter test cases for
 * com.topcoder.web.reg.profilecompleteness.actions.CompleteProfileAction.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class CompleteProfileActionParametersUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents CompleteProfileAction instance to test.
     * </p>
     */
    private CompleteProfileAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any exception occurred
     */
    public void setUp() throws Exception {
        action = new CompleteProfileAction();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any exception occurred
     */
    public void tearDown() throws Exception {
        action = null;
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setUserDAO(UserDAO) method.
     * </p>
     * <p>
     * userDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetUserDAO() {
        UserDAO userDAO = MockFactory.getUserDAO();
        action.setUserDAO(userDAO);
        assertSame("setUserDAO() doesn't work properly.", userDAO,
                getFieldValue("userDAO", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setAuditDAO(AuditDAO) method.
     * </p>
     * <p>
     * auditDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetAuditDAO() {
        AuditDAO auditDAO = MockFactory.getAuditDAO();
        action.setAuditDAO(auditDAO);
        assertSame("setAuditDAO() doesn't work properly.", auditDAO,
                getFieldValue("auditDAO", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setUserIdRetriever(UserIdRetriever) method.
     * </p>
     * <p>
     * userIdRetriever should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetUserIdRetriever() {
        UserIdRetriever userIdRetriever = MockFactory.getUserIdRetriever();
        action.setUserIdRetriever(userIdRetriever);
        assertSame("setUserIdRetriever() doesn't work properly.", userIdRetriever,
                getFieldValue("userIdRetriever", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setStateDAO(StateDAO) method.
     * </p>
     * <p>
     * stateDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetStateDAO() {
        StateDAO stateDAO = MockFactory.getStateDAO();
        action.setStateDAO(stateDAO);
        assertSame("setStateDAO() doesn't work properly.", stateDAO,
                getFieldValue("stateDAO", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setCountryDAO(CountryDAO) method.
     * </p>
     * <p>
     * countryDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCountryDAO() {
        CountryDAO countryDAO = MockFactory.getCountryDAO();
        action.setCountryDAO(countryDAO);
        assertSame("setCountryDAO() doesn't work properly.", countryDAO,
                getFieldValue("countryDAO", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setTimeZoneDAO(TimeZoneDAO) method.
     * </p>
     * <p>
     * timeZoneDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetTimeZoneDAO() {
        TimeZoneDAO timeZoneDAO = MockFactory.getTimeZoneDAO();
        action.setTimeZoneDAO(timeZoneDAO);
        assertSame("setTimeZoneDAO() doesn't work properly.", timeZoneDAO,
                getFieldValue("timeZoneDAO", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setCoderTypeDAO(CoderTypeDAO) method.
     * </p>
     * <p>
     * coderTypeDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCoderTypeDAO() {
        CoderTypeDAO coderTypeDAO = MockFactory.getCoderTypeDAO();
        action.setCoderTypeDAO(coderTypeDAO);
        assertSame("setCoderTypeDAO() doesn't work properly.", coderTypeDAO,
                getFieldValue("coderTypeDAO", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setDemographicQuestionDAO(DemographicQuestionDAO) method.
     * </p>
     * <p>
     * demographicQuestionDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetDemographicQuestionDAO() {
        DemographicQuestionDAO demographicQuestionDAO = MockFactory.getDemographicQuestionDAO();
        action.setDemographicQuestionDAO(demographicQuestionDAO);
        assertSame("setDemographicQuestionDAO() doesn't work properly.", demographicQuestionDAO,
                getFieldValue("demographicQuestionDAO", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setPhoneTypeId(int) method.
     * </p>
     * <p>
     * phoneTypeId should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetPhoneTypeId() {
        int phoneTypeId = 1;
        action.setPhoneTypeId(phoneTypeId);
        assertEquals("setPhoneTypeId() doesn't work properly.", phoneTypeId,
                getFieldValue("phoneTypeId", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setPrimary(boolean) method.
     * </p>
     * <p>
     * primary should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetPrimary() {
        boolean primary = true;
        action.setPrimary(primary);
        assertEquals("setPrimary() doesn't work properly.", primary,
                getFieldValue("primary", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setSecurityKeyTypeId(int) method.
     * </p>
     * <p>
     * securityKeyTypeId should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetSecurityKeyTypeId() {
        int securityKeyTypeId = 1;
        action.setSecurityKeyTypeId(securityKeyTypeId);
        assertEquals("setSecurityKeyTypeId() doesn't work properly.", securityKeyTypeId,
                getFieldValue("securityKeyTypeId", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setGetUserProfileOperationType(String) method.
     * </p>
     * <p>
     * getUserProfileOperationType should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetGetUserProfileOperationType() {
        String getUserProfileOperationType = "test";
        action.setGetUserProfileOperationType(getUserProfileOperationType);
        assertSame("setGetUserProfileOperationType() doesn't work properly.", getUserProfileOperationType,
                getFieldValue("getUserProfileOperationType", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setSaveUserProfileOperationType(String) method.
     * </p>
     * <p>
     * saveUserProfileOperationType should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetSaveUserProfileOperationType() {
        String saveUserProfileOperationType = "test";
        action.setSaveUserProfileOperationType(saveUserProfileOperationType);
        assertSame("setSaveUserProfileOperationType() doesn't work properly.", saveUserProfileOperationType,
                getFieldValue("saveUserProfileOperationType", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setIncomingRequestURIKey(String) method.
     * </p>
     * <p>
     * incomingRequestURIKey should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetIncomingRequestURIKey() {
        String incomingRequestURIKey = "test";
        action.setIncomingRequestURIKey(incomingRequestURIKey);
        assertSame("setIncomingRequestURIKey() doesn't work properly.", incomingRequestURIKey,
                getFieldValue("incomingRequestURIKey", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setGenderQuestionId(long) method.
     * </p>
     * <p>
     * genderQuestionId should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetGenderQuestionId() {
        long genderQuestionId = 1;
        action.setGenderQuestionId(genderQuestionId);
        assertEquals("setGenderQuestionId() doesn't work properly.", genderQuestionId,
                getFieldValue("genderQuestionId", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setAgeQuestionId(long) method.
     * </p>
     * <p>
     * ageQuestionId should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetAgeQuestionId() {
        long ageQuestionId = 1;
        action.setAgeQuestionId(ageQuestionId);
        assertEquals("setAgeQuestionId() doesn't work properly.", ageQuestionId,
                getFieldValue("ageQuestionId", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setServletRequest(HttpServletRequest) method.
     * </p>
     * <p>
     * servletRequest should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetServletRequest() {
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        action.setServletRequest(servletRequest);
        assertSame("setServletRequest() doesn't work properly.", servletRequest,
                getFieldValue("servletRequest", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setServletResponse(HttpServletResponse) method.
     * </p>
     * <p>
     * servletResponse should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetServletResponse() {
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);
        action.setServletResponse(servletResponse);
        assertSame("setServletResponse() doesn't work properly.", servletResponse,
                getFieldValue("servletResponse", action, CompleteProfileAction.class));
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getUser() method.
     * </p>
     * <p>
     * user should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetUser() {
        User user = new User();
        setFieldValue("user", user, action, CompleteProfileAction.class);
        assertSame("getUser() doesn't work properly.", user, action.getUser());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getFirstName() method.
     * </p>
     * <p>
     * firstName should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetFirstName() {
        String firstName = "test";
        action.setFirstName(firstName);
        assertSame("getFirstName() doesn't work properly.", firstName, action.getFirstName());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setFirstName(String) method.
     * </p>
     * <p>
     * firstName should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetFirstName() {
        String firstName = "test";
        action.setFirstName(firstName);
        assertSame("setFirstName() doesn't work properly.", firstName, action.getFirstName());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getLastName() method.
     * </p>
     * <p>
     * lastName should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetLastName() {
        String lastName = "test";
        action.setLastName(lastName);
        assertSame("getLastName() doesn't work properly.", lastName, action.getLastName());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setLastName(String) method.
     * </p>
     * <p>
     * lastName should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetLastName() {
        String lastName = "test";
        action.setLastName(lastName);
        assertSame("setLastName() doesn't work properly.", lastName, action.getLastName());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getJobTitle() method.
     * </p>
     * <p>
     * jobTitle should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetJobTitle() {
        String jobTitle = "test";
        action.setJobTitle(jobTitle);
        assertSame("getJobTitle() doesn't work properly.", jobTitle, action.getJobTitle());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setJobTitle(String) method.
     * </p>
     * <p>
     * jobTitle should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetJobTitle() {
        String jobTitle = "test";
        action.setJobTitle(jobTitle);
        assertSame("setJobTitle() doesn't work properly.", jobTitle, action.getJobTitle());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getCompanyName() method.
     * </p>
     * <p>
     * companyName should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCompanyName() {
        String companyName = "test";
        action.setCompanyName(companyName);
        assertSame("getCompanyName() doesn't work properly.", companyName, action.getCompanyName());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setCompanyName(String) method.
     * </p>
     * <p>
     * companyName should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCompanyName() {
        String companyName = "test";
        action.setCompanyName(companyName);
        assertSame("setCompanyName() doesn't work properly.", companyName, action.getCompanyName());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getAge() method.
     * </p>
     * <p>
     * age should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetAge() {
        String age = "test";
        action.setAge(age);
        assertSame("getAge() doesn't work properly.", age, action.getAge());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setAge(String) method.
     * </p>
     * <p>
     * age should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetAge() {
        String age = "test";
        action.setAge(age);
        assertSame("setAge() doesn't work properly.", age, action.getAge());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getGender() method.
     * </p>
     * <p>
     * gender should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetGender() {
        String gender = "test";
        action.setGender(gender);
        assertSame("getGender() doesn't work properly.", gender, action.getGender());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setGender(String) method.
     * </p>
     * <p>
     * gender should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetGender() {
        String gender = "test";
        action.setGender(gender);
        assertSame("setGender() doesn't work properly.", gender, action.getGender());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getCurrentAddress1() method.
     * </p>
     * <p>
     * currentAddress1 should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCurrentAddress1() {
        String currentAddress1 = "test";
        action.setCurrentAddress1(currentAddress1);
        assertSame("getCurrentAddress1() doesn't work properly.", currentAddress1, action.getCurrentAddress1());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setCurrentAddress1(String) method.
     * </p>
     * <p>
     * currentAddress1 should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCurrentAddress1() {
        String currentAddress1 = "test";
        action.setCurrentAddress1(currentAddress1);
        assertSame("setCurrentAddress1() doesn't work properly.", currentAddress1, action.getCurrentAddress1());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getCurrentAddress2() method.
     * </p>
     * <p>
     * currentAddress2 should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCurrentAddress2() {
        String currentAddress2 = "test";
        action.setCurrentAddress2(currentAddress2);
        assertSame("getCurrentAddress2() doesn't work properly.", currentAddress2, action.getCurrentAddress2());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setCurrentAddress2(String) method.
     * </p>
     * <p>
     * currentAddress2 should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCurrentAddress2() {
        String currentAddress2 = "test";
        action.setCurrentAddress2(currentAddress2);
        assertSame("setCurrentAddress2() doesn't work properly.", currentAddress2, action.getCurrentAddress2());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getCurrentAddress3() method.
     * </p>
     * <p>
     * currentAddress3 should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCurrentAddress3() {
        String currentAddress3 = "test";
        action.setCurrentAddress3(currentAddress3);
        assertSame("getCurrentAddress3() doesn't work properly.", currentAddress3, action.getCurrentAddress3());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setCurrentAddress3(String) method.
     * </p>
     * <p>
     * currentAddress3 should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCurrentAddress3() {
        String currentAddress3 = "test";
        action.setCurrentAddress3(currentAddress3);
        assertSame("setCurrentAddress3() doesn't work properly.", currentAddress3, action.getCurrentAddress3());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getCity() method.
     * </p>
     * <p>
     * city should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCity() {
        String city = "test";
        action.setCity(city);
        assertSame("getCity() doesn't work properly.", city, action.getCity());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setCity(String) method.
     * </p>
     * <p>
     * city should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCity() {
        String city = "test";
        action.setCity(city);
        assertSame("setCity() doesn't work properly.", city, action.getCity());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getState() method.
     * </p>
     * <p>
     * state should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetState() {
        String state = "test";
        action.setState(state);
        assertSame("getState() doesn't work properly.", state, action.getState());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setState(String) method.
     * </p>
     * <p>
     * state should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetState() {
        String state = "test";
        action.setState(state);
        assertSame("setState() doesn't work properly.", state, action.getState());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getPostalCode() method.
     * </p>
     * <p>
     * postalCode should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetPostalCode() {
        String postalCode = "test";
        action.setPostalCode(postalCode);
        assertSame("getPostalCode() doesn't work properly.", postalCode, action.getPostalCode());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setPostalCode(String) method.
     * </p>
     * <p>
     * postalCode should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetPostalCode() {
        String postalCode = "test";
        action.setPostalCode(postalCode);
        assertSame("setPostalCode() doesn't work properly.", postalCode, action.getPostalCode());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getProvince() method.
     * </p>
     * <p>
     * province should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetProvince() {
        String province = "test";
        action.setProvince(province);
        assertSame("getProvince() doesn't work properly.", province, action.getProvince());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setProvince(String) method.
     * </p>
     * <p>
     * province should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetProvince() {
        String province = "test";
        action.setProvince(province);
        assertSame("setProvince() doesn't work properly.", province, action.getProvince());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getCountry() method.
     * </p>
     * <p>
     * country should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCountry() {
        String country = "test";
        action.setCountry(country);
        assertSame("getCountry() doesn't work properly.", country, action.getCountry());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setCountry(String) method.
     * </p>
     * <p>
     * country should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCountry() {
        String country = "test";
        action.setCountry(country);
        assertSame("setCountry() doesn't work properly.", country, action.getCountry());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getCountryToRepresent() method.
     * </p>
     * <p>
     * countryToRepresent should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCountryToRepresent() {
        String countryToRepresent = "test";
        action.setCountryToRepresent(countryToRepresent);
        assertSame("getCountryToRepresent() doesn't work properly.", countryToRepresent,
                action.getCountryToRepresent());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setCountryToRepresent(String) method.
     * </p>
     * <p>
     * countryToRepresent should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCountryToRepresent() {
        String countryToRepresent = "test";
        action.setCountryToRepresent(countryToRepresent);
        assertSame("setCountryToRepresent() doesn't work properly.", countryToRepresent,
                action.getCountryToRepresent());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getPhoneNumber() method.
     * </p>
     * <p>
     * phoneNumber should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetPhoneNumber() {
        String phoneNumber = "test";
        action.setPhoneNumber(phoneNumber);
        assertSame("getPhoneNumber() doesn't work properly.", phoneNumber, action.getPhoneNumber());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setPhoneNumber(String) method.
     * </p>
     * <p>
     * phoneNumber should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetPhoneNumber() {
        String phoneNumber = "test";
        action.setPhoneNumber(phoneNumber);
        assertSame("setPhoneNumber() doesn't work properly.", phoneNumber, action.getPhoneNumber());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getTimezone() method.
     * </p>
     * <p>
     * timezone should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetTimezone() {
        String timezone = "test";
        action.setTimezone(timezone);
        assertSame("getTimezone() doesn't work properly.", timezone, action.getTimezone());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setTimezone(String) method.
     * </p>
     * <p>
     * timezone should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetTimezone() {
        String timezone = "test";
        action.setTimezone(timezone);
        assertSame("setTimezone() doesn't work properly.", timezone, action.getTimezone());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getCoderTypeId() method.
     * </p>
     * <p>
     * coderTypeId should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCoderTypeId() {
        Integer coderTypeId = 1;
        action.setCoderTypeId(coderTypeId);
        assertSame("getCoderTypeId() doesn't work properly.", coderTypeId, action.getCoderTypeId());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setCoderTypeId(Integer) method.
     * </p>
     * <p>
     * coderTypeId should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCoderTypeId() {
        Integer coderTypeId = 1;
        action.setCoderTypeId(coderTypeId);
        assertSame("setCoderTypeId() doesn't work properly.", coderTypeId, action.getCoderTypeId());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getSecurityKey() method.
     * </p>
     * <p>
     * securityKey should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetSecurityKey() {
        String securityKey = "test";
        action.setSecurityKey(securityKey);
        assertSame("getSecurityKey() doesn't work properly.", securityKey, action.getSecurityKey());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setSecurityKey(String) method.
     * </p>
     * <p>
     * securityKey should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetSecurityKey() {
        String securityKey = "test";
        action.setSecurityKey(securityKey);
        assertSame("setSecurityKey() doesn't work properly.", securityKey, action.getSecurityKey());
    }
}