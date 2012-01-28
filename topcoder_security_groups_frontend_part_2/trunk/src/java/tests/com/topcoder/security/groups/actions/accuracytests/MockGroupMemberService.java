/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.accuracytests;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.GroupMemberAccessHistoricalData;
import com.topcoder.security.groups.services.dto.GroupMemberSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

/**
 * <p>
 * Mock implementation of GroupMemberService used in accuracy test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockGroupMemberService implements GroupMemberService {
    /**
     * Represents all GroupMember records used. The key is id, the value is GroupMember.
     */
    static final Map<Long, GroupMember> RECORDS = new HashMap<Long, GroupMember>();


    /**
     * <p>
     * Updates the member.
     * </p>
     *
     * @param member
     *             the member to update.
     */
    @Test
    public void update(GroupMember member) throws EntityNotFoundException, SecurityGroupException {
        RECORDS.put(member.getId(), member);

    }

    /**
     * <p>
     * Mock implementation, does nothing.
     * </p>
     *
     * @param criteria
     *             the criteria.
     * @param pageSize
     *             the page size.
     * @param page
     *             the page.
     */
    public PagedResult<GroupMemberAccessHistoricalData> searchHistoricalData(
        GroupMemberSearchCriteria criteria, int pageSize, int page) throws SecurityGroupException {
        return null;
    }
}