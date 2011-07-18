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
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.dao.hibernate.UserDAOHibernate;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
import com.topcoder.web.common.security.WebAuthentication;

/**
 * <p>
 * Unit tests for class <code>ActivateAccountAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class ActivateAccountActionTest {
    /**
     * <p>
     * Represents the <code>ActivateAccountAction</code> instance used to test against.
     * </p>
     */
    private ActivateAccountAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ActivateAccountActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ActivateAccountAction();
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
     * Inheritance test, verifies <code>ActivateAccountAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof EmailSendingAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ActivateAccountAction()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
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
            String templatePath = "test_files" + File.separator + "mailBodyTemplate.txt";
            impl.setEmailBodyTemplateName(templatePath);

            impl.setActivationCode("1M1");

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

            impl.setActivationCode("1M1abcdefgh");

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
     * The authentication got by authenticationSessionKey is null.<br>
     * Expect TCWebException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = TCWebException.class)
    public void testExecute_AuthenticationNull() throws Exception {
        try {
            Log logger = LogManager.getLog("test");
            impl.setLogger(logger);

            impl.setAuditDAO(new AuditDAOHibernate());
            impl.setUserDAO(new UserDAOHibernate());
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            SimpleUser user = new SimpleUser(20, "mike", "123456");
            EasyMock.expect(httpSession.getAttribute("user_obj")).andReturn(user);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession).anyTimes();

            Map<String, Object> session = new HashMap<String, Object>();
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
            String templatePath = "test_files" + File.separator + "mailBodyTemplate.txt";
            impl.setEmailBodyTemplateName(templatePath);

            impl.setActivationCode("1M1");

            EasyMock.replay(httpSession, servletRequest);

            HibernateUtils.begin();

            String result = impl.execute();

            assertEquals("The return value should be same as ", "success", result);

            EasyMock.verify(httpSession, servletRequest);
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
            String templatePath = "test_files" + File.separator + "mailBodyTemplate.txt";
            impl.setEmailBodyTemplateName(templatePath);

            impl.setActivationCode("1M1");

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
     * Accuracy test for the method <code>getActivationCode()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetActivationCode() {
        assertNull("The initial value should be null", impl.getActivationCode());

        String expect = "test";

        impl.setActivationCode(expect);

        assertEquals("The return value should be same as ", expect, impl.getActivationCode());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setActivationCode(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetActivationCode() {
        String expect = "test";

        impl.setActivationCode(expect);

        assertEquals("The return value should be same as ", expect, impl.getActivationCode());
    }

}
