/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.bean;


/**
 * <p>
 * This class is used by this component only. Provide some common utility methods.
 * </p>
 *
 * <p>Thread safety: the class is thread-safe.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Helper {
    /**
     * <p>The minimum valid port number.</p>
     */
    private static final int MIN_PORT = 0;

    /**
     * <p>The maximum valid port number.</p>
     */
    private static final int MAX_PORT = 65535;

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private Helper() {
        // do nothing
    }

    /**
     * <p>Checks whether the port number is valid.</p>
     *
     * @param port the port number
     *
     * @throws IllegalArgumentException if port is out of range
     */
    public static void checkPort(final int port) {
        if ((port < MIN_PORT) || (port > MAX_PORT)) {
            throw new IllegalArgumentException("The port is out of range: " + port);
        }
    }

    /**
     * <p>
     * Checks whether the param is null.
     * </p>
     *
     * @param param the parameter to check
     * @param paramName the name of the parameter
     * @throws IllegalArgumentException if the param is null
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
     * @param param the parameter to check
     * @param paramName the name of the parameter
     * @throws IllegalArgumentException if the param is empty string.
     */
    public static void checkEmpty(final String param, final String paramName) {
        if ((param != null) && (param.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should not be empty.");
        }
    }

    /**
     * <p>
     * Checks whether a string is null or empty, if it is, <code>IllegalArgumentException</code>
     * is thrown.
     * </p>
     *
     * @param param the string to check
     * @param paramName the parameter name
     * @throws IllegalArgumentException if the string is null or empty
     */
    public static void checkString(final String param, final String paramName) {
        Helper.checkNull(param, paramName);

        if (param.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should not be null.");
        }
    }
}
