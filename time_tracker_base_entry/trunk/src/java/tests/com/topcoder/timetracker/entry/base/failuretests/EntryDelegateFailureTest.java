/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.failuretests;

import java.util.Date;

import com.topcoder.timetracker.entry.base.BaseEntry;
import com.topcoder.timetracker.entry.base.ConfigurationException;
import com.topcoder.timetracker.entry.base.CutoffTimeBean;
import com.topcoder.timetracker.entry.base.EntryManagerException;
import com.topcoder.timetracker.entry.base.ejb.EntryDelegate;

import junit.framework.TestCase;

/**
 * <p>
 * The failure test for <code>EntryDelegate</code>.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class EntryDelegateFailureTest extends TestCase {

    /**
     * Default namespace for EntryDelegate.
     */
    private static final String DEFAULT_NAMESPACE = "EntryDelegate";

    /**
     * The EntryDelegate instance used for failure test.
     */
    private EntryDelegate entryDelegate = null;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.clearConfig();
        FailureTestHelper.loadDefaultConfig();
        FailureTestHelper.loadConfig("DBConnectionFactory.xml");
        FailureTestHelper.loadConfig("EntryDelegateTest.xml");
        FailureTestHelper.backupConfig("EntryDelegateTest.xml");
        FailureTestHelper.initDB();
        FailureTestHelper.initJNDI();

        entryDelegate = new EntryDelegate(DEFAULT_NAMESPACE);
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.restoreConfig("EntryDelegateTest.xml");
        FailureTestHelper.restoreJNDI();
        FailureTestHelper.clearConfig();
    }

    /**
     * <p>
     * Failure test for EntryDelegate(String namespace).
     * </p>
     *
     * <p>
     * namespace is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEntryDelegateFailure1() throws Exception {
        try {
            new EntryDelegate(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for EntryDelegate(String namespace).
     * </p>
     *
     * <p>
     * namespace is empty.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEntryDelegateFailure2() throws Exception {
        try {
            new EntryDelegate("");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for EntryDelegate(String namespace).
     * </p>
     *
     * <p>
     * namespace is empty after trimmed.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEntryDelegateFailure3() throws Exception {
        try {
            new EntryDelegate("  ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for EntryDelegate(String namespace).
     * </p>
     *
     * <p>
     * namespace is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testEntryDelegateFailure4() throws Exception {
        try {
            new EntryDelegate("invalid_namespace");
            fail("ConfigurationException is expected.");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for canSubmitEntry(BaseEntry entry).
     * </p>
     *
     * <p>
     * entry is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCanSubmitEntryFailure1() throws Exception {
        try {
            entryDelegate.canSubmitEntry(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for canSubmitEntry(BaseEntry entry).
     * </p>
     *
     * <p>
     * entry has no date.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCanSubmitEntryFailure2() throws Exception {
        try {
            entryDelegate.canSubmitEntry(new BaseEntry() {
            });
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for createCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * cutoffTimeBean is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeFailure1() throws Exception {
        try {
            entryDelegate.createCutoffTime(null, false);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for createCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * cutoffTimeBean is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeFailure2() throws Exception {
        CutoffTimeBean bean = new CutoffTimeBean();
        bean.setCompanyId(10000);
        bean.setCreationUser("user");
        bean.setCutoffTime(new Date());
        try {
            entryDelegate.createCutoffTime(bean, true);
            fail("EntryManagerException is expected");
        } catch (EntryManagerException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for createCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * cutoffTimeBean is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeFailure3() throws Exception {
        CutoffTimeBean bean = new CutoffTimeBean();
        bean.setCompanyId(1);
        bean.setCutoffTime(new Date());
        try {
            entryDelegate.createCutoffTime(bean, true);
            fail("EntryManagerException is expected");
        } catch (EntryManagerException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for createCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * cutoffTimeBean is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateCutoffTimeFailure4() throws Exception {
        CutoffTimeBean bean = new CutoffTimeBean();
        bean.setCompanyId(1);
        bean.setCreationUser("user");
        try {
            entryDelegate.createCutoffTime(bean, true);
            fail("EntryManagerException is expected");
        } catch (EntryManagerException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for deleteCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * cutoffTimeBean is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteCutoffTimeFailure1() throws Exception {
        try {
            entryDelegate.deleteCutoffTime(null, true);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for fetchCutoffTimeByCompanyID(long companyId).
     * </p>
     *
     * <p>
     * companyId is 0.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByCompanyIDFailure1() throws Exception {
        try {
            entryDelegate.fetchCutoffTimeByCompanyID(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for fetchCutoffTimeByCompanyID(long companyId).
     * </p>
     *
     * <p>
     * companyId is negative.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByCompanyIDFailure2() throws Exception {
        try {
            entryDelegate.fetchCutoffTimeByCompanyID(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for fetchCutoffTimeById(long cutoffTimeId).
     * </p>
     *
     * <p>
     * cutoffTimeId is 0.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByIdFailure1() throws Exception {
        try {
            entryDelegate.fetchCutoffTimeById(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for fetchCutoffTimeById(long cutoffTimeId).
     * </p>
     *
     * <p>
     * cutoffTimeId is negative.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFetchCutoffTimeByIdFailure2() throws Exception {
        try {
            entryDelegate.fetchCutoffTimeById(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for updateCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit).
     * </p>
     *
     * <p>
     * cutoffTimeBean is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateCutoffTimeFailure1() throws Exception {
        try {
            entryDelegate.updateCutoffTime(null, true);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}
