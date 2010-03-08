/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.failuretests;

import junit.framework.TestCase;

import com.topcoder.service.actions.AggregateDataModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Failure tests for AggregateDataModel.
 *
 * @author morehappiness
 * @version 1.0
 */
public class AggregateDataModelFailureTests {
    /**
     * The model for tests.
     */
    private AggregateDataModel model;

    /**
     * Sets up the environment.
     */
    @Before
    public void setupModel() {
        model = new AggregateDataModel();
    }

    /**
     * Test for setData. If argument's key is null, IllegalArgumentException.class is expected.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setData_failure1() {
        model.setData(null, null);
        fail("IllegalArgumentException is expected");
    }

    /**
     * Test for setData. If argument's key is empty, IllegalArgumentException.class is expected.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setData_failure2() {
        model.setData("  \t\n   ", null);
        fail("IllegalArgumentException is expected");
    }
    
    /**
     * Test for getData. If argument's key is null, IllegalArgumentException.class is expected.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getData_failure1() {
        model.getData(null);
        fail("IllegalArgumentException is expected");
    }

    /**
     * Test for getData. If argument's key is empty, IllegalArgumentException.class is expected.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getData_failure2() {
        model.getData("  \t\n   ");
        fail("IllegalArgumentException is expected");
    }
}
