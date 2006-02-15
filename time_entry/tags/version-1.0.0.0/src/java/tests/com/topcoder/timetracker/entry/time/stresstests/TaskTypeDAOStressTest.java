/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.stresstests;

import com.topcoder.timetracker.entry.time.TaskTypeDAO;
import com.topcoder.timetracker.entry.time.TaskType;

import junit.framework.TestCase;

/**
 * <p>
 * Stress test for TaskTypeDAO class.
 * </p>
 *
 * @author arylio
 * @version 1.0
 */
public class TaskTypeDAOStressTest extends TestCase {

    /**
     * <p>
     * Represents an instance of TaskTypeDAO, for stress test.
     * </p>
     */
    private TaskTypeDAO dao;

    /**
     * <p>
     * The thread count for stress test.
     * </p>
     */
    private int thread_count;

    /**
     * <p>
     * The operation number of each thread to run.
     * </p>
     */
    private int operations;

    /**
     * <p>
     * Set up for eacht test.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void setUp() throws Exception {
        super.setUp();

        thread_count = 10;
        operations = 20;

        StressHelper.addConfigFile();

        dao = new TaskTypeDAO(StressHelper.PROPERTY_CONNECTION, StressHelper.NAMESPACE);
    }

    /**
     * <p>
     * Tear down for each class.
     * </p>
     */
    public void tearDown() throws Exception {
        StressHelper.clearTable("TaskTypes");
        StressHelper.removeConfigFile();

        super.tearDown();
    }

    /**
     * <p>
     * Stress test create().
     * Concurrent convert operations through multiple threads.
     * And each thread iterate many times.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreate() throws Exception {
        testDbOperator(StressHelper.CREATE, "TaskTypeDAO.create");
    }

    /**
     * <p>
     * Stress test update().
     * Concurrent convert operations through multiple threads.
     * And each thread iterate many times.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdate() throws Exception {
        testDbOperator(StressHelper.UPDATE, "TaskTypeDAO.update");
    }


    /**
     * <p>
     * Stress test get().
     * Concurrent convert operations through multiple threads.
     * And each thread iterate many times.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGet() throws Exception {
        testDbOperator(StressHelper.GET, "TaskTypeDAO.get");
    }

    /**
     * <p>
     * Stress test getList().
     * Concurrent convert operations through multiple threads.
     * And each thread iterate many times.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetList() throws Exception {
        testDbOperator(StressHelper.GETLIST, "TaskTypeDAO.getList");
    }

    /**
     * <p>
     * Stress test delete().
     * Concurrent convert operations through multiple threads.
     * And each thread iterate many times.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testDelete() throws Exception {
        testDbOperator(StressHelper.DELETE, "TaskTypeDAO.Delete");
    }

    /**
     * <p>
     * Test the Dao db access.
     * </p>
     *
     * @param operator the operator id.
     * @param name the operate name for tip.
     *
     * @throws Exception Exception to JUnit.
     */
    private void testDbOperator(int operator, String name) throws Exception {
        TaskTypeDAOTester[] threads = new TaskTypeDAOTester[thread_count];

        long totalTimeSpent = 0;
        for (int i = 0; i < thread_count; i++) {
            threads[i] = new TaskTypeDAOTester(operations, operator);
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < thread_count; i++) {
            threads[i].start();
        }
        for (int i = 0; i < thread_count; i++) {
            threads[i].join();
        }
        totalTimeSpent += (System.currentTimeMillis() - startTime);

        for (int i = 0; i < thread_count; i++) {
            if (threads[i].getException() != null) {
                System.out.println(threads[i].getException().getMessage() + "exception was thrown.");
                throw threads[i].getException();
            }
        }

        System.out.println("-----stress test for " + name + "-----");
        System.out.println("Threads: " + thread_count);
        System.out.println("Operations/Thread: " + operations);
        System.out.println("totaltime:" + totalTimeSpent + "ms");

    }


    /**
     * <p>
     * Test Thread for TaskTypeDAO,
     * if should set the thread count, used to the loop operation.
     * </p>
     */
    private class TaskTypeDAOTester extends Thread {
        /**
         * <p>
         * Represent the operation count.
         * </p>
         */
        private int count;

        /**
         * <p>
         * Represent the operator(create,update,get, test...) to test.
         * </p>
         */
        private int operator;

        /**
         * <p>
         * Represent an array of TaskType, used to added to db.
         */
        private TaskType[] taskTypes;

        /**
         * <p>
         * Represent the exception, thrown when getTemplateHierarchy.
         * </p>
         */
        private Exception exception = null;

        /**
         * <p>
         * Constructor, save the argument.
         * </p>
         *
         * @param count the operation count.
         *
         * @throws Exception Exception to JUnit.
         */
        public TaskTypeDAOTester(int count, int operator) throws Exception {
            this.count = count;
            this.operator = operator;
            taskTypes = new TaskType[count];
            for (int i = 0; i < count; ++i) {
                taskTypes[i] = new TaskType();
                taskTypes[i].setDescription("taskType");
                if (operator == StressHelper.GETLIST || operator == StressHelper.DELETE) {
                    dao.create(taskTypes[i], "topcoder");
                }
            }
        }

        /**
         * <p>
         * run.
         * </p>
         */
        public void run() {
            switch (operator) {
            case StressHelper.CREATE:
                testCreate();
                break;
            case StressHelper.UPDATE:
                testUpdate();
                break;
            case StressHelper.GET:
                testGet();
                break;
            case StressHelper.GETLIST:
                testGetList();
                break;
            case StressHelper.DELETE:
                testDelete();
                break;
            }
        }

        /**
         * <p>
         * test create(DataObject dataObject, String user) method,
         * iterate the create the specified times.
         * </p>
         */
        private void testCreate() {
            try {
                for (int i = 0; i < count; i++) {
                    dao.create(taskTypes[i], "topcoder");
                }
            } catch (Exception e) {
                exception = e;
            }
        }

        /**
         * <p>
         * test update(DataObject dataObject, String user) method,
         * iterate the create the specified times.
         * </p>
         */
        private void testUpdate() {
            try {
                dao.create(taskTypes[0], "error");
                for (int i = 0; i < count; i++) {
                    dao.update(taskTypes[0], "topcoder" + i);
                }
            } catch (Exception e) {
                exception = e;
            }
        }

        /**
         * <p>
         * test get(long primaryId) method,
         * iterate the create the specified times.
         * </p>
         */
        private void testGet() {
            try {
                dao.create(taskTypes[0], "error");
                int id = taskTypes[0].getPrimaryId();
                for (int i = 0; i < count; i++) {
                    dao.get(id);
                }
            } catch (Exception e) {
                exception = e;
            }
        }

        /**
         * <p>
         * test getList(String whereClause) method,
         * iterate the create the specified times.
         * </p>
         */
        private void testGetList() {
            try {
                int id = taskTypes[0].getPrimaryId();
                for (int i = 0; i < count; i++) {
                    dao.getList("");
                }
            } catch (Exception e) {
                exception = e;
            }
        }

        /**
         * <p>
         * test delete(long primaryId) method,
         * iterate the create the specified times.
         * </p>
         */
        private void testDelete() {
            try {
                for (int i = 0; i < count; i++) {
                    dao.delete(taskTypes[i].getPrimaryId());
                }
            } catch (Exception e) {
                exception = e;
            }
        }

        /**
         * <p>
         * Get the Exception.
         * </p>
         *
         * @return the exception.
         */
        public Exception getException() {
            return exception;
        }
    }
}
