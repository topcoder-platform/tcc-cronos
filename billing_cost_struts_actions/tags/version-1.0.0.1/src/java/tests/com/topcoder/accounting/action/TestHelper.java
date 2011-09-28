/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import java.lang.reflect.Field;

/**
 * <p>
 * This is the test helper class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class TestHelper {

    /** private constructor. */
    private TestHelper() {

    }

    /**
     * <p>
     * Gets a Field object of the instance.
     * </p>
     *
     * @param instance the instance.
     * @param field the field name.
     * @return the Field object.
     */
    private static Field getFieldObj(Object instance, String field) {
        Field fieldObj = null;
        try {
            try {
                fieldObj = instance.getClass().getDeclaredField(field);
            } catch (NoSuchFieldException e) {
                // Ignore
            }
            if (fieldObj == null) {
                try {
                    // From the superclass
                    fieldObj = instance.getClass().getSuperclass()
                            .getDeclaredField(field);
                } catch (NoSuchFieldException e) {
                    // ignore
                }
                if (fieldObj == null) {
                    // From the superclass
                    fieldObj = instance.getClass().getSuperclass()
                            .getSuperclass().getDeclaredField(field);

                }
            }
        } catch (IllegalArgumentException e) {
            // Ignore
        } catch (SecurityException e) {
            // Ignore
        } catch (NoSuchFieldException e) {
            // Ignore
        }
        return fieldObj;
    }

    /**
     * <p>
     * Sets value for field of given object.
     * </p>
     *
     * @param instance the instance.
     * @param field the field name.
     * @param value the field value.
     */
    protected static void setField(Object instance, String field, Object value) {
        try {
            Field fieldObj = getFieldObj(instance, field);
            fieldObj.setAccessible(true);
            fieldObj.set(instance, value);
            fieldObj.setAccessible(false);
        } catch (IllegalAccessException e) {
            // Ignore
        }
    }
}
