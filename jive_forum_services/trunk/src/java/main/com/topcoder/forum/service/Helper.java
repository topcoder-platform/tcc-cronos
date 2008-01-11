/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;


/**
 * <p>
 * This class is used by this component only. Provides some common utility methods.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong>
 * The class is thread-safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class Helper {
    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private Helper() {
        // do nothing
    }

    /**
     * <p>
     * Checks whether the given object is null. If null, <code>IllegalArgumentException</code> will be
     * thrown.
     * </p>
     *
     * @param param the argument to be checked
     * @param paramName the name of the argument to be checked
     *
     * @throws IllegalArgumentException if param is null
     */
    static void checkNull(final Object param, final String paramName) {
        if (param == null) {
            throw new IllegalArgumentException("The argument '" + paramName
                    + "' should not be null.");
        }
    }

    /**
     * <p>
     * Checks whether the string is null or empty. If it is, <code>IllegalArgumentException</code>
     * will be thrown.
     * </p>
     *
     * @param param the string to be checked
     * @param paramName the name of the string
     * @throws IllegalArgumentException if the string is null or empty
     */
    static void checkString(final String param, final String paramName) {
        Helper.checkNull(param, paramName);

        if (param.trim().length() == 0) {
            throw new IllegalArgumentException("The argument '" + paramName
                    + "' should not be empty string.");
        }
    }

    /**
     * <p>
     * Checks whether the id is above 0, if it is not above 0, <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     *
     * @param id the long value to be checked
     * @param paramName the name of the parameter
     * @throws IllegalArgumentException if the id is not above 0
     */
    static void checkId(final long id, final String paramName) {
        if (id <= 0) {
            throw new IllegalArgumentException("The '" + paramName
                    + "' should be above 0.");
        }
    }

    /**
     * <p>
     * Checks the id's value. If it is not above 0, neither -1,
     * <code>IllegalArgumentException</code> is thrown.
     * </p>
     *
     * @param id the long value to be checked
     * @param paramName the name of the parameter
     * @throws IllegalArgumentException if the id is not above 0, neither -1
     */
    static void checkOptionId(final long id, final String paramName) {
        if ((id <= 0) && (id != -1)) {
            throw new IllegalArgumentException("The '" + paramName
                    + "' should be above 0, or it should be -1.");
        }
    }
}
