/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.actions.LoginAction;

/**
 * Test Helper for unit tests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestHelper extends TestCase {

    /**
     * Represents the key to use when verifying user is logged in.
     */
    public static final String KEY_FOR_LOGIN_CHECK = "USER_ID_KEY";

    /**
     * The base URL value to use when accessing the web application.
     */
    private static final String BASE_URL;

    /**
     * Private constructor to prevent class instantiation.
     */
    private TestHelper() {
    }

    /**
     * The getter for the base URL value to use when accessing the web application.
     *
     * @return the base URL value to use when accessing the web application
     */
    public static String getBaseURL() {
        return BASE_URL;
    }

    /**
     * Initialize static resources used by this class.
     */
    static {
        try {
            Properties properties = new Properties();
            properties.load(TestHelper.class.getResourceAsStream("/test.properties"));

            // get the base URL to use when accessing the web application
            BASE_URL = properties.getProperty("BASE_URL");

        } catch (IOException e) {
            throw new RuntimeException("Error during initialization", e);
        }
    }

    /**
     * Sets an instance field to a value using reflection.
     *
     * @param instance the instance on which to set the field value
     * @param fieldName the name of the field
     * @param fieldValue the value of the field
     * @throws Exception if any problem occurs setting the field value
     */
    public static void setInstanceField(Object instance, String fieldName, Object fieldValue) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, fieldValue);
    }

    /**
     * Gets a value from an instance field using reflection.
     *
     * @param instance the instance from which to fetch the value
     * @param fieldName the name of the field
     * @return the value of the field
     * @throws Exception if any error occurs getting the field value
     */
    public static Object getInstanceField(Object instance, String fieldName) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }

    /**
     * Asserts that inheritance is correct for a class by checking that the class extends the given
     * superclass.
     *
     * @param clazz the class to check
     * @param expectedSuperclass the expected superclass that the class should extend
     */
    public static void assertSuperclass(Class<?> clazz, Class<?> expectedSuperclass) {
        assertTrue("The inheritance is wrong, the class doesn't extend " + expectedSuperclass.getName(), clazz
            .getSuperclass().equals(expectedSuperclass));
    }

    /**
     * Prepares the action proxy object for tests and makes sure it's the correct type.
     *
     * @param proxy the action proxy object to prepare
     */
    public static void prepareActionProxy(ActionProxy proxy) {
        assertTrue("action is of wrong type", proxy.getAction() instanceof LoginAction);

        // inject a session map into proxy
        Map<String, Object> session = new HashMap<String, Object>();
        proxy.getInvocation().getInvocationContext().setSession(session);
    }
}
