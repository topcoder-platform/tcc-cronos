/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.failuretests;
import junit.framework.TestCase;

import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceConfigurationException;
import com.topcoder.timetracker.invoice.InvoiceSearchDepth;
import com.topcoder.timetracker.invoice.InvoiceUnrecognizedEntityException;
import com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO;
/**
 * This class contains unit tests for <code>InformixInvoiceDAO</code> class.
 *
 * @author enefem21
 * @version 1.0
 */
public class FailureTestInformixInvoiceDAO extends TestCase {
	/**
	 * The InformixInvoiceDAO instance used to test.
	 */
	private InformixInvoiceDAO dao = null;
    /**
     * Set Up the test environment before testing.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.removeNamespaces();
        FailureTestHelper.loadConfig("test_files/failure/InvoiceDAO.xml");
        dao = new InformixInvoiceDAO("com.topcoder.timetracker.invoice.informix.InformixInvoiceDAO");
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
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with null Namespace.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAONullNamespace() throws Exception {
        try {
        	new InformixInvoiceDAO(null);
            fail("testInformixInvoiceDAO_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with empty Namespace,
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOEmptyNamespace() throws Exception {
        try {
        	new InformixInvoiceDAO(" ");
            fail("testInformixInvoiceDAO_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail1() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail1");
            fail("testInformixInvoiceDAOFail1 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail2() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail2");
            fail("testInformixInvoiceDAOFail2 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail3() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail3");
            fail("testInformixInvoiceDAOFail3 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail4() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail4");
            fail("testInformixInvoiceDAOFail4 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail5() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail5");
            fail("testInformixInvoiceDAOFail5 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail6() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail6");
            fail("testInformixInvoiceDAOFail6 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail7() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail7");
            fail("testInformixInvoiceDAOFail7 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail8() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail8");
            fail("testInformixInvoiceDAOFail8 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail9() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail9");
            fail("testInformixInvoiceDAOFail9 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail10() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail10");
            fail("testInformixInvoiceDAOFail10 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail11() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail11");
            fail("testInformixInvoiceDAOFail11 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail12() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail12");
            fail("testInformixInvoiceDAOFail12 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail13() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail13");
            fail("testInformixInvoiceDAOFail13 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail14() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail14");
            fail("testInformixInvoiceDAOFail14 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail15() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail15");
            fail("testInformixInvoiceDAOFail15 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail16() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail16");
            fail("testInformixInvoiceDAOFail16 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail17() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail17");
            fail("testInformixInvoiceDAOFail17 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail18() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail18");
            fail("testInformixInvoiceDAOFail18 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail19() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail19");
            fail("testInformixInvoiceDAOFail19 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail20() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail20");
            fail("testInformixInvoiceDAOFail20 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail21() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail21");
            fail("testInformixInvoiceDAOFail21is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail22() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail22");
            fail("testInformixInvoiceDAOFail22is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail23() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail23");
            fail("testInformixInvoiceDAOFail23 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail24() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail24");
            fail("testInformixInvoiceDAOFail24 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail25() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail25");
            fail("testInformixInvoiceDAOFail25 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail26() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail26");
            fail("testInformixInvoiceDAOFail26 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail27() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail27");
            fail("testInformixInvoiceDAOFail27 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail28() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail28");
            fail("testInformixInvoiceDAOFail28 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail29() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail29");
            fail("testInformixInvoiceDAOFail29 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail30() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail30");
            fail("testInformixInvoiceDAOFail30 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail31() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail31");
            fail("testInformixInvoiceDAOFail31 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail32() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail32");
            fail("testInformixInvoiceDAOFail32 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }

    /**
     * Tests <code>InformixInvoiceDAO(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInformixInvoiceDAOFail33() throws Exception {
        try {
        	new InformixInvoiceDAO("InformixInvoiceDAO.fail33");
            fail("testInformixInvoiceDAOFail33 is failure.");
        } catch (InvoiceConfigurationException iae) {
            // pass
        }
    }


    /**
     * Tests <code>addInvoice(Invoice invoice, boolean audit)</code> method
     * for failure with null Invoice.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testAddInvoiceNullInvoice() throws Exception {
        try {
        	dao.addInvoice(null, false);
            fail("testAddInvoice_NullInvoice is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests <code>addInvoice(Invoice invoice, boolean audit)</code> method
     * for failure with invalid Invoice.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testAddInvoiceInvalidInvoice() throws Exception {
        try {
        	dao.addInvoice(FailureTestHelper.createInvoice(), false);
            fail("testAddInvoiceInvalidInvoice is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests <code>updateInvoice(Invoice invoice, boolean audit)</code> method
     * for failure with null Invoice.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testUpdateInvoiceNullInvoice() throws Exception {
        try {
        	dao.updateInvoice(null, false);
            fail("testUpdateInvoice_NullInvoice is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests <code>updateInvoice(Invoice invoice, boolean audit)</code> method
     * for failure with null Invoice.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testUpdateInvoiceInvalidInvoice() throws Exception {
        try {
        	dao.updateInvoice(new Invoice(), false);
            fail("testUpdateInvoiceInvalidInvoice is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests <code>getInvoice(long invoiceId)</code> method
     * for failure with non exist InvoiceId.
     * InvoiceUnrecognizedEntityException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetInvoiceNonExistInvoiceId() throws Exception {
        try {
        	dao.getInvoice(9999898);
            fail("testGetInvoiceNonExistInvoiceId is failure.");
        } catch (InvoiceUnrecognizedEntityException iae) {
            // pass
        }
    }
    /**
     * Tests <code>searchInvoices(Filter filter, InvoiceSearchDepth depth)</code> method
     * for failure with null Filter.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testSearchInvoicesNullFilter() throws Exception {
        try {
        	dao.searchInvoices(null, InvoiceSearchDepth.INVOICE_ALL);
            fail("testSearchInvoices_NullFilter is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests <code>canCreateInvoice(long projectId)</code> method
     * for failure with negative ProjectId.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCanCreateInvoiceNegativeProjectId() throws Exception {
        try {
        	dao.canCreateInvoice(-1);
            fail("testCanCreateInvoiceNegativeProjectId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests <code>canCreateInvoice(long projectId)</code> method
     * for failure with negative ProjectId.
     * InvoiceUnrecognizedEntityException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCanCreateInvoiceNonExistProjectId() throws Exception {
        try {
        	dao.canCreateInvoice(9998);
            fail("testCanCreateInvoiceNonExistProjectId is failure.");
        } catch (InvoiceUnrecognizedEntityException iae) {
            // pass
        }
    }

}