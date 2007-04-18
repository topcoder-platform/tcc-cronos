/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ejb.SessionContext;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.ExpenseType;

import junit.framework.TestCase;

/**
 * The abstract testcase for persistence functionality.
 *
 * @author brain_cn
 * @version 1.0
 */
public class PersistenceTestCase extends TestCase {
    /** Represents the database connection to access database. */
    protected Connection connection = null;
    
    /** Represents a valid expense entry instance. */
    protected ExpenseEntry entry;

    /** Represents a valid expense entries array instance. */
    protected ExpenseEntry[] entries;

    /** Represents the <code>SessionContext</code> instance used for testing. */
    protected SessionContext context;

    /**
     * <p>
     * Sets up the test environment. Valid configurations are loaded. A valid manager is created. The data table is
     * truncated. A valid expense entry entry is created. One expense type and one expense status are added into the
     * database.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
    	ConfigHelper.loadNamespaces();
    	TestHelper.deployEJB();

        TestHelper.initDatabase();
        ExpenseType type = TestHelper.createTypeInstance();
        ExpenseStatus status = TestHelper.createStatusInstance();
        entry = TestHelper.prepareExpenseEntry(type, status);
        
        entries = new ExpenseEntry[3];
        TestHelper.prepareExpenseEntries(entries, type, status);

        initConnection();
    }

    /**
     * <p>
     * Cleans up the test environment. The configurations are removed. The data table is truncated.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
    	TestHelper.undeployEJB();
        TestHelper.clearDatabase();
    	ConfigHelper.releaseNamespaces();
        closeConnection();
    }

    /**
     * Initialize the connection field.
     *
     * @throws Exception to JUnit
     */
    private void initConnection() throws Exception {
        if (connection == null) {
            connection = TestHelper.getConnection();
        }
    }

    /**
     * Close the connection field.
     *
     * @throws Exception to JUnit
     */
    private void closeConnection() throws Exception {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * Asserts the entry does not exist in database.
     *
     * @param entry the expense entry to assert
     *
     * @throws Exception to JUnit
     */
    protected void assertExpenseEntryNotExist(ExpenseEntry entry)
        throws Exception {
        // Verify record in database
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM expense_entry WHERE expense_entry_id=?");
            ps.setLong(1, entry.getId());
            resultSet = ps.executeQuery();

            assertFalse("The record should not exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * Asserts the entry is consistent with the data in database.
     *
     * @param entry the expense entry to assert
     *
     * @throws Exception to JUnit
     */
    protected void assertExpenseEntryExist(ExpenseEntry entry)
        throws Exception {
        // Verify record in database
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM expense_entry WHERE expense_entry_id=?");
            ps.setLong(1, entry.getId());
            resultSet = ps.executeQuery();

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", entry.getId(), resultSet.getInt("expense_entry_id"));
            assertEquals("The description should be correct.", entry.getDescription(),
                resultSet.getString("Description"));
            TestHelper.assertEquals("The creation date should be correct.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            TestHelper.assertEquals("The modification date should be correct.", entry.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should be correct.", entry.getCreationUser(),
                resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", entry.getModificationUser(),
                resultSet.getString("modification_user"));
            assertEquals("The amount of money should be correct.", entry.getAmount().doubleValue(),
                resultSet.getDouble("Amount"), 1E-9);
            assertEquals("The billable flag should be correct.", entry.isBillable() ? 1 : 0,
                resultSet.getShort("Billable"));
            assertEquals("The expense type ID should be correct.", entry.getExpenseType().getId(),
                resultSet.getInt("expense_type_id"));
            assertEquals("The expense status ID should be correct.", entry.getStatus().getId(),
                resultSet.getInt("expense_status_id"));
            assertEquals("The company id should be same", entry.getCompanyId(), resultSet.getInt("company_id"));
            TestHelper.assertEquals("The date should be correct.", entry.getDate(), resultSet.getDate("entry_date"));
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>
     * Gets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance.
     * </p>
     *
     * @param type the class which the private field belongs to.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be retrieved.
     *
     * @return the value of the private field or <code>null</code> if any error occurs.
     */
    protected static Object getField(Object instance, String name) {
        Field field = null;
        Object obj = null;
        boolean isAccess = true;

        try {
            // Get the reflection of the field and get the value
            field = instance.getClass().getDeclaredField(name);
            isAccess = field.isAccessible();
            field.setAccessible(true);
            obj = field.get(instance);
        } catch (IllegalAccessException e) {
        } catch (NoSuchFieldException e) {
        } finally {
            if (field != null) {
                // set the old status
                field.setAccessible(isAccess);
            }
        }

        return obj;
    }

	/**
	 * Creates the test ExpenseType object.
	 * 
	 * @param typeId the id.
	 * @param companyId the company id.
	 * @return the ExpenseType object.
	 */
    protected ExpenseType createType(int typeId, int companyId) {
		ExpenseType type = new ExpenseType(typeId);
		type.setDescription("desc");
		type.setCompanyId(companyId);
		type.setCreationUser("user");
		type.setModificationUser("user2");

		return type;
	}

    /**
     * Creates ExpenseStatus instance.
     *
     * @param id the id of status.
     * @return the ExpenseStatus object.
     */
    protected ExpenseStatus createStatus(int id) {
        ExpenseStatus status = new ExpenseStatus(id);
        status.setDescription("desc");
        status.setCreationUser("user" + id);
        status.setModificationUser("user2" + id);

        return status;
    }
}
