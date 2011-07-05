/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.profilecompleteness.actions.accuracytests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

import com.topcoder.reg.profilecompleteness.filter.impl.UserIdRetriever;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.CoderTypeDAO;
import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.DemographicQuestionDAO;
import com.topcoder.web.common.dao.StateDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.State;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.profilecompleteness.actions.CompleteProfileAction;

/**
 * <p>
 * Accuracy Unit test cases for CompleteProfileAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class CompleteProfileActionAccuracyTests extends TestCase {
    /**
     * <p>CompleteProfileAction instance for helping test.</p>
     */
    private CompleteProfileAction action;

    /**
     * <p>HttpServletRequest instance for helping test.</p>
     */
    private HttpServletRequest request;

    /**
     * <p>HttpServletResponse instance for helping test.</p>
     */
    private HttpServletResponse response;

    /**
     * <p>UserDAO instance for helping test.</p>
     */
    private UserDAO userDAO;

    /**
     * <p>AuditDAO instance for helping test.</p>
     */
    private AuditDAO auditDAO;

    /**
     * <p>UserIdRetriever instance for helping test.</p>
     */
    private UserIdRetriever userIdRetriever;

    /**
     * <p>CoderTypeDAO instance for helping test.</p>
     */
    private CoderTypeDAO coderTypeDAO;

    /**
     * <p>TimeZoneDAO instance for helping test.</p>
     */
    private TimeZoneDAO timeZoneDAO;

    /**
     * <p>StateDAO instance for helping test.</p>
     */
    private StateDAO stateDAO;

    /**
     * <p>DemographicQuestionDAO instance for helping test.</p>
     */
    private DemographicQuestionDAO demographicQuestionDAO;

    /**
     * <p>CountryDAO instance for helping test.</p>
     */
    private CountryDAO countryDAO;

    /**
     * <p>HttpSession instance for helping test.</p>
     */
    private HttpSession session;

    /**
     * <p>Setup test environment.</p>
     *
     */
    protected void setUp() {
        action = new CompleteProfileAction();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        userDAO = mock(UserDAO.class);
        auditDAO = mock(AuditDAO.class);
        userIdRetriever = mock(UserIdRetriever.class);
        coderTypeDAO = mock(CoderTypeDAO.class);
        timeZoneDAO = mock(TimeZoneDAO.class);
        stateDAO = mock(StateDAO.class);
        demographicQuestionDAO = mock(DemographicQuestionDAO.class);
        countryDAO = mock(CountryDAO.class);
        session = mock(HttpSession.class);

        action.setPhoneTypeId(1);
        action.setSecurityKeyTypeId(2);
        action.setGenderQuestionId(3);
        action.setAgeQuestionId(4);
        action.setCountry("country");
        action.setIncomingRequestURIKey("incomingRequestURIKey");
        action.setGetUserProfileOperationType("getUserProfileOperationType");
        action.setIncomingRequestURIKey("incomingRequestURIKey");
        action.setSaveUserProfileOperationType("saveUserProfileOperationType");
        action.setUserDAO(userDAO);
        action.setAuditDAO(auditDAO);
        action.setUserIdRetriever(userIdRetriever);
        action.setCoderTypeDAO(coderTypeDAO);
        action.setTimeZoneDAO(timeZoneDAO);
        action.setStateDAO(stateDAO);
        action.setDemographicQuestionDAO(demographicQuestionDAO);
        action.setCountryDAO(countryDAO);
        action.setServletRequest(request);
        action.setServletResponse(response);
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(CompleteProfileActionAccuracyTests.class);
    }

    /**
     * <p>Tests ctor CompleteProfileAction#CompleteProfileAction() for accuracy.</p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create CompleteProfileAction instance.", action);
    }

    /**
     * <p>Tests CompleteProfileAction#getUserProfile() for accuracy.</p>
     * @throws Exception to JUnit
     */
    public void testGetUserProfile1() throws Exception {
        when(userIdRetriever.getUserId(request)).thenReturn(1L);
        when(userDAO.find(1L)).thenReturn(new User());

        String result = action.getUserProfile();

        verify(userIdRetriever, times(1)).getUserId(request);
        verify(userDAO, times(1)).find(1L);
        verify(auditDAO, times(1)).audit(any(AuditRecord.class));
        assertEquals("Failed to get user profile correctly.", "success", result);
    }

    /**
     * <p>Tests CompleteProfileAction#getUserProfile() for accuracy.</p>
     * @throws Exception to JUnit
     */
    public void testGetUserProfile2() throws Exception {
        when(userIdRetriever.getUserId(request)).thenReturn(null);

        String result = action.getUserProfile();

        verify(userIdRetriever, times(1)).getUserId(request);
        verify(userDAO, times(0)).find(1L);
        verify(auditDAO, times(0)).audit(any(AuditRecord.class));
        assertEquals("Failed to get user profile correctly.", "login", result);
    }

    /**
     * <p>Tests CompleteProfileAction#saveUserProfile() for accuracy.</p>
     * @throws Exception to JUnit
     */
    public void testSaveUserProfile1() throws Exception {
        when(userIdRetriever.getUserId(request)).thenReturn(1L);
        when(userDAO.find(1L)).thenReturn(new User());
        when(countryDAO.find("country")).thenReturn(new Country());
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("incomingRequestURIKey")).thenReturn("/success.jsp");

        String result = action.saveUserProfile();

        verify(userIdRetriever, times(1)).getUserId(request);
        verify(userDAO, times(1)).find(1L);
        verify(request, times(1)).getSession();
        verify(session, times(1)).getAttribute("incomingRequestURIKey");
        assertNull("Failed to save user profile correctly.", result);
    }

    /**
     * <p>Tests CompleteProfileAction#saveUserProfile() for accuracy.</p>
     * @throws Exception to JUnit
     */
    public void testSaveUserProfile2() throws Exception {
        when(userIdRetriever.getUserId(request)).thenReturn(null);

        String result = action.saveUserProfile();

        verify(userIdRetriever, times(1)).getUserId(request);
        verify(userDAO, times(0)).find(1L);
        verify(request, times(0)).getSession();
        verify(session, times(0)).getAttribute("incomingRequestURIKey");
        assertEquals("Failed to save user profile correctly.", "login", result);
    }

    /**
     * <p>Tests CompleteProfileAction#setSecurityKey(String) for accuracy.</p>
     */
    public void testSetSecurityKey() {
        action.setSecurityKey("securityKey");
        assertEquals("Failed to setSecurityKey correctly.", "securityKey", action.getSecurityKey());
    }

    /**
     * <p>Tests CompleteProfileAction#getUser() for accuracy.</p>
     */
    public void testGetUser() {
        assertNull("Failed to getUser correctly.", action.getUser());
    }

    /**
     * <p>Tests CompleteProfileAction#getFirstName() for accuracy.</p>
     */
    public void testGetFirstName() {
        action.setFirstName("firstName");
        assertEquals("Failed to getFirstName correctly.", "firstName", action.getFirstName());
    }

    /**
     * <p>Tests CompleteProfileAction#getLastName() for accuracy.</p>
     */
    public void testGetLastName() {
        action.setLastName("lastName");
        assertEquals("Failed to getLastName correctly.", "lastName", action.getLastName());
    }

    /**
     * <p>Tests CompleteProfileAction#getJobTitle() for accuracy.</p>
     */
    public void testGetJobTitle() {
        action.setJobTitle("jobTitle");
        assertEquals("Failed to getJobTitle correctly.", "jobTitle", action.getJobTitle());
    }

    /**
     * <p>Tests CompleteProfileAction#getCompanyName() for accuracy.</p>
     */
    public void testGetCompanyName() {
        action.setCompanyName("companyName");
        assertEquals("Failed to getCompanyName correctly.", "companyName", action.getCompanyName());
    }

    /**
     * <p>Tests CompleteProfileAction#getAge() for accuracy.</p>
     */
    public void testGetAge() {
        action.setAge("10");
        assertEquals("Failed to getAge correctly.", "10", action.getAge());
    }

    /**
     * <p>Tests CompleteProfileAction#getGender() for accuracy.</p>
     */
    public void testGetGender() {
        action.setGender("gender");
        assertEquals("Failed to getGender correctly.", "gender", action.getGender());
    }

    /**
     * <p>Tests CompleteProfileAction#getCurrentAddress1() for accuracy.</p>
     */
    public void testGetCurrentAddress1() {
        action.setCurrentAddress1("currentAddress1");
        assertEquals("Failed to getCurrentAddress1 correctly.", "currentAddress1", action.getCurrentAddress1());
    }

    /**
     * <p>Tests CompleteProfileAction#getCurrentAddress2() for accuracy.</p>
     */
    public void testGetCurrentAddress2() {
        action.setCurrentAddress2("currentAddress2");
        assertEquals("Failed to getCurrentAddress2 correctly.", "currentAddress2", action.getCurrentAddress2());
    }

    /**
     * <p>Tests CompleteProfileAction#getCurrentAddress3() for accuracy.</p>
     */
    public void testGetCurrentAddress3() {
        action.setCurrentAddress3("currentAddress3");
        assertEquals("Failed to getCurrentAddress3 correctly.", "currentAddress3", action.getCurrentAddress3());
    }

    /**
     * <p>Tests CompleteProfileAction#getCity() for accuracy.</p>
     */
    public void testGetCity() {
        action.setCity("city");
        assertEquals("Failed to getCity correctly.", "city", action.getCity());
    }

    /**
     * <p>Tests CompleteProfileAction#getPostalCode() for accuracy.</p>
     */
    public void testGetPostalCode() {
        action.setPostalCode("500000");
        assertEquals("Failed to getPostalCode correctly.", "500000", action.getPostalCode());
    }

    /**
     * <p>Tests CompleteProfileAction#getProvince() for accuracy.</p>
     */
    public void testGetProvince() {
        action.setProvince("province");
        assertEquals("Failed to getProvince correctly.", "province", action.getProvince());
    }

    /**
     * <p>Tests CompleteProfileAction#getCountryToRepresent() for accuracy.</p>
     */
    public void testGetCountryToRepresent() {
        action.setCountryToRepresent("countryToRepresent");
        assertEquals("Failed to getCountryToRepresent correctly.", "countryToRepresent", action.getCountryToRepresent());
    }

    /**
     * <p>Tests CompleteProfileAction#getPhoneNumber() for accuracy.</p>
     */
    public void testGetPhoneNumber() {
        action.setPhoneNumber("phoneNumber");
        assertEquals("Failed to getPhoneNumber correctly.", "phoneNumber", action.getPhoneNumber());
    }

    /**
     * <p>Tests CompleteProfileAction#getTimezone() for accuracy.</p>
     */
    public void testGetTimezone() {
        action.setTimezone("timezone");
        assertEquals("Failed to getTimezone correctly.", "timezone", action.getTimezone());
    }

    /**
     * <p>Tests CompleteProfileAction#getCoderTypeId() for accuracy.</p>
     */
    public void testGetCoderTypeId() {
        action.setCoderTypeId(new Integer(10));
        assertEquals("Failed to getCoderTypeId correctly.", 10, action.getCoderTypeId().intValue());
    }

    /**
     * <p>Tests CompleteProfileAction#getState() for accuracy.</p>
     */
    public void testGetState() {
        action.setState("state");
        assertEquals("Failed to getState correctly.", "state", action.getState());
    }

    /**
     * <p>Tests CompleteProfileAction#getCountry() for accuracy.</p>
     */
    public void testGetCountry() {
        assertEquals("Failed to getCountry correctly.", "country", action.getCountry());
    }

    /**
     * <p>Tests CompleteProfileAction#validate() for accuracy.</p>
     */
    public void testValidate() {
        action.setState("state");
        when(stateDAO.find("state")).thenReturn(new State());

        action.validate();

        verify(stateDAO, times(1)).find("state");
    }

}