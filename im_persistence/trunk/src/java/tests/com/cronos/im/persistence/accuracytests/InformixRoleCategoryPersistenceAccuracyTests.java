/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.accuracytests;

import java.sql.ResultSet;
import java.sql.Statement;

import com.cronos.im.persistence.rolecategories.Category;
import com.cronos.im.persistence.rolecategories.InformixRoleCategoryPersistence;
import com.cronos.im.persistence.rolecategories.User;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.datavalidator.NullValidator;
import com.topcoder.util.datavalidator.ObjectValidator;

/**
 * <p>
 * Accuracy test for <code>{@link InformixRoleCategoryPersistence}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class InformixRoleCategoryPersistenceAccuracyTests extends BaseTestCase {

    /**
     * <p>
     * Represents the InformixRoleCategoryPersistence instance used in tests.
     * </p>
     */
    private InformixRoleCategoryPersistence informixRoleCategoryPersistence;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        informixRoleCategoryPersistence = new InformixRoleCategoryPersistence();

    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixRoleCategoryPersistence#InformixRoleCategoryPersistence()}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixRoleCategoryPersistence1Accuracy() throws Exception {
        informixRoleCategoryPersistence = new InformixRoleCategoryPersistence();

        assertNotNull(informixRoleCategoryPersistence);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixRoleCategoryPersistence#InformixRoleCategoryPersistence(String)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixRoleCategoryPersistence2Accuracy() throws Exception {
        informixRoleCategoryPersistence = new InformixRoleCategoryPersistence(
            InformixRoleCategoryPersistence.DEFAULT_NAMESPACE);

        assertNotNull(informixRoleCategoryPersistence);
    }

    /**
     * <p>
     * Accuracy test for
     * <code>{@link InformixRoleCategoryPersistence#InformixRoleCategoryPersistence(DBConnectionFactory, String, ObjectValidator)}</code>
     * constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixRoleCategoryPersistence3Accuracy() throws Exception {
        DBConnectionFactory dbConnectionFactory = new DBConnectionFactoryImpl();

        // case 1
        informixRoleCategoryPersistence = new InformixRoleCategoryPersistence(dbConnectionFactory, "Informix", null);
        assertNotNull(informixRoleCategoryPersistence);

        // case 2
        informixRoleCategoryPersistence = new InformixRoleCategoryPersistence(dbConnectionFactory, "Informix",
            new NullValidator());
        assertNotNull(informixRoleCategoryPersistence);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixRoleCategoryPersistence#getUsers(String)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetUsersAccuracy() throws Exception {
        Statement stmt = getConnection().createStatement();
        // add user.
        stmt.executeUpdate("INSERT INTO principal (principal_id, principal_name) VALUES (1, \"evirn\")");
        stmt.executeUpdate("INSERT INTO principal (principal_id, principal_name) VALUES (2, \"moss\")");
        stmt.executeUpdate("INSERT INTO principal (principal_id, principal_name) VALUES (3, \"cales\")");

        // add roles
        stmt.executeUpdate("INSERT INTO role (role_id, role_name) VALUES (1, \"admin\")");
        stmt.executeUpdate("INSERT INTO role (role_id, role_name) VALUES (2, \"code\")");
        // add principal_role
        stmt.executeUpdate("INSERT INTO principal_role (principal_id, role_id) VALUES (1,1)");
        stmt.executeUpdate("INSERT INTO principal_role (principal_id, role_id) VALUES (1,2)");
        stmt.executeUpdate("INSERT INTO principal_role (principal_id, role_id) VALUES (2,1)");
        stmt.executeUpdate("INSERT INTO principal_role (principal_id, role_id) VALUES (3,2)");

        stmt.close();

        User[] users = informixRoleCategoryPersistence.getUsers("admin");

        assertNotNull(users);
        assertEquals("there should be 2 users.", 2, users.length);

        int hash = 0;
        for (int i = 0; i < users.length; i++) {
            if ("evirn".equals(users[i].getName())) {
                hash += 10;
            }

            if ("moss".equals(users[i].getName())) {
                hash += 100;
            }
        }

        assertEquals("incorrect user list.", 110, hash);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixRoleCategoryPersistence#getAllCategories()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllCategoriesAccuracy() throws Exception {
        Statement stmt = getConnection().createStatement();
        stmt
            .executeUpdate("INSERT INTO category (category_id, name, description, chattable_flag, create_date,"
                + "create_user, modify_date, modify_user) VALUES (1, \"coding\", \"Coding category\", 'Y', CURRENT, USER, CURRENT, USER)");
        stmt
            .executeUpdate("INSERT INTO category (category_id, name, description, chattable_flag, create_date,"
                + "create_user, modify_date, modify_user) VALUES (2, \"management\", \"Management category\", 'N', CURRENT, USER, CURRENT, USER)");
        stmt
            .executeUpdate("INSERT INTO category (category_id, name, description, chattable_flag, create_date,"
                + "create_user, modify_date, modify_user) VALUES (3, \"modeliing\", \"Modeliing category\", 'Y', CURRENT, USER, CURRENT, USER)");

        stmt.close();

        Category[] categories = informixRoleCategoryPersistence.getAllCategories();
        assertNotNull(categories);
        assertEquals("should 3 elements.", 3, categories.length);
        int hash = 0;
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].getId() == 1) {
                assertEquals("incorrect name", "coding", categories[i].getName());
                assertEquals("incorrect description", "Coding category", categories[i].getDescription());
                assertTrue("incorrect chattable", categories[i].getChattable());
                hash += 1;
            } else if (categories[i].getId() == 2) {
                assertEquals("incorrect name", "management", categories[i].getName());
                assertEquals("incorrect description", "Management category", categories[i].getDescription());
                assertFalse("incorrect chattable", categories[i].getChattable());
                hash += 10;
            } else if (categories[i].getId() == 3) {
                assertEquals("incorrect name", "modeliing", categories[i].getName());
                assertEquals("incorrect description", "Modeliing category", categories[i].getDescription());
                assertTrue("incorrect chattable", categories[i].getChattable());
                hash += 100;
            }
        }

        assertEquals("incorrect category list.", 111, hash);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixRoleCategoryPersistence#getCategories(boolean)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCategoriesByChattableAccuracy() throws Exception {
        Statement stmt = getConnection().createStatement();
        stmt
            .executeUpdate("INSERT INTO category (category_id, name, description, chattable_flag, create_date,"
                + "create_user, modify_date, modify_user) VALUES (1, \"coding\", \"Coding category\", 'Y', CURRENT, USER, CURRENT, USER)");
        stmt
            .executeUpdate("INSERT INTO category (category_id, name, description, chattable_flag, create_date,"
                + "create_user, modify_date, modify_user) VALUES (2, \"management\", \"Management category\", 'N', CURRENT, USER, CURRENT, USER)");
        stmt
            .executeUpdate("INSERT INTO category (category_id, name, description, chattable_flag, create_date,"
                + "create_user, modify_date, modify_user) VALUES (3, \"modeliing\", \"Modeliing category\", 'Y', CURRENT, USER, CURRENT, USER)");

        stmt.close();

        Category[] categories = informixRoleCategoryPersistence.getCategories(true);
        assertNotNull(categories);
        assertEquals("should 2 elements.", 2, categories.length);
        int hash = 0;
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].getId() == 1) {
                assertEquals("incorrect name", "coding", categories[i].getName());
                assertEquals("incorrect description", "Coding category", categories[i].getDescription());
                assertTrue("incorrect chattable", categories[i].getChattable());
                hash += 1;
            } else if (categories[i].getId() == 3) {
                assertEquals("incorrect name", "modeliing", categories[i].getName());
                assertEquals("incorrect description", "Modeliing category", categories[i].getDescription());
                assertTrue("incorrect chattable", categories[i].getChattable());
                hash += 100;
            }
        }

        assertEquals("incorrect category list.", 101, hash);

        categories = informixRoleCategoryPersistence.getCategories(false);
        assertNotNull(categories);
        assertEquals("should 1 elements.", 1, categories.length);
        assertEquals("incorrect name", "management", categories[0].getName());
        assertEquals("incorrect description", "Management category", categories[0].getDescription());
        assertFalse("incorrect chattable", categories[0].getChattable());
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixRoleCategoryPersistence#getAllCategories()}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCategoriesByManagerAccuracy() throws Exception {
        Statement stmt = getConnection().createStatement();
        // add user.
        stmt.executeUpdate("INSERT INTO principal (principal_id, principal_name) VALUES (1, \"evirn\")");
        stmt.executeUpdate("INSERT INTO principal (principal_id, principal_name) VALUES (2, \"moss\")");
        stmt.executeUpdate("INSERT INTO principal (principal_id, principal_name) VALUES (3, \"cales\")");

        // add category
        stmt
            .executeUpdate("INSERT INTO category (category_id, name, description, chattable_flag, create_date,"
                + "create_user, modify_date, modify_user) VALUES (1, \"coding\", \"Coding category\", 'Y', CURRENT, USER, CURRENT, USER)");
        stmt
            .executeUpdate("INSERT INTO category (category_id, name, description, chattable_flag, create_date,"
                + "create_user, modify_date, modify_user) VALUES (2, \"management\", \"Management category\", 'N', CURRENT, USER, CURRENT, USER)");
        stmt
            .executeUpdate("INSERT INTO category (category_id, name, description, chattable_flag, create_date,"
                + "create_user, modify_date, modify_user) VALUES (3, \"modeling\", \"Modeling category\", 'Y', CURRENT, USER, CURRENT, USER)");

        stmt.executeUpdate("INSERT INTO manager_category (manager_id, category_id, create_date, create_user,"
            + " modify_date, modify_user) VALUES (1,1, CURRENT, USER, CURRENT, USER)");

        stmt.executeUpdate("INSERT INTO manager_category (manager_id, category_id, create_date, create_user,"
            + " modify_date, modify_user) VALUES (1,2, CURRENT, USER, CURRENT, USER)");

        stmt.executeUpdate("INSERT INTO manager_category (manager_id, category_id, create_date, create_user,"
            + " modify_date, modify_user) VALUES (3,1, CURRENT, USER, CURRENT, USER)");
        stmt.close();

        Category[] categories = informixRoleCategoryPersistence.getCategories("evirn");
        assertNotNull(categories);
        assertEquals("should 2 elements.", 2, categories.length);
        int hash = 0;
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].getId() == 1) {
                assertEquals("incorrect name", "coding", categories[i].getName());
                assertEquals("incorrect description", "Coding category", categories[i].getDescription());
                assertTrue("incorrect chattable", categories[i].getChattable());
                hash += 1;
            } else if (categories[i].getId() == 2) {
                assertEquals("incorrect name", "management", categories[i].getName());
                assertEquals("incorrect description", "Management category", categories[i].getDescription());
                assertFalse("incorrect chattable", categories[i].getChattable());
                hash += 10;
            }
        }

        assertEquals("incorrect category list.", 11, hash);

        categories = informixRoleCategoryPersistence.getCategories("moss");
        assertNotNull(categories);
        assertEquals("should 0 elements.", 0, categories.length);
    }

    /**
     * <p>
     * Accuracy test for <code>{@link InformixRoleCategoryPersistence#updateCategories(String, Category[])}</code>
     * method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateCategoriesAccuracy() throws Exception {
        Statement stmt = getConnection().createStatement();
        // add user.
        stmt.executeUpdate("INSERT INTO principal (principal_id, principal_name) VALUES (1, \"evirn\")");
        stmt.executeUpdate("INSERT INTO principal (principal_id, principal_name) VALUES (2, \"moss\")");
        stmt.executeUpdate("INSERT INTO principal (principal_id, principal_name) VALUES (3, \"cales\")");

        // add category
        stmt
            .executeUpdate("INSERT INTO category (category_id, name, description, chattable_flag, create_date,"
                + "create_user, modify_date, modify_user) VALUES (1, \"coding\", \"Coding category\", 'Y', CURRENT, USER, CURRENT, USER)");
        stmt
            .executeUpdate("INSERT INTO category (category_id, name, description, chattable_flag, create_date,"
                + "create_user, modify_date, modify_user) VALUES (2, \"management\", \"Management category\", 'N', CURRENT, USER, CURRENT, USER)");
        stmt
            .executeUpdate("INSERT INTO category (category_id, name, description, chattable_flag, create_date,"
                + "create_user, modify_date, modify_user) VALUES (3, \"modeling\", \"Modeling category\", 'Y', CURRENT, USER, CURRENT, USER)");

        stmt.executeUpdate("INSERT INTO manager_category (manager_id, category_id, create_date, create_user,"
            + " modify_date, modify_user) VALUES (1, 1, CURRENT, USER, CURRENT, USER)");

        stmt.executeUpdate("INSERT INTO manager_category (manager_id, category_id, create_date, create_user,"
            + " modify_date, modify_user) VALUES (1, 2, CURRENT, USER, CURRENT, USER)");

        stmt.executeUpdate("INSERT INTO manager_category (manager_id, category_id, create_date, create_user,"
            + " modify_date, modify_user) VALUES (3, 1, CURRENT, USER, CURRENT, USER)");

        informixRoleCategoryPersistence.updateCategories("moss", new Category[] {
            new Category(1, "coding", "Coding category", true),
            new Category(2, "management", "Management category", false),
            new Category(3, "modeling", "Modeling category", true)});

        ResultSet rs = stmt.executeQuery("SELECT count(*) FROM manager_category WHERE manager_id = 2");

        assertTrue(rs.next());
        assertEquals("there should be 3 records.", 3, rs.getInt(1));
        rs.close();

        informixRoleCategoryPersistence.updateCategories("cales", new Category[] {
            new Category(2, "management", "Management category", false),
            new Category(3, "modeling", "Modeling category", true)});

        rs = stmt.executeQuery("SELECT count(*) FROM manager_category WHERE manager_id = 3");

        assertTrue(rs.next());
        assertEquals("there should be 2 records.", 2, rs.getInt(1));

        stmt.close();
    }
}
