/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.registration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.templatesource.FileTemplateSource;
import com.topcoder.util.file.templatesource.TemplateSource;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.RegistrationTypeDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.reg.RegistrationActionConfigurationException;

/**
 * <p>
 * Unit tests for class <code>EmailSendingAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class EmailSendingActionTest {
    /**
     * <p>
     * Represents the <code>EmailSendingAction</code> instance used to test against.
     * </p>
     */
    private EmailSendingAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(EmailSendingActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new MockEmailSendingAction();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        impl = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>EmailSendingAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseRegistrationAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>EmailSendingAction()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInitialization()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCheckInitialization() {
        initialImpl();

        impl.checkInitialization();
    }

    /**
     * <p>
     * Initial the implement instance for testing.
     * </p>
     */
    private void initialImpl() {
        AuditDAO auditDAO = EasyMock.createMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        Log logger = LogManager.getLog("test");
        impl.setLogger(logger);
        impl.setAuthenticationSessionKey("webAuthentication");
        Map<String, Object> session = new HashMap<String, Object>();
        impl.setSession(session);
        UserDAO userDAO = EasyMock.createMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        RegistrationTypeDAO registrationTypeDAO = EasyMock.createMock(RegistrationTypeDAO.class);
        impl.setRegistrationTypeDAO(registrationTypeDAO);
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);

        DocumentGenerator documentGenerator = EasyMock.createMock(DocumentGenerator.class);
        impl.setDocumentGenerator(documentGenerator);
        impl.setEmailSubject("emailSubject");
        impl.setEmailBodyTemplateSourceId("1");
        impl.setEmailBodyTemplateName("emailBodyTemplateName");
        impl.setEmailSender("sender@topcoder.com");
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The documentGenerator is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_DocumentGeneratorNotSet() throws Exception {
        initialImpl();

        impl.setDocumentGenerator(null);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The emailSubject is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_EmailSubjectNotSet() throws Exception {
        initialImpl();

        impl.setEmailSubject(null);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The emailBodyTemplateSourceId is empty.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_EmailBodyTemplateSourceIdEmpty() throws Exception {
        initialImpl();

        impl.setEmailBodyTemplateSourceId("");

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The emailBodyTemplateName is empty.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_EmailBodyTemplateNameEmpty() throws Exception {
        initialImpl();

        impl.setEmailBodyTemplateName("    ");

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The emailSender is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_EmailSenderNotSet() throws Exception {
        initialImpl();

        impl.setEmailSender(null);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Accuracy test for the method <code>sendEmail(String, String)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSendEmail() throws Exception {
        impl.setLogger(LogManager.getLog("test"));

        // Create a DocumentGenerator:
        DocumentGenerator documentGenerator = new DocumentGenerator();
        // Create a FileTemplateSource:
        TemplateSource templateSource = new FileTemplateSource();
        // Set the default template source:
        documentGenerator.setDefaultTemplateSource(templateSource);

        String emailBodyTemplateData = "<DATA><handle>user</handle><link>www.topcoder.com</link></DATA>";

        impl.setEmailSubject("emailSubject");
        impl.setEmailSender("sender@topcoder.com");
        impl.setDocumentGenerator(documentGenerator);
        String templatePath = "test_files" + File.separator + "mailBodyTemplate.txt";
        impl.setEmailBodyTemplateName(templatePath);

        impl.sendEmail("to@topcoder.com", emailBodyTemplateData);

    }

    /**
     * <p>
     * Accuracy test for the method <code>sendEmail(String, String)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSendEmail2() throws Exception {
        impl.setLogger(LogManager.getLog("test"));

        // Create a DocumentGenerator:
        DocumentGenerator documentGenerator = new DocumentGenerator();
        // Create a FileTemplateSource:
        TemplateSource templateSource = new FileTemplateSource();
        // Set the template source:
        documentGenerator.setTemplateSource("fileTemplate", templateSource);

        String emailBodyTemplateData = "<DATA><handle>user</handle><link>www.topcoder.com</link></DATA>";

        impl.setEmailSubject("emailSubject");
        impl.setEmailSender("sender@topcoder.com");
        impl.setDocumentGenerator(documentGenerator);
        impl.setEmailBodyTemplateSourceId("fileTemplate");
        String templatePath = "test_files" + File.separator + "mailBodyTemplate.txt";
        impl.setEmailBodyTemplateName(templatePath);

        impl.sendEmail("to@topcoder.com", emailBodyTemplateData);
    }

    /**
     * <p>
     * Failure test for the method <code>sendEmail(String, String))</code>.<br>
     * The sourceId is invalid.<br>
     * Expect TCWebException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = TCWebException.class)
    public void testSendEmail_SourceIdInvalid() throws Exception {
        impl.setLogger(LogManager.getLog("test"));

        // Create a DocumentGenerator:
        DocumentGenerator documentGenerator = new DocumentGenerator();
        // Create a FileTemplateSource:
        TemplateSource templateSource = new FileTemplateSource();
        // Set the template source:
        documentGenerator.setTemplateSource("fileTemplate", templateSource);

        String emailBodyTemplateData = "<DATA><handle>user</handle><link>www.topcoder.com</link></DATA>";

        impl.setEmailSubject("emailSubject");
        impl.setEmailSender("sender@topcoder.com");
        impl.setDocumentGenerator(documentGenerator);
        impl.setEmailBodyTemplateSourceId("wrong");
        String templatePath = "test_files" + File.separator + "mailBodyTemplate.txt";
        impl.setEmailBodyTemplateName(templatePath);

        impl.sendEmail("to@topcoder.com", emailBodyTemplateData);
    }

    /**
     * <p>
     * Failure test for the method <code>sendEmail(String, String))</code>.<br>
     * The mailBodyTemplate is invalid.<br>
     * Expect TCWebException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = TCWebException.class)
    public void testSendEmail_MailBodyTemplateInvalid() throws Exception {
        impl.setLogger(LogManager.getLog("test"));

        // Create a DocumentGenerator:
        DocumentGenerator documentGenerator = new DocumentGenerator();
        // Create a FileTemplateSource:
        TemplateSource templateSource = new FileTemplateSource();
        // Set the template source:
        documentGenerator.setTemplateSource("fileTemplate", templateSource);

        String emailBodyTemplateData = "<DATA><handle>user</handle><link>www.topcoder.com</link></DATA>";

        impl.setEmailSubject("emailSubject");
        impl.setEmailSender("sender@topcoder.com");
        impl.setDocumentGenerator(documentGenerator);
        impl.setEmailBodyTemplateSourceId("fileTemplate");
        String templatePath = "test_files" + File.separator + "mailBodyTemplateError.txt";
        impl.setEmailBodyTemplateName(templatePath);

        impl.sendEmail("to@topcoder.com", emailBodyTemplateData);
    }

    /**
     * <p>
     * Failure test for the method <code>sendEmail(String, String))</code>.<br>
     * The templateDataFormat is invalid.<br>
     * Expect TCWebException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = TCWebException.class)
    public void testSendEmail_TemplateDataFormatInvalid() throws Exception {
        impl.setLogger(LogManager.getLog("test"));

        // Create a DocumentGenerator:
        DocumentGenerator documentGenerator = new DocumentGenerator();
        // Create a FileTemplateSource:
        TemplateSource templateSource = new FileTemplateSource();
        // Set the template source:
        documentGenerator.setTemplateSource("fileTemplate", templateSource);

        String emailBodyTemplateData = "<DATA><handle>user</handle><link>www.topcoder.com</link><DATA>";

        impl.setEmailSubject("emailSubject");
        impl.setEmailSender("sender@topcoder.com");
        impl.setDocumentGenerator(documentGenerator);
        impl.setEmailBodyTemplateSourceId("fileTemplate");
        String templatePath = "test_files" + File.separator + "valid-simple-comment.txt";
        impl.setEmailBodyTemplateName(templatePath);

        impl.sendEmail("to@topcoder.com", emailBodyTemplateData);
    }

    /**
     * <p>
     * Failure test for the method <code>sendEmail(String, String))</code>.<br>
     * The email address is invalid.<br>
     * Expect TCWebException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = TCWebException.class)
    public void testSendEmail_AddressInvalid() throws Exception {
        impl.setLogger(LogManager.getLog("test"));

        // Create a DocumentGenerator:
        DocumentGenerator documentGenerator = new DocumentGenerator();
        // Create a FileTemplateSource:
        TemplateSource templateSource = new FileTemplateSource();
        // Set the default template source:
        documentGenerator.setDefaultTemplateSource(templateSource);

        String emailBodyTemplateData = "<DATA><handle>user</handle><link>www.topcoder.com</link></DATA>";

        impl.setEmailSubject("emailSubject");
        impl.setEmailSender("sender@@@topcoder.com");
        impl.setDocumentGenerator(documentGenerator);
        String templatePath = "test_files" + File.separator + "mailBodyTemplate.txt";
        impl.setEmailBodyTemplateName(templatePath);

        impl.sendEmail("to@topcoder.com", emailBodyTemplateData);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getDocumentGenerator()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetDocumentGenerator() {
        assertNull("The initial value should be null", impl.getDocumentGenerator());

        DocumentGenerator expect = EasyMock.createMock(DocumentGenerator.class);

        impl.setDocumentGenerator(expect);

        assertEquals("The return value should be same as ", expect, impl.getDocumentGenerator());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDocumentGenerator(DocumentGenerator)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetDocumentGenerator() {
        DocumentGenerator expect = EasyMock.createMock(DocumentGenerator.class);

        impl.setDocumentGenerator(expect);

        assertEquals("The return value should be same as ", expect, impl.getDocumentGenerator());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getEmailSubject()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetEmailSubject() {
        assertNull("The initial value should be null", impl.getEmailSubject());

        String expect = "test";

        impl.setEmailSubject(expect);

        assertEquals("The return value should be same as ", expect, impl.getEmailSubject());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setEmailSubject(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetEmailSubject() {
        String expect = "test";

        impl.setEmailSubject(expect);

        assertEquals("The return value should be same as ", expect, impl.getEmailSubject());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getEmailBodyTemplateSourceId()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetEmailBodyTemplateSourceId() {
        assertNull("The initial value should be null", impl.getEmailBodyTemplateSourceId());

        String expect = "test";

        impl.setEmailBodyTemplateSourceId(expect);

        assertEquals("The return value should be same as ", expect, impl.getEmailBodyTemplateSourceId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setEmailBodyTemplateSourceId(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetEmailBodyTemplateSourceId() {
        String expect = "test";

        impl.setEmailBodyTemplateSourceId(expect);

        assertEquals("The return value should be same as ", expect, impl.getEmailBodyTemplateSourceId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getEmailBodyTemplateName()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetEmailBodyTemplateName() {
        assertNull("The initial value should be null", impl.getEmailBodyTemplateName());

        String expect = "test";

        impl.setEmailBodyTemplateName(expect);

        assertEquals("The return value should be same as ", expect, impl.getEmailBodyTemplateName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setEmailBodyTemplateName(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetEmailBodyTemplateName() {
        String expect = "test";

        impl.setEmailBodyTemplateName(expect);

        assertEquals("The return value should be same as ", expect, impl.getEmailBodyTemplateName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getEmailSender()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetEmailSender() {
        assertNull("The initial value should be null", impl.getEmailSender());

        String expect = "test";

        impl.setEmailSender(expect);

        assertEquals("The return value should be same as ", expect, impl.getEmailSender());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setEmailSender(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetEmailSender() {
        String expect = "test";

        impl.setEmailSender(expect);

        assertEquals("The return value should be same as ", expect, impl.getEmailSender());
    }

    /**
     * <p>
     * Mock class for testing.
     * </p>
     *
     * @author stevenfrog
     * @version 1.0
     */
    class MockEmailSendingAction extends EmailSendingAction {
        // Empty
    }

}
