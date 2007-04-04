/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.accuracytests;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.mockejb.jndi.MockContextFactory;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.LessThanFilter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;

/**
 * Accuracy test for <code>{@link com.topcoder.timetracker.audit.ejb.AuditDelegate}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class AuditEjbTest extends TestCase {
    /**
     * <p>
     * Represents the <code>AuditDelegate</code> to test.
     * </p>
     */
    private AuditDelegate delegate;

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
        Map env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, MockContextFactory.class.getName());
        InitialContext context = new InitialContext((Hashtable) env);
        AccuracyTestHelper.deployEJB(context);
        delegate = new AuditDelegate("com.topcoder.timetracker.audit.ejb.accuracy");
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        AuditHeader[] records = delegate.searchAudit(new EqualToFilter("resource_id", new Integer(6)));
        for (int i = 0; i < records.length; i++) {
            delegate.rollbackAuditRecord(records[i].getId());
        }
        AccuracyTestHelper.clearDatabase(connection);
        AccuracyTestHelper.undeployEJB();
    }

    /**
     * Accuracy test for <code>{@link AuditDelegate#AuditDelegate(String)}</code>.
     */
    public void testConstructor_String() {
        assertNotNull("Failed to create AuditDelegate", delegate);
    }

    /**
     * Accuracy test for <code>{@link AuditDelegate#createAuditRecord(AuditHeader)}</code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testMethodCreateAuditRecord_AuditHeader() throws Exception {
        AuditHeader record = AccuracyTestHelper.createRecord();
        delegate.createAuditRecord(record);

        AuditHeader[] actual = delegate.searchAudit(new EqualToFilter("resource_id", new Long(record
                .getResourceId())));
        assertEquals("Failed to create audit record", 1, actual.length);

        assertEquals("Failed to create audit record", 5, actual[0].getDetails().length);

    }

    /**
     * Accuracy test for <code>{@link AuditDelegate#rollbackAuditRecord(int)}</code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testMethodRollbackAuditRecord_int() throws Exception {
        AuditHeader record = AccuracyTestHelper.createRecord();
        delegate.createAuditRecord(record);
        AuditHeader[] records = delegate.searchAudit(new EqualToFilter("resource_id", new Long(6)));
        for (int i = 0; i < records.length; i++) {
            delegate.rollbackAuditRecord(records[i].getId());
        }

        records = delegate.searchAudit(new EqualToFilter("resource_id", new Long(record.getResourceId())));
        assertEquals("Failed to rollback ", 0, records.length);

    }

    /**
     * Accuracy test for <code>{@link AuditDelegate#searchAudit(Filter)}</code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testMethodSearchAudit_Filter() throws Exception {
        AuditHeader record = AccuracyTestHelper.createRecord();
        delegate.createAuditRecord(record);
        AuditHeader[] actual = delegate.searchAudit(new EqualToFilter("resource_id", new Long(record
                .getResourceId())));
        assertEquals("Failed to search audit", 1, actual.length);

        actual = delegate.searchAudit(new EqualToFilter("application_area_id", new Long(record
                .getApplicationArea().getId())));
        assertEquals("Failed to search audit", 1, actual.length);

        actual = delegate.searchAudit(new EqualToFilter("client_id", new Long(record.getClientId())));
        assertEquals("Failed to search audit", 1, actual.length);

        actual = delegate.searchAudit(new EqualToFilter("project_id", new Long(record.getProjectId())));
        assertEquals("Failed to search audit", 1, actual.length);

        Filter filter = new AndFilter(new GreaterThanFilter("creation_date", new Timestamp(record
                .getCreationDate().getTime())), new LessThanFilter("creation_date", new Timestamp(System
                .currentTimeMillis())));
        delegate.searchAudit(filter);
        assertEquals("Failed to search audit", 1, actual.length);
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AuditEjbTest.class);
    }
}
