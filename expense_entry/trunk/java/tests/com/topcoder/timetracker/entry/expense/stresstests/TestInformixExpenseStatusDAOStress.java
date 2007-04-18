/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.stresstests;

import java.io.File;

import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseStatusDAO;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Stress test cases for class <code>InformixExpenseStatusDAO </code>.
 *
 * @author Chenhong
 * @version 3.2
 */
public class TestInformixExpenseStatusDAOStress extends TestCase {

    /**
     * Represents the InformixExpenseStatusDAO instance for testing.
     */
    private InformixExpenseStatusDAO dao = null;

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

        dao = new InformixExpenseStatusDAO();
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
     * Test method <code>boolean addStatus(ExpenseStatus status) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testAddStatus() throws Exception {

        long start = System.currentTimeMillis();

        for (int i = 0; i < 20; i++) {
            ExpenseStatus status = new ExpenseStatus();
            status.setCreationUser("user");
            status.setModificationUser("modificationUser");
            status.setDescription("description" + i);

            boolean flag = dao.addStatus(status);

            assertTrue("The status should be added.", flag);
        }

        long end = System.currentTimeMillis();

        System.out.println("add 20 ExpenseStatus cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>boolean deleteStatus(long statusId) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteStatus() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 1; i <= 20; i++) {
            boolean flag = dao.deleteStatus(100);

            assertFalse("No ExpenseStatus with 100.", flag);
        }

        long end = System.currentTimeMillis();

        System.out.println("Calling deleteStatus 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>void deleteAllStatuses() </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDeleteAllStatuses() throws Exception {
        for (int i = 0; i < 20; i++) {
            ExpenseStatus status = new ExpenseStatus();
            status.setCreationUser("user");
            status.setModificationUser("modificationUser");
            status.setDescription("description" + i);

            boolean flag = dao.addStatus(status);

            assertTrue("The status should be added.", flag);
        }

        long start = System.currentTimeMillis();

        dao.deleteAllStatuses();

        long end = System.currentTimeMillis();

        System.out.println("DeleteAllStatus for 20 records cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>boolean updateStatus(ExpenseStatus status) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateStatus() throws Exception {
        ExpenseStatus status = new ExpenseStatus();
        status.setCreationUser("user");
        status.setModificationUser("modificationUser");
        status.setDescription("description");

        dao.addStatus(status);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 20; i++) {
            status.setDescription("update" + i);

            boolean flag = dao.updateStatus(status);
            assertTrue("The status should be updated.", flag);
        }

        long end = System.currentTimeMillis();

        System.out.println("Update ExpenseStatus 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>ExpenseStatus retrieveStatus(long statusId) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRetrieveStatus() throws Exception {
        ExpenseStatus status = new ExpenseStatus();
        status.setCreationUser("user");
        status.setModificationUser("modificationUser");
        status.setDescription("description");

        dao.addStatus(status);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            ExpenseStatus retStatus = dao.retrieveStatus(status.getId());

            assertEquals("Equal is expected.", "description", retStatus.getDescription());
        }

        long end = System.currentTimeMillis();

        System.out.println("Calling retrieveStatus for 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>ExpenseStatus[] retrieveAllStatuses() </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRetrieveAllStatuses() throws Exception {
        for (int i = 0; i < 20; i++) {
            ExpenseStatus status = new ExpenseStatus();
            status.setCreationUser("user");
            status.setModificationUser("modificationUser");
            status.setDescription("description" + i);

            boolean flag = dao.addStatus(status);

            assertTrue("The status should be added.", flag);
        }

        long start = System.currentTimeMillis();
        ExpenseStatus[] ret = dao.retrieveAllStatuses();

        long end = System.currentTimeMillis();

        System.out.println("RetrieveAllStatus with 20 records cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>ExpenseStatus[] searchEntries(Criteria criteria) </code>.
     *
     * @throws Exception
     *             to jutni.
     */
    public void testSearchEntries() throws Exception {
        for (int i = 0; i < 20; i++) {
            ExpenseStatus status = new ExpenseStatus();
            status.setCreationUser("user");
            status.setModificationUser("modificationUser");
            status.setDescription("description" + i);

            boolean flag = dao.addStatus(status);

            assertTrue("The status should be added.", flag);
        }

        long start = System.currentTimeMillis();
        ExpenseStatus[] ret = dao.searchEntries(FieldMatchCriteria.getExpenseStatusCreationUserMatchCriteria("user"));

        long end = System.currentTimeMillis();

        assertEquals("20 ExpenseStatus instance should be returned.", 20, ret.length);

        System.out.println("SearchEntries with 20 records cost " + (end - start) / 1000.0 + " seconds.");

    }

}