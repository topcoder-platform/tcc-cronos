/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot;

import java.lang.reflect.Field;

/**
 * <p>
 * TestHelper class for the test.
 * </p>
 * <p>
 * Thread safe: This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {
    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private TestHelper() {
        // empty
    }

    /**
     * Sets the value of a field in the given class.
     *
     * @param instance the instance which the field belongs to
     * @param name the name of the field to be set
     * @param value the value to set
     * @param classType the class to get field
     * @throws Exception to JUnit
     */
    public static void setField(Class<?> classType, Object instance, String name, Object value) throws Exception {
        // get the reflection of the field
        Field field = classType.getDeclaredField(name);
        // set the field accessible
        field.setAccessible(true);
        // set the value
        field.set(instance, value);
    }

    /**
     * Gets the value of a field in the given instance by given name.
     *
     * @param instance the instance which the field belongs to.
     * @param name the name of the field to be retrieved.
     * @param classType the class to get field
     * @return the value of the field.
     * @throws Exception to JUnit
     */
    public static Object getField(Class<?> classType, Object instance, String name) throws Exception {
        Field field = classType.getDeclaredField(name);
        // Set the field accessible.
        field.setAccessible(true);

        // Get the value
        return field.get(instance);
    }
}