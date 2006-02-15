/*
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project.failuretests;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.topcoder.timetracker.project.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.util.config.ConfigManager;

/**
 * Failure tests helper class.
 *
 * @author dmks
 * @version 1.0
 */
public class FailureTestHelper{
    /**
     * The namespace of the time tracker project.
     */
    public static final String NAMESPACE = "com.topcoder.timetracker.project";

    /**
     * The configuration file of the time tracker project.
     */
    public static final String CONFIG_FILE = "test_files/failure/TimeTrackerProject.xml";

    /**
     * The namespace of the db connection factory.
     */
    public static final String DB_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * The configuration file of the db connection factory.
     */
    public static final String DB_CONFIG_FILE = "test_files/failure/DBConnectionFactory.xml";

    /**
     * The connection producer name used to obtain the connection.
     */
    public static final String CONNECTION_PRODUCER_NAME = "Informix_Connection_Producer";

    /**
     * Private constructor to prevent instantiation.
     */
    private FailureTestHelper() {
    }

    /**
     * Loads the namespaces of time tracker project and db connection factory.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public static void loadConfig() throws Exception {
        ConfigManager config = ConfigManager.getInstance();

        config.add(new File(CONFIG_FILE).getCanonicalPath());
        config.add(new File(DB_CONFIG_FILE).getCanonicalPath());
    }
    
    /**
     * Loads the specified configuration file.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    public static void loadConfig(String fileName) throws Exception {
        ConfigManager config = ConfigManager.getInstance();

        config.add(new File(fileName).getCanonicalPath());        
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
     * Creates a valid project with the specified id for testing. All necessary fields of the project are filled.
     *
     * @param projectId the id of the project
     *
     * @return a valid project
     */
    public static Project createProject(int projectId) {
        Project project = new Project();

        project.setId(projectId);
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
        if (!pw1.getPayRate().equals(pw2.getPayRate())) {
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
