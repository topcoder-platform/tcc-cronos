/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo.accuracytests;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import com.opensymphony.xwork2.Action;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.templatesource.FileTemplateSource;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.reg.actions.photo.RemoveMemberPhotoAction;

/**
 * Accuracy test for class RemoveMemberPhotoAction.
 *
 * @author extra
 * @version 1.0
 */
public class RemoveMemberPhotoActionAccuracyTests extends TestCase {

    /**
     * action of RemoveMemberPhotoAction for test.
     */
    private RemoveMemberPhotoAction action;

    /**
     * Set up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        action = new RemoveMemberPhotoAction();
        action.setAuditDAO(new MockAuditDAO());

        DocumentGenerator generator = new DocumentGenerator();
        generator.setTemplateSource("file", new FileTemplateSource());
        action.setDocumentGenerator(generator);
        action.setEmailBodyTemplateFileName("test_files/accuracy/emailTemplate.txt");
        action.setEmailSendFlag(true);
        action.setFromEmailAddress("accuracy@topcoder.com");
        action.setUserDAO(new MockUserDAO());
        action.setMemberPhotoManager(new MockMemberPhotoManager());
        action.setRemovalReasonParameterName("removalReasonKey");
        action.setPhotoStoredDirectory("test_files/accuracy/stored");
        action.setPhotoRemovedDirectory("test_files/accuracy/removed");
        action.setLog(LogManager.getLog());
        action.setAuthenticationSessionKey("authenticationkey");
        action.setEmailSubject("Email subject");
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
     * Accuracy test for method execute.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_execute_Acc() throws Exception {
        // setup
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        request.setParameter("removalReasonKey", "I need to test it.");
        session.setAttribute("authenticationkey", new MockBasicAuthentication());

        action.setServletRequest(request);

        String result = action.execute();
        assertEquals("result should be success.", Action.SUCCESS, result);
    }
}
