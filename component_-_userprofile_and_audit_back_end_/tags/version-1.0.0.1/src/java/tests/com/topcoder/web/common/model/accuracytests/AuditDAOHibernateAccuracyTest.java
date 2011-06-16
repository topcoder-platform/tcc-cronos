/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.hibernate.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;
import com.topcoder.web.common.model.AuditRecord;

/**
 * <p>
 * This class contains Accuracy tests for AuditDAOHibernate.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class AuditDAOHibernateAccuracyTest {

    /**
     * <p>
     * Represents AuditDAOHibernate instance for testing.
     * </p>
     */
    private AuditDAOHibernate auditDAOHibernate;

    /**
     * Represents AuditRecord instance for testing.
     */
    private AuditRecord auditRecord;

    /**
     * <p>
     * Creates TestSuite that aggregates all tests for class under test.
     * </p>
     * @return TestSuite that aggregates all tests for class under test.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AuditDAOHibernateAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        auditDAOHibernate = new AuditDAOHibernate();
        auditRecord = AccuracyHelper.creatAuditRecord(AccuracyHelper.HANDLE, AccuracyHelper.OPERATION_TYPE, false);
        HibernateUtils.begin();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        try {
            AccuracyHelper.clearDatabase();
        } finally {
            HibernateUtils.close();
        }
        auditDAOHibernate = null;
        auditRecord = null;
    }

    /**
     * <p>
     * Tests AuditDAOHibernate default constructor.
     * </p>
     * <p>
     * AuditDAOHibernate should be created successfully.
     * </p>
     */
    @Test
    public void testCtor1() {
        assertNotNull("AuditDAOHibernate should be created successfully.", auditDAOHibernate);
    }

    /**
     * <p>
     * Tests AuditDAOHibernate constructor with passed Session.
     * </p>
     * <p>
     * AuditDAOHibernate should be created successfully.
     * </p>
     */
    @Test
    public void testCtor2() {
        auditDAOHibernate = new AuditDAOHibernate(HibernateUtils.getSession());
        assertNotNull("AuditDAOHibernate should be created successfully.", auditDAOHibernate);
    }

    /**
     * <p>
     * Tests {@link AuditDAOHibernate#audit(AuditRecord)} with valid argument passed.
     * </p>
     * <p>
     * Audit record should be saved successfully.
     * </p>
     */
    @Test
    public void testAudit1() {
        auditDAOHibernate.audit(auditRecord);
        AccuracyHelper.endTransaction();
        HibernateUtils.begin();
        assertNotNull("Audit record should be saved.", getAuditRecord(auditRecord.getHandle()));
    }

    /**
     * <p>
     * Tests {@link AuditDAOHibernate#audit(AuditRecord)} with valid argument passed.
     * </p>
     * <p>
     * Audit record should be updated successfully.
     * </p>
     */
    @Test
    public void testAudit2() {
        auditRecord = AccuracyHelper.creatAuditRecord(AccuracyHelper.HANDLE, AccuracyHelper.OPERATION_TYPE, true);
        HibernateUtils.begin();
        String newIpAddress = "192.0.0.1";
        auditRecord.setIpAddress(newIpAddress);
        auditDAOHibernate.audit(auditRecord);
        AccuracyHelper.endTransaction();
        HibernateUtils.begin();
        assertEquals("Audit record should be updated.", newIpAddress, getAuditRecord(AccuracyHelper.HANDLE)
                .getIpAddress());
    }

    /**
     * <p>
     * Tests {@link AuditDAOHibernate#audit(AuditRecord)} with existed operation for handle.
     * </p>
     * <p>
     * Audit record should has operation.
     * </p>
     */
    @Test
    public void testHasOperation1() {
        auditRecord = AccuracyHelper.creatAuditRecord(AccuracyHelper.HANDLE, AccuracyHelper.OPERATION_TYPE, true);
        HibernateUtils.begin();
        boolean actualResult = auditDAOHibernate.hasOperation(auditRecord.getHandle(), auditRecord.getOperationType());
        assertTrue("Audit record should has operation.", actualResult);
    }

    /**
     * <p>
     * Tests {@link AuditDAOHibernate#audit(AuditRecord)} with not existed operation for handle.
     * </p>
     * <p>
     * Audit record doesn't have operation.
     * </p>
     */
    @Test
    public void testHasOperation2() {
        boolean actualResult = auditDAOHibernate.hasOperation(auditRecord.getHandle(), auditRecord.getOperationType());
        assertFalse("Audit record doesn't has operation.", actualResult);
    }

    /**
     * <p>
     * Retrieves AuditRecord from database for given handle.
     * </p>
     * @param handle
     *            the audit record handle
     * @return AuditRecord from database for given handle
     */
    private AuditRecord getAuditRecord(String handle) {
        Query query = HibernateUtils.getSession().createQuery("from AuditRecord a where handle = ?");
        query.setString(0, handle);
        return (AuditRecord) query.uniqueResult();
    }
}
