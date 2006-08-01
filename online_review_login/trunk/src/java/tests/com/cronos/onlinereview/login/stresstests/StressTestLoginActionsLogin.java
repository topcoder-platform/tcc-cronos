/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.login.stresstests;

import junit.framework.TestCase;

/**
 * Stress test for class <code>LoginActions#login </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class StressTestLoginActionsLogin extends TestCase {

    /**
     * Stress test for login for 100 threads.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testLogin_With100Thread() throws Exception {
        Thread[] thread = new Thread[100];

        long total = 0;
        for (int i = 0; i < thread.length; i++) {
            thread[i] = new LoginThread();

            long start = System.currentTimeMillis();

            thread[i].start();

            total += System.currentTimeMillis() - start;
        }

        for (int i = 0; i < thread.length; i++) {
            thread[i].join();
        }

        System.out.println("Running login for 100 user, cost " + total / 1000.0 + " seconds.");
    }

    /**
     * Stress test for login for 1000 thread.
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testLogin_With1000Thread() throws Exception {
        Thread[] thread = new Thread[1000];

        long total = 0;

        for (int i = 0; i < thread.length; i++) {
            thread[i] = new LoginThread();

            long start = System.currentTimeMillis();
            thread[i].start();

            total += System.currentTimeMillis() - start;
        }

        for (int i = 0; i < thread.length; i++) {
            thread[i].join();
        }

        System.out.println("Running login for 1000 user, cost " + total / 1000.0 + " seconds.");
    }

    /**
     * A Login Thread for login process
     */
    private class LoginThread extends Thread {
        /**
         * Represents a Login test instance for running login.
         */
        private LoginTest login = null;

        /**
         * Create a LoginThread instance for test.
         *
         * @throws Exception
         *             to junit.
         */
        LoginThread() throws Exception {
            login = new LoginTest();
        }

        /**
         * Run the thread.
         */
        public void run() {
            try {
                login.testLogin();
            } catch (Exception e) {
                fail("Run method login failed.");
            }
        }
    }
}
