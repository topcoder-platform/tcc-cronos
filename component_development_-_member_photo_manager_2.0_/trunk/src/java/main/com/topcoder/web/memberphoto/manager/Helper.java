/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.web.memberphoto.manager;

/**
 * <p>
 * Helper class used by this component. This contains the common methods to handle code redundancy.
 * </p>
 *
 * <p>
 * Thread Safety: This class does not maintain any state and is thread safe.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public final class Helper {

    /**
     * No instances allowed.
     */
    private Helper() {
        // empty
    }

    /**
     * Checks the object is <code>null</code> or not.
     *
     * @param <T>
     *            the type param of object
     * @param obj
     *            the object to check
     * @param paramName
     *            the parameter name
     * @return the object
     * @throws IllegalStateException
     *             if the object is <code>null</code>
     */
    public static <T> T checkState(T obj, String paramName) {
        if (obj == null) {
            throw new IllegalStateException("The parameter '" + paramName + "' cannot be null.");
        }
        return obj;
    }
}
