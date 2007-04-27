/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.stresstests;

import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingStatusDAO;

import junit.framework.TestCase;

/**
 * Stress tests for class <code>DbFixedBillingStatusDAO</code>.
 *
 * @author myxgyy
 * @version 1.0
 */
public class DbFixedBillingStatusDAOStressTests extends TestCase {
	/**
	 * The <code>DbFixedBillingStatusDAO</code> instance for testing.
	 */
    private DbFixedBillingStatusDAO statusDAO;

    /**
     * Represents the <code>FixedBillingStatus</code> instances used to insert.
     */
    private FixedBillingStatus[][] statusesToInsert;

    /**
     * Represents the <code>FixedBillingStatus</code> instances used to update and delete.
     */
    private FixedBillingStatus[] statusesToUpdateDelete;

    /**
     * Set up the test.
     *
     * @throws Exception to JUnit.
     */
    public void setUp() throws Exception {
    	StressTestHelper.clearConfig();
    	StressTestHelper.loadConfig();
    	StressTestHelper.tearDownDatabase();
    	StressTestHelper.setUpDatabase();

    	DBConnectionFactoryImpl connFactory =
    		new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

    	statusDAO = new DbFixedBillingStatusDAO(connFactory,
    			"informix",
    			"com.topcoder.timetracker.entry.fixedbilling",
    			"com.topcoder.search.builder");

    	statusesToInsert = new FixedBillingStatus[StressTestHelper.RUN_NUMBER][StressTestHelper.RECORD_NUMBER];
    	for (int i = 0; i < statusesToInsert.length; i++) {
    		for (int j = 0; j < statusesToInsert[i].length; j++) {
    			FixedBillingStatus status = new FixedBillingStatus();

    			status.setCreationDate(new Date());
    			status.setCreationUser("stresstester");
    			status.setDescription("statuses[" + j + "]");
    			status.setModificationDate(new Date());
    			status.setModificationUser("stresstester");

    			statusesToInsert[i][j] = status;
    		}
    	}

    	statusesToUpdateDelete = new FixedBillingStatus[StressTestHelper.RECORD_NUMBER];
    	for (int i = 0; i < statusesToUpdateDelete.length; i++) {
    		statusesToUpdateDelete[i] = new FixedBillingStatus();
    		statusesToUpdateDelete[i].setId(40001 + i);
    		statusesToUpdateDelete[i].setCreationDate(new Date());
    		statusesToUpdateDelete[i].setCreationUser("stresstester");
    		statusesToUpdateDelete[i].setDescription("statuses[" + i + "]");
    		statusesToUpdateDelete[i].setModificationDate(new Date());
    		statusesToUpdateDelete[i].setModificationUser("stresstester");
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
     * Stress test for the method {@link DbFixedBillingStatusDAO#createFixedBillingStatuses(
     *     FixedBillingStatus[])}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testCreateFixedBillingStatuses() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            statusDAO.createFixedBillingStatuses(statusesToInsert[i]);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingStatusDAO#createFixedBillingStatuses(FixedBillingStatus[])",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method {@link DbFixedBillingStatusDAO#updateFixedBillingStatuses(FixedBillingStatus[])}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testUpdateFixedBillingStatuses() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
        	FixedBillingStatus[] subStatuses =
        		new FixedBillingStatus[StressTestHelper.RECORD_NUMBER / StressTestHelper.RUN_NUMBER];
        	for (int j = 0; j < subStatuses.length; j++) {
        		subStatuses[j] = statusesToUpdateDelete[i * subStatuses.length + j];
            }
            statusDAO.updateFixedBillingStatuses(subStatuses);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingStatusDAO#updateFixedBillingStatuses(FixedBillingStatus[])",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method {@link DbFixedBillingStatusDAO#deleteFixedBillingStatuses(long[])}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testDeleteFixedBillingStatuses() throws Exception {
    	StressTestHelper.clearTable("fb_reject_reason");
    	StressTestHelper.clearTable("fix_bill_entry");

        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
        	long[] subStatuses =
        		new long[StressTestHelper.RECORD_NUMBER / StressTestHelper.RUN_NUMBER];
        	for (int j = 0; j < subStatuses.length; j++) {
        		subStatuses[j] = statusesToUpdateDelete[i * subStatuses.length + j].getId();
            }
            statusDAO.deleteFixedBillingStatuses(subStatuses);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingStatusDAO#deleteFixedBillingStatuses(long[])",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method {@link DbFixedBillingStatusDAO#getFixedBillingStatuses(long[])}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testGetFixedBillingStatuses() throws Exception {
    	StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
        	long[] subStatuses =
        		new long[StressTestHelper.RECORD_NUMBER / StressTestHelper.RUN_NUMBER];
        	for (int j = 0; j < subStatuses.length; j++) {
        		subStatuses[j] = statusesToUpdateDelete[i * subStatuses.length + j].getId();
            }
            assertEquals(statusDAO.getFixedBillingStatuses(subStatuses).length, subStatuses.length);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingStatusDAO#getFixedBillingStatuses(long[])",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method {@link DbFixedBillingStatusDAO#getAllFixedBillingStatuses()}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testGetAllFixedBillingStatuses() throws Exception {
    	StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            assertEquals(statusDAO.getAllFixedBillingStatuses().length, StressTestHelper.RECORD_NUMBER);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingStatusDAO#getAllFixedBillingStatuses()",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method {@link DbFixedBillingStatusDAO#searchFixedBillingStatuses(Filter)}.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testSearchFixedBillingStatuses() throws Exception {
    	StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            assertEquals(statusDAO.searchFixedBillingStatuses(
            		statusDAO.getFixedBillingStatusFilterFactory().createDescriptionFilter("statuses["
            				+ i +"]",
            				StringMatchType.EXACT_MATCH)).length, 1);
        }
        StressTestHelper.printResultMulTimes(
        		"DbFixedBillingStatusDAO#searchFixedBillingStatuses(Filter)",
                StressTestHelper.RUN_NUMBER);
    }
}