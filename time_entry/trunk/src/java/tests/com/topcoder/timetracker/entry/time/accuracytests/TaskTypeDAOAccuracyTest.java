/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import java.sql.Connection;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.DataObject;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TaskTypeDAO;

/**
 * <p>
 * Accuracy tests for {@link com.topcoder.timetracker.entry.time.TaskTypeDAO} class.
 * </p>
 *
 * @author kr00tki
 * @version 2.0
 */
public class TaskTypeDAOAccuracyTest extends TestCase {
    /**
     * <p>
     * The name of the Connection to fetch from the DBConnectionFactory.
     * </p>
     */
    private static final String CONNAME = "informix";

    /**
     * <p>
     * Represents the user that created this record.
     * </p>
     */
    private static final String CREATION_USER = "TCSDEVELOPER";

    /**
     * <p>
     * Represents the user that last modified this record.
     * </p>
     */
    private static final String MODIFICATION_USER = "TCSDESIGNER";

    /**
     * <p>
     * Namespace to be used in the DBConnection Factory component to load the configuration.
     * </p>
     */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.time.myconfig";

    /**
     * The database connection used during tests.
     */
    private Connection conn = null;

    /**
     * The TaskTypeDAO instance to test on.
     */
    private TaskTypeDAO dao = null;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        // load the configuration
        AccuracyTestHelper.clearConfiguration();
        AccuracyTestHelper.addValidConfiguration();

        // delete all the records in all tables
        conn = AccuracyTestHelper.getConnection(NAMESPACE, CONNAME);
        AccuracyTestHelper.clearDatabase(conn);
        AccuracyTestHelper.insertCompany(10, conn);
        AccuracyTestHelper.insertCompany(20, conn);

        dao = new TaskTypeDAO(CONNAME, NAMESPACE);
    }

    /**
     * Removes the configuration if it exists.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        // clear the configuration
        AccuracyTestHelper.clearConfiguration();

        // delete all the records in all tables
        AccuracyTestHelper.clearDatabase(conn);

        conn.close();
    }

    /**
     * Creates TaskType object.
     *
     * @param companyId the id of the company.
     * @return TaskType object.
     */
    private TaskType createTaskType(int companyId) {
        TaskType type = new TaskType();
        type.setCompanyId(companyId);
        type.setDescription("desc");

        return type;
    }

    /**
     * Tests the {@link TaskTypeDAO#create(DataObject, String)} method accuracy.
     *
     * @throws Exception to JUnit.
     */
    public void testCreate() throws Exception {
        TaskType task = createTaskType(10);
        // FIXME: this should be boolean
        task.setActive((short) 1);

        dao.create(task, CREATION_USER);

        TaskType task2 = (TaskType) dao.get(task.getPrimaryId());

        assertEquals("Incorrect company id.", task.getCompanyId(), task2.getCompanyId());
        assertEquals("Incorrect active flag.", task.getActive(), task2.getActive());
        AccuracyTestHelper.assertEquals("SHould be equal", task, task2);
    }

    /**
     * Tests the {@link TaskTypeDAO#create(DataObject, String)} method accuracy.
     *
     * @throws Exception to JUnit.
     */
    public void testDelete() throws Exception {
        TaskType task = createTaskType(10);
        // FIXME: this should be boolean
        task.setActive((short) 1);

        dao.create(task, CREATION_USER);

        dao.delete(task.getPrimaryId());

        assertNull("Should be deleted.", dao.get(task.getPrimaryId()));
    }

    /**
     * Tests the {@link TaskTypeDAO#update(DataObject, String)} method accuracy.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdate() throws Exception {
        TaskType task = createTaskType(10);
        // FIXME: this should be boolean
        task.setActive((short) 0);

        dao.create(task, CREATION_USER);
        // FIXME: this should be boolean
        task.setActive((short) 1);
        task.setCompanyId(20);

        dao.update(task, MODIFICATION_USER);

        TaskType task2 = (TaskType) dao.get(task.getPrimaryId());

        assertEquals("Incorrect active flag.", task.getActive(), task2.getActive());
        assertEquals("Incorrect company id.", task.getCompanyId(), task2.getCompanyId());
        AccuracyTestHelper.assertEquals("SHould be equal", task, task2);
    }
}
