/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.stresstests;

/**
 * <p>
 * Defines helper methods used in tests.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class StressTestHelper {
    /** Represents the time to run the testing method. */
    public static final int TIMES = 1000;

    /** Represents the current time. */
    private static long current = -1;

    /**
     * <p>
     * Creates a new instance of <code>StressTestHelper</code> class. The private constructor prevents the creation of
     * a new instance.
     * </p>
     */
    private StressTestHelper() {
    }

    /**
     * Start to count time.
     */
    public static void start() {
        current = System.currentTimeMillis();
    }

    /**
     * Print test result.
     *
     * @param name the test name.
     */
    public static void printResult(String name) {
        System.out.println("The test " + name + " running " + TIMES + " times, took time: " +
            (System.currentTimeMillis() - current) + " ms");
    }
}
