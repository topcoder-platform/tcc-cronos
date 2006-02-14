/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.stresstests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TaskTypeDAO;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryDAO;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.TimeStatusDAO;

/**
 * <p>
 * Stress test for TimeEntryDAO class.
 * </p>
 *
 * @author arylio
 * @version 1.0
 */
public class TimeEntryDAOStressTest extends TestCase {

    /**
     * <p>
     * Represents an instance of TimeEntryDAO, for stress test.
     * </p>
     */
    private TimeEntryDAO dao;

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
     * The primary id of tasktype, used for construct TimeEntry.
     * </p>
     */
    private int taskTypeId;

    /**
     * <p>
     * The primary id of timestatus, used for construct TimeStatus.
     * </p>
     */
    private int timeStatusId;

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

        TaskTypeDAO taskTypeDao = new TaskTypeDAO(StressHelper.PROPERTY_CONNECTION, StressHelper.NAMESPACE);
        TimeStatusDAO timeStatusDao = new TimeStatusDAO(StressHelper.PROPERTY_CONNECTION, StressHelper.NAMESPACE);

        TaskType taskType = new TaskType();
        taskType.setDescription("task type");
        taskTypeDao.create(taskType, "Topcoder");
        taskTypeId = taskType.getPrimaryId();

        TimeStatus timeStatus = new TimeStatus();
        timeStatus.setDescription("time status");
        timeStatusDao.create(timeStatus, "Topcoder");
        timeStatusId = timeStatus.getPrimaryId();

        dao = new TimeEntryDAO(StressHelper.PROPERTY_CONNECTION, StressHelper.NAMESPACE);
    }

    /**
     * <p>
     * Tear down for each class.
     * </p>
     */
    public void tearDown() throws Exception {
        StressHelper.clearTable("TimeEntries");
        StressHelper.clearTable("tasktypes");
        StressHelper.clearTable("timestatuses");
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
        testDbOperator(StressHelper.CREATE, "TimeEntryDAO.create");
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
        testDbOperator(StressHelper.UPDATE, "TimeEntryDAO.update");
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
        testDbOperator(StressHelper.GET, "TimeEntryDAO.get");
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
        testDbOperator(StressHelper.GETLIST, "TimeEntryDAO.getList");
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
        testDbOperator(StressHelper.DELETE, "TimeEntryDAO.Delete");
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
        TimeEntryDAOTester[] threads = new TimeEntryDAOTester[thread_count];

        long totalTimeSpent = 0;
        for (int i = 0; i < thread_count; i++) {
            threads[i] = new TimeEntryDAOTester(operations, operator);
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
     * Test Thread for TimeEntryDAO,
     * if should set the thread count, used to the loop operation.
     * </p>
     */
    private class TimeEntryDAOTester extends Thread {
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
         * Represent an array of TimeEntry, used to added to db.
         */
        private TimeEntry[] timeEntries;

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
        public TimeEntryDAOTester(int count, int operator) throws Exception {
            this.count = count;
            this.operator = operator;
            timeEntries = new TimeEntry[count];

            for (int i = 0; i < count; ++i) {
                timeEntries[i] = new TimeEntry();
                timeEntries[i].setDescription("TimeEntry");
                timeEntries[i].setTaskTypeId(taskTypeId);
                timeEntries[i].setTimeStatusId(timeStatusId);
                timeEntries[i].setDate(new Date());
                if (operator == StressHelper.GETLIST || operator == StressHelper.DELETE) {
                    dao.create(timeEntries[i], "topcoder");
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
                    dao.create(timeEntries[i], "topcoder");
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
                dao.create(timeEntries[0], "error");
                for (int i = 0; i < count; i++) {
                    dao.update(timeEntries[0], "topcoder" + i);
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
                dao.create(timeEntries[0], "error");
                int id = timeEntries[0].getPrimaryId();
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
                int id = timeEntries[0].getPrimaryId();
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
                    dao.delete(timeEntries[i].getPrimaryId());
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
