/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.accuracytests;


import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.topcoder.web.memberphoto.manager.MemberPhotoManager;
import com.topcoder.web.memberphoto.servlet.MemberPhotoListServlet;

/**
 * <p>
 * This class contains all Failure tests for MemberPhotoListServlet.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class MemberPhotoListServletAccuracyTest extends BaseAccuracyTest {

    /**
     * <p>
     * Represents photo list servlet bean name in Spring context.
     * </p>
     */
    private static final String PHOTO_LIST_SERVLET_BEAN_NAME = "photoListServlet";

    /**
     * <p>
     * Represents Spring configuration file name.
     * </p>
     */
    private static final String MEMBER_PHOTO_LIST_CONFIG = "list.xml";

    /**
     * <p>
     * Represents photo list parameter name.
     * </p>
     */
    private static final String PHOTOLIST_PARAMETER_NAME = "photoListName";

    /**
     * An instance of <code>MemberPhotoListServlet</code> used in test.
     */
    private MemberPhotoListServlet servlet;

    /**
     * <p>
     * Represents MemberPhotoManager instance for testing.
     * </p>
     */
    private MemberPhotoManager memberPhotoManager;

    /**
     * <p>
     * The HttpServletRequest to be mocked.
     * </p>
     */
    private MockHttpServletRequest request = null;

    /**
     * <p>
     * The HttpSevletResponse to be mocked.
     * </p>
     */
    private MockHttpServletResponse response = null;

    /**
     * Set up for test test.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        servlet = AccuracyTestHelper.getServletBean(MEMBER_PHOTO_LIST_CONFIG, PHOTO_LIST_SERVLET_BEAN_NAME);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        memberPhotoManager = new MockMemberPhotoManager();
        servlet.setMemberPhotoManager(memberPhotoManager);
    }

    /**
     * Tear down for each test.
     * @throws Exception wraps all exception
     */
    protected void tearDown() throws Exception {
        request = null;
        response = null;
        servlet = null;
        memberPhotoManager = null;
        super.tearDown();
    }

    /**
     * <p>
     * Creates MockHttpServletRequest instance with all parameters needed for correct working of doPost() method.
     * </p>
     * @return MockHttpServletRequest instance with all parameters needed for correct working of doPost() method
     */
    private static MockHttpServletRequest createRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(IS_ADMIN_PARAMETER, true);
        request.setSession(session);
        request.setParameter(MEMBER_ID_PARAMETER_NAME, "1");
        return request;
    }

    /**
     * <p>
     * Tests MemberPhotoListServlet constructor.
     * </p>
     * <p>
     * MemberPhotoListServlet instance should be created successfully. No exception is exception.
     * </p>
     */
    public void testCtor() {
        assertNotNull("MemberPhotoListServlet instance should be created successfully.", servlet);
    }

    /**
     * <p>
     * Tests {@link MemberPhotoListServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Url should be redirected to successful url. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Success_NoPhotos() throws Exception {
        request = createRequest();
        request.setParameter(MEMBER_ID_PARAMETER_NAME, "1021");
        servlet.doPost(request, response);
        assertEquals("Url should be redirected successful.", SUCCESSFUL_RESULT_URL, response.getForwardedUrl());
    }

    /**
     * <p>
     * Tests {@link MemberPhotoListServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Url should be redirected to successful url. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Success_OnePhoto() throws Exception {
        request = createRequest();
        servlet.doPost(request, response);
        assertEquals("Url should be redirected successful.", SUCCESSFUL_RESULT_URL, response.getForwardedUrl());
        assertNotNull("photoListName attribute should be saved in request.", request
                .getAttribute(PHOTOLIST_PARAMETER_NAME));
    }

    /**
     * <p>
     * Tests {@link MemberPhotoListServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Page size has not valid value. Url should be redirected to successful url. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Success_PageSize() throws Exception {
        request = createRequest();
        request.setParameter("pageSizeParameterName", "Unknown");
        servlet.doPost(request, response);
        assertEquals("Url should be redirected successful.", SUCCESSFUL_RESULT_URL, response.getForwardedUrl());
    }

    /**
     * <p>
     * Tests {@link MemberPhotoListServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Page size has not valid value. Url should be redirected to successful url. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Success_PageNumber() throws Exception {
        request = createRequest();
        request.setParameter("pageNumberParameterName", "Unknown");
        servlet.doPost(request, response);
        assertEquals("Url should be redirected successful.", SUCCESSFUL_RESULT_URL, response.getForwardedUrl());
    }

    /**
     * <p>
     * Tests {@link MemberPhotoListServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Url should be redirected to error url. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Error_NotAdmin() throws Exception {
        request = createRequest();
        request.getSession().setAttribute(IS_ADMIN_PARAMETER, false);
        servlet.doPost(request, response);
        assertEquals("Url should be redirected error.", ERROR_RESULT_URL, response.getForwardedUrl());
    }

    /**
     * <p>
     * Tests {@link MemberPhotoListServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Url should be redirected to error url. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Error_NoAdmin() throws Exception {
        request = createRequest();
        request.setSession(new MockHttpSession());
        servlet.doPost(request, response);
        assertEquals("Url should be redirected error.", ERROR_RESULT_URL, response.getForwardedUrl());
    }
}
