/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

import com.cronos.im.persistence.ConfigurationException;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.util.config.ConfigManager;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>InformixRoleCategoryPersistence</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class InformixRoleCategoryPersistenceTests extends TestCase {
    /**
     * The configuration manager.
     */
    private static final ConfigManager MANAGER = ConfigManager.getInstance();

    /**
     * An database connection to use for the test. This is initialized in {@link #setUp setUp} to be a new instance
     * for each test.
     */
    private Connection connection;

    /**
     * An indication of whether this is the first test that is run.
     */
    private boolean firstTime = true;

    /**
     * An <code>InformixRoleCategoryPersistence</code> instance to use for the test. This is
     * initialized in {@link #setUp setUp} to be a new instance for each test.
     */
    private InformixRoleCategoryPersistence ircp;

    /**
     * Pre-test setup: loads the configuration, populates the tables, and creates the per-test variables.
     *     * @throws Exception if an unexpected exception occurs
     */
    public void setUp() throws Exception {
        if (firstTime) {
            // clean up in case a previous test left things in a bad state
            removeAllNamespaces();
        }

        MANAGER.add("test.xml");

        ircp = new InformixRoleCategoryPersistence();

        connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").
            createConnection();

        if (firstTime) {
            firstTime = false;
            clearAllTables();
        }

        addPrincipal(1, "ivern");
        addPrincipal(2, "magicpig");
        addPrincipal(3, "pops");

        addRole(1, "Designer");
        addRole(2, "Developer");
        addRole(3, "Manager");

        addPrincipalRole(1, 3);
        addPrincipalRole(2, 2);
        addPrincipalRole(3, 1);

        addCategory(1, "cat1", "category 1", true);
        addCategory(2, "cat2", "category 2", true);
        addCategory(3, "cat3", "category 3", false);

        addManagerCategory(1, 1);
        addManagerCategory(1, 2);
        addManagerCategory(2, 1);
    }

    /**
     * Per-test cleanup: clears the tables, closes the connection, and resets the config manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void tearDown() throws Exception {
        ircp = null;

        removeAllNamespaces();
        clearAllTables();

        connection.close();
        connection = null;
    }

    /**
     * Removes all namespaces from the config manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    private void removeAllNamespaces() throws Exception {
        for (Iterator it = MANAGER.getAllNamespaces(); it.hasNext();) {
            MANAGER.removeNamespace((String) it.next());
        }
    }

    /**
     * Clears all tables used by this test suite.
     *
     * @throws Exception if an unexpected exception occurs
     */
    private void clearAllTables() throws Exception {
        connection.prepareStatement("DELETE FROM principal_role").executeUpdate();
        connection.prepareStatement("DELETE FROM manager_category").executeUpdate();
        connection.prepareStatement("DELETE FROM principal").executeUpdate();
        connection.prepareStatement("DELETE FROM role").executeUpdate();
        connection.prepareStatement("DELETE FROM category").executeUpdate();
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_null_arg1() throws Exception {
        try {
            new InformixRoleCategoryPersistence(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed an empty namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_empty_arg1() throws Exception {
        try {
            new InformixRoleCategoryPersistence("  ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a bad namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_bad_namespace() throws Exception {
        try {
            new InformixRoleCategoryPersistence("bad.namespace");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing a missing specification namespace.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_missing_spec_namespace() throws Exception {
        try {
            new InformixRoleCategoryPersistence("missing.spec.namespace");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing a missing connection factory.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_missing_connection_factory() throws Exception {
        try {
            new InformixRoleCategoryPersistence("missing.connection.factory");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing an invalid connection factory.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_bad_connection_factory() throws Exception {
        try {
            new InformixRoleCategoryPersistence("bad.connection.factory");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing a missing connection name.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_missing_connection_name() throws Exception {
        try {
            new InformixRoleCategoryPersistence("missing.connection.name");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests that the first constructor throws <code>IllegalArgumentException</code> when passed a namespace
     * containing a invalid object validator.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1_bad_validator() throws Exception {
        try {
            new InformixRoleCategoryPersistence("bad.validator");
            fail("should have thrown ConfigurationException");
        } catch (ConfigurationException ex) {
            // pass
        }
    }

    /**
     * Tests the first constructor.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor1() throws Exception {
        new InformixRoleCategoryPersistence(InformixRoleCategoryPersistence.DEFAULT_NAMESPACE);
        // should succeed
    }

    /**
     * Tests that the second constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> connection factory.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor2_null_arg1() throws Exception {
        try {
            new InformixRoleCategoryPersistence(null, "name", null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the second constructor throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> connection name.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor2_null_arg2() throws Exception {
        try {
            new InformixRoleCategoryPersistence(new DBConnectionFactoryImpl(), null, null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the second constructor throws <code>IllegalArgumentException</code> when passed an empty
     * connection name.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor2_empty_arg2() throws Exception {
        try {
            new InformixRoleCategoryPersistence(new DBConnectionFactoryImpl(), "  ", null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the second constructor.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_ctor2() throws Exception {
        new InformixRoleCategoryPersistence(new DBConnectionFactoryImpl(), "name", null);
        // should succeed
    }

    // the default constructor is used by many of the other tests, so there is no need for a special test case

    /**
     * Tests that the <code>getUsers</code> method throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> role.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getUsers_null_arg1() throws Exception {
        try {
            ircp.getUsers(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>getUsers</code> method throws <code>IllegalArgumentException</code> when passed an
     * empty role.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getUsers_empty_arg1() throws Exception {
        try {
            ircp.getUsers("  ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>getUsers</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getUsers() throws Exception {
        addPrincipal(4, "mess");
        addPrincipalRole(4, 3);

        User[] users = ircp.getUsers("Manager");
        assertEquals("there should be two users", 2, users.length);
        String name1 = users[0].getName();
        String name2 = users[1].getName();
        assertTrue("the users should be mess and ivern",
                   (name1.equals("mess") && name2.equals("ivern"))
                   || (name1.equals("ivern") && name2.equals("mess")));
    }

    /**
     * Tests that <code>getUsers</code> throws <code>RoleNotFoundException</code> when the specified role is not
     * found.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getUsers_role_not_found() throws Exception {
        try {
            ircp.getUsers("oh no");
            fail("should have thrown RoleNotFoundException");
        } catch (RoleNotFoundException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>getAllCategories</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getAllCategories() throws Exception {
        Category[] cats = ircp.getAllCategories();
        assertEquals("there should be three categories", 3, cats.length);

        for (long id = 1; id <= 3; ++id) {
            boolean found = false;
            for (int idx = 0; idx < cats.length && !found; ++idx) {
                found = (id == cats[idx].getId());
            }

            if (!found) {
                fail("cateogry IDs should be 1, 2, and 3");
            }
        }
    }

    /**
     * Tests the <code>getCategories(boolean)</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getCategories_chattable() throws Exception {
        Category[] cats = ircp.getCategories(false);

        assertEquals("there should be 1 category", 1, cats.length);
        assertEquals("the ID should be 3", 3, cats[0].getId());
    }

    /**
     * Tests that the <code>getCategories</code> method throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getCategories_null_arg1() throws Exception {
        try {
            ircp.getCategories(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>getCategories</code> method throws <code>IllegalArgumentException</code> when passed an
     * empty manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getCategories_empty_arg1() throws Exception {
        try {
            ircp.getCategories("  ");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>getCategories</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getCategories() throws Exception {
        Category[] cats = ircp.getCategories("ivern");
        assertEquals("there should be 2 categories", 2, cats.length);
        long id1 = cats[0].getId();
        long id2 = cats[1].getId();

        assertTrue("the categories should be 1 and 2", (id1 == 1 && id2 == 2) || (id1 == 2 && id2 == 1));
    }

    /**
     * Tests that <code>getCategories</code> throws <code>ManagerNotFoundException</code> when the manager does not
     * exist.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getCategories_bad_manager() throws Exception {
        try {
            ircp.getCategories("assistant");
            fail("should have thrown ManagerNotFoundException");
        } catch (ManagerNotFoundException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>updateCategories</code> method throws <code>IllegalArgumentException</code> when passed
     * a <code>null</code> manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_updateCategories_null_arg1() throws Exception {
        try {
            ircp.updateCategories(null, new Category[0]);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>updateCategories</code> method throws <code>IllegalArgumentException</code> when passed
     * an empty manager.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_updateCategories_empty_arg1() throws Exception {
        try {
            ircp.updateCategories("  ", new Category[0]);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>updateCategories</code> method throws <code>IllegalArgumentException</code> when passed
     * a <code>null</code> categories array.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_updateCategories_null_arg2() throws Exception {
        try {
            ircp.updateCategories("manager", null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests that the <code>updateCategories</code> method throws <code>IllegalArgumentException</code> when passed
     * a <code>null</code> category array.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_updateCategories_null_element_arg2() throws Exception {
        try {
            ircp.updateCategories("manager", new Category[] {null});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>updateCategories</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_updateCategories() throws Exception {
        ircp.updateCategories("ivern", new Category[] {new Category(2, "cat2", "category 2", true),
                                                       new Category(3, "cat3", "category 3", false)});

        Category[] cats = ircp.getCategories("ivern");
        assertEquals("there should be 2 categories", 2, cats.length);
        long id1 = cats[0].getId();
        long id2 = cats[1].getId();

        assertTrue("the categories should be 2 and 3", (id1 == 2 && id2 == 3) || (id1 == 3 && id2 == 2));
    }

    /**
     * Tests that <code>updateCategories</code> throws <code>CategoryNotFoundException</code> when passed an
     * invalid category.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_updateCategories_category_not_found() throws Exception {
        try {
            ircp.updateCategories("ivern", new Category[] {new Category(4, "cat4", "category 4", true)});
            fail("should have thrown CategoryNotFoundException");
        } catch (CategoryNotFoundException ex) {
            // pass
        }
    }

    /**
     * Adds a new row to the <i>princial</i> table.
     *
     * @param id the principal ID
     * @param name the principal name
     * @throws Exception if an unexpected exception occurs
     */
    private void addPrincipal(long id, String name) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO principal (principal_id, principal_name) VALUES (?, ?)");

        statement.setLong(1, id);
        statement.setString(2, name);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Adds a new row to the <i>role</i> table.
     *
     * @param id the role ID
     * @param name the role name
     * @throws Exception if an unexpected exception occurs
     */
    private void addRole(long id, String name) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO role (role_id, role_name) VALUES (?, ?)");

        statement.setLong(1, id);
        statement.setString(2, name);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Adds a new row to the <i>principal_role</i> table.
     *
     * @param principal the principal ID
     * @param role the role ID
     * @throws Exception if an unexpected exception occurs
     */
    private void addPrincipalRole(long principal, long role) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO principal_role (principal_id, role_id) VALUES (?, ?)");

        statement.setLong(1, principal);
        statement.setLong(2, role);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Adds a new row to the <i>category</i> table.
     *
     * @param id the category ID
     * @param name the category name
     * @param description the description
     * @param chattable the chattable flag
     * @throws Exception if an unexpected exception occurs
     */
    private void addCategory(long id, String name, String description, boolean chattable) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO category (category_id, name, description, chattable_flag, "
                                        + "create_date, create_user, modify_date, modify_user) VALUES (?, ?, ?, ?, "
                                        + " CURRENT, USER, CURRENT, USER)");

        statement.setLong(1, id);
        statement.setString(2, name);
        statement.setString(3, description);
        statement.setString(4, chattable ? "Y" : "N");
        statement.executeUpdate();
        statement.close();
    }

    /**
     * Adds a new row to the <i>manager_category</i> table.
     *
     * @param manager the manager ID
     * @param category the category ID
     * @throws Exception if an unexpected exception occurs
     */
    private void addManagerCategory(long manager, long category) throws Exception {
        PreparedStatement statement =
            connection.prepareStatement("INSERT INTO manager_category (manager_id, category_id, create_date, "
                                        + "create_user, modify_date, modify_user) VALUES (?, ?, CURRENT, USER, "
                                        + "CURRENT, USER)");

        statement.setLong(1, manager);
        statement.setLong(2, category);
        statement.executeUpdate();
        statement.close();
    }
}
