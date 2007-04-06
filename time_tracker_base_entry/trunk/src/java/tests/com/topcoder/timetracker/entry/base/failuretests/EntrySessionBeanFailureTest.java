/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.failuretests;

import java.util.Date;

import javax.ejb.SessionContext;

import com.topcoder.timetracker.entry.base.BaseEntry;
import com.topcoder.timetracker.entry.base.CutoffTimeBean;
import com.topcoder.timetracker.entry.base.EntryManagerException;
import com.topcoder.timetracker.entry.base.ejb.EntrySessionBean;

import junit.framework.TestCase;

/**
 * <p>
 * The failure test for <code>EntrySessionBean</code>.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class EntrySessionBeanFailureTest extends TestCase {

    /**
     * Represents SessionContext instance used in the failure test.
     */
    private SessionContext sessionContext;

    /**
     * Represents EntrySessionBean instance used in the failure test.
     */
    private EntrySessionBean entrySessionBean;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.clearConfig();
        FailureTestHelper.loadConfig("DBConnectionFactory.xml");
        FailureTestHelper.loadConfig("EntrySessionBeanTest.xml");
        FailureTestHelper.backupConfig("EntrySessionBeanTest.xml");
        FailureTestHelper.initDB();

        sessionContext = new MockSessionContext();
        entrySessionBean = new EntrySessionBean();
        entrySessionBean.setSessionContext(sessionContext);
        entrySessionBean.ejbCreate();
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.restoreConfig("EntrySessionBeanTest.xml");
        FailureTestHelper.clearConfig();
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
            entrySessionBean.canSubmitEntry(null);
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
            entrySessionBean.canSubmitEntry(new BaseEntry() {
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
            entrySessionBean.createCutoffTime(null, true);
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
        // use bean with a companyid not existing in persistence
        CutoffTimeBean cutoffTimeBean = new CutoffTimeBean();
        cutoffTimeBean.setCompanyId(1000);
        cutoffTimeBean.setCreationUser("user");
        cutoffTimeBean.setCutoffTime(new Date());
        try {
            entrySessionBean.createCutoffTime(cutoffTimeBean, true);
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
        // use bean with a companyid not existing in persistence
        CutoffTimeBean cutoffTimeBean = new CutoffTimeBean();
        cutoffTimeBean.setCompanyId(1);
        cutoffTimeBean.setCutoffTime(new Date());
        try {
            entrySessionBean.createCutoffTime(cutoffTimeBean, true);
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
        // use bean with a companyid not existing in persistence
        CutoffTimeBean cutoffTimeBean = new CutoffTimeBean();
        cutoffTimeBean.setCompanyId(1);
        cutoffTimeBean.setCreationUser("user");
        try {
            entrySessionBean.createCutoffTime(cutoffTimeBean, true);
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
            entrySessionBean.deleteCutoffTime(null, true);
            fail("IllegalArgumentException is expected.");
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
            entrySessionBean.updateCutoffTime(null, true);
            fail("IllegalArgumentException is expected.");
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
            entrySessionBean.fetchCutoffTimeByCompanyID(0);
            fail("IllegalArgumentException is expected.");
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
            entrySessionBean.fetchCutoffTimeByCompanyID(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}
