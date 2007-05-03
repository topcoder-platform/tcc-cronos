/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.servicedetail.ejb.LocalServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.ejb.LocalServiceDetailHome;
import com.topcoder.timetracker.invoice.servicedetail.ejb.ServiceDetailBean;
import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Unit test for <code>ServiceDetailManagerImpl</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class ServiceDetailManagerImplTest extends DBTestCase {

    /** Unit under test. */
    private ServiceDetailManagerImpl serviceDetailManager;

    /** JDBC connection used in the unit test. */
    private Connection jdbcConnection;

    /** Context used in this unit test. */
    private Context context;

    /** Represents the configuration file that contains the properties needed by this demo
     * for setting DBUNIT system properties regarding the test database.
     */
    private static final String DBUNIT_CONFIG_DB_FILE="dbunit_config.properties";

    /**
     * Represents the database driver class name. It is initialized in the
     * constructor.
     */
    private final String driverClass;

    /**
     * Represents the connection URL for the database.It is initialized in the
     * constructor.
     */
    private final String connectionURL;

    /**
     * Represents the user for the database.It is initialized in the 
     * constructor.
     */
    private final String username;

    /**
     * Represents the user's password for the database.It is initialized in the
     * constructor.
     */
    private final String password;

    /**
     * Constructor of the unit test.
     *
     * @param name
     *            test name
     * @throws Exception
     *             to JUnit
     */
    public ServiceDetailManagerImplTest(String name) throws Exception {
        super(name);
        // Read the config properties for DbUnit
        ConfigManager cm= ConfigManager.getInstance();
        final String dbunitNamespace = "demo.namespace";
        cm.add(dbunitNamespace, DBUNIT_CONFIG_DB_FILE, ConfigManager.CONFIG_PROPERTIES_FORMAT );
        driverClass= cm.getString(dbunitNamespace, "driverClass");
        connectionURL = cm.getString(dbunitNamespace, "connectionURL");
        username = cm.getString(dbunitNamespace, "username");
        password = cm.getString(dbunitNamespace, "password");

        cm.removeNamespace(dbunitNamespace);

        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, driverClass);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
            connectionURL);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, username);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, password);

        // database connection
        Class.forName(driverClass);
    }

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(ServiceDetailManagerImplTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestHelper.loadConfiguration("service_detail_manager_accuracy.xml");
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

        serviceDetailManager = new ServiceDetailManagerImpl();

        jdbcConnection =
            DriverManager.getConnection(connectionURL, username,password);
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        jdbcConnection.close();

        TestHelper.clearNamespaces();
        super.tearDown();
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: the object is constructed correctly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailManagerImplAccuracy() throws Exception {
        new ServiceDetailManagerImpl();
        assertNotNull("The creation is failed", new ServiceDetailManagerImpl());
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: the object is constructed correctly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailManagerImplWithNamespaceAccuracy() throws Exception {
        assertNotNull("The creation is failed", new ServiceDetailManagerImpl(
            "com.topcoder.timetracker.invoice.servicedetail.impl"));
    }

    /**
     * Test constructor for failure. Condition: namespace is null. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailManagerImplWithNamespaceNull() throws Exception {
        try {
            new ServiceDetailManagerImpl(null);
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
    public void testServiceDetailManagerImplWithNamespaceEmptyString() throws Exception {
        try {
            new ServiceDetailManagerImpl("   \n");
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
    public void testServiceDetailManagerImplWithNamespaceUnknown() throws Exception {
        try {
            new ServiceDetailManagerImpl("unknown");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: there is no connection_string. Expect:
     * <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailManagerImplWithNamespaceNoConnectionString() throws Exception {
        TestHelper.loadConfiguration("service_detail_manager_no_connection_string.xml");

        try {
            new ServiceDetailManagerImpl("noConnectionString");
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
        ResultSet set =
            statement.executeQuery("select * from service_details where invoice_id = 60 and time_entry_id = 60");

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
     * Test <code>addServiceDetail</code> for accuracy. Condition: normal. Expect: the detail is inserted to the
     * database.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailAccuracy2() throws Exception {
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

        serviceDetailManager.addServiceDetail(detail, false);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set =
            statement.executeQuery("select * from service_details where invoice_id = 60 and time_entry_id = 60");

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
     * Test <code>addServiceDetail</code> for failure. Condition: detail is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailNull() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");
        try {
            serviceDetailManager.addServiceDetail(null, false);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
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
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

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
            serviceDetailManager.addServiceDetail(detail, true);
            fail("InvalidDataException is expected");
        } catch (InvalidDataException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
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
        detail.setModificationUser("testuser");
        detail.setCreationUser("testuser");

        try {
            serviceDetailManager.addServiceDetail(detail, true);
            fail("InvalidDataException is expected");
        } catch (InvalidDataException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
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
        detail.setCreationUser("testuser");

        try {
            serviceDetailManager.addServiceDetail(detail, true);
            fail("InvalidDataException is expected");
        } catch (InvalidDataException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
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

        try {
            serviceDetailManager.addServiceDetail(detail, true);
            fail("InvalidDataException is expected");
        } catch (InvalidDataException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
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
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

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
            serviceDetailManager.addServiceDetail(detail, false);
            fail("InvalidDataException is expected");
        } catch (InvalidDataException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
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
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        serviceDetailManager.deleteServiceDetail(78, false);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id = 788 ");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>deleteServiceDetail</code> for accuracy. Condition: normal. Expect: the deleted record is not
     * in the database anymore.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetailAccuracy2() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        serviceDetailManager.deleteServiceDetail(78, true);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details where service_detail_id = 788");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>deleteServiceDetail</code> for failure. Condition: the record is not in the table. Expect:
     * <code>EntityNotFoundException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetailNotFound() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        try {
            serviceDetailManager.deleteServiceDetail(101, true);
            fail("Should throw EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
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
     * Test <code>deleteAllServiceDetails</code> for accuracy. Condition: normal. Expect: there is no record in
     * the database.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteAllServiceDetailsAccuracy2() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        serviceDetailManager.deleteAllServiceDetails(false);

        Statement statement = jdbcConnection.createStatement();
        ResultSet set = statement.executeQuery("select * from service_details");

        if (set.next()) {
            fail("The record is not deleted correctly");
        }

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>updateServiceDetail</code> for accuracy. Condition: normal. Expect: the record is updated
     * correctly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailAccuracy1() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

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

        serviceDetailManager.updateServiceDetail(detail, false);

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

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>updateServiceDetail</code> for accuracy. Condition: normal. Expect: the record is updated
     * correctly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailAccuracy2() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

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

        serviceDetailManager.updateServiceDetail(detail, true);

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

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>updateServiceDetail</code> for failure. Condition: detail is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailNull() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        try {
            serviceDetailManager.updateServiceDetail(null, false);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
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
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

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
            serviceDetailManager.updateServiceDetail(detail, true);
            fail("expected InvalidDataException");
        } catch (InvalidDataException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
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
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        InvoiceServiceDetail detail = serviceDetailManager.retrieveServiceDetail(56);

        assertEquals("The id is not as expected", 56, detail.getId());
        assertEquals("The invoice id is not as expected", 79, detail.getInvoice().getId());

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>retrieveServiceDetails</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetailsInvoiceId() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        InvoiceServiceDetail[] details = serviceDetailManager.retrieveServiceDetails(29);

        assertEquals("The result size is wrong", 2, details.length);
        assertEquals("The invoice id is not as expected", 29, details[0].getInvoice().getId());
        assertEquals("The invoice id is not as expected", 29, details[1].getInvoice().getId());

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>retrieveAllServiceDetails</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveAllServiceDetails() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        InvoiceServiceDetail[] details = serviceDetailManager.retrieveAllServiceDetails();

        assertEquals("The result size is wrong", 100, details.length);

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>addServiceDetails</code> for accuracy. Condition: normal. Expect: the records are inserted to
     * the database.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailsAccuracy() throws Exception {
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

        serviceDetailManager.addServiceDetails(new InvoiceServiceDetail[] {detail1, detail2}, true);

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

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>addServiceDetails</code> for failure. Condition: details is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailsNull() throws Exception {
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
     * Test <code>addServiceDetails</code> for failure. Condition: details contains null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddServiceDetailsContainsNull() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");
        try {
            serviceDetailManager.addServiceDetails(new InvoiceServiceDetail[] {null}, false);
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
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetailsAccuracy() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        serviceDetailManager.deleteServiceDetails(new long[] {60}, false);

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

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>deleteServiceDetails</code> for failure. Condition: details is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteServiceDetailsNull() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");
        try {
            serviceDetailManager.deleteServiceDetails(null, false);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
        }
    }

    /**
     * Test <code>updateServiceDetails</code> for accuracy. Condition: normal. Expect: the records are updated.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailsAccuracy() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

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

        serviceDetailManager.updateServiceDetails(new InvoiceServiceDetail[] {detail1, detail2}, true);

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

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>updateServiceDetails</code> for failure. Condition: details is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateServiceDetailsNull() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");
        try {
            serviceDetailManager.updateServiceDetail(null, false);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
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
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");
        try {
            serviceDetailManager.updateServiceDetails(new InvoiceServiceDetail[] {null}, false);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
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
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        InvoiceServiceDetail[] details = serviceDetailManager.retrieveServiceDetails(new long[] {10, 20, 30});

        assertEquals("The result size is wrong", 3, details.length);

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>retrieveServiceDetails</code> for failure. Condition: details is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRetrieveServiceDetailsNull() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");
        try {
            serviceDetailManager.retrieveServiceDetails(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
        }
    }

    /**
     * Test <code>getService</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetServiceAccuracy() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        assertNotNull("getService return nothing", serviceDetailManager.getService());

        context.unbind("java:comp/env/factory_namespace");
        context.unbind("java:comp/env/dao_name");
    }

    /**
     * Test <code>getService</code> for failure. Condition: the sessionBean is not in JNDI. Expect:
     * <code>TransactionCreationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetServiceNamingException() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        context.unbind("sessionBean");

        try {
            serviceDetailManager.getService();
            fail("should throw TransactionCreationException");
        } catch (TransactionCreationException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
        }
    }

    /**
     * Test <code>getService</code> for failure. Condition: error creating the session bean (missing
     * configuration). Expect: <code>TransactionCreationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetServiceMissingConfiguration() throws Exception {
        context.rebind("java:comp/env/factory_namespace", "objectFactoryNamespace");
        context.rebind("java:comp/env/dao_name", "dao_key");

        TestHelper.clearNamespaces();

        try {
            serviceDetailManager.getService();
            fail("should throw EJBException");
        } catch (TransactionCreationException e) {
            // expected
        } finally {
            context.unbind("java:comp/env/factory_namespace");
            context.unbind("java:comp/env/dao_name");
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
