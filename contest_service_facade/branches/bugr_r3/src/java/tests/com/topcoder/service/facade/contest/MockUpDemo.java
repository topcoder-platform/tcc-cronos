/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import java.util.List;

import com.topcoder.service.facade.contest.ejb.ContestServiceFacadeBean;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.SubmissionData;


/**
 * The demo for contest service facade 1.1 new added method. Using the mockup as
 * implementation.
 *
 * @author TCS Developer
 * @version 1.1
 *
 */
public class MockUpDemo {
    private ContestServiceFacadeBean contestServiceFacadeBean;

    /**
     * Demo for added methods in contest service facade 1.1. Using the mockup as
     * implementation.
     *
     * @throws Exception
     * @since 1.1
     */
    public void demo() throws Exception {
        /* Init service */
        this.contestServiceFacadeBean = new ContestServiceFacadeBean();

        MockUpStudioServiceBean mo = new MockUpStudioServiceBean();
        java.lang.reflect.Field f = ContestServiceFacadeBean.class.getDeclaredField(
                "studioService");
        f.setAccessible(true);
        f.set(contestServiceFacadeBean, mo);
        // assume that the submission id and milestone prize id has been created
        // in the persistence.
        // set submission milestone prize
        contestServiceFacadeBean.setSubmissionMilestonePrize(1, 1);

        // get user contests
        List<StudioCompetition> contestsForUser = contestServiceFacadeBean.getUserContests(
                "tom");

        // get milestone submissions for contest
        List<SubmissionData> milestoneSubmissions = contestServiceFacadeBean.getMilestoneSubmissionsForContest(1);

        // get final submissions for contest
        List<SubmissionData> finalSubmissions = contestServiceFacadeBean.getFinalSubmissionsForContest(1);

        // check the final submission
        for (SubmissionData data : finalSubmissions) {
            // do some action here
        }
    }
}
