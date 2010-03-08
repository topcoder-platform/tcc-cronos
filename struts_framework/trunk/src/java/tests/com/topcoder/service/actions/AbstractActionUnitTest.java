/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.service.TestHelper;

/**
 * <p>
 * Unit tests for <code>AbstractAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AbstractActionUnitTest extends TestCase {

    /**
     * The instance used in testing.
     */
    private EmployeeAction instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        instance = new EmployeeAction();
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to junit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Tests the class inheritance to make sure it is correct.
     */
    @Test
    public void testInheritance() {
        TestHelper.assertSuperclass(instance.getClass(), AbstractAction.class);
        TestHelper.assertSuperclass(instance.getClass().getSuperclass(), ActionSupport.class);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    @Test
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for getModel. Verifies the returned value is correct.
     */
    @Test
    public void test_getModel_Accuracy() {
        assertNull("incorrect default value", instance.getModel());
        AggregateDataModel model = new AggregateDataModel();
        model.setData("key", "value");
        model.setAction("action1");
        instance.setModel(model);
        assertSame("incorrect value after setting", model, instance.getModel());
        assertEquals("incorrect value for action", model.getAction(), instance.getModel().getAction());
        assertEquals("incorrect value for data in model", model.getData("key"), instance.getModel().getData("key"));
    }

    /**
     * Accuracy test for setModel. Verifies the assigned value is correct.
     */
    @Test
    public void test_setModel_Accuracy() {
        AggregateDataModel model = new AggregateDataModel();
        model.setData("key", "value");
        model.setAction("action1");
        instance.setModel(model);
        assertSame("incorrect value after setting", model, instance.getModel());
        assertEquals("incorrect value for action", model.getAction(), instance.getModel().getAction());
        assertEquals("incorrect value for data in model", model.getData("key"), instance.getModel().getData("key"));
    }

    /**
     * Accuracy test for getAction. Verifies the returned value is correct.
     */
    @Test
    public void test_getAction_Accuracy() {
        assertNull("incorrect default value", instance.getAction());
        String action = "topcoder action";
        instance.setAction(action);
        assertEquals("incorrect value after setting", action, instance.getAction());
    }

    /**
     * Accuracy test for setAction. Verifies the assigned value is correct.
     */
    @Test
    public void test_setAction_Accuracy() {
        String action = "struts action";
        instance.setAction(action);
        assertEquals("incorrect value after setting", action, instance.getAction());
    }
}
