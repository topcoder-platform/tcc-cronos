/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.stresstests;

import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingEntryDAO;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingStatusDAO;

import junit.framework.TestCase;

/**
 * Stress tests for class <code>DbFixedBillingEntryDAO</code>.
 *
 * @author myxgyy
 * @version 1.0
 */
public class DbFixedBillingEntryDAOStressTests extends TestCase {
	/**
	 * The <code>DbFixedBillingEntryDAO</code> instance for testing.
	 */
    private DbFixedBillingEntryDAO dao;

    /**
	 * The <code>DbFixedBillingStatusDAO</code> instance for testing.
	 */
    private DbFixedBillingStatusDAO statusDAO;

    /**
     * Represents the <code>FixedBillingEntry</code> instances used to insert.
     */
    private FixedBillingEntry[][] entriesToInsert;

    /**
     * Represents the <code>FixedBillingEntry</code> instances used to insert.
     */
    private FixedBillingEntry[] entriesToUpdateDelete;

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

    	statusDAO = new DbFixedBillingStatusDAO(connFactory,
    			"informix",
    			"com.topcoder.timetracker.entry.fixedbilling",
    			"com.topcoder.search.builder");

    	dao = new DbFixedBillingEntryDAO(connFactory,
    			"informix",
    			"com.topcoder.timetracker.entry.fixedbilling",
    			"com.topcoder.search.builder",
    			new MockAuditManager(),
    			statusDAO);

    	FixedBillingStatus[] statuses = new FixedBillingStatus[StressTestHelper.RECORD_NUMBER];
    	for (int i = 0; i < statuses.length; i++) {
    		statuses[i] = new FixedBillingStatus();
    		statuses[i].setId(40001 + i);
    		statuses[i].setCreationDate(new Date());
    		statuses[i].setCreationUser("stresstester");
    		statuses[i].setDescription("statuses[" + i + "]");
    		statuses[i].setModificationDate(new Date());
    		statuses[i].setModificationUser("stresstester");
    	}

    	entriesToInsert = new FixedBillingEntry[StressTestHelper.RUN_NUMBER][StressTestHelper.RECORD_NUMBER];
    	for (int i = 0; i < entriesToInsert.length; i++) {
    		for (int j = 0; j < entriesToInsert[i].length; j++) {
    			entriesToInsert[i][j] = new FixedBillingEntry();
    			entriesToInsert[i][j].setAmount(20.0);
    			entriesToInsert[i][j].setFixedBillingStatus(statuses[j]);
    			entriesToInsert[i][j].setInvoiceId(41000);
    			entriesToInsert[i][j].setDescription("description[" + j + "]");
    			entriesToInsert[i][j].setDate(new Date());
    			entriesToInsert[i][j].setCreationDate(new Date());
    			entriesToInsert[i][j].setCreationUser("stresstester");
    			entriesToInsert[i][j].setModificationDate(new Date());
    			entriesToInsert[i][j].setModificationUser("stresstester");
    			entriesToInsert[i][j].setCompanyId(41000);
    		}
    	}

    	entriesToUpdateDelete = new FixedBillingEntry[StressTestHelper.RECORD_NUMBER];
    	for (int i = 0; i < entriesToUpdateDelete.length; i++) {
    		entriesToUpdateDelete[i] = new FixedBillingEntry();
    		entriesToUpdateDelete[i].setId(41000 + i);
    		entriesToUpdateDelete[i].setAmount(20.0);
    		entriesToUpdateDelete[i].setFixedBillingStatus(statuses[i]);
    		entriesToUpdateDelete[i].setInvoiceId(41000);
    		entriesToUpdateDelete[i].setDescription("description[" + i + "]");
    		entriesToUpdateDelete[i].setDate(new Date());
    		entriesToUpdateDelete[i].setCreationDate(new Date());
    		entriesToUpdateDelete[i].setCreationUser("stresstester");
    		entriesToUpdateDelete[i].setModificationDate(new Date());
    		entriesToUpdateDelete[i].setModificationUser("stresstester");
    		entriesToUpdateDelete[i].setCompanyId(41000);
    	}
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
     * Stress test for the method {@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[],
     * boolean)}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testCreateFixedBillingEntries() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
        	dao.createFixedBillingEntries(entriesToInsert[i], true);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[], boolean)",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method {@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[],
     * boolean)}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testUpdateFixedBillingEntries() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
        	FixedBillingEntry[] subEntries =
        		new FixedBillingEntry[StressTestHelper.RECORD_NUMBER / StressTestHelper.RUN_NUMBER];
        	for (int j = 0; j < subEntries.length; j++) {
        		subEntries[j] = entriesToUpdateDelete[i * subEntries.length + j];
            }
        	dao.updateFixedBillingEntries(subEntries, true);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[], boolean)",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method {@link DbFixedBillingEntryDAO#deleteFixedBillingEntries(long[], boolean)}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testDeleteFixedBillingEntries() throws Exception {
    	StressTestHelper.clearTable("fb_reject_reason");

    	StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
        	long[] subEntries =
        		new long[StressTestHelper.RECORD_NUMBER / StressTestHelper.RUN_NUMBER];
        	for (int j = 0; j < subEntries.length; j++) {
        		subEntries[j] = entriesToUpdateDelete[i * subEntries.length + j].getId();
            }
        	dao.deleteFixedBillingEntries(subEntries, true);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingEntryDAO#deleteFixedBillingEntries(long[], boolean)",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method {@link DbFixedBillingEntryDAO#getFixedBillingEntries(long[])}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testGetFixedBillingEntries() throws Exception {
    	StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
        	long[] subEntries =
        		new long[StressTestHelper.RECORD_NUMBER / StressTestHelper.RUN_NUMBER];
        	for (int j = 0; j < subEntries.length; j++) {
        		subEntries[j] = entriesToUpdateDelete[i * subEntries.length + j].getId();
            }
        	dao.getFixedBillingEntries(subEntries);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingEntryDAO#getFixedBillingEntries(long[])",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method {@link DbFixedBillingEntryDAO#getAllFixedBillingEntries()}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testGetAllFixedBillingEntries() throws Exception {
    	StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
        	assertEquals(dao.getAllFixedBillingEntries().length, 50);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingEntryDAO#getAllFixedBillingEntries()",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method {@link DbFixedBillingEntryDAO#searchFixedBillingEntries(Filter)}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testSearchFixedBillingEntries() throws Exception {
    	StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
        	assertEquals(dao.searchFixedBillingEntries(
            		dao.getFixedBillingEntryFilterFactory().createDescriptionFilter("description["
            				+ i + "]",
            				StringMatchType.EXACT_MATCH)).length, 1);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingEntryDAO#searchFixedBillingEntries(Filter)",
                StressTestHelper.RUN_NUMBER);
    }
}
