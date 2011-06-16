/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.dao.hibernate;

import java.util.Date;

import com.topcoder.web.common.HibernateUtils;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.model.AuditRecord;

import junit.framework.TestCase;

/**
 * Unit test cases for {@link AuditDAOHibernate} class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AuditDAOHibernateTest extends TestCase {
    /**
     * AuditDAO instance used for testing.
     */
    private AuditDAO instance;

    /**
     * Sets up the test environment.
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        instance = new AuditDAOHibernate();
        HibernateUtils.begin();
    }

    /**
     * Tears down the test environment.
     */
    public void tearDown() {
        HibernateUtils.getSession().flush();
        HibernateUtils.commit();
        HibernateUtils.close();
        instance = null;
    }

    /**
     * Test case for {@link AuditDAOHibernate#AuditDAOHibernate()} constructor.
     */
    public void testAuditDAOHibernate() {
        assertNotNull("Error instantiating the class", instance);
    }

    /**
     * Test case for {@link AuditDAOHibernate#AuditDAOHibernate(org.hibernate.Session)} constructor.
     */
    public void testAuditDAOHibernate1() {
        instance = new AuditDAOHibernate(HibernateUtils.getSession());
        assertNotNull("Error instantiating the class", instance);
    }

    /**
     * Test case for {@link AuditDAOHibernate#audit(AuditRecord)} method.
     */
    public void testAudit() {
        AuditRecord record = getAuditRecord();
        instance.audit(record);

        HibernateUtils.getSession().delete(record);
    }

    /**
     * Helper method to construct AuditRecord objects.
     * @return the AuditRecord Object
     */
    private AuditRecord getAuditRecord() {
        AuditRecord record = new AuditRecord();
        record.setHandle("handle");
        record.setIpAddress("ip");
        record.setOperationType("op");
        record.setTimestamp(new Date());

        return record;
    }

    /**
     * Test case for {@link AuditDAOHibernate#hasOperation(String, String)} method.
     */
    public void testHasOperation() {
        AuditRecord record = getAuditRecord();
        instance.audit(record);
        assertTrue("Error checking for " + "operation", instance.hasOperation(record.getHandle(), record
                .getOperationType()));

        HibernateUtils.getSession().delete(record);
    }
}
