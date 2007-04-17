/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.stresstests;

import java.util.Calendar;
import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TaskTypeManagerImpl;
import com.topcoder.timetracker.entry.time.TestHelper;
import com.topcoder.timetracker.entry.time.db.DbTaskTypeDAO;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Stress test cases for TaskTypeManagerImpl.
 * </p>
 * 
 * @author superZZ
 * @version 3.2
 */
public class TaskTypeManagerImplStressTests extends TestCase {
    /**
     * <p>
     * The TaskTypeManagerImpl instance for testing.
     * </p>
     */
    private TaskTypeManagerImpl taskTypeMgr;

    /**
     * <p>
     * The DbTaskTypeDAO instance for testing.
     * </p>
     */
    private DbTaskTypeDAO taskTypeDao;

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory dbFactory;

    /**
     * <p>
     * The AuditManager instance for testing.
     * </p>
     */
    private AuditManager auditManager;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.AUDIT_CONFIG_FILE);
        TestHelper.setUpDataBase();
        TestHelper.setUpEJBEnvironment(null, null, null);

        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);
        auditManager = new AuditDelegate(TestHelper.AUDIT_NAMESPACE);

        taskTypeDao = new DbTaskTypeDAO(dbFactory, TestHelper.CONNECTION_NAME,
                "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
                auditManager);

        taskTypeMgr = new TaskTypeManagerImpl(taskTypeDao);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();

        dbFactory = null;
        auditManager = null;
        taskTypeDao = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     * 
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TaskTypeManagerImplStressTests.class);
    }

    /**
     * <p>
     * Tests the performance of TaskTypeManagerImpl#createTaskType(TaskType).
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    public void testCreateTaskType() throws Exception {

        TaskType taskTypes[] = new TaskType[3];
        Calendar date = Calendar.getInstance();
        date.set(1996, 1, 1);

        for (int i = 0; i < taskTypes.length; i++) {
            taskTypes[i] = TestHelper.createTestingTaskType(null);
            taskTypes[i].setActive((i % 2 == 0) ? true : false);
            taskTypes[i].setDescription("description " + i);
            taskTypes[i].setCreationDate(date.getTime());
            taskTypes[i].setModificationDate(date.getTime());
        }

        Date now = new Date();

        for (int i = 0; i < taskTypes.length; i++) {
            taskTypeMgr.createTaskType(taskTypes[i]);
        }

        TaskType[] insertedTaskTypes = taskTypeMgr.getAllTaskTypes();

        for (int i = 0; i < taskTypes.length; i++) {
            assertTaskType(taskTypes[i], insertedTaskTypes[i]);
        }
    }

    /**
     * <p>
     * Tests the performance of TaskTypeManagerImpl#createTaskTypes(TaskType[]).
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    public void testCreateTaskTypes() throws Exception {
        TaskType taskTypes[] = new TaskType[100];
        Calendar date = Calendar.getInstance();
        date.set(1996, 1, 1);

        for (int i = 0; i < taskTypes.length; i++) {
            taskTypes[i] = TestHelper.createTestingTaskType(null);
            taskTypes[i].setActive((i % 2 == 0) ? true : false);
            taskTypes[i].setDescription("description " + i);
            taskTypes[i].setCreationDate(date.getTime());
            taskTypes[i].setModificationDate(date.getTime());
        }

        Date now = new Date();
        long startTime = System.currentTimeMillis();
        taskTypeMgr.createTaskTypes(taskTypes);
        System.out.println("Creating 100 task types takes "
                + (System.currentTimeMillis() - startTime) + " ms");

        TaskType[] insertedTaskTypes = taskTypeMgr.getAllTaskTypes();

        for (int i = 0; i < taskTypes.length; i++) {
            assertTaskType(taskTypes[i], insertedTaskTypes[i]);
            assertEquals("CreationDate is incorrect.", now, taskTypes[i]
                    .getCreationDate());
            assertEquals("ModificationDate is incorrect.", now, taskTypes[i]
                    .getModificationDate());
        }
    }

    /**
     * Assert the two given task types are equal.
     * 
     * @param expected
     *            Expected Task Type.
     * @param actual
     *            Actual Task Type.
     */
    static void assertTaskType(TaskType expected, TaskType actual) {
        assertEquals("TaskType#active is incorrect.", expected.getActive(),
                actual.getActive());
        assertEquals("TaskType#companyId is incorrect.", expected
                .getCompanyId(), actual.getCompanyId());
        assertEquals("TaskType#description is incorrect.", expected
                .getDescription(), actual.getDescription());
        assertEquals("TaskType#id is incorrect.", expected.getId(), actual
                .getId());
    }

    /**
     * <p>
     * Tests the performance of TaskTypeManagerImpl#updateTaskType(TaskType).
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    public void testUpdateTaskType() throws Exception {
        TaskType taskTypes[] = new TaskType[1];

        Calendar date = Calendar.getInstance();
        date.set(1996, 1, 1);

        for (int i = 0; i < taskTypes.length; i++) {
            taskTypes[i] = TestHelper.createTestingTaskType(null);
            taskTypes[i].setActive((i % 2 == 0) ? true : false);
            taskTypes[i].setDescription("description " + i);
            taskTypes[i].setCreationDate(date.getTime());
        }

        taskTypeMgr.createTaskTypes(taskTypes);

        for (int i = 0; i < taskTypes.length; i++) {
            taskTypes[i].setActive((i % 2 == 0) ? false : true);
            taskTypes[i].setDescription("updated description " + i);
            taskTypes[i].setModificationDate(date.getTime());
        }

        Date now = new Date();
        for (int i = 0; i < taskTypes.length; i++) {
            taskTypeMgr.updateTaskType(taskTypes[i]);
        }

        TaskType[] insertedTaskTypes = taskTypeMgr.getAllTaskTypes();

        for (int i = 0; i < taskTypes.length; i++) {
            assertTaskType(taskTypes[i], insertedTaskTypes[i]);
            assertEquals("ModificationDate is incorrect.", now.toString(),
                    taskTypes[i].getModificationDate().toString());
        }
    }

    /**
     * <p>
     * Tests the performance of TaskTypeManagerImpl#updateTaskTypes(TaskType[]).
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateTaskTypes() throws Exception {
        TaskType taskTypes[] = new TaskType[100];

        Calendar date = Calendar.getInstance();
        date.set(1996, 1, 1);

        for (int i = 0; i < taskTypes.length; i++) {
            taskTypes[i] = TestHelper.createTestingTaskType(null);
            taskTypes[i].setActive((i % 2 == 0) ? true : false);
            taskTypes[i].setDescription("description " + i);
            taskTypes[i].setCreationDate(date.getTime());
            taskTypes[i].setModificationDate(date.getTime());
        }

        taskTypeMgr.createTaskTypes(taskTypes);

        for (int i = 0; i < taskTypes.length; i++) {
            taskTypes[i].setActive((i % 2 == 0) ? false : true);
            taskTypes[i].setDescription("updated description " + i);
        }

        Date now = new Date();
        long startTime = System.currentTimeMillis();
        taskTypeMgr.updateTaskTypes(taskTypes);
        System.out.println("Updating 100 task types takes "
                + (System.currentTimeMillis() - startTime) + " ms");

        TaskType[] insertedTaskTypes = taskTypeMgr.getAllTaskTypes();

        for (int i = 0; i < taskTypes.length; i++) {
            assertTaskType(taskTypes[i], insertedTaskTypes[i]);
            assertEquals("ModificationDate is incorrect.", taskTypes[i]
                    .getModificationDate(), now);
        }
    }

    /**
     * <p>
     * Tests the performance of TaskTypeManagerImpl#searchTaskTypes(Filter).
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    public void testSearchTaskTypes() throws Exception {
        TaskType taskTypes[] = new TaskType[100];

        for (int i = 0; i < taskTypes.length; i++) {
            taskTypes[i] = TestHelper.createTestingTaskType(null);
            taskTypes[i].setActive((i % 2 == 0) ? true : false);
            taskTypes[i].setDescription("description " + i);
        }

        taskTypeMgr.createTaskTypes(taskTypes);

        Filter filter = taskTypeMgr.getTaskTypeFilterFactory()
                .createDescriptionFilter("des", StringMatchType.STARTS_WITH);
        TaskType[] foundTaskTypes = taskTypeMgr.searchTaskTypes(filter);

        assertEquals("Size of retrieved task types is incorrect.",
                taskTypes.length, foundTaskTypes.length);

        filter = taskTypeMgr.getTaskTypeFilterFactory()
                .createDescriptionFilter("0", StringMatchType.ENDS_WITH);
        foundTaskTypes = taskTypeMgr.searchTaskTypes(filter);

        assertEquals("Size of retrieved task types is incorrect.", 10,
                foundTaskTypes.length);

        filter = taskTypeMgr.getTaskTypeFilterFactory()
                .createActiveFilter(true);
        foundTaskTypes = taskTypeMgr.searchTaskTypes(filter);

        assertEquals("Size of retrieved task types is incorrect.", 50,
                foundTaskTypes.length);

        filter = taskTypeMgr.getTaskTypeFilterFactory().createActiveFilter(
                false);
        long startTime = System.currentTimeMillis();
        foundTaskTypes = taskTypeMgr.searchTaskTypes(filter);
        System.out.println("Searching 50 task types in 100 task types takes "
                + (System.currentTimeMillis() - startTime) + " ms");

        assertEquals("Size of retrieved task types is incorrect.", 50,
                foundTaskTypes.length);
    }
}