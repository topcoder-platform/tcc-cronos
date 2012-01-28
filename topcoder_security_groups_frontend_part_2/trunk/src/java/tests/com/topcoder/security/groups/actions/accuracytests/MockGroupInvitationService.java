/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.accuracytests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * Mock implementation of GroupInvitationService used in accuracy test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockGroupInvitationService implements GroupInvitationService {
    /**
     * Represents the records ued in test. The key is id, the value is GroupInvitation.
     *
     * @param invitation
     *            not used
     */
    public static final Map<Long, GroupInvitation> RECORDS = new HashMap<Long, GroupInvitation>();

    /**
     * Just For testing.
     *
     * @param invitation
     *            not used
     */
    public void addInvitation(GroupInvitation invitation) {
        RECORDS.put(invitation.getId(), invitation);
    }

    /**
     * Just For testing.
     *
     * @param invitationId
     *            not used
     * @return null
     */
    public GroupInvitation getInvitation(long invitationId) {
        GroupInvitation gi = new GroupInvitation();
        gi.setId(invitationId);
        if (invitationId > 100) {
            gi.setStatus(InvitationStatus.PENDING);
            GroupMember groupMember = new GroupMember();
            groupMember.setId(invitationId);

            groupMember.setUserId(invitationId);
            gi.setGroupMember(groupMember);
        }
        return gi;
    }

    /**
     * Just For testing.
     *
     * @param criteria
     *            not used
     * @param clientId
     *            not used
     * @param pageSize
     *            not used
     * @param page
     *            not used
     * @return a new paged result.
     * @throws SecurityGroupException
     *             not used.
     */
    public PagedResult<GroupInvitation> search(InvitationSearchCriteria criteria, long clientId,
        int pageSize, int page) throws SecurityGroupException {

        PagedResult<GroupInvitation> pr = new PagedResult<GroupInvitation>();
        pr.setPage(page);
        pr.setTotal(100);
        pr.setValues(new ArrayList<GroupInvitation>(RECORDS.values()));

        if (criteria.getStatus() != InvitationStatus.APPROVAL_PENDING && page != 7) {
            throw new SecurityGroupException("");
        }

        return pr;
    }

    /**
     * Just For testing.
     *
     * @param invitation
     *            not used
     * @param registrationUrl
     *            not used
     * @param acceptanceUrl
     *            not used
     * @param rejectionUrl
     *            not used
     * @throws SecurityGroupException
     *             not used.
     */
    public void sendInvitation(GroupInvitation invitation, String registrationUrl,
        String acceptanceUrl, String rejectionUrl) throws SecurityGroupException {

    }

    /**
     * Just For testing.
     *
     * @param invitation
     *            not used
     * @throws EntityNotFoundException
     *             not used.
     * @throws SecurityGroupException
     *             not used.
     */
    public void updateInvitation(GroupInvitation invitation) throws EntityNotFoundException,
        SecurityGroupException {
        RECORDS.put(invitation.getId(), invitation);
    }
}