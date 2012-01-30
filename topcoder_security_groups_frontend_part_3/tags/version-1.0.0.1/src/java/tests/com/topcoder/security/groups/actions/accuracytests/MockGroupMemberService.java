package com.topcoder.security.groups.actions.accuracytests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.GroupMemberAccessHistoricalData;
import com.topcoder.security.groups.services.dto.GroupMemberSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

public class MockGroupMemberService implements GroupMemberService {

	public PagedResult<GroupMemberAccessHistoricalData> searchHistoricalData(
			GroupMemberSearchCriteria criteria, int pageSize, int page)
			throws SecurityGroupException {
		PagedResult<GroupMemberAccessHistoricalData> result = new PagedResult<GroupMemberAccessHistoricalData>();
		List<GroupMemberAccessHistoricalData> list = new ArrayList<GroupMemberAccessHistoricalData>();
		GroupMemberAccessHistoricalData data = new GroupMemberAccessHistoricalData();
		data.setDirectProjectIds(Arrays.asList(new Long[]{1l, 2l}));
		list.add(data);
		result.setValues(list);
		return result;
	}

	public void update(GroupMember member) throws EntityNotFoundException,
			SecurityGroupException {
		

	}

}
