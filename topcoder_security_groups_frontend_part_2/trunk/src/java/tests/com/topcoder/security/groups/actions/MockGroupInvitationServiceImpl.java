/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

/**
 * <p>
 * Mock implementation for the service {@link GroupInvitationService}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockGroupInvitationServiceImpl implements GroupInvitationService {

    /**
     * Just For testing.
     *
     * @param invitation not used
     */
    public void addInvitation(GroupInvitation invitation) {

    }

    /**
     * Just For testing.
     *
     * @param invitationId not used
     * @return null
     */
    public GroupInvitation getInvitation(long invitationId) {
        return null;
    }

    /**
     * Just For testing.
     *
     * @param criteria not used
     * @param clientId not used
     * @param pageSize not used
     * @param page not used
     * @return a new paged result.
     * @throws SecurityGroupException not used.
     */
    public PagedResult<GroupInvitation> search(
            InvitationSearchCriteria criteria, long clientId, int pageSize,
            int page) throws SecurityGroupException {

        return new PagedResult<GroupInvitation>();
    }

    /**
     * Just For testing.
     *
     * @param invitation not used
     * @param registrationUrl not used
     * @param acceptanceUrl not used
     * @param rejectionUrl not used
     * @throws SecurityGroupException not used.
     */
    public void sendInvitation(GroupInvitation invitation,
            String registrationUrl, String acceptanceUrl, String rejectionUrl)
        throws SecurityGroupException {

    }

    /**
     * Just For testing.
     *
     * @param invitation not used
     * @throws EntityNotFoundException not used.
     * @throws SecurityGroupException not used.
     */
    public void updateInvitation(GroupInvitation invitation)
        throws EntityNotFoundException, SecurityGroupException {

    }

}
