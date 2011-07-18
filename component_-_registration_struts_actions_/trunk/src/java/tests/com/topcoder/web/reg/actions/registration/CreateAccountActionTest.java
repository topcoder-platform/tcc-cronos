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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.shared.security.Persistor;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.templatesource.FileTemplateSource;
import com.topcoder.util.file.templatesource.TemplateSource;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.SessionInfo;
import com.topcoder.web.common.TCRequest;
import com.topcoder.web.common.TCResponse;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.RegistrationTypeDAO;
import com.topcoder.web.common.dao.SecurityGroupDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.dao.hibernate.RegistrationTypeDAOHibernate;
import com.topcoder.web.common.dao.hibernate.SecurityGroupDAOHibernate;
import com.topcoder.web.common.dao.hibernate.UserDAOHibernate;
import com.topcoder.web.common.model.CoderReferral;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.Referral;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.RegistrationActionConfigurationException;

/**
 * <p>
 * Unit tests for class <code>CreateAccountAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class CreateAccountActionTest {
    /**
     * <p>
     * Represents the <code>CreateAccountAction</code> instance used to test against.
     * </p>
     */
    private CreateAccountAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CreateAccountActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new MockCreateAccountAction();
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
     * Inheritance test, verifies <code>CreateAccountAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof EmailSendingAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>CreateAccountAction()</code>.<br>
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

        Set<RegistrationType> userRoles = new HashSet<RegistrationType>();
        userRoles.add(new RegistrationType(2));
        impl.setUserRoles(userRoles);
        impl.setSessionInfoSessionKey("sessionInfoSessionKey");

        impl.setPrincipalMgr(new MockPrincipalMgrRemote());
        impl.setSecurityGroupDAO(new SecurityGroupDAOHibernate());
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The principalMgr is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_PrincipalMgrNotSet() throws Exception {
        initialImpl();

        impl.setPrincipalMgr(null);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The securityGroupDAO is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_SecurityGroupDAONotSet() throws Exception {
        initialImpl();

        impl.setSecurityGroupDAO(null);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The userRoles is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_UserRolesNotSet() throws Exception {
        initialImpl();

        impl.setUserRoles(null);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The userRoles is empty.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_UserRolesEmpty() throws Exception {
        initialImpl();

        impl.setUserRoles(new HashSet<RegistrationType>());

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The sessionInfoSessionKey is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_SessionInfoSessionKeyNotSet() throws Exception {
        initialImpl();

        impl.setSessionInfoSessionKey(null);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The sessionInfoSessionKey is empty.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_SessionInfoSessionKeyEmpty() throws Exception {
        initialImpl();

        impl.setSessionInfoSessionKey("   ");

        impl.checkInitialization();
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate() {
        try {
            HibernateUtils.begin();

            impl.setUserDAO(new UserDAOHibernate());

            Map<String, Object> session = new HashMap<String, Object>();
            session.put("capword", "aaa");
            impl.setSession(session);
            impl.setVerificationCode("aaa");

            User user = new User();
            user.setHandle("bbbbbb");
            Set<Email> emails = new HashSet<Email>();
            Email email = new Email();
            email.setAddress("ccccccc@topcoder.com");
            emails.add(email);
            user.setEmailAddresses(emails);
            impl.setUser(user);

            impl.setReferrerHandle("dok_tester1");

            impl.setPassword("ddd");
            impl.setConfirmPassword("ddd");

            impl.validate();

            assertEquals("The error map size should be same as ", 0, impl.getFieldErrors().size());
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate2() {
        try {
            HibernateUtils.begin();

            impl.setUserDAO(new UserDAOHibernate());

            Map<String, Object> session = new HashMap<String, Object>();
            session.put("capword", "aaa");
            impl.setSession(session);
            impl.setVerificationCode("aaabbb");

            User user = new User();
            user.setHandle("bbbbbb");
            Set<Email> emails = new HashSet<Email>();
            Email email = new Email();
            email.setAddress("foo@fooonyou.com");
            emails.add(email);
            user.setEmailAddresses(emails);
            impl.setUser(user);

            impl.setReferrerHandle("wrong");

            impl.setPassword("ddd");
            impl.setConfirmPassword("ddd");

            impl.validate();

            assertEquals("The error map size should be same as ", 3, impl.getFieldErrors().size());

            assertEquals("The error field should be same as ", "verificationCode.invalid", impl
                .getFieldErrors().get("verificationCode").get(0));
            assertEquals("The error field should be same as ", "email.duplicate",
                impl.getFieldErrors().get("user.handle").get(0));
            assertEquals("The error field should be same as ", "handle.notfound",
                impl.getFieldErrors().get("referrerHandle").get(0));
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate3() {
        try {
            HibernateUtils.begin();

            impl.setUserDAO(new UserDAOHibernate());

            Map<String, Object> session = new HashMap<String, Object>();
            session.put("capword", "aaa");
            impl.setSession(session);
            impl.setVerificationCode("aaa");

            User user = new User();
            user.setHandle("dok_tester1");
            Set<Email> emails = new HashSet<Email>();
            Email email = new Email();
            email.setAddress("ccccccc@topcoder.com");
            emails.add(email);
            user.setEmailAddresses(emails);
            impl.setUser(user);

            impl.setReferrerHandle("dok_tester1");

            impl.setPassword("ddd");
            impl.setConfirmPassword("dddeee");

            impl.validate();

            assertEquals("The error map size should be same as ", 2, impl.getFieldErrors().size());

            assertEquals("The error field should be same as ", "handle.duplicate",
                impl.getFieldErrors().get("user.handle").get(0));
            assertEquals("The error field should be same as ", "password.match.incorrect", impl
                .getFieldErrors().get("referrerHandle").get(0));
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>execute()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute() throws Exception {
        try {
            Log logger = LogManager.getLog("test");
            impl.setLogger(logger);

            impl.setAuditDAO(new AuditDAOHibernate());
            impl.setUserDAO(new UserDAOHibernate());
            impl.setPrincipalMgr(new MockPrincipalMgrRemote());
            impl.setSecurityGroupDAO(new SecurityGroupDAOHibernate());
            impl.setRegistrationTypeDAO(new RegistrationTypeDAOHibernate());
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            SimpleUser activeUser = new SimpleUser(20, "mike", "123456");
            EasyMock.expect(httpSession.getAttribute("user_obj")).andReturn(activeUser);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession).anyTimes();

            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new MockAuthentication(persistor, tcRequest, tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            SessionInfo sessionInfo = new SessionInfo();
            session.put("sessionInfoSessionKey", sessionInfo);
            impl.setSession(session);
            impl.setAuthenticationSessionKey("webAuthentication");
            impl.setSessionInfoSessionKey("sessionInfoSessionKey");

            HibernateUtils.begin();

            User user = impl.getUserDAO().find(20L);
            CoderReferral coderReferral = new CoderReferral();
            user.getUserProfile().getCoder().setCoderReferral(coderReferral);
            Referral referral = new Referral();
            coderReferral.setReferral(referral);
            impl.setUser(user);
            impl.setReferrerHandle("dok_tester1");

            // Create a DocumentGenerator:
            DocumentGenerator documentGenerator = new DocumentGenerator();
            // Create a FileTemplateSource:
            TemplateSource templateSource = new FileTemplateSource();
            // Set the default template source:
            documentGenerator.setDefaultTemplateSource(templateSource);

            impl.setEmailSubject("emailSubject");
            impl.setEmailSender("sender@topcoder.com");
            impl.setDocumentGenerator(documentGenerator);
            String templatePath = "test_files" + File.separator + "valid-simple-comment.txt";
            impl.setEmailBodyTemplateName(templatePath);

            Set<RegistrationType> userRoles = new HashSet<RegistrationType>();
            userRoles.add(new RegistrationType(3));
            userRoles.add(new RegistrationType(4));
            userRoles.add(new RegistrationType(5));
            impl.setUserRoles(userRoles);

            EasyMock.replay(httpSession, servletRequest, tcRequest, tcResponse);

            String result = impl.execute();

            assertEquals("The return value should be same as ", "success", result);

            EasyMock.verify(httpSession, servletRequest, tcRequest, tcResponse);
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The user's email is null.<br>
     * Expect TCWebException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = TCWebException.class)
    public void testExecute_EmailsNull() throws Exception {
        try {
            Log logger = LogManager.getLog("test");
            impl.setLogger(logger);

            impl.setUserDAO(new UserDAOHibernate());
            impl.setPrincipalMgr(new MockPrincipalMgrRemote());
            impl.setSecurityGroupDAO(new SecurityGroupDAOHibernate());

            HibernateUtils.begin();

            User user = impl.getUserDAO().find(20L);
            CoderReferral coderReferral = new CoderReferral();
            user.getUserProfile().getCoder().setCoderReferral(coderReferral);
            Referral referral = new Referral();
            coderReferral.setReferral(referral);
            user.setEmailAddresses(null);
            impl.setUser(user);
            impl.setUserRoles(user.getRegistrationTypes());
            impl.setReferrerHandle("dok_tester1");

            impl.execute();
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The active user's username is null.<br>
     * Expect TCWebException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = TCWebException.class)
    public void testExecute_ActiveUserNameNull() throws Exception {
        try {
            Log logger = LogManager.getLog("test");
            impl.setLogger(logger);

            impl.setAuditDAO(new AuditDAOHibernate());
            impl.setUserDAO(new UserDAOHibernate());
            impl.setPrincipalMgr(new MockPrincipalMgrRemote());
            impl.setSecurityGroupDAO(new SecurityGroupDAOHibernate());
            impl.setRegistrationTypeDAO(new RegistrationTypeDAOHibernate());
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            SimpleUser activeUser = new SimpleUser(20, null, "123456");
            EasyMock.expect(httpSession.getAttribute("user_obj")).andReturn(activeUser);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession).anyTimes();

            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new MockAuthentication(persistor, tcRequest, tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            SessionInfo sessionInfo = new SessionInfo();
            session.put("sessionInfoSessionKey", sessionInfo);
            impl.setSession(session);
            impl.setAuthenticationSessionKey("webAuthentication");
            impl.setSessionInfoSessionKey("sessionInfoSessionKey");

            HibernateUtils.begin();

            User user = impl.getUserDAO().find(20L);
            CoderReferral coderReferral = new CoderReferral();
            user.getUserProfile().getCoder().setCoderReferral(coderReferral);
            Referral referral = new Referral();
            coderReferral.setReferral(referral);
            impl.setUser(user);
            impl.setUserRoles(user.getRegistrationTypes());

            // Create a DocumentGenerator:
            DocumentGenerator documentGenerator = new DocumentGenerator();
            // Create a FileTemplateSource:
            TemplateSource templateSource = new FileTemplateSource();
            // Set the default template source:
            documentGenerator.setDefaultTemplateSource(templateSource);

            impl.setEmailSubject("emailSubject");
            impl.setEmailSender("sender@topcoder.com");
            impl.setDocumentGenerator(documentGenerator);
            String templatePath = "test_files" + File.separator + "valid-simple-comment.txt";
            impl.setEmailBodyTemplateName(templatePath);

            Set<RegistrationType> userRoles = new HashSet<RegistrationType>();
            userRoles.add(new RegistrationType(3));
            userRoles.add(new RegistrationType(4));
            userRoles.add(new RegistrationType(5));
            impl.setUserRoles(userRoles);

            EasyMock.replay(httpSession, servletRequest, tcRequest, tcResponse);

            impl.execute();
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPrincipalMgr()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetPrincipalMgr() throws Exception {
        assertNull("The initial value should be null", impl.getPrincipalMgr());

        PrincipalMgrRemote expect = EasyMock.createMock(PrincipalMgrRemote.class);

        impl.setPrincipalMgr(expect);

        assertEquals("The return value should be same as ", expect, impl.getPrincipalMgr());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPrincipalMgr(PrincipalMgrRemote)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetPrincipalMgrRemote() throws Exception {
        PrincipalMgrRemote expect = EasyMock.createMock(PrincipalMgrRemote.class);

        impl.setPrincipalMgr(expect);

        assertEquals("The return value should be same as ", expect, impl.getPrincipalMgr());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSecurityGroupDAO()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetSecurityGroupDAO() throws Exception {
        assertNull("The initial value should be null", impl.getSecurityGroupDAO());

        SecurityGroupDAO expect = EasyMock.createMock(SecurityGroupDAO.class);

        impl.setSecurityGroupDAO(expect);

        assertEquals("The return value should be same as ", expect, impl.getSecurityGroupDAO());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSecurityGroupDAO(SecurityGroupDAO)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetSecurityGroupDAORemote() throws Exception {
        SecurityGroupDAO expect = EasyMock.createMock(SecurityGroupDAO.class);

        impl.setSecurityGroupDAO(expect);

        assertEquals("The return value should be same as ", expect, impl.getSecurityGroupDAO());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUserRoles()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetUserRoles() {
        assertNull("The initial value should be null", impl.getUserRoles());

        Set<RegistrationType> expect = new HashSet<RegistrationType>();

        impl.setUserRoles(expect);

        assertEquals("The return value should be same as ", expect, impl.getUserRoles());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUserRoles(Set)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetUserRoles() {
        Set<RegistrationType> expect = new HashSet<RegistrationType>();

        impl.setUserRoles(expect);

        assertEquals("The return value should be same as ", expect, impl.getUserRoles());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSessionInfoSessionKey()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetSessionInfoSessionKey() {
        assertNull("The initial value should be null", impl.getSessionInfoSessionKey());

        String expect = "test";

        impl.setSessionInfoSessionKey(expect);

        assertEquals("The return value should be same as ", expect, impl.getSessionInfoSessionKey());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSessionInfoSessionKey(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetSessionInfoSessionKey() {
        String expect = "test";

        impl.setSessionInfoSessionKey(expect);

        assertEquals("The return value should be same as ", expect, impl.getSessionInfoSessionKey());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUser()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetUser() {
        assertNull("The initial value should be null", impl.getUser());

        User expect = new User();

        impl.setUser(expect);

        assertEquals("The return value should be same as ", expect, impl.getUser());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUser(User)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetUser() {
        User expect = new User();

        impl.setUser(expect);

        assertEquals("The return value should be same as ", expect, impl.getUser());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getVerificationCode()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetVerificationCode() {
        assertNull("The initial value should be null", impl.getVerificationCode());

        String expect = "test";

        impl.setVerificationCode(expect);

        assertEquals("The return value should be same as ", expect, impl.getVerificationCode());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setVerificationCode(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetVerificationCode() {
        String expect = "test";

        impl.setVerificationCode(expect);

        assertEquals("The return value should be same as ", expect, impl.getVerificationCode());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getReferrerHandle()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetReferrerHandle() {
        assertNull("The initial value should be null", impl.getReferrerHandle());

        String expect = "test";

        impl.setReferrerHandle(expect);

        assertEquals("The return value should be same as ", expect, impl.getReferrerHandle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setReferrerHandle(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetReferrerHandle() {
        String expect = "test";

        impl.setReferrerHandle(expect);

        assertEquals("The return value should be same as ", expect, impl.getReferrerHandle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPassword()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetPassword() {
        assertNull("The initial value should be null", impl.getPassword());

        String expect = "test";

        impl.setPassword(expect);

        assertEquals("The return value should be same as ", expect, impl.getPassword());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPassword(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetPassword() {
        String expect = "test";

        impl.setPassword(expect);

        assertEquals("The return value should be same as ", expect, impl.getPassword());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getConfirmPassword()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetConfirmPassword() {
        assertNull("The initial value should be null", impl.getConfirmPassword());

        String expect = "test";

        impl.setConfirmPassword(expect);

        assertEquals("The return value should be same as ", expect, impl.getConfirmPassword());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setConfirmPassword(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetConfirmPassword() {
        String expect = "test";

        impl.setConfirmPassword(expect);

        assertEquals("The return value should be same as ", expect, impl.getConfirmPassword());
    }

    /**
     * <p>
     * Mock class for testing.
     * </p>
     *
     * @author stevenfrog
     * @version 1.0
     */
    class MockCreateAccountAction extends CreateAccountAction {

        /**
         * <p>
         * Override the text method.
         * </p>
         *
         * @param text
         *            the text
         * @return the text string
         */
        @Override
        public String getText(String text) {
            return text;
        }
    }

    /**
     * <p>
     * Mock class for testing.
     * </p>
     *
     * @author stevenfrog
     * @version 1.0
     */
    class MockAuthentication extends BasicAuthentication {

        /**
         * <p>
         * Construct an authentication instance backed by the given persistor and HTTP request and response.
         * </p>
         *
         * @param userPersistor
         *            userPersistor
         * @param request
         *            request
         * @param response
         *            response
         * @throws Exception
         *             if any error occurs
         */
        public MockAuthentication(Persistor userPersistor, TCRequest request, TCResponse response)
            throws Exception {
            super(userPersistor, request, response);
        }

        /**
         * <p>
         * Put a cookie in the response which will allow the user to be recognized on their next visit. The
         * cookie includes the password hash generated by {@link #hashForUser(long)}. public so
         * com.topcoder.web.hs.controller.requests.Base can reach it, a bit of a kludge.
         * </p>
         *
         * @param uid
         *            the user id
         * @param rememberUser
         *            whether to set the cookie or not
         * @throws Exception
         *             if there is a problem creating the has
         */
        @Override
        public void setCookie(long uid, boolean rememberUser) throws Exception {
            // Empty
        }
    }

}
