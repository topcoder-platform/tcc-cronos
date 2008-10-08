/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import com.topcoder.clients.manager.dao.DAOProjectManager;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.ProjectServiceException;
import com.topcoder.clients.webservices.mock.MockProjectDAO;
import com.topcoder.clients.webservices.mock.MockSessionContext;
import com.topcoder.clients.webservices.mock.TestBase;
import com.topcoder.clients.webservices.mock.TestHelper;
import com.topcoder.clients.webservices.usermapping.impl.JPAUserMappingRetriever;

/**
 * Unit test for {@link ProjectServiceBean}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class ProjectServiceBeanTest extends TestBase {

    /**
     * Represents IAE fail message.
     */
    private static final String T_IAE = "IllegalArgumentException is expected to be thrown.";

    /**
     * Represents ISE fail message.
     */
    private static final String T_ISE = "IllegalStateException is expected to be thrown.";

    /**
     * Represents AFE fail message.
     */
    private static final String T_AFE = "AuthorizationFailedException is expected to be thrown";

    /**
     * Represents PSE fail message.
     */
    private static final String T_PSE = "ProjectServiceException is expected to be thrown.";

    /**
     * Represents session context instance.
     */
    private MockSessionContext session;

    /**
     * Represents project service bean instance.
     */
    private ProjectServiceBean beanLocal;

    /**
     * Setup environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        session = new MockSessionContext();
        beanLocal = new ProjectServiceBean();

        TestHelper.injectPropertyValue(ProjectServiceBean.class,
                "projectManagerFile", beanLocal, "projectManagerFileLocal.properties");
        TestHelper.injectPropertyValue(ProjectServiceBean.class,
                "projectManagerNamespace", beanLocal, "projectManagerNamespace");
        TestHelper.injectPropertyValue(ProjectServiceBean.class,
                "userMappingRetrieverFile", beanLocal, "userMappingRetrieverFileLocal.properties");
        TestHelper.injectPropertyValue(ProjectServiceBean.class,
                "userMappingRetrieverNamespace", beanLocal, "userMappingRetrieverNamespace");
        TestHelper.injectPropertyValue(ProjectServiceBean.class,
                "sessionContext", beanLocal, session);

        beanLocal.initialize();

        JPAUserMappingRetriever jpa =
            (JPAUserMappingRetriever) TestHelper.getPrivateField(ProjectServiceBean.class,
                    beanLocal, "userMappingRetriever");
        jpa.setEntityManager(getEntityManager());
    }

    /**
     * Teardown environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void tearDown() throws Exception {
        session.clearRoles();

        session = null;
        beanLocal = null;
        super.tearDown();
    }

    /**
     * Test for {@link ProjectServiceBean#ProjectServiceBean()}.
     */
    public void testProjectServiceBean() {
        assertNotNull("Fail create new instance.", beanLocal);
    }

    /**
     * Test for {@link ProjectServiceBean#initialize()}.
     */
    public void testInitialize() {
        assertNotNull(TestHelper.getPrivateField(ProjectServiceBean.class,
                beanLocal, "userMappingRetriever"));
        assertNotNull(TestHelper.getPrivateField(ProjectServiceBean.class,
                beanLocal, "log"));
        assertNotNull(TestHelper.getPrivateField(ProjectServiceBean.class,
                beanLocal, "projectManager"));
    }

    /**
     * Test for {@link ProjectServiceBean#initialize()}.
     */
    public void testInitialize_WithInvalidConfiguration() {
        TestHelper.injectPropertyValue(ProjectServiceBean.class,
                "projectManagerFile", beanLocal, "\t \n");

        try {
            beanLocal.initialize();
            fail("ProjectServiceBeanConfigurationException is expected to be thrown.");
        } catch (ProjectServiceBeanConfigurationException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#setVerboseLogging(boolean)}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetVerboseLogging() throws Exception {
        assertFalse("initial value is invalid.", beanLocal.isVerboseLogging());
        beanLocal.setVerboseLogging(true);
        assertTrue("Fail setup verbose logging", beanLocal.isVerboseLogging());
    }

    /**
     * Test for {@link ProjectServiceBean#setLog(com.topcoder.util.log.Log)}.
     */
    public void testSetLog() {
        assertNotNull("fail setup log.", beanLocal.getLog());
        beanLocal.setLog(null);
        assertNull("Fail setup log", beanLocal.getLog());
    }

    /**
     * Test for {@link ProjectServiceBean#createProject(Project, Client)}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateProject() throws Exception {
        Client client = TestHelper.createClient(0, null, null);
        Project project = TestHelper.createProject(0, null, null, null);
        assertEquals("Fail create project.", 0, project.getId());

        session.addRole("admin");
        initRole("admin", "");
        session.setPrincipal(TestHelper.createPrincipal(1, "dev"));

        project.setParentProjectId(0);
        project = beanLocal.createProject(project, client);
        assertTrue("Fail create project.", project.getId() > 0);
    }

    /**
     * Test for {@link ProjectServiceBean#createProject(Project, Client)}.
     *
     * Caused by: Null project.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateProject_WithNullProject() throws Exception {
        Client client = TestHelper.createClient(0, null, null);
        Project project = TestHelper.createProject(0, null, null, null);
        assertEquals("Fail create project.", 0, project.getId());

        session.addRole("admin");
        initRole("admin", "");
        session.setPrincipal(TestHelper.createPrincipal(1, "dev"));

        try {
            beanLocal.createProject(null, client);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#createProject(Project, Client)}.
     *
     * Caused by: Null client.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateProject_WithNullClient() throws Exception {
        Project project = TestHelper.createProject(0, null, null, null);
        assertEquals("Fail create project.", 0, project.getId());

        session.addRole("admin");
        initRole("admin", "");
        session.setPrincipal(TestHelper.createPrincipal(1, "dev"));

        try {
            beanLocal.createProject(project, null);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#createProject(Project, Client)}.
     *
     * Caused by: Null manager.
     * Expected : {@link IllegalStateException}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateProject_WithNullManager() throws Exception {
        Client client = TestHelper.createClient(0, null, null);
        Project project = TestHelper.createProject(0, null, null, null);
        assertEquals("Fail create project.", 0, project.getId());

        session.addRole("admin");
        initRole("admin", "");
        session.setPrincipal(TestHelper.createPrincipal(1, "dev"));

        TestHelper.injectPropertyValue(ProjectServiceBean.class,
                "projectManager", beanLocal, null);

        try {
            beanLocal.createProject(project, client);
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#createProject(Project, Client)}.
     *
     * Caused by: Fail authorization.
     * Expected : {@link AuthorizationFailedException}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateProject_WithFailAuthorization() throws Exception {
        Client client = TestHelper.createClient(0, null, null);
        Project project = TestHelper.createProject(0, null, null, null);
        assertEquals("Fail create project.", 0, project.getId());

        session.addRole("admin");
        initRole("administrator", "");
        session.setPrincipal(TestHelper.createPrincipal(1, "dev"));

        try {
            beanLocal.createProject(project, client);
            fail(T_AFE);
        } catch (AuthorizationFailedException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#createProject(Project, Client)}.
     *
     * Caused by: Fail serives.
     * Expected : {@link ProjectServiceException}.
     *
     * @throws Exception into JUnit.
     */
    public void testCreateProject_WithFailService() throws Exception {
        Client client = TestHelper.createClient(0, null, null);
        Project project = TestHelper.createProject(0, null, null, null);
        assertEquals("Fail create project.", 0, project.getId());

        session.addRole("admin");
        initRole("admin", "");
        session.setPrincipal(TestHelper.createPrincipal(1, "dev"));

        try {
            getProjectDAO().setFlag(true);
            project.setParentProjectId(0);
            beanLocal.createProject(project, client);
            fail(T_PSE);
        } catch (ProjectServiceException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#updateProject(Project)}.
     *
     * @throws Exception into JUnit.
     */
    public void testUpdateProject() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        long t = project.getModifyDate().getTime();
        project = beanLocal.updateProject(project);
        assertTrue("Fail update project", project.getModifyDate().getTime() > t);
    }

    /**
     * Test for {@link ProjectServiceBean#updateProject(Project)}.
     *
     * Caused by: Null project
     * Expected : {@link IllegalArgumentException}
     *
     * @throws Exception into JUnit.
     */
    public void testUpdateProject_WithNullProject() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        try {
            beanLocal.updateProject(null);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#updateProject(Project)}.
     *
     * Caused by: Null manager
     * Expected : {@link IllegalStateException}
     *
     * @throws Exception into JUnit.
     */
    public void testUpdateProject_WithNullManager() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        TestHelper.injectPropertyValue(ProjectServiceBean.class,
                "projectManager", beanLocal, null);

        try {
            beanLocal.updateProject(project);
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#updateProject(Project)}.
     *
     * Caused by: Null manager
     * Expected : {@link AuthorizationFailedException}
     *
     * @throws Exception into JUnit.
     */
    public void testUpdateProject_WithFailAuthorization() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "dev");

        try {
            beanLocal.updateProject(project);
            fail(T_AFE);
        } catch (AuthorizationFailedException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#updateProject(Project)}.
     *
     * Caused by: Fail services
     * Expected : {@link ProjectServiceException}
     *
     * @throws Exception into JUnit.
     */
    public void testUpdateProject_WithFailService() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        try {
            getProjectDAO().setFlag(true);
            beanLocal.updateProject(project);
            fail(T_PSE);
        } catch (ProjectServiceException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#deleteProject(Project)}.
     *
     * @throws Exception into JUnit.
     */
    public void testDeleteProject() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);

        beanLocal.setVerboseLogging(true);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        assertFalse("initial value is invalid", project.isDeleted());
        project = beanLocal.deleteProject(project);
        assertTrue("Fail delete project", project.isDeleted());
    }

    /**
     * Test for {@link ProjectServiceBean#deleteProject(Project)}.
     *
     * Caused by: Null project.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testDeleteProject_WithNullProject() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);

        beanLocal.setVerboseLogging(true);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        try {
            beanLocal.deleteProject(null);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#deleteProject(Project)}.
     *
     * Caused by: Null manager.
     * Expected : {@link IllegalStateException}.
     *
     * @throws Exception into JUnit.
     */
    public void testDeleteProject_WithNullManager() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);

        beanLocal.setVerboseLogging(true);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        TestHelper.injectPropertyValue(ProjectServiceBean.class,
                "projectManager", beanLocal, null);

        try {
            beanLocal.deleteProject(project);
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#deleteProject(Project)}.
     *
     * Caused by: Fail authorization.
     * Expected : {@link AuthorizationFailedException}.
     *
     * @throws Exception into JUnit.
     */
    public void testDeleteProject_WithFailAuthorization() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);

        beanLocal.setVerboseLogging(true);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("dev");
        initRole(null, "developer");

        try {
            beanLocal.deleteProject(project);
            fail(T_AFE);
        } catch (AuthorizationFailedException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#deleteProject(Project)}.
     *
     * Caused by: Fail services.
     * Expected : {@link ProjectServiceException}.
     *
     * @throws Exception into JUnit.
     */
    public void testDeleteProject_WithFailService() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);

        beanLocal.setVerboseLogging(true);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        try {
            getProjectDAO().setFlag(true);
            beanLocal.deleteProject(project);
            fail(T_PSE);
        } catch (ProjectServiceException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#setProjectStatus(Project, ProjectStatus)}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetProjectStatus() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);

        beanLocal.setVerboseLogging(true);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        ProjectStatus status = createProjectStatus();
        assertFalse("Fail setup project status.", status.equals(project.getProjectStatus()));

        project = beanLocal.setProjectStatus(project, status);
        assertTrue("Fail setup project status.", status.equals(project.getProjectStatus()));
    }

    /**
     * Test for {@link ProjectServiceBean#setProjectStatus(Project, ProjectStatus)}.
     *
     * Caused by: Null Project.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetProjectStatus_WithNullProject() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);

        beanLocal.setVerboseLogging(true);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        ProjectStatus status = createProjectStatus();
        try {
            beanLocal.setProjectStatus(null, status);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#setProjectStatus(Project, ProjectStatus)}.
     *
     * Caused by: Null Project status.
     * Expected : {@link IllegalArgumentException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetProjectStatus_WithNullStatus() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);

        beanLocal.setVerboseLogging(true);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        try {
            beanLocal.setProjectStatus(project, null);
            fail(T_IAE);
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#setProjectStatus(Project, ProjectStatus)}.
     *
     * Caused by: Null Manager.
     * Expected : {@link IllegalStateException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetProjectStatus_WithNullManager() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);

        beanLocal.setVerboseLogging(true);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        TestHelper.injectPropertyValue(ProjectServiceBean.class,
                "projectManager", beanLocal, null);

        ProjectStatus status = createProjectStatus();
        try {
            beanLocal.setProjectStatus(project, status);
            fail(T_ISE);
        } catch (IllegalStateException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#setProjectStatus(Project, ProjectStatus)}.
     *
     * Caused by: Fail authorization.
     * Expected : {@link AuthorizationFailedException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetProjectStatus_WithFailAuthorization() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);

        beanLocal.setVerboseLogging(true);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "dev");

        ProjectStatus status = createProjectStatus();
        try {
            beanLocal.setProjectStatus(project, status);
            fail(T_AFE);
        } catch (AuthorizationFailedException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ProjectServiceBean#setProjectStatus(Project, ProjectStatus)}.
     *
     * Caused by: Fail services.
     * Expected : {@link ProjectServiceException}.
     *
     * @throws Exception into JUnit.
     */
    public void testSetProjectStatus_WithFailServices() throws Exception {
        long userId = 1;
        session.addRole("admin");
        initRole("admin", null);
        session.setPrincipal(TestHelper.createPrincipal(userId, "dev"));

        Project p = TestHelper.createProject(0, null, null, null);
        p.setParentProjectId(0);

        beanLocal.setVerboseLogging(true);
        p = beanLocal.createProject(p, TestHelper.createClient(0, null, null));

        Project project = createProject(p.getId());
        createUserProjectMapping(project.getId(), userId);

        session.clearRoles();
        session.addRole("developer");
        initRole(null, "developer");

        ProjectStatus status = createProjectStatus();
        try {
            getProjectDAO().setFlag(true);
            beanLocal.setProjectStatus(project, status);
            fail(T_PSE);
        } catch (ProjectServiceException e) {
            // OK.
        }
    }

    /**
     * Initializa role in bean using injection.
     *
     * @param adminRole
     *      The name of admin role.
     * @param clientProjectUserRole
     *      The name of client and project user role.
     */
    private void initRole(String adminRole, String clientProjectUserRole) {
        TestHelper.injectPropertyValue(ProjectServiceBean.class,
                "administratorRole", beanLocal, adminRole);
        TestHelper.injectPropertyValue(ProjectServiceBean.class,
                "clientAndProjectUserRole", beanLocal, clientProjectUserRole);
    }

    /**
     * Simple getter for Project DAO.
     *
     * @return project DAO instance.
     * @throws Exception into JUnit.
     */
    private MockProjectDAO getProjectDAO() throws Exception {
        DAOProjectManager manager = (DAOProjectManager) TestHelper.getPrivateField(ProjectServiceBean.class,
                beanLocal, "projectManager");
        return (MockProjectDAO) TestHelper.getPrivateField(DAOProjectManager.class, manager, "projectDAO");
    }
}
