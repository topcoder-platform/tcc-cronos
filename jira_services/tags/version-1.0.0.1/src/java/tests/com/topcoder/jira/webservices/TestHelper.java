/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import java.lang.reflect.Field;

/**
 * Simple helper for tests, currently has only one method.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestHelper {
    /**
     * Private constructor to prevent instantiation.
     */
    private TestHelper() {
    }

    /**
     * Injects provided object into property of another object.
     *
     * @param target   object to inject in
     * @param property name of the property to inject in
     * @param value    value to inject
     * @throws Exception when it occurs deeper
     */
    public static void inject(Object target, String property, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(property);
        field.setAccessible(true);
        field.set(target, value);
    }
}
