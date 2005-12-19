/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility.template.stresstests;

import com.topcoder.buildutility.template.TemplateHierarchy;
import com.topcoder.buildutility.template.persistence.JDBCPersistence;
import junit.framework.TestCase;

/**
 * <p>
 * Stress Test for TemplateHierarchy class.
 * It mainly test the AddNestedHierarchy method.
 * </p>
 *
 * @author arylio
 * @version 1.0
 */
public class TemplateHierarchyStressTest extends TestCase {

    /**
     * <p>
     * Represent the instance of TemplateHierarchy.
     * </p>
     */
    private TemplateHierarchy templateHierarchy;

    /**
     * <p>
     * Represent an instance of TemplateHierarchy, used to test add addNestedHierarchy
     * </p>
     */
    private TemplateHierarchy[] nestedHierarchys;

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
        thread_count = 10;
        operations = 1000;

        StressTestHelper.addConfigFile();
        StressTestHelper.addTestData();

        // load the persistences
        JDBCPersistence persistence = new JDBCPersistence();
        templateHierarchy = persistence.getTemplateHierarchy("Top");
        while (templateHierarchy.hasChildren())
        {
            templateHierarchy = templateHierarchy.getChildren()[0];
        }

        // creat nestedHierarchys for test.
        int total = thread_count * operations;
        nestedHierarchys = new TemplateHierarchy[thread_count * operations];
        for (int i = 0; i < total; ++i )
        {
            nestedHierarchys[i] = new TemplateHierarchy(100000 + i, "TopCoder_" + i, templateHierarchy.getId());
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
     * Test addNestedHierarchy(TemplateHierarchy templateHierarchy).
     * Concurrent convert operations through multiple threads.
     * And each thread iterate many times.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddNestedHierarchy() throws Exception {

        TemplateHierarchyTester[] threads = new TemplateHierarchyTester[thread_count];

        long totalTimeSpent = 0;
        for (int i = 0; i < thread_count; i++) {
            threads[i] = new TemplateHierarchyTester(i, operations);
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

        System.out.println("-----stress test for AddNestedHierarchy----");
        System.out.println("Threads: " + thread_count);
        System.out.println("Operations/Thread: " + operations);
        System.out.println("totaltime:" + totalTimeSpent + "ms");
    }

    /**
     * <p>
     * Test Thread for TemplateHierarchy,
     * if should set the thread count, used to the loop operation.
     * </p>
     */
    private class TemplateHierarchyTester extends Thread {
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
         * Represent the exception, thrown when addNestedHierarchy.
         * </p>
         */
        private Exception exception = null;

        /**
         * <p>
         * Constructor, save the argument.
         * </p>
         *
         * @param id the thread index.
         * @param count the operation count.
         */
        public TemplateHierarchyTester(int id, int count) {
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
                    templateHierarchy.addNestedHierarchy(nestedHierarchys[id * operations + i]);
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
