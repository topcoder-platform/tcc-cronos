/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import java.sql.ResultSet;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.util.config.ConfigManager;

/**
 * The unit test cases for class Screener.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ScreenerTest extends BaseTestCase {

    /**
     * Represents the path of the screener config file.
     */
    protected static final String SCREENER_CONFIG_FILE = "screener.xml";

    /**
     * Represents the path of the object factory config file.
     */
    protected static final String OBJECT_FACTORY_CONFIG_FILE = "object_factory.xml";

    /**
     * Represents the config namespace of the screener.
     */
    protected static final String SCREENER_NAMESPACE = "com.cronos.onlinereview.autoscreening.tool.Screener";

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ScreenerTest.class);
    }

    /**
     * Sets up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        ConfigManager.getInstance().add(OBJECT_FACTORY_CONFIG_FILE);
        ConfigManager.getInstance().add(SCREENER_CONFIG_FILE);
    }

    /**
     * Clean up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>Screener(long screenerId, String namespace)</code>.
     * </p>
     * <p>
     * An instance of Screener should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor1() throws Exception {
        Screener screener = new Screener(123, SCREENER_NAMESPACE);

        assertNotNull("check the instance", screener);
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>Screener(long screenerId, String namespace)</code>.
     * </p>
     * <p>
     * screenerId is -1. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtor1() throws Exception {

        try {
            new Screener(-1, SCREENER_NAMESPACE);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "screenerId should be > 0.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>Screener(long screenerId, String namespace)</code>.
     * </p>
     * <p>
     * namespace is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtor2() throws Exception {

        try {
            new Screener(123, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "namespace should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>Screener(long screenerId, String namespace)</code>.
     * </p>
     * <p>
     * namespace is empty. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCtor3() throws Exception {

        try {
            new Screener(123, "   ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "namespace should not be empty (trimmed).", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>void initialize()</code>.
     * </p>
     * <p>
     * the tasks with status 'screening' of this screener will be rollbacked to
     * 'pending'.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyInitialize1() throws Exception {
        Screener screener = new Screener(1, SCREENER_NAMESPACE);

        int count = doScalarQuery("SELECT COUNT(*) FROM screening_task WHERE screener_id=1",
            new Object[0]);
        assertEquals("check # of task of screener id 1 before init", 1, count);

        screener.initialize();

        count = doScalarQuery("SELECT COUNT(*) FROM screening_task WHERE screener_id=1",
            new Object[0]);
        assertEquals("check # of task of screener id 1 after init", 0, count);
    }

    /**
     * <p>
     * Accuracy test of the method <code>void screen()</code>.
     * </p>
     * <p>
     * perform the screening. The tester should check the console output.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyScreen1() throws Exception {
        Screener screener = new Screener(123, SCREENER_NAMESPACE);

        screener.screen();

        // have a look at the database
        ResultSet resultSet = doSQLQuery("SELECT * FROM screening_result", new Object[] {});
        int count = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i < count; ++i) {
                System.out.print(resultSet.getObject(i));
                System.out.print('\t');
            }
            System.out.println();
        }
        resultSet.close();
    }

    /**
     * <p>
     * Failure test of the method <code>void screen()</code>.
     * </p>
     * <p>
     * no screening logics for the selected task. ScreeningException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureScreen1() throws Exception {
        Screener screener = new Screener(123, SCREENER_NAMESPACE);

        doSQLUpdate("DELETE FROM screening_task WHERE screening_task_id=4", new Object[] {});

        try {
            screener.screen();
            fail("ScreeningException should be thrown");
        } catch (ScreeningException e) {
            assertEquals("check message",
                "Unable to find the screening logics for the selected screening task "
                    + "with taskId [3] with projectId [3] and projectCategoryId [3].", e
                    .getMessage());
        }
    }
}