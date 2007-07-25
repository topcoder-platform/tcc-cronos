/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.failuretests.impl;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.project.phases.Phase;
import com.topcoder.registration.service.impl.RegistrationPositionImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure tests for RegistrationPositionImpl.
 *
 * @author liulike
 * @version 1.0
 */
public class RegistrationPositionImplFailureTest extends TestCase {

    /**
     * The Project instance used in test.
     */
    private Project project;

    /**
     * The Phase instance used in test.
     */
    private Phase phase;

    /**
     * The ResourceRole array instance used in test.
     */
    private ResourceRole[] availableRoles;

    /**
     * The RegistrationPositionImpl instance used in test.
     */
    private RegistrationPositionImpl instance;

    /**
     * Set up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        project =
            new Project(new ProjectCategory(1, "2", new ProjectType(3, "4")), new ProjectStatus(5, "6"));

        com.topcoder.project.phases.Project phaseProject =
            new com.topcoder.project.phases.Project(new Date(), new DefaultWorkdays());
        phase = new Phase(phaseProject, 7);

        availableRoles = new ResourceRole[2];
        availableRoles[0] = new ResourceRole(1);
        availableRoles[1] = new ResourceRole(2);

        instance = new RegistrationPositionImpl(project, phase, availableRoles);

    }

    /**
     * Tear down the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        project = null;
        phase = null;
        availableRoles = null;
        instance = null;
    }

    /**
     * Failure tests for RegistrationPositionImpl(Project, Phase, ResourceRole[]), it tests the case that
     * parameter is null, and the IllegalArgumentException is expected.
     *
     */
    public void testRegistrationPositionImplProjectPhaseResourceRoleArray_invalid1() {
        try {
            new RegistrationPositionImpl(null, phase, availableRoles);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for RegistrationPositionImpl(Project, Phase, ResourceRole[]), it tests the case that
     * parameter is null, and the IllegalArgumentException is expected.
     *
     */
    public void testRegistrationPositionImplProjectPhaseResourceRoleArray_invalid2() {
        try {
            new RegistrationPositionImpl(project, null, availableRoles);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for RegistrationPositionImpl(Project, Phase, ResourceRole[]), it tests the case that
     * parameter is null, and the IllegalArgumentException is expected.
     *
     */
    public void testRegistrationPositionImplProjectPhaseResourceRoleArray_invalid3() {
        try {
            new RegistrationPositionImpl(project, phase, null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for RegistrationPositionImpl(Project, Phase, ResourceRole[]), it tests the case that
     * ResourceRole contains null, and the IllegalArgumentException is expected.
     *
     */
    public void testRegistrationPositionImplProjectPhaseResourceRoleArray_invalid4() {
        try {
            availableRoles = new ResourceRole[2];
            availableRoles[0] = new ResourceRole();
            new RegistrationPositionImpl(project, phase, availableRoles);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for setProject(Project), it tests the case that Project is null, and the
     * IllegalArgumentException is expected.
     *
     */
    public void testSetProject_invalid() {
        try {
            instance.setProject(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for setPhase(Phase), it tests the case that Phase is null, and the
     * IllegalArgumentException is expected.
     *
     */
    public void testSetPhase_invalid() {
        try {
            instance.setPhase(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for setAvailableRoles(availableRoles), it tests the case that availableRoles is null, and
     * the IllegalArgumentException is expected.
     *
     */
    public void testSetAvailableRoles_invalid1() {
        try {
            instance.setAvailableRoles(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for setAvailableRoles(availableRoles), it tests the case that availableRoles contains
     * null, and the IllegalArgumentException is expected.
     *
     */
    public void testSetAvailableRoles_invalid2() {
        availableRoles = new ResourceRole[2];
        availableRoles[0] = new ResourceRole();
        try {
            instance.setAvailableRoles(availableRoles);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all test cases.
     */
    public static Test suite() {
        return new TestSuite(RegistrationPositionImplFailureTest.class);
    }
}
