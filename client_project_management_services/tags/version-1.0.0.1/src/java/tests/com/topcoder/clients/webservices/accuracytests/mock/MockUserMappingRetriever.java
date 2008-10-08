/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.accuracytests.mock;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.webservices.usermapping.UserMappingRetrievalException;
import com.topcoder.clients.webservices.usermapping.UserMappingRetriever;

/**
 * <p>
 * Mock implementation of the UserMappingRetriever interface.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockUserMappingRetriever implements UserMappingRetriever {

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

    /**
     * <p>
     * If set to true, operations will throw UserMappingRetrievalException.
     * </p>
     */
    private static boolean isFail = false;

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
     * Add client to the clients list.
     * </p>
     *
     * @param client
     *            client to be add
     */
    public static void addClient(Client client) {
        if (clients == null) {
            clients = new ArrayList < Client >();
        }
        clients.add(client);
    }

    /**
     * <p>
     * Add project to the projects list.
     * </p>
     *
     * @param project
     *            client to be add
     */
    public static void addProject(Project project) {
        if (projects == null) {
            projects = new ArrayList < Project >();
        }
        projects.add(project);
    }

    /**
     * <p>
     * If set to true, operations will throw UserMappingRetrievalException.
     * </p>
     *
     * @param f
     *          flag value
     */
    public static void setFail(boolean f) {
        isFail = f;
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
     * @throws UserMappingRetrievalException
     *            if the isFail is true
     */
    public List < Client > getClientsForUserId(long userId) throws UserMappingRetrievalException {
        checkFail();
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
     * @throws UserMappingRetrievalException
     *              if the isFail is true
     */
    public List < Project > getProjectsForUserId(long userId) throws UserMappingRetrievalException {
        checkFail();
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

    /**
     * <p>
     * Check the isFail flag value, if it is true throw UserMappingRetrievalException.
     * </p>
     *
     * @throws UserMappingRetrievalException
     *                  if isFail is true
     */
    private static void checkFail() throws UserMappingRetrievalException {
        if (isFail) {
            throw new UserMappingRetrievalException("for test.");
        }
    }

}
