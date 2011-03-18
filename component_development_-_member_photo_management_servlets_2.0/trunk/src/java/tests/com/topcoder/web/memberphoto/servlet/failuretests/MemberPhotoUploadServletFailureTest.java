/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.failuretests;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.topcoder.web.memberphoto.servlet.MemberPhotoUploadException;
import com.topcoder.web.memberphoto.servlet.MemberPhotoUploadServlet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

/**
 * Failure test for MemberPhotoUploadServlet class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberPhotoUploadServletFailureTest extends TestCase {
    /**
     * Represents the instance of MemberPhotoUploadServlet used in unit test.
     */
    private MemberPhotoUploadServlet instance;

    /**
     * Represents the HttpServletRequest instance.
     */
    private MockHttpServletRequest request;

    /**
     * Represents the HttpServletResponse instance.
     */
    private HttpServletResponse response;

    /**
     * Represents the HttpSession instance.
     */
    private HttpSession session;

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(MemberPhotoUploadServletFailureTest.class);
    }

    /**
     * Set up for each test cases.
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        response = new MockHttpServletResponse();

        session = new MockHttpSession();
        session.setAttribute("isAdministrator", Boolean.TRUE);
        session.setAttribute("memberIdSessionKey", new Long("1"));

        request = new MockHttpServletRequest();
        request.setSession(session);
        request.setParameter("submittedAction", "crop");
        request.setParameter("memberId", "1");
        request.setParameter("targetImageLeftCornerX", "10");
        request.setParameter("targetImageLeftCornerY", "10");
        request.setParameter("targetImageRightCornerX", "110");
        request.setParameter("targetImageRightCornerY", "110");

        instance = new MemberPhotoUploadServlet();
        instance.setFileUploaderNamespace("failuretests");
        instance.setMaxFileExceededErrorName("maxFileExceededErrorName");
        instance.setMaxFileSize(100000);
        instance.setMemberIdErrorName("memberIdErrorName");
        instance.setMemberIdParameterName("memberId");
        instance.setMemberIdSessionKey("memberIdSessionKey");
        instance.setMemberPhotoManager(new MockMemberPhotoManager());
        instance.setPhotoImageCropDirectory("crop");
        instance.setPhotoImageErrorName("photoImageErrorName");
        instance.setPhotoImageFormFileName("photoImageFormFileName");
        instance.setPhotoImagePreviewDirectory("preview");
        instance.setPhotoImageSubmittedDirectory("submitted");
        instance.setPreviewForwardUrl("PreviewForwardUrl");
        instance.setPreviewPhotoImagePathName("previewPhotoImagePathName");
        instance.setSubmittedActionParameterName("submittedAction");
        instance.setSubmittedRedirectUrl("submittedRedirectUrl");
        instance.setTargetImageHeight(100);
        instance.setTargetImageLeftCornerXParameterName("targetImageLeftCornerX");
        instance.setTargetImageLeftCornerYParameterName("targetImageLeftCornerY");
        instance.setTargetImageRightCornerXParameterName("targetImageRightCornerX");
        instance.setTargetImageRightCornerYParameterName("targetImageRightCornerY");
        instance.setTargetImageWidth(100);
        instance.setValidationErrorForwardUrl("validationErrorForwardUrl");
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When request is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_RequestIsNull() throws Exception {
        try {
            instance.doPost(null, response);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When response is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_ResponseIsNull() throws Exception {
        try {
            instance.doPost(request, null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * memberIdParameterName is
     * null.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberIdParameterNameIsNull() throws Exception {
        FailureTestHelper.setField(instance, "memberIdParameterName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * photoImageFormFileName is
     * null.
     * @throws Exception to JUnit
     */
    public void testDoPost_PhotoImageFormFileNameIsNull() throws Exception {
        FailureTestHelper.setField(instance, "photoImageFormFileName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When memberPhotoManager
     * is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberPhotoManagerIsNull() throws Exception {
        FailureTestHelper.setField(instance, "memberPhotoManager", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When memberIdSessionKey
     * is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberIdSessionKeyIsNull() throws Exception {
        FailureTestHelper.setField(instance, "memberIdSessionKey", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When memberIdErrorName
     * is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberIdErrorNameIsNull() throws Exception {
        FailureTestHelper.setField(instance, "memberIdErrorName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When photoImageErrorName
     * is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_PhotoImageErrorNameIsNull() throws Exception {
        FailureTestHelper.setField(instance, "photoImageErrorName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * validationErrorForwardUrl is
     * null.
     * @throws Exception to JUnit
     */
    public void testDoPost_ValidationErrorForwardUrlIsNull() throws Exception {
        FailureTestHelper.setField(instance, "validationErrorForwardUrl", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When targetImageWidth is
     * 0.
     * @throws Exception to JUnit
     */
    public void testDoPost_TargetImageWidthIsNull() throws Exception {
        FailureTestHelper.setField(instance, "targetImageWidth", 0);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When targetImageHeight
     * is 0.
     * @throws Exception to JUnit
     */
    public void testDoPost_TargetImageHeightIsNull() throws Exception {
        FailureTestHelper.setField(instance, "targetImageHeight", 0);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * targetImageLeftCornerXParameterName is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_TargetImageLeftCornerXParameterNameIsNull() throws Exception {
        FailureTestHelper.setField(instance, "targetImageLeftCornerXParameterName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * targetImageLeftCornerYParameterName is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_TargetImageLeftCornerYParameterNameIsNull() throws Exception {
        FailureTestHelper.setField(instance, "targetImageLeftCornerYParameterName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * targetImageRightCornerXParameterName is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_TargetImageRightCornerXParameterNameIsNull() throws Exception {
        FailureTestHelper.setField(instance, "targetImageRightCornerXParameterName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * targetImageRightCornerYParameterName is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_TargetImageRightCornerYParameterNameIsNull() throws Exception {
        FailureTestHelper.setField(instance, "targetImageRightCornerYParameterName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * photoImageSubmittedDirectory
     * is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_PhotoImageSubmittedDirectoryIsNull() throws Exception {
        FailureTestHelper.setField(instance, "photoImageSubmittedDirectory", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * photoImagePreviewDirectory is
     * null.
     * @throws Exception to JUnit
     */
    public void testDoPost_PhotoImagePreviewDirectoryIsNull() throws Exception {
        FailureTestHelper.setField(instance, "photoImagePreviewDirectory", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * photoImageCropDirectory is
     * null.
     * @throws Exception to JUnit
     */
    public void testDoPost_PhotoImageCropDirectoryIsNull() throws Exception {
        FailureTestHelper.setField(instance, "photoImageCropDirectory", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * previewPhotoImagePathName is
     * null.
     * @throws Exception to JUnit
     */
    public void testDoPost_PreviewPhotoImagePathNameIsNull() throws Exception {
        FailureTestHelper.setField(instance, "previewPhotoImagePathName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When previewForwardUrl
     * is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_PreviewForwardUrlIsNull() throws Exception {
        FailureTestHelper.setField(instance, "previewForwardUrl", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * submittedRedirectUrl is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_submittedRedirectUrlIsNull() throws Exception {
        FailureTestHelper.setField(instance, "submittedRedirectUrl", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * fileUploaderNamespace is
     * null.
     * @throws Exception to JUnit
     */
    public void testDoPost_fileUploaderNamespaceIsNull() throws Exception {
        FailureTestHelper.setField(instance, "fileUploaderNamespace", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When maxFileSize is 0.
     * @throws Exception to JUnit
     */
    public void testDoPost_maxFileSizeIsNull() throws Exception {
        FailureTestHelper.setField(instance, "maxFileSize", 0);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * maxFileExceededErrorName is
     * null.
     * @throws Exception to JUnit
     */
    public void testDoPost_maxFileExceededErrorNameIsNull() throws Exception {
        FailureTestHelper.setField(instance, "maxFileExceededErrorName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * submittedActionParameterName
     * is null.
     * @throws Exception to JUnit
     */
    public void testDoPost_submittedActionParameterNameIsNull() throws Exception {
        FailureTestHelper.setField(instance, "submittedActionParameterName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When memberId in session
     * invalid.
     * @throws Exception to JUnit
     */
    public void testDoPost_memberIdInSessionIsInvalid() throws Exception {
        request.getSession().setAttribute("memberIdSessionKey", "A");
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When memberId is
     * invalid.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberIdIsInvalid() throws Exception {
        request.setAttribute("memberId", "A");
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (MemberPhotoUploadException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When memberPhotoManager
     * throw
     * exception.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberPhotoManagerThrowException() throws Exception {
        request.getSession().setAttribute("memberIdSessionKey", new Long(101));
        request.setParameter("memberId", "101");
        request.setParameter("submittedAction", "commit");
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (MemberPhotoUploadException e) {
            // OK
        }
    }

    /**
     * Failure test for setMemberIdParameterName(String). When MemberIdParameterName is null.
     */
    public void testSetMemberIdParameterName_MemberIdParameterNameIsNull() {
        try {
            instance.setMemberIdParameterName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMemberIdParameterName(String). When MemberIdParameterName is empty.
     */
    public void testSetMemberIdParameterName_MemberIdParameterNameIsEmpty() {
        try {
            instance.setMemberIdParameterName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPhotoImageFormFileName(String). When PhotoImageFormFileName is null.
     */
    public void testSetPhotoImageFormFileName_PhotoImageFormFileNameIsNull() {
        try {
            instance.setPhotoImageFormFileName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPhotoImageFormFileName(String). When PhotoImageFormFileName is empty.
     */
    public void testSetPhotoImageFormFileName_PhotoImageFormFileNameIsEmpty() {
        try {
            instance.setPhotoImageFormFileName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setSubmittedActionParameterName(String). When SubmittedActionParameterName is null.
     */
    public void testSetSubmittedActionParameterName_SubmittedActionParameterNameIsNull() {
        try {
            instance.setSubmittedActionParameterName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setSubmittedActionParameterName(String). When SubmittedActionParameterName is empty.
     */
    public void testSetSubmittedActionParameterName_SubmittedActionParameterNameIsEmpty() {
        try {
            instance.setSubmittedActionParameterName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMemberPhotoManager(MemberPhotoManager). When MemberPhotoManager is null.
     */
    public void testSetMemberPhotoManager_MemberPhotoManagerIsNull() {
        try {
            instance.setMemberPhotoManager(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMemberIdSessionKey(String). When MemberIdSessionKey is null.
     */
    public void testSetMemberIdSessionKey_MemberIdSessionKeyIsNull() {
        try {
            instance.setMemberIdSessionKey(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMemberIdSessionKey(String). When MemberIdSessionKey is empty.
     */
    public void testSetMemberIdSessionKey_MemberIdSessionKeyIsEmpty() {
        try {
            instance.setMemberIdSessionKey(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMemberIdErrorName(String). When MemberIdErrorName is null.
     */
    public void testSetMemberIdErrorName_MemberIdErrorNameIsNull() {
        try {
            instance.setMemberIdErrorName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMemberIdErrorName(String). When MemberIdErrorName is empty.
     */
    public void testSetMemberIdErrorName_MemberIdErrorNameIsEmpty() {
        try {
            instance.setMemberIdErrorName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPhotoImageErrorName(String). When PhotoImageErrorName is null.
     */
    public void testSetPhotoImageErrorName_PhotoImageErrorNameIsNull() {
        try {
            instance.setPhotoImageErrorName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPhotoImageErrorName(String). When PhotoImageErrorName is empty.
     */
    public void testSetPhotoImageErrorName_PhotoImageErrorNameIsEmpty() {
        try {
            instance.setPhotoImageErrorName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setValidationErrorForwardUrl(String). When ValidationErrorForwardUrl is null.
     */
    public void testSetValidationErrorForwardUrl_ValidationErrorForwardUrlIsNull() {
        try {
            instance.setValidationErrorForwardUrl(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setValidationErrorForwardUrl(String). When ValidationErrorForwardUrl is empty.
     */
    public void testSetValidationErrorForwardUrl_ValidationErrorForwardUrlIsEmpty() {
        try {
            instance.setValidationErrorForwardUrl(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setTargetImageWidth(String). When TargetImageWidth is 0.
     */
    public void testSetTargetImageWidth_TargetImageWidthIsNull() {
        try {
            instance.setTargetImageWidth(0);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setTargetImageHeight(String). When TargetImageHeight is 0.
     */
    public void testSetTargetImageHeight_TargetImageHeightIsNull() {
        try {
            instance.setTargetImageHeight(0);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setTargetImageLeftCornerXParameterName(String). When
     * TargetImageLeftCornerXParameterName is
     * null.
     */
    public void testSetTargetImageLeftCornerXParameterName_TargetImageLeftCornerXParameterNameIsNull() {
        try {
            instance.setTargetImageLeftCornerXParameterName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setTargetImageLeftCornerXParameterName(String). When
     * TargetImageLeftCornerXParameterName is
     * empty.
     */
    public void testSetTargetImageLeftCornerXParameterName_TargetImageLeftCornerXParameterNameIsEmpty() {
        try {
            instance.setTargetImageLeftCornerXParameterName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setTargetImageLeftCornerYParameterName(String). When
     * TargetImageLeftCornerYParameterName is
     * null.
     */
    public void testSetTargetImageLeftCornerYParameterName_TargetImageLeftCornerYParameterNameIsNull() {
        try {
            instance.setTargetImageLeftCornerYParameterName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setTargetImageLeftCornerYParameterName(String). When
     * TargetImageLeftCornerYParameterName is
     * empty.
     */
    public void testSetTargetImageLeftCornerYParameterName_TargetImageLeftCornerYParameterNameIsEmpty() {
        try {
            instance.setTargetImageLeftCornerYParameterName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setTargetImageRightCornerXParameterName(String). When
     * TargetImageRightCornerYParameterName is
     * null.
     */
    public void testSetTargetImageRightCornerXParameterName_TargetImageRightCornerXParameterNameIsNull() {
        try {
            instance.setTargetImageRightCornerXParameterName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setTargetImageRightCornerXParameterName(String). When
     * TargetImageRightCornerYParameterName is
     * empty.
     */
    public void testSetTargetImageRightCornerXParameterName_TargetImageRightCornerXParameterNameIsEmpty() {
        try {
            instance.setTargetImageRightCornerXParameterName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setTargetImageRightCornerYParameterName(String). When
     * TargetImageRightCornerYParameterName is
     * null.
     */
    public void testSetTargetImageRightCornerYParameterName_TargetImageRightCornerYParameterNameIsNull() {
        try {
            instance.setTargetImageRightCornerYParameterName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setTargetImageRightCornerYParameterName(String). When
     * TargetImageRightCornerYParameterName is
     * empty.
     */
    public void testSetTargetImageRightCornerYParameterName_TargetImageRightCornerYParameterNameIsEmpty() {
        try {
            instance.setTargetImageRightCornerYParameterName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPhotoImageSubmittedDirectory(String). When PhotoImageSubmittedDirectory is null.
     */
    public void testSetPhotoImageSubmittedDirectory_PhotoImageSubmittedDirectoryIsNull() {
        try {
            instance.setPhotoImageSubmittedDirectory(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPhotoImageSubmittedDirectory(String). When PhotoImageSubmittedDirectory is empty.
     */
    public void testSetPhotoImageSubmittedDirectory_PhotoImageSubmittedDirectoryIsEmpty() {
        try {
            instance.setPhotoImageSubmittedDirectory(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPhotoImagePreviewDirectory(String). When PhotoImagePreviewDirectory is null.
     */
    public void testSetPhotoImagePreviewDirectory_PhotoImagePreviewDirectoryIsNull() {
        try {
            instance.setPhotoImagePreviewDirectory(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPhotoImagePreviewDirectory(String). When PhotoImagePreviewDirectory is empty.
     */
    public void testSetPhotoImagePreviewDirectory_PhotoImagePreviewDirectoryIsEmpty() {
        try {
            instance.setPhotoImagePreviewDirectory(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPhotoImageCropDirectory(String). When PhotoImageCropDirectory is null.
     */
    public void testSetPhotoImageCropDirectory_PhotoImageCropDirectoryIsNull() {
        try {
            instance.setPhotoImageCropDirectory(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPhotoImageCropDirectory(String). When PhotoImageCropDirectory is empty.
     */
    public void testSetPhotoImageCropDirectory_PhotoImageCropDirectoryIsEmpty() {
        try {
            instance.setPhotoImageCropDirectory(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPreviewPhotoImagePathName(String). When PreviewPhotoImagePathName is null.
     */
    public void testSetPreviewPhotoImagePathName_PreviewPhotoImagePathNameIsNull() {
        try {
            instance.setPreviewPhotoImagePathName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPreviewPhotoImagePathName(String). When PreviewPhotoImagePathName is empty.
     */
    public void testSetPreviewPhotoImagePathName_PreviewPhotoImagePathNameIsEmpty() {
        try {
            instance.setPreviewPhotoImagePathName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPreviewForwardUrl(String). When PreviewForwardUrl is null.
     */
    public void testSetPreviewForwardUrl_PreviewForwardUrlIsNull() {
        try {
            instance.setPreviewForwardUrl(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPreviewForwardUrl(String). When PreviewForwardUrl is empty.
     */
    public void testSetPreviewForwardUrl_PreviewForwardUrlsEmpty() {
        try {
            instance.setPreviewForwardUrl(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setSubmittedRedirectUrl(String). When submittedRedirectUrl is null.
     */
    public void testSetSubmittedRedirectUrl_SubmittedRedirectUrlIsNull() {
        try {
            instance.setSubmittedRedirectUrl(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setSubmittedRedirectUrl(String). When submittedRedirectUrl is empty.
     */
    public void testSetSubmittedRedirectUrl_SubmittedRedirectUrlIsEmpty() {
        try {
            instance.setSubmittedRedirectUrl(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setFileUploaderNamespace(String). When fileUploaderNamespace is null.
     */
    public void testSetFileUploaderNamespace_FileUploaderNamespaceIsNull() {
        try {
            instance.setFileUploaderNamespace(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setFileUploaderNamespace(String). When fileUploaderNamespace is empty.
     */
    public void testSetFileUploaderNamespace_FileUploaderNamespaceIsEmpty() {
        try {
            instance.setFileUploaderNamespace(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMaxFileSize(long). When maxFileSize is zero.
     */
    public void testSetMaxFileSize_MaxFileSizeIsZero() {
        try {
            instance.setMaxFileSize(0);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMaxFileExceededErrorName(String). When maxFileExceededErrorName is null.
     */
    public void testSetMaxFileExceededErrorName_MaxFileExceededErrorNameIsNull() {
        try {
            instance.setMaxFileExceededErrorName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMaxFileExceededErrorName(String). When maxFileExceededErrorName is empty.
     */
    public void testSetMaxFileExceededErrorName_MaxFileExceededErrorNameIsEmpty() {
        try {
            instance.setMaxFileExceededErrorName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
}
