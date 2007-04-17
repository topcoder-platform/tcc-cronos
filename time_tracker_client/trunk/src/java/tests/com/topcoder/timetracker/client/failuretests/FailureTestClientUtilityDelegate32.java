/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.failuretests;
import java.io.File;

import junit.framework.TestCase;

import com.topcoder.timetracker.client.ConfigurationException;
import com.topcoder.timetracker.client.ejb.ClientUtilityDelegate;
import com.topcoder.util.config.ConfigManager;
/**
 * This class contains unit tests for <code>ClientUtilityDelegate</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class FailureTestClientUtilityDelegate32 extends TestCase {
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
        cm.add(new File("test_files/failure/ClientUtilityDelegate.xml").getAbsolutePath());
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
     * Tests <code>ClientUtilityDelegate(String namespace)</code> method
     * for failure with null Namespace.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testClientUtilityDelegate_NullNamespace() throws Exception {
        try {
        	new ClientUtilityDelegate(null);
            fail("testClientUtilityDelegate_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>ClientUtilityDelegate(String namespace)</code> method
     * for failure with empty Namespace,
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testClientUtilityDelegate_EmptyNamespace() throws Exception {
        try {
        	new ClientUtilityDelegate(" ");
            fail("testClientUtilityDelegate_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>ClientUtilityDelegate(String namespace)</code> method
     * for failure with invalid configuration,
     * ConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testClientUtilityDelegate_Invalid1() throws Exception {
        try {
        	new ClientUtilityDelegate("ClientUtilityDelegate.fail1");
            fail("testClientUtilityDelegate_Invalid1 is failure.");
        } catch (ConfigurationException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>ClientUtilityDelegate(String namespace)</code> method
     * for failure with invalid configuration,
     * ConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testClientUtilityDelegate_Invalid2() throws Exception {
        try {
        	new ClientUtilityDelegate("ClientUtilityDelegate.fail2");
            fail("testClientUtilityDelegate_Invalid2 is failure.");
        } catch (ConfigurationException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>ClientUtilityDelegate(String namespace)</code> method
     * for failure with invalid configuration,
     * ConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testClientUtilityDelegate_Invalid3() throws Exception {
        try {
        	new ClientUtilityDelegate("ClientUtilityDelegate.fail3");
            fail("testClientUtilityDelegate_Invalid3 is failure.");
        } catch (ConfigurationException iae) {
            // pass
        }
    } 
}