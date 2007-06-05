/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.stresstests;

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

import java.io.File;
import java.util.Date;
import java.util.Iterator;

/**
 * <p>
 * This class is the helper class for this stress test.
 * </p>
 *
 * @author Hacker_QC
 * @version 1.0
 */
final class StressTestHelper {

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
     * @param roleId
     *            the resourceRole id
     * @return an instance of FullProjectData
     */
    public static FullProjectData createFullProjectData(long roleId) {
        FullProjectData data = new FullProjectData(new Date(19999), new DefaultWorkdays());

        Resource resource = new Resource();
        resource.setId(1);
        resource.setProperty("External reference ID", new Long(1));
        resource.setProperty("Handle", "tomek");
        resource.setProperty("Email", "tomek@topcoder.com");
        resource.setProperty("Registration Date", new Date(1000000));

        ResourceRole role = new ResourceRole(roleId);
        resource.setResourceRole(role);

        data.setResources(new Resource[] {resource});

        Project project = new Project(1, new ProjectCategory(1, "cate-1", new ProjectType(1,
            "type-1")), new ProjectStatus(1, "dev"));
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