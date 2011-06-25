/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo.accuracytests;

import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.model.AuditRecord;

/**
 * Mock class for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockAuditDAO implements AuditDAO {

    /**
     * Mock method.
     */
    public void audit(AuditRecord record) {
    }

    /**
     * Mock method.
     */
    public boolean hasOperation(String handle, String operationType) {
        return false;
    }

}
