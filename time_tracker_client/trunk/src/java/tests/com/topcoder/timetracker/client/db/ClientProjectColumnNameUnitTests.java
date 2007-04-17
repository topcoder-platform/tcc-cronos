/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.db;

import junit.framework.TestCase;

import com.topcoder.util.collection.typesafeenum.Enum;


/**
 * Unit test for the <code>ClientProjectColumnName</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ClientProjectColumnNameUnitTests extends TestCase {
    /** Represents the private instance used for test. */
    private ClientProjectColumnName columnName = null;

    /**
     * Sets up the test environment.
     */
    protected void setUp() {
        columnName = ClientProjectColumnName.CLIENT_ID;
    }

    /**
     * <p>
     * Accuracy test for inheritance, should inherit from Enum.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testInheritance() throws Exception {
        assertTrue("ClientProjectColumnName should inherit from Enum", columnName instanceof Enum);
    }

    /**
     * <p>
     * Accuracy test for method <code>CLIENT_ID</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCLIENT_IDAccuracy() throws Exception {
        assertNotNull("The CLIENT_ID should be set.", ClientProjectColumnName.CLIENT_ID);
    }

    /**
     * <p>
     * Accuracy test for method <code>PROJECT_ID</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testPROJECT_IDAccuracy() throws Exception {
        assertNotNull("The PROJECT_ID should be set.", ClientProjectColumnName.PROJECT_ID);
    }

    /**
     * <p>
     * Accuracy test for method <code>MODIFICATION_DATE</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testMODIFICATION_DATEAccuracy() throws Exception {
        assertNotNull("The MODIFICATION_DATE should be set.", ClientProjectColumnName.MODIFICATION_DATE);
    }

    /**
     * <p>
     * Accuracy test for method <code>MODIFICATION_USER</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testMODIFICATION_USERAccuracy() throws Exception {
        assertNotNull("The MODIFICATION_USER should be set.", ClientProjectColumnName.MODIFICATION_USER);
    }

    /**
     * <p>
     * Accuracy test for method <code>CREATION_USER</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCREATION_USERAccuracy() throws Exception {
        assertNotNull("The CREATION_USER should be set.", ClientProjectColumnName.CREATION_USER);
    }

    /**
     * <p>
     * Accuracy test for method <code>CREATION_DATE</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCREATION_DATEAccuracy() throws Exception {
        assertNotNull("The CREATION_DATE should be set.", ClientProjectColumnName.CREATION_DATE);
    }
}
