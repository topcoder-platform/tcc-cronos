/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.failuretests;

import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.model.AuditRecord;


/**
 * Mock class for failure tests.
 *
 * @author gjw99
 * @version 1.0
 */
public class MockAuditDAO implements AuditDAO {
    /**
     * Doing nothing.
     *
     * @param record record
     */
    public void audit(AuditRecord record) {
    }

    /**
     * Always return false.
     *
     * @param handle the handle
     * @param operationType the operation type
     *
     * @return DOCUMENT ME!
     */
    public boolean hasOperation(String handle, String operationType) {
        // TODO Auto-generated method stub
        return false;
    }
}
