/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio.stresstests;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.studio.ContestManagerProjectAdapter;
import com.topcoder.management.project.studio.converter.ProjectToContestConverterImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.Submission;

/**
 * Stress tests for <code>ContestManagerProjectAdapter</code>
 * 
 * @author hfx
 * @version 1.0
 */
public class ContestManagerProjectAdapterTests extends BaseStressTest {
    /**
     * Represents the test loop count of the stress tests.
     */
    private static int BURDEN1 = 20;
    /**
     * Represents the test loop count of the stress tests.
     */
    private static int BURDEN2 = 40;

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
     * Sets up the test environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        // create test objects
        manager = new MockContestManager();
        converter = new ProjectToContestConverterImpl();
        adapter = new ContestManagerProjectAdapter(manager, converter);
        project = createProject();
    }

    static Project createProject() {
        Project project = new Project(1, new ProjectCategory(2, "category",
                "icon contest", new ProjectType(1, "type", "icon")),
                new ProjectStatus(1, "Open", "cockpit contest"));
        // set project
        project.setProperty(
                ProjectToContestConverterImpl.PROPERTY_CONTEST_NAME, "cockpit");
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_PROJECT_ID,
                new Long(3333));
        project.setProperty(
                ProjectToContestConverterImpl.PROPERTY_TC_DIRECT_PROJECT_ID,
                new Long(4444));
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_FORUM_ID,
                new Long(5555));
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_EVENT_ID,
                new Long(6666));
        Set<Submission> submissions = new HashSet<Submission>();
        submissions.add(new Submission());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_SUBMISSIONS,
                submissions);
        Set<StudioFileType> fileTypes = new HashSet<StudioFileType>();
        fileTypes.add(new StudioFileType());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_FILE_TYPES,
                fileTypes);
        Set<ContestResult> results = new HashSet<ContestResult>();
        results.add(new ContestResult());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_RESULTS,
                results);
        Set<Document> docs = new HashSet<Document>();
        docs.add(new Document());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_DOCUMENTS,
                docs);
        Set<ContestConfig> configs = new HashSet<ContestConfig>();
        configs.add(new ContestConfig());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_CONFIG,
                configs);
        project.setProperty(
                ProjectToContestConverterImpl.PROPERTY_CONTEST_TYPE,
                new ContestType());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_START_DATE,
                new Date());
        project.setProperty(ProjectToContestConverterImpl.PROPERTY_END_DATE,
                new Date());
        project
                .setProperty(
                        ProjectToContestConverterImpl.PROPERTY_WINNER_ANNOUNCEMENT_DEADLINE,
                        new Date());
        project.setCreationUser("8888");
        return project;
    }

    /**
     * Test method ContestManagerProjectAdapter#createProject under
     * <code>BURDEN1</code>.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_CreateProject_Stress1() throws Exception {
        start();
        for (int i = 0; i < BURDEN1; i++) {
            adapter.createProject(project, "no op");
        }
        stop();
        System.out
                .println("Run ContestManagerProjectAdapter#createProject for "
                        + BURDEN1 + " times consumes " + getMilliseconds()
                        + " milliseconds.");
    }

    /**
     * Test method ContestManagerProjectAdapter#createProject under
     * <code>BURDEN2</code>.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_CreateProject_Stress2() throws Exception {
        start();
        for (int i = 0; i < BURDEN2; i++) {
            adapter.createProject(project, "no op");
        }
        stop();
        System.out
                .println("Run ContestManagerProjectAdapter#createProject for "
                        + BURDEN2 + " times consumes " + getMilliseconds()
                        + " milliseconds.");
    }

    /**
     * Test method ContestManagerProjectAdapter#getProject under
     * <code>BURDEN1</code>.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_GetProject_Stress1() throws Exception {
        start();
        for (int i = 0; i < BURDEN1; i++) {
            adapter.getProject(1);
        }
        stop();
        System.out.println("Run ContestManagerProjectAdapter#getProject for "
                + BURDEN1 + " times consumes " + getMilliseconds()
                + " milliseconds.");
    }

    /**
     * Test method ContestManagerProjectAdapter#getProject under
     * <code>BURDEN2</code>.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_GetProject_Stress2() throws Exception {
        start();
        for (int i = 0; i < BURDEN2; i++) {
            adapter.getProject(1);
        }
        stop();
        System.out.println("Run ContestManagerProjectAdapter#getProject for "
                + BURDEN2 + " times consumes " + getMilliseconds()
                + " milliseconds.");
    }

    /**
     * Test method ContestManagerProjectAdapter#searchProjects under
     * <code>BURDEN1</code>.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_SearchProjects_Stress1() throws Exception {
        Filter filter = ProjectFilterUtility.buildCategoryIdEqualFilter(1);
        start();
        for (int i = 0; i < BURDEN1; i++) {
            adapter.searchProjects(filter);
        }
        stop();
        System.out
                .println("Run ContestManagerProjectAdapter#searchProjects for "
                        + BURDEN1 + " times consumes " + getMilliseconds()
                        + " milliseconds.");
    }

    /**
     * Test method ContestManagerProjectAdapter#searchProjects under
     * <code>BURDEN2</code>.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_SearchProjects_Stress2() throws Exception {
        Filter filter = ProjectFilterUtility.buildCategoryIdEqualFilter(2);
        start();
        for (int i = 0; i < BURDEN2; i++) {
            adapter.searchProjects(filter);
        }
        stop();
        System.out
                .println("Run ContestManagerProjectAdapter#searchProjects for "
                        + BURDEN2 + " times consumes " + getMilliseconds()
                        + " milliseconds.");
    }
}
