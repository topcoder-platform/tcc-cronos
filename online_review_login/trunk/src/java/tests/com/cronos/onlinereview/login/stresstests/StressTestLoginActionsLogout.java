/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.login.stresstests;

import junit.framework.TestCase;

/**
 * Stress test for class <code>LoginActions#logout </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class StressTestLoginActionsLogout extends TestCase {

    /**
     * Stress test for running 100 users to logout.
     *
     * @throws Exception
     *             ot junit.
     */
    public void testLogout_with100Threads() throws Exception {
        Thread[] logoutThreads = new Thread[100];

        long total = 0;

        for (int i = 0; i < logoutThreads.length; i++) {
            logoutThreads[i] = new LogoutThread();

            long start = System.currentTimeMillis();

            logoutThreads[i].start();
            total += System.currentTimeMillis() - start;
        }

        for (int i = 0; i < logoutThreads.length; i++) {
            logoutThreads[i].join();
        }

        System.out.println("Running 100 user for logout cost " + total / 1000.0 + " seconds.");
    }

    /**
     * Stress test for running 1000 users to logout.
     *
     * @throws Exception
     *             to junit.
     */
    public void testLogout_with1000Threads() throws Exception {
        Thread[] logoutThreads = new Thread[1000];

        long total = 0;

        for (int i = 0; i < logoutThreads.length; i++) {
            logoutThreads[i] = new LogoutThread();

            long start = System.currentTimeMillis();

            logoutThreads[i].start();
            total += System.currentTimeMillis() - start;
        }

        for (int i = 0; i < logoutThreads.length; i++) {
            logoutThreads[i].join();
        }

        System.out.println("Running 1000 user for logout cost " + total / 1000.0 + " seconds.");

    }

    /**
     * A Logout Thread for logout process
     */
    private class LogoutThread extends Thread {
        /**
         * Represents a Logout test instance for running logout.
         */
        private LogoutTest logout = null;

        /**
         * Create a LogoutThread instance for test.
         *
         * @throws Exception
         *             to junit.
         */
        LogoutThread() throws Exception {
            logout = new LogoutTest();
        }

        /**
         * Run the thread.
         */
        public void run() {
            try {
                logout.testLogout();
            } catch (Exception e) {
                fail("Run method logout failed.");
            }
        }
    }
}
