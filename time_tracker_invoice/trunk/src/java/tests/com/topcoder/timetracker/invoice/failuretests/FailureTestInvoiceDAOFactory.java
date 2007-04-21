/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.failuretests;
import junit.framework.TestCase;

import com.topcoder.timetracker.invoice.InvoiceConfigurationException;
import com.topcoder.timetracker.invoice.InvoiceDAOFactory;

/**
 * This class contains unit tests for <code>InvoiceDAOFactory</code> class.
 *
 * @author enefem21
 * @version 1.0
 */
public class FailureTestInvoiceDAOFactory extends TestCase {

    /**
     * Set Up the test environment before testing.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        InvoiceDAOFactory.clear();
        super.setUp();
        FailureTestHelper.removeNamespaces();
    }

    /**
     * Clean up the test environment after testing.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.removeNamespaces();
    }

    /**
     * Tests <code>getInvoiceDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceDAOInvalid1() throws Exception {
        try {
        	InvoiceDAOFactory.getInvoiceDAO();
            fail("testGetInvoiceDAOInvalid1 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }
    /**
     * Tests <code>getInvoiceDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceDAOInvalid2() throws Exception {
    	FailureTestHelper.loadConfig("test_files/failure/InvoiceDAO/InvoiceDAOInvalid1.xml");
        try {
        	InvoiceDAOFactory.getInvoiceDAO();
            fail("testGetInvoiceDAOInvalid2 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>getInvoiceDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceDAOInvalid3() throws Exception {
    	FailureTestHelper.loadConfig("test_files/failure/InvoiceDAO/InvoiceDAOInvalid2.xml");
        try {
        	InvoiceDAOFactory.getInvoiceDAO();
            fail("testGetInvoiceDAOInvalid3 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>getInvoiceDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceDAOInvalid4() throws Exception {
    	FailureTestHelper.loadConfig("test_files/failure/InvoiceDAO/InvoiceDAOInvalid3.xml");
        try {
        	InvoiceDAOFactory.getInvoiceDAO();
            fail("testGetInvoiceDAOInvalid4 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>getInvoiceDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceDAOInvalid5() throws Exception {
    	FailureTestHelper.loadConfig("test_files/failure/InvoiceDAO/InvoiceDAOInvalid4.xml");
        try {
        	InvoiceDAOFactory.getInvoiceDAO();
            fail("testGetInvoiceDAOInvalid5 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>getInvoiceDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceDAOInvalid6() throws Exception {
    	FailureTestHelper.loadConfig("test_files/failure/InvoiceDAO/InvoiceDAOInvalid5.xml");
        try {
        	InvoiceDAOFactory.getInvoiceDAO();
            fail("testGetInvoiceDAOInvalid6 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>getInvoiceDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceDAOInvalid7() throws Exception {
    	FailureTestHelper.loadConfig("test_files/failure/InvoiceDAO/InvoiceDAOInvalid6.xml");
        try {
        	InvoiceDAOFactory.getInvoiceDAO();
            fail("testGetInvoiceDAOInvalid7 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>getInvoiceStatusDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceStatusDAOInvalid1() throws Exception {
        try {
        	InvoiceDAOFactory.getInvoiceStatusDAO();
            fail("testGetInvoiceStatusDAOInvalid1 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }
    /**
     * Tests <code>getInvoiceStatusDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceStatusDAOInvalid2() throws Exception {
    	FailureTestHelper.loadConfig("test_files/failure/InvoiceDAO/InvoiceStatusInvalid1.xml");
        try {
        	InvoiceDAOFactory.getInvoiceStatusDAO();
            fail("testGetInvoiceStatusDAOInvalid2 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>getInvoiceStatusDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceStatusDAOInvalid3() throws Exception {
    	FailureTestHelper.loadConfig("test_files/failure/InvoiceDAO/InvoiceStatusInvalid2.xml");
        try {
        	InvoiceDAOFactory.getInvoiceStatusDAO();
            fail("testGetInvoiceStatusDAOInvalid3 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>getInvoiceStatusDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceStatusDAOInvalid4() throws Exception {
    	FailureTestHelper.loadConfig("test_files/failure/InvoiceDAO/InvoiceStatusInvalid3.xml");
        try {
        	InvoiceDAOFactory.getInvoiceStatusDAO();
            fail("testGetInvoiceStatusDAOInvalid4 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>getInvoiceStatusDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceStatusDAOInvalid5() throws Exception {
    	FailureTestHelper.loadConfig("test_files/failure/InvoiceDAO/InvoiceStatusInvalid4.xml");
        try {
        	InvoiceDAOFactory.getInvoiceStatusDAO();
            fail("testGetInvoiceStatusDAOInvalid5 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>getInvoiceStatusDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceStatusDAOInvalid6() throws Exception {
    	FailureTestHelper.loadConfig("test_files/failure/InvoiceDAO/InvoiceStatusInvalid5.xml");
        try {
        	InvoiceDAOFactory.getInvoiceStatusDAO();
            fail("testGetInvoiceStatusDAOInvalid6 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>getInvoiceStatusDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceStatusDAOInvalid7() throws Exception {
    	FailureTestHelper.loadConfig("test_files/failure/InvoiceDAO/InvoiceStatusInvalid6.xml");
        try {
        	InvoiceDAOFactory.getInvoiceStatusDAO();
            fail("testGetInvoiceStatusDAOInvalid7 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }
    /**
     * Tests <code>getInvoiceStatusDAO()</code> method
     * for failure with invalid configuration.
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceStatusDAOInvalid8() throws Exception {
    	FailureTestHelper.loadConfig("test_files/failure/InvoiceDAO/InvoiceStatusInvalid7.xml");
        try {
        	InvoiceDAOFactory.getInvoiceStatusDAO();
            fail("testGetInvoiceStatusDAOInvalid8 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }
}