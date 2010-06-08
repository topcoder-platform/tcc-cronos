/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct.ejb;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.direct.ContestPrize;
import com.topcoder.service.facade.direct.ContestScheduleExtension;
import com.topcoder.service.facade.direct.DirectServiceFacade;

import junit.framework.TestCase;

/**
 * <p>
 * Demonstrates the usage of this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {

    /**
     * <p>
     * Demo test for this component.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testDemo() throws Exception {
        // Get direct service facade
        Context context = new InitialContext();
        DirectServiceFacade directServiceFacade = (DirectServiceFacadeRemote) context
                .lookup("DirectServiceFacadeBean/remote");

        TCSubject tcSubject = new TCSubject(1);

        // Retrieve the contest receipt data for studio contest with ID=1001
        directServiceFacade.getContestReceiptData(tcSubject, 1, true);

        // Send contest receipt by email for this contest
        String[] additionalEmailAddrs = new String[] {"user@mail.com"};
        directServiceFacade.sendContestReceiptByEmail(tcSubject, 1, true, additionalEmailAddrs);

        // Retrieve the contest prize for software contest with ID=2001
        ContestPrize contestPrize = directServiceFacade.getContestPrize(tcSubject, 1, true);

        // Update first place prize for this contest
        double contestPrizes[] = contestPrize.getContestPrizes();
        contestPrizes[0] += 100;
        contestPrize.setContestPrizes(contestPrizes);
        directServiceFacade.updateActiveContestPrize(tcSubject, contestPrize);

        // Retrieve the contest schedule
        directServiceFacade.getContestSchedule(tcSubject, 2001, false);

        // Extend registration phase by 24 hours
        ContestScheduleExtension extension = new ContestScheduleExtension();
        extension.setContestId(1);
        extension.setExtendMilestoneBy(1);
        extension.setExtendRegistrationBy(24);
        extension.setExtendSubmissionBy(3);
        directServiceFacade.extendActiveContestSchedule(tcSubject, extension);

        // Repost software contest with ID=2001
        directServiceFacade.repostSoftwareContest(tcSubject, 2001);

        // Create new version for design contest with ID=2002
        directServiceFacade.createNewVersionForDesignDevContest(tcSubject, 2002);

        // Delete software contest with ID=2002
        directServiceFacade.deleteContest(tcSubject, 2002, false);

        // Retrieve the project game plan for project with ID=1
        directServiceFacade.getProjectGamePlan(tcSubject, 1);

        // Retrieve the parent projects for project with ID=2001
        directServiceFacade.getParentProjects(tcSubject, 2001);

        // Retrieve the child projects for project with ID=2001
        directServiceFacade.getChildProjects(tcSubject, 2001);

        // Update project links (remove all links)
        long[] parentProjectIds = new long[0];
        long[] childProjectIds = new long[0];
        directServiceFacade.updateProjectLinks(tcSubject, 2001, parentProjectIds, childProjectIds);

        // Retrieve the project budget for billing project with ID=1
        directServiceFacade.getProjectBudget(tcSubject, 1);

        // Update project budget (increase by 1000)
        directServiceFacade.updateProjectBudget(tcSubject, 1, 1000);

        // Retrieve the spec review state for contest with ID=2001
        directServiceFacade.getSpecReviewState(tcSubject, 2001);
    }
}
