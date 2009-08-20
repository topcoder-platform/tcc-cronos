/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.submission.EntityExistsException;
import com.topcoder.service.studio.submission.EntityNotFoundException;
import com.topcoder.service.studio.submission.InconsistentContestsException;
import com.topcoder.service.studio.submission.NumberOfSubmissionsExceededException;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.SubmissionManagementException;
import com.topcoder.service.studio.submission.SubmissionManagerLocal;
import com.topcoder.service.studio.submission.SubmissionPayment;
import com.topcoder.service.studio.submission.SubmissionReview;
import com.topcoder.service.studio.submission.SubmissionStatus;
import com.topcoder.service.studio.submission.SubmissionType;

/**
 * <p>
 * Mock implementation of submission manager remote interface.
 * </p>
 * 
 * @author moon.river, myxgyy
 * @version 1.3
 * @since 1.0
 */
public class SubmissionManagerImpl implements SubmissionManagerLocal {

    /**
     * <p>
     * Represents the submissions.
     * </p>
     */
    public static List<Submission> submissions;

    /**
     * <p>
     * Constructor.
     * </p>
     */
    public SubmissionManagerImpl() {
        submissions = new ArrayList<Submission>();
    }

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
    public Submission getSubmission(long submissionId)
            throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * <p>
     * Gets the submissions for the contest with the given id. Also, the
     * selectFullSubmission flag will determine if the full submission is returned to the
     * caller.
     * </p>
     * 
     * @param contestId
     *            The id of the contest of the submissions to get
     * @param selectFullSubmission
     *            a flag whether the full submission should be returned
     * @return List of Submission for the contest with the given id, possibly empty if
     *         none found.
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    public List<Submission> getSubmissionsForContest(long contestId,
            boolean selectFullSubmission) throws SubmissionManagementException {
        return submissions;
    }

    /**
     * <p>
     * Gets the submissions for the submitter with the given id.
     * </p>
     * 
     * @param userId
     *            The id of the submitter of the submissions to get
     * @return list of submissions for the submitter with the given id, never null,
     *         possibly empty if none found.
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    public List<Submission> getAllSubmissionsByMember(long userId)
            throws SubmissionManagementException {
        return submissions;
    }

    /**
     * <p>
     * Updates the submission in persistence. Will not allow changes to rank or prizes. It
     * will interpret an empty prize set as an indication that the prizes should not be
     * changed.
     * </p>
     * 
     * @param submission
     *            The Submission to update
     * @throws IllegalArgumentException
     *             If the submission argument is null
     * @throws EntityNotFoundException
     *             If the submission does not exist in persistence or is already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the update, or if there is a change in the
     *             rank or prize set.
     */
    public void updateSubmission(Submission submission)
            throws SubmissionManagementException {
        submissions.clear();
        submissions.add(submission);
    }

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
    public boolean removeSubmission(long submissionId)
            throws SubmissionManagementException {
         // 'was here' mark
        submissions = null;
        return true; // return value not used anyway
    }

    /**
     * <p>
     * Updates the status of the submission with the given id to the status with the given
     * id.
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
    public void updateSubmissionStatus(long submissionId,
            long submissionStatusId) throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * <p>
     * Updates the submission result in persistence.
     * </p>
     * <p>
     * Notes, only rank the prizes set will be changed for all submissions of the contest.
     * </p>
     * 
     * @param submission
     *            The Submission whose result is to be updated
     * @throws IllegalArgumentException
     *             If the submission argument is null, or its rank is not set.
     * @throws EntityNotFoundException
     *             If the submission does not exist in persistence, or submission already
     *             deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the update
     */
    public void updateSubmissionResult(Submission submission)
            throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

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
    public Prize addPrize(Prize prize) throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

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
    public void updatePrize(Prize prize) throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * <p>
     * Removes the prize with the given id from persistence.
     * </p>
     * 
     * @param prizeId
     *            The id of the prize to delete
     * @return true if found and deleted, false if not found and thus not deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the delete
     */
    public boolean removePrize(long prizeId)
            throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

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
    public Prize getPrize(long prizeId) throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * <p>
     * Adds the prize with the given id to the submission with the given id. Both must
     * currently exist in persistence.
     * </p>
     * 
     * @param submissionId
     *            the id of the submission to add the prize to
     * @param prizeId
     *            the id of the prize to add to the submission
     * @throws IllegalArgumentException
     *             If the rank of submission is not set, or the rank is not same as the
     *             prize's place.
     * @throws EntityNotFoundException
     *             If the prize or submission with the given ids does not exist in
     *             persistence, or submission already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the add
     */
    public void addPrizeToSubmission(long submissionId, long prizeId)
            throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * <p>
     * Removes the prize with the given id from the submission with the given id. Both
     * must currently exist in persistence. No action is taken if the prize is already not
     * in the submission.
     * </p>
     * 
     * @param submissionId
     *            the id of the submission to remove the prize from
     * @param prizeId
     *            the id of the prize to remove from the submission
     * @return true if prize in submission found and deleted, false if not found
     * @throws EntityNotFoundException
     *             If the prize or submission with the given ids does not exist in
     *             persistence, or submission already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the removal
     */
    public boolean removePrizeFromSubmission(long submissionId, long prizeId)
            throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * <p>
     * Adds the submission payment to persistence. The submission must already exist.
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
    public SubmissionPayment addSubmissionPayment(
            SubmissionPayment submissionPayment)
            throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * <p>
     * Updates the submission payment in persistence. The submission and its payment must
     * already exist.
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
    public void updateSubmissionPayment(SubmissionPayment submissionPayment)
            throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

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
    public SubmissionPayment getSubmissionPayment(long submissionId)
            throws SubmissionManagementException {
        SubmissionPayment sp = new SubmissionPayment();
        PaymentStatus ps = new PaymentStatus();
        sp.setStatus(ps);
        ps.setPaymentStatusId(submissionId);
        return sp;
    }

    /**
     * <p>
     * Adds the submission review to persistence. It will assign a new id. The submission
     * must already exist.
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
    public SubmissionReview addSubmissionReview(
            SubmissionReview submissionReview)
            throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * <p>
     * Updates the submission review in persistence. The submission and its review must
     * already exist.
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
    public void updateSubmissionReview(SubmissionReview submissionReview)
            throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * <p>
     * Gets the submission review for the submission with the given id. Returns null if
     * not found.
     * </p>
     * 
     * @param submissionId
     *            The id of the submission of the submission review to get
     * @return The SubmissionReview for the submission and reviewer with the given ids
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    public SubmissionReview getSubmissionReview(long submissionId)
            throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * <p>
     * Removes the submission review for the submission with the given id. Returns true if
     * anything was deleted.
     * </p>
     * 
     * @param submissionId
     *            The id of the submission of the submission review to delete
     * @return true if deletion occurred, false otherwise
     * @throws SubmissionManagementException
     *             If any error occurs during the deletion
     */
    public boolean removeSubmissionReview(long submissionId)
            throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    /**
     * <p>
     * Gets the prizes of the submission with the given id. The submission must currently
     * exist in persistence.
     * </P>
     * 
     * @param submissionId
     *            the id of the submission whose prizes are to be retrieved
     * @return the retrieved prizes for the given
     * @throws EntityNotFoundException
     *             If the submission with the given id does not exist in persistence, or
     *             submission already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    public List<Prize> getSubmissionPrizes(long submissionId)
            throws SubmissionManagementException {
        throw new UnsupportedOperationException(
                "Method is not implemented yet.");
    }

    public List<Submission> getFinalSubmissionsForContest(long arg0)
            throws SubmissionManagementException {
        List<Submission> submissions = new ArrayList<Submission>();
        if (arg0 != 1) {
            return submissions;
        }
        Submission s = new Submission();
        Contest c = new Contest();
        c.setContestId(100l);
        ContestType type = new ContestType();
        type.setDescription("development");
        c.setContestType(type);
        
        s.setSubmissionId(2l);
        s.setContest(c);
        s.setStatus(new SubmissionStatus());
        SubmissionType st = new SubmissionType();
        st.setDescription("final fix");
        s.setType(st);
        submissions.add(s);
        return submissions;
    }

    public List<Submission> getMilestoneSubmissionsForContest(long arg0)
            throws SubmissionManagementException {
        List<Submission> submissions = new ArrayList<Submission>();
        if (arg0 != 1) {
            return submissions;
        }
        Submission s = new Submission();
        Contest c = new Contest();
        c.setContestId(100l);
        ContestType type = new ContestType();
        type.setDescription("development");
        c.setContestType(type);
        
        s.setSubmissionId(2l);
        s.setContest(c);
        s.setStatus(new SubmissionStatus());
        SubmissionType st = new SubmissionType();
        st.setDescription("Milestone");
        s.setType(st);
        submissions.add(s);
        return submissions;
    }

    public PaymentStatus getPaymentStatus(long arg0)
            throws SubmissionManagementException {
        return null;
    }

    public List<SubmissionPayment> getSubmissionPaymentsForContest(long arg0)
            throws SubmissionManagementException {
        return null;
    }

    public List<Submission> getSubmissionsForContest(long arg0, boolean arg1,
            int arg2) throws SubmissionManagementException {
        
        return submissions;
    }

    public List<Submission> getSubmissionsForContest(Contest arg0,
            boolean arg1, int arg2, boolean arg3)
            throws SubmissionManagementException {
        return null;
    }

    private static Map<Long, Long> milestonePrize = new HashMap<Long, Long>();

    public void setSubmissionMilestonePrize(long arg0, long arg1)
            throws SubmissionManagementException, EntityNotFoundException,
            IllegalArgumentException, InconsistentContestsException,
            NumberOfSubmissionsExceededException {
        milestonePrize.put(arg0, arg1);
    }

    public Map<Long, Long> getMilestonePrize() {
        return milestonePrize;
    }
    public void updateSubmissionFeedback(long arg0, String arg1, int arg2)
            throws SubmissionManagementException {
    }

    public void updateSubmissionUserRank(long arg0, int arg1)
            throws SubmissionManagementException {
    }
}
