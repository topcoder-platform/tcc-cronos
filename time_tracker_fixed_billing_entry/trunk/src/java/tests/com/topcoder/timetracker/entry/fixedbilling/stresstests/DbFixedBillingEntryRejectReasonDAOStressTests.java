/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.stresstests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingEntryRejectReasonDAO;

/**
 * Stress tests for class <code>DbFixedBillingEntryRejectReasonDAO</code>.
 *
 * @author myxgyy
 * @version 1.0
 */
public class DbFixedBillingEntryRejectReasonDAOStressTests extends TestCase {
	/**
	 * The <code>DbFixedBillingEntryRejectReasonDAO</code> instance for testing.
	 */
    private DbFixedBillingEntryRejectReasonDAO dao;

    /**
     * Represents the <code>FixedBillingEntry</code> instances used for testing.
     */
    private FixedBillingEntry entry;

    /**
     * Set up the test.
     *
     * @throws Exception to JUnit.
     */
    public void setUp() throws Exception {
    	StressTestHelper.loadConfig();
    	StressTestHelper.tearDownDatabase();
    	StressTestHelper.setUpDatabase();

    	DBConnectionFactoryImpl connFactory =
    		new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

    	dao = new DbFixedBillingEntryRejectReasonDAO(connFactory,
    			"informix", new MockAuditManager());

    	FixedBillingStatus status = new FixedBillingStatus();
    	status = new FixedBillingStatus();
    	status.setId(40001);
    	status.setCreationDate(new Date());
    	status.setCreationUser("stresstester");
    	status.setDescription("statuses[40001");
    	status.setModificationDate(new Date());
    	status.setModificationUser("stresstester");

    	entry = new FixedBillingEntry();
    	entry.setId(41001);
    	entry.setAmount(20.0);
    	entry.setFixedBillingStatus(status);
    	entry.setInvoiceId(41000);
    	entry.setDescription("description[41001]");
    	entry.setDate(new Date());
    	entry.setCreationDate(new Date());
    	entry.setCreationUser("stresstester");
    	entry.setModificationDate(new Date());
    	entry.setModificationUser("stresstester");
    	entry.setCompanyId(41000);
    }

    /**
     * Clear up the test.
     *
     * @throws Exception to junit.
     */
    public void tearDown() throws Exception {
    	StressTestHelper.tearDownDatabase();
    	StressTestHelper.clearConfig();
    }

    /**
     * Stress test for the method {@link DbFixedBillingEntryRejectReasonDAO#addRejectReasonToEntry(long,
     * FixedBillingEntry, boolean)}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testAddRejectReasonToEntry() throws Exception {
    	dao.removeAllRejectReasonsFromEntry(entry, false);

        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
        	dao.addRejectReasonToEntry(i + 1, entry, true);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingEntryRejectReasonDAO#addRejectReasonToEntry(long, FixedBillingEntry, boolean)",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method {@link DbFixedBillingEntryRejectReasonDAO#getAllRejectReasonsForEntry(long)}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testGetAllRejectReasonsForEntry() throws Exception {
    	StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
        	assertEquals(dao.getAllRejectReasonsForEntry(41001).length, 50);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingEntryRejectReasonDAO#getAllRejectReasonsForEntry(long)",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method {@link
     * DbFixedBillingEntryRejectReasonDAO#removeAllRejectReasonsFromEntry(FixedBillingEntry, boolean)}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testRemoveAllRejectReasonsFromEntry() throws Exception {
    	StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
        	dao.removeAllRejectReasonsFromEntry(entry, true);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingEntryRejectReasonDAO#removeAllRejectReasonsFromEntry(FixedBillingEntry,"
        		+ " boolean)",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method {@link
     * DbFixedBillingEntryRejectReasonDAO#removeRejectReasonFromEntry(long, FixedBillingEntry, boolean)}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testRemoveRejectReasonFromEntry() throws Exception {
    	StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
        	dao.removeRejectReasonFromEntry(i + 1, entry, true);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingEntryRejectReasonDAO#removeRejectReasonFromEntry(long, FixedBillingEntry,"
        		+ " boolean)",
                StressTestHelper.RUN_NUMBER);
    }
}
