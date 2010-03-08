/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.util.Map;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.service.TestHelper;

/**
 * <p>
 * Unit tests for <code>AggregateDataModel</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AggregateDataModelUnitTest extends TestCase {

    /**
     * The instance used in testing.
     */
    private AggregateDataModel instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        instance = new AggregateDataModel();
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
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    @Test
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for getData. Verifies the returned value is correct.
     */
    @Test
    public void test_getData_Accuracy() {
        assertNull("incorrect default value", instance.getData("key"));
        Employee emp = new Employee();
        emp.setFirstName("John");
        emp.setLastName("Doe");
        instance.setData("key", emp);
        assertSame("incorrect value after setting", emp, instance.getData("key"));
        assertEquals("incorrect value for first name", emp.getFirstName(), ((Employee) instance.getData("key"))
            .getFirstName());
        assertEquals("incorrect value for last name", emp.getLastName(), ((Employee) instance.getData("key"))
            .getLastName());

        // replace the value, make sure it is updated
        emp = new Employee();
        emp.setFirstName("Jane");
        emp.setLastName("Doe");
        instance.setData("key", emp);
        assertSame("incorrect value after replacing original value", emp, instance.getData("key"));
        assertEquals("incorrect value for first name", emp.getFirstName(), ((Employee) instance.getData("key"))
            .getFirstName());
        assertEquals("incorrect value for last name", emp.getLastName(), ((Employee) instance.getData("key"))
            .getLastName());
    }

    /**
     * Failure test for getData method. The key is null and IllegalArgumentException is expected.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_getData_Failure1() throws Exception {
        try {
            instance.getData(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for getData method. The key is empty and IllegalArgumentException is expected.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_getData_Failure2() throws Exception {
        try {
            instance.getData("  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for setData. Verifies the assigned value is correct.
     */
    @Test
    public void test_setData_Accuracy1() {
        Employee emp = new Employee();
        emp.setFirstName("William");
        emp.setLastName("Zhang");
        instance.setData("key", emp);
        assertSame("incorrect value after setting", emp, instance.getData("key"));
        assertEquals("incorrect value for first name", emp.getFirstName(), ((Employee) instance.getData("key"))
            .getFirstName());
        assertEquals("incorrect value for last name", emp.getLastName(), ((Employee) instance.getData("key"))
            .getLastName());

        // replace the value, make sure it is updated
        emp = new Employee();
        emp.setFirstName("Mary");
        emp.setLastName("Russovich");
        instance.setData("key", emp);
        assertSame("incorrect value after replacing original value", emp, instance.getData("key"));
        assertEquals("incorrect value for first name", emp.getFirstName(), ((Employee) instance.getData("key"))
            .getFirstName());
        assertEquals("incorrect value for last name", emp.getLastName(), ((Employee) instance.getData("key"))
            .getLastName());
    }

    /**
     * Accuracy test for setData. Verifies that null can be assigned to a key.
     */
    @Test
    public void test_setData_Accuracy2() {
        Employee emp = new Employee();
        emp.setFirstName("Barry");
        emp.setLastName("Davis");
        instance.setData("key", emp);
        assertSame("incorrect value after setting", emp, instance.getData("key"));

        // replace the value, make sure it is updated
        instance.setData("key", null);
        assertNull("incorrect value after replacing original value", instance.getData("key"));
    }

    /**
     * Failure test for setData method. The key is null and IllegalArgumentException is expected.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_setData_Failure1() throws Exception {
        try {
            instance.setData(null, null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for setData method. The key is empty and IllegalArgumentException is expected.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_setData_Failure2() throws Exception {
        try {
            instance.setData("  ", null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for getDataAsMap. Verifies the returned value is correct.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_getDataAsMap_Accuracy() throws Exception {
        assertNull("incorrect default value", instance.getData("key"));
        Employee emp1 = new Employee();
        emp1.setFirstName("John");
        emp1.setLastName("Doe");
        instance.setData("key1", emp1);

        Employee emp2 = new Employee();
        emp2.setFirstName("Jane");
        emp2.setLastName("Doe");
        instance.setData("key2", emp2);

        // validate fetched map
        Map<String, Object> map = instance.getDataAsMap();
        assertEquals("wrong number of elements in map", 2, map.size());
        assertSame("incorrect value in map", emp1, map.get("key1"));
        assertSame("incorrect value in map", emp2, map.get("key2"));

        // make sure the fetched map is a shallow copy
        Object instanceMap = TestHelper.getInstanceField(instance, "data");
        assertNotSame("the returned map should be a shallow copy", map, instanceMap);
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
    public void test_setAction_Accuracy1() {
        String action = "struts action";
        instance.setAction(action);
        assertEquals("incorrect value after setting", action, instance.getAction());
    }

    /**
     * Accuracy test for setAction. Verifies that null can be assigned.
     */
    @Test
    public void test_setAction_Accuracy2() {
        instance.setAction(null);
        assertNull("incorrect value after setting", instance.getAction());
    }

}
