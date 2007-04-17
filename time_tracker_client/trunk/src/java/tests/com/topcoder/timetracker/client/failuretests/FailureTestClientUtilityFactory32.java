/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.failuretests;
import java.io.File;

import junit.framework.TestCase;

import com.topcoder.timetracker.client.ConfigurationException;
import com.topcoder.timetracker.client.ejb.ClientUtilityFactory;
import com.topcoder.util.config.ConfigManager;
/**
 * This class contains unit tests for <code>ClientUtilityFactory</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class FailureTestClientUtilityFactory32 extends TestCase {
	
	/**
	 * The ConfigManager instance used to test.
	 */
	private ConfigManager cm = ConfigManager.getInstance();
    /**
     * Set Up the test environment before testing.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.removeNamespaces();
    }

    /**
     * Clean up the test environment after testing.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.removeNamespaces();
    }
    /**
     * Tests <code>getClientUtility()</code> method
     * for failure with invalid configuration.
     * ConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetClientUtilityInvalid1() throws Exception {
        try {
        	ClientUtilityFactory.getClientUtility();
            fail("testGetClientUtilityInvalid1 is failure.");
        } catch (ConfigurationException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>getClientUtility()</code> method
     * for failure with invalid configuration.
     * ConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetClientUtilityInvalid2() throws Exception {
    	cm.add(new File("test_files/failure/FactoryInvalid1.xml").getAbsolutePath());
        try {
        	ClientUtilityFactory.getClientUtility();
            fail("testGetClientUtilityInvalid1 is failure.");
        } catch (ConfigurationException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>getClientUtility()</code> method
     * for failure with invalid configuration.
     * ConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetClientUtilityInvalid3() throws Exception {
    	cm.add(new File("test_files/failure/FactoryInvalid2.xml").getAbsolutePath());
        try {
        	ClientUtilityFactory.getClientUtility();
            fail("testGetClientUtilityInvalid1 is failure.");
        } catch (ConfigurationException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>getClientUtility()</code> method
     * for failure with invalid configuration.
     * ConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testGetClientUtilityInvalid4() throws Exception {
    	cm.add(new File("test_files/failure/FactoryInvalid3.xml").getAbsolutePath());
        try {
        	ClientUtilityFactory.getClientUtility();
            fail("testGetClientUtilityInvalid1 is failure.");
        } catch (ConfigurationException iae) {
            // pass
        }
    }
}