/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.failuretests;

import java.lang.reflect.Field;

/**
 * Failure test helper.
 * @author extra
 * @version 1.0
 */
public class FailureTestHelper {

    /**
     * Private ctor.
     */
    private FailureTestHelper() {
    }

    /**
     * Sets the value of a private field.
     *
     * @param instance
     *            the instance
     * @param name
     *            the name of the private field
     * @param value
     *            the value to set
     */
    public static void setPrivateField(Object instance, String name, Object value) {
        try {
            Field field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // do nothing
        } catch (IllegalAccessException e) {
            // do nothing
        }
    }
}
