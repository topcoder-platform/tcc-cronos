/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingStatusDAO;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test cases for <code>{@link FixedBillingStatusManagerImpl}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class FixedBillingStatusManagerImplTest extends TestCase {
    /** The DBConnectionFactory instance for testing. */
    private DBConnectionFactory connFactory;

    /** The String instance for testing. */
    private String connName;

    /** The String instance for testing. */
    private String idGen;

    /** The String instance for testing. */
    private String searchBundleNamespace;

    /** The DbFixedBillingStatusDAO instance for testing. */
    private DbFixedBillingStatusDAO statusDAO;

    /** The FixedBillingStatus array instance for testing. */
    private FixedBillingStatus[] statuses;

    /** The FixedBillingStatusManagerImpl instance for testing. */
    private FixedBillingStatusManagerImpl fixedBillingStatusManagerImpl;

    /**
     * Set up the initial values.
     * @throws Exception to JUnit.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfigFile(TestHelper.DB_FACTORY_CONFIGE_FILE_NAME);
        TestHelper.loadConfigFile(TestHelper.DB_CONFIGE_FILE_NAME);
        connFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory");
        connName = "Informix_Connection_Producer";
        idGen = "com.topcoder.timetracker.entry.fixedbilling";
        searchBundleNamespace = "com.topcoder.search.builder";
        statusDAO = new DbFixedBillingStatusDAO(connFactory, connName, idGen, searchBundleNamespace);
        statuses = new FixedBillingStatus[3];

        for (int i = 0; i < statuses.length; i++) {
            statuses[i] = new FixedBillingStatus();
            statuses[i].setDescription("desc" + i);
            statuses[i].setCreationUser("user" + i);
            statuses[i].setModificationUser("modifyuser" + i);
        }

        fixedBillingStatusManagerImpl = new FixedBillingStatusManagerImpl(statusDAO);
        TestHelper.executeSQL("delete from fix_bill_status");
    }

    /**
     * Clear up the config.
     *
     * @throws Exception to junit.
     */
    public void tearDown() throws Exception {
        TestHelper.executeSQL("delete from fix_bill_status");
        TestHelper.removeNamespaces();
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerImpl#FixedBillingStatusManagerImpl(FixedBillingStatusDAO)}</code>
     * with null value. Should throw an IllegalArgumentException here.
     */
    public void testConstructor_FixedBillingStatusDAO_Null() {
        try {
            statusDAO = null;
            new FixedBillingStatusManagerImpl(statusDAO);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerImpl#FixedBillingStatusManagerImpl(FixedBillingStatusDAO)}</code>
     * with success process.
     */
    public void testConstructor_FixedBillingStatusDAO_Success() {
        fixedBillingStatusManagerImpl = new FixedBillingStatusManagerImpl(statusDAO);
        assertNotNull("Unable to create the instance.", fixedBillingStatusManagerImpl);
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerImpl#createFixedBillingStatus(FixedBillingStatus)}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatus_FixedBillingStatus_Success()
        throws Exception {
        fixedBillingStatusManagerImpl.createFixedBillingStatus(statuses[0]);
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerImpl#updateFixedBillingStatus(FixedBillingStatus)}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatus_FixedBillingStatus_Success()
        throws Exception {
        fixedBillingStatusManagerImpl.createFixedBillingStatus(statuses[0]);
        fixedBillingStatusManagerImpl.updateFixedBillingStatus(statuses[0]);
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerImpl#deleteFixedBillingStatus(long)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatus_long_Success()
        throws Exception {
        fixedBillingStatusManagerImpl.createFixedBillingStatus(statuses[0]);
        fixedBillingStatusManagerImpl.deleteFixedBillingStatus(statuses[0].getId());
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerImpl#getFixedBillingStatus(long)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatus_long_Success()
        throws Exception {
        fixedBillingStatusManagerImpl.createFixedBillingStatus(statuses[0]);
        fixedBillingStatusManagerImpl.getFixedBillingStatus(statuses[0].getId());
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerImpl#searchFixedBillingStatuses(Filter)}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchFixedBillingStatuses_Filter_Success()
        throws Exception {
        fixedBillingStatusManagerImpl.createFixedBillingStatus(statuses[0]);
        fixedBillingStatusManagerImpl.searchFixedBillingStatuses(
                fixedBillingStatusManagerImpl.getFixedBillingStatusFilterFactory().createDescriptionFilter("desc0",
                StringMatchType.EXACT_MATCH));
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerImpl#createFixedBillingStatuses(FixedBillingStatus[])}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatus_Success()
        throws Exception {
        fixedBillingStatusManagerImpl.createFixedBillingStatuses(statuses);
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerImpl#updateFixedBillingStatuses(FixedBillingStatus[])}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatus_Success()
        throws Exception {
        fixedBillingStatusManagerImpl.createFixedBillingStatuses(statuses);
        fixedBillingStatusManagerImpl.updateFixedBillingStatuses(statuses);
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerImpl#deleteFixedBillingStatuses(long[])}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatuses_long_Success()
        throws Exception {
        fixedBillingStatusManagerImpl.createFixedBillingStatuses(statuses);
        fixedBillingStatusManagerImpl.deleteFixedBillingStatuses(new long[] {statuses[0].getId() });
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerImpl#getFixedBillingStatuses(long[])}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatuses_long_Success()
        throws Exception {
        fixedBillingStatusManagerImpl.createFixedBillingStatuses(statuses);
        fixedBillingStatusManagerImpl.getFixedBillingStatuses(new long[] {statuses[0].getId() });
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerImpl#getFixedBillingStatusFilterFactory()}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatusFilterFactory_Success()
        throws Exception {
        fixedBillingStatusManagerImpl.getFixedBillingStatusFilterFactory();
    }

    /**
     * Test the <code>{@link FixedBillingStatusManagerImpl#getAllFixedBillingStatuses()}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllFixedBillingStatuses_Success()
        throws Exception {
        fixedBillingStatusManagerImpl.createFixedBillingStatuses(statuses);
        fixedBillingStatusManagerImpl.getAllFixedBillingStatuses();
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(FixedBillingStatusManagerImplTest.class);
    }
}
