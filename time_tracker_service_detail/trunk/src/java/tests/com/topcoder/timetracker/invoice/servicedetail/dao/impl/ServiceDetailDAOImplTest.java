/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.dao.impl;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.servicedetail.BatchExecutionException;
import com.topcoder.timetracker.invoice.servicedetail.ConfigurationException;
import com.topcoder.timetracker.invoice.servicedetail.DataAccessException;
import com.topcoder.timetracker.invoice.servicedetail.EntityNotFoundException;
import com.topcoder.timetracker.invoice.servicedetail.InvalidDataException;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.TestHelper;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Unit test for <code>ServiceDetailDAOImpl</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class ServiceDetailDAOImplTest extends DBTestCase {

    /** Unit under test use batch. */
    private ServiceDetailDAOImpl serviceDetailDAOImpl;

    /** Unit under test not use batch. */
    private ServiceDetailDAOImpl serviceDetailDAOImpl2;

    /** Unit under test database not exist. */
    private ServiceDetailDAOImpl serviceDetailDAOImplNotExist;

    /** JDBC connection used in the unit test. */
    private Connection jdbcConnection;

    /**
     * Constructor of the unit test.
     *
     * @param name
     *            test name
     * @throws Exception
     *             to JUnit
     */
    public ServiceDetailDAOImplTest(String name) throws Exception {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.informix.jdbc.IfxDriver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
            "jdbc:informix-sqli://192.168.1.101:1526/service_details:INFORMIXSERVER=topcoder");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "informix");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "123456");

        // database connection
        Class.forName("com.informix.jdbc.IfxDriver");

    }

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(ServiceDetailDAOImplTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestHelper.loadConfiguration("service_detail_dao_accuracy.xml");
        TestHelper.loadConfiguration("service_detail_dao_accuracy2.xml");
        TestHelper.loadConfiguration("service_detail_dao_database_not_exist.xml");

        serviceDetailDAOImpl = new ServiceDetailDAOImpl();
        serviceDetailDAOImpl2 =
            new ServiceDetailDAOImpl("com.topcoder.timetracker.invoice.servicedetail.dao.impl2");
        serviceDetailDAOImplNotExist = new ServiceDetailDAOImpl("notExistNS");

        jdbcConnection =
            DriverManager.getConnection(
                "jdbc:informix-sqli://192.168.1.101:1526/service_details:INFORMIXSERVER=topcoder", "informix",
                "123456");
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearNamespaces();

        jdbcConnection.close();

        super.tearDown();
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: the object is created successfully.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailDAOImplAccuracy() throws Exception {
        assertNotNull("The object can't be created successfully", new ServiceDetailDAOImpl());
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: the object is created successfully.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailDAOImplWithNamespaceAccuracy1() throws Exception {
        assertNotNull("The object can't be created successfully", new ServiceDetailDAOImpl(
            "com.topcoder.timetracker.invoice.servicedetail.dao.impl"));
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: the object is created successfully.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailDAOImplWithNamespaceAccuracy2() throws Exception {
        assertNotNull("The object can't be created successfully", new ServiceDetailDAOImpl(
            "com.topcoder.timetracker.invoice.servicedetail.dao.impl2"));
    }

    /**
     * Test constructor for failure. Condition: namespace is null. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailDAOImplWithNamespaceNull() throws Exception {
        try {
            new ServiceDetailDAOImpl(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: namespace is empty string. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailDAOImplWithNamespaceEmptyString() throws Exception {
        try {
            new ServiceDetailDAOImpl("   \n");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: namespace is unknown. Expect: <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailDAOImplWithNamespaceUnknown() throws Exception {
        try {
            new ServiceDetailDAOImpl("unknown");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: use_batch is not valid. Expect: <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailDAOImplWithNamespaceNotValidUseBatch() throws Exception {
        TestHelper.loadConfiguration("service_detail_dao_error_use_batch.xml");

        try {
            new ServiceDetailDAOImpl("service_detail_dao_error_use_batch");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: no connection_namespace. Expect:
     * <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailDAOImplWithNamespaceNoConnectionNamespace() throws Exception {
        TestHelper.loadConfiguration("service_detail_dao_no_connection_namespace.xml");

        try {
            new ServiceDetailDAOImpl("service_detail_dao_no_connection_namespace");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test <code>addServiceDetail</code> for accuracy. Condition: normal. Expect: the detail is inserted to the
     * database.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailAccuracy1() throws Exception {
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

        Statement statement = jdbcConnection.createStatement();
        ResultSet set =
            statement.executeQuery("select * from service_details where invoice_id = 76 and time_entry_id = 60");

        if (set.next()) {
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 1000, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }
    }

    /**
     * Test <code>addServiceDetail</code> for accuracy. Condition: normal. Expect: the detail is inserted to the
     * database.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailAccuracy2() throws Exception {
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

        Statement statement = jdbcConnection.createStatement();
        ResultSet set =
            statement.executeQuery("select * from service_details where invoice_id = 76 and time_entry_id = 60");

        if (set.next()) {
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 1000, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }
    }

    /**
     * Test <code>addServiceDetail</code> for failure. Condition: detail is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailNull() throws Exception {
        try {
            serviceDetailDAOImpl.addServiceDetail(null, false);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addServiceDetail</code> for failure. Condition: no invoice. Expect:
     * <code>InvalidDataException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailNoInvoice() throws Exception {
        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        Invoice invoice = new Invoice();
        invoice.setId(76);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        try {
            serviceDetailDAOImpl.addServiceDetail(detail, true);
            fail("InvalidDataException is expected");
        } catch (InvalidDataException e) {
            // expected
        }
    }

    /**
     * Test <code>addServiceDetail</code> for failure. Condition: no time entry. Expect:
     * <code>InvalidDataException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailNoTimeEntry() throws Exception {
        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        Invoice invoice = new Invoice();
        invoice.setId(76);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        try {
            serviceDetailDAOImpl.addServiceDetail(detail, true);
            fail("InvalidDataException is expected");
        } catch (InvalidDataException e) {
            // expected
        }
    }

    /**
     * Test <code>addServiceDetail</code> for failure. Condition: no modification user. Expect:
     * <code>InvalidDataException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailNoModificationUser() throws Exception {
        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        Invoice invoice = new Invoice();
        invoice.setId(76);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setCreationUser("testuser");

        try {
            serviceDetailDAOImpl.addServiceDetail(detail, true);
            fail("InvalidDataException is expected");
        } catch (InvalidDataException e) {
            // expected
        }
    }

    /**
     * Test <code>addServiceDetail</code> for failure. Condition: no creation user. Expect:
     * <code>InvalidDataException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailNoCreationUser() throws Exception {
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

        try {
            serviceDetailDAOImpl.addServiceDetail(detail, true);
            fail("InvalidDataException is expected");
        } catch (InvalidDataException e) {
            // expected
        }
    }

    /**
     * Test <code>addServiceDetail</code> for failure. Condition: time entry creation user is not in the
     * database. Expect: <code>InvalidDataException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailNoCreationUserInDB() throws Exception {
        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        Invoice invoice = new Invoice();
        invoice.setId(76);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser4");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        try {
            serviceDetailDAOImpl.addServiceDetail(detail, false);
            fail("InvalidDataException is expected");
        } catch (InvalidDataException e) {
            // expected
        }
    }

    /**
     * Test <code>addServiceDetail</code> for failure. Condition: database is not exist. Expect:
     * <code>DataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailNotExist() throws Exception {
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

        try {
            serviceDetailDAOImplNotExist.addServiceDetail(detail, false);
            fail("DataAccessException is expected");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>deleteServiceDetail</code> for accuracy. Condition: normal. Expect: the deleted record is not
     * in the database anymore.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetailAccuracy1() throws Exception {
        serviceDetailDAOImpl.deleteServiceDetail(78, false);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id = 788 ");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }
    }

    /**
     * Test <code>deleteServiceDetail</code> for accuracy. Condition: normal. Expect: the deleted record is not
     * in the database anymore.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetailAccuracy2() throws Exception {
        serviceDetailDAOImpl.deleteServiceDetail(78, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id = 788");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }
    }

    /**
     * Test <code>deleteServiceDetail</code> for failure. Condition: database is not exist. Expect:
     * <code>DataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetailNotExist() throws Exception {
        try {
            serviceDetailDAOImplNotExist.deleteServiceDetail(78, true);
            fail("Should throw DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>deleteServiceDetail</code> for failure. Condition: the record is not in the table. Expect:
     * <code>EntityNotFoundException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetailNotFound() throws Exception {
        try {
            serviceDetailDAOImpl.deleteServiceDetail(1001, true);
            fail("Should throw EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * Test <code>deleteAllServiceDetails</code> for accuracy. Condition: normal. Expect: there is no record in
     * the database.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteAllServiceDetailsAccuracy1() throws Exception {
        serviceDetailDAOImpl.deleteAllServiceDetails(true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }
    }

    /**
     * Test <code>deleteAllServiceDetails</code> for accuracy. Condition: normal. Expect: there is no record in
     * the database.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteAllServiceDetailsAccuracy2() throws Exception {
        serviceDetailDAOImpl.deleteAllServiceDetails(false);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }
    }

    /**
     * Test <code>deleteAllServiceDetails</code> for failure. Condition: database is not exist. Expect:
     * <code>DataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteAllServiceDetailsNotExist() throws Exception {

        try {
            serviceDetailDAOImplNotExist.deleteAllServiceDetails(false);
            fail("Should throw DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>updateServiceDetail</code> for accuracy. Condition: normal. Expect: the record is updated
     * correctly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailAccuracy1() throws Exception {

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setId(100);
        detail.setAmount(new BigDecimal(100));
        detail.setRate(100);
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

        serviceDetailDAOImpl.updateServiceDetail(detail, false);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id=100");

        if (set.next()) {
            assertEquals("The timeEntryId is not inserted correctly", 60, set.getLong("time_entry_id"));
            assertEquals("The invoiceId is not inserted correctly", 76, set.getLong("invoice_id"));
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 100, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }
    }

    /**
     * Test <code>updateServiceDetail</code> for accuracy. Condition: normal. Expect: the record is updated
     * correctly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailAccuracy2() throws Exception {

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setId(100);
        detail.setAmount(new BigDecimal(100));
        detail.setRate(100);
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

        serviceDetailDAOImpl.updateServiceDetail(detail, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id=100");

        if (set.next()) {
            assertEquals("The timeEntryId is not inserted correctly", 60, set.getLong("time_entry_id"));
            assertEquals("The invoiceId is not inserted correctly", 76, set.getLong("invoice_id"));
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 100, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }
    }

    /**
     * Test <code>updateServiceDetail</code> for accuracy. Condition: normal. Expect: the record is updated
     * correctly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailAccuracy3() throws Exception {

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setId(100);
        detail.setAmount(new BigDecimal(100));
        detail.setRate(100);
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
        detail.setChanged(true);

        serviceDetailDAOImpl.updateServiceDetail(detail, false);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id=100");

        if (set.next()) {
            assertEquals("The timeEntryId is not inserted correctly", 60, set.getLong("time_entry_id"));
            assertEquals("The invoiceId is not inserted correctly", 76, set.getLong("invoice_id"));
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 1000, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }
    }

    /**
     * Test <code>updateServiceDetail</code> for failure. Condition: detail is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailNull() throws Exception {
        try {
            serviceDetailDAOImpl.updateServiceDetail(null, false);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateServiceDetail</code> for failure. Condition: the updated record is not in the database.
     * Expect: <code>InvalidDataException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailNoRecord() throws Exception {

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setId(102);
        detail.setAmount(new BigDecimal(100));
        detail.setRate(100);
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

        try {
            serviceDetailDAOImpl.updateServiceDetail(detail, true);
            fail("expected InvalidDataException");
        } catch (InvalidDataException e) {
            // expected
        }
    }

    /**
     * Test <code>updateServiceDetail</code> for failure. Condition: the database is not exist. Expect:
     * <code>DataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailNotExist() throws Exception {

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setId(102);
        detail.setAmount(new BigDecimal(100));
        detail.setRate(100);
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

        try {
            serviceDetailDAOImplNotExist.updateServiceDetail(detail, true);
            fail("expected DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>retrieveServiceDetail</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetailAccuracy() throws Exception {
        InvoiceServiceDetail detail = serviceDetailDAOImpl.retrieveServiceDetail(56);

        assertEquals("The id is not as expected", 56, detail.getId());
        assertEquals("The invoice id is not as expected", 79, detail.getInvoice().getId());

    }

    /**
     * Test <code>retrieveServiceDetail</code> for failure. Condition: no record with the id. Expect:
     * <code>EntityNotFoundException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetailNoRecord() throws Exception {
        try {
            serviceDetailDAOImpl.retrieveServiceDetail(1000);
            fail("Should throw EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * Test <code>retrieveServiceDetail</code> for failure. Condition: database is not exist. Expect:
     * <code>DataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetailNotExist() throws Exception {

        try {
            serviceDetailDAOImplNotExist.retrieveServiceDetail(56);
            fail("Should throw DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }

    }

    /**
     * Test <code>retrieveServiceDetails</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetailsInvoiceId() throws Exception {
        InvoiceServiceDetail[] details = serviceDetailDAOImpl.retrieveServiceDetails(29);

        assertEquals("The result size is wrong", 2, details.length);
        assertEquals("The invoice id is not as expected", 29, details[0].getInvoice().getId());
        assertEquals("The invoice id is not as expected", 29, details[1].getInvoice().getId());
    }

    /**
     * Test <code>retrieveServiceDetails</code> for failure. Condition: database is not exist. Expect:
     * <code>DataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetailsInvoiceIdNotExist() throws Exception {
        try {
            serviceDetailDAOImplNotExist.retrieveServiceDetails(29);
            fail("Should throw DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>retrieveAllServiceDetails</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveAllServiceDetailsAccuracy() throws Exception {
        InvoiceServiceDetail[] details = serviceDetailDAOImpl.retrieveAllServiceDetails();

        assertEquals("The result size is wrong", 100, details.length);
    }

    /**
     * Test <code>retrieveAllServiceDetails</code> for failure. Condition: database is not exist. Expect:
     * <code>DataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveAllServiceDetailsNotExist() throws Exception {
        try {
            serviceDetailDAOImplNotExist.retrieveAllServiceDetails();
            fail("Should throw DataAccessException");
        } catch (DataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>addServiceDetails</code> for accuracy. Condition: normal. Expect: the records are inserted to
     * the database.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailsAccuracy1() throws Exception {
        InvoiceServiceDetail detail1 = new InvoiceServiceDetail();
        Invoice invoice1 = new Invoice();
        invoice1.setId(76);
        TimeEntry timeEntry1 = new TimeEntry();
        timeEntry1.setId(60);
        timeEntry1.setCreationUser("testuser");
        timeEntry1.setHours(10);

        detail1.setInvoice(invoice1);
        detail1.setTimeEntry(timeEntry1);
        detail1.setModificationUser("testuser");
        detail1.setCreationUser("testuser");

        InvoiceServiceDetail detail2 = new InvoiceServiceDetail();
        Invoice invoice2 = new Invoice();
        invoice2.setId(75);
        TimeEntry timeEntry2 = new TimeEntry();
        timeEntry2.setId(51);
        timeEntry2.setCreationUser("testuser");
        timeEntry2.setHours(5);

        detail2.setInvoice(invoice2);
        detail2.setTimeEntry(timeEntry2);
        detail2.setModificationUser("testuser2");
        detail2.setCreationUser("testuser2");

        serviceDetailDAOImpl.addServiceDetails(new InvoiceServiceDetail[] {detail1, detail2}, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set =
            statement.executeQuery("select * from service_details where invoice_id = 76 and time_entry_id = 60");

        if (set.next()) {
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 1000, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }

        set = statement.executeQuery("select * from service_details where invoice_id = 75 and time_entry_id = 51");

        if (set.next()) {
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 500, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }
    }

    /**
     * Test <code>addServiceDetails</code> for accuracy. Condition: normal. Expect: the records are inserted to
     * the database.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailsAccuracy2() throws Exception {
        InvoiceServiceDetail detail1 = new InvoiceServiceDetail();
        Invoice invoice1 = new Invoice();
        invoice1.setId(76);
        TimeEntry timeEntry1 = new TimeEntry();
        timeEntry1.setId(60);
        timeEntry1.setCreationUser("testuser");
        timeEntry1.setHours(10);

        detail1.setInvoice(invoice1);
        detail1.setTimeEntry(timeEntry1);
        detail1.setModificationUser("testuser");
        detail1.setCreationUser("testuser");

        InvoiceServiceDetail detail2 = new InvoiceServiceDetail();
        Invoice invoice2 = new Invoice();
        invoice2.setId(75);
        TimeEntry timeEntry2 = new TimeEntry();
        timeEntry2.setId(51);
        timeEntry2.setCreationUser("testuser");
        timeEntry2.setHours(5);

        detail2.setInvoice(invoice2);
        detail2.setTimeEntry(timeEntry2);
        detail2.setModificationUser("testuser2");
        detail2.setCreationUser("testuser2");

        serviceDetailDAOImpl2.addServiceDetails(new InvoiceServiceDetail[] {detail1, detail2}, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set =
            statement.executeQuery("select * from service_details where invoice_id = 76 and time_entry_id = 60");

        if (set.next()) {
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 1000, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }

        set = statement.executeQuery("select * from service_details where invoice_id = 75 and time_entry_id = 51");

        if (set.next()) {
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 500, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }
    }

    /**
     * Test <code>addServiceDetails</code> for failure. Condition: details is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailsNull() throws Exception {
        try {
            serviceDetailDAOImpl.addServiceDetails(null, false);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>addServiceDetails</code> for failure. Condition: details contains null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailsContainsNull() throws Exception {
        try {
            serviceDetailDAOImpl.addServiceDetails(new InvoiceServiceDetail[] {null}, false);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>deleteServiceDetails</code> for accuracy. Condition: normal. Expect: record is deleted
     * correctly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetailsAccuracy1() throws Exception {
        serviceDetailDAOImpl.deleteServiceDetails(new long[] {78, 86, 76}, false);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id = 788");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }

        set = statement.executeQuery("select * from service_details where service_detail_id = 865");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }

        set = statement.executeQuery("select * from service_details where service_detail_id = 76");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }
    }

    /**
     * Test <code>deleteServiceDetails</code> for accuracy. Condition: normal. Expect: record is deleted
     * correctly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetailsAccuracy2() throws Exception {
        serviceDetailDAOImpl.deleteServiceDetails(new long[] {78, 86, 76}, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id = 788");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }

        set = statement.executeQuery("select * from service_details where service_detail_id = 865");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }

        set = statement.executeQuery("select * from service_details where service_detail_id = 76");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }
    }

    /**
     * Test <code>deleteServiceDetails</code> for accuracy. Condition: normal. Expect: record is deleted
     * correctly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetailsAccuracy3() throws Exception {
        serviceDetailDAOImpl2.deleteServiceDetails(new long[] {78, 86, 76}, false);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id = 788");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }

        set = statement.executeQuery("select * from service_details where service_detail_id = 865");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }

        set = statement.executeQuery("select * from service_details where service_detail_id = 76");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }
    }

    /**
     * Test <code>deleteServiceDetails</code> for failure. Condition: some records are not in the database.
     * Expect: <code>BatchExecutionException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetailsBatchError() throws Exception {
        try {
            serviceDetailDAOImpl.deleteServiceDetails(new long[] {1000, 86, 76}, true);
            fail("BatchExecutionException is expected");
        } catch (BatchExecutionException e) {
            // expected
        }

    }

    /**
     * Test <code>deleteServiceDetails</code> for failure. Condition: details is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetailsNull() throws Exception {
        try {
            serviceDetailDAOImpl.deleteServiceDetails(null, false);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateServiceDetails</code> for accuracy. Condition: normal. Expect: the records are updated.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailsAccuracy1() throws Exception {
        InvoiceServiceDetail detail1 = new InvoiceServiceDetail();
        detail1.setId(100);
        detail1.setAmount(new BigDecimal(100));
        detail1.setRate(100);
        Invoice invoice1 = new Invoice();
        invoice1.setId(76);
        TimeEntry timeEntry1 = new TimeEntry();
        timeEntry1.setId(60);
        timeEntry1.setCreationUser("testuser");
        timeEntry1.setHours(10);

        detail1.setInvoice(invoice1);
        detail1.setTimeEntry(timeEntry1);
        detail1.setModificationUser("testuser");
        detail1.setCreationUser("testuser");

        InvoiceServiceDetail detail2 = new InvoiceServiceDetail();
        detail2.setId(56);
        detail2.setAmount(new BigDecimal(50));
        detail2.setRate(100);
        Invoice invoice2 = new Invoice();
        invoice2.setId(76);
        TimeEntry timeEntry2 = new TimeEntry();
        timeEntry2.setId(60);
        timeEntry2.setCreationUser("testuser");
        timeEntry2.setHours(10);

        detail2.setInvoice(invoice2);
        detail2.setTimeEntry(timeEntry2);
        detail2.setModificationUser("testuser");
        detail2.setCreationUser("testuser");

        serviceDetailDAOImpl.updateServiceDetails(new InvoiceServiceDetail[] {detail1, detail2}, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id=100");

        if (set.next()) {
            assertEquals("The timeEntryId is not inserted correctly", 60, set.getLong("time_entry_id"));
            assertEquals("The invoiceId is not inserted correctly", 76, set.getLong("invoice_id"));
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 100, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }

        set = statement.executeQuery("select * from service_details where service_detail_id=56");

        if (set.next()) {
            assertEquals("The timeEntryId is not inserted correctly", 60, set.getLong("time_entry_id"));
            assertEquals("The invoiceId is not inserted correctly", 76, set.getLong("invoice_id"));
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 50, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }
    }

    /**
     * Test <code>updateServiceDetails</code> for accuracy. Condition: normal. Expect: the records are updated.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailsAccuracy2() throws Exception {
        InvoiceServiceDetail detail1 = new InvoiceServiceDetail();
        detail1.setId(100);
        detail1.setAmount(new BigDecimal(100));
        detail1.setRate(100);
        Invoice invoice1 = new Invoice();
        invoice1.setId(76);
        TimeEntry timeEntry1 = new TimeEntry();
        timeEntry1.setId(60);
        timeEntry1.setCreationUser("testuser");
        timeEntry1.setHours(10);

        detail1.setInvoice(invoice1);
        detail1.setTimeEntry(timeEntry1);
        detail1.setModificationUser("testuser");
        detail1.setCreationUser("testuser");

        InvoiceServiceDetail detail2 = new InvoiceServiceDetail();
        detail2.setId(56);
        detail2.setAmount(new BigDecimal(50));
        detail2.setRate(100);
        Invoice invoice2 = new Invoice();
        invoice2.setId(76);
        TimeEntry timeEntry2 = new TimeEntry();
        timeEntry2.setId(60);
        timeEntry2.setCreationUser("testuser");
        timeEntry2.setHours(10);

        detail2.setInvoice(invoice2);
        detail2.setTimeEntry(timeEntry2);
        detail2.setModificationUser("testuser");
        detail2.setCreationUser("testuser");

        serviceDetailDAOImpl2.updateServiceDetails(new InvoiceServiceDetail[] {detail1, detail2}, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id=100");

        if (set.next()) {
            assertEquals("The timeEntryId is not inserted correctly", 60, set.getLong("time_entry_id"));
            assertEquals("The invoiceId is not inserted correctly", 76, set.getLong("invoice_id"));
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 100, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }

        set = statement.executeQuery("select * from service_details where service_detail_id=56");

        if (set.next()) {
            assertEquals("The timeEntryId is not inserted correctly", 60, set.getLong("time_entry_id"));
            assertEquals("The invoiceId is not inserted correctly", 76, set.getLong("invoice_id"));
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 50, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }
    }

    /**
     * Test <code>updateServiceDetails</code> for failure. Condition: some record are not in the database.
     * Expect: <code>BatchExecutionException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailsBatchError() throws Exception {
        InvoiceServiceDetail detail1 = new InvoiceServiceDetail();
        detail1.setId(1000);
        detail1.setAmount(new BigDecimal(100));
        detail1.setRate(100);
        Invoice invoice1 = new Invoice();
        invoice1.setId(76);
        TimeEntry timeEntry1 = new TimeEntry();
        timeEntry1.setId(60);
        timeEntry1.setCreationUser("testuser");
        timeEntry1.setHours(10);

        detail1.setInvoice(invoice1);
        detail1.setTimeEntry(timeEntry1);
        detail1.setModificationUser("testuser");
        detail1.setCreationUser("testuser");

        InvoiceServiceDetail detail2 = new InvoiceServiceDetail();
        detail2.setId(56);
        detail2.setAmount(new BigDecimal(50));
        detail2.setRate(100);
        Invoice invoice2 = new Invoice();
        invoice2.setId(76);
        TimeEntry timeEntry2 = new TimeEntry();
        timeEntry2.setId(60);
        timeEntry2.setCreationUser("testuser");
        timeEntry2.setHours(10);

        detail2.setInvoice(invoice2);
        detail2.setTimeEntry(timeEntry2);
        detail2.setModificationUser("testuser");
        detail2.setCreationUser("testuser");

        try {
            serviceDetailDAOImpl.updateServiceDetails(new InvoiceServiceDetail[] {detail1, detail2}, true);
            fail("Expected BatchExecutionException");
        } catch (BatchExecutionException e) {
            // expected
        }

    }

    /**
     * Test <code>updateServiceDetails</code> for failure. Condition: details is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailsNull() throws Exception {
        try {
            serviceDetailDAOImpl.updateServiceDetail(null, false);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>updateServiceDetails</code> for failure. Condition: details contains null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailsContainsNull() throws Exception {
        try {
            serviceDetailDAOImpl.updateServiceDetails(new InvoiceServiceDetail[] {null}, false);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>retrieveServiceDetails</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetailsAccuracy() throws Exception {
        InvoiceServiceDetail[] details = serviceDetailDAOImpl.retrieveServiceDetails(new long[] {10, 20, 30});

        assertEquals("The result size is wrong", 3, details.length);
    }

    /**
     * Test <code>retrieveServiceDetails</code> for failure. Condition: details is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetailsNull() throws Exception {
        try {
            serviceDetailDAOImpl.retrieveServiceDetails(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * The DataSet loader used by DBUnit.
     *
     * @return the data set
     *
     * @throws Exception
     *             to JUnit
     */
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSet(new FileInputStream("test_files/partial.xml"));
    }

}
