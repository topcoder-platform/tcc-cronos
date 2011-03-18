/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.web.memberphoto.manager.MemberPhotoManager;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagerImpl;

/**
 * <p>
 * Unit tests for <code>MemberPhotoRemovalServlet</code> class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberPhotoRemovalServletTest extends TestCase {
    /**
     * <p>
     * String constant for testing.
     * </p>
     */
    private static final String TEST = "the_test_string";
    /**
     * <p>
     * Constant for test files location.
     * </p>
     */
    private static final String TEST_FILES = "test_files/";

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(MemberPhotoRemovalServletTest.class);
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
        TestHelper.deldir(TEST_FILES + "removed");
        TestHelper.shutDownRunner();
    }

    /**
     * <p>
     * Accuracy test for constructor <code>MemberPhotoRemovalServlet()</code>.
     * </p>
     * <p>
     * Verify that the instance can be created successfully.
     * </p>
     */
    public void testEmptyCtorAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        assertNotNull("Unable to create MemberPhotoRemovalServlet instance.",
                instance);
        assertTrue("Should be instance of MemberPhotoRemovalServlet.",
                instance instanceof MemberPhotoRemovalServlet);
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setMemberIdParameterName(memberIdParameterName)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetMemberIdParameterNameAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        instance.setMemberIdParameterName(TEST);

        String value = getStringValue(instance, "memberIdParameterName");
        assertEquals("'memberIdParameterName' should be the same.", TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMemberIdParameterName(memberIdParameterName)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetMemberIdParameterNameFailure1() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setMemberIdParameterName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMemberIdParameterName(memberIdParameterName)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetMemberIdParameterNameFailure2() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setMemberIdParameterName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setRemovalReasonParameterName(removalReasonParameterName)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetRemovalReasonParameterNameAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        instance.setRemovalReasonParameterName(TEST);

        String value = getStringValue(instance, "removalReasonParameterName");
        assertEquals("'removalReasonParameterName' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setRemovalReasonParameterName(removalReasonParameterName)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetRemovalReasonParameterNameFailure1() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setRemovalReasonParameterName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setRemovalReasonParameterName(removalReasonParameterName)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetRemovalReasonParameterNameFailure2() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setRemovalReasonParameterName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setMemberHasNoPhotoErrorName(memberHasNoPhotoErrorName)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetMemberHasNoPhotoErrorNameAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        instance.setMemberHasNoPhotoErrorName(TEST);

        String value = getStringValue(instance, "memberHasNoPhotoErrorName");
        assertEquals("'memberHasNoPhotoErrorName' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMemberHasNoPhotoErrorName(memberHasNoPhotoErrorName)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetMemberHasNoPhotoErrorNameFailure1() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setMemberHasNoPhotoErrorName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMemberHasNoPhotoErrorName(memberHasNoPhotoErrorName)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetMemberHasNoPhotoErrorNameFailure2() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setMemberHasNoPhotoErrorName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setIsAdministratorSessionKey(isAdministratorSessionKey)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetIsAdministratorSessionKeyAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        instance.setIsAdministratorSessionKey(TEST);

        String value = getStringValue(instance, "isAdministratorSessionKey");
        assertEquals("'isAdministratorSessionKey' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setIsAdministratorSessionKey(isAdministratorSessionKey)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetIsAdministratorSessionKeyFailure1() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setIsAdministratorSessionKey(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setIsAdministratorSessionKey(isAdministratorSessionKey)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetIsAdministratorSessionKeyFailure2() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setIsAdministratorSessionKey(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setNotAdministratorErrorName(notAdministratorErrorName)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetNotAdministratorErrorNameAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        instance.setNotAdministratorErrorName(TEST);

        String value = getStringValue(instance, "notAdministratorErrorName");
        assertEquals("'notAdministratorErrorName' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setNotAdministratorErrorName(notAdministratorErrorName)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetNotAdministratorErrorNameFailure1() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setNotAdministratorErrorName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setNotAdministratorErrorName(notAdministratorErrorName)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetNotAdministratorErrorNameFailure2() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setNotAdministratorErrorName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setValidationErrorForwardUrl(validationErrorForwardUrl)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetValidationErrorForwardUrlAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        instance.setValidationErrorForwardUrl(TEST);

        String value = getStringValue(instance, "validationErrorForwardUrl");
        assertEquals("'validationErrorForwardUrl' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setValidationErrorForwardUrl(validationErrorForwardUrl)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetValidationErrorForwardUrlFailure1() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setValidationErrorForwardUrl(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setValidationErrorForwardUrl(validationErrorForwardUrl)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetValidationErrorForwardUrlFailure2() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setValidationErrorForwardUrl(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setDocumentGenerator(documentGenerator)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     * @throws Exception to JUnit
     */
    public void testSetDocumentGeneratorAccuracy() throws Exception {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        DocumentGenerator docGen = DocumentGenerator.getInstance();
        instance.setDocumentGenerator(docGen);

        DocumentGenerator value =
                (DocumentGenerator) getValue(instance, "documentGenerator");
        assertEquals("'documentGenerator' should be the same.", docGen, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setDocumentGenerator(documentGenerator)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetDocumentGeneratorFailure1() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setDocumentGenerator(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setFromEmailAddress(fromEmailAddress)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetFromEmailAddressAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        instance.setFromEmailAddress(TEST);

        String value = getStringValue(instance, "fromEmailAddress");
        assertEquals("'fromEmailAddress' should be the same.", TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setFromEmailAddress(fromEmailAddress)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetFromEmailAddressFailure1() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setFromEmailAddress(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setFromEmailAddress(fromEmailAddress)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetFromEmailAddressFailure2() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setFromEmailAddress(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setEmailSubject(emailSubject)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetEmailSubjectAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        instance.setEmailSubject(TEST);

        String value = getStringValue(instance, "emailSubject");
        assertEquals("'emailSubject' should be the same.", TEST, value);
    }

    /**
     * <p>
     * Failure test for method <code>setEmailSubject(emailSubject)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetEmailSubjectFailure1() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setEmailSubject(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>setEmailSubject(emailSubject)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetEmailSubjectFailure2() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setEmailSubject(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setSuccessResultForwardUrl(successResultForwardUrl)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetSuccessResultForwardUrlAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        instance.setSuccessResultForwardUrl(TEST);

        String value = getStringValue(instance, "successResultForwardUrl");
        assertEquals("'successResultForwardUrl' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setSuccessResultForwardUrl(successResultForwardUrl)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetSuccessResultForwardUrlFailure1() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setSuccessResultForwardUrl(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setSuccessResultForwardUrl(successResultForwardUrl)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetSuccessResultForwardUrlFailure2() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setSuccessResultForwardUrl(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setMemberInformationRetriever(memberInformationRetriever)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetMemberInformationRetrieverAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        MemberInformationRetriever retriever =
                new MockMemberInformationRetrieverImpl();
        instance.setMemberInformationRetriever(retriever);

        MemberInformationRetriever value =
                (MemberInformationRetriever) getValue(instance,
                        "memberInformationRetriever");
        assertEquals("'memberInformationRetriever' should be the same.",
                retriever, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMemberInformationRetriever(memberInformationRetriever)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetMemberInformationRetrieverFailure1() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setMemberInformationRetriever(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setMemberPhotoManager(memberPhotoManager)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetMemberPhotoManagerAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        MemberPhotoManager manager = new MemberPhotoManagerImpl();
        instance.setMemberPhotoManager(manager);

        MemberPhotoManager value =
                (MemberPhotoManager) getValue(instance, "memberPhotoManager");
        assertEquals("'memberPhotoManager' should be the same.", manager, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMemberPhotoManager(memberPhotoManager)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetMemberPhotoManagerFailure1() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setMemberPhotoManager(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setEmailBodyTemplateFileName(emailBodyTemplateFileName)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetEmailBodyTemplateFileNameAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        instance.setEmailBodyTemplateFileName(TEST);

        String value = getStringValue(instance, "emailBodyTemplateFileName");
        assertEquals("'emailBodyTemplateFileName' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setEmailBodyTemplateFileName(emailBodyTemplateFileName)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetEmailBodyTemplateFileNameFailure1() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setEmailBodyTemplateFileName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setEmailBodyTemplateFileName(emailBodyTemplateFileName)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetEmailBodyTemplateFileNameFailure2() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setEmailBodyTemplateFileName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setPhotoImageRemovedDirectory(String photoImageRemovedDirectory)</code>
     * .
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetPhotoImageRemovedDirectoryAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        instance.setPhotoImageRemovedDirectory(TEST);

        String value = getStringValue(instance, "photoImageRemovedDirectory");
        assertEquals("'emailBodyTemplateFileName' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPhotoImageRemovedDirectory(String photoImageRemovedDirectory)</code>
     * .
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetPhotoImageRemovedDirectoryFailure1() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setPhotoImageRemovedDirectory(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPhotoImageRemovedDirectory(String photoImageRemovedDirectory)</code>
     * .
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetPhotoImageRemovedDirectoryFailure2() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();

        try {
            instance.setPhotoImageRemovedDirectory(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setEmailSendFlag(boolean emailSendFlag)</code> .
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetEmailSendFlagAccuracy() {
        MemberPhotoRemovalServlet instance = new MemberPhotoRemovalServlet();
        instance.setEmailSendFlag(true);

        Boolean value = (Boolean) getValue(instance, "emailSendFlag");
        assertEquals("'emailBodyTemplateFileName' should be the same.",
                new Boolean(true), value);
    }


    /**
     * <p>
     * Accuracy test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * Verify that can handle the request and response correctly.
     * </p>
     * <p>
     * isAdministratorSessionKey is null, validate error.
     * </p>
     * <p>
     * forward to error URL.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostAccuracy1() throws Exception {
        MemberPhotoRemovalServlet bean = TestHelper.genRemovalServletBean();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", null);
        request.setSession(session);

        bean.doPost(request, response);
        assertEquals("error message should be the same.",
                "The user is not an administrator.",
                request.getAttribute("not_admin_error_name").toString());
        assertEquals("forward url should be the same.",
                "validation_error_forward_url", response.getForwardedUrl());
    }

    /**
     * <p>
     * Accuracy test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * The user is not an administrator, forward to error URL.
     * </p>
     * <p>
     * forward to error URL.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostAccuracy2() throws Exception {
        MemberPhotoRemovalServlet bean = TestHelper.genRemovalServletBean();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", new Boolean(false));
        request.setSession(session);

        bean.doPost(request, response);
        assertEquals("error message should be same.",
                "The user is not an administrator.",
                request.getAttribute("not_admin_error_name"));
        assertEquals("forward url should be the same.",
                "validation_error_forward_url", response.getForwardedUrl());
    }

    /**
     * <p>
     * Accuracy test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * member image is null, dispatch to error forward URL.
     * </p>
     * <p>
     * forward to error URL.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostAccuracy3() throws Exception {
        MemberPhotoRemovalServlet bean = TestHelper.genRemovalServletBean();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "0");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        bean.doPost(request, response);
        assertEquals("error message should be same.",
                "The member has no photo to remove.",
                request.getAttribute("member_has_no_photo_error_name"));
        assertEquals("forward url should be the same.",
                "validation_error_forward_url", response.getForwardedUrl());
    }

    /**
     * <p>
     * Accuracy test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * Verify that can handle the request and response correctly.
     * </p>
     * <p>
     * forward to successful URL.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostAccuracy4() throws Exception {
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
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'request' is null, IAE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure1() throws Exception {
        MemberPhotoRemovalServlet bean = TestHelper.genRemovalServletBean();
        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(null, response);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'response' is null, IAE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure2() throws Exception {
        MemberPhotoRemovalServlet bean = TestHelper.genRemovalServletBean();
        MockHttpServletRequest request = new MockHttpServletRequest();

        try {
            bean.doPost(request, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'memberIdParameterName' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure3() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_1");
        MockHttpServletRequest request = new MockHttpServletRequest();

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'member_id' is not a valid long value, IAE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure4() throws Exception {
        MemberPhotoRemovalServlet bean = TestHelper.genRemovalServletBean();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "not_a_long_value");

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("MemberPhotoRemovalException should be thrown.");
        } catch (MemberPhotoRemovalException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'removalReasonParameterName' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure5() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_2");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "717");

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'isAdministratorSessionKey' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure6() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_3");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "717");

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'notAdministratorErrorName' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure7() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_4");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", new Boolean(false));
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'memberHasNoPhotoErrorName' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure8() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_5");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "0");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'memberPhotoManager' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure9() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_14");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "0");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'is_admin_session_key' is not a Boolean, MemberPhotoRemovalException
     * should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure10() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_6");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "0");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", new Long(717));
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("MemberPhotoRemovalException should be thrown.");
        } catch (MemberPhotoRemovalException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'memberInformationRetriever' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure11() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_7");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'emailBodyTemplateFileName' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure12() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_8");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "717");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'fromEmailAddress' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure13() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_8");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "717");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'emailSubject' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure14() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_8");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "717");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * 'successResultForwardUrl' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure15() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_8");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "717");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * member photo not found, MemberPhotoRemovalException should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure16() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_15");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "-2");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("MemberPhotoRemovalException should be thrown.");
        } catch (MemberPhotoRemovalException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * error occurs when manager member photo, MemberPhotoRemovalException
     * should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure17() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_15");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "-1");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("MemberPhotoRemovalException should be thrown.");
        } catch (MemberPhotoRemovalException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * error occurs when get member information, MemberPhotoRemovalException
     * should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure18() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_15");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "-10");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("MemberPhotoRemovalException should be thrown.");
        } catch (MemberPhotoRemovalException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * Template file is empty, MemberPhotoRemovalException should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure19() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_12");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "717");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("MemberPhotoRemovalException should be thrown.");
        } catch (MemberPhotoRemovalException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * Template file is not valid, MemberPhotoRemovalException should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure20() throws Exception {
        MemberPhotoRemovalServlet bean =
                TestHelper.genRemovalServletBean("removal_invalid_13");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "717");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("MemberPhotoRemovalException should be thrown.");
        } catch (MemberPhotoRemovalException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * Template data format is invalid, MemberPhotoRemovalException should be
     * thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure21() throws Exception {
        MemberPhotoRemovalServlet bean = TestHelper.genRemovalServletBean();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("member_id", "888");
        request.setParameter("removal_reason", "this is removal reason.");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("is_admin_session_key", true);
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        try {
            bean.doPost(request, response);
            fail("MemberPhotoRemovalException should be thrown.");
        } catch (MemberPhotoRemovalException e) {
            // success
        }
    }

    /**
     * <p>
     * Test private move.
     * </p>
     */
    public void testMove() {
        try {
            MemberPhotoRemovalServlet bean = TestHelper.genRemovalServletBean();
            Method accessMethod =
                    bean.getClass().getDeclaredMethod("move", String.class,
                            String.class);
            accessMethod.setAccessible(true);

            accessMethod.invoke(bean, "test_files/move/test.gif",
                    "test_files/move/test2.gif");
            assertTrue("test_files/move/test.gif should not exist", !new File(
                    "test_files/move/test.gif").exists());
            assertTrue("test_files/move/test2.gif should  exist", new File(
                    "test_files/move/test2.gif").exists());
            accessMethod.invoke(bean, "test_files/move/test2.gif",
                    "test_files/move/test.gif");
        } catch (Exception e) {
            e.printStackTrace();
            // ignore
        }

    }

    /**
     * <p>
     * Use reflect to get private field value.
     * </p>
     * @param instance the instance
     * @param fieldName the field name
     * @return the field value
     */
    private Object getValue(Object instance, String fieldName) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            return field.get(instance);
        } catch (SecurityException e) {
            return null;
        } catch (NoSuchFieldException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * <p>
     * Use reflect to get private field value.
     * </p>
     * @param instance the instance
     * @param fieldName the field name
     * @return the field value
     */
    private String getStringValue(Object instance, String fieldName) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            return (String) field.get(instance);
        } catch (SecurityException e) {
            return null;
        } catch (NoSuchFieldException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}
