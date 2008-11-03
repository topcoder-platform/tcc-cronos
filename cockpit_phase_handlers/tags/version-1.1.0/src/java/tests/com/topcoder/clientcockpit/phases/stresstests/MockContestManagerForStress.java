/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.stresstests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestPayment;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.ContestTypeConfig;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.Medium;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;

/**
 * <p>
 * Mock class <code>ContestManager</code> for stress tests.
 * </p>
 *
 * @author Littleken
 * @version 1.0
 */
public class MockContestManagerForStress implements ContestManager {

    /**
     * <p>
     * Represents all the statuses for stress tests.
     * </p>
     */
    private List<ContestStatus> statuses = new ArrayList<ContestStatus>();

    /**
     * <p>
     * Represents id for stress tests.
     * </p>
     */
    private long id = 10L;

    /**
     * Constructor method.
     *
     * @param throwException
     *            whether to throw exception.
     */
    public MockContestManagerForStress() {
        ContestStatus completed = new ContestStatus();
        completed.setContestStatusId(1L);
        completed.setName("Completed");
        statuses.add(completed);

        ContestStatus abandoned = new ContestStatus();
        abandoned.setContestStatusId(2L);
        abandoned.setName("Abandoned");
        statuses.add(abandoned);

        ContestStatus actionRequired = new ContestStatus();
        actionRequired.setContestStatusId(3L);
        actionRequired.setName("ActionRequired");
        statuses.add(actionRequired);

        ContestStatus active = new ContestStatus();
        active.setContestStatusId(4L);
        active.setName("Active");
        statuses.add(active);

        ContestStatus cancelled = new ContestStatus();
        cancelled.setContestStatusId(5L);
        cancelled.setName("Cancelled");
        statuses.add(cancelled);

        ContestStatus draft = new ContestStatus();
        draft.setContestStatusId(6L);
        draft.setName("Draft");
        statuses.add(draft);

        ContestStatus extended = new ContestStatus();
        extended.setContestStatusId(7L);
        extended.setName("Extended");
        statuses.add(extended);

        ContestStatus inDanger = new ContestStatus();
        inDanger.setContestStatusId(8L);
        inDanger.setName("InDanger");
        statuses.add(inDanger);

        ContestStatus insufficientSubmissions = new ContestStatus();
        insufficientSubmissions.setContestStatusId(9L);
        insufficientSubmissions.setName("InsufficientSubmissions");
        statuses.add(insufficientSubmissions);

        ContestStatus insufficientSubmissionsReRunPossible = new ContestStatus();
        insufficientSubmissionsReRunPossible.setContestStatusId(10L);
        insufficientSubmissionsReRunPossible.setName("InsufficientSubmissionsReRunPossible");
        statuses.add(insufficientSubmissionsReRunPossible);

        ContestStatus noWinnerChosen = new ContestStatus();
        noWinnerChosen.setContestStatusId(11L);
        noWinnerChosen.setName("NoWinnerChosen");
        statuses.add(noWinnerChosen);

        ContestStatus repost = new ContestStatus();
        repost.setContestStatusId(12L);
        repost.setName("Repost");
        statuses.add(repost);

        ContestStatus scheduled = new ContestStatus();
        scheduled.setContestStatusId(13L);
        scheduled.setName("Scheduled");
        statuses.add(scheduled);

        ContestStatus terminated = new ContestStatus();
        terminated.setContestStatusId(14L);
        terminated.setName("Terminated");
        statuses.add(terminated);

    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestConfig
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public ContestConfig addConfig(ContestConfig contestConfig) throws ContestManagementException {
        return null;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestChannel
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public ContestChannel addContestChannel(ContestChannel contestChannel) throws ContestManagementException {
        return null;
    }

    /**
     * Add the status to the statuses.
     *
     * @param contestStatus
     *            the status to add.
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return the added status.
     */
    public ContestStatus addContestStatus(ContestStatus contestStatus) throws ContestManagementException {
        contestStatus.setContestStatusId(id++);
        statuses.add(contestStatus);
        return contestStatus;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param document
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public Document addDocument(Document document) throws ContestManagementException {
        return null;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param documentId
     *            Does nothing  for stress tests..
     * @param contestId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     */
    public void addDocumentToContest(long documentId, long contestId) throws ContestManagementException {
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestId
     *            Does nothing  for stress tests..
     * @param prizeId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     */
    public void addPrizeToContest(long contestId, long prizeId) throws ContestManagementException {

    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contest
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public Contest createContest(Contest contest) throws ContestManagementException {
        return null;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param documentId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public boolean existDocumentContent(long documentId) throws ContestManagementException {
        return false;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public List<ContestChannel> getAllContestChannels() throws ContestManagementException {
        return null;
    }

    /**
     * Get all statuses.
     *
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return all the statuses
     */
    public List<ContestStatus> getAllContestStatuses() throws ContestManagementException {
        return statuses;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public List<StudioFileType> getAllStudioFileTypes() throws ContestManagementException {
        return null;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public long getClientForContest(long contestId) throws ContestManagementException {
        return 0;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param projectId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public long getClientForProject(long projectId) throws ContestManagementException {
        return 0;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestConfigId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public ContestConfig getConfig(long contestConfigId) throws ContestManagementException {
        return null;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public Contest getContest(long contestId) throws ContestManagementException {
        return null;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestChannelId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public ContestChannel getContestChannel(long contestChannelId) throws ContestManagementException {
        return null;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public List<Prize> getContestPrizes(long contestId) throws ContestManagementException {
        return null;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestStatusId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public ContestStatus getContestStatus(long contestStatusId) throws ContestManagementException {
        return null;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param tcDirectProjectId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public List<Contest> getContestsForProject(long tcDirectProjectId) throws ContestManagementException {
        return null;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param documentId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public Document getDocument(long documentId) throws ContestManagementException {
        return null;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param documentId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public byte[] getDocumentContent(long documentId) throws ContestManagementException {
        return null;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestChannelId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public boolean removeContestChannel(long contestChannelId) throws ContestManagementException {
        return false;
    }

    /**
     * Remove status.
     *
     * @param contestStatusId
     *            the status id.
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return true if removed else false.
     */
    public boolean removeContestStatus(long contestStatusId) throws ContestManagementException {
        for (ContestStatus status : statuses) {
            if (status.getContestStatusId() == contestStatusId) {
                statuses.remove(status);
                return true;
            }
        }
        return false;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param documentId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public boolean removeDocument(long documentId) throws ContestManagementException {
        return false;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param documentId
     *            Does nothing  for stress tests..
     * @param contestId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public boolean removeDocumentFromContest(long documentId, long contestId) throws ContestManagementException {
        return false;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestId
     *            Does nothing  for stress tests..
     * @param prizeId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public boolean removePrizeFromContest(long contestId, long prizeId) throws ContestManagementException {
        return false;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param documentId
     *            Does nothing  for stress tests..
     * @param documentContent
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     */
    public void saveDocumentContent(long documentId, byte[] documentContent) throws ContestManagementException {

    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestConfig
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     */
    public void updateConfig(ContestConfig contestConfig) throws ContestManagementException {

    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contest
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     */
    public void updateContest(Contest contest) throws ContestManagementException {

    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestChannel
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     */
    public void updateContestChannel(ContestChannel contestChannel) throws ContestManagementException {

    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestId
     *            Does nothing  for stress tests..
     * @param newStatusId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     */
    public void updateContestStatus(long contestId, long newStatusId) throws ContestManagementException {
    }

    /**
     * Update the contestStatus.
     *
     * @param contestStatus
     *            the contestStatus to update
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     */
    public void updateContestStatus(ContestStatus contestStatus) throws ContestManagementException {
        for (ContestStatus status : statuses) {
            if (status.getContestStatusId() == contestStatus.getContestStatusId()) {
                statuses.remove(status);
                statuses.add(contestStatus);
                break;
            }
        }

    }

    /**
     * Does nothing  for stress tests..
     *
     * @param document
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     */
    public void updateDocument(Document document) throws ContestManagementException {
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestTypeConfig
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public ContestTypeConfig addContestTypeConfig(ContestTypeConfig contestTypeConfig)
            throws ContestManagementException {
        return null;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestTypeConfigId
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     * @return Does nothing  for stress tests..
     */
    public ContestTypeConfig getContestTypeConfig(long contestTypeConfigId) throws ContestManagementException {
        return null;
    }

    /**
     * Does nothing  for stress tests..
     *
     * @param contestTypeConfig
     *            Does nothing  for stress tests..
     * @throws ContestManagementException
     *             Does nothing  for stress tests..
     */
    public void updateContestTypeConfig(ContestTypeConfig contestTypeConfig) throws ContestManagementException {

    }

    public ContestPayment createContestPayment(ContestPayment contestPayment) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public Prize createPrize(Prize prize) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public void editContestPayment(ContestPayment contestPayment) throws ContestManagementException {
        // TODO Auto-generated method stub
        
    }

    public List<ContestProperty> getAllContestProperties() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ContestType> getAllContestTypes() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Contest> getAllContests() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<DocumentType> getAllDocumentTypes() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Medium> getAllMedia() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<MimeType> getAllMimeTypes() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PaymentStatus> getAllPaymentStatuses() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<PrizeType> getAllPrizeTypes() throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public ContestPayment getContestPayment(long contestPaymentId) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public ContestProperty getContestProperty(long contestPropertyId) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Contest> getContestsForUser(long createdUser) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public DocumentType getDocumentType(long documentTypeId) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public MimeType getMimeType(long mimeTypeId) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public PaymentStatus getPaymentStatus(long paymentStatusId) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public PrizeType getPrizeType(long prizeTypeId) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean removeContestPayment(long contestPaymentId) throws ContestManagementException {
        // TODO Auto-generated method stub
        return false;
    }

    public List<Contest> searchContests(Filter filter) throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }
}
