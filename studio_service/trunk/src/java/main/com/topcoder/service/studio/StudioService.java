/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.util.List;

import javax.ejb.Remote;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.SimpleContestData;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.EntityAlreadyExistsException;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.SimpleProjectPermissionData;
import com.topcoder.service.studio.contest.SimpleProjectContestData;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.User;
import com.topcoder.service.studio.submission.Submission;

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
 * <p>
 * Module Cockpit Contest Service Enhancement Assembly change: Several new
 * methods related to the permission and permission type are added.
 * </p>
 *
 * <p>
 * Module Cockpit Share Submission Integration Assembly change: Added method to
 * retrieve all permissions by projectId.
 * </p>
 *
 * <p>
 * All the methods that does CRUD on permission have been commented for Cockpit
 * Project Admin Release Assembly v1.0.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER, TCSASSEMBLER
 * @version 1.0
 */
// @WebService
@Remote
public interface StudioService {
    /**
     * Create contest for project. Return contest populated with id
     *
     * @param contestData the contestData used to create the Contest
     * @param tcDirectProjectId the tc project id set to Contest
     * @return the ContestData persisted and polulated with the new id
     * @throws IllegalArgumentWSException if the contestData is null, if the
     *         tcDirectProjectId is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     */
    public ContestData createContest(ContestData contestData, long tcDirectProjectId)
        throws PersistenceException;

    /**
     * <p>
     * Get contest by id
     *
     * @param contestId the id used to retrireve the contest
     * @return the ContestData retrieved
     * @throws IllegalArgumentWSException if the contestId is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     * @throws ContestNotFoundException if the contest is not found
     */
    public ContestData getContest(long contestId) throws PersistenceException, ContestNotFoundException;

    /**
     * <p>
     * Get contests for given project. Return an empty list if there are no
     * contests.
     *
     * @param tcDirectProjectId the tc Direct Projec tId used to retrieve the
     *        ContestData
     * @return the contest datas which represents the contests
     * @throws IllegalArgumentWSException if the tcDirectProjectId is less than
     *         0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     * @throws ProjectNotFoundException if the project is not found
     */
    public List<ContestData> getContestsForProject(long tcDirectProjectId)
        throws PersistenceException,
            ProjectNotFoundException;

    /**
     * <p>
     * Update contest
     *
     * @param contestData the contest data to update
     * @throws IllegalArgumentWSException if the argument is null
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     * @throws ContestNotFoundException if the contest is not found
     */
    public void updateContest(ContestData contestData) throws PersistenceException, ContestNotFoundException;

    /**
     * <p>
     * Update contest status
     *
     * @param contestId the id of contest to what the status will be updated
     * @param newStatusId the id of the new status
     * @throws IllegalArgumentWSException if any id is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     * @throws StatusNotAllowedException if the status is not allowed
     * @throws StatusNotFoundException if the status is not found
     * @throws ContestNotFoundException if the contest is not found
     */
    public void updateContestStatus(long contestId, long newStatusId)
        throws PersistenceException,
            StatusNotAllowedException,
            StatusNotFoundException,
            ContestNotFoundException;

    /**
     * <p>
     * Upload document to contest. Return document populated with id.
     *
     * @param uploadedDocument the uploadDocument to update
     * @return the same instance passed in argument with the documentId updated
     * @throws IllegalArgumentWSException if the argument is null
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     * @throws ContestNotFoundException if the contest is not found
     */
    public UploadedDocument uploadDocumentForContest(UploadedDocument uploadedDocument)
        throws PersistenceException,
            ContestNotFoundException;

    /**
     * <p>
     * Upload document. Return document populated with id.
     *
     * @param uploadedDocument the uploadDocument to update
     * @return the same instance passed in argument with the documentId updated
     * @throws IllegalArgumentWSException if the argument is null
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     */
    public UploadedDocument uploadDocument(UploadedDocument uploadedDocument) throws PersistenceException;

    /**
     * <p>
     * Links a document to a contest.
     *
     * @param documentId document id, which document will be linked.
     * @param contestId cpntest id, which contest will be linked.
     * @throws IllegalArgumentWSException if any id is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     * @throws ContestNotFoundException if the contest or document is not found.
     */
    public void addDocumentToContest(long documentId, long contestId)
        throws PersistenceException,
            ContestNotFoundException;

    /**
     * <p>
     * Remove document from contest.
     *
     * @param document the document to remove
     * @throws IllegalArgumentWSException if the argument is null
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     * @throws DocumentNotFoundException if the document is not found
     */
    public void removeDocumentFromContest(UploadedDocument document)
        throws PersistenceException,
            DocumentNotFoundException;

    /**
     * <p>
     * Retrieve submission data. return an empty list if there are no submission
     * data
     *
     * @param contestId the contest if used
     * @return the submission data of contest
     * @throws IllegalArgumentWSException if the argument id less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     * @throws ContestNotFoundException if the contest is not found
     */
    public List<SubmissionData> retrieveSubmissionsForContest(long contestId)
        throws PersistenceException,
            ContestNotFoundException;

    /**
     * <p>
     * Retrieve submission by member. return an empty list if there are no
     * submission data
     *
     * @param userId the user id used to retrieve the submissions
     * @return the submissions by member
     * @throws IllegalArgumentWSException if the id is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     */
    public List<SubmissionData> retrieveAllSubmissionsByMember(long userId)
        throws PersistenceException,
            UserNotAuthorizedException;

    /**
     * <p>
     * Update submission.
     *
     * @param submission the submission to update
     * @throws IllegalArgumentWSException if the submission is null
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     */
    public void updateSubmission(SubmissionData submission) throws PersistenceException;

    /**
     * <p>
     * Remove submission
     *
     * @param submissionId the id of submission to remove
     * @throws IllegalArgumentWSException if the submissionId is less than 0
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     */
    public void removeSubmission(long submissionId) throws PersistenceException;

    /**
     * <p>
     * Get contest statuses. Return an empty list if there are no
     * ContestStatusData
     *
     * @return the list of status
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     */
    public List<ContestStatusData> getStatusList() throws PersistenceException;

    /**
     * <p>
     * Get submission types
     *
     * @return the file types
     * @throws PersistenceException if some persistence errors occur
     * @throws UserNotAuthorizedException if the user is not authorized to
     *         perform this method
     */
    public String getSubmissionFileTypes() throws PersistenceException;

    /**
     * <p>
     * This is going to fetch all the currently available contests.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<ContestData> getAllContests() throws PersistenceException;

    /**
     * <p>
     * This is going to fetch all the currently available contests for contest
     * monitor widget.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleContestData> getSimpleContestData() throws PersistenceException;

    /**
     * <p>
     * This is going to fetch all the currently available contests related given
     * project.
     * </p>
     *
     * @param pid the given project id
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleContestData> getSimpleContestData(long pid) throws PersistenceException;

    /**
     * <p>
     * This is going to fetch all the currently available contests for my
     * project widget.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData() throws PersistenceException;

    /**
     * <p>
     * This is going to fetch all the currently available contests related to
     * given projectid.
     * </p>
     *
     * @param pid the given project id
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData(long pid) throws PersistenceException;

    /**
     * <p>
     * This is going to get all the matching contest entities that fulfill the
     * input criteria.
     * </p>
     *
     * @param filter a search filter used as criteria for contests.
     * @return a list (possibly empty) of all the matched contest entities.
     *
     * @throws IllegalArgumentException if the input filter is null or filter is
     *         not supported for searching
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<ContestData> searchContests(Filter filter) throws PersistenceException;

    /**
     * Returns all contests for a particular client ID, provided as a long
     *
     * @param clientId client Id to search for.
     * @return all contests for the given client ID.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<ContestData> getContestsForClient(long clientId) throws PersistenceException;

    /**
     * <p>
     * Gets the submission with the given id.
     * </p>
     *
     * @param submissionId The id of the submission to get
     * @return the submission with the given id, or null if not found
     * @throws PersistenceException If any error occurs during the retrieval
     */
    public Submission retrieveSubmission(long submissionId) throws PersistenceException;

    /**
     * <p>
     * Gets the submission with the given id.
     * </p>
     *
     * @param submissionId The id of the submission to get
     * @return the submission with the given id, or null if not found
     * @throws PersistenceException If any error occurs during the retrieval
     */
    public SubmissionData retrieveSubmissionData(long submissionId) throws PersistenceException;

    /**
     * <p>
     * This is going to fetch all the currently available contest types.
     * </p>
     *
     * @return the list of all available content types (or empty if none found)
     *
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<ContestTypeData> getAllContestTypes() throws PersistenceException;

    /**
     * <p>
     * Remove document
     *
     * @param documentId the id of document to remove
     * @throws IllegalArgumentWSException if the documentId is less than 0
     * @throws PersistenceException if some persistence errors occur
     */
    public boolean removeDocument(long documentId) throws PersistenceException;

    /**
     * <p>
     * Get matched the MimeType id.
     * </p>
     *
     * @param contentType
     * @return the matched MimeType id.
     *
     * @throws PersistenceException if any error occurs when getting MimeType
     * @throws IllegalArgumentWSException if the argument is null or empty.
     */
    public long getMimeTypeId(String contentType) throws PersistenceException;

    /**
     * <p>
     * Purchase submission.
     * </p>
     * <p>
     * set the price of submission. create an entry at submission payment table
     * </p>
     *
     * @param submissionId the id of submission to remove
     * @param payPalOrderId PayPal order id.
     * @param securityToken the security token.
     *
     * @throws PersistenceException if any error occurs when purchasing
     *         submission.
     * @throws IllegalArgumentWSException if the submissionId is less than 0 or
     *         price is negative.
     */
    public void purchaseSubmission(long submissionId, String payPalOrderId, String securityToken)
        throws PersistenceException;

    /**
     * <p>
     * Creates a new contest payment and returns the created contest payment.
     * </p>
     *
     * @param contestPayment the contest payment to create.
     * @param securityToken the security token.
     *
     * @return the created contest payment.
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public ContestPaymentData createContestPayment(ContestPaymentData contestPayment, String securityToken)
        throws PersistenceException;

    /**
     * <p>
     * Gets contest payment by id, and return the retrieved contest payment. If
     * the contest payment doesn't exist, null is returned.
     * </p>
     *
     * @param contestId the contest id
     * @return the retrieved contest, or null if id doesn't exist
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contest.
     * @since BUGR-1363 changed method signature
     */
    public List<ContestPaymentData> getContestPayments(long contestId) throws PersistenceException;

    /**
     * <p>
     * Updates contest payment data.
     * </p>
     *
     * @param contestPayment the contest payment to update
     * @throws IllegalArgumentException if the argument is null.
     * @throws EntityNotFoundException if the contest payment doesn't exist in
     *         persistence.
     * @throws ContestManagementException if any error occurs when updating
     *         contest payment.
     */
    public void editContestPayment(ContestPaymentData contestPayment) throws PersistenceException;

    /**
     * <p>
     * Removes contest payment, return true if the contest payment exists and
     * removed successfully, return false if it doesn't exist.
     * </p>
     *
     * @param contestPaymentId the contest payment id
     * @return true if the contest payment exists and removed successfully,
     *         return false if it doesn't exist
     * @throws ContestManagementException if any error occurs.
     */
    public boolean removeContestPayment(long contestPaymentId) throws PersistenceException;

    /**
     * <p>
     * This is going to fetch all the currently available media.
     * </p>
     *
     * @return the list of all available mediums (or empty if none found)
     *
     * @throws PersistenceException if any error occurs when getting medium.
     */
    public List<MediumData> getAllMediums() throws PersistenceException;

    /**
     * Set submission placement.
     *
     * @param submissionId Submission Id.
     * @param placement placement
     * @throws PersistenceException if any error occurs when setting placement.
     * @since TCCC-353
     */
    public void setSubmissionPlacement(long submissionId, int placement) throws PersistenceException;

    /**
     * Set submission placement.
     *
     * @param submissionId Submission Id.
     * @param prizeId prizeId
     * @throws PersistenceException if any error occurs when setting placement.
     * @since TCCC-353
     */
    public void setSubmissionPlacement(long submissionId, long prizeId) throws PersistenceException;

    /**
     * Marks submission for purchase.
     *
     * @param submissionId Submission Id.
     * @throws PersistenceException if any error occurs when marking for
     *         purchase.
     *
     * @since TCCC-353
     */
    public void markForPurchase(long submissionId) throws PersistenceException;

    /**
     * Add a change history entity.
     *
     * @param history Change history entity to be added.
     *
     * @throws PersistenceException if any other error occurs.
     */
    public void addChangeHistory(List<ChangeHistoryData> history) throws PersistenceException;

    /**
     * Returns change history entity list.
     *
     * @param contestId contest id to search for.
     * @return Change history entities match the contest id.
     * @throws PersistenceException if any other error occurs.
     */
    public List<ChangeHistoryData> getChangeHistory(long contestId) throws PersistenceException;

    /**
     * Returns latest change history entity list.
     *
     * @param contestId contest id to search for.
     *
     * @return Latest change history entities match the contest id and
     *         transaction id.
     * @throws PersistenceException if any other error occurs.
     */
    public List<ChangeHistoryData> getLatestChanges(long contestId) throws PersistenceException;

    /**
     * Delete contest.
     *
     * @param contestId contest id to delete.
     * @throws PersistenceException if any other error occurs.
     */
    public void deleteContest(long contestId) throws PersistenceException;

    /**
     * <p>
     * This is going to fetch all the currently available contests. This method
     * only return values used in my project widget.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<ContestData> getAllContestHeaders() throws PersistenceException;

    /**
     * Send payments to PACTS for all unpaid submussions with a prize already
     * assigned This service is not atomic. If it fails, you'll have to check
     * what payments where actually done trough the submussion.paid flag.
     *
     * @param contestId Contest Id.
     * @throws PersistenceException if any error occurs when processing a
     *         payment.
     * @since STUDIO-217
     */
    public void processMissingPayments(long contestId) throws PersistenceException;

    /**
     * <p>
     * Get all the DocumentType objects.
     * </p>
     *
     * @return the list of all available DocumentType
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contest
     *
     * @throws PersistenceException if any error occurss
     * @since 1.1.2
     */
    public List<DocumentType> getAllDocumentTypes() throws PersistenceException;

    /**
     * <p>
     * Gets all studio file types to return. If no studio file type exists,
     * return an empty list
     * </p>
     *
     * @return a list of studio file types
     * @throws PersistenceException if any error occurs when getting studio file
     *         types.
     */
    public List<StudioFileType> getAllStudioFileTypes() throws PersistenceException;

    /**
     * <p>
     * This is going to fetch only contestid and contest name for contest.
     * </p>
     *
     * @return the list of all available contents (only id and name) (or empty
     *         if none found)
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contest
     *
     * @since 1.1
     */
    public List<SimpleContestData> getContestDataOnly() throws PersistenceException;

    /**
     * <p>
     * This is going to fetch only contestid and contest name related to given
     * project.
     * </p>
     *
     * @param pid the given project id
     * @return the list of all available contents (only id and name) (or empty
     *         if none found)
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contest
     *
     * @since 1.1
     */
    public List<SimpleContestData> getContestDataOnly(long pid) throws PersistenceException;

    public long createForum(String name, long userId);

    /**
     * Loads and returns the list of payment types (currently it is 'Paypal' and
     * 'TC Purchase order')
     *
     * @since BUGR-1076
     * @return list of payment types
     * @throws PersistenceException if any error occurs
     */
    public List<PaymentType> getAllPaymentTypes() throws PersistenceException;

    /**
     * <p>
     * Purchase submission.
     * </p>
     * <p>
     * set the price of submission. create an entry at submission payment table
     * </p>
     *
     * @param submissionId the id of submission to purchase.
     * @param payment the submission payment data
     * @param username the username.
     *
     * @throws PersistenceException if any error occurs when purchasing
     *         submission.
     * @throws IllegalArgumentWSException if the submissionId is less than 0 or
     *         price is negative.
     */
    public void purchaseSubmission(long submissionId, SubmissionPaymentData payment, String username)
        throws PersistenceException;

    /**
     * <p>
     * This method ranks and purchases submission. If placement > 0, then it
     * ranks the submission. If paymentData != null, then it purchases the
     * submission
     * </p>
     *
     * @param submissionId a <code>long</code> id of submission
     * @param placement a <code>int</code> placement
     * @param paymentData a <code>SubmissionPaymentData</code>
     * @param userId a <code>String</code> user identifier.
     * @throws PersistenceException when error reported by manager
     */
    public void rankAndPurchaseSubmission(long submissionId, int placement,
            SubmissionPaymentData paymentData, String userId) throws PersistenceException;

    /**
     * <p>
     * Updates the submission feedback
     * </p>
     *
     * @param feedback a <code>SubmissionFeedback</code> the feedback
     * @throws PersistenceException when error reported by manager
     */
    public void updateSubmissionFeedback(SubmissionFeedback feedback) throws PersistenceException;

    /**
     * <p>
     * Updates the submission user rank
     * </p>
     *
     * @param submissionId a <code>long</code> id of the submission
     * @param rank a <code>int</code> rank of the submission.
     * @throws PersistenceException when error reported by manager
     * @since TCCC-1219
     */
    public void updateSubmissionUserRank(long submissionId, int rank) throws PersistenceException;

    /**
     * <p>
     * Gets the list of project, contest and their read/write/full permissions.
     * </p>
     *
     * Comment added for Cockpit Project Admin Release Assembly v1.0
     *
     * @param createdUser the specified user for which to get the permission
     * @return the list of project, contest and their read/write/full
     *         permissions.
     */
    public List<SimpleProjectPermissionData> getSimpleProjectPermissionDataForUser(long createdUser)
        throws PersistenceException;

    /**
     * <p>
     * Retrieves the list of users whose handle contains the specified key.
     * </p>
     *
     * Comment added for Cockpit Project Admin Release Assembly v1.0
     *
     * @param specified key to search for.
     * @return the list of users.
     */
    public List<User> searchUser(String key) throws PersistenceException;

    /**
     * Retrieves the list of data for contests for which the user with the given
     * name is a resource. Returns an empty list if no contests are found.
     *
     * @throws IllegalArgumentException if username is null or empty
     * @throws PersistenceException when any other error occurs
     * @param username the name of the user
     * @return the list of found contests data (empty list of none found).
     * @since 1.3
     */
    public List<ContestData> getUserContests(String username) throws PersistenceException;

    /**
     * Gets the milestone submissions for the given contest. Returns an empty
     * list if none found.
     *
     * @throws PersistenceException if any error occurs during the retrieval
     * @param contestId the id of the contest for which milestone submission
     *        data should be retrieved
     * @return the list of retrieved submission data, an empty list if none
     *         found.
     * @since 1.3
     */
    public List<SubmissionData> getMilestoneSubmissionsForContest(long contestId) throws PersistenceException;

    /**
     * Gets the final submissions for the given contest. Returns an empty list
     * if none found.
     *
     * @throws PersistenceException if any error occurs during the retrieval
     * @param contestId the id of the contest for which submission data should
     *        be retrieved.
     * @return the list of retrieved submission data, an empty list if none
     *         found.
     * @since 1.3
     */
    public List<SubmissionData> getFinalSubmissionsForContest(long contestId) throws PersistenceException;

    /**
     * Sets the specified milestone prize for submission with the given ID.
     *
     * @throws PersistenceException if any error occurs.
     * @param submissionId the ID of the submission.
     * @param milestonePrizeId the ID of the milestone prize.
     * @since 1.3
     */
    public void setSubmissionMilestonePrize(long submissionId, long milestonePrizeId)
        throws PersistenceException;
}
