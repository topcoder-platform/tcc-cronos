/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock;

import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.model.AuditRecord;

/**
 * <p>
 * Mock AuditDAO class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockAuditDao implements AuditDAO {
    /**
     * Empty.
     *
     * @param record
     *            the record
     */
    public void audit(AuditRecord record) {
    }

    /**
     * Empty.
     *
     * @param handle
     *            the handle
     * @param operationType
     *            the operation type.
     * @return false.
     */
    public boolean hasOperation(String handle, String operationType) {
        return false;
    }

}