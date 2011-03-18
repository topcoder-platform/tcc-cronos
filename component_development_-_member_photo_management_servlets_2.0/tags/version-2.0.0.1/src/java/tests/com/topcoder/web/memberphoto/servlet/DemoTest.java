/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import java.io.File;

/**
 * <p>
 * Demo class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest extends TestCase {
    /**
     * <p>
     * Constant for test files location.
     * </p>
     */
    private static final String TEST_FILES = "test_files/";

    static {
        ConfigManager configManager = ConfigManager.getInstance();

        try {
            configManager.add("test_files/demoConfig.xml");
            configManager.add("test_files/MimeTypeAndFileExtensionValidator.xml");
        } catch (ConfigManagerException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(DemoTest.class);
    }

    /**
     * <p>
     * Set up the environment.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
    }

    /**
     * <p>
     * Destroy the environment.
     * </p>
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.deldir(TEST_FILES + "submitted");
        TestHelper.deldir(TEST_FILES + "preview");
        TestHelper.deldir(TEST_FILES + "crop");
        TestHelper.shutDownRunner();
    }

    /**
     * <p>
     * Accuracy test for method <code>MemberPhotoUploadServletTest#doPost(request,response)</code>.
     * </p>
     * <p>
     * Verify that can handle the request and response correctly.
     * </p>
     * <p>
     * submitted flag is true, use 'submitted' directory.
     * </p>
     * @throws Exception to JUnit
     */
    public void testMemberPhotoUploadServletTestDoPostDemo() throws Exception {
        MemberPhotoUploadServlet bean = TestHelper.genUploadServletBean();
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setParameter("submit_action", "commit");
        request.setParameter("member_id", "717");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);
        bean.doPost(request, response);
        assertTrue("File should exist.", new File(TEST_FILES + "submitted/717.gif").exists());
        assertNull("forward url should be null.", response.getForwardedUrl());
    }

    /**
     * <p>
     * Accuracy test for method <code>MemberPhotoRemovalServletTest#doPost(request,response)</code>.
     * </p>
     * <p>
     * Verify that can handle the request and response correctly.
     * </p>
     * <p>
     * forward to successful URL.
     * </p>
     * @throws Exception to JUnit
     */
    public void testMemberPhotoRemovalServletTestDoPostDemo() throws Exception {
        MemberPhotoRemovalServlet bean = TestHelper.genRemovalServletBean();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        try {
            bean.doPost(request, response);
            assertEquals("forward url should be the same.",
                    "success_result_forward_url", response.getForwardedUrl());
        } catch (MemberPhotoRemovalException e) {
            // this is an accuracy test for the servlet
            // I use try-catch here because I am afraid that you did not use my
            // mock EmailEngine
            // actually the catch block will not reached if you use my mock
            // EmailEngine.
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>MemberPhotoListServletTest#doPost(request,response)</code>.
     * </p>
     * <p>
     * All right
     * </p>
     * @throws Exception to JUnit
     */
    public void testMemberPhotoListServletTestDoPostDemo() throws Exception {
        MemberPhotoListServlet bean = TestHelper.genListServletBean();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");
        request.setParameter("page_number_parameter_name", "0");
        request.setParameter("page_size_parameter_name", "50");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", new Boolean(true));
        request.setSession(session);

        bean.doPost(request, response);
        assertEquals("forward url should be the same.",
                "success_result_forward_url", response.getForwardedUrl());
    }
}
