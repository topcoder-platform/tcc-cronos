/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.model.AuditRecord;
/**
 * <p>
 * A simple accuracy mock of AuditDAO.
 * </p>
 *
 * @author Beijing2008
 * @version 1.0
 */
public class AuditDAOAccuracyMock implements AuditDAO {

    private static AuditRecord record;
    public AuditDAOAccuracyMock () {
        //clear the record
        record = null;
    }
    @Override
    public void audit(AuditRecord r) {
        record = r;
    }

    /**
     * @return the record
     */
    public static AuditRecord getRecord() {
        return record;
    }

    @Override
    public boolean hasOperation(String handle, String operationType) {
        return false;
    }

}
