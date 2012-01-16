/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.util.log.Log;

/**
 * <p>
 * Unit tests for class <code>BaseAction</code>.
 * </p>
 *
 * @author hanshuai
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BaseActionTest {
    /**
     * <p>
     * Represents the <code>BaseAction</code> instance used to test against.
     * </p>
     */
    private BaseAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseActionTest.class);
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
     * Accuracy test for the constructor <code>BaseAction()</code>. Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull(impl);
        assertNull("'logger' should be correct.", impl.getLogger());
        assertNull("'auditService' should be correct.", impl.getAuditService());
        assertNull("'authorizationService' should be correct.", impl.getAuthorizationService());
    }

    /**
     * fill the required field of the instance.
     *
     * @param instance
     *            the instance
     */
    public static void fillField(BaseAction instance) {
        instance.setAuditService(EasyMock.createNiceMock(GroupAuditService.class));
        instance.setAuthorizationService(EasyMock.createNiceMock(AuthorizationService.class));
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInit()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCheckInit() {
        impl = new BaseAction() {
        };
        impl.setAuditService(EasyMock.createNiceMock(GroupAuditService.class));
        impl.setAuthorizationService(EasyMock.createNiceMock(AuthorizationService.class));
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * auditService is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit2() {
        impl = new BaseAction() {
        };
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * authorizationService is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit3() {
        impl = new BaseAction() {
        };
        impl.setAuditService(EasyMock.createNiceMock(GroupAuditService.class));
        impl.checkInit();
    }

    /**
     * <p>
     * <code>BaseAction</code> should be subclass of <code>superClassName</code>.
     * </p>
     */
    @Test
    public void testInheritance0() {
        assertTrue("BaseAction should be subclass of ActionSupport.",
                BaseAction.class.getSuperclass() == ActionSupport.class);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLogger()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetLogger() {
        Log value = EasyMock.createNiceMock(Log.class);
        impl.setLogger(value);
        assertEquals("'getLogger' should be correct.", value, impl.getLogger());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setLogger(Logger logger)</code>. The value should be properly set.
     * </p>
     */
    @Test
    public void testSetLogger() {
        Log value = EasyMock.createNiceMock(Log.class);
        impl.setLogger(value);
        assertEquals("'setLogger' should be correct.", value, TestHelper.getField(impl, "logger"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuditService()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetAuditService() {
        GroupAuditService value = EasyMock.createNiceMock(GroupAuditService.class);
        impl.setAuditService(value);
        assertEquals("'getAuditService' should be correct.", value, impl.getAuditService());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAuditService(GroupAuditService auditService)</code>. The value should
     * be properly set.
     * </p>
     */
    @Test
    public void testSetAuditService() {
        GroupAuditService value = EasyMock.createNiceMock(GroupAuditService.class);
        impl.setAuditService(value);
        assertEquals("'setAuditService' should be correct.", value, TestHelper.getField(impl, "auditService"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAuthorizationService()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetAuthorizationService() {
        AuthorizationService value = EasyMock.createNiceMock(AuthorizationService.class);
        impl.setAuthorizationService(value);
        assertEquals("'getAuthorizationService' should be correct.", value, impl.getAuthorizationService());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAuthorizationService(AuthorizationService authorizationService)</code>
     * . The value should be properly set.
     * </p>
     */
    @Test
    public void testSetAuthorizationService() {
        AuthorizationService value = EasyMock.createNiceMock(AuthorizationService.class);
        impl.setAuthorizationService(value);
        assertEquals("'setAuthorizationService' should be correct.", value,
                TestHelper.getField(impl, "authorizationService"));
    }
}
