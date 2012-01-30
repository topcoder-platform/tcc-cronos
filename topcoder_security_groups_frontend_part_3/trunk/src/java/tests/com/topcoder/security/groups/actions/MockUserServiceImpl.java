/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * Mock service implementation class for the demo usage.
 *
 * @author progloco
 * @version 1.0
 */
public class MockUserServiceImpl implements UserService {

    /**
     * <p>
     * This method gets a user. If not found, returns null.
     * </p>
     *
     * @param id the ID fo the user to be retrieved.
     * @throws SecurityGroupException If there are any errors during the
     *             execution of this method.
     * @return the requested UserDTO
     */
    public UserDTO get(long id) throws SecurityGroupException {
        return null;
    }

    /**
     * <p>
     * This method retrieves the list of users with the given handle. If not
     * found, returns an empty list.
     * </p>
     *
     * @param handle the handle of the desired user.
     * @throws IllegalArgumentException If handle is null/empty.
     * @throws SecurityGroupException If there are any errors during the
     *             execution of this method.
     * @return the users with the given handle
     */
    public List<UserDTO> search(String handle) throws SecurityGroupException {
        List<UserDTO> users = new ArrayList<UserDTO>();

        if ("gevak".equals(handle)) {
            UserDTO user = new UserDTO();
            user.setUserId(10);
            user.setHandle(handle);
            users.add(user);
        }
        return users;
    }

}
