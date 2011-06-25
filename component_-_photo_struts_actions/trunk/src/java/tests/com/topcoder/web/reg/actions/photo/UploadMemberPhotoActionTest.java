/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import com.topcoder.util.log.LogManager;

/**
 * <p> Unit test case of {@link UploadMemberPhotoAction}. </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class UploadMemberPhotoActionTest extends BaseUnitTest {
    /**
     * <p>
     * The UploadMemberPhotoAction instance to test.
     * </p>
     * */
    private UploadMemberPhotoAction instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new UploadMemberPhotoAction();
        instance.setAuditDAO(new MockAuditDAO());
        instance.setMemberPhotoManager(new MockMemberPhotoManager());
        instance.setUserDAO(new MockUserDAO());
        instance.setPhotoStoredDirectory("test_files/stored");
        instance.setLog(LogManager.getLog());
        instance.setAuthenticationSessionKey("authenticationkey");
        instance.setSubmittedActionParameterName("actionname");
        instance.setOriginalImageFileNameParameterName("originalImageParam");
        instance.setUploadFileName("default.gif");
        instance.setUploadContentType("gif");
        instance.setUpload(new File("test_files/stored/default.gif"));
        instance.setPreviewImageFileNameParameterName("previewImageParam");
        instance.setPhotoPreviewDirectory("test_files/previewed");
        Map<String, String> extensionsMap = new HashMap<String, String>();
        extensionsMap.put("png", "png");
        extensionsMap.put("gif", "gif");
        extensionsMap.put("jpg", "jpg");
        instance.setAllowedContentTypesFileExtensions(extensionsMap);

        instance.setTargetImageRightCornerYParameterName("rightY");
        instance.setTargetImageRightCornerXParameterName("rightX");
        instance.setTargetImageLeftCornerYParameterName("leftY");
        instance.setTargetImageLeftCornerXParameterName("leftX");

        instance.setTargetImageWidth(80);
        instance.setTargetImageHeight(60);
        instance.checkParameters();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p> Creates a test suite for the tests in this test case. </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(UploadMemberPhotoActionTest.class);
        return suite;
    }

    /**
     * <p>
     * Accuracy test for constructor UploadMemberPhotoAction.
     * It verifies instance is correctly created.
     * </p>
     * @throws Exception to Junit
     */
    public void test_UploadMemberPhotoAction0() throws Exception {
        assertNotNull("The instance should not be null", instance);
    }

    /**
     * <p>
     * Accuracy test for method execute.
     *
     * It verifies the preview mode works well.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute() throws Exception {
        //restore the image for removing
        fileCopy("test_files/stored/default.bak.gif", "test_files/stored/default.gif");

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        session.setAttribute("authenticationkey", new MockBasicAuthentication());
        instance.setServletRequest(request);

        String res = instance.execute();

        assertEquals("Incorrect result", "previewSuccess", res);
        assertEquals("Incorrect result", "&previewImageParam=handle100.gif&originalImageParam=default.gif",
                instance.getPreviewSuccessUrlSuffix());

        assertFalse("The uploaded image should be moved to dir 'previewed'", new File(
                "test_files/stored/default.gif").isFile());
        assertTrue("The uploaded image should be moved to dir 'previewed'", new File(
                "test_files/previewed/handle100.gif").isFile());
    }

    /**
     * <p>
     * Accuracy test for method execute.
     *
     * It verifies the upload mode works well when  the crop is not performed, and scaling is not performed.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute2() throws Exception {
        //prepare the preview image
        fileCopy("test_files/stored/default.bak.gif", "test_files/previewed/toCommit.gif");
        //this file should not exist
        new File("test_files/stored/toCommit.gif").delete();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        session.setAttribute("authenticationkey", new MockBasicAuthentication());
        instance.setServletRequest(request);

        request.setParameter("previewImageParam", "toCommit.gif");
        request.setParameter("actionname", "commit");
        instance.setTargetImageWidth(80);
        instance.setTargetImageHeight(60);

        String res = instance.execute();

        assertEquals("Incorrect result", "success", res);
        assertTrue("The uploaded image should be copied to dir 'stored'", new File(
                "test_files/stored/toCommit.gif").isFile());
        //remove the uploaded file
        new File("test_files/stored/toCommit.gif").delete();
    }

    /**
     * <p>
     * Accuracy test for method execute.
     *
     * It verifies the upload mode works well when the crop is not performed, and scaling is performed.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute4() throws Exception {
        //prepare the preview image
        fileCopy("test_files/stored/default.bak.gif", "test_files/previewed/toCommit.gif");
        //this file should not exist
        new File("test_files/stored/toCommit.gif").delete();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        session.setAttribute("authenticationkey", new MockBasicAuthentication());
        instance.setServletRequest(request);

        request.setParameter("previewImageParam", "toCommit.gif");
        request.setParameter("actionname", "commit");
        instance.setTargetImageWidth(40);
        instance.setTargetImageHeight(30);
        //not used
        request.setParameter("rightY", "x");

        String res = instance.execute();

        assertEquals("Incorrect result", "success", res);
        assertTrue("The uploaded image should be copied to dir 'stored'", new File(
                "test_files/stored/toCommit.gif").isFile());
        //remove the uploaded file
        new File("test_files/stored/toCommit.gif").delete();
    }

    /**
     * <p>
     * Accuracy test for method execute.
     *
     * It verifies the upload mode works well when  the crop is  performed.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute5() throws Exception {
        instance.setTargetImageWidth(40);
        instance.setTargetImageHeight(30);

        //prepare the preview image
        fileCopy("test_files/stored/default.bak.gif", "test_files/previewed/toCommit.gif");
        //this file should not exist
        new File("test_files/stored/toCommit.gif").delete();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        session.setAttribute("authenticationkey", new MockBasicAuthentication());
        instance.setServletRequest(request);

        request.setParameter("previewImageParam", "toCommit.gif");
        request.setParameter("actionname", "commit");
        request.setParameter("rightY", "40");
        request.setParameter("leftY", "20");
        request.setParameter("rightX", "60");
        request.setParameter("leftX", "20");

        String res = instance.execute();

        assertEquals("Incorrect result", "success", res);
        assertTrue("The uploaded image should be copied to dir 'stored'", new File(
                "test_files/stored/toCommit.gif").isFile());
        //remove the uploaded file
        new File("test_files/stored/toCommit.gif").delete();
    }

    /**
     * <p>
     * Failure test for method execute().
     *
     * Expects {@link MemberPhotoUploadException} if BasicAuthentication is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute8() throws Exception {
        //prepare the preview image
        fileCopy("test_files/stored/default.bak.gif", "test_files/previewed/toCommit.gif");
        //this file should not exist
        new File("test_files/stored/toCommit.gif").delete();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        instance.setServletRequest(request);

        request.setParameter("previewImageParam", "toCommit.gif");
        request.setParameter("actionname", "commit");
        instance.setTargetImageWidth(40);
        instance.setTargetImageHeight(30);

        try {
            instance.execute();
            fail("Expects an Exception");
        } catch (MemberPhotoUploadException e) {
            // pass
        }
    }

    /**
      * <p>
      * Failure test for method execute().
      *
      * Expects {@link MemberPhotoUploadException} if UploadContentType is not allowed.
      * </p>
      * @throws Exception to Junit
      */
    public void test_execute9() throws Exception {
        //restore the image for removing
        fileCopy("test_files/stored/default.bak.gif", "test_files/stored/default.gif");

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        session.setAttribute("authenticationkey", new MockBasicAuthentication());
        instance.setServletRequest(request);
        instance.setUploadContentType("invalid");

        try {
            instance.execute();
            fail("Expects an Exception");
        } catch (MemberPhotoUploadException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method execute().
     *
     * Expects {@link MemberPhotoUploadException} if fail to extract file extension.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute10() throws Exception {
        instance.setTargetImageWidth(40);
        instance.setTargetImageHeight(30);

        //prepare the preview image
        fileCopy("test_files/stored/default.bak.gif", "test_files/previewed/toCommit.gif");
        //this file should not exist
        new File("test_files/stored/toCommit.gif").delete();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        session.setAttribute("authenticationkey", new MockBasicAuthentication());
        instance.setServletRequest(request);

        request.setParameter("previewImageParam", "toCommit");
        request.setParameter("actionname", "commit");
        request.setParameter("rightY", "40");
        request.setParameter("leftY", "20");
        request.setParameter("rightX", "60");
        request.setParameter("leftX", "20");

        try {
            instance.execute();
            fail("Expects an Exception");
        } catch (MemberPhotoUploadException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method execute().
     *
     * Expects {@link MemberPhotoUploadException} if file to preview doesn't exist.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute11() throws Exception {
        instance.setTargetImageWidth(40);
        instance.setTargetImageHeight(30);

        //prepare the preview image
        fileCopy("test_files/stored/default.bak.gif", "test_files/previewed/toCommit.gif");
        //this file should not exist
        new File("test_files/stored/toCommit.gif").delete();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        session.setAttribute("authenticationkey", new MockBasicAuthentication());
        instance.setServletRequest(request);

        request.setParameter("previewImageParam", "notexist.gif");
        request.setParameter("actionname", "commit");
        request.setParameter("rightY", "40");
        request.setParameter("leftY", "20");
        request.setParameter("rightX", "60");
        request.setParameter("leftX", "20");

        try {
            instance.execute();
            fail("Expects an Exception");
        } catch (MemberPhotoUploadException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#setServletRequest()}. It verifies the assigned value is
     * correct.
     * </p>
     * @throws Exception to junit
     */
    public void test_setServletRequest() throws Exception {
        // set a value
        HttpServletRequest request = new MockHttpServletRequest();
        instance.setServletRequest(request);
        assertEquals("Incorrect object after set a new one", request, outject(instance.getClass(), instance,
                "request"));
    }

    /**
     * <p>
     * Accuracy test for method checkParameters().
     * No error occurs if the instance is configured well.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters0() throws Exception {
        instance.checkParameters();
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#getTargetImageHeight()}. It verifies the
     * returned value is correct.
     * </p>
     */
    public void testGetTargetImageHeight() {
        // set a value
        instance.setTargetImageHeight(1);
        assertEquals("Incorrect value after set a new one", 1, instance.getTargetImageHeight());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#setTargetImageHeight()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetTargetImageHeight() {
        // set a value
        instance.setTargetImageHeight(1);
        assertEquals("Incorrect value after set a new one", 1, instance.getTargetImageHeight());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#getTargetImageWidth()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetTargetImageWidth() {
        // set a value
        instance.setTargetImageWidth(1);
        assertEquals("Incorrect value after set a new one", 1, instance.getTargetImageWidth());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#setTargetImageWidth()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetTargetImageWidth() {
        // set a value
        instance.setTargetImageWidth(1);
        assertEquals("Incorrect value after set a new one", 1, instance.getTargetImageWidth());
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#setUploadFileName()}. It verifies the assigned value is
     * correct.
     * </p>
     * @throws Exception to junit
     */
    public void test_setUploadFileName() throws Exception {
        // set a value
        instance.setUploadFileName("test");
        assertEquals("Incorrect value after set a new one", "test", outject(instance.getClass(), instance,
                "filename"));
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#setUploadContentType()}. It verifies the assigned value is
     * correct.
     * </p>
     * @throws Exception to junit
     */
    public void test_setUploadContentType() throws Exception {
        // set a value
        instance.setUploadContentType("test");
        assertEquals("Incorrect value after set a new one", "test", outject(instance.getClass(), instance,
                "contentType"));
    }

    /**
     * <p>
     * Test method for {@link RemoveMemberPhotoAction#setUpload()}. It verifies the assigned value is
     * correct.
     * </p>
     * @throws Exception to junit
     */
    public void test_setUpload() throws Exception {
        File file = new File("test_files");
        // set a value
        instance.setUpload(file);
        assertEquals("Incorrect value after set a new one", file, outject(instance.getClass(), instance,
                "file"));
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#getTargetImageRightCornerYParameterName()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetTargetImageRightCornerYParameterName() {
        // set a value
        String obj = "test";
        instance.setTargetImageRightCornerYParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance
                .getTargetImageRightCornerYParameterName());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#setTargetImageRightCornerYParameterName()}.
     * It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetTargetImageRightCornerYParameterName() {
        // set a value
        String obj = "test";
        instance.setTargetImageRightCornerYParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance
                .getTargetImageRightCornerYParameterName());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#getTargetImageRightCornerXParameterName()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetTargetImageRightCornerXParameterName() {
        // set a value
        String obj = "test";
        instance.setTargetImageRightCornerXParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance
                .getTargetImageRightCornerXParameterName());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#setTargetImageRightCornerXParameterName()}.
     * It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetTargetImageRightCornerXParameterName() {
        // set a value
        String obj = "test";
        instance.setTargetImageRightCornerXParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance
                .getTargetImageRightCornerXParameterName());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#getTargetImageLeftCornerYParameterName()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetTargetImageLeftCornerYParameterName() {
        // set a value
        String obj = "test";
        instance.setTargetImageLeftCornerYParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance
                .getTargetImageLeftCornerYParameterName());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#setTargetImageLeftCornerYParameterName()}.
     * It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetTargetImageLeftCornerYParameterName() {
        // set a value
        String obj = "test";
        instance.setTargetImageLeftCornerYParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance
                .getTargetImageLeftCornerYParameterName());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#getTargetImageLeftCornerXParameterName()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetTargetImageLeftCornerXParameterName() {
        // set a value
        String obj = "test";
        instance.setTargetImageLeftCornerXParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance
                .getTargetImageLeftCornerXParameterName());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#setTargetImageLeftCornerXParameterName()}.
     * It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetTargetImageLeftCornerXParameterName() {
        // set a value
        String obj = "test";
        instance.setTargetImageLeftCornerXParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance
                .getTargetImageLeftCornerXParameterName());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#getOriginalImageFileNameParameterName()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetOriginalImageFileNameParameterName() {
        // set a value
        String obj = "test";
        instance.setOriginalImageFileNameParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance
                .getOriginalImageFileNameParameterName());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#setOriginalImageFileNameParameterName()}.
     * It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetOriginalImageFileNameParameterName() {
        // set a value
        String obj = "test";
        instance.setOriginalImageFileNameParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance
                .getOriginalImageFileNameParameterName());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#getPreviewImageFileNameParameterName()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetPreviewImageFileNameParameterName() {
        // set a value
        String obj = "test";
        instance.setPreviewImageFileNameParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance
                .getPreviewImageFileNameParameterName());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#setPreviewImageFileNameParameterName()}.
     * It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetPreviewImageFileNameParameterName() {
        // set a value
        String obj = "test";
        instance.setPreviewImageFileNameParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance
                .getPreviewImageFileNameParameterName());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#getPhotoPreviewDirectory()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetPhotoPreviewDirectory() {
        // set a value
        String obj = "test";
        instance.setPhotoPreviewDirectory(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getPhotoPreviewDirectory());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#setPhotoPreviewDirectory()}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetPhotoPreviewDirectory() {
        // set a value
        String obj = "test";
        instance.setPhotoPreviewDirectory(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getPhotoPreviewDirectory());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#getSubmittedActionParameterName()}.
     * It verifies the returned value is correct.
     * </p>
     */
    public void testGetSubmittedActionParameterName() {
        // set a value
        String obj = "test";
        instance.setSubmittedActionParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getSubmittedActionParameterName());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#setSubmittedActionParameterName()}.
     * It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetSubmittedActionParameterName() {
        // set a value
        String obj = "test";
        instance.setSubmittedActionParameterName(obj);
        assertEquals("Incorrect object after set a new one", obj, instance.getSubmittedActionParameterName());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#getRreviewSuccessUrlSuffix()}.
     *  It verifies the initial value is correct.
     * </p>
     */
    public void testGetRreviewSuccessUrlSuffix() {
        assertEquals("Incorrect initial value", null, instance.getPreviewSuccessUrlSuffix());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#getAllowedContentTypesFileExtensions()}.
     *  It verifies the returned value is correct.
     * </p>
     */
    public void testGetAllowedContentTypesFileExtensions() {
        // set a value
        Map<String, String> obj = new HashMap<String, String>();
        instance.setAllowedContentTypesFileExtensions(obj);
        assertEquals("Incorrect object after set a new one", obj, instance
                .getAllowedContentTypesFileExtensions());
    }

    /**
     * <p>
     * Test method for {@link UploadMemberPhotoAction#setAllowedContentTypesFileExtensions()}.
     * It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetAllowedContentTypesFileExtensions() {
        // set a value
        Map<String, String> obj = new HashMap<String, String>();
        instance.setAllowedContentTypesFileExtensions(obj);
        assertEquals("Incorrect object after set a new one", obj, instance
                .getAllowedContentTypesFileExtensions());
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if SubmittedActionParameterName is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters11() throws Exception {
        instance.setSubmittedActionParameterName(null);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if SubmittedActionParameterName is empty.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters12() throws Exception {
        instance.setSubmittedActionParameterName(EMPTY);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if AllowedContentTypesFileExtensions is null.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters3() throws Exception {
        instance.setAllowedContentTypesFileExtensions(null);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if AllowedContentTypesFileExtensions is empty.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters4() throws Exception {
        instance.setAllowedContentTypesFileExtensions(new HashMap<String, String>());
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if AllowedContentTypesFileExtensions has null keys.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters5() throws Exception {
        HashMap<String, String> allowedContentTypesFileExtensions = new HashMap<String, String>();
        allowedContentTypesFileExtensions.put(null, "test");
        instance.setAllowedContentTypesFileExtensions(allowedContentTypesFileExtensions);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if AllowedContentTypesFileExtensions has empty keys.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters6() throws Exception {
        HashMap<String, String> allowedContentTypesFileExtensions = new HashMap<String, String>();
        allowedContentTypesFileExtensions.put(EMPTY, "test");
        instance.setAllowedContentTypesFileExtensions(allowedContentTypesFileExtensions);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if AllowedContentTypesFileExtensions has null values.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters7() throws Exception {
        HashMap<String, String> allowedContentTypesFileExtensions = new HashMap<String, String>();
        allowedContentTypesFileExtensions.put("test", null);
        instance.setAllowedContentTypesFileExtensions(allowedContentTypesFileExtensions);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if AllowedContentTypesFileExtensions has empty values.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters8() throws Exception {
        HashMap<String, String> allowedContentTypesFileExtensions = new HashMap<String, String>();
        allowedContentTypesFileExtensions.put("test", EMPTY);
        instance.setAllowedContentTypesFileExtensions(allowedContentTypesFileExtensions);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for method checkParameters against invalid configuration.
     * Expects {@link MemberPhotoActionConfigurationException} if targetImageWidth is not positive.
     * </p>
     * @throws Exception to Junit
     */
    public void test_checkParameters9() throws Exception {
        instance.setTargetImageWidth(0);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
        instance.setTargetImageWidth(-1);
        try {
            instance.checkParameters();
            fail("Expects an Exception");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }
}
