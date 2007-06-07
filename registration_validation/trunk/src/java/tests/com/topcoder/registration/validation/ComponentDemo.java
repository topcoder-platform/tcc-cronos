/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.topcoder.project.service.impl.MockProjectServicesImpl;
import com.topcoder.management.ban.manager.MockBanManager;
import com.topcoder.management.team.impl.MockTeamManagerImpl;

import com.topcoder.util.datavalidator.BundleInfo;
import java.io.FileOutputStream;
import java.io.PrintStream;
import com.cronos.onlinereview.external.RatingType;
import com.topcoder.registration.service.RegistrationInfo;
import com.cronos.onlinereview.external.RatingInfo;
import com.topcoder.registration.service.impl.RegistrationInfoImpl;
import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.management.ban.BanManager;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.team.TeamManager;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.topcoder.registration.team.service.OperationResult;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.basic.BasicLogFactory;
import com.topcoder.registration.validation.validators.simple.MemberNotBarredValidator;
import com.topcoder.registration.validation.validators.simple.MemberNotTeamMemberForProjectValidator;
import com.topcoder.registration.validation.validators.simple.MemberNotRegisteredWithRoleForProjectValidator;
import com.topcoder.registration.validation.validators.simple.MemberNotTeamCaptainWithMembersForProjectValidator;
import com.topcoder.registration.validation.validators.simple.MemberMustBeRegisteredValidator;
import com.topcoder.registration.validation.validators.conditional.ProjectCategoryConditionalValidator;
import com.topcoder.registration.validation.validators.conditional.ProjectTypeConditionalValidator;
import com.topcoder.registration.validation.validators.util.AndValidator;

import java.util.Date;
import junit.framework.TestCase;

/**
 * <p>
 * Component demonstration for Registration Validation 1.0.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentDemo extends TestCase {

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("TestDemoConfig.xml");
        TestHelper.loadXMLConfig("Document_Generator.xml");
        TestHelper
                .loadXMLConfig("MemberMinimumRatingForRatingTypeValidator.xml");

        // create a print stream to the file with auto flushing
        PrintStream ps = new PrintStream(new FileOutputStream(
                "test_files/ComponentDemoLog.txt", true), true);
        // specify the basic logger with the above print stream
        LogManager.setLogFactory(new BasicLogFactory(ps));

    }

    /**
     * Clears the testing environment.
     *
     * @throws Exception
     *             when error occurs
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * This method demonstrates how the registration validator validates using
     * configured validators.
     * </p>
     *
     * <p>
     * A typical usage scenario involves validation of user information: Suppose
     * we have validation to check that the user is not on any team on the
     * project, has a rating of 1300, and the project is still in registration:
     *
     * Our configuration would then use the following validators:
     * <ul>
     * <li>MemberNotTeamMemberForProjectValidator</li>
     * <li>MemberMinimumRatingForRatingTypeValidator</li>
     * <li>ProjectInPhaseValidator</li>
     * </ul>
     * We can then test the suitability of various users for a position on the
     * project:
     * </p>
     *
     * @throws Exception
     *             when error occurs
     */
    public void testDemo1() throws Exception {

        // Create RegistrationValidator from configuration.
        DataValidationRegistrationValidator registrationValidator = new DataValidationRegistrationValidator(
                "testDemoConfig");

        // Create FullProjectData for test.
        FullProjectData project = createFullProjectDataForTest();

        // sets the user information and registration information for the user1.
        // the user 1 is not registered, has rating 900, and the project is in
        // registration
        RegistrationInfo info1 = new RegistrationInfoImpl();
        info1.setProjectId(2);
        info1.setRoleId(3);
        info1.setUserId(2);
        RatingInfo ratingInfo1 = new RatingInfo(RatingType.DESIGN, 900, 5, 20);
        ExternalUser user1 = new ExternalUserImpl(1, "handle1", "user1",
                "TCMember", "user1@topcoder.com");
        ((ExternalUserImpl) user1).addRatingInfo(ratingInfo1);

        OperationResult result1 = registrationValidator.validate(info1, user1,
                project);
        // The result will be that validation fails because the rating is too
        // small

        // sets the user information and registration information for the user2.
        // the user 2 is already registered on this project, has rating 2000,
        // and the project is in registration
        RatingInfo ratingInfo2 = new RatingInfo(RatingType.DESIGN, 2000, 5, 20);
        ExternalUser user2 = new ExternalUserImpl(2, "handle2", "user2",
                "TCMember", "user2@topcoder.com");
        ((ExternalUserImpl) user2).addRatingInfo(ratingInfo2);
        RegistrationInfo info2 = new RegistrationInfoImpl();
        info2.setProjectId(2);
        info2.setRoleId(3);
        info2.setUserId(1);
        OperationResult result2 = registrationValidator.validate(info2, user2, project);
        // The result will be that validation fails because the user is
        // already registered on this project

        // sets the user information and registration information for the user3.
        // the user3 is not registered, has rating 1345, and the project is in
        // registration
        RegistrationInfo info3 = new RegistrationInfoImpl();
        info3.setProjectId(2);
        info3.setRoleId(3);
        info3.setUserId(3);
        RatingType ratingType3 = RatingType.DESIGN;
        RatingInfo ratingInfo3 = new RatingInfo(RatingType.DESIGN, 1345,
                5, 20);
        ExternalUser user3 = new ExternalUserImpl(3, "handle3", "user3",
                "TCMember", "user3@topcoder.com");
        ((ExternalUserImpl) user3).addRatingInfo(ratingInfo3);

        OperationResult result3 = registrationValidator.validate(info3, user3,
                project);
        // The result will be that validation succeeds

        // This demo has provided a typical scenario for the usage of the validator.
    }

    /**
     * <p>
     * This method demonstrates how does one decide which validators are
     * applicable.
     * </p>
     *
     * <p>
     * This scenario has the following conditions (with assumed IDs for the
     * various aspects):
     * <ul>
     * <li>Registering member must not be barred</li>
     * <li>If the project is of category ¡°Assembly¡± (category Id=4), then
     * registering member must not be currently a Team Member (roleId = 3) for
     * this project</li>
     * <li> Member registering as Team Captain must not be currently a Team
     * Captain for this project</li>
     * <li>Member registering as a Free Agent (roleId = 2) must not be
     * currently a Team Captain with assigned Team</li>
     * </ul>
     * Suppose the registrant has user ID = 1 and is a Free Agent, and the
     * project is of category ¡°Assembly¡± and ID 5. The following validators
     * would be used (with appropriate BundleInfo)
     * </p>
     *
     * @throws Exception
     *             when error occurs
     */
    public void testDemo2() throws Exception {
        BundleInfo bundleInfo = new BundleInfo();
        bundleInfo.setBundle("mybundle");
        bundleInfo.setMessageKey("messageKey");
        bundleInfo.setDefaultMessage("./test_files/myTemplate.txt");

        // Suppose the registrant has user ID = 1 and is a Free Agent, and the
        // project is of category ¡°Assembly¡± and ID 5.
        RegistrationInfo info = new RegistrationInfoImpl();
        info.setProjectId(5);
        info.setRoleId(2);
        info.setUserId(1);

        // Create FullProjectData for test.
        FullProjectData project = createFullProjectDataForTest();

        // sets the user information. The user is not registered, has rating
        // 900, and the project is in registration
        RegistrationInfo info1 = new RegistrationInfoImpl();
        info1.setProjectId(2);
        info1.setRoleId(3);
        info1.setUserId(2);
        RatingInfo ratingInfo = new RatingInfo(RatingType.DESIGN, 900, 5, 20);
        ExternalUser user = new ExternalUserImpl(1, "handle1", "user1",
                "TCMember", "user1@topcoder.com");
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);

        // The following validators would be used (with appropriate BundleInfo)

        // member is not barred
        MemberNotBarredValidator val1 = new MemberNotBarredValidator(bundleInfo);

        // if project is in ¡°Assembly¡± category, the registrant is not yet a
        // team member.
        MemberNotTeamMemberForProjectValidator val21 = new MemberNotTeamMemberForProjectValidator(
                bundleInfo);
        ProjectCategoryConditionalValidator val2 = new ProjectCategoryConditionalValidator(
                val21, 4);

        // Check that member registering as team captain as is not a team
        // captain on the team already.
        MemberNotRegisteredWithRoleForProjectValidator val3 =
            new MemberNotRegisteredWithRoleForProjectValidator(
                bundleInfo, 3);

        // Check that member is not a team captain with registrants
        MemberNotTeamCaptainWithMembersForProjectValidator val4
            = new MemberNotTeamCaptainWithMembersForProjectValidator(
                bundleInfo, 3);

        // These four validators (val1, val2, val3, and val4) would be packaged
        // into an Array and put in an AndValidator and passed to the
        // DataValidationRegistrationValidator instance during construction.
        AndValidator validator = new AndValidator(new ConfigurableValidator[] {
            val1, val2, val3, val4 }, false);
        ProjectServices projectServices = new MockProjectServicesImpl();
        BanManager banManager = new MockBanManager();
        TeamManager teamManager = new MockTeamManagerImpl();
        DataValidationRegistrationValidator registrationValidator =
            new DataValidationRegistrationValidator(
                teamManager, projectServices, banManager, null, validator);

        OperationResult result3 = registrationValidator.validate(info, user,
                project);
    }

    /**
     * <p>
     * This method demonstrates the case that there¡¯s a tournament going on, a
     * new custom validator may be developed and added to the above
     * configuration with the following rule: If the project is of type
     * ¡°Contest¡± (type Id=5), then a registering member must be registered to
     * the tournament.
     * </p>
     *
     * <p>
     * Suppose our custom validator is of type MemberMustBeRegisteredValidator.
     * It would be packaged in a ProjectTypeConditionalValidator that would be
     * only activated if the project type is ¡°Contest¡±. The
     * ProjectTypeConditionalValidator instance would then be passed to the
     * DataValidationRegistrationValidator instance during construction.
     * </p>
     *
     * @throws Exception
     *             when error occurs
     */
    public void testDemo3() throws Exception {
        BundleInfo bundleInfo = new BundleInfo();
        bundleInfo.setBundle("myBundle");
        bundleInfo.setMessageKey("messageKey");
        bundleInfo.setDefaultMessage("./test_files/myTemplate.txt");
        // Creates an instance of the custom validator MemberMustBeRegisteredValidator.
        MemberMustBeRegisteredValidator innerValidator = new MemberMustBeRegisteredValidator(
                bundleInfo);

        // Packages the custom validator in a ProjectTypeConditionalValidator that would be
        // only activated if the project type is "Contest".
        ProjectTypeConditionalValidator validator = new ProjectTypeConditionalValidator(
                innerValidator, 5);

        ProjectServices projectServices = new MockProjectServicesImpl();
        BanManager banManager = new MockBanManager();
        TeamManager teamManager = new MockTeamManagerImpl();
        // Passes to the ProjectServices instance to the DataValidationRegistrationValidator
        // instance during construction.
        DataValidationRegistrationValidator registrationValidator =
            new DataValidationRegistrationValidator(
                teamManager, projectServices, banManager, null, validator);
    }

    /**
     * Creates a FullProjectData instance for test.
     *
     * @return the created FullProjectData instance
     */
    private FullProjectData createFullProjectDataForTest() {
            // creates a FullProjectData instance
        FullProjectData project = new FullProjectData(new Date(),
                new DefaultWorkdaysFactory().createWorkdaysInstance());

        // sets the technologies of the project
        project.setTechnologies(new String[] {"Java"});

        // sets the resources of the project
        Resource resource = new Resource(11, new ResourceRole());
        resource.setProperty("External Reference ID", new Long(1));
        project.setResources(new Resource[] {resource});

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