/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.accuracytests;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.registration.service.impl.RegistrationPositionImpl;

/**
 * Accuracy test for <code>{@link com.topcoder.registration.service.impl.RegistrationPositionImpl}</code>
 * class.
 * 
 * @author mittu
 * @version 1.0
 */
public class RegistrationPositionImplTest extends TestCase {
    /**
     * <p>
     * Represents the RegistrationPositionImpl for testing.
     * </p>
     */
    private RegistrationPositionImpl impl1, impl2;

    /**
     * <p>
     * Represents the Project for testing.
     * </p>
     */
    private Project project;

    /**
     * <p>
     * Represents the Phase for testing.
     * </p>
     */
    private Phase phase;

    /**
     * <p>
     * Represents the array of ResourceRole for testing.
     * </p>
     */
    private ResourceRole[] roles;

    /**
     * Sets up test environment.
     * 
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        project = new Project(101, new ProjectCategory(1, "assembly", new ProjectType(1, "java")),
                new ProjectStatus(1, "reg"));

        com.topcoder.project.phases.Project phProject = new com.topcoder.project.phases.Project(new Date(),
                new DefaultWorkdays());
        phProject.setId(101);
        phase = new Phase(phProject, 300000);
        phase.setId(1);
        phase.setPhaseStatus(PhaseStatus.OPEN);

        roles = new ResourceRole[] { new ResourceRole(1), new ResourceRole(2) };

        impl1 = new RegistrationPositionImpl();
        impl2 = new RegistrationPositionImpl(project, phase, roles);
    }

    /**
     * Accuracy test for <code>{@link RegistrationPositionImpl#RegistrationPositionImpl()}</code>.
     */
    public void testConstructor() {
        assertNotNull("Failed to create RegistrationPositionImpl", impl1);
    }

    /**
     * Accuracy test for
     * <code>{@link RegistrationPositionImpl#RegistrationPositionImpl(Project,Phase,ResourceRole[])}</code>.
     */
    public void testConstructor_Project_Phase_ResourceRoleArray() {
        assertNotNull("Failed to create RegistrationPositionImpl", impl2);
    }

    /**
     * Accuracy test for <code>{@link RegistrationPositionImpl#getPhase()}</code> and
     * <code>{@link RegistrationPositionImpl#setPhase(Phase)}</code>.
     */
    public void testMethodGetPhase() {
        assertEquals("Failed to get the phase", phase, impl2.getPhase());
    }

    /**
     * Accuracy test for <code>{@link RegistrationPositionImpl#setPhase(Phase)}</code>.
     */
    public void testMethodSetPhase_Phase() {
        impl1.setPhase(phase);
        assertEquals("Failed to get the phase", phase, impl1.getPhase());
    }

    /**
     * Accuracy test for <code>{@link RegistrationPositionImpl#getProject()}</code>.
     */
    public void testMethodGetProject() {
        assertEquals("Failed to get the project", project, impl2.getProject());
    }

    /**
     * Accuracy test for <code>{@link RegistrationPositionImpl#getAvailableRoles()}</code>.
     */
    public void testMethodGetAvailableRoles() {
        assertEquals("Failed to get available roles", roles.length, impl2.getAvailableRoles().length);
    }

    /**
     * Accuracy test for <code>{@link RegistrationPositionImpl#setProject(Project)}</code>.
     */
    public void testMethodSetProject_Project() {
        impl1.setProject(project);
        assertEquals("Failed to get the project", project, impl1.getProject());
    }

    /**
     * Accuracy test for <code>{@link RegistrationPositionImpl#setAvailableRoles(ResourceRole[])}</code>.
     */
    public void testMethodSetAvailableRoles_ResourceRoleArray() {
        impl1.setAvailableRoles(roles);
        assertEquals("Failed to get available roles", roles.length, impl1.getAvailableRoles().length);
    }

    /**
     * Returns all tests.
     * 
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(RegistrationPositionImplTest.class);
    }
}
