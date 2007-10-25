/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import java.io.File;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.project.service.impl.ProjectServicesImpl;
import com.topcoder.project.service.impl.TestHelper;
import com.topcoder.search.builder.filter.Filter;

import junit.framework.TestCase;

/**
 * <p>
 * This is a demo for demonstrating the usage of this component.
 * </p>
 * <p>
 * <code>ProjectServices</code> is the core class in this component, so this demo will concentrate
 * on usage of this class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest extends TestCase {

    /**
     * <p>
     * Represents an instance of <code>ProjectServices</code>.
     * </p>
     */
    private ProjectServices services;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "configuration.xml");
        services = new ProjectServicesImpl();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        services = null;
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Demonstrates the usage of this component.
     * </p>
     * <p>
     * In this demo, the active project status id is 1, here is the current state of the system,
     * there are three available projects:
     *
     * <pre>
     *       Project 1: Active, Type=Design
     *                --Resources: 1, 2
     *                --Teams: 1
     *                --Technologies: C#, SQL
     *
     *       Project 2: Active, Type=Development
     *                --Resources: 3, 4, 5, 6
     *                --Teams: 2, 3
     *                --Technologies: C#, XML
     *
     *       Project 3: InActive, Type=Design
     *                --Resources: 1, 5
     *                --Teams: 4
     *                --Technologies: Java, EJB
     * </pre>
     *
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testDemo() throws Exception {
        // Find active project headers
        Project[] activeProjectHeaders = services.findActiveProjectsHeaders();
        // This would return 2 projects: 1, 2

        // Find active projects
        FullProjectData[] activeProjects = services.findActiveProjects();
        // This would return 2 projects: 1, 2.

        // Project 1 would have resources 1 and 2, teams 1, and technologies C# and SQL.
        // Project 2 would have resources 3, 4, 5, and 6, teams 2 and 3, and technologies C# and
        // XML.
        // Find project headers. This would involve using Filters. We will perform a search for all
        // Design projects.
        Filter typeFilter = ProjectFilterUtility.buildTypeNameEqualFilter("Design");
        Project[] projectHeaders = services.findProjectsHeaders(typeFilter);
        // This would return 2 projects: 1, 3

        // The findFullProjects method would work in the same manner, but retrieve the full project
        // data
        // Finally, one can retrieve data for a single project, by project ID
        FullProjectData project = services.getFullProjectData(2);
        // This would return Project 2, and would have resources 3, 4, 5, and 6, teams 2 and 3, and
        // technologies C# and XML.
        // This demo has provided a typical scenario for the usage of the service.
    }

}
