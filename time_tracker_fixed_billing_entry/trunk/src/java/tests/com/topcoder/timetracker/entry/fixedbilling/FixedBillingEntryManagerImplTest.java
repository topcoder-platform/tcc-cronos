/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.MockAuditManager;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingEntryDAO;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingEntryRejectReasonDAO;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingStatusDAO;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;


/**
 * Unit test cases for <code>{@link FixedBillingEntryManagerImpl}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class FixedBillingEntryManagerImplTest extends TestCase {
    /** The DbFixedBillingEntryRejectReasonDAO instance for testing. */
    private DbFixedBillingEntryRejectReasonDAO reasonDAO;

    /** The DbFixedBillingStatusDAO instance for testing. */
    private DbFixedBillingStatusDAO statusDAO;

    /** The AuditManager instance for testing. */
    private AuditManager auditor;

    /** The DbFixedBillingEntryDAO instance for testing. */
    private DbFixedBillingEntryDAO entryDAO;

    /** The FixedBillingEntry array instance for testing. */
    private FixedBillingEntry[] entries;

    /** The RejectReasonManager array instance for testing. */
    private RejectReasonManager rejectReasonManager;

    /** The FixedBillingEntryManagerImpl array instance for testing. */
    private FixedBillingEntryManagerImpl fixedBillingEntryManagerImpl;

    /**
     * Set up the initial values.
     * @throws Exception to JUnit.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfigFile(TestHelper.DB_FACTORY_CONFIGE_FILE_NAME);
        TestHelper.loadConfigFile(TestHelper.DB_CONFIGE_FILE_NAME);

        DBConnectionFactory connFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory");
        String connName = "Informix_Connection_Producer";
        String idGen = "com.topcoder.timetracker.entry.fixedbilling";
        String searchBundleNamespace = "com.topcoder.search.builder";
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
            status.setModificationUser("modifyuser" + i);
            entries[i].setFixedBillingStatus(status);
            entries[i].setDescription("desc" + i);
            entries[i].setDate(new Date());
            entries[i].setCompanyId(100);
            entries[i].setCreationUser("user" + i);
            entries[i].setModificationUser("m-user" + i);
        }

        rejectReasonManager = new RejectReasonManager("test");
        fixedBillingEntryManagerImpl = new FixedBillingEntryManagerImpl(entryDAO, reasonDAO, rejectReasonManager);
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
     * Test the <code>{@link FixedBillingEntryManagerImpl#FixedBillingEntryManagerImpl(FixedBillingEntryDAO,
     * FixedBillingEntryRejectReasonDAO, RejectReasonManager)}</code> with null value. Should throw an
     * IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_DAODAOManager_Null1() throws Exception {
        try {
            entryDAO = null;
            new FixedBillingEntryManagerImpl(entryDAO, reasonDAO, rejectReasonManager);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#FixedBillingEntryManagerImpl(FixedBillingEntryDAO,
     * FixedBillingEntryRejectReasonDAO, RejectReasonManager)}</code> with null value. Should throw an
     * IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_DAODAOManager_Null2() throws Exception {
        try {
            reasonDAO = null;
            new FixedBillingEntryManagerImpl(entryDAO, reasonDAO, rejectReasonManager);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#FixedBillingEntryManagerImpl(FixedBillingEntryDAO,
     * FixedBillingEntryRejectReasonDAO, RejectReasonManager)}</code> with null value. Should throw an
     * IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_DAODAOManager_Null3() throws Exception {
        try {
            rejectReasonManager = null;
            new FixedBillingEntryManagerImpl(entryDAO, reasonDAO, rejectReasonManager);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#FixedBillingEntryManagerImpl(FixedBillingEntryDAO,
     * FixedBillingEntryRejectReasonDAO, RejectReasonManager)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_DAODAOManager_Success()
        throws Exception {
        fixedBillingEntryManagerImpl = new FixedBillingEntryManagerImpl(entryDAO, reasonDAO, rejectReasonManager);
        assertNotNull("Unable to create the instance.", fixedBillingEntryManagerImpl);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#createFixedBillingEntry(FixedBillingEntry, boolean)}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntry_FixedBillingEntryboolean_Success()
        throws Exception {
        fixedBillingEntryManagerImpl.createFixedBillingEntry(entries[0], false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#updateFixedBillingEntry(FixedBillingEntry, boolean)}</code>
     * with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntry_FixedBillingEntryboolean_Success()
        throws Exception {
        fixedBillingEntryManagerImpl.createFixedBillingEntry(entries[0], false);
        fixedBillingEntryManagerImpl.updateFixedBillingEntry(entries[0], false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#deleteFixedBillingEntry(long, boolean)}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntry_longlong_Success()
        throws Exception {
        fixedBillingEntryManagerImpl.createFixedBillingEntry(entries[0], false);
        fixedBillingEntryManagerImpl.deleteFixedBillingEntry(entries[0].getId(), false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#getFixedBillingEntry(long)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntry_long_Success()
        throws Exception {
        fixedBillingEntryManagerImpl.createFixedBillingEntry(entries[0], false);
        fixedBillingEntryManagerImpl.getFixedBillingEntry(entries[0].getId());
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#searchFixedBillingEntries(Filter)}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testSearchFixedBillingEntries_Filter_Success()
        throws Exception {
        fixedBillingEntryManagerImpl.createFixedBillingEntry(entries[0], false);
        fixedBillingEntryManagerImpl.searchFixedBillingEntries(
                fixedBillingEntryManagerImpl.getFixedBillingEntryFilterFactory().createDescriptionFilter("desc0",
                StringMatchType.EXACT_MATCH));
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#createFixedBillingEntries(FixedBillingEntry[],
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFixedBillingEntries_FixedBillingEntryboolean_Success()
        throws Exception {
        fixedBillingEntryManagerImpl.createFixedBillingEntries(entries, false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#updateFixedBillingEntries(FixedBillingEntry[],
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateFixedBillingEntries_FixedBillingEntryboolean_Success()
        throws Exception {
        fixedBillingEntryManagerImpl.createFixedBillingEntries(entries, false);
        fixedBillingEntryManagerImpl.updateFixedBillingEntries(entries, false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#deleteFixedBillingEntries(long[], boolean)}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testDeleteFixedBillingEntries_longboolean_Success()
        throws Exception {
        fixedBillingEntryManagerImpl.createFixedBillingEntries(entries, false);
        fixedBillingEntryManagerImpl.deleteFixedBillingEntries(new long[] {entries[0].getId(), entries[1].getId()},
            false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#getFixedBillingEntries(long[])}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntries_long_Success()
        throws Exception {
        fixedBillingEntryManagerImpl.createFixedBillingEntries(entries, false);
        fixedBillingEntryManagerImpl.getFixedBillingEntries(new long[] {entries[0].getId(), entries[1].getId()});
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#addRejectReasonToEntry(FixedBillingEntry, long,
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testAddRejectReasonToEntry_FixedBillingEntrylongboolean_Success()
        throws Exception {
        fixedBillingEntryManagerImpl.createFixedBillingEntries(entries, false);
        fixedBillingEntryManagerImpl.addRejectReasonToEntry(entries[0], 100, false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#removeRejectReasonFromEntry(FixedBillingEntry, long,
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveRejectReasonFromEntry_FixedBillingEntrylongboolean_Success()
        throws Exception {
        fixedBillingEntryManagerImpl.createFixedBillingEntries(entries, false);
        fixedBillingEntryManagerImpl.addRejectReasonToEntry(entries[0], 100, false);
        fixedBillingEntryManagerImpl.removeRejectReasonFromEntry(entries[0], 100, false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#removeAllRejectReasonsFromEntry(FixedBillingEntry,
     * boolean)}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveAllRejectReasonsFromEntry_FixedBillingEntryboolean_Success()
        throws Exception {
        fixedBillingEntryManagerImpl.createFixedBillingEntries(entries, false);
        fixedBillingEntryManagerImpl.addRejectReasonToEntry(entries[0], 100, false);
        fixedBillingEntryManagerImpl.removeAllRejectReasonsFromEntry(entries[0], false);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#getAllRejectReasonsForEntry(FixedBillingEntry)}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllRejectReasonsForEntry_FixedBillingEntry_Success()
        throws Exception {
        fixedBillingEntryManagerImpl.createFixedBillingEntries(entries, false);
        fixedBillingEntryManagerImpl.addRejectReasonToEntry(entries[0], 100, false);
        fixedBillingEntryManagerImpl.getAllRejectReasonsForEntry(entries[0]);
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#getAllFixedBillingEntries()}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllFixedBillingEntries_Success()
        throws Exception {
        fixedBillingEntryManagerImpl.createFixedBillingEntries(entries, false);
        fixedBillingEntryManagerImpl.addRejectReasonToEntry(entries[0], 100, false);
        fixedBillingEntryManagerImpl.getAllFixedBillingEntries();
    }

    /**
     * Test the <code>{@link FixedBillingEntryManagerImpl#getFixedBillingEntryFilterFactory()}</code> with success
     * process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetFixedBillingEntryFilterFactory_Success()
        throws Exception {
        assertNotNull("Unable to get the factory.", fixedBillingEntryManagerImpl.getFixedBillingEntryFilterFactory());
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(FixedBillingEntryManagerImplTest.class);
    }
}
