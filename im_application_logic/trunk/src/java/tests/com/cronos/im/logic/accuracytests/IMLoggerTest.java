/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic.accuracytests;

import java.text.DateFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.im.logic.IMLogger;
import com.topcoder.util.log.Level;

/**
 * Accuracy test for <code>{@link com.cronos.im.logic.IMLogger}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class IMLoggerTest extends TestCase {
    /**
     * <p>
     * Represents the IMLogger for testing.
     * </p>
     */
    private IMLogger logger;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadConfigs();
        logger = new IMLogger("accuracy_tests", DateFormat.getDateInstance());
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.releaseConfigs();
    }

    /**
     * Accuracy test for <code>{@link IMLogger#IMLogger(DateFormat)}</code>.
     */
    public void testConstructor_DateFormat() {
        logger = new IMLogger(DateFormat.getDateInstance());
        assertNotNull("Failed to create IMLogger", logger);
    }

    /**
     * Accuracy test for <code>{@link IMLogger#IMLogger(String,DateFormat)}</code>.
     */
    public void testConstructor_String_DateFormat() {
        assertNotNull("Failed to create IMLogger", logger);
    }

    /**
     * Accuracy test for <code>{@link IMLogger#log(Level,String,String[])}</code>.
     */
    public void testMethodLog_Level_String_StringArray() {
        // check None is there in the log.
        logger.log(Level.INFO, "Accuracy Testing", new String[] {});
        // check the entity is there.
        logger.log(Level.INFO, "Accuracy Testing", new String[] { "acc_entity" });
        // check the entity is none
        logger.log(Level.INFO, "Accuracy Testing", new String[] { null });
    }

    /**
     * Accuracy test for <code>{@link IMLogger#log(Level,String,long[],long[])}</code>.
     */
    public void testMethodLog_Level_String_longArray_longArray() {
        // check None is there in the log.
        logger.log(Level.INFO, "Accuracy Testing", new long[] {}, new long[] {});
        // check the userIds and sessionIds are there
        logger.log(Level.INFO, "Accuracy Testing", new long[] { 1 }, new long[] { 101 });
    }

    /**
     * Accuracy test for <code>{@link IMLogger#log(Level,String)}</code>.
     */
    public void testMethodLog_Level_String() {
        logger.log(Level.INFO, "Accuracy Testing");
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(IMLoggerTest.class);
    }
}
