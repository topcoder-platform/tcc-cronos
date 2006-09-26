/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.failuretests;

import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.cronos.onlinereview.autoscreening.tool.chooser.EarliestTaskChooser;
import com.cronos.onlinereview.autoscreening.tool.rules.DirectoryStructureRule;

import junit.framework.TestCase;

/**
 * Failure tests for <code>DAOLogger</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class EarliestTaskChooserTest extends TestCase {

    /**
     * Represents the chooser to test.
     */
    private EarliestTaskChooser chooser = new EarliestTaskChooser();

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig();
    }

    /**
     * Clean up the environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }

    /**
     * Test method for chooseScreeningTask(ScreeningTask[]).
     * In this case, the tasks are null.
     */
    public void testChooseScreeningTask_Null() {
        try {
            chooser.chooseScreeningTask(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for chooseScreeningTask(ScreeningTask[]).
     * In this case, the tasks are empty.
     */
    public void testChooseScreeningTask_Empty() {
        try {
            chooser.chooseScreeningTask(new ScreeningTask[0]);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for chooseScreeningTask(ScreeningTask[]).
     * In this case, the tasks contains null.
     */
    public void testChooseScreeningTask_ContainsNull() {
        try {
            chooser.chooseScreeningTask(new ScreeningTask[1]);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
