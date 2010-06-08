package com.topcoder.service.facade.direct.stresstests;

import com.topcoder.service.user.User;
import com.topcoder.service.user.UserInfo;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;

import java.util.Date;

public class MockUserService implements UserService {

    public MockUserService() {
    }

    public void addUserTerm(String arg0, long arg1, Date arg2) throws UserServiceException {
        // TODO Auto-generated method stub

    }

    public void addUserTerm(long arg0, long arg1, Date arg2) throws UserServiceException {
        // TODO Auto-generated method stub

    }

    public void addUserToGroups(String arg0, long[] arg1) throws UserServiceException {
        // TODO Auto-generated method stub

    }

    public void addUserToGroups(long arg0, long[] arg1) throws UserServiceException {
        // TODO Auto-generated method stub

    }

    public String getEmailAddress(long arg0) throws UserServiceException {
        return "test@mydomain.com";
    }

    public String getEmailAddress(String arg0) throws UserServiceException {
        return "test@mydomain.com";
    }

    public User getUser(long arg0) {
        User user = new User();
        user.setPhone("1234567890");
        return user;
    }

    public String getUserHandle(long arg0) throws UserServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public long getUserId(String arg0) throws UserServiceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public UserInfo getUserInfo(String arg0) throws UserServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isAdmin(String arg0) throws UserServiceException {
        // TODO Auto-generated method stub
        return false;
    }

    public long registerUser(User arg0) throws UserServiceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public void removeUserFromGroups(String arg0, long[] arg1) throws UserServiceException {
        // TODO Auto-generated method stub

    }

    public void removeUserTerm(String arg0, long arg1) throws UserServiceException {
        // TODO Auto-generated method stub

    }

}