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
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.dao.hibernate.RegistrationTypeDAOHibernate;
import com.topcoder.web.common.dao.hibernate.UserDAOHibernate;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.RegistrationActionConfigurationException;

/**
 * <p>
 * Unit tests for class <code>ResendAccountActivationEmailAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class ResendAccountActivationEmailActionTest {
    /**
     * <p>
     * Represents the <code>ResendAccountActivationEmailAction</code> instance used to test against.
     * </p>
     */
    private ResendAccountActivationEmailAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ResendAccountActivationEmailActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ResendAccountActivationEmailAction();
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
     * Inheritance test, verifies <code>ResendAccountActivationEmailAction</code> subclasses should be
     * correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof EmailSendingAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ResendAccountActivationEmailAction()</code>.<br>
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
            impl.setRegistrationTypeDAO(new RegistrationTypeDAOHibernate());
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            SimpleUser user = new SimpleUser(20, "mike", "123456");
            EasyMock.expect(httpSession.getAttribute("user_obj")).andReturn(user);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession).anyTimes();

            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new BasicAuthentication(persistor, tcRequest, tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            SessionInfo sessionInfo = new SessionInfo();
            session.put("sessionInfoSessionKey", sessionInfo);
            impl.setSession(session);
            impl.setAuthenticationSessionKey("webAuthentication");
            impl.setSessionInfoSessionKey("sessionInfoSessionKey");

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

            HibernateUtils.begin();

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
     * The user is null.<br>
     * Expect TCWebException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = TCWebException.class)
    public void testExecute_UserNull() throws Exception {
        try {
            Log logger = LogManager.getLog("test");
            impl.setLogger(logger);

            impl.setUserDAO(new UserDAOHibernate());
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession).anyTimes();

            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new BasicAuthentication(persistor, tcRequest, tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            SessionInfo sessionInfo = new SessionInfo();
            session.put("sessionInfoSessionKey", sessionInfo);
            impl.setSession(session);
            impl.setAuthenticationSessionKey("webAuthentication");
            impl.setSessionInfoSessionKey("sessionInfoSessionKey");

            EasyMock.replay(httpSession, servletRequest, tcRequest, tcResponse);

            HibernateUtils.begin();

            impl.execute();
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The sessionInfo is null.<br>
     * Expect TCWebException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = TCWebException.class)
    public void testExecute_SessionInfoNull() throws Exception {
        try {
            Log logger = LogManager.getLog("test");
            impl.setLogger(logger);

            impl.setUserDAO(new UserDAOHibernate());
            impl.setRegistrationTypeDAO(new RegistrationTypeDAOHibernate());
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            SimpleUser user = new SimpleUser(20, "mike", "123456");
            EasyMock.expect(httpSession.getAttribute("user_obj")).andReturn(user);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession).anyTimes();

            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new BasicAuthentication(persistor, tcRequest, tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);
            impl.setAuthenticationSessionKey("webAuthentication");
            impl.setSessionInfoSessionKey("sessionInfoSessionKey");

            EasyMock.replay(httpSession, servletRequest, tcRequest, tcResponse);

            HibernateUtils.begin();

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
            impl.setRegistrationTypeDAO(new RegistrationTypeDAOHibernate());
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            SimpleUser user = new SimpleUser(20, null, "123456");
            EasyMock.expect(httpSession.getAttribute("user_obj")).andReturn(user);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession).anyTimes();

            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new BasicAuthentication(persistor, tcRequest, tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            SessionInfo sessionInfo = new SessionInfo();
            session.put("sessionInfoSessionKey", sessionInfo);
            impl.setSession(session);
            impl.setAuthenticationSessionKey("webAuthentication");
            impl.setSessionInfoSessionKey("sessionInfoSessionKey");

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

            HibernateUtils.begin();

            impl.execute();
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
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

}
