/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.ejb;

import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.UnitTestHelper;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Date;

import javax.ejb.SessionContext;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseStatusBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ExpenseStatusBeanUnitTest extends TestCase {
    /** Default namespace used to in ejbCreate(). */
    private static final String DEFAULT_NAMESPACE = ExpenseStatusBean.class.getName();

    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** Represents the <code>SessionContext</code> instance used for testing. */
    private SessionContext context;

    /** Represents the <code>ExpenseStatusBean</code> instance used for testing. */
    private ExpenseStatusBean bean = null;

    /** Represents a valid expense entry status instance. */
    private ExpenseStatus status;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.clearConfig();
        UnitTestHelper.addConfig("DBConnectionFactory_Config.xml");
        UnitTestHelper.addConfig("ObjectFactory_Config.xml");
        UnitTestHelper.addConfig("InformixExpenseStatusDAO_Config.xml");
        UnitTestHelper.addConfig("ExpenseStatusBean_Config.xml");
        ConfigManager.getInstance().add("com.topcoder.naming.jndiutility", "JNDIUtils.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);

        connection = UnitTestHelper.getConnection();
        UnitTestHelper.prepareData(connection);

        // create bean
        UnitTestHelper.deployEJB();
        context = new MockSessionContext();
        bean = new ExpenseStatusBean();
        bean.ejbCreate();
        bean.setSessionContext(context);

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
            UnitTestHelper.clearConfig();
            UnitTestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        UnitTestHelper.undeployEJB();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ExpenseStatusBean()</code>. No exception is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseStatusBean_Accuracy() throws Exception {
        assertNotNull("The ExpenseStatusBean instance should be created.", bean);
    }

    /**
     * <p>
     * Accuracy test for the method <code>ejbCreate()</code> when the namespace which hold the property of jndi_context
     * does not exist, the default context of JNDIUtils should be used.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbCreate_MissingJndiContextNamespace() throws Exception {
        ConfigManager.getInstance().removeNamespace(DEFAULT_NAMESPACE);

        bean.ejbCreate();
        assertNotNull("The bean should be created successfully.",
            UnitTestHelper.getPrivateField(bean.getClass(), bean, "expenseStatusDAO"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>ejbCreate()</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbCreate_Accuracy() throws Exception {
        assertNotNull("The bean should be created successfully.",
            UnitTestHelper.getPrivateField(bean.getClass(), bean, "expenseStatusDAO"));
    }

    /**
     * Accuracy test for the method <code>ejbRemove()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbRemove_Accuracy() throws Exception {
        bean.ejbRemove();
    }

    /**
     * Accuracy test for the method <code>ejbActivate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbActivate_Accuracy() throws Exception {
        bean.ejbActivate();
    }

    /**
     * Accuracy test for the method <code>ejbPassivate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbPassivate_Accuracy() throws Exception {
        bean.ejbPassivate();
    }

    /**
     * Accuracy test for the method <code>setSessionContext(SessionContext)</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSetSessionContext_Accuracy() throws Exception {
        bean.setSessionContext(context);
        assertEquals("The context should be set properly.", context,
            UnitTestHelper.getPrivateField(bean.getClass(), bean, "sessionContext"));
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
            bean.addStatus(null);
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
            bean.addStatus(status);
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
            bean.addStatus(status);
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
            bean.addStatus(status);
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
            bean.addStatus(status);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addStatus</code> when the description is not set. No exception is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatus_DescriptionNull() throws Exception {
        status.setDescription(null);

        bean.addStatus(status);

        ExpenseStatus retrieved = bean.retrieveStatus(status.getId());
        UnitTestHelper.assertEquals(status, retrieved);
    }

    /**
     * <p>
     * Tests accuracy of <code>addStatus</code> when ID is -1. A new ID is generated. A new record should be inserted
     * into the database. The creation date and modification date should be set the same. The method should return
     * <code>true</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatus_Accuracy() throws Exception {
        assertTrue("The status should be added.", bean.addStatus(status));

        // Verify instance
        assertTrue("A ID should be generated.", status.getId() > 0);
        assertNotNull("The creation date should be set.", status.getCreationDate());
        assertNotNull("The modification date should be set.", status.getModificationDate());
        assertEquals("The creation date and modification date should be the same.", status.getCreationDate(),
            status.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_status");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", status.getId(), resultSet.getLong("expense_status_id"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("description"));
            UnitTestHelper.assertEquals("The creation date should be correct.", status.getCreationDate(),
                resultSet.getDate("creation_date"));
            UnitTestHelper.assertEquals("The modification date should be correct.", status.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify",
                    resultSet.getString("modification_user"));

            assertFalse("Only one record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addStatus</code> when ID is not -1. The ID should be used. A new record should be
     * inserted into the database. The creation date and modification date should be set the same. The method should
     * return <code>true</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatus_IDSetAccuracy() throws Exception {
        status.setId(5);

        assertTrue("The status should be added.", bean.addStatus(status));

        // Verify instance
        assertEquals("The ID should be used.", 5, status.getId());
        assertNotNull("The creation date should be set.", status.getCreationDate());
        assertNotNull("The modification date should be set.", status.getModificationDate());
        assertEquals("The creation date and modification date should be the same.", status.getCreationDate(),
            status.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_status");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", status.getId(), resultSet.getLong("expense_status_id"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("description"));
            UnitTestHelper.assertEquals("The creation date should be correct.", status.getCreationDate(),
                resultSet.getDate("creation_date"));
            UnitTestHelper.assertEquals("The modification date should be correct.", status.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify",
                    resultSet.getString("modification_user"));

            assertFalse("Only one record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addStatus</code> when ID already exists in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddStatus_ExistAccuracy() throws Exception {
        status.setId(5);
        bean.addStatus(status);
        status.setCreationDate(null);
        status.setModificationDate(null);

        // Add again, should return false.
        assertFalse("The ID exists in database.", bean.addStatus(status));
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteStatus</code> when ID does not exist in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteStatus_IDNotExistAccuracy() throws Exception {
        assertFalse("The ID does not exist in database.", bean.deleteStatus(5));
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteStatus</code> when ID exists in database. The method should return
     * <code>true</code>. The record should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteStatus_Accuracy() throws Exception {
        bean.addStatus(status);

        // Delete
        assertTrue("The ID exists in database, should be deleted.", bean.deleteStatus(status.getId()));

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_status");

            assertFalse("No record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteAllStatuses</code>. All records should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllStatuses_Accuracy() throws Exception {
        // Add two records
        status.setId(1);
        bean.addStatus(status);

        status.setCreationDate(null);
        status.setModificationDate(null);
        status.setId(2);
        bean.addStatus(status);

        // Delete
        bean.deleteAllStatuses();

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_status");

            assertFalse("No record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
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
            bean.updateStatus(null);
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
            bean.updateStatus(status);
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
            bean.updateStatus(status);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateStatus</code> when the description is not set. No exception is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatus_DescriptionNull() throws Exception {
        status.setId(5);
        bean.addStatus(status);

        ExpenseStatus update = new ExpenseStatus(5);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");

        assertTrue("The record should be updated.", bean.updateStatus(update));

        // Verify instance
        assertNull("The creation date should not be modified.", update.getCreationDate());
        assertNotNull("The modification date should be set.", update.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_status");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", update.getId(), resultSet.getLong("expense_status_id"));
            assertEquals("The description should be correct.", null, resultSet.getString("description"));
            UnitTestHelper.assertEquals("The creation date should not be modified.", status.getCreationDate(),
                resultSet.getDate("creation_date"));
            UnitTestHelper.assertEquals("The modification date should be correct.", update.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should not be modified.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify2",
                    resultSet.getString("modification_user"));

            assertFalse("Only one record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateStatus</code> when ID exists in database. The method should return
     * <code>true</code>. The modification date should be modified. The record in database should be updated. The
     * creation user and creation date should not be modified.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatus_Accuracy() throws Exception {
        status.setId(5);
        bean.addStatus(status);

        ExpenseStatus update = new ExpenseStatus(5);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");

        assertTrue("The record should be updated.", bean.updateStatus(update));

        // Verify instance
        assertNull("The creation date should not be modified.", update.getCreationDate());
        assertNotNull("The modification date should be set.", update.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_status");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", update.getId(), resultSet.getLong("expense_status_id"));
            assertEquals("The description should be correct.", "Modified", resultSet.getString("description"));
            UnitTestHelper.assertEquals("The creation date should not be modified.", status.getCreationDate(),
                resultSet.getDate("creation_date"));
            UnitTestHelper.assertEquals("The modification date should be correct.", update.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should not be modified.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.", "Modify2",
                    resultSet.getString("modification_user"));

            assertFalse("Only one record should exist.", resultSet.next());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateStatus</code> when ID does not exist in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateStatus_IDNotExistAccuracy() throws Exception {
        status.setId(5);

        assertFalse("The record should not be updated.", bean.updateStatus(status));
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveStatus</code> when ID does not exist in database. <code>null</code> should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveStatus_IDNotExistAccuracy() throws Exception {
        assertNull("The ID does not exist in database, null should return.", bean.retrieveStatus(5));
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveStatus</code> when ID exists in database. The correct instance should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveStatus_Accuracy() throws Exception {
        bean.addStatus(status);

        ExpenseStatus retrieved = bean.retrieveStatus(status.getId());

        UnitTestHelper.assertEquals(status, retrieved);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllStatuses</code> when no record exists in database. An empty list should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllStatuses_NoRecordAccuracy() throws Exception {
        assertTrue("The retrieved list should be empty.", bean.retrieveAllStatuses().length == 0);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllStatuses</code>. The correct list of instances should be returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllStatuses_Accuracy() throws Exception {
        ExpenseStatus[] expected = new ExpenseStatus[5];

        // Add 5 instances
        for (int i = 0; i < expected.length; ++i) {
            status = new ExpenseStatus(i + 1);
            status.setCreationUser("Create" + i);
            status.setModificationUser("Modify" + i);
            status.setDescription("Description" + i);

            bean.addStatus(status);
            expected[i] = status;
        }

        ExpenseStatus[] actual = bean.retrieveAllStatuses();

        // Verify
        UnitTestHelper.assertEquals(expected, actual);
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
            bean.searchEntries(null);
            fail("The criteria is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test <code>searchEntries(Criteria criteria)</code>, the status matched should be returned.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchEntries_Accuracy() throws Exception {
        status = new ExpenseStatus();
        status.setDescription("search");
        status.setCreationUser("admin");
        status.setModificationUser("Modify");
        bean.addStatus(status);
        status = new ExpenseStatus();
        status.setDescription("search");
        status.setCreationUser("client");
        status.setModificationUser("Modify");
        bean.addStatus(status);

        Criteria criteria = FieldMatchCriteria.getExpenseStatusCreationUserMatchCriteria("admin");
        ExpenseStatus[] statuses = bean.searchEntries(criteria);
        assertEquals("One record should be matched", 1, statuses.length);
    }
}
