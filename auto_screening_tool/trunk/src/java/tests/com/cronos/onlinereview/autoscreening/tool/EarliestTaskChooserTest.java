/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.onlinereview.autoscreening.tool.chooser.EarliestTaskChooser;

/**
 * The unit test cases for class EarliestTaskChooser.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EarliestTaskChooserTest extends BaseTestCase {

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(EarliestTaskChooserTest.class);
    }

    /**
     * Sets up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

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
     * Accuracy test of the constructor <code>EarliestTaskChooser()</code>.
     * </p>
     * <p>
     * An instance of EarliestTaskChooser should be created successfully.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor1() throws Exception {
        ScreeningTaskChooser chooser = new EarliestTaskChooser();

        assertNotNull("check the instance", chooser);
    }

    /**
     * <p>
     * Accuracy test of the method
     * <code>ScreeningTask chooseScreeningTask(ScreeningTask[] tasks)</code>.
     * </p>
     * <p>
     * the task with earliest create date is selected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyChooseScreeningTask1() throws Exception {
        ScreeningTaskChooser chooser = new EarliestTaskChooser();

        Date now = new Date();

        ScreeningTask task1 = new ScreeningTask();
        task1.setCreationDate(now);

        ScreeningTask task2 = new ScreeningTask();
        task2.setCreationDate(new Date(now.getTime() - 123456));

        ScreeningTask task3 = new ScreeningTask();
        task3.setCreationDate(new Date(now.getTime() + 123456));

        ScreeningTask task = chooser.chooseScreeningTask(new ScreeningTask[] {task1, task2, task3});

        assertEquals("check selected task", task2, task);
    }

    /**
     * <p>
     * Failure test of the method
     * <code>ScreeningTask chooseScreeningTask(ScreeningTask[] tasks)</code>.
     * </p>
     * <p>
     * tasks is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureChooseScreeningTask1() throws Exception {
        ScreeningTaskChooser chooser = new EarliestTaskChooser();

        try {
            chooser.chooseScreeningTask(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "tasks should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>ScreeningTask chooseScreeningTask(ScreeningTask[] tasks)</code>.
     * </p>
     * <p>
     * tasks is empty. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureChooseScreeningTask2() throws Exception {
        ScreeningTaskChooser chooser = new EarliestTaskChooser();

        try {
            chooser.chooseScreeningTask(new ScreeningTask[] {});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "tasks should not be empty.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method
     * <code>ScreeningTask chooseScreeningTask(ScreeningTask[] tasks)</code>.
     * </p>
     * <p>
     * tasks contains null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureChooseScreeningTask3() throws Exception {
        ScreeningTaskChooser chooser = new EarliestTaskChooser();

        try {
            chooser.chooseScreeningTask(new ScreeningTask[] {null, new ScreeningTask()});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "tasks should not contain null elements.", e.getMessage());
        }
    }
}