/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.actions.basic.BaseAction;
import com.topcoder.web.reg.actions.basic.LogoutAction;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BaseAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class BaseActionFailureTests extends TestCase {
    /**
     * <p>
     * The BaseAction instance for testing.
     * </p>
     */
    private BaseAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new LogoutAction();
        instance.setOperationType("operationType");
        instance.setAuditDAO(new AuditDAOHibernate());
        instance.setWebAuthenticationSessionKey("webAuthenticationSessionKey");
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BaseActionFailureTests.class);
    }

    /**
     * <p>
     * Tests BaseAction#checkConfiguration() for failure.
     * It tests the case that when auditDAO is null and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullAuditDAO() {
        instance.setAuditDAO(null);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseAction#checkConfiguration() for failure.
     * It tests the case that when webAuthenticationSessionKey is null and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testcheckConfiguration_NullWebAuthenticationSessionKey() {
        instance.setWebAuthenticationSessionKey(null);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseAction#checkConfiguration() for failure.
     * It tests the case that when webAuthenticationSessionKey is empty and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testcheckConfiguration_EmptyWebAuthenticationSessionKey() {
        instance.setWebAuthenticationSessionKey(" ");
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseAction#checkConfiguration() for failure.
     * It tests the case that when operationType is null and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testcheckConfiguration_NullOperationType() {
        instance.setOperationType(null);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseAction#checkConfiguration() for failure.
     * It tests the case that when operationType is empty and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testcheckConfiguration_EmptyOperationType() {
        instance.setOperationType(" ");
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

}