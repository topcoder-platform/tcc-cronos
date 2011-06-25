/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.log.Log;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.miscellaneous.mock.MockBasePreferencesAction;

/**
 * <p>
 * This class contains Unit tests for BasePreferenceAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BasePreferenceActionParametersUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents BasePreferencesAction action for testing.
     * </p>
     */
    private BasePreferencesAction basePreferencesAction;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        basePreferencesAction = new MockBasePreferencesAction();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        basePreferencesAction = null;
    }

    /**
     * <p>
     * Tests BasePreferencesAction constructor.
     * </p>
     * <p>
     * BasePreferencesAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("BasePreferencesAction instance should be created successfully.", basePreferencesAction);
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getAction() method.
     * </p>
     * <p>
     * action should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetAction() {
        assertNull("action should be null.", basePreferencesAction.getAction());
        String action = "test";
        basePreferencesAction.setAction(action);
        assertSame("getAction() doesn't work properly.", action, basePreferencesAction.getAction());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#setAction(String) method.
     * </p>
     * <p>
     * action should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetAction() {
        assertNull("action should be null.", basePreferencesAction.getAction());
        String action = "test";
        basePreferencesAction.setAction(action);
        assertSame("setAction() doesn't work properly.", action, basePreferencesAction.getAction());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getAuditDao() method.
     * </p>
     * <p>
     * auditDao should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetAuditDao() {
        assertNull("auditDao should be null.", basePreferencesAction.getAuditDao());
        AuditDAO auditDao = getAuditDAO();
        basePreferencesAction.setAuditDao(auditDao);
        assertSame("getAuditDao() doesn't work properly.", auditDao, basePreferencesAction.getAuditDao());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#setAuditDao(AuditDAO) method.
     * </p>
     * <p>
     * auditDao should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetAuditDao() {
        assertNull("auditDao should be null.", basePreferencesAction.getAuditDao());
        AuditDAO auditDao = getAuditDAO();
        basePreferencesAction.setAuditDao(auditDao);
        assertSame("setAuditDao() doesn't work properly.", auditDao, basePreferencesAction.getAuditDao());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getBackupSessionKey() method.
     * </p>
     * <p>
     * backupSessionKey should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetBackupSessionKey() {
        assertNull("backupSessionKey should be null.", basePreferencesAction.getBackupSessionKey());
        String backupSessionKey = "test";
        basePreferencesAction.setBackupSessionKey(backupSessionKey);
        assertSame("getBackupSessionKey() doesn't work properly.", backupSessionKey,
                basePreferencesAction.getBackupSessionKey());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#setBackupSessionKey(String) method.
     * </p>
     * <p>
     * backupSessionKey should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetBackupSessionKey() {
        assertNull("backupSessionKey should be null.", basePreferencesAction.getBackupSessionKey());
        String backupSessionKey = "test";
        basePreferencesAction.setBackupSessionKey(backupSessionKey);
        assertSame("setBackupSessionKey() doesn't work properly.", backupSessionKey,
                basePreferencesAction.getBackupSessionKey());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getBasicAuthenticationSessionKey() method.
     * </p>
     * <p>
     * basicAuthenticationSessionKey should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetBasicAuthenticationSessionKey() {
        assertNull("basicAuthenticationSessionKey should be null.",
                basePreferencesAction.getBasicAuthenticationSessionKey());
        String basicAuthenticationSessionKey = "test";
        basePreferencesAction.setBasicAuthenticationSessionKey(basicAuthenticationSessionKey);
        assertSame("getBasicAuthenticationSessionKey() doesn't work properly.", basicAuthenticationSessionKey,
                basePreferencesAction.getBasicAuthenticationSessionKey());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#setBasicAuthenticationSessionKey(String) method.
     * </p>
     * <p>
     * basicAuthenticationSessionKey should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetBasicAuthenticationSessionKey() {
        assertNull("basicAuthenticationSessionKey should be null.",
                basePreferencesAction.getBasicAuthenticationSessionKey());
        String basicAuthenticationSessionKey = "test";
        basePreferencesAction.setBasicAuthenticationSessionKey(basicAuthenticationSessionKey);
        assertSame("setBasicAuthenticationSessionKey() doesn't work properly.", basicAuthenticationSessionKey,
                basePreferencesAction.getBasicAuthenticationSessionKey());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getDocumentGenerator() method.
     * </p>
     * <p>
     * documentGenerator should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetDocumentGenerator() {
        assertNull("documentGenerator should be null.", basePreferencesAction.getDocumentGenerator());
        DocumentGenerator documentGenerator = getDocumentGenerator();
        basePreferencesAction.setDocumentGenerator(documentGenerator);
        assertSame("getDocumentGenerator() doesn't work properly.", documentGenerator,
                basePreferencesAction.getDocumentGenerator());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#setDocumentGenerator(DocumentGenerator) method.
     * </p>
     * <p>
     * documentGenerator should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetDocumentGenerator() {
        assertNull("documentGenerator should be null.", basePreferencesAction.getDocumentGenerator());
        DocumentGenerator documentGenerator = getDocumentGenerator();
        basePreferencesAction.setDocumentGenerator(documentGenerator);
        assertSame("setDocumentGenerator() doesn't work properly.", documentGenerator,
                basePreferencesAction.getDocumentGenerator());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getEmailBodyTemplateFileName() method.
     * </p>
     * <p>
     * emailBodyTemplateFileName should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetEmailBodyTemplateFileName() {
        assertNull("emailBodyTemplateFileName should be null.", basePreferencesAction.getEmailBodyTemplateFileName());
        String emailBodyTemplateFileName = "test";
        basePreferencesAction.setEmailBodyTemplateFileName(emailBodyTemplateFileName);
        assertSame("getEmailBodyTemplateFileName() doesn't work properly.", emailBodyTemplateFileName,
                basePreferencesAction.getEmailBodyTemplateFileName());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#setEmailBodyTemplateFileName(String) method.
     * </p>
     * <p>
     * emailBodyTemplateFileName should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetEmailBodyTemplateFileName() {
        assertNull("emailBodyTemplateFileName should be null.", basePreferencesAction.getEmailBodyTemplateFileName());
        String emailBodyTemplateFileName = "test";
        basePreferencesAction.setEmailBodyTemplateFileName(emailBodyTemplateFileName);
        assertSame("setEmailBodyTemplateFileName() doesn't work properly.", emailBodyTemplateFileName,
                basePreferencesAction.getEmailBodyTemplateFileName());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#isEmailSendFlag() method.
     * </p>
     * <p>
     * emailSendFlag should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testIsEmailSendFlag() {
        boolean emailSendFlag = true;
        basePreferencesAction.setEmailSendFlag(emailSendFlag);
        assertEquals("isEmailSendFlag() doesn't work properly.", emailSendFlag,
                basePreferencesAction.isEmailSendFlag());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#setEmailSendFlag(boolean) method.
     * </p>
     * <p>
     * emailSendFlag should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetEmailSendFlag() {
        boolean emailSendFlag = true;
        basePreferencesAction.setEmailSendFlag(emailSendFlag);
        assertEquals("setEmailSendFlag() doesn't work properly.", emailSendFlag,
                basePreferencesAction.isEmailSendFlag());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getEmailSubject() method.
     * </p>
     * <p>
     * emailSubject should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetEmailSubject() {
        assertNull("emailSubject should be null.", basePreferencesAction.getEmailSubject());
        String emailSubject = "test";
        basePreferencesAction.setEmailSubject(emailSubject);
        assertSame("getEmailSubject() doesn't work properly.", emailSubject, basePreferencesAction.getEmailSubject());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#setEmailSubject(String) method.
     * </p>
     * <p>
     * emailSubject should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetEmailSubject() {
        assertNull("emailSubject should be null.", basePreferencesAction.getEmailSubject());
        String emailSubject = "test";
        basePreferencesAction.setEmailSubject(emailSubject);
        assertSame("setEmailSubject() doesn't work properly.", emailSubject, basePreferencesAction.getEmailSubject());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getEmailText() method.
     * </p>
     * <p>
     * emailText should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetEmailText() {
        assertNull("emailText should be null.", basePreferencesAction.getEmailText());
        String emailText = "test";
        basePreferencesAction.setEmailText(emailText);
        assertSame("getEmailText() doesn't work properly.", emailText, basePreferencesAction.getEmailText());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#setEmailText(String) method.
     * </p>
     * <p>
     * emailText should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetEmailText() {
        assertNull("emailText should be null.", basePreferencesAction.getEmailText());
        String emailText = "test";
        basePreferencesAction.setEmailText(emailText);
        assertSame("setEmailText() doesn't work properly.", emailText, basePreferencesAction.getEmailText());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getFromEmailAddress() method.
     * </p>
     * <p>
     * fromEmailAddress should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetFromEmailAddress() {
        assertNull("fromEmailAddress should be null.", basePreferencesAction.getFromEmailAddress());
        String fromEmailAddress = "test";
        basePreferencesAction.setFromEmailAddress(fromEmailAddress);
        assertSame("getFromEmailAddress() doesn't work properly.", fromEmailAddress,
                basePreferencesAction.getFromEmailAddress());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#setFromEmailAddress(String) method.
     * </p>
     * <p>
     * fromEmailAddress should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetFromEmailAddress() {
        assertNull("fromEmailAddress should be null.", basePreferencesAction.getFromEmailAddress());
        String fromEmailAddress = "test";
        basePreferencesAction.setFromEmailAddress(fromEmailAddress);
        assertSame("setFromEmailAddress() doesn't work properly.", fromEmailAddress,
                basePreferencesAction.getFromEmailAddress());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getLogger() method.
     * </p>
     * <p>
     * logger should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetLogger() {
        assertNull("logger should be null.", basePreferencesAction.getLogger());
        Log logger = getLog();
        basePreferencesAction.setLogger(logger);
        assertSame("getLogger() doesn't work properly.", logger, basePreferencesAction.getLogger());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#setLogger(Log) method.
     * </p>
     * <p>
     * logger should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetLogger() {
        assertNull("logger should be null.", basePreferencesAction.getLogger());
        Log logger = getLog();
        basePreferencesAction.setLogger(logger);
        assertSame("setLogger() doesn't work properly.", logger, basePreferencesAction.getLogger());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getUserDao() method.
     * </p>
     * <p>
     * userDao should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetUserDao() {
        assertNull("userDao should be null.", basePreferencesAction.getUserDao());
        UserDAO userDao = getUserDAO();
        basePreferencesAction.setUserDao(userDao);
        assertSame("getUserDao() doesn't work properly.", userDao, basePreferencesAction.getUserDao());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#setUserDao(UserDAO) method.
     * </p>
     * <p>
     * userDao should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetUserDao() {
        assertNull("userDao should be null.", basePreferencesAction.getUserDao());
        UserDAO userDao = getUserDAO();
        basePreferencesAction.setUserDao(userDao);
        assertSame("setUserDao() doesn't work properly.", userDao, basePreferencesAction.getUserDao());
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getLoggedInUser() without logged in user.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetLoggedInUser_NoUser() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        // remove authentication from session
        putKeyValueToSession(basePreferencesAction.getBasicAuthenticationSessionKey(), null);
        try {
            basePreferencesAction.getLoggedInUser();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getLoggedInUser() with logged in user, but not found in database.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetLoggedInUser_NoUserInDB() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        putKeyValueToSession(basePreferencesAction.getBasicAuthenticationSessionKey(), createAuthentication());
        when(basePreferencesAction.getUserDao().find(anyLong())).thenReturn(null);
        try {
            basePreferencesAction.getLoggedInUser();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getLoggedInUser() with invalid object for basic authentication session key.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetLoggedInUser_InvalidUser() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        // put invalid class for basic authentication session key
        putKeyValueToSession(basePreferencesAction.getBasicAuthenticationSessionKey(), "Unknown");
        try {
            basePreferencesAction.getLoggedInUser();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#sendEmail() without logged in user.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSendEmail_NoUser() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        // remove authentication from session
        putKeyValueToSession(basePreferencesAction.getBasicAuthenticationSessionKey(), null);
        try {
            basePreferencesAction.sendEmail();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#audit() without logged in user.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testAudit_NoUser() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        // remove authentication from session
        putKeyValueToSession(basePreferencesAction.getBasicAuthenticationSessionKey(), null);
        try {
            basePreferencesAction.audit("oldValud", "newValue", "operationType");
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#audit() with invalid object for basic authentication session key.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testAudit_InvalidUser() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        // put invalid class for basic authentication session key
        putKeyValueToSession(basePreferencesAction.getBasicAuthenticationSessionKey(), "Unknown");
        try {
            basePreferencesAction.audit("oldValud", "newValue", "operationType");
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#sendEmail() with invalid object for basic authentication session key.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSendEmail_InvalidUser() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        // put invalid class for basic authentication session key
        putKeyValueToSession(basePreferencesAction.getBasicAuthenticationSessionKey(), "Unknown");
        try {
            basePreferencesAction.sendEmail();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#sendEmail() with null user's primary email.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSendEmail_NullPrimaryEmail() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        Long id = 1L;
        UserDAO userDAO = basePreferencesAction.getUserDao();
        // create user without primary email
        User user = createUser(id, "First", "Last", "tc_handle", false);
        when(userDAO.find(id)).thenReturn(user);
        // put valid class for basic authentication session key
        putKeyValueToSession(basePreferencesAction.getBasicAuthenticationSessionKey(), createAuthentication());
        try {
            basePreferencesAction.sendEmail();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#sendEmail() with invalid emailBodyTemplateFileName.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSendEmail_InvalidEmailBodyTemplateFileName() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        Long id = 1L;
        UserDAO userDAO = basePreferencesAction.getUserDao();
        // create user with primary email
        User user = createUser(id, "First", "Last", "tc_handle", true);
        when(userDAO.find(id)).thenReturn(user);
        // put valid class for basic authentication session key
        putKeyValueToSession(basePreferencesAction.getBasicAuthenticationSessionKey(), createAuthentication());
        // set invalid emailBodyTemplateFileName
        basePreferencesAction.setEmailBodyTemplateFileName("Unknown");
        try {
            basePreferencesAction.sendEmail();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with null auditDao passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_AuditDao() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setAuditDao(null);
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with null userDao passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_UserDao() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setUserDao(null);
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with null logger passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_Logger() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setLogger(null);
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with null backupSessionKey passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_BackupSessionKey() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setBackupSessionKey(null);
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with empty backupSessionKey passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Empty_BackupSessionKey() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setBackupSessionKey("");
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with null basicAuthenticationSessionKey passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_BasicAuthenticationSessionKey() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setBasicAuthenticationSessionKey(null);
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with empty basicAuthenticationSessionKey passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Empty_BasicAuthenticationSessionKey() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setBasicAuthenticationSessionKey("");
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with null documentGenerator passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_DocumentGenerator() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setDocumentGenerator(null);
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with null emailBodyTemplateFileName passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_EmailBodyTemplateFileName() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setEmailBodyTemplateFileName(null);
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with empty emailBodyTemplateFileName passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Empty_EmailBodyTemplateFileName() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setEmailBodyTemplateFileName("");
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with null emailSubject passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_EmailSubject() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setEmailSubject(null);
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with empty emailSubject passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Empty_EmailSubject() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setEmailSubject("");
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with null emailText passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_EmailText() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setEmailText(null);
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with empty emailText passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Empty_EmailText() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setEmailText("");
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with null fromEmailAddress passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_FromEmailAddress() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setFromEmailAddress(null);
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BasePreferencesAction#prepare() with empty fromEmailAddress passed.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Empty_FromEmailAddress() throws Exception {
        basePreferencesAction = getBasePreferencesAction();
        basePreferencesAction.setFromEmailAddress("");
        try {
            basePreferencesAction.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }
}
