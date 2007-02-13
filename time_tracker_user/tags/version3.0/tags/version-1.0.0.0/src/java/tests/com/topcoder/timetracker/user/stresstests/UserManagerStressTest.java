/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.stresstests;

import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserManager;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.TestCase;

/** 
 * <p>
 * Stress test for <code>UserManager</code> class.
 * </p>
 * 
 * 
 * @author arylio
 * @version 1.0
 */
public class UserManagerStressTest extends TestCase {
    
    /**
     * <p>
     * Const defined for operator, importUser.
     * </p> 
     */
    private static final int IMPORT = 1;
    
    /**
     * <p>
     * Const defined for operator, removeUser.
     * </p> 
     */
    private static final int REMOVE = 2;
    
    /**
     * <p>
     * Const defined for operator, setUserRole.
     * </p> 
     */
    private static final int SET = 3;
    
    /**
     * <p>
     * Const defined for operator, authenticate.
     * </p> 
     */
    private static final int AUTHENTICATE = 4;
    
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
     * Represents the UserManager instance used to test.
     */
    private UserManager manager;
    
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
     * Stress test importUser.
     * Concurrent convert operations through multiple threads.
     * And each thread iterate many times.
     * </p>
     */
    public void testImportUser() throws Exception {
        thread_count = THREAD_COUNT;
        operations = 20;
        
        // add test users 
        users = new User[thread_count * operations];
        StressHelper.addUserManagerTestData(generator, thread_count * operations, users);
        
        testUserManager(IMPORT, "UserManager.importUser");
    }
    
    /**
     * <p>
     * Stress test removeUser.
     * Concurrent convert operations through multiple threads.
     * And each thread iterate many times.
     * </p>
     */
    public void testRemoveUser() throws Exception {
        thread_count = THREAD_COUNT;
        operations = 20;
        
        // add test users 
        users = new User[thread_count * operations];
        StressHelper.addUserManagerTestData(generator, thread_count * operations, users);
        
        testUserManager(REMOVE, "UserManager.removeUser");
    }
    
    
    /**
     * <p>
     * Stress test setUserRole.
     * Concurrent convert operations through multiple threads.
     * And each thread iterate many times.
     * </p>
     */
    public void testSetUserRole() throws Exception {
        thread_count = THREAD_COUNT;
        operations = 20;
        
        users = new User[operations];
        StressHelper.addUserManagerTestData(generator, operations, users);
        
        testUserManager(SET, "UserManager.setUserRole");
    }
    
    /**
     * <p>
     * Stress test authenticate.
     * Concurrent convert operations through multiple threads.
     * And each thread iterate many times.
     * </p>
     */
    public void testAuthenticate() throws Exception {
        thread_count = THREAD_COUNT;
        operations = 20;
        
        users = new User[operations];
        StressHelper.addUserManagerTestData(generator, operations, users);
        
        testUserManager(AUTHENTICATE, "UserManager.authenticate");
    }
    
    /**
     * <p>
     * Test the UserManager operations.
     * </p>
     *
     * @param operator the operator id.
     * @param name the operate name for tip.
     *
     * @throws Exception Exception to JUnit.
     */
    private void testUserManager(int operator, String name) throws Exception {
        UserManagerTester[] threads = new UserManagerTester[thread_count];

        long totalTimeSpent = 0;
        for (int i = 0; i < thread_count; i++) {
            threads[i] = new UserManagerTester(operations, operator, i);
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
     * Test Thread for UserManager,
     * if should set the thread count, used to the loop operation.
     * </p>
     */
    private class UserManagerTester extends Thread {
        
        /**
         * <p>
         * The unique test thread id.
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
        public UserManagerTester(int count, int operator, int id) throws Exception {
            this.count = count;
            this.operator = operator;
            this.id = id;
            manager = new UserManager();
        }

        /**
         * <p>
         * run.
         * </p>
         */
        public void run() {
            switch (operator) {
            case IMPORT:
                testImportUser();
                break;
            case REMOVE:
                testRemoveUser();
                break;
            case SET:
                testSetUserRole();
                break;
            case AUTHENTICATE:
                testAuthenticate();
                break;
            }
        }
        
        /**
         * <p>
         * test importUser method,
         * iterate the specified times.
         * </p>
         *
         */
        public void testImportUser() {
            try {
                int start = id * operations;
                for (int i = 0; i < count; i++) {
                    manager.importUser(users[i + start].getName(), 
                            users[i + start].getUserStoreName());
                }
            } catch (Exception e) {
                exception = e;
            }
        }
        
        /**
         * <p>
         * test removeUser(),
         * iterate the specified times.
         * </p>
         */
        public void testRemoveUser() {
            try {
                int start = id * operations;
                for (int i = 0; i < count; i++) {
                    manager.removeUser(users[i + start].getName());
                }
            } catch (Exception e) {
                exception = e;
            }     
        }
        
        /**
         * <p>
         * test setUserRole method, 
         * iterate the specified times.
         * </p>
         */
        private void testSetUserRole() {
            try {
                for (int i = 0; i < count; i++) {
                    manager.setUserRole(users[i].getName(), UserManager.ACCOUNT_MANAGER);
                }
            } catch (Exception e) {
                exception = e;
            }
        }
        
        /**
         * <p>
         * test authenticate method,
         * iterate the specified times.
         * </p>
         */
        private void testAuthenticate() {
            try {
                for (int i = 0; i < count; i++) {
                    // authenticate the user, with password as the name
                    manager.authenticate(users[i].getName(), users[i].getName());
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
