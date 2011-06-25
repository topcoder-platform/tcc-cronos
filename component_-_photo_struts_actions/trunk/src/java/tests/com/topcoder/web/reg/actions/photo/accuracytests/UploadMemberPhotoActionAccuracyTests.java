/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo.accuracytests;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import com.opensymphony.xwork2.Action;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.templatesource.FileTemplateSource;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.reg.actions.photo.UploadMemberPhotoAction;

import junit.framework.TestCase;

/**
 * Accuracy test for class UploadMemberPhotoAction.
 *
 * @author extra
 * @version 1.0
 */
public class UploadMemberPhotoActionAccuracyTests extends TestCase {
    /**
     * action of UploadMemberPhotoAction for test.
     */
    private UploadMemberPhotoAction action;

    /**
     * Set up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        action = new UploadMemberPhotoAction();
        action.setAuditDAO(new MockAuditDAO());

        DocumentGenerator generator = new DocumentGenerator();
        generator.setTemplateSource("file", new FileTemplateSource());
        action.setUserDAO(new MockUserDAO());
        action.setMemberPhotoManager(new MockMemberPhotoManager());
        action.setPhotoStoredDirectory("test_files/accuracy/stored");
        action.setPhotoPreviewDirectory("test_files/accuracy/preview");
        action.setLog(LogManager.getLog());
        action.setAuthenticationSessionKey("authenticationkey");

        Map<String, String> extensionsMap = new HashMap<String, String>();
        extensionsMap.put("png", "png");
        extensionsMap.put("gif", "gif");
        extensionsMap.put("jpg", "jpg");
        action.setAllowedContentTypesFileExtensions(extensionsMap);

        action.setTargetImageRightCornerYParameterName("rightY");
        action.setTargetImageRightCornerXParameterName("rightX");
        action.setTargetImageLeftCornerYParameterName("leftY");
        action.setTargetImageLeftCornerXParameterName("leftX");

        action.setTargetImageWidth(80);
        action.setTargetImageHeight(60);

        action.setSubmittedActionParameterName("actionNameKey");
        action.setOriginalImageFileNameParameterName("originalImageKey");
        action.setPreviewImageFileNameParameterName("previewImageKey");

        action.setUploadFileName("default.gif");

        action.setUploadContentType("gif");
        action.setUpload(new File("test_files/accuracy/stored/topcoder.gif"));

        action.checkParameters();
    }

    /**
     * Tear down test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        action = null;
    }

    /**
     * Accuracy test for method execute. If actionName is not commit, the previewSuccessUrlSuffix should be correct.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_execute_Acc1() throws Exception {
        // setup
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        session.setAttribute("authenticationkey", new MockBasicAuthentication());
        request.setParameter("actionNameKey", "submit");

        action.setServletRequest(request);

        String result = action.execute();
        assertEquals("result should be previewSuccess.", "previewSuccess", result);
        assertEquals("previewSuccessUrlSuffix of action.", "&previewImageKey=handle.gif&originalImageKey=default.gif",
                action.getPreviewSuccessUrlSuffix());
    }

    /**
     * Accuracy test for method execute. If actionName is commit, the image should be correct.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_execute_Acc2() throws Exception {
        // setup
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        session.setAttribute("authenticationkey", new MockBasicAuthentication());
        request.setParameter("previewImageKey", "topcoder.gif");
        request.setParameter("actionNameKey", "commit");

        action.setServletRequest(request);

        String result = action.execute();
        assertEquals("result should be success.", Action.SUCCESS, result);
    }

    /**
     * Accuracy test for method execute. If actionName is commit, the target image size is set, the image should be
     * correct.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_execute_Acc3() throws Exception {
        // setup
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        session.setAttribute("authenticationkey", new MockBasicAuthentication());
        request.setParameter("previewImageKey", "topcoder.gif");
        request.setParameter("actionNameKey", "commit");

        request.setParameter("leftX", "10");
        request.setParameter("leftY", "10");
        request.setParameter("rightX", "40");
        request.setParameter("rightY", "40");
        action.setServletRequest(request);

        String result = action.execute();
        assertEquals("result should be success.", Action.SUCCESS, result);
    }
}
