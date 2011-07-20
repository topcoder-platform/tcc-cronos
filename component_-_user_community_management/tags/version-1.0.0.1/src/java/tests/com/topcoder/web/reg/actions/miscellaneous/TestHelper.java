/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.lang.reflect.Field;

/**
 * <p>
 * TestHelper class for the tests.
 * </p>
 * <p>
 * Thread safe: This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class TestHelper {
    /**
     * <p>
     * An empty string.
     * </p>
     */
    public static final String EMPTY = " \n\t";

    /**
     * <p>
     * An empty constructor.
     * </p>
     */

    private TestHelper() {

    }

    /**
     * <p>Outjects the value of the given instance.</p>
     *
     * @param clazz clazz of where the field is declared
     * @param instance the instance whose value to outject
     * @param fieldName the field whose value to outject
     * @return the value
     * @throws Exception if anything wrong
     */
    public static Object outject(Class<?> clazz, Object instance, String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        try {
            field.setAccessible(true);
            return field.get(instance);
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }

}