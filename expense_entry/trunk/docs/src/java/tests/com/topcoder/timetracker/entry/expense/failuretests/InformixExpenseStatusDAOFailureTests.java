/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.io.File;
import java.sql.Connection;
import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseStatusDAO;

/**
 * <p>
 * Failure test cases of <code>InformixExpenseStatusDAO</code> class.
 * </p>
 * @author myxgyy
 * @version 3.2
 */
public class InformixExpenseStatusDAOFailureTests extends TestCase {
	/**
	 * The path of the configuration files.
	 */
	private static final String PATH = "FailureTests" + File.separatorChar;

    /**
     * Represents the <code>InformixExpenseStatusDAO</code> instance used for testing.
     */
    private InformixExpenseStatusDAO dao = null;

    /**
     * Represents the connection instance for testing.
     */
    private Connection connection = null;

    /**
     * Represents a valid expense entry status instance.
     */
    private ExpenseStatus status;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace
     * will be loaded.
     * </p>
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.addConfig(PATH + "DBConnectionFactory_Config.xml");
        TestHelper.addConfig(PATH + "ObjectFactory_Config.xml");
        TestHelper.addConfig(PATH + "InformixExpenseStatusDAO_Config.xml");

        connection = TestHelper.getConnection();
        TestHelper.prepareData(connection);

        // create the testing instance
        dao = new InformixExpenseStatusDAO(InformixExpenseStatusDAO.class.getName());

        status = new ExpenseStatus();
        status.setDescription("Description");
        status.setCreationUser("Create");
        status.setModificationUser("Modify");
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
     * Tests constructor <code>InformixExpenseStatusDAO(String)</code> when the given namespace is <code>null</code>.
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseStatusDAO_StringNullNamespace() throws Exception {
        try {
            new InformixExpenseStatusDAO(null);
            fail("The namespace is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseStatusDAO(String)</code> when the given namespace is empty string. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseStatusDAO_StringEmptyNamespace1() throws Exception {
        try {
            new InformixExpenseStatusDAO("");
            fail("The namespace is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseStatusDAO(String)</code> when the given namespace is empty string. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixExpenseStatusDAO_StringEmptyNamespace2() throws Exception {
        try {
            new InformixExpenseStatusDAO("   \n ");
            fail("The namespace is empty string, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseStatusDAO(String)</code> when the given namespace does not exist. Expect
     * ConfigurationException.
     * </p>
     */
    public void testInformixExpenseStatusDAO_StringNotExistNamespace() {
        try {
            new InformixExpenseStatusDAO("Unknown");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseStatusDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     */
    public void testInformixExpenseStatusDAO_InvalidNamespace1() {
        try {
            new InformixExpenseStatusDAO("InformixExpenseStatusDAO.invalid1");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseStatusDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     */
    public void testInformixExpenseStatusDAO_InvalidNamespace2() {
        try {
            new InformixExpenseStatusDAO("InformixExpenseStatusDAO.invalid2");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseStatusDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     */
    public void testInformixExpenseStatusDAO_InvalidNamespace3() {
        try {
            new InformixExpenseStatusDAO("InformixExpenseStatusDAO.invalid3");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseStatusDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     */
    public void testInformixExpenseStatusDAO_InvalidNamespace4() {
        try {
            new InformixExpenseStatusDAO("InformixExpenseStatusDAO.invalid4");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseStatusDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     */
    public void testInformixExpenseStatusDAO_InvalidNamespace5() {
        try {
            new InformixExpenseStatusDAO("InformixExpenseStatusDAO.invalid5");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseStatusDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     */
    public void testInformixExpenseStatusDAO_InvalidNamespace6() {
        try {
            new InformixExpenseStatusDAO("InformixExpenseStatusDAO.invalid6");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>InformixExpenseStatusDAO(String)</code> when the given namespace is invalid.
     * Expect ConfigurationException.
     * </p>
     */
    public void testInformixExpenseStatusDAO_InvalidNamespace7() {
        try {
            new InformixExpenseStatusDAO("InformixExpenseStatusDAO.invalid7");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addStatus</code> when the given expense status is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatus_NullStatus() throws Exception {
        try {
            dao.addStatus(null);
            fail("The expense status is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addStatus</code> when the creation date is already set. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatus_CreationDateNotNull() throws Exception {
        status.setCreationDate(new Date());

        try {
            dao.addStatus(status);
            fail("The creation date is not null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addStatus</code> when the modification date is already set. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatus_ModificationDateNotNull() throws Exception {
        status.setModificationDate(new Date());

        try {
            dao.addStatus(status);
            fail("The modification date is not null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addStatus</code> when the creation user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatus_CreationUserNull() throws Exception {
        status.setCreationUser(null);

        try {
            dao.addStatus(status);
            fail("The creation user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addStatus</code> when the modification user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatus_ModificationUserNull() throws Exception {
        status.setModificationUser(null);

        try {
            dao.addStatus(status);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateStatus</code> when the given expense status is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatus_NullStatus() throws Exception {
        try {
            dao.updateStatus(null);
            fail("The expense status is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateStatus</code> when the creation user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatus_CreationUserNull() throws Exception {
        status.setCreationUser(null);

        try {
            dao.updateStatus(status);
            fail("The creation user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateStatus</code> when the modification user is not set. Expect InsufficientDataException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatus_ModificationUserNull() throws Exception {
        status.setModificationUser(null);

        try {
            dao.updateStatus(status);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
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
