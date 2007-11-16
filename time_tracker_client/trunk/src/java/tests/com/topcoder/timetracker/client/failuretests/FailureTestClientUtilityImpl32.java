/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.failuretests;
import java.io.File;

import junit.framework.TestCase;

import com.topcoder.timetracker.client.ClientUtilityImpl;
import com.topcoder.timetracker.client.ConfigurationException;
import com.topcoder.util.config.ConfigManager;
/**
 * This class contains unit tests for <code>ClientUtilityImpl</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class FailureTestClientUtilityImpl32 extends TestCase {
	
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
        cm.add(new File("test_files/failure/ClientUtilityImpl.xml").getAbsolutePath());
        cm.add(new File("test_files/failure/conf/db_config.xml").getAbsolutePath());
        FailureTestHelper.setUpDatabase();
    }

    /**
     * Clean up the test environment after testing.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.tearDownDataBase();
        FailureTestHelper.removeNamespaces();
    }
    /**
     * Tests <code>ClientUtilityImpl(String namespace)</code> method
     * for failure with null Namespace.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testClientUtilityImpl_NullNamespace() throws Exception {
        try {
        	new ClientUtilityImpl(null);
            fail("testClientUtilityImpl_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>ClientUtilityImpl(String namespace)</code> method
     * for failure with empty Namespace,
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testClientUtilityImpl_EmptyNamespace() throws Exception {
        try {
        	new ClientUtilityImpl(" ");
            fail("testClientUtilityImpl_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>ClientUtilityImpl(String namespace)</code> method
     * for failure with invalid configuration,
     * ConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testClientUtilityImplInvalid1() throws Exception {
        try {
        	new ClientUtilityImpl("ClientUtilityImpl.fail1");
            fail("testClientUtilityImplInvalid1 is failure.");
        } catch (ConfigurationException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>ClientUtilityImpl(String namespace)</code> method
     * for failure with invalid configuration,
     * ConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testClientUtilityImplInvalid2() throws Exception {
        try {
        	new ClientUtilityImpl("ClientUtilityImpl.fail2");
            fail("testClientUtilityImplInvalid2 is failure.");
        } catch (ConfigurationException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>ClientUtilityImpl(String namespace)</code> method
     * for failure with invalid configuration,
     * ConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testClientUtilityImplInvalid3() throws Exception {
        try {
        	new ClientUtilityImpl("ClientUtilityImpl.fail3");
            fail("testClientUtilityImplInvalid3 is failure.");
        } catch (ConfigurationException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>ClientUtilityImpl(String namespace)</code> method
     * for failure with invalid configuration,
     * ConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testClientUtilityImplInvalid4() throws Exception {
        try {
        	new ClientUtilityImpl("ClientUtilityImpl.fail4");
            fail("testClientUtilityImplInvalid4 is failure.");
        } catch (ConfigurationException iae) {
            // pass
        }
    }
    
    /**
     * Tests <code>ClientUtilityImpl(String namespace)</code> method
     * for failure with invalid configuration,
     * ConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testClientUtilityImplInvalid5() throws Exception {
        try {
        	new ClientUtilityImpl("ClientUtilityImpl.fail5");
            fail("testClientUtilityImplInvalid5 is failure.");
        } catch (ConfigurationException iae) {
            // pass
        }
    }
}