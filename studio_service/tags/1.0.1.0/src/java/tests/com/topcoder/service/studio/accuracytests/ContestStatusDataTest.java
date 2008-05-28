/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import java.util.Arrays;

import junit.framework.TestCase;

import com.topcoder.service.studio.ContestStatusData;

/**
 * <p>
 * Accuracy tests for ContestStatusData class.
 * </p>
 *
 * @author moon.river
 * @version 1.0
 */
public class ContestStatusDataTest extends TestCase {
    /**
     * <p>
     * Represents the data to test.
     * </p>
     */
    private ContestStatusData data;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        data = new ContestStatusData();
    }

    /**
     * <p>
     * Tests setter/getter for statusId field.
     * </p>
     */
    public void testStatusId() {
        data.setStatusId(35);
        assertEquals("The status id is wrong.", 35, data.getStatusId());
    }

    /**
     * <p>
     * Tests setter/getter for name field.
     * </p>
     */
    public void testName() {
        data.setName("test");
        assertEquals("The name is wrong.", "test", data.getName());
    }

    /**
     * <p>
     * Tests setter/getter for description field.
     * </p>
     */
    public void testDescription() {
        data.setDescription("test");
        assertEquals("The description is wrong.", "test", data.getDescription());
    }

    /**
     * <p>
     * Tests setter/getter for allowableNextStatus field.
     * </p>
     */
    public void testAllowableNextStatus() {
        Long[] val = new Long[] {2l, 3l};
        data.setAllowableNextStatus(Arrays.asList(val));
        assertSame("The status is wrong.", val[0], data.getAllowableNextStatus().get(0));
        assertSame("The status is wrong.", val[1], data.getAllowableNextStatus().get(1));
    }
}
