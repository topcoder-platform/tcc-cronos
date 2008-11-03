/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.failuretests;

import java.util.List;

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
 * This class implements ContestManager interface.
 * It is only used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockContestManager implements ContestManager {
    /**
     * <p>
     * Adds contest configuration parameter, and return the added contest configuration parameter.
     * </p>
     *
     * @param contestConfig the contest configuration parameter to add
     * @return the added contest configuration parameter
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public ContestConfig addConfig(ContestConfig contestConfig) throws ContestManagementException {
        return null;
    }

    /**
     * <p>
     * Adds contest category, and return the added contest category.
     * </p>
     *
     * @param contestChannel the contest channel to add
     * @return the added contest channel
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public ContestChannel addContestChannel(ContestChannel contestChannel) throws ContestManagementException {
        return null;
    }

    /**
     * <p>
     * Adds contest status, and return the added contest status.
     * </p>
     *
     * @param contestStatus the contest status to add
     * @return the added contest status
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public ContestStatus addContestStatus(ContestStatus contestStatus) throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Adds contest type configuration parameter, and return the added contest type configuration parameter.
     * </p>
     *
     * @param contestTypeConfig the contest type configuration parameter to add
     * @return the added contest type configuration parameter
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public ContestTypeConfig addContestTypeConfig(ContestTypeConfig contestTypeConfig)
        throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Adds document to contest. Nothing happens if the document already exists in contest.
     * </p>
     *
     * @param documentId the document id
     * @param contestId the contest id
     * @throws EntityNotFoundException if there is no corresponding document or contest in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public Document addDocument(Document document) throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Adds document to contest. Nothing happens if the document already exists in contest.
     * </p>
     *
     * @param documentId the document id
     * @param contestId the contest id
     * @throws EntityNotFoundException if there is no corresponding document or contest in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void addDocumentToContest(long documentId, long contestId) throws ContestManagementException {

    }

    /**
     * <p>
     * Adds prize to the given contest. Nothing happens if the prize already exists in contest.
     * </p>
     *
     * @param contestId the contest id
     * @param prizeId the prize id
     * @throws EntityNotFoundException if there is no corresponding prize or contest in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void addPrizeToContest(long contestId, long prizeId) throws ContestManagementException {

    }

    /**
     * <p>
     * Creates a new contest and returns the created contest.
     * </p>
     *
     * @param contest the contest to create
     * @return the created contest
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public Contest createContest(Contest contest) throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Checks the document's content exists or not. Return true if it exists, return false otherwise.
     * It will use DocumentContentManager to check document content's existence.
     * </p>
     *
     * @param documentId the document id
     * @return true if the document content exists, return false otherwise.
     * @throws EntityNotFoundException if the document doesn't exist in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public boolean existDocumentContent(long documentId) throws ContestManagementException {

        return false;
    }

    /**
     * <p>
     * Gets all contest channels to return. If no contest category exists, return an empty list.
     * </p>
     *
     * @return a list of contest channels
     * @throws ContestManagementException if any error occurs when getting contest channels.
     */
    public List<ContestChannel> getAllContestChannels() throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Gets all contest statuses to return.  If no contest status exists, return an empty list.
     * </p>
     *
     * @return a list of contest statuses
     * @throws ContestManagementException if any error occurs when getting contest statuses.
     */
    public List<ContestStatus> getAllContestStatuses() throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Get all studio file types to return. If no studio file type exists, return an empty list.
     * </p>
     *
     * @return a list of studio file types
     * @throws ContestManagementException if any error occurs when getting studio file types.
     */
    public List<StudioFileType> getAllStudioFileTypes() throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Gets client for contest, the client id is returned.
     * </p>
     *
     * @param contestId the contest id
     * @return the id of the client for this contest
     *
     * @throws EntityNotFoundException if there is no corresponding contest (or project) in persistence.
     * @throws ContestManagementException if any error occurs when retrieving the client id.
     */
    public long getClientForContest(long contestId) throws ContestManagementException {

        return 0;
    }

    /**
     * <p>
     * Gets client for project, and return the retrieved client id.
     * </p>
     *
     * @param projectId the project id
     * @return the client id
     * @throws EntityNotFoundException if there is no corresponding project in persistence.
     * @throws ContestManagementException if any error occurs when retrieving the client id.
     */
    public long getClientForProject(long projectId) throws ContestManagementException {

        return 0;
    }

    /**
     * <p>
     * Gets contest configuration parameter by id, and return the retrieved contest
     * configuration parameter. Return null if it doesn't exist.
     * </p>
     *
     * @param contestConfigId the contest configuration parameter id
     * @return the retrieved contest configuration parameter, or null if it doesn't exist.
     * @throws ContestManagementException if any error occurs when getting contest configuration parameter
     */
    public ContestConfig getConfig(long contestConfigId) throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Gets contest by id, and return the retrieved contest. If the contest doesn't exist,
     * null is returned.
     * </p>
     *
     * @param contestId the contest id
     * @return the retrieved contest, or null if id doesn't exist
     *
     * @throws ContestManagementException if any error occurs when getting contest.
     */
    public Contest getContest(long contestId) throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Gets contest channel, and return the retrieved contest channel. Return null if it doesn't exist.
     * </p>
     *
     * @param contestChannelId the contest channel id
     * @return the retrieved contest channel, or null if it doesn't exist.
     * @throws ContestManagementException if any error occurs when getting contest channel.
     */
    public ContestChannel getContestChannel(long contestChannelId) throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Retrieves all prizes in the given contest to return. An empty list is returned if there is no such prizes.
     * </p>
     *
     * @param contestId the contest id
     * @return a list of prizes
     *
     * @throws EntityNotFoundException if there is no corresponding contest in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public List<Prize> getContestPrizes(long contestId) throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Gets contest status, and return the retrieved contest status. Return null if it doesn't exist.
     * </p>
     *
     * @param contestStatusId the contest status id
     * @return the retrieved contest status, or null if it doesn't exist
     * @throws ContestManagementException if any error occurs when getting contest status.
     */
    public ContestStatus getContestStatus(long contestStatusId) throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Gets contest type configuration parameter by id, and return the retrieved contest
     * type configuration parameter. Return null if it doesn't exist.
     * </p>
     *
     * @param contestTypeConfigId the contest type configuration parameter id
     * @return the retrieved contest type configuration parameter, or null if it doesn't exist
     * @throws ContestManagementException if any error occurs when getting contest type configuration parameter
     */
    public ContestTypeConfig getContestTypeConfig(long contestTypeConfigId) throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Gets projects's contests by the project id. A list of contests associated with the
     * given tcDirectProjectId should be returned. If there is no such contests, an empty
     * list should be returned.
     * </p>
     *
     * @param tcDirectProjectId the project id
     * @return a list of associated contests
     *
     * @throws ContestManagementException if any error occurs when getting contests
     */
    public List<Contest> getContestsForProject(long tcDirectProjectId) throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Gets document by id, and return the retrieved document. Return null if the document doesn't exist.
     * </p>
     *
     * @param documentId the document id
     * @return the retrieved document, or null if it doesn't exist
     * @throws ContestManagementException if any error occurs when getting document.
     */
    public Document getDocument(long documentId) throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Gets document content to return.  If the document is not saved, null is returned.
     * It will use DocumentContentManager to get document content. It can also return empty
     * array if the document content is empty.
     * </p>
     *
     * @param documentId the document id
     * @return the document content in byte array. If the document is not saved, null is returned.
     * @throws EntityNotFoundException if the document doesn't exist in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public byte[] getDocumentContent(long documentId) throws ContestManagementException {

        return null;
    }

    /**
     * <p>
     * Removes contest channel, return true if the contest category exists and removed
     * successfully, return false if it doesn't exist.
     * </p>
     *
     * @param contestChannelId the contest channel id
     * @return true if the contest channel exists and removed successfully, return false if it doesn't exist.
     * @throws ContestManagementException if fail to remove the contest category when it exists.
     */
    public boolean removeContestChannel(long contestChannelId) throws ContestManagementException {

        return false;
    }

    /**
     * <p>
     * Removes contest status, return true if the contest status exists and removed successfully,
     * return false if it doesn't exist.
     * </p>
     *
     * @param contestStatusId the contest status id
     * @return true if the contest status exists and removed successfully, return false if it doesn't exist
     * @throws ContestManagementException if any error occurs.
     */
    public boolean removeContestStatus(long contestStatusId) throws ContestManagementException {

        return false;
    }

    /**
     * <p>
     * Removes document, return true if the document exists and removed successfully,
     * return false if it doesn't exist.
     * </p>
     *
     * @param documentId the document id
     * @return true if the document exists and removed successfully, return false if it doesn't exist
     * @throws ContestManagementException if any error occurs.
     */
    public boolean removeDocument(long documentId) throws ContestManagementException {

        return false;
    }

    /**
     * <p>
     * Removes document from contest. Return true if the document exists in the contest
     * and removed successfully, return false if it doesn't exist in contest.
     * </p>
     *
     * @param documentId the document id
     * @param contestId the contest id
     * @return true if the document exists in the contest and removed successfully,
     *         returns false if it doesn't exist in contest
     * @throws EntityNotFoundException if there is no corresponding document or contest in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public boolean removeDocumentFromContest(long documentId, long contestId) throws ContestManagementException {

        return false;
    }

    /**
     * <p>
     * Removes prize from contest. Return true if the prize exists in the contest and
     * removed successfully, return false if it doesn't exist in contest.
     * </p>
     *
     * @param contestId the contest id
     * @param prizeId the prize id
     * @return true if the prize exists in the contest and removed successfully,
     *         return false if it doesn't exist in contest.
     * @throws EntityNotFoundException if there is no corresponding prize or contest in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public boolean removePrizeFromContest(long contestId, long prizeId) throws ContestManagementException {

        return false;
    }

    /**
     * <p>
     * Saves document content in file system. This method should call DocumentContentManager.saveDocumentContent
     * to save the document content.
     * </p>
     *
     * @param documentId the document id
     * @param documentContent the file data of the document to save
     * @throws IllegalArgumentException if fileData argument is null or empty array.
     * @throws EntityNotFoundException if the document doesn't exist in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void saveDocumentContent(long documentId, byte[] documentContent) throws ContestManagementException {

    }

    /**
     * <p>
     * Updates contest configuration parameter.
     * </p>
     *
     * @param contestConfig the contest configuration parameter to update
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityNotFoundException if the contest configuration parameter doesn't exist in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void updateConfig(ContestConfig contestConfig) throws ContestManagementException {

    }

    /**
     * <p>
     * Updates contest data. Note that all data can be updated only if contest is not active.
     * If contest is active it is possible to increase prize amount and duration.
     * </p>
     *
     * @param contest the contest to update
     * @throws IllegalArgumentException if the argument is null.
     * @throws EntityNotFoundException if the contest doesn't exist in persistence.
     * @throws ContestManagementException if any error occurs when updating contest.
     */
    public void updateContest(Contest contest) throws ContestManagementException {

    }

    /**
     * <p>
     * Updates contest channel.
     * </p>
     *
     * @param contestChannel the contest channel to update
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityNotFoundException if the contestChannel doesn't exist in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void updateContestChannel(ContestChannel contestChannel) throws ContestManagementException {

    }

    /**
     * <p>
     * Updates contest status.
     * </p>
     *
     * @param contestStatus the contest status to update
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityNotFoundException if the contestStatus doesn't exist in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void updateContestStatus(ContestStatus contestStatus) throws ContestManagementException {

    }

    /**
     * <p>
     * Updates contest status to the given value.
     * </p>
     *
     * @param contestId the contest id
     * @param newStatusId the new status id
     *
     * @throws EntityNotFoundException if there is no corresponding Contest or ContestStatus in persistence.
     * @throws ContestStatusTransitionException if it's not allowed to update the contest to the given status.
     * @throws ContestManagementException if any error occurs when updating contest's status.
     */
    public void updateContestStatus(long contestId, long newStatusId) throws ContestManagementException {

    }

    /**
     * <p>Updates contest type configuration parameter.</p>
     *
     * @param contestTypeConfig the contest type configuration parameter to update
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityNotFoundException if the contest type configuration parameter doesn't exist in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void updateContestTypeConfig(ContestTypeConfig contestTypeConfig) throws ContestManagementException {

    }

    /**
     * <p>
     * Updates the document.
     * </p>
     *
     * @param document the document to update
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityNotFoundException if the document doesn't exist in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void updateDocument(Document document) throws ContestManagementException {

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

    public List<Contest> searchContests(com.topcoder.search.builder.filter.Filter filter)
            throws ContestManagementException {
        // TODO Auto-generated method stub
        return null;
    }

}
