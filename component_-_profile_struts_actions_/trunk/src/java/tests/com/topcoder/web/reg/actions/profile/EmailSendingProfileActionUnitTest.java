/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Mockito.mock;

import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

/**
 * <p>
 * This class contains Unit tests for EmailSendingProfileAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EmailSendingProfileActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents EmailSendingProfileAction instance for testing.
     * </p>
     */
    private EmailSendingProfileAction action;

    /**
     * <p>
     * Represents email body template data for testing.
     * </p>
     */
    private String emailBodyTemplateData;

    /**
     * <p>
     * Represents email address for testing.
     * </p>
     */
    private String email;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = (EmailSendingProfileAction) getBean("emailSendingProfileAction");
        email = "toaddress@example.com";
        emailBodyTemplateData = "<DATA><ID>1</ID><HANDLE>handle</HANDLE></DATA>";
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        action = null;
        email = null;
        emailBodyTemplateData = null;
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction constructor.
     * </p>
     * <p>
     * EmailSendingProfileAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("EmailSendingProfileAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#checkInitialization() method with valid fields values.
     * </p>
     * <p>
     * No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization() throws Exception {
        action.checkInitialization();
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#checkInitialization() method null documentGenerator.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_documentGenerator() throws Exception {
        action.setDocumentGenerator(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#checkInitialization() method null emailSubject.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_emailSubject() throws Exception {
        action.setEmailSubject(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#checkInitialization() method empty emailSubject.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Empty_emailSubject() throws Exception {
        action.setEmailSubject("");
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#checkInitialization() method null emailBodyTemplateName.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_emailBodyTemplateName() throws Exception {
        action.setEmailBodyTemplateName(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#checkInitialization() method empty emailBodyTemplateName.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Empty_emailBodyTemplateName() throws Exception {
        action.setEmailBodyTemplateName("");
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#checkInitialization() method null emailSender.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_emailSender() throws Exception {
        action.setEmailSender(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#checkInitialization() method empty emailSender.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Empty_emailSender() throws Exception {
        action.setEmailSender("");
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#sendEmail() method with valid fields.
     * </p>
     * <p>
     * Mail should be sent successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSendEmail() throws Exception {
        action.sendEmail(email, emailBodyTemplateData);
        // check email manually via DevNullSmtp
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#sendEmail() method with null template source id. Default should be used.
     * </p>
     * <p>
     * Mail should be sent successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSendEmail_NullTemplate() throws Exception {
        action.setEmailBodyTemplateSourceId(null);
        action.sendEmail(email, emailBodyTemplateData);
        // check email manually via DevNullSmtp
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#sendEmail() method invalid template name.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSendEmail_InvalidTemplate() throws Exception {
        action.setEmailBodyTemplateName("invalid_template.txt");
        try {
            action.sendEmail(email, emailBodyTemplateData);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#sendEmail() method invalid template name.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSendEmail_InvalidSourceId() throws Exception {
        action.setEmailBodyTemplateSourceId("Unknown");
        try {
            action.sendEmail(email, emailBodyTemplateData);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#sendEmail() method invalid email address.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSendEmail_InvalidEmail() throws Exception {
        email = "Unknown";
        try {
            action.sendEmail(email, emailBodyTemplateData);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#sendEmail() method invalid emailBodyTemplateData.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSendEmail_InvalidBodyTemplateData() throws Exception {
        emailBodyTemplateData = "<DATA><</DATA>";
        try {
            action.sendEmail(email, emailBodyTemplateData);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#getDocumentGenerator() method.
     * </p>
     * <p>
     * documentGenerator should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetDocumentGenerator() {
        DocumentGenerator documentGenerator = mock(DocumentGenerator.class);
        action.setDocumentGenerator(documentGenerator);
        assertSame("getDocumentGenerator() doesn't work properly.", documentGenerator, action.getDocumentGenerator());
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#setDocumentGenerator(DocumentGenerator) method.
     * </p>
     * <p>
     * documentGenerator should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetDocumentGenerator() {
        DocumentGenerator documentGenerator = mock(DocumentGenerator.class);
        action.setDocumentGenerator(documentGenerator);
        assertSame("setDocumentGenerator() doesn't work properly.", documentGenerator, action.getDocumentGenerator());
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#getEmailSubject() method.
     * </p>
     * <p>
     * emailSubject should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetEmailSubject() {
        String emailSubject = "test";
        action.setEmailSubject(emailSubject);
        assertSame("getEmailSubject() doesn't work properly.", emailSubject, action.getEmailSubject());
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#setEmailSubject(String) method.
     * </p>
     * <p>
     * emailSubject should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetEmailSubject() {
        String emailSubject = "test";
        action.setEmailSubject(emailSubject);
        assertSame("setEmailSubject() doesn't work properly.", emailSubject, action.getEmailSubject());
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#getEmailBodyTemplateSourceId() method.
     * </p>
     * <p>
     * emailBodyTemplateSourceId should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetEmailBodyTemplateSourceId() {
        String emailBodyTemplateSourceId = "test";
        action.setEmailBodyTemplateSourceId(emailBodyTemplateSourceId);
        assertSame("getEmailBodyTemplateSourceId() doesn't work properly.", emailBodyTemplateSourceId,
                action.getEmailBodyTemplateSourceId());
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#setEmailBodyTemplateSourceId(String) method.
     * </p>
     * <p>
     * emailBodyTemplateSourceId should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetEmailBodyTemplateSourceId() {
        String emailBodyTemplateSourceId = "test";
        action.setEmailBodyTemplateSourceId(emailBodyTemplateSourceId);
        assertSame("setEmailBodyTemplateSourceId() doesn't work properly.", emailBodyTemplateSourceId,
                action.getEmailBodyTemplateSourceId());
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#getEmailBodyTemplateName() method.
     * </p>
     * <p>
     * emailBodyTemplateName should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetEmailBodyTemplateName() {
        String emailBodyTemplateName = "test";
        action.setEmailBodyTemplateName(emailBodyTemplateName);
        assertSame("getEmailBodyTemplateName() doesn't work properly.", emailBodyTemplateName,
                action.getEmailBodyTemplateName());
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#setEmailBodyTemplateName(String) method.
     * </p>
     * <p>
     * emailBodyTemplateName should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetEmailBodyTemplateName() {
        String emailBodyTemplateName = "test";
        action.setEmailBodyTemplateName(emailBodyTemplateName);
        assertSame("setEmailBodyTemplateName() doesn't work properly.", emailBodyTemplateName,
                action.getEmailBodyTemplateName());
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#getEmailSender() method.
     * </p>
     * <p>
     * emailSender should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetEmailSender() {
        String emailSender = "test";
        action.setEmailSender(emailSender);
        assertSame("getEmailSender() doesn't work properly.", emailSender, action.getEmailSender());
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#setEmailSender(String) method.
     * </p>
     * <p>
     * emailSender should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetEmailSender() {
        String emailSender = "test";
        action.setEmailSender(emailSender);
        assertSame("setEmailSender() doesn't work properly.", emailSender, action.getEmailSender());
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#isSendEmail() method.
     * </p>
     * <p>
     * sendEmail should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testIsSendEmail() {
        boolean sendEmail = true;
        action.setSendEmail(sendEmail);
        assertEquals("isSendEmail() doesn't work properly.", sendEmail, action.isSendEmail());
    }

    /**
     * <p>
     * Tests EmailSendingProfileAction#setSendEmail(boolean) method.
     * </p>
     * <p>
     * sendEmail should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetSendEmail() {
        boolean sendEmail = true;
        action.setSendEmail(sendEmail);
        assertEquals("setSendEmail() doesn't work properly.", sendEmail, action.isSendEmail());
    }
}
