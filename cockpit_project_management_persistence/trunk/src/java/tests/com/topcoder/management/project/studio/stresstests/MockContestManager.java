/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio.stresstests;

import com.topcoder.management.project.studio.ConversionException;
import com.topcoder.management.project.studio.converter.ProjectToContestConverterImpl;
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
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.Medium;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mock implementation of <code>ContestManager</code> to be used in testing.
 * </p>
 * 
 * @author hfx
 * @version 1.0
 */
public class MockContestManager implements ContestManager {
    /**
     * <p>
     * Represents a mapping of contest id to the contest instance.
     * </p>
     */
    private final Map<Long, Contest> contests = new HashMap<Long, Contest>();

    /**
     * <p>
     * Represents a mapping of contest status id to the contest status instance.
     * </p>
     */
    private final Map<Long, ContestStatus> contestStatuses = new HashMap<Long, ContestStatus>();

    /**
     * Not implemented.
     * 
     * @param contestConfig
     *            contest config.
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */
    public ContestConfig addConfig(ContestConfig contestConfig)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param contestChannel
     *            contest channel.
     * @return none
     * @throws ContestManagementException
     *             never thrown
     */

    public ContestChannel addContestChannel(ContestChannel contestChannel)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Add a contest status to this manager.
     * 
     * @param contestStatus
     *            the contest status to add.
     * @return the newly added contest status.
     * @throws ContestManagementException
     *             if any error occurs while adding contest status.
     */

    public ContestStatus addContestStatus(ContestStatus contestStatus)
            throws ContestManagementException {
        contestStatuses.put(contestStatus.getContestStatusId(), contestStatus);

        return contestStatus;
    }

    /**
     * Not implemented.
     * 
     * @param contestTypeConfig
     *            contest type config.
     * @return none.
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public ContestTypeConfig addContestTypeConfig(
            ContestTypeConfig contestTypeConfig)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param document
     *            document
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public Document addDocument(Document document)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param documentId
     *            document id
     * @param contestId
     *            contest id
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public void addDocumentToContest(long documentId, long contestId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param contestId
     *            contest id.
     * @param prizeId
     *            prize id.
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public void addPrizeToContest(long contestId, long prizeId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Creates the contest in this manager.
     * 
     * @param contest
     *            contest.
     * @return created contest.
     * @throws ContestManagementException
     *             if any error occurs while creating the contest.
     */

    public Contest createContest(Contest contest)
            throws ContestManagementException {
        contests.put(contest.getContestId(), contest);

        ContestStatus status = contest.getStatus();

        if (status != null) {
            addContestStatus(status);
        }

        return contest;
    }

    /**
     * Not implemented.
     * 
     * @param documentId
     *            document id
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public boolean existDocumentContent(long documentId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public List<ContestChannel> getAllContestChannels()
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Gets all contest statuses.
     * 
     * @return all contest statuses.
     * @throws ContestManagementException
     *             if any error occurs while retrieving all contest statuses.
     */

    public List<ContestStatus> getAllContestStatuses()
            throws ContestManagementException {
        List<ContestStatus> statuses = new ArrayList<ContestStatus>();
        statuses.addAll(contestStatuses.values());

        return statuses;
    }

    /**
     * Not implemented.
     * 
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public List<StudioFileType> getAllStudioFileTypes()
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param contestId
     *            contest id.
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public long getClientForContest(long contestId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param projectId
     *            project id.
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public long getClientForProject(long projectId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param contestConfigId
     *            contest config id.
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public ContestConfig getConfig(long contestConfigId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Get the contest with specified id.
     * 
     * @param contestId
     *            the contest id.
     * @return a Contest instance.
     * @throws ContestManagementException
     *             never thrown.
     */

    public Contest getContest(long contestId) throws ContestManagementException {
        try {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // ignore
            }
            return new ProjectToContestConverterImpl()
                    .convertProjectToContest(ContestManagerProjectAdapterTests
                            .createProject());
        } catch (ConversionException e) {
            throw new ContestManagementException("Failed");
        }
    }

    /**
     * Not implemented.
     * 
     * @param contestChannelId
     *            contest channel id.
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public ContestChannel getContestChannel(long contestChannelId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param contestId
     *            contest id.
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public List<Prize> getContestPrizes(long contestId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Gets the contest status with specified id.
     * 
     * @param contestStatusId
     *            contest status id.
     * @return ContestStatus instance.
     * @throws ContestManagementException
     *             never thrown.
     */

    public ContestStatus getContestStatus(long contestStatusId)
            throws ContestManagementException {
        return contestStatuses.get(contestStatusId);
    }

    /**
     * Not implemented.
     * 
     * @param contestTypeConfigId
     *            contest type config id.
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public ContestTypeConfig getContestTypeConfig(long contestTypeConfigId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param tcDirectProjectId
     *            tc direct project id
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public List<Contest> getContestsForProject(long tcDirectProjectId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param documentId
     *            document id.
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public Document getDocument(long documentId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param documentId
     *            document id.
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public byte[] getDocumentContent(long documentId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param contestChannelId
     *            contest channel id.
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public boolean removeContestChannel(long contestChannelId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Remove contest status from this manager.
     * 
     * @param contestStatusId
     *            contest status id.
     * @return true if contest status is removed, false otherwise.
     * @throws ContestManagementException
     *             never thrown.
     */

    public boolean removeContestStatus(long contestStatusId)
            throws ContestManagementException {
        return contestStatuses.remove(contestStatusId) != null;
    }

    /**
     * Not implemented.
     * 
     * @param documentId
     *            the document id.
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public boolean removeDocument(long documentId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param documentId
     *            document id.
     * @param contestId
     *            contest id.
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public boolean removeDocumentFromContest(long documentId, long contestId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param contestId
     *            contest id.
     * @param prizeId
     *            prize id.
     * @return none
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public boolean removePrizeFromContest(long contestId, long prizeId)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param documentId
     *            document id.
     * @param documentContent
     *            document content.
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public void saveDocumentContent(long documentId, byte[] documentContent)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param contestConfig
     *            contest config.
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public void updateConfig(ContestConfig contestConfig)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param contest
     *            contest.
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public void updateContest(Contest contest)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param contestChannel
     *            contest channel.
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public void updateContestChannel(ContestChannel contestChannel)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param contestStatus
     *            contest status.
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public void updateContestStatus(ContestStatus contestStatus)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Updates a contest status.
     * 
     * @param contestId
     *            contest id
     * @param newStatusId
     *            new status id.
     * @throws ContestManagementException
     *             if any error occurs while updating contest status.
     */

    public void updateContestStatus(long contestId, long newStatusId)
            throws ContestManagementException {
        if (!contests.containsKey(contestId)) {
            throw new EntityNotFoundException("No contest found for id '"
                    + contestId + "'");
        }

        if (!contestStatuses.containsKey(newStatusId)) {
            throw new EntityNotFoundException(
                    "No contest status found for id '" + newStatusId + "'");
        }

        Contest contest = contests.get(contestId);
        contest.setStatus(contestStatuses.get(newStatusId));
    }

    /**
     * Not implemented.
     * 
     * @param contestTypeConfig
     *            contest type config.
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public void updateContestTypeConfig(ContestTypeConfig contestTypeConfig)
            throws ContestManagementException {
        throw new UnsupportedOperationException("This method is not supported");
    }

    /**
     * Not implemented.
     * 
     * @param document
     *            document.
     * @throws ContestManagementException
     *             never thrown.
     * @throws UnsupportedOperationException
     *             always thrown.
     */

    public void updateDocument(Document document)
            throws ContestManagementException {

    }

    /**
     * Return null.
     */
    public List<Contest> getAllContests() throws ContestManagementException {
        return null;
    }

    /**
     * Simply return 5 contests
     * 
     * @param filter the filter
     */
    public List<Contest> searchContests(Filter filter)
            throws ContestManagementException {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // ignore
        }
        ArrayList<Contest> list = new ArrayList<Contest>();
        for (int i = 0; i < 5; i++) {
            try {
                // add contest
                list.add(new ProjectToContestConverterImpl()
                                .convertProjectToContest(ContestManagerProjectAdapterTests
                                        .createProject()));
            } catch (ConversionException e) {
                throw new ContestManagementException("Failed");
            }
        }

        return list;
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
}
