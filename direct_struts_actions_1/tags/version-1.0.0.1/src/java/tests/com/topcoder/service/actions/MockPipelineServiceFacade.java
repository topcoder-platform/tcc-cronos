/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

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
import com.topcoder.service.studio.contest.ContestManagementException;

/**
 * <p>
 * This is an implementation of <code>ContestPipelineService</code> web service in form of stateless session EJB.
 * </p>
 * <p>
 * Thread-safety: This is an CMT bean, so it transaction is managed by the container.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class MockPipelineServiceFacade implements PipelineServiceFacade {
    /**
     * A default empty constructor.
     */
    public MockPipelineServiceFacade() {
    }

    /**
     * <p>
     * Search the date competition change history for the given contest ids and their competition types.
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param contestIds
     *            the contest ids
     * @param competitionTypes
     *            competition types, could be studio or software
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException
     *             fail to do the query
     */
    public List<CompetitionChangeHistory> getContestDateChangeHistories(TCSubject tcSubject, long[] contestIds,
            String[] competitionTypes) throws ContestPipelineServiceException {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Search the prize competition change history for the given contest ids and their competition types.
     * </p>
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param contestIds
     *            the contest ids
     * @param competitionTypes
     *            competition types, could be studio or software
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException
     *             fail to do the query
     */
    public List<CompetitionChangeHistory> getContestPrizeChangeHistories(TCSubject tcSubject, long[] contestIds,
            String[] competitionTypes) throws ContestPipelineServiceException {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Search the date competition change history for the given contest and competition type.
     * </p>
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the contest id
     * @param competitionType
     *            competition type, could be studio or software
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException
     *             fail to do the query
     */
    public List<CompetitionChangeHistory> getContestDateChangeHistory(TCSubject tcSubject, long contestId,
            CompetitionType competitionType) throws ContestPipelineServiceException {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>
     * Search the prize competition change history for the given contest and competition type.
     * </p>
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the contest id
     * @param competitionType
     *            competition type, could be studio or software
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException
     *             fail to do the query
     */
    public List<CompetitionChangeHistory> getContestPrizeChangeHistory(TCSubject tcSubject, long contestId,
            CompetitionType competitionType) throws ContestPipelineServiceException {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the list of competition for the specified date search criteria.
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param searchCriteria
     *            the date search criteria
     * @return the list of competition for the specified search criteria.
     * @throws ContestPipelineServiceException
     *             if any error occurs during retrieval of competitions.
     */
    public List<Competition> getContestsByDate(TCSubject tcSubject, DateSearchCriteria criteria)
        throws ContestPipelineServiceException {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the list of competition for the specified search criteria.
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param searchCriteria
     *            the search criteria
     * @return the list of competition for the specified search criteria.
     * @throws ContestPipelineServiceException
     *             if any error occurs during retrieval of competitions.
     */
    public List<Competition> getContests(TCSubject tcSubject, ContestsSearchCriteria criteria)
        throws ContestPipelineServiceException {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the list of common pipeline data within between specified start and end date.
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param startDate
     *            the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate
     *            the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests
     *            whether to include overdue contests or not.
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws ContestManagementException
     *             if error during retrieval from database.
     */
    public List<CommonPipelineData> getCommonPipelineData(TCSubject tcSubject, Date startDate, Date endDate,
            boolean overdueContests) throws ContestPipelineServiceException {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the list of dates that have full capacity starting from tomorrow for the given contest type (for software or
     * studio contests) This method delegates to Pipeline Service layer.
     *
     * @param tcSubject
     *            TCSubject instance contains the login security info for the current user
     * @param contestType
     *            the contest type
     * @param isStudio
     *            true of it is a studio competition, false otherwise
     * @return the list of dates that have full capacity.
     * @throws ContestPipelineServiceException
     *             if any error occurs during retrieval of information.
     */
    public List<CapacityData> getCapacityFullDates(TCSubject tcSubject, int contestType, boolean isStudio)
        throws ContestPipelineServiceException {
        List<CapacityData> results = new ArrayList<CapacityData>();

        for (int i = 1; i <= 5; i++) {
            results.add(new CapacityData());
        }

        return results;
    }
}
