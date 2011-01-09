/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ProjectManagerImpl class.
 *
 * @author iamajia
 * @version 1.0
 */
public class ProjectManagerImplTest extends TestCase {
    /**
     * File contains sql statement to initial database.
     */
    private static final String INIT_DB_SQL = "test_files/InitDB.sql";

    /**
     * File contains sql statement to clear database.
     */
    private static final String CLEAR_DB_SQL = "test_files/ClearDB.sql";

    /**
     * This project is used in the test.
     */
    private Project project;

    /**
     * this projectManager is used in the test.
     */
    private ProjectManagerImpl projectManager;

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ProjectManagerImplTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // load config.xml
        ConfigManager.getInstance().add("config.xml");

        // initialize the instance that is used in the test.
        ProjectType projectType = new ProjectType(1, "type1", 1);
        ProjectCategory projectCategory = new ProjectCategory(1, "category1", projectType);
        ProjectStatus projectStatus = new ProjectStatus(1, "status1");
        project = new Project(projectCategory, projectStatus);

        executeSQL(CLEAR_DB_SQL);

        executeSQL(INIT_DB_SQL);

        projectManager = new ProjectManagerImpl();
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        executeSQL(CLEAR_DB_SQL);

        // remove all namespace.
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Accuracy test of <code>ProjectManagerImpl()</code> constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectManagerImplAccuracy1() throws Exception {
        new ProjectManagerImpl();
    }

    /**
     * Accuracy test of <code>ProjectManagerImpl(String ns)</code> constructor.
     *
     * <p>
     * use 'custom.ProjectManagerImpl' namespace.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectManagerImplAccuracy2() throws Exception {
        new ProjectManagerImpl(ProjectManagerImpl.NAMESPACE);
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     *
     * <p>
     * ns is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectManagerImplFailure1() throws Exception {
        try {
            new ProjectManagerImpl(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     *
     * <p>
     * ns is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectManagerImplFailure2() throws Exception {
        try {
            new ProjectManagerImpl("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     *
     * <p>
     *'PersistenceClass' property is missed.
     * </p>
     *
     * <p>
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectManagerImplFailure3() throws Exception {
        try {
            new ProjectManagerImpl("invalid1.ProjectManagerImpl");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     *
     * <p>
     *'ValidatorClass' property is missed.
     * </p>
     *
     * <p>
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectManagerImplFailure4() throws Exception {
        try {
            new ProjectManagerImpl("invalid2.ProjectManagerImpl");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     *
     * <p>
     *'SearchBuilderNamespace' property is missed.
     * </p>
     *
     * <p>
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectManagerImplFailure5() throws Exception {
        try {
            new ProjectManagerImpl("invalid3.ProjectManagerImpl");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     *
     * <p>
     *'PersistenceClass' is String.
     * </p>
     *
     * <p>
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectManagerImplFailure6() throws Exception {
        try {
            new ProjectManagerImpl("invalid4.ProjectManagerImpl");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     *
     * <p>
     * PersistenceClass is empty.
     * </p>
     *
     * <p>
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectManagerImplFailure7() throws Exception {
        try {
            new ProjectManagerImpl("invalid5.ProjectManagerImpl");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     *
     * <p>
     * PersistenceClass is invalid.
     * </p>
     *
     * <p>
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testProjectManagerImplFailure8() throws Exception {
        try {
            new ProjectManagerImpl("invalid6.ProjectManagerImpl");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>createProject(Project project, String operator)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateProjectAccuracy() throws Exception {
        projectManager.createProject(project, "999");
    }

    /**
     * Failure test of <code>createProject(Project project, String operator)</code> method.
     *
     * <p>
     * project is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateProjectFailure1() throws Exception {
        try {
            projectManager.createProject(null, "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createProject(Project project, String operator)</code> method.
     *
     * <p>
     * operator is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateProjectFailure2() throws Exception {
        try {
            projectManager.createProject(project, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createProject(Project project, String operator)</code> method.
     *
     * <p>
     * operator is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateProjectFailure3() throws Exception {
        try {
            projectManager.createProject(project, "   ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>updateProject(Project project, String reason, String operator)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateProjectAccuracy() throws Exception {
        projectManager.createProject(project, "999");
        projectManager.updateProject(project, "reason", "999");
    }

    /**
     * Failure test of <code>updateProject(Project project, String reason, String operator)</code> method.
     *
     * <p>
     * project is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateProjectFailure1() throws Exception {
        try {
            projectManager.updateProject(null, "reason", "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateProject(Project project, String reason, String operator)</code> method.
     *
     * <p>
     * operator is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateProjectFailure2() throws Exception {
        try {
            projectManager.updateProject(project, "reason", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateProject(Project project, String reason, String operator)</code> method.
     *
     * <p>
     * operator is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateProjectFailure3() throws Exception {
        try {
            projectManager.updateProject(project, "reason", "  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateProject(Project project, String reason, String operator)</code> method.
     *
     * <p>
     * reason is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateProjectFailure4() throws Exception {
        try {
            projectManager.updateProject(project, null, "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getProject(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetProjectAccuracy() throws Exception {
        projectManager.getProject(1);
    }

    /**
     * Failure test of <code>getProject(long id)</code> method.
     *
     * <p>
     * id <= 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetProjectFailure() throws Exception {
        try {
            projectManager.getProject(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getProjects(long[] ids)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetProjectsAccuracy() throws Exception {
        long[] ids = {1, 2, 3};
        projectManager.getProjects(ids);
    }

    /**
     * Failure test of <code>getProjects(long[] ids)</code> method.
     *
     * <p>
     * ids is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetProjectsFailure1() throws Exception {
        try {
            projectManager.getProjects(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getProjects(long[] ids)</code> method.
     *
     * <p>
     * ids is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetProjectsFailure2() throws Exception {
        try {
            projectManager.getProjects(new long[0]);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getProjects(long[] ids)</code> method.
     *
     * <p>
     * ids contain an id <= 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetProjectsFailure3() throws Exception {
        try {
            projectManager.getProjects(new long[] {1, 0});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>searchProjects(Filter filter)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testSearchProjectsAccuracy() throws Exception {
        Project[] projects = projectManager.searchProjects(ProjectFilterUtility.buildTypeIdEqualFilter(1));

        Set<Long> ids = new HashSet<Long>();

        for (int i = 0; i < projects.length; ++i) {
            ids.add(new Long(projects[i].getId()));
        }

        Set<Long> expectIds = new HashSet<Long>();
        expectIds.add(new Long(1));
        expectIds.add(new Long(2));
        expectIds.add(new Long(3));
        expectIds.add(new Long(5));

        assertEquals("ids is incorrect..", expectIds, ids);
    }

    /**
     * Failure test of <code>searchProjects(Filter filter)</code> method.
     *
     * <p>
     * filter is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testSearchProjectsFailure() throws Exception {
        try {
            projectManager.searchProjects(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getUserProjects(long user)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetUserProjectsAccuracy() throws Exception {
        Project[] projects = projectManager.getUserProjects(999);

        Set<Long> ids = new HashSet<Long>();

        for (int i = 0; i < projects.length; ++i) {
            ids.add(new Long(projects[i].getId()));
        }

        Set<Long> expectIds = new HashSet<Long>();
        expectIds.add(new Long(4));

        assertEquals("ids is incorrect.", expectIds, ids);
    }

    /**
     * Accuracy test of <code>getAllProjectTypes()</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetAllProjectTypesAccuracy() throws Exception {
        projectManager.getAllProjectTypes();
    }

    /**
     * Accuracy test of <code>getAllProjectCategories()</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetAllProjectCategoriesAccuracy() throws Exception {
        projectManager.getAllProjectCategories();
    }

    /**
     * Accuracy test of <code>getAllProjectStatuses()</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetAllProjectStatusesAccuracy() throws Exception {
        projectManager.getAllProjectStatuses();
    }

    /**
     * Accuracy test of <code>getAllProjectPropertyTypes()</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetAllProjectPropertyTypesAccuracy() throws Exception {
        projectManager.getAllProjectPropertyTypes();
    }

    /**
     * Executes a sql batch contains in a file.
     *
     * @param file the file contains the sql statements.
     *
     * @throws Exception pass to JUnit.
     */
    private void executeSQL(String file) throws Exception {
        // get db connection
        Connection connection = new DBConnectionFactoryImpl(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").createConnection();
        Statement statement = connection.createStatement();

        // get sql statements and add to statement
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;

        while ((line = in.readLine()) != null) {
            if (line.trim().length() != 0) {
                statement.addBatch(line);
            }
        }

        statement.executeBatch();
    }
}
