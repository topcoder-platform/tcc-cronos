/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.profilecompleteness.actions.stresstests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.profilecompleteness.actions.CompleteProfileAction;
import com.topcoder.web.reg.profilecompleteness.actions.CompleteProfileActionException;

/**
 * <p>
 * Stress test cases for CompleteProfileAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class CompleteProfileActionStressTests extends TestCase {
    /**
     * <p>
     * This constant represents the test count used for testing.
     * </p>
     */
    private static final long NUMBER = 2000;

    /**
     * <p>
     * This constant represents the current time used for testing.
     * </p>
     */
    private long current = -1;

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
        return new TestSuite(CompleteProfileActionStressTests.class);
    }

    /**
     * <p>Tests CompleteProfileAction#getUserProfile() for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetUserProfile1() throws Exception {
        Thread[] threads = new Thread[(int) NUMBER];

        for (int i = 0; i < NUMBER; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    when(userIdRetriever.getUserId(request)).thenReturn(null);

                    String result = null;
                    try {
                        result = action.getUserProfile();
                    } catch (CompleteProfileActionException e) {
                        fail("CompleteProfileActionException expected.");
                    }

                    verify(userIdRetriever, times(1)).getUserId(request);
                    verify(userDAO, times(0)).find(1L);
                    verify(auditDAO, times(0)).audit(any(AuditRecord.class));
                    assertEquals("Failed to get user profile correctly.", "login", result);
                }

            });
        }

        start();

        for (int i = 0; i < NUMBER; i++) {
            threads[i].start();
        }

        for (int i = 0; i < NUMBER; i++) {
            threads[i].join();
        }

        this.printResult("CompleteProfileAction#getUserProfile()", NUMBER);
    }

    /**
     * <p>Tests CompleteProfileAction#getUserProfile() for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetUserProfile2() throws Exception {
        Thread[] threads = new Thread[(int) NUMBER];

        for (int i = 0; i < NUMBER; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    when(userIdRetriever.getUserId(request)).thenReturn(1L);
                    when(userDAO.find(1L)).thenReturn(new User());

                    String result = null;
                    try {
                        result = action.getUserProfile();
                    } catch (CompleteProfileActionException e) {
                        fail("CompleteProfileActionException expected.");
                    }

                    verify(userIdRetriever, times(1)).getUserId(request);
                    verify(userDAO, times(1)).find(1L);
                    verify(auditDAO, times(1)).audit(any(AuditRecord.class));
                    assertEquals("Failed to get user profile correctly.", "success", result);
                }

            });
        }

        start();

        for (int i = 0; i < NUMBER; i++) {
            threads[i].start();
        }

        for (int i = 0; i < NUMBER; i++) {
            threads[i].join();
        }

        this.printResult("CompleteProfileAction#getUserProfile()", NUMBER);
    }

    /**
     * <p>Tests CompleteProfileAction#saveUserProfile() for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testSaveUserProfile1() throws Exception {
        Thread[] threads = new Thread[(int) NUMBER];

        for (int i = 0; i < NUMBER; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    when(userIdRetriever.getUserId(request)).thenReturn(1L);
                    when(userDAO.find(1L)).thenReturn(new User());
                    when(countryDAO.find("country")).thenReturn(new Country());
                    when(request.getSession()).thenReturn(session);
                    when(session.getAttribute("incomingRequestURIKey")).thenReturn("/success.jsp");

                    String result = null;
                    try {
                        result = action.saveUserProfile();
                    } catch (CompleteProfileActionException e) {
                        fail("CompleteProfileActionException expected.");
                    }

                    verify(userIdRetriever, times(1)).getUserId(request);
                    verify(userDAO, times(1)).find(1L);
                    verify(request, times(1)).getSession();
                    verify(session, times(1)).getAttribute("incomingRequestURIKey");
                    assertNull("Failed to save user profile correctly.", result);
                }

            });
        }

        start();

        for (int i = 0; i < NUMBER; i++) {
            threads[i].start();
        }

        for (int i = 0; i < NUMBER; i++) {
            threads[i].join();
        }

        this.printResult("CompleteProfileAction#saveUserProfile()", NUMBER);
    }

    /**
     * <p>Tests CompleteProfileAction#saveUserProfile() for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testSaveUserProfile2() throws Exception {
        Thread[] threads = new Thread[(int) NUMBER];

        for (int i = 0; i < NUMBER; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    when(userIdRetriever.getUserId(request)).thenReturn(null);

                    String result = null;
                    try {
                        result = action.saveUserProfile();
                    } catch (CompleteProfileActionException e) {
                        fail("CompleteProfileActionException expected.");
                    }

                    verify(userIdRetriever, times(1)).getUserId(request);
                    verify(userDAO, times(0)).find(1L);
                    verify(request, times(0)).getSession();
                    verify(session, times(0)).getAttribute("incomingRequestURIKey");
                    assertEquals("Failed to save user profile correctly.", "login", result);
                }

            });
        }

        start();

        for (int i = 0; i < NUMBER; i++) {
            threads[i].start();
        }

        for (int i = 0; i < NUMBER; i++) {
            threads[i].join();
        }

        this.printResult("CompleteProfileAction#saveUserProfile()", NUMBER);
    }

    /**
     * <p>
     * Starts to count time.
     * </p>
     */
    private void start() {
        current = System.currentTimeMillis();
    }

    /**
     * <p>
     * Prints test result.
     * </p>
     *
     * @param name the test name
     * @param count the run count
     */
    private void printResult(String name, long count) {
        System.out.println("The test [" + name + "] run " + count + " times, took time: "
            + (System.currentTimeMillis() - current) + " ms");
    }
}