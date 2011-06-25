/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo.failuretests;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import com.topcoder.util.log.LogManager;
import com.topcoder.web.reg.actions.photo.MemberPhotoActionConfigurationException;
import com.topcoder.web.reg.actions.photo.MemberPhotoUploadException;
import com.topcoder.web.reg.actions.photo.UploadMemberPhotoAction;

/**
 * <p>
 * Failure tests for UploadMemberPhotoAction.
 * </p>
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class UploadMemberPhotoActionFailureTest extends TestCase {

    private FailureMemeberPhotoManager photomanager;
    /** Represents UploadMemberPhotoAction instance for test. */
    private UploadMemberPhotoAction instance;
    private MockHttpServletRequest request;

    /**
     * <p>
     * Setup the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new UploadMemberPhotoAction();
        photomanager = new FailureMemeberPhotoManager();
        instance.setAuditDAO(new FailureAuditDao());
        instance.setAuthenticationSessionKey("key");
        instance.setUserDAO(new FailureUserDAO());
        instance.setPhotoStoredDirectory("test_files/failure");
        instance.setPhotoPreviewDirectory("test_files/failure");
        instance.setTargetImageHeight(100);
        instance.setTargetImageWidth(30);
        instance.setMemberPhotoManager(photomanager);
        instance.setLog(LogManager.getLog("failure"));
        Map<String, String> allowedContentTypesFileExtensions = new HashMap<String, String>();
        allowedContentTypesFileExtensions.put("jpg", "jpg");
        instance.setAllowedContentTypesFileExtensions(allowedContentTypesFileExtensions );
        instance.checkParameters();
        request = new MockHttpServletRequest();
        MockHttpSession s = new MockHttpSession();
        s.setAttribute("key", new FailureMockBasicAuthentication());
        request.setSession(s);
        instance.setServletRequest(request );
        instance.setUpload(new File("test_files/failure/image2.jpg"));
        instance.setUploadContentType("jpg");
        instance.setUploadFileName("image2.jpg");
    }

    /**
     * <p>
     * Clean up the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        instance = null;
    }
    /**
     * Tests for checkParameters() with illegal configuration.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_checkParameters() throws Exception {
        instance.setAuthenticationSessionKey("  ");
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
        instance.setAuthenticationSessionKey(null);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }
    /**
     * Tests for checkParameters() with illegal configuration.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_checkParameters2() throws Exception {
        instance.setUserDAO(null);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }
    /**
     * Tests for checkParameters() with illegal configuration.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_checkParameters3() throws Exception {
        instance.setPhotoPreviewDirectory(null);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
        instance.setPhotoPreviewDirectory(" ");
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }
    /**
     * Tests for checkParameters() with illegal configuration.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_checkParameters4() throws Exception {
        instance.setSubmittedActionParameterName(null);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
        instance.setSubmittedActionParameterName(" ");
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }
    /**
     * Tests for checkParameters() with illegal configuration.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_checkParameters5() throws Exception {
        instance.setOriginalImageFileNameParameterName(null);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
        instance.setOriginalImageFileNameParameterName(" ");
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }
    /**
     * Tests for checkParameters() with illegal configuration.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_checkParameters6() throws Exception {
        instance.setTargetImageLeftCornerXParameterName(null);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
        instance.setTargetImageLeftCornerXParameterName(" ");
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }
    /**
     * Tests for checkParameters() with illegal configuration.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_checkParameters7() throws Exception {
        instance.setPhotoStoredDirectory(null);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
        instance.setPhotoStoredDirectory(" ");
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }
    /**
     * Tests for checkParameters() with illegal configuration.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_checkParameters10() throws Exception {
        instance.setPhotoPreviewDirectory(null);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
        instance.setPhotoPreviewDirectory(" ");
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }
    /**
     * Tests for checkParameters() with illegal configuration.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_checkParameters8() throws Exception {
        instance.setTargetImageHeight(0);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
        instance.setTargetImageHeight(-1);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }
    /**
     * Tests for checkParameters() with illegal configuration.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_checkParameters9() throws Exception {
        instance.setTargetImageWidth(0);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
        instance.setTargetImageWidth(-1);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
    }
    /**
     * Tests for execute() with no photo found.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_execute() throws Exception {
        instance.setUpload(null);
        try {
            instance.execute();
            fail("Expects IllegalStateException");
        } catch (IllegalStateException e) {
            // pass
        }
    }
    /**
     * Tests for execute() with no photo found.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_execute2() throws Exception {
        instance.setUploadFileName(null);
        try {
            instance.execute();
            fail("Expects IllegalStateException");
        } catch (IllegalStateException e) {
            // pass
        }
        instance.setUploadFileName(" ");
        try {
            instance.execute();
            fail("Expects IllegalStateException");
        } catch (IllegalStateException e) {
            // pass
        }
    }
    /**
     * Tests for execute() with no photo found.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_execute3() throws Exception {
        instance.setUploadContentType(null);
        try {
            instance.execute();
            fail("Expects IllegalStateException");
        } catch (IllegalStateException e) {
            // pass
        }
        instance.setUploadContentType(" ");
        try {
            instance.execute();
            fail("Expects IllegalStateException");
        } catch (IllegalStateException e) {
            // pass
        }
    }
    /**
     * Tests for execute() with photo found doesn't exist.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_execute12() throws Exception {
        instance.setUpload(new File("test_files/failure/notexist.jpg"));
        instance.setUploadContentType("jpg");
        instance.setUploadFileName("notexist.jpg");
        try {
            instance.execute();
            fail("Expects MemberPhotoUploadException");
        } catch (MemberPhotoUploadException e) {
            // pass
        }
    }
    /**
     * Tests for execute() with photo found doesn't exist.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_execute13() throws Exception {
        instance.setUpload(new File("image2.jpg"));
        instance.setUploadContentType("jpg");
        instance.setUploadFileName("image2.jpg");
        instance.setPhotoPreviewDirectory("notexistdir+:?//\\");
        try {
            instance.execute();
            fail("Expects MemberPhotoUploadException");
        } catch (MemberPhotoUploadException e) {
            // pass
        }
    }
    /**
     * Tests for execute() with previewFileName doesn't exist.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_execute14() throws Exception {
        instance.setUpload(new File("image2.jpg"));
        instance.setUploadContentType("jpg");
        instance.setUploadFileName("image2.jpg");
        instance.setPhotoPreviewDirectory("notexistdir+:?//\\");
        
        request.setParameter("photoUploadAction", "commit");
        try {
            instance.execute();
            fail("Expects MemberPhotoUploadException");
        } catch (MemberPhotoUploadException e) {
            // pass
        }
    }
    /**
     * Tests for execute() with extension doesn't exist.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_execute15() throws Exception {
        instance.setUpload(new File("image2.jpg"));
        instance.setUploadContentType("jpg");
        instance.setUploadFileName("image2.jpg");
        instance.setPhotoPreviewDirectory("notexistdir+:?//\\");
        
        request.setParameter("photoUploadAction", "commit");
        request.setParameter("previewImageFileName", "previewabc");
        try {
            instance.execute();
            fail("Expects MemberPhotoUploadException");
        } catch (MemberPhotoUploadException e) {
            // pass
            e.printStackTrace();
        }
    }
    /**
     * Tests for execute() with invalid preview dir.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_execute16() throws Exception {
        instance.setUpload(new File("image2.jpg"));
        instance.setUploadContentType("jpg");
        instance.setUploadFileName("image2.jpg");
        instance.setPhotoPreviewDirectory("notexistdir+:?//\\");
        
        request.setParameter("photoUploadAction", "commit");
        request.setParameter("previewImageFileName", "previewabc.unknowntype");
        try {
            instance.execute();
            fail("Expects MemberPhotoUploadException");
        } catch (MemberPhotoUploadException e) {
            // pass
            e.printStackTrace();
        }
    }
}
