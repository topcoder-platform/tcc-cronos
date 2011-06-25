package com.topcoder.web.reg.actions.photo.failuretests;

import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.model.AuditRecord;

public class FailureAuditDao implements AuditDAO {

    public void audit(AuditRecord record) {
    }

    public boolean hasOperation(String handle, String operationType) {
        // TODO Auto-generated method stub
        return false;
    }

}
