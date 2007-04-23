/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.failuretests;

import java.io.File;
import java.math.BigDecimal;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.servicedetail.BatchExecutionException;
import com.topcoder.timetracker.invoice.servicedetail.ConfigurationException;
import com.topcoder.timetracker.invoice.servicedetail.EntityNotFoundException;
import com.topcoder.timetracker.invoice.servicedetail.InvalidDataException;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.dao.impl.ServiceDetailDAOImpl;

/**
 * Failure test cases for <code>ServiceDetailDAOImpl</code>.
 *
 * @author myxgyy
 * @version 1.0
 */
public class ServiceDetailDAOImplFailureTests extends TestCase {
	/**
	 * Represents the config file used for testing.
	 */
	private static final String CONFIG_FILE = "test_files" + File.separatorChar
	    + "Failure" + File.separatorChar + "ServiceDetailDAOFailure.xml";

    /**
     * Unit under test use batch.
     */
    private ServiceDetailDAOImpl serviceDetailDAOImpl;

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     * @return the suite.
     */
    public static Test suite() {
        return new TestSuite(ServiceDetailDAOImplFailureTests.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
    	TestHelper.loadXMLConfig(CONFIG_FILE);
    	TestHelper.setUpDataBase();
        serviceDetailDAOImpl = new ServiceDetailDAOImpl();
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
    	TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
    }

    /**
     * Test constructor for failure. Condition: namespace is null.
     * Expect: <code>IllegalArgumentException</code>.
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
     * Test constructor for failure. Condition: namespace is unknown.
     * Expect: <code>ConfigurationException</code>.
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
        try {
            new ServiceDetailDAOImpl("BatchError");
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
        try {
            new ServiceDetailDAOImpl("NoConnection");
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
    public void testServiceDetailDAOImplWithNamespaceInvalidConnectionNamespace() throws Exception {
        try {
            new ServiceDetailDAOImpl("InvalidConnection");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: no connection_name. Expect:
     * <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailDAOImplWithNamespaceNoConnectionName() throws Exception {
        try {
            new ServiceDetailDAOImpl("NoConnectionName");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: no connection_name. Expect:
     * <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailDAOImplWithNamespaceInvalidConnectionName() throws Exception {
        try {
            new ServiceDetailDAOImpl("InvalidConnectionName");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: no id generator. Expect:
     * <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailDAOImplWithNamespaceNoIdGenerator() throws Exception {
        try {
            new ServiceDetailDAOImpl("NoIdGenerator");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: invalid id generator name. Expect:
     * <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailDAOImplWithNamespaceInvalidIdGenerator() throws Exception {
        try {
            new ServiceDetailDAOImpl("InvalidIdGenerator");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: no FactoryNamespace. Expect:
     * <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailDAOImplWithNamespaceNoFactoryNamespace() throws Exception {
        try {
            new ServiceDetailDAOImpl("NoFactoryNamespace");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: invalid FactoryNamespace. Expect:
     * <code>ConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testServiceDetailDAOImplWithNamespaceInvalidFactoryNamespace() throws Exception {
        try {
            new ServiceDetailDAOImpl("InvalidFactoryNamespace");
            fail("Should throw ConfigurationException");
        } catch (ConfigurationException e) {
            // expected
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
}
