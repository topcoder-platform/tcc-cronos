/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.failuretests;

import java.sql.Timestamp;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditConfigurationException;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.persistence.InformixAuditPersistence;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link InformixAuditPersistence}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class InformixAuditPersistenceFailureTests extends TestCase {

    /**
     * <p>
     * Represents the <code>{@link InformixAuditPersistence}</code> class
     * </p>
     */
    private InformixAuditPersistence informixAuditPersistence;

    /**
     * <p>
     * Represents the aduit header to create.
     * </p>
     */
    private AuditHeader record;

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        FailureTestHelper.clearNamespaces();
        // load namespaces.
        FailureTestHelper.loadNamesapces(FailureTestHelper.DBCONNECTIONFACTORY_CONFIG);
        FailureTestHelper.loadNamesapces(FailureTestHelper.SEARCHBUILDER_CONFIG);
        FailureTestHelper.loadNamesapces(FailureTestHelper.INFORMIXAUDITPERSIST_CONFIG);
        FailureTestHelper.loadNamesapces(FailureTestHelper.LOGGING_CONFIG);

        informixAuditPersistence = new InformixAuditPersistence(
            "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence");

        record = new AuditHeader();
        record.setApplicationArea(ApplicationArea.TT_TIME);
        record.setCreationDate(new Timestamp(System.currentTimeMillis()));
        record.setCreationUser("TopCoder");
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        
        FailureTestHelper.clearNamespaces();
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_NullNamespace() throws Exception {
        try {
            new InformixAuditPersistence(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_EmptyNamespace() throws Exception {
        try {
            new InformixAuditPersistence("");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_TrimmedEmptyNamespace() throws Exception {
        try {
            new InformixAuditPersistence("  ");
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConfig_Case1() throws Exception {
        try {
            new InformixAuditPersistence(
                "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence.invalid.case1");
            fail("expect throw AuditConfigurationException");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConfig_Case2() throws Exception {
        try {
            new InformixAuditPersistence(
                "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence.invalid.case2");
            fail("expect throw AuditConfigurationException");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConfig_Case3() throws Exception {
        try {
            new InformixAuditPersistence(
                "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence.invalid.case3");
            fail("expect throw AuditConfigurationException");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConfig_Case4() throws Exception {
        try {
            new InformixAuditPersistence(
                "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence.invalid.case4");
            fail("expect throw AuditConfigurationException");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConfig_Case5() throws Exception {
        try {
            new InformixAuditPersistence(
                "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence.invalid.case5");
            fail("expect throw AuditConfigurationException");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConfig_Case6() throws Exception {
        try {
            new InformixAuditPersistence(
                "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence.invalid.case6");
            fail("expect throw AuditConfigurationException");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConfig_Case7() throws Exception {
        try {
            new InformixAuditPersistence(
                "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence.invalid.case7");
            fail("expect throw AuditConfigurationException");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConfig_Case8() throws Exception {
        try {
            new InformixAuditPersistence(
                "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence.invalid.case8");
            fail("expect throw AuditConfigurationException");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConfig_Case9() throws Exception {
        try {
            new InformixAuditPersistence(
                "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence.invalid.case9");
            fail("expect throw AuditConfigurationException");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConfig_Case10() throws Exception {
        try {
            new InformixAuditPersistence(
                "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence.invalid.case10");
            fail("expect throw AuditConfigurationException");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConfig_Case11() throws Exception {
        try {
            new InformixAuditPersistence(
                "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence.invalid.case11");
            fail("expect throw AuditConfigurationException");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConfig_Case12() throws Exception {
        try {
            new InformixAuditPersistence(
                "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence.invalid.case12");
            fail("expect throw AuditConfigurationException");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConfig_Case13() throws Exception {
        try {
            new InformixAuditPersistence(
                "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence.invalid.case13");
            fail("expect throw AuditConfigurationException");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#InformixAuditPersistence(String)}</code> constructor.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConfig_Case14() throws Exception {
        try {
            new InformixAuditPersistence(
                "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence.invalid.case14");
            fail("expect throw AuditConfigurationException");
        } catch (AuditConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#createAuditRecord(AuditHeader)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuditRecord_NullRecord() throws Exception {
        try {
            informixAuditPersistence.createAuditRecord(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#createAuditRecord(AuditHeader)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuditRecord_InvalidRecord1() throws Exception {
        record.setApplicationArea(null);
        try {
            informixAuditPersistence.createAuditRecord(record);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#createAuditRecord(AuditHeader)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuditRecord_InvalidRecord2() throws Exception {
        record.setCreationDate(null);
        try {
            informixAuditPersistence.createAuditRecord(record);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#createAuditRecord(AuditHeader)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuditRecord_InvalidRecord3() throws Exception {
        record.setCreationUser(null);
        try {
            informixAuditPersistence.createAuditRecord(record);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#createAuditRecord(AuditHeader)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuditRecord_InvalidRecord4() throws Exception {
        record.setTableName(null);
        try {
            informixAuditPersistence.createAuditRecord(record);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#createAuditRecord(AuditHeader)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuditRecord_InvalidRecord5() throws Exception {
        record.setDetails(new AuditDetail[] {null});
        try {
            informixAuditPersistence.createAuditRecord(record);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for <code>{@link InformixAuditPersistence#createAuditRecord(AuditHeader)}</code> method.
     * </p>
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateAuditRecord_InvalidRecord6() throws Exception {
        AuditDetail detail = new AuditDetail();
        record.setDetails(new AuditDetail[] {detail});
        try {
            informixAuditPersistence.createAuditRecord(record);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
