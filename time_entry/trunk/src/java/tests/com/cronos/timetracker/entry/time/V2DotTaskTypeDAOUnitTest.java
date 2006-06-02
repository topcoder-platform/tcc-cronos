/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.cronos.timetracker.entry.time.search.ComparisonExpression;
import com.cronos.timetracker.entry.time.search.SearchExpression;
import com.cronos.timetracker.entry.time.search.SubstringExpression;
import com.cronos.timetracker.entry.time.search.TaskTypeCriteria;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * <p>
 * The unit test tests for TaskTypeDAO.
 * Mainly it tests the CRUD operations and search functionality.
 * </p>
 *
 * @author arylio
 * @value 2.0
 * @since 2.0
 */
public class V2DotTaskTypeDAOUnitTest extends TestCase {
    /**
     * <p>
     * The name of the Connection to fetch from the DBConnectionFactory.
     * </p>
     */
    private static final String CONNAME = "informix";

    /**
     * <p>
     * Namespace to be used in the DBConnection Factory component to load the configuration.
     * </p>
     */
    private static final String NAMESPACE = "com.cronos.timetracker.entry.time.myconfig";

    /**
     * <p>
     * An instance of TaskTypeDAO to test.
     * </p>
     */
    private TaskTypeDAO taskTypeDAO;

    /**
     * <p>
     * Set up the environment for testing.
     * Valid configurations are loaded.
     * A valid task type dao is created.
     * The data table is truncated.
     * And two company record is added for test.
     * And ten task typs would be added for test too.
     * </p>
     *
     * @throws Exception Exception to junit.
     */
    protected void setUp() throws Exception {
        // load the configuration
        V1Dot1TestHelper.clearConfiguration();
        V1Dot1TestHelper.addValidConfiguration();

        Connection conn = null;
        taskTypeDAO = new TaskTypeDAO(CONNAME, NAMESPACE);

        try {
            // delete all the records in all tables
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.clearDatabase(conn);
            V1Dot1TestHelper.executeSQL("insert into company values(1, 'a', 'a', current, 'a', current, 'a');", conn);
            V1Dot1TestHelper.executeSQL("insert into company values(2, 'b', 'b', current, 'b', current, 'b');", conn);

            for (int i = 0; i < 10; i++) {
                // Insert an task type
                TaskType type = new TaskType();
                type.setCompanyId(i % 2 + 1);
                type.setDescription("TaskType" + (i % 5));
                type.setActive(i % 2 == 0);
                taskTypeDAO.create(type, "creator" + (i % 5));
            }
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * Removes the configuration if it exists.
     * And clear the test datum.
     *
     * @throws Exception Exception to junit.
     */
    protected void tearDown() throws Exception {
        Connection conn = null;
        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.clearDatabase(conn);
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
        V1Dot1TestHelper.clearConfiguration();
    }

    /**
     * <p>
     * Test ctor TaskTypeDAO(String connName, String namespace), when connName is null,
     * NullPointerException is expected.
     * </p>
     */
    public void testCtor_ConnNameIsNull() {
        try {
            new TaskTypeDAO(null, NAMESPACE);
            fail("connName is null, NullPointerException is expected.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor TaskTypeDAO(String connName, String namespace), when connName is empty,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_ConnNameIsEmpty() {
        try {
            new TaskTypeDAO("  ", NAMESPACE);
            fail("connName is empty, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor TaskTypeDAO(String connName, String namespace), when namespace is null,
     * NullPointerException is expected.
     * </p>
     */
    public void testCtor_NamespaceIsNull() {
        try {
            new TaskTypeDAO(CONNAME, null);
            fail("namespace is null, NullPointerException is expected.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor TaskTypeDAO(String connName, String namespace), when namespace is empty,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_NamespaceIsEmpty() {
        try {
            new TaskTypeDAO(CONNAME, "  ");
            fail("namespace is empty, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test create(DataObject dataObject, String user), when dataObject is null,
     * NullPointerException is expected.
     * </p>
     */
    public void testCreate_DataobjectIsNull() throws Exception {
        try {
            taskTypeDAO.create(null, "admin");
            fail("When dataObject is null, NullPointerException is expected.");
        } catch (NullPointerException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test create(DataObject dataObject, String user), when user is null,
     * NullPointerException is expected.
     * </p>
     *
     * @throws Exception Exception to junit.
     */
    public void testCreate_UserIsNull() throws Exception {
        TaskType type = new TaskType();
        type.setCompanyId(1);
        type.setDescription("description");
        try {
            taskTypeDAO.create(type, null);
            fail("When dataObject is null, NullPointerException is expected.");
        } catch (NullPointerException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test create(DataObject dataObject, String user), when user is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to junit.
     */
    public void testCreate_UserIsEmpty() throws Exception {
        TaskType type = new TaskType();
        type.setCompanyId(1);
        type.setDescription("description");
        try {
            taskTypeDAO.create(type, "  ");
            fail("When dataObject is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test create(DataObject dataObject, String user), an task type should be added to the database.
     * </p>
     *
     * @throws Exception Exception to junit.
     */
    public void testCreate() throws Exception {
        TaskType type = new TaskType();
        type.setCompanyId(1);
        type.setDescription("description");

        taskTypeDAO.create(type, "test");
        TaskType returned = (TaskType) taskTypeDAO.get(type.getPrimaryId());
        assertEquals("Failed to get the task type correctly", type.getCompanyId(), returned.getCompanyId());
        assertEquals("Failed to get the task type correctly", type.getCreationDate(), returned.getCreationDate());
        assertEquals("Failed to get the task type correctly", type.getCreationUser(), returned.getCreationUser());
        assertEquals("Failed to get the task type correctly", type.getDescription(), returned.getDescription());
        assertEquals("Failed to get the task type correctly", type.getModificationDate(),
            returned.getModificationDate());
        assertEquals("Failed to get the task type correctly", type.getModificationUser(),
            returned.getModificationUser());
        assertEquals("Failed to get the task type correctly", type.isActive(), returned.isActive());
    }

    /**
     * <p>
     * Helper method for assert data type.
     * </p>
     *
     * @param message the message.
     * @param expected the expected date.
     * @param actual the actual data.
     */
    private static void assertEquals(String message, Date expected, Date actual) {
        Calendar expectedCalendar = Calendar.getInstance();
        Calendar actualCalendar = Calendar.getInstance();

        expectedCalendar.setTime(expected);
        actualCalendar.setTime(actual);

        Assert.assertEquals(message, expectedCalendar.get(Calendar.YEAR), actualCalendar.get(Calendar.YEAR));
        Assert.assertEquals(message, expectedCalendar.get(Calendar.MONTH), actualCalendar.get(Calendar.MONTH));
        Assert.assertEquals(message, expectedCalendar.get(Calendar.DAY_OF_MONTH),
            actualCalendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(message, expectedCalendar.get(Calendar.HOUR_OF_DAY),
            actualCalendar.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(message, expectedCalendar.get(Calendar.MINUTE), actualCalendar.get(Calendar.MINUTE));
        Assert.assertEquals(message, expectedCalendar.get(Calendar.SECOND), actualCalendar.get(Calendar.SECOND));
    }

    /**
     * <p>
     * Test update(DataObject dataObject, String user), when dataObject is null,
     * NullPointerException is expected.
     * </p>
     *
     * @throws Exception Exception to junit.
     */
    public void testUpdate_DataObjectIsNull() throws Exception {
        try {
            taskTypeDAO.create(null, "admin");
            fail("When dataObject is null, NullPointerException is expected.");
        } catch (NullPointerException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test update(DataObject dataObject, String user), when user is null,
     * NullPointerException is expected.
     * </p>
     *
     * @throws Exception Exception to junit.
     */
    public void testUpdate_UserIsNull() throws Exception {
        TaskType type = new TaskType();
        type.setCompanyId(1);
        type.setDescription("description");

        try {
            taskTypeDAO.create(type, null);
            fail("When user is null, NullPointerException is expected.");
        } catch (NullPointerException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test update(DataObject dataObject, String user), when user is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to junit.
     */
    public void testUpdate_UserIsEmpty() throws Exception {
        TaskType type = new TaskType();
        type.setCompanyId(1);
        type.setDescription("description");

        try {
            taskTypeDAO.create(type, "  ");
            fail("When user is emtpy, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test create(DataObject dataObject, String user), an task type should be added to the database.
     * </p>
     *
     * @throws Exception Exception to junit.
     */
    public void testUpdate() throws Exception {
        TaskType type = new TaskType();
        type.setCompanyId(1);
        type.setDescription("description");
        taskTypeDAO.create(type, "test");

        type.setCompanyId(2);
        type.setActive(true);
        taskTypeDAO.update(type, "modifier");

        TaskType returned = (TaskType) taskTypeDAO.get(type.getPrimaryId());
        assertEquals("Failed to get the task type correctly", type.getCompanyId(), returned.getCompanyId());
        assertEquals("Failed to get the task type correctly", type.getCreationDate(), returned.getCreationDate());
        assertEquals("Failed to get the task type correctly", type.getCreationUser(), returned.getCreationUser());
        assertEquals("Failed to get the task type correctly", type.getDescription(), returned.getDescription());
        assertEquals("Failed to get the task type correctly",
            type.getModificationDate(), returned.getModificationDate());
        assertEquals("Failed to get the task type correctly",
            type.getModificationUser(), returned.getModificationUser());
        assertEquals("Failed to get the task type correctly", type.isActive(), returned.isActive());
    }

    /**
     * <p>
     * Test delete(int primaryId),
     * </p>
     *
     * @throws Exception Exception to junit.
     */
    public void testDelete() throws Exception {
        TaskType type = new TaskType();
        type.setCompanyId(1);
        type.setDescription("description");
        taskTypeDAO.create(type, "test");

        assertNotNull("Failed to create type", taskTypeDAO.get(type.getPrimaryId()));
        taskTypeDAO.delete(type.getPrimaryId());
        assertNull("Failed to detele type", taskTypeDAO.get(type.getPrimaryId()));
    }

    /**
     * <p>
     * Test get task types by company id.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchByCompanyId() throws Exception {
        TaskTypeCriteria c1 = TaskTypeCriteria.COMPANY_ID;
        SearchExpression exp = ComparisonExpression.equals(c1, "1");
        List result = taskTypeDAO.getList(exp.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 5, result.size());
    }

    /**
     * <p>
     * Test get task types by description.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchByDescription() throws Exception {
        TaskTypeCriteria c1 = TaskTypeCriteria.DESCRIPTION;
        SearchExpression exp1 = ComparisonExpression.equals(c1, "'TaskType1'");
        List result = taskTypeDAO.getList(exp1.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 2, result.size());

        SubstringExpression exp2 = SubstringExpression.contains(c1, "TaskType");
        result = taskTypeDAO.getList(exp2.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 10, result.size());

    }

    /**
     * <p>
     * Test get task types by active status.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchByActive() throws Exception {
        TaskTypeCriteria c1 = TaskTypeCriteria.ACTIVE;
        SearchExpression exp = ComparisonExpression.equals(c1, "0");
        List result = taskTypeDAO.getList(exp.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 5, result.size());
    }

    /**
     * <p>
     * Test get task types by creation date.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchByCreationData() throws Exception {
        TaskTypeCriteria c1 = TaskTypeCriteria.CREATION_DATE;
        SearchExpression exp = ComparisonExpression.greaterThanOrEquals(c1, "MDY(01,01,2005)");
        List result = taskTypeDAO.getList(exp.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 10, result.size());
    }

    /**
     * <p>
     * Test get task types by creation user.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchByCreationUser() throws Exception  {
        TaskTypeCriteria c1 = TaskTypeCriteria.CREATION_USER;
        SearchExpression exp = SubstringExpression.contains(c1, "creator");
        List result = taskTypeDAO.getList(exp.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 10, result.size());
    }

    /**
     * <p>
     * Test get task types by modification date.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchByModificationData() throws Exception  {
        TaskTypeCriteria c1 = TaskTypeCriteria.MODIFICATION_DATE;
        SearchExpression exp = ComparisonExpression.greaterThanOrEquals(c1, "MDY(01,01,2005)");
        List result = taskTypeDAO.getList(exp.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 10, result.size());
    }

    /**
     * <p>
     * Test get task types by modification user.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchByModificationUser() throws Exception {
        TaskTypeCriteria c1 = TaskTypeCriteria.MODIFICATION_USER;
        SearchExpression exp = SubstringExpression.contains(c1, "modificator");
        List result = taskTypeDAO.getList(exp.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 0, result.size());

        exp = SubstringExpression.contains(c1, "creator");
        result = taskTypeDAO.getList(exp.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 10, result.size());
    }
}
