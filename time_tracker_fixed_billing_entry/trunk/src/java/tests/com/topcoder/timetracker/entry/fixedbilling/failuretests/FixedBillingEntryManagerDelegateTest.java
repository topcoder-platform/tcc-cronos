/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import java.sql.Connection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntryManagerDelegate;

/**
 * Failure test for
 * <code>{@link com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntryManagerDelegate}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class FixedBillingEntryManagerDelegateTest extends TestCase {
    /**
     * <p>
     * Represents the connection for data setup.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Represents the FixedBillingEntryManagerDelegate for testing.
     * </p>
     */
    private FixedBillingEntryManagerDelegate delegate;

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
        FailureTestHelper.deployEJB();
        delegate = new FixedBillingEntryManagerDelegate("com.topcoder.timetracker.entry.fb.ejb.failure");
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
     * <code>{@link FixedBillingEntryManagerDelegate#FixedBillingEntryManagerDelegate(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_IAE() throws Exception {
        try {
            new FixedBillingEntryManagerDelegate(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new FixedBillingEntryManagerDelegate(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#FixedBillingEntryManagerDelegate(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_CFE() throws Exception {
        try {
            new FixedBillingEntryManagerDelegate("unknown");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expect
        }
        try {
            new FixedBillingEntryManagerDelegate("empty");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expect
        }
        try {
            new FixedBillingEntryManagerDelegate("wrong");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#createFixedBillingEntry(FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingEntry_DAE() throws Exception {
        try {
            delegate.createFixedBillingEntry(FailureTestHelper.createFixedBillingEntry(), false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.createFixedBillingEntry(FailureTestHelper.createFixedBillingEntry(), false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#createFixedBillingEntry(FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingEntry_IAE() throws Exception {
        try {
            delegate.createFixedBillingEntry(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#createFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodCreateFixedBillingEntries_DAE() throws Exception {
        try {
            delegate.createFixedBillingEntries(new FixedBillingEntry[] { FailureTestHelper
                    .createFixedBillingEntry() }, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.createFixedBillingEntries(new FixedBillingEntry[] { FailureTestHelper
                    .createFixedBillingEntry() }, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#createFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodCreateFixedBillingEntries_IAE() throws Exception {
        try {
            delegate.createFixedBillingEntries(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.createFixedBillingEntries(new FixedBillingEntry[] { null }, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#updateFixedBillingEntry(FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateFixedBillingEntry_DAE() throws Exception {
        FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
        entry.setId(1);
        entry.getFixedBillingStatus().setId(1);
        try {
            delegate.updateFixedBillingEntry(entry, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.updateFixedBillingEntry(entry, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#updateFixedBillingEntry(FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateFixedBillingEntry_IAE() throws Exception {
        try {
            delegate.updateFixedBillingEntry(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link  FixedBillingEntryManagerDelegate#updateFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingEntries_DAE() throws Exception {
        FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
        entry.setId(1);
        entry.getFixedBillingStatus().setId(1);
        try {
            delegate.updateFixedBillingEntries(new FixedBillingEntry[] { entry }, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.updateFixedBillingEntries(new FixedBillingEntry[] { entry }, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link  FixedBillingEntryManagerDelegate#updateFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingEntries_IAE() throws Exception {
        try {
            delegate.updateFixedBillingEntries(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            delegate.updateFixedBillingEntries(new FixedBillingEntry[] { null }, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#deleteFixedBillingEntry(long,boolean)}</code>.
     *
     * @throws Exception
     */
    public void testMethodDeleteFixedBillingEntry_DAE() throws Exception {
        try {
            delegate.deleteFixedBillingEntry(1, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.deleteFixedBillingEntry(1, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#deleteFixedBillingEntry(long,boolean)}</code>.
     *
     * @throws Exception
     */
    public void testMethodDeleteFixedBillingEntry_IAE() throws Exception {
        try {
            delegate.deleteFixedBillingEntry(-1, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#deleteFixedBillingEntries(long[],boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingEntries_DAE() throws Exception {
        try {
            delegate.deleteFixedBillingEntries(new long[] { 1 }, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.deleteFixedBillingEntries(new long[] { 1 }, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#deleteFixedBillingEntries(long[],boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingEntries_IAE() throws Exception {
        try {
            delegate.deleteFixedBillingEntries(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.deleteFixedBillingEntries(new long[] { -1 }, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#searchFixedBillingEntries(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodSearchFixedBillingEntries_DAE() throws Exception {
        try {
            delegate.searchFixedBillingEntries(new EqualToFilter("dummy", new Long(1)));
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#searchFixedBillingEntries(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodSearchFixedBillingEntries_IAE() throws Exception {
        try {
            delegate.searchFixedBillingEntries(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#removeRejectReasonFromEntry(FixedBillingEntry,long,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRemoveRejectReasonFromEntry_DAE() throws Exception {
        FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
        entry.setId(1);
        entry.getFixedBillingStatus().setId(1);
        try {
            delegate.removeRejectReasonFromEntry(entry, 1, false);
            fail("DataAccessException Expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.removeRejectReasonFromEntry(entry, 1, false);
            fail("DataAccessException Expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#removeRejectReasonFromEntry(FixedBillingEntry,long,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRemoveRejectReasonFromEntry_IAE() throws Exception {
        try {
            delegate.removeRejectReasonFromEntry(null, 1, false);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            delegate.removeRejectReasonFromEntry(FailureTestHelper.createFixedBillingEntry(), -1, false);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#removeAllRejectReasonsFromEntry(FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRemoveAllRejectReasonsFromEntry_DAE() throws Exception {
        FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
        entry.setId(1);
        entry.getFixedBillingStatus().setId(1);
        try {
            delegate.removeAllRejectReasonsFromEntry(entry, false);
            fail("DataAccessException Expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.removeAllRejectReasonsFromEntry(entry, false);
            fail("DataAccessException Expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#removeAllRejectReasonsFromEntry(FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRemoveAllRejectReasonsFromEntry_IAE() throws Exception {
        try {
            delegate.removeAllRejectReasonsFromEntry(null, false);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#addRejectReasonToEntry(FixedBillingEntry,long,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodAddRejectReasonToEntry_DAE() throws Exception {
        try {
            delegate.addRejectReasonToEntry(FailureTestHelper.createFixedBillingEntry(), 1, false);
            fail("DataAccessException Expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.addRejectReasonToEntry(FailureTestHelper.createFixedBillingEntry(), 1, false);
            fail("DataAccessException Expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#addRejectReasonToEntry(FixedBillingEntry,long,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodAddRejectReasonToEntry_IAE() throws Exception {
        try {
            delegate.addRejectReasonToEntry(null, 1, false);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            delegate.addRejectReasonToEntry(FailureTestHelper.createFixedBillingEntry(), -1, false);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingEntryManagerDelegate#getAllFixedBillingEntries()}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetAllFixedBillingEntries() throws Exception {
        try {
            delegate.getAllFixedBillingEntries();
            fail("DataAccessException Expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.getAllFixedBillingEntries();
            fail("DataAccessException Expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingEntryManagerDelegate#getFixedBillingEntries(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingEntries_DAE() throws Exception {
        try {
            delegate.getFixedBillingEntries(new long[] { 1 });
            fail("DataAccessException Expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.getFixedBillingEntries(new long[] { 1 });
            fail("DataAccessException Expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingEntryManagerDelegate#getFixedBillingEntries(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingEntries_IAE() throws Exception {
        try {
            delegate.getFixedBillingEntries(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.getFixedBillingEntries(new long[] { -1 });
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#getAllRejectReasonsForEntry(FixedBillingEntry)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetAllRejectReasonsForEntry_DAE() throws Exception {
        FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
        entry.setId(1);
        entry.getFixedBillingStatus().setId(1);
        try {
            delegate.getAllRejectReasonsForEntry(entry);
            fail("DataAccessException Expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.getAllRejectReasonsForEntry(entry);
            fail("DataAccessException Expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingEntryManagerDelegate#getAllRejectReasonsForEntry(FixedBillingEntry)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetAllRejectReasonsForEntry_IAE() throws Exception {
        try {
            delegate.getAllRejectReasonsForEntry(null);
            fail("IllegalArgumentException Expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingEntryManagerDelegate#getFixedBillingEntry(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingEntry_DAE() throws Exception {
        try {
            delegate.getFixedBillingEntry(1);
            fail("DataAccessException Expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.getFixedBillingEntry(1);
            fail("DataAccessException Expected");
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
        return new TestSuite(FixedBillingEntryManagerDelegateTest.class);
    }
}
