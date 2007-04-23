/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.stresstests;

import java.math.BigDecimal;
import java.util.Date;

import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.dao.ServiceDetailDAO;
import com.topcoder.timetracker.invoice.servicedetail.dao.impl.ServiceDetailDAOImpl;

import junit.framework.TestCase;

/**
 * Stress tests for <code>ServiceDetailDAOImpl</code> class.
 *
 * @author vividmxx
 * @version 3.2
 */
public class ServiceDetailDAOStressTests extends TestCase {

    /**
     * Represents the <code>ServiceDetailDAOImpl</code> instance used to test against.
     */
    private ServiceDetailDAO dao;

    /**
     * Represents the <code>InvoiceServiceDetail</code> instances used to insert.
     */
    private InvoiceServiceDetail[] detailsToInsert;

    /**
     * Represents the <code>InvoiceServiceDetail</code> instances used to update and delete.
     */
    private InvoiceServiceDetail[] detailsToUpdateDelete;

    /**
     * Sets up the test environment.
     *
     * @throws Exception
     *             throws to JUnit
     */
    protected void setUp() throws Exception {
        StressTestHelper.loadConfig();
        StressTestHelper.setUpDatabase();

        dao = new ServiceDetailDAOImpl("com.topcoder.timetracker.invoice.servicedetail.stresstests");

        detailsToInsert = new InvoiceServiceDetail[StressTestHelper.RECORD_NUMBER];
        for (int i = 0; i < detailsToInsert.length; i++) {
            detailsToInsert[i] = new InvoiceServiceDetail();
            detailsToInsert[i].setAmount(new BigDecimal(100));
            detailsToInsert[i].setRate(1);
            Invoice invoice = new Invoice();
            invoice.setId(41005 + i / 20);
            invoice.setProjectId(41005 + i / 20);
            detailsToInsert[i].setInvoice(invoice);
            TimeEntry time = new TimeEntry();
            time.setId(41100 + i);
            time.setCreationUser("Tester" + (41100 + i));
            detailsToInsert[i].setTimeEntry(time);
            detailsToInsert[i].setCreationDate(new Date());
            detailsToInsert[i].setCreationUser("Tester");
            detailsToInsert[i].setModificationDate(new Date());
            detailsToInsert[i].setModificationUser("Tester");
        }

        detailsToUpdateDelete = new InvoiceServiceDetail[StressTestHelper.RECORD_NUMBER];
        for (int i = 0; i < detailsToInsert.length; i++) {
            detailsToUpdateDelete[i] = new InvoiceServiceDetail();
            detailsToUpdateDelete[i].setId(41000 + i);
            detailsToUpdateDelete[i].setAmount(new BigDecimal(100));
            detailsToUpdateDelete[i].setRate(1);
            Invoice invoice = new Invoice();
            invoice.setId(41000 + i / 20);
            invoice.setProjectId(41000 + i / 20);
            detailsToUpdateDelete[i].setInvoice(invoice);
            TimeEntry time = new TimeEntry();
            time.setId(41000 + i);
            time.setCreationUser("Tester" + (41000 + i));
            detailsToUpdateDelete[i].setTimeEntry(time);
            detailsToUpdateDelete[i].setCreationDate(new Date());
            detailsToUpdateDelete[i].setCreationUser("Tester");
            detailsToUpdateDelete[i].setModificationDate(new Date());
            detailsToUpdateDelete[i].setModificationUser("Tester");
        }
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception
     *             throws to JUnit
     */
    protected void tearDown() throws Exception {
        StressTestHelper.tearDownDatabase();
        StressTestHelper.clearConfig();
    }

    /**
     * Stress test for the method <code>addServiceDetail(InvoiceServiceDetail, boolean).</code>.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testAddServiceDetail() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            dao.addServiceDetail(detailsToInsert[i], true);
        }
        StressTestHelper.printResultMulTimes("addServiceDetail(InvoiceServiceDetail, boolean)",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>deleteServiceDetail(long, boolean).</code>.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testDeleteServiceDetail() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            dao.deleteServiceDetail(detailsToUpdateDelete[i].getId(), true);
        }
        StressTestHelper.printResultMulTimes("deleteServiceDetail(long, boolean)",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>deleteAllServiceDetails(boolean).</code>.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testDeleteAllServiceDetails() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            dao.deleteAllServiceDetails(true);
        }
        StressTestHelper.printResultMulTimes("deleteAllServiceDetails(boolean)", StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>updateServiceDetail(InvoiceServiceDetail, boolean).</code>.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testUpdateServiceDetail() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            dao.updateServiceDetail(detailsToUpdateDelete[i], true);
        }
        StressTestHelper.printResultMulTimes("updateServiceDetail(InvoiceServiceDetail, boolean)",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>retrieveServiceDetail(long).</code>.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testRetrieveServiceDetail() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            assertNotNull("retrieve failed.", dao.retrieveServiceDetail(41000 + i));
        }
        StressTestHelper.printResultMulTimes("retrieveServiceDetail(long)", StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>retrieveServiceDetails(long).</code>.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testRetrieveServiceDetails() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            if (i < 5) {
                assertTrue("retrieve failed.", dao.retrieveServiceDetails(41000 + i).length == 20);
            } else {
                assertTrue("retrieve failed.", dao.retrieveServiceDetails(41000 + i).length == 0);
            }
        }
        StressTestHelper.printResultMulTimes("retrieveServiceDetails(long)", StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>retrieveAllServiceDetails().</code>.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testRetrieveAllServiceDetails() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            assertEquals("retrieve failed.", dao.retrieveAllServiceDetails().length, 100);
        }
        StressTestHelper.printResultMulTimes("retrieveAllServiceDetails()", StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>addServiceDetails(InvoiceServiceDetail[], boolean).</code>.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testAddServiceDetails() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            InvoiceServiceDetail[] subDetails
                = new InvoiceServiceDetail[StressTestHelper.RECORD_NUMBER / StressTestHelper.RUN_NUMBER];
            for (int j = 0; j < subDetails.length; j++) {
                subDetails[j] = detailsToInsert[i * subDetails.length + j];
            }
            dao.addServiceDetails(subDetails, true);
        }
        StressTestHelper.printResultMulTimes("addServiceDetails(InvoiceServiceDetail[], boolean)",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>deleteServiceDetails(long[], boolean).</code>.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testDeleteServiceDetails() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            long[] subDetails = new long[StressTestHelper.RECORD_NUMBER / StressTestHelper.RUN_NUMBER];
            for (int j = 0; j < subDetails.length; j++) {
                subDetails[j] = detailsToUpdateDelete[i * subDetails.length + j].getId();
            }
            dao.deleteServiceDetails(subDetails, true);
        }
        StressTestHelper.printResultMulTimes("deleteServiceDetails(long[], boolean)", StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>updateServiceDetails(InvoiceServiceDetail[], boolean).</code>.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testUpdateServiceDetails() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            InvoiceServiceDetail[] subDetails
                = new InvoiceServiceDetail[StressTestHelper.RECORD_NUMBER / StressTestHelper.RUN_NUMBER];
            for (int j = 0; j < subDetails.length; j++) {
                subDetails[j] = detailsToUpdateDelete[i * subDetails.length + j];
            }
            dao.updateServiceDetails(subDetails, true);
        }
        StressTestHelper.printResultMulTimes("updateServiceDetails(InvoiceServiceDetail[], boolean)",
                StressTestHelper.RUN_NUMBER);
    }

    /**
     * Stress test for the method <code>retrieveServiceDetails(long[]).</code>.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public void testRetrieveServiceDetailsBatch() throws Exception {
        StressTestHelper.startTimer();
        for (int i = 0; i < StressTestHelper.RUN_NUMBER; i++) {
            long[] subDetails = new long[StressTestHelper.RECORD_NUMBER / StressTestHelper.RUN_NUMBER];
            for (int j = 0; j < subDetails.length; j++) {
                subDetails[j] = detailsToUpdateDelete[i * subDetails.length + j].getId();
            }
            assertTrue("retrieve failed.", dao.retrieveServiceDetails(subDetails).length == subDetails.length);
        }
        StressTestHelper.printResultMulTimes("retrieveServiceDetails(long[])", StressTestHelper.RUN_NUMBER);
    }
}
