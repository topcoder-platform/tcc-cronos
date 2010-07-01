package com.topcoder.direct.services.view.action.contest.launch.failuretests;

import java.util.List;

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

public class FailureMockDirectServiceFacade implements DirectServiceFacade {

    public long createNewVersionForDesignDevContest(TCSubject tcSubject,
            long contestId) throws DirectServiceFacadeException {
        // TODO Auto-generated method stub
        return 0;
    }

    public void deleteContest(TCSubject tcSubject, long contestId,
            boolean studio) throws DirectServiceFacadeException {
        // TODO Auto-generated method stub

    }

    public void extendActiveContestSchedule(TCSubject tcSubject,
            ContestScheduleExtension extension)
            throws DirectServiceFacadeException {
        // TODO Auto-generated method stub

    }

    public List<Project> getChildProjects(TCSubject tcSubject, long projectId)
            throws DirectServiceFacadeException {
        // TODO Auto-generated method stub
        return null;
    }

    public ContestPrize getContestPrize(TCSubject tcSubject, long contestId,
            boolean studio) throws DirectServiceFacadeException {
        ContestPrize prize = new ContestPrize();
        prize.setContestPrizes(new double[]{1000.0, 500.0});
        prize.setMilestonePrizes(new double[]{1000.0, 500.0});
        return prize;
    }

    public ContestReceiptData getContestReceiptData(TCSubject tcSubject,
            long contestId, boolean isStudio)
            throws DirectServiceFacadeException {
        // TODO Auto-generated method stub
        return null;
    }

    public ContestSchedule getContestSchedule(TCSubject tcSubject,
            long contestId, boolean studio) throws DirectServiceFacadeException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Project> getParentProjects(TCSubject tcSubject, long projectId)
            throws DirectServiceFacadeException {
        // TODO Auto-generated method stub
        return null;
    }

    public double getProjectBudget(TCSubject tcSubject, long billingProjectId)
            throws DirectServiceFacadeException {
        // TODO Auto-generated method stub
        return 0;
    }

    public List<ContestPlan> getProjectGamePlan(TCSubject tcSubject,
            long projectId) throws DirectServiceFacadeException {
        // TODO Auto-generated method stub
        return null;
    }

    public SpecReviewState getSpecReviewState(TCSubject tcSubject,
            long contestId) throws DirectServiceFacadeException {
        // TODO Auto-generated method stub
        return null;
    }

    public long repostSoftwareContest(TCSubject tcSubject, long contestId)
            throws DirectServiceFacadeException {
        // TODO Auto-generated method stub
        return 0;
    }

    public ContestReceiptData sendContestReceiptByEmail(TCSubject tcSubject,
            long contestId, boolean isStudio, String[] additionalEmailAddrs)
            throws DirectServiceFacadeException {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateActiveContestPrize(TCSubject tcSubject,
            ContestPrize contestPrize) throws DirectServiceFacadeException {
        // TODO Auto-generated method stub

    }

    public ProjectBudget updateProjectBudget(TCSubject tcSubject,
            long billingProjectId, double changedAmount)
            throws DirectServiceFacadeException {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateProjectLinks(TCSubject tcSubject, long projectId,
            long[] parentProjectIds, long[] childProjectIds)
            throws DirectServiceFacadeException {
        // TODO Auto-generated method stub

    }

}
