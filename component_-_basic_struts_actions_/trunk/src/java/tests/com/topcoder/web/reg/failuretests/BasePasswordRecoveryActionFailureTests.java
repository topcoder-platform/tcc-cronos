/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import java.io.File;

import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.actions.basic.BasePasswordRecoveryAction;
import com.topcoder.web.reg.actions.basic.RecoverPasswordAction;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BasePasswordRecoveryAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class BasePasswordRecoveryActionFailureTests extends TestCase {
    /**
     * <p>
     * The BasePasswordRecoveryAction instance for testing.
     * </p>
     */
    private BasePasswordRecoveryAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new RecoverPasswordAction();
        instance.setEmailBodyTemplateFilePath("test_files" + File.separator + "mailBodyTemplate.txt");
        instance.setEmailFromAddress("emailFromAddress");
        instance.setEmailSubject("emailSubject");
        instance.setResetPasswordLinkTemplate("Id:%passwordRecoveryId%, hashCode:%hashCode%");
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BasePasswordRecoveryActionFailureTests.class);
    }

    /**
     * <p>
     * Tests BasePasswordRecoveryAction#checkConfiguration() for failure.
     * It tests the case that when emailSubject is null and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullEmailSubject() {
        instance.setEmailSubject(null);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BasePasswordRecoveryAction#checkConfiguration() for failure.
     * It tests the case that when emailSubject is empty and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_EmptyEmailSubject() {
        instance.setEmailSubject(" ");
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BasePasswordRecoveryAction#checkConfiguration() for failure.
     * It tests the case that when emailBodyTemplateFilePath is null and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullemailBodyTemplateFilePath() {
        instance.setEmailBodyTemplateFilePath(null);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BasePasswordRecoveryAction#checkConfiguration() for failure.
     * It tests the case that when emailBodyTemplateFilePath is empty and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_EmptyemailBodyTemplateFilePath() {
        instance.setEmailBodyTemplateFilePath(" ");
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BasePasswordRecoveryAction#checkConfiguration() for failure.
     * It tests the case that when emailBodyTemplateFilePath is invalid and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_InvalidemailBodyTemplateFilePath() {
        instance.setEmailBodyTemplateFilePath("test_files" + File.separator + "failure" + File.separator
            + "mailBodyTemplateError.txt");
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BasePasswordRecoveryAction#checkConfiguration() for failure.
     * It tests the case that when emailFromAddress is null and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullemailFromAddress() {
        instance.setEmailFromAddress(null);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BasePasswordRecoveryAction#checkConfiguration() for failure.
     * It tests the case that when emailFromAddress is empty and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_EmptyemailFromAddress() {
        instance.setEmailFromAddress(" ");
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BasePasswordRecoveryAction#checkConfiguration() for failure.
     * It tests the case that when resetPasswordLinkTemplate is null and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullresetPasswordLinkTemplate() {
        instance.setResetPasswordLinkTemplate(null);
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BasePasswordRecoveryAction#checkConfiguration() for failure.
     * It tests the case that when resetPasswordLinkTemplate is empty and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_EmptyresetPasswordLinkTemplate() {
        instance.setResetPasswordLinkTemplate(" ");
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BasePasswordRecoveryAction#checkConfiguration() for failure.
     * It tests the case that when resetPasswordLinkTemplate is invalid and expects BasicStrutsActionsConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_InvalidresetPasswordLinkTemplate() {
        instance.setResetPasswordLinkTemplate("invalid");
        try {
            instance.checkConfiguration();
            fail("BasicStrutsActionsConfigurationException expected.");
        } catch (BasicStrutsActionsConfigurationException e) {
            //good
        }
    }
}