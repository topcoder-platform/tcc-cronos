/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.db;

import junit.framework.TestCase;

import com.topcoder.util.collection.typesafeenum.Enum;


/**
 * Unit test for the <code>ClientColumnName</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ClientColumnNameUnitTests extends TestCase {
    /** Represents the private instance used for test. */
    private ClientColumnName columnName = null;

    /**
     * Sets up the test environment.
     */
    protected void setUp() {
        columnName = ClientColumnName.ALL;
    }

    /**
     * <p>
     * Accuracy test for inheritance, should inherit from Enum.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testInheritance() throws Exception {
        assertTrue("ClientColumnName should inherit from Enum", columnName instanceof Enum);
    }

    /**
     * <p>
     * Accuracy test for method <code>NAME</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testNAMEAccuracy() throws Exception {
        assertNotNull("The NAME should be set.", ClientColumnName.NAME);
    }

    /**
     * <p>
     * Accuracy test for method <code>COMPANY_ID</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCOMPANY_IDAccuracy() throws Exception {
        assertNotNull("The COMPANY_ID should be set.", ClientColumnName.COMPANY_ID);
    }

    /**
     * <p>
     * Accuracy test for method <code>CREATION_DATE</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCREATION_DATEAccuracy() throws Exception {
        assertNotNull("The CREATION_DATE should be set.", ClientColumnName.CREATION_DATE);
    }

    /**
     * <p>
     * Accuracy test for method <code>CREATION_USER</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCREATION_USERAccuracy() throws Exception {
        assertNotNull("The CREATION_USER should be set.", ClientColumnName.CREATION_USER);
    }

    /**
     * <p>
     * Accuracy test for method <code>MODIFICATION_DATE</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testMODIFICATION_DATEAccuracy() throws Exception {
        assertNotNull("The MODIFICATION_DATE should be set.", ClientColumnName.MODIFICATION_DATE);
    }

    /**
     * <p>
     * Accuracy test for method <code>MODIFICATION_USER</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testMODIFICATION_USERAccuracy() throws Exception {
        assertNotNull("The MODIFICATION_USER should be set.", ClientColumnName.MODIFICATION_USER);
    }

    /**
     * <p>
     * Accuracy test for method <code>START_DATE</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSTART_DATEAccuracy() throws Exception {
        assertNotNull("The START_DATE should be set.", ClientColumnName.START_DATE);
    }

    /**
     * <p>
     * Accuracy test for method <code>END_DATE</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testEND_DATEAccuracy() throws Exception {
        assertNotNull("The END_DATE should be set.", ClientColumnName.END_DATE);
    }

    /**
     * <p>
     * Accuracy test for method <code>SALES_TAX</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSALES_TAXAccuracy() throws Exception {
        assertNotNull("The SALES_TAX should be set.", ClientColumnName.SALES_TAX);
    }

    /**
     * <p>
     * Accuracy test for method <code>ACTIVE</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testACTIVEAccuracy() throws Exception {
        assertNotNull("The ACTIVE should be set.", ClientColumnName.ACTIVE);
    }

    /**
     * <p>
     * Accuracy test for method <code>PAYMENT_TERM_ID</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testPAYMENT_TERM_IDAccuracy() throws Exception {
        assertNotNull("The PAYMENT_TERM_ID should be set.", ClientColumnName.PAYMENT_TERM_ID);
    }

    /**
     * <p>
     * Accuracy test for method <code>ALL</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testALLAccuracy() throws Exception {
        assertNotNull("The ALL should be set.", ClientColumnName.ALL);
    }

    /**
     * <p>
     * Accuracy test for method <code>ID</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testIDAccuracy() throws Exception {
        assertNotNull("The ID should be set.", ClientColumnName.ID);
    }
}
