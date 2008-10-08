/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import junit.framework.TestCase;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.usermapping.impl.UserClientMapping;
import com.topcoder.clients.webservices.usermapping.impl.UserProjectMapping;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * The super class of all persistence related class.
 *
 * @author CaDenza
 * @version 1.0
 */
public abstract class TestBase extends TestCase {

    /**
     * Flag whether we should setup database first.
     */
    private static boolean setupDatabase = false;

    /**
     * Counter for project status.
     */
    private static int projectSTATUSCOUNTER = 1;

    /**
     * Counter for project.
     */
    private static int userPROJECTCOUNTER = 1;

    /**
     * Counter for user client.
     */
    private static int userCLIENTCOUNTER = 1;

    /**
     * Counter for client status.
     */
    private static int clientSTATUSCOUNTER = 1;

    /**
     * Counter for company.
     */
    private static int companyCOUNTER = 1;

    /**
     * Counter for client.
     */
    private static int clientCOUNTER = 1;

    /**
     * Counter for project.
     */
    private static int projectCOUNTER = 1;

    /**
     * Setup system property.
     */
    static {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        System.setProperty(Context.PROVIDER_URL, "jnp://localhost:1099");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
    }

    /**
     * An EntityManager instance used in tests.
     */
    private static EntityManager entityManager;

    /**
     * The clear table sql.
     */
    private String[] clearSQLs = new String[] {
        "delete from client_user_xref", "delete from project_user_xref",
        "delete from project", "delete from client" , "delete from client_status",
        "delete from project_status", "delete from company"};

    /**
     * The drop table sql.
     */
    private String[] dropSQLs = new String[] {
        "drop table client_user_xref", "drop table project_user_xref",
        "drop table project", "drop table client" , "drop table client_status",
        "drop table project_status", "drop table company", "drop table id_sequences"};

    /**
     * <p>
     * setUp() routine.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    protected void setUp() throws Exception {
        getEntityManager();
        prepareDatabase();
    }

    /**
     * <p>
     * tearDown() routine.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    protected void tearDown() throws Exception {
        clearDatabase();
    }

    /**
     * Preparing database for testing.
     *
     * @throws Exception if any failure occurs.
     */
    private void prepareDatabase() throws Exception {
        if (!setupDatabase) {
            EntityManager myEntityManager = getEntityManager();

            // Drop table.
            for (String sql : dropSQLs) {
                try {
                    myEntityManager.getTransaction().begin();
                    myEntityManager.createNativeQuery(sql).executeUpdate();
                    myEntityManager.getTransaction().commit();
                } catch (Exception e) {
                    myEntityManager.getTransaction().rollback();
                }
            }

            InputStream inStream = null;
            BufferedReader in = null;
            StringBuilder createDbScript = new StringBuilder();
            try {
                inStream = this.getClass().getClassLoader().getResourceAsStream("schema.sql");
                in = new BufferedReader(new InputStreamReader(inStream));
                String line;
                while ((line = in.readLine()) != null) {
                    createDbScript.append(line + "\n");
                }
            } finally {
                closeStream(inStream, in);
            }
            String[] statements = createDbScript.toString().split(";");

            Log logger = LogManager.getLog("setup.database");
            logger.log(Level.INFO, "--------- setup database for testing ----------");
            for (String sql : statements) {
                try {
                    String execSQL = sql.trim();
                    if ((execSQL.length() != 0) && (!execSQL.startsWith("-"))) {
                        myEntityManager.getTransaction().begin();
                        logger.log(Level.INFO, "Execute: " + execSQL);
                        myEntityManager.createNativeQuery(execSQL).executeUpdate();
                        myEntityManager.getTransaction().commit();
                    }
                } catch (Exception e) {
                    myEntityManager.getTransaction().rollback();
                    throw e;
                }
            }
            logger.log(Level.INFO, "--------- accomplish setup database for testing --------");

            setupDatabase = true;
        } else {
            clearDatabase();
        }
    }

    /**
     * Closing opened stream.
     *
     * @param iStream
     *      The input stream instance.
     * @param iReader
     *      The buffered reader instance.
     */
    private void closeStream(InputStream iStream, BufferedReader iReader) {
        if (iStream != null) {
            try {
                iStream.close();
            } catch (IOException e) {
                // ignore
            }
        }

        if (iReader != null) {
            try {
                iReader.close();
            } catch (IOException e) {
                // ignore.
            }
        }
    }

    /**
     * Clear database.
     */
    private void clearDatabase() {
        EntityManager myEntityManager = getEntityManager();
        for (String sql : clearSQLs) {
            myEntityManager.getTransaction().begin();
            try {
                myEntityManager.createNativeQuery(sql).executeUpdate();
                myEntityManager.getTransaction().commit();
            } catch (Exception e) {
                myEntityManager.getTransaction().rollback();
            }
        }
    }

    /**
     * Get EntityManager.
     * @return EntityManager
     */
    protected EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            // create entityManager
            try {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnitLocal");
                entityManager = emf.createEntityManager();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        entityManager.clear();
        return entityManager;
    }

    /**
     * Set child project.
     * @param parent the parent id
     * @param child the child id
     */
    protected void setChildProject(long parent, long child) {
        Query query = entityManager
                .createNativeQuery("update project set parent_project_id=? where id=?");
        query.setParameter(1, parent);
        query.setParameter(2, child);
        query.executeUpdate();
        entityManager.flush();
    }

    /**
     * Create ProjectStatus.
     *
     * @return ProjectStatus created
     */
    protected ProjectStatus createProjectStatus() {
        ProjectStatus projectStatus = TestHelper.createProjectStatus(projectSTATUSCOUNTER);

        entityManager.getTransaction().begin();
        entityManager.persist(projectStatus);
        entityManager.getTransaction().commit();

        projectSTATUSCOUNTER++;
        return projectStatus;
    }

    /**
     * Create client.
     *
     * @return client created
     */
    protected Client createClient() {
        Client client = createClient(clientCOUNTER);
        clientCOUNTER++;
        return client;
    }

    /**
     * Create client.
     *
     * @param counter
     *      The counter id of Client.
     *
     * @return client created
     */
    protected Client createClient(long counter) {
        Client client = TestHelper.createClient(counter, createClientStatus(), createCompany());
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
        return client;
    }

    /**
     * Create Project.
     *
     * @return Project created
     */
    protected Project createProject() {
        Project project = createProject(projectCOUNTER);
        projectCOUNTER++;
        return project;
    }

    /**
     * Create Project.
     *
     * @param counter
     *      The counter id of project.
     *
     * @return Project created
     */
    protected Project createProject(long counter) {
        Project project = TestHelper.createProject(counter, createClient(),
                createProjectStatus(), createCompany());
        project.setParentProjectId(0);

        entityManager.getTransaction().begin();
        entityManager.persist(project);
        entityManager.getTransaction().commit();
        return project;
    }

    /**
     * Create company.
     *
     * @return company created
     */
    protected Company createCompany() {
        Company company = createCompany(companyCOUNTER);
        companyCOUNTER++;
        return company;
    }

    /**
     * Create company.
     *
     * @param counter
     *      The counter id of company.
     *
     * @return company created
     */
    protected Company createCompany(long counter) {
        Company company = TestHelper.createCompany(counter);
        // persist object
        entityManager.getTransaction().begin();
        entityManager.persist(company);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return company;
    }

    /**
     * Create ClientStatus.
     *
     * @return ClientStatus created
     */
    protected ClientStatus createClientStatus() {
        ClientStatus clientStatus = TestHelper.createClientStatus(clientSTATUSCOUNTER);

        entityManager.getTransaction().begin();
        entityManager.persist(clientStatus);
        entityManager.getTransaction().commit();

        clientSTATUSCOUNTER++;
        return clientStatus;
    }

    /**
     * Create UserClientMapping.
     *
     * @param clientId the id of client.
     * @param userId the id of user.
     *
     * @return UserClientMapping created
     */
    protected UserClientMapping createUserClientMapping(long clientId, long userId) {
        UserClientMapping entity = TestHelper.createUserClientMapping(userCLIENTCOUNTER, clientId, userId);

        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();

        userCLIENTCOUNTER++;
        return entity;
    }

    /**
     * Create UserProjectMapping.
     *
     * @param projectId the id of project.
     * @param userId the id of user.
     *
     * @return UserClientMapping created
     */
    protected UserProjectMapping createUserProjectMapping(long projectId, long userId) {
        UserProjectMapping entity =
            TestHelper.createUserProjectMapping(userPROJECTCOUNTER, projectId, userId);

        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();

        userPROJECTCOUNTER++;
        return entity;
    }
}
