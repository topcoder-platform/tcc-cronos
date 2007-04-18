/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.ejb;

import com.topcoder.timetracker.entry.expense.ExpenseType;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.UnitTestHelper;
import com.topcoder.timetracker.entry.expense.criteria.CompositeCriteria;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldLikeCriteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Date;

import javax.ejb.SessionContext;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseTypeBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ExpenseTypeBeanUnitTest extends TestCase {
    /** Default namespace used to in ejbCreate(). */
    private static final String DEFAULT_NAMESPACE = ExpenseTypeBean.class.getName();

    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** Represents the <code>SessionContext</code> instance used for testing. */
    private SessionContext context;

    /** Represents the <code>ExpenseTypeBean</code> instance used for testing. */
    private ExpenseTypeBean bean = null;

    /** Represents a valid expense entry type instance. */
    private ExpenseType type;

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
        UnitTestHelper.addConfig("InformixExpenseTypeDAO_Config.xml");
        UnitTestHelper.addConfig("ExpenseTypeBean_Config.xml");
        ConfigManager.getInstance().add("com.topcoder.naming.jndiutility", "JNDIUtils.properties",
            ConfigManager.CONFIG_PROPERTIES_FORMAT);

        connection = UnitTestHelper.getConnection();
        UnitTestHelper.prepareData(connection);

        // create bean
        UnitTestHelper.deployEJB();
        context = new MockSessionContext();
        bean = new ExpenseTypeBean();
        bean.ejbCreate();
        bean.setSessionContext(context);

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
     * Accuracy test for the constructor <code>ExpenseTypeBean()</code>. No exception is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testExpenseTypeBean_Accuracy() throws Exception {
        assertNotNull("The ExpenseTypeBean instance should be created.", bean);
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
            UnitTestHelper.getPrivateField(bean.getClass(), bean, "expenseTypeDAO"));
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
            UnitTestHelper.getPrivateField(bean.getClass(), bean, "expenseTypeDAO"));
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
     * Tests <code>addType</code> when the given expense type is <code>null</code>. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddType_NullType() throws Exception {
        try {
            bean.addType(null);
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
            bean.addType(type);
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
            bean.addType(type);
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
            bean.addType(type);
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
            bean.addType(type);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>addType</code> when the description is not set. No exception is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddType_DescriptionNull() throws Exception {
        type.setDescription(null);

        bean.addType(type);

        ExpenseType retrieved = bean.retrieveType(type.getId());
        UnitTestHelper.assertEquals(type, retrieved);
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
            bean.addType(type);
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
            bean.addType(type);
            fail("The company id does not exist, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>addType</code> when ID is -1. A new ID is generated. A new record should be inserted
     * into the database. The creation date and modification date should be set the same. The method should return
     * <code>true</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddType_Accuracy() throws Exception {
        assertTrue("The type should be added.", bean.addType(type));

        // Verify instance
        assertTrue("A ID should be generated.", type.getId() > 0);
        assertNotNull("The creation date should be set.", type.getCreationDate());
        assertNotNull("The modification date should be set.", type.getModificationDate());
        assertEquals("The creation date and modification date should be the same.", type.getCreationDate(),
            type.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_type");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", type.getId(), resultSet.getLong("expense_type_id"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("description"));
            UnitTestHelper.assertEquals("The creation date should be correct.", type.getCreationDate(),
                resultSet.getDate("creation_date"));
            UnitTestHelper.assertEquals("The modification date should be correct.", type.getModificationDate(),
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
     * Tests accuracy of <code>addType</code> when ID is not -1. The ID should be used. A new record should be inserted
     * into the database. The creation date and modification date should be set the same. The method should return
     * <code>true</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddType_IDSetAccuracy() throws Exception {
        type.setId(5);

        assertTrue("The type should be added.", bean.addType(type));

        // Verify instance
        assertEquals("The ID should be used.", 5, type.getId());
        assertNotNull("The creation date should be set.", type.getCreationDate());
        assertNotNull("The modification date should be set.", type.getModificationDate());
        assertEquals("The creation date and modification date should be the same.", type.getCreationDate(),
            type.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_type");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", type.getId(), resultSet.getLong("expense_type_id"));
            assertEquals("The description should be correct.", "Description", resultSet.getString("description"));
            UnitTestHelper.assertEquals("The creation date should be correct.", type.getCreationDate(),
                resultSet.getDate("creation_date"));
            UnitTestHelper.assertEquals("The modification date should be correct.", type.getModificationDate(),
                resultSet.getDate("modification_date"));
            assertEquals("The creation user should be correct.", "Create", resultSet.getString("creation_user"));
            assertEquals("The modification user should be correct.",
                    "Modify", resultSet.getString("modification_user"));

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
     * Tests accuracy of <code>addType</code> when ID already exists in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAddType_ExistAccuracy() throws Exception {
        type.setId(5);
        bean.addType(type);
        type.setCreationDate(null);
        type.setModificationDate(null);

        // Add again, should return false.
        assertFalse("The ID exists in database.", bean.addType(type));
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteType</code> when ID does not exist in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteType_IDNotExistAccuracy() throws Exception {
        assertFalse("The ID does not exist in database.", bean.deleteType(5));
    }

    /**
     * <p>
     * Tests accuracy of <code>deleteType</code> when ID exists in database. The method should return
     * <code>true</code>. The record should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteType_Accuracy() throws Exception {
        bean.addType(type);

        // Delete
        assertTrue("The ID exists in database, should be deleted.", bean.deleteType(type.getId()));

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_type");

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
     * Tests accuracy of <code>deleteAllTypes</code>. All records should be deleted from database.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testDeleteAllTypes_Accuracy() throws Exception {
        // Add two records
        type.setId(1);
        bean.addType(type);

        type.setCreationDate(null);
        type.setModificationDate(null);
        type.setId(2);
        bean.addType(type);

        // Delete
        bean.deleteAllTypes();

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_type");

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
     * Tests <code>updateType</code> when the given expense type is <code>null</code>. Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateType_NullType() throws Exception {
        try {
            bean.updateType(null);
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
            bean.updateType(type);
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
            bean.updateType(type);
            fail("The modification user is null, should throw InsufficientDataException.");
        } catch (InsufficientDataException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests <code>updateType</code> when the description is not set. No exception is expected.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateType_DescriptionNull() throws Exception {
        type.setId(5);
        bean.addType(type);

        ExpenseType update = new ExpenseType(5);
        update.setCompanyId(1);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");

        assertTrue("The record should be updated.", bean.updateType(update));

        // Verify instance
        assertNull("The creation date should not be modified.", update.getCreationDate());
        assertNotNull("The modification date should be set.", update.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_type");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", update.getId(), resultSet.getLong("expense_type_id"));
            assertEquals("The description should be correct.", null, resultSet.getString("description"));
            UnitTestHelper.assertEquals("The creation date should not be modified.", type.getCreationDate(),
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
            bean.updateType(type);
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
        bean.addType(type);
        type.setCompanyId(10);

        try {
            bean.updateType(type);
            fail("The company id does not exist, should throw PersistenceException.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>updateType</code> when ID exists in database. The method should return
     * <code>true</code>. The modification date should be modified. The record in database should be updated. The
     * creation user and creation date should not be modified.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateType_Accuracy() throws Exception {
        type.setId(5);
        bean.addType(type);

        ExpenseType update = new ExpenseType(5);
        update.setCompanyId(1);
        update.setCreationUser("Create2");
        update.setModificationUser("Modify2");
        update.setDescription("Modified");

        assertTrue("The record should be updated.", bean.updateType(update));

        // Verify instance
        assertNull("The creation date should not be modified.", update.getCreationDate());
        assertNotNull("The modification date should be set.", update.getModificationDate());

        // Verify record in database
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM expense_type");

            assertTrue("A record should exist.", resultSet.next());

            assertEquals("The ID should be correct.", update.getId(), resultSet.getLong("expense_type_id"));
            assertEquals("The description should be correct.", "Modified", resultSet.getString("description"));
            UnitTestHelper.assertEquals("The creation date should not be modified.", type.getCreationDate(),
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
     * Tests accuracy of <code>updateType</code> when ID does not exist in database. The method should return
     * <code>false</code>.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testUpdateType_IDNotExistAccuracy() throws Exception {
        type.setId(5);

        assertFalse("The record should not be updated.", bean.updateType(type));
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveType</code> when ID does not exist in database. <code>null</code> should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveType_IDNotExistAccuracy() throws Exception {
        assertNull("The ID does not exist in database, null should return.", bean.retrieveType(5));
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveType</code> when ID exists in database. The correct instance should be returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveType_Accuracy() throws Exception {
        bean.addType(type);

        ExpenseType retrieved = bean.retrieveType(type.getId());

        UnitTestHelper.assertEquals(type, retrieved);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllTypes</code> when no record exists in database. An empty list should be
     * returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllTypes_NoRecordAccuracy() throws Exception {
        assertTrue("The retrieved list should be empty.", bean.retrieveAllTypes().length == 0);
    }

    /**
     * <p>
     * Tests accuracy of <code>retrieveAllTypes</code>. The correct list of instances should be returned.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRetrieveAllTypes_Accuracy() throws Exception {
        ExpenseType[] expected = new ExpenseType[5];

        // Add 5 instances
        for (int i = 0; i < expected.length; ++i) {
            type = new ExpenseType(i + 1);
            type.setCompanyId(1);
            type.setCreationUser("Create" + i);
            type.setModificationUser("Modify" + i);
            type.setDescription("Description" + i);

            bean.addType(type);
            expected[i] = type;
        }

        ExpenseType[] actual = bean.retrieveAllTypes();

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
     * Test <code>searchEntries(Criteria criteria)</code>, the types matched should be returned.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchEntries_Accuracy() throws Exception {
        for (int i = 0; i < 5; ++i) {
            type = new ExpenseType(i + 1);
            type.setCompanyId(1);
            type.setCreationUser("Create" + i);
            type.setModificationUser("Modify" + i);

            if ((i % 3) == 0) {
                type.setDescription("Description" + i);
            } else {
                type.setDescription("xxxx");
            }

            bean.addType(type);
        }

        Criteria c1 = FieldMatchCriteria.getExpenseTypeCompanyIdMatchCriteria(1);
        Criteria c2 = FieldLikeCriteria.getExpenseTypeDescriptionContainsCriteria("Des");
        Criteria criteria = CompositeCriteria.getAndCompositeCriteria(new Criteria[] {c1, c2});

        ExpenseType[] types = bean.searchEntries(criteria);
        assertEquals("Failed to get the expected result.", 2, types.length);
    }
}
