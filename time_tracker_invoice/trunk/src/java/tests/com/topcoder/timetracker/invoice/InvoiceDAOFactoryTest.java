/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import com.topcoder.timetracker.invoice.informix.TestHelper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>InvoiceDAOFactory</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class InvoiceDAOFactoryTest extends TestCase {

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(InvoiceDAOFactoryTest.class);
    }

    /**
     * Sets the unit test up.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestHelper.loadConfiguration("config-InvoiceManagerDelegate.xml");
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
     * Test <code>getInvoiceDAO</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceDAO1() throws Exception {
        InvoiceDAOFactory.clear();
        assertNotNull("The method can't return a result", InvoiceDAOFactory.getInvoiceDAO());
    }

    /**
     * Test <code>getInvoiceDAO</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceDAO2() throws Exception {
        assertNotNull("The method can't return a result", InvoiceDAOFactory.getInvoiceDAO());
    }

    /**
     * Test <code>getInvoiceStatusDAO</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatusDAO1() throws Exception {
        InvoiceDAOFactory.clear();
        assertNotNull("The method can't return a result", InvoiceDAOFactory.getInvoiceStatusDAO());
    }

    /**
     * Test <code>getInvoiceStatusDAO</code> for accuracy. Condition: normal. Expect: returned value is as
     * expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetInvoiceStatusDAO2() throws Exception {
        assertNotNull("The method can't return a result", InvoiceDAOFactory.getInvoiceStatusDAO());
    }

}
