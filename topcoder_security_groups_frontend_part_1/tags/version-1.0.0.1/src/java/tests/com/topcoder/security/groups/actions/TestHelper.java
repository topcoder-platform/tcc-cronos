/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.lang.reflect.Field;

/**
 * <p>
 * This class provides methods for testing this component.
 * </p>
 *
 * @author hanshuai
 * @version 1.0
 */
final class TestHelper {

    /**
     * <p>
     * Private constructor to prevent this class being instantiated.
     * </p>
     */
    private TestHelper() {
        // Empty
    }

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     *
     * @param obj
     *            the given object.
     * @param field
     *            the field name.
     * @return the field value.
     */
    public static Object getField(Object obj, String field) {
        Object value = null;
        try {
            Field declaredField = null;
            Class<?> clazz = obj.getClass();
            while (true) {
                try {
                    declaredField = clazz.getDeclaredField(field);
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
                if (declaredField != null) {
                    break;
                }
            }
            declaredField.setAccessible(true);
            value = declaredField.get(obj);
            declaredField.setAccessible(false);
        } catch (IllegalAccessException e) {
            // Ignore
        }
        return value;
    }
}
