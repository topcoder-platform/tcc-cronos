package com.topcoder.security.groups.actions.failuretests;

import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

public class MockGroupInvitationService implements GroupInvitationService {

	public void addInvitation(GroupInvitation invitation) {
		

	}

	public GroupInvitation getInvitation(long invitationId) {
		
		return null;
	}

	public PagedResult<GroupInvitation> search(
			InvitationSearchCriteria criteria, long clientId, int pageSize,
			int page) throws SecurityGroupException {
		if (page == 2) {
			throw new SecurityGroupException("f");
		}
		return null;
	}

	public void sendInvitation(GroupInvitation invitation,
			String registrationUrl, String acceptanceUrl, String rejectionUrl)
			throws SecurityGroupException {
		

	}

	public void updateInvitation(GroupInvitation invitation)
			throws EntityNotFoundException, SecurityGroupException {
		

	}

}
