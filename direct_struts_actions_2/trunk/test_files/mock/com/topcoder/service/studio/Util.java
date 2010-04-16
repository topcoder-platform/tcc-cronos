/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

/**
 * <p>Simple helper to be used inside the component. Contains several methods to perform parameter checking and
 * logging.</p>
 *
 * <p>This class is obviously thread-safe since it's stateless.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class Util {
    /**
     * <p>Private constructor, prevents class from being instantiated.</p>
     */
    private Util() {
    }

    /**
     * <p>Checks object for null and throw IllegalArgumentException if it is.</p>
     *
     * @param name of the object, to be used in messages.
     * @param data Object to check
     * @throws IllegalArgumentException when data parameter is null
     */
    public static void checkNull(String name, Object data) {
        if (data == null) {
            throw new IllegalArgumentException(name + " parameter not supposed to be null.");
        }
    }
}
