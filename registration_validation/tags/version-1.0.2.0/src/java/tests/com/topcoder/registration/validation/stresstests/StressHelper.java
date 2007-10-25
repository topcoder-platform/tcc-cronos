/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.stresstests;

import java.util.Date;
import java.util.Iterator;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * This class is the stress helper class for Registration Validation version 1.0.
 * </p>
 *
 * @author Hacker_QC
 * @version 1.0
 */
final class StressHelper {
	/**
	 * <p>
	 * The config file for the project.
	 * </p>
	 */
	public static final String CONFIG = "stresstests/config.xml";

	/**
	 * <p>
	 * The private constructor prevents the creation of a new instance.
	 * </p>
	 */
	private StressHelper() {
	}

	/**
	 * <p>
	 * Loads all configurations from files with given file name.
	 * </p>
	 *
	 * @param file
	 *            the file to loaded from.
	 * @throws Exception
	 *             when error occurs
	 */
	public static void loadConfig(String file) throws Exception {
		ConfigManager cm = ConfigManager.getInstance();
		cm.add(file);
	}

	/**
	 * <p>
	 * Clear the config.
	 * </p>
	 *
	 * @throws Exception -
	 *             to JUnit.
	 */
	public static void clearConfig() throws Exception {
		ConfigManager configManager = ConfigManager.getInstance();

		for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
			configManager.removeNamespace((String) iter.next());
		}
	}

	/**
	 * <p>
	 * Creates a <code>FullProjectData</code> instance for stress test.
	 * </p>
	 *
	 * @return the created FullProjectData instance
	 */
	public static FullProjectData getProject() {
		// creates a FullProjectData instance
		FullProjectData project = new FullProjectData(new Date(),
				new DefaultWorkdaysFactory().createWorkdaysInstance());

		// sets the technologies of the project
		project.setTechnologies(new String[] { "Java" });

		// sets the resources of the project
		Resource resource = new Resource(11, new ResourceRole());
		resource.setProperty("External Reference ID", new Long(1));
		project.setResources(new Resource[] { resource });

		// sets the projectHeader of the project
		ProjectCategory projectCategory = new ProjectCategory(1,
				"projectCategoryName", new ProjectType(1, "myProjectType"));
		ProjectStatus projectStatus = new ProjectStatus(1, "projectStatusName");
		Project projectHeader = new Project(projectCategory, projectStatus);
		projectHeader.setCreationUser("projectHeaderCreationUser");
		project.setProjectHeader(projectHeader);

		// adds an open phase to the project
		long phaseLength = 7;
		Phase phase = new Phase(project, phaseLength);
		phase.setPhaseStatus(PhaseStatus.OPEN);
		phase.setId(3);
		project.addPhase(phase);

		return project;
	}
}
