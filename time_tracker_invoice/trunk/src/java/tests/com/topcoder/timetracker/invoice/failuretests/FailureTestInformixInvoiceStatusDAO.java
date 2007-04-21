/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.failuretests;
import junit.framework.TestCase;

import com.topcoder.timetracker.invoice.InvoiceConfigurationException;
import com.topcoder.timetracker.invoice.InvoiceDataAccessException;
import com.topcoder.timetracker.invoice.InvoiceUnrecognizedEntityException;
import com.topcoder.timetracker.invoice.informix.InformixInvoiceStatusDAO;
/**
 * This class contains unit tests for <code>InformixInvoiceStatusDAO</code> class.
 *
 * @author enefem21
 * @version 1.0
 */
public class FailureTestInformixInvoiceStatusDAO extends TestCase {
	/**
	 * The InformixInvoiceStatusDAO instance used to test.
	 */
	private InformixInvoiceStatusDAO dao = null;
    /**
     * Set Up the test environment before testing.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.removeNamespaces();
        FailureTestHelper.loadConfig("test_files/failure/InvoiceStatusDAO.xml");
        dao = new InformixInvoiceStatusDAO("com.topcoder.timetracker.invoice.informix.InformixInvoiceStatusDAO");
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
     * Tests <code>InformixInvoiceStatusDAO(String namespace)</code> method
     * for failure with null Namespace.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceStatusDAONullNamespace() throws Exception {
        try {
        	new InformixInvoiceStatusDAO(null);
            fail("testInformixInvoiceStatusDAO_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>InformixInvoiceStatusDAO(String namespace)</code> method
     * for failure with empty Namespace,
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceStatusDAOEmptyNamespace() throws Exception {
        try {
        	new InformixInvoiceStatusDAO(" ");
            fail("testInformixInvoiceStatusDAO_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>InformixInvoiceStatusDAO(String namespace)</code> method
     * for failure with invalid configurable file,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceStatusDAOFail1() throws Exception {
        try {
        	new InformixInvoiceStatusDAO("InformixInvoiceStatusDAO.fail1");
            fail("testInformixInvoiceStatusDAOFail1 is failure.");
        } catch (InvoiceConfigurationException ice) {
            // pass
        }
    }
    
    /**
     * Tests <code>InformixInvoiceStatusDAO(String namespace)</code> method
     * for failure with invalid configurable file,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceStatusDAOFail2() throws Exception {
        try {
        	new InformixInvoiceStatusDAO("InformixInvoiceStatusDAO.fail2");
            fail("testInformixInvoiceStatusDAOFail2 is failure.");
        } catch (InvoiceConfigurationException ice) {
            // pass
        }
    }
    
    /**
     * Tests <code>InformixInvoiceStatusDAO(String namespace)</code> method
     * for failure with invalid configurable file,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceStatusDAOFail3() throws Exception {
        try {
        	new InformixInvoiceStatusDAO("InformixInvoiceStatusDAO.fail3");
            fail("testInformixInvoiceStatusDAOFail3 is failure.");
        } catch (InvoiceConfigurationException ice) {
            // pass
        }
    }
    
    /**
     * Tests <code>InformixInvoiceStatusDAO(String namespace)</code> method
     * for failure with invalid configurable file,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceStatusDAOFail4() throws Exception {
        try {
        	new InformixInvoiceStatusDAO("InformixInvoiceStatusDAO.fail4");
            fail("testInformixInvoiceStatusDAOFail4 is failure.");
        } catch (InvoiceConfigurationException ice) {
            // pass
        }
    }
    
    /**
     * Tests <code>InformixInvoiceStatusDAO(String namespace)</code> method
     * for failure with invalid configurable file,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceStatusDAOFail5() throws Exception {
        try {
        	new InformixInvoiceStatusDAO("InformixInvoiceStatusDAO.fail5");
            fail("testInformixInvoiceStatusDAOFail5 is failure.");
        } catch (InvoiceConfigurationException ice) {
            // pass
        }
    }
    
    /**
     * Tests <code>InformixInvoiceStatusDAO(String namespace)</code> method
     * for failure with invalid configurable file,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceStatusDAOFail6() throws Exception {
        try {
        	new InformixInvoiceStatusDAO("InformixInvoiceStatusDAO.fail6");
            fail("testInformixInvoiceStatusDAOFail6 is failure.");
        } catch (InvoiceConfigurationException ice) {
            // pass
        }
    }
    
    /**
     * Tests <code>InformixInvoiceStatusDAO(String namespace)</code> method
     * for failure with invalid configurable file,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceStatusDAOFail7() throws Exception {
        try {
        	new InformixInvoiceStatusDAO("InformixInvoiceStatusDAO.fail7");
            fail("testInformixInvoiceStatusDAOFail7 is failure.");
        } catch (InvoiceConfigurationException ice) {
            // pass
        }
    }
    
    /**
     * Tests <code>getInvoiceStatus(long id)</code> method
     * for failure with missing Id.
     * InvoiceUnrecognizedEntityException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceStatusMissingId() throws Exception {
        try {
        	dao.getInvoiceStatus(9999921);
            fail("testGetInvoiceStatusMissingId is failure.");
        } catch (InvoiceUnrecognizedEntityException iae) {
            // pass
        }
    }
    /**
     * Tests <code>getInvoiceStatus(String description)</code> method
     * for failure with null Description.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceStatusNullDescription() throws Exception {
        try {
        	dao.getInvoiceStatus(null);
            fail("testGetInvoiceStatus_NullDescription is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>getInvoiceStatus(String description)</code> method
     * for failure with empty Description,
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceStatusEmptyDescription() throws Exception {
        try {
        	dao.getInvoiceStatus(" ");
            fail("testGetInvoiceStatus_EmptyDescription is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>getInvoiceStatus(String description)</code> method
     * for failure with non exist Description,
     * InvoiceDataAccessException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceStatusNonExistDescription() throws Exception {
        try {
        	dao.getInvoiceStatus("helloworld.");
            fail("testGetInvoiceStatusNonExistDescription is failure.");
        } catch (InvoiceDataAccessException iae) {
            // pass
        }
    }
}