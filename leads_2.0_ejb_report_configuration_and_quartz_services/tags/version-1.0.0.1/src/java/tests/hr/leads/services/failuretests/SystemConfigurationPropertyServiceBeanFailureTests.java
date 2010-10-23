/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.failuretests;

import org.apache.log4j.Logger;

import hr.leads.services.ejb.SystemConfigurationPropertyServiceBean;
import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for SystemConfigurationPropertyServiceBean.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class SystemConfigurationPropertyServiceBeanFailureTests extends TestCase {
    /**
     * <p>
     * The SystemConfigurationPropertyServiceBean instance for testing.
     * </p>
     */
    private SystemConfigurationPropertyServiceBean instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new SystemConfigurationPropertyServiceBean();
        TestsHelper.setField(instance, "logger", Logger.getLogger(SystemConfigurationPropertyServiceBean.class));
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(SystemConfigurationPropertyServiceBeanFailureTests.class);
    }

    /**
     * <p>
     * Tests SystemConfigurationPropertyServiceBean#getSystemConfigurationPropertyValue(String) for failure.
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetSystemConfigurationPropertyValue_NullName() throws Exception {
        try {
            instance.getSystemConfigurationPropertyValue(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests SystemConfigurationPropertyServiceBean#getSystemConfigurationPropertyValue(String) for failure.
     * It tests the case that when name is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetSystemConfigurationPropertyValue_EmptyName() throws Exception {
        try {
            instance.getSystemConfigurationPropertyValue(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests SystemConfigurationPropertyServiceBean#setSystemConfigurationPropertyValue(String,String) for failure.
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testSetSystemConfigurationPropertyValue_NullName() throws Exception {
        try {
            instance.setSystemConfigurationPropertyValue(null, "value");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests SystemConfigurationPropertyServiceBean#setSystemConfigurationPropertyValue(String,String) for failure.
     * It tests the case that when name is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testSetSystemConfigurationPropertyValue_EmptyName() throws Exception {
        try {
            instance.setSystemConfigurationPropertyValue(" ", "value");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests SystemConfigurationPropertyServiceBean#setSystemConfigurationPropertyValue(String,String) for failure.
     * It tests the case that when value is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testSetSystemConfigurationPropertyValue_NullValue() throws Exception {
        try {
            instance.setSystemConfigurationPropertyValue("name", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests SystemConfigurationPropertyServiceBean#setSystemConfigurationPropertyValue(String,String) for failure.
     * It tests the case that when value is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testSetSystemConfigurationPropertyValue_EmptyValue() throws Exception {
        try {
            instance.setSystemConfigurationPropertyValue("name", " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests SystemConfigurationPropertyServiceBean#updatePipelineCycleStatus(PipelineCycleStatus) for failure.
     * It tests the case that when status is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdatePipelineCycleStatus_NullStatus() throws Exception {
        try {
            instance.updatePipelineCycleStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}