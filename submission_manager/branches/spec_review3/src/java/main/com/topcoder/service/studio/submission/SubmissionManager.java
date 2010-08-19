/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import com.topcoder.service.studio.contest.Contest;

import java.util.List;


/**
 * <p>
 * The business interface that defines methods for managing a submission, prize,
 * submission payment, and submission review. In general, but not always, it has
 * methods to create, update, delete, get, and list these entities, as well as
 * some methods to perform more important operations. These include getting
 * submissions for a contest or member, updating a submission status or result,
 * adding or removing a prize from a submission, and getting and deleting
 * submission reviews for a submission and/or a reviewer.
 * <p>
 * <p>
 * <b>Thread Safety</b>: Implementations should perform actions in a thread-safe
 * manner.
 * </p>
 *
 * Update in version 1.2: 1. Three methods are added to support get milestone
 * submissions, get final submissions and set submission milestone prize. 2. Doc
 * is updated to get consistent with the current implementation, see the doc for
 * blue methods for details. Especially, several existing methods are totally
 * missing in the original diagram, they are added in this version.(at the
 * bottom of this interface)
 *
 * <p>
 * Changes in v1.3 (Prototype Conversion Studio Multi-Rounds Assembly - Submission Viewer UI):
 * - Added a flag to updateSubmissionUserRank method to support ranking milestone submissions.
 * - Added support to award milestone prizes.
 * </p>
 *
 * @author pulky
 * @version 1.3
 */
public interface SubmissionManager {
    /**
     * <p>
     * Gets the submission with the given id.
     * </p>
     *
     * @param submissionId
     *            The id of the submission to get
     * @return the submission with the given id, or null if not found
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    Submission getSubmission(long submissionId)
        throws SubmissionManagementException;

    /**
     * <p>
     * Gets the submissions for the contest with the given id. Also, the
     * selectFullSubmission flag will determine if the full submission is
     * returned to the caller.
     * </p>
     *
     * @param contestId
     *            The id of the contest of the submissions to get
     * @param selectFullSubmission
     *            a flag whether the full submission should be returned
     * @return List of Submission for the contest with the given id, possibly
     *         empty if none found.
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    List<Submission> getSubmissionsForContest(long contestId,
        boolean selectFullSubmission) throws SubmissionManagementException;

    /**
     * <p>
     * Gets the submissions for the contest with the given id. Also, the
     * selectFullSubmission flag will determine if the full submission is
     * returned to the caller.
     * </p>
     *
     * @param contestId
     *            The id of the contest of the submissions to get
     * @param selectFullSubmission
     *            a flag whether the full submission should be returned
     *
     * @param maxSubmissionsPerUser
     *            max sub per user
     * @return List of Submission for the contest with the given id, possibly
     *         empty if none found.
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    List<Submission> getSubmissionsForContest(long contestId,
        boolean selectFullSubmission, int maxSubmissionsPerUser)
        throws SubmissionManagementException;

    /**
     * <p>
     * Gets the submissions for the submitter with the given id.
     * </p>
     *
     * @param userId
     *            The id of the submitter of the submissions to get
     * @return list of submissions for the submitter with the given id, never
     *         null, possibly empty if none found.
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    List<Submission> getAllSubmissionsByMember(long userId)
        throws SubmissionManagementException;

    /**
     * <p>
     * Updates the submission in persistence. Will not allow changes to rank or
     * prizes. It will interpret an empty prize set as an indication that the
     * prizes should not be changed.
     * </p>
     *
     * @param submission
     *            The Submission to update
     * @throws IllegalArgumentException
     *             If the submission argument is null
     * @throws EntityNotFoundException
     *             If the submission does not exist in persistence or is already
     *             deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the update, or if there is a
     *             change in the rank or prize set.
     */
    void updateSubmission(Submission submission)
        throws SubmissionManagementException;

    /**
     * <p>
     * Removes the submission with the given id from persistence.
     * </p>
     *
     * @param submissionId
     *            The id of the submission to delete
     * @return true if found and deleted, false if not found or already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the delete
     */
    boolean removeSubmission(long submissionId)
        throws SubmissionManagementException;

    /**
     * <p>
     * Updates the status of the submission with the given id to the status with
     * the given id.
     * </p>
     *
     * @param submissionId
     *            the id of the status to update the submission to
     * @param submissionStatusId
     *            The id of the submission to update
     * @throws EntityNotFoundException
     *             If the submission or status does not exist in persistence, or
     *             submission already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the update
     */
    void updateSubmissionStatus(long submissionId, long submissionStatusId)
        throws SubmissionManagementException;

    /**
     * <p>
     * Updates the submission result in persistence.
     * </p>
     * <p>
     * Notes, only rank the prizes set will be changed for all submissions of
     * the contest.
     * </p>
     *
     * @param submission
     *            The Submission whose result is to be updated
     * @throws IllegalArgumentException
     *             If the submission argument is null, or its rank is not set.
     * @throws EntityNotFoundException
     *             If the submission does not exist in persistence, or
     *             submission already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the update
     */
    void updateSubmissionResult(Submission submission)
        throws SubmissionManagementException;

    /**
     * <p>
     * Adds the prize to persistence. It will assign a new id.
     * </p>
     *
     * @param prize
     *            The Prize to add
     * @return the added Prize
     * @throws IllegalArgumentException
     *             If the prize argument is null
     * @throws EntityExistsException
     *             If the prize already exists in persistence
     * @throws SubmissionManagementException
     *             If any error occurs during the add
     */
    Prize addPrize(Prize prize) throws SubmissionManagementException;

    /**
     * <p>
     * Updates the prize in persistence.
     * </p>
     *
     * @param prize
     *            The Prize to update
     * @throws IllegalArgumentException
     *             If the prize argument is null
     * @throws EntityNotFoundException
     *             If the prize does not exist in persistence
     * @throws SubmissionManagementException
     *             If any error occurs during the update
     */
    void updatePrize(Prize prize) throws SubmissionManagementException;

    /**
     * <p>
     * Removes the prize with the given id from persistence.
     * </p>
     *
     * @param prizeId
     *            The id of the prize to delete
     * @return true if found and deleted, false if not found and thus not
     *         deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the delete
     */
    boolean removePrize(long prizeId) throws SubmissionManagementException;

    /**
     * <p>
     * Gets the prize with the given id. Returns null if not found.
     * </p>
     *
     * @param prizeId
     *            The id of the prize to get
     * @return Prize with the given id, or null if not found
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    Prize getPrize(long prizeId) throws SubmissionManagementException;

    /**
     * <p>
     * Adds the prize with the given id to the submission with the given id.
     * Both must currently exist in persistence.
     * </p>
     *
     * @param submissionId
     *            the id of the submission to add the prize to
     * @param prizeId
     *            the id of the prize to add to the submission
     * @throws IllegalArgumentException
     *             If the rank of submission is not set, or the rank is not same
     *             as the prize's place.
     * @throws EntityNotFoundException
     *             If the prize or submission with the given ids does not exist
     *             in persistence, or submission already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the add
     */
    void addPrizeToSubmission(long submissionId, long prizeId)
        throws SubmissionManagementException;

    /**
     * <p>
     * Adds the milestone prize with the given id to the submission with the given id.
     * Both must currently exist in persistence.
     * </p>
     *
     * @param submissionId the id of the submission to add the prize to
     * @param milestonePrizeId the id of the milestone prize to add to the submission
     * @throws IllegalArgumentException
     *             If the submission is not marked for milestone prize
     * @throws EntityNotFoundException
     *             If the milestone prize or submission with the given ids does not exist
     *             in persistence, or submission already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the addition
     *
     * @since 1.3
     */
    void addMilestonePrizeToSubmission(long submissionId, long milestonePrizeId)
        throws SubmissionManagementException;

    /**
     * <p>
     * Removes the prize with the given id from the submission with the given
     * id. Both must currently exist in persistence. No action is taken if the
     * prize is already not in the submission.
     * </p>
     *
     * @param submissionId
     *            the id of the submission to remove the prize from
     * @param prizeId
     *            the id of the prize to remove from the submission
     * @return true if prize in submission found and deleted, false if not found
     * @throws EntityNotFoundException
     *             If the prize or submission with the given ids does not exist
     *             in persistence, or submission already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the removal
     */
    boolean removePrizeFromSubmission(long submissionId, long prizeId)
        throws SubmissionManagementException;

    /**
     * <p>
     * Adds the submission payment to persistence. The submission must already
     * exist.
     * </p>
     *
     * @param submissionPayment
     *            The SubmissionPayment to add
     * @return the added SubmissionPayment
     * @throws IllegalArgumentException
     *             If the submissionPayment argument is null
     * @throws EntityNotFoundException
     *             If the submission that this payment is for does not exist in
     *             persistence, or has been already deleted
     * @throws EntityExistsException
     *             If the submission payment already exists in persistence
     * @throws SubmissionManagementException
     *             If any error occurs during the add
     */
    SubmissionPayment addSubmissionPayment(SubmissionPayment submissionPayment)
        throws SubmissionManagementException;

    /**
     * <p>
     * Updates the submission payment in persistence. The submission and its
     * payment must already exist.
     * </p>
     *
     * @param submissionPayment
     *            The submission payment to update
     * @throws IllegalArgumentException
     *             If the submissionPayment argument is null
     * @throws EntityNotFoundException
     *             If the submission that this payment is for does not exist in
     *             persistence, or has been already deleted
     * @throws EntityNotFoundException
     *             If the submission payment does not exist in persistence
     * @throws SubmissionManagementException
     *             If any error occurs during the update
     */
    void updateSubmissionPayment(SubmissionPayment submissionPayment)
        throws SubmissionManagementException;

    /**
     * <p>
     * Gets the submission payment with the given id. Returns null if not found.
     * </p>
     *
     * @param submissionId
     *            The id of the submission payment to get
     * @return SubmissionPayment with the given id, or null if not found
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    SubmissionPayment getSubmissionPayment(long submissionId)
        throws SubmissionManagementException;

    /**
     * <p>
     * Adds the submission review to persistence. It will assign a new id. The
     * submission must already exist.
     * </p>
     *
     * @param submissionReview
     *            The SubmissionReview to add
     * @return the added SubmissionReview
     * @throws IllegalArgumentException
     *             If the submissionReview argument is null
     * @throws EntityNotFoundException
     *             If the submission that this review is for does not exist in
     *             persistence, or has been already deleted
     * @throws EntityExistsException
     *             If the submission review already exists in persistence
     * @throws SubmissionManagementException
     *             If any error occurs during the add
     */
    SubmissionReview addSubmissionReview(SubmissionReview submissionReview)
        throws SubmissionManagementException;

    /**
     * <p>
     * Updates the submission review in persistence. The submission and its
     * review must already exist.
     * </p>
     *
     * @param submissionReview
     *            The submission review to update
     * @throws IllegalArgumentException
     *             If the submissionReview argument is null
     * @throws EntityNotFoundException
     *             If the submission that this review is for does not exist in
     *             persistence, or has been already deleted
     * @throws EntityNotFoundException
     *             If the submission review does not exist in persistence
     * @throws SubmissionManagementException
     *             If any error occurs during the update
     */
    void updateSubmissionReview(SubmissionReview submissionReview)
        throws SubmissionManagementException;

    /**
     * <p>
     * Gets the submission review for the submission with the given id. Returns
     * null if not found.
     * </p>
     *
     * @param submissionId
     *            The id of the submission of the submission review to get
     * @return The SubmissionReview for the submission and reviewer with the
     *         given ids
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    SubmissionReview getSubmissionReview(long submissionId)
        throws SubmissionManagementException;

    /**
     * <p>
     * Removes the submission review for the submission with the given id.
     * Returns true if anything was deleted.
     * </p>
     *
     * @param submissionId
     *            The id of the submission of the submission review to delete
     * @return true if deletion occurred, false otherwise
     * @throws SubmissionManagementException
     *             If any error occurs during the deletion
     */
    boolean removeSubmissionReview(long submissionId)
        throws SubmissionManagementException;

    /**
     * <p>
     * Gets the prizes of the submission with the given id. The submission must
     * currently exist in persistence.
     * </P>
     *
     * @param submissionId
     *            the id of the submission whose prizes are to be retrieved
     * @return the retrieved prizes for the given
     * @throws EntityNotFoundException
     *             If the submission with the given id does not exist in
     *             persistence, or submission already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    List<Prize> getSubmissionPrizes(long submissionId)
        throws SubmissionManagementException;

    /**
     * <p>
     * Gets the payment status with the given id. Returns null if not found.
     * </p>
     *
     * @param paymentStatusId
     *            The id of the payment status to get
     * @return The PaymentStatus with the given id.
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    PaymentStatus getPaymentStatus(long paymentStatusId)
        throws SubmissionManagementException;

    /**
     * <p>
     * Gets the active submissions for the contest with the given id. Also, the
     * selectFullSubmission will determine if the full submission is returned to
     * the caller.
     * </p>
     *
     * @param contest
     *            a <code>Contest</code> for which the submissions to be get
     * @param selectFullSubmission
     *            a <code>boolean</code> flag whether the full submission should
     *            be returned
     * @param maxSubmissionsPerUser
     *            a <code>int</code> value. if non-zero then, criteria to limit
     *            submissions by their rank else it is not considered.
     * @param includeFailedScreening
     *            a <code>boolean</code> flag. true if failed screening
     *            submission need to be included else false.
     * @return List of Submission for the contest with the given id, or empty
     *         list if none found.
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    public List<Submission> getSubmissionsForContest(Contest contest,
        boolean selectFullSubmission, int maxSubmissionsPerUser,
        boolean includeFailedScreening) throws SubmissionManagementException;

    /**
     * <p>
     * Gets the submission payments for the contest with the given id.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> id of the contest for which the
     *            submissions to be get
     * @return List of SubmissionPayment for the contest with the given id, or
     *         empty list if none found.
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    public List<SubmissionPayment> getSubmissionPaymentsForContest(
        long contestId) throws SubmissionManagementException;

    /**
     * <p>
     * Updates the feedback of the submission with the given id.
     * </p>
     *
     * @param submissionId
     *            The id of the submission to update
     * @param feedbackText
     *            The feedback text
     * @param feedbackThumb
     *            The feedback thumb.
     * @throws EntityNotFoundException
     *             If the submission or status does not exist in persistence, or
     *             submission already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the update
     */
    public void updateSubmissionFeedback(long submissionId,
        String feedbackText, int feedbackThumb)
        throws SubmissionManagementException;

    /**
     * <p>
     * Updates the user rank of the submission with the given id. If the isRankingMilestone flag is true,
     * the rank will target milestone submissions.
     * </p>
     *
     * @param submissionId
     *            The id of the submission to update
     * @param rank
     *            The rank of the submission
     * @param isRankingMilestone
     *            true if the user is ranking milestone submissions.
     *
     * @throws EntityNotFoundException
     *             If the submission or status does not exist in persistence, or
     *             submission already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the update
     * @since TCCC-1219
     */
    public void updateSubmissionUserRank(long submissionId, int rank, Boolean isRankingMilestone)
        throws SubmissionManagementException;

    /**
     * This is a new method defined in version 1.2.
     * <p>
     * Get all the milestone submissions for the contest with the given ID.
     * Return and empty list if not found any matched submission.
     * </P>
     *
     * @param contestId
     *            The ID of the contest of the submissions to get result.
     * @return List of milestone submission for given contest ID. If not found
     *         any matched submission, return an empty list.
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     * @since 1.2
     */
    public List<Submission> getMilestoneSubmissionsForContest(long contestId)
        throws SubmissionManagementException;

    /**
     * This is a new method defined in version 1.2.
     * <p>
     * Gets all the final submissions for the contest with the given ID. Returns
     * an empty list if none found.
     * </p>
     *
     * @param contestId
     *            The ID of the contest of the submissions to get result
     * @return List of Final Submission for the contest with the given ID
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     * @since 1.2
     */
    public List<Submission> getFinalSubmissionsForContest(long contestId)
        throws SubmissionManagementException;

    /**
     * This is a new method defined in version 1.2.
     * <p>
     * Sets the specified milestone prize for the specified submission. Both
     * must currently exist in persistence.
     * </p>
     *
     * @param milestonePrizeId
     *            The ID of the milestone prize to be associate with to the
     *            submission
     * @param submissionId
     *            The ID of the submission needed to set into the milestone
     *            prize
     *
     * @throws EntityNotFoundException
     *             If the milestone prize or submission with the given IDs does
     *             not exist in persistence, or submission already deleted
     *
     * @throws IllegalArgumentException
     *             If the given submission has already been associated with the
     *             given milestone prize before, OR the submission type is not
     *             milestone submission.
     *
     * @throws NumberOfSubmissionsExceededException
     *             I f we've already reached the maximum number of submissions
     *             receiving milestone prizes for that contest.
     *
     * @throws InconsistentContestsException
     *             If the contest the submission belongs to is not one of the
     *             contests set of the milestone prize
     *
     * @throws SubmissionManagementException
     *             If any error occurs during the set
     * @since 1.2
     */
    public void setSubmissionMilestonePrize(long submissionId,
        long milestonePrizeId) throws SubmissionManagementException;
}
