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

import com.topcoder.randomstringimg.RandomStringImage;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.TCRequest;
import com.topcoder.web.common.TCResponse;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.RegistrationTypeDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.RegistrationActionConfigurationException;
import com.topcoder.web.tc.Constants;

/**
 * <p>
 * Unit tests for class <code>PreCreateAccountAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class PreCreateAccountActionTest {
    /**
     * <p>
     * Represents the <code>PreCreateAccountAction</code> instance used to test against.
     * </p>
     */
    private PreCreateAccountAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PreCreateAccountActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new PreCreateAccountAction();
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
     * Inheritance test, verifies <code>PreCreateAccountAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseRegistrationAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>PreCreateAccountAction()</code>.<br>
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
        setFieldForBase();

        RandomStringImage captchaImageGenerator = EasyMock.createMock(RandomStringImage.class);
        impl.setCaptchaImageGenerator(captchaImageGenerator);

        impl.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code>.<br>
     * The captchaImageGenerator is not set.<br>
     * Expect RegistrationActionConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = RegistrationActionConfigurationException.class)
    public void testCheckInitialization_CaptchaImageGeneratorNotSet() throws Exception {
        setFieldForBase();

        impl.checkInitialization();
    }

    /**
     * <p>
     * Set the field of base class.
     * </p>
     */
    private void setFieldForBase() {
        AuditDAO auditDAO = EasyMock.createNiceMock(AuditDAO.class);
        impl.setAuditDAO(auditDAO);
        Log logger = LogManager.getLog("test");
        impl.setLogger(logger);
        impl.setAuthenticationSessionKey("webAuthentication");
        Map<String, Object> session = new HashMap<String, Object>();
        impl.setSession(session);
        UserDAO userDAO = EasyMock.createNiceMock(UserDAO.class);
        impl.setUserDAO(userDAO);
        RegistrationTypeDAO registrationTypeDAO = EasyMock.createNiceMock(RegistrationTypeDAO.class);
        impl.setRegistrationTypeDAO(registrationTypeDAO);
        HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
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

            Constants.CAPTCHA_PATH = "test_files" + File.separator;

            RandomStringImage captchaImageGenerator = new RandomStringImage(
                "conf/com/topcoder/randomstringimg/RandomStringImage.xml");
            impl.setCaptchaImageGenerator(captchaImageGenerator);

            EasyMock.replay(httpSession, servletRequest, tcRequest, tcResponse);

            HibernateUtils.begin();

            String result = impl.execute();

            assertEquals("The return value should be same as ", "success", result);
            assertNotNull("The return value should be not null ", session.get(Constants.CAPTCHA_WORD));

            String filename = impl.getCapfname();
            File file = new File(Constants.CAPTCHA_PATH + filename);
            assertTrue("The return value should be true ", file.exists());
            file.delete();

            EasyMock.verify(httpSession, servletRequest, tcRequest, tcResponse);
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The config is invalid.<br>
     * Expect TCWebException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = TCWebException.class)
    public void testExecute_ConfigError() throws Exception {
        try {
            Log logger = LogManager.getLog("test");
            impl.setLogger(logger);

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

            Constants.CAPTCHA_PATH = "test_files" + File.separator;

            RandomStringImage captchaImageGenerator = new RandomStringImage(
                "conf/com/topcoder/randomstringimg/RandomStringImage.xml");
            captchaImageGenerator.getConfiguration().setMaxFontSize(800f);
            captchaImageGenerator.getConfiguration().setMinFontSize(750f);
            impl.setCaptchaImageGenerator(captchaImageGenerator);

            EasyMock.replay(httpSession, servletRequest, tcRequest, tcResponse);

            impl.execute();

            EasyMock.verify(httpSession, servletRequest, tcRequest, tcResponse);
        } finally {
            String filename = impl.getCapfname();
            File file = new File(Constants.CAPTCHA_PATH + filename);
            if (file.exists()) {
                file.delete();
            }
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
        Log logger = LogManager.getLog("test");
        impl.setLogger(logger);

        Map<String, Object> session = new HashMap<String, Object>();
        impl.setSession(session);
        impl.setAuthenticationSessionKey("webAuthentication");

        impl.execute();

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

            Constants.CAPTCHA_PATH = "test_files" + File.separator;

            RandomStringImage captchaImageGenerator = new RandomStringImage(
                "conf/com/topcoder/randomstringimg/RandomStringImage.xml");
            impl.setCaptchaImageGenerator(captchaImageGenerator);

            EasyMock.replay(httpSession, servletRequest, tcRequest, tcResponse);

            HibernateUtils.begin();

            impl.execute();
        } finally {
            String filename = impl.getCapfname();
            File file = new File(Constants.CAPTCHA_PATH + filename);
            if (file.exists()) {
                file.delete();
            }

            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCaptchaImageGenerator()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetCaptchaImageGenerator() {
        assertNull("The initial value should be null", impl.getCaptchaImageGenerator());

        RandomStringImage expect = EasyMock.createMock(RandomStringImage.class);

        impl.setCaptchaImageGenerator(expect);

        assertEquals("The return value should be same as ", expect, impl.getCaptchaImageGenerator());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCaptchaImageGenerator(RandomStringImage)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetCaptchaImageGenerator() {
        RandomStringImage expect = EasyMock.createMock(RandomStringImage.class);

        impl.setCaptchaImageGenerator(expect);

        assertEquals("The return value should be same as ", expect, impl.getCaptchaImageGenerator());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCapfname()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetCapfname() {
        assertNull("The initial value should be null", impl.getCapfname());

        String expect = "newKey";

        impl.setCapfname(expect);

        assertEquals("The return value should be same as ", expect, impl.getCapfname());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCapfname(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetCapfname() {
        String expect = "newKey";

        impl.setCapfname(expect);

        assertEquals("The return value should be same as ", expect, impl.getCapfname());
    }

}
