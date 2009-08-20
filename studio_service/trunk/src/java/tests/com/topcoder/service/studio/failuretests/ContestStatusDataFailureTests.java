/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.studio.failuretests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.service.studio.ContestStatusData;

/**
 * Generated failure test for the <code>ContestStatusData.java</code> class.
 *
 * @author kakarotto
 * @version 1.0
 */
public class ContestStatusDataFailureTests {
    /** The flag to denote whether to test with value overrides. */
    private boolean useOverrides = true;

    /** The flag to denote whether to test with default value. */
    private boolean useDefaults = true;

    /** The object to test. */
    private ContestStatusData testObject;

    /**
     * Sets up testing environment
     *
     * @throws Exception when error occurs
     */
    @Before
    public void setUp() throws Exception {
        testObject = new ContestStatusData();
    }
	
    /**
     * Tears down testing environment
     *
     * @throws Exception when error occurs
     */
    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * Gets flag denoting whether to use default value for testing.
     *
     * @return flag denoting whether to use default value for testing
     */
    public boolean getUseDefaults() {
        return useDefaults;
    }
    
    /**
     * Sets flag denoting whether to use default value for testing.
     *
     * @param value the flag denoting whether to use default value for testing
     */
    public void setUseDefaults(boolean value) {
        useDefaults = value;
    }
    
    /**
     * Gets flag denoting whether to use value overrides for testing
     *
     * @return flag denoting whether to use default value for testing
     */
    public boolean getUseOverrides() {
        return useOverrides;
    }
    
    /**
     * Sets flag denoting whether to use value overrides for testing
     *
     * @param value flag denoting whether to use value overrides for testing
     */
    public void setUseOverrides(boolean value) {
        useOverrides = value;
    }
    
    /**
     * Gets the object to test.
     *
     * @return the object to test
     */
    public ContestStatusData getTestObject() {
        return testObject;
    }
    
    /**
     * Sets the object to test.
     *
     * @param value the object to test
     */
    public void setTestObject(ContestStatusData value) {
        testObject = value;
    }
    
    // This section contains tests for the <code>setAllowableNextStatus</code> method.
    
    /**
     * Tests for the <code>setAllowableNextStatus</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSetAllowableNextStatus0() {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().setAllowableNextStatus(null);
    
    }
}