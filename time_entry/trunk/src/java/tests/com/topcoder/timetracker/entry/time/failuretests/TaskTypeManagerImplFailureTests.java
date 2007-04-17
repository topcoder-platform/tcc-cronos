package com.topcoder.timetracker.entry.time.failuretests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TaskTypeDAO;
import com.topcoder.timetracker.entry.time.TaskTypeManagerImpl;
import com.topcoder.timetracker.entry.time.TestHelper;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.time.db.DbTaskTypeDAO;

import junit.framework.TestCase;
/**
 * <p>
 * Failure test cases for TaskTypeManagerImpl.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class TaskTypeManagerImplFailureTests extends TestCase {
    /**
     * <p>
     * The TaskTypeManagerImpl instance for testing.
     * </p>
     */
    private TaskTypeManagerImpl instance;

    /**
     * <p>
     * The TaskTypeDAO instance for testing.
     * </p>
     */
    private TaskTypeDAO taskTypeDAO;

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
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.AUDIT_CONFIG_FILE);
        TestHelper.setUpDataBase();
        TestHelper.setUpEJBEnvironment(null, null, null);

        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);
        auditManager = new AuditDelegate(TestHelper.AUDIT_NAMESPACE);
        taskTypeDAO = new DbTaskTypeDAO(dbFactory, TestHelper.CONNECTION_NAME, "TaskTypeIdGenerator",
            TestHelper.SEARCH_NAMESPACE, auditManager);

        instance = new TaskTypeManagerImpl(taskTypeDAO);
    }
    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();

        instance = null;
        taskTypeDAO = null;
        auditManager = null;
        dbFactory = null;
    }
    /**
     * <p>
     * Tests ctor TaskTypeManagerImpl#TaskTypeManagerImpl(TaskTypeDAO) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypeDAO is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor() {
        try {
            new TaskTypeManagerImpl(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#getTaskType(long) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskType1() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        instance.createTaskType(taskType);

        taskTypeDAO = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TaskTypeManagerImpl(taskTypeDAO);

        try {
            instance.getTaskType(taskType.getId());
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#getTaskType(long) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskType2() throws Exception {
        try {
            instance.getTaskType(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskType(TaskType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskType is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskType1() throws Exception {
        try {
            instance.createTaskType(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskType(TaskType) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskType2() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        taskTypeDAO = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TaskTypeManagerImpl(taskTypeDAO);

        try {
            instance.createTaskType(taskType);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskType(TaskType) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskType is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskType1() throws Exception {
        try {
            instance.updateTaskType(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskType(TaskType) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskType2() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        taskType.setId(100);

        try {
            instance.updateTaskType(taskType);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskType(TaskType) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskType3() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        instance.createTaskType(taskType);

        taskTypeDAO = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TaskTypeManagerImpl(taskTypeDAO);

        try {
            instance.updateTaskType(taskType);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#deleteTaskType(long) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskType1() throws Exception {
        try {
            instance.deleteTaskType(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#deleteTaskType(long) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskType2() throws Exception {
        TaskType taskType = TestHelper.createTestingTaskType(null);
        instance.createTaskType(taskType);
        taskTypeDAO = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TaskTypeManagerImpl(taskTypeDAO);

        try {
            instance.deleteTaskType(taskType.getId());
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
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
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
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
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes3() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);

        try {
            instance.createTaskTypes(new TaskType[] {taskType1, taskType1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes4() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);

        try {
            instance.createTaskTypes(new TaskType[] {taskType1, null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes5() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);
        taskType2.setId(34);

        try {
            instance.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null creation user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes6() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType("CreationUser");

        try {
            instance.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes7() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType("ModificationUser");

        try {
            instance.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#createTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTaskTypes8() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);

        taskTypeDAO = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TaskTypeManagerImpl(taskTypeDAO);

        try {
            instance.createTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for failure.
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
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for failure.
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
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes3() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] {taskType1});

        try {
            instance.updateTaskTypes(new TaskType[] {taskType1, taskType1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypes contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes4() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] {taskType1});

        try {
            instance.updateTaskTypes(new TaskType[] {taskType1, null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes5() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] {taskType1});
        TaskType taskType2 = TestHelper.createTestingTaskType(null);

        try {
            instance.updateTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a task type has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes6() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] {taskType1});
        TaskType taskType2 = TestHelper.createTestingTaskType("ModificationUser");
        taskType2.setId(300);

        try {
            instance.updateTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#updateTaskTypes(TaskType[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTaskTypes7() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        TaskType taskType2 = TestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] {taskType1, taskType2});

        taskTypeDAO = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TaskTypeManagerImpl(taskTypeDAO);

        try {
            instance.updateTaskTypes(new TaskType[] {taskType1, taskType2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#deleteTaskTypes(long[]) for failure.
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
     * Tests TaskTypeManagerImpl#deleteTaskTypes(long[]) for failure.
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
     * Tests TaskTypeManagerImpl#deleteTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypeIds contains equal id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes3() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] {taskType1});

        try {
            instance.deleteTaskTypes(new long[] {taskType1.getId(), taskType1.getId()});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TaskTypeManagerImpl#deleteTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTaskTypes4() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] {taskType1});

        taskTypeDAO = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TaskTypeManagerImpl(taskTypeDAO);

        try {
            instance.deleteTaskTypes(new long[] {taskType1.getId()});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#getTaskTypes(long[]) for failure.
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
     * Tests TaskTypeManagerImpl#getTaskTypes(long[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTaskTypes2() throws Exception {
        TaskType taskType1 = TestHelper.createTestingTaskType(null);
        instance.createTaskTypes(new TaskType[] {taskType1});

        taskTypeDAO = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TaskTypeManagerImpl(taskTypeDAO);

        try {
            instance.getTaskTypes(new long[] {taskType1.getId()});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
    /**
     * <p>
     * Tests TaskTypeManagerImpl#searchTaskTypes(Filter) for failure.
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
     * Tests TaskTypeManagerImpl#searchTaskTypes(Filter) for failure.
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
     * Tests TaskTypeManagerImpl#getAllTaskTypes() for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTaskTypes() throws Exception {
        taskTypeDAO = new DbTaskTypeDAO(dbFactory, "empty", "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TaskTypeManagerImpl(taskTypeDAO);

        try {
            instance.getAllTaskTypes();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}
