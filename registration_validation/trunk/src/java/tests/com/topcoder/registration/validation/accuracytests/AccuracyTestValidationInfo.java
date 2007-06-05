/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.accuracytests;

import java.util.Date;

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
import com.topcoder.registration.service.RegistrationInfoImpl;
import com.topcoder.registration.validation.ValidationInfo;

import junit.framework.TestCase;

/**
 * This class contains unit tests for <code>ValidationInfo</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestValidationInfo extends TestCase {

	/**
	 * <p>
	 * The registration information of the user.
	 * </p>
	 */
	private RegistrationInfo registration;

	/**
	 * <p>
	 * The detailed external information about the user.
	 * </p>
	 */
	private ExternalUserImpl user;

	/**
	 * <p>
	 * The details project information.
	 * </p>
	 */
	private FullProjectData project;

	/**
	 * <p>
	 * The ValidationInfo instance for testing purpose.
	 * </p>
	 */
	private ValidationInfo validationInfo;

	/**
	 * Set Up the test environment before testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		long userId = 1;
		RatingType ratingType = RatingType.DESIGN;
		int rating = 100;
		int numRatings = 3;
		int volatility = 50;
		RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings, volatility, 100.01);
		user = new ExternalUserImpl(userId, "handle1", "firstName", "lastName", "email");
		user.addAlternativeEmail("alternativeEmail_1@topcoder.com");
		user.addAlternativeEmail("alternativeEmail_2@topcoder.com");
		user.addRatingInfo(ratingInfo);

		// create project.
		project = new FullProjectData(new Date(), (new DefaultWorkdaysFactory()).createWorkdaysInstance());

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
		registration = new RegistrationInfoImpl();
		registration.setProjectId(2);
		registration.setRoleId(3);
		registration.setUserId(1);

		validationInfo = new ValidationInfo();
		validationInfo.setUser(user);
		validationInfo.setRegistration(registration);
		validationInfo.setProject(project);

	}

	/**
	 * Clean up the test environment after testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Function test : Tests <code>ValidationInfo()</code> method for
	 * accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testValidationInfo1Accuracy() throws Exception {
		assertNotNull("Null is not allowed.", new ValidationInfo());
	}

	/**
	 * Function test : Tests
	 * <code>ValidationInfo(RegistrationInfo registration, ExternalUser user,FullProjectData project)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testValidationInfo2Accuracy() throws Exception {
		assertNotNull("Null is not allowed.", new ValidationInfo(registration, user, project));
	}

	/**
	 * Function test : Tests <code>getRegistration()</code> method for
	 * accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetRegistrationAccuracy() throws Exception {
		assertEquals("object is not equals.", this.registration, validationInfo.getRegistration());
	}

	/**
	 * Function test : Tests
	 * <code>setRegistration(RegistrationInfo registration)</code> method for
	 * accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetRegistrationAccuracy() throws Exception {
		RegistrationInfo r2 = new RegistrationInfoImpl();
		validationInfo.setRegistration(r2);
		assertEquals("object is not equals.", r2, validationInfo.getRegistration());
	}

	/**
	 * Function test : Tests <code>getUser()</code> method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetUserAccuracy() throws Exception {
		assertEquals("object is not equals.", this.user, validationInfo.getUser());
	}

	/**
	 * Function test : Tests <code>setUser(ExternalUser user)</code> method
	 * for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetUserAccuracy() throws Exception {
		ExternalUserImpl u2 = new ExternalUserImpl(1, "handle1", "firstName", "lastName", "email");
		validationInfo.setUser(u2);
		assertEquals("object is not equals.", u2, validationInfo.getUser());
	}

	/**
	 * Function test : Tests <code>getProject()</code> method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetProjectAccuracy() throws Exception {
		assertEquals("object is not equals.", this.project, validationInfo.getProject());
	}

	/**
	 * Function test : Tests <code>setProject(FullProjectData project)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetProjectAccuracy() throws Exception {
		FullProjectData p2 = new FullProjectData(new Date(), (new DefaultWorkdaysFactory())
				.createWorkdaysInstance());

		validationInfo.setProject(p2);
		assertEquals("object is not equals.", p2, validationInfo.getProject());
	}

}