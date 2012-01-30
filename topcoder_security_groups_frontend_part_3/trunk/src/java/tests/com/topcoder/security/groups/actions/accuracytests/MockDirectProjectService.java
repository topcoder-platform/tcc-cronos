package com.topcoder.security.groups.actions.accuracytests;

import java.util.List;

import com.topcoder.security.groups.services.DirectProjectService;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.ProjectDTO;

public class MockDirectProjectService implements DirectProjectService {

	public ProjectDTO get(long id) throws SecurityGroupException {
		return new ProjectDTO();
	}

	public List<ProjectDTO> getProjectsByClientId(long id)
			throws EntityNotFoundException, SecurityGroupException {
		return null;
	}

}
