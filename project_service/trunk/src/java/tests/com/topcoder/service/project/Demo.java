/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.io.File;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.ws.WebServiceRef;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>
 * This is the demo for this component.
 * </p>
 *
 * @author humblefool, FireIce
 * @version 1.0
 */
public class Demo extends BaseTestCase {

    /**
     * <p>
     * Represents the <code>ProjectPersistence</code> instance for testing.
     * </p>
     */
    private ProjectPersistence projectPersistence;

    /**
     * <p>
     * Represents the <code>InitialContext</code> instance for looking up.
     * </p>
     */
    private InitialContext ctx;

    @WebServiceRef(wsdlLocation="http://127.0.0.1:8080/project_service-project_service/ProjectServiceBean?wsdl")
    ProjectService service;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        executeScriptFile("/clean.sql");

        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "username");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        ctx = new InitialContext(env);

        projectPersistence = (ProjectPersistence) ctx.lookup("project_service/ProjectPersistenceBean/remote");
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        ctx.close();

        executeScriptFile("test_files" + File.separator + "clean.sql");

        super.tearDown();
    }

    /**
     * <p>
     * Demonstrates the usage of the JPAProjectPersistence.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testJPAProjectPersistence() throws Exception {
        Project project = new Project();
        project.setName("Arena");
        project.setDescription("A new competition arena");
        /* Similarly set the other properties */

        /* We assume DesignCompetition and StudioCompetition are subclasses of Competition */
        Set<Competition> competitions = new HashSet<Competition>();
        competitions.add(new DesignCompetition("GUI design"));
        competitions.add(new DesignCompetition("Backend design"));
        competitions.add(new StudioCompetition("Arena skins"));
        for (Competition c : competitions) {
            c.setProject(project);
        }

        /*
         * Create everything. Note that to fully persist the Competition subclasses, custom Hibernate configuration will
         * be required.
         */
        // JPAProjectPersistence persistence = new JPAProjectPersistence();
        projectPersistence.createProject(project);

        /* This will create 4 records in the database - 1 in the project table and 3 in the competition table. */

        /* Similarly we may update, delete, and retrieve by different criteria as shown in the demo above. */
    }

    /**
     * <p>
     * Demonstrates the usage of the Web Service.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testWebService() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Arena");
        projectData.setDescription("A new competition arena");

        projectData = service.createProject(projectData);

        System.out.println("Project created with id - " + projectData.getProjectId());
    }
}
