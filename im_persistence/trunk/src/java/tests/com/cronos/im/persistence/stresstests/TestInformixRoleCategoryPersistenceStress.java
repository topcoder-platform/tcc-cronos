/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.stresstests;

import java.sql.Connection;
import java.util.Iterator;

import com.cronos.im.persistence.rolecategories.Category;
import com.cronos.im.persistence.rolecategories.InformixRoleCategoryPersistence;
import com.cronos.im.persistence.rolecategories.User;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Stress test cases for class <code>InformixRoleCategoryPersistence </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestInformixRoleCategoryPersistenceStress extends TestCase {

    /**
     * Represents the InformixRoleCategoryPersistence instance for testing.
     */
    private InformixRoleCategoryPersistence persistence = null;

    /**
     * Set up the environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        persistence = new InformixRoleCategoryPersistence(DBUtil.getDBConnectionFactory(), "sysuser", null);
    }

    /**
     * Clear all the namespace in the config manager.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Test method <code> User[] getUsers(String role) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testGetUsers() throws Exception {
        Connection connection = DBUtil.getConnection();
        try {
            DBUtil.insertRecordIntoPrincipal(connection, 1, "p_name1");
            DBUtil.insertRecordIntoPrincipal(connection, 2, "p_name2");

            DBUtil.insertRecordIntoRole(connection, 3, "roleName");
            DBUtil.insertRecordIntoRole(connection, 4, "roleName");

            DBUtil.insertRecordIntoPrincipal_Role(connection, 1, 3);
            DBUtil.insertRecordIntoPrincipal_Role(connection, 2, 4);

            long start = System.currentTimeMillis();
            for (int i = 0; i < 20; i++) {
                User[] users = persistence.getUsers("roleName");
            }

            long end = System.currentTimeMillis();

            System.out.println("Call getUsers with role for 20 times cost " + (end - start) / 1000.0);

        } finally {
            DBUtil.clearTablesForRoleCategory(connection);
            DBUtil.closeConnection(connection);
        }
    }

    /**
     * Test method <code>Category[] getAllCategories() </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetAllCategories() throws Exception {
        Connection conn = DBUtil.getConnection();
        try {
            for (int i = 0; i < 20; i++) {
                DBUtil.insertRecordIntoCategory(conn, i + 1);
            }

            Category[] ret = null;

            long start = System.currentTimeMillis();
            for (int i = 0; i < 20; i++) {
                ret = persistence.getAllCategories();
            }

            long end = System.currentTimeMillis();

            System.out.println("Calling AllCategories for 20 times cost " + (end - start) / 1000.0);

            assertEquals("Equal is expected.", 20, ret.length);
        } finally {
            DBUtil.clearTablesForRoleCategory(conn);
            DBUtil.closeConnection(conn);
        }

    }

    /**
     * Test method <code>Category[] getCategories(boolean chattable) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetCategoriesboolean() throws Exception {
        Connection conn = DBUtil.getConnection();
        try {
            for (int i = 0; i < 20; i++) {
                DBUtil.insertRecordIntoCategory(conn, i + 1);
            }

            long start = System.currentTimeMillis();
            for (int i = 0; i < 20; i++) {
                Category[] ret = persistence.getCategories(true);
            }
            long end = System.currentTimeMillis();

            System.out.println("Calling getCategories for 20 times cost " + (end - start) / 1000.0);

        } finally {
            DBUtil.clearTablesForRoleCategory(conn);
            DBUtil.closeConnection(conn);
        }

    }

    /**
     * Test method <code>Category[] getCategories(String manager) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetCategoriesString() throws Exception {
        Connection connection = DBUtil.getConnection();
        try {

            DBUtil.insertRecordIntoPrincipal(connection, 1, "p_name1");
            DBUtil.insertRecordIntoPrincipal(connection, 2, "p_name2");

            DBUtil.insertRecordIntoRole(connection, 3, "roleName");
            DBUtil.insertRecordIntoRole(connection, 4, "roleName");

            DBUtil.insertRecordIntoPrincipal_Role(connection, 1, 3);
            DBUtil.insertRecordIntoPrincipal_Role(connection, 2, 4);

            DBUtil.insertRecordIntoCategory(connection, 1);
            DBUtil.insertRecordIntoCategory(connection, 2);

            DBUtil.insertRecordInto_manager_category(connection, 1, 1);
            DBUtil.insertRecordInto_manager_category(connection, 2, 2);

            long start = System.currentTimeMillis();
            for (int i = 0; i < 20; i++) {
                Category[] ret = persistence.getCategories("p_name1");
            }
            long end = System.currentTimeMillis();

            System.out.println("call getCategories with manager for 20 times cost " + (end - start) / 1000.0);
        } finally {
            DBUtil.clearTablesForRoleCategory(connection);
            DBUtil.closeConnection(connection);
        }
    }

    /**
     * Test method <code>void updateCategories(String manager, Category[] categories) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testUpdateCategories() throws Exception {
        Connection connection = DBUtil.getConnection();
        try {

            DBUtil.insertRecordIntoPrincipal(connection, 1, "p_name1");
            DBUtil.insertRecordIntoPrincipal(connection, 2, "manager");

            DBUtil.insertRecordIntoRole(connection, 3, "roleName");
            DBUtil.insertRecordIntoRole(connection, 4, "roleName");

            DBUtil.insertRecordIntoPrincipal_Role(connection, 1, 3);
            DBUtil.insertRecordIntoPrincipal_Role(connection, 2, 4);

            DBUtil.insertRecordIntoCategory(connection, 1);
            DBUtil.insertRecordIntoCategory(connection, 2);

            DBUtil.insertRecordInto_manager_category(connection, 1, 1);
            DBUtil.insertRecordInto_manager_category(connection, 2, 2);

            long start = System.currentTimeMillis();

            for (int i = 0; i < 20; i++) {
                Category[] ret = persistence.getCategories("p_name1");
            }

            long end = System.currentTimeMillis();

            System.out.println("calling updateCategories for 20 times cost " + (end - start) / 1000.0);

        } finally {
            DBUtil.clearTablesForRoleCategory(connection);
            DBUtil.closeConnection(connection);
        }
    }
}