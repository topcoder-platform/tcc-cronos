/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio.accuracytests;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.studio.ContestManagerProjectAdapter;
import com.topcoder.management.project.studio.ProjectToContestConverter;
import com.topcoder.management.project.studio.converter.ProjectToContestConverterImpl;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.ContestConfig.Identifier;
import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.Submission;

import junit.extensions.TestSetup;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Accuracy test cases for the class <code>ContestManagerProjectAdapter</code>.
 *
 * @author waits
 * @version 1.0
 */
public class ContestManagerProjectAdapterAccTest extends TestCase {
    /** ContestManagerProjectAdapter instance to test against. */
    private static ContestManagerProjectAdapter instance;

    /** The ContestManager to plugin into the adapter. */
    private static ContestManager contestManager = null;

    /** The ProjectToContestConverter to plugin into the adapter. */
    private static ProjectToContestConverter converter = null;

    /**
     * <p>
     * Return All the test case for the ContestManagerProjectAdapter.
     * </p>
     *
     * @return all the accuracy test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ContestManagerProjectAdapterAccTest.class);

        /**
         * <p>
         * Setup the accuracy test.
         * </p>
         */
        TestSetup wrapper = new TestSetup(suite) {
                /**
                 * Create instances.
                 */
                protected void setUp() throws Exception {
                    contestManager = new MockContestManager();
                    converter = new ProjectToContestConverterImpl();
                    instance = new ContestManagerProjectAdapter(contestManager);
                    instance.setConverter(converter);
                }

                protected void tearDown() throws Exception {
                    super.tearDown();
                }
            };

        return wrapper;
    }

    /**
     * <p>
     * Test the createProject method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testCreateProject() throws Exception {
        Project project = createProjectInstance();
        assertProject(project);
    }

    /**
     * <p>
     * Test the updateProject method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testUpdateProject() throws Exception {
        Project project = createProjectInstance();
        //update it
        instance.updateProject(project, getName(), getName());

        //retrieve it
        Contest contest = ((MockContestManager) contestManager).getContest();
        //verify it
        assertProjectContest(project, contest);
    }

    /**
     * <p>
     * Test the getAllProjectCategories method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testgetAllProjectCategories() throws Exception {
        ProjectCategory[] categories = instance.getAllProjectCategories();
        assertEquals("The size of categories is invalid.", 1, categories.length);

        assertEquals("The desc is invalid.", "PS", categories[0].getDescription());
        assertEquals("The name is invalid.", "PS", categories[0].getName());
        assertEquals("The id is invalid.", 1, categories[0].getId());
        assertEquals("The id is invalid.", 1, categories[0].getProjectType().getId());
        assertEquals("The desc is invalid.", "PS", categories[0].getProjectType().getDescription());
        assertEquals("The desc is invalid.", ".ps", categories[0].getProjectType().getName());
    }

    /**
     * <p>
     * Test getAllProjectStatuses method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetAllProjectStatuses() throws Exception {
        ProjectStatus[] projectStatus = instance.getAllProjectStatuses();

        assertEquals("The size of project status is invalid.", 2, projectStatus.length);
        assertEquals("The desc of the status is invalid.", "This is a status", projectStatus[0].getDescription());
        assertEquals("The desc of the status is invalid.", "register", projectStatus[0].getName());
        assertEquals("The desc of the status is invalid.", 1, projectStatus[0].getId());
        assertEquals("The desc of the status is invalid.", "This is a status", projectStatus[1].getDescription());
        assertEquals("The desc of the status is invalid.", "review", projectStatus[1].getName());
        assertEquals("The desc of the status is invalid.", 2, projectStatus[1].getId());
    }

    /**
     * <p>
     * Test getAllProjectPropertyTypes method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetAllProjectPropertyTypes() throws Exception {
        assertEquals("The size of property type is invalid.", 0, instance.getAllProjectPropertyTypes().length);
    }

    /**
     * <p>
     * Test the getAllProjectTypes method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetAllProjectTypes() throws Exception {
        ProjectType[] types = instance.getAllProjectTypes();
        assertEquals("The id is invalid.", 1, types[0].getId());
        assertEquals("The desc is invalid.", "PS", types[0].getDescription());
        assertEquals("The desc is invalid.", ".ps", types[0].getName());
    }

    /**
     * <p>
     * Test the getProject method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetProject() throws Exception {
        Project project = instance.getProject(1);

        assertProject(project);
    }

    /**
     * <p>
     * Test getUserProjects method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetUserProjects() throws Exception {
        Project[] projects = instance.getUserProjects(1);
        assertEquals("The size of projects is invald.", 1, projects.length);
        assertProject(projects[0]);
    }

    /**
     * <p>
     * Test the searchProjects method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testSearchProjects_projectId() throws Exception {
        EqualToFilter filter = new EqualToFilter(ProjectFilterUtility.PROJECT_TYPE_ID, 1L);
        Project[] projects = instance.searchProjects(filter);
        assertEquals("The size of projects is invald.", 1, projects.length);
        assertProject(projects[0]);
    }

    /**
     * <p>
     * Test the searchProjects method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testSearchProjects_projectTypeName() throws Exception {
        EqualToFilter filter = new EqualToFilter(ProjectFilterUtility.PROJECT_TYPE_NAME, "Custom");
        Project[] projects = instance.searchProjects(filter);
        assertEquals("The size of projects is invald.", 1, projects.length);
        assertProject(projects[0]);
    }

    /**
     * <p>
     * Test the searchProjects method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testSearchProjects_categoryId() throws Exception {
        EqualToFilter filter = new EqualToFilter(ProjectFilterUtility.PROJECT_CATEGORY_ID, 1L);
        Project[] projects = instance.searchProjects(filter);
        assertEquals("The size of projects is invald.", 1, projects.length);
        assertProject(projects[0]);
    }

    /**
     * <p>
     * Test the searchProjects method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testSearchProjects_categoryName() throws Exception {
        EqualToFilter filter = new EqualToFilter(ProjectFilterUtility.PROJECT_CATEGORY_NAME, "JAVA");
        Project[] projects = instance.searchProjects(filter);
        assertEquals("The size of projects is invald.", 1, projects.length);
        assertProject(projects[0]);
    }

    /**
     * <p>
     * Test the searchProjects method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testSearchProjects_StatusId() throws Exception {
        EqualToFilter filter = new EqualToFilter(ProjectFilterUtility.PROJECT_STATUS_ID, 3L);
        Project[] projects = instance.searchProjects(filter);
        assertEquals("The size of projects is invald.", 1, projects.length);
        assertProject(projects[0]);
    }

    /**
     * <p>
     * Test the searchProjects method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testSearchProjects_statusName() throws Exception {
        EqualToFilter filter = new EqualToFilter(ProjectFilterUtility.PROJECT_STATUS_NAME, "active");
        Project[] projects = instance.searchProjects(filter);
        assertEquals("The size of projects is invald.", 1, projects.length);
        assertProject(projects[0]);
    }

    /**
     * <p>
     * Test the searchProjects method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testSearchProjects_projectPropertyName()
        throws Exception {
        EqualToFilter filter = new EqualToFilter(ProjectToContestConverterImpl.PROPERTY_EVENT_ID, 1L);
        Project[] projects = instance.searchProjects(filter);
        assertEquals("The size of projects is invald.", 1, projects.length);
        assertProject(projects[0]);
    }

    /**
     * <p>
     * Test the searchProjects method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testSearchProjects_propertyValue() throws Exception {
        EqualToFilter filter = new EqualToFilter(ProjectFilterUtility.PROJECT_PROPERTY_VALUE, 1L);
        Project[] projects = instance.searchProjects(filter);
        assertEquals("The size of projects is invald.", 1, projects.length);
        assertProject(projects[0]);
    }

    /**
     * <p>
     * Test the searchProjects method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testSearchProjects_combo() throws Exception {
        EqualToFilter filter = new EqualToFilter(ProjectFilterUtility.PROJECT_PROPERTY_NAME, "AutoPilotOption");
        EqualToFilter anotherFilter = new EqualToFilter(ProjectFilterUtility.PROJECT_PROPERTY_VALUE, 1L);
        AndFilter andFilter = new AndFilter(filter, anotherFilter);
        NotFilter notFilter = new NotFilter(andFilter);
        EqualToFilter eFilter = new EqualToFilter(ProjectFilterUtility.PROJECT_PROPERTY_VALUE, 1L);
        OrFilter orFilter = new OrFilter(notFilter, eFilter);

        Project[] projects = instance.searchProjects(orFilter);
        assertEquals("The size of projects is invald.", 1, projects.length);
        assertProject(projects[0]);
    }

    /**
     * Verify the project.
     *
     * @param project Project to verify
     *
     * @throws Exception into JUnit
     */
    private void assertProject(Project project) throws Exception {
        //create it
        instance.createProject(project, getName());

        //retrieve it
        Contest contest = ((MockContestManager) contestManager).getContest();
        //verify it
        assertProjectContest(project, contest);
    }

    /**
     * <p>
     * Verify the project and converted result.
     * </p>
     *
     * @param project expected Project instance
     * @param contest contest to verify
     */
    @SuppressWarnings("unchecked")
    private void assertProjectContest(Project project, Contest contest) {
        assertEquals("The property is invalid.", project.getId(), contest.getContestId().longValue());
        assertEquals("The property is invalid.",
            project.getProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_NAME), contest.getName());
        assertEquals("The property is invalid.",
            ((Long) project.getProperty(ProjectToContestConverterImpl.PROPERTY_PROJECT_ID)).longValue(),
            contest.getProjectId().longValue());
        assertEquals("The property is invalid.",
            ((Long) project.getProperty(ProjectToContestConverterImpl.PROPERTY_TC_DIRECT_PROJECT_ID)).longValue(),
            contest.getTcDirectProjectId().longValue());
        assertEquals("The property is invalid.",
            ((Long) project.getProperty(ProjectToContestConverterImpl.PROPERTY_FORUM_ID)).longValue(),
            contest.getForumId().longValue());
        assertEquals("The property is invalid.",
            ((Long) project.getProperty(ProjectToContestConverterImpl.PROPERTY_EVENT_ID)).longValue(),
            contest.getEventId().longValue());
        assertEquals("The property is invalid.",
            ((ContestType) project.getProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_TYPE)).getDescription(),
            contest.getContestType().getDescription());
        assertEquals("The property is invalid.",
            ((Date) project.getProperty(ProjectToContestConverterImpl.PROPERTY_START_DATE)).getTime(),
            contest.getStartDate().getTime());
        assertEquals("The property is invalid.",
            ((Date) project.getProperty(ProjectToContestConverterImpl.PROPERTY_END_DATE)).getTime(),
            contest.getEndDate().getTime());
        assertEquals("The property is invalid.",
            ((Date) project.getProperty(ProjectToContestConverterImpl.PROPERTY_WINNER_ANNOUNCEMENT_DEADLINE)).getTime(),
            contest.getWinnerAnnoucementDeadline().getTime());
        assertEquals("The property is invalid.", project.getCreationUser(), String.valueOf(contest.getCreatedUser()));
        assertEquals("The property is invalid.",
            ((Set<Submission>) project.getProperty(ProjectToContestConverterImpl.PROPERTY_SUBMISSIONS)).size(),
            contest.getSubmissions().size());
        assertEquals("The property is invalid.",
            ((Set<StudioFileType>) project.getProperty(ProjectToContestConverterImpl.PROPERTY_FILE_TYPES)).size(),
            contest.getFileTypes().size());
        assertEquals("The property is invalid.",
            ((Set<ContestResult>) project.getProperty(ProjectToContestConverterImpl.PROPERTY_RESULTS)).size(),
            contest.getResults().size());
        assertEquals("The property is invalid.",
            ((Set<Document>) project.getProperty(ProjectToContestConverterImpl.PROPERTY_DOCUMENTS)).size(),
            contest.getDocuments().size());
        assertEquals("The property is invalid.",
            ((Set<ContestResult>) project.getProperty(ProjectToContestConverterImpl.PROPERTY_CONFIG)).size(),
            contest.getConfig().size());
    }

    /**
     * <p>
     * Create project instance for testing.
     * </p>
     *
     * @return Project instance
     */
    protected static Project createProjectInstance() {
        ProjectType projectType = new ProjectType(1, "Custom", "this is a project type.");
        ProjectCategory category = new ProjectCategory(2, "JAVA", "Java project", projectType);
        ProjectStatus status = new ProjectStatus(3, "active", "It is not closed yet");
        Project project = new Project(4, category, status);

        //add properties
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_NAME, "contest name");
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_PROJECT_ID, new Long(123));
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_TC_DIRECT_PROJECT_ID, new Long(1234));
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_FORUM_ID, new Long(232));
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_EVENT_ID, new Long(435));

        ContestType contestType = new ContestType();
        contestType.setDescription("description");
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONTEST_TYPE, contestType);
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_START_DATE, new Date(10000));
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_END_DATE, new Date(20000));
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_WINNER_ANNOUNCEMENT_DEADLINE, new Date(20000));
        project.setCreationUser("1");

        Set<Submission> submissions = new HashSet<Submission>();
        submissions.add(new Submission());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_SUBMISSIONS, submissions);

        Set<StudioFileType> fileTypes = new HashSet<StudioFileType>();
        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("PS");
        fileType.setExtension(".ps");
        fileType.setImageFile(false);
        fileType.setStudioFileType(34);

        fileTypes.add(fileType);
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_FILE_TYPES, fileTypes);

        Set<ContestResult> results = new HashSet<ContestResult>();
        results.add(new ContestResult());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_RESULTS, results);

        Set<Document> docs = new HashSet<Document>();
        docs.add(new Document());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_DOCUMENTS, docs);

        Set<ContestConfig> configs = new HashSet<ContestConfig>();

        ContestConfig o = new ContestConfig();
        Identifier id = new Identifier();
        id.setContest(new Contest());
        id.setProperty(new ContestProperty());
        o.setId(id);
        configs.add(o);
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONFIG, configs);

        return project;
    }
}
