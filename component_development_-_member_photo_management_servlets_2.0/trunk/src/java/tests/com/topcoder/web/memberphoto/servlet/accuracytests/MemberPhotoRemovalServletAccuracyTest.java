/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.accuracytests;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.topcoder.web.memberphoto.manager.MemberPhotoManager;
import com.topcoder.web.memberphoto.servlet.MemberPhotoRemovalServlet;

/**
 * Accuracy test for class MemberPhotoRemovalServlet.
 * @author sokol
 * @version 1.0
 */
public class MemberPhotoRemovalServletAccuracyTest extends BaseAccuracyTest {

    /**
     * <p>
     * Represents removal configuration file name.
     * </p>
     */
    private static final String REMOVAL_CONFIG_FILE_NAME = "removal.xml";

    /**
     * <p>
     * Represents removal bean name.
     * </p>
     */
    private static final String REMOVAL_BEAN_NAME = "removalServlet";

    /**
     * <p>
     * Represents removal reason parameter name.
     * </p>
     */
    private static final String REMOVAL_REASON_PARAMETER_NAME = "removalReason";

    /**
     * <p>
     * Represents removal reason default value.
     * </p>
     */
    private static final String DEFAULT_REMOVAL_REASON = "security reason";

    /**
     * <p>
     * Represents removal directory.
     * </p>
     */
    private static final String REMOVAL_DIRECTORY = AccuracyTestHelper.BASE_PATH + "removal" + "/";

    /**
     * <p>
     * Represents id of member without images.
     * </p>
     */
    private static final String NO_IMAGES_MEMBER = "1031";

    /**
     * <p>
     * Represents request parameter name for member that has no photo.
     * </p>
     */
    private static final String MEMBER_HAS_NO_PHOTO_ERROR = "memberHasNoPhotoErrorName";

    /**
     * <p>
     * Represents directory with images to be removed.
     * </p>
     */
    private static final String IMAGES_DIRECTORY = AccuracyTestHelper.BASE_PATH + "images";

    /**
     * The MemberPhotoRemovalServlet instance for testing.
     */
    private MemberPhotoRemovalServlet servlet;

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
     * Sets up test environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        servlet =
                (MemberPhotoRemovalServlet) AccuracyTestHelper.getServletBean(REMOVAL_CONFIG_FILE_NAME,
                        REMOVAL_BEAN_NAME);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        memberPhotoManager = new MockMemberPhotoManager();
        servlet.setMemberPhotoManager(memberPhotoManager);
        // make removal directory
        new File(REMOVAL_DIRECTORY).mkdir();
        copyImages();
    }

    /**
     * <p>
     * Copies images from base dir to images.
     * </p>
     * @throws Exception if any error occurs
     */
    private void copyImages() throws Exception {
        File dir = new File(IMAGES_DIRECTORY);
        dir.mkdir();
        File baseDir = new File(AccuracyTestHelper.BASE_PATH);
        File[] files = baseDir.listFiles(new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".png");
            }
        });
        for (File file : files) {
            copyFile(file, dir.getAbsolutePath());
        }
    }

    /**
     * <p>
     * Copies given file to given directory.
     * </p>
     * @param file the file to be copied
     * @param dir the directory where file should be copied
     * @throws Exception if any error occurs
     */
    private void copyFile(File file, String dir) throws Exception {
        String outputFile = dir + "/" + file.getName();
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
        int size = 4096;
        byte data[] = new byte[size];
        try {
            while (inputStream.read(data) > 0) {
                outputStream.write(data);
            }
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                // ignore
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * Tears down test environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        servlet = null;
        request = null;
        response = null;
        memberPhotoManager = null;
        AccuracyTestHelper.deletedir(REMOVAL_DIRECTORY);
        AccuracyTestHelper.deletedir(IMAGES_DIRECTORY);
    }

    /**
     * <p>
     * Tests {@link MemberPhotoRemovalServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Url should be redirected to successful. Image should be removed and placed into image removed directory. No
     * exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Success() throws Exception {
        long memberId = 1;
        request = createRequest();
        servlet.doPost(request, response);
        assertEquals("Url should be successful.", SUCCESSFUL_RESULT_URL, response.getForwardedUrl());
   }

    /**
     * <p>
     * Tests {@link MemberPhotoRemovalServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Url should be redirected to successful and mail should be sent. Image should be removed and placed into image
     * removed directory. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Email() throws Exception {
        request = createRequest();
        servlet.setEmailSendFlag(true);
        servlet.doPost(request, response);
        assertEquals("Url should be successful.", SUCCESSFUL_RESULT_URL, response.getForwardedUrl());
    }

    /**
     * <p>
     * Tests {@link MemberPhotoRemovalServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Url should be redirected to error. Image should be removed and placed into image removed directory. No exception
     * is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Error_NotAdmin() throws Exception {
        request = createRequest();
        request.getSession().setAttribute(IS_ADMIN_PARAMETER, false);
        servlet.doPost(request, response);
        assertEquals("Url should be successful.", ERROR_RESULT_URL, response.getForwardedUrl());
        assertNotNull("Not administrator error should be placed into request attributes.", request
                .getAttribute("notAdministratorErrorName"));
    }

    /**
     * <p>
     * Tests {@link MemberPhotoRemovalServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Url should be redirected to error. Image should be removed and placed into image removed directory. No exception
     * is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Error_NoAdmin() throws Exception {
        request = createRequest();
        request.setSession(new MockHttpSession());
        servlet.doPost(request, response);
        assertEquals("Url should be successful.", ERROR_RESULT_URL, response.getForwardedUrl());
        assertNotNull("Not administrator error should be placed into request attributes.", request
                .getAttribute("notAdministratorErrorName"));
    }

    /**
     * <p>
     * Tests {@link MemberPhotoRemovalServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Url should be redirected to error. Image should be removed and placed into image removed directory. No exception
     * is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Error_NoImage() throws Exception {
        request = createRequest();
        request.setParameter(MEMBER_ID_PARAMETER_NAME, NO_IMAGES_MEMBER);
        servlet.doPost(request, response);
        assertEquals("Url should be successful.", ERROR_RESULT_URL, response.getForwardedUrl());
        assertNotNull("Member has no photo error should be placed to request attributes.", request
                .getAttribute(MEMBER_HAS_NO_PHOTO_ERROR));
    }

    /**
     * <p>
     * Creates MockHttpServletRequest request with default values.
     * </p>
     * @return MockHttpServletRequest request with default values
     */
    private static MockHttpServletRequest createRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(IS_ADMIN_PARAMETER, true);
        request.setSession(session);
        request.setParameter(MEMBER_ID_PARAMETER_NAME, "1");
        request.setParameter(REMOVAL_REASON_PARAMETER_NAME, DEFAULT_REMOVAL_REASON);
        return request;
    }
}
