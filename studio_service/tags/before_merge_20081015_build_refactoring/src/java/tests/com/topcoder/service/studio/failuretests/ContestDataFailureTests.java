/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.studio.failuretests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.service.studio.ContestData;

/**
 * Generated failure test for the <code>ContestData.java</code> class.
 *
 * @author kakarotto
 * @version 1.0
 */
public class ContestDataFailureTests {
    /** The flag to denote whether to test with value overrides. */
    private boolean useOverrides = true;

    /** The flag to denote whether to test with default value. */
    private boolean useDefaults = true;

    /** The object to test. */
    private ContestData testObject;

    /** ObjectFactory instance for testing. */
    private ObjectFactory objectFactory;
    
    /**
     * Sets up testing environment
     *
     * @throws Exception when error occurs
     */
    @Before
    public void setUp() throws Exception {
        //objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory("defaultObjectFactory"));
        testObject = new ContestData();
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
    public ContestData getTestObject() {
        return testObject;
    }
    
    /**
     * Sets the object to test.
     *
     * @param value the object to test
     */
    public void setTestObject(ContestData value) {
        testObject = value;
    }
    
    /**
     * Gets the ObjectFactory for testing.
     *
     * @return the ObjectFactory for testing
     */
    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }
    
    /**
     * Sets the ObjectFactory for testing.
     *
     * @param value ObjectFactory for testing
     */
    public void setObjectFactory(ObjectFactory value) {
        objectFactory = value;
    }
    
    // This section contains tests for the <code>setPrizes</code> method.
    
    /**
     * Tests for the <code>setPrizes</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSetPrizes0() {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().setPrizes(null);
    
    }
    
    // This section contains tests for the <code>setDocumentationUploads</code> method.
    
    /**
     * Tests for the <code>setDocumentationUploads</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSetDocumentationUploads0() {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().setDocumentationUploads(null);
    
    }
    
    // This section contains tests for the <code>setContestPayloads</code> method.
    
    /**
     * Tests for the <code>setContestPayloads</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSetContestPayloads0() {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().setContestPayloads(null);
    
    }
    
}