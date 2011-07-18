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
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.RegistrationActionConfigurationException;

/**
 * <p>
 * Unit tests for class <code>SelectRegistrationPreferenceAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class SelectRegistrationPreferenceActionTest {
    /**
     * <p>
     * Represents the <code>SelectRegistrationPreferenceAction</code> instance used to test against.
     * </p>
     */
    private SelectRegistrationPreferenceAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(SelectRegistrationPreferenceActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new MockSelectRegistrationPreferenceAction();
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
     * Inheritance test, verifies <code>SelectRegistrationPreferenceAction</code> subclasses should be
     * correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof EmailSendingAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>SelectRegistrationPreferenceAction()</code>.<br>
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

        impl.setSecurityGroupDAO(new SecurityGroupDAOHibernate());
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
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate() {
        try {
            HibernateUtils.begin();

            RegistrationTypeDAO registrationTypeDAO = new RegistrationTypeDAOHibernate();
            impl.setRegistrationTypeDAO(registrationTypeDAO);
            impl.setUserRegistrationTypes(new HashSet<RegistrationType>(registrationTypeDAO
                .getRegistrationTypes()));

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

            RegistrationTypeDAO registrationTypeDAO = new RegistrationTypeDAOHibernate();
            impl.setRegistrationTypeDAO(registrationTypeDAO);

            Set<RegistrationType> userRegistrationTypes = new HashSet<RegistrationType>();
            userRegistrationTypes.add(new RegistrationType(-1));
            userRegistrationTypes.add(new RegistrationType(-2));
            userRegistrationTypes.add(new RegistrationType(-3));
            impl.setUserRegistrationTypes(userRegistrationTypes);

            impl.validate();

            assertEquals("The error map size should be same as ", 1, impl.getFieldErrors().size());
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
            impl.setSecurityGroupDAO(new SecurityGroupDAOHibernate());
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

            Set<RegistrationType> userRegistrationTypes = new HashSet<RegistrationType>();
            userRegistrationTypes.add(new RegistrationType(3));
            userRegistrationTypes.add(new RegistrationType(4));
            userRegistrationTypes.add(new RegistrationType(5));
            impl.setUserRegistrationTypes(userRegistrationTypes);

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
            impl.setSecurityGroupDAO(new SecurityGroupDAOHibernate());
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
            impl.setSession(session);
            impl.setAuthenticationSessionKey("webAuthentication");

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
            impl.setSecurityGroupDAO(new SecurityGroupDAOHibernate());
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
            impl.setSession(session);
            impl.setAuthenticationSessionKey("webAuthentication");

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

            Set<RegistrationType> userRegistrationTypes = new HashSet<RegistrationType>();
            userRegistrationTypes.add(new RegistrationType(3));
            userRegistrationTypes.add(new RegistrationType(4));
            userRegistrationTypes.add(new RegistrationType(5));
            impl.setUserRegistrationTypes(userRegistrationTypes);

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
     * Accuracy test for the method <code>getUserRegistrationTypes()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetUserRegistrationTypes() {
        assertNull("The initial value should be null", impl.getUserRegistrationTypes());

        Set<RegistrationType> expect = new HashSet<RegistrationType>();

        impl.setUserRegistrationTypes(expect);

        assertEquals("The return value should be same as ", expect, impl.getUserRegistrationTypes());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUserRegistrationTypes(Set)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetUserRegistrationTypes() {
        Set<RegistrationType> expect = new HashSet<RegistrationType>();

        impl.setUserRegistrationTypes(expect);

        assertEquals("The return value should be same as ", expect, impl.getUserRegistrationTypes());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSecurityGroupDAO()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetSecurityGroupDAO() {
        assertNull("The initial value should be null", impl.getSecurityGroupDAO());

        SecurityGroupDAO expect = EasyMock.createMock(SecurityGroupDAO.class);

        impl.setSecurityGroupDAO(expect);

        assertEquals("The return value should be same as ", expect, impl.getSecurityGroupDAO());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSecurityGroupDAO(Set)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetSecurityGroupDAO() {
        SecurityGroupDAO expect = EasyMock.createMock(SecurityGroupDAO.class);

        impl.setSecurityGroupDAO(expect);

        assertEquals("The return value should be same as ", expect, impl.getSecurityGroupDAO());
    }

    /**
     * <p>
     * Mock class for testing.
     * </p>
     *
     * @author stevenfrog
     * @version 1.0
     */
    class MockSelectRegistrationPreferenceAction extends SelectRegistrationPreferenceAction {

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

}
