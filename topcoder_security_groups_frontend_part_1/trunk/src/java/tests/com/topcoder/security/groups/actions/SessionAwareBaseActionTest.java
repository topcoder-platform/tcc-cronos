/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.GroupAuditService;

/**
 * <p>
 * Unit tests for class <code>SessionAwareBaseAction</code>.
 * </p>
 *
 * @author hanshuai
 * @version 1.0
 */
public class SessionAwareBaseActionTest {
    /**
     * <p>
     * Represents the <code>SessionAwareBaseAction</code> instance used to test against.
     * </p>
     */
    private SessionAwareBaseAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(SessionAwareBaseActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new CreateGroupAction();
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
     * Accuracy test for the constructor <code>SessionAwareBaseAction()</code>. Instance should be correctly
     * created.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull(impl);
        assertNull("'session' should be correct.", impl.getSession());
    }

    /**
     * <p>
     * <code>SessionAwareBaseAction</code> should be subclass of <code>superClassName</code>.
     * </p>
     */
    @Test
    public void testInheritance0() {
        assertTrue("SessionAwareBaseAction should be subclass of BaseAction.",
                SessionAwareBaseAction.class.getSuperclass() == BaseAction.class);
    }

    /**
     * fill the required field of the instance.
     *
     * @param instance
     *            the instance
     */
    public static void fillField(SessionAwareBaseAction instance) {
        BaseActionTest.fillField(instance);
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("key", "value");
        instance.setSession(session);
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInit()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @SuppressWarnings("serial")
    @Test
    public void testCheckInit() {
        impl = new SessionAwareBaseAction() {
        };
        impl.setAuditService(EasyMock.createNiceMock(GroupAuditService.class));
        impl.setAuthorizationService(EasyMock.createNiceMock(AuthorizationService.class));
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("key", "value");
        impl.setSession(session);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * session is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @SuppressWarnings("serial")
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit2() {
        impl = new SessionAwareBaseAction() {
        };
        impl.setAuditService(EasyMock.createNiceMock(GroupAuditService.class));
        impl.setAuthorizationService(EasyMock.createNiceMock(AuthorizationService.class));
        impl.checkInit();
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSession()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetSession() {
        Map<String, Object> value = new HashMap<String, Object>();
        impl.setSession(value);
        assertEquals("'getSession' should be correct.", value, impl.getSession());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSession(Map session)</code>. The value should be properly set.
     * </p>
     */
    @Test
    public void testSetSession() {
        Map<String, Object> value = new HashMap<String, Object>();
        impl.setSession(value);
        assertEquals("'setSession' should be correct.", value, TestHelper.getField(impl, "session"));
    }
}
