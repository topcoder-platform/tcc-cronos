/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.stresstests;

import java.io.File;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.contact.AddressManager;
import com.topcoder.timetracker.contact.ContactManager;
import com.topcoder.timetracker.user.StringMatchType;
import com.topcoder.timetracker.user.TestHelper;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.db.DbUserDAO;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Stress test cases for class <code>DbUserDAO </code>.
 *
 * @author Chenhong
 * @version 3.2
 */
public class TestDbUserDAOStress extends TestCase {

    /**
     * Represents the DbUserDAO instance for testing.
     */
    private DbUserDAO dao = null;

    /**
     * Set up the environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }

        cm.add(new File("test_files/stress/DBConnectionFactory.xml").getCanonicalPath());
        cm.add(new File("test_files/stress/authorization.xml").getCanonicalPath());
        cm.add(new File("test_files/stress/DatabaseSearchStrategy.xml").getCanonicalPath());
        cm.add(new File("test_files/stress/log.xml").getCanonicalPath());

        ContactManager contactManager = new MyContactManager();
        AddressManager addressManager = new MyAddressManager();
        AuditManager auditManager = new MyAuditManager();

        DBConnectionFactory factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        String connectionName = "sysuser";

        String authorizationNamespace = "com.topcoder.timetracker.application.authorization";
        AuthorizationPersistence authPersistence = new SQLAuthorizationPersistence(authorizationNamespace);

        String dbSearchNamespace = "com.topcoder.search.builder.database.DatabaseSearchStrategy";

        dao = new DbUserDAO(factory, connectionName, "stress", dbSearchNamespace, auditManager, contactManager,
                authPersistence, addressManager, true);
    }

    /**
     * Tear down the environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        DBUtil.clearTables();
        DBUtil.clearConfigNamespace();
    }

    /**
     * Stress test case for method <code>void addUsers(User[] users, boolean audit) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddUsers_with_10User() throws Exception {
        User[] users = new User[10];
        for (int i = 0; i < users.length; i++) {
            users[i] = DBUtil.createUser(i + 1);
        }

        long start = System.currentTimeMillis();

        dao.addUsers(users, false);

        long end = System.currentTimeMillis();

        System.out.println("Add 10 user cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test case for method <code>void addUsers(User[] users, boolean audit) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddUsers_with_20User() throws Exception {
        User[] users = new User[20];
        for (int i = 0; i < users.length; i++) {
            users[i] = DBUtil.createUser(i + 1);
        }

        long start = System.currentTimeMillis();

        dao.addUsers(users, false);

        long end = System.currentTimeMillis();

        System.out.println("Add 20 user cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>void updateUsers(User[] users, boolean audit) </code>.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testUpdateUsers() throws Exception {
        User[] users = new User[20];
        for (int i = 0; i < users.length; i++) {
            users[i] = DBUtil.createUser(i + 1);
        }
        dao.addUsers(users, false);

        for (int i = 0; i < users.length; i++) {
            users[i].setCreationUser("topcoder");
        }

        long start = System.currentTimeMillis();

        dao.updateUsers(users, false);
        long end = System.currentTimeMillis();

        System.out.println("Update 20 user cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code> void removeUsers(long[] userIds, boolean audit) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testRemoveUsers() throws Exception {
        User[] users = new User[20];
        for (int i = 0; i < users.length; i++) {
            users[i] = DBUtil.createUser(i + 1);
        }
        dao.addUsers(users, false);

        long[] ids = new long[users.length];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = users[i].getId();
        }

        long start = System.currentTimeMillis();

        dao.removeUsers(ids, false);
        long end = System.currentTimeMillis();

        System.out.println("Remove 20 user cost " + (end - start) / 1000.0 + " seconds.");

    }

    /**
     * Test method <code>User[] getUsers(long[] userIds) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetUsers() throws Exception {
        User[] users = new User[20];
        for (int i = 0; i < users.length; i++) {
            users[i] = DBUtil.createUser(i + 1);
        }
        dao.addUsers(users, false);

        long[] ids = new long[users.length];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = users[i].getId();
        }

        long start = System.currentTimeMillis();

        User[] ret = dao.getUsers(ids);

        long end = System.currentTimeMillis();

        System.out.println("Get users with 20 id cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>User[] searchUsers(Filter filter) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testSearchUsers() throws Exception {

        User[] users = new User[10];
        for (int i = 0; i < users.length; i++) {
            users[i] = DBUtil.createUser(i + 1);
        }

        dao.addUsers(users, false);

        User[] ret = null;
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 20; i++) {

            ret = dao.searchUsers(dao.getUserFilterFactory().createUsernameFilter(StringMatchType.EXACT_MATCH,
                    "topcoder"));
        }

        long end = System.currentTimeMillis();

        System.out.println("Call 20 searchUser with UserNameFilter cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code>User[] getAllUsers() </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetAllUsers() throws Exception {
        User[] users = new User[20];
        for (int i = 0; i < users.length; i++) {
            users[i] = DBUtil.createUser(i + 1);
        }
        dao.addUsers(users, false);

        long start = System.currentTimeMillis();
        User[] ret = dao.getAllUsers();

        long end = System.currentTimeMillis();

        System.out.println("Gall GetAllUser cost " + (end - start) / 1000.0 + " seconds.");
    }
}