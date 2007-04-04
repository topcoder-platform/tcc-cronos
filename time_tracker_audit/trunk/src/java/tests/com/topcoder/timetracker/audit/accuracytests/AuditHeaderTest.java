/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.accuracytests;

import java.sql.Timestamp;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;

/**
 * Accuracy test for <code>{@link com.topcoder.timetracker.audit.AuditHeader}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class AuditHeaderTest extends TestCase {
    /**
     * <p>
     * Represents the <code>AuditHeader</code> to test.
     * </p>
     */
    private AuditHeader header;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        header = new AuditHeader();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        header = null;
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#AuditHeader()}</code>.
     */
    public void testConstructor() {
        assertNotNull("failed to create AuditHeader", header);
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#getClientName()}</code>.
     */
    public void testMethodGetClientName() {
        header.setClientName("testClient");
        assertEquals("Failed to get client name", "testClient", header.getClientName());
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#getEntityId()}</code>.
     */
    public void testMethodGetEntityId() {
        header.setEntityId(1);
        assertEquals("Failed to get entity id", 1, header.getEntityId());
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#getProjectId()}</code>.
     */
    public void testMethodGetProjectId() {
        header.setProjectId(7);
        assertEquals("Failed to get project id", 7, header.getProjectId());
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#getId()}</code>.
     */
    public void testMethodGetId() {
        header.setId(12);
        assertEquals("Failed to get id", 12, header.getId());
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#getProjectName()}</code>.
     */
    public void testMethodGetProjectName() {
        header.setProjectName("TT_Audit");
        assertEquals("Failed to get project name", "TT_Audit", header.getProjectName());
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#getCreationUser()}</code>.
     */
    public void testMethodGetCreationUser() {
        header.setCreationUser("accuracy_tester");
        assertEquals("Failed to get creation user", "accuracy_tester", header.getCreationUser());
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#getActionType()}</code>.
     */
    public void testMethodGetActionType() {
        header.setActionType(1);
        assertEquals("Failed to get action type", 1, header.getActionType());
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#getClientId()}</code>.
     */
    public void testMethodGetClientId() {
        header.setClientId(4);
        assertEquals("Failed to get client id", 4, header.getClientId());
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#getDetails()}</code>.
     */
    public void testMethodGetDetails() {
        AuditDetail detail = new AuditDetail();
        detail.setId(10);
        detail.setColumnName("col_name");
        detail.setNewValue("new_value");
        header.setDetails(new AuditDetail[]{detail});
        assertNotNull("Failed to get AuditDetail", header.getDetails());
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#getResourceId()}</code>.
     */
    public void testMethodGetResourceId() {
        header.setResourceId(6);
        assertEquals("Failed to get resource id", 6, header.getResourceId());
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#getTableName()}</code>.
     */
    public void testMethodGetTableName() {
        header.setTableName("testTable");
        assertEquals("Failed to get table name", header.getTableName(), "testTable");
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#getCompanyId()}</code>.
     */
    public void testMethodGetCompanyId() {
        header.setCompanyId(11);
        assertEquals("Failed to get company id", 11, header.getCompanyId());
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#getCreationDate()}</code>.
     */
    public void testMethodGetCreationDate() {
        Timestamp timestamp = new Timestamp(10000);
        header.setCreationDate(timestamp);
        assertEquals("Failed to get creation date", timestamp, header.getCreationDate());
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#isPersisted()}</code>.
     */
    public void testMethodIsPersisted() {
        header.setPersisted(true);
        assertTrue("Failed to get persisted", header.isPersisted());
    }

    /**
     * Accuracy test for <code>{@link AuditHeader#getApplicationArea()}</code>.
     */
    public void testMethodGetApplicationArea() {
        header.setApplicationArea(ApplicationArea.TT_CONFIGURATION);
        assertEquals("Failed to get the application area", ApplicationArea.TT_CONFIGURATION, header
                .getApplicationArea());
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AuditHeaderTest.class);
    }
}
