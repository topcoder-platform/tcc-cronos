package com.topcoder.service.actions.stresstests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.security.TCSubject;
import com.topcoder.service.pipeline.CapacityData;
import com.topcoder.service.pipeline.CommonPipelineData;
import com.topcoder.service.pipeline.CompetitionType;
import com.topcoder.service.pipeline.ContestPipelineServiceException;
import com.topcoder.service.pipeline.PipelineServiceFacade;
import com.topcoder.service.pipeline.entities.CompetitionChangeHistory;
import com.topcoder.service.pipeline.searchcriteria.ContestsSearchCriteria;
import com.topcoder.service.pipeline.searchcriteria.DateSearchCriteria;
import com.topcoder.service.project.Competition;

public class StressMockPipelineServiceFacade implements PipelineServiceFacade {

    public List<CapacityData> getCapacityFullDates(TCSubject tcSubject, int contestType, boolean isStudio)
            throws ContestPipelineServiceException {
        List<CapacityData> result = new ArrayList<CapacityData>();
        for (int i = 0; i < 3000; i++) {
            CapacityData prj = new CapacityData();
            result.add(prj);
        }
        return result;
    }

    public List<CommonPipelineData> getCommonPipelineData(TCSubject tcSubject, Date startDate, Date endDate,
            boolean overdueContests) throws ContestPipelineServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<CompetitionChangeHistory> getContestDateChangeHistory(TCSubject tcSubject, long contestId,
            CompetitionType competitionType) throws ContestPipelineServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<CompetitionChangeHistory> getContestPrizeChangeHistories(TCSubject tcSubject,
            long[] contestIds, String[] competitionTypes) throws ContestPipelineServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<CompetitionChangeHistory> getContestPrizeChangeHistory(TCSubject tcSubject, long contestId,
            CompetitionType competitionType) throws ContestPipelineServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Competition> getContests(TCSubject tcSubject, ContestsSearchCriteria criteria)
            throws ContestPipelineServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Competition> getContestsByDate(TCSubject tcSubject, DateSearchCriteria criteria)
            throws ContestPipelineServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<CompetitionChangeHistory> getContestDateChangeHistories(TCSubject tcSubject,
            long[] contestIds, String[] competitionTypes) throws ContestPipelineServiceException {
        // TODO Auto-generated method stub
        return null;
    }

}
