/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.registration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.TCRequest;
import com.topcoder.web.common.TCResponse;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.dao.hibernate.RegistrationTypeDAOHibernate;
import com.topcoder.web.common.dao.hibernate.UserDAOHibernate;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
import com.topcoder.web.common.security.WebAuthentication;

/**
 * <p>
 * Unit tests for class <code>ViewRegistrationPreferenceAction</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class ViewRegistrationPreferenceActionTest {
    /**
     * <p>
     * Represents the <code>ViewRegistrationPreferenceAction</code> instance used to test against.
     * </p>
     */
    private ViewRegistrationPreferenceAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ViewRegistrationPreferenceActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ViewRegistrationPreferenceAction();
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
     * Inheritance test, verifies <code>ViewRegistrationPreferenceAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseRegistrationAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ViewRegistrationPreferenceAction()</code>.<br>
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

            EasyMock.replay(httpSession, servletRequest, tcRequest, tcResponse);

            HibernateUtils.begin();

            String result = impl.execute();

            assertEquals("The return value should be same as ", "success", result);
            assertNotNull("The return value should not be null ", impl.getAllRegistrationTypes());
            assertEquals("The return value should be same as ", 7, impl.getAllRegistrationTypes().size());
            assertNotNull("The return value should not be null ", impl.getUserRegistrationTypes());
            assertEquals("The return value should be same as ", 5, impl.getUserRegistrationTypes().size());

            EasyMock.verify(httpSession, servletRequest, tcRequest, tcResponse);
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
     * Accuracy test for the method <code>getAllRegistrationTypes()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetAllRegistrationTypes() {
        assertNull("The initial value should be null", impl.getAllRegistrationTypes());

        Set<RegistrationType> expect = new HashSet<RegistrationType>();

        impl.setAllRegistrationTypes(expect);

        assertEquals("The return value should be same as ", expect, impl.getAllRegistrationTypes());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAllRegistrationTypes(Set)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetAllRegistrationTypes() {
        Set<RegistrationType> expect = new HashSet<RegistrationType>();

        impl.setAllRegistrationTypes(expect);

        assertEquals("The return value should be same as ", expect, impl.getAllRegistrationTypes());
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
}
