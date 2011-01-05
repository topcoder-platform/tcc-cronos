/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.project.phases.Phase;

import junit.framework.TestCase;

/**
 * Unit tests for <code>{@link LateDeliverable}</code> class.
 *
 * <p>
 * <em>Change in 1.1:</em>
 * <ol>
 * <li>Added tests for the new attribute.</li>
 * </ol>
 * </p>
 *
 * @author myxgyy, sparemax
 * @version 1.1
 */
public class LateDeliverableTests extends TestCase {
    /**
     * The <code>{@link LateDeliverable}</code> instance used for testing.
     */
    private LateDeliverable target;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        target = new LateDeliverable();
    }

    /**
     * <p>
     * Accuracy test case for the {@link LateDeliverable#LateDeliverable()}
     * method.
     * </p>
     */
    public void test_Constructor()  {
        assertNull("deliverable field wrong", target.getDeliverable());
        assertNull("phase field wrong", target.getPhase());
        assertNull("project field wrong", target.getPhase());
    }

    /**
     * <p>
     * Accuracy test case for the {@link LateDeliverable#getDeliverable()} method.
     * </p>
     */
    public void test_getDeliverable()  {
        assertNull("deliverable field should be retrieved correctly", target.getDeliverable());
    }

    /**
     * <p>
     * Accuracy test case for the {@link LateDeliverable#setDeliverable(Deliverable)} method.
     * </p>
     */
    public void test_setDeliverable()  {
        Deliverable d = new Deliverable(1, 112, 1000, null, false);
        target.setDeliverable(d);
        assertSame("deliverable field should be set correctly", d, target.getDeliverable());
    }

    /**
     * <p>
     * Accuracy test case for the {@link LatePhase#getPhase()} method.
     * </p>
     */
    public void test_getPhase()  {
        assertNull("phase field should be retrieved correctly", target.getPhase());
    }

    /**
     * <p>
     * Accuracy test case for the {@link LatePhase#setPhase(Phase)} method.
     * </p>
     */
    public void test_setPhase()  {
        Phase phase = new Phase(new com.topcoder.project.phases.Project(new Date(),
            new DefaultWorkdays()), 100);
        target.setPhase(phase);
        assertSame("phase field should be set correctly", phase, target.getPhase());
    }

    /**
     * <p>
     * Accuracy test case for the {@link LateProject#getProject()} method.
     * </p>
     */
    public void test_getProject()  {
        assertNull("project field should be retrieved correctly", target.getProject());
    }

    /**
     * <p>
     * Accuracy test case for the {@link LateProject#setProject(Project)} method.
     * </p>
     */
    public void test_setProject()  {
        Project project = new Project(1, new ProjectCategory(1, "Dev", new ProjectType(1, "type")),
            new ProjectStatus(2, "Active"));
        target.setProject(project);
        assertSame("project field should be set correctly", project, target.getProject());
    }

    /**
     * <p>
     * Accuracy test case for the {@link LateProject#getCompensatedDeadline()} method.
     * </p>
     *
     * @since 1.1
     */
    public void test_getCompensatedDeadline() {
        assertNull("compensatedDeadline field should be retrieved correctly", target.getCompensatedDeadline());
    }

    /**
     * <p>
     * Accuracy test case for the {@link LateProject#setCompensatedDeadline(Date)} method.
     * </p>
     *
     * @since 1.1
     */
    public void test_setCompensatedDeadline() {
        Date compensatedDeadline = new Date();
        target.setCompensatedDeadline(compensatedDeadline);
        assertSame("project field should be set correctly", compensatedDeadline, target.getCompensatedDeadline());
    }
}
