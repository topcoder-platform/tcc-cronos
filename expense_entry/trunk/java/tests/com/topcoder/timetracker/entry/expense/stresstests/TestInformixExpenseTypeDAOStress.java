/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.stresstests;

import java.io.File;
import java.sql.Connection;
import java.util.Date;

import com.topcoder.timetracker.entry.expense.ExpenseType;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseTypeDAO;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Stress test cases for class <code>InformixExpenseTypeDAO </code>.
 *
 * @author Chenhong
 * @version 3.2
 */
public class TestInformixExpenseTypeDAOStress extends TestCase {

    /**
     * Represents the InformixExpenseTypeDAO instance for testing.
     */
    private InformixExpenseTypeDAO dao = null;

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
        cm.add(new File("test_files/stress/InformixExpenseTypeDAO.xml").getCanonicalPath());

        dao = new InformixExpenseTypeDAO();

        Connection connection = DBUtil.getConnection();

        try {
            DBUtil.insertRecordInto_Company(connection, 1);
        } finally {
            DBUtil.closeConnection(connection);
        }
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
     * Test method <code> boolean addType(ExpenseType type) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testAddType() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            ExpenseType type = new ExpenseType();
            type.setCreationUser("stress");
            type.setModificationUser("user");
            type.setCompanyId(1);
            type.setDescription("this is just a test case");
            boolean ret = dao.addType(type);
            assertTrue("True is expected.", ret);
        }
        long end = System.currentTimeMillis();
        System.out.println("addType 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code> boolean deleteType(long typeId) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testDeleteType() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 20; i++) {
            boolean ret = dao.deleteType(1000);

            assertFalse("The type is not existed.", ret);
        }
        long end = System.currentTimeMillis();

        System.out.println("delete type 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>void deleteAllTypes() </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testDeleteAllTypes() throws Exception {
        for (int i = 0; i < 20; i++) {
            ExpenseType type = new ExpenseType();
            type.setCreationUser("stress");
            type.setModificationUser("user");
            type.setCompanyId(1);
            type.setDescription("this is just a test case");
            boolean ret = dao.addType(type);
            assertTrue("True is expected.", ret);
        }

        long start = System.currentTimeMillis();

        dao.deleteAllTypes();
        long end = System.currentTimeMillis();

        System.out.println("delete 20 record table cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>boolean updateType(ExpenseType type) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testUpdateType() throws Exception {
        ExpenseType type = new ExpenseType();
        type.setCreationUser("stress");
        type.setModificationUser("user");
        type.setCompanyId(1);
        type.setDescription("this is just a test case");
        boolean ret = dao.addType(type);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 20; i++) {
            type.setDescription("des" + i);

            type.setModificationDate(new Date());

            type.setModificationUser("update");

            boolean result = dao.updateType(type);

            assertTrue("The type should be updated.", result);
        }

        long end = System.currentTimeMillis();

        System.out.println("Update the type 20 times costs " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>ExpenseType retrieveType(long typeId) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testRetrieveType() throws Exception {
        ExpenseType type = new ExpenseType();
        type.setCreationUser("stress");
        type.setModificationUser("user");
        type.setCompanyId(1);
        type.setDescription("this is just a test case");
        boolean ret = dao.addType(type);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 20; i++) {
            ExpenseType typeRet = dao.retrieveType(type.getId());

            assertEquals("The company id should be 1.", 1, typeRet.getCompanyId());
        }

        long end = System.currentTimeMillis();

        System.out.println("Retrieve type 20 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>ExpenseType[] retrieveAllTypes() </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRetrieveAllTypes() throws Exception {
        for (int i = 0; i < 20; i++) {
            ExpenseType type = new ExpenseType();
            type.setCreationUser("stress");
            type.setModificationUser("user");
            type.setCompanyId(1);
            type.setDescription("this is just a test case");
            boolean ret = dao.addType(type);
            assertTrue("True is expected.", ret);
        }

        long start = System.currentTimeMillis();
        ExpenseType[] types = dao.retrieveAllTypes();

        assertEquals("There should be 20 EnpenseType instances.", 20, types.length);

        long end = System.currentTimeMillis();

        System.out.println("RetrieveAllTypes for 20 records table cost " + (end - start) / 1000.0 + " seconds.");

    }

    /**
     * Test method <code>ExpenseType[] searchEntries(Criteria criteria) </code>.
     *
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testSearchEntries() throws Exception {
        for (int i = 0; i < 20; i++) {
            ExpenseType type = new ExpenseType();
            type.setCreationUser("stress");
            type.setModificationUser("user");
            type.setCompanyId(1);
            type.setDescription("this is just a test case");
            boolean ret = dao.addType(type);
            assertTrue("True is expected.", ret);
        }

        long start = System.currentTimeMillis();

        ExpenseType[] types = dao.searchEntries(FieldMatchCriteria.getExpenseTypeCompanyIdMatchCriteria(1));

        long end = System.currentTimeMillis();

        assertEquals("Should return 20 ExpenseType instances.", 20, types.length);

        System.out.println("Invoke SearchEntries cost " + (end - start) / 1000.0 + " seconds.");
    }
}