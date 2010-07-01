/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.accuracytests;

import com.topcoder.management.project.Project;

import com.topcoder.security.TCSubject;

import com.topcoder.service.facade.direct.ContestPlan;
import com.topcoder.service.facade.direct.ContestPrize;
import com.topcoder.service.facade.direct.ContestReceiptData;
import com.topcoder.service.facade.direct.ContestSchedule;
import com.topcoder.service.facade.direct.ContestScheduleExtension;
import com.topcoder.service.facade.direct.DirectServiceFacade;
import com.topcoder.service.facade.direct.DirectServiceFacadeException;
import com.topcoder.service.facade.direct.ProjectBudget;
import com.topcoder.service.facade.direct.SpecReviewState;

import java.util.ArrayList;
import java.util.List;


/**
 * The mock class for testing.
 *
 * @author KLW
 * @version 1.0
 */
public class MockDirectServiceFacade implements DirectServiceFacade {
    /** the extension for testing. */
    private ContestScheduleExtension contestScheduleExtension;
    /**
     * the prize for testing.
     */
	private ContestPrize contestPrize;
    /**
     * gets the prize.
     * @return
     */
    public ContestPrize getContestPrize() {
		return contestPrize;
	}

	/**
     * gets the extension.
     *
     * @return
     */
    public ContestScheduleExtension getContestScheduleExtension() {
        return contestScheduleExtension;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     * @param isStudio DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    
    public ContestReceiptData getContestReceiptData(TCSubject tcSubject, long contestId, boolean isStudio)
        throws DirectServiceFacadeException {
        if (contestId == 1) {
            ContestReceiptData data = new ContestReceiptData();
            data.setContestId(1);
            data.setStudio(isStudio);

            return data;
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     * @param isStudio DOCUMENT ME!
     * @param additionalEmailAddrs DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public ContestReceiptData sendContestReceiptByEmail(TCSubject tcSubject, long contestId, boolean isStudio,
        String[] additionalEmailAddrs) throws DirectServiceFacadeException {
        if (contestId == 1) {
            ContestReceiptData data = new ContestReceiptData();
            data.setContestId(1);
            data.setStudio(isStudio);

            return data;
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestPrize DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public void updateActiveContestPrize(TCSubject tcSubject, ContestPrize contestPrize)
        throws DirectServiceFacadeException {
    	this.contestPrize = contestPrize;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     * @param studio DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public ContestPrize getContestPrize(TCSubject tcSubject, long contestId, boolean studio)
        throws DirectServiceFacadeException {
        ContestPrize contestPrize = new ContestPrize();

        if (contestId == 1) {
            contestPrize.setContestPrizes(new double[] { 900.0, 750.0 });
            contestPrize.setMilestonePrizes(new double[] { 200.0, 200.0 });
        }

        return contestPrize;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     * @param studio DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public ContestSchedule getContestSchedule(TCSubject tcSubject, long contestId, boolean studio)
        throws DirectServiceFacadeException {
        if (contestId == 1) {
            ContestSchedule schedule = new ContestSchedule();
            schedule.setContestId(1);
            schedule.setStudio(studio);

            return schedule;
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param extension DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public void extendActiveContestSchedule(TCSubject tcSubject, ContestScheduleExtension extension)
        throws DirectServiceFacadeException {
        this.contestScheduleExtension = extension;
        System.out.println("execute extendActiveContestSchedule.");
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public long repostSoftwareContest(TCSubject tcSubject, long contestId)
        throws DirectServiceFacadeException {
        return 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public long createNewVersionForDesignDevContest(TCSubject tcSubject, long contestId)
        throws DirectServiceFacadeException {
        return 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     * @param studio DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public void deleteContest(TCSubject tcSubject, long contestId, boolean studio)
        throws DirectServiceFacadeException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public List<ContestPlan> getProjectGamePlan(TCSubject tcSubject, long projectId)
        throws DirectServiceFacadeException {
        if (projectId == 1) {
            List<ContestPlan> plans = new ArrayList<ContestPlan>();
            ContestPlan plan1 = new ContestPlan();
            plan1.setName("plan1");

            ContestPlan plan2 = new ContestPlan();
            plan2.setName("plan2");
            plans.add(plan1);
            plans.add(plan2);

            return plans;
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public List<Project> getParentProjects(TCSubject tcSubject, long projectId)
        throws DirectServiceFacadeException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public List<Project> getChildProjects(TCSubject tcSubject, long projectId)
        throws DirectServiceFacadeException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param projectId DOCUMENT ME!
     * @param parentProjectIds DOCUMENT ME!
     * @param childProjectIds DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public void updateProjectLinks(TCSubject tcSubject, long projectId, long[] parentProjectIds, long[] childProjectIds)
        throws DirectServiceFacadeException {
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param billingProjectId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public double getProjectBudget(TCSubject tcSubject, long billingProjectId)
        throws DirectServiceFacadeException {
        return 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param billingProjectId DOCUMENT ME!
     * @param changedAmount DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public ProjectBudget updateProjectBudget(TCSubject tcSubject, long billingProjectId, double changedAmount)
        throws DirectServiceFacadeException {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tcSubject DOCUMENT ME!
     * @param contestId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DirectServiceFacadeException DOCUMENT ME!
     */
    
    public SpecReviewState getSpecReviewState(TCSubject tcSubject, long contestId)
        throws DirectServiceFacadeException {
        return null;
    }
}
