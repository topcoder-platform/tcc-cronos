/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestSuite;
import junit.framework.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.Notification;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.miscellaneous.EmailNotificationAction;

/**
 * <p>
 * Accuracy Unit test cases for EmailNotificationAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class EmailNotificationActionAccuracyTests extends BaseAccuracyTest {
    /**
     * <p>EmailNotificationAction instance for testing.</p>
     */
    private EmailNotificationAction instance;

    /**
     * <p>Setup test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        super.setUp();
        ActionProxy proxy = getActionProxy("/email");
        instance = (EmailNotificationAction) proxy.getAction();
    }

    /**
     * <p>Tears down test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(EmailNotificationActionAccuracyTests.class);
    }

    /**
     * <p>Tests ctor EmailNotificationAction#EmailNotificationAction() for accuracy.</p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create EmailNotificationAction instance.", instance);
    }

    /**
     * <p>Tests EmailNotificationAction#setNotifications(Set) for accuracy.</p>
     */
    public void testSetNotifications() {
        Set<Notification> notifications = new HashSet<Notification>();
        instance.setNotifications(notifications);
        assertEquals("Failed to setNotifications correctly.", 0, instance.getNotifications().size());
    }

    /**
     * <p>Tests EmailNotificationAction#execute() for accuracy.</p>
     * @throws Exception to JUnit
     */
    public void testExecute() throws Exception {
        setBasePreferencesActionDAOs(instance);
        putKeyValueToSession(instance.getBasicAuthenticationSessionKey(), createAuthentication());
        User loggedInUser = createUser(1L, "First", "Second", "handle");
        when(instance.getUserDao().find(1L)).thenReturn(loggedInUser);

        instance.setAction(SUBMIT_ACTION);
        assertEquals("Failed to execute correctly.", ActionSupport.SUCCESS, instance.execute());
        verify(instance.getAuditDao(), times(1)).audit(any(AuditRecord.class));
        verify(instance.getUserDao(), times(1)).saveOrUpdate(any(User.class));
    }

}