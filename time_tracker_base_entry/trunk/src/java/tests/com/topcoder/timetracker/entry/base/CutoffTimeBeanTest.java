/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import junit.framework.TestCase;

import java.util.Date;


/**
 * Test CutoffTimeBean.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class CutoffTimeBeanTest extends TestCase {
    /** Default CutoffTimeBean instance used in this test. */
    private CutoffTimeBean bean;

    /**
     * Tests {@link CutoffTimeBean#CutoffTimeBean()}. The instance should be created and field values should be
     * as expected.
     */
    public void testCutoffTimeBean() {
        assertNotNull("CutoffTimeBean should be created", bean);
        assertNull("initially cutoffTime should be null", bean.getCutoffTime());
        assertEquals("initially compId should be 0", 0, bean.getCompanyId());
    }

    /**
     * Tests {@link CutoffTimeBean#getCompanyId()}, compId should be obtained as expected.
     */
    public void testGetCompanyId() {
        long id = 123;
        bean.setCompanyId(id);
        assertEquals("compId should be obtained as expected", id, bean.getCompanyId());
    }

    /**
     * Tests {@link CutoffTimeBean#getCutoffTime()}, the time should be obtained as expected.
     */
    public void testGetCutoffTime() {
        Date date = new Date();
        bean.setCutoffTime(date);

        assertEquals("cutoffTime should be set", date, bean.getCutoffTime());
    }

    /**
     * Tests {@link CutoffTimeBean#setCompanyId()}, compId should be set as expected.
     */
    public void testSetCompanyId() {
        long id = 123;
        bean.setCompanyId(id);
        assertEquals("compId should be obtained as expected", id, bean.getCompanyId());
    }

    /**
     * Tests {@link CutoffTimeBean#setCompanyId()} with not positive id, IAE is expected.
     */
    public void testSetCompanyIdNotPositive() {
        try {
            bean.setCompanyId(0);
            fail("not positive compId will cause IAE");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link CutoffTimeBean#setCutoffTime()}, the time should be set.
     */
    public void testSetCutoffTime() {
        Date date = new Date();
        bean.setCutoffTime(date);

        assertEquals("cutoffTime should be set", date, bean.getCutoffTime());
    }

    /**
     * Tests {@link CutoffTimeBean#setCutoffTime()} with null, IAE is expected.
     */
    public void testSetCutoffTimeNull() {
        try {
            bean.setCutoffTime(null);
            fail("null cutoffTime will cause IAE");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        bean = new CutoffTimeBean();
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        bean = null;
    }
}
