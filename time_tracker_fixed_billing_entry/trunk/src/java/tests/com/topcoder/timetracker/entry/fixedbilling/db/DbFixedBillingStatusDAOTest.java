/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.entry.fixedbilling.BatchOperationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;
import com.topcoder.timetracker.entry.fixedbilling.TestHelper;
import com.topcoder.timetracker.entry.fixedbilling.UnrecognizedEntityException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;


/**
 * Unit test cases for <code>{@link DbFixedBillingStatusDAO}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class DbFixedBillingStatusDAOTest extends TestCase {
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

    /**
     * Set up the initial values.
     *
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
            statuses[i].setCreationDate(new Date());
            statuses[i].setModificationDate(new Date());
            statuses[i].setModificationUser("modifyuser" + i);
        }

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
     * Test the <code>{@link DbFixedBillingStatusDAO#DbFixedBillingStatusDAO(DBConnectionFactory, String, String,
     * String, boolean)}</code> with null connection factory. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringBoolean_NullFactory()
        throws Exception {
        try {
            connFactory = null;
            new DbFixedBillingStatusDAO(connFactory, connName, idGen, searchBundleNamespace);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#DbFixedBillingStatusDAO(DBConnectionFactory, String, String,
     * String, boolean)}</code> with empty connection name. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringBoolean_EmptyConnName()
        throws Exception {
        try {
            connName = " ";
            new DbFixedBillingStatusDAO(connFactory, connName, idGen, searchBundleNamespace);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#DbFixedBillingStatusDAO(DBConnectionFactory, String, String,
     * String, boolean)}</code> with empty idGen name. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringBoolean_EmptyIdgen()
        throws Exception {
        try {
            idGen = " ";
            new DbFixedBillingStatusDAO(connFactory, connName, idGen, searchBundleNamespace);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#DbFixedBillingStatusDAO(DBConnectionFactory, String, String,
     * String, boolean)}</code> with empty search name space. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringBoolean_EmptySearchStrategy()
        throws Exception {
        try {
            searchBundleNamespace = " ";
            new DbFixedBillingStatusDAO(connFactory, connName, idGen, searchBundleNamespace);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#DbFixedBillingStatusDAO(DBConnectionFactory, String, String,
     * String, boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringBoolean_Success()
        throws Exception {
        statusDAO = new DbFixedBillingStatusDAO(connFactory, connName, idGen, searchBundleNamespace);
        assertNotNull("Unable to create the instance.", statusDAO);
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#createFixedBillingStatuses(FixedBillingStatus[])}</code> with null
     * array. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatusArray_NullArray()
        throws Exception {
        try {
            statuses = null;
            statusDAO.createFixedBillingStatuses(statuses);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#createFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * array which contains null. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatusArray_ContainNull()
        throws Exception {
        try {
            statuses[0] = null;
            statusDAO.createFixedBillingStatuses(statuses);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#createFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * wrong values. Should throw a IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatusArray_Missing1()
        throws Exception {
        try {
            statuses = new FixedBillingStatus[1];
            statuses[0] = new FixedBillingStatus();
            statuses[0].setCreationUser("user0");
            statuses[0].setModificationUser("modifyuser0");
            statusDAO.createFixedBillingStatuses(statuses);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#createFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * wrong values. Should throw a IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatusArray_Missing2()
        throws Exception {
        try {
            statuses = new FixedBillingStatus[1];
            statuses[0] = new FixedBillingStatus();
            statuses[0].setDescription("desc0");
            statuses[0].setModificationUser("modifyuser0");
            statusDAO.createFixedBillingStatuses(statuses);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#createFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * wrong values. Should throw a IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatusArray_Missing3()
        throws Exception {
        try {
            statuses = new FixedBillingStatus[1];
            statuses[0] = new FixedBillingStatus();
            statuses[0].setDescription("desc0");
            statuses[0].setCreationUser("user0");
            statusDAO.createFixedBillingStatuses(statuses);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#createFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * wrong values. Should throw a IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatusArray_Missing4()
        throws Exception {
        try {
            statuses = new FixedBillingStatus[1];
            statuses[0] = new FixedBillingStatus();
            statuses[0].setDescription("desc0");
            statuses[0].setCreationUser("user0");
            statuses[0].setModificationUser("modifyuser0");
            statuses[0].setModificationDate(new Date());
            statusDAO.createFixedBillingStatuses(statuses);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#createFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * wrong values. Should throw a IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatusArray_Missing5()
        throws Exception {
        try {
            statuses = new FixedBillingStatus[1];
            statuses[0] = new FixedBillingStatus();
            statuses[0].setDescription("desc0");
            statuses[0].setCreationUser("user0");
            statuses[0].setModificationUser("modifyuser0");
            statuses[0].setCreationDate(new Date());
            statusDAO.createFixedBillingStatuses(statuses);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#createFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * wrong values. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatusArray_Missing6()
        throws Exception {
        try {
            statuses = new FixedBillingStatus[1];
            statuses[0] = new FixedBillingStatus();
            statuses[0].setDescription("desc0");
            statuses[0].setCreationUser("user0");
            statuses[0].setModificationUser("modifyuser0");
            statuses[0].setModificationDate(new Date());
            statuses[0].setCreationDate(new Date());
            statuses[0].setId(10);
            statusDAO.createFixedBillingStatuses(statuses);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#createFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingStatuses_FixedBillingStatusArray_Success1()
        throws Exception {
        statusDAO = new DbFixedBillingStatusDAO(connFactory, connName, idGen, searchBundleNamespace);
        statusDAO.createFixedBillingStatuses(statuses);

        FixedBillingStatus[] result = statusDAO.getAllFixedBillingStatuses();
        compareStatuses(statuses, result);
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#updateFixedBillingStatuses(FixedBillingStatus[])}</code> with null
     * array. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatusArray_NullArray()
        throws Exception {
        try {
            statuses = null;
            statusDAO.updateFixedBillingStatuses(statuses);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#updateFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * array which contains null. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatusArray_ContainNull()
        throws Exception {
        try {
            statuses[0] = null;
            statusDAO.updateFixedBillingStatuses(statuses);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#updateFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * wrong values. Should throw a IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatusArray_Missing1()
        throws Exception {
        try {
            statuses = new FixedBillingStatus[1];
            statuses[0] = new FixedBillingStatus();
            statuses[0].setCreationUser("user0");
            statuses[0].setCreationDate(new Date());
            statuses[0].setModificationUser("modifyuser0");
            statuses[0].setId(10);
            statusDAO.updateFixedBillingStatuses(statuses);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#updateFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * wrong values. Should throw a IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatusArray_Missing2()
        throws Exception {
        try {
            statuses = new FixedBillingStatus[1];
            statuses[0] = new FixedBillingStatus();
            statuses[0].setDescription("desc0");
            statuses[0].setCreationDate(new Date());
            statuses[0].setModificationUser("modifyuser0");
            statuses[0].setId(10);
            statusDAO.updateFixedBillingStatuses(statuses);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#updateFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * wrong values. Should throw a IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatusArray_Missing3()
        throws Exception {
        try {
            statuses = new FixedBillingStatus[1];
            statuses[0] = new FixedBillingStatus();
            statuses[0].setDescription("desc0");
            statuses[0].setCreationUser("user0");
            statuses[0].setModificationUser("modifyuser0");
            statuses[0].setId(10);
            statusDAO.updateFixedBillingStatuses(statuses);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#updateFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * wrong values. Should throw a IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatusArray_Missing4()
        throws Exception {
        try {
            statuses = new FixedBillingStatus[1];
            statuses[0] = new FixedBillingStatus();
            statuses[0].setDescription("desc0");
            statuses[0].setCreationUser("user0");
            statuses[0].setCreationDate(new Date());
            statuses[0].setId(10);
            statusDAO.updateFixedBillingStatuses(statuses);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#updateFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * wrong values. Should throw a IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatusArray_Missing5()
        throws Exception {
        try {
            statuses = new FixedBillingStatus[1];
            statuses[0] = new FixedBillingStatus();
            statuses[0].setDescription("desc0");
            statuses[0].setCreationUser("user0");
            statuses[0].setCreationDate(new Date());
            statuses[0].setModificationUser("modifyuser0");
            statusDAO.updateFixedBillingStatuses(statuses);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#updateFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * wrong values. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatusArray_Missing6()
        throws Exception {
        try {
            statuses = new FixedBillingStatus[1];
            statuses[0] = new FixedBillingStatus();
            statuses[0].setDescription("desc0");
            statuses[0].setCreationUser("user0");
            statuses[0].setCreationDate(new Date());
            statuses[0].setModificationDate(new Date());
            statuses[0].setModificationUser("modifyuser0");
            statusDAO.updateFixedBillingStatuses(statuses);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#updateFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * wrong values. Should throw an UnrecognizedEntityException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatusArray_NotExist()
        throws Exception {
        try {
            statuses = new FixedBillingStatus[1];
            statuses[0] = new FixedBillingStatus();
            statuses[0].setDescription("desc0");
            statuses[0].setCreationUser("user0");
            statuses[0].setCreationDate(new Date());
            statuses[0].setModificationUser("modifyuser0");
            statuses[0].setModificationDate(new Date());
            statuses[0].setId(10);
            statusDAO.updateFixedBillingStatuses(statuses);
            fail("Should throw an UnrecognizedEntityException here.");
        } catch (UnrecognizedEntityException uee) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#updateFixedBillingStatuses(FixedBillingStatus[])}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingStatuses_FixedBillingStatusArray_Success1()
        throws Exception {
        statusDAO = new DbFixedBillingStatusDAO(connFactory, connName, idGen, searchBundleNamespace);
        statusDAO.createFixedBillingStatuses(statuses);

        for (int i = 0; i < statuses.length; i++) {
            statuses[i].setDescription("newdesc" + i);
            statuses[i].setCreationUser("newuser" + i);
            statuses[i].setModificationUser("newmodifyuser" + i);
        }

        statusDAO.updateFixedBillingStatuses(statuses);

        FixedBillingStatus[] result = statusDAO.getAllFixedBillingStatuses();
        compareStatuses(statuses, result);
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#deleteFixedBillingStatuses(long[])}</code> with null array. Should
     * throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatuses_longArray_NullArray()
        throws Exception {
        try {
            statusDAO.deleteFixedBillingStatuses(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#deleteFixedBillingStatuses(long[])}</code> with array which
     * contains null. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatuses_longArray_WrongItem()
        throws Exception {
        try {
            statusDAO.deleteFixedBillingStatuses(new long[] {-1 });
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#deleteFixedBillingStatuses(long[])}</code> with wrong values.
     * Should throw a DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatuses_longArray_NotExist()
        throws Exception {
        try {
            statusDAO.deleteFixedBillingStatuses(new long[] {10 });
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#deleteFixedBillingStatuses(long[])}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingStatuses_longArray_Success1()
        throws Exception {
        statusDAO.createFixedBillingStatuses(statuses);
        statusDAO.deleteFixedBillingStatuses(
                new long[] {statuses[0].getId(), statuses[1].getId(), statuses[2].getId() });

        FixedBillingStatus[] result = statusDAO.getAllFixedBillingStatuses();
        assertEquals("The return result should have 0 value.", 0, result.length);
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#getFixedBillingStatuses(long[])}</code> with null array. Should
     * throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatuses_longArray_Null()
        throws Exception {
        try {
            statusDAO.getFixedBillingStatuses(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#getFixedBillingStatuses(long[])}</code> with wrong values. Should
     * throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatuses_longArray_WrongValue()
        throws Exception {
        try {
            statusDAO.getFixedBillingStatuses(new long[] {Long.parseLong("-1") });
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#getFixedBillingStatuses(long[])}</code> with wrong values. Should
     * throw a BatchOperationException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatuses_longArray_NotExist()
        throws Exception {
        try {
            statusDAO.createFixedBillingStatuses(statuses);
            statusDAO.getFixedBillingStatuses(new long[] {
                    statuses[0].getId(), statuses[1].getId(), statuses[2].getId() + 100});
            fail("Should throw a BatchOperationException here.");
        } catch (BatchOperationException boe) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#getFixedBillingStatuses(long[])}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingStatuses_longArray_Success2()
        throws Exception {
        statusDAO.createFixedBillingStatuses(statuses);

        long[] ids = new long[3];
        ids[0] = statuses[0].getId();
        ids[1] = statuses[1].getId();
        ids[2] = statuses[2].getId();

        FixedBillingStatus[] result = statusDAO.getFixedBillingStatuses(ids);
        compareStatuses(statuses, result);
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#searchFixedBillingStatuses(Filter)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchFixedBillingStatuses_Filter_Success()
        throws Exception {
        statusDAO.createFixedBillingStatuses(statuses);

        Filter filter = statusDAO.getFixedBillingStatusFilterFactory()
                                 .createDescriptionFilter("desc0", StringMatchType.EXACT_MATCH);
        FixedBillingStatus[] result = statusDAO.searchFixedBillingStatuses(filter);
        assertEquals("The return result should have 1 value.", 1, result.length);
        assertEquals("The return result should be same.", result[0].getDescription(), statuses[0].getDescription());
        assertEquals("The return result should be same.", result[0].getCreationUser(), statuses[0].getCreationUser());
        assertEquals("The return result should be same.", result[0].getModificationUser(),
            statuses[0].getModificationUser());
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#getFixedBillingStatusFilterFactory()}</code> with success process.
     */
    public void testGetFixedBillingStatusFilterFactory_Success() {
        assertNotNull("The return value should not be null.", statusDAO.getFixedBillingStatusFilterFactory());
    }

    /**
     * Test the <code>{@link DbFixedBillingStatusDAO#getAllFixedBillingStatuses()}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllFixedBillingStatuses_Success()
        throws Exception {
        statusDAO.createFixedBillingStatuses(statuses);

        FixedBillingStatus[] result = statusDAO.getAllFixedBillingStatuses();
        compareStatuses(statuses, result);
    }

    /**
     * Compare two status array.
     *
     * @param statuses the original statuses.
     * @param result the result statuses.
     */
    private static void compareStatuses(FixedBillingStatus[] statuses, FixedBillingStatus[] result) {
        assertEquals("The return result should be same.", statuses.length, result.length);

        for (int i = 0; i < result.length; i++) {
            assertEquals("The return result should be same.", result[i].getDescription(),
                    statuses[i].getDescription());
            assertEquals("The return result should be same.", result[i].getCreationUser(),
                    statuses[i].getCreationUser());
            assertEquals("The return result should be same.", result[i].getModificationUser(),
                statuses[i].getModificationUser());
        }
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(DbFixedBillingStatusDAOTest.class);
    }
}
