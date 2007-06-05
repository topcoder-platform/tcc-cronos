/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test case for <code>RegistrationPositionImpl</code> class.
 * </p>
 * @author moonli
 * @version 1.0
 */
public class RegistrationPositionImplTest extends TestCase {

    /**
     * <p>
     * Represents an instance of <code>RegistrationPositionImpl</code>.
     * </p>
     */
    private RegistrationPositionImpl regPos;

    /**
     * <p>
     * Represents an instance of <code>Project</code>.
     * </p>
     */
    private Project project;

    /**
     * <p>
     * Represents an instance of <code>Phase</code>.
     * </p>
     */
    private Phase phase;

    /**
     * <p>
     * Represents an array of <code>ResourceRole</code>.
     * </p>
     */
    private ResourceRole[] roles;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        project = new Project(1, new ProjectCategory(1, "cate-1", new ProjectType(1, "type-1")),
            new ProjectStatus(1, "dev"));

        com.topcoder.project.phases.Project theProject = new com.topcoder.project.phases.Project(
            new Date(), new DefaultWorkdays());
        theProject.setId(1);
        phase = new Phase(theProject, 1000000);
        phase.setId(1);
        phase.setPhaseStatus(PhaseStatus.OPEN);

        roles = new ResourceRole[] {new ResourceRole(1), new ResourceRole(2)};

        regPos = new RegistrationPositionImpl(project, phase, roles);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        this.phase = null;
        this.project = null;
        this.regPos = null;
        this.roles = null;
    }

    /**
     * <p>
     * Test for default constructor.
     * </p>
     * <p>
     * Tests it for accuracy, non-null instance should be created.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultCtorAccuracy() throws Exception {
        assertNotNull("Should not be null.", new RegistrationPositionImpl());
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it against null project.Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithNullProject() throws Exception {
        try {
            new RegistrationPositionImpl(null, phase, roles);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it against null phase. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithNullPhase() throws Exception {
        try {
            new RegistrationPositionImpl(project, null, roles);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it against null roles array.Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithNullRoles() throws Exception {
        try {
            new RegistrationPositionImpl(project, phase, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it against roles containing null elements. Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithRolesHasNullEle() throws Exception {
        try {
            new RegistrationPositionImpl(project, phase, new ResourceRole[] {new ResourceRole(1),
                null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it for accuracy, non-null instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorAccuracy() throws Exception {
        assertNotNull("'regPos' should not be null.", regPos);
        assertEquals("Project mismatched.", project, regPos.getProject());
        assertEquals("Phase mismatched.", phase, regPos.getPhase());
        assertEquals("There should be two roles.", 2, regPos.getAvailableRoles().length);
    }

    /**
     * <p>
     * Test for <code>getProject</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, non-null Project instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectAccuracy() throws Exception {
        assertEquals("Project mismatched.", project, regPos.getProject());
    }

    /**
     * <p>
     * Test for <code>getPhase</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, non-null Phase instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetPhaseAccuracy() throws Exception {
        assertEquals("Phase mismatched.", phase, regPos.getPhase());
    }

    /**
     * <p>
     * Test for <code>getAvailableRoles</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, an array containing two instances of ResourceRole should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetAvailableRolesAccuracy() throws Exception {
        assertEquals("There should be two elements.", 2, regPos.getAvailableRoles().length);
    }

    /**
     * <p>
     * Test for <code>setProject(project)</code> method.
     * </p>
     * <p>
     * Tests it against null project. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetProjectWithNullArg() throws Exception {
        try {
            regPos.setProject(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>setProject(project)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, project should be set successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetProjectAccuracy() throws Exception {
        regPos = new RegistrationPositionImpl();

        assertNull("Null should be returned.", regPos.getProject());
        regPos.setProject(project);
        assertEquals("Project mismatched.", project, regPos.getProject());
    }

    /**
     * <p>
     * Test for <code>setPhase(Phase)</code> method.
     * </p>
     * <p>
     * Tests it against null Phase. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetPhaseWithNullArg() throws Exception {
        try {
            regPos.setPhase(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>setPhase(Phase)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, Phase should be set successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetPhaseAccuracy() throws Exception {
        regPos = new RegistrationPositionImpl();

        assertNull("Null should be returned.", regPos.getPhase());
        regPos.setPhase(phase);
        assertEquals("Phase mismatched.", phase, regPos.getPhase());
    }

    /**
     * <p>
     * Test for <code>setAvailableRoles(roles)</code> method.
     * </p>
     * <p>
     * Tests it against null argument. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetAvailableRolesWithNullArg() throws Exception {
        try {
            regPos.setAvailableRoles(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>setAvailableRoles(roles)</code> method.
     * </p>
     * <p>
     * Tests it against array containing null elements. Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetAvailableRolesWithArrayHasNullEle() throws Exception {
        try {
            regPos.setAvailableRoles(new ResourceRole[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>setAvailableRoles(roles)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, there should be only one available role now.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetAvailableRolesAccuracy() throws Exception {
        regPos.setAvailableRoles(new ResourceRole[] {new ResourceRole(3)});
        assertEquals("There should be only one role.", 1, regPos.getAvailableRoles().length);
    }

}
