/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.CommonInfo;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryRejectReason;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
import com.topcoder.timetracker.entry.expense.ExpenseEntryType;
import com.topcoder.timetracker.entry.expense.accuracytests.TestHelper;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryDbPersistence;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryStatusDbPersistence;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryTypeDbPersistence;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;
import com.topcoder.util.config.ConfigManager;

/**
 * FailureTests for ExpenseEntryDbPersistence class.
 *
 * @author qiucx0161
 * @author kr00tki
 * @version 2.0
 * @since 1.0
 */
public class TestExpenseEntryDbPersistence extends TestCase {

    /** DBConnectionFactory config namespace. */
    protected static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /** Producer name. Points to valid database. */
    protected static final String PRODUCER_NAME = "Connection";

    /** Producer name. Points to empty database. */
    protected static final String EMPTY_DATABASE = "empty";

    /** DBConnectionFactory config file. */
    private static final String DB_FACTORY_FILE = "failuretests/Database.xml";

    /** ExpenseEntryDbPersistence instance to test on. */
    private ExpenseEntryDbPersistence persistence = null;

    /** ExpenseEntryDbPersistence instance to test on. */
    private ExpenseEntryDbPersistence emptyPersistence = null;

    /**
     * The expense type persistence.
     *
     * @since 2.0
     */
    private ExpenseEntryTypeDbPersistence typePersistence = null;

    /**
     * The expense status persistence.
     *
     * @since 2.0
     */
    private ExpenseEntryStatusDbPersistence statusPersistence = null;

    /** Configuration Manager instance. */
    protected ConfigManager config = null;

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        clearConfigManager();
        config = ConfigManager.getInstance();
        config.add(DB_FACTORY_FILE);

        persistence = new ExpenseEntryDbPersistence(PRODUCER_NAME, DB_FACTORY_NAMESPACE);
        emptyPersistence = new ExpenseEntryDbPersistence(EMPTY_DATABASE, DB_FACTORY_NAMESPACE);
        typePersistence = new ExpenseEntryTypeDbPersistence(PRODUCER_NAME, DB_FACTORY_NAMESPACE);
        statusPersistence = new ExpenseEntryStatusDbPersistence(PRODUCER_NAME, DB_FACTORY_NAMESPACE);
        persistence.initConnection(PRODUCER_NAME);
        clearTables(persistence.getConnection());
        createCompanies(persistence.getConnection());
    }

    /**
     * Creates test companies.
     *
     * @param conn the database connection.
     * @throws Exception to JUnit.
     */
    private void createCompanies(Connection conn) throws Exception {
        Statement stmt = conn.createStatement();
        try {
        stmt.execute("insert into company values(10, 'a', 'a', current, 'a', current, 'a')");
        stmt.execute("insert into company values(20, 'a', 'a1', current, 'a', current, 'a')");
        stmt.execute("insert into company values(30, 'a', 'a2', current, 'a', current, 'a')");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * Removes data from tables.
     *
     * @param conn the database connection.
     * @throws Exception to JUnit.
     */
    private void clearTables(Connection conn) throws Exception {
        Statement statement = conn.createStatement();
        try {
            statement.executeUpdate("DELETE FROM comp_exp_type;");
            statement.executeUpdate("DELETE FROM comp_rej_reason;");
            statement.executeUpdate("DELETE FROM exp_reject_reason;");
            statement.executeUpdate("DELETE FROM expense_entry;");
            statement.executeUpdate("DELETE FROM expense_status;");
            statement.executeUpdate("DELETE FROM expense_type;");
            statement.executeUpdate("DELETE FROM reject_reason;");
            statement.executeUpdate("DELETE FROM company;");
        } finally {
            statement.close();
        }
    }

    private ExpenseEntryRejectReason insertRejectReason(int reasonId, int companyId, Connection conn)
        throws Exception {
        ExpenseEntryRejectReason reason = new ExpenseEntryRejectReason(reasonId);
        reason.setDescription("desc");

        // Insert three reject reasons whose id is 0, 1, 2.
        PreparedStatement ps = conn.prepareStatement("INSERT INTO reject_reason(reject_reason_id, Description, "
                + "creation_date, creation_user, modification_date, modification_user, active) "
                + "VALUES (?,?,?,?,?,?,?)");

        try {
            ps.setInt(1, reasonId);
            ps.setString(2, "reject reason1");
            ps.setDate(3, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
            ps.setString(4, "Creator");
            ps.setDate(5, new java.sql.Date(TestHelper.createDate(2005, 1, 1).getTime()));
            ps.setString(6, "Modifier");
            ps.setInt(7, 1);

            ps.executeUpdate();

        } finally {
            ps.close();
        }

        // add company - reject reason mapping
        ps = conn.prepareStatement("INSERT INTO comp_rej_reason(company_id, reject_reason_id,"
                + "creation_date, creation_user, modification_date, modification_user) "
                + "VALUES (?, ?, CURRENT, 'system', CURRENT, 'system')");
        try {
            ps.setInt(1, companyId);
            ps.setInt(2, reasonId);
            ps.executeUpdate();

        } finally {
            ps.close();
        }

        return reason;
    }

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        clearTables(persistence.getConnection());
        clearConfigManager();
        persistence.closeConnection();
        emptyPersistence.closeConnection();
    }

    /**
     * Tests addEntries(ExpenseEntry[] entries, boolean isAtomic) method with null Entries IllegalArgumentException
     * should be thrown.
     */
    public void testAddEntriesNullEntries() {
        try {
            persistence.addEntries(null, true);
            fail("testAddEntriesNullEntries is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testAddEntriesNullEntries is failure.");
        }
    }

    /**
     * Tests addEntries(ExpenseEntry[] entries, boolean isAtomic) method with empty Entries
     * IllegalArgumentException should be thrown.
     */
    public void testAddEntriesEmptyEntries() {
        try {
            persistence.addEntries(new ExpenseEntry[] {}, true);
            fail("testAddEntriesEmptyEntries is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testAddEntriesEmptyEntries is failure.");
        }
    }

    /**
     * Tests addEntries(ExpenseEntry[] entries, boolean isAtomic) method with null element in Entries
     * IllegalArgumentException should be thrown.
     */
    public void testAddEntriesNullElementInEntries() {
        try {
            persistence.addEntries(new ExpenseEntry[] {null}, true);
            fail("testAddEntriesNullElementInEntries is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testAddEntriesNullElementInEntries is failure.");
        }
    }

    /**
     * Tests addEntries method failure. PersistenceException should be thrown.
     */
    public void testAddEntrysEmptyDB() {
        try {
            emptyPersistence.addEntries(new ExpenseEntry[] {getExpenseEntry()}, true);
            fail("PersistenceException expected.");
        } catch (PersistenceException ex) {
            // ok
        } catch (Exception e) {
            fail("testAddEntrysEmptyDB is failure.");
        }
    }

    /**
     * Tests deleteEntries(int[] entryIds, boolean isAtomic) method with null EntryIds IllegalArgumentException
     * should be thrown.
     */
    public void testDeleteEntriesNullEntryIds() {
        try {
            persistence.deleteEntries(null, true);
            fail("testDeleteEntriesNullEntryIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testDeleteEntriesNullEntryIds is failure.");
        }
    }

    /**
     * Tests deleteEntries(int[] entryIds, boolean isAtomic) method with empty EntryIds IllegalArgumentException
     * should be thrown.
     */
    public void testDeleteEntriesEmptyEntryIds() {
        try {
            persistence.deleteEntries(new int[] {}, true);
            fail("testDeleteEntriesEmptyEntryIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testDeleteEntriesEmptyEntryIds is failure.");
        }
    }

    /**
     * Tests updateEntries(ExpenseEntry[] entries, boolean isAtomic) method with null Entries
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateEntriesNullEntries() {
        try {
            persistence.updateEntries(null, true);
            fail("testUpdateEntriesNullEntries is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testUpdateEntriesNullEntries is failure.");
        }
    }

    /**
     * Tests updateEntries(ExpenseEntry[] entries, boolean isAtomic) method with empty Entries
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateEntriesEmptyEntries() {
        try {
            persistence.updateEntries(new ExpenseEntry[] {}, true);
            fail("testUpdateEntriesEmptyEntries is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testUpdateEntriesEmptyEntries is failure.");
        }
    }

    /**
     * Tests updateEntries(ExpenseEntry[] entries, boolean isAtomic) method with null element in Entries
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateEntriesNullElementInEntries() {
        try {
            persistence.updateEntries(new ExpenseEntry[] {null}, true);
            fail("testUpdateEntriesNullElementInEntries is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testUpdateEntriesNullElementInEntries is failure.");
        }
    }

    /**
     * Tests updateEntries(ExpenseEntry[] entries, boolean isAtomic) with failure state. IllegalArgumentException
     * should be thrown.
     */
    public void testUpdateEntriesInvalid() {
        try {
            persistence.updateEntries(new ExpenseEntry[] {}, true);
            fail("testUpdateEntriesInvalid is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testUpdateEntriesInvalid is failure.");
        }
    }

    /**
     * Tests retrieveEntries(int[] entryIds, boolean isAtomic) method with null EntryIds IllegalArgumentException
     * should be thrown.
     */
    public void testRetrieveEntriesNullEntryIds() {
        try {
            persistence.retrieveEntries(null, true);
            fail("testRetrieveEntriesNullEntryIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testRetrieveEntriesNullEntryIds is failure.");
        }
    }

    /**
     * Tests retrieveEntries(int[] entryIds, boolean isAtomic) method with empty EntryIds IllegalArgumentException
     * should be thrown.
     */
    public void testRetrieveEntriesEmptyEntryIds() {
        try {
            persistence.retrieveEntries(new int[] {}, true);
            fail("testRetrieveEntriesEmptyEntryIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testRetrieveEntriesEmptyEntryIds is failure.");
        }
    }

    /**
     * Tests searchEntries(Criteria criteria) method with null Criteria IllegalArgumentException should be thrown.
     */
    public void testSearchEntriesNullCriteria() {
        try {
            persistence.searchEntries(null);
            fail("testSearchEntriesNullCriteria is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testSearchEntriesNullCriteria is failure.");
        }
    }

    /**
     * Removes all namespaces from Configuration Manager.
     *
     * @throws Exception to JUnit.
     */
    private void clearConfigManager() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String ns = (String) it.next();
            cm.removeNamespace(ns);
        }
    }

    /**
     * Helper method. Creates <c>ExpenseEntryType</c> filled with data.
     *
     * @return <c>ExpenseEntryType</c> instance.
     */
    private ExpenseEntryType getExpenseEntryType() {
        ExpenseEntryType type = new ExpenseEntryType(2);
        setCommonInfo(type);
        type.setCompanyId(10);
        type.setId(10);
        return type;
    }

    /**
     * Helper method. Fills <c>CommonInfo</c> with default data.
     *
     * @param type object to fill.
     */
    private void setCommonInfo(CommonInfo type) {
        type.setCreationDate(new Date());
        type.setCreationUser("tcs");
        type.setModificationDate(new Date());
        type.setModificationUser("tcs");
        type.setDescription("desc");
    }

    /**
     * Helper method. Creates <c>ExpenseEntryStatus</c> filled with data.
     *
     * @return <c>ExpenseEntryStatus</c> instance.
     */
    private ExpenseEntryStatus getExpenseEntryStatus() {
        ExpenseEntryStatus status = new ExpenseEntryStatus(1);
        setCommonInfo(status);
        return status;
    }

    /**
     * Helper method. Creates <c>ExpenseEntry</c> filled with data.
     *
     * @return <c>ExpenseEntry</c> instance.
     */
    private ExpenseEntry getExpenseEntry() {
        ExpenseEntry entry = new ExpenseEntry(1);
        entry.setStatus(getExpenseEntryStatus());
        entry.setExpenseType(getExpenseEntryType());
        entry.setDate(new Date());
        entry.setAmount(new BigDecimal(1.0));
        setCommonInfo(entry);
        entry.setCompanyId(10);

        return entry;
    }

    // since 2.0

    /**
     * <p>
     * Tests the <code>addEntry(ExpenseEntry)</code> method failure.
     * Checks if exception is thrown when the expense type and entry companies doesn't match.
     * </p>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testAddEntry_DifferentCompany() throws Exception {
        ExpenseEntry entry = getExpenseEntry();
        statusPersistence.addStatus(entry.getStatus());
        typePersistence.addType(entry.getExpenseType());
        entry.setCompanyId(20);
        entry.getExpenseType().setCompanyId(10);
        try {
            persistence.addEntry(entry);
            fail("Different expense type and entry companies.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>addEntries(ExpenseEntry[], boolean)</code> method failure.
     * Checks if exception is thrown when the expense type and entry companies doesn't match.
     * </p>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testAddEntries_DifferentCompany() throws Exception {
        ExpenseEntry entry = getExpenseEntry();
        statusPersistence.addStatus(entry.getStatus());
        typePersistence.addType(entry.getExpenseType());
        entry.setCompanyId(20);
        entry.getExpenseType().setCompanyId(10);
        try {
            persistence.addEntries(new ExpenseEntry[] {entry}, true);
            fail("Different expense type and entry companies.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>updateEntry(ExpenseEntry)</code> method failure.
     * Checks if exception is thrown when the expense type and entry companies doesn't match.
     * </p>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testUpdateEntry_DifferentCompany() throws Exception {
        ExpenseEntry entry = getExpenseEntry();
        statusPersistence.addStatus(entry.getStatus());
        typePersistence.addType(entry.getExpenseType());
        persistence.addEntry(entry);

        entry.setCompanyId(20);
        entry.getExpenseType().setCompanyId(10);
        try {
            persistence.updateEntry(entry);
            fail("Different expense type and entry companies.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>updateEntries(ExpenseEntry[], boolean)</code> method failure.
     * Checks if exception is thrown when the expense type and entry companies doesn't match.
     * </p>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testUpdateEntries_DifferentCompany() throws Exception {
        ExpenseEntry entry = getExpenseEntry();
        statusPersistence.addStatus(entry.getStatus());
        typePersistence.addType(entry.getExpenseType());
        persistence.addEntry(entry);

        entry.setCompanyId(20);
        entry.getExpenseType().setCompanyId(10);
        try {
            persistence.updateEntries(new ExpenseEntry[] {entry}, true);
            fail("Different expense type and entry companies.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>addEntry(ExpenseEntry)</code> method failure.
     * Checks if exception is thrown when the reject reason and entry companies doesn't match.
     * </p>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testAddEntry_DifferentRejectReasonCompany() throws Exception {
        ExpenseEntry entry = getExpenseEntry();
        statusPersistence.addStatus(entry.getStatus());
        typePersistence.addType(entry.getExpenseType());

        ExpenseEntryRejectReason reason = insertRejectReason(2, 20, persistence.getConnection());
        entry.addRejectReason(reason);

        try {
            persistence.addEntry(entry);
            fail("Different expense type and entry companies.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>addEntries(ExpenseEntry[], boolean)</code> method failure.
     * Checks if exception is thrown when the reject reason and entry companies doesn't match.
     * </p>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testAddEntries_DifferentRejectReasonCompany() throws Exception {
        ExpenseEntry entry = getExpenseEntry();
        statusPersistence.addStatus(entry.getStatus());
        typePersistence.addType(entry.getExpenseType());

        ExpenseEntryRejectReason reason = insertRejectReason(2, 20, persistence.getConnection());
        entry.addRejectReason(reason);
        try {
            persistence.addEntries(new ExpenseEntry[] {entry}, true);
            fail("Different expense type and entry companies.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>updateEntry(ExpenseEntry)</code> method failure.
     * Checks if exception is thrown when the reject reason and entry companies doesn't match.
     * </p>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testUpdateEntry_DifferentRejectReasonCompany() throws Exception {
        ExpenseEntry entry = getExpenseEntry();
        statusPersistence.addStatus(entry.getStatus());
        typePersistence.addType(entry.getExpenseType());
        persistence.addEntry(entry);

        ExpenseEntryRejectReason reason = insertRejectReason(1, 20, persistence.getConnection());
        entry.addRejectReason(reason);

        try {
            persistence.updateEntry(entry);
            fail("Different expense type and entry companies.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>updateEntries(ExpenseEntry[], boolean)</code> method failure.
     * Checks if exception is thrown when the reject reason and entry companies doesn't match.
     * </p>
     *
     * @throws Exception to JUnit.
     * @since 2.0
     */
    public void testUpdateEntries_DifferentRejectReasonCompany() throws Exception {
        ExpenseEntry entry = getExpenseEntry();
        statusPersistence.addStatus(entry.getStatus());
        typePersistence.addType(entry.getExpenseType());
        persistence.addEntry(entry);

        ExpenseEntryRejectReason reason = insertRejectReason(2, 20, persistence.getConnection());
        entry.addRejectReason(reason);
        try {
            persistence.updateEntries(new ExpenseEntry[] {entry}, true);
            fail("Different expense type and entry companies.");
        } catch (PersistenceException ex) {
            // ok
        }
    }

}
