/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg;

import java.io.File;
import java.lang.reflect.Field;

/**
 * <p>
 * This class provides methods for testing this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestHelper {
    /**
     * <p>
     * Represents the path of test files.
     * </p>
     */
    public static final String TEST_FILES = "test_files" + File.separator;

    /**
     * <p>
     * Private constructor to prevent this class being instantiated.
     * </p>
     */
    private TestHelper() {
        // empty
    }

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     *
     * @param clazz
     *            the class type of the object
     * @param obj
     *            the given object.
     * @param fieldName
     *            the field name.
     * @return the field value.
     * @throws Exception
     *             if any error occurs
     */
    public static Object getField(Class<?> clazz, Object obj, String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        Object value = field.get(obj);

        return value;
    }

    /**
     * <p>
     * Sets value for field of given object.
     * </p>
     *
     * @param clazz
     *            the class type of the object
     * @param obj
     *            the given object.
     * @param fieldName
     *            the field name.
     * @param value
     *            the field value.
     * @throws Exception
     *             if any error occurs
     */
    public static void setField(Class<?> clazz, Object obj, String fieldName, Object value) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        field.set(obj, value);
    }

}
