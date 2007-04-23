/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.accuracytests.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.servicedetail.BatchExecutionException;
import com.topcoder.timetracker.invoice.servicedetail.DataAccessException;
import com.topcoder.timetracker.invoice.servicedetail.EntityNotFoundException;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.accuracytests.TestHelper;
import com.topcoder.timetracker.invoice.servicedetail.dao.impl.ServiceDetailDAOImpl;

/**
 * Unit test for <code>ServiceDetailDAOImpl</code>.
 * @author KLW
 * @version 1.1
 */
public class AccracyTestServiceDetailDAOImpl extends TestCase {

    /** Unit under test use batch. */
    private ServiceDetailDAOImpl serviceDetailDAOImpl;

    /**
     * Sets the unit test up.
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfiguration(TestHelper.FILE_DAO_CONFIG);
    }

    /**
     * Tears the unit test down.
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.releaseConfiguration();
    }

    /**
     * Tests constructor with param namespace is null or empty.
     * @throws Exception
     *             to JUnit
     */
    public void testConstructro_01() throws Exception {
        try {
            this.serviceDetailDAOImpl = new ServiceDetailDAOImpl(null);
            fail("Should throw IAE.");
        } catch (IllegalArgumentException e) {
            // Succes.
        }
        try {
            this.serviceDetailDAOImpl = new ServiceDetailDAOImpl("   ");
            fail("Should throw IAE.");
        } catch (IllegalArgumentException e) {
            // Succes.
        }
    }

    /**
     * Tests constructor with param namespace is null or empty.
     * @throws Exception
     *             to JUnit
     */
    public void testConstructro_02() throws Exception {
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
    }

    /**
     * Tests retrieveServiceDetail.
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetail() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        InvoiceServiceDetail detail = this.serviceDetailDAOImpl.retrieveServiceDetail(5);
        assertEquals(-1, detail.getTimeEntry().getId());
        assertEquals(25, detail.getInvoice().getId());
        try {
            detail = this.serviceDetailDAOImpl.retrieveServiceDetail(-1);
            fail("Should throw EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // success.
        }
    }

    /**
     * Tests retrieveServiceDetails by invoice id.
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetails_invoice() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        InvoiceServiceDetail[] details;
        try {
            details = this.serviceDetailDAOImpl.retrieveServiceDetails(5);
            assertEquals(0, details.length);
        } catch (Exception e) {
            fail("Should not throw Exception");
        }

        details = this.serviceDetailDAOImpl.retrieveServiceDetails(42);
        assertEquals(2, details.length);
        assertEquals(1, details[0].getId());
        assertEquals(4, details[1].getId());
    }

    /**
     * Tests retrieveServiceDetails by ids.
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetails_ids_01() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        InvoiceServiceDetail[] details;
        long[] ids = new long[0];
        try {
            details = this.serviceDetailDAOImpl.retrieveServiceDetails(ids);
            assertEquals(0, details.length);
        } catch (Exception e) {
            fail("Should not throw Exception");
        }
    }

    /**
     * Tests retrieveServiceDetails by ids.
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetails_ids_02() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        InvoiceServiceDetail[] details;
        long[] ids = new long[] { 10 };
        try {
            details = this.serviceDetailDAOImpl.retrieveServiceDetails(ids);
            fail("Should throw EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // Success.
        }
    }

    /**
     * Tests retrieveServiceDetails by ids.
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetails_ids_03() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        InvoiceServiceDetail[] details;
        long[] ids = new long[] { 1, 10 };
        try {
            details = this.serviceDetailDAOImpl.retrieveServiceDetails(ids);
            fail("Should throw EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // Success.
        }
    }

    /**
     * Tests retrieveServiceDetails by ids.
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetails_ids_04() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        InvoiceServiceDetail[] details = null;
        long[] ids = new long[] { 1, 2 };
        try {
            details = this.serviceDetailDAOImpl.retrieveServiceDetails(ids);
        } catch (Exception e) {
            fail("Should not throw Exception.");
        }
        assertEquals(2, details.length);
        assertEquals(1, details[0].getId());
        assertEquals(2, details[1].getId());
    }

    /**
     * Tests addServiceDetail.
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetail_01() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        Invoice invoice = new Invoice();
        invoice.setId(76);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        serviceDetailDAOImpl.addServiceDetail(detail, true);
        DBConnectionFactory connFact = TestHelper.getDBConnectionFactory(TestHelper.TEST_CONN_NAMESPACE);
        Connection conn = connFact.createConnection();
        Statement statement = conn.createStatement();
        ResultSet set = statement
                .executeQuery("select * from service_details where invoice_id = 76 and time_entry_id = 60");

        if (set.next()) {
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 1000, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }
    }

    /**
     * Tests addServiceDetail.
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetail_02() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        Invoice invoice = new Invoice();
        invoice.setId(76);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        serviceDetailDAOImpl.addServiceDetail(detail, false);

        DBConnectionFactory connFact = TestHelper.getDBConnectionFactory(TestHelper.TEST_CONN_NAMESPACE);
        Connection conn = connFact.createConnection();
        Statement statement = conn.createStatement();
        ResultSet set = statement
                .executeQuery("select * from service_details where invoice_id = 76 and time_entry_id = 60");

        if (set.next()) {
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 1000, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }
    }

    /**
     * Tests addServiceDetail.
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetail_03() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();

        InvoiceServiceDetail detail = this.serviceDetailDAOImpl.retrieveServiceDetail(1);

        try {
            serviceDetailDAOImpl.addServiceDetail(detail, false);
            fail("Should throw DataAccessException");
        } catch (DataAccessException e) {
            // Success.
        }

    }

    /**
     * Tests addServiceDetails.
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetails_01() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        Invoice invoice = new Invoice();
        invoice.setId(76);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        InvoiceServiceDetail[] details = new InvoiceServiceDetail[1];
        details[0] = detail;
        serviceDetailDAOImpl.addServiceDetails(details, false);

        DBConnectionFactory connFact = TestHelper.getDBConnectionFactory(TestHelper.TEST_CONN_NAMESPACE);
        Connection conn = connFact.createConnection();
        Statement statement = conn.createStatement();
        ResultSet set = statement
                .executeQuery("select * from service_details where invoice_id = 76 and time_entry_id = 60");

        if (set.next()) {
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 1000, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }
    }

    /**
     * Tests addServiceDetails.
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetails_02() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        Invoice invoice = new Invoice();
        invoice.setId(76);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        InvoiceServiceDetail[] details = new InvoiceServiceDetail[2];
        details[0] = detail;
        details[1] = detail;
        try {
            serviceDetailDAOImpl.addServiceDetails(details, false);
            // Succes
        } catch (DataAccessException e) {
            fail("Should not throw DataAccessException.");
        }

        DBConnectionFactory connFact = TestHelper.getDBConnectionFactory(TestHelper.TEST_CONN_NAMESPACE);
        Connection conn = connFact.createConnection();
        Statement statement = conn.createStatement();
        ResultSet set = statement
                .executeQuery("select * from service_details where invoice_id = 76 and time_entry_id = 60");
        details = this.serviceDetailDAOImpl.retrieveServiceDetails(76);
        assertEquals(2, details.length);
        assertEquals("testuser", details[0].getModificationUser());

        if (set.next()) {
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 1000, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }
    }

    /**
     * Test <code>deleteServiceDetail</code> .
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetail_01() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        try {
            serviceDetailDAOImpl.deleteServiceDetail(78, true);
            fail("Should throw EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // Success.
        }
    }

    /**
     * Test <code>deleteServiceDetail</code> .
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetail_02() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        try {
            serviceDetailDAOImpl.deleteServiceDetail(1, true);
            // Success.
        } catch (Exception e) {
            fail("Should not throw Exception.");
        }

        try {
            this.serviceDetailDAOImpl.retrieveServiceDetail(1);
            fail("Should throw EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // Success.
        }
    }

    /**
     * Test <code>deleteAllServiceDetail</code> .
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteAllServiceDetail() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        try {
            serviceDetailDAOImpl.deleteAllServiceDetails(true);
        } catch (EntityNotFoundException e) {
            fail("Should not throw Exception.");
        }

        InvoiceServiceDetail[] details = this.serviceDetailDAOImpl.retrieveAllServiceDetails();
        assertEquals("There should be no record in DB.", 0, details.length);
    }

    /**
     * Test <code>deleteServiceDetails</code> .
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetails_01() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        InvoiceServiceDetail[] details = null;
        long[] ids = new long[] { 1, 2 };
        try {
            this.serviceDetailDAOImpl.deleteServiceDetails(ids, true);
        } catch (Exception e) {
            fail("Should not throw Exception.");
        }
        try {
            details = this.serviceDetailDAOImpl.retrieveServiceDetails(ids);
            fail("Should throw EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // Success.
        }
    }

    /**
     * Test <code>deleteServiceDetails</code> .
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetails_02() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        InvoiceServiceDetail[] details = null;
        long[] ids = new long[] { 1, 10 };
        try {
            this.serviceDetailDAOImpl.deleteServiceDetails(ids, true);
            fail("Should throw BatchExecutionException.");
        } catch (BatchExecutionException e) {
            // Success.
            assertEquals("Should throw the currect ids.", (e.getIds())[0], 10);
        }
        try {
            InvoiceServiceDetail detail = this.serviceDetailDAOImpl.retrieveServiceDetail(1);
            fail("Should throw EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // Success.
        }
    }

    /**
     * Test <code>deleteServiceDetails</code> .
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetails_03() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl(TestHelper.TEST_DAO_NAMESPACE + ".notusebatch");
        InvoiceServiceDetail[] details = null;
        long[] ids = new long[] { 1, 10 };
        try {
            this.serviceDetailDAOImpl.deleteServiceDetails(ids, false);
            fail("Should throw EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // Success.
            assertEquals(10, e.getId());
        }
        try {
            InvoiceServiceDetail detail = this.serviceDetailDAOImpl.retrieveServiceDetail(1);
            fail("Should throw EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // Success.
        }
    }

    /**
     * Test <code>UpdateServiceDetail</code> .
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetail_01() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        InvoiceServiceDetail detail = this.serviceDetailDAOImpl.retrieveServiceDetail(1);
        detail.setModificationUser("accuracy");
        this.serviceDetailDAOImpl.updateServiceDetail(detail, true);
        detail = this.serviceDetailDAOImpl.retrieveServiceDetail(1);
        assertEquals("accuracy", detail.getModificationUser());
    }

    /**
     * Test <code>UpdateServiceDetail</code> .
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetail_02() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        InvoiceServiceDetail detail = this.serviceDetailDAOImpl.retrieveServiceDetail(1);
        detail.setId(20);
        detail.setModificationUser("accuracy");
        try {
            this.serviceDetailDAOImpl.updateServiceDetail(detail, true);
            fail("Should throw DataAccessException");
        } catch (DataAccessException e) {
            // success.
        }
    }

    /**
     * Test <code>UpdateServiceDetails</code> .
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetails_01() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        InvoiceServiceDetail[] details = this.serviceDetailDAOImpl.retrieveAllServiceDetails();
        this.serviceDetailDAOImpl.updateServiceDetails(details, true);
        details = this.serviceDetailDAOImpl.retrieveAllServiceDetails();
        assertEquals(5, details.length);
    }

    /**
     * Test <code>UpdateServiceDetails</code> .
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetails_02() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        InvoiceServiceDetail[] details = this.serviceDetailDAOImpl.retrieveServiceDetails(new long[] { 1, 2 });
        details[1].setId(10);
        try {
            this.serviceDetailDAOImpl.updateServiceDetails(details, true);
            fail("Should throw BatchExecutionException");
        } catch (BatchExecutionException e) {
            assertEquals(10, e.getIds()[0]);
        }
    }

    /**
     * Test <code>UpdateServiceDetails</code> .
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetails_03() throws Exception {
        TestHelper.setUpDatabase();
        this.serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        InvoiceServiceDetail[] details = new InvoiceServiceDetail[0];
        try {
            this.serviceDetailDAOImpl.updateServiceDetails(details, true);
        } catch (Exception e) {
            fail("Should not throw Exception");
        }
    }
}
