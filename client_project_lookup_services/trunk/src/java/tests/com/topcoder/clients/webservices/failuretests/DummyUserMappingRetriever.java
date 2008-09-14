package com.topcoder.clients.webservices.failuretests;

import java.util.List;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.webservices.usermapping.UserMappingRetriever;

public class DummyUserMappingRetriever implements UserMappingRetriever {

    public List<Client> getClientsForUserId(long userId) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Project> getProjectsForUserId(long userId) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Long> getValidUsersForClient(Client client) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Long> getValidUsersForProject(Project project) {
        // TODO Auto-generated method stub
        return null;
    }

}
