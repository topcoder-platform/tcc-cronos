/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import com.topcoder.util.collection.typesafeenum.Enum;

import junit.framework.TestCase;


/**
 * <p>This test case contains accuracy tests for <code>ContactType</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ContactTypeTestAcc extends TestCase {
    /**
     * <p>
     * Test method <code>getId()</code>.
     * </p>
     */
    public void testGetId() {
        assertEquals(1, ContactType.PROJECT.getId());
        assertEquals(2, ContactType.CLIENT.getId());
        assertEquals(3, ContactType.COMPANY.getId());
        assertEquals(4, ContactType.USER.getId());
    }

    /**
     * <p>
     * Test method <code>toString()</code>.
     * </p>
     */
    public void testToString() {
        assertEquals("PROJECT", ContactType.PROJECT.toString());
        assertEquals("CLIENT", ContactType.CLIENT.toString());
        assertEquals("COMPANY", ContactType.COMPANY.toString());
        assertEquals("USER", ContactType.USER.toString());
    }

    /**
     * <p>
     * Test method <code>getEnumByOrdinal()</code>.
     * </p>
     */
    public void testGetEnumByOrdinal1() {
        assertEquals(Enum.getEnumByOrdinal(0, ContactType.class), ContactType.PROJECT);
        assertEquals(Enum.getEnumByOrdinal(1, ContactType.class), ContactType.CLIENT);
        assertEquals(Enum.getEnumByOrdinal(2, ContactType.class), ContactType.COMPANY);
        assertEquals(Enum.getEnumByOrdinal(3, ContactType.class), ContactType.USER);
    }

    /**
     * <p>
     * Test method <code>getEnumByOrdinal()</code>.
     * </p>
     */
    public void testGetEnumByOrdinal2() {
        Enum e = Enum.getEnumByOrdinal(4, ContactType.class);
        assertFalse(e instanceof ContactType);
        e = Enum.getEnumByOrdinal(-1, ContactType.class);
        assertFalse(e instanceof ContactType);
    }
}
