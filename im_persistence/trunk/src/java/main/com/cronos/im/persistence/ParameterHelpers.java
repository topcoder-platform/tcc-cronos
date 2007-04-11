/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Helper methods used to validate method parameters.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class ParameterHelpers {
    /**
     * Private constructor to prevent instantiation.
     */
    private ParameterHelpers() {
    }

    /**
     * Verifies that an object parameter is not <code>null</code>.
     *
     * @param obj the parameter to validate
     * @param description a description used to format the exception
     * @throws IllegalArgumentException if <code>obj</code> is <code>null</code>
     */
    public static void checkParameter(Object obj, String description) {
        if (obj == null) {
            throw new IllegalArgumentException(description + " must not be null");
        }
    }

    /**
     * Verifies that a string parameter is not <code>null</code> or an empty string.
     *
     * @param str the parameter to validate
     * @param description a description used to format the exception
     * @throws IllegalArgumentException if <code>str</code> is <code>null</code> or an empty string
     */
    public static void checkParameter(String str, String description) {
        checkParameter((Object) str, description);

        if (str.trim().length() == 0) {
            throw new IllegalArgumentException(description + " must not be an empty string");
        }
    }

    /**
     * Validates the specified criteria map.
     *
     * @param criteria the criteria map to validate
     * @throws IllegalArgumentException if <code>criteria</code> is <code>null</code> or contains <code>null</code>
     *   keys or values or contains values that are not non-<code>null</code>, non-empty strings or a list of
     *   non-<code>null</code>, non-empty strings
     */
    static void validateCriteria(Map criteria) {
        ParameterHelpers.checkParameter(criteria, "search criteria");

        for (Iterator it = criteria.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            try {
                ParameterHelpers.checkParameter((String) entry.getKey(), "criteria key");
            } catch (ClassCastException ex) {
                throw new IllegalArgumentException("criteria keys must be string values");
            }

            Object value = entry.getValue();
            if (value instanceof String) {
                ParameterHelpers.checkParameter((String) value, "criteria value");
            } else if (value instanceof List) {
                ParameterHelpers.checkParameter(value, "criteria value");
                for (Iterator lit = ((List) value).iterator(); lit.hasNext();) {
                    try {
                        ParameterHelpers.checkParameter((String) lit.next(), "criteria value");
                    } catch (ClassCastException ex) {
                        throw new IllegalArgumentException("criteria values must be strings or lists of strings");
                    }
                }
            } else {
                throw new IllegalArgumentException("criteria values must be strings or lists of strings");
            }
        }
    }
}
