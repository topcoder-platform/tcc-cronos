/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import javax.jws.WebService;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.submission.Submission;

import java.util.List;

/**
 * <p>
 * This is service interface which defines operations available for client. It
 * allows providing different operations under contest and submission. It allows
 * getting, updating, and creating contest data; get, remove and update
 * submission data; get some additional information like content's categories,
 * statuses and file types.
 * </p>
 * <p>
 * This interface defines the webservices endpoint with the operation. It uses
 * the annotations to defined the requests and responses definitions.
 * </p>
 * 
 * <p>
 * It has the following annotations: "@WebService". All the other annotations
 * (also in the methods) are optional: WebParam, WebMethod are not necessary
 * because if WebMethod is not used then automatically all methods are
 * WebMethod.
 * </p>
 * 
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
@WebService
public interface StudioService {
	/**
	 * Create contest for project. Return contest populated with id
	 * 
	 * @param contestData
	 *            the contestData used to create the Contest
	 * @param tcDirectProjectId
	 *            the tc project id set to Contest
	 * @return the ContestData persisted and polulated with the new id
	 * @throws IllegalArgumentWSException
	 *             if the contestData is null, if the tcDirectProjectId is less
	 *             than 0
	 * @throws PersistenceException
	 *             if some persistence errors occur
	 * @throws UserNotAuthorizedException
	 *             if the user is not authorized to perform this method
	 */
	public ContestData createContest(ContestData contestData,
			long tcDirectProjectId) throws PersistenceException;

	/**
	 * <p>
	 * Get contest by id
	 * 
	 * @param contestId
	 *            the id used to retrireve the contest
	 * @return the ContestData retrieved
	 * @throws IllegalArgumentWSException
	 *             if the contestId is less than 0
	 * @throws PersistenceException
	 *             if some persistence errors occur
	 * @throws UserNotAuthorizedException
	 *             if the user is not authorized to perform this method
	 * @throws ContestNotFoundException
	 *             if the contest is not found
	 */
	public ContestData getContest(long contestId) throws PersistenceException,
			ContestNotFoundException;

	/**
	 * <p>
	 * Get contests for given project. Return an empty list if there are no
	 * contests.
	 * 
	 * @param tcDirectProjectId
	 *            the tc Direct Projec tId used to retrieve the ContestData
	 * @return the contest datas which represents the contests
	 * @throws IllegalArgumentWSException
	 *             if the tcDirectProjectId is less than 0
	 * @throws PersistenceException
	 *             if some persistence errors occur
	 * @throws UserNotAuthorizedException
	 *             if the user is not authorized to perform this method
	 * @throws ProjectNotFoundException
	 *             if the project is not found
	 */
	public List<ContestData> getContestsForProject(long tcDirectProjectId)
			throws PersistenceException, ProjectNotFoundException;

	/**
	 * <p>
	 * Update contest
	 * 
	 * @param contestData
	 *            the contest data to update
	 * @throws IllegalArgumentWSException
	 *             if the argument is null
	 * @throws PersistenceException
	 *             if some persistence errors occur
	 * @throws UserNotAuthorizedException
	 *             if the user is not authorized to perform this method
	 * @throws ContestNotFoundException
	 *             if the contest is not found
	 */
	public void updateContest(ContestData contestData)
			throws PersistenceException, ContestNotFoundException;

	/**
	 * <p>
	 * Update contest status
	 * 
	 * @param contestId
	 *            the id of contest to what the status will be updated
	 * @param newStatusId
	 *            the id of the new status
	 * @throws IllegalArgumentWSException
	 *             if any id is less than 0
	 * @throws PersistenceException
	 *             if some persistence errors occur
	 * @throws UserNotAuthorizedException
	 *             if the user is not authorized to perform this method
	 * @throws StatusNotAllowedException
	 *             if the status is not allowed
	 * @throws StatusNotFoundException
	 *             if the status is not found
	 * @throws ContestNotFoundException
	 *             if the contest is not found
	 */
	public void updateContestStatus(long contestId, long newStatusId)
			throws PersistenceException, StatusNotAllowedException,
			StatusNotFoundException, ContestNotFoundException;

	/**
	 * <p>
	 * Upload document to contest. Return document populated with id.
	 * 
	 * @param uploadedDocument
	 *            the uploadDocument to update
	 * @return the same instance passed in argument with the documentId updated
	 * @throws IllegalArgumentWSException
	 *             if the argument is null
	 * @throws PersistenceException
	 *             if some persistence errors occur
	 * @throws UserNotAuthorizedException
	 *             if the user is not authorized to perform this method
	 * @throws ContestNotFoundException
	 *             if the contest is not found
	 */
	public UploadedDocument uploadDocumentForContest(
			UploadedDocument uploadedDocument) throws PersistenceException,
			ContestNotFoundException;

	/**
	 * <p>
	 * Remove document from contest.
	 * 
	 * @param document
	 *            the document to remove
	 * @throws IllegalArgumentWSException
	 *             if the argument is null
	 * @throws PersistenceException
	 *             if some persistence errors occur
	 * @throws UserNotAuthorizedException
	 *             if the user is not authorized to perform this method
	 * @throws DocumentNotFoundException
	 *             if the document is not found
	 */
	public void removeDocumentFromContest(UploadedDocument document)
			throws PersistenceException, DocumentNotFoundException;

	/**
	 * <p>
	 * Retrieve submission data. return an empty list if there are no submission
	 * data
	 * 
	 * @param contestId
	 *            the contest if used
	 * @return the submission data of contest
	 * @throws IllegalArgumentWSException
	 *             if the argument id less than 0
	 * @throws PersistenceException
	 *             if some persistence errors occur
	 * @throws UserNotAuthorizedException
	 *             if the user is not authorized to perform this method
	 * @throws ContestNotFoundException
	 *             if the contest is not found
	 */
	public List<SubmissionData> retrieveSubmissionsForContest(long contestId)
			throws PersistenceException, ContestNotFoundException;

	/**
	 * <p>
	 * Retrieve submission by member. return an empty list if there are no
	 * submission data
	 * 
	 * @param userId
	 *            the user id used to retrieve the submissions
	 * @return the submissions by member
	 * @throws IllegalArgumentWSException
	 *             if the id is less than 0
	 * @throws PersistenceException
	 *             if some persistence errors occur
	 * @throws UserNotAuthorizedException
	 *             if the user is not authorized to perform this method
	 */
	public List<SubmissionData> retrieveAllSubmissionsByMember(long userId)
			throws PersistenceException, UserNotAuthorizedException;

	/**
	 * <p>
	 * Update submission.
	 * 
	 * @param submission
	 *            the submission to update
	 * @throws IllegalArgumentWSException
	 *             if the submission is null
	 * @throws PersistenceException
	 *             if some persistence errors occur
	 * @throws UserNotAuthorizedException
	 *             if the user is not authorized to perform this method
	 */
	public void updateSubmission(SubmissionData submission)
			throws PersistenceException;

	/**
	 * <p>
	 * Remove submission
	 * 
	 * @param submissionId
	 *            the id of submission to remove
	 * @throws IllegalArgumentWSException
	 *             if the submissionId is less than 0
	 * @throws PersistenceException
	 *             if some persistence errors occur
	 * @throws UserNotAuthorizedException
	 *             if the user is not authorized to perform this method
	 */
	public void removeSubmission(long submissionId) throws PersistenceException;

	/**
	 * <p>
	 * Get contest categories
	 * 
	 * @return the list of categories
	 * @throws PersistenceException
	 *             if some persistence errors occur
	 * @throws UserNotAuthorizedException
	 *             if the user is not authorized to perform this method
	 */
	public List<ContestCategoryData> getContestCategories()
			throws PersistenceException;

	/**
	 * <p>
	 * Get contest statuses. Return an empty list if there are no
	 * ContestStatusData
	 * 
	 * @return the list of status
	 * @throws PersistenceException
	 *             if some persistence errors occur
	 * @throws UserNotAuthorizedException
	 *             if the user is not authorized to perform this method
	 */
	public List<ContestStatusData> getStatusList() throws PersistenceException;

	/**
	 * <p>
	 * Get submission types
	 * 
	 * @return the file types
	 * @throws PersistenceException
	 *             if some persistence errors occur
	 * @throws UserNotAuthorizedException
	 *             if the user is not authorized to perform this method
	 */
	public String getSubmissionFileTypes() throws PersistenceException;

	/**
	 * <p>
	 * This is going to fetch all the currently available contests.
	 * </p>
	 * 
	 * @return the list of all available contents (or empty if none found)
	 * 
	 * @throws PersistenceException
	 *             if any error occurs when getting contest.
	 */
	public List<Contest> getAllContests() throws PersistenceException;

	/**
	 * <p>
	 * This is going to get all the matching contest entities that fulfill the
	 * input criteria.
	 * </p>
	 * 
	 * @param filter
	 *            a search filter used as criteria for contests.
	 * @return a list (possibly empty) of all the matched contest entities.
	 * 
	 * @throws IllegalArgumentException
	 *             if the input filter is null or filter is not supported for
	 *             searching
	 * @throws PersistenceException
	 *             if any error occurs when getting contest.
	 */
	public List<Contest> searchContests(Filter filter)
			throws PersistenceException;

	/**
	 * Returns all contests for a particular client ID, provided as a long
	 * 
	 * @param clientId
	 *            client Id to search for.
	 * @return all contests for the given client ID.
	 * @throws PersistenceException
	 *             if any error occurs when getting contest.
	 */
	public List<Contest> getContestsForClient(long clientId)
			throws PersistenceException;

    /**
     * <p>
     * Gets the submission with the given id.
     * </p>
     *
     * @param submissionId
     *            The id of the submission to get
     * @return the submission with the given id, or null if not found
     * @throws PersistenceException
     *             If any error occurs during the retrieval
     */
	public Submission retrieveSubmission(long submissionId)
			throws PersistenceException;
}