/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.accuracytests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.PagedResult;

/**
 * <p>
 * Mock implementation of GroupAuditService used in accuracy test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockGroupAuditService implements GroupAuditService {
    /**
     * Represents all audit records saved.
     */
    public static final List<GroupAuditRecord> RECORDS = new ArrayList<GroupAuditRecord>();

    /**
     * <p>
     * add the record.
     * </p>
     *
     * @param record
     *             the record to update.
     */
    @Override
    public long add(GroupAuditRecord record) throws SecurityGroupException {
        record.setId(RECORDS.size() + 1);
        RECORDS.add(record);
        return record.getId();
    }


    /**
     * <p>
     * Mock implementation, does nothing.
     * </p>
     *
     * @param pageSize
     *             the page size.
     * @param page
     *             the page.
     */
    public PagedResult<GroupAuditRecord> searchAuditRecords(int pageSize, int page)
        throws SecurityGroupException {
        return null;
    }
}