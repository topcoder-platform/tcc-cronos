/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo.failuretests;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.reg.actions.photo.MemberPhotoActionConfigurationException;
import com.topcoder.web.reg.actions.photo.MemberPhotoRemovalException;
import com.topcoder.web.reg.actions.photo.RemoveMemberPhotoAction;

/**
 * <p>
 * Failure tests for RemoveMemberPhotoAction.
 * </p>
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class RemoveMemberPhotoActionFailureTest extends TestCase {

    private FailureMemeberPhotoManager photomanager;
    /** Represents RemoveMemberPhotoAction instance for test. */
    private RemoveMemberPhotoAction instance;

    /**
     * <p>
     * Setup the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new RemoveMemberPhotoAction();
        photomanager = new FailureMemeberPhotoManager();
        instance.setAuditDAO(new FailureAuditDao());
        instance.setAuthenticationSessionKey("key");
        DocumentGenerator documentGenerator = new DocumentGenerator();
        instance.setDocumentGenerator(documentGenerator);
        instance.setUserDAO(new FailureUserDAO());
        instance.setRemovalReasonParameterName("reason");
        instance.setPhotoStoredDirectory("test_files/failure");
        instance.setMemberPhotoManager(photomanager);
        instance.setPhotoRemovedDirectory("test_files/failure/");
        instance.setLog(LogManager.getLog("failure"));
        instance.setFromEmailAddress("failure@topcoder.com");
        instance.setEmailSubject("title");
        instance.setEmailSendFlag(true);
        instance.setEmailBodyTemplateFileName("templatefile");
        instance.checkParameters();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession s = new MockHttpSession();
        s.setAttribute("key", new FailureMockBasicAuthentication());
        request.setSession(s);
        instance.setServletRequest(request );
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
        instance.setRemovalReasonParameterName(null);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
        instance.setRemovalReasonParameterName(" ");
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
        instance.setEmailBodyTemplateFileName(null);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
        instance.setEmailBodyTemplateFileName(" ");
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
        instance.setEmailSubject(null);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
        instance.setEmailSubject(" ");
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
        instance.setFromEmailAddress(null);
        try {
            instance.checkParameters();
            fail("Expects MemberPhotoActionConfigurationException");
        } catch (MemberPhotoActionConfigurationException e) {
            // pass
        }
        instance.setFromEmailAddress(" ");
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
     * Tests for execute() with no photo found.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_execute() throws Exception {
        try {
            instance.execute();
            fail("Expects MemberPhotoRemovalException");
        } catch (MemberPhotoRemovalException e) {
            // pass
        }
    }
    /**
     * Tests for execute() with photo found doesn't exist.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_execute2() throws Exception {
        Image image = new Image();
        image.setFileName("image1.jpg");
        photomanager.setImage(image );
        instance.setPhotoRemovedDirectory("this dir is not valid +-?\\//");
        try {
            instance.execute();
            fail("Expects MemberPhotoRemovalException");
        } catch (MemberPhotoRemovalException e) {
            // pass
        }
    }
    /**
     * Tests for execute() with no template source.
     * 
     * @throws Exception
     *             to junit
     */
    public void test_execute3() throws Exception {
        Image image = new Image();
        image.setFileName("image1.jpg");
        photomanager.setImage(image);
        try {
            instance.execute();
            fail("Expects MemberPhotoRemovalException");
        } catch (MemberPhotoRemovalException e) {
            e.printStackTrace();
            // pass
        }
    }
}
