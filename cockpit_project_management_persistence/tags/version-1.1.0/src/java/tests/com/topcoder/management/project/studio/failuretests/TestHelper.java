/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio.failuretests;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.jndi.MockContextFactory;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestStatus;

/**
 * Failure test helper.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
class TestHelper {

    /**
     * Private ctor.
     */
    private TestHelper() {
    }
    /**
     * Deploy the bean name for test.
     *
     * @throws Exception
     *             to test case
     */
    static void deploy() throws Exception {
        MockContextFactory.setAsInitial();

        MockContestManager validContestManager = new MockContestManager();
        MockContestManager errorContestManager = new MockContestManager();
        errorContestManager.setError(true);
        MockContestManager invalidContestManager = new MockContestManager();
        invalidContestManager.setInvalid(true);
        Object invalidTypeContestManager = new Object();

        Context ctx = new InitialContext();
        ctx.bind("java:comp/env/ValidContestManager", validContestManager);
        ctx.bind("java:comp/env/InvalidTypeContestManager", invalidTypeContestManager);
        ctx.bind("java:comp/env/InvalidContestManager", invalidContestManager);
        ctx.bind("java:comp/env/ErrorContestManager", errorContestManager);
        ctx.bind("java:comp/env/NullContestManager", null);

        MockContextFactory.setDelegateContext(ctx);
    }

    /**
     * Create project.
     *
     * @return the created project
     */
    static Project createProject() {
        ProjectType projectType = new ProjectType(10, "project type");
        ProjectCategory projectCategory = new ProjectCategory(1, "project category", projectType);
        return new Project(10, projectCategory, new ProjectStatus(10, "project status"));
    }

    /**
     * Create contest.
     *
     * @return the created contest.
     */
    static Contest createContest() {
        Contest contest = new Contest();

        ContestChannel contestChannel = new ContestChannel();
        contest.setContestChannel(contestChannel);

        ContestStatus status = new ContestStatus();
        contest.setStatus(status);

        contest.setContestId(1L);
        return contest;
    }

}
