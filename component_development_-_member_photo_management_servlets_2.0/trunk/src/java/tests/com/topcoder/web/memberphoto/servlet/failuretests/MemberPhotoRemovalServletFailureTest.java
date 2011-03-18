/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.failuretests;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.web.memberphoto.servlet.MemberPhotoRemovalException;
import com.topcoder.web.memberphoto.servlet.MemberPhotoRemovalServlet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

/**
 * Failure test for MemberPhotoRemovalServlet class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberPhotoRemovalServletFailureTest extends TestCase {
    /**
     * Represents the instance of MemberPhotoRemovalServlet used in unit test.
     */
    private MemberPhotoRemovalServlet instance;

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
     * Returns the test suite of this class.
     * @return the test suite.
     */
    public static Test suite() {
        return new TestSuite(MemberPhotoRemovalServletFailureTest.class);
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

        request = new MockHttpServletRequest();
        request.setSession(session);
        request.setParameter("memberId", "1");
        request.setParameter("removalReason", "test");

        instance = new MemberPhotoRemovalServlet();
        instance.setDocumentGenerator(new DocumentGenerator("hi"));
        instance.setEmailBodyTemplateFileName("a");
        instance.setEmailSendFlag(false);
        instance.setEmailSubject("subject");
        instance.setFromEmailAddress("test@topcoder.com");
        instance.setIsAdministratorSessionKey("isAdministrator");
        instance.setMemberIdParameterName("memberId");
        instance.setMemberHasNoPhotoErrorName("memberHasNoPhoto");
        instance.setMemberInformationRetriever(new MockMemberInformationRetriever());
        instance.setMemberPhotoManager(new MockMemberPhotoManager());
        instance.setNotAdministratorErrorName("notAdministrator");
        instance.setPhotoImageRemovedDirectory("removed");
        instance.setRemovalReasonParameterName("removalReason");
        instance.setSuccessResultForwardUrl("successResultForwardUrl");
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
     * memberIdParameterName is not
     * set.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberIdParameterNameIsNotSet() throws Exception {
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
     * removalReasonParameterName is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_RemovalReasonParameterNameIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "removalReasonParameterName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * memberHasNoPhotoErrorName is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberHasNoPhotoErrorNameIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "memberHasNoPhotoErrorName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * isAdministratorSessionKey is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_IsAdministratorSessionKeyIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "isAdministratorSessionKey", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * notAdministratorErrorName is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_NotAdministratorErrorNameIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "notAdministratorErrorName", null);
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
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_ValidationErrorForwardUrlIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "validationErrorForwardUrl", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When documentGenerator
     * is not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_DocumentGeneratorIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "documentGenerator", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When fromEmailAddress is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_FromEmailAddressIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "fromEmailAddress", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When emailSubject is not
     * set.
     * @throws Exception to JUnit
     */
    public void testDoPost_EmailSubjectIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "emailSubject", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * successResultForwardUrl is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_SuccessResultForwardUrlIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "successResultForwardUrl", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * memberInformationRetriever is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberInformationRetrieverIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "memberInformationRetriever", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When memberPhotoManager
     * is not
     * set.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberPhotoManagerIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "memberPhotoManager", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * emailBodyTemplateFileName is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_EmailBodyTemplateFileNameIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "emailBodyTemplateFileName", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * MemberInformationRetriever
     * throws exception.
     * @throws Exception to JUnit
     */
    public void testDoPost_MemberInformationRetrieverThrowException() throws Exception {
        request.setAttribute("memberId", "201");
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (MemberPhotoRemovalException e) {
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
        request.setAttribute("memberId", "101");
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (MemberPhotoRemovalException e) {
            // OK
        }
    }

    /**
     * Failure test doPost(HttpServletRequest request, HttpServletResponse response). When
     * photoImageRemovedDirectory is
     * not set.
     * @throws Exception to JUnit
     */
    public void testDoPost_PhotoImageRemovedDirectoryIsNotSet() throws Exception {
        FailureTestHelper.setField(instance, "photoImageRemovedDirectory", null);
        try {
            instance.doPost(request, response);
            fail("Cannot go here.");
        } catch (IllegalStateException e) {
            // OK
        }
    }

    /**
     * Failure test for setMemberIdParameterName(String). When memberIdParameterName is null.
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
     * Failure test for setMemberIdParameterName(String). When memberIdParameterName is empty.
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
     * Failure test for setRemovalReasonParameterName(String). When RemovalReasonParameterName is null.
     */
    public void testSetRemovalReasonParameterName_RemovalReasonParameterNameIsNull() {
        try {
            instance.setRemovalReasonParameterName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setRemovalReasonParameterName(String). When RemovalReasonParameterName is empty.
     */
    public void testSetRemovalReasonParameterName_RemovalReasonParameterNameIsEmpty() {
        try {
            instance.setRemovalReasonParameterName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMemberHasNoPhotoErrorName(String). When MemberHasNoPhotoErrorName is null.
     */
    public void testSetMemberHasNoPhotoErrorName_MemberHasNoPhotoErrorNameIsNull() {
        try {
            instance.setMemberHasNoPhotoErrorName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMemberHasNoPhotoErrorName(String). When MemberHasNoPhotoErrorName is empty.
     */
    public void testSetMemberHasNoPhotoErrorName_MemberHasNoPhotoErrorNameIsEmpty() {
        try {
            instance.setMemberHasNoPhotoErrorName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setIsAdministratorSessionKey(String). When IsAdministratorSessionKey is null.
     */
    public void testSetIsAdministratorSessionKey_IsAdministratorSessionKeyIsNull() {
        try {
            instance.setIsAdministratorSessionKey(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setIsAdministratorSessionKey(String). When IsAdministratorSessionKey is empty.
     */
    public void testSetIsAdministratorSessionKey_IsAdministratorSessionKeyIsEmpty() {
        try {
            instance.setIsAdministratorSessionKey(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setNotAdministratorErrorName(String). When NotAdministratorErrorName is null.
     */
    public void testSetNotAdministratorErrorName_NotAdministratorErrorNameIsNull() {
        try {
            instance.setNotAdministratorErrorName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setNotAdministratorErrorName(String). When NotAdministratorErrorName is empty.
     */
    public void testSetNotAdministratorErrorName_NotAdministratorErrorNameIsEmpty() {
        try {
            instance.setNotAdministratorErrorName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setValidationErrorForwardUrl(String). When validationErrorForwardUrl is null.
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
     * Failure test for setValidationErrorForwardUrl(String). When validationErrorForwardUrl is empty.
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
     * Failure test for setDocumentGenerator(DocumentGenerator). When DocumentGenerator is null.
     */
    public void testSetDocumentGenerator_DocumentGeneratorIsNull() {
        try {
            instance.setDocumentGenerator(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setFromEmailAddress(String). When FromEmailAddress is null.
     */
    public void testSetFromEmailAddress_FromEmailAddressIsNull() {
        try {
            instance.setFromEmailAddress(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setFromEmailAddress(String). When FromEmailAddress is empty.
     */
    public void testSetFromEmailAddress_FromEmailAddressIsEmpty() {
        try {
            instance.setFromEmailAddress(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setEmailSubject(String). When EmailSubject is null.
     */
    public void testSetEmailSubject_EmailSubjectIsNull() {
        try {
            instance.setSuccessResultForwardUrl(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setEmailSubject(String). When EmailSubject is empty.
     */
    public void testSetEmailSubject_EmailSubjectIsEmpty() {
        try {
            instance.setSuccessResultForwardUrl(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setSuccessResultForwardUrl(String). When SuccessResultForwardUrl is null.
     */
    public void testSetSuccessResultForwardUrl_SuccessResultForwardUrlIsNull() {
        try {
            instance.setSuccessResultForwardUrl(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setSuccessResultForwardUrl(String). When SuccessResultForwardUrl is empty.
     */
    public void testSetSuccessResultForwardUrl_SuccessResultForwardUrlIsEmpty() {
        try {
            instance.setSuccessResultForwardUrl(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMemberInformationRetriever(MemberInformationRetriever). When
     * memberInformationRetriever is
     * null.
     */
    public void testSetMemberInformationRetriever_MemberInformationRetrieverIsNull() {
        try {
            instance.setMemberInformationRetriever(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setMemberPhotoManager(MemberPhotoManager). When memberPhotoManager is null.
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
     * Failure test for setEmailBodyTemplateFileName(String). When emailBodyTemplateFileName is null.
     */
    public void testSetEmailBodyTemplateFileName_EmailBodyTemplateFileNameIsNull() {
        try {
            instance.setEmailBodyTemplateFileName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setEmailBodyTemplateFileName(String). When emailBodyTemplateFileName is empty.
     */
    public void testSetEmailBodyTemplateFileName_EmailBodyTemplateFileNameIsEmpty() {
        try {
            instance.setEmailBodyTemplateFileName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPhotoImageRemovedDirectory(String). When photoImageRemovedDirectory is null.
     */
    public void testSetPhotoImageRemovedDirectory_PhotoImageRemovedDirectoryIsNull() {
        try {
            instance.setPhotoImageRemovedDirectory(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for setPhotoImageRemovedDirectory(String). When photoImageRemovedDirectory is empty.
     */
    public void testSetPhotoImageRemovedDirectory_PhotoImageRemovedDirectoryIsEmpty() {
        try {
            instance.setPhotoImageRemovedDirectory(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
}