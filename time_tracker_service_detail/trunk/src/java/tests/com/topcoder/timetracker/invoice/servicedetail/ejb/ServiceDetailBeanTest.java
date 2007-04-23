/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.ejb;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.mockejb.EjbBeanAccess;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.servicedetail.EntityNotFoundException;
import com.topcoder.timetracker.invoice.servicedetail.InvalidDataException;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.TestHelper;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Unit test for <code>ServiceDetailBean</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class ServiceDetailBeanTest extends DBTestCase {

    /** Unit under test. */
    private ServiceDetailBean serviceDetailBean;

    /** JDBC connection used in the unit test. */
    private Connection jdbcConnection;

    /** Context used in this unit test. */
    private Context context;

    /**
     * Constructor of the unit test.
     *
     * @param name
     *            test name
     * @throws Exception
     *             to JUnit
     */
    public ServiceDetailBeanTest(String name) throws Exception {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.informix.jdbc.IfxDriver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
            "jdbc:informix-sqli://192.168.1.101:1526/service_details:INFORMIXSERVER=topcoder");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "informix");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "123456");

        // database connection
        Class driverClass = Class.forName("com.informix.jdbc.IfxDriver");

    }

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(ServiceDetailBeanTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestHelper.loadConfiguration("service_detail_bean_accuracy.xml");
        TestHelper.loadConfiguration("service_detail_dao_accuracy.xml");

        MockContextFactory.setAsInitial();

        context = new InitialContext();

        // creates an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        // creates deployment descriptor of our sample bean. MockEjb does not support XML descriptors.
        SessionBeanDescriptor localServiceDetailDescriptor =
            new SessionBeanDescriptor("sessionBean", LocalServiceDetailHome.class, LocalServiceDetail.class,
                ServiceDetailBean.class);
        // Deploy operation simply creates Home and binds it to JNDI
        mockContainer.deploy(localServiceDetailDescriptor);

        // bind factory_namespace environment variable
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        LocalServiceDetailHome sampleHome = (LocalServiceDetailHome) context.lookup("sessionBean");

        LocalServiceDetail sampleService = sampleHome.create();

        serviceDetailBean = (ServiceDetailBean) ((EjbBeanAccess) sampleService).getBean();

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");

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
        jdbcConnection.close();

        MockContextFactory.revertSetAsInitial();

        TestHelper.clearNamespaces();

        super.tearDown();
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: the object is created.
     */
    public void testServiceDetailBeanAccuracy() {
        assertNotNull("The creation is failed", new ServiceDetailBean());
    }

    /**
     * Test <code>ejbCreate</code> for accuracy. Condition: normal. Expect: all fields are set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testEjbCreateAccuracy() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        LocalServiceDetailHome sampleHome = (LocalServiceDetailHome) context.lookup("sessionBean");

        LocalServiceDetail sampleService = sampleHome.create();

        ((EjbBeanAccess) sampleService).getBean();

        // check the dao fields
        Field daoField = ServiceDetailBean.class.getDeclaredField("dao");
        daoField.setAccessible(true);
    }

    /**
     * Test ejbCreate for failure. Condition: there is no factory namespace. Expect: <code>EJBException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testEjbCreateNoFactoryNamespace() throws Exception {
        context.rebind("java:comp/env/dao_name", "dao_key");
        LocalServiceDetailHome sampleHome = (LocalServiceDetailHome) context.lookup("sessionBean");

        try {
            LocalServiceDetail sampleService = sampleHome.create();
            fail("Should throw EJBException");
        } catch (EJBException e) {
            // expected
        }
    }

    /**
     * Test ejbCreate for failure. Condition: there is no dao name. Expect: <code>EJBException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testEjbCreateNoDaoName() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        LocalServiceDetailHome sampleHome = (LocalServiceDetailHome) context.lookup("sessionBean");

        try {
            sampleHome.create();
            fail("Should throw EJBException");
        } catch (EJBException e) {
            // expected
        }
    }

    /**
     * Test ejbCreate for failure. Condition: the namespace is unknown. Expect: <code>EJBException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testEjbCreateNoNamespace() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "notAvailableNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");
        LocalServiceDetailHome sampleHome = (LocalServiceDetailHome) context.lookup("sessionBean");

        try {
            sampleHome.create();
            fail("Should throw EJBException");
        } catch (EJBException e) {
            // expected
        }
    }

    /**
     * Test ejbCreate for failure. Condition: the dao key is not in the object factory configuration. Expect:
     * <code>EJBException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testEjbCreateNoDao() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "notAvailableDaoName");
        LocalServiceDetailHome sampleHome = (LocalServiceDetailHome) context.lookup("sessionBean");

        try {
            sampleHome.create();
            fail("Should throw EJBException");
        } catch (EJBException e) {
            // expected
        }
    }

    /**
     * Test ejbCreate for failure. Condition: the dao key is refer to wrong object. Expect:
     * <code>EJBException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testEjbCreateClassCastException() throws Exception {
        TestHelper.loadConfiguration("service_detail_bean_classcast_ex.xml");

        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace_test");
        context.rebind("java:comp/env/dao_name", "dao_key");
        LocalServiceDetailHome sampleHome = (LocalServiceDetailHome) context.lookup("sessionBean");

        try {
            sampleHome.create();
            fail("Should throw EJBException");
        } catch (EJBException e) {
            // expected
        }
    }

    /**
     * Test <code>setSessionContext</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testSetSessionContext() {
        serviceDetailBean.setSessionContext(null);
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
        invoice.setId(60);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        serviceDetailBean.addServiceDetail(detail, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set =
            statement.executeQuery("select * from service_details where invoice_id = 60 and time_entry_id = 60");

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
        invoice.setId(60);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        serviceDetailBean.addServiceDetail(detail, false);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set =
            statement.executeQuery("select * from service_details where invoice_id = 60 and time_entry_id = 60");

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
            serviceDetailBean.addServiceDetail(null, false);
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
        invoice.setId(60);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        try {
            serviceDetailBean.addServiceDetail(detail, true);
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
        invoice.setId(60);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        try {
            serviceDetailBean.addServiceDetail(detail, true);
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
        invoice.setId(60);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setCreationUser("testuser");

        try {
            serviceDetailBean.addServiceDetail(detail, true);
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
        invoice.setId(60);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");

        try {
            serviceDetailBean.addServiceDetail(detail, true);
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
        invoice.setId(60);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser4");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        try {
            serviceDetailBean.addServiceDetail(detail, false);
            fail("InvalidDataException is expected");
        } catch (InvalidDataException e) {
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
        serviceDetailBean.deleteServiceDetail(78, false);

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
        serviceDetailBean.deleteServiceDetail(78, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id = 788");

        if (set.next()) {
            fail("The record is not deleted correctly");
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
            serviceDetailBean.deleteServiceDetail(101, true);
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
        serviceDetailBean.deleteAllServiceDetails(true);

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
        serviceDetailBean.deleteAllServiceDetails(false);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details");

        if (set.next()) {
            fail("The record is not deleted correctly");
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
        invoice.setId(60);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        serviceDetailBean.updateServiceDetail(detail, false);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id=100");

        if (set.next()) {
            assertEquals("The timeEntryId is not inserted correctly", 60, set.getLong("time_entry_id"));
            assertEquals("The invoiceId is not inserted correctly", 60, set.getLong("invoice_id"));
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
        invoice.setId(60);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        serviceDetailBean.updateServiceDetail(detail, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id=100");

        if (set.next()) {
            assertEquals("The timeEntryId is not inserted correctly", 60, set.getLong("time_entry_id"));
            assertEquals("The invoiceId is not inserted correctly", 60, set.getLong("invoice_id"));
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 100, set.getDouble("amount"), 10e-5);
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
            serviceDetailBean.updateServiceDetail(null, false);
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
        invoice.setId(60);
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(60);
        timeEntry.setCreationUser("testuser");
        timeEntry.setHours(10);

        detail.setInvoice(invoice);
        detail.setTimeEntry(timeEntry);
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        try {
            serviceDetailBean.updateServiceDetail(detail, true);
            fail("expected InvalidDataException");
        } catch (InvalidDataException e) {
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
        InvoiceServiceDetail detail = serviceDetailBean.retrieveServiceDetail(56);

        assertEquals("The id is not as expected", 56, detail.getId());
        assertEquals("The invoice id is not as expected", 79, detail.getInvoice().getId());

    }

    /**
     * Test <code>retrieveServiceDetails</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetailsInvoiceId() throws Exception {
        InvoiceServiceDetail[] details = serviceDetailBean.retrieveServiceDetails(29);

        assertEquals("The result size is wrong", 2, details.length);
        assertEquals("The invoice id is not as expected", 29, details[0].getInvoice().getId());
        assertEquals("The invoice id is not as expected", 29, details[1].getInvoice().getId());
    }

    /**
     * Test <code>retrieveAllServiceDetails</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveAllServiceDetails() throws Exception {
        InvoiceServiceDetail[] details = serviceDetailBean.retrieveAllServiceDetails();

        assertEquals("The result size is wrong", 100, details.length);
    }

    /**
     * Test <code>addServiceDetails</code> for accuracy. Condition: normal. Expect: the records are inserted to
     * the database.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailsAccuracy() throws Exception {
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

        serviceDetailBean.addServiceDetails(new InvoiceServiceDetail[] {detail1, detail2}, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set =
            statement.executeQuery("select * from service_details where invoice_id = 60 and time_entry_id = 60");

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
            serviceDetailBean.addServiceDetails(null, false);
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
            serviceDetailBean.addServiceDetails(new InvoiceServiceDetail[] {null}, false);
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
    public void testDeleteServiceDetailsAccuracy() throws Exception {
        serviceDetailBean.deleteServiceDetails(new long[] {78, 86, 60}, false);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id = 788");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }

        set = statement.executeQuery("select * from service_details where service_detail_id = 865");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }

        set = statement.executeQuery("select * from service_details where service_detail_id = 60");

        if (set.next()) {
            fail("The record is not deleted correctly");
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
            serviceDetailBean.deleteServiceDetails(null, false);
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
    public void testUpdateServiceDetailsAccuracy() throws Exception {
        InvoiceServiceDetail detail1 = new InvoiceServiceDetail();
        detail1.setId(100);
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
        detail2.setId(56);
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

        serviceDetailBean.updateServiceDetails(new InvoiceServiceDetail[] {detail1, detail2}, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id=100");

        if (set.next()) {
            assertEquals("The timeEntryId is not inserted correctly", 60, set.getLong("time_entry_id"));
            assertEquals("The invoiceId is not inserted correctly", 60, set.getLong("invoice_id"));
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 100, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
        }

        set = statement.executeQuery("select * from service_details where service_detail_id=56");

        if (set.next()) {
            assertEquals("The timeEntryId is not inserted correctly", 60, set.getLong("time_entry_id"));
            assertEquals("The invoiceId is not inserted correctly", 60, set.getLong("invoice_id"));
            assertEquals("The rate is not inserted correctly", 100, set.getDouble("rate"), 10e-5);
            assertEquals("The amount is not inserted correctly", 50, set.getDouble("amount"), 10e-5);
        } else {
            fail("Error in addServiceDetail");
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
            serviceDetailBean.updateServiceDetail(null, false);
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
            serviceDetailBean.updateServiceDetails(new InvoiceServiceDetail[] {null}, false);
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
        InvoiceServiceDetail[] details = serviceDetailBean.retrieveServiceDetails(new long[] {10, 20, 30});

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
            serviceDetailBean.retrieveServiceDetails(null);
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
