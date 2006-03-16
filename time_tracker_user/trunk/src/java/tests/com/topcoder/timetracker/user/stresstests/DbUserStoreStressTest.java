/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.stresstests;

import com.topcoder.timetracker.user.DbUserStore;
import com.topcoder.timetracker.user.User;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.TestCase;

/**
 * <p>
 * Stress test for <code>DbUserStore</code> class.
 * </p>
 * 
 * 
 * @author arylio
 * @version 1.0
 */
public class DbUserStoreStressTest extends TestCase {
    /**
     * <p>
     * Const defined for operator, getNames.
     * </p>
     */
    private static final int GET = 1;

    /**
     * <p>
     * Const defined for operator, search.
     * </p>
     */
    private static final int SEARCH = 2;

    /**
     * <p>
     * Const defined for operator, contain.
     * </p>
     */
    private static final int CONTAIN = 3;

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
     * Represents an instance of DbUserStore, to test it's member.
     * </p>
     */
    private DbUserStore userStore;

    /**
     * <p>
     * Represents an instace of IDGenerator implementation, to generate id.
     * </p>
     */
    private IDGenerator generator = null;

    /**
     * <p>
     * Represents the users added for test.
     * </p>
     */
    private static User[] users = null;

    /**
     * <p>
     * Represents the tested test case count.
     */
    private static int testCount = 0;

    /**
     * <p>
     * Set up for eacht test.
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit.
     */
    public void setUp() throws Exception {
        super.setUp();
        StressHelper.addConfigFile();

        generator = IDGeneratorFactory
                .getIDGenerator(StressHelper.IDGENERATOR_NAME);
        userStore = new DbUserStore();
        userStore.setConnectionString("Informix");
    }

    /**
     * <p>
     * Tear down for each class.
     * </p>
     */
    public void tearDown() throws Exception {

        StressHelper.clearTable("DefaultUsers");
        StressHelper.clearTable("Users");
        StressHelper.removeConfigFile();
        super.tearDown();
    }

    /**
     * <p>
     * Stress test getNames(). Concurrent convert operations through multiple
     * threads. And each thread iterate many times.
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testGetNames() throws Exception {
        thread_count = 10;
        operations = 50;
        users = new User[operations];
        StressHelper.addTestUser(generator, operations, users);
        testDbUserStore(GET, "DbUserStore.getNames");
    }

    /**
     * <p>
     * Stress test search(String pattern). Concurrent convert operations through
     * multiple threads. And each thread iterate many times.
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testSearch() throws Exception {
        thread_count = 10;
        operations = 50;

        users = new User[operations];
        StressHelper.addTestUser(generator, operations, users);
        testDbUserStore(SEARCH, "DbUserStore.search");
    }

    /**
     * <p>
     * Stress test contains(String name). Concurrent convert operations through
     * multiple threads. And each thread iterate many times.
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit.
     */
    public void testContains() throws Exception {

        thread_count = 10;
        operations = 50;

        users = new User[operations];
        StressHelper.addTestUser(generator, operations, users);
        testDbUserStore(CONTAIN, "DbUserStore.contains");
    }

    /**
     * <p>
     * Test the UserManager operations.
     * </p>
     * 
     * @param operator
     *            the operator id.
     * @param name
     *            the operate name for tip.
     * 
     * @throws Exception
     *             Exception to JUnit.
     */
    private void testDbUserStore(int operator, String name) throws Exception {
        DbUserStoreTester[] threads = new DbUserStoreTester[thread_count];

        long totalTimeSpent = 0;
        for (int i = 0; i < thread_count; i++) {
            threads[i] = new DbUserStoreTester(operations, operator);
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
                System.out.println(threads[i].getException().getMessage()
                        + "exception was thrown.");
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
     * Test Thread for DbUserStore, if should set the thread count, used to the
     * loop operation.
     * </p>
     */
    private class DbUserStoreTester extends Thread {
        /**
         * <p>
         * Represent the operation count.
         * </p>
         */
        private int count;

        /**
         * <p>
         * Represent the operator to test.
         * </p>
         */
        private int operator;

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
         * @param count
         *            the operation count.
         * 
         * @throws Exception
         *             Exception to JUnit.
         */
        public DbUserStoreTester(int count, int operator) throws Exception {
            this.count = count;
            this.operator = operator;
        }

        /**
         * <p>
         * run.
         * </p>
         */
        public void run() {
            switch (operator) {
            case GET:
                testGetNames();
                break;
            case SEARCH:
                testSearch();
                break;
            case CONTAIN:
                testContains();
                break;
            }
        }

        /**
         * <p>
         * test getNames method, iterate the specified times.
         * </p>
         *  
         */
        public void testGetNames() {
            try {
                for (int i = 0; i < count; i++) {
                    userStore.getNames();
                }
            } catch (Exception e) {
                exception = e;
            }
        }

        /**
         * <p>
         * test removeUser(), iterate the specified times.
         * </p>
         */
        public void testSearch() {
            try {
                for (int i = 0; i < count; i++) {
                    userStore.search(users[i].getName());
                }
            } catch (Exception e) {
                exception = e;
            }
        }

        /**
         * <p>
         * test contains method, iterate the specified times.
         * </p>
         */
        private void testContains() {
            try {
                for (int i = 0; i < count; i++) {
                    userStore.contains(users[i].getName());
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