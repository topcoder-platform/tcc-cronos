/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.failuretests;

import com.topcoder.timetracker.audit.AuditConfigurationException;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link AuditDelegate}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class AuditDelegateFailureTests extends TestCase {

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        FailureTestHelper.clearNamespaces();
        FailureTestHelper.loadNamesapces(FailureTestHelper.AUDITDELEGATE_CONFIG);
    }

    /**
     * <p>
     * Teardown the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.clearNamespaces();
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditDelegate#AuditDelegate(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAuditDelegate_NullNamespace() throws Exception {
        try {
            new AuditDelegate(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditDelegate#AuditDelegate(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAuditDelegate_EmptyNamespace() throws Exception {
        try {
            new AuditDelegate("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditDelegate#AuditDelegate(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAuditDelegate_TrimmedNamespace() throws Exception {
        try {
            new AuditDelegate(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditDelegate#AuditDelegate(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAuditDelegate_InvalidConfig_Case1() throws Exception {
        try {
            new AuditDelegate("com.topcoder.timetracker.audit.ejb.AuditDelegate.invalid.case1");
            fail("expect throw AuditConfigurationException.");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditDelegate#AuditDelegate(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAuditDelegate_InvalidConfig_Case2() throws Exception {
        try {
            new AuditDelegate("com.topcoder.timetracker.audit.ejb.AuditDelegate.invalid.case2");
            fail("expect throw AuditConfigurationException.");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link AuditDelegate#AuditDelegate(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testAuditDelegate_InvalidConfig_Case3() throws Exception {
        try {
            new AuditDelegate("com.topcoder.timetracker.audit.ejb.AuditDelegate.invalid.case3");
            fail("expect throw AuditConfigurationException.");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }
}
