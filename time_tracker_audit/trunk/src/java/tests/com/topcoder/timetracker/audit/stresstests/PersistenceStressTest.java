/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.stresstests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.UnitTestHelper;
import com.topcoder.timetracker.audit.persistence.InformixAuditPersistence;

import junit.framework.TestCase;

/**
 * <p>
 * This class is the stress test for Time Tracker Notification 3.2.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class PersistenceStressTest extends TestCase {

    /**
     * The looping count for testing
     */
    private static final int COUNT = 20;

    /**
     * The start time for the stress test.
     */
    private long startTime = 0;

    /**
     * The end time for the stress test.
     */
    private long endTime = 0;

    /**
     * Represents the namespace for the stress test.
     */
    private static final String NAME_SPACE = "com.topcoder.timetracker.audit.persistence.InformixAuditPersistence";

    /**
     * Represents the connection instance for testing.
     */
    private Connection connection = null;

    /**
     * Represents the <code>InformixAuditPersistence</code> instance used for testing.
     */
    private InformixAuditPersistence persistence;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        StressTestHelper.loadConfig();
        DBConnectionFactory factory = new DBConnectionFactoryImpl(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        connection = factory.createConnection();
        StressTestHelper.initData(connection);

    }

    /**
     * <p>
     * Clears the test environment.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        StressTestHelper.clearConfig();
        StressTestHelper.clearDatabase(connection);
    }

    /**
     * <p>
     * This method tests the functionality of creating new persistence instance in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testPersistenceStressForCreatingNewInstance() throws Exception {
        startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            persistence = new InformixAuditPersistence(NAME_SPACE);
            assertNotNull("The InformixAuditPersistence instance should be created.", persistence);
        }

        endTime = System.currentTimeMillis();
        System.out.println("The stress test for creating new persistence instance costs: " + (endTime - startTime)
            + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of searching persistence record in high stress.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testPersistenceStressForSearchingRecord() throws Exception {
        persistence = new InformixAuditPersistence(NAME_SPACE);
        startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            AuditHeader auditHeader = new AuditHeader();
            auditHeader.setActionType(1);
            auditHeader.setApplicationArea(ApplicationArea.TT_COMPANY);
            auditHeader.setClientId(2);
            auditHeader.setClientName("client2");
            auditHeader.setCompanyId(3);
            auditHeader.setCreationDate(new Timestamp((5 + 1) * 1000));
            auditHeader.setCreationUser("createUser");

            AuditDetail[] details = new AuditDetail[5];

            for (int j = 0; j < details.length; j++) {
                details[j] = new AuditDetail();
                details[j].setColumnName("columnName" + j);
                details[j].setNewValue("newValue" + j);
                details[j].setOldValue("oldValue" + j);
            }

            auditHeader.setDetails(details);
            auditHeader.setEntityId(4);
            auditHeader.setProjectId(5);
            auditHeader.setProjectName("project5");
            auditHeader.setResourceId(6);
            auditHeader.setTableName("tableName");

            persistence.createAuditRecord(auditHeader);

            AuditHeader[] ret = persistence.searchAudit(new EqualToFilter("client id", new Long(auditHeader
                .getClientId())));

            // note that: the length of searching result is equal to the loop counts.
            assertEquals("The audit header should be added properly.", i + 1, ret.length);

            // compare each result with the initial audit header
            for (int j = 0; j < ret.length; ++j) {
                AuditHeader expected = auditHeader;
                AuditHeader actual = ret[j];
                assertEquals("The entityId should be correct.", expected.getEntityId(), actual.getEntityId());
                assertEquals("The creationDate should be correct.", expected.getCreationDate(), actual
                    .getCreationDate());
                assertEquals("The tableName should be correct.", expected.getTableName(), actual.getTableName());
                assertEquals("The companyId should be correct.", expected.getCompanyId(), actual.getCompanyId());
                assertEquals("The creationUser should be correct.", expected.getCreationUser(), actual
                    .getCreationUser());
                assertEquals("The actionType should be correct.", expected.getActionType(), actual.getActionType());
                assertEquals("The clientId should be correct.", expected.getClientId(), actual.getClientId());
                assertEquals("The projectId should be correct.", expected.getProjectId(), actual.getProjectId());
                assertEquals("The resourceId should be correct.", expected.getResourceId(), actual.getResourceId());
                assertEquals("The clientName should be correct.", expected.getClientName(), actual.getClientName());
                assertEquals("The projectName should be correct.", expected.getProjectName(), actual
                    .getProjectName());
                assertEquals("The applicationArea should be correct.", expected.getApplicationArea(), actual
                    .getApplicationArea());
                assertEquals("The persisted should be correct.", expected.isPersisted(), actual.isPersisted());

                assertEquals("The details' count should be correct.", expected.getDetails().length, actual
                    .getDetails().length);
            }
        }

        endTime = System.currentTimeMillis();
        System.out.println("The stress test for searching record costs: " + (endTime - startTime)
            + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the functionality of rolling back in high stress.
     * </p>
     *
     * @throws Exception any exception to JUnit.
     */
    public void testRollbackAuditRecord_Accuracy() throws Exception {
        persistence = new InformixAuditPersistence(NAME_SPACE);
        startTime = System.currentTimeMillis();

        for (int i = 0; i < COUNT; ++i) {
            AuditHeader auditHeader = UnitTestHelper.buildAuditHeader(5);

            persistence.createAuditRecord(auditHeader);

            boolean result = persistence.rollbackAuditRecord(auditHeader.getId());

            assertEquals("There should be records in the audit table.", true, result);

            Statement statement = connection.createStatement();
            ResultSet rs = null;
            int cnt1 = -1;
            int cnt2 = -1;
            try {
                rs = statement.executeQuery("select count(*) FROM " + "audit_detail");
                rs.next();

                cnt1 = rs.getInt(1);
            } finally {
                rs.close();
                statement.close();
            }

            statement = connection.createStatement();
            rs = null;
            try {
                rs = statement.executeQuery("select count(*) FROM " + "audit");
                rs.next();

                cnt2 = rs.getInt(1);
            } finally {
                rs.close();
                statement.close();
            }

            assertEquals("The records in table audit_detail.", 0, cnt1);
            assertEquals("The records in table audit should be removed.", 0, cnt2);
        }

        endTime = System.currentTimeMillis();
        System.out.println("The stress test for rolling back costs: " + (endTime - startTime) + " milliseconds.");
    }
}
