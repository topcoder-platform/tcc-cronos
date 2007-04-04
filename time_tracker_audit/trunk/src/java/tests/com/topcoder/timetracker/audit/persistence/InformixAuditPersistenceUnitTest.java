/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.persistence;

import java.sql.Connection;
import java.sql.Timestamp;

import junit.framework.TestCase;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.LessThanFilter;
import com.topcoder.timetracker.audit.AuditConfigurationException;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.UnitTestHelper;


/**
 * <p>
 * Tests functionality and error cases of <code>InformixAuditPersistence</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class InformixAuditPersistenceUnitTest extends TestCase {
    /** Represents the valid namespace for testing. */
    private static final String NAME_SPACE = "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence";

    /** Represents the connection instance for testing. */
    private Connection connection = null;

    /** Represents the <code>InformixAuditPersistence</code> instance used for testing. */
    private InformixAuditPersistence persistence;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();
        connection = UnitTestHelper.getConnection();
        UnitTestHelper.prepareData(connection);
        persistence = new InformixAuditPersistence(NAME_SPACE);
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        try {
            UnitTestHelper.clearConfig();
            UnitTestHelper.clearDatabase(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Test the constructor <code>InformixAuditPersistence(String)</code> when the given namespace is null,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInformixAuditPersistenceNullNamespace() throws Exception {
        try {
            new InformixAuditPersistence(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the constructor <code>InformixAuditPersistence(String)</code> when the given namespace is an empty string,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInformixAuditPersistence_EmptyNamespace() throws Exception {
        try {
            new InformixAuditPersistence(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the constructor <code>InformixAuditPersistence(String)</code> when the given namespace does not exist,
     * AuditConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInformixAuditPersistence_NotExistNamespace() throws Exception {
        try {
            new InformixAuditPersistence("NotExist");
            fail("AuditConfigurationException should be thrown.");
        } catch (AuditConfigurationException e) {
            // good
        }
    }

    /**
     * Test the constructor <code>AuditDelegate(String)</code> when the connectionFactoryNamespace property value is
     * invalid, AuditConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidConnectionFactoryNamespace() throws Exception {
        try {
            new InformixAuditPersistence(NAME_SPACE + ".InvalidConnectionFactoryNamespace");
            fail("AuditConfigurationException should be thrown.");
        } catch (AuditConfigurationException e) {
            // good
        }
    }

    /**
     * Test the constructor <code>InformixAuditPersistence(String)</code> when the idGeneratorName property value is
     * invalid, AuditConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidIdGeneratorName() throws Exception {
        try {
            new InformixAuditPersistence(NAME_SPACE + ".InvalidIdGeneratorName");
            fail("AuditConfigurationException should be thrown.");
        } catch (AuditConfigurationException e) {
            // good
        }
    }

    /**
     * Test the constructor <code>InformixAuditPersistence(String)</code> when the searchBundleNamespace property value
     * is invalid, AuditConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidSearchBundleNamespace() throws Exception {
        try {
            new InformixAuditPersistence(NAME_SPACE + ".InvalidSearchBundleNamespace");
            fail("AuditConfigurationException should be thrown.");
        } catch (AuditConfigurationException e) {
            // good
        }
    }

    /**
     * Test the constructor <code>InformixAuditPersistence(String)</code> when the searchBundleName property value is
     * invalid, AuditConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testInformixAuditPersistence_InvalidSearchBundleName() throws Exception {
        try {
            new InformixAuditPersistence(NAME_SPACE + ".InvalidSearchBundleName");
            fail("AuditConfigurationException should be thrown.");
        } catch (AuditConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>InformixAuditPersistence(String)</code> when the namespace is correct.
     * Everything should be successfully initialized.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testInformixAuditPersistence_Accuracy() throws Exception {
        assertNotNull("The InformixAuditPersistence instance should be created.", persistence);
        assertNotNull("The log should be created.",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "log"));
        assertNotNull("The connectionFactory should be created.",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "connectionFactory"));
        assertNotNull("The searchBundle should be created.",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "searchBundle"));
        assertNotNull("The defaultValues should be created.",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "defaultValues"));
        assertEquals("The connectionName should be loaded properly.", "informix",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "connectionName"));
    }

    /**
     * <p>
     * Test the method of <code>createAuditRecord(AuditHeader)</code> when the AuditHeader is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_NullAuditHeader() throws Exception {
        try {
            persistence.createAuditRecord(null);
            fail("AuditConfigurationException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createAuditRecord(AuditHeader)</code> when the AuditHeader#ApplicationArea is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_NullAuditHeaderApplicationArea() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);
        auditHeader.setApplicationArea(null);

        try {
            persistence.createAuditRecord(auditHeader);
            fail("AuditConfigurationException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createAuditRecord(AuditHeader)</code> when the AuditHeader#TableName is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_NullAuditHeaderTableName() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);
        auditHeader.setTableName(null);

        try {
            persistence.createAuditRecord(auditHeader);
            fail("AuditConfigurationException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createAuditRecord(AuditHeader)</code> when the AuditHeader#CreationDate is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_NullAuditHeaderCreationDate() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);
        auditHeader.setCreationDate(null);

        try {
            persistence.createAuditRecord(auditHeader);
            fail("AuditConfigurationException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createAuditRecord(AuditHeader)</code> when the AuditHeader#CreationUser is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_NullAuditHeaderCreationUser() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);
        auditHeader.setCreationUser(null);

        try {
            persistence.createAuditRecord(auditHeader);
            fail("AuditConfigurationException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createAuditRecord(AuditHeader)</code> when the AuditHeader#AuditDetail is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_NullAuditHeaderAuditDetail() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);
        auditHeader.setDetails(new AuditDetail[] {null});

        try {
            persistence.createAuditRecord(auditHeader);
            fail("AuditConfigurationException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test the method of <code>createAuditRecord(AuditHeader)</code> when the AuditHeader#AuditDetail#ColumnName is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_NullAuditHeaderAuditDetailColumnName() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);
        auditHeader.getDetails()[0].setColumnName(null);

        try {
            persistence.createAuditRecord(auditHeader);
            fail("AuditConfigurationException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>createAuditRecord(AuditHeader)</code>. The persisted value should be the
     * same as the retrieved value.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_Accuracy1() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);

        // add it
        persistence.createAuditRecord(auditHeader);

        // get it and check it
        AuditHeader[] ret = persistence.searchAudit(new EqualToFilter("client id",
                    new Long(auditHeader.getClientId())));
        assertEquals("The audit header should be added properly.", 1, ret.length);
        UnitTestHelper.assertEquals(auditHeader, ret[0]);
    }

    /**
     * <p>
     * Accuracy test for the method of <code>createAuditRecord(AuditHeader)</code> with no audit details. The persisted
     * value should be the same as the retrieved value.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_Accuracy2() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(0);

        // add it
        persistence.createAuditRecord(auditHeader);

        // get it and check it
        AuditHeader[] ret = persistence.searchAudit(new EqualToFilter("client id",
                    new Long(auditHeader.getClientId())));
        assertEquals("The audit header should be added properly.", 1, ret.length);
        UnitTestHelper.assertEquals(auditHeader, ret[0]);
    }

    /**
     * <p>
     * Accuracy test for the method of <code>createAuditRecord(AuditHeader)</code> with no audit details. The persisted
     * value should be the same as the retrieved value.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_Accuracy3() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);
        auditHeader.setDetails(null);

        // add it
        persistence.createAuditRecord(auditHeader);

        // get it and check it
        AuditHeader[] ret = persistence.searchAudit(new EqualToFilter("client id",
                    new Long(auditHeader.getClientId())));
        assertEquals("The audit header should be added properly.", 1, ret.length);

        auditHeader.setDetails(new AuditDetail[0]);
        UnitTestHelper.assertEquals(auditHeader, ret[0]);
    }

    /**
     * <p>
     * Accuracy test for the method of <code>createAuditRecord(AuditHeader)</code> with some option values for audit
     * details are not set. The persisted value should be the same as the retrieved value, the default values for
     * audit details should be persisted.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuditRecord_Accuracy4() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);
        AuditDetail[] details = auditHeader.getDetails();

        for (int i = 0; i < details.length; i++) {
            if ((i % 2) == 0) {
                details[i].setOldValue(null);
            }

            if ((i % 2) == 1) {
                details[i].setNewValue(null);
            }
        }

        // add it
        persistence.createAuditRecord(auditHeader);

        // get it and check it
        AuditHeader[] ret = persistence.searchAudit(new EqualToFilter("client id",
                    new Long(auditHeader.getClientId())));
        assertEquals("The audit header should be added properly.", 1, ret.length);

        for (int i = 0; i < details.length; i++) {
            if ((i % 2) == 0) {
                // it is the default value for DefaultValuesContainer
                details[i].setOldValue("oldValue");
            }

            if ((i % 2) == 1) {
                // it is the default value for DefaultValuesContainer
                details[i].setNewValue("newValue");
            }
        }

        UnitTestHelper.assertEquals(auditHeader, ret[0]);
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is null. All the records
     * in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_NullAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            persistence.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = persistence.searchAudit(null);

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by resource id. All
     * the records for given resource id in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByResourceIdAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            persistence.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = persistence.searchAudit(new EqualToFilter("resource id",
                    new Long(auditHeaders[0].getResourceId())));

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by application area
     * id. All the records for given application area id in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByApplicationAreaIdAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            persistence.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = persistence.searchAudit(new EqualToFilter("application area id",
                    new Long(auditHeaders[0].getApplicationArea().getId())));

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by client id. All the
     * records for given client id in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByClientIdAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            persistence.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = persistence.searchAudit(new EqualToFilter("client id",
                    new Long(auditHeaders[0].getClientId())));

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by project id. All the
     * records for given project id in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByProjectIdAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            persistence.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        AuditHeader[] ret = persistence.searchAudit(new EqualToFilter("project id",
                    new Long(auditHeaders[0].getProjectId())));

        assertEquals("The audit header should be got properly.", auditHeaders.length, ret.length);

        for (int i = 0; i < auditHeaders.length; i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>searchAudit(Filter)</code> when the input Filter is by creation date. All
     * the records for given range of creation date in the table should be retrieved.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testSearchAudit_ByCreationDateAccuracy() throws Exception {
        AuditHeader[] auditHeaders = new AuditHeader[5];

        for (int i = 0; i < auditHeaders.length; i++) {
            auditHeaders[i] = UnitTestHelper.buildAuditHeader(i);
            persistence.createAuditRecord(auditHeaders[i]);
        }

        // get it and check it
        Filter filter = new AndFilter(new GreaterThanFilter("creation date", new Timestamp(1000)),
                new LessThanFilter("creation date", new Timestamp(5000)));
        AuditHeader[] ret = persistence.searchAudit(filter);

        assertEquals("The audit header should be got properly.", auditHeaders.length - 2, ret.length);

        for (int i = 1; i < (auditHeaders.length - 1); i++) {
            UnitTestHelper.assertEquals(auditHeaders[i], ret[i - 1]);
        }
    }

    /**
     * <p>
     * Accuracy test for the method of <code>rollbackAuditRecord(int)</code>. All the records in the table should be
     * removed under this audit id.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRollbackAuditRecord_Accuracy() throws Exception {
        AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);

        // add it
        persistence.createAuditRecord(auditHeader);

        // remove it
        boolean success = persistence.rollbackAuditRecord(auditHeader.getId());

        // validate
        assertEquals("There should be records in the audit table.", true, success);
        assertEquals("The records in table audit_detail should be removed.", 0,
            UnitTestHelper.getAuditDetailRecords(connection));
        assertEquals("The records in table audit should be removed.", 0, UnitTestHelper.getAuditRecords(connection));
    }

    /**
     * <p>
     * Accuracy test for the method of <code>rollbackAuditRecord(int)</code> when there is no record in the database.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRollbackAuditRecord_NoRecordAccuracy() throws Exception {
        // remove it
        boolean success = persistence.rollbackAuditRecord(1);

        // validate
        assertEquals("There should be no records in the audit table.", false, success);
    }
}
