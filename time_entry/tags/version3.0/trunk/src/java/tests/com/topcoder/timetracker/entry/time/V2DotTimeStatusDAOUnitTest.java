/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.topcoder.timetracker.entry.time.search.ComparisonExpression;
import com.topcoder.timetracker.entry.time.search.SearchExpression;
import com.topcoder.timetracker.entry.time.search.SubstringExpression;
import com.topcoder.timetracker.entry.time.search.TaskTypeCriteria;
import com.topcoder.timetracker.entry.time.search.TimeStatusCriteria;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * <p>
 * The unit test tests for TimeStatusDAO.
 * Mainly it tests the CRUD operations and search functionality.
 * </p>
 *
 * @author arylio
 * @value 2.0
 * @since 2.0
 */
public class V2DotTimeStatusDAOUnitTest extends TestCase {

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
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.time.myconfig";

    /**
     * <p>
     * An instance of TimeStatusDAO to test.
     * </p>
     */
    private TimeStatusDAO timeStatusDAO;

    /**
     * <p>
     * Set up the environment for testing.
     * Valid configurations are loaded.
     * A valid time status dao is created.
     * The data table is truncated.
     * And two company record is added for test.
     * And ten task status would be added for test too.
     * </p>
     *
     * @throws Exception Exception to junit.
     */
    protected void setUp() throws Exception {
        // load the configuration
        V1Dot1TestHelper.clearConfiguration();
        V1Dot1TestHelper.addValidConfiguration();

        Connection conn = null;
        timeStatusDAO = new TimeStatusDAO(CONNAME, NAMESPACE);

        try {
            // delete all the records in all tables
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.clearDatabase(conn);

            for (int i = 0; i < 10; i++) {
                // Insert a task status
                TimeStatus status = new TimeStatus();
                status.setDescription("TaskStatus" + (i % 5));
                timeStatusDAO.create(status, "creator" + (i % 5));
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
     * Test ctor TimeStatusDAO(String connName, String namespace), when connName is null,
     * NullPointerException is expected.
     * </p>
     */
    public void testCtor_ConnNameIsNull() {
        try {
            new TimeStatusDAO(null, NAMESPACE);
            fail("connName is null, NullPointerException is expected.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor TimeStatusDAO(String connName, String namespace), when connName is empty,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_ConnNameIsEmpty() {
        try {
            new TimeStatusDAO("  ", NAMESPACE);
            fail("connName is empty, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor TimeStatusDAO(String connName, String namespace), when namespace is null,
     * NullPointerException is expected.
     * </p>
     */
    public void testCtor_NamespaceIsNull() {
        try {
            new TimeStatusDAO(CONNAME, null);
            fail("namespace is null, NullPointerException is expected.");
        } catch (NullPointerException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor TimeStatusDAO(String connName, String namespace), when namespace is empty,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_NamespaceIsEmpty() {
        try {
            new TimeStatusDAO(CONNAME, "  ");
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
            timeStatusDAO.create(null, "admin");
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
        TimeStatus status = new TimeStatus();
        status.setDescription("description");
        try {
            timeStatusDAO.create(status, null);
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
        TimeStatus status = new TimeStatus();
        status.setDescription("description");
        try {
            timeStatusDAO.create(status, "  ");
            fail("When dataObject is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test create(DataObject dataObject, String user), an time status should be added to the database.
     * </p>
     *
     * @throws Exception Exception to junit.
     */
    public void testCreate() throws Exception {
        TimeStatus status = new TimeStatus();
        status.setDescription("description");

        timeStatusDAO.create(status, "test");
        TimeStatus returned = (TimeStatus) timeStatusDAO.get(status.getPrimaryId());
        assertEquals("Failed to get the time status correctly", status.getCreationDate(), returned.getCreationDate());
        assertEquals("Failed to get the time status correctly", status.getCreationUser(), returned.getCreationUser());
        assertEquals("Failed to get the time status correctly", status.getDescription(), returned.getDescription());
        assertEquals("Failed to get the time status correctly", status.getModificationDate(),
            returned.getModificationDate());
        assertEquals("Failed to get the time status correctly", status.getModificationUser(),
            returned.getModificationUser());
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
            timeStatusDAO.create(null, "admin");
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
        TimeStatus status = new TimeStatus();
        status.setDescription("description");

        try {
            timeStatusDAO.create(status, null);
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
        TimeStatus status = new TimeStatus();
        status.setDescription("description");

        try {
            timeStatusDAO.create(status, "  ");
            fail("When user is emtpy, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Test create(DataObject dataObject, String user), an time status should be added to the database.
     * </p>
     *
     * @throws Exception Exception to junit.
     */
    public void testUpdate() throws Exception {
        TimeStatus status = new TimeStatus();
        status.setDescription("description");
        timeStatusDAO.create(status, "test");

        status.setDescription("Modified");
        timeStatusDAO.update(status, "modifier");

        TimeStatus returned = (TimeStatus) timeStatusDAO.get(status.getPrimaryId());
        assertEquals("Failed to get the time status correctly", status.getCreationDate(), returned.getCreationDate());
        assertEquals("Failed to get the time status correctly", status.getCreationUser(), returned.getCreationUser());
        assertEquals("Failed to get the time status correctly", status.getDescription(), returned.getDescription());
        assertEquals("Failed to get the time status correctly",
            status.getModificationDate(), returned.getModificationDate());
        assertEquals("Failed to get the time status correctly",
            status.getModificationUser(), returned.getModificationUser());
    }

    /**
     * <p>
     * Test delete(int primaryId),
     * </p>
     *
     * @throws Exception Exception to junit.
     */
    public void testDelete() throws Exception {
        TimeStatus status = new TimeStatus();
        status.setDescription("description");
        timeStatusDAO.create(status, "test");

        assertNotNull("Failed to create time status", timeStatusDAO.get(status.getPrimaryId()));
        timeStatusDAO.delete(status.getPrimaryId());
        assertNull("Failed to detele time status", timeStatusDAO.get(status.getPrimaryId()));
    }

    /**
     * <p>
     * Test get time status by description.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchByDescription() throws Exception {
        TimeStatusCriteria c1 = TimeStatusCriteria.DESCRIPTION;
        SearchExpression exp1 = ComparisonExpression.equals(c1, "'TaskStatus1'");
        List result = timeStatusDAO.getList(exp1.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 2, result.size());

        SubstringExpression exp2 = SubstringExpression.contains(c1, "TaskStatus");
        result = timeStatusDAO.getList(exp2.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 10, result.size());

    }

    /**
     * <p>
     * Test get time status by creation date.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchByCreationData() throws Exception {
        TimeStatusCriteria c1 = TimeStatusCriteria.CREATION_DATE;
        SearchExpression exp = ComparisonExpression.greaterThanOrEquals(c1, "MDY(01,01,2005)");
        List result = timeStatusDAO.getList(exp.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 10, result.size());
    }

    /**
     * <p>
     * Test get time status by creation user.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchByCreationUser() throws Exception  {
        TimeStatusCriteria c1 = TimeStatusCriteria.CREATION_USER;
        SearchExpression exp = SubstringExpression.contains(c1, "creator");
        List result = timeStatusDAO.getList(exp.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 10, result.size());
    }

    /**
     * <p>
     * Test get time status by modification date.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchByModificationData() throws Exception  {
        TimeStatusCriteria c1 = TimeStatusCriteria.MODIFICATION_DATE;
        SearchExpression exp = ComparisonExpression.greaterThanOrEquals(c1, "MDY(01,01,2005)");
        List result = timeStatusDAO.getList(exp.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 10, result.size());
    }

    /**
     * <p>
     * Test get time status by modification user.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSearchByModificationUser() throws Exception {
        TimeStatusCriteria c1 = TimeStatusCriteria.MODIFICATION_USER;
        SearchExpression exp = SubstringExpression.contains(c1, "modificator");
        List result = timeStatusDAO.getList(exp.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 0, result.size());

        exp = SubstringExpression.contains(c1, "creator");
        result = timeStatusDAO.getList(exp.getSearchExpressionString());
        assertEquals("Failed to get the correct record", 10, result.size());
    }
}
