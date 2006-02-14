/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.stresstests;

import java.util.Date;

import com.topcoder.timetracker.entry.time.TimeStatusDAO;
import com.topcoder.timetracker.entry.time.TimeStatus;

import junit.framework.TestCase;

/**
 * <p>
 * Stress test for TimeStatusDAO class.
 * </p>
 *
 * @author arylio
 * @version 1.0
 */
public class TimeStatusDAOStressTest extends TestCase {

    /**
     * <p>
     * Represents an instance of TimeStatusDAO, for stress test.
     * </p>
     */
    private TimeStatusDAO dao;

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

        dao = new TimeStatusDAO(StressHelper.PROPERTY_CONNECTION, StressHelper.NAMESPACE);
    }

    /**
     * <p>
     * Tear down for each class.
     * </p>
     */
    public void tearDown() throws Exception {
        StressHelper.clearTable("TimeStatuses");
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
        testDbOperator(StressHelper.CREATE, "TimeStatusDAO.create");
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
        testDbOperator(StressHelper.UPDATE, "TimeStatusDAO.update");
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
        testDbOperator(StressHelper.GET, "TimeStatusDAO.get");
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
        testDbOperator(StressHelper.GETLIST, "TimeStatusDAO.getList");
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
        testDbOperator(StressHelper.DELETE, "TimeStatusDAO.Delete");
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
        TimeStatusDAOTester[] threads = new TimeStatusDAOTester[thread_count];

        long totalTimeSpent = 0;
        for (int i = 0; i < thread_count; i++) {
            threads[i] = new TimeStatusDAOTester(operations, operator);
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
                System.out.println(threads[i].getException().getMessage() + " exception was thrown.");
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
     * Test Thread for TimeStatusDAO,
     * if should set the thread count, used to the loop operation.
     * </p>
     */
    private class TimeStatusDAOTester extends Thread {
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
         * Represent an array of TimeStatus, used to added to db.
         */
        private TimeStatus[] timeStatuses;

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
        public TimeStatusDAOTester(int count, int operator) throws Exception {
            this.count = count;
            this.operator = operator;
            timeStatuses = new TimeStatus[count];
            for (int i = 0; i < count; ++i) {
                timeStatuses[i] = new TimeStatus();
                timeStatuses[i].setDescription("TimeStatus");
                if (operator == StressHelper.GETLIST || operator == StressHelper.DELETE) {
                    dao.create(timeStatuses[i], "topcoder");
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
                    dao.create(timeStatuses[i], "topcoder");
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
                dao.create(timeStatuses[0], "error");
                for (int i = 0; i < count; i++) {
                    dao.update(timeStatuses[0], "topcoder" + i);
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
                dao.create(timeStatuses[0], "error");
                int id = timeStatuses[0].getPrimaryId();
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
                int id = timeStatuses[0].getPrimaryId();
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
                for (int i = 0; i < count; ++i) {
                    dao.delete(timeStatuses[i].getPrimaryId());
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
