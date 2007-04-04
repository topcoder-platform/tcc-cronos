/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.accuracytests;

import java.sql.Connection;
import java.sql.Timestamp;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.LessThanFilter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditPersistence;
import com.topcoder.timetracker.audit.persistence.InformixAuditPersistence;

/**
 * Accuracy test for <code>{@link com.topcoder.timetracker.audit.persistence.InformixAuditPersistence}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class InformixAuditPersistenceTest extends TestCase {
    /**
     * <p>
     * Represents the <code>InformixAuditPersistence</code> to test.
     * </p>
     */
    private InformixAuditPersistence persistence;

    /**
     * <p>
     * Represents the connection used for data setup.
     * </p>
     */
    private Connection connection;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadConfigs();
        connection = AccuracyTestHelper.getConnection();
        AccuracyTestHelper.prepareData(connection);
        persistence = new InformixAuditPersistence("com.topcoder.timetracker.audit.accuracy");
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        // clear the db before next test.
        AuditHeader[] actual = persistence.searchAudit(new EqualToFilter("resource_id", new Integer(6)));
        for (int i = 0; i < actual.length; i++) {
            persistence.rollbackAuditRecord(actual[i].getId());
        }
        AccuracyTestHelper.clearDatabase(connection);
        persistence = null;
    }

    /**
     * Accuracy test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code>.
     */
    public void testConstructor_String() {
        assertNotNull("failed to create InformixAuditPersistence", persistence);

        assertTrue("InformixAuditPersistence should inherit from AuditPersistence",
                persistence instanceof AuditPersistence);
    }

    /**
     * Accuracy test for <code>{@link InformixAuditPersistence#createAuditRecord(AuditHeader)}</code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testMethodCreateAuditRecord_AuditHeader() throws Exception {
        AuditHeader record = AccuracyTestHelper.createRecord();
        persistence.createAuditRecord(record);

        AuditHeader[] actual = persistence.searchAudit(new EqualToFilter("resource_id", new Long(record
                .getResourceId())));
        assertEquals("Failed to create audit record", 1, actual.length);

        assertEquals("Failed to create audit record", 5, actual[0].getDetails().length);

    }

    /**
     * Accuracy test for <code>{@link InformixAuditPersistence#searchAudit(Filter)}</code>.
     *
     * @throws Exception
     *             to junit
     */
    public void testMethodSearchAudit_Filter() throws Exception {
        AuditHeader record = AccuracyTestHelper.createRecord();
        persistence.createAuditRecord(record);
        AuditHeader[] actual = persistence.searchAudit(new EqualToFilter("resource_id", new Long(record
                .getResourceId())));
        assertEquals("Failed to search audit", 1, actual.length);

        actual = persistence.searchAudit(new EqualToFilter("application_area_id", new Long(record
                .getApplicationArea().getId())));
        assertEquals("Failed to search audit", 1, actual.length);

        actual = persistence.searchAudit(new EqualToFilter("client_id", new Long(record.getClientId())));
        assertEquals("Failed to search audit", 1, actual.length);

        actual = persistence.searchAudit(new EqualToFilter("project_id", new Long(record.getProjectId())));
        assertEquals("Failed to search audit", 1, actual.length);

        Filter filter = new AndFilter(new GreaterThanFilter("creation_date", new Timestamp(record
                .getCreationDate().getTime())), new LessThanFilter("creation_date", new Timestamp(System
                .currentTimeMillis())));
        persistence.searchAudit(filter);
        assertEquals("Failed to search audit", 1, actual.length);
    }

    /**
     * Accuracy test for <code>{@link InformixAuditPersistence#rollbackAuditRecord(int)}</code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testMethodRollbackAuditRecord_int() throws Exception {
        AuditHeader record = AccuracyTestHelper.createRecord();
        persistence.createAuditRecord(record);
        AuditHeader[] records = persistence.searchAudit(new EqualToFilter("resource_id", new Long(6)));
        for (int i = 0; i < records.length; i++) {
            persistence.rollbackAuditRecord(records[i].getId());
        }

        records = persistence.searchAudit(new EqualToFilter("resource_id",
                new Long(record.getResourceId())));
        assertEquals("Failed to rollback ", 0, records.length);
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(InformixAuditPersistenceTest.class);
    }
}
