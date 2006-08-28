/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.accuracytests;

import java.util.Date;

import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTaskChooser;
import com.cronos.onlinereview.autoscreening.tool.chooser.EarliestTaskChooser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Accuracy test cases for <code>EarliestTaskChooser</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EarliestTaskChooserAccuracyTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(EarliestTaskChooserAccuracyTest.class);
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
        task2.setCreationDate(new Date(now.getTime() - 1));

        ScreeningTask task3 = new ScreeningTask();
        task3.setCreationDate(new Date(now.getTime() + 1));

        ScreeningTask task4 = new ScreeningTask();
        task4.setCreationDate(now);

        ScreeningTask task =
            chooser.chooseScreeningTask(new ScreeningTask[] {task1, task2, task3, task4});

        assertEquals("check selected task", task2, task);
    }

}
