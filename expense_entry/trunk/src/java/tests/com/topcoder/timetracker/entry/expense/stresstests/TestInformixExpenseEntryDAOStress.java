/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.stresstests;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.ExpenseType;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryStatusDAO;
import com.topcoder.timetracker.entry.expense.persistence.ExpenseEntryTypeDAO;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseEntryDAO;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseStatusDAO;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseTypeDAO;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Stress test cases for class <code>InformixExpenseEntryDAO </code>.
 *
 * @author Chenhong
 * @version 3.2
 */
public class TestInformixExpenseEntryDAOStress extends TestCase {

    /**
     * Represents the InformixExpenseEntryDAO instance for testing.
     */
    private InformixExpenseEntryDAO dao = null;

    /**
     * Represents the ExpenseType instance for testing.
     */
    private ExpenseType type = null;

    /**
     * Represents the ExpenseStatus instance for testing.
     */
    private ExpenseStatus status = null;

    /**
     * Setup.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        DBUtil.clearNamespaces();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add(new File("test_files/stress/DBConnectionFactory.xml").getCanonicalPath());
        cm.add(new File("test_files/stress/InformixExpenseStatusDAO.xml").getCanonicalPath());
        cm.add(new File("test_files/stress/InformixExpenseEntryDAO.xml").getCanonicalPath());
        cm.add(new File("test_files/stress/InformixExpenseTypeDAO.xml").getCanonicalPath());

        dao = new InformixExpenseEntryDAO();

        Connection connection = DBUtil.getConnection();

        try {
            DBUtil.insertRecordInto_Company(connection, 1);
            DBUtil.insertRecordInto_Project(connection, 1);
            DBUtil.insertRecordInto_PaymentTerm(connection);

            DBUtil.insertRerordInto_reject_reason(connection, 1);
            DBUtil.insertRecordInto_comp_rej_reason(connection, 1);
            DBUtil.insertRecordInto_invoice_status(connection, 1);
            DBUtil.insertRecordInto_invoice(connection, 1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DBUtil.closeConnection(connection);
        }

        ExpenseEntryStatusDAO statusDao = new InformixExpenseStatusDAO();
        status = new ExpenseStatus();
        status.setCreationUser("user");
        status.setDescription("des");
        status.setModificationUser("user");

        statusDao.addStatus(status);

        this.status = statusDao.retrieveStatus(status.getId());

        ExpenseEntryTypeDAO typeDao = new InformixExpenseTypeDAO();
        type = new ExpenseType();
        type.setCompanyId(1);
        type.setCreationUser("user");
        type.setModificationUser("user");
        type.setDescription("des");

        typeDao.addType(type);

        this.type = typeDao.retrieveType(type.getId());
    }

    /**
     * Clear the tables and clear the namespaces in the config manager.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        DBUtil.clearTables();
        DBUtil.clearNamespaces();
    }

    /**
     * Create an ExpenseEntry instance for testing.
     *
     * @return ExpenseEntry instance.
     */
    private ExpenseEntry createExpenseEntry() {
        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal("11.4"));
        entry.setBillable(true);
        entry.setChanged(false);
        entry.setCompanyId(1);
        entry.setClientId(1);
        entry.setProjectId(1);
        entry.setCreationUser("user");
        entry.setDate(new Date());
        entry.setModificationUser("userM");

        entry.setDescription("des");
        entry.setExpenseType(type);

        Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setCompanyId(1);

        entry.setInvoice(invoice);

        entry.setMileage(0);

        RejectReason rejectReason = new RejectReason();
        rejectReason.setId(1);
        rejectReason.setCompanyId(1);

        Map map = new HashMap();

        map.put(new Long(1), rejectReason);

        entry.setRejectReasons(map);

        entry.setStatus(status);

        return entry;
    }

    /**
     * Test method <code>boolean addEntry(ExpenseEntry entry, boolean auditFlag) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddEntry() throws Exception {

        long start = System.currentTimeMillis();

        for (int i = 0; i < 20; i++) {
            ExpenseEntry entry = this.createExpenseEntry();
            boolean flag = dao.addEntry(entry, false);

            assertTrue("The Entry should be added succesfully.", flag);
        }

        long end = System.currentTimeMillis();

        System.out.println("Add 20 ExpenseEntry cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test the method <code>boolean deleteEntry(long entryId, boolean auditFlag) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteEntry() throws Exception {

        long start = System.currentTimeMillis();

        for (int i = 0; i < 20; i++) {
            dao.deleteEntry(100, false);
        }

        long end = System.currentTimeMillis();

        System.out.println("Delete Entry for 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>void deleteAllEntries(boolean auditFlag)  </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteAllEntries() throws Exception {
        for (int i = 0; i < 20; i++) {
            ExpenseEntry entry = this.createExpenseEntry();
            boolean flag = dao.addEntry(entry, false);

            assertTrue("The Entry should be added succesfully.", flag);
        }

        long start = System.currentTimeMillis();

        dao.deleteAllEntries(false);

        long end = System.currentTimeMillis();

        System.out.println("Delete all Entries with 20 records cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>boolean updateEntry(ExpenseEntry entry, boolean auditFlag) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateEntry() throws Exception {

        ExpenseEntry entry = this.createExpenseEntry();
        boolean flag = dao.addEntry(entry, false);

        assertTrue("The Entry should be added succesfully.", flag);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 20; i++) {
            entry.setDescription("description" + i);

            boolean update = dao.updateEntry(entry, false);

            assertTrue("The update should be ok.", update);
        }

        long end = System.currentTimeMillis();

        System.out.println("update Entry 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>ExpenseEntry retrieveEntry(long entryId) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRetrieveEntry() throws Exception {

        ExpenseEntry entry = this.createExpenseEntry();
        boolean flag = dao.addEntry(entry, false);

        assertTrue("The Entry should be added succesfully.", flag);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 20; i++) {
            ExpenseEntry ret = dao.retrieveEntry(entry.getId());

            assertNotNull("Should not be null.", ret);
        }

        long end = System.currentTimeMillis();

        System.out.println("RetrieveEntry 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>ExpenseEntry[] retrieveAllEntries() </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRetrieveAllEntries() throws Exception {
        for (int i = 0; i < 20; i++) {
            ExpenseEntry entry = this.createExpenseEntry();
            boolean flag = dao.addEntry(entry, false);

            assertTrue("The Entry should be added succesfully.", flag);
        }

        long start = System.currentTimeMillis();

        ExpenseEntry[] entries = dao.retrieveAllEntries();

        assertEquals("Equal is expected.", 20, entries.length);

        long end = System.currentTimeMillis();

        System.out.println("RetrieveAllEntry with 20 records cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>ExpenseEntry[] searchEntries(Criteria criteria)  </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchEntries() throws Exception {
        for (int i = 0; i < 20; i++) {
            ExpenseEntry entry = this.createExpenseEntry();
            boolean flag = dao.addEntry(entry, false);

            assertTrue("The Entry should be added succesfully.", flag);
        }

        long start = System.currentTimeMillis();

        ExpenseEntry[] ret = dao.searchEntries(FieldMatchCriteria.getExpenseEntryCompanyIdMatchCriteria(1));

        long end = System.currentTimeMillis();

        assertEquals("There should be 20 ExpenseEntry searched out.", 20, ret.length);

        System.out.println("SearchEntries cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test for void addEntries(ExpenseEntry[] entries, boolean auditFlag)
     *
     * @throws Exception
     *             to juint.
     */
    public void testAddEntriesExpenseEntryArrayboolean() throws Exception {
        ExpenseEntry[] entries = new ExpenseEntry[20];
        for (int i = 0; i < entries.length; i++) {
            entries[i] = this.createExpenseEntry();
        }

        long start = System.currentTimeMillis();

        dao.addEntries(entries, false);

        long end = System.currentTimeMillis();

        System.out.println("Add 20 ExpenseEntry once cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>ExpenseEntry[] retrieveEntries(long[] entryIds) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRetrieveEntrieslongArray() throws Exception {

        List list = new ArrayList();

        for (int i = 0; i < 20; i++) {
            ExpenseEntry entry = this.createExpenseEntry();
            boolean flag = dao.addEntry(entry, false);

            list.add(new Long(entry.getId()));

            assertTrue("The Entry should be added succesfully.", flag);
        }

        long[] ids = new long[20];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = ((Long) list.get(i)).longValue();
        }

        long start = System.currentTimeMillis();
        ExpenseEntry[] ret = dao.retrieveEntries(ids);

        long end = System.currentTimeMillis();
        assertEquals("There should be 20 ExpenseEntry instances returned.", 20, ret.length);

        System.out.println("RetrieveEntries with 20 id cost " + (end - start) / 1000.0 + " seconds.");
    }

}