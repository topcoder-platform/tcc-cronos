/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio;

import java.util.Calendar;
import java.util.Date;

import javax.naming.InitialContext;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.studio.converter.ProjectToContestConverterImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestStatus;

import junit.framework.TestCase;

/**
 * This is a demo for this component.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {

    /**
     * Demonstrates the usage of this component.
     * @throws Exception
     *         to JUnit
     */
    public void testDemo() throws Exception {
        long projectId = 1;
        long categoryId = 2;
        long statusId = 3;

        // Initialize the Adapter
        ProjectManager manager = new ContestManagerProjectAdapter("java:comp/env/contestManagerRemote");

        // Also create a ContestManager for reference
        InitialContext ic = new InitialContext();
        ContestManager contestMngr = (ContestManager) ic.lookup("java:comp/env/contestManagerRemote");

        // Initialize start/end dates.
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        // Project ends in one week.
        endDate.add(Calendar.DATE, 7);

        // Create a Project
        ProjectType projectType = new ProjectType(projectId, "Flash", "Macromedia Flash Project");

        ProjectCategory category = new ProjectCategory(categoryId, "Open", "Open To All", projectType);

        ProjectStatus regStatus = new ProjectStatus(statusId, "Registration", "Registration Phase");

        Project targetProject = new Project(category, regStatus);

        // Set some custom properties in the Project
        targetProject.setProperty(ProjectToContestConverterImpl.PROPERTY_START_DATE, startDate.getTime());

        targetProject.setProperty(ProjectToContestConverterImpl.PROPERTY_END_DATE, endDate.getTime());

        // Create the Project in the manager
        manager.createProject(targetProject, "Ignored Parameter");

        // Result should be visible in contest manager
        Contest contest = contestMngr.getContest(projectId);

        // Should be project id
        System.out.println(contest.getContestId());

        // Should be start date
        System.out.println(contest.getStartDate());

        // Should be end date
        System.out.println(contest.getEndDate());

        // Should be equivalent to Project Status
        ContestStatus contestStatus = contest.getStatus();

        System.out.println(contestStatus.getName());
        System.out.println(contestStatus.getDescription());
        System.out.println(contestStatus.getContestStatusId());

        // Update the Status to Screening
        ProjectStatus screenStatus = new ProjectStatus(4, "Screening", "Screening Phase");

        targetProject.setProjectStatus(screenStatus);

        // Status should be updated in contest manager too
        contest = contestMngr.getContest(projectId);

        contestStatus = contest.getStatus();

        System.out.println(contestStatus.getName());
        System.out.println(contestStatus.getDescription());
        System.out.println(contestStatus.getContestStatusId());

        // Update the end date in the contest manager
        contest.setEndDate(new Date());

        contestMngr.updateContest(contest);

        // Result should also be visible in Project Manager
        targetProject = manager.getProject(projectId);

        // Should be the new end date.
        targetProject.getProperty(ProjectToContestConverterImpl.PROPERTY_END_DATE);

        // Perform a search
        Filter filter = ProjectFilterUtility.buildCategoryIdEqualFilter(categoryId);

        // Should return our existing project
        Project[] results = manager.searchProjects(filter);

        // Get all Project Categories
        ProjectCategory[] categories = manager.getAllProjectCategories();

        // Get all Project Types
        ProjectType[] projectCategories = manager.getAllProjectTypes();

        // Get all Project Statuses
        ProjectStatus[] projectStatus = manager.getAllProjectStatuses();

        // Get projects by ids
        Project[] projects = manager.getProjects(new long[] { 1, 2, 3, 4 });
    }
}
