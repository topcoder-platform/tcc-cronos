/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.TCRequest;
import com.topcoder.web.common.TCResponse;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
import com.topcoder.web.common.security.WebAuthentication;

/**
 * <p>
 * Unit tests for class <code>LogoutAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LogoutActionTest {
    /**
     * <p>
     * Represents the <code>LogoutAction</code> instance used to test against.
     * </p>
     */
    private LogoutAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(LogoutActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new LogoutAction();
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
     * Inheritance test, verifies <code>LogoutAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseAction);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>LogoutAction()</code>.<br>
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
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            SimpleUser user = new SimpleUser(2222, "mike", "123456");
            EasyMock.expect(httpSession.getAttribute("user_obj")).andReturn(user);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession);

            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new BasicAuthentication(persistor, tcRequest, tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

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
     * The user name is null.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testExecute_UsernameNull() throws Exception {
        AuditDAO auditDAO = new AuditDAOHibernate();
        impl.setAuditDAO(auditDAO);
        impl.setOperationType("op");
        HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
        SimpleUser user = new SimpleUser(2222, null, "123456");
        EasyMock.expect(httpSession.getAttribute("user_obj")).andReturn(user);
        HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
        impl.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getSession()).andReturn(httpSession);

        TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
        TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
        SessionPersistor persistor = new SessionPersistor(httpSession);
        WebAuthentication webAuthentication = new BasicAuthentication(persistor, tcRequest, tcResponse);
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("webAuthentication", webAuthentication);
        impl.setSession(session);

        EasyMock.replay(httpSession, servletRequest, tcRequest, tcResponse);

        impl.execute();
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * The HttpSession has invalidated.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testExecute_HttpSessionInvalidated() throws Exception {
        try {
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            httpSession.invalidate();
            EasyMock.expectLastCall().andThrow(
                new IllegalStateException("this method is called on an already invalidated session"));
            SimpleUser user = new SimpleUser(2222, "mike", "123456");
            EasyMock.expect(httpSession.getAttribute("user_obj")).andReturn(user);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession);

            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            TCResponse tcResponse = EasyMock.createNiceMock(TCResponse.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new BasicAuthentication(persistor, tcRequest, tcResponse);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

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
     * The BasicAuthentication is read only.<br>
     * Expect BasicActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BasicActionException.class)
    public void testExecute_RuntimeException() throws Exception {
        try {
            AuditDAO auditDAO = new AuditDAOHibernate();
            impl.setAuditDAO(auditDAO);
            impl.setOperationType("op");
            HttpSession httpSession = EasyMock.createNiceMock(HttpSession.class);
            SimpleUser user = new SimpleUser(2222, "mike", "123456");
            EasyMock.expect(httpSession.getAttribute("user_obj")).andReturn(user);
            HttpServletRequest servletRequest = EasyMock.createNiceMock(HttpServletRequest.class);
            impl.setServletRequest(servletRequest);
            EasyMock.expect(servletRequest.getRemoteAddr()).andReturn("localhost");
            EasyMock.expect(servletRequest.getSession()).andReturn(httpSession);

            TCRequest tcRequest = EasyMock.createNiceMock(TCRequest.class);
            SessionPersistor persistor = new SessionPersistor(httpSession);
            WebAuthentication webAuthentication = new BasicAuthentication(persistor, tcRequest, null);
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("webAuthentication", webAuthentication);
            impl.setSession(session);

            EasyMock.replay(httpSession, servletRequest, tcRequest);

            HibernateUtils.begin();

            String result = impl.execute();

            assertEquals("The return value should be same as ", "success", result);

            EasyMock.verify(httpSession, servletRequest, tcRequest);
        } finally {
            HibernateUtils.getSession().clear();
            HibernateUtils.close();
        }
    }

}
