/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.impl;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * This is a helper class for test.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {

    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Uses the given file to config the configuration manager.
     * </p>
     * @param fileName
     *            config file to set up environment
     * @throws Exception
     *             when any exception occurs
     */
    public static void loadXMLConfig(String fileName) throws Exception {
        // set up environment
        ConfigManager config = ConfigManager.getInstance();
        File file = new File(fileName);

        config.add(file.getCanonicalPath());
    }

    /**
     * <p>
     * Clears all the namespaces from the configuration manager.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }

    /**
     * <p>
     * Creates an instance of <code>FullProjectData</code>.
     * </p>
     * @return an instance of FullProjectData
     */
    public static FullProjectData createFullProjectData() {
        FullProjectData data = new FullProjectData(new Date(19999), new DefaultWorkdays());

        Resource resource = new Resource();
        resource.setId(1);
        resource.setProperty("External reference ID", new Long(1));
        resource.setProperty("Handle", "argolite");
        resource.setProperty("Email", "argolite@topcoder.com");
        resource.setProperty("Registration Date", new Date());

        ResourceRole role = new ResourceRole(2);
        resource.setResourceRole(role);

        data.setResources(new Resource[] {resource});

        Project project = new Project(1, new ProjectCategory(1, "Design",
            new ProjectType(1, "Java")), new ProjectStatus(1, "submission"));
        project.setProperty("Project Name", "Registration Services");
        data.setProjectHeader(project);

        TeamHeader team = new TeamHeader();
        team.setName("Robot Team");
        team.setDescription("A strong team.");
        team.setCaptainResourceId(1);
        data.setTeams(new TeamHeader[] {team});

        return data;
    }

}
