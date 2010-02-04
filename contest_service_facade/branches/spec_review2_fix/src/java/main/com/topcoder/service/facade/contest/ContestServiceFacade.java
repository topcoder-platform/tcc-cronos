/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import java.util.List;

import javax.activation.DataHandler;
import javax.jws.WebService;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Technology;

import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.management.project.Project;
import com.topcoder.management.review.data.Comment;
import com.topcoder.project.service.ScorecardReviewData;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.service.specreview.SpecReview;

import com.topcoder.management.project.DesignComponents;
import com.topcoder.service.facade.contest.ContestPaymentResult;
import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.payment.PaymentException;
import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.permission.PermissionType;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.specreview.SpecReview;
import com.topcoder.service.studio.ChangeHistoryData;
import com.topcoder.service.studio.CompletedContestData;
import com.topcoder.service.studio.ContestNotFoundException;
import com.topcoder.service.studio.ContestPaymentData;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.ContestTypeData;
import com.topcoder.service.studio.DocumentNotFoundException;
import com.topcoder.service.studio.IllegalArgumentWSException;
import com.topcoder.service.studio.MediumData;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.ProjectNotFoundException;
import com.topcoder.service.studio.StatusNotAllowedException;
import com.topcoder.service.studio.StatusNotFoundException;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.SubmissionFeedback;
import com.topcoder.service.studio.SubmissionPaymentData;
import com.topcoder.service.studio.UploadedDocument;
import com.topcoder.service.studio.UserNotAuthorizedException;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.SimpleContestData;
import com.topcoder.service.studio.contest.SimpleProjectContestData;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.User;

import java.util.List;

import javax.activation.DataHandler;

import javax.jws.WebService;


/**
 * <p>
 * An interface for the web service implementing unified <code>Facade</code>
 * interface to various service components provided by global
 * <code>TopCoder</code> application.
 * </p>
 *
 * <p>
 * As of this version a facade to <code>Studio Service</code> is provided only.
 * </p>
 *
 * <p>
 * Module Cockpit Contest Service Enhancement Assembly change: Several new
 * methods related to the permission and permission type are added.
 * </p>
 *
 * <p>
 * <p>
 * Version 1.0.2 (Spec Reviews Finishing Touches v1.0) Change Notes:
 *  - Made the getSpecReviews method return instance of SpecReview rather than a list.
 *  - Added the methods to mark ready for review, review done and resubmit for review.
 * </p>
 * Module Cockpit Share Submission Integration Assembly change: Added method to
 * retrieve all permissions by projectId.
 * </p>
 *
 * * -----------------------changed in the version 1.1-----------------
 *
 * Four methods are added
 * setSubmissionMilestonePrize(submissionId:long,milestonePrizeId:long):void
 * getUserContests(userName:String):List<StudioCompetition>
 * getMilestoneSubmissionsForContest(contestId:long):List<SubmissionData>
 * getFinalSubmissionsForContest(contestId:long):List<SubmissionData>
 *
 * -----
 * <p>
 * Changes in v1.1.1 - Cockpit Release Assembly 11
 * Add method getDesignComponents to get design components.
 * </p>
 * <p>
 * Changes in v1.2 (Prototype Conversion Studio Multi-Rounds Assembly - Submission Viewer UI): Added a flag to
 * updateSubmissionUserRank method to support ranking milestone submissions.
 * </p>
 * <p>
 * Changes in v1.2: Added elegibility services.
 * </p>
 *
 * <p>
 * Changes in v1.2.1 Added support for eligibility services.
 * </p>
 * <p>
 * Changes in v1.3 (Cockpit Spec Review Backend Service Update v1.0):
 * - Added method to create specification review project for an existing project.
 * - Added method to get scorecard and review information for a specific project.
 * - Added method to upload a mock submission / final fixes to the associated specification review of a project
 *   to make it ready for review.
 * - Added method to add comments to an existing review.
 * </p>
 * <p>
 * Changes in v1.5 (Cockpit Release Assembly - Contest Repost and New Version v1.0):
 * - Added method to re open failed software contest.
 * - Added method to create new version for development or design contest.
 * </p>
 *
 * @author pulky, murphydog, waits
 * @version 1.5
 */
@WebService(name = "ContestServiceFacade")
public interface ContestServiceFacade {
    /**
     * <p>
     * Creates new contest for specified project. Upon creation an unique ID is
     * generated and assigned to returned contest.
     * </p>
     *
     * @param contest
     *            a <code>StudioCompetition</code> providing the data for new
     *            contest to be created.
     * @param tcDirectProjectId
     *            a <code>long</code> providing the ID of a project the new
     *            competition belongs to.
     * @return a <code>StudioCompetition</code> providing the data for created
     *         contest and having the ID auto-generated.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if specified <code>competition</code> is <code>null</code> or
     *             if the specified <code>tcDirectProjectId</code> is negative.
     * @tested
     */
    public StudioCompetition createContest(StudioCompetition contest, long tcDirectProjectId) 
        throws PersistenceException;

    /**
     * <p>
     * Gets the contest referenced by the specified ID.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get
     *            details for.
     * @return a <code>StudioCompetition</code> providing the data for the
     *         requested contest.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws ContestNotFoundException
     *             if requested contest is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if specified <code>contestId</code> is negative.
     * @tested
     */
    public StudioCompetition getContest(long contestId)
        throws PersistenceException, ContestNotFoundException;

    /**
     * <p>
     * Gets the contests for the given project.
     * </p>
     *
     * @param tcDirectProjectId
     *            a <code>long</code> providing the ID of a project to get the
     *            list of associated contests for.
     * @return a <code>List</code> providing the contests for specified project.
     *         Empty list is returned if there are no such contests found.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws ProjectNotFoundException
     *             if requested project is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if specified <code>tcDirectProjectId</code> is negative.
     * @tested
     */
    public List<StudioCompetition> getContestsForProject(long tcDirectProjectId)
        throws PersistenceException, ProjectNotFoundException;

    /**
     * <p>
     * Updates the specified contest.
     * </p>
     *
     * @param contest
     *            a <code>StudioCompetition</code> providing the contest data to
     *            update.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws ContestNotFoundException
     *             if requested contest is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if specified <code>contest</code> is <code>null</code>.
     * @tested
     */
    public void updateContest(StudioCompetition contest)
        throws PersistenceException, ContestNotFoundException;

    /**
     * <p>
     * Sets the status of contest referenced by the specified ID to specified
     * value.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to update
     *            status for.
     * @param newStatusId
     *            a <code>long</code> providing the ID of a new contest status
     *            to set.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws StatusNotAllowedException
     *             if the specified status is not allowed to be set for
     *             specified contest.
     * @throws StatusNotFoundException
     *             if specified status is not found.
     * @throws ContestNotFoundException
     *             if specified contest is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if any of specified IDs is negative.
     * @tested
     */
    public void updateContestStatus(long contestId, long newStatusId)
        throws PersistenceException, StatusNotAllowedException,
            StatusNotFoundException, ContestNotFoundException;

    /**
     * <p>
     * Uploads the specified document and associates it with assigned contest.
     * </p>
     *
     * @param uploadedDocument
     *            an <code>UploadedDocument</code> providing the data for the
     *            uploaded document.
     * @return an <code>UploadedDocument</code> passed as argument and having
     *         the document ID auto-generated and set.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws ContestNotFoundException
     *             if requested contest is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if the argument is <code>null</code>.
     * @tested
     */
    public UploadedDocument uploadDocumentForContest(
        UploadedDocument uploadedDocument)
        throws PersistenceException, ContestNotFoundException;

    /**
     * <p>
     * Uploads the specified document without associating it with any contest.
     * </p>
     *
     * @param uploadedDocument
     *            an <code>UploadedDocument</code> providing the data for the
     *            uploaded document.
     * @return an <code>UploadedDocument</code> passed as argument and having
     *         the document ID auto-generated and set.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if the argument is <code>null</code>.
     * @tested
     */
    public UploadedDocument uploadDocument(UploadedDocument uploadedDocument)
        throws PersistenceException;

    /**
     * <p>
     * Associates the specified document with specified contest.
     * </p>
     *
     * @param documentId
     *            a <code>long</code> providing the ID of a document to be
     *            associated with specified contest.
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to associate
     *            the specified document with.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws ContestNotFoundException
     *             if any of requested contest or document is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if any of specified IDs is negative.
     * @tested
     */
    public void addDocumentToContest(long documentId, long contestId)
        throws PersistenceException, ContestNotFoundException;

    /**
     * <p>
     * Removes the specified document from specified contest.
     * </p>
     *
     * @param document
     *            an <code>UploadedDocument</code> representing the document to
     *            be removed.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws DocumentNotFoundException
     *             if the specified document is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if the specified argument is <code>null</code>.
     * @tested
     */
    public void removeDocumentFromContest(UploadedDocument document)
        throws PersistenceException, DocumentNotFoundException;

    /**
     * <p>
     * Retrieves the list of submissions for the specified contest.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get the
     *            list of submissions for.
     * @return a <code>List</code> providing the details for the submissions
     *         associated with the specified contest. Empty list is returned if
     *         there are no submissions found.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws ContestNotFoundException
     *             if requested contest is not found.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if the specified ID is negative.
     * @tested
     */
    public List<SubmissionData> retrieveSubmissionsForContest(long contestId)
        throws PersistenceException, ContestNotFoundException;


    /**
     * <p>
     * Updates specified submission.
     * </p>
     *
     * @param submission
     *            a <code>SubmissionData</code> providing the data for
     *            submission to be updated.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if the specified argument is <code>null</code>.
     * @tested
     */
    public void updateSubmission(SubmissionData submission)
        throws PersistenceException;

    /**
     * <p>
     * Removes specified submission.
     * </p>
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission to
     *            remove.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @throws IllegalArgumentWSException
     *             if the <code>submissionId</code> is negative.
     * @tested
     */
    public void removeSubmission(long submissionId) throws PersistenceException;

    /**
     * <p>
     * Gets existing contest statuses.
     * </p>
     *
     * @return a <code>List</code> listing available contest statuses. Empty
     *         list is returned if there are no statuses.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @tested
     */
    public List<ContestStatusData> getStatusList() throws PersistenceException;

    /**
     * <p>
     * Gets the list of existing submission types.
     * </p>
     *
     * @return a <code>String</code> providing the comma-separated list of
     *         submission types.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws UserNotAuthorizedException
     *             if the caller is not authorized to call this operation.
     * @tested
     */
    public String getSubmissionFileTypes() throws PersistenceException;

    /**
     * <p>
     * Gets the list of all existing contests.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     * @tested
     */
    public List<StudioCompetition> getAllContests() throws PersistenceException;

    /**
     * <p>
     * Gets the list of all existing contests for contest monitor widget.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<SimpleContestData> getSimpleContestData()
        throws PersistenceException;

    /**
     * <p>
     * Gets the list of all existing contests related to given project for
     * contest monitor widget .
     * </p>
     *
     * @param pid
     *            the given project id
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<SimpleContestData> getSimpleContestDataByPID(long pid)
        throws PersistenceException;

    /**
     * <p>
     * Gets the list of all existing contests for my project widget.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData()
        throws PersistenceException;

    /**
     * <p>
     * Gets the list of all existing contests for my project widget.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<CommonProjectContestData> getCommonProjectContestData()
        throws PersistenceException;

    /**
     * <p>
     * Gets the list of all existing contests related to given project for my
     * project widget.
     * </p>
     *
     * @param pid
     *            given project id
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<CommonProjectContestData> getCommonProjectContestDataByPID(
        long pid) throws PersistenceException;

    /**
     * <p>
     * Gets the list of existing contests matching the specified criteria.
     * </p>
     *
     * @param filter
     *            a <code>Filter</code> providing the criteria for searching for
     *            contests.
     * @return a <code>List</code> listing all existing contests matching the
     *         specified criteria. Empty list is returned if there are no
     *         matching contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     * @throws IllegalArgumentException
     *             if specified <code>filter</code> is <code>null</code> or if
     *             it is not supported by implementor.
     */
    public List<StudioCompetition> searchContests(ContestServiceFilter filter)
        throws PersistenceException;

    

    /**
     * <p>
     * Gets the submission referenced by the specified ID.
     * </p>
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission to get
     *            details for.
     * @return a <code>SubmissionData</code> providing details for the
     *         submission referenced by the specified ID or <code>null</code> if
     *         such a submission is not found.
     * @throws PersistenceException
     *             if any error occurs during the retrieval.
     * @tested
     */
    public SubmissionData retrieveSubmission(long submissionId)
        throws PersistenceException;

    /**
     * <p>
     * Gets existing contest types.
     * </p>
     *
     * @return a <code>List</code> listing available contest types. Empty list
     *         is returned if there are no types.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @tested
     */
    public List<ContestTypeData> getAllContestTypes()
        throws PersistenceException;

    /**
     * <p>
     * Removes the document referenced by the specified ID.
     * </p>
     *
     * @param documentId
     *            a <code>long</code> providing the ID of a document to remove.
     * @return <code>true</code> if requested document was removed successfully;
     *         <code>false</code> otherwise.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws IllegalArgumentWSException
     *             if specified <code>documentId</code> is negative.
     * @tested
     */
    public boolean removeDocument(long documentId) throws PersistenceException;

    /**
     * <p>
     * Gest the MIME type matching the specified context type.
     * </p>
     *
     * @param contentType
     *            a <code>String</code> providing the content type to get the
     *            matching MIME type for.
     * @return a <code>long</code> providing the ID of MIME type matching the
     *         specified content type.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @throws IllegalArgumentWSException
     *             if the specified <code>contentType</code> is
     *             <code>null</code> or empty.
     * @tested
     */
    public long getMimeTypeId(String contentType) throws PersistenceException;

    /**
     * <p>
     * Purchases the specified submission. E.g. records a fact that submission
     * referenced by specified ID has been paid for in the course of payment
     * transaction referenced by the specified <code>PayPal</code> order ID.
     * </p>
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission which has
     *            been paid for.
     * @param payPalOrderId
     *            a <code>String</code> providing the <code>PayPal</code> order
     *            ID referencing the payment transaction.
     * @param securityToken
     *            a <code>String</code> providing the security token to be used
     *            for tracking the payment and prevent fraud.
     * @throws PersistenceException
     *             if any error occurs when purchasing submission.
     * @throws IllegalArgumentWSException
     *             if specified <code>submissionId</code> is negative.
     * @tested
     */

    // public void purchaseSubmission(long submissionId, String payPalOrderId,
    // String securityToken)
    // throws PersistenceException;
    /**
     * <p>
     * Purchases the specified submission. E.g. records a fact that submission
     * referenced by specified ID has been paid
     * </p>
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission which has
     *            been paid for.
     * @param submissionPaymentData
     *            a <code>SubmissionPaymentData</code> providing the data of
     *            successfully purchased submission.
     * @param securityToken
     *            a <code>String</code> providing the security token to be used
     *            for tracking the payment and prevent fraud.
     * @throws PersistenceException
     *             if any error occurs when purchasing submission.
     * @throws IllegalArgumentWSException
     *             if specified <code>submissionId</code> is negative.
     */
    public void purchaseSubmission(long submissionId,
        SubmissionPaymentData submissionPaymentData, String securityToken)
        throws PersistenceException;


    /**
     * <p>
     * Gets existing medium types.
     * </p>
     *
     * @return a <code>List</code> listing available medium types. Empty list is
     *         returned if there are no types.
     * @throws PersistenceException
     *             if some persistence errors occur.
     * @tested
     */
    public List<MediumData> getAllMediums() throws PersistenceException;

    /**
     * <p>
     * Sets the placement for the specified submission.
     * </p>
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission to set
     *            the placement for.
     * @param placement
     *            an <code>int</code> providing the submission placement.
     * @throws PersistenceException
     *             if any error occurs when setting placement.
     * @tested
     */
    public void setSubmissionPlacement(long submissionId, int placement)
        throws PersistenceException;

    /**
     * <p>
     * Associates the specified submission with the specified prize.
     * </p>
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission.
     * @param prizeId
     *            a <code>long</code> providing the ID of a prize.
     * @throws PersistenceException
     *             if any error occurs when setting submission prize.
     * @tested
     */
    public void setSubmissionPrize(long submissionId, long prizeId)
        throws PersistenceException;

    /**
     * <p>
     * Marks the specified submission for purchse.
     * </p>
     *
     * @param submissionId
     *            a <code>long</code> providing the ID of a submission to be
     *            marked for purchase.
     * @throws PersistenceException
     *             if any error occurs when marking for purchase.
     * @tested
     */
    public void markForPurchase(long submissionId) throws PersistenceException;

    /**
     * <p>
     * Adss the specified list of history data for the associated contest.
     * </p>
     *
     * @param history
     *            a <code>List</code> of history data for a contest.
     * @throws PersistenceException
     *             if any other error occurs.
     * @tested
     */
    public void addChangeHistory(List<ChangeHistoryData> history)
        throws PersistenceException;

    /**
     * <p>
     * Gets the history data for the specified contest.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get
     *            history data for.
     * @return a <code>List</code> of history data for specified contest.
     * @throws PersistenceException
     *             if any other error occurs.
     * @tested
     */
    public List<ChangeHistoryData> getChangeHistory(long contestId)
        throws PersistenceException;

    /**
     * <p>
     * Gets the most history data for the most recent changes to specified
     * contest.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get
     *            history data for.
     * @return a <code>List</code> of history data for the most recent change
     *         for specified contest.
     * @throws PersistenceException
     *             if any other error occurs.
     * @tested
     */
    public List<ChangeHistoryData> getLatestChanges(long contestId)
        throws PersistenceException;

    /**
     * <p>
     * Deletes the contest referenced by the specified ID.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to delete.
     * @throws PersistenceException
     *             if any other error occurs.
     * @tested
     */
    public void deleteContest(long contestId) throws PersistenceException;

    /**
     * <p>
     * Gets the list of all existing contests.
     * </p>
     *
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     * @tested
     */
    public List<StudioCompetition> getAllContestHeaders()
        throws PersistenceException;

    /**
     * <p>
     * Sends payments to <code>PACTS</code> application for all unpaid
     * submussions with a prize already assigned. This service is not atomic. If
     * it fails, you'll have to check what payments where actually done trough
     * the <code>submussion.paid</code> flag.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to process
     *            missing payments for.
     * @throws PersistenceException
     *             if any error occurs when processing a payment.
     */
    public void processMissingPayments(long contestId)
        throws PersistenceException;

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
    public List<StudioFileType> getAllStudioFileTypes()
        throws PersistenceException;

    /**
     * <p>
     * This is going to fetch all the currently available contests for my
     * project widget.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<SimpleContestData> getContestDataOnly()
        throws PersistenceException;

    /**
     * <p>
     * This is going to fetch all the currently available contests for my
     * project widget related to given project.
     * </p>
     *
     * @param pid
     *            given project id
     * @return the list of all available contents (or empty if none found)
     *
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    public List<SimpleContestData> getContestDataOnlyByPID(long pid)
        throws PersistenceException;

    /**
     * <p>
     * This method retrieve all the permissions that the user owned for any
     * projects. Returns empty list if no permission found.
     * </p>
     *
     * @param userid
     *            user id to look for
     *
     * @return all the permissions that the user owned for any projects.
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when getting permissions.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public List<Permission> getPermissionsByUser(long userid)
        throws PermissionServiceException;

    /**
     * <p>
     * This method retrieve all the permissions that various users own for a
     * given project. Returns empty list if no permission found.
     * </p>
     *
     * @param projectid
     *            project id to look for
     *
     * @return all the permissions that various users own for a given project.
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when getting permissions.
     *
     * @since Cockpit Share Submission Integration
     */
    public List<Permission> getPermissionsByProject(long projectid)
        throws PermissionServiceException;

    /**
     * <p>
     * This method retrieve all the permissions that the user own for a given
     * project. Returns empty list if no permission found.
     * </p>
     *
     * @param userid
     *            user id to look for
     * @param projectid
     *            project id to look for
     *
     * @return all the permissions that the user own for a given project.
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when getting permissions.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public List<Permission> getPermissions(long userid, long projectid)
        throws PermissionServiceException;

    /**
     * <p>
     * This method retrieve all the permission types.
     * </p>
     *
     * @return all the permission types.
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when getting permission types.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public List<PermissionType> getAllPermissionType()
        throws PermissionServiceException;

    /**
     * <p>
     * This method will add a permission type, and return the added type entity.
     * </p>
     *
     * @param type
     *            the permission type to add.
     *
     * @return the added permission type entity
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when adding the permission type.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public PermissionType addPermissionType(PermissionType type)
        throws PermissionServiceException;

    /**
     * <p>
     * This method will update permission type data.
     * </p>
     *
     * @param type
     *            the permission type to update.
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when updating the permission type.
     *
     * @since Module Cockpit Contest Service Enhancement Assembly
     */
    public void updatePermissionType(PermissionType type)
        throws PermissionServiceException;



    /**
     * <p>
     * Processes the contest payment. It does following steps:
     * <ul>
     * <li>Checks contest id to decide whether to create new contest or update
     * existing contest</li>
     * <li>If payment type is credit card then it processes the payment through
     * <code>PaymentProcessor</code></li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>
     * On successful processing -
     * <ul>
     * <li>set contests to CONTEST_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set detailed contests to CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC = 2</li>
     * <li>set payment reference number and type</li>
     * <li>Creates new forum for the contest, forum name being contest name. It
     * uses studio service for doing the same.</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     *
     * @param competition
     *            <code>ContestData</code> data that recognizes a contest.
     * @param paymentData
     *            <code>PaymentData</code> payment information (credit card/po
     *            details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     * @throws ContestNotFoundException
     *             if contest is not found while update.
     * @throws PaymentException
     *             if any errors occurs in processing the payment.
     * @throws IllegalArgumentException
     *             if specified <code>filter</code> is <code>null</code> or if
     *             it is not supported by implementor.
     * @since BUGR-1494 returns ContestPaymentResult instead of PaymentResult
     */
    public ContestPaymentResult processContestCreditCardPayment(
        StudioCompetition competition, CreditCardPaymentData paymentData)
        throws PersistenceException, PaymentException, ContestNotFoundException;

    /**
     * @since BUGR-1494 returns ContestPaymentResult instead of PaymentResult
     *
     * @param competition
     * @param paymentData
     * @return ContestPaymentResult
     * @throws PersistenceException
     * @throws PaymentException
     * @throws ContestNotFoundException
     */
    public ContestPaymentResult processContestPurchaseOrderPayment(
        StudioCompetition competition, TCPurhcaseOrderPaymentData paymentDat)
        throws PersistenceException, PaymentException, ContestNotFoundException;

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     *
     * @param competition
     *            data that recognizes a contest.
     * @param paymentData
     *            payment information (credit card/po details) that need to be
     *            processed.
     *
     * @return a <code>PaymentResult</code> result of the payment processing.
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     * @since BUGR-1682 changed return value
     */
    public SoftwareContestPaymentResult processContestCreditCardSale(
        SoftwareCompetition competition, CreditCardPaymentData paymentData)
        throws ContestServiceException;

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     *
     * @param competition
     *            data that recognizes a contest.
     * @param paymentData
     *            payment information (credit card/po details) that need to be
     *            processed.
     *
     * @return a <code>PaymentResult</code> result of the payment processing.
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     * @since BUGR-1682 changed return value
     */
    public SoftwareContestPaymentResult processContestPurchaseOrderSale(
        SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData)
        throws ContestServiceException;

    /**
     * <p>
     * Processes the submissions payment. It does following steps:
     * <ul>
     * <li>Checks submissionIds to see if is available, if not then it throws
     * PaymentException.</li>
     * <li>It processes the payment through <code>PaymentProcessor</code></li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>On successful processing -
     * <ul>
     * <li>it calls <code>this.purchaseSubmission(...)</code></li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     *
     * @param completedContestData
     *            data of completed contest.
     * @param paymentData
     *            a <code>PaymentData</code> payment information (credit card/po
     *            details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PaymentException
     *             if any errors occurs in processing the payment or submission
     *             is not valid.
     * @throws PersistenceException
     *             if any error occurs when retrieving the submission.
     */
    public PaymentResult processSubmissionCreditCardPayment(
        CompletedContestData completedContestData,
        CreditCardPaymentData paymentData)
        throws PaymentException, PersistenceException;

    /**
     * <p>
     * Processes the submission payment. It does following steps:
     * <ul>
     * <li>Checks submissionIds to see if is available, if not then it throws
     * PaymentException.</li>
     * <li>Right-now this method doesn't process PO payments.</li>
     * <li>On successful processing -
     * <ul>
     * <li>it calls <code>this.purchaseSubmission(...)</code></li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     *
     * @param completedContestData
     *            data of completed contest.
     * @param paymentData
     *            a <code>PaymentData</code> payment information (credit card/po
     *            details) that need to be processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws PaymentException
     *             if any errors occurs in processing the payment or submission
     *             is not valid.
     * @throws PersistenceException
     *             if any error occurs when retrieving the submission.
     */
    public PaymentResult processSubmissionPurchaseOrderPayment(
        CompletedContestData completedContestData,
        TCPurhcaseOrderPaymentData paymentData)
        throws PaymentException, PersistenceException;

    /**
     * <p>
     * Ranks the submissions, given submission identifiers in the rank order.
     * </p>
     *
     * @param submissionIdsInRankOrder
     *            an array of long submission identifier in the rank order.
     * @return a <code>boolean</code> true if successful, else false.
     * @throws PersistenceException
     *             if any error occurs when retrieving/updating the data.
     */
    public boolean rankSubmissions(long[] submissionIdsInRankOrder)
        throws PersistenceException;

    /**
     * <p>
     * Updates the submission feedback.
     * </p>
     *
     * @param feedbacks
     *            an array of <code>SubmissionFeedback</code>
     * @return a <code>boolean</code> true if successful, else false.
     * @throws PersistenceException
     *             if any error occurs when retrieving/updating the data.
     */
    public boolean updateSubmissionsFeedback(SubmissionFeedback[] feedbacks)
        throws PersistenceException;

    /**
     * <p>
     * Returns a list containing all active <code>Categories</code>.
     * </p>
     *
     * @return a list containing all active <code>Categories</code>. It can be
     *         empty if no objects found.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Category> getActiveCategories() throws ContestServiceException;

    /**
     * <p>
     * Returns a list containing all active <code>Technologies</code>.
     * </p>
     *
     * @return a list containing all active <code>Categories</code>. It can be
     *         empty if no objects found.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Technology> getActiveTechnologies()
        throws ContestServiceException;

    /**
     * <p>
     * Returns a list containing all <code>Phases</code>.
     * </p>
     *
     * @return a list containing all active <code>Categories</code>. It can be
     *         empty if no objects found.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Phase> getPhases() throws ContestServiceException;

    /**
     * <p>
     * Creates a new <code>SoftwareCompetition</code> in the persistence.
     * </p>
     *
     * @param contest
     *            the <code>SoftwareCompetition</code> to create as a contest
     * @param tcDirectProjectId
     *            the TC direct project id.
     *
     * @return the created <code>SoftwareCompetition</code> as a contest
     *
     * @throws IllegalArgumentException
     *             if the input argument is invalid.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition createSoftwareContest(SoftwareCompetition contest, long tcDirectProjectId)
        throws ContestServiceException;

    /**
     * <p>
     * BURG-1716: We need to add a method to get software contest by project id,
     * the method wil get all OR project related data, then from project
     * property to get comp version id then to call getAssetByVersionId to get
     * assetDTO, please check create software contest to see what data need to
     * be returned.
     * </p>
     *
     * @param projectId
     *            the OR Project Id
     * @return SoftwareCompetition
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since BURG-1716
     */
    public SoftwareCompetition getSoftwareContestByProjectId(long projectId)
        throws ContestServiceException;

    /**
     * <p>
     * Updates a <code>SoftwareCompetition</code> in the persistence.
     * </p>
     *
     * @param contest
     *            the <code>SoftwareCompetition</code> to update as a contest
     * @param tcDirectProjectId
     *            the TC direct project id.
     *
     * @throws IllegalArgumentException
     *             if the input argument is invalid.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition updateSoftwareContest(
        SoftwareCompetition contest, long tcDirectProjectId)
        throws ContestServiceException;

    /**
     * <p>
     * Assigns a specified user to a specified <code>assetDTO</code>.
     * </p>
     *
     * <p>
     * If the user already assigned to the asset, this method simply does
     * nothing.
     * </p>
     *
     * @param userId
     *            the id of the user
     * @param assetId
     *            the id of the assetDTO
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void assignUserToAsset(long userId, long assetId)
        throws ContestServiceException;

    /**
     * <p>
     * Removes a specified user from a specified <code>assetDTO</code>.
     * </p>
     *
     * @param userId
     *            the id of the user
     * @param assetId
     *            the id of the asset
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void removeUserFromAsset(long userId, long assetId)
        throws ContestServiceException;

    /**
     * <p>
     * This method finds all tc direct projects. Returns empty array if no
     * projects found.
     * </p>
     *
     * @return Project array with project info, or empty array if none found
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition[] findAllTcDirectProjects()
        throws ContestServiceException;

    /**
     * <p>
     * This method finds all given user tc direct projects . Returns empty array
     * if no projects found.
     * </p>
     *
     * @param operator
     *            The user to search for projects
     *
     * @return Project array with project info, or empty array if none found
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition[] findAllTcDirectProjectsForUser(String operator)
        throws ContestServiceException;

    /**
     * <p>
     * This method retrieves the project along with all known associated
     * information. Returns null if not found.
     * </p>
     *
     * @param projectId
     *            The ID of the project to retrieve
     *
     * @return the project along with all known associated information
     *
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition getFullProjectData(long projectId)
        throws ContestServiceException;

    /**
     * <p>
     * Adds a new submission for an user in a particular project.
     * </p>
     *
     * <p>
     * If the project allows multiple submissions for users, it will add the new
     * submission and return. If multiple submission are not allowed for the
     * project, firstly it will add the new submission, secondly mark previous
     * submissions as deleted and then return.
     * </p>
     *
     * @param projectId
     *            the project's id
     * @param filename
     *            the file name to use
     * @param submission
     *            the submission file data
     *
     * @return the id of the new submission
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0, if any argument is <code>null</code> or
     *             trim to empty
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadSubmission(long projectId, String filename,
        DataHandler submission) throws ContestServiceException;

    /**
     * <p>
     * Adds a new final fix upload for an user in a particular project. This
     * submission always overwrite the previous ones.
     * </p>
     *
     * @param projectId
     *            the project's id
     * @param filename
     *            the file name to use
     * @param finalFix
     *            the final fix file data
     *
     * @return the id of the created final fix submission
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0, if any argument is <code>null</code> or
     *             trim to empty
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadFinalFix(long projectId, String filename,
        DataHandler finalFix) throws ContestServiceException;

    /**
     * <p>
     * Adds a new test case upload for an user in a particular project. This
     * submission always overwrite the previous ones.
     * </p>
     *
     * @param projectId
     *            the project's id
     * @param filename
     *            the file name to use
     * @param testCases
     *            the test cases data
     *
     * @return the id of the created test cases submission
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0, if any argument is <code>null</code> or
     *             trim to empty
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadTestCases(long projectId, String filename,
        DataHandler testCases) throws ContestServiceException;

    /**
     * <p>
     * Sets the status of a existing submission.
     * </p>
     *
     * @param submissionId
     *            the submission's id
     * @param submissionStatusId
     *            the submission status id
     * @param operator
     *            the operator which execute the operation
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0 or if operator is null or trim to empty
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void setSubmissionStatus(long submissionId, long submissionStatusId,
        String operator) throws ContestServiceException;

    /**
     * Adds the given user as a new submitter to the given project id.
     *
     * @param projectId
     *            the project to which the user needs to be added
     * @param userId
     *            the user to be added
     *
     * @return the added resource id
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long addSubmitter(long projectId, long userId)
        throws ContestServiceException;

    /**
     * <p>
     * Ranks the submissions, given submission identifiers and the rank. If the isRankingMilestone flag is true,
     * the rank will target milestone submissions.
     * </p>
     *
     * @param submissionId
     *            identifier of the submission.
     * @param rank
     *            rank of the submission.
     * @param isRankingMilestone
     *            true if the user is ranking milestone submissions.
     *
     * @return a <code>boolean</code> true if successful, else false.
     * @throws PersistenceException
     *             if any error occurs when retrieving/updating the data.
     * @since TCCC-1219
     */
    public boolean updateSubmissionUserRank(long submissionId, int rank, Boolean isRankingMilestone)
        throws PersistenceException;

    /**
     * <p>
     * Gets the list of project and their permissions (including permissions for
     * the parent tc project)
     * </p>
     *
     * <p>
     * Updated for Cockpit Project Admin Release Assembly v1.0 - software
     * projects also included.
     * </p>
     *
     * @param createdUser
     *            user for which to get the permissions
     * @return list of project and their permissions.
     *
     * @since TCCC-1329
     */
    public List<CommonProjectPermissionData> getCommonProjectPermissionDataForUser(
        long createdUser) throws PersistenceException;

    /**
     * <p>
     * Searches the user with the given key.
     * </p>
     *
     * @return list of matching users, empty list if none matches.
     * @since TCCC-1329
     */
    public List<User> searchUser(String key) throws PersistenceException;
    
    /**
     * <p>
     * This method updates array of permissions to the persistence.
     * </p>
     *
     * @param permissions
     *            the permissions to update.
     *
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws PermissionServiceException
     *             if any error occurs when updating the permission.
     *
     * @since Cockpit Project Admin Release Assembly.
     */
    public void updatePermissions(Permission[] permissions)
        throws PermissionServiceException;

	 
    /**
     * Gets all contest fees by billing project id.
     * 
     * @param projectId the billing project id
     * @return the list of project contest fees for the given project id
     * @throws ContestServiceException  if any persistence or other error occurs
     * @since 1.0.1
     */
    public List<ProjectContestFee> getContestFeesByProject(long projectId) throws ContestServiceException;


      
    /**
     * Gets the spec review for specified contest id.
     * 
     * Updated for Spec Reviews Finishing Touches v1.0
     * 
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * 
     * @return the spec review that matches the specified contest id.
     * 
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public SpecReview getSpecReviews(long contestId, boolean studio) throws ContestServiceException;

    /**
     * Save specified review comment and review status for specified section and specified contest id to persistence.
     * 
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * @param sectionName
     *            the section name
     * @param comment
     *            the comment
     * @param isPass
     *            the is pass
     * @param role
     *            the user role type           
     * 
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void saveReviewStatus(long contestId, boolean studio, String sectionName, String comment, boolean isPass, String role)
            throws ContestServiceException;

    /**
     * Save specified review comment for specified section and specified contest id to persistence.
     * 
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * @param sectionName
     *            the section name
     * @param comment
     *            the comment
     * @param role
     *            the user role type           
     * 
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void saveReviewComment(long contestId, boolean studio, String sectionName, String comment, String role)
            throws ContestServiceException;

    /**
     * Mark review comment with specified comment id as seen.
     * 
     * @param commentId
     *            the comment id
     * 
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void markReviewCommentSeen(long commentId) throws ContestServiceException;
    
    /**
     * Marks 'review done' by reviewer of the specs for specified contest.
     * Persistence is updated and all end users having write/full permission on the contest are notified by email.
     * 
     * @param contestId
     *            the specified contest id.
     * @param contestName
     *            the contest name.            
     * @param studio
     *            whether contest is studio or not.
     * @param tcDirectProjectId
     *            the tc direct project id.            
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void markReviewDone(long contestId, String contestName, boolean studio, long tcDirectProjectId) throws ContestServiceException;

    /**
     * Marks 'ready for review' by the writer of the specs for specified contest.
     * Persistence is updated, on update the spec would appear as review opportunity on tc site.
     * 
     * @param contestId
     *            the specified contest id.
     * @param studio
     *            whether contest is studio or not.
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void markReadyForReview(long contestId, boolean studio) throws ContestServiceException;

    /**
     * Marks 'resubmit for review' by the writer of the specs for specified contest.
     * Persistence is updated. Reviewer (if any) is notified about the updates.
     * 
     * @param contestId
     *            the specified contest id.
     * @param contestName
     *            the contest name.            
     * @param studio
     *            whether contest is studio or not.
     * @param reviewerUserId
     *            reviewer user id.
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void resubmitForReview(long contestId, String contestName, boolean studio, long reviewerUserId) throws ContestServiceException;

    /**
     * Get the user contest by user name Return empty list if none found
     *
     * @param userName
     *            the user name to get the user contest
     * @return a list of matching studio competitions
     * @throws IllegalArgumentException
     *             if userName is null or empty
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.1
     */
    public List<StudioCompetition> getUserContests(String userName)
        throws ContestServiceException;

    /**
     * get milestone submissions for contest
     *
     * @return empty list of none submission found for the given contest id.
     * @param contestId
     *            The contest id to get the milestone submissions.
     * @throws IllegalArgumentException
     *             if long argument is negative
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.1
     */
    public List<SubmissionData> getMilestoneSubmissionsForContest(
        long contestId) throws ContestServiceException;

    /**
     * get final submissions for contest
     *
     * @return empty list of none submission found for the given contest id.
     * @param contestId
     *            The contest id to get the final submissions
     * @throws IllegalArgumentException
     *             if long argument is negative
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.1
     */
    public List<SubmissionData> getFinalSubmissionsForContest(long contestId)
        throws ContestServiceException;

    /**
     * set submission milestone prize If given submission has already been
     * associated with the given milestone prize before, ContestServiceException
     * will be thrown. It's required that the contest field of the submission of
     * given id is one of the contests set of the milestone prize, otherwise,
     * ContestServiceException will be thrown. If the MilestonePrize with given
     * id has reached the max number of submissions, ContestServiceException
     * will be thrown.
     *
     * @param submissionId
     *            The submission id
     * @param milestonePrizeId
     *            The milestone prize id
     * @throws IllegalArgumentException
     *             if long argument is negative
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.1
     */
    public void setSubmissionMilestonePrize(long submissionId,
        long milestonePrizeId) throws ContestServiceException;

    /**
     * Get all design components.
     *
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.1.1
     */
    public List<DesignComponents> getDesignComponents() throws ContestServiceException;
    
    
    /**
     * Returns whether a user is eligible for a particular contest.
     *
     * @param userId
     *            The user id
     * @param contestId
     *            The contest id
     * @param isStudio
     *            true if the contest is a studio contest, false otherwise.
     * @return true if the user is eligible for the specified contest, false otherwise.
     * 
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.2
     */
    public boolean isEligible(long userId, long contestId, boolean isStudio) throws ContestServiceException;

    /**
     * Find eligibility name for the client.
     * 
     * @param billingProjectId;
     * 			The ID of the billingProject.
     * @return
     * 			The name of the eligibility group.
     * @since 1.2.1
     */
    public String getEligibilityName(long billingProjectId);

    /**
     * This method creates a Specification Review project associated to a project determined by parameter.
     *
     * @param projectId the project id to create a Specification Review for
     * @return the created project
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services
     *
     * @since 1.4
     */
    public FullProjectData createSpecReview(long projectId) throws ContestServiceException;

    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter.
     * Note: a single reviewer / review is assumed.
     *
     * @param projectId the project id to search for
     * @return the aggregated scorecard and review data
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services
     *
     * @since 1.4
     */
    public ScorecardReviewData getScorecardAndReview(long projectId) throws ContestServiceException;

    /**
     * This method uploads a mock file to the corresponding specification review project of the specified project
     * id, so that it can continue with review. Regular submission or final fix will be uploaded according to the
     * open phase.
     *
     * @param projectId the project id of the original project
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services, if the associated
     * specification review project id cannot be found or if neither submission or final fixes phase are open.
     *
     * @since 1.4
     */
    public void markSoftwareContestReadyForReview(long projectId) throws ContestServiceException;

    /**
     * This method adds a review comment to a review. It simply delegates all logic to underlying services.
     *
     * @param reviewId the review id to add the comment to
     * @param comment the review comment to add
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services.
     * @throws IllegalArgumentException if comment is null
     *
     * @since 1.4
     */
    public void addReviewComment(long reviewId, Comment comment) throws ContestServiceException;
    /**
     * <p>
     * Re-Open the software project in status of (project_status_id = 4-6, 8-10 in tcs_catalog:project_status_lu).
     * </p>
     *
     * @param projectId the project id to open
     * @param tcDirectProjectId the tc-direct-project-id
     * @return returns the newly created contest id
     * @throws ContestServiceException if any problem occurs
     */
    public long reOpenSoftwareContest(long projectId, long tcDirectProjectId) throws ContestServiceException;
    /**
     * <p>
     * Create new version for design or development contest. (project_status_id = 4-10 in tcs_catalog:project_status_lu).
     * </p>
     * @param projectId the project to create new version
     * @param tcDirectProjectId tc direct project id
     * @param autoDevCreating if it is true and it is design contest, then will create development too
     * @param minorVersion true for minor, false for major
     * @return newly version contest id
     * @throws ContestServiceException if any error occurs
     */
    public long createNewVersionForDesignDevContest(long projectId, long tcDirectProjectId, boolean autoDevCreating,
                                                    boolean minorVersion) throws ContestServiceException;
}
