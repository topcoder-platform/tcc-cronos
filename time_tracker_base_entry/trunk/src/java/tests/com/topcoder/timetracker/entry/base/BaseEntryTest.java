/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import com.topcoder.timetracker.rejectreason.RejectReason;

import junit.framework.TestCase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Test case for BaseEntry.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class BaseEntryTest extends TestCase {
    /** Default BaseEntry instance used in this test. */
    private BaseEntry entry;

    /**
     * Tests {@link BaseEntry#BaseEntry()}, the instance should be created and the fields should be set to
     * default values.
     */
    public void testBaseEntry() {
        assertNotNull("entry should be created successfully", entry);
        assertEquals("compId should be initially 0", 0, entry.getCompanyId());
        assertNull("date should be initially null", entry.getDate());
        assertNull("reject reasons should be initially null", entry.getRejectReasons());
        assertNull("description should be initially null", entry.getDescription());
    }

    /**
     * Tests {@link BaseEntry#getCompanyId()}.
     */
    public void testGetCompanyId() {
        long id = 123;
        entry.setCompanyId(id);

        assertEquals("id should be obtained as expected", id, entry.getCompanyId());
    }

    /**
     * Tests {@link BaseEntry#getDate()}, date should be obtained.
     */
    public void testGetDate() {
        Date date = new Date();
        entry.setDate(date);

        assertEquals("entry should be obtained as expected", date, entry.getDate());
    }

    /**
     * Tests {@link BaseEntry#getDescription()}, description should be obtained.
     */
    public void testGetDescription() {
        String desc = "desc";

        entry.setDescription(desc);

        assertEquals("description should be set and obtained", desc, entry.getDescription());
    }

    /**
     * Tests {@link BaseEntry#getRejectReasons()}, the reject reasons should be obtained.
     */
    public void testGetRejectReasons() {
        Map map = new HashMap();
        RejectReason reason = new RejectReason();
        map.put("key", reason);

        entry.setRejectReasons(map);

        Map result = entry.getRejectReasons();

        assertEquals("should have only 1 element", 1, result.size());
        assertTrue("reason should be set", result.containsValue(reason));
    }

    /**
     * Tests {@link BaseEntry#setCompanyId(long)}. The id should be set.
     */
    public void testSetCompanyId() {
        long id = 123;
        entry.setCompanyId(id);

        assertEquals("id should be obtained as expected", id, entry.getCompanyId());
    }

    /**
     * Tests {@link BaseEntry#setDate(Date)}, the date should be set.
     */
    public void testSetDate() {
        Date date = new Date();
        entry.setDate(date);

        assertEquals("entry should be obtained as expected", date, entry.getDate());
    }

    /**
     * Tests {@link BaseEntry#setDate(Date)} with null and IAE is expected.
     */
    public void testSetDateNull() {
        try {
            entry.setDate(null);
            fail("dates is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link BaseEntry#setDescription(String)}, description should be set.
     */
    public void testSetDescription() {
        String desc = "desc";

        entry.setDescription(desc);

        assertEquals("description should be set and obtained", desc, entry.getDescription());
    }

    /**
     * Tests {@link BaseEntry#setDescription(String)} with null, IAE is expected.
     */
    public void testSetDescriptionNull() {
        try {
            entry.setDescription(null);
            fail("description is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link BaseEntry#setRejectReasons(Map)}, reasons should be set.
     */
    public void testSetRejectReasons() {
        Map map = new HashMap();
        RejectReason reason = new RejectReason();
        map.put("key", reason);

        entry.setRejectReasons(map);

        Map result = entry.getRejectReasons();

        assertEquals("should have only 1 element", 1, result.size());
        assertTrue("reason should be set", result.containsValue(reason));
    }

    /**
     * Tests {@link BaseEntry#setRejectReasons(Map)} with map containing element not of RejectReason type,
     * IllegalArgumentException is expected.
     */
    public void testSetRejectReasonsInvalidElement() {
        Map map = new HashMap();
        map.put("key", "String_Value");

        try {
            entry.setRejectReasons(map);
            fail("values is not RejectReason type and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link BaseEntry#setRejectReasons(Map)} with null and IAE is expected.
     */
    public void testSetRejectReasonsNull() {
        try {
            entry.setRejectReasons(null);
            fail("argument is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link BaseEntry#setRejectReasons(Map)} with map containning null key, IAE is expected.
     */
    public void testSetRejectReasonsNullKey() {
        Map map = new HashMap();
        map.put(null, new RejectReason());

        try {
            entry.setRejectReasons(map);
            fail("keys contains null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link BaseEntry#setRejectReasons(Map)} with map containning null value, IAE is expected.
     */
    public void testSetRejectReasonsNullValue() {
        Map map = new HashMap();
        map.put("key", null);

        try {
            entry.setRejectReasons(map);
            fail("values contains null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        entry = new BaseEntry() {
        };
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        entry = null;
    }
}
