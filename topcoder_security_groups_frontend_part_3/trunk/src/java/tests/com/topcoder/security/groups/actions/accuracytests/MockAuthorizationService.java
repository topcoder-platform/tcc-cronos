package com.topcoder.security.groups.actions.accuracytests;

import java.util.Arrays;
import java.util.List;

import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.model.ResourceType;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.SecurityGroupException;

public class MockAuthorizationService implements AuthorizationService {

	public GroupPermissionType checkAuthorization(long userId, long resourceId,
			ResourceType resourceType) throws SecurityGroupException {
		
		return null;
	}

	public List<Long> getGroupIdsOfFullPermissionsUser(long userId) {
		
		return Arrays.asList(new Long[]{1l, 2l});
	}

	public boolean isAdministrator(long userId) {		
		return false;
	}

	public boolean isCustomerAdministrator(long userId, long clientId)
			throws SecurityGroupException {
		
		return false;
	}

}
