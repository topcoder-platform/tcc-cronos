/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.accuracytests;

import java.io.File;

import javax.imageio.ImageIO;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.topcoder.web.memberphoto.servlet.MemberPhotoUploadServlet;

/**
 * Accuracy test for class MemberPhotoUploadServlet.
 * @author extra, sokol
 * @version 2.0
 */
public class MemberPhotoUploadServletAccuracyTest extends BaseAccuracyTest {

    /**
     * <p>
     * Represents preview photo image path name.
     * </p>
     */
    private static final String PREVIEW_PHOTO_IMAGE_PATH_NAME = "previewPhotoImagePathName";

    /**
     * <p>
     * Represents JPEG file extension.
     * </p>
     */
    private static final String JPEG_EXTENSION = ".jpeg";

    /**
     * <p>
     * Represents PNG file extension.
     * </p>
     */
    private static final String PNG_EXTENSION = ".png";

    /**
     * <p>
     * Represents GIF file extension.
     * </p>
     */
    private static final String GIF_EXTENSION = ".gif";

    /**
     * <p>
     * Represents submitted action parameter name.
     * </p>
     */
    private static final String SUBMITTED_ACTION_PARAMETER_NAME = "submittedActionParameterName";

    /**
     * <p>
     * Represents member photo upload configuration file name.
     * </p>
     */
    private static final String MEMBER_PHOTO_UPLOAD_CONFIG_NAME = "upload.xml";

    /**
     * <p>
     * Represents member photo upload bean name.
     * </p>
     */
    private static final String MEMBER_PHOTO_UPLOAD_BEAN_NAME = "uploadServlet";

    /**
     * <p>
     * Represents directory for submitted images.
     * </p>
     */
    private static final String SUBMITTED_DIRECTORY = AccuracyTestHelper.BASE_PATH + "submitted" + "/";

    /**
     * <p>
     * Represents directory for cropped images.
     * </p>
     */
    private static final String CROP_DIRECTORY = AccuracyTestHelper.BASE_PATH + "crop" + "/";

    /**
     * <p>
     * Represents directory for preview images.
     * </p>
     */
    private static final String PREVIEW_DIRECTORY = AccuracyTestHelper.BASE_PATH + "preview" + "/";

    /**
     * <p>
     * Represents preview forward url.
     * </p>
     */
    private static final String PREVIEW_FORWARD_URL = "/preview";

    /**
     * <p>
     * Represents submit redirected url.
     * </p>
     */
    private static final String SUBMIT_REDIRECTED_URL = "/submitted?1";

    /**
     * The MemberPhotoUploadServlet instance for testing.
     */
    private MemberPhotoUploadServlet servlet;

    /**
     * Sets up test environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        servlet = AccuracyTestHelper.getServletBean(MEMBER_PHOTO_UPLOAD_CONFIG_NAME, MEMBER_PHOTO_UPLOAD_BEAN_NAME);
        AccuracyTestHelper.clearNameSpaces();
        AccuracyTestHelper.addConfigFile("fileUploadConfig.xml");
        AccuracyTestHelper.addConfigFile("validator.xml");
        super.setUp();
    }

    /**
     * Tears down test environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.clearNameSpaces();
        AccuracyTestHelper.deletedir(SUBMITTED_DIRECTORY);
        AccuracyTestHelper.deletedir(PREVIEW_DIRECTORY);
        AccuracyTestHelper.deletedir(CROP_DIRECTORY);
        super.tearDown();
    }

    /**
     * <p>
     * Tests {@link MemberPhotoUploadServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Action is "commit". Image should be cropped and stored in submitted directory. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Commit() throws Exception {
        Long memberId = 1L;
        MockHttpServletRequest request = AccuracyTestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        populateRequest(request);
        servlet.doPost(request, response);
        assertTrue("File should exist.", new File(SUBMITTED_DIRECTORY + memberId + JPEG_EXTENSION).exists());
        assertEquals("Url should be redirected successfully.", SUBMIT_REDIRECTED_URL, response.getRedirectedUrl());
        assertNotNull("Image should be read successfully.", ImageIO.read(new File(SUBMITTED_DIRECTORY + memberId
                + JPEG_EXTENSION)));
    }

    /**
     * <p>
     * Tests {@link MemberPhotoUploadServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Action is "preview". Image should be stored in preview directory. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Preview() throws Exception {
        Long memberId = 1L;
        MockHttpServletRequest request = AccuracyTestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        populateRequest(request);
        request.setParameter(SUBMITTED_ACTION_PARAMETER_NAME, "preview");
        servlet.doPost(request, response);
        String fileName = PREVIEW_DIRECTORY + memberId + JPEG_EXTENSION;
        assertTrue("File should exist.", new File(fileName).exists());
        assertEquals("Url should be redirected successfully.", PREVIEW_FORWARD_URL, response.getForwardedUrl());
        assertNotNull("Image should be read successfully.", ImageIO.read(new File(fileName)));
        assertNotNull("previewPhotoImagePathName should be placed to request.", request
                .getAttribute(PREVIEW_PHOTO_IMAGE_PATH_NAME));
    }

    /**
     * <p>
     * Tests {@link MemberPhotoUploadServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Action is "Unknown", so "preview" should be used. Image should be stored in preview directory. No exception is
     * expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_UnknownAction() throws Exception {
        Long memberId = 1L;
        MockHttpServletRequest request = AccuracyTestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        populateRequest(request);
        request.setParameter(SUBMITTED_ACTION_PARAMETER_NAME, "Unknown");
        servlet.doPost(request, response);
        String fileName = PREVIEW_DIRECTORY + memberId + JPEG_EXTENSION;
        assertTrue("File should exist.", new File(fileName).exists());
        assertEquals("Url should be redirected successfully.", PREVIEW_FORWARD_URL, response.getForwardedUrl());
        assertNotNull("Image should be read successfully.", ImageIO.read(new File(fileName)));
    }

    /**
     * <p>
     * Tests {@link MemberPhotoUploadServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * There is no action, so "preview" should be used. Image should be stored in preview directory. No exception is
     * expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_NoAction() throws Exception {
        Long memberId = 1L;
        MockHttpServletRequest request = AccuracyTestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        populateRequest(request);
        request.removeParameter(SUBMITTED_ACTION_PARAMETER_NAME);
        servlet.doPost(request, response);
        String fileName = PREVIEW_DIRECTORY + memberId + JPEG_EXTENSION;
        assertTrue("File should exist.", new File(fileName).exists());
        assertEquals("Url should be redirected successfully.", PREVIEW_FORWARD_URL, response.getForwardedUrl());
        assertNotNull("Image should be read successfully.", ImageIO.read(new File(fileName)));
    }

    /**
     * <p>
     * Tests {@link MemberPhotoUploadServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Action is "commit". Image should be cropped and stored in submitted directory. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Commit_GIF() throws Exception {
        Long memberId = 1L;
        MockHttpServletRequest request = AccuracyTestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        populateRequest(request);
        servlet.setPhotoImageFormFileName("accuracytests/topcoder" + GIF_EXTENSION);
        servlet.doPost(request, response);
        String fileName = SUBMITTED_DIRECTORY + memberId + GIF_EXTENSION;
        assertTrue("File should exist.", new File(fileName).exists());
        assertEquals("Url should be redirected successfully.", SUBMIT_REDIRECTED_URL, response.getRedirectedUrl());
        //assertNotNull("Image should be read successfully.", ImageIO.read(new File(fileName)));
    }

    /**
     * <p>
     * Tests {@link MemberPhotoUploadServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Action is "crop". Image should be cropped and stored in cropped directory. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Crop_GIF() throws Exception {
        Long memberId = 1L;
        MockHttpServletRequest request = AccuracyTestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        populateRequest(request);
        request.setParameter(SUBMITTED_ACTION_PARAMETER_NAME, "crop");
        servlet.setPhotoImageFormFileName("accuracytests/topcoder" + GIF_EXTENSION);
        servlet.doPost(request, response);
        String fileName = CROP_DIRECTORY + memberId + GIF_EXTENSION;
        assertTrue("File should exist.", new File(fileName).exists());
        assertEquals("Url should be redirected successfully.", SUBMIT_REDIRECTED_URL, response.getRedirectedUrl());
        //assertNotNull("Image should be read successfully.", ImageIO.read(new File(fileName)));
    }

    /**
     * <p>
     * Tests {@link MemberPhotoUploadServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Action is "crop". Image should be cropped and stored in cropped directory. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Preview_GIF() throws Exception {
        Long memberId = 1L;
        MockHttpServletRequest request = AccuracyTestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        populateRequest(request);
        request.setParameter(SUBMITTED_ACTION_PARAMETER_NAME, "preview");
        servlet.setPhotoImageFormFileName("accuracytests/topcoder" + GIF_EXTENSION);
        servlet.doPost(request, response);
        String fileName = PREVIEW_DIRECTORY + memberId + GIF_EXTENSION;
        assertTrue("File should exist.", new File(fileName).exists());
        assertEquals("Url should be redirected successfully.", PREVIEW_FORWARD_URL, response.getForwardedUrl());
        //assertNotNull("Image should be read successfully.", ImageIO.read(new File(fileName)));
        assertNotNull("previewPhotoImagePathName should be placed to request.", request
                .getAttribute(PREVIEW_PHOTO_IMAGE_PATH_NAME));
    }

    /**
     * <p>
     * Tests {@link MemberPhotoUploadServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Action is "commit". Image should be cropped and stored in submitted directory. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Commit_PNG() throws Exception {
        Long memberId = 1L;
        MockHttpServletRequest request = AccuracyTestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        populateRequest(request);
        servlet.setPhotoImageFormFileName("accuracytests/topcoder" + PNG_EXTENSION);
        servlet.doPost(request, response);
        String fileName = SUBMITTED_DIRECTORY + memberId + PNG_EXTENSION;
        assertTrue("PNG file should exist.", new File(fileName).exists());
        assertEquals("Url should be redirected successfully.", SUBMIT_REDIRECTED_URL, response.getRedirectedUrl());
        assertNotNull("Image should be read successfully.", ImageIO.read(new File(fileName)));
    }

    /**
     * <p>
     * Tests {@link MemberPhotoUploadServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Action is "crop". Image should be cropped and stored in cropped directory. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Crop_PNG() throws Exception {
        Long memberId = 1L;
        MockHttpServletRequest request = AccuracyTestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        populateRequest(request);
        request.setParameter(SUBMITTED_ACTION_PARAMETER_NAME, "crop");
        servlet.setPhotoImageFormFileName("accuracytests/topcoder" + PNG_EXTENSION);
        servlet.doPost(request, response);
        String fileName = CROP_DIRECTORY + memberId + PNG_EXTENSION;
        assertTrue("PNG file should exist.", new File(fileName).exists());
        assertEquals("Url should be redirected successfully.", SUBMIT_REDIRECTED_URL, response.getRedirectedUrl());
        assertNotNull("Image should be read successfully.", ImageIO.read(new File(fileName)));
    }

    /**
     * <p>
     * Tests {@link MemberPhotoUploadServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Action is "preview". Image should be stored in preview directory. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Preview_PNG() throws Exception {
        Long memberId = 1L;
        MockHttpServletRequest request = AccuracyTestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        populateRequest(request);
        request.setParameter(SUBMITTED_ACTION_PARAMETER_NAME, "preview");
        servlet.setPhotoImageFormFileName("accuracytests/topcoder" + PNG_EXTENSION);
        servlet.doPost(request, response);
        String fileName = PREVIEW_DIRECTORY + memberId + PNG_EXTENSION;
        assertTrue("PNG file should exist.", new File(fileName).exists());
        assertEquals("Url should be redirected successfully.", PREVIEW_FORWARD_URL, response.getForwardedUrl());
        assertNotNull("Image should be read successfully.", ImageIO.read(new File(fileName)));
        assertNotNull("previewPhotoImagePathName should be placed to request.", request
                .getAttribute(PREVIEW_PHOTO_IMAGE_PATH_NAME));
    }

    /**
     * <p>
     * Tests {@link MemberPhotoUploadServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Action is "commit", submitted file is not image. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_FileIsNotImage() throws Exception {
        MockHttpServletRequest request = AccuracyTestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        populateRequest(request);
        servlet.setPhotoImageFormFileName("accuracytests/template.txt");
        servlet.doPost(request, response);
        assertNotNull("photoImageErrorName should be added to request.", request.getAttribute("photoImageErrorName"));
        assertEquals("Url should be redirected successfully to error.", ERROR_RESULT_URL, response.getForwardedUrl());
    }

    /**
     * <p>
     * Tests {@link MemberPhotoUploadServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Member id is not in the session. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_MemberIdIsNotInSession() throws Exception {
        MockHttpServletRequest request = AccuracyTestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        populateRequest(request);
        // member is not in session
        servlet.setPhotoImageFormFileName("accuracytests/topcoder" + JPEG_EXTENSION);
        request.setParameter(MEMBER_ID_PARAMETER_NAME, System.currentTimeMillis() + "");
        servlet.doPost(request, response);
        assertNotNull("memberIdErrorName should be added to request.", request.getAttribute("memberIdErrorName"));
        assertEquals("Url should be redirected successfully to error.", ERROR_RESULT_URL, response.getForwardedUrl());
    }

    /**
     * <p>
     * Tests {@link MemberPhotoUploadServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * File size greater than maximum. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_FileSize() throws Exception {
        MockHttpServletRequest request = AccuracyTestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        populateRequest(request);
        // small max file size
        long maxFileSize = 1;
        servlet.setMaxFileSize(maxFileSize);
        servlet.doPost(request, response);
        assertNotNull("maxFileExceededErrorName should be added to request.", request
                .getAttribute("maxFileExceededErrorName"));
        assertEquals("Url should be redirected successfully to error.", ERROR_RESULT_URL, response.getForwardedUrl());
    }

    /**
     * <p>
     * Tests {@link MemberPhotoUploadServlet#doPost(HttpServletRequest, HttpServletResponse)} with valid arguments
     * passed.
     * </p>
     * <p>
     * Action is "crop". Image should be cropped and stored in cropped directory. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testDoPost_Crop() throws Exception {
        Long memberId = 1L;
        MockHttpServletRequest request = AccuracyTestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        populateRequest(request);
        request.setParameter(SUBMITTED_ACTION_PARAMETER_NAME, "crop");
        servlet.doPost(request, response);
        String fileName = CROP_DIRECTORY + memberId + JPEG_EXTENSION;
        assertTrue("File should exist.", new File(fileName).exists());
        assertEquals("Url should be redirected successfully.", SUBMIT_REDIRECTED_URL, response.getRedirectedUrl());
        assertNotNull("Image should be read successfully.", ImageIO.read(new File(fileName)));
    }

    /**
     * <p>
     * Populates request with parameters.
     * </p>
     * @param request the request to be populated.
     */
    private void populateRequest(MockHttpServletRequest request) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("sessionMemberId", 1L);
        request.setSession(session);
        // Crop "[Topcoder]" from image
        request.setParameter("targetImageLeftCornerXParameterName", "90");
        request.setParameter("targetImageLeftCornerYParameterName", "5");
        request.setParameter("targetImageRightCornerXParameterName", "273");
        request.setParameter("targetImageRightCornerYParameterName", "36");
        request.setParameter(SUBMITTED_ACTION_PARAMETER_NAME, "commit");
        request.setParameter(MEMBER_ID_PARAMETER_NAME, "1");
    }
}
