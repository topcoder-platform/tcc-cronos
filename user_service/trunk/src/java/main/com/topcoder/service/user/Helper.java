/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user;

/**
 * <p>
 * This class provides static utility methods which are used to facilitate the coding or reduce the redundancies.
 * </p>
 *
 * <p>
 * Thread Safety: This class is thread safe since it is immutable.
 * </p>
 *
 * @author snow01
 * @since Cockpit Release Assembly for Receipts
 * @version 1.0
 */
public final class Helper {
    /**
     * <p>
     * Private constructor to prevent instantiation of this class.
     * </p>
     */
    private Helper() {
    }

    /**
     * <p>
     * Checks whether the param is null.
     * </p>
     *
     * @param param
     *            the parameter to check
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if the param is null
     */
    public static void checkNull(final Object param, final String paramName) {
        if (param == null) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should not be null.");
        }
    }

    /**
     * <p>
     * Checks whether the param is empty string.
     * </p>
     *
     * @param param
     *            the parameter to check
     * @param paramName
     *            the name of the parameter
     * @throws IllegalArgumentException
     *             if the param is empty string.
     */
    public static void checkEmpty(final String param, final String paramName) {
        if ((param != null) && (param.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should not be empty.");
        }
    }
}
