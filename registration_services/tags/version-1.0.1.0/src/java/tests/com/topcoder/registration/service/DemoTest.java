/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.registration.service.impl.RegistrationInfoImpl;
import com.topcoder.registration.service.impl.RegistrationServicesImpl;
import com.topcoder.registration.service.impl.TestHelper;

/**
 * <p>
 * This is a demo for demonstrating the usage of this component.
 * </p>
 * <p>
 * <code>RegistrationServices</code> is the core class in this component, so this demo will
 * concentrate on usage of this class.
 * </p>
 * @author moonli
 * @version 1.0
 */
public class DemoTest extends TestCase {

    /**
     * <p>
     * Represents an instance of <code>RegistrationServicesImpl</code> class.
     * </p>
     */
    private RegistrationServicesImpl services;

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
        services = new RegistrationServicesImpl();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        services = null;
    }

    /**
     * <p>
     * Demonstrates usage of this component.
     * </p>
     * <p>
     * In this demo, that roleID = 1 is a Team Member role, and roleId=2 is a Team Captain role.
     * </p>
     * <p>
     * For the benefit of the scenario, demo provides a simple state of the system in terms of
     * projects that are in registration phase. These projects are arbitrary:
     * <ul>
     * <li>Design: Survey Data Model (Id=10): 2 registrants (1 member, 1 captain)
     * <li>Design: Survey Persistence (Id=11): 0 registrants</li>
     * <li>Development: Survey Servlet (Id=12): 1 registrant (1 member)</li>
     * </ul>
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testDemo() throws Exception {

        // Register a user (id=3) for a captain role for Survey Persistence project
        RegistrationInfo registrationInfo = new RegistrationInfoImpl(11, 3, 2);
        RegistrationResult result = services.registerForProject(registrationInfo);
        // The result would indicate that the validation was successful, and that there was no
        // previous registration for this user in this project. Survey Persistence would now have
        // this user registered as a Team Captain.

        // Note that since the registration provides automatic validation, explicit validation is
        // not necessary but could be explicitly performed with the following call:
        RegistrationResult result2 = services.validateRegistration(registrationInfo);
        // The result would hold the same information as the previous shown call

        // If we now attempt to retrieve information about this registration, we will confirm the
        // registration
        RegistrationInfo retrievedRegistrationInfo = services.getRegistration(3, 11);
        // This would confirm role as Team Captain (roleId=2)

        // If this person decided to change their role to Team Member, the can repeat the call to
        // registration
        RegistrationResult result3 = services
            .registerForProject(new RegistrationInfoImpl(11, 3, 1));
        // The result would contain the previous registration info, where roleID was 2.

        // Should this user decide to remove himself/herself from the project, they could use the
        // removeRegistration method, and he/she will be banned for 4 days
        RemovalResult result4 = services.removeRegistration(new RegistrationInfoImpl(1, 3, 1), 4);
        // The result would confirm deletion of the resource, and Survey Persistence project would
        // once again have no registrants

        // We can query which design projects are available
        ProjectCategory category = new ProjectCategory(1, "DesignCategory", new ProjectType(1,
            "JAVA"));
        RegistrationPosition[] positions = services.findAvailableRegistrationPositions(category);
        // The result would state that Survey Data Model and Survey Persistence are in design stage,
        // with the same configured roles available: Team member, and Team captain.

        // Suppose user 1 wants to know in which projects he/she is:
        Project[] projects = services.getRegisteredProjects(1);
        // The result would be two projects: Survey Data Model and Survey Servlet projects

        // If the aim was to find out who is currently registered in Survey Data Model, the
        // following method call can be made:
        Resource[] resources = services.getRegisteredResources(10);
        // The result is:
        // - a resource for user 1 as Team member
        // - a resource for user 2 as Team captain
        // This demo has provided a typical scenario for the usage of the service.
    }
}
