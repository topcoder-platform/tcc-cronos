/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.studio.converter.ProjectToContestConverterImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.Submission;

import junit.framework.TestCase;

/**
 * This is a test case for <code>ContestManagerProjectAdapter</code> class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestManagerProjectAdapterTest extends TestCase {

    /**
     * Represents an instance of ContestManagerProjectAdapter class.
     */
    private ContestManagerProjectAdapter adapter;

    /**
     * Represents an instance of converter.
     */
    private ProjectToContestConverterImpl converter;

    /**
     * Represents an instance of ContestManager.
     */
    private ContestManager manager;

    /**
     * Represents an instance of Project.
     */
    private Project project;

    /**
     * Represents an instance of ProjectType.
     */
    private ProjectType projectType;

    /**
     * Represents an instance of ProjectCategory.
     */
    private ProjectCategory category;

    /**
     * Represents an instance of ProjectStatus.
     */
    private ProjectStatus status;

    /**
     * Sets up the test environment.
     * @throws Exception
     *         to JUnit
     */
    protected void setUp() throws Exception {
        MockContestManager.error = false;
        MockContestManager.invalid = false;

        manager = new MockContestManager();
        converter = new ProjectToContestConverterImpl();
        adapter = new ContestManagerProjectAdapter(manager, converter);

        projectType = new ProjectType(1, "type", "this is a project type.");
        category = new ProjectCategory(2, "j2ee", "this is a project category", projectType);
        status = new ProjectStatus(3, "opening", "this is a project status");

        project = new Project(4, category, status);
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_NAME, "contest name");
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_PROJECT_ID, new Long(123));
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_TC_DIRECT_PROJECT_ID, new Long(1234));
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_FORUM_ID, new Long(232));
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_EVENT_ID, new Long(435));
        Set<Submission> submissions = new HashSet<Submission>();
        submissions.add(new Submission());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_SUBMISSIONS, submissions);
        Set<StudioFileType> fileTypes = new HashSet<StudioFileType>();
        fileTypes.add(new StudioFileType());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_FILE_TYPES, fileTypes);
        Set<ContestResult> results = new HashSet<ContestResult>();
        results.add(new ContestResult());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_RESULTS, results);
        Set<Document> docs = new HashSet<Document>();
        docs.add(new Document());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_DOCUMENTS, docs);
        Set<ContestConfig> configs = new HashSet<ContestConfig>();
        configs.add(new ContestConfig());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONFIG, configs);
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_TYPE, new ContestType());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_START_DATE, new Date());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_END_DATE, new Date());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_WINNER_ANNOUNCEMENT_DEADLINE, new Date());
        project.setCreationUser("12345");
    }

    /**
     * Tears down the test environment.
     * @throws Exception
     *         to JUnit
     */
    protected void tearDown() throws Exception {
        MockContestManager.error = false;
        MockContestManager.invalid = false;
    }

    /**
     * Test for constructor default constructor.
     * <p>
     * Tests it for accuracy, expects non-null instance created.
     * </p>
     */
    public void testCtor1Accuracy() {
        assertNotNull("Should not be null.", new ContestManagerProjectAdapter());
    }

    /**
     * Test for constructor with manager as parameter.
     * <p>
     * Tests it against null argument, expects IAE.
     * </p>
     */
    public void testCtor2WithNullManager() {
        try {
            new ContestManagerProjectAdapter((ContestManager) null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for constructor with manager as parameter.
     * <p>
     * Tests it for accuracy with non-null manager, expects non-null instance created.
     * </p>
     */
    public void testCtor2Accuracy() {
        assertNotNull("Should not be null.", new ContestManagerProjectAdapter(manager));
    }

    /**
     * Test for constructor with jndi name parameter.
     * <p>
     * Tests it against null string, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testCtor3WithNullString() throws Exception {
        try {
            new ContestManagerProjectAdapter((String) null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for constructor with jndi name parameter.
     * <p>
     * Tests it against empty string, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testCtor3WithEmptyString() throws Exception {
        try {
            new ContestManagerProjectAdapter("   ");
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for constructor with jndi name parameter.
     * <p>
     * Tests it against invalid jndi configuration, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testCtor3WithInvalidJNDIConfig() throws Exception {
        try {
            new ContestManagerProjectAdapter("invalid_contest_manager");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * Test for constructor with jndi name parameter.
     * <p>
     * Tests it for accuracy with valid jndi config, expects non-null instance created.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testCtor3Accuracy() throws Exception {
        assertNotNull("Should not be null.", new ContestManagerProjectAdapter("contest_manager"));
    }

    /**
     * Test for constructor with manager and converter parameters.
     * <p>
     * Tests it against null manager, expects IAE.
     * </p>
     */
    public void testCtor4WithNullManager() {
        try {
            new ContestManagerProjectAdapter((ContestManager) null, converter);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for constructor with manager and converter parameters.
     * <p>
     * Tests it against null converter, expects IAE.
     * </p>
     */
    public void testCtor4WithNullConverter() {
        try {
            new ContestManagerProjectAdapter(manager, null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for constructor with manager and converter parameters.
     * <p>
     * Tests it for accuracy, non-null instance should be created.
     * </p>
     */
    public void testCtor4Accuracy() {
        assertNotNull("Should not be null.", new ContestManagerProjectAdapter(manager, converter));
    }

    /**
     * Test for constructor with jndi name and converter.
     * <p>
     * Tests it for accuracy, non-null instance should be created.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testCtor5Accuracy() throws Exception {
        assertNotNull("Should not be null.", new ContestManagerProjectAdapter("contest_manager", converter));
    }

    /**
     * Test for <code>createProject</code> method.
     * <p>
     * Tests it against null null, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testCreateProjectWithNullProject() throws Exception {
        try {
            adapter.createProject(null, "tc");
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>createProject</code> method.
     * <p>
     * Tests it with project that can't be converted successfully, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testCreateProjectWithInvalidProject() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_NAME, new Object());
        try {
            adapter.createProject(project, "tc");
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>createProject</code> method.
     * <p>
     * Tests it with error occurred in contest manager, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testCreateProjectWithErrorOccurredInContestManager() throws Exception {
        MockContestManager.error = true;
        try {
            adapter.createProject(project, "tc");
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>createProject</code> method.
     * <p>
     * Tests it for accuracy, the project should be created successfully.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testCreateProjectAccuracy() throws Exception {
        adapter.createProject(project, "tc");

        Project theProject = adapter.getProject(4);
        assertEquals("Create user mismatched.", "34654", theProject.getCreationUser());
        assertEquals("Description of category is invalid.", "This is a channel", theProject.getProjectCategory()
            .getDescription());
        assertEquals("Project type description is invalid.", "PS", theProject.getProjectCategory()
            .getProjectType().getDescription());
    }

    /**
     * Test for <code>updateProject</code> method.
     * <p>
     * Tests it against null project, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testUpdateProjectWithNullProject() throws Exception {
        try {
            adapter.updateProject(null, "reason", "tc");
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>updateProject</code> method.
     * <p>
     * Tests it with project that can't be converted successfully, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testUpdateProjectWithInvalidProject() throws Exception {
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_NAME, new Object());
        try {
            adapter.updateProject(project, "re", "tc");
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>updateProject</code> method.
     * <p>
     * Tests it with error occurred in contest manager, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testUpdateProjectWithErrorOccurredInContestManager() throws Exception {
        MockContestManager.error = true;
        try {
            adapter.updateProject(project, "reason", "tc");
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>updateProject</code> method.
     * <p>
     * Tests it for accuracy, the project should be updated successfully.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testUpdateProjectAccuracy() throws Exception {
        adapter.updateProject(project, "reason", "tc");

        Project theProject = adapter.getProject(4);
        assertEquals("Create user mismatched.", "34654", theProject.getCreationUser());
        assertEquals("Description of category is invalid.", "This is a channel", theProject.getProjectCategory()
            .getDescription());
        assertEquals("Project type description is invalid.", "PS", theProject.getProjectCategory()
            .getProjectType().getDescription());
    }

    /**
     * Test for <code>getProject</code> method.
     * <p>
     * Tests it against id=0, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetProjectWithInvalidID() throws Exception {
        try {
            adapter.getProject(0);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>getProject</code> method.
     * <p>
     * Tests it with error occurred in contest manager, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetProjectWithErrorInContestManager() throws Exception {
        MockContestManager.error = true;
        try {
            adapter.getProject(1);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>getProject</code> method.
     * <p>
     * Tests it with invalid contest, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetProjectWithInvalidContest() throws Exception {
        MockContestManager.invalid = true;
        try {
            adapter.getProject(1);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>getProject</code> method.
     * <p>
     * Tests it for accuracy, valid project should be returned.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetProjectAccuracy() throws Exception {
        Project theProject = adapter.getProject(1);

        assertEquals("Create user mismatched.", "34654", theProject.getCreationUser());
        assertEquals("Description of category is invalid.", "This is a channel", theProject.getProjectCategory()
            .getDescription());
        assertEquals("Project type description is invalid.", "PS", theProject.getProjectCategory()
            .getProjectType().getDescription());
    }

    /**
     * Test for <code>getProjects</code> method.
     * <p>
     * Tests it against null argument, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetProjectsWithNullArgument() throws Exception {
        try {
            adapter.getProjects(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>getProjects</code> method.
     * <p>
     * Tests it against id array having negative id, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetProjectsWithNegativeID() throws Exception {
        try {
            adapter.getProjects(new long[] {-1, 1, 1, 1 });
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>getProjects</code> method.
     * <p>
     * Tests it for accuracy with {1}, a project should be returned.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetProjectsAccuracy() throws Exception {
        Project[] ps = adapter.getProjects(new long[] {1 });

        assertEquals("There should be one project.", 1, ps.length);
        assertEquals("Create user mismatched.", "34654", ps[0].getCreationUser());
        assertEquals("Description of category is invalid.", "This is a channel", ps[0].getProjectCategory()
            .getDescription());
        assertEquals("Project type description is invalid.", "PS", ps[0].getProjectCategory().getProjectType()
            .getDescription());
    }

    /**
     * Test for <code>searchProjects</code> method.
     * <p>
     * Tests it against null filter, expects IAE.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testSearchProjectsWithNullFilter() throws Exception {
        try {
            adapter.searchProjects(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>searchProjects</code> method.
     * <p>
     * Tests it with error occurred in contest manager, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testSearchProjectWithErrorInContestManager() throws Exception {
        MockContestManager.error = true;
        Filter filter = new EqualToFilter("contest_name", "name");
        try {
            adapter.searchProjects(filter);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>searchProjects</code> method.
     * <p>
     * Tests it with invalid contest, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testSearchProjectWithInvalidContest() throws Exception {
        MockContestManager.invalid = true;
        Filter filter = new EqualToFilter("contest_name", "name");
        try {
            adapter.searchProjects(filter);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>searchProjects</code> method.
     * <p>
     * Tests it for accuracy, one project should be returned.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testSearchProjectAccuracy() throws Exception {
        Project[] ps = adapter.searchProjects(new EqualToFilter("contest_name", "name"));

        assertEquals("There should be one project.", 1, ps.length);
        assertEquals("Create user mismatched.", "34654", ps[0].getCreationUser());
        assertEquals("Description of category is invalid.", "This is a channel", ps[0].getProjectCategory()
            .getDescription());
        assertEquals("Project type description is invalid.", "PS", ps[0].getProjectCategory().getProjectType()
            .getDescription());
    }

    /**
     * Test for <code>getUserProjects</code> method.
     * <p>
     * Tests it with error occurred in contest manager, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetUserProjectsWithErrorInContestManager() throws Exception {
        MockContestManager.error = true;
        try {
            adapter.getUserProjects(1);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>searchProjects</code> method.
     * <p>
     * Tests it with invalid contest, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetUserProjectsWithInvalidContest() throws Exception {
        MockContestManager.invalid = true;
        try {
            adapter.getUserProjects(1);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>searchProjects</code> method.
     * <p>
     * Tests it for accuracy, one project should be returned.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetUserProjectsAccuracy() throws Exception {
        Project[] ps = adapter.getUserProjects(1);

        assertEquals("There should be one project.", 1, ps.length);
        assertEquals("Create user mismatched.", "34654", ps[0].getCreationUser());
        assertEquals("Description of category is invalid.", "This is a channel", ps[0].getProjectCategory()
            .getDescription());
        assertEquals("Project type description is invalid.", "PS", ps[0].getProjectCategory().getProjectType()
            .getDescription());
    }

    /**
     * Test for <code>getAllProjectTypes</code> method.
     * <p>
     * Tests it with error occurred in contest manager, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetAllProjectTypesWithErrorInContestManager() throws Exception {
        MockContestManager.error = true;
        try {
            adapter.getAllProjectTypes();
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>getAllProjectTypes</code> method.
     * <p>
     * Tests it with invalid file type, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetAllProjectTypesWithInvalidFileType() throws Exception {
        MockContestManager.invalid = true;
        try {
            adapter.getAllProjectTypes();
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>getAllProjectTypes</code> method.
     * <p>
     * Tests it for accuracy, a project type should be returned.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetAllProjectTypesAccuracy() throws Exception {
        ProjectType[] types = adapter.getAllProjectTypes();

        assertEquals("Should have one type.", 1, types.length);
        assertEquals("Description mismatched.", "PS", types[0].getDescription());
        assertEquals("Name mismatched.", ".ps", types[0].getName());
    }

    /**
     * Test for <code>getAllProjectCategories</code> method.
     * <p>
     * Tests it with error occurred in contest manager, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetAllProjectCategoriesWithErrorInContestManager() throws Exception {
        MockContestManager.error = true;
        try {
            adapter.getAllProjectCategories();
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>getAllProjectCategories</code> method.
     * <p>
     * Tests it with invalid project category, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetAllProjectCategoriesWithInvalidFileType() throws Exception {
        MockContestManager.invalid = true;
        try {
            adapter.getAllProjectCategories();
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>getAllProjectCategories</code> method.
     * <p>
     * Tests it for accuracy, a project category should be returned.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetAllProjectCategoriesAccuracy() throws Exception {
        ProjectCategory[] cs = adapter.getAllProjectCategories();

        assertEquals("Should have one type.", 1, cs.length);
        assertEquals("Description mismatched.", "This is a channel", cs[0].getDescription());
        assertEquals("Name mismatched.", "news", cs[0].getName());
    }

    /**
     * Test for <code>getAllProjectStatuses</code> method.
     * <p>
     * Tests it with error occurred in contest manager, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetAllProjectStatusesWithErrorInContestManager() throws Exception {
        MockContestManager.error = true;
        try {
            adapter.getAllProjectStatuses();
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>getAllProjectStatuses</code> method.
     * <p>
     * Tests it with invalid contest category, expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetAllProjectStatusesWithInvalidFileType() throws Exception {
        MockContestManager.invalid = true;
        try {
            adapter.getAllProjectStatuses();
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>getAllProjectStatuses</code> method.
     * <p>
     * Tests it for accuracy, a project status should be returned.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetAllProjectStatusesAccuracy() throws Exception {
        ProjectStatus[] ss = adapter.getAllProjectStatuses();

        assertEquals("Should have one type.", 1, ss.length);
        assertEquals("Description mismatched.", "This is a status", ss[0].getDescription());
        assertEquals("Name mismatched.", "name", ss[0].getName());
    }

    /**
     * Test for <code>getAllProjectPropertyTypes</code> method.
     * <p>
     * Tests it for accuracy, empty array should be returned.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetAllProjectPropertyTypesAccuracy() throws Exception {
        ProjectPropertyType[] type = adapter.getAllProjectPropertyTypes();
        assertEquals("Should have no record.", 0, type.length);
    }

    /**
     * Test for <code>getContestManager</code> method.
     * <p>
     * Tests it for accuracy, non-null ContestManager should be returned.
     * </p>
     */
    public void testGetContestManager() {
        assertNotNull("Should not be null.", adapter.getContestManager());
    }

    /**
     * Test for <code>setContestManager</code> method.
     * <p>
     * Tests it against null argument, expects IAE.
     * </p>
     */
    public void testSetContestManagerWithNull() {
        try {
            adapter.setContestManager(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>setContestManager</code> method.
     * <p>
     * Tests it for accuracy with non-null ContestManager, it should be set successfully.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testSetContestManager() throws Exception {
        ContestManager m = new MockContestManager();

        adapter.setContestManager(m);

        assertEquals("Contest manager mismatched.", m, adapter.getContestManager());
    }

    /**
     * Test for <code>getConverter</code> method.
     * <p>
     * Tests it for accuracy, non-null converter should be returned.
     * </p>
     */
    public void testGetConverter() {
        assertNotNull("Should not be null.", adapter.getConverter());
    }

    /**
     * Test for <code>setConverter</code> method.
     * <p>
     * Tests it against null argument, expects IAE.
     * </p>
     */
    public void testSetConverterWithNull() {
        try {
            adapter.setConverter(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test for <code>setConverter</code> method.
     * <p>
     * Tests it for accuracy with non-null converter, it should be set successfully.
     * </p>
     */
    public void testSetConverter() {
        adapter.setConverter(converter);

        assertEquals("Converter mismatched.", converter, adapter.getConverter());
    }

    /**
     * Test for <code>getAllPropectTypes</code> method.
     * <p>
     * Tests it when the manager is not set. Expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetAllProjectTypesWithManagerNotSet() throws Exception {
        adapter = new ContestManagerProjectAdapter();

        try {
            adapter.getAllProjectTypes();
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test for <code>getProjects</code> method.
     * <p>
     * Tests it when the manager is not set. Expects PersistenceException.
     * </p>
     * @throws Exception
     *         to JUnit
     */
    public void testGetProjectsWithManagerNotSet() throws Exception {
        adapter = new ContestManagerProjectAdapter();

        try {
            adapter.getProjects(new long[] {1, 2, 3 });
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        }
    }
}
