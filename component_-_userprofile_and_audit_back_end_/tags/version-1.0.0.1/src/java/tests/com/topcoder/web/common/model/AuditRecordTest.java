/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Test case for {@link AuditRecord} class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AuditRecordTest extends TestCase {

    /**
     * AuditRecord instance used for testing.
     */
    private AuditRecord instance;

    /**
     * Sets up the test environment.
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        instance = new AuditRecord();
    }

    /**
     * Tears down the test environment.
     */
    public void tearDown() {
        instance = null;
    }

    /**
     * Test case for {@link AuditRecord#setId(Long)} and {@link AuditRecord#getId()} methods.
     */
    public void testSetGetId() {
        instance.setId(100l);
        assertEquals("error setting getting id", 100l, instance.getId().longValue());
    }

    /**
     * Test case for {@link AuditRecord#setOperationType(String)} and {@link AuditRecord#getOperationType()} methods.
     */
    public void testSetGetOperationType() {
        instance.setOperationType("op");
        assertEquals("error setting getting OperationType", "op", instance.getOperationType());
    }

    /**
     * Test case for {@link AuditRecord#setHandle(String)} and {@link AuditRecord#getHandle()} methods.
     */
    public void testSetGetHandle() {
        instance.setHandle("op");
        assertEquals("error setting getting Handle", "op", instance.getHandle());
    }

    /**
     * Test case for {@link AuditRecord#setIpAddress(String)} and {@link AuditRecord#getIpAddress()} methods.
     */
    public void testSetGetIpAdress() {
        instance.setIpAddress("op");
        assertEquals("error setting getting IpAdress", "op", instance.getIpAddress());
    }

    /**
     * Test case for {@link AuditRecord#setPreviousValue(String)} and {@link AuditRecord#getPreviousValue()} methods.
     */
    public void testSetGetPreviousValue() {
        instance.setPreviousValue("op");
        assertEquals("error setting getting PreviousValue", "op", instance.getPreviousValue());
    }

    /**
     * Test case for {@link AuditRecord#setNewValue(String)} and {@link AuditRecord#getNewValue()} methods.
     */
    public void testSetGetNewValue() {
        instance.setNewValue("op");
        assertEquals("error setting getting NewValue", "op", instance.getNewValue());
    }

    /**
     * Test case for {@link AuditRecord#getTimestamp()} and {@link AuditRecord#setTimestamp(Date)} methods.
     */
    public void testSetGetTimestamp() {
        Date date = new Date();
        instance.setTimestamp(date);
        assertEquals("Error setting getting timestamp", date, instance.getTimestamp());
    }
}
