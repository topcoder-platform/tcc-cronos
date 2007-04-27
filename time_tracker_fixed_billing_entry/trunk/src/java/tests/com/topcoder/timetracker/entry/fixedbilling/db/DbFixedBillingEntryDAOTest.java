/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.MockAuditManager;
import com.topcoder.timetracker.entry.fixedbilling.BatchOperationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;
import com.topcoder.timetracker.entry.fixedbilling.TestHelper;
import com.topcoder.timetracker.entry.fixedbilling.UnrecognizedEntityException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;


/**
 * Unit test cases for <code>{@link DbFixedBillingEntryDAO}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class DbFixedBillingEntryDAOTest extends TestCase {
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

    /** The AuditManager instance for testing. */
    private AuditManager auditor;

    /** The DbFixedBillingEntryDAO instance for testing. */
    private DbFixedBillingEntryDAO entryDAO;

    /** The FixedBillingEntry array instance for testing. */
    private FixedBillingEntry[] entries;

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
        auditor = new MockAuditManager(false);
        entryDAO = new DbFixedBillingEntryDAO(connFactory, connName, idGen, searchBundleNamespace, auditor,
                statusDAO);
        entries = new FixedBillingEntry[3];

        for (int i = 0; i < entries.length; i++) {
            entries[i] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc" + i);
            status.setCreationUser("user" + i);
            status.setCreationDate(new Date());
            status.setModificationDate(new Date());
            status.setModificationUser("modifyuser" + i);
            entries[i].setFixedBillingStatus(status);
            entries[i].setDescription("desc" + i);
            entries[i].setDate(new Date());
            entries[i].setCompanyId(100);
            entries[i].setClientId(1);
            entries[i].setProjectId(1);
            entries[i].setCreationUser("user" + i);
            entries[i].setCreationDate(new Date());
            entries[i].setModificationDate(new Date());
            entries[i].setModificationUser("m-user" + i);
        }
        TestHelper.executeSQL("delete from fb_reject_reason");
        TestHelper.executeSQL("delete from fix_bill_entry");
        TestHelper.executeSQL("delete from fix_bill_status");
    }

    /**
     * Clear up the config.
     *
     * @throws Exception to junit.
     */
    public void tearDown() throws Exception {
        TestHelper.executeSQL("delete from fb_reject_reason");
        TestHelper.executeSQL("delete from fix_bill_entry");
        TestHelper.executeSQL("delete from fix_bill_status");
        TestHelper.removeNamespaces();
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#DbFixedBillingEntryDAO(DBConnectionFactory, String, String, String,
     * AuditManager, FixedBillingStatusDAO, boolean)}</code> with null connection factory. Should throw an
     * IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAuditStatusDAOBatch_NullFactory()
        throws Exception {
        try {
            connFactory = null;
            new DbFixedBillingEntryDAO(connFactory, connName, idGen, searchBundleNamespace, auditor, statusDAO);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#DbFixedBillingEntryDAO(DBConnectionFactory, String, String, String,
     * AuditManager, FixedBillingStatusDAO, boolean)}}</code> with empty connection name. Should throw an
     * IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAuditStatusDAOBatch_EmptyConnName()
        throws Exception {
        try {
            connName = " ";
            new DbFixedBillingEntryDAO(connFactory, connName, idGen, searchBundleNamespace, auditor, statusDAO);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#DbFixedBillingEntryDAO(DBConnectionFactory, String, String, String,
     * AuditManager, FixedBillingStatusDAO, boolean)}}</code> with empty idGen name. Should throw an
     * IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAuditStatusDAOBatch_EmptyIdGen()
        throws Exception {
        try {
            idGen = " ";
            new DbFixedBillingEntryDAO(connFactory, connName, idGen, searchBundleNamespace, auditor, statusDAO);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#DbFixedBillingEntryDAO(DBConnectionFactory, String, String, String,
     * AuditManager, FixedBillingStatusDAO, boolean)}</code> with empty search name space. Should throw an
     * IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAuditStatusDAOBatch_EmptySearch()
        throws Exception {
        try {
            searchBundleNamespace = " ";
            new DbFixedBillingEntryDAO(connFactory, connName, idGen, searchBundleNamespace, auditor, statusDAO);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#DbFixedBillingEntryDAO(DBConnectionFactory, String, String, String,
     * AuditManager, FixedBillingStatusDAO, boolean)}}</code> with null audit manager. Should throw an
     * IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAuditStatusDAOBatch_NullAudit()
        throws Exception {
        try {
            auditor = null;
            new DbFixedBillingEntryDAO(connFactory, connName, idGen, searchBundleNamespace, auditor, statusDAO);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#DbFixedBillingEntryDAO(DBConnectionFactory, String, String, String,
     * AuditManager, FixedBillingStatusDAO, boolean)}}</code> with null status DAO. Should throw an
     * IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAuditStatusDAOBatch_NullDAO()
        throws Exception {
        try {
            statusDAO = null;
            new DbFixedBillingEntryDAO(connFactory, connName, idGen, searchBundleNamespace, auditor, statusDAO);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#DbFixedBillingEntryDAO(DBConnectionFactory, String, String, String,
     * AuditManager, FixedBillingStatusDAO, boolean)}}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAuditStatusDAOBatch_Success()
        throws Exception {
        entryDAO = new DbFixedBillingEntryDAO(connFactory, connName, idGen, searchBundleNamespace, auditor,
                statusDAO);
        assertNotNull("Unable to create the instance.", entryDAO);
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with null array. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_Arrayboolean_NullArray()
        throws Exception {
        try {
            entries = null;
            entryDAO.createFixedBillingEntries(entries, true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with array which contains null. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_Arrayboolean_ContainNull()
        throws Exception {
        try {
            entries[1] = null;
            entryDAO.createFixedBillingEntries(entries, true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with wrong values. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_Arrayboolean_MissingKey1()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc0");
            status.setCreationUser("user0");
            status.setModificationUser("modifyuser0");
            entries[0].setId(10);
            entries[0].setFixedBillingStatus(status);
            entries[0].setDescription("desc0");
            entries[0].setDate(new Date());
            entries[0].setCompanyId(100);
            entries[0].setCreationUser("user0");
            entries[0].setModificationUser("m-user0");
            entryDAO.createFixedBillingEntries(entries, true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with  wrong values. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_Arrayboolean_MissingKey2()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();
            entries[0].setDescription("desc0");
            entries[0].setDate(new Date());
            entries[0].setCompanyId(100);
            entries[0].setCreationUser("user0");
            entries[0].setCreationDate(new Date());
            entries[0].setModificationDate(new Date());
            entries[0].setModificationUser("m-user0");
            entryDAO.createFixedBillingEntries(entries, true);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with wrong values. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_Arrayboolean_MissingKey3()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc0");
            status.setCreationUser("user0");
            status.setModificationUser("modifyuser0");
            entries[0].setFixedBillingStatus(status);
            entries[0].setDate(new Date());
            entries[0].setCompanyId(100);
            entries[0].setCreationUser("user0");
            entries[0].setModificationUser("m-user0");
            entryDAO.createFixedBillingEntries(entries, true);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with wrong values. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_Arrayboolean_MissingKey4()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc0");
            status.setCreationUser("user0");
            status.setModificationUser("modifyuser0");
            entries[0].setFixedBillingStatus(status);
            entries[0].setDescription("desc0");
            entries[0].setCompanyId(100);
            entries[0].setCreationUser("user0");
            entries[0].setModificationUser("m-user0");
            entryDAO.createFixedBillingEntries(entries, true);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with wrong values. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_Arrayboolean_MissingKey5()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc0");
            status.setCreationUser("user0");
            status.setModificationUser("modifyuser0");
            entries[0].setFixedBillingStatus(status);
            entries[0].setDescription("desc0");
            entries[0].setDate(new Date());
            entries[0].setCompanyId(100);
            entries[0].setModificationUser("m-user0");
            entryDAO.createFixedBillingEntries(entries, true);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with wrong values. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_Arrayboolean_MissingKey6()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc0");
            status.setCreationUser("user0");
            status.setModificationUser("modifyuser0");
            entries[0].setFixedBillingStatus(status);
            entries[0].setDescription("desc0");
            entries[0].setDate(new Date());
            entries[0].setCompanyId(100);
            entries[0].setCreationUser("user0");
            entryDAO.createFixedBillingEntries(entries, true);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with wrong values. Should throw an DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_Arrayboolean_ForeignKey()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc0");
            status.setCreationUser("user0");
            status.setCreationDate(new Date());
            status.setModificationDate(new Date());
            status.setModificationUser("modifyuser0");
            entries[0].setFixedBillingStatus(status);
            entries[0].setDescription("desc0");
            entries[0].setDate(new Date());
            entries[0].setCompanyId(100);
            entries[0].setInvoiceId(123);
            entries[0].setCreationUser("user0");
            entries[0].setCreationDate(new Date());
            entries[0].setModificationDate(new Date());
            entries[0].setModificationUser("m-user0");
            entryDAO.createFixedBillingEntries(entries, true);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_Arrayboolean_Success1()
        throws Exception {
        entryDAO = new DbFixedBillingEntryDAO(connFactory, connName, idGen, searchBundleNamespace, auditor,
                statusDAO);
        entryDAO.createFixedBillingEntries(entries, true);

        FixedBillingEntry[] results = entryDAO.getAllFixedBillingEntries();
        assertEquals("The return result should be same.", entries.length, results.length);

        for (int i = 0; i < entries.length; i++) {
            assertEquals("The return result should be same.", entries[i].getDescription(), results[i].getDescription());
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_Arrayboolean_Success2()
        throws Exception {
        entryDAO.createFixedBillingEntries(entries, false);

        FixedBillingEntry[] results = entryDAO.getAllFixedBillingEntries();
        assertEquals("The return result should be same.", entries.length, results.length);

        for (int i = 0; i < entries.length; i++) {
            assertEquals("The return result should be same.", entries[i].getDescription(), results[i].getDescription());
        }
    }



    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with null array. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_Arrayboolean_Null()
        throws Exception {
        try {
            entries = null;
            entryDAO.updateFixedBillingEntries(entries, true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with array which contains null. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_Arrayboolean_ContainNull()
        throws Exception {
        try {
            entries[1] = null;
            entryDAO.updateFixedBillingEntries(entries, true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with wrong values. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_Arrayboolean_MissingKey1()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc0");
            status.setCreationUser("user0");
            status.setModificationUser("modifyuser0");
            entries[0].setId(-1);
            entries[0].setFixedBillingStatus(status);
            entries[0].setDescription("desc0");
            entries[0].setDate(new Date());
            entries[0].setCompanyId(100);
            entries[0].setCreationDate(new Date());
            entries[0].setCreationUser("user0");
            entries[0].setModificationUser("m-user0");
            entryDAO.updateFixedBillingEntries(entries, true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with wrong values. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_Arrayboolean_MissingKey2()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();
            entries[0].setId(10);

            entries[0].setDescription("desc0");
            entries[0].setDate(new Date());
            entries[0].setCompanyId(100);
            entries[0].setCreationDate(new Date());
            entries[0].setCreationUser("user0");
            entries[0].setModificationUser("m-user0");
            entryDAO.updateFixedBillingEntries(entries, true);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with wrong values. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_Arrayboolean_MissingKey3()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc0");
            status.setCreationUser("user0");
            status.setModificationUser("modifyuser0");
            entries[0].setId(10);
            entries[0].setFixedBillingStatus(status);
            entries[0].setDate(new Date());
            entries[0].setCompanyId(100);
            entries[0].setCreationUser("user0");
            entries[0].setModificationUser("m-user0");
            entries[0].setCreationDate(new Date());
            entries[0].setModificationDate(new Date());
            entryDAO.updateFixedBillingEntries(entries, true);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with wrong values. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_Arrayboolean_MissingKey4()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc0");
            status.setCreationUser("user0");
            status.setModificationUser("modifyuser0");
            entries[0].setId(10);
            entries[0].setFixedBillingStatus(status);
            entries[0].setDescription("desc0");
            entries[0].setCompanyId(100);
            entries[0].setCreationUser("user0");
            entries[0].setCreationDate(new Date());
            entries[0].setModificationUser("m-user0");
            entries[0].setModificationDate(new Date());
            entryDAO.updateFixedBillingEntries(entries, true);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with wrong values. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_Arrayboolean_MissingKey5()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc0");
            status.setCreationUser("user0");
            status.setModificationUser("modifyuser0");
            entries[0].setId(10);
            entries[0].setFixedBillingStatus(status);
            entries[0].setDescription("desc0");
            entries[0].setDate(new Date());
            entries[0].setCompanyId(100);
            entries[0].setCreationDate(new Date());
            entries[0].setModificationUser("m-user0");
            entries[0].setModificationDate(new Date());
            entryDAO.updateFixedBillingEntries(entries, true);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with wrong values. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_Arrayboolean_MissingKey6()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc0");
            status.setCreationUser("user0");
            status.setModificationUser("modifyuser0");
            entries[0].setId(10);
            entries[0].setFixedBillingStatus(status);
            entries[0].setDescription("desc0");
            entries[0].setDate(new Date());
            entries[0].setCompanyId(100);
            entries[0].setCreationDate(new Date());
            entries[0].setCreationUser("user0");
            entries[0].setModificationDate(new Date());
            entryDAO.updateFixedBillingEntries(entries, true);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with wrong values. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_Arrayboolean_MissingKey7()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc0");
            status.setCreationUser("user0");
            status.setModificationUser("modifyuser0");
            entries[0].setId(10);
            entries[0].setFixedBillingStatus(status);
            entries[0].setDescription("desc0");
            entries[0].setCompanyId(100);
            entries[0].setCreationUser("user0");
            entries[0].setCreationDate(new Date());
            entries[0].setModificationDate(new Date());
            entries[0].setModificationUser("m-user0");
            entryDAO.updateFixedBillingEntries(entries, true);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with wrong values. Should throw a DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_Arrayboolean_ForeignKey()
        throws Exception {
        try {
            entries = new FixedBillingEntry[1];
            entries[0] = new FixedBillingEntry();

            FixedBillingStatus status = new FixedBillingStatus();
            status.setDescription("desc0");
            status.setCreationUser("user0");
            status.setModificationUser("modifyuser0");
            entries[0].setId(10);
            entries[0].setFixedBillingStatus(status);
            entries[0].setDescription("desc0");
            entries[0].setDate(new Date());
            entries[0].setCompanyId(100);
            entries[0].setInvoiceId(123);
            entries[0].setCreationUser("user0");
            entries[0].setCreationDate(new Date());
            entries[0].setModificationDate(new Date());
            entries[0].setModificationUser("m-user0");
            entryDAO.updateFixedBillingEntries(entries, true);
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_Arrayboolean_Success1()
        throws Exception {
        entryDAO = new DbFixedBillingEntryDAO(connFactory, connName, idGen, searchBundleNamespace, auditor,
                statusDAO);
        entryDAO.createFixedBillingEntries(entries, true);

        for (int i = 0; i < entries.length; i++) {
            entries[i].setDescription("new desc");
        }

        entryDAO.updateFixedBillingEntries(entries, true);

        FixedBillingEntry[] results = entryDAO.getAllFixedBillingEntries();
        assertEquals("The return result should be same.", entries.length, results.length);

        for (int i = 0; i < entries.length; i++) {
            assertEquals("The return result should be same.", entries[i].getDescription(), results[i].getDescription());
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[], boolean)}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_Arrayboolean_Success2()
        throws Exception {
        entryDAO.createFixedBillingEntries(entries, true);

        for (int i = 0; i < entries.length; i++) {
            entries[i].setDescription("new desc");
        }

        entryDAO.updateFixedBillingEntries(entries, false);

        FixedBillingEntry[] results = entryDAO.getAllFixedBillingEntries();
        assertEquals("The return result should be same.", entries.length, results.length);

        for (int i = 0; i < entries.length; i++) {
            assertEquals("The return result should be same.", entries[i].getDescription(), results[i].getDescription());
        }
    }


    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#deleteFixedBillingEntries(long[], boolean)}</code> with null array.
     * Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntries_Arrayboolean_Null()
        throws Exception {
        try {
            entryDAO.deleteFixedBillingEntries(null, true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#deleteFixedBillingEntries(long[], boolean)}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntries_Arrayboolean_UnableToDelete()
        throws Exception {
        entryDAO.createFixedBillingEntries(entries, true);

        long[] ids = new long[] {entries[0].getId(), entries[1].getId(), entries[2].getId()};
        try {
            TestHelper.executeSQL("insert into fb_reject_reason(fix_bill_entry_id, reject_reason_id, creation_date, "
                    + "creation_user,modification_date,modification_user) values (" + entries[0].getId()
                    + ",100,current,"
                    + "'user',current,'muser')");
            entryDAO.deleteFixedBillingEntries(ids, false);
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#deleteFixedBillingEntries(long[], boolean)}</code> with invalid
     * value. Should throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntries_Arrayboolean_InvalidValue()
        throws Exception {
        try {
            entryDAO.deleteFixedBillingEntries(new long[] {-1}, true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#deleteFixedBillingEntries(long[], boolean)}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntries_Arrayboolean_Success1()
        throws Exception {
        entryDAO = new DbFixedBillingEntryDAO(connFactory, connName, idGen, searchBundleNamespace, auditor,
                statusDAO);
        entryDAO.createFixedBillingEntries(entries, true);

        long[] ids = new long[] {entries[0].getId(), entries[1].getId(), entries[2].getId()};
        entryDAO.deleteFixedBillingEntries(ids, true);

        FixedBillingEntry[] results = entryDAO.getAllFixedBillingEntries();
        assertEquals("The return result should be same.", 0, results.length);
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#deleteFixedBillingEntries(long[], boolean)}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntries_Arrayboolean_Success3()
        throws Exception {
        entryDAO.createFixedBillingEntries(entries, true);

        long[] ids = new long[] {entries[0].getId(), entries[1].getId(), entries[2].getId()};
        entryDAO.deleteFixedBillingEntries(ids, false);

        FixedBillingEntry[] results = entryDAO.getAllFixedBillingEntries();
        assertEquals("The return result should be same.", 0, results.length);
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#getFixedBillingEntries(long[])}</code> with null value. Should
     * throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntries_Array_Null()
        throws Exception {
        try {
            entryDAO.getFixedBillingEntries(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#getFixedBillingEntries(long[])}</code> with invalid value. Should
     * throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntries_Array_InvalidValue()
        throws Exception {
        try {
            entryDAO.getFixedBillingEntries(new long[] {-1});
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#getFixedBillingEntries(long[])}</code> with invalid long value.
     * Should throw a BatchOperationException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntries_Array_NotExist()
        throws Exception {
        try {
            entryDAO.getFixedBillingEntries(new long[] {10});
            fail("Should throw a BatchOperationException here.");
        } catch (BatchOperationException boe) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#getFixedBillingEntries(long[])}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntries_Array_Success1()
        throws Exception {
        entryDAO = new DbFixedBillingEntryDAO(connFactory, connName, idGen, searchBundleNamespace, auditor,
                statusDAO);
        entryDAO.createFixedBillingEntries(entries, true);

        long[] ids = new long[] {entries[0].getId(), entries[1].getId(), entries[2].getId()};
        FixedBillingEntry[] results = entryDAO.getFixedBillingEntries(ids);
        assertEquals("The return result should be same.", entries.length, results.length);

        for (int i = 0; i < entries.length; i++) {
            assertEquals("The return result should be same.", entries[i].getDescription(),
                    results[i].getDescription());
        }
    }



    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#getFixedBillingEntries(long[])}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntries_Array_Success3()
        throws Exception {
        entryDAO.createFixedBillingEntries(entries, true);

        long[] ids = new long[] {entries[0].getId(), entries[1].getId(), entries[2].getId()};
        FixedBillingEntry[] results = entryDAO.getFixedBillingEntries(ids);
        assertEquals("The return result should be same.", entries.length, results.length);

        for (int i = 0; i < entries.length; i++) {
            assertEquals("The return result should be same.", entries[i].getDescription(), results[i].getDescription());
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#searchFixedBillingEntries(Filter)}</code> with null filter. Should
     * throw an  IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchFixedBillingEntries_Filter_Null()
        throws Exception {
        try {
            entryDAO.searchFixedBillingEntries(null);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#searchFixedBillingEntries(Filter)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchFixedBillingEntries_Filter_Success()
        throws Exception {
        entryDAO.createFixedBillingEntries(entries, true);

        Filter filter = entryDAO.getFixedBillingEntryFilterFactory()
                                .createDescriptionFilter("desc0", StringMatchType.EXACT_MATCH);
        FixedBillingEntry[] results = entryDAO.searchFixedBillingEntries(filter);
        assertEquals("The return result should be same.", 1, results.length);
        assertEquals("The return result should be same.", "desc0", results[0].getDescription());
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#getFixedBillingEntryFilterFactory()}</code> with success process.
     */
    public void testGetFixedBillingEntryFilterFactory_Success() {
        assertNotNull("The return value should not be null.", entryDAO.getFixedBillingEntryFilterFactory());
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryDAO#getAllFixedBillingEntries()}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllFixedBillingEntries_Success()
        throws Exception {
        entryDAO.createFixedBillingEntries(entries, true);

        FixedBillingEntry[] results = entryDAO.getAllFixedBillingEntries();
        assertEquals("The return result should be same.", entries.length, results.length);

        for (int i = 0; i < entries.length; i++) {
            assertEquals("The return result should be same.", entries[i].getDescription(), results[i].getDescription());
        }
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(DbFixedBillingEntryDAOTest.class);
    }
}
