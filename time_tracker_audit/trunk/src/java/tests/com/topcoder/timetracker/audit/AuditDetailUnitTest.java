/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>AuditDetail</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AuditDetailUnitTest extends TestCase {
    /** Represents the <code>AuditDetail</code> instance used for testing. */
    private AuditDetail auditDetail = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        auditDetail = new AuditDetail();
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>AuditDetail()</code>.
     * </p>
     */
    public void testAuditDetail_Accuracy() {
        assertNotNull("The AuditDetail instance should be created.", auditDetail);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code>, should return the default value.
     * </p>
     */
    public void testGetId_Default_Accuracy() {
        assertEquals("The id value should be got properly.", -1, auditDetail.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code>, should return the set value.
     * </p>
     */
    public void testGetId_Accuracy() {
        int id = 1;
        UnitTestHelper.setPrivateField(AuditDetail.class, auditDetail, "id", new Integer(id));
        assertEquals("The id value should be got properly.", id, auditDetail.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setId(int)</code>.
     * </p>
     */
    public void testSetId_Accuracy() {
        int id = 1;
        auditDetail.setId(id);
        assertEquals("The id value should be set properly.", "" + id,
            UnitTestHelper.getPrivateField(AuditDetail.class, auditDetail, "id").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getNewValue()</code>, should return the default value.
     * </p>
     */
    public void testGetNewValue_Default_Accuracy() {
        assertEquals("The newValue value should be got properly.", null, auditDetail.getNewValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getNewValue()</code>, should return the set value.
     * </p>
     */
    public void testGetNewValue_Accuracy() {
        String newValue = "newValue";
        UnitTestHelper.setPrivateField(AuditDetail.class, auditDetail, "newValue", newValue);
        assertEquals("The newValue value should be got properly.", newValue, auditDetail.getNewValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setNewValue(String)</code>.
     * </p>
     */
    public void testSetNewValue_Accuracy() {
        String newValue = "newValue";
        auditDetail.setNewValue(newValue);
        assertEquals("The newValue value should be set properly.", newValue,
            UnitTestHelper.getPrivateField(AuditDetail.class, auditDetail, "newValue").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOldValue()</code>, should return the default value.
     * </p>
     */
    public void testGetOldValue_Default_Accuracy() {
        assertEquals("The oldValue value should be got properly.", null, auditDetail.getOldValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOldValue()</code>, should return the set value.
     * </p>
     */
    public void testGetOldValue_Accuracy() {
        String oldValue = "oldValue";
        UnitTestHelper.setPrivateField(AuditDetail.class, auditDetail, "oldValue", oldValue);
        assertEquals("The oldValue value should be got properly.", oldValue, auditDetail.getOldValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setOldValue(String)</code>.
     * </p>
     */
    public void testSetOldValue_Accuracy() {
        String oldValue = "oldValue";
        auditDetail.setOldValue(oldValue);
        assertEquals("The oldValue value should be set properly.", oldValue,
            UnitTestHelper.getPrivateField(AuditDetail.class, auditDetail, "oldValue").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getColumnName()</code>, should return the default value.
     * </p>
     */
    public void testGetColumnName_Default_Accuracy() {
        assertEquals("The columnName value should be got properly.", null, auditDetail.getColumnName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getColumnName()</code>, should return the set value.
     * </p>
     */
    public void testGetColumnName_Accuracy() {
        String columnName = "columnName";
        UnitTestHelper.setPrivateField(AuditDetail.class, auditDetail, "columnName", columnName);
        assertEquals("The columnName value should be got properly.", columnName, auditDetail.getColumnName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setColumnName(String)</code>.
     * </p>
     */
    public void testSetColumnName_Accuracy() {
        String columnName = "columnName";
        auditDetail.setColumnName(columnName);
        assertEquals("The columnName value should be set properly.", columnName,
            UnitTestHelper.getPrivateField(AuditDetail.class, auditDetail, "columnName").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>isPersisted()</code>, should return the default value.
     * </p>
     */
    public void testIsPersisted_Default_Accuracy() {
        assertEquals("The persisted value should be got properly.", false, auditDetail.isPersisted());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>isPersisted()</code>, should return the set value.
     * </p>
     */
    public void testIsPersisted_Accuracy() {
        UnitTestHelper.setPrivateField(AuditDetail.class, auditDetail, "persisted", new Boolean(true));
        assertEquals("The persisted value should be got properly.", true, auditDetail.isPersisted());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setPersisted(boolean)</code>.
     * </p>
     */
    public void testSetPersisted_Accuracy() {
        auditDetail.setPersisted(true);
        assertEquals("The persisted value should be set properly.", true,
            Boolean.valueOf(UnitTestHelper.getPrivateField(AuditDetail.class, auditDetail, "persisted").toString())
                   .booleanValue());
    }
}
