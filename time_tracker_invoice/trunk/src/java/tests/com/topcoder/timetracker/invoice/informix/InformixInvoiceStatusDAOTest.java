/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.informix;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.timetracker.invoice.InvoiceConfigurationException;
import com.topcoder.timetracker.invoice.InvoiceDataAccessException;
import com.topcoder.timetracker.invoice.InvoiceStatus;
import com.topcoder.timetracker.invoice.InvoiceUnrecognizedEntityException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>InformixInvoiceStatusDAO</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class InformixInvoiceStatusDAOTest extends TestCase {

    /** Unit under test. */
    private InformixInvoiceStatusDAO informixInvoiceStatusDAO;

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(InformixInvoiceStatusDAOTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestHelper.loadConfiguration("config-InformixInvoiceStatusDAO.xml");

        informixInvoiceStatusDAO = new InformixInvoiceStatusDAO();
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearNamespaces();

        super.tearDown();
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: all fields are set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceStatusDAONoNamespaceAccuracy() throws Exception {
        InformixInvoiceStatusDAO dao = new InformixInvoiceStatusDAO();

        // check the connectionFactory fields
        Field connectionFactoryField = InformixInvoiceStatusDAO.class.getDeclaredField("connectionFactory");
        connectionFactoryField.setAccessible(true);
        assertNotNull("The field connectionFactory should be set", connectionFactoryField.get(dao));
    }

    /**
     * Test constructor for accuracy. Condition: normal. Expect: all fields are set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceStatusDAOWithNamespaceAccuracy() throws Exception {
        InformixInvoiceStatusDAO dao =
            new InformixInvoiceStatusDAO("com.topcoder.timetracker.invoice.informix.InformixInvoiceStatusDAO");

        // check the connectionFactory fields
        Field connectionFactoryField = InformixInvoiceStatusDAO.class.getDeclaredField("connectionFactory");
        connectionFactoryField.setAccessible(true);
        assertNotNull("The field connectionFactory should be set", connectionFactoryField.get(dao));
    }

    /**
     * Test constructor for failure. Condition: namespace is null. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceStatusDAOWithNamespaceNull() throws Exception {
        try {
            new InformixInvoiceStatusDAO(null);
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
    public void testInformixInvoiceStatusDAOWithNamespaceEmptyString() throws Exception {
        try {
            new InformixInvoiceStatusDAO("  \n");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: namespace is not available. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceStatusDAOWithNamespaceNotAvailable() throws Exception {
        try {
            new InformixInvoiceStatusDAO("notAvailable");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: there is no objectFactoryNamespace. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceStatusDAOWithNamespaceNoObjectFactoryNamespace() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceStatusDAO-noObjectFactoryNamespace.xml");

        try {
            new InformixInvoiceStatusDAO("noObjectFactoryNamespace");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: objectFactoryNamespace is an empty string. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceStatusDAOWithNamespaceEmptyObjectFactoryNamespace() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceStatusDAO-emptyObjectFactoryNamespace.xml");

        try {
            new InformixInvoiceStatusDAO("emptyObjectFactoryNamespace");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: there is no dbConnectionFactoryKey. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceStatusDAOWithNamespaceDbConnectionFactoryKey() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceStatusDAO-noDbConnectionFactoryKey.xml");

        try {
            new InformixInvoiceStatusDAO("noDbConnectionFactoryKey");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: dbConnectionFactoryKey is an empty string. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceStatusDAOWithNamespaceEmptyDbConnectionFactoryKey() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceStatusDAO-emptyDbConnectionFactoryKey.xml");

        try {
            new InformixInvoiceStatusDAO("emptyDbConnectionFactoryKey");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: objectFactoryNamespace is not available. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceStatusDAOWithNamespaceObjectFactoryNamespaceNotAvailable() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceStatusDAO-objectFactoryNamespaceNotAvailable.xml");

        try {
            new InformixInvoiceStatusDAO("objectFactoryNamespaceNotAvailable");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: objectFactoryNamespace is not correct. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceStatusDAOWithNamespaceObjectFactoryNamespaceNotCorrect() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceStatusDAO-objectFactoryNamespaceNotCorrect.xml");

        try {
            new InformixInvoiceStatusDAO("objectFactoryNamespaceNotCorrect");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: the entry for key dbConnectionFactory is not correct class. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceStatusDAOWithNamespaceDbConnectionFactoryClassCast() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceStatusDAO-dbConnectionFactoryClassCast.xml");

        try {
            new InformixInvoiceStatusDAO("dbConnectionFactoryClassCast");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: objectFactoryNamespace doesn't contain the needed key. Expect:
     * <code>InvoiceConfigurationException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInformixInvoiceStatusDAOWithNamespaceMissingKey() throws Exception {
        TestHelper.loadConfiguration("config-InformixInvoiceStatusDAO-missingKey.xml");

        try {
            new InformixInvoiceStatusDAO("missingKey");
            fail("Should throw InvoiceConfigurationException");
        } catch (InvoiceConfigurationException e) {
            // expected
        }
    }

    /**
     * Test <code>getInvoiceStatus</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatusByIdAccuracy() throws Exception {
        InvoiceStatus invoiceStatus = informixInvoiceStatusDAO.getInvoiceStatus(1);

        assertEquals("The id of the returned value is not as expected", 1, invoiceStatus.getId());
    }

    /**
     * Test <code>getInvoiceStatus</code> for failure. Condition: there is no such invoice status with given id.
     * Expect: <code>InvoiceUnrecognizedEntityException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatusByIdMissing() throws Exception {
        try {
            informixInvoiceStatusDAO.getInvoiceStatus(7);
            fail("Should throw InvoiceUnrecognizedEntityException");
        } catch (InvoiceUnrecognizedEntityException e) {
            // expected
        }
    }

    /**
     * Test <code>getInvoiceStatus</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatusByDescriptionAccuracy() throws Exception {
        InvoiceStatus invoiceStatus = informixInvoiceStatusDAO.getInvoiceStatus("testDescription");

        assertEquals("The id of the returned value is not as expected", 1, invoiceStatus.getId());
        assertEquals("The description of the returned value is not as expected", "testDescription", invoiceStatus
            .getDescription());
    }

    /**
     * Test <code>getInvoiceStatus</code> for failure. Condition: there is no such invoice status with given
     * description. Expect: <code>InvoiceDataAccessException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatusByDescriptionMissing() throws Exception {
        try {
            informixInvoiceStatusDAO.getInvoiceStatus("unrecognized");
            fail("Should throw InvoiceDataAccessException");
        } catch (InvoiceDataAccessException e) {
            // expected
        }
    }

    /**
     * Test <code>getInvoiceStatus</code> for failure. Condition: the description is null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatusByDescriptionNull() throws Exception {
        try {
            informixInvoiceStatusDAO.getInvoiceStatus(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getAllInvoiceStatus</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllInvoiceStatus() throws Exception {
        InvoiceStatus[] invoiceStatuses = informixInvoiceStatusDAO.getAllInvoiceStatus();
        List expectedIds = new ArrayList();
        expectedIds.add(new Long(1));
        expectedIds.add(new Long(2));
        expectedIds.add(new Long(3));
        expectedIds.add(new Long(4));
        expectedIds.add(new Long(5));

        assertEquals("The returned numbers of invoice statuses is not correct", 5, invoiceStatuses.length);
        for (int i = 0; i < invoiceStatuses.length; i++) {
            expectedIds.remove(new Long(invoiceStatuses[i].getId()));
        }
        assertTrue("The returned value is not as expected", expectedIds.isEmpty());
    }

}
