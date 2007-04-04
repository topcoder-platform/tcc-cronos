/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.audit.AuditDetail;

/**
 * Accuracy test for <code>{@link com.topcoder.timetracker.audit.AuditDetail}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class AuditDetailTest extends TestCase {
    /**
     * <p>
     * Represents the <code>AuditDetail</code> to test.
     * </p>
     */
    private AuditDetail detail;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        detail = new AuditDetail();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        detail = null;
    }

    /**
     * Accuracy test for <code>{@link AuditDetail#AuditDetail()}</code>.
     */
    public void testConstructor() {
        assertNotNull("Failed to create AuditDetail", detail);
    }

    /**
     * Accuracy test for <code>{@link AuditDetail#getColumnName()}</code>.
     */
    public void testMethodGetColumnName() {
        detail.setColumnName("column_name");
        assertEquals("failed to get the column name", "column_name", detail.getColumnName());
    }

    /**
     * Accuracy test for <code>{@link AuditDetail#isPersisted()}</code>.
     */
    public void testMethodIsPersisted() {
        detail.setPersisted(true);
        assertTrue("failed to get the persisted", detail.isPersisted());
    }

    /**
     * Accuracy test for <code>{@link AuditDetail#getNewValue()}</code>.
     */
    public void testMethodGetNewValue() {
        detail.setNewValue("new_value");
        assertEquals("failed to get new value", "new_value", detail.getNewValue());
    }

    /**
     * Accuracy test for <code>{@link AuditDetail#getOldValue()}</code>.
     */
    public void testMethodGetOldValue() {
        detail.setOldValue("old_value");
        assertEquals("failed to get old value", "old_value", detail.getOldValue());
    }

    /**
     * Accuracy test for <code>{@link AuditDetail#getId()}</code>.
     */
    public void testMethodGetId() {
        detail.setId(1);
        assertEquals("failed to get Id", 1, detail.getId());
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AuditDetailTest.class);
    }
}
