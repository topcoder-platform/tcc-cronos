/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import com.topcoder.util.collection.typesafeenum.Enum;

import junit.framework.TestCase;


/**
 * <p>This test case contains accuracy tests for <code>AddressType</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AddressTypeTestAcc extends TestCase {
    /**
     * <p>
     * Test method <code>getId()</code>.
     * </p>
     */
    public void testGetId() {
        assertEquals(1, AddressType.PROJECT.getId());
        assertEquals(2, AddressType.CLIENT.getId());
        assertEquals(3, AddressType.COMPANY.getId());
        assertEquals(4, AddressType.USER.getId());
    }

    /**
     * <p>
     * Test method <code>toString()</code>.
     * </p>
     */
    public void testToString() {
        assertEquals("PROJECT", AddressType.PROJECT.toString());
        assertEquals("CLIENT", AddressType.CLIENT.toString());
        assertEquals("COMPANY", AddressType.COMPANY.toString());
        assertEquals("USER", AddressType.USER.toString());
    }

    /**
     * <p>
     * Test method <code>getEnumByOrdinal()</code>.
     * </p>
     */
    public void testGetEnumByOrdinal1() {
        assertEquals(Enum.getEnumByOrdinal(0, AddressType.class), AddressType.PROJECT);
        assertEquals(Enum.getEnumByOrdinal(1, AddressType.class), AddressType.CLIENT);
        assertEquals(Enum.getEnumByOrdinal(2, AddressType.class), AddressType.COMPANY);
        assertEquals(Enum.getEnumByOrdinal(3, AddressType.class), AddressType.USER);
    }

    /**
     * <p>
     * Test method <code>getEnumByOrdinal()</code>.
     * </p>
     */
    public void testGetEnumByOrdinal2() {
        Enum e = Enum.getEnumByOrdinal(4, AddressType.class);
        assertFalse(e instanceof AddressType);
        e = Enum.getEnumByOrdinal(-1, AddressType.class);
        assertFalse(e instanceof AddressType);
    }
}
