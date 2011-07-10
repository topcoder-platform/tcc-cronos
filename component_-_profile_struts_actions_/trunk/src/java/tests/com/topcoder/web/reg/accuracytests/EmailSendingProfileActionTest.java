/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.web.reg.actions.profile.BaseProfileAction;
import com.topcoder.web.reg.actions.profile.EmailSendingProfileAction;
import com.topcoder.web.reg.actions.profile.SaveAccountInfoAction;

/**
 * <p>
 * Accuracy tests for the {@link EmailSendingProfileAction}.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class EmailSendingProfileActionTest {
    /** Represents the documentGenerator used to test again. */
    private DocumentGenerator documentGeneratorValue;

    /** Represents the emailBodyTemplateName used to test again. */
    private String emailBodyTemplateNameValue;

    /** Represents the emailBodyTemplateSourceId used to test again. */
    private String emailBodyTemplateSourceIdValue;

    /** Represents the emailSender used to test again. */
    private String emailSenderValue;

    /** Represents the emailSubject used to test again. */
    private String emailSubjectValue;

    /** Represents the sendEmail used to test again. */
    private boolean sendEmailValue;

    /** Represents the instance used to test again. */
    private EmailSendingProfileAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new SaveAccountInfoAction();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to JAccuracy
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link EmailSendingProfileAction#EmailSendingProfileAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void testEmailSendingProfileAction() throws Exception {
        new SaveAccountInfoAction();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link EmailSendingProfileAction#getDocumentGenerator()}
     * </p>
     * <p>
     * The value of <code>documentGenerator</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getDocumentGenerator() throws Exception {
        assertNull("Old value", testInstance.getDocumentGenerator());
    }

    /**
     * <p>
     * Accuracy test {@link EmailSendingProfileAction#setDocumentGenerator(DocumentGenerator)}.
     * </p>
     * <p>
     * The value of <code>documentGenerator</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setDocumentGenerator() throws Exception {
        testInstance.setDocumentGenerator(documentGeneratorValue);
        assertEquals("New value", documentGeneratorValue, testInstance.getDocumentGenerator());
    }

    /**
     * <p>
     * Accuracy test for {@link EmailSendingProfileAction#getEmailBodyTemplateName()}
     * </p>
     * <p>
     * The value of <code>emailBodyTemplateName</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getEmailBodyTemplateName() throws Exception {
        assertNull("Old value", testInstance.getEmailBodyTemplateName());
    }

    /**
     * <p>
     * Accuracy test {@link EmailSendingProfileAction#setEmailBodyTemplateName(String)}.
     * </p>
     * <p>
     * The value of <code>emailBodyTemplateName</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setEmailBodyTemplateName() throws Exception {
        testInstance.setEmailBodyTemplateName(emailBodyTemplateNameValue);
        assertEquals("New value", emailBodyTemplateNameValue, testInstance.getEmailBodyTemplateName());
    }

    /**
     * <p>
     * Accuracy test for {@link EmailSendingProfileAction#getEmailBodyTemplateSourceId()}
     * </p>
     * <p>
     * The value of <code>emailBodyTemplateSourceId</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getEmailBodyTemplateSourceId() throws Exception {
        assertNull("Old value", testInstance.getEmailBodyTemplateSourceId());
    }

    /**
     * <p>
     * Accuracy test {@link EmailSendingProfileAction#setEmailBodyTemplateSourceId(String)}.
     * </p>
     * <p>
     * The value of <code>emailBodyTemplateSourceId</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setEmailBodyTemplateSourceId() throws Exception {
        testInstance.setEmailBodyTemplateSourceId(emailBodyTemplateSourceIdValue);
        assertEquals("New value", emailBodyTemplateSourceIdValue, testInstance.getEmailBodyTemplateSourceId());
    }

    /**
     * <p>
     * Accuracy test for {@link EmailSendingProfileAction#getEmailSender()}
     * </p>
     * <p>
     * The value of <code>emailSender</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getEmailSender() throws Exception {
        assertNull("Old value", testInstance.getEmailSender());
    }

    /**
     * <p>
     * Accuracy test {@link EmailSendingProfileAction#setEmailSender(String)}.
     * </p>
     * <p>
     * The value of <code>emailSender</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setEmailSender() throws Exception {
        testInstance.setEmailSender(emailSenderValue);
        assertEquals("New value", emailSenderValue, testInstance.getEmailSender());
    }

    /**
     * <p>
     * Accuracy test for {@link EmailSendingProfileAction#getEmailSubject()}
     * </p>
     * <p>
     * The value of <code>emailSubject</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_getEmailSubject() throws Exception {
        assertNull("Old value", testInstance.getEmailSubject());
    }

    /**
     * <p>
     * Accuracy test {@link EmailSendingProfileAction#setEmailSubject(String)}.
     * </p>
     * <p>
     * The value of <code>emailSubject</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setEmailSubject() throws Exception {
        testInstance.setEmailSubject(emailSubjectValue);
        assertEquals("New value", emailSubjectValue, testInstance.getEmailSubject());
    }

    /**
     * <p>
     * Accuracy test for {@link EmailSendingProfileAction#isSendEmail()}
     * </p>
     * <p>
     * The value of <code>sendEmail</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_isSendEmail() throws Exception {
        assertTrue("Old value", testInstance.isSendEmail());
    }

    /**
     * <p>
     * Accuracy test {@link EmailSendingProfileAction#setSendEmail(boolean)}.
     * </p>
     * <p>
     * The value of <code>sendEmail</code> should be properly set.
     * </p>
     *
     * @throws Exception
     *             to JAccuracy
     */
    @Test
    public void test_setSendEmail() throws Exception {
        testInstance.setSendEmail(sendEmailValue);
        assertEquals("New value", sendEmailValue, testInstance.isSendEmail());
    }

}