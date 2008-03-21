/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document;

import java.util.Collection;

/**
 * <p>
 * Helper class that providing common parameter validation.
 * </p>
 * <p>
 * <b>Thread Safety</b>: The class is stateless, so it is thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public final class Helper {

    /**
     * <p>
     * Private constructor preventing instantiation.
     * </p>
     */
    private Helper() {
    }

    /**
     * <p>
     * Checks whether the specified object is null or not.
     * </p>
     *
     * @param obj
     *            the specified object to check.
     * @param name
     *            the object name.
     * @throws IllegalArgumentException
     *             If the object is null.
     */
    public static void checkNull(Object obj, String name) {
        if (null == obj) {
            throw new IllegalArgumentException("Parameter [" + name + "] can not be null.");
        }
    }

    /**
     * <p>
     * Checks whether the specified string is null/empty.
     * </p>
     *
     * @param str
     *            the string parameter to check
     * @param name
     *            the name of the string parameter.
     * @throws IllegalArgumentException
     *             If the string is null/empty.
     */
    public static void checkNullOrEmpty(String str, String name) {
        checkNull(str, name);
        if (0 == str.trim().length()) {
            throw new IllegalArgumentException("Parameter [" + name + "] can not be empty.");
        }
    }

    /**
     * <p>
     * Checks whether the specified collection is null or contains any null element.
     * </p>
     *
     * @param collection
     *            the collection to check
     * @param name
     *            the name of the collection parameter.
     * @throws IllegalArgumentException
     *             If the collection is null or contains any null element.
     */
    public static void checkCollection(Collection<? extends Object> collection, String name) {
        checkNull(collection, name);

        for (Object obj : collection) {
            checkNull(obj, "element of " + name);
        }
    }
}
