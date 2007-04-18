/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.io.File;
import java.sql.Connection;
import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseType;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseTypeDAO;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

/**
 * <p>
 * Failure test cases of <code>InformixExpenseTypeDAO</code> class.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class InformixExpenseTypeDAOFailureTests extends TestCase {
	/**
	 * The path of the configuration files.
	 */
	private static final String PATH = "FailureTests" + File.separatorChar;

    /**
     * Represents the <code>InformixExpenseTypeDAO</code> instance used for testing.
     */
    private InformixExpenseTypeDAO dao = null;

    /**
     * Represents the connection instance for testing.
     */
    private Connection connection = null;

    /**
     * Represents a valid expense entry type instance.
     */
    private ExpenseType type;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig(PATH + "DBConnectionFactory_Config.xml");
        TestHelper.addConfig(PATH + "ObjectFactory_Config.xml");
        TestHelper.addConfig(PATH + "InformixExpenseTypeDAO_Config.xml");

        connection = TestHelper.getConnection();
        TestHelper.clearDatabase(connection);
        TestHelper.prepareData(connection);

        // create the testing instance
        dao = new InformixExpenseTypeDAO(InformixExpenseTypeDAO.class.getName());
        type = new ExpenseType();
        type.setCompanyId(1);
        type.setDescription("Description");
        type.setCreationUser("Create");
        type.setModificationUser("Modify");
        type.setActive(true);
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        try {
            TestHelper.clearConfig();
            TestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseTypeDAO(String)</code> when the given namespace is <code>null</code>.
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseTypeDAO_StringNullNamespace() throws Exception {
        try {
            new InformixExpenseTypeDAO(null);
            fail("The namespace is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseTypeDAO(String)</code> when the given namespace is empty string. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseTypeDAO_StringEmptyNamespace1() throws Exception {
        try {
            new InformixExpenseTypeDAO("");
            fail("The namespace is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseTypeDAO(String)</code> when the given namespace is empty string. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseTypeDAO_StringEmptyNamespace2() throws Exception {
        try {
            new InformixExpenseTypeDAO("   \n ");
            fail("The namespace is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseTypeDAO(String)</code> when the given namespace does not exist. Expect
     * ConfigurationException.
     * </p>
     */
    public void testInformixExpenseTypeDAO_StringNotExistNamespace() {
        try {
            new InformixExpenseTypeDAO("Unknown");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseTypeDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     */
    public void testInformixExpenseTypeDAO_InvalidNamespace1() {
        try {
            new InformixExpenseTypeDAO("InformixExpenseTypeDAO.invalid1");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseTypeDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     */
    public void testInformixExpenseTypeDAO_InvalidNamespace2() {
        try {
            new InformixExpenseTypeDAO("InformixExpenseTypeDAO.invalid2");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseTypeDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     */
    public void testInformixExpenseTypeDAO_InvalidNamespace3() {
        try {
            new InformixExpenseTypeDAO("InformixExpenseTypeDAO.invalid3");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseTypeDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     */
    public void testInformixExpenseTypeDAO_InvalidNamespace4() {
        try {
            new InformixExpenseTypeDAO("InformixExpenseTypeDAO.invalid4");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseTypeDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     */
    public void testInformixExpenseTypeDAO_InvalidNamespace5() {
        try {
            new InformixExpenseTypeDAO("InformixExpenseTypeDAO.invalid5");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseTypeDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     */
    public void testInformixExpenseTypeDAO_InvalidNamespace6() {
        try {
            new InformixExpenseTypeDAO("InformixExpenseTypeDAO.invalid6");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseTypeDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     */
    public void testInformixExpenseTypeDAO_InvalidNamespace7() {
        try {
            new InformixExpenseTypeDAO("InformixExpenseTypeDAO.invalid7");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when the given expense type is <code>null</code>. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddType_NullType() throws Exception {
        try {
            dao.addType(null);
            fail("The expense type is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when the creation date is already set. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddType_CreationDateNotNull() throws Exception {
        type.setCreationDate(new Date());

        try {
            dao.addType(type);
            fail("The creation date is not null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when the modification date is already set. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddType_ModificationDateNotNull() throws Exception {
        type.setModificationDate(new Date());

        try {
            dao.addType(type);
            fail("The modification date is not null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when the creation user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddType_CreationUserNull() throws Exception {
        type.setCreationUser(null);

        try {
            dao.addType(type);
            fail("The creation user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when the modification user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddType_ModificationUserNull() throws Exception {
        type.setModificationUser(null);

        try {
            dao.addType(type);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when the company id is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddType_CompanyIdNotSet() throws Exception {
        type = new ExpenseType();
        type.setCreationUser("Create");
        type.setDescription("Description");
        type.setModificationUser("Modify");

        try {
            dao.addType(type);
            fail("The company id is not set, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when the company id does not exist. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddType_CompanyIdNotExist() throws Exception {
        type.setCompanyId(10);

        try {
            dao.addType(type);
            fail("The company id does not exist, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateType</code> when the given expense type is <code>null</code>. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateType_NullType() throws Exception {
        try {
            dao.updateType(null);
            fail("The expense type is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateType</code> when the creation user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateType_CreationUserNull() throws Exception {
        type.setCreationUser(null);

        try {
            dao.updateType(type);
            fail("The creation user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateType</code> when the modification user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateType_ModificationUserNull() throws Exception {
        type.setModificationUser(null);

        try {
            dao.updateType(type);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateType</code> when the company id is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateType_CompanyIdNotSet() throws Exception {
        type = new ExpenseType(5);
        type.setCreationUser("Create");
        type.setDescription("Description");
        type.setModificationUser("Modify");

        try {
            dao.updateType(type);
            fail("The company id is not set, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateType</code> when the company id does not exist. Expect PersistenceException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateType_CompanyIdNotExist() throws Exception {
        dao.addType(type);
        type.setCompanyId(10);

        try {
            dao.updateType(type);
            fail("The company id does not exist, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>searchEntries(Criteria)</code> when the given criteria is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSearchEntries_NullCriteria() throws Exception {
        try {
            dao.searchEntries(null);
            fail("The criteria is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
