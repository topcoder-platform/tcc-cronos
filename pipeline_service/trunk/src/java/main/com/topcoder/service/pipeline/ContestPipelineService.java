/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline;

import com.topcoder.service.pipeline.entities.CompetitionChangeHistory;
import com.topcoder.service.pipeline.searchcriteria.ContestsSearchCriteria;
import com.topcoder.service.pipeline.searchcriteria.DateSearchCriteria;
import com.topcoder.service.project.Competition;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * <p>
 * An interface for the web service for contest pipeline services. It contains methods to find the contests pipeline
 * info and methods to search the competition change history info for both date and prize type.
 * </p>
 * 
 * <p>
 * Updated for Pipeline Conversion Cockpit Integration Assembly 1 v1.0
 *      - Added method for getContestsByDate
 *      - Added method to retrieve change histories for array of contest ids and their types.
 * </p>
 *
 * @author TCSASSEMBLER
 * @since Pipeline Conversion Service Layer Assembly v1.0
 * @version 1.0
 */
public interface ContestPipelineService {
	/**
	 * <p>
	 * Search the contests by the given criteria.
	 * </p>
	 * @param criteria the search criteria, not null
	 * @return List of Competition, could be empty if nothing found
	 * @throws ContestPipelineServiceException fail to do the search
	 */
    List<Competition> getContests(ContestsSearchCriteria criteria)
        throws ContestPipelineServiceException;
    
    /**
     * <p>
     * Search the contests by the given date criteria.
     * </p>
     * @param criteria the date search criteria, not null
     * @return List of Competition, could be empty if nothing found
     * @throws ContestPipelineServiceException fail to do the search
     * 
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    List<Competition> getContestsByDate(DateSearchCriteria criteria) throws ContestPipelineServiceException;

    /**
     * <p>
     * Search the date competition change history for the given contest and competition type.
     * </p>
     * @param contestId the contest id
     * @param competitionType competition type, could be studio or software
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException fail to do the query
     */
    List<CompetitionChangeHistory> getContestDateChangeHistory(long contestId, CompetitionType competitionType)
        throws ContestPipelineServiceException;
    /**
     * <p>
     * Search the prize competition change history for the given contest and competition type.
     * </p>
     * @param contestId the contest id
     * @param competitionType competition type, could be studio or software
     * @return List of CompetitionChangeHistory
     * @throws ContestPipelineServiceException fail to do the query
     */
    List<CompetitionChangeHistory> getContestPrizeChangeHistory(long contestId, CompetitionType competitionType)
        throws ContestPipelineServiceException;
    
    /**
     * <p>
     * Search the date competition change history for the given contest ids and their competition types.
     * </p>
     * 
     * @param contestIds
     *            the contest ids
     * @param competitionTypess
     *            competition types, could be studio or software
     * 
     * @return List of CompetitionChangeHistory
     * 
     * @throws ContestPipelineServiceException
     *             fail to do the query
     * 
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestDateChangeHistories(long[] contestIds,
            String[] competitionTypes) throws ContestPipelineServiceException;

    /**
     * <p>
     * Search the prize competition change history for the given contest ids and their competition types.
     * </p>
     * 
     * @param contestIds
     *            the contest ids
     * @param competitionTypess
     *            competition types, could be studio or software
     * 
     * @return List of CompetitionChangeHistory
     * 
     * @throws ContestPipelineServiceException
     *             fail to do the query
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CompetitionChangeHistory> getContestPrizeChangeHistories(long[] contestIds,
            String[] competitionTypes) throws ContestPipelineServiceException;
}
