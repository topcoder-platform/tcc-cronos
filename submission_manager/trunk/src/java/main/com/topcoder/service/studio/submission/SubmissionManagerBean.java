/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * The stateless session bean that performs the CRUDE specified by the <code>{@link SubmissionManagerRemote}</code>
 * and <code>{@link SubmissionManagerLocal}</code> interfaces. It uses JPA perform all operations and the Log for
 * logging.
 * </p>
 * <p>
 * Sample <b>ejb-jar.xml</b> file:<br>
 *
 * <pre>
 *  &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 *  &lt;ejb-jar&gt;
 *      &lt;enterprise-beans&gt;
 *          &lt;session&gt;
 *              &lt;ejb-name&gt;SubmissionManagerBean&lt;/ejb-name&gt;
 *              &lt;ejb-class&gt;
 *                  com.topcoder.service.studio.submission.SubmissionManagerBean
 *              &lt;/ejb-class&gt;
 *              &lt;env-entry&gt;
 *                  &lt;env-entry-name&gt;unitName&lt;/env-entry-name&gt;
 *                  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                  &lt;env-entry-value&gt;java:submissionMananger&lt;/env-entry-value&gt;
 *              &lt;/env-entry&gt;
 *              &lt;env-entry&gt;
 *                  &lt;env-entry-name&gt;logger&lt;/env-entry-name&gt;
 *                  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                  &lt;env-entry-value&gt;myLogger&lt;/env-entry-value&gt;
 *              &lt;/env-entry&gt;
 *          &lt;/session&gt;
 *      &lt;/enterprise-beans&gt;
 *  &lt;/ejb-jar&gt;
 * </pre>
 *
 * <b>Sample usage</b>:<br>
 *
 * <pre>
 * // create prize type
 * PrizeType type = new PrizeType();
 * type.setPrizeTypeId(1L);
 * // Create a prize and fill it
 * Prize prize = new Prize();
 * prize.setPlace(1);
 * prize.setAmount(900.00);
 * prize.setCreateDate(new Date());
 * prize.setType(type);
 * // and other field are omitted for clarity
 * Prize createdPrize = manager.addPrize(prize);
 * // Checking the ID of the prize, it would be filled. We assume ID=11
 * Long prizeId = createdPrize.getPrizeId();
 *
 * // At a later point, we can retrieve and update the prize to bump the
 * // prize to half.
 * Prize retrievedPrize = manager.getPrize(prizeId);
 * prize.setPlace(2);
 * prize.setAmount(450.00);
 * manager.updatePrize(retrievedPrize);
 *
 * // We can remove the prize if it no longer meets our needs.
 * manager.removePrize(retrievedPrize.getPrizeId());
 *
 * // This shows basic CRUD operations for a simple entity. We can proceed
 * // to more involved examples of managing a submission:
 *
 * // Suppose we have 4 submissions for a given contest (contestId=1).
 * // The relevant fields are to be denoted as:
 * // submission 1: submissionId=1, rank=1
 * // submission 2: submissionId=2, rank=2
 * // submission 3: submissionId=3, rank=3
 * // submission 4: submissionId=4, rank=4
 *
 * // If we wanted to retrieve and update some properties of a submission,
 * // we would do the following:
 * Submission submission = manager.getSubmission(2);
 * submission.setHeight(2);
 *
 * manager.updateSubmission(submission);
 *
 * // If we wanted to retrieve the submissions, without the actual files, we
 * // would do the following:
 * List&lt;Submission&gt; submissions = manager.getSubmissionsForContest(1, false);
 * // This would return a list of 4 submissions shown above
 *
 * // Suppose that after additional appeals, the rankings were changed, and
 * // submission 3 was promoted to rank 1. The submission result would be
 * // updated:
 * Submission submission3 = manager.getSubmission(3);
 * submission.setRank(1);
 * manager.updateSubmissionResult(submission3);
 * // This would result in the rankings to be recalculated to accommodate
 * // the change in ranks. The submissions for the given contest would be
 * // adjusted as follows:
 * // submission 3: submissionId=3, rank=1
 * // submission 1: submissionId=1, rank=2
 * // submission 2: submissionId=2, rank=3
 * // submission 4: submissionId=4, rank=4
 * </pre>
 *
 * </p>
 * <p>
 * <b>Thread Safety</b>: This bean is mutable and not thread-safe as it deals with non-thread-safe entities. However,
 * in the context of being used in a container, it is thread-safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
@RolesAllowed("Cockpit Administrator")
public class SubmissionManagerBean implements SubmissionManagerLocal, SubmissionManagerRemote {
    /**
     * <p>
     * Represents the EJB QL for finding active submission reviews.
     * </p>
     */
    private static final String QUERY_ACTIVE_SUBMISSION_REVIEWS = "SELECT sr FROM SubmissionReview sr WHERE"
            + " sr.submission.submissionId = :submissionId AND sr.submission.status.description != :description";

    /**
     * <p>
     * Represents the description of deleted status.
     * </p>
     */
    private static final String DELETED_STATUS = "deleted";

    /**
     * <p>
     * Represents the SessionContext injected by the EJB container.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * Represents the persistence unit name to lookup the EntityManager from the SessionContext.
     * </p>
     * <p>
     * It is set during the initilize() method from the JNDI ENC. It will not be null or empty and must refer to a valid
     * EntityManager configuration. It will be used in the getEntityManager method to retrieve it. It will not change
     * after that.
     * </p>
     */
    private String unitName;

    /**
     * <p>
     * Represents the configured Log instance that is used to log all activity.
     * </p>
     * <p>
     * It is set during the initilize() method. It may be null if logging is not configured. Once set, it will not
     * change.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * Creates a <code>SubmissionManagerBean</code> instance.
     * </p>
     */
    public SubmissionManagerBean() {
    }

    /**
     * <p>
     * Configures this bean with values from the JNDI ENC.
     * </p>
     * <p>
     * Following are sample configuration<br/>
     *
     * <pre>
     * &lt;!-- unitName entry is required, should be a valid persistence unit name --&gt;
     * env-entry&gt;
     *     &lt;env-entry-name&gt;unitName&lt;/env-entry-name&gt;
     *     &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
     *     &lt;env-entry-value&gt;submission_manager&lt;/env-entry-value&gt;
     * &lt;/env-entry&gt;
     * &lt;!-- logger entry is optional, which mean no logging needed --&gt;
     * &lt;env-entry&gt;
     *     &lt;env-entry-name&gt;logger&lt;/env-entry-name&gt;
     *     &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
     *     &lt;env-entry-value&gt;myLogger&lt;/env-entry-value&gt;
     * &lt;/env-entry&gt;
     * </pre>
     *
     * </p>
     *
     * @throws SubmissionManagementConfigurationException
     *             If "unitName" parameter in the JNDI ENC is missing, or if "logger " does not refer to a valid Log, or
     *             there is an error during the lookup or log retrieval
     */
    @PostConstruct
    public void initialize() {
        // retrieve the required unitName
        unitName = getConfigString("unitName", true);

        String logName = getConfigString("logger", false);

        if (null != logName) {
            try {
                logger = LogManager.getLogWithExceptions(logName);
            } catch (LogException e) {
                throw new SubmissionManagementConfigurationException("Invalid log name - " + logName, e);
            }
        } else {
            logger = null;
        }

        // check the unitName refer to a valid entity manager
        try {
            getEntityManager("initialize()");
        } catch (SubmissionManagementException e) {
            throw new SubmissionManagementConfigurationException(
                    "no persistence configuration for [" + unitName + "].", e);
        }
    }

    /**
     * <p>
     * Gets the submission with the given id.
     * </p>
     *
     * @param submissionId
     *            The id of the submission to get
     * @return Submission with the given id, or null if not found
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */

    public Submission getSubmission(long submissionId) throws SubmissionManagementException {
        final String methodName = "getSubmission(long)";
        logEnter(methodName);

        Submission submission = getEntity(getEntityManager(methodName), Submission.class, submissionId, methodName);

        // should not return the deleted submission
        // according to the schema definition and foreign key constraint, the getStatus() method never return null.
        if (null != submission && isSubmissionDeleted(submission)) {
            submission = null;
        }

        logExit(methodName);
        return submission;
    }

    /**
     * <p>
     * Gets the active submissions for the contest with the given id. Also, the selectFullSubmission will determine if
     * the full submission is returned to the caller.
     * </p>
     *
     * @param contestId
     *            The id of the contest of the submissions to get
     * @param selectFullSubmission
     *            a flag whether the full submission should be returned
     * @return List of Submission for the contest with the given id, or empty list if none found.
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    public List<Submission> getSubmissionsForContest(long contestId, boolean selectFullSubmission)
        throws SubmissionManagementException {
        final String methodName = "getSubmissionsForContest(long, boolean)";
        logEnter(methodName);

        List<Submission> submissions = getSubmissionsForContest(
                getEntityManager(methodName), contestId, methodName);

        if (!selectFullSubmission) {
            // Fix [TCCC-137]
            List<Submission> ret = new ArrayList<Submission>();
            for (Submission submission : submissions) {
                Submission clonedSubmission = cloneSubmission(submission);
                clonedSubmission.setFullSubmissionPath(null);
                ret.add(clonedSubmission);
            }

            return ret;
        }

        logExit(methodName);
        return submissions;
    }

    /**
     * <p>
     * Gets the active submissions for the submitter with the given id.
     * </p>
     *
     * @param userId
     *            The id of the submitter of the submissions to get
     * @return List of Submission for the submitter with the given id, or empty list if none found.
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    @SuppressWarnings("unchecked")
    public List<Submission> getAllSubmissionsByMember(long userId) throws SubmissionManagementException {
        final String methodName = "getAllSubmissionsByMember(long)";
        logEnter(methodName);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("submitterId", userId);
        parameters.put("description", DELETED_STATUS);

        List<Submission> submissions = getResultList(getEntityManager(methodName), "SELECT s FROM Submission s"
                + " WHERE s.submitterId = :submitterId AND s.status.description != :description", parameters,
                methodName);

        logExit(methodName);
        return submissions;
    }

    /**
     * <p>
     * Updates the submission in persistence. Will not allow changes to rank or prizes.
     * </p>
     * <p>
     * It will interpret an empty prize set as an indication that the prizes should not be changed.
     * </p>
     *
     * @param submission
     *            The Submission to update
     * @throws IllegalArgumentException
     *             If the submission is null
     * @throws EntityNotFoundException
     *             If the submission does not exist in persistence or is already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the update, or if there is a change in the rank or prize set.
     */
    public void updateSubmission(Submission submission) throws SubmissionManagementException {
        final String methodName = "updateSubmission(Submission)";
        logEnter(methodName);

        checkNull(submission, "submission", methodName);

        EntityManager entityManager = getEntityManager(methodName);

        Submission entity = getActiveSubmission(entityManager, submission.getSubmissionId(), methodName);

        if (null == entity.getRank()) {
            if (null != submission.getRank()) {
                throw logException(new SubmissionManagementException("There is a change in the rank."), methodName);
            }
        } else if (!entity.getRank().equals(submission.getRank())) {
            throw logException(new SubmissionManagementException("There is a change in the rank."), methodName);
        }

        // It will interpret an empty prize set as an indication that the prizes should not be changed.
        Set<Prize> prizes = submission.getPrizes();
        if (null != prizes && !prizes.isEmpty() && !prizes.equals(entity.getPrizes())) {
            throw logException(new SubmissionManagementException("There is a change to the prize set."), methodName);
        }

        // copy the data.
        // rank and prizes fields not need to be updated.
        entity.setContest(submission.getContest());
        entity.setCreateDate(submission.getCreateDate());
        entity.setFullSubmissionPath(submission.getFullSubmissionPath());
        entity.setHeight(submission.getHeight());
        entity.setMimeType(submission.getMimeType());
        entity.setModifyDate(submission.getModifyDate());
        entity.setOriginalFileName(submission.getOriginalFileName());
        entity.setOrSubmission(submission.getOrSubmission());
        entity.setResult(submission.getResult());
        entity.setReview(submission.getReview());
        entity.setStatus(submission.getStatus());
        entity.setSubmissionDate(submission.getSubmissionDate());
        entity.setSubmitterId(submission.getSubmitterId());
        entity.setSystemFileName(submission.getSystemFileName());
        entity.setType(submission.getType());
        entity.setWidth(submission.getWidth());

        updateEntity(entityManager, entity, methodName);
        logExit(methodName);
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
    public boolean removeSubmission(long submissionId) throws SubmissionManagementException {
        final String methodName = "removeSubmission(long)";
        logEnter(methodName);

        EntityManager entityManager = getEntityManager(methodName);

        Submission submission = getEntity(entityManager, Submission.class, submissionId, methodName);

        boolean removed = false;

        // submission exists and not deleted.
        if (null != submission && !isSubmissionDeleted(submission)) {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("description", DELETED_STATUS);
            SubmissionStatus status = (SubmissionStatus) getSingleResult(entityManager,
                    "SELECT ss FROM SubmissionStatus ss WHERE ss.description=:description", parameters, methodName);

            submission.setStatus(status);

            updateEntity(entityManager, submission, methodName);

            removed = true;
        }

        logExit(methodName);
        return removed;
    }

    /**
     * <p>
     * Updates the status of the submission with the given id to the status with the given id.
     * </p>
     *
     * @param submissionStatusId
     *            the id of the status to update the submission to
     * @param submissionId
     *            The id of the submission to update
     * @throws EntityNotFoundException
     *             If the submission or status does not exist in persistence, or submission already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the update
     */
    public void updateSubmissionStatus(long submissionId, long submissionStatusId)
        throws SubmissionManagementException {
        final String methodName = "updateSubmissionStatus(long, long)";
        logEnter(methodName);

        EntityManager entityManager = getEntityManager(methodName);

        Submission submission = getActiveSubmission(entityManager, submissionId, methodName);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("submissionStatusId", submissionStatusId);
        SubmissionStatus status = (SubmissionStatus) getSingleResult(entityManager,
                "SELECT ss FROM SubmissionStatus ss WHERE ss.submissionStatusId=:submissionStatusId", parameters,
                methodName);
        submission.setStatus(status);

        updateEntity(entityManager, submission, methodName);

        logExit(methodName);
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
     *             If the submission does not exist in persistence, or submission already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the update
     */
    @SuppressWarnings("unchecked")
    public void updateSubmissionResult(Submission submission) throws SubmissionManagementException {
        final String methodName = "updateSubmissionResult(Submission)";
        logEnter(methodName);
        checkNull(submission, "submission", methodName);

        if (null == submission.getRank()) {
            throw logException(new IllegalArgumentException("The rank of the submission is not set."), methodName);
        }

        EntityManager entityManager = getEntityManager(methodName);

        Long submissionId = submission.getSubmissionId();
        Submission submissionEntity = getActiveSubmission(entityManager, submissionId, methodName);

        // promotion or demotion.
        int step = 1;
        if (submissionEntity.getRank() != null && submissionEntity.getRank() < submission.getRank()) {
            // demotion.
            step = -1;
        }

        // update rank
        submissionEntity.setRank(submission.getRank());

        Long contestId = submissionEntity.getContest().getContestId();
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("contestId", contestId);
        parameters.put("submissionId", submissionId);

        // get all submissions for this submission's contest except itself
        List<Submission> submissions = getResultList(entityManager,
                "SELECT s FROM Submission s WHERE s.contest.contestId=:contestId AND s.submissionId!=:submissionId",
                parameters, methodName);

        // rearrange the ranks.
        Map<Integer, Submission> map = new HashMap<Integer, Submission>();
        for (Submission submis : submissions) {
            // ignore no rank submission
            if (null != submis.getRank()) {
                map.put(submis.getRank(), submis);
            }
        }

        Integer currentRank = submissionEntity.getRank();
        Submission currentSubmission = submissionEntity;

        while (true) {
            if (!map.containsKey(currentRank)) {
                // no collision
                map.put(currentRank, currentSubmission);

                // update the rank
                currentSubmission.setRank(currentRank);
                break;
            } else {
                Submission temp = map.get(currentRank);

                map.put(currentRank, currentSubmission);

                // update the rank
                currentSubmission.setRank(currentRank);

                // increase or decrease rank.
                currentRank += step;
                currentSubmission = temp;
            }
        }

        // get all prizes for this submission's contest.
        parameters = new HashMap<String, Object>();
        parameters.put("contestId", contestId);
        List<Prize> prizes = getResultList(
                entityManager,
                "SELECT p FROM Prize p, Contest c WHERE c.contestId=:contestId AND c in elements(p.contests)",
                parameters, methodName);

        // remove all old relation
        for (Entry<Integer, Submission> entry : map.entrySet()) {
            entry.getValue().getPrizes().removeAll(prizes);
        }

        // update the new submission prize relation
        for (Entry<Integer, Submission> entry : new TreeMap<Integer, Submission>(map).entrySet()) {
            Submission submiss = entry.getValue();

            // one submission can associate with multiple prize
            for (Prize prize : prizes) {
                if (prize.getPlace().equals(submiss.getRank())) {
                    submiss.getPrizes().add(prize);
                }
            }
        }

        // update all the prizes and submissions.
        for (Submission submiss : map.values()) {
            updateEntity(entityManager, submiss, methodName);
        }

        logExit(methodName);
    }

    /**
     * <p>
     * Adds the prize to persistence. A new id will be assigned.
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
        final String methodName = "addPrize(Prize)";
        logEnter(methodName);

        checkNull(prize, "prize", methodName);
        saveEntity(getEntityManager(methodName), prize, methodName);

        logExit(methodName);
        return prize;
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
        final String methodName = "updatePrize(Prize)";
        logEnter(methodName);

        checkNull(prize, "prize", methodName);

        EntityManager entityManager = getEntityManager(methodName);
        Prize entity = getEntity(entityManager, Prize.class, prize.getPrizeId(), methodName);

        if (null == entity) {
            throw logException(new EntityNotFoundException("No matched prize to update."), methodName);
        }

        // copy data
        entity.setAmount(prize.getAmount());
        entity.setCreateDate(prize.getCreateDate());
        entity.setPlace(prize.getPlace());
        entity.setType(prize.getType());
        updateEntity(entityManager, entity, methodName);

        logExit(methodName);
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
    public boolean removePrize(long prizeId) throws SubmissionManagementException {
        final String methodName = "removePrize(long)";
        logEnter(methodName);

        boolean removed = removeEntity(getEntityManager(methodName), Prize.class, prizeId, methodName);

        logExit(methodName);
        return removed;
    }

    /**
     * <p>
     * Gets the prize with the given id.
     * </p>
     *
     * @param prizeId
     *            The id of the prize to get
     * @return Prize with the given id, or null if not found
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    public Prize getPrize(long prizeId) throws SubmissionManagementException {
        final String methodName = "getPrize(long)";
        logEnter(methodName);

        Prize prize = getEntity(getEntityManager(methodName), Prize.class, prizeId, methodName);

        logExit(methodName);

        return prize;
    }

    /**
     * <p>
     * Adds the prize with the given id to the submission with the given id. Both must currently exist in persistence.
     * </p>
     *
     * @param submissionId
     *            the id of the submission to add the prize to
     * @param prizeId
     *            the id of the prize to add to the submission
     * @throws IllegalArgumentException
     *             If the rank of submission is not set, or the rank is not same as the prize's place.
     * @throws EntityNotFoundException
     *             If the prize or submission with the given ids does not exist in persistence, or submission already
     *             deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the add
     */
    public void addPrizeToSubmission(long submissionId, long prizeId) throws SubmissionManagementException {
        final String methodName = "addPrizeToSubmission(long, long)";
        logEnter(methodName);

        EntityManager entityManager = getEntityManager(methodName);

        Prize prize = getEntity(entityManager, Prize.class, prizeId, methodName);

        if (null == prize) {
            throw logException(new EntityNotFoundException("The prize does not exist."), methodName);
        }

        Submission submission = getActiveSubmission(entityManager, submissionId, methodName);

        Integer rank = submission.getRank();
        if (null == rank) {
            throw logException(new IllegalArgumentException("The rank of the submission is not assigned yet."),
                    methodName);
        } else if (!prize.getPlace().equals(rank)) {
            throw logException(new IllegalArgumentException("The prize is not for this rank of submission."),
                    methodName);
        }

        if (!submission.getPrizes().contains(prize)) {
            submission.getPrizes().add(prize);
            updateEntity(entityManager, submission, methodName);

            // not need to update prize as the relation is created, same relation insertion will raise problem.
        }

        logExit(methodName);
    }

    /**
     * <p>
     * Removes the prize with the given id from the submission with the given id. Both must currently exist in
     * persistence.
     * </p>
     *
     * @param submissionId
     *            the id of the submission to remove the prize from
     * @param prizeId
     *            the id of the prize to remove from the submission
     * @return true if prize in submission found and deleted, false if not found
     * @throws EntityNotFoundException
     *             If the prize or submission with the given ids does not exist in persistence, or submission already
     *             deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the removal
     */
    public boolean removePrizeFromSubmission(long submissionId, long prizeId) throws SubmissionManagementException {
        final String methodName = "removePrizeFromSubmission(long, long)";
        logEnter(methodName);

        EntityManager entityManager = getEntityManager(methodName);
        Prize prize = getEntity(entityManager, Prize.class, prizeId, methodName);

        if (null == prize) {
            throw logException(new EntityNotFoundException("The prize does not exist."), methodName);
        }

        Submission submission = getActiveSubmission(entityManager, submissionId, methodName);

        boolean removed = false;
        // No action is taken if the prize is already not in the submission.
        if (submission.getPrizes().remove(prize)) {
            updateEntity(entityManager, submission, methodName);

            prize.getSubmissions().remove(submission);

            updateEntity(entityManager, prize, methodName);

            removed = true;
        }

        logExit(methodName);
        return removed;
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
     *             If the submissionPayment is null
     * @throws EntityNotFoundException
     *             If the submission that this payment is for does not exist in persistence, or has been already deleted
     * @throws EntityExistsException
     *             If the submission payment already exists in persistence
     * @throws SubmissionManagementException
     *             If any error occurs during the add
     */
    public SubmissionPayment addSubmissionPayment(SubmissionPayment submissionPayment)
        throws SubmissionManagementException {
        final String methodName = "addSubmissionPayment(SubmissionPayment)";
        logEnter(methodName);

        checkNull(submissionPayment, "submissionPayment", methodName);

        EntityManager entityManager = getEntityManager(methodName);

        Submission submission = getActiveSubmission(entityManager, submissionPayment.getSubmission().getSubmissionId(),
                methodName);

        submissionPayment.setSubmission(submission);
        saveEntity(entityManager, submissionPayment, methodName);

        logExit(methodName);
        return submissionPayment;
    }

    /**
     * <p>
     * Updates the submission payment in persistence. The submission and its payment must already exist.
     * </p>
     *
     * @param submissionPayment
     *            The submission payment to update
     * @throws IllegalArgumentException
     *             If the submissionPayment argument is null
     * @throws EntityNotFoundException
     *             If the submission that this payment is for does not exist in persistence, or has been already deleted
     * @throws EntityNotFoundException
     *             If the submission payment does not exist in persistence
     * @throws SubmissionManagementException
     *             If any error occurs during the update
     */
    public void updateSubmissionPayment(SubmissionPayment submissionPayment) throws SubmissionManagementException {
        final String methodName = "updateSubmissionPayment(SubmissionPayment)";
        logEnter(methodName);

        checkNull(submissionPayment, "submissionPayment", methodName);
        EntityManager entityManager = getEntityManager(methodName);
        Long submissionId = submissionPayment.getSubmission().getSubmissionId();
        Submission submission = getActiveSubmission(entityManager, submissionId, methodName);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("submissionId", submissionId);
        SubmissionPayment payment = (SubmissionPayment) getSingleResult(entityManager,
                "SELECT sp FROM SubmissionPayment sp WHERE sp.submission.submissionId=:submissionId", parameters,
                methodName);

        // copy data
        payment.setPrice(submissionPayment.getPrice());
        payment.setStatus(submissionPayment.getStatus());
        payment.setSubmission(submission);
        updateEntity(entityManager, submissionPayment, methodName);

        logExit(methodName);
    }

    /**
     * <p>
     * Gets the submission payment with the given id.
     * </p>
     *
     * @param submissionId
     *            The id of the submission payment to get
     * @return SubmissionPayment with the given id, or null if not found or it is deleted.
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    public SubmissionPayment getSubmissionPayment(long submissionId) throws SubmissionManagementException {
        final String methodName = "getSubmissionPayment(long)";
        logEnter(methodName);

        SubmissionPayment submissionPayment = null;
        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("submissionId", submissionId);
            submissionPayment = (SubmissionPayment) getSingleResult(getEntityManager(methodName),
                    "SELECT sp FROM SubmissionPayment sp WHERE sp.submission.submissionId=:submissionId", parameters,
                    methodName);
            if (DELETED_STATUS.equals(submissionPayment.getStatus().getDescription())) {
                submissionPayment = null;
            }

        } catch (EntityNotFoundException e) {
            // ignore it.
            submissionPayment = null;
        }

        logExit(methodName);
        return submissionPayment;
    }

    /**
     * <p>
     * Adds the submission review to persistence. It will assign a new id. The submission must already exist.
     * </p>
     *
     * @param submissionReview
     *            The SubmissionReview to add
     * @return the added SubmissionReview
     * @throws IllegalArgumentException
     *             If the submissionReview argument is null
     * @throws EntityNotFoundException
     *             If the submission that this review is for does not exist in persistence, or has been already deleted
     * @throws EntityExistsException
     *             If the submission review already exists in persistence
     * @throws SubmissionManagementException
     *             If any error occurs during the add
     */
    public SubmissionReview addSubmissionReview(SubmissionReview submissionReview)
        throws SubmissionManagementException {
        final String methodName = "addSubmissionReview(SubmissionReview)";
        logEnter(methodName);

        checkNull(submissionReview, "submissionReview", methodName);

        EntityManager entityManager = getEntityManager(methodName);
        Submission submission = getActiveSubmission(entityManager, submissionReview.getSubmission().getSubmissionId(),
                methodName);

        submissionReview.setSubmission(submission);
        saveEntity(entityManager, submissionReview, methodName);
        logExit(methodName);
        return submissionReview;
    }

    /**
     * <p>
     * Updates the submission review in persistence. The submission and its review must already exist.
     * </p>
     *
     * @param submissionReview
     *            The submission review to update
     * @throws IllegalArgumentException
     *             If the submissionReview argument is null
     * @throws EntityNotFoundException
     *             If the submission that this review is for does not exist in persistence, or has been already deleted
     * @throws EntityNotFoundException
     *             If the submission review does not exist in persistence
     * @throws SubmissionManagementException
     *             If any error occurs during the update
     */
    public void updateSubmissionReview(SubmissionReview submissionReview) throws SubmissionManagementException {
        final String methodName = "updateSubmissionReview(SubmissionReview)";
        logEnter(methodName);

        checkNull(submissionReview, "submissionReview", methodName);

        EntityManager entityManager = getEntityManager(methodName);
        Long submissionId = submissionReview.getSubmission().getSubmissionId();
        Submission submission = getActiveSubmission(entityManager, submissionId, methodName);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("submissionId", submissionId);
        parameters.put("description", DELETED_STATUS);
        SubmissionReview review = (SubmissionReview) getSingleResult(entityManager, QUERY_ACTIVE_SUBMISSION_REVIEWS,
                parameters, methodName);

        // copy data
        review.setModifyDate(submissionReview.getModifyDate());
        review.setReviewerId(submissionReview.getReviewerId());
        review.setStatus(submissionReview.getStatus());
        review.setSubmission(submission);
        review.setText(submissionReview.getText());

        updateEntity(entityManager, review, methodName);
        logExit(methodName);
    }

    /**
     * <p>
     * Gets the submission review for the submission with the given id.
     * </p>
     *
     * @param submissionId
     *            The id of the submission of the submission review to get
     * @return The SubmissionReview for the submission, or null if not found.
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    public SubmissionReview getSubmissionReview(long submissionId) throws SubmissionManagementException {
        final String methodName = "getSubmissionReview(long)";
        logEnter(methodName);
        SubmissionReview submissionReview = null;
        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("submissionId", submissionId);
            parameters.put("description", DELETED_STATUS);

            submissionReview = (SubmissionReview) getSingleResult(getEntityManager(methodName),
                    QUERY_ACTIVE_SUBMISSION_REVIEWS, parameters, methodName);
        } catch (EntityNotFoundException e) {
            submissionReview = null;
        }
        logExit("getSubmissionReview(long)");
        return submissionReview;
    }

    /**
     * <p>
     * Removes the submission review for the submission with the given id.
     * </p>
     *
     * @param submissionId
     *            The id of the submission of the submission review to delete
     * @return true of deletions occurred, false otherwise
     * @throws SubmissionManagementException
     *             If any error occurs during the deletion
     */
    @SuppressWarnings("unchecked")
    public boolean removeSubmissionReview(long submissionId) throws SubmissionManagementException {
        final String methodName = "removeSubmissionReview(long)";
        logEnter(methodName);

        boolean removed = false;

        EntityManager entityManager = getEntityManager(methodName);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("submissionId", submissionId);
        parameters.put("description", DELETED_STATUS);
        List<SubmissionReview> submissionReviews = getResultList(entityManager, QUERY_ACTIVE_SUBMISSION_REVIEWS,
                parameters, methodName);

        if (!submissionReviews.isEmpty()) {
            for (SubmissionReview submissionReview : submissionReviews) {
                entityManager.remove(submissionReview);
            }

            removed = true;
        }

        logExit(methodName);

        return removed;
    }

    /**
     * <p>
     * Gets the prizes of the submission with the given id. The submission must currently exist in persistence.
     * </p>
     *
     * @param submissionId
     *            the id of the submission whose prizes are to be retrieved
     * @return the retrieved prizes for the given
     * @throws EntityNotFoundException
     *             If the submission with the given id does not exist in persistence, or submission already deleted
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    public List<Prize> getSubmissionPrizes(long submissionId) throws SubmissionManagementException {
        final String methodName = "getSubmissionPrizes(long)";
        logEnter(methodName);

        Submission submission = getActiveSubmission(getEntityManager(methodName), submissionId, methodName);

        logExit(methodName);
        return new ArrayList<Prize>(submission.getPrizes());
    }

    /**
     * <p>
     * Obtains the EntityManager from the JNDI ENC via the session context.
     * </p>
     *
     * @param methodName
     *            this called method name
     * @return The EntityManager instance
     * @throws SubmissionManagementException
     *             If it can't obtain the manager instance
     */
    private EntityManager getEntityManager(String methodName) throws SubmissionManagementException {
        try {
            EntityManager entityManager = (EntityManager) sessionContext.lookup(unitName);
            if (entityManager == null) {
                throw logException(new SubmissionManagementException("Unable to obtain the manager instance."),
                        methodName);
            }
            return entityManager;
        } catch (ClassCastException e) {
            throw logException(new SubmissionManagementException("Not entity manager.", e), methodName);
        }
    }

    /**
     * <p>
     * Retrieve the required configuration string from context.
     * </p>
     *
     * @param name
     *            the name of the object to look up
     * @param required
     *            whether this property is required.
     * @return the configured object.
     * @throws SubmissionManagementConfigurationException
     *             If the required property is missing or is not desired type, or any naming exception occurs.
     */
    private String getConfigString(String name, boolean required) throws SubmissionManagementConfigurationException {
        try {
            String value = (String) sessionContext.lookup("java:comp/env/" + name);

            if (required) {
                if (value == null || 0 == value.trim().length()) {
                    throw new SubmissionManagementConfigurationException(MessageFormat.format(
                            "The '{0}' property is null/empty.", name));
                }
            }
            return value;
        } catch (ClassCastException e) {
            throw new SubmissionManagementConfigurationException(MessageFormat.format(
                    "The '{0}' property is not String type.", name), e);
        }
    }

    /**
     * <p>
     * Logs the entrance of a method.
     * </p>
     *
     * @param methodName
     *            the method to enter.
     */
    private void logEnter(String methodName) {
        if (logger != null) {
            logger.log(Level.INFO, "Entering method [SubmissionManagerBean.{0}]", methodName);
        }
    }

    /**
     * <p>
     * Logs the exit of a method.
     * </p>
     *
     * @param methodName
     *            the method name
     */
    private void logExit(String methodName) {
        if (logger != null) {
            logger.log(Level.INFO, "Exiting method [SubmissionManagerBean.{0}].", methodName);
        }
    }

    /**
     * <p>
     * Logs the exception thrown.
     * </p>
     *
     * @param exception
     *            the exception thrown.
     * @return the logged exception
     */
    private <T extends Exception> T logException(T exception, String methodName) {
        if (logger != null) {
            logger.log(Level.ERROR, exception, "Error in method [SubmissionManagerBean.{0}]: Details [{1}]",
                    methodName, exception.getMessage());
        }

        return exception;
    }

    /**
     * <p>
     * Gets the entity from persistence layer.
     * </p>
     *
     * @param entityManager
     *            the entity manager
     * @param entityClass
     *            the entity class type.
     * @param primaryKey
     *            the primary key to find the entity
     * @param methodName
     *            this called method name
     * @return the entity instance, or null if not found.
     * @throws SubmissionManagementException
     *             If any error occurs while retrieving the entity.
     */
    private <T> T getEntity(EntityManager entityManager, Class<T> entityClass, Object primaryKey, String methodName)
        throws SubmissionManagementException {
        try {
            return entityManager.find(entityClass, primaryKey);
        } catch (IllegalStateException e) {
            throw logException(new SubmissionManagementException("the EntityManager has been closed.", e), methodName);
        } catch (IllegalArgumentException e) {
            throw logException(new SubmissionManagementException("The object is not an entity.", e), methodName);
        }
    }

    /**
     * <p>
     * Saves the entity into persistence.
     * </p>
     *
     * @param entityManager
     *            the entity manager
     * @param obj
     *            the entity object to save.
     * @param methodName
     *            this called method name
     * @throws SubmissionManagementException
     *             If any error occurs while saving the entity.
     */
    private void saveEntity(EntityManager entityManager, Object obj, String methodName)
        throws SubmissionManagementException {
        try {
            entityManager.persist(obj);

            // call it in order to make the operation done right now, or it will do when committing the transaction.
            entityManager.flush();
        } catch (IllegalStateException e) {
            throw logException(new SubmissionManagementException("the EntityManager has been closed.", e), methodName);
        } catch (IllegalArgumentException e) {
            throw logException(new SubmissionManagementException("The object is not an entity.", e), methodName);
        } catch (javax.persistence.EntityExistsException e) {
            throw logException(new EntityExistsException("Entity already exists.", e), methodName);
        } catch (PersistenceException e) {
            throw logException(new SubmissionManagementException("Persistence problem occurs.", e), methodName);
        }
    }

    /**
     * <p>
     * Updates the entity into persistence.
     * </p>
     *
     * @param entityManager
     *            the entity manager
     * @param obj
     *            the entity object to save.
     * @param methodName
     *            this called method name
     * @throws SubmissionManagementException
     *             If any error occurs while saving the entity.
     */
    private void updateEntity(EntityManager entityManager, Object obj, String methodName)
        throws SubmissionManagementException {
        try {
            entityManager.merge(obj);

            // call it in order to make the operation done right now, or it will do when committing the transaction.
            entityManager.flush();
        } catch (IllegalStateException e) {
            throw logException(new SubmissionManagementException("the EntityManager has been closed.", e), methodName);
        } catch (IllegalArgumentException e) {
            throw logException(new SubmissionManagementException("The object is not an entity.", e), methodName);
        } catch (PersistenceException e) {
            throw logException(new SubmissionManagementException("Persistence problem occurs.", e), methodName);
        }
    }

    /**
     * <p>
     * Removes the entity from persistence layer.
     * </p>
     *
     * @param entityManager
     *            the entity manager
     * @param entityClass
     *            the entity class type.
     * @param primaryKey
     *            the primary key to find the entity
     * @param methodName
     *            this called method name
     * @return true if the entity is removed, or false if the entity does not found
     * @throws SubmissionManagementException
     *             If any error occurs while removing the entity.
     */
    private <T> boolean removeEntity(EntityManager entityManager, Class<T> entityClass, Object primaryKey,
            String methodName) throws SubmissionManagementException {
        T entity = getEntity(entityManager, entityClass, primaryKey, methodName);

        if (null == entity) {
            return false;
        }
        try {
            entityManager.remove(entity);

        } catch (IllegalStateException e) {
            throw logException(new SubmissionManagementException("the EntityManager has been closed.", e), methodName);
        } catch (IllegalArgumentException e) {
            throw logException(new SubmissionManagementException("The object is not an entity.", e), methodName);
        }
        return true;
    }

    /**
     * <p>
     * Gets the single result from persistence.
     * </p>
     *
     * @param entityManager
     *            the entity manager
     * @param qlString
     *            the query string
     * @param parameters
     *            the key and value of parameters.
     * @param methodName
     *            this called method name
     * @return the found entity, or null if not found.
     * @throws SubmissionManagementException
     *             If any error occurs except the NoResultException.
     */
    private Object getSingleResult(EntityManager entityManager, String qlString, Map<String, Object> parameters,
        String methodName) throws SubmissionManagementException {
        try {
            Query query = buildQuery(entityManager, qlString, parameters, methodName);
            Object obj = query.getSingleResult();

            return obj;
        } catch (NoResultException e) {
            throw logException(new EntityNotFoundException("entity does not found.", e), methodName);
        } catch (NonUniqueResultException e) {
            throw logException(new SubmissionManagementException("Multiple results exist.", e), methodName);
        } catch (IllegalStateException e) {
            throw logException(new SubmissionManagementException(
                    "Called for a Java Persistence query language UPDATE or DELETE statement", e), methodName);
        }
    }

    /**
     * <p>
     * Builds the Query object from the query string and parameters map.
     * </p>
     *
     * @param entityManager
     *            the entity manager
     * @param qlString
     *            the query string
     * @param parameters
     *            the key and value of parameters.
     * @param methodName
     *            this called method name
     * @return the created query
     * @throws SubmissionManagementException
     *             If any problem occurs.
     */
    private Query buildQuery(EntityManager entityManager, String qlString, Map<String, Object> parameters,
        String methodName) throws SubmissionManagementException {
        try {
            Query query = entityManager.createQuery(qlString);

            if (null != parameters) {
                for (Entry<String, Object> entry : parameters.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }

            return query;
        } catch (IllegalStateException e) {
            throw logException(new SubmissionManagementException("the EntityManager has been closed.", e), methodName);
        } catch (IllegalArgumentException e) {
            throw logException(new SubmissionManagementException("Fail to build the query.", e), methodName);
        }
    }

    /**
     * <p>
     * Gets the result list from persistence.
     * </p>
     *
     * @param entityManager
     *            the entity manager
     * @param qlString
     *            the query string
     * @param parameters
     *            the key and value of parameters.
     * @param methodName
     *            this called method name
     * @return the result list, possible empty. never null.
     * @throws SubmissionManagementException
     *             If any error occurs except the NoResultException.
     */
    @SuppressWarnings("unchecked")
    private List getResultList(EntityManager entityManager, String qlString, Map<String, Object> parameters,
        String methodName) throws SubmissionManagementException {
        try {
            Query query = buildQuery(entityManager, qlString, parameters, methodName);
            return query.getResultList();
        } catch (IllegalStateException e) {
            throw logException(new SubmissionManagementException(
                    "Called for a Java Persistence query language UPDATE or DELETE statement", e), methodName);
        }
    }

    /**
     * <p>
     * Checks whether the given submission is deleted.
     * </p>
     *
     * @param submission
     *            the submission to check
     * @return true if it is deleted, otherwise false.
     */
    private boolean isSubmissionDeleted(Submission submission) {
        // status will never null by the database schema
        return DELETED_STATUS.equals(submission.getStatus().getDescription());
    }

    /**
     * <p>
     * Checks whether the specified object is null or not.
     * </p>
     *
     * @param obj
     *            the specified object to check.
     * @param name
     *            the object name.
     * @param methodName
     *            this called method name
     * @throws IllegalArgumentException
     *             If the object is null.
     */
    private void checkNull(Object obj, String name, String methodName) {
        if (null == obj) {
            throw logException(new IllegalArgumentException("Parameter [" + name + "] can not be null."), methodName);
        }
    }

    /**
     * <p>
     * Get the active(exist and not deleted) submission specified by the submission id.
     * </p>
     *
     * @param entityManager
     *            the entity manager
     * @param submissionId
     *            the submission id to find
     * @param methodName
     *            this called method name
     * @return the matched submission
     * @throws SubmissionManagementException
     *             If any error occurs occurs.
     * @throws EntityNotFoundException
     *             If the submission does not exist in persistence or is already deleted
     */
    private Submission getActiveSubmission(EntityManager entityManager, Long submissionId, String methodName)
        throws SubmissionManagementException, EntityNotFoundException {
        Submission entity;
        entity = getEntity(entityManager, Submission.class, submissionId, methodName);

        if (null == entity) {
            throw logException(new EntityNotFoundException("No corresponding record to update."), methodName);
        }

        if (isSubmissionDeleted(entity)) {
            throw logException(new EntityNotFoundException("The submission is already deleted."), methodName);
        }
        return entity;
    }

    /**
     * <p>
     * Gets the active submissions for the contest with the given id.
     * </p>
     *
     * @param entityManager
     *            the entity manager
     * @param contestId
     *            The id of the contest of the submissions to get
     * @param selectFullSubmission
     *            a flag whether the full submission should be returned
     * @param methodName
     *            this called method name
     * @return List of Submission for the contest with the given id, or empty list if none found.
     * @throws SubmissionManagementException
     *             If any error occurs during the retrieval
     */
    @SuppressWarnings("unchecked")
    private List<Submission> getSubmissionsForContest(EntityManager entityManager, long contestId, String methodName)
        throws SubmissionManagementException {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("contestId", new Long(contestId));
        parameters.put("description", DELETED_STATUS);

        List<Submission> submissions = getResultList(entityManager,
                "SELECT s FROM Submission s WHERE s.contest.contestId=:contestId"
                        + " AND s.status.description != :description", parameters, methodName);
        return submissions;
    }
    

    /**
     * Clones submission.
     * Fix [TCCC-137]
     * 
     * @param submission
     *            Submission to be cloned.
     * @return cloned submission.
     */
    private Submission cloneSubmission(Submission submission) {
        Submission ret = new Submission();
        ret.setContest(submission.getContest());
        ret.setCreateDate(submission.getCreateDate());
        ret.setFullSubmissionPath(submission.getFullSubmissionPath());
        ret.setHeight(submission.getHeight());
        ret.setMimeType(submission.getMimeType());
        ret.setModifyDate(submission.getModifyDate());
        ret.setOriginalFileName(submission.getOriginalFileName());
        ret.setOrSubmission(submission.getOrSubmission());
        ret.setPrizes(submission.getPrizes());
        ret.setRank(submission.getRank());
        ret.setResult(submission.getResult());
        ret.setReview(submission.getReview());
        ret.setStatus(submission.getStatus());
        ret.setSubmissionDate(submission.getSubmissionDate());
        ret.setSubmissionId(submission.getSubmissionId());
        ret.setSubmitterId(submission.getSubmitterId());
        ret.setSystemFileName(submission.getSystemFileName());
        ret.setType(submission.getType());
        ret.setWidth(submission.getWidth());

        return ret;
    }
}
