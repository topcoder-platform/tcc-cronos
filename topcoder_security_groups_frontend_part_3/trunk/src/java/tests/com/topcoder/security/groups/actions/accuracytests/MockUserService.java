package com.topcoder.security.groups.actions.accuracytests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.UserDTO;

public class MockUserService implements UserService {

	public UserDTO get(long id) throws SecurityGroupException {
		return null;
	}

	public List<UserDTO> search(String handle) throws SecurityGroupException {
		List<UserDTO> users = new ArrayList<UserDTO>();
		UserDTO user = new UserDTO();
		user.setUserId(2);
		users.add(user);
		return users;
	}

}
