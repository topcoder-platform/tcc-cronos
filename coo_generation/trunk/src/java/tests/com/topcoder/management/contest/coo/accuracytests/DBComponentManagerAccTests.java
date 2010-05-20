/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.accuracytests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.contest.coo.Component;
import com.topcoder.management.contest.coo.impl.componentmanager.DBComponentManager;


/**
 * <p>Accuracy test case for {@link DBComponentManager} class.</p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class DBComponentManagerAccTests extends BaseTestCase {
    /** Target. */
    private DBComponentManager instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        ConfigurationObject config = BaseTestCase
            .getConfigurationObject(BaseTestCase.ACCURACY + "Config.xml");

        instance = new DBComponentManager(config);
    }

    /**
     * Clears all the test environments.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Accuracy test case for constructor.
     *
     * @throws Exception to JUnit.
     */
    public void testCtor() throws Exception {
        assertNotNull(instance);
    }

    /**
     * Accuracy test case for <code>retrieveComponent(String,String)</code> method.
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveComponent() throws Exception {
        Component component = instance.retrieveComponent("Antlr", "2.7.6");
        assertEquals("Antlr 3 License (BSD Style)", component.getLicense());
        assertEquals("Parser Generator", component.getDescription());
        assertEquals("www.antlr.org", component.getUrl());
        assertEquals("Antlr", component.getName());
        assertEquals("2.7.6", component.getVersion());
    }

    /**
     * Accuracy test case for <code>addComponents(String)</code> method.
     *
     * @throws Exception to JUnit.
     */
    public void ttestAddComponents() throws Exception {
        instance.addComponents(BaseTestCase.ACCURACY + "addComponents.xls");

        Component component = instance.retrieveComponent("Junit", "3.8.2");
        assertEquals("CPL", component.getLicense());
        assertEquals("Unit Testing Framework", component.getDescription());
        assertEquals("www.junit.org", component.getUrl());
        assertEquals("Junit", component.getName());
        assertEquals("3.8.2", component.getVersion());
    }
}
