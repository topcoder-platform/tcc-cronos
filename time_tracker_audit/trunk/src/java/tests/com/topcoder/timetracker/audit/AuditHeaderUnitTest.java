/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import junit.framework.TestCase;

import java.sql.Timestamp;

import java.util.Date;


/**
 * <p>
 * Tests functionality and error cases of <code>AuditHeader</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AuditHeaderUnitTest extends TestCase {
    /** Represents the <code>AuditHeader</code> instance used for testing. */
    private AuditHeader auditHeader = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        auditHeader = new AuditHeader();
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>AuditHeader()</code>.
     * </p>
     */
    public void testAuditHeader_Accuracy() {
        assertNotNull("The AuditHeader instance should be created.", auditHeader);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code>, should return the default value.
     * </p>
     */
    public void testGetId_Default_Accuracy() {
        assertEquals("The id value should be got properly.", -1, auditHeader.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code>, should return the set value.
     * </p>
     */
    public void testGetId_Accuracy() {
        int id = 1;
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "id", new Integer(id));
        assertEquals("The id value should be got properly.", id, auditHeader.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setId(int)</code>.
     * </p>
     */
    public void testSetId_Accuracy() {
        int id = 1;
        auditHeader.setId(id);
        assertEquals("The id value should be set properly.", "" + id,
            UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "id").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getEntityId()</code>, should return the default value.
     * </p>
     */
    public void testGetEntityId_Default_Accuracy() {
        assertEquals("The entityId value should be got properly.", -1, auditHeader.getEntityId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getEntityId()</code>, should return the set value.
     * </p>
     */
    public void testGetEntityId_Accuracy() {
        int entityId = 1;
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "entityId", new Integer(entityId));
        assertEquals("The entityId value should be got properly.", entityId, auditHeader.getEntityId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setEntityId(int)</code>.
     * </p>
     */
    public void testSetEntityId_Accuracy() {
        int entityId = 1;
        auditHeader.setEntityId(entityId);
        assertEquals("The entityId value should be set properly.", "" + entityId,
            UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "entityId").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCreationDate()</code>, should return the default value.
     * </p>
     */
    public void testGetCreationDate_Default_Accuracy() {
        assertEquals("The creationDate value should be got properly.", null, auditHeader.getCreationDate());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCreationDate()</code>, should return the set value.
     * </p>
     */
    public void testGetCreationDate_Accuracy() {
        Timestamp creationDate = new Timestamp(new Date().getTime());
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "creationDate", creationDate);
        assertEquals("The creationDate value should be got properly.", creationDate, auditHeader.getCreationDate());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setCreationDate(Timestamp)</code>.
     * </p>
     */
    public void testSetCreationDate_Accuracy() {
        Timestamp creationDate = new Timestamp(new Date().getTime());
        auditHeader.setCreationDate(creationDate);
        assertEquals("The creationDate value should be set properly.", creationDate,
            UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "creationDate"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getTableName()</code>, should return the default value.
     * </p>
     */
    public void testGetTableName_Default_Accuracy() {
        assertEquals("The tableName value should be got properly.", null, auditHeader.getTableName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getTableName()</code>, should return the set value.
     * </p>
     */
    public void testGetTableName_Accuracy() {
        String tableName = "tableName";
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "tableName", tableName);
        assertEquals("The tableName value should be got properly.", tableName, auditHeader.getTableName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setTableName(String)</code>.
     * </p>
     */
    public void testSetTableName_Accuracy() {
        String tableName = "tableName";
        auditHeader.setTableName(tableName);
        assertEquals("The tableName value should be set properly.", tableName,
            UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "tableName").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCompanyId()</code>, should return the default value.
     * </p>
     */
    public void testGetCompanyId_Default_Accuracy() {
        assertEquals("The companyId value should be got properly.", -1, auditHeader.getCompanyId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCompanyId()</code>, should return the set value.
     * </p>
     */
    public void testGetCompanyId_Accuracy() {
        int companyId = 1;
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "companyId", new Integer(companyId));
        assertEquals("The companyId value should be got properly.", companyId, auditHeader.getCompanyId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setCompanyId(int)</code>.
     * </p>
     */
    public void testSetCompanyId_Accuracy() {
        int companyId = 1;
        auditHeader.setCompanyId(companyId);
        assertEquals("The companyId value should be set properly.", "" + companyId,
            UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "companyId").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCreationUser()</code>, should return the default value.
     * </p>
     */
    public void testGetCreationUser_Default_Accuracy() {
        assertEquals("The creationUser value should be got properly.", null, auditHeader.getCreationUser());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCreationUser()</code>, should return the set value.
     * </p>
     */
    public void testGetCreationUser_Accuracy() {
        String creationUser = "creationUser";
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "creationUser", creationUser);
        assertEquals("The creationUser value should be got properly.", creationUser, auditHeader.getCreationUser());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setCreationUser(String)</code>.
     * </p>
     */
    public void testSetCreationUser_Accuracy() {
        String creationUser = "creationUser";
        auditHeader.setCreationUser(creationUser);
        assertEquals("The creationUser value should be set properly.", creationUser,
            UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "creationUser").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getActionType()</code>, should return the default value.
     * </p>
     */
    public void testGetActionType_Default_Accuracy() {
        assertEquals("The actionType value should be got properly.", -1, auditHeader.getActionType());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getActionType()</code>, should return the set value.
     * </p>
     */
    public void testGetActionType_Accuracy() {
        int actionType = 1;
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "actionType", new Integer(actionType));
        assertEquals("The actionType value should be got properly.", actionType, auditHeader.getActionType());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setActionType(int)</code>.
     * </p>
     */
    public void testSetActionType_Accuracy() {
        int actionType = 1;
        auditHeader.setActionType(actionType);
        assertEquals("The actionType value should be set properly.", "" + actionType,
            UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "actionType").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getClientId()</code>, should return the default value.
     * </p>
     */
    public void testGetClientId_Default_Accuracy() {
        assertEquals("The clientId value should be got properly.", -1, auditHeader.getClientId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getClientId()</code>, should return the set value.
     * </p>
     */
    public void testGetClientId_Accuracy() {
        int clientId = 1;
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "clientId", new Integer(clientId));
        assertEquals("The clientId value should be got properly.", clientId, auditHeader.getClientId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setClientId(int)</code>.
     * </p>
     */
    public void testSetClientId_Accuracy() {
        int clientId = 1;
        auditHeader.setClientId(clientId);
        assertEquals("The clientId value should be set properly.", "" + clientId,
            UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "clientId").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getProjectId()</code>, should return the default value.
     * </p>
     */
    public void testGetProjectId_Default_Accuracy() {
        assertEquals("The projectId value should be got properly.", -1, auditHeader.getProjectId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getProjectId()</code>, should return the set value.
     * </p>
     */
    public void testGetProjectId_Accuracy() {
        int projectId = 1;
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "projectId", new Integer(projectId));
        assertEquals("The projectId value should be got properly.", projectId, auditHeader.getProjectId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setProjectId(int)</code>.
     * </p>
     */
    public void testSetProjectId_Accuracy() {
        int projectId = 1;
        auditHeader.setProjectId(projectId);
        assertEquals("The projectId value should be set properly.", "" + projectId,
            UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "projectId").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getResourceId()</code>, should return the default value.
     * </p>
     */
    public void testGetResourceId_Default_Accuracy() {
        assertEquals("The resourceId value should be got properly.", -1, auditHeader.getResourceId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getResourceId()</code>, should return the set value.
     * </p>
     */
    public void testGetResourceId_Accuracy() {
        int resourceId = 1;
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "resourceId", new Integer(resourceId));
        assertEquals("The resourceId value should be got properly.", resourceId, auditHeader.getResourceId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setResourceId(int)</code>.
     * </p>
     */
    public void testSetResourceId_Accuracy() {
        int resourceId = 1;
        auditHeader.setResourceId(resourceId);
        assertEquals("The resourceId value should be set properly.", "" + resourceId,
            UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "resourceId").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getClientName()</code>, should return the default value.
     * </p>
     */
    public void testGetClientName_Default_Accuracy() {
        assertEquals("The clientName value should be got properly.", null, auditHeader.getClientName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getClientName()</code>, should return the set value.
     * </p>
     */
    public void testGetClientName_Accuracy() {
        String clientName = "clientName";
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "clientName", clientName);
        assertEquals("The clientName value should be got properly.", clientName, auditHeader.getClientName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setClientName(String)</code>.
     * </p>
     */
    public void testSetClientName_Accuracy() {
        String clientName = "clientName";
        auditHeader.setClientName(clientName);
        assertEquals("The clientName value should be set properly.", clientName,
            UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "clientName").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getProjectName()</code>, should return the default value.
     * </p>
     */
    public void testGetProjectName_Default_Accuracy() {
        assertEquals("The projectName value should be got properly.", null, auditHeader.getProjectName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getProjectName()</code>, should return the set value.
     * </p>
     */
    public void testGetProjectName_Accuracy() {
        String projectName = "projectName";
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "projectName", projectName);
        assertEquals("The projectName value should be got properly.", projectName, auditHeader.getProjectName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setProjectName(String)</code>.
     * </p>
     */
    public void testSetProjectName_Accuracy() {
        String projectName = "projectName";
        auditHeader.setProjectName(projectName);
        assertEquals("The projectName value should be set properly.", projectName,
            UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "projectName").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getDetails()</code>, should return the default value.
     * </p>
     */
    public void testGetDetails_Default_Accuracy() {
        assertEquals("The details value should be got properly.", null, auditHeader.getDetails());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getDetails()</code>, should return the set value.
     * </p>
     */
    public void testGetDetails_Accuracy() {
        AuditDetail[] details = new AuditDetail[0];
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "details", details);
        assertEquals("The details value should be got properly.", details, auditHeader.getDetails());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setDetails(AuditDetail[])</code>.
     * </p>
     */
    public void testSetDetails_Accuracy() {
        AuditDetail[] details = new AuditDetail[0];
        auditHeader.setDetails(details);
        assertEquals("The details value should be set properly.", details,
            UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "details"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getApplicationArea()</code>, should return the default value.
     * </p>
     */
    public void testGetApplicationArea_Default_Accuracy() {
        assertEquals("The applicationArea value should be got properly.", null, auditHeader.getApplicationArea());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getApplicationArea()</code>, should return the set value.
     * </p>
     */
    public void testGetApplicationArea_Accuracy() {
        ApplicationArea applicationArea = ApplicationArea.TT_CLIENT;
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "applicationArea", applicationArea);
        assertEquals("The applicationArea value should be got properly.", applicationArea,
            auditHeader.getApplicationArea());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setApplicationArea(ApplicationArea)</code>.
     * </p>
     */
    public void testSetApplicationArea_Accuracy() {
        ApplicationArea applicationArea = ApplicationArea.TT_CLIENT;
        auditHeader.setApplicationArea(applicationArea);
        assertEquals("The applicationArea value should be set properly.", applicationArea,
            UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "applicationArea"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>isPersisted()</code>, should return the default value.
     * </p>
     */
    public void testIsPersisted_Default_Accuracy() {
        assertEquals("The persisted value should be got properly.", false, auditHeader.isPersisted());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>isPersisted()</code>, should return the set value.
     * </p>
     */
    public void testIsPersisted_Accuracy() {
        UnitTestHelper.setPrivateField(AuditHeader.class, auditHeader, "persisted", new Boolean(true));
        assertEquals("The persisted value should be got properly.", true, auditHeader.isPersisted());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setPersisted(boolean)</code>.
     * </p>
     */
    public void testSetPersisted_Accuracy() {
        auditHeader.setPersisted(true);
        assertEquals("The persisted value should be set properly.", true,
            Boolean.valueOf(UnitTestHelper.getPrivateField(AuditHeader.class, auditHeader, "persisted").toString())
                   .booleanValue());
    }
}
