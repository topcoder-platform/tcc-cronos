/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.failuretests;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.timetracker.company.ejb.LocalCompanyManagerDelegate;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure test <code>{@link LocalCompanyManagerDelegate}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class LocalCompanyManagerDelegateFailureTests extends TestCase {

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
        ConfigManager.getInstance().add("failuretests" + File.separator + "LocalCompanyManagerDelegate.xml");
    }

    /**
     * <p>
     * Tear down the testing environment.
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
     * Failure test for <code>{@link LocalCompanyManagerDelegate#LocalCompanyManagerDelegate(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testLocalCompanyManagerDelegate_NullNamespace() throws Exception {
        try {
            new LocalCompanyManagerDelegate(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link LocalCompanyManagerDelegate#LocalCompanyManagerDelegate(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testLocalCompanyManagerDelegate_EmptyNamespace() throws Exception {
        try {
            new LocalCompanyManagerDelegate("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link LocalCompanyManagerDelegate#LocalCompanyManagerDelegate(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testLocalCompanyManagerDelegate_TrimmedEmptyNamespace() throws Exception {
        try {
            new LocalCompanyManagerDelegate(" ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link LocalCompanyManagerDelegate#LocalCompanyManagerDelegate(String)}</code> method.
     * </p>
     */
    public void testLocalCompanyManagerDelegate_UnknownNamespace() {
        try {
            new LocalCompanyManagerDelegate("UnknownNamespace");
            fail("expected throw InstantiationException");
        } catch (com.topcoder.timetracker.company.ejb.InstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link LocalCompanyManagerDelegate#LocalCompanyManagerDelegate(String)}</code> method.
     * </p>
     */
    public void testLocalCompanyManagerDelegate_InvalidNamespace3() {
        try {
            new LocalCompanyManagerDelegate(
                "com.topcoder.timetracker.company.ejb.LocalCompanyManagerDelegate.invalidcase3");
            fail("expected throw InstantiationException");
        } catch (com.topcoder.timetracker.company.ejb.InstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link LocalCompanyManagerDelegate#LocalCompanyManagerDelegate(String)}</code> method.
     * </p>
     */
    public void testLocalCompanyManagerDelegate_InvalidNamespace1() {
        try {
            new LocalCompanyManagerDelegate(
                "com.topcoder.timetracker.company.ejb.LocalCompanyManagerDelegate.invalidcase1");
            fail("expected throw InstantiationException");
        } catch (com.topcoder.timetracker.company.ejb.InstantiationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link LocalCompanyManagerDelegate#LocalCompanyManagerDelegate(String)}</code> method.
     * </p>
     */
    public void testLocalCompanyManagerDelegate_InvalidNamespace2() {
        try {
            new LocalCompanyManagerDelegate(
                "com.topcoder.timetracker.company.ejb.LocalCompanyManagerDelegate.invalidcase2");
            fail("expected throw InstantiationException");
        } catch (com.topcoder.timetracker.company.ejb.InstantiationException e) {
            // expected
        }
    }
}
