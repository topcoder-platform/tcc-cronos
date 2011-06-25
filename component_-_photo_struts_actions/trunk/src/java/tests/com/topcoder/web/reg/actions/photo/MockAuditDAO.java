/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.model.AuditRecord;
/**
 * <p>
 * A simple mock of {@link AuditDAO}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class MockAuditDAO implements AuditDAO {
    /**
     * Does nothing.
     * @param record the record
     */
    public void audit(AuditRecord record) {
    }
    /**
     * Does nothing.
     * @param handle not used
     * @param operationType not used
     * @return false.
     */
    public boolean hasOperation(String handle, String operationType) {
        return false;
    }

}
