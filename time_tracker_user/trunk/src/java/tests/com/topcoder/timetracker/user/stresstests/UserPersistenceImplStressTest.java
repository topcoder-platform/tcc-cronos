/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.stresstests;

import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserPersistenceImpl;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.TestCase;

/** 
 * <p>
 * Stress test for <code>UserPersistenceImpl</code> class.
 * </p>
 * 
 * 
 * @author arylio
 * @version 1.0
 */
public class UserPersistenceImplStressTest extends TestCase {

    /**
     * <p>
     * Const defined for operator, add a user to persistence.
     * </p> 
     */
    private static final int ADD = 1;

    /**
     * <p>
     * Const defined for operator, remove a user from persistence.
     * </p> 
     */
    private static final int REMOVE = 2;

    /**
     * <p>
     * Const defined for operator, get all users from persistence.
     * </p> 
     */
    private static final int GET = 3;
    
    /**
     * <p>
     * Const defined the number of threads to execute.
     * </p>
     */
    private static final int THREAD_COUNT = 1;

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
     * Represents an instance of UserPersistenceImpl, to test it's member.
     * </p>
     */
    private UserPersistenceImpl persistence;

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
    private User[] users = null;

    /**
     * <p>
     * Set up for eacht test.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void setUp() throws Exception {
        super.setUp();
        StressHelper.addConfigFile();
        
        StressHelper.clearTables();

        generator = IDGeneratorFactory.getIDGenerator(StressHelper.IDGENERATOR_NAME);
        persistence = new UserPersistenceImpl(StressHelper.NAMESPACE);
    }

    /**
     * <p>
     * Tear down for each class.
     * </p>
     */
    public void tearDown() throws Exception {
        StressHelper.clearTables();

        StressHelper.removeConfigFile();
        super.tearDown();
    }

    /**
     * <p>
     * Stress test addUser(User user).
     * Concurrent convert operations through multiple threads.
     * And each thread iterate many times.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddUser() throws Exception {
        thread_count = THREAD_COUNT;
        operations = 50;
        testUserPersistenceImpl(ADD, "UserPersistenceImpl.addUser");
    }

    /**
     * <p>
     * Stress test removeUser(User user).
     * Concurrent convert operations through multiple threads.
     * And each thread iterate many times.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRemoveUser() throws Exception {
        thread_count = THREAD_COUNT;
        operations = 30;

        // add some data to test
        users = new User[operations];
        StressHelper.addTestUser(generator, operations, users);
        testUserPersistenceImpl(REMOVE, "UserPersistenceImpl.removeUser");
    }

    /**
     * <p>
     * Stress test getUsers().
     * Concurrent convert operations through multiple threads.
     * And each thread iterate many times.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetUsers() throws Exception {
        thread_count = THREAD_COUNT;
        operations = 50;

        // add some data to test
        users = new User[operations];
        StressHelper.addTestUser(generator, operations, users);
        testUserPersistenceImpl(GET, "UserPersistenceImpl.getUsers");
    }

    /**
     * <p>
     * Test the UserPersisteneceImpl operations.
     * </p>
     *
     * @param operator the operator id.
     * @param name the operate name for tip.
     *
     * @throws Exception Exception to JUnit.
     */
    private void testUserPersistenceImpl(int operator, String name)
            throws Exception {
        UserPersistenceImplTester[] threads = new UserPersistenceImplTester[thread_count];

        long totalTimeSpent = 0;
        for (int i = 0; i < thread_count; i++) {
            threads[i] = new UserPersistenceImplTester(operations, operator);
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
     * Test Thread for UserPersistenceImpl,
     * if should set the thread count, used to the loop operation.
     * </p>
     */
    private class UserPersistenceImplTester extends Thread {
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
         * @param count the operation count.
         *
         * @throws Exception Exception to JUnit.
         */
        public UserPersistenceImplTester(int count, int operator)
                throws Exception {
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
            case ADD:
                testAddUser();
                break;
            case REMOVE:
                testRemoveUser();
                break;
            case GET:
                testGetUsers();
                break;
            }
        }

        /**
         * <p>
         * test addUser(User user),
         * iterate the specified times.
         * </p>
         */
        private void testAddUser() {
            try {
                for (int i = 0; i < count; i++) {
                    int id = (int) generator.getNextID();
                    persistence.addUser(new User(id, "topcoder_" + id, "StressTest1"));
                }
            } catch (Exception e) {
                exception = e;
            }
        }

        /**
         * <p>
         * test removeUser(User user) method,
         * iterate the specified times.
         * </p>
         */
        private void testRemoveUser() {
            try {
                for (int i = 0; i < count; i++) {
                    persistence.removeUser(users[i]);
                }
            } catch (Exception e) {
                exception = e;
            }
        }

        /**
         * <p>
         * test getUsers() method,
         * iterate the specified times.
         * </p>
         */
        private void testGetUsers() {
            try {
                for (int i = 0; i < count; i++) {
                    persistence.getUsers();
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