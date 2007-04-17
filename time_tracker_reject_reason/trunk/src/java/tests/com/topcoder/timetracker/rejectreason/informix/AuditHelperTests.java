/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.informix;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditType;

import junit.framework.TestCase;

import java.sql.Date;


/**
 * Unit test for <code>AuditHelper</code> class. Basic functionalities tests.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AuditHelperTests extends TestCase {
    /**
     * Tests createAdutiHeader method accuracy.
     */
    public void testCreateAdutiHeader_accuracy() {
        AuditDetail detail = new AuditDetail();
        Date now = new Date(new java.util.Date().getTime());
        AuditHeader header = DAOHelper.createAdutiHeader(AuditType.INSERT, "table", 111, 222,
                new AuditDetail[] {detail }, ApplicationArea.TT_CLIENT, now, "user");

        assertNotNull("The return value should not be null.", header);
        assertEquals("The action type is not set correctly.", AuditType.INSERT, header.getActionType());
        assertEquals("The table name is not set correctly.", "table", header.getTableName());
        assertEquals("The entity id is not set correctly.", 111, header.getEntityId());
        assertEquals("The company id is not set correctly.", 222, header.getCompanyId());
        assertEquals("The audit details is not set correctly.", 1, header.getDetails().length);
        assertEquals("The audit details is not set correctly.", detail, header.getDetails()[0]);
        assertEquals("The application area is not set correctly.", ApplicationArea.TT_CLIENT,
            header.getApplicationArea());
        assertEquals("The creation date is not set correctly.", now, header.getCreationDate());
        assertEquals("The creation user is not set correctly.", "user", header.getCreationUser());
    }

    /**
     * Tests createAuditDetail method accuracy.
     */
    public void testCreateAuditDetail_accuracy() {
        AuditDetail detail = DAOHelper.createAuditDetail("columnName", "oldValue", "newValue");

        assertNotNull("The return value should not be null.", detail);
        assertEquals("The column name is not set correctly.", "columnName", detail.getColumnName());
        assertEquals("The old value is not set correctly.", "oldValue", detail.getOldValue());
        assertEquals("The new value is not set correctly.", "newValue", detail.getNewValue());
    }
}