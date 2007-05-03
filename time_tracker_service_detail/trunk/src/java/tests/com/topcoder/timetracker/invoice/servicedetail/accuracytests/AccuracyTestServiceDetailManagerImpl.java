/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.accuracytests;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.dbunit.DBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.servicedetail.BatchExecutionException;
import com.topcoder.timetracker.invoice.servicedetail.ConfigurationException;
import com.topcoder.timetracker.invoice.servicedetail.DataAccessException;
import com.topcoder.timetracker.invoice.servicedetail.EntityNotFoundException;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManagerImpl;
import com.topcoder.timetracker.invoice.servicedetail.ejb.LocalServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.ejb.LocalServiceDetailHome;
import com.topcoder.timetracker.invoice.servicedetail.ejb.ServiceDetailBean;

/**
 * Unit test for <code>ServiceDetailManagerImpl</code>.
 * @author KLW
 * @version 1.1
 */
public class AccuracyTestServiceDetailManagerImpl extends DBTestCase {

    /** Instance used for testing. */
    private ServiceDetailManagerImpl serviceDetailManager;

    /** JDBC connection used in the unit test. */
    private Connection jdbcConnection;

    /** Context used in this unit test. */
    private Context context;

    /**
     * Sets the unit test up.
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {

        TestHelper.loadConfiguration(TestHelper.FILE_MANAGER_CONFIG);
        TestHelper.loadConfiguration(TestHelper.FILE_BEAN_CONFIG);
        TestHelper.loadConfiguration(TestHelper.FILE_DAO_CONFIG);

        MockContextFactory.setAsInitial();

        context = new InitialContext();

        // creates an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        // creates deployment descriptor of our sample bean. MockEjb does not support XML descriptors.
        SessionBeanDescriptor descriptor = new SessionBeanDescriptor("sessionBean", LocalServiceDetailHome.class,
                LocalServiceDetail.class, ServiceDetailBean.class);
        // Deploy operation simply creates Home and binds it to JNDI
        mockContainer.deploy(descriptor);

        serviceDetailManager = new ServiceDetailManagerImpl();

        DBConnectionFactory connFact = TestHelper.getDBConnectionFactory(TestHelper.TEST_CONN_NAMESPACE);
        this.jdbcConnection = connFact.createConnection();
    }

    /**
     * Tears the unit test down.
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.releaseConfiguration();
        this.jdbcConnection.close();
    }

    /**
     * Test constructor for accuracy.
     * @throws Exception
     *             to JUnit
     */
    public void testCounstructor_01() throws Exception {
        new ServiceDetailManagerImpl();
        assertNotNull("The creation is dailed", new ServiceDetailManagerImpl());
    }

    /**
     * Test constructor for accuracy.
     * @throws Exception
     *             to JUnit
     */
    public void testCounstructor_02() throws Exception {
        assertNotNull("The creation is dailed", new ServiceDetailManagerImpl(TestHelper.TEST_NAMESPACE));
    }

    /**
     * Test constructor for failure.
     * @throws Exception
     *             to JUnit
     */
    public void testCounstructor_03() throws Exception {
        try {
            new ServiceDetailManagerImpl(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure.
     * @throws Exception
     *             to JUnit
     */
    public void testCounstructor_04() throws Exception {
        try {
            new ServiceDetailManagerImpl("   ");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure.
     * @throws Exception
     *             to JUnit
     */
    public void testCounstructor_05() throws Exception {
        try {
            new ServiceDetailManagerImpl("unknown");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test <code>addServiceDetail</code> for accuracy.
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetail_01() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        Invoice invoice = new Invoice();
        invoice.setId(60);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        serviceDetailManager.addServiceDetail(detail, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement
                .executeQuery("select * from service_details where invoice_id = 60 and time_entry_id = 60");

        if (set.next()) {
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 1000, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>deleteServiceDetail</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetail_01() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        try {
            serviceDetailManager.deleteServiceDetail(78, false);
            fail("Should throw DataAccessException.");
        } catch (DataAccessException e) {
            // Success.
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
        }
    }

    /**
     * Test <code>deleteServiceDetail</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetail_02() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        serviceDetailManager.deleteServiceDetail(1, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id = 1");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>deleteAllServiceDetails</code> for accuracy. Condition: normal. Expect: there is no record in
     * the database.
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteAllServiceDetails() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        serviceDetailManager.deleteAllServiceDetails(true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>updateServiceDetail</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetail_01() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        InvoiceServiceDetail detail = this.serviceDetailManager.retrieveServiceDetail(1);
        detail.setModificationUser("accuracy");
        this.serviceDetailManager.updateServiceDetail(detail, true);
        detail = this.serviceDetailManager.retrieveServiceDetail(1);
        assertEquals("accuracy", detail.getModificationUser());

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>updateServiceDetail</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetail_02() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        InvoiceServiceDetail detail = this.serviceDetailManager.retrieveServiceDetail(1);
        detail.setId(20);
        detail.setModificationUser("accuracy");
        try {
            this.serviceDetailManager.updateServiceDetail(detail, true);
            fail("Should throw DataAccessException");
        } catch (DataAccessException e) {
            // success.
        }
        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>retrieveServiceDetail</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetail() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        InvoiceServiceDetail detail = serviceDetailManager.retrieveServiceDetail(5);

        assertEquals(-1, detail.getTimeEntry().getId());
        assertEquals(25, detail.getInvoice().getId());
        try {
            detail = this.serviceDetailManager.retrieveServiceDetail(-1);
            fail("Should throw EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // success.
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
        }
    }

    /**
     * Test <code>retrieveAllServiceDetails</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveAllServiceDetails() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        InvoiceServiceDetail[] details = serviceDetailManager.retrieveAllServiceDetails();

        assertEquals("The result size is wrong", 5, details.length);

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>addServiceDetails</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetails_01() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        InvoiceServiceDetail detail1 = new InvoiceServiceDetail();
        Invoice invoice1 = new Invoice();
        invoice1.setId(60);
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

        serviceDetailManager.addServiceDetails(new InvoiceServiceDetail[] { detail1, detail2 }, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement
                .executeQuery("select * from service_details where invoice_id = 60 and time_entry_id = 60");

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

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>addServiceDetails</code> for failure. Condition: details is null. Expect:
     * <code>IllegalArgumentException</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetails_02() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");
        try {
            serviceDetailManager.addServiceDetails(null, false);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
        }
    }

    /**
     * Test <code>deleteServiceDetails</code> for accuracy. Condition: normal. Expect: record is deleted
     * correctly.
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetails_01() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");
        InvoiceServiceDetail[] details = null;
        long[] ids = new long[] { 1, 2 };
        try {
            serviceDetailManager.deleteServiceDetails(ids, false);
        } catch (Exception e) {
            fail("Should not throw Exception.");
        }
        try {
            details = this.serviceDetailManager.retrieveServiceDetails(ids);
            fail("Should throw EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // Success.
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
        }
    }

    /**
     * Test <code>deleteServiceDetails</code> for accuracy. Condition: normal. Expect: record is deleted
     * correctly.
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetails_02() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");
        InvoiceServiceDetail[] details = null;
        long[] ids = new long[] { 1, 10 };
        try {
            serviceDetailManager.deleteServiceDetails(ids, false);
            fail("Should throw BatchExecutionException.");
        } catch (BatchExecutionException e) {
            // Success.
            assertEquals("Should throw the currect ids.", (e.getIds())[0], 10);
        }
        try {
            InvoiceServiceDetail detail = this.serviceDetailManager.retrieveServiceDetail(1);
            fail("Should throw EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // Success.
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
        }
    }

    /**
     * Test <code>updateServiceDetails</code> for accuracy. Condition: normal. Expect: the records are updated.
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetails_01() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        InvoiceServiceDetail detail1 = new InvoiceServiceDetail();
        detail1.setId(1);
        detail1.setAmount(new BigDecimal(100));
        detail1.setRate(100);
        Invoice invoice1 = new Invoice();
        invoice1.setId(60);
        TimeEntry timeEntry1 = new TimeEntry();
        timeEntry1.setId(60);
        timeEntry1.setCreationUser("testuser");
        timeEntry1.setHours(10);

        detail1.setInvoice(invoice1);
        detail1.setTimeEntry(timeEntry1);
        detail1.setModificationUser("testuser");
        detail1.setCreationUser("testuser");

        InvoiceServiceDetail detail2 = new InvoiceServiceDetail();
        detail2.setId(2);
        detail2.setAmount(new BigDecimal(50));
        detail2.setRate(100);
        Invoice invoice2 = new Invoice();
        invoice2.setId(60);
        TimeEntry timeEntry2 = new TimeEntry();
        timeEntry2.setId(60);
        timeEntry2.setCreationUser("testuser");
        timeEntry2.setHours(10);

        detail2.setInvoice(invoice2);
        detail2.setTimeEntry(timeEntry2);
        detail2.setModificationUser("testuser");
        detail2.setCreationUser("testuser");

        serviceDetailManager.updateServiceDetails(new InvoiceServiceDetail[] { detail1, detail2 }, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id=1");

        if (set.next()) {
            assertEquals("The timeEntryId is not inserted correctly", 60, set.getLong("time_entry_id"));
            assertEquals("The invoiceId is not inserted correctly", 60, set.getLong("invoice_id"));
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 100, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }

        set = statement.executeQuery("select * from service_details where service_detail_id=2");

        if (set.next()) {
            assertEquals("The timeEntryId is not inserted correctly", 60, set.getLong("time_entry_id"));
            assertEquals("The invoiceId is not inserted correctly", 60, set.getLong("invoice_id"));
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 50, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>updateServiceDetails</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetails_02() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        InvoiceServiceDetail[] details = this.serviceDetailManager.retrieveAllServiceDetails();
        this.serviceDetailManager.updateServiceDetails(details, true);
        details = this.serviceDetailManager.retrieveAllServiceDetails();
        assertEquals(5, details.length);
        assertEquals(13559, details[0].getRate());
        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>updateServiceDetails</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetails_03() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        InvoiceServiceDetail[] details = this.serviceDetailManager.retrieveServiceDetails(new long[] { 1, 2 });
        details[1].setId(10);
        try {
            this.serviceDetailManager.updateServiceDetails(details, true);
            fail("Should throw BatchExecutionException");
        } catch (BatchExecutionException e) {
            assertEquals(10, e.getIds()[0]);
        }

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>updateServiceDetails</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetails_04() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        InvoiceServiceDetail[] details = new InvoiceServiceDetail[0];
        try {
            this.serviceDetailManager.updateServiceDetails(details, true);
        } catch (Exception e) {
            fail("Should not throw Exception");
        }

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>retrieveServiceDetails</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetails_01() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");
        long[] ids = new long[0];
        try {
            InvoiceServiceDetail[] details = serviceDetailManager.retrieveServiceDetails(ids);
            assertEquals(0, details.length);
        } catch (Exception e) {
            fail("Should not throw Exception");
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
        }
    }

    /**
     * Test <code>retrieveServiceDetails</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetails_02() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");
        long[] ids = new long[] { 10 };
        try {
            InvoiceServiceDetail[] details = serviceDetailManager.retrieveServiceDetails(ids);
            fail("Should throw EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // Success.
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
        }
    }

    /**
     * Test <code>retrieveServiceDetails</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetails_03() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");
        long[] ids = new long[] { 1, 10 };
        try {
            InvoiceServiceDetail[] details = serviceDetailManager.retrieveServiceDetails(ids);
            fail("Should throw EntityNotFoundException.");
        } catch (EntityNotFoundException e) {
            // Success.
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
        }
    }

    /**
     * Test <code>retrieveServiceDetails</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetails_04() throws Exception {
        TestHelper.setUpDatabase();
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");
        InvoiceServiceDetail[] details = null;
        long[] ids = new long[] { 1, 2 };
        try {
            details = serviceDetailManager.retrieveServiceDetails(ids);
        } catch (Exception e) {
            fail("Should not throw Exception.");
        }
        assertEquals(2, details.length);
        assertEquals(1, details[0].getId());
        assertEquals(2, details[1].getId());
        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");

    }

    /**
     * The DataSet loader used by DBUnit.
     * @return the data set
     * @throws Exception
     *             to JUnit
     */
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSet(new FileInputStream("test_files/partial.xml"));
    }

}
