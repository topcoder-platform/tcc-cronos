/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.accuracytests;

import java.lang.reflect.Field;

/**
 * Helper class for accuracy test class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestHelper {
    /**
     * Gets the field value using reflection.
     *
     * @param clazz
     *            the type of class.
     * @param instance
     *            the instance.
     * @param field
     *            the field name.
     * @return the field value.
     *
     * @throws Exception
     *             to jUnit.
     */
    public static Object getFieldObj(Class<?> clazz, Object object, String name) throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        return field.get(object);
    }
}
