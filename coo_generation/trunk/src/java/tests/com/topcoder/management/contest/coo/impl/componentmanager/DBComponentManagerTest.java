/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.impl.componentmanager;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.contest.coo.Component;
import com.topcoder.management.contest.coo.ComponentManagerException;
import com.topcoder.management.contest.coo.impl.TestHelper;

/**
 *
 * <p>
 * Unit test case of {@link DBComponentManager}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DBComponentManagerTest extends TestCase {
    /**
     * instance created for testing.
     */
    private DBComponentManager instance;

    /**
     * <p>
     * set up test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void setUp() throws Exception {
        TestHelper.executeSqlFile("test_files/clean.sql");
        ConfigurationObject config = TestHelper.getConfiguration("test_files/componentManager.xml");

        instance = new DBComponentManager(config);

    }

    /**
     * <p>
     * tear down test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void tearDown() throws Exception {
//        TestHelper.executeSqlFile("test_files/clean.sql");
        instance = null;
    }

    /**
     * test constructor.
     *
     * @throws Exception to JUNIT.
     */
    public void testCtor() throws Exception {
        assertNotNull("fail to create instance.", instance);
    }

    /**
     * test <code>retrieveComponent(String,String)</code> method.
     *
     * @throws Exception to JUNIT.
     */
    public void ttestRetrieveComponent() throws Exception {
        Component component = instance.retrieveComponent("log4net", "1.2.9");
        assertNull("log4net not exist in db.", component);
        //add component
        TestHelper.executeSqlFile("test_files/insert.sql");
        component = instance.retrieveComponent("log4net", "1.2.9");
        assertNotNull("log4net exist in db now.", component);
        assertEquals("should be GPL", "GPL", component.getLicense());
        assertEquals("should be 'do logging'", "do logging", component.getDescription());
    }

    /**
     * accuracy test for <code>addComponents(String)</code> method.
     *
     * @throws Exception to JUNIT.
     */
    public void ttestAddComponents() throws Exception {

        Component component = instance.retrieveComponent("log4j", "1.2.12");
        assertNull("log4j 1.2.12 should not exist in db.", component);

        // add component from excel file.
       instance.addComponents("test_files/third_party_list.xls");

        component = instance.retrieveComponent("log4j", "1.2.12");
        assertNotNull("log4j 1.2.12 should exist in db now.", component);
        assertEquals("description should be 'logging'", "logging", component.getDescription());
        assertEquals("should be log4j", "log4j", component.getName());
        assertEquals("should be commercial", "commercial", component.getLicense());
        assertEquals("should be 'www.apache.org'", "www.apache.org", component.getUrl());
  
    }

    /**
     * <p>
     * failure test for <code>addComponents(String)</code> method.
     * </p>
     * <p>
     * file not exist. ComponentManagerException throw.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testAddComponents_fail_1() throws Exception {
        try {
            instance.addComponents("test_files/no_exist.xls");
            fail("ComponentManagerException should throw");
        } catch (ComponentManagerException e) {
            // OK
        }
    }

    /**
     * <p>
     * failure test for <code>addComponents(String)</code> method.
     * </p>
     * <p>
     * file is not .xls format. ComponentManagerException throw.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testAddComponents_fail_2() throws Exception {
        try {
            instance.addComponents("test_files/config.xml");
            fail("ComponentManagerException should throw");
        } catch (ComponentManagerException e) {
            // OK
        }
    }
}
