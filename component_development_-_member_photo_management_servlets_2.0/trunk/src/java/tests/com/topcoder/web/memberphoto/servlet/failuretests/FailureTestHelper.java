/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.failuretests;

import java.lang.reflect.Field;

/**
 * Helper class for failure test.
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class FailureTestHelper {
    /**
     * Empty constructor..
     */
    private FailureTestHelper() {

    }

    /**
     * Sets the field value.
     * @param obj
     *            the given object.
     * @param fieldName
     *            the field name.
     * @param fieldvalue
     *            the field value.
     * @throws Exception
     *             to jUnit.
     */
    static void setField(Object obj, String fieldName, Object fieldvalue) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);

        field.setAccessible(true);
        field.set(obj, fieldvalue);
        field.setAccessible(false);
    }
}