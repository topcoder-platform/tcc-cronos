/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.MockAuditManager;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.TestHelper;
import com.topcoder.timetracker.entry.fixedbilling.UnrecognizedEntityException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;


/**
 * Unit test cases for <code>{@link DbFixedBillingEntryRejectReasonDAO}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class DbFixedBillingEntryRejectReasonDAOTest extends TestCase {
    /** The DBConnectionFactory instance for testing. */
    private DBConnectionFactory connFactory;

    /** The String instance for testing. */
    private String connName;

    /** The DbFixedBillingEntryRejectReasonDAO instance for testing. */
    private DbFixedBillingEntryRejectReasonDAO reasonDAO;

    /** The DbFixedBillingStatusDAO instance for testing. */
    private DbFixedBillingStatusDAO statusDAO;

    /** The AuditManager instance for testing. */
    private AuditManager auditor;

    /** The DbFixedBillingEntryDAO instance for testing. */
    private DbFixedBillingEntryDAO entryDAO;

    /** The String instance for testing. */
    private String idGen;

    /** The String instance for testing. */
    private String searchBundleNamespace;


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
        auditor = new MockAuditManager(false);
        reasonDAO = new DbFixedBillingEntryRejectReasonDAO(connFactory, connName, auditor);
        statusDAO = new DbFixedBillingStatusDAO(connFactory, connName, idGen, searchBundleNamespace);
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
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#DbFixedBillingEntryRejectReasonDAO(DBConnectionFactory,
     * String, AuditManager)}</code> with null connection factory. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryString_NullFactory()
        throws Exception {
        try {
            connFactory = null;
            reasonDAO = new DbFixedBillingEntryRejectReasonDAO(connFactory, connName, auditor);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#DbFixedBillingEntryRejectReasonDAO(DBConnectionFactory,
     * String, AuditManager)}</code> with empty connection name. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryString_EmptyConnName()
        throws Exception {
        try {
            connName = " ";
            reasonDAO = new DbFixedBillingEntryRejectReasonDAO(connFactory, connName, auditor);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#DbFixedBillingEntryRejectReasonDAO(DBConnectionFactory,
     * String, AuditManager)}</code> with null audit manager. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryString_NullAudit()
        throws Exception {
        try {
            auditor = null;
            reasonDAO = new DbFixedBillingEntryRejectReasonDAO(connFactory, connName, auditor);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#DbFixedBillingEntryRejectReasonDAO(DBConnectionFactory,
     * String, AuditManager)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryString_Success()
        throws Exception {
        reasonDAO = new DbFixedBillingEntryRejectReasonDAO(connFactory, connName, auditor);
        assertNotNull("Unable to create the instance.", reasonDAO);
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#addRejectReasonToEntry(long, FixedBillingEntry,
     * boolean)}</code> with invalid long value. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testAddRejectReasonToEntry_longlongboolean_InvalidLongValue()
        throws Exception {
        try {
            reasonDAO.addRejectReasonToEntry(-1, entries[0], true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#addRejectReasonToEntry(long, FixedBillingEntry,
     * boolean)}</code> with null entry. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testAddRejectReasonToEntry_longlongboolean_NullEntry()
        throws Exception {
        try {
            reasonDAO.addRejectReasonToEntry(100, null, true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#addRejectReasonToEntry(long, FixedBillingEntry,
     * boolean)}</code> with wrong entry id. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testAddRejectReasonToEntry_longlongboolean_InvalidEntryId()
        throws Exception {
        try {
            reasonDAO.addRejectReasonToEntry(100, new FixedBillingEntry(), true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#addRejectReasonToEntry(long, FixedBillingEntry,
     * boolean)}</code> with null modify user. Should throw a IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testAddRejectReasonToEntry_longlongboolean_NullModifyUser()
        throws Exception {
        try {
            FixedBillingEntry entry = new FixedBillingEntry();
            entry.setId(100);
            reasonDAO.addRejectReasonToEntry(100, entry, true);
            fail("Should throw a IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#addRejectReasonToEntry(long, FixedBillingEntry,
     * boolean)}</code> with wrong id. Should throw an UnrecognizedEntityException here.
     *
     * @throws Exception to JUnit.
     */
    public void testAddRejectReasonToEntry_longlongboolean_NotExist()
        throws Exception {
        try {
            FixedBillingEntry entry = new FixedBillingEntry();
            entry.setId(100);
            entry.setModificationUser("m user");
            reasonDAO.addRejectReasonToEntry(100, entry, true);
            fail("Should throw an UnrecognizedEntityException here.");
        } catch (UnrecognizedEntityException uee) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#addRejectReasonToEntry(long, FixedBillingEntry,
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testAddRejectReasonToEntry_longlongboolean_Success()
        throws Exception {
        entryDAO.createFixedBillingEntries(entries, true);
        reasonDAO.addRejectReasonToEntry(100, entries[0], true);

        long[] result = reasonDAO.getAllRejectReasonsForEntry(entries[0].getId());
        assertEquals("The return value should be same.", 100, result[0]);
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#removeRejectReasonFromEntry(long, FixedBillingEntry,
     * boolean)}</code> with wrong long value. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveRejectReasonFromEntry_longlongboolean_InvalidLong()
        throws Exception {
        try {
            reasonDAO.removeRejectReasonFromEntry(-1, entries[0], true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#removeRejectReasonFromEntry(long, FixedBillingEntry,
     * boolean)}</code> with null entry. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveRejectReasonFromEntry_longlongboolean_NullEntry()
        throws Exception {
        try {
            reasonDAO.removeRejectReasonFromEntry(100, null, true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#removeRejectReasonFromEntry(long, FixedBillingEntry,
     * boolean)}</code> with wrong entry id. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveRejectReasonFromEntry_longlongboolean_InvalidEntryId()
        throws Exception {
        try {
            reasonDAO.removeRejectReasonFromEntry(100, new FixedBillingEntry(), true);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#removeRejectReasonFromEntry(long, FixedBillingEntry,
     * boolean)}</code> with wrong id. Should throw an UnrecognizedEntityException here.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveRejectReasonFromEntry_longlongboolean_NotExist()
        throws Exception {
        try {
            FixedBillingEntry entry = new FixedBillingEntry();
            entry.setId(100);
            entry.setModificationUser("m user");
            reasonDAO.removeRejectReasonFromEntry(100, entry, true);
            fail("Should throw an UnrecognizedEntityException here.");
        } catch (UnrecognizedEntityException uee) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#removeRejectReasonFromEntry(long, FixedBillingEntry,
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveRejectReasonFromEntry_longlongboolean_Success()
        throws Exception {
        entryDAO.createFixedBillingEntries(entries, true);
        reasonDAO.addRejectReasonToEntry(100, entries[0], true);
        reasonDAO.removeRejectReasonFromEntry(100, entries[0], true);

        long[] result = reasonDAO.getAllRejectReasonsForEntry(entries[0].getId());
        assertEquals("The return value should be same.", 0, result.length);
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#getAllRejectReasonsForEntry(long)}</code> with wrong
     * id. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllRejectReasonsForEntry_long_InvalidId()
        throws Exception {
        try {
            reasonDAO.getAllRejectReasonsForEntry(-1);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#getAllRejectReasonsForEntry(long)}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllRejectReasonsForEntry_long_Success()
        throws Exception {
        entryDAO.createFixedBillingEntries(entries, true);
        reasonDAO.addRejectReasonToEntry(100, entries[0], true);
        reasonDAO.addRejectReasonToEntry(100, entries[1], true);
        reasonDAO.addRejectReasonToEntry(100, entries[2], true);

        long[] result = reasonDAO.getAllRejectReasonsForEntry(entries[0].getId());
        assertEquals("The return value should be same.", 100, result[0]);
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#removeAllRejectReasonsFromEntry(FixedBillingEntry,
     * boolean)}</code> with null value. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveAllRejectReasonsFromEntry_longboolean_Null()
        throws Exception {
        try {
            reasonDAO.removeAllRejectReasonsFromEntry(null, false);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#removeAllRejectReasonsFromEntry(FixedBillingEntry,
     * boolean)}</code> with wrong entry id. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveAllRejectReasonsFromEntry_longboolean_InvalidId()
        throws Exception {
        try {
            reasonDAO.removeAllRejectReasonsFromEntry(new FixedBillingEntry(), false);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link DbFixedBillingEntryRejectReasonDAO#removeAllRejectReasonsFromEntry(FixedBillingEntry,
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveAllRejectReasonsFromEntry_longboolean_Success()
        throws Exception {
        entryDAO.createFixedBillingEntries(entries, true);
        reasonDAO.addRejectReasonToEntry(100, entries[0], true);
        reasonDAO.addRejectReasonToEntry(100, entries[1], true);
        reasonDAO.addRejectReasonToEntry(100, entries[2], true);
        reasonDAO.removeAllRejectReasonsFromEntry(entries[0], true);

        long[] result = reasonDAO.getAllRejectReasonsForEntry(entries[0].getId());
        assertEquals("The return value should be same.", 0, result.length);
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(DbFixedBillingEntryRejectReasonDAOTest.class);
    }
}
