/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import java.lang.reflect.Field;

/**
 * <p>
 * Helper class used for testing purpose.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
class UnitTestHelper {
    /**
     * <p>
     * private constructor, preventing instantiation.
     * </p>
     */
    private UnitTestHelper() {
    }

    /**
     * <p>
     * Sets the field value of an object.
     * </p>
     *
     * @param obj
     *            the object where to get the field value.
     * @param fieldName
     *            the name of the field.
     * @param value
     *            the value of the object.
     * @throws Exception
     *             any exception occurs.
     */
    static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }
}
