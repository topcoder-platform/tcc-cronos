/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio.failuretests;

import junit.framework.TestCase;

import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.studio.ContestManagerProjectAdapter;
import com.topcoder.management.project.studio.ProjectToContestConverter;
import com.topcoder.management.project.studio.converter.ProjectToContestConverterImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NullFilter;
import com.topcoder.service.studio.contest.ContestManager;

/**
 * Failure test for class ContestManagerProjectAdapter.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestManagerProjectAdapterFailureTest extends TestCase {

    /**
     * Represents instance of ContestManagerProjectAdapter for test.
     */
    private ContestManagerProjectAdapter adapter;

    /**
     * Represents instance of ContestManager for test.
     */
    private ContestManager manager;

    /**
     * Represents instance of ProjectToContestConverter for test.
     */
    private ProjectToContestConverter converter;

    /**
     * Represents instance of Project for test.
     */
    private Project project;

    /**
     * Represents instance of ContestManager for test.
     */
    private MockContestManager errorContestManager;

    /**
     * Represents instance of ContestManager for test.
     */
    private MockContestManager invalidContestManager;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        adapter = new ContestManagerProjectAdapter();
        manager = new MockContestManager();
        converter = new ProjectToContestConverterImpl();
        ProjectType projectType = new ProjectType(10, "project type");
        ProjectCategory projectCategory = new ProjectCategory(1, "project category", projectType);
        project = new Project(10, projectCategory, new ProjectStatus(10, "project status"));
        errorContestManager = new MockContestManager();
        errorContestManager.setError(true);
        invalidContestManager = new MockContestManager();
        invalidContestManager.setInvalid(true);
        TestHelper.deploy();
        super.setUp();
    }

    /**
     * Failure test for ctor method ContestManagerProjectAdapter(ContestManager manager). With null manager,
     * IllegalArgumentException should be thrown.
     */
    public void testContestManagerProjectAdapter_Null() {
        try {
            new ContestManagerProjectAdapter((ContestManager) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for ctor method ContestManagerProjectAdapter(String jndiName). With null jndiName,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testContestManagerProjectAdapter_JndiName_Null() throws Exception {
        try {
            new ContestManagerProjectAdapter((String) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for ctor method ContestManagerProjectAdapter(String jndiName). With empty jndiName,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testContestManagerProjectAdapter_JndiName_Empty() throws Exception {
        try {
            new ContestManagerProjectAdapter(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for ctor method ContestManagerProjectAdapter(String jndiName). With jndiName
     * "InvalidTypeContestManager", ConfigurationException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testContestManagerProjectAdapter_JndiName_InvalidTypeContestManager() throws Exception {
        try {
            new ContestManagerProjectAdapter("java:comp/env/InvalidTypeContestManager");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for ctor method ContestManagerProjectAdapter(String jndiName). With jndiName "NullContestManager",
     * ConfigurationException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testContestManagerProjectAdapter_JndiName_NullContestManager() throws Exception {
        try {
            new ContestManagerProjectAdapter("java:comp/env/NullContestManager");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for ctor method ContestManagerProjectAdapter(String jndiName). With jndiName
     * "NotFoundContestManager", ConfigurationException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testContestManagerProjectAdapter_JndiName_NotFoundContestManager() throws Exception {
        try {
            new ContestManagerProjectAdapter("java:comp/env/NotFoundContestManager");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for ctor method ContestManagerProjectAdapter(ContestManager manager, ProjectToContestConverter
     * converter). With jndiName "NotFoundContestManager", IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testContestManagerProjectAdapter_ManagerConverter_NullManager() throws Exception {
        try {
            new ContestManagerProjectAdapter((ContestManager) null, converter);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for ctor method ContestManagerProjectAdapter(ContestManager manager, ProjectToContestConverter
     * converter). With jndiName "NotFoundContestManager", IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testContestManagerProjectAdapter_ManagerConverter_NullConverter() throws Exception {
        try {
            new ContestManagerProjectAdapter(manager, (ProjectToContestConverter) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for ctor method ContestManagerProjectAdapter(String jndiName, ProjectToContestConverter converter).
     * With null jndiName, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testContestManagerProjectAdapter_JndiNameConverter_Null() throws Exception {
        try {
            new ContestManagerProjectAdapter((String) null, converter);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for ctor method ContestManagerProjectAdapter(String jndiName, ProjectToContestConverter converter).
     * With empty jndiName, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testContestManagerProjectAdapter_JndiNameConverter_Empty() throws Exception {
        try {
            new ContestManagerProjectAdapter(" ", converter);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for ctor method ContestManagerProjectAdapter(String jndiName, ProjectToContestConverter converter).
     * With jndiName "InvalidTypeContestManager", ConfigurationException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testContestManagerProjectAdapter_JndiNameConverter_InvalidTypeContestManager() throws Exception {
        try {
            new ContestManagerProjectAdapter("java:comp/env/InvalidTypeContestManager", converter);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for ctor method ContestManagerProjectAdapter(String jndiName, ProjectToContestConverter converter).
     * With jndiName "NullContestManager", ConfigurationException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testContestManagerProjectAdapter_JndiNameConverter_NullContestManager() throws Exception {
        try {
            new ContestManagerProjectAdapter("java:comp/env/NullContestManager", converter);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for ctor method ContestManagerProjectAdapter(String jndiName, ProjectToContestConverter converter).
     * With jndiName "NotFoundContestManager", ConfigurationException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testContestManagerProjectAdapter_JndiNameConverter_NotFoundContestManager() throws Exception {
        try {
            new ContestManagerProjectAdapter("java:comp/env/NotFoundContestManager", converter);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for ctor method ContestManagerProjectAdapter(String jndiName, ProjectToContestConverter converter).
     * With jndiName "ValidContestManager", null converter, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testContestManagerProjectAdapter_JndiNameConverter_NullConverter() throws Exception {
        try {
            new ContestManagerProjectAdapter("java:comp/env/ValidContestManager", (ProjectToContestConverter) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createProject(Project project, String operator). With null project,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateProject_NullProject() throws Exception {
        try {
            adapter.createProject((Project) null, "operator");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method createProject(Project project, String operator). With error occurs while createContest,
     * PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateProject_Error() throws Exception {
        adapter.setContestManager(errorContestManager);
        try {
            adapter.createProject(project, "operator");
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateProject(Project project, String reason, String operator). With null project,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProject_NullProject() throws Exception {
        try {
            adapter.updateProject((Project) null, "reason", "operator");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateProject(Project project, String reason, String operator). With error occurs while
     * updateProject, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProject_Error() throws Exception {
        adapter.setContestManager(errorContestManager);
        try {
            adapter.updateProject(project, "reason", "operator");
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getProject(long id). With negative id, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProject_NegativeId() throws Exception {
        adapter.setContestManager(manager);
        try {
            adapter.getProject(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method getProject(long id). With null manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProject_NullManager() throws Exception {
        try {
            adapter.getProject(1L);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getProject(long id). With invalid manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProject_InvalidManager() throws Exception {
        adapter.setContestManager(errorContestManager);
        adapter.setConverter(converter);
        try {
            adapter.getProject(1L);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getProject(long id). With invalid manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProject_InvalidConverter() throws Exception {
        adapter.setContestManager(invalidContestManager);
        adapter.setConverter(converter);
        try {
            adapter.getProject(1L);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getProjects(long[] ids). With negative id, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjects_NegativeId() throws Exception {
        adapter.setContestManager(manager);
        try {
            adapter.getProjects(new long[] {1, 2, -1 });
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method getProjects(long[] ids). With null manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjects_NullManager() throws Exception {
        try {
            adapter.getProjects(new long[] {1L });
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getProjects(long[] ids). With invalid manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjects_InvalidManager() throws Exception {
        adapter.setContestManager(errorContestManager);
        adapter.setConverter(converter);
        try {
            adapter.getProjects(new long[] {1L });
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getProjects(long[] ids). With invalid manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjects_InvalidConverter() throws Exception {
        adapter.setContestManager(invalidContestManager);
        adapter.setConverter(converter);
        try {
            adapter.getProjects(new long[] {1L });
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method searchProjects(Filter filter). With null filter, IllegalArgumentException should be
     * thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchProjects_NullFilter() throws Exception {
        Filter filter = null;
        adapter.setContestManager(manager);
        try {
            adapter.searchProjects(filter);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method searchProjects(Filter filter). With null manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchProjects_NullManager() throws Exception {
        Filter filter = new NullFilter("*");
        try {
            adapter.searchProjects(filter);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method searchProjects(Filter filter). With invalid manager, PersistenceException should be
     * thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchProjects_InvalidManager() throws Exception {
        Filter filter = new NullFilter("*");
        adapter.setContestManager(errorContestManager);
        adapter.setConverter(converter);
        try {
            adapter.searchProjects(filter);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method searchProjects(Filter filter). With invalid manager, PersistenceException should be
     * thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSearchProjects_InvalidConverter() throws Exception {
        Filter filter = new NullFilter("*");
        adapter.setContestManager(invalidContestManager);
        adapter.setConverter(converter);
        try {
            adapter.searchProjects(filter);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getUserProjects(long user). With null manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserProjects_NullManager() throws Exception {
        try {
            adapter.getUserProjects(2L);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getUserProjects(long user). With invalid manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserProjects_InvalidManager() throws Exception {
        adapter.setContestManager(errorContestManager);
        adapter.setConverter(converter);
        try {
            adapter.getUserProjects(2L);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getUserProjects(long user). With invalid manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserProjects_InvalidConverter() throws Exception {
        adapter.setContestManager(invalidContestManager);
        adapter.setConverter(converter);
        try {
            adapter.getUserProjects(2L);
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getAllProjectTypes(). With null manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllProjectTypes_NullManager() throws Exception {
        try {
            adapter.getAllProjectTypes();
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getAllProjectTypes(). With invalid manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllProjectTypes_InvalidManager() throws Exception {
        adapter.setContestManager(errorContestManager);
        adapter.setConverter(converter);
        try {
            adapter.getAllProjectTypes();
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getAllProjectTypes(). With invalid manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllProjectTypes_InvalidConverter() throws Exception {
        adapter.setContestManager(invalidContestManager);
        adapter.setConverter(converter);
        try {
            adapter.getAllProjectTypes();
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getAllProjectCategories(). With null manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllProjectCategories_NullManager() throws Exception {
        try {
            adapter.getAllProjectCategories();
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getAllProjectCategories(). With invalid manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllProjectCategories_InvalidManager() throws Exception {
        adapter.setContestManager(errorContestManager);
        adapter.setConverter(converter);
        try {
            adapter.getAllProjectCategories();
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for method getAllProjectCategories(). With invalid manager, PersistenceException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllProjectCategories_InvalidConverter() throws Exception {
        adapter.setContestManager(invalidContestManager);
        adapter.setConverter(converter);
        try {
            adapter.getAllProjectCategories();
            fail("PersistenceException expected.");
        } catch (PersistenceException e) {
            // expected
        }
    }
}
