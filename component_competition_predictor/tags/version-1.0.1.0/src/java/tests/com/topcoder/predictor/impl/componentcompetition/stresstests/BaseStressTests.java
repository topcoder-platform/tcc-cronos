/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.stresstests;

import junit.framework.TestCase;

/**
 * Base class for stress test.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class BaseStressTests extends TestCase {

    /**
     * stress run times.
     */
    protected static final int TIMES = 20;

    /**
     * The start time.
     */
    private long start;

    /**
     * begin.
     */
    protected void begin() {
        start = System.currentTimeMillis();
    }

    /**
     * Print the message.
     *
     * @param method
     *            the method name.
     */
    protected void print(String method) {
        long end = System.currentTimeMillis();

        System.out.println("Run method:" + method + " " + TIMES + " times, cost time:" + (end - start)
                + " milliseconds.");
    }
}
