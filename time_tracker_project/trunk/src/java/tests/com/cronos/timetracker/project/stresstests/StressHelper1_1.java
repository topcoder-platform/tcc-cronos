/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.stresstests;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.cronos.timetracker.project.Client;
import com.cronos.timetracker.project.Project;
import com.cronos.timetracker.project.persistence.PersistenceException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

import java.io.File;
import java.util.Date;
import junit.framework.TestCase;


/**
 * <p>
 * The help class which provides help to the stress tests.
 * </p>
 * 
 * @author nhzp339
 * @version 1.1
 */
public final class StressHelper1_1 extends TestCase {
    /**
     * The namespace of this component.
     */
    private static final String NS = "com.cronos.timetracker.project";
    
    /**
     * The folder which is populated by the config files.
     */
    private static final String DIR = "test_files/stresstests1_1/";
    
    /**
     * The namespace of <code>DBConnectionFactory</code>.
     */
    private static final String DB_NS = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
    
    /**
     * Private method is used to avoid being initialized.
     */
    private StressHelper1_1() {
    }
    
    /**
     * <p>A helper method to be used to initialize the specified configuration namespace with the configuration
     * properties provided by specified file. If specified namespace is already loaded to <code>ConfigurationManager
     * </code> then it is re-loaded with new configuration properties.</p>
     *
     * @param namespace a <code>String</code> providing the namespace to load configuration for.
     * @param filename a <code>String</code> providing the name of the file to load configuration file from.
     * @param format a <code>String</code> specifying the format of the configuration file.
     */
    static final void loadConfiguration(String namespace, String filename, String format) {
        ConfigManager configManager = ConfigManager.getInstance();
        if (configManager.existsNamespace(namespace)) {
            try {
                configManager.removeNamespace(namespace);
            } catch (UnknownNamespaceException e) {}
        }

        try {
            configManager.add(namespace, new File(filename).getAbsolutePath(), format);
        } catch (ConfigManagerException e) {
            System.err.println("An error occurred while loading the configuration namespace '"
                + namespace  + "' from file : " + filename + "\n\n" + e);
        }
    }
    
    /**
     * <p>Load all the namespaces of this componenet.</p>
     */
    static final void loadAllConfiguration() {
        loadConfiguration(DB_NS, DIR + "DBConnectionFactoryImpl.xml", ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
        loadConfiguration(NS, DIR + "timetrackerproject.xml", ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
        loadConfiguration("com.cronos.timetracker.project.persistence.DatabaseSearchUtility.clients",
        		DIR + "timetrackerproject.xml", ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
        loadConfiguration("com.cronos.timetracker.project.persistence.DatabaseSearchUtility.projects",
        		DIR + "timetrackerproject.xml", ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
    }
    
    /**
     * <p>Deletes specified configuration namespace from Config Manager.</p>
     */
    static final void releaseNamespaces() {
        releaseNamespace(NS);
        releaseNamespace(DB_NS);
        releaseNamespace("com.cronos.timetracker.project.persistence.DatabaseSearchUtility.clients");
        releaseNamespace("com.cronos.timetracker.project.persistence.DatabaseSearchUtility.projects");
    }
    
    /**
     * <p>Deletes specified configuration namespace from Config Manager.</p>
     *
     * @param namespace a <code>String</code> referencing the configuration namespace to remove from configuration
     *        manager.
     */
    static final void releaseNamespace(String namespace) {
        ConfigManager configManager = ConfigManager.getInstance();
        if (configManager.existsNamespace(namespace)) {
            try {
                configManager.removeNamespace(namespace);
            } catch (UnknownNamespaceException e) {}
        }
    }
    
    /**
     * Get Connection instance for testing.
     *
     * @return the Connection instance for testing
     *
     * @throws Exception if error occurs get connection
     */
    static final Connection createConnection() throws Exception {
            
        try {
            // obtain the connection via the DB Connection Factory
            DBConnectionFactory factory = new DBConnectionFactoryImpl(DB_NS);
            return factory.createConnection();
            
        } catch (ConfigurationException e) {
            throw new PersistenceException("Error occurs during reading DBConfiguration file", e);
        } catch (DBConnectionException e) {
            throw new PersistenceException("Fail to obtain the connection", e);
        }
    }
    
    /**
     * Creates a client with the given id. And then the <code>Client</code>will be initialized
     * with its properties.
     *
     * @param clientId the id of the client
     *
     * @return a created client
     */
    static final Client createClient(int clientId) {
        Client client = new Client();
        
        // Set the properties of client.
        client.setId(clientId);
        client.setName("name" + clientId);
        client.setCreationUser("creationUser" + clientId);
        client.setModificationUser("modificationUser" + clientId);
        return client;
    }
    
    /**
     * Creates a project with the given id. And then the <code>Project</code>will be initialized
     * with its properties.
     *
     * @param projectId the id of the project
     *
     * @return a created project
     */
    static final Project createProject(int projectId) {
        Project project = new Project();

        // Set the properties of Project.
        project.setId(projectId);
        project.setName("name" + projectId);
        project.setDescription("description" + projectId);
        project.setCreationUser("creationUser" + projectId);
        project.setModificationUser("modificationUser" + projectId);
        project.setStartDate(new Date());
        project.setEndDate(new Date());

        return project;
    }
    
    /**
     * Delete all the data from all the tables.
     * @throws Exception to JUnit.
     */
    static final void clearTables() throws Exception {
        final String[] tableNames = {
                "ProjectTimes", "ProjectExpenses", "ProjectManagers", "ProjectWorkers",
                "ClientProjects", "Projects", "Clients"
            };
        
        Connection conn = createConnection();
        conn.setAutoCommit(false);
        for (int i = 0; i < tableNames.length; i++) {
            delTable(tableNames[i]);
        }
        
        conn.commit();
    }
    
    /**
     * Remove the data from the the table whose name is passed by tableName;
     * @param tableName the table name
     * @throws Exception to Junit.
     */
    static final void delTable(String tableName) throws Exception {
        Connection conn = createConnection();
        String sql = "DELETE FROM " + tableName;
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.executeUpdate();
        ps.close();
    }
}
