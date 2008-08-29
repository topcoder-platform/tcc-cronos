/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.accuracytests;

import java.lang.reflect.Field;

/**
 * Helper class for Accuracy test.
 *
 * @author onsky
 * @version 1.0
 */
final class TestHelper {
    /**
     * Find field value by the given object.
     * @param obj the given object
     * @param name the name of this field
     * @return the field value
     * @throws Exception if any error
     */
    static Object getPropertyByReflection(Object obj, String name) throws Exception {
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(obj);
    }
}
