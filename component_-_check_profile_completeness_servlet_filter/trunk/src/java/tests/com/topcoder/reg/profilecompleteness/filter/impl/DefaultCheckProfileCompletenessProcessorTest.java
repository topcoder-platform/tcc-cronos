/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessConfigurationException;
import com.topcoder.reg.profilecompleteness.filter.CheckProfileCompletenessProcessorException;
import com.topcoder.reg.profilecompleteness.filter.MockLog;
import com.topcoder.reg.profilecompleteness.filter.TestHelper;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.User;

/**
 * Unit tests for <code>DefaultCheckProfileCompletenessProcessor</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class DefaultCheckProfileCompletenessProcessorTest extends TestCase {
    /**
     * Represents the user id used in tests.
     */
    private static final Long USER_ID = 123L;
    /**
     * Represents the instance of <code>User</code> used in tests.
     */
    private User user;
    /**
     * Represents the <code>DefaultCheckProfileCompletenessProcessor</code> instance used to test
     * against.
     */
    private DefaultCheckProfileCompletenessProcessor t;
    /**
     * Represents the instance of <code>HttpServletRequest</code> used in tests.
     */
    private MockHttpServletRequest request;
    /**
     * Represents the instance of <code>HttpServletResponse</code> used in tests.
     */
    private MockHttpServletResponse response;
    /**
     * Represents the instance of <code>FilterChain</code> used in tests.
     */
    private FilterChain chain;
    /**
     * Represents the field "completeDataURIs" used in tests.
     */
    private List<String> completeDataURIs;
    /**
     * Represents the field "matchUriPatterns" used in tests.
     */
    private List<String> matchUriPatterns;
    /**
     * Represents the field "profileCompletenessCheckers" used in tests.
     */
    private List<ProfileCompletenessChecker> profileCompletenessCheckers;
    /**
     * Represents the field "requestURISessionKey" used in tests.
     */
    private String requestURISessionKey;
    /**
     * Represents the instance of <code>UserDAO</code> used in tests.
     */
    private UserDAO userDAO;
    /**
     * Represents the instance of <code>UserIdRetriever</code> used in tests.
     */
    private UserIdRetriever userIdRetriever;
    /**
     * Represents the instance of <code>Log</code> used in tests.
     */
    private MockLog log;

    /**
     * Sets up the fixture. This method is called before a test is executed.
     *
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        chain = mock(FilterChain.class);
        user = new User();
        log = new MockLog();
        completeDataURIs = new ArrayList<String>();
        matchUriPatterns = new ArrayList<String>();
        profileCompletenessCheckers = new ArrayList<ProfileCompletenessChecker>();
        requestURISessionKey = "session_key";
        userDAO = mock(UserDAO.class);
        userIdRetriever = mock(UserIdRetriever.class);

        t = new DefaultCheckProfileCompletenessProcessor();
        t.setLog(log);
        t.setCompleteDataURIs(completeDataURIs);
        t.setMatchUriPatterns(matchUriPatterns);
        t.setProfileCompletenessCheckers(profileCompletenessCheckers);
        t.setRequestURISessionKey(requestURISessionKey);
        t.setUserDAO(userDAO);
        t.setUserIdRetriever(userIdRetriever);

    }

    /**
     * Tears down the fixture. This method is called after a test is executed.
     *
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void tearDown() throws Exception {
        request = null;
        response = null;
        chain = null;
        user = null;
        t = null;
        log = null;
        completeDataURIs = null;
        matchUriPatterns = null;
        profileCompletenessCheckers = null;
        requestURISessionKey = null;
        userDAO = null;
        userIdRetriever = null;
    }

    /**
     * Accuracy test for constructor <code>DefaultCheckProfileCompletenessProcessor()</code>.
     *
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     */
    public void testCtor_Accuracy() {
        assertNotNull("Instance should be created.", t);
    }

    /**
     * Accuracy test for <code>checkInitialization()</code>.
     *
     * No exception thrown if the fields have valid values after injecting.
     */
    public void testCheckInitialization_Accuracy() {
        t.checkInitialization();
        // no exception thrown
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If anything of following is true after injecting:
     * <ul>
     * <li>matchUriPatterns is null, contain null/empty elements or
     * <li>userIdRetriever is null or
     * <li>profileCompletenessCheckers is null, contain null elements or
     * <li>completeDataURIs is null, contain null/empty elements or
     * <li>userDAO is null or
     * <li>requestURISessionKey is null/empty or
     * <li>log is null or
     * <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     * <li>matchUriPatterns.size() != completeDataURIs.size() or
     * <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     * </ul>
     * , <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException1() {
        t.setMatchUriPatterns(null);
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If anything of following is true after injecting:
     * <ul>
     * <li>matchUriPatterns is null, contain null/empty elements or
     * <li>userIdRetriever is null or
     * <li>profileCompletenessCheckers is null, contain null elements or
     * <li>completeDataURIs is null, contain null/empty elements or
     * <li>userDAO is null or
     * <li>requestURISessionKey is null/empty or
     * <li>log is null or
     * <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     * <li>matchUriPatterns.size() != completeDataURIs.size() or
     * <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     * </ul>
     * , <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException2() {
        matchUriPatterns.add(null);
        t.setMatchUriPatterns(matchUriPatterns);
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If anything of following is true after injecting:
     * <ul>
     * <li>matchUriPatterns is null, contain null/empty elements or
     * <li>userIdRetriever is null or
     * <li>profileCompletenessCheckers is null, contain null elements or
     * <li>completeDataURIs is null, contain null/empty elements or
     * <li>userDAO is null or
     * <li>requestURISessionKey is null/empty or
     * <li>log is null or
     * <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     * <li>matchUriPatterns.size() != completeDataURIs.size() or
     * <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     * </ul>
     * , <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException3() {
        matchUriPatterns.add("  ");
        t.setMatchUriPatterns(matchUriPatterns);
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If anything of following is true after injecting:
     * <ul>
     * <li>matchUriPatterns is null, contain null/empty elements or
     * <li>userIdRetriever is null or
     * <li>profileCompletenessCheckers is null, contain null elements or
     * <li>completeDataURIs is null, contain null/empty elements or
     * <li>userDAO is null or
     * <li>requestURISessionKey is null/empty or
     * <li>log is null or
     * <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     * <li>matchUriPatterns.size() != completeDataURIs.size() or
     * <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     * </ul>
     * , <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException4() {
        t.setUserIdRetriever(null);
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If anything of following is true after injecting:
     * <ul>
     * <li>matchUriPatterns is null, contain null/empty elements or
     * <li>userIdRetriever is null or
     * <li>profileCompletenessCheckers is null, contain null elements or
     * <li>completeDataURIs is null, contain null/empty elements or
     * <li>userDAO is null or
     * <li>requestURISessionKey is null/empty or
     * <li>log is null or
     * <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     * <li>matchUriPatterns.size() != completeDataURIs.size() or
     * <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     * </ul>
     * , <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException5() {
        t.setProfileCompletenessCheckers(null);
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If anything of following is true after injecting:
     * <ul>
     * <li>matchUriPatterns is null, contain null/empty elements or
     * <li>userIdRetriever is null or
     * <li>profileCompletenessCheckers is null, contain null elements or
     * <li>completeDataURIs is null, contain null/empty elements or
     * <li>userDAO is null or
     * <li>requestURISessionKey is null/empty or
     * <li>log is null or
     * <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     * <li>matchUriPatterns.size() != completeDataURIs.size() or
     * <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     * </ul>
     * , <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException6() {
        profileCompletenessCheckers.add(null);
        t.setProfileCompletenessCheckers(profileCompletenessCheckers);
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If anything of following is true after injecting:
     * <ul>
     * <li>matchUriPatterns is null, contain null/empty elements or
     * <li>userIdRetriever is null or
     * <li>profileCompletenessCheckers is null, contain null elements or
     * <li>completeDataURIs is null, contain null/empty elements or
     * <li>userDAO is null or
     * <li>requestURISessionKey is null/empty or
     * <li>log is null or
     * <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     * <li>matchUriPatterns.size() != completeDataURIs.size() or
     * <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     * </ul>
     * , <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException7() {
        t.setCompleteDataURIs(null);
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If anything of following is true after injecting:
     * <ul>
     * <li>matchUriPatterns is null, contain null/empty elements or
     * <li>userIdRetriever is null or
     * <li>profileCompletenessCheckers is null, contain null elements or
     * <li>completeDataURIs is null, contain null/empty elements or
     * <li>userDAO is null or
     * <li>requestURISessionKey is null/empty or
     * <li>log is null or
     * <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     * <li>matchUriPatterns.size() != completeDataURIs.size() or
     * <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     * </ul>
     * , <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException8() {
        matchUriPatterns.add(null);
        t.setMatchUriPatterns(matchUriPatterns);
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If anything of following is true after injecting:
     * <ul>
     * <li>matchUriPatterns is null, contain null/empty elements or
     * <li>userIdRetriever is null or
     * <li>profileCompletenessCheckers is null, contain null elements or
     * <li>completeDataURIs is null, contain null/empty elements or
     * <li>userDAO is null or
     * <li>requestURISessionKey is null/empty or
     * <li>log is null or
     * <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     * <li>matchUriPatterns.size() != completeDataURIs.size() or
     * <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     * </ul>
     * , <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException9() {
        matchUriPatterns.add("  ");
        t.setMatchUriPatterns(matchUriPatterns);
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If anything of following is true after injecting:
     * <ul>
     * <li>matchUriPatterns is null, contain null/empty elements or
     * <li>userIdRetriever is null or
     * <li>profileCompletenessCheckers is null, contain null elements or
     * <li>completeDataURIs is null, contain null/empty elements or
     * <li>userDAO is null or
     * <li>requestURISessionKey is null/empty or
     * <li>log is null or
     * <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     * <li>matchUriPatterns.size() != completeDataURIs.size() or
     * <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     * </ul>
     * , <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException10() {
        t.setUserDAO(null);
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If anything of following is true after injecting:
     * <ul>
     * <li>matchUriPatterns is null, contain null/empty elements or
     * <li>userIdRetriever is null or
     * <li>profileCompletenessCheckers is null, contain null elements or
     * <li>completeDataURIs is null, contain null/empty elements or
     * <li>userDAO is null or
     * <li>requestURISessionKey is null/empty or
     * <li>log is null or
     * <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     * <li>matchUriPatterns.size() != completeDataURIs.size() or
     * <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     * </ul>
     * , <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException11() {
        t.setRequestURISessionKey(null);
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If anything of following is true after injecting:
     * <ul>
     * <li>matchUriPatterns is null, contain null/empty elements or
     * <li>userIdRetriever is null or
     * <li>profileCompletenessCheckers is null, contain null elements or
     * <li>completeDataURIs is null, contain null/empty elements or
     * <li>userDAO is null or
     * <li>requestURISessionKey is null/empty or
     * <li>log is null or
     * <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     * <li>matchUriPatterns.size() != completeDataURIs.size() or
     * <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     * </ul>
     * , <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException12() {
        t.setRequestURISessionKey("  ");
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If anything of following is true after injecting:
     * <ul>
     * <li>matchUriPatterns is null, contain null/empty elements or
     * <li>userIdRetriever is null or
     * <li>profileCompletenessCheckers is null, contain null elements or
     * <li>completeDataURIs is null, contain null/empty elements or
     * <li>userDAO is null or
     * <li>requestURISessionKey is null/empty or
     * <li>log is null or
     * <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     * <li>matchUriPatterns.size() != completeDataURIs.size() or
     * <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     * </ul>
     * , <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException14() {
        // 1 item
        matchUriPatterns.add("/context");
        t.setMatchUriPatterns(matchUriPatterns);
        // 2 items
        profileCompletenessCheckers.add(mock(ProfileCompletenessChecker.class));
        profileCompletenessCheckers.add(mock(ProfileCompletenessChecker.class));
        t.setProfileCompletenessCheckers(profileCompletenessCheckers);
        // 1 item
        completeDataURIs.add("/context_form");
        t.setCompleteDataURIs(completeDataURIs);

        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkInitialization()</code>.
     *
     * If anything of following is true after injecting:
     * <ul>
     * <li>matchUriPatterns is null, contain null/empty elements or
     * <li>userIdRetriever is null or
     * <li>profileCompletenessCheckers is null, contain null elements or
     * <li>completeDataURIs is null, contain null/empty elements or
     * <li>userDAO is null or
     * <li>requestURISessionKey is null/empty or
     * <li>log is null or
     * <li>matchUriPatterns.size() != profileCompletenessCheckers.size() or
     * <li>matchUriPatterns.size() != completeDataURIs.size() or
     * <li>profileCompletenessCheckers.size() != completeDataURIs.size()
     * </ul>
     * , <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckInitialization_Failure_CheckProfileCompletenessConfigurationException15() {
        // 1 item
        matchUriPatterns.add("/context");
        t.setMatchUriPatterns(matchUriPatterns);
        // 1 item
        profileCompletenessCheckers.add(mock(ProfileCompletenessChecker.class));
        t.setProfileCompletenessCheckers(profileCompletenessCheckers);
        // 2 items
        completeDataURIs.add("/context_form");
        completeDataURIs.add("/context2_form");
        t.setCompleteDataURIs(completeDataURIs);
        try {
            t.checkInitialization();
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Accuracy test for
     * <code>process(HttpServletRequest request, HttpServletResponse response, FilterChain chain)</code>
     * .
     *
     * If the user has incomplete profile for a url, it will be forward to corresponding page to
     * complete the information.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testProcess_Accuracy1() throws Exception {
        matchUriPatterns.add("/tc1");
        matchUriPatterns.add("/tc2"); // only this pattern will be used
        matchUriPatterns.add("/tc2");
        ProfileCompletenessChecker checker1 = mock(ProfileCompletenessChecker.class);
        ProfileCompletenessChecker checker2 = mock(ProfileCompletenessChecker.class);
        profileCompletenessCheckers.add(checker1);
        profileCompletenessCheckers.add(checker2);
        profileCompletenessCheckers.add(checker2);
        completeDataURIs.add("/tc1_form");
        completeDataURIs.add("/tc2_form");
        completeDataURIs.add("/tc2_form");

        when(userIdRetriever.getUserId(request)).thenReturn(USER_ID);
        when(userDAO.find(USER_ID)).thenReturn(user);
        when(checker2.isProfileComplete(user)).thenReturn(false);

        request.setRequestURI("/tc2");

        // process
        t.process(request, response, chain);

        verify(chain, never()).doFilter(request, response);
        assertEquals("/tc2", request.getSession().getAttribute(requestURISessionKey));
        assertEquals("/tc2_form", response.getForwardedUrl());

        // check the log
        String logMessage = log.getLogMessages();
        assertTrue(logMessage
            .contains("[Entering method {DefaultCheckProfileCompletenessProcessor.process}]"));
        assertTrue(logMessage
            .contains("[Exiting method {DefaultCheckProfileCompletenessProcessor.process}]"));
    }

    /**
     * Accuracy test for
     * <code>process(HttpServletRequest request, HttpServletResponse response, FilterChain chain)</code>
     * .
     *
     * If the user's profile is complete, then it will call <code>chain.doFilter(...)</code>.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testProcess_Accuracy2() throws Exception {
        matchUriPatterns.add("/tc1");
        ProfileCompletenessChecker checker1 = mock(ProfileCompletenessChecker.class);
        profileCompletenessCheckers.add(checker1);
        completeDataURIs.add("/tc1_form");

        when(userIdRetriever.getUserId(request)).thenReturn(USER_ID);
        when(userDAO.find(USER_ID)).thenReturn(user);
        when(checker1.isProfileComplete(user)).thenReturn(true);

        request.setRequestURI("/tc1");

        // process
        t.process(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
    }

    /**
     * Accuracy test for
     * <code>process(HttpServletRequest request, HttpServletResponse response, FilterChain chain)</code>
     * .
     *
     * If the user is not found, then it will call <code>chain.doFilter(...)</code>.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testProcess_Accuracy3() throws Exception {
        matchUriPatterns.add("/tc1");
        ProfileCompletenessChecker checker1 = mock(ProfileCompletenessChecker.class);
        profileCompletenessCheckers.add(checker1);
        completeDataURIs.add("/tc1_form");

        when(userIdRetriever.getUserId(request)).thenReturn(null);

        request.setRequestURI("/tc1");

        // process
        t.process(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
    }

    /**
     * Accuracy test for
     * <code>process(HttpServletRequest request, HttpServletResponse response, FilterChain chain)</code>
     * .
     *
     * If the request uri is not match any of the patterns, it will call
     * <code>chain.doFilter(...)</code>.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testProcess_Accuracy4() throws Exception {
        matchUriPatterns.add("/tc1");
        matchUriPatterns.add("/tc2");
        matchUriPatterns.add("/tc2");
        ProfileCompletenessChecker checker1 = mock(ProfileCompletenessChecker.class);
        ProfileCompletenessChecker checker2 = mock(ProfileCompletenessChecker.class);
        profileCompletenessCheckers.add(checker1);
        profileCompletenessCheckers.add(checker2);
        profileCompletenessCheckers.add(checker2);
        completeDataURIs.add("/tc1_form");
        completeDataURIs.add("/tc2_form");
        completeDataURIs.add("/tc2_form");

        when(userIdRetriever.getUserId(request)).thenReturn(USER_ID);
        when(userDAO.find(USER_ID)).thenReturn(user);
        when(checker2.isProfileComplete(user)).thenReturn(false);

        request.setRequestURI("/no_match_url");

        // process
        t.process(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        assertEquals(null, request.getSession().getAttribute(requestURISessionKey));
        assertEquals(null, response.getForwardedUrl());
    }

    /**
     * Failure test for method
     * <code>process(HttpServletRequest request, HttpServletResponse response, FilterChain chain)</code>
     * .
     *
     * If request is null, response is null, chain is null, <code>IllegalArgumentException</code>
     * will be thrown
     *
     * @throws Exception
     *             to jUnit
     */
    public void testProcess_Failure_IllegalArgumentException1() throws Exception {
        try {
            t.process(null, response, chain);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method
     * <code>process(HttpServletRequest request, HttpServletResponse response, FilterChain chain)</code>
     * .
     *
     * If request is null, response is null, chain is null, <code>IllegalArgumentException</code>
     * will be thrown
     *
     * @throws Exception
     *             to jUnit
     */
    public void testProcess_Failure_IllegalArgumentException2() throws Exception {
        try {
            t.process(request, null, chain);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method
     * <code>process(HttpServletRequest request, HttpServletResponse response, FilterChain chain)</code>
     * .
     *
     * If request is null, response is null, chain is null, <code>IllegalArgumentException</code>
     * will be thrown
     *
     * @throws Exception
     *             to jUnit
     */
    public void testProcess_Failure_IllegalArgumentException3() throws Exception {
        try {
            t.process(request, response, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method
     * <code>process(HttpServletRequest request, HttpServletResponse response, FilterChain chain)</code>
     * .
     *
     * If any exception occurred while processing the data,
     * <code>CheckProfileCompletenessProcessorException</code> will be thrown
     *
     * @throws Exception
     *             to jUnit
     */
    public void testProcess_Failure_CheckProfileCompletenessProcessorException1() throws Exception {
        matchUriPatterns.add("/tc1");
        ProfileCompletenessChecker checker1 = mock(ProfileCompletenessChecker.class);
        profileCompletenessCheckers.add(checker1);
        completeDataURIs.add("/tc1_form");

        when(userIdRetriever.getUserId(request)).thenReturn(null);
        // throws IOException
        doThrow(new IOException()).when(chain).doFilter(request, response);

        request.setRequestURI("/tc1");
        try {
            t.process(request, response, chain);
            fail("CheckProfileCompletenessProcessorException expected.");
        } catch (CheckProfileCompletenessProcessorException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method
     * <code>process(HttpServletRequest request, HttpServletResponse response, FilterChain chain)</code>
     * .
     *
     * If any exception occurred while processing the data,
     * <code>CheckProfileCompletenessProcessorException</code> will be thrown
     *
     * @throws Exception
     *             to jUnit
     */
    public void testProcess_Failure_CheckProfileCompletenessProcessorException2() throws Exception {
        matchUriPatterns.add("/tc1");
        ProfileCompletenessChecker checker1 = mock(ProfileCompletenessChecker.class);
        profileCompletenessCheckers.add(checker1);
        completeDataURIs.add("/tc1_form");

        when(userIdRetriever.getUserId(request)).thenReturn(null);
        // throws ServletException
        doThrow(new ServletException()).when(chain).doFilter(request, response);

        request.setRequestURI("/tc1");
        try {
            t.process(request, response, chain);
            fail("CheckProfileCompletenessProcessorException expected.");
        } catch (CheckProfileCompletenessProcessorException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method
     * <code>process(HttpServletRequest request, HttpServletResponse response, FilterChain chain)</code>
     * .
     *
     * If any exception occurred while processing the data,
     * <code>CheckProfileCompletenessProcessorException</code> will be thrown
     *
     * @throws Exception
     *             to jUnit
     */
    public void testProcess_Failure_CheckProfileCompletenessProcessorException3() throws Exception {
        matchUriPatterns.add("/tc1");
        ProfileCompletenessChecker checker1 = mock(ProfileCompletenessChecker.class);
        profileCompletenessCheckers.add(checker1);
        completeDataURIs.add("/tc1_form");

        when(userIdRetriever.getUserId(request)).thenReturn(USER_ID);
        when(userDAO.find(USER_ID)).thenReturn(user);
        when(checker1.isProfileComplete(user)).thenThrow(
            new ProfileCompletenessCheckerException("test"));

        request.setRequestURI("/tc1");
        try {
            t.process(request, response, chain);
            fail("CheckProfileCompletenessProcessorException expected.");
        } catch (CheckProfileCompletenessProcessorException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Accuracy test for <code>setMatchUriPatterns(List&lt;String&gt; matchUriPatterns)</code>.
     *
     * Field "matchUriPatterns" should be set correctly.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testSetMatchUriPatterns_Accuracy() throws Exception {
        t.setMatchUriPatterns(matchUriPatterns);
        assertEquals(matchUriPatterns, TestHelper.getField(t, "matchUriPatterns"));
    }

    /**
     * Accuracy test for <code>setUserIdRetriever(UserIdRetriever userIdRetriever)</code>.
     *
     * Field "userIdRetriever" should be set correctly.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testSetUserIdRetriever_Accuracy() throws Exception {
        t.setUserIdRetriever(userIdRetriever);
        assertEquals(userIdRetriever, TestHelper.getField(t, "userIdRetriever"));
    }

    /**
     * Accuracy test for
     * <code>setProfileCompletenessCheckers(List&lt;ProfileCompletenessChecker&gt; profileCompletenessCheckers)</code>
     * .
     *
     * Field "profileCompletenessCheckers" should be set correctly.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testSetProfileCompletenessCheckers_Accuracy() throws Exception {
        t.setProfileCompletenessCheckers(profileCompletenessCheckers);
        assertEquals(profileCompletenessCheckers,
            TestHelper.getField(t, "profileCompletenessCheckers"));
    }

    /**
     * Accuracy test for <code>setCompleteDataURIs(List&lt;String&gt; completeDataURIs)</code>.
     *
     * Field "completeDataURIs" should be set correctly.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testSetCompleteDataURIs_Accuracy() throws Exception {
        t.setCompleteDataURIs(completeDataURIs);
        assertEquals(completeDataURIs, TestHelper.getField(t, "completeDataURIs"));
    }

    /**
     * Accuracy test for <code>setUserDAO(UserDAO userDAO)</code>.
     *
     * Field "userDAO" should be set correctly.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testSetUserDAO_Accuracy() throws Exception {
        t.setUserDAO(userDAO);
        assertEquals(userDAO, TestHelper.getField(t, "userDAO"));
    }

    /**
     * Accuracy test for <code>setRequestURISessionKey(String requestURISessionKey)</code>.
     *
     * Field "requestURISessionKey" should be set correctly.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testSetRequestURISessionKey_Accuracy() throws Exception {
        t.setRequestURISessionKey(requestURISessionKey);
        assertEquals(requestURISessionKey, TestHelper.getField(t, "requestURISessionKey"));
    }

    /**
     * Accuracy test for <code>setLog(Log log)</code>.
     *
     * Field "log" should be set correctly.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testSetLog_Accuracy() throws Exception {
        t.setLog(log);
        assertEquals(log, TestHelper.getField(t, "log"));
    }
}
