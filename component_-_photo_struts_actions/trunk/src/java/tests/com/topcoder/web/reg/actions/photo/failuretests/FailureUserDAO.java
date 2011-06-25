package com.topcoder.web.reg.actions.photo.failuretests;

import java.util.List;

import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserProfile;

public class FailureUserDAO implements UserDAO {

    public User find(Long id) {
        User user = new User();
        user.setFirstName("user1");
        user.setUserProfile(new UserProfile());
        return user ;
    }

    public User find(String userName, boolean ignoreCase) {
        return null;
    }

    public User find(String userName, boolean ignoreCase, boolean activeRequired) {
        return null;
    }

    public List<?> find(String handle, String firstName, String lastName,
            String email) {
        return null;
    }

    public void saveOrUpdate(User u) {

    }

}
