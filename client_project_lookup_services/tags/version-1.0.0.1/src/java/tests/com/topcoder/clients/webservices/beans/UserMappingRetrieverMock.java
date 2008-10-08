/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.webservices.usermapping.UserMappingRetriever;

/**
 * <p>
 * Mock implementation of the UserMappingRetriever interface.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserMappingRetrieverMock implements UserMappingRetriever {

    /**
     * <p>
     * Clients which will be returned from getClientsForUserId operation.
     * </p>
     */
    private static List < Client > clients;

    /**
     * <p>
     * Clients which will be returned from getProjectsForUserId operation.
     * </p>
     */
    private static List < Project > projects;

    static {

        Client client1 = new Client();
        client1.setId(42);
        Client client2 = new Client();
        client2.setId(15);
        clients = new ArrayList < Client >();
        clients.add(client1);
        clients.add(client2);

        Project project1 = new Project();
        project1.setId(42);
        Project project2 = new Project();
        project2.setId(15);
        projects = new ArrayList < Project >();
        projects.add(project1);
        projects.add(project2);

    }

    /**
     * <p>
     * Set clients field to passed clients.
     * </p>
     *
     * @param clientsList
     *            clients to set
     */
    public static void setClients(List < Client > clientsList) {
        clients = new ArrayList < Client >(clientsList);
    }

    /**
     * <p>
     * Set projects field to passed projects.
     * </p>
     *
     * @param projectsList
     *            projects to set
     */
    public static void setProjects(List < Project > projectsList) {
        projects = new ArrayList < Project >(projectsList);
    }

    /**
     * <p>
     * Gets clients for given userId.
     * </p>
     *
     * @param userId
     *            user ID
     * @return clients for given userId
     */
    public List < Client > getClientsForUserId(long userId) {
        return new ArrayList < Client >(clients);
    }

    /**
     * <p>
     * Gets projects for given userId.
     * </p>
     *
     * @param userId
     *            user ID
     * @return projects for given userId
     */
    public List < Project > getProjectsForUserId(long userId) {
        return new ArrayList < Project >(projects);
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param client
     *            not used
     * @return null
     */
    public List < Long > getValidUsersForClient(Client client) {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param project
     *            not used
     * @return null
     */
    public List < Long > getValidUsersForProject(Project project) {
        return null;
    }

}
