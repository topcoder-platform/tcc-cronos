/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.util.config.ConfigManager;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * A helper class to provide some useful methods for testing. These include setting up the configuration namespace, and
 * creating various types of clients, projects, project managers and project workers.
 *
 * @author colau
 * @author TCSDEVELOPER
 * @author costty000
 * @version 2.0
 *
 * @since 1.0
 */
public class TestHelper {
    /**
     * The configuration file of the time tracker project.
     */
    public static final String CONFIG_FILE = "timetrackerproject.xml";

    /**
     * The namespace of the time tracker project pertaining to the Informix database persistence.
     */
    public static final String NAMESPACE = "com.topcoder.timetracker.project";

    /**
     * The namespace of the time tracker project pertaining to a simple persistence.
     */
    public static final String SIMPLE_NAMESPACE = "com.topcoder.timetracker.project.simple";

    /**
     * The namespace of the db connection factory.
     */
    public static final String DB_NAMESPACE = "com.topcoder.db.connectionfactory";

    /**
     * The connection producer name used to obtain the connection.
     */
    public static final String CONNECTION_PRODUCER_NAME = "Informix_Connection_Producer";

    /**
     * The namespace used by the project search utility.
     */
    public static final String PROJECT_SEARCH_UTILITY_NAMESPACE = "com.topcoder.timetracker.project.persistence."
        + "DatabaseSearchUtility.projects";

    /**
     * The namespace used by the client search utility.
     */
    public static final String CLIENT_SEARCH_UTILITY_NAMESPACE = "com.topcoder.timetracker.project.persistence."
        + "DatabaseSearchUtility.clients";

    /**
     * The bad configuration file of the time tracker project.
     */
    public static final String BAD_CONFIG_FILE = "badtimetrackerproject.xml";

    /**
     * The bad namespaces used by the database search utility.
     */
    public static final String[] BAD_SEARCH_UTILITY_NAMESPACES = {
        "BadDatabaseSearchUtility1", "BadDatabaseSearchUtility2", "BadDatabaseSearchUtility3"
    };

    /**
     * The bad namespaces used by the project persistence manager.
     */
    public static final String[] BAD_NAMESPACES = {
        "BadProjectPersistenceManager1", "BadProjectPersistenceManager2", "BadProjectPersistenceManager3",
        "BadProjectPersistenceManager4", "BadProjectPersistenceManager5"
    };

    /**
     * The size of the array argument supplied to the methods.
     */
    public static final int ARRAY_SIZE = 3;

    /**
     * Private constructor to prevent instantiation.
     */
    private TestHelper() {
    }

    /**
     * Loads the namespaces of time tracker project.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public static void loadConfig() throws Exception {
        unloadConfig();
        ConfigManager.getInstance().add(CONFIG_FILE);
        ConfigManager.getInstance().add(BAD_CONFIG_FILE);
    }

    /**
     * Clears all the namespaces.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public static void unloadConfig() throws Exception {
        ConfigManager config = ConfigManager.getInstance();

        for (Iterator i = config.getAllNamespaces(); i.hasNext();) {
            config.removeNamespace((String) i.next());
        }
    }

    /**
     * Creates a valid client for testing. All necessary fields of the client are filled.
     *
     * @return a valid client
     */
    public static Client createClient() {
        return createClient(-1);
    }

    /**
     * Creates a valid array of clients for testing. All necessary fields of the clients are filled.
     *
     * @return a valid array of clients
     */
    public static Client[] createClients() {
        Client[] clients = new Client[ARRAY_SIZE];

        for (int i = 0; i < ARRAY_SIZE; i++) {
            clients[i] = createClient();
            // added in 2.0: set a unique names to clients
            clients[i].setName(clients[i].getName() + "_" + i);
        }
        return clients;
    }

    /**
     * Creates a valid client with the specified id for testing. All necessary fields of the client are filled.
     *
     * @param clientId the id of the client
     *
     * @return a valid client
     */
    public static Client createClient(int clientId) {
        Client client = new Client();

        client.setId(clientId);
        // from 2.0: set the corespondace with the company
        client.setCompanyId(1);
        // from 2.0: assign a unique name to all clients
        client.setName("name" + "_" + clientId);
        client.setCreationUser("creationUser");
        client.setModificationUser("modificationUser");

        return client;
    }

    /**
     * Creates an illegal client for testing. The creation date of this client is set.
     *
     * @return an illegal client
     */
    public static Client createIllegalClient() {
        Client client = new Client();

        // the creation date is set
        client.setCreationDate(new Date());

        client.setName("name");
        client.setCreationUser("creationUser");
        client.setModificationUser("modificationUser");

        return client;
    }

    /**
     * Creates a client with insufficient data for testing. The name of this client is not set.
     *
     * @return a client with insufficient data
     */
    public static Client createInsufficientClient() {
        Client client = new Client();

        // the name is not set
        client.setCreationUser("creationUser");
        client.setModificationUser("modificationUser");

        return client;
    }

    /**
     * Creates a valid project for testing. All necessary fields of the project are filled.
     *
     * @return a valid project
     */
    public static Project createProject() {
        return createProject(-1);
    }

    /**
     * Creates a valid array of projects for testing. All necessary fields of the projects are filled.
     *
     * @return a valid array of projects
     */
    public static Project[] createProjects() {
        Project[] projects = new Project[ARRAY_SIZE];

        for (int i = 0; i < ARRAY_SIZE; i++) {
            projects[i] = createProject();
        }
        return projects;
    }

    /**
     * Creates a valid project with the specified id for testing. All necessary fields of the project are filled.
     *
     * @param projectId the id of the project
     *
     * @return a valid project
     */
    public static Project createProject(int projectId) {
        Project project = new Project();

        project.setId(projectId);
        // from 2.0: set a specific company for the project
        project.setCompanyId(1);
        //project.setManagerId(1);

        project.setName("name");
        project.setDescription("description");
        project.setCreationUser("creationUser");
        project.setModificationUser("modificationUser");
        project.setStartDate(new Date());
        project.setEndDate(new Date());

        return project;
    }

    /**
     * Creates an illegal project for testing. The creation date of this project is set.
     *
     * @return an illegal project
     */
    public static Project createIllegalProject() {
        Project project = new Project();

        // the creation date is set
        project.setCreationDate(new Date());

        project.setName("name");
        project.setDescription("description");
        project.setCreationUser("creationUser");
        project.setModificationUser("modificationUser");
        project.setStartDate(new Date());
        project.setEndDate(new Date());

        return project;
    }

    /**
     * Creates a project with insufficient data for testing. The name of this project is not set.
     *
     * @return a project with insufficient data
     */
    public static Project createInsufficientProject() {
        Project project = new Project();

        // the name is not set
        project.setDescription("description");
        project.setCreationUser("creationUser");
        project.setModificationUser("modificationUser");
        project.setStartDate(new Date());
        project.setEndDate(new Date());

        return project;
    }

    /**
     * Creates a valid project manager for testing. All necessary fields of the project manager are filled.
     *
     * @return a valid project manager
     */
    public static ProjectManager createManager() {
        ProjectManager manager = new ProjectManager();

        // from 2.0: create a project with specific id (that has assigned a company)
        Project project = new Project();
        project.setId(1);
        manager.setProject(project);
        manager.setManagerId(1);
        manager.setCreationUser("creationUser");
        manager.setModificationUser("modificationUser");

        return manager;
    }

    /**
     * Creates an illegal project manager for testing. The creation date of this project manager is set.
     *
     * @return an illegal project manager
     */
    public static ProjectManager createIllegalManager() {
        ProjectManager manager = new ProjectManager();

        // the creation date is set
        manager.setCreationDate(new Date());

        manager.setProject(new Project());
        manager.setCreationUser("creationUser");
        manager.setModificationUser("modificationUser");

        return manager;
    }

    /**
     * Creates a project manager with insufficient data for testing. The project of this project manager is not set.
     *
     * @return a project manager with insufficient data
     */
    public static ProjectManager createInsufficientManager() {
        ProjectManager manager = new ProjectManager();

        // the project is not set
        manager.setCreationUser("creationUser");
        manager.setModificationUser("modificationUser");

        return manager;
    }

    /**
     * Creates a valid project worker for testing. All necessary fields of the project worker are filled.
     *
     * @return a valid project worker
     */
    public static ProjectWorker createWorker() {
        ProjectWorker worker = new ProjectWorker();

        worker.setWorkerId(1);
        // from 2.0: create and assign a specific project for worker, that has assigned a specific company
        Project project = new Project();
        project.setId(1);
        project.setCompanyId(1);

        worker.setProject(project);
        worker.setStartDate(new Date());
        worker.setEndDate(new Date());
        worker.setPayRate(new BigDecimal(10));
        worker.setCreationUser("creationUser");
        worker.setModificationUser("modificationUser");

        return worker;
    }

    /**
     * Creates a project worker with specific id for testing..
     *
     * @param id
     *            id of the worker
     * @param project
     *            the assigned project to created worker
     * @return a project worker
     *
     * @since 2.0
     */
    public static ProjectWorker createWorker(int id, Project project) {
        ProjectWorker worker = new ProjectWorker();

        worker.setWorkerId(id);
        worker.setProject(project);
        worker.setStartDate(new Date(System.currentTimeMillis()));
        worker.setEndDate(new Date(System.currentTimeMillis()));
        worker.setPayRate(new BigDecimal(1));
        worker.setCreationUser("creationUser");
        worker.setModificationUser("creationUser");

        return worker;
    }


    /**
     * Creates a valid array of project workers for testing. All necessary fields of the project workers are filled.
     *
     * @return a valid array of project workers
     */
    public static ProjectWorker[] createWorkers() {
        ProjectWorker[] workers = new ProjectWorker[ARRAY_SIZE];

        for (int i = 0; i < ARRAY_SIZE; i++) {
            workers[i] = createWorker();
        }
        return workers;
    }

    /**
     * Creates an illegal project worker for testing. The creation date of this project worker is set.
     *
     * @return an illegal project worker
     */
    public static ProjectWorker createIllegalWorker() {
        ProjectWorker worker = new ProjectWorker();

        // the creation date is set
        worker.setCreationDate(new Date());

        worker.setProject(new Project());
        worker.setStartDate(new Date());
        worker.setEndDate(new Date());
        worker.setPayRate(new BigDecimal(10));
        worker.setCreationUser("creationUser");
        worker.setModificationUser("modificationUser");

        return worker;
    }

    /**
     * Creates a project worker with insufficient data for testing. The project of this project worker is not set.
     *
     * @return a project worker with insufficient data
     */
    public static ProjectWorker createInsufficientWorker() {
        ProjectWorker worker = new ProjectWorker();

        // the project is not set
        worker.setStartDate(new Date());
        worker.setEndDate(new Date());
        worker.setPayRate(new BigDecimal(10));
        worker.setCreationUser("creationUser");
        worker.setModificationUser("modificationUser");

        return worker;
    }

    /**
     * Creates an array of ids for testing. The ids start with the given integer, and increment by one for next
     * element.
     *
     * @param start the id to start with
     *
     * @return an array of ids
     */
    public static int[] createIds(int start) {
        int[] result = new int[ARRAY_SIZE];

        for (int i = 0; i < ARRAY_SIZE; i++) {
            result[i] = start + i;
        }
        return result;
    }

    /**
     * Checks whether two projects are equal. To be considered equal, all the fields must be the same.
     *
     * @param p1 the source project
     * @param p2 the target project
     *
     * @return whether two projects are equal
     */
    public static boolean compareProjects(Project p1, Project p2) {
        if (!compareDates(p1.getCreationDate(), p2.getCreationDate())) {
            return false;
        }
        if (!p1.getCreationUser().equals(p2.getCreationUser())) {
            return false;
        }
        if (!p1.getDescription().equals(p2.getDescription())) {
            return false;
        }
        if (!compareDates(p1.getEndDate(), p2.getEndDate())) {
            return false;
        }
        if (!p1.getExpenseEntries().equals(p2.getExpenseEntries())) {
            return false;
        }
        if (p1.getId() != p2.getId()) {
            return false;
        }
        if (p1.getManagerId() != p2.getManagerId()) {
            return false;
        }
        if (!compareDates(p1.getModificationDate(), p2.getModificationDate())) {
            return false;
        }
        if (!p1.getModificationUser().equals(p2.getModificationUser())) {
            return false;
        }
        if (!p1.getName().equals(p2.getName())) {
            return false;
        }
        if (!compareDates(p1.getStartDate(), p2.getStartDate())) {
            return false;
        }
        if (!p1.getTimeEntries().equals(p2.getTimeEntries())) {
            return false;
        }
        if (!p1.getWorkersIds().equals(p2.getWorkersIds())) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether two clients are equal. To be considered equal, all the fields must be the same.
     *
     * @param c1 the source client
     * @param c2 the target client
     *
     * @return whether two clients are equal
     */
    public static boolean compareClients(Client c1, Client c2) {
        if (!compareDates(c1.getCreationDate(), c2.getCreationDate())) {
            return false;
        }
        if (!c1.getCreationUser().equals(c2.getCreationUser())) {
            return false;
        }
        if (c1.getId() != c2.getId()) {
            return false;
        }
        if (!compareDates(c1.getModificationDate(), c2.getModificationDate())) {
            return false;
        }
        if (!c1.getModificationUser().equals(c2.getModificationUser())) {
            return false;
        }
        if (!c1.getName().equals(c2.getName())) {
            return false;
        }
        if (c1.getProjects().size() != c2.getProjects().size()) {
            return false;
        }

        // verify the list of projects
        List projects1 = c1.getProjects();
        List projects2 = c2.getProjects();

        for (Iterator i = projects1.iterator(); i.hasNext();) {
            Project p1 = (Project) i.next();

            for (Iterator j = projects2.iterator(); j.hasNext();) {
                Project p2 = (Project) j.next();

                if (compareProjects(p1, p2)) {
                    j.remove();
                    break;
                }
            }
        }
        return projects2.isEmpty();
    }

    /**
     * Checks whether two project managers are equal. To be considered equal, all the fields must be the same.
     *
     * @param pm1 the source project manager
     * @param pm2 the target project manager
     *
     * @return whether two project managers are equal
     */
    public static boolean compareProjectManagers(ProjectManager pm1, ProjectManager pm2) {
        if (!compareDates(pm1.getCreationDate(), pm2.getCreationDate())) {
            return false;
        }
        if (!pm1.getCreationUser().equals(pm2.getCreationUser())) {
            return false;
        }
        if (pm1.getManagerId() != pm2.getManagerId()) {
            return false;
        }
        if (!compareDates(pm1.getModificationDate(), pm2.getModificationDate())) {
            return false;
        }
        if (!pm1.getModificationUser().equals(pm2.getModificationUser())) {
            return false;
        }
        if (!compareProjects(pm1.getProject(), pm2.getProject())) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether two project workers are equal. To be considered equal, all the fields must be the same.
     *
     * @param pw1 the source project worker
     * @param pw2 the target project worker
     *
     * @return whether two project workers are equal
     */
    public static boolean compareProjectWorkers(ProjectWorker pw1, ProjectWorker pw2) {
        if (!compareDates(pw1.getCreationDate(), pw2.getCreationDate())) {
            return false;
        }
        if (!pw1.getCreationUser().equals(pw2.getCreationUser())) {
            return false;
        }
        if (!compareDates(pw1.getEndDate(), pw2.getEndDate())) {
            return false;
        }
        if (!compareDates(pw1.getModificationDate(), pw2.getModificationDate())) {
            return false;
        }
        if (!pw1.getModificationUser().equals(pw2.getModificationUser())) {
            return false;
        }
        if (pw1.getPayRate().compareTo(pw2.getPayRate()) != 0) {
            return false;
        }
        if (!compareProjects(pw1.getProject(), pw2.getProject())) {
            return false;
        }
        if (!compareDates(pw1.getStartDate(), pw2.getStartDate())) {
            return false;
        }
        if (pw1.getWorkerId() != pw2.getWorkerId()) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether two dates are equal, disregarding the milliseconds.
     *
     * @param d1 the source date
     * @param d2 the target date
     *
     * @return whether two dates are equal
     */
    private static boolean compareDates(Date d1, Date d2) {
        return (d1.getTime() / 1000) == (d2.getTime() / 1000);
    }
}
