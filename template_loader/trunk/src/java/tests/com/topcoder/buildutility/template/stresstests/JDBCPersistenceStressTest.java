/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility.template.stresstests;

import com.topcoder.buildutility.template.persistence.JDBCPersistence;
import junit.framework.TestCase;

/**
 * <p>
 * Stress Test for JDBCPersistence class.
 * </p>
 *
 * @author arylio
 * @version 1.0
 */
public class JDBCPersistenceStressTest extends TestCase {

    /**
     * <p>
     * Represnets the instance of JDBCPersistence.
     * </p>
     */
    private JDBCPersistence[] persistences;

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
     * Set up for each test.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void setUp() throws Exception{
        super.setUp();

        StressTestHelper.addConfigFile();
        StressTestHelper.addTestData();

        thread_count = 10;
        operations = 100;
        persistences = new JDBCPersistence[thread_count];
        for (int i = 0; i < thread_count ; ++i)
        {
            persistences[i] = new JDBCPersistence();
        }
    }

    /**
     * <p>
     * Tear down for each test.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void tearDown() throws Exception {
        StressTestHelper.removeTestData();
        StressTestHelper.removeConfigFile();

        super.tearDown();
    }

    /**
     * <p>
     * Test getTemplateHierarchy(String name).
     * Concurrent convert operations through multiple threads.
     * And each thread iterate many times.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetTemplateHierarchy() throws Exception {
        JDBCPersistenceTester[] threads = new JDBCPersistenceTester[thread_count];

        long totalTimeSpent = 0;
        for (int i = 0; i < thread_count; i++) {
            threads[i] = new JDBCPersistenceTester(i, operations);
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

        System.out.println("-----stress test for getTemplateHierarchy----");
        System.out.println("Threads: " + thread_count);
        System.out.println("Operations/Thread: " + operations);
        System.out.println("totaltime:" + totalTimeSpent + "ms");
    }

    /**
     * <p>
     * Test Thread for JDBCPersistence,
     * if should set the thread count, used to the loop operation.
     * </p>
     */
    private class JDBCPersistenceTester extends Thread {
        /**
         * <p>
         * Represent the thread unique id.
         * </p>
         */
        private int id;

        /**
         * <p>
         * Represent the operation count.
         * </p>
         */
        private int count;

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
         */
        public JDBCPersistenceTester(int id, int count) {
            this.id = id;
            this.count = count;
        }

        /**
         * <p>
         * run.
         * </p>
         */
        public void run() {
            try {
                for (int i = 0; i < count; i++) {
                    persistences[id].getTemplateHierarchy("Top");
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
