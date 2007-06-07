/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.accuracytests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.Iterator;

import com.cronos.onlinereview.external.RatingInfo;
import com.cronos.onlinereview.external.RatingType;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.impl.RegistrationInfoImpl;
import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.basic.BasicLogFactory;

/**
 * Defines helper methods used in accuracy tests of this component.
 * 
 * @author qiucx0161
 * @version 1.0
 */
public class AccuracyTestHelper {

	/**
	 * This private constructor prevents the creation of a new instance.
	 */
	private AccuracyTestHelper() {
	}

	/**
	 * <p>
	 * Clear testing configuration.
	 * </p>
	 * 
	 * @throws Exception
	 *             pass any unexpected exception to JUnit.
	 */
	public static void clearNamespaces() throws Exception {
		ConfigManager configManager = ConfigManager.getInstance();
		for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
			configManager.removeNamespace((String) iter.next());
		}
	}

	/**
	 * <p>
	 * Load the testing environment.
	 * </p>
	 * 
	 * @throws Exception
	 *             pass any unexpected exception to JUnit.
	 */
	public static void loadNamespaces() throws Exception {
		clearNamespaces();

		ConfigManager.getInstance().add(new File("test_files/accuracy/accuracy.xml").getAbsolutePath());
	}

	public static ValidationInfo createValidationInfo() {
		long userId = 1;
		RatingType ratingType = RatingType.DESIGN;
		int rating = 100;
		int numRatings = 3;
		int volatility = 50;
		RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings, volatility, 100.01);
		ExternalUserImpl user = new ExternalUserImpl(userId, "handle1", "firstName", "lastName", "email");
		user.addAlternativeEmail("alternativeEmail_1@topcoder.com");
		user.addAlternativeEmail("alternativeEmail_2@topcoder.com");
		user.addRatingInfo(ratingInfo);

		// create project.
		FullProjectData project = new FullProjectData(new Date(), (new DefaultWorkdaysFactory())
				.createWorkdaysInstance());

		Resource resource1 = new Resource(11, new ResourceRole());
		resource1.setProperty("External Reference ID", new Long(1));
		Resource resource2 = new Resource(22, new ResourceRole());
		resource2.setProperty("External Reference ID", new Long(2));
		project.setResources(new Resource[] { resource1, resource2 });

		ProjectCategory projectCategory = new ProjectCategory(1, "projectCategoryName", new ProjectType(1,
				"ProjectType"));
		ProjectStatus projectStatus = new ProjectStatus(1, "projectStatusName");
		Project projectHeader = new Project(projectCategory, projectStatus);
		project.setProjectHeader(projectHeader);

		TeamHeader team1 = new TeamHeader();
		team1.setTeamId(1);
		team1.setCaptainResourceId(11);
		TeamHeader team2 = new TeamHeader();
		team2.setTeamId(2);
		team2.setCaptainResourceId(22);
		project.setTeams(new TeamHeader[] { team1, team2 });

		project.setTechnologies(new String[] { "Java", "SQL" });

		Phase phase = new Phase(project, 7);
		phase.setPhaseStatus(PhaseStatus.OPEN);
		phase.setId(1);
		project.addPhase(phase);

		// create RegistrationInfo.
		RegistrationInfo registration = new RegistrationInfoImpl();
		registration.setProjectId(2);
		registration.setRoleId(3);
		registration.setUserId(1);

		ValidationInfo validationInfo = new ValidationInfo();
		validationInfo.setUser(user);
		validationInfo.setRegistration(registration);
		validationInfo.setProject(project);
		return validationInfo;
	}

	public static void setLog(ConfigurableValidator validator) {
		PrintStream ps = null;
		try {
			// create a print stream to the file with auto flushing
			ps = new PrintStream(new FileOutputStream("test_files\\accuracy\\accLog.txt", true), true);
			// specify the basic logger with the above print stream
			LogManager.setLogFactory(new BasicLogFactory(ps));

			DataValidationRegistrationValidator rv = new DataValidationRegistrationValidator();
			rv.getProjectServices();
			validator.setRegistrationValidator(rv);
		} catch (FileNotFoundException e) {
			// do nothing
		} finally {
			if (ps != null) {
				ps.close();
			}
		}
	}
}
