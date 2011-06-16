/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model.accuracytests;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.model.AuditRecord;

/**
 * <p>
 * This class contains Accuracy test for AuditRecord.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class AuditRecordAccuracyTest {

    /**
     * <p>
     * Represents AuditRecord instance for testing.
     * </p>
     */
    private AuditRecord auditRecord;

    /**
     * <p>
     * Creates TestSuite that aggregates all tests for class under test.
     * </p>
     * @return TestSuite that aggregates all tests for class under test.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AuditRecordAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        auditRecord = new AuditRecord();
        auditRecord.setId(1L);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        auditRecord = null;
    }

    /**
     * <p>
     * Tests {@link AuditRecord#setId(Long)} and {@link AuditRecord#getId()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testSetGetId() {
        Long id = 1L;
        auditRecord.setId(id);
        assertEquals("Values should be equal.", id, auditRecord.getId());
    }

    /**
     * <p>
     * Tests {@link AuditRecord#setHandle(String)} and {@link AuditRecord#getHandle()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testSetGetHandle() {
        String handle = "tc_admin";
        auditRecord.setHandle(handle);
        assertEquals("Values should be equal.", handle, auditRecord.getHandle());
    }

    /**
     * <p>
     * Tests {@link AuditRecord#setOperationType(String)} and {@link AuditRecord#getOperationType()} with valid
     * arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testSetGetOperationType() {
        String operationType = "operationType";
        auditRecord.setOperationType(operationType);
        assertEquals("Values should be equal.", operationType, auditRecord.getOperationType());
    }

    /**
     * <p>
     * Tests {@link AuditRecord#setIpAddress(String)} and {@link AuditRecord#getIpAddress()} with valid arguments
     * passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testSetGetIpAddress() {
        String ipAddress = "ipAddress";
        auditRecord.setIpAddress(ipAddress);
        assertEquals("Values should be equal.", ipAddress, auditRecord.getIpAddress());
    }

    /**
     * <p>
     * Tests {@link AuditRecord#setPreviousValue(String)} and {@link AuditRecord#getPreviousValue()} with valid
     * arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testSetGetPreviousValue() {
        String previousValue = "previousValue";
        auditRecord.setPreviousValue(previousValue);
        assertEquals("Values should be equal.", previousValue, auditRecord.getPreviousValue());
    }

    /**
     * <p>
     * Tests {@link AuditRecord#setNewValue(String)} and {@link AuditRecord#getNewValue()} with valid arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testSetGetNewValue() {
        String newValue = "previousValue";
        auditRecord.setNewValue(newValue);
        assertEquals("Values should be equal.", newValue, auditRecord.getNewValue());
    }

    /**
     * <p>
     * Tests {@link AuditRecord#setTimestamp(java.util.Date)} and {@link AuditRecord#getTimestamp()} with valid
     * arguments passed.
     * </p>
     * <p>
     * Values should be set and retrieved successfully.
     * </p>
     */
    @Test
    public void testSetGetTimestamp() {
        Date timestamp = new Date();
        auditRecord.setTimestamp(timestamp);
        assertEquals("Values should be equal.", timestamp, auditRecord.getTimestamp());
    }
}
