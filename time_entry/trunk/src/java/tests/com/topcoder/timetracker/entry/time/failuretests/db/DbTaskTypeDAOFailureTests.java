/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.db.DbTaskTypeDAO;
import com.topcoder.timetracker.entry.time.failuretests.FailureTestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Failure test cases for DbTaskTypeDAO.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class DbTaskTypeDAOFailureTests extends TestCase {
    /**
     * <p>
     * The DbTaskTypeDAO instance for testing.
     * </p>
     */
    private DbTaskTypeDAO instance;

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
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        FailureTestHelper.clearConfig();
        FailureTestHelper.loadXMLConfig(FailureTestHelper.CONFIG_FILE);
        FailureTestHelper.loadXMLConfig(FailureTestHelper.AUDIT_CONFIG_FILE);
        FailureTestHelper.setUpDataBase();
        FailureTestHelper.setUpEJBEnvironment(null, null, null);

        dbFactory = new DBConnectionFactoryImpl(FailureTestHelper.DB_FACTORY_NAMESPACE);
        auditManager = new AuditDelegate(FailureTestHelper.AUDIT_NAMESPACE);

        instance = new DbTaskTypeDAO(dbFactory, FailureTestHelper.CONNECTION_NAME,
                "TaskTypeIdGenerator", FailureTestHelper.SEARCH_NAMESPACE, auditManager);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.tearDownDataBase();
        FailureTestHelper.clearConfig();

        dbFactory = null;
        auditManager = null;
        instance = null;
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connFactory is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception {
        try {
            new DbTaskTypeDAO(null, FailureTestHelper.CONNECTION_NAME, "TaskTypeIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connName is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception {
        try {
            new DbTaskTypeDAO(dbFactory, " ", "TaskTypeIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor3() throws Exception {
        try {
            new DbTaskTypeDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, null,
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor4() throws Exception {
        try {
            new DbTaskTypeDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, " ",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchStrategyNamespace is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor5() throws Exception {
        try {
            new DbTaskTypeDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TaskTypeIdGenerator",
                null, auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchStrategyNamespace is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor6() throws Exception {
        try {
            new DbTaskTypeDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TaskTypeIdGenerator",
                " ", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when auditor is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor7() throws Exception {
        try {
            new DbTaskTypeDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TaskTypeIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTaskTypeDAO#DbTaskTypeDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the search strategy namespace is unknown and expects for ConfigurationException.
     * </p>
     */
    public void testCtor8() {
        try {
            new DbTaskTypeDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TaskTypeIdGenerator",
                "unknown_namespace", auditManager);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes1() throws Exception {
        try {
            instance.createTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes2() throws Exception {
        try {
            instance.createTaskTypes(new TaskType[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes3() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);

        try {
            instance.createTaskTypes(new TaskType[] { taskType1, taskType1 });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes4() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);

        try {
            instance.createTaskTypes(new TaskType[] { taskType1, null });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes5() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        TaskType taskType2 = FailureTestHelper.createTestingTaskType(null);
        taskType2.setId(34);

        try {
            instance.createTaskTypes(new TaskType[] { taskType1, taskType2 });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null creation date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes6() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        TaskType taskType2 = FailureTestHelper.createTestingTaskType("CreationDate");

        try {
            instance.createTaskTypes(new TaskType[] { taskType1, taskType2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null creation user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes7() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        TaskType taskType2 = FailureTestHelper.createTestingTaskType("CreationUser");

        try {
            instance.createTaskTypes(new TaskType[] { taskType1, taskType2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null modification date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes8() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        TaskType taskType2 = FailureTestHelper.createTestingTaskType("ModificationDate");

        try {
            instance.createTaskTypes(new TaskType[] { taskType1, taskType2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes9() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        TaskType taskType2 = FailureTestHelper.createTestingTaskType("ModificationUser");

        try {
            instance.createTaskTypes(new TaskType[] { taskType1, taskType2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes10() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        TaskType taskType2 = FailureTestHelper.createTestingTaskType(null);
        instance = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);

        try {
            instance.createTaskTypes(new TaskType[] { taskType1, taskType2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes1() throws Exception {
        try {
            instance.updateTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes2() throws Exception {
        try {
            instance.updateTaskTypes(new TaskType[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes3() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] { taskType1 });

        try {
            instance.updateTaskTypes(new TaskType[] { taskType1, taskType1 });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes4() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] { taskType1 });

        try {
            instance.updateTaskTypes(new TaskType[] { taskType1, null });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes5() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] { taskType1 });

        TaskType taskType2 = FailureTestHelper.createTestingTaskType(null);

        try {
            instance.updateTaskTypes(new TaskType[] { taskType1, taskType2 });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null modification date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes6() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] { taskType1 });

        TaskType taskType2 = FailureTestHelper.createTestingTaskType("ModificationDate");
        taskType2.setId(100);

        try {
            instance.updateTaskTypes(new TaskType[] { taskType1, taskType2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes7() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] { taskType1 });

        TaskType taskType2 = FailureTestHelper.createTestingTaskType("ModificationUser");
        taskType2.setId(300);

        try {
            instance.updateTaskTypes(new TaskType[] { taskType1, taskType2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes8() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        TaskType taskType2 = FailureTestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] { taskType1, taskType2 });

        instance = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);

        try {
            instance.updateTaskTypes(new TaskType[] { taskType1, taskType2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#deleteTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypeIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes1() throws Exception {
        try {
            instance.deleteTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#deleteTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypeIds is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes2() throws Exception {
        try {
            instance.deleteTaskTypes(new long[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#deleteTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypeIds contains equal id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes3() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] { taskType1 });

        try {
            instance.deleteTaskTypes(new long[] { taskType1.getId(), taskType1.getId() });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#deleteTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes4() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] { taskType1 });

        instance = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);

        try {
            instance.deleteTaskTypes(new long[] { taskType1.getId() });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#getTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypeIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskTypes1() throws Exception {
        try {
            instance.getTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#getTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskTypes2() throws Exception {
        TaskType taskType1 = FailureTestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] { taskType1 });

        instance = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);

        try {
            instance.getTaskTypes(new long[] { taskType1.getId() });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#searchTaskTypes(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when criteria is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTaskTypes1() throws Exception {
        try {
            instance.searchTaskTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#searchTaskTypes(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchTaskTypes2() {
        Filter filter = new EqualToFilter("no_column", "value");

        try {
            instance.searchTaskTypes(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTaskTypeDAO#getAllTaskTypes() for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTaskTypes() throws Exception {
        instance = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);

        try {
            instance.getAllTaskTypes();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}
