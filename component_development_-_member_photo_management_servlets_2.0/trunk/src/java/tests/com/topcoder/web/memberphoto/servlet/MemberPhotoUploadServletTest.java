/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;

import java.io.File;
import java.lang.reflect.Field;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.web.memberphoto.manager.MemberPhotoManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for <code>MemberPhotoUploadServlet</code> class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberPhotoUploadServletTest extends TestCase {
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

    static {
        ConfigManager configManager = ConfigManager.getInstance();

        try {
            configManager.add("test_files/demoConfig.xml");
            configManager
                    .add("test_files/MimeTypeAndFileExtensionValidator.xml");
        } catch (ConfigManagerException e) {
            //ignore
        }
    }

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(MemberPhotoUploadServletTest.class);
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
     * Accuracy test for constructor <code>MemberPhotoUploadServlet()</code>.
     * </p>
     * <p>
     * Verify that the instance can be created successfully.
     * </p>
     */
    public void testEmptyCtorAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        assertNotNull("Unable to create MemberPhotoUploadServlet instance.",
                instance);
        assertTrue("Should be instance of MemberPhotoUploadServlet.",
                instance instanceof MemberPhotoUploadServlet);
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
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
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
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

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
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

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
     * <code>setPhotoImageFormFileName(photoImageFormFileName)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetPhotoImageFormFileNameAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setPhotoImageFormFileName(TEST);

        String value = getStringValue(instance, "photoImageFormFileName");
        assertEquals("'photoImageFormFileName' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPhotoImageFormFileName(photoImageFormFileName)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetPhotoImageFormFileNameFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setPhotoImageFormFileName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPhotoImageFormFileName(photoImageFormFileName)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetPhotoImageFormFileNameFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setPhotoImageFormFileName(" ");
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
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        MemberPhotoManager manager = new MockMemberPhotoManagerImpl();
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
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

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
     * <code>setMemberIdSessionKey(memberIdSessionKey)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetMemberIdSessionKeyAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setMemberIdSessionKey(TEST);

        String value = getStringValue(instance, "memberIdSessionKey");
        assertEquals("'memberIdSessionKey' should be the same.", TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMemberIdSessionKey(memberIdSessionKey)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetMemberIdSessionKeyFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setMemberIdSessionKey(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMemberIdSessionKey(memberIdSessionKey)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetMemberIdSessionKeyFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setMemberIdSessionKey(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setMemberIdErrorName(memberIdErrorName)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetMemberIdErrorNameAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setMemberIdErrorName(TEST);

        String value = getStringValue(instance, "memberIdErrorName");
        assertEquals("'memberIdErrorName' should be the same.", TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMemberIdErrorName(memberIdErrorName)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetMemberIdErrorNameFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setMemberIdErrorName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMemberIdErrorName(memberIdErrorName)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetMemberIdErrorNameFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setMemberIdErrorName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setPhotoImageErrorName(photoImageErrorName)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetPhotoImageErrorNameAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setPhotoImageErrorName(TEST);

        String value = getStringValue(instance, "photoImageErrorName");
        assertEquals("'photoImageErrorName' should be the same.", TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPhotoImageErrorName(photoImageErrorName)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetPhotoImageErrorNameFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setPhotoImageErrorName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPhotoImageErrorName(photoImageErrorName)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetPhotoImageErrorNameFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setPhotoImageErrorName(" ");
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
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
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
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

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
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

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
     * <code>setTargetImageWidth(targetImageWidth)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetTargetImageWidthAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setTargetImageWidth(622);

        int value = (Integer) getValue(instance, "targetImageWidth");
        assertEquals("'targetImageWidth' should be the same.", 622, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setTargetImageWidth(targetImageWidth)</code>.
     * </p>
     * <p>
     * parameter is not positive, IAE should be thrown.
     * </p>
     */
    public void testSetTargetImageWidthFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setTargetImageWidth(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setTargetImageHeight(targetImageHeight)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetTargetImageHeightAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setTargetImageHeight(622);

        int value = (Integer) getValue(instance, "targetImageHeight");
        assertEquals("'targetImageHeight' should be the same.", 622, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setTargetImageHeight(targetImageHeight)</code>.
     * </p>
     * <p>
     * parameter is not positive, IAE should be thrown.
     * </p>
     */
    public void testSetTargetImageHeightFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setTargetImageHeight(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setPhotoImageSubmittedDirectory(photoImageSubmittedDirectory)</code>
     * .
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetPhotoImageSubmittedDirectoryAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setPhotoImageSubmittedDirectory(TEST);

        String value = getStringValue(instance, "photoImageSubmittedDirectory");
        assertEquals("'photoImageSubmittedDirectory' should be the same.",
                TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPhotoImageSubmittedDirectory(photoImageSubmittedDirectory)</code>
     * .
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetPhotoImageSubmittedDirectoryFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setPhotoImageSubmittedDirectory(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPhotoImageSubmittedDirectory(photoImageSubmittedDirectory)</code>
     * .
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetPhotoImageSubmittedDirectoryFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setPhotoImageSubmittedDirectory(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setPhotoImagePreviewDirectory(photoImagePreviewDirectory)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetPhotoImagePreviewDirectoryAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setPhotoImagePreviewDirectory(TEST);

        String value = getStringValue(instance, "photoImagePreviewDirectory");
        assertEquals("'photoImagePreviewDirectory' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPhotoImagePreviewDirectory(photoImagePreviewDirectory)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetPhotoImagePreviewDirectoryFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setPhotoImagePreviewDirectory(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPhotoImagePreviewDirectory(photoImagePreviewDirectory)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetPhotoImagePreviewDirectoryFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setPhotoImagePreviewDirectory(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setPreviewPhotoImagePathName(previewPhotoImagePathName)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetPreviewPhotoImagePathNameAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setPreviewPhotoImagePathName(TEST);

        String value = getStringValue(instance, "previewPhotoImagePathName");
        assertEquals("'previewPhotoImagePathName' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPreviewPhotoImagePathName(previewPhotoImagePathName)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetPreviewPhotoImagePathNameFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setPreviewPhotoImagePathName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPreviewPhotoImagePathName(previewPhotoImagePathName)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetPreviewPhotoImagePathNameFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setPreviewPhotoImagePathName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setPreviewForwardUrl(previewForwardUrl)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetPreviewForwardUrlAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setPreviewForwardUrl(TEST);

        String value = getStringValue(instance, "previewForwardUrl");
        assertEquals("'previewForwardUrl' should be the same.", TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPreviewForwardUrl(previewForwardUrl)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetPreviewForwardUrlFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setPreviewForwardUrl(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPreviewForwardUrl(previewForwardUrl)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetPreviewForwardUrlFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setPreviewForwardUrl(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setSubmittedRedirectUrl(submittedRedirectUrl)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetSubmittedRedirectUrlAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setSubmittedRedirectUrl(TEST);

        String value = getStringValue(instance, "submittedRedirectUrl");
        assertEquals("'submittedRedirectUrl' should be the same.", TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setSubmittedRedirectUrl(submittedRedirectUrl)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetSubmittedRedirectUrlFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setSubmittedRedirectUrl(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setSubmittedRedirectUrl(submittedRedirectUrl)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetSubmittedRedirectUrlFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setSubmittedRedirectUrl(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setFileUploaderNamespace(fileUploaderNamespace)</code>.
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetFileUploaderNamespaceAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setFileUploaderNamespace(TEST);

        String value = getStringValue(instance, "fileUploaderNamespace");
        assertEquals("'fileUploaderNamespace' should be the same.", TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setFileUploaderNamespace(fileUploaderNamespace)</code>.
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetFileUploaderNamespaceFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setFileUploaderNamespace(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setFileUploaderNamespace(fileUploaderNamespace)</code>.
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetFileUploaderNamespaceFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setFileUploaderNamespace(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setSubmittedActionParameterName(String submittedActionParameterName)</code>
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetSubmittedActionParameterNameAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setSubmittedActionParameterName(TEST);

        String value = getStringValue(instance, "submittedActionParameterName");
        assertEquals("'submittedActionParameterName' should be the same.",
                TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setSubmittedActionParameterName(String submittedActionParameterName)</code>
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetSubmittedActionParameterNameFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setSubmittedActionParameterName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setSubmittedActionParameterName(String submittedActionParameterName)</code>
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetSubmittedActionParameterNameFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setSubmittedActionParameterName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setTargetImageLeftCornerXParameterName(String targetImageLeftCornerXParameterName)</code>
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetTargetImageLeftCornerXParameterNameAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setTargetImageLeftCornerXParameterName(TEST);

        String value =
                getStringValue(instance, "targetImageLeftCornerXParameterName");
        assertEquals(
                "'targetImageLeftCornerXParameterName' should be the same.",
                TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setTargetImageLeftCornerXParameterName(String targetImageLeftCornerXParameterName)</code>
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetTargetImageLeftCornerXParameterNameFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setTargetImageLeftCornerXParameterName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setTargetImageLeftCornerXParameterName(String targetImageLeftCornerXParameterName)</code>
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetTargetImageLeftCornerXParameterNameFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setSubmittedActionParameterName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setTargetImageLeftCornerYParameterName(String targetImageLeftCornerYParameterName)</code>
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetTargetImageLeftCornerYParameterNameAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setTargetImageLeftCornerYParameterName(TEST);

        String value =
                getStringValue(instance, "targetImageLeftCornerYParameterName");
        assertEquals(
                "'targetImageLeftCornerYParameterName' should be the same.",
                TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setTargetImageLeftCornerYParameterName(String targetImageLeftCornerYParameterName)</code>
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetTargetImageLeftCornerYParameterNameFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setTargetImageLeftCornerYParameterName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setTargetImageLeftCornerYParameterName(String targetImageLeftCornerYParameterName)</code>
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetTargetImageLeftCornerYParameterNameFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setSubmittedActionParameterName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setTargetImageRightCornerYParameterName(String targetImageRightCornerYParameterName)</code>
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetTargetImageRightCornerYParameterNameAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setTargetImageRightCornerYParameterName(TEST);

        String value =
                getStringValue(instance, "targetImageRightCornerYParameterName");
        assertEquals(
                "'targetImageRightCornerYParameterName' should be the same.",
                TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setTargetImageRightCornerYParameterName(String targetImageRightCornerYParameterName)</code>
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetTargetImageRightCornerYParameterNameFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setTargetImageRightCornerYParameterName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setTargetImageRightCornerYParameterName(String targetImageRightCornerYParameterName)</code>
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetTargetImageRightCornerYParameterNameFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setSubmittedActionParameterName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setTargetImageRightCornerXParameterName(String targetImageRightCornerXParameterName)</code>
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetTargetImageRightCornerXParameterNameAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setTargetImageRightCornerXParameterName(TEST);

        String value =
                getStringValue(instance, "targetImageRightCornerXParameterName");
        assertEquals(
                "'targetImageRightCornerXParameterName' should be the same.",
                TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setTargetImageRightCornerXParameterName(String targetImageRightCornerXParameterName)</code>
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetTargetImageRightCornerXParameterNameFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setTargetImageRightCornerXParameterName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setTargetImageRightCornerXParameterName(String targetImageRightCornerXParameterName)</code>
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetTargetImageRightCornerXParameterNameFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setSubmittedActionParameterName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setPhotoImageCropDirectory(String photoImageCropDirectory)</code>
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetPhotoImageCropDirectoryAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setPhotoImageCropDirectory(TEST);

        String value = getStringValue(instance, "photoImageCropDirectory");
        assertEquals("'photoImageCropDirectory' should be the same.", TEST,
                value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPhotoImageCropDirectory(String photoImageCropDirectory)</code>
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetPhotoImageCropDirectoryFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setPhotoImageCropDirectory(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setPhotoImageCropDirectory(String photoImageCropDirectory)</code>
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetPhotoImageCropDirectoryFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setPhotoImageCropDirectory(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setMaxFileSize(long maxFileSize)</code>
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetMaxFileSizeAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setMaxFileSize(1);

        long value = (Long) getValue(instance, "maxFileSize");
        assertEquals("'maxFileSize' should be the same.", 1, value);
    }

    /**
     * <p>
     * Failure test for method <code>setMaxFileSize(long maxFileSize)</code>
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetMaxFileSizeFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setMaxFileSize(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>setMaxFileSize(long maxFileSize)</code>
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetMaxFileSizeFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setMaxFileSize(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method
     * <code>setMaxFileExceededErrorName(String maxFileExceededErrorName)</code>
     * </p>
     * <p>
     * Verify that can set field successfully.
     * </p>
     */
    public void testSetMaxFileExceededErrorNameAccuracy() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();
        instance.setMaxFileExceededErrorName(TEST);

        String value = getStringValue(instance, "maxFileExceededErrorName");
        assertEquals("'maxFileSize' should be the same.", TEST, value);
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMaxFileExceededErrorName(String maxFileExceededErrorName)</code>
     * </p>
     * <p>
     * parameter is null, IAE should be thrown.
     * </p>
     */
    public void testSetMaxFileExceededErrorNameFailure1() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setMaxFileExceededErrorName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method
     * <code>setMaxFileExceededErrorName(String maxFileExceededErrorName)</code>
     * </p>
     * <p>
     * parameter is empty string, IAE should be thrown.
     * </p>
     */
    public void testSetMaxFileExceededErrorNameFailure2() {
        MemberPhotoUploadServlet instance = new MemberPhotoUploadServlet();

        try {
            instance.setMaxFileExceededErrorName(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * Verify that can handle the request and response correctly.
     * </p>
     * <p>
     * submitted flag is true, use 'submitted' directory.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostAccuracy1() throws Exception {
        MemberPhotoUploadServlet bean = TestHelper.genUploadServletBean();
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setParameter("submit_action", "commit");
        request.setParameter("member_id", "717");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);
        bean.doPost(request, response);
        assertTrue("File should exist.", new File(TEST_FILES
                + "submitted/717.gif").exists());
        assertNull("forward url should be null.", response.getForwardedUrl());
    }

    /**
     * <p>
     * Accuracy test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * Verify that can handle the request and response correctly.
     * </p>
     * <p>
     * submitted flag is true, use 'submitted' directory.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostAccuracy2() throws Exception {
        MemberPhotoUploadServlet bean = TestHelper.genUploadServletBean();
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setParameter("submit_action", "preview");
        request.setParameter("member_id", "717");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);
        bean.doPost(request, response);
        assertTrue("File should exist.", new File(TEST_FILES
                + "preview/717.gif").exists());
        assertEquals("forward url should be the same.", "/preview",
                response.getForwardedUrl());
    }

    /**
     * <p>
     * Accuracy test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * Verify that can handle the request and response correctly.
     * </p>
     * <p>
     * submitted flag is true, use 'submitted' directory.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostAccuracy3() throws Exception {
        MemberPhotoUploadServlet bean = TestHelper.genUploadServletBean();
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setParameter("submit_action", "crop");
        request.setParameter("member_id", "717");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);
        bean.doPost(request, response);
        assertTrue("File should exist.",
                new File(TEST_FILES + "crop/717.gif").exists());
        assertNull("forward url should be null.", response.getForwardedUrl());
    }

    /**
     * <p>
     * Accuracy test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * Verify that can handle the request and response correctly.
     * </p>
     * <p>
     * submitted flag is true, use 'submitted' directory.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostAccuracy4() throws Exception {
        MemberPhotoUploadServlet bean = TestHelper.genUploadServletBean();
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setParameter("submit_action", "none");
        request.setParameter("member_id", "717");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);
        bean.doPost(request, response);
        assertTrue("File should exist.", new File(TEST_FILES
                + "preview/717.gif").exists());
        assertEquals("forward url should be the same.", "/preview",
                response.getForwardedUrl());
    }

    /**
     * <p>
     * Accuracy test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * Verify that can handle the request and response correctly.
     * </p>
     * <p>
     * no need to resize the image.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostAccuracy5() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_valid_2");

        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("submit_action", "preview");
        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);

        bean.doPost(request, response);
        assertTrue("File should exist.", new File(TEST_FILES
                + "preview/717.jpeg").exists());
        assertEquals("forward url should be the same.", "/preview",
                response.getForwardedUrl());
    }

    /**
     * <p>
     * Accuracy test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * Verify that can handle the request and response correctly.
     * </p>
     * <p>
     * upload file is neither "jpeg" or "png" or "gif".
     * </p>
     * <p>
     * forward to validation error URL.
     * </p>
     * @throws Exception to JUnit
     */
    /*
     * public void testDoPostAccuracy6() throws Exception {
     * MemberPhotoUploadServlet bean =
     * TestHelper.genUploadServletBean("upload_valid_3");
     * MockHttpServletRequest request = TestHelper.prepareRequest();
     * MockHttpServletResponse response = new MockHttpServletResponse();
     * request.setParameter("submit_action", "preview");
     * request.setParameter("member_id", "717");
     * MockHttpSession session = new MockHttpSession();
     * session.setAttribute("member_id_session_key", new Long(717));
     * request.setSession(session);
     * bean.doPost(request, response);
     * assertEquals("error message should be the same.",
     * "The image format is not supported.",
     * request.getAttribute("photo_image_error_name").toString());
     * assertEquals("forward url should be the same.",
     * "validation_error_forward_url", response.getForwardedUrl());
     * }
     */

    /**
     * <p>
     * Accuracy test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * Verify that can handle the request and response correctly.
     * </p>
     * <p>
     * 'memberId' not equals to 'member session id'.
     * </p>
     * <p>
     * forward to validation error URL.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostAccuracy7() throws Exception {
        MemberPhotoUploadServlet bean = TestHelper.genUploadServletBean();

        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("submit_action", "preview");
        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(622));
        request.setSession(session);

        bean.doPost(request, response);
        assertEquals("error message should be the same.",
                "The member id doesn't match the one in session.", request
                        .getAttribute("member_id_error_name").toString());
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
     * target image width is less than target image height.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostAccuracy8() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_valid_1");

        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("submit_action", "preview");
        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);

        bean.doPost(request, response);
        assertTrue("File should exist.", new File(TEST_FILES
                + "preview/717.jpeg").exists());
        assertEquals("forward url should be the same.", "/preview",
                response.getForwardedUrl());
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * request is null ,IAE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure1() throws Exception {
        MemberPhotoUploadServlet bean = TestHelper.genUploadServletBean();
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
     * response is null ,IAE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure2() throws Exception {
        MemberPhotoUploadServlet bean = TestHelper.genUploadServletBean();
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
     * @throws Exception to JUnit
     */
    public void testDoPostFailure3() throws Exception {
        // ignore
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
    public void testDoPostFailure4() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_2");
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
     * 'memberIdSessionKey' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure5() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_3");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

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
     * 'member_id_session_key' is null, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure6() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_4");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", null);
        request.setSession(session);

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
     * 'member_id_session_key' is not valid long value, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure7() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_4");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", "not_long_value");
        request.setSession(session);

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
     * 'fileUploaderNamespace' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure8() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_5");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);

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
     * 'photoImageFormFileName' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure9() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_6");
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);

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
     * 'photoImageErrorName' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure10() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_7");
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);

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
     * 'memberIdErrorName' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure11() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_8");
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);

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
     * 'validationErrorForwardUrl' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure12() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_9");
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);

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
     * 'targetImageWidth' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure13() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_10");
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);

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
     * 'targetImageHeigth' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure14() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_12");
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);

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
     * 'photoImageSubmittedDirectory' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure15() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_14");
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);

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
     * 'photoImagePreviewDirectory' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure16() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_15");
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);

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
     * 'previewPhotoImagePathName' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure17() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_16");
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);

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
     * 'previewForwardUrl' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure18() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_17");
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);

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
     * 'submittedRedirectUrl' does not exist, ISE should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure19() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_18");
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("member_id", "717");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);

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
     * member is not a number, MemberPhotoUploadException should be thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure20() throws Exception {
        MemberPhotoUploadServlet bean = TestHelper.genUploadServletBean();
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setParameter("submit_action", "preview");
        request.setParameter("member_id", "not_a_number");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);
        try {
            bean.doPost(request, response);
            fail("MemberPhotoUploadException should be thrown.");
        } catch (MemberPhotoUploadException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * <p>
     * member_id_session_key is not a number, IllegalStateException should be
     * thrown.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure21() throws Exception {
        MemberPhotoUploadServlet bean = TestHelper.genUploadServletBean();
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setParameter("submit_action", "preview");
        request.setParameter("member_id", "77");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", "Not number");
        request.setSession(session);
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
     * uploadedFile.getSize() > maxFileSize
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure23() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_20");
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setParameter("submit_action", "commit");
        request.setParameter("member_id", "717");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", new Long(717));
        request.setSession(session);
        bean.doPost(request, response);
    }

    /**
     * <p>
     * Failure test for method <code>doPost(request,response)</code>.
     * </p>
     * @throws Exception to JUnit
     */
    public void testDoPostFailure24() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_21");
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setParameter("submit_action", "preview");
        request.setParameter("member_id", "77");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", "Not number");
        request.setSession(session);
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
     * @throws Exception to JUnit
     */
    public void testDoPostFailure25() throws Exception {
        MemberPhotoUploadServlet bean =
                TestHelper.genUploadServletBean("upload_invalid_22");
        MockHttpServletRequest request = TestHelper.prepareRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setParameter("submit_action", "preview");
        request.setParameter("member_id", "77");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member_id_session_key", "Not number");
        request.setSession(session);
        try {
            bean.doPost(request, response);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
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
}
