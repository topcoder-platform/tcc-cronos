package com.topcoder.registration.team.service.failuretests;

import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.RetrievalException;
import com.cronos.onlinereview.external.UserRetrieval;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;

public class MockUserRetrieval implements UserRetrieval {

    public MockUserRetrieval() {
    }

    public ExternalUser retrieveUser(long arg0) throws RetrievalException {
        // TODO Auto-generated method stub
        return new ExternalUserImpl(1, "arg", "handle", "firstname", "lastname");
    }

    public ExternalUser retrieveUser(String arg0) throws RetrievalException {
        // TODO Auto-generated method stub
        return null;
    }

    public ExternalUser[] retrieveUsers(long[] arg0) throws RetrievalException {
        // TODO Auto-generated method stub
        return null;
    }

    public ExternalUser[] retrieveUsers(String[] arg0) throws RetrievalException {
        // TODO Auto-generated method stub
        return null;
    }

    public ExternalUser[] retrieveUsersIgnoreCase(String[] arg0) throws RetrievalException {
        // TODO Auto-generated method stub
        return null;
    }

    public ExternalUser[] retrieveUsersByName(String arg0, String arg1) throws RetrievalException {
        // TODO Auto-generated method stub
        return null;
    }

}
