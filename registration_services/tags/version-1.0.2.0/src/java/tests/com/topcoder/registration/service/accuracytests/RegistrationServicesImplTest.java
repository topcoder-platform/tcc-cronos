/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.RegistrationPosition;
import com.topcoder.registration.service.impl.RegistrationInfoImpl;
import com.topcoder.registration.service.impl.RegistrationServicesImpl;

/**
 * Accuracy test for <code>{@link com.topcoder.registration.service.impl.RegistrationServicesImpl}</code>
 * class.
 * 
 * @author mittu
 * @version 1.0
 */
public class RegistrationServicesImplTest extends TestCase {
    /**
     * <p>
     * Represents the RegistrationServicesImpl to test.
     * </p>
     */
    private RegistrationServicesImpl impl1, impl2;

    /**
     * <p>
     * Represents the RegistrationInfo used for testing.
     * </p>
     */
    private RegistrationInfo info;

    /**
     * Sets up test environment.
     * 
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadConfigs();
        impl1 = new RegistrationServicesImpl();
        impl2 = new RegistrationServicesImpl("com.topcoder.registration.service.accuracytests");
        info = new RegistrationInfoImpl(101, 10, 1);
    }

    /**
     * Accuracy test for <code>{@link RegistrationServicesImpl#RegistrationServicesImpl()}</code>.
     */
    public void testConstructor() {
        assertNotNull("Failed to create RegistrationServicesImpl", impl1);
    }

    /**
     * Accuracy test for <code>{@link RegistrationServicesImpl#RegistrationServicesImpl(String)}</code>.
     */
    public void testConstructor_String() {
        assertNotNull("Failed to create RegistrationServicesImpl", impl2);
    }

    /**
     * Accuracy test for <code>{@link RegistrationServicesImpl#registerForProject(RegistrationInfo)}</code>.
     */
    public void testMethodRegisterForProject_RegistrationInfo() {
        assertTrue("Failed to register for project", impl2.registerForProject(info).isSuccessful());
    }

    /**
     * Accuracy test for <code>{@link RegistrationServicesImpl#validateRegistration(RegistrationInfo)}</code>.
     */
    public void testMethodValidateRegistration_RegistrationInfo() {
        assertTrue("Failed to validate registration", impl2.validateRegistration(info).isSuccessful());
    }

    /**
     * Accuracy test for <code>{@link RegistrationServicesImpl#getRegisteredProjects(long)}</code>.
     */
    public void testMethodGetRegisteredProjects_long() {
        Project[] projects = impl2.getRegisteredProjects(101);

        assertEquals("Failed to get registered projects", 1, projects.length);
        assertEquals("project id mismatch", 101, projects[0].getId());
        assertEquals("Project name mismatch", "Registration Services", projects[0]
                .getProperty("Project Name"));
    }

    /**
     * Accuracy test for <code>{@link RegistrationServicesImpl#getRegistration(long,long)}</code>.
     */
    public void testMethodGetRegistration_long_long() {
        info = impl2.getRegistration(10, 101);
        assertEquals("User id mismatch", 10, info.getUserId());
        assertEquals("Project id mismatch", 101, info.getProjectId());
        assertEquals("Resource role id mismatch", 1, info.getRoleId());
    }

    /**
     * Accuracy test for
     * <code>{@link RegistrationServicesImpl#findAvailableRegistrationPositions(ProjectCategory)}</code>.
     */
    public void testMethodFindAvailableRegistrationPositions_ProjectCategory() {
        RegistrationPosition[] positions = impl2.findAvailableRegistrationPositions(new ProjectCategory(1,
                "component dev", new ProjectType(1, "java")));

        assertEquals("Failed to find available registration positions", 1, positions.length);
        assertEquals("Project id mismatch", 101, positions[0].getProject().getId());
        assertEquals("Phase id mismatch.", 1, positions[0].getPhase().getId());
        assertEquals("Available roles mismatch", 2, positions[0].getAvailableRoles().length);
    }

    /**
     * Accuracy test for
     * <code>{@link RegistrationServicesImpl#removeRegistration(RegistrationInfo,int)}</code>.
     */
    public void testMethodRemoveRegistration_RegistrationInfo_int() {
        assertTrue("Failed to remove registration", impl2.removeRegistration(info, 5).isSuccessful());
    }

    /**
     * Accuracy test for <code>{@link RegistrationServicesImpl#getRegisteredResources(long)}</code>.
     */
    public void testMethodGetRegisteredResources_long() {
        Resource[] resources = impl2.getRegisteredResources(101);

        assertEquals("Failed to get registered resources", 1, resources.length);
        assertEquals("Resource id mismatch", 1, resources[0].getId());
        assertEquals("Resource property mismatch", resources[0].getProperty("External reference ID"),
                new Long(10));
        assertEquals("Resource property mismatch.", resources[0].getProperty("Handle"), "handle");
        assertEquals("Resource property mismatch.", resources[0].getProperty("Email"), "handle@topcoder.com");
    }

    /**
     * Returns all tests.
     * 
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(RegistrationServicesImplTest.class);
    }
}
