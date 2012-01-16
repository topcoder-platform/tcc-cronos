/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

/**
 * The TestHelper class provides static methods used to facilitate component testing.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTestHelper {

    /**
     * Do not allow this class to be instantiated.
     */
    private FailureTestHelper() {
        // Empty
    }

    public static String getText() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 46; i++) {
            text.append('a');
        }
        return text.toString();
    }

}
