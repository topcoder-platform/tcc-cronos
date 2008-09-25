/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.accuracytests.AccuracyTestHelper;
import com.topcoder.clients.webservices.accuracytests.mock.MockPrincipal;
import com.topcoder.clients.webservices.accuracytests.mock.MockSessionContext;
import com.topcoder.clients.webservices.accuracytests.mock.MockUserMappingRetriever;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import junit.framework.TestCase;

/**
 * This class contains unit tests for <code>ProjectServiceBean</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestProjectServiceBean extends TestCase {
    /**
     * <p>
     * Represents the instance of ProjectServiceBean used for test.
     * </p>
     */
    private ProjectServiceBean bean = null;

    /**
     * <p>
     * Represents the instance of Project used for test.
     * </p>
     */
    private Project project = null;

    /**
     * <p>
     * Represents the instance of Client used for test.
     * </p>
     */
    private Client client = null;

    /**
     * Set Up the test environment before testing.
     * 
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        bean = new ProjectServiceBean();

        AccuracyTestHelper.setField(bean, "projectManagerFile", new File(
            "test_files/accuracy/configuration.properties").getAbsolutePath());
        AccuracyTestHelper.setField(bean, "projectManagerNamespace", "ProjectServiceBean");
        AccuracyTestHelper.setField(bean, "userMappingRetrieverFile", new File(
            "test_files/accuracy/configuration.properties").getAbsolutePath());
        AccuracyTestHelper.setField(bean, "userMappingRetrieverNamespace", "UserMappingRetriever");
        AccuracyTestHelper.setField(bean, "administratorRole", Roles.ADMIN);
        AccuracyTestHelper.setField(bean, "clientAndProjectUserRole", Roles.USER);

        // Initialize SessionContext
        MockSessionContext context = new MockSessionContext();
        Principal principal = new MockPrincipal();
        MockSessionContext.setPrincipal(principal);
        AccuracyTestHelper.setField(bean, "sessionContext", context);
        MockSessionContext.setRoles(new String[] { Roles.ADMIN, Roles.USER });

        bean.setVerboseLogging(true);

        bean.initialize();

        project = new Project();
        project.setName("pname");
        project.setParentProjectId(1);

        client = new Client();
        client.setName("cname");
    }

    /**
     * Clean up the test environment after testing.
     * 
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        bean = null;
        MockSessionContext.setRoles(null);
        MockSessionContext.setPrincipal(null);
        MockUserMappingRetriever.setClients(new ArrayList<Client>());
        MockUserMappingRetriever.setProjects(new ArrayList<Project>());
        MockUserMappingRetriever.setFail(false);
    }

    /**
     * Function test : Tests <code>ProjectServiceBean()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testProjectServiceBeanAccuracy() throws Exception {
        assertNotNull("Should not be null.", bean);
    }

    /**
     * Function test : Tests <code>createProject(Project project, Client client)</code> method for
     * accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testCreateProjectAccuracy() throws Exception {
        MockSessionContext.setRoles(new String[] { Roles.USER });
        AccuracyTestHelper.setField(bean, "clientAndProjectUserRole", Roles.USER);

        Project result = bean.createProject(project, client);

        assertNotNull("Client object should be returned.", result);
        assertEquals("Should be AAAA.", "AAAA", result.getName());
    }

    /**
     * Function test : Tests <code>updateProject(Project project)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testUpdateProjectAccuracy() throws Exception {
    }

    /**
     * Function test : Tests <code>deleteProject(Project project)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testDeleteProjectAccuracy() throws Exception {
        MockSessionContext.setRoles(new String[] { Roles.ADMIN });
        AccuracyTestHelper.setField(bean, "administratorRole", Roles.ADMIN);

        Project result = bean.deleteProject(project);

        assertNotNull("Should not be null.", result);
        assertEquals("Should be CCCC.", "CCCC", result.getName());
    }

    /**
     * Function test : Tests <code>setProjectStatus(Project project, ProjectStatus status)</code>
     * method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testSetProjectStatusAccuracy() throws Exception {
        MockSessionContext.setRoles(new String[] { Roles.ADMIN });
        AccuracyTestHelper.setField(bean, "administratorRole", Roles.ADMIN);

        client.setName("name");
        ProjectStatus status = new ProjectStatus();
        Project result = bean.setProjectStatus(project, status);

        assertNotNull("Should not be null.", result);
        assertEquals("Should be EEEE.", "EEEE", result.getName());
    }

    /**
     * Function test : Tests <code>isVerboseLogging()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testIsVerboseLoggingAccuracy() throws Exception {
        assertTrue("should be true", bean.isVerboseLogging());
    }

    /**
     * Function test : Tests <code>setVerboseLogging(boolean verboseLogging)</code> method for
     * accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testSetVerboseLoggingAccuracy() throws Exception {
        bean.setVerboseLogging(false);
        assertFalse("should be false", bean.isVerboseLogging());
    }

    /**
     * Function test : Tests <code>getLog()</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testGetLogAccuracy() throws Exception {
        assertNotNull("shouldn't be null.", bean.getLog());
    }

    /**
     * Function test : Tests <code>setLog(Log log)</code> method for accuracy.
     * 
     * @throws Exception to JUnit.
     */
    public void testSetLogAccuracy() throws Exception {
        Log log = LogManager.getLog("name");
        bean.setLog(log);
        assertEquals("Should be log.", log, bean.getLog());
    }

}