/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.IllegalArgumentWSException;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.UserNotAuthorizedException;
import com.topcoder.service.studio.ContestNotFoundException;
import com.topcoder.service.studio.ProjectNotFoundException;
import com.topcoder.service.studio.StatusNotAllowedException;
import com.topcoder.service.studio.StatusNotFoundException;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.DocumentNotFoundException;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.ContestTypeData;
import com.topcoder.service.studio.ContestPaymentData;
import com.topcoder.service.studio.MediumData;
import com.topcoder.service.studio.ChangeHistoryData;
import com.topcoder.service.studio.contest.SimpleContestData;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.SimpleProjectContestData;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.payment.PaymentData;
import com.topcoder.service.payment.PaymentException;
import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.project.StudioCompetition;

import javax.jws.WebService;
import java.util.List;

/**
 * <p>An interface for the web service implementing unified <code>Facade</code> interface to various service components
 * provided by global <code>TopCoder</code> application.</p>
 *
 * <p>As of this version a facade to <code>Studio Service</code> is provided only.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@WebService(name = "ContestServiceFacade")
public interface ContestServiceFacade {

    /**
     * <p>Creates new contest for specified project. Upon creation an unique ID is generated and assigned to returned
     * contest.</p>
     *
     * @param contest a <code>StudioCompetition</code> providing the data for new contest to be created.
     * @param tcDirectProjectId a <code>long</code> providing the ID of a project the new competition belongs to.
     * @return a <code>StudioCompetition</code> providing the data for created contest and having the ID auto-generated.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>competition</code> is <code>null</code> or if the specified
     *         <code>tcDirectProjectId</code> is negative.
     * @tested
     */
    public StudioCompetition createContest(StudioCompetition contest, long tcDirectProjectId)
        throws PersistenceException;

    /**
     * <p>Gets the contest referenced by the specified ID.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get details for.
     * @return a <code>StudioCompetition</code> providing the data for the requested contest.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>contestId</code> is negative.
     * @tested
     */
    public StudioCompetition getContest(long contestId) throws PersistenceException, ContestNotFoundException;

    /**
     * <p>Gets the contests for the given project.</p>
     *
     * @param tcDirectProjectId a <code>long</code> providing the ID of a project to get the list of associated contests
     *        for.
     * @return a <code>List</code> providing the contests for specified project. Empty list is returned if there are no
     *         such contests found.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ProjectNotFoundException if requested project is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>tcDirectProjectId</code> is negative.
     * @tested
     */
    public List<StudioCompetition> getContestsForProject(long tcDirectProjectId) throws PersistenceException,
                                                                                        ProjectNotFoundException;

    /**
     * <p>Updates the specified contest.</p>
     *
     * @param contest a <code>StudioCompetition</code> providing the contest data to update.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if specified <code>contest</code> is <code>null</code>.
     * @tested
     */
    public void updateContest(StudioCompetition contest) throws PersistenceException, ContestNotFoundException;

    /**
     * <p>Sets the status of contest referenced by the specified ID to specified value.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to update status for.
     * @param newStatusId a <code>long</code> providing the ID of a new contest status to set.
     * @throws PersistenceException if some persistence errors occur.
     * @throws StatusNotAllowedException if the specified status is not allowed to be set for specified contest.
     * @throws StatusNotFoundException if specified status is not found.
     * @throws ContestNotFoundException if specified contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if any of specified IDs is negative.
     * @tested
     */
    public void updateContestStatus(long contestId, long newStatusId)
        throws PersistenceException, StatusNotAllowedException, StatusNotFoundException, ContestNotFoundException;

    /**
     * <p>Uploads the specified document and associates it with assigned contest.</p>
     *
     * @param uploadedDocument an <code>UploadedDocument</code> providing the data for the uploaded document.
     * @return an <code>UploadedDocument</code> passed as argument and having the document ID auto-generated and set.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the argument is <code>null</code>.
     * @tested
     */
    public UploadedDocument uploadDocumentForContest(UploadedDocument uploadedDocument) throws PersistenceException,
                                                                                               ContestNotFoundException;

    /**
     * <p>Uploads the specified document without associating it with any contest.</p>
     *
     * @param uploadedDocument an <code>UploadedDocument</code> providing the data for the uploaded document.
     * @return an <code>UploadedDocument</code> passed as argument and having the document ID auto-generated and set.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the argument is <code>null</code>.
     * @tested
     */
    public UploadedDocument uploadDocument(UploadedDocument uploadedDocument) throws PersistenceException;

    /**
     * <p>Associates the specified document with specified contest.</p>
     *
     * @param documentId a <code>long</code> providing the ID of a document to be associated with specified contest.
     * @param contestId a <code>long</code> providing the ID of a contest to associate the specified document with.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if any of requested contest or document is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if any of specified IDs is negative.
     * @tested
     */
    public void addDocumentToContest(long documentId, long contestId) throws PersistenceException,
                                                                             ContestNotFoundException;

    /**
     * <p>Removes the specified document from specified contest.</p>
     *
     * @param document an <code>UploadedDocument</code> representing the document to be removed.
     * @throws PersistenceException if some persistence errors occur.
     * @throws DocumentNotFoundException if the specified document is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified argument is <code>null</code>.
     * @tested
     */
    public void removeDocumentFromContest(UploadedDocument document) throws PersistenceException,
                                                                            DocumentNotFoundException;

    /**
     * <p>Retrieves the list of submissions for the specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get the list of submissions for.
     * @return a <code>List</code> providing the details for the submissions associated with the specified contest.
     *         Empty list is returned if there are no submissions found.
     * @throws PersistenceException if some persistence errors occur.
     * @throws ContestNotFoundException if requested contest is not found.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified ID is negative.
     * @tested
     */
    public List<SubmissionData> retrieveSubmissionsForContest(long contestId) throws PersistenceException,
                                                                                     ContestNotFoundException;

    /**
     * <p>Retrieves the list of submissions for the specified user.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user to get the list of submissions for.
     * @return a <code>List</code> providing the details for the submissions associated with the specified user. Empty
     *         list is returned if there are no submissions found.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified ID is negative.
     * @tested
     */
    public List<SubmissionData> retrieveAllSubmissionsByMember(long userId) throws PersistenceException,
                                                                                   UserNotAuthorizedException;

    /**
     * <p>Updates specified submission.</p>
     *
     * @param submission a <code>SubmissionData</code> providing the data for submission to be updated.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the specified argument is <code>null</code>.
     * @tested
     */
    public void updateSubmission(SubmissionData submission) throws PersistenceException;

    /**
     * <p>Removes specified submission.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission to remove.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException if the <code>submissionId</code>  is negative.
     * @tested
     */
    public void removeSubmission(long submissionId) throws PersistenceException;

    /**
     * <p>Gets existing contest statuses.</p>
     *
     * @return a <code>List</code> listing available contest statuses. Empty list is returned if there are no statuses.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @tested
     */
    public List<ContestStatusData> getStatusList() throws PersistenceException;

    /**
     * <p>Gets the list of existing submission types.</p>
     *
     * @return a <code>String</code> providing the comma-separated list of submission types.
     * @throws PersistenceException if some persistence errors occur.
     * @throws UserNotAuthorizedException if the caller is not authorized to call this operation.
     * @tested
     */
    public String getSubmissionFileTypes() throws PersistenceException;

    /**
     * <p>Gets the list of all existing contests.</p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @tested
     */
    public List<StudioCompetition> getAllContests() throws PersistenceException;
    
    /**
     * <p>Gets the list of all existing contests for contest monitor widget.</p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests
     *         found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleContestData> getSimpleContestData() throws PersistenceException;
    
    /**
     * <p>Gets the list of all existing contests related to given project for contest monitor widget .</p>
     *
     * @param pid the given project id
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests
     *         found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleContestData> getSimpleContestDataByPID(long pid) throws PersistenceException;
    /**
     * <p>Gets the list of all existing contests for my project widget.</p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests
     *         found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData() throws PersistenceException;

    /**
     * <p>Gets the list of all existing contests related to given project for my project widget.</p>
     *
     * @param the given project id
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests
     *         found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestDataByPID(long pid) throws PersistenceException;

    /**
     * <p>Gets the list of existing contests matching the specified criteria.</p>
     *
     * @param filter a <code>Filter</code> providing the criteria for searching for contests.
     * @return a <code>List</code> listing all existing contests matching the specified criteria. Empty list is returned
     *         if there are no matching contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws IllegalArgumentException if specified <code>filter</code> is <code>null</code> or if it is not supported
     *         by implementor.
     */
    public List<StudioCompetition> searchContests(ContestServiceFilter filter) throws PersistenceException;

    /**
     * <p>Gets the list of existing contests associated with the client referenced by the specified ID.</p>
     *
     * @param clientId a <code>long</code> providing the ID of a client to get the associated contests for.
     * @return a <code>List</code> providing the contests associated with the requested client. Empty list is returned
     *         if there are no matching contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @tested
     */
    public List<StudioCompetition> getContestsForClient(long clientId) throws PersistenceException;

    /**
     * <p>Gets the submission referenced by the specified ID.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission to get details for.
     * @return a <code>SubmissionData</code> providing details for the submission referenced by the specified ID or
     *         <code>null</code> if such a submission is not found.
     * @throws PersistenceException if any error occurs during the retrieval.
     * @tested
     */
    public SubmissionData retrieveSubmission(long submissionId) throws PersistenceException;

    /**
     * <p>Gets existing contest types.</p>
     *
     * @return a <code>List</code> listing available contest types. Empty list is returned if there are no types.
     * @throws PersistenceException if some persistence errors occur.
     * @tested
     */
    public List<ContestTypeData> getAllContestTypes() throws PersistenceException;

    /**
     * <p>Removes the document referenced by the specified ID.</p>
     *
     * @param documentId a <code>long</code> providing the ID of a document to remove.
     * @return <code>true</code> if requested document was removed successfully; <code>false</code> otherwise.
     * @throws PersistenceException if some persistence errors occur.
     * @throws IllegalArgumentWSException if specified <code>documentId</code> is negative.
     * @tested
     */
    public boolean removeDocument(long documentId) throws PersistenceException;

    /**
     * <p>Gest the MIME type matching the specified context type.</p>
     *
     * @param contentType a <code>String</code> providing the content type to get the matching MIME type for.
     * @return a <code>long</code> providing the ID of MIME type matching the specified content type.
     * @throws PersistenceException if some persistence errors occur.
     * @throws IllegalArgumentWSException if the specified <code>contentType</code> is <code>null</code> or empty.
     * @tested
     */
    public long getMimeTypeId(String contentType) throws PersistenceException;

    /**
     * <p>Purchases the specified submission. E.g. records a fact that submission referenced by specified ID has been
     * paid for in the course of payment transaction referenced by the specified <code>PayPal</code> order ID.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission which has been paid for.
     * @param payPalOrderId a <code>String</code> providing the <code>PayPal</code> order ID referencing the payment
     *        transaction.
     * @param securityToken a <code>String</code> providing the security token to be used for tracking the payment and
     *        prevent fraud.
     * @throws PersistenceException if any error occurs when purchasing submission.
     * @throws IllegalArgumentWSException if specified <code>submissionId</code> is negative.
     * @tested
     */
    public void purchaseSubmission(long submissionId, String payPalOrderId, String securityToken)
        throws PersistenceException;

    /**
     * <p>Creates a new contest payment. Upon creation an unique ID is generated and assigned to returned payment.</p>
     *
     * @param contestPayment a <code>ContestPaymentData</code> providing the data for the contest payment to be created.
     * @param securityToken a <code>String</code> providing the security token to be used for tracking the payment and
     *        prevent fraud.
     * @return a <code>ContestPaymentData</code> providing the details for created contest payment and having the ID
     *         for contest payment auto-generated and set.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     * @tested
     */
    public ContestPaymentData createContestPayment(ContestPaymentData contestPayment, String securityToken)
        throws PersistenceException;

    /**
     * <p>Gets the contest payment referenced by specified contest ID.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get payment details for.
     * @return a <code>ContestPaymentData</code> representing the contest payment matching the specified ID; or
     *         <code>null</code> if there is no such contest.
     * @throws PersistenceException if any error occurs when getting contest.
     * @tested
     */
    public ContestPaymentData getContestPayment(long contestId) throws PersistenceException;

    /**
     * <p>Updates specified contest payment data.</p>
     *
     * @param contestPayment a <code>ContestPaymentData</code> providing the details for the contest payment to be
     *        updated.
     * @throws PersistenceException if any error occurs when updating contest.
     * @throws IllegalArgumentException if the specified argument is <code>null</code>.
     * @tested
     */
    public void editContestPayment(ContestPaymentData contestPayment) throws PersistenceException;

    /**
     * <p>Removes the contest payment referenced by the specified contest ID.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to remove payment details for.
     * @return <code>true</code> if requested contest payment was removed successfully; <code>false</code> otherwise.
     * @throws PersistenceException if any error occurs when removing contest.
     * @tested
     */
    public boolean removeContestPayment(long contestId) throws PersistenceException;

    /**
     * <p>Gets existing medium types.</p>
     *
     * @return a <code>List</code> listing available medium types. Empty list is returned if there are no types.
     * @throws PersistenceException if some persistence errors occur.
     * @tested
     */
    public List<MediumData> getAllMediums() throws PersistenceException;

    /**
     * <p>Sets the placement for the specified submission.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission to set the placement for.
     * @param placement an <code>int</code> providing the submission placement.
     * @throws PersistenceException if any error occurs when setting placement.
     * @tested
     */
    public void setSubmissionPlacement(long submissionId, int placement)throws PersistenceException;

    /**
     * <p>Associates the specified submission with the specified prize.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission.
     * @param prizeId a <code>long</code>  providing the ID of a prize.
     * @throws PersistenceException if any error occurs when setting submission prize.
     * @tested
     */
    public void setSubmissionPrize(long submissionId, long prizeId) throws PersistenceException;

    /**
     * <p>Marks the specified submission for purchse.</p>
     *
     * @param submissionId a <code>long</code> providing the ID of a submission to be marked for purchase.
     * @throws PersistenceException if any error occurs when marking for purchase.
     * @tested
     */
    public void markForPurchase(long submissionId)throws PersistenceException;

    /**
     * <p>Adss the specified list of history data for the associated contest.</p>
     *
     * @param history a <code>List</code> of history data for a contest.
     * @throws PersistenceException if any other error occurs.
     * @tested
     */
    public void addChangeHistory(List<ChangeHistoryData> history) throws PersistenceException;

    /**
     * <p>Gets the history data for the specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get history data for.
     * @return a <code>List</code> of history data for specified contest.
     * @throws PersistenceException if any other error occurs.
     * @tested
     */
    public List<ChangeHistoryData> getChangeHistory(long contestId) throws PersistenceException;

    /**
     * <p>Gets the most history data for the most recent changes to specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to get history data for.
     * @return a <code>List</code> of history data for the most recent change for specified contest.
     * @throws PersistenceException if any other error occurs.
     * @tested
     */
    public List<ChangeHistoryData> getLatestChanges(long contestId) throws PersistenceException;

    /**
     * <p>Deletes the contest referenced by the specified ID.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to delete.
     * @throws PersistenceException if any other error occurs.
     * @tested
     */
    public void deleteContest(long contestId) throws PersistenceException;

    /**
     * <p>Gets the list of all existing contests.</p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     * @tested
     */
    public List<StudioCompetition> getAllContestHeaders() throws PersistenceException;

    /**
     * <p>Sends payments to <code>PACTS</code> application for all unpaid submussions with a prize already assigned.
     * This service is not atomic. If it fails, you'll have to check what payments where actually done trough the
     * <code>submussion.paid</code> flag.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest to process missing payments for.
     * @throws PersistenceException if any error occurs when processing a payment.
     */
    public void processMissingPayments(long contestId) throws PersistenceException;

	/**
     * <p>
     * Get all the DocumentType objects.
     * </p>
     * 
     * @return the list of all available DocumentType
     * 
     * @throws ContestManagementException
     *             if any error occurs when getting contest
     * 
	 * @throws PersistenceException
     *             if any error occurss
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
     * @throws PersistenceException
     *             if any error occurs when getting studio file types.
     */
    public List<StudioFileType> getAllStudioFileTypes() throws PersistenceException;


	 /**
     * <p>
     * This is going to fetch all the currently available contests for my project widget.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<SimpleContestData> getContestDataOnly() throws PersistenceException;
    
    /**
     * <p>
     * This is going to fetch all the currently available contests for my project widget related to given project.
     * </p>
     * 
     * @param  the given project id
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<SimpleContestData> getContestDataOnlyByPID(long pid) throws PersistenceException;
    
    /**
     * <p>
     * Processes the contest payment. It does following steps:
     * <ul>
     * <li>Checks contest id to decide whether to create new contest or update existing contest</li>
     * <li>If payment type is credit card then it processes the payment through <code>PaymentProcessor</code></li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>
     *  On successful processing - 
     * <ul>
     * <li>set contests to CONTEST_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set detailed contests to CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set payment reference number and type</li>
     * <li>Creates new forum for the contest, forum name being contest name. It uses studio service for doing the same.</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     *
     * @param <code>ContestData</code> data that recognizes a contest.
     * @param <code>PaymentData</code> payment information (credit card/po details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PersistenceException if any error occurs when getting contest.
     * @throws ContestNotFoundException 
     * @throws IllegalArgumentException if specified <code>filter</code> is <code>null</code> or if it is not supported
     *         by implementor.
     */
    public PaymentResult processContestPayment(ContestData contestData, PaymentData paymentData) 
    throws PersistenceException, PaymentException, ContestNotFoundException;

}
