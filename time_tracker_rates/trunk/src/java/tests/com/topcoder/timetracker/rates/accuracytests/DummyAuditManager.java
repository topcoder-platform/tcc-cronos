/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.accuracytests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;

/**
 * A mock up class of AuditManager for accuracy tests.
 *
 * @author kinfkong
 * @version 3.2
 *
 */
public class DummyAuditManager implements AuditManager {

    /**
     * Mock up implementation for the method: createAuditRecord.
     */
    public void createAuditRecord(AuditHeader arg0) throws AuditManagerException {
        // TODO Auto-generated method stub

    }

    /**
     * Mock up implementation for the method: rollbackAuditRecord.
     */
    public boolean rollbackAuditRecord(long arg0) throws AuditManagerException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Mock up implementation for the method: searchAudit.
     */
    public AuditHeader[] searchAudit(Filter arg0) throws AuditManagerException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean rollbackAuditRecord(int auditHeaderId) throws AuditManagerException {
        // TODO Auto-generated method stub
        return false;
    }

}
