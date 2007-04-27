/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import java.sql.Connection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryDAO;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManagerImpl;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryRejectReasonDAO;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusDAO;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingEntryDAO;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingEntryRejectReasonDAO;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingStatusDAO;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager;

/**
 * Failure test for
 * <code>{@link com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManagerImpl}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class FixedBillingEntryManagerImplTest extends TestCase {
    /**
     * <p>
     * Represents the FixedBillingEntryDAO for testing.
     * </p>
     */
    private FixedBillingEntryDAO fixedBillingEntryDAO;

    /**
     * <p>
     * Represents the FixedBillingEntryRejectReasonDAO for testing.
     * </p>
     */
    private FixedBillingEntryRejectReasonDAO reasonDAO;

    /**
     * <p>
     * Represents the RejectReasonManager for testing.
     * </p>
     */
    private RejectReasonManager rejectReasonManager;

    /**
     * <p>
     * Represents the FixedBillingEntryManagerImpl for testing.
     * </p>
     */
    private FixedBillingEntryManagerImpl impl;

    /**
     * <p>
     * Represents the connection for data setup.
     * </p>
     */
    private Connection connection;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        FailureTestHelper.loadConfigs();
        connection = FailureTestHelper.getConnection();
        FailureTestHelper.prepareData(connection);

        DBConnectionFactory factory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        AuditManager auditManager = new MockAuditManager();
        FixedBillingStatusDAO statusDAO = new DbFixedBillingStatusDAO(factory, "informix_connect_failure_2",
                "FixedBillingFailure", "com.topcoder.search.builder");
        fixedBillingEntryDAO = new DbFixedBillingEntryDAO(factory, "informix_connect_failure_2",
                "FixedBillingFailure", "com.topcoder.search.builder", auditManager, statusDAO);

        reasonDAO = new DbFixedBillingEntryRejectReasonDAO(factory, "informix_connect_failure_2",
                auditManager);
        rejectReasonManager = new MockRejectReasonManager("empty");

        impl = new FixedBillingEntryManagerImpl(fixedBillingEntryDAO, reasonDAO, rejectReasonManager);
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.clearDatabase(connection);
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        FailureTestHelper.releaseConfigs();
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#FixedBillingEntryManagerImpl(FixedBillingEntryDAO,
     * FixedBillingEntryRejectReasonDAO,RejectReasonManager)}</code>.
     */
    public void testConstructor_IAE() {
        try {
            new FixedBillingEntryManagerImpl(null, reasonDAO, rejectReasonManager);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new FixedBillingEntryManagerImpl(fixedBillingEntryDAO, null, rejectReasonManager);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new FixedBillingEntryManagerImpl(fixedBillingEntryDAO, reasonDAO, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#addRejectReasonToEntry(FixedBillingEntry,long,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodAddRejectReasonToEntry_IAE() throws Exception {
        try {
            impl.addRejectReasonToEntry(null, 1, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            impl.addRejectReasonToEntry(FailureTestHelper.createFixedBillingEntry(), -1, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#addRejectReasonToEntry(FixedBillingEntry,long,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodAddRejectReasonToEntry_DAE() throws Exception {
        try {
            impl.addRejectReasonToEntry(FailureTestHelper.createFixedBillingEntry(), 1, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingEntryManagerImpl#getFixedBillingEntry(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingEntry_IAE() throws Exception {
        try {
            impl.getFixedBillingEntry(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingEntryManagerImpl#getFixedBillingEntry(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingEntry_DAE() throws Exception {
        try {
            impl.getFixedBillingEntry(1);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#removeAllRejectReasonsFromEntry(FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRemoveAllRejectReasonsFromEntry_IAE() throws Exception {
        try {
            impl.removeAllRejectReasonsFromEntry(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#removeAllRejectReasonsFromEntry(FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRemoveAllRejectReasonsFromEntry_DAE() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(1);
            impl.removeAllRejectReasonsFromEntry(entry, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#createFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingEntries_IAE() throws Exception {
        try {
            impl.createFixedBillingEntries(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            impl.createFixedBillingEntries(new FixedBillingEntry[] { null }, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#createFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingEntries_DAE() throws Exception {
        try {
            impl.createFixedBillingEntries(new FixedBillingEntry[] { FailureTestHelper
                    .createFixedBillingEntry() }, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingEntryManagerImpl#getFixedBillingEntries(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingEntries_IAE() throws Exception {
        try {
            impl.getFixedBillingEntries(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            impl.getFixedBillingEntries(new long[] { -1 });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingEntryManagerImpl#getFixedBillingEntries(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingEntries_DAE() throws Exception {
        try {
            impl.getFixedBillingEntries(new long[] { 1 });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#deleteFixedBillingEntry(long,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingEntry_IAE() throws Exception {
        try {
            impl.deleteFixedBillingEntry(-1, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#deleteFixedBillingEntry(long,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingEntry_DAE() throws Exception {
        try {
            impl.deleteFixedBillingEntry(1, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#updateFixedBillingEntry(FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingEntry_IAE() throws Exception {
        try {
            impl.updateFixedBillingEntry(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#updateFixedBillingEntry(FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingEntry_DAE() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(1);
            entry.getFixedBillingStatus().setId(1);

            impl.updateFixedBillingEntry(entry, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#getAllRejectReasonsForEntry(FixedBillingEntry)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetAllRejectReasonsForEntry_IAE() throws Exception {
        try {
            impl.getAllRejectReasonsForEntry(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#getAllRejectReasonsForEntry(FixedBillingEntry)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetAllRejectReasonsForEntry_DAE() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(1);
            impl.getAllRejectReasonsForEntry(entry);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#createFixedBillingEntry(FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingEntry_IAE() throws Exception {
        try {
            impl.createFixedBillingEntry(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#createFixedBillingEntry(FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingEntry_DAE() throws Exception {
        try {
            impl.createFixedBillingEntry(FailureTestHelper.createFixedBillingEntry(), false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#updateFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingEntries_IAE() throws Exception {
        try {
            impl.updateFixedBillingEntries(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            impl.updateFixedBillingEntries(new FixedBillingEntry[] { null }, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#updateFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingEntries_DAE() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(1);
            entry.getFixedBillingStatus().setId(1);
            impl.updateFixedBillingEntries(new FixedBillingEntry[] { entry }, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingEntryManagerImpl#searchFixedBillingEntries(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchFixedBillingEntries_IAE() throws Exception {
        try {
            impl.searchFixedBillingEntries(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingEntryManagerImpl#searchFixedBillingEntries(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchFixedBillingEntries_DAE() throws Exception {
        try {
            impl.searchFixedBillingEntries(new EqualToFilter("dummy", new Long(1)));
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingEntryManagerImpl#getAllFixedBillingEntries()}</code>.
     */
    public void testMethodGetAllFixedBillingEntries_DAE() {
        try {
            impl.getAllFixedBillingEntries();
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#removeRejectReasonFromEntry(FixedBillingEntry,long,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRemoveRejectReasonFromEntry_IAE() throws Exception {
        try {
            impl.removeRejectReasonFromEntry(null, 1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            impl.removeRejectReasonFromEntry(FailureTestHelper.createFixedBillingEntry(), -1, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#removeRejectReasonFromEntry(FixedBillingEntry,long,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRemoveRejectReasonFromEntry_DAE() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(1);
            impl.removeRejectReasonFromEntry(entry, 1, true);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#deleteFixedBillingEntries(long[],boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingEntries_IAE() throws Exception {
        try {
            impl.deleteFixedBillingEntries(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            impl.deleteFixedBillingEntries(new long[] { -1 }, true);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerImpl#deleteFixedBillingEntries(long[],boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingEntries_DAE() throws Exception {
        try {
            impl.deleteFixedBillingEntries(new long[] { 1 }, true);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(FixedBillingEntryManagerImplTest.class);
    }
}
