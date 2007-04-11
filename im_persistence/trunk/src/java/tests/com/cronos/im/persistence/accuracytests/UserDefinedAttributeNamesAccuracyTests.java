/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.accuracytests;

import com.cronos.im.persistence.UserDefinedAttributeNames;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for <code>{@link UserDefinedAttributeNames}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class UserDefinedAttributeNamesAccuracyTests extends TestCase {

    /**
     * <p>
     * Accuracy test for <code>{@link UserDefinedAttributeNames}</code>.
     * </p>
     */
    public void testUserDefinedAttributeNamesAccuracy() {
        assertEquals("incorrect attribute name.", "Company", UserDefinedAttributeNames.COMPANY);
        assertEquals("incorrect attribute name.", "Email", UserDefinedAttributeNames.EMAIL);
        assertEquals("incorrect attribute name.", "First Name", UserDefinedAttributeNames.FIRST_NAME);
        assertEquals("incorrect attribute name.", "Last Name", UserDefinedAttributeNames.LAST_NAME);
        assertEquals("incorrect attribute name.", "Name", UserDefinedAttributeNames.NAME);
        assertEquals("incorrect attribute name.", "Title", UserDefinedAttributeNames.TITLE);
    }
}
