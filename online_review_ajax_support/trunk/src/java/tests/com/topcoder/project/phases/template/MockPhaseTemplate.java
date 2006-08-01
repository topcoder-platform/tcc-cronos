/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import java.util.Date;

import com.cronos.onlinereview.ajax.TestHelper;
import com.topcoder.project.phases.MockPhase;
import com.topcoder.project.phases.MockPhaseType;
import com.topcoder.project.phases.MockProject;
import com.topcoder.project.phases.Project;

/**
 * Mock implementation of <code>PhaseTemplate</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPhaseTemplate implements PhaseTemplate {

    /**
     * Apply template.
     * @param template the template
     * @param startDate the start date.
     * @return the project
     */
    public Project applyTemplate(String template, Date startDate) {
        Project project = new MockProject();
        project.setId(4);
        project.setStartDate(startDate);

        MockPhase phase = new MockPhase();
        phase.setFixedStartDate(TestHelper.DATE_1);
        phase.calcStartDate = TestHelper.DATE_2;
        phase.calcEndDate = TestHelper.DATE_2;
        phase.setLength(1000);
        phase.setId(33);

        phase.setPhaseType(new MockPhaseType());

        project.addPhase(phase);

        return project;
    }

    /**
     * Apply template.
     * @param template the template
     * @return the project
     */
    public Project applyTemplate(String template) {
        Project project = new MockProject();
        project.setId(4);

        MockPhase phase = new MockPhase();
        phase.setFixedStartDate(TestHelper.DATE_1);
        phase.calcStartDate = TestHelper.DATE_2;
        phase.calcEndDate = TestHelper.DATE_2;
        phase.setLength(1000);
        phase.setId(33);

        phase.setPhaseType(new MockPhaseType());

        project.addPhase(phase);

        return project;
    }

    /**
     * Get names for all the template.
     * @return all the template names
     */
    public String[] getAllTemplateNames() {
        return new String[] {"Screening", "Review", "Appeal", "Appeal Response"};
    }

}
