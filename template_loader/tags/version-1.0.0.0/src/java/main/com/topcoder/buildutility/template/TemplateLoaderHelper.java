/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0
 */
package com.topcoder.buildutility.template;

/**
 * <p>A Helper class for Template Loader.</p>
 * <p>This class provides some useful methods, such as argument validation, etc.</p>
 *
 * @author oldbig
 *
 * @version 1.0
 */
class TemplateLoaderHelper {

    /**
     * Private constructor to prevent this class be instantiated.
     */
    private TemplateLoaderHelper() {
        // empty
    }

    /**
     * Checks whether the given Object is null.
     *
     * @param arg the argument to check
     * @param name the name of the argument
     * @throws NullPointerException if the given Object is null
     */
    static void checkNull(Object arg, String name) {
        if (arg == null) {
            throw new NullPointerException(name + " should not be null.");
        }
    }

    /**
     * Checks whether the given String is empty.
     *
     * @param arg the String to check
     * @param name the name of the argument
     * @throws IllegalArgumentException if the given string is empty
     */
    static void checkEmpty(String arg, String name) {
        if (arg != null && arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }
    }

    /**
     * Checks whether the given String is null or empty.
     *
     * @param arg the String to check
     * @param name the name of the argument
     * @throws NullPointerException if the given string is null
     * @throws IllegalArgumentException if the given string is empty
     */
    static void checkString(String arg, String name) {
        checkNull(arg, name);
        checkEmpty(arg, name);
    }
}