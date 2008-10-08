/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.accuracytests;

import java.lang.reflect.Field;


/**
 * <p>
 * Helper class for accuracy tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestHelper {
    /**
     * <p>
     * Private ctor.
     * </p>
     */
    private AccuracyTestHelper() {
    }

    /**
     * <p>
     * Sets the field value of the given instance. The field name is also given.
     * </p>
     *
     * @param instance the instance whose field value is retrieved.
     * @param field the field name.
     * @param value the field value to be set.
     * @throws Exception if error occurs when retrieving the value.
     */
    public static void setField(Object instance, String field, Object value)
        throws Exception {
        Field info = instance.getClass().getDeclaredField(field);
        boolean accessFlag = info.isAccessible();

        try {
            info.setAccessible(true);
            info.set(instance, value);
        } finally {
            info.setAccessible(accessFlag);
        }
    }

    /**
     * <p>
     * Used to get the declared field of the specified object.
     * </p>
     *
     * @param instance the instance from where to get the field value.
     * @param fieldName the name of the field.
     * @throws Exception If any exception is thrown from the inner code.
     * @return the value of the field.
     */
    public static Object getField(Object instance, String fieldName)
        throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        try {
            field.setAccessible(true);

            Object res = field.get(instance);

            return res;
        } finally {
            field.setAccessible(false);
        }
    }
}
