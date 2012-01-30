package com.topcoder.security.groups.actions.accuracytests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

public class MockGroupService implements GroupService {

	public long add(Group group) throws SecurityGroupException {
		
		return 0;
	}

	public void delete(long id) throws EntityNotFoundException,
			SecurityGroupException {
		

	}

	public Group get(long id) throws SecurityGroupException {
		Group g = new Group();
		List<GroupMember> groupMembers = new ArrayList<GroupMember>();
		GroupMember member = new GroupMember();
		member.setUserId(2l);
		g.setGroupMembers(groupMembers);
		return g;
	}

	public PagedResult<Group> search(GroupSearchCriteria criteria,
			int pageSize, int page) throws SecurityGroupException {
		PagedResult<Group> result = new PagedResult<Group>();
		List<Group> values = new ArrayList<Group>();
		Group g = new Group();
		List<GroupMember> groupMembers = new ArrayList<GroupMember>();
		GroupMember member = new GroupMember();
		member.setUserId(2l);
		g.setGroupMembers(groupMembers);
		values.add(g);
		result.setValues(values);
		return result;
	}

	public void update(Group group) throws EntityNotFoundException,
			SecurityGroupException {
		

	}

}
