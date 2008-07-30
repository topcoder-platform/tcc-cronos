/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio.failuretests;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestStatusTransitionException;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.ContestTypeConfig;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.EntityAlreadyExistsException;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.submission.Prize;

/**
 * This is a mock implementation of ContestManager class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockContestManager implements ContestManager {

    /**
     * Error flag indicating whether the methods should throw an exception.
     */
    private boolean error = false;

    /**
     * Flag indicating whether to return invalid entity.
     */
    private boolean invalid = false;

    /**
     * Set error.
     *
     * @param error
     *            error.
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * Set invalid.
     *
     * @param invalid
     *            invalid
     */
    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    /**
     * <p>
     * Creates a new contest and returns the created contest.
     * </p>
     *
     * @param contest
     *            the contest to create
     * @return the created contest
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public Contest createContest(Contest contest) throws ContestManagementException {
        if (error) {
            throw new ContestManagementException("error");
        }
        return contest;
    }

    /**
     * <p>
     * Gets contest by id, and return the retrieved contest. If the contest doesn't exist, null is returned.
     * </p>
     *
     * @param contestId
     *            the contest id
     * @return the retrieved contest, or null if id doesn't exist
     * @throws ContestManagementException
     *             if any error occurs when getting contest.
     */
    public Contest getContest(long contestId) throws ContestManagementException {
        if (error) {
            throw new ContestManagementException("error");
        }
        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("PS");
        fileType.setExtension(".ps");
        fileType.setImageFile(false);
        fileType.setStudioFileType(34);

        ContestChannel channel = new ContestChannel();
        channel.setContestChannelId(new Long(2));
        channel.setDescription("This is a channel");
        channel.setFileType(fileType);
        channel.setName("news");

        ContestStatus contestStatus = new ContestStatus();
        contestStatus.setContestStatusId(new Long(24));
        contestStatus.setDescription("This is a status");
        contestStatus.setName("name");

        ContestType contestType = new ContestType();
        contestType.setContestType(new Long(234));
        contestType.setDescription("this is a contest type");

        Contest contest = new Contest();
        contest.setContestChannel(channel);
        contest.setContestId(contestId);
        contest.setContestType(contestType);
        contest.setCreatedUser(new Long(34654));
        contest.setEndDate(new Date());
        contest.setStatus(contestStatus);
        contest.setStartDate(new Date());

        if (invalid) {
            contest.setContestId(new Long(-1));
        }

        return contest;
    }

    /**
     * <p>
     * Gets projects's contests by the project id. A list of contests associated with the given tcDirectProjectId should
     * be returned. If there is no such contests, an empty list should be returned.
     * </p>
     *
     * @param tcDirectProjectId
     *            the project id
     * @return a list of associated contests
     * @throws ContestManagementException
     *             if any error occurs when getting contests
     */
    public List<Contest> getContestsForProject(long tcDirectProjectId) throws ContestManagementException {
        if (error) {
            throw new ContestManagementException("error");
        }
        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("PS");
        fileType.setExtension(".ps");
        fileType.setImageFile(false);
        fileType.setStudioFileType(34);

        ContestChannel channel = new ContestChannel();
        channel.setContestChannelId(new Long(2));
        channel.setDescription("This is a channel");
        channel.setFileType(fileType);
        channel.setName("news");

        ContestStatus contestStatus = new ContestStatus();
        contestStatus.setContestStatusId(new Long(24));
        contestStatus.setDescription("This is a status");
        contestStatus.setName("name");

        ContestType contestType = new ContestType();
        contestType.setContestType(new Long(234));
        contestType.setDescription("this is a contest type");

        Contest contest = new Contest();
        contest.setContestChannel(channel);
        contest.setContestId(new Long(24));
        contest.setContestType(contestType);
        contest.setCreatedUser(new Long(34654));
        contest.setEndDate(new Date());
        contest.setStatus(contestStatus);
        contest.setStartDate(new Date());

        if (invalid) {
            contest.setContestId(new Long(-1));
        }

        return Arrays.asList(new Contest[] { contest });
    }

    /**
     * <p>
     * Updates contest data. Note that all data can be updated only if contest is not active. If contest is active it is
     * possible to increase prize amount and duration.
     * </p>
     *
     * @param contest
     *            the contest to update
     * @throws IllegalArgumentException
     *             if the argument is null.
     * @throws EntityNotFoundException
     *             if the contest doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any error occurs when updating contest.
     */
    public void updateContest(Contest contest) throws ContestManagementException {
        if (error) {
            throw new ContestManagementException("error");
        }
        if (contest.getContestId() == (long) 1000) {
            throw new EntityNotFoundException("Error");
        }
    }

    /**
     * <p>
     * Updates contest status to the given value.
     * </p>
     *
     * @param contestId
     *            the contest id
     * @param newStatusId
     *            the new status id
     * @throws EntityNotFoundException
     *             if there is no corresponding Contest or ContestStatus in persistence.
     * @throws ContestStatusTransitionException
     *             if it's not allowed to update the contest to the given status.
     * @throws ContestManagementException
     *             if any error occurs when updating contest's status.
     */
    public void updateContestStatus(long contestId, long newStatusId) throws ContestManagementException {
    }

    /**
     * <p>
     * Gets client for contest, the client id is returned.
     * </p>
     *
     * @param contestId
     *            the contest id
     * @return the id of the client for this contest
     * @throws EntityNotFoundException
     *             if there is no corresponding contest (or project) in persistence.
     * @throws ContestManagementException
     *             if any error occurs when retrieving the client id.
     */
    public long getClientForContest(long contestId) throws ContestManagementException {
        return 0;
    }

    /**
     * <p>
     * Gets client for project, and return the retrieved client id.
     * </p>
     *
     * @param projectId
     *            the project id
     * @return the client id
     * @throws EntityNotFoundException
     *             if there is no corresponding project in persistence.
     * @throws ContestManagementException
     *             if any error occurs when retrieving the client id.
     */
    public long getClientForProject(long projectId) throws ContestManagementException {
        return 0;
    }

    /**
     * <p>
     * Adds contest status, and return the added contest status.
     * </p>
     *
     * @param contestStatus
     *            the contest status to add
     * @return the added contest status
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public ContestStatus addContestStatus(ContestStatus contestStatus) throws ContestManagementException {
        return null;
    }

    /**
     * <p>
     * Updates contest status.
     * </p>
     *
     * @param contestStatus
     *            the contest status to update
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityNotFoundException
     *             if the contestStatus doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public void updateContestStatus(ContestStatus contestStatus) throws ContestManagementException {
    }

    /**
     * <p>
     * Removes contest status, return true if the contest status exists and removed successfully, return false if it
     * doesn't exist.
     * </p>
     *
     * @param contestStatusId
     *            the contest status id
     * @return true if the contest status exists and removed successfully, return false if it doesn't exist
     * @throws ContestManagementException
     *             if any error occurs.
     */
    public boolean removeContestStatus(long contestStatusId) throws ContestManagementException {
        return false;
    }

    /**
     * <p>
     * Gets contest status, and return the retrieved contest status. Return null if it doesn't exist.
     * </p>
     *
     * @param contestStatusId
     *            the contest status id
     * @return the retrieved contest status, or null if it doesn't exist
     * @throws ContestManagementException
     *             if any error occurs when getting contest status.
     */
    public ContestStatus getContestStatus(long contestStatusId) throws ContestManagementException {
        return null;
    }

    /**
     * <p>
     * Adds new document, and return the added document.
     * </p>
     *
     * @param document
     *            the document to add
     * @return the added document
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public Document addDocument(Document document) throws ContestManagementException {
        return null;
    }

    /**
     * <p>
     * Updates the document.
     * </p>
     *
     * @param document
     *            the document to update
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityNotFoundException
     *             if the document doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public void updateDocument(Document document) throws ContestManagementException {

    }

    /**
     * <p>
     * Gets document by id, and return the retrieved document. Return null if the document doesn't exist.
     * </p>
     *
     * @param documentId
     *            the document id
     * @return the retrieved document, or null if it doesn't exist
     * @throws ContestManagementException
     *             if any error occurs when getting document.
     */
    public Document getDocument(long documentId) throws ContestManagementException {
        return null;
    }

    /**
     * <p>
     * Removes document, return true if the document exists and removed successfully, return false if it doesn't exist.
     * </p>
     *
     * @param documentId
     *            the document id
     * @return true if the document exists and removed successfully, return false if it doesn't exist
     * @throws ContestManagementException
     *             if any error occurs.
     */
    public boolean removeDocument(long documentId) throws ContestManagementException {
        return false;
    }

    /**
     * <p>
     * Adds document to contest. Nothing happens if the document already exists in contest.
     * </p>
     *
     * @param documentId
     *            the document id
     * @param contestId
     *            the contest id
     * @throws EntityNotFoundException
     *             if there is no corresponding document or contest in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public void addDocumentToContest(long documentId, long contestId) throws ContestManagementException {

    }

    /**
     * <p>
     * Removes document from contest. Return true if the document exists in the contest and removed successfully, return
     * false if it doesn't exist in contest.
     * </p>
     *
     * @param documentId
     *            the document id
     * @param contestId
     *            the contest id
     * @return true if the document exists in the contest and removed successfully, returns false if it doesn't exist in
     *         contest
     * @throws EntityNotFoundException
     *             if there is no corresponding document or contest in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public boolean removeDocumentFromContest(long documentId, long contestId) throws ContestManagementException {
        return false;
    }

    /**
     * <p>
     * Adds contest category, and return the added contest category.
     * </p>
     *
     * @param contestChannel
     *            the contest channel to add
     * @return the added contest channel
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public ContestChannel addContestChannel(ContestChannel contestChannel) throws ContestManagementException {
        return null;
    }

    /**
     * <p>
     * Updates contest channel.
     * </p>
     *
     * @param contestChannel
     *            the contest channel to update
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityNotFoundException
     *             if the contestChannel doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public void updateContestChannel(ContestChannel contestChannel) throws ContestManagementException {
    }

    /**
     * <p>
     * Removes contest channel, return true if the contest category exists and removed successfully, return false if it
     * doesn't exist.
     * </p>
     *
     * @param contestChannelId
     *            the contest channel id
     * @return true if the contest channel exists and removed successfully, return false if it doesn't exist.
     * @throws ContestManagementException
     *             if fail to remove the contest category when it exists.
     */
    public boolean removeContestChannel(long contestChannelId) throws ContestManagementException {
        return false;
    }

    /**
     * <p>
     * Gets contest channel, and return the retrieved contest channel. Return null if it doesn't exist.
     * </p>
     *
     * @param contestChannelId
     *            the contest channel id
     * @return the retrieved contest channel, or null if it doesn't exist.
     * @throws ContestManagementException
     *             if any error occurs when getting contest channel.
     */
    public ContestChannel getContestChannel(long contestChannelId) throws ContestManagementException {
        return null;
    }

    /**
     * <p>
     * Adds contest configuration parameter, and return the added contest configuration parameter.
     * </p>
     *
     * @param contestConfig
     *            the contest configuration parameter to add
     * @return the added contest configuration parameter
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public ContestConfig addConfig(ContestConfig contestConfig) throws ContestManagementException {
        return null;
    }

    /**
     * <p>
     * Updates contest configuration parameter.
     * </p>
     *
     * @param contestConfig
     *            the contest configuration parameter to update
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityNotFoundException
     *             if the contest configuration parameter doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public void updateConfig(ContestConfig contestConfig) throws ContestManagementException {

    }

    /**
     * <p>
     * Gets contest configuration parameter by id, and return the retrieved contest configuration parameter. Return null
     * if it doesn't exist.
     * </p>
     *
     * @param contestConfigId
     *            the contest configuration parameter id
     * @return the retrieved contest configuration parameter, or null if it doesn't exist.
     * @throws ContestManagementException
     *             if any error occurs when getting contest configuration parameter
     */
    public ContestConfig getConfig(long contestConfigId) throws ContestManagementException {
        return null;
    }

    /**
     * <p>
     * Saves document content in file system. This method should call DocumentContentManager.saveDocumentContent to save
     * the document content.
     * </p>
     *
     * @param documentId
     *            the document id
     * @param documentContent
     *            the file data of the document to save
     * @throws IllegalArgumentException
     *             if fileData argument is null or empty array.
     * @throws EntityNotFoundException
     *             if the document doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public void saveDocumentContent(long documentId, byte[] documentContent) throws ContestManagementException {

    }

    /**
     * <p>
     * Gets document content to return. If the document is not saved, null is returned. It will use
     * DocumentContentManager to get document content. It can also return empty array if the document content is empty.
     * </p>
     *
     * @param documentId
     *            the document id
     * @return the document content in byte array. If the document is not saved, null is returned.
     * @throws EntityNotFoundException
     *             if the document doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public byte[] getDocumentContent(long documentId) throws ContestManagementException {
        return null;
    }

    /**
     * <p>
     * Checks the document's content exists or not. Return true if it exists, return false otherwise. It will use
     * DocumentContentManager to check document content's existence.
     * </p>
     *
     * @param documentId
     *            the document id
     * @return true if the document content exists, return false otherwise.
     * @throws EntityNotFoundException
     *             if the document doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public boolean existDocumentContent(long documentId) throws ContestManagementException {
        return false;
    }

    /**
     * <p>
     * Gets all contest statuses to return. If no contest status exists, return an empty list.
     * </p>
     *
     * @return a list of contest statuses
     * @throws ContestManagementException
     *             if any error occurs when getting contest statuses.
     */
    public List<ContestStatus> getAllContestStatuses() throws ContestManagementException {
        if (error) {
            throw new ContestManagementException("error");
        }

        ContestStatus contestStatus = new ContestStatus();
        contestStatus.setContestStatusId(new Long(24));
        contestStatus.setDescription("This is a status");
        contestStatus.setName("name");

        if (invalid) {
            contestStatus.setContestStatusId(new Long(-1));
        }

        return Arrays.asList(new ContestStatus[] { contestStatus });
    }

    /**
     * <p>
     * Gets all contest channels to return. If no contest category exists, return an empty list.
     * </p>
     *
     * @return a list of contest channels
     * @throws ContestManagementException
     *             if any error occurs when getting contest channels.
     */
    public List<ContestChannel> getAllContestChannels() throws ContestManagementException {
        if (error) {
            throw new ContestManagementException("error");
        }

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("PS");
        fileType.setExtension(".ps");
        fileType.setImageFile(false);
        fileType.setStudioFileType(34);

        ContestChannel channel = new ContestChannel();
        channel.setContestChannelId(new Long(2));
        channel.setDescription("This is a channel");
        channel.setFileType(fileType);
        channel.setName("news");

        if (invalid) {
            channel.setContestChannelId(new Long(-1));
        }

        return Arrays.asList(new ContestChannel[] { channel });
    }

    /**
     * <p>
     * Get all studio file types to return. If no studio file type exists, return an empty list.
     * </p>
     *
     * @return a list of studio file types
     * @throws ContestManagementException
     *             if any error occurs when getting studio file types.
     */
    public List<StudioFileType> getAllStudioFileTypes() throws ContestManagementException {
        if (error) {
            throw new ContestManagementException("error");
        }

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("PS");
        fileType.setExtension(".ps");
        fileType.setImageFile(false);
        fileType.setStudioFileType(34);

        if (invalid) {
            fileType.setStudioFileType(-1);
        }

        return Arrays.asList(new StudioFileType[] { fileType });
    }

    /**
     * <p>
     * Adds contest type configuration parameter, and return the added contest type configuration parameter.
     * </p>
     *
     * @param contestTypeConfig
     *            the contest type configuration parameter to add
     * @return the added contest type configuration parameter
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityAlreadyExistsException
     *             if the entity already exists in the persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public ContestTypeConfig addContestTypeConfig(ContestTypeConfig contestTypeConfig)
            throws ContestManagementException {
        return null;
    }

    /**
     * <p>
     * Updates contest type configuration parameter.
     * </p>
     *
     * @param contestTypeConfig
     *            the contest type configuration parameter to update
     * @throws IllegalArgumentException
     *             if the arg is null.
     * @throws EntityNotFoundException
     *             if the contest type configuration parameter doesn't exist in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public void updateContestTypeConfig(ContestTypeConfig contestTypeConfig) throws ContestManagementException {
    }

    /**
     * <p>
     * Gets contest type configuration parameter by id, and return the retrieved contest type configuration parameter.
     * Return null if it doesn't exist.
     * </p>
     *
     * @param contestTypeConfigId
     *            the contest type configuration parameter id
     * @return the retrieved contest type configuration parameter, or null if it doesn't exist
     * @throws ContestManagementException
     *             if any error occurs when getting contest type configuration parameter
     */
    public ContestTypeConfig getContestTypeConfig(long contestTypeConfigId) throws ContestManagementException {
        return null;
    }

    /**
     * <p>
     * Adds prize to the given contest. Nothing happens if the prize already exists in contest.
     * </p>
     *
     * @param contestId
     *            the contest id
     * @param prizeId
     *            the prize id
     * @throws EntityNotFoundException
     *             if there is no corresponding prize or contest in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public void addPrizeToContest(long contestId, long prizeId) throws ContestManagementException {

    }

    /**
     * <p>
     * Removes prize from contest. Return true if the prize exists in the contest and removed successfully, return false
     * if it doesn't exist in contest.
     * </p>
     *
     * @param contestId
     *            the contest id
     * @param prizeId
     *            the prize id
     * @return true if the prize exists in the contest and removed successfully, return false if it doesn't exist in
     *         contest.
     * @throws EntityNotFoundException
     *             if there is no corresponding prize or contest in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public boolean removePrizeFromContest(long contestId, long prizeId) throws ContestManagementException {
        return false;
    }

    /**
     * <p>
     * Retrieves all prizes in the given contest to return. An empty list is returned if there is no such prizes.
     * </p>
     *
     * @param contestId
     *            the contest id
     * @return a list of prizes
     * @throws EntityNotFoundException
     *             if there is no corresponding contest in persistence.
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public List<Prize> getContestPrizes(long contestId) throws ContestManagementException {
        return null;
    }

    /**
     * Gets all contests.
     *
     * @return All contests
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public List<Contest> getAllContests() throws ContestManagementException {
        return null;
    }

    /**
     * Searches contests with filter.
     *
     * @param filter
     *            The filter to search with
     * @return The found contests
     * @throws ContestManagementException
     *             if any other error occurs.
     */
    public List<Contest> searchContests(Filter filter) throws ContestManagementException {
        if (error) {
            throw new ContestManagementException("error");
        }

        StudioFileType fileType = new StudioFileType();
        fileType.setDescription("PS");
        fileType.setExtension(".ps");
        fileType.setImageFile(false);
        fileType.setStudioFileType(34);

        ContestChannel channel = new ContestChannel();
        channel.setContestChannelId(new Long(2));
        channel.setDescription("This is a channel");
        channel.setFileType(fileType);
        channel.setName("news");

        ContestStatus contestStatus = new ContestStatus();
        contestStatus.setContestStatusId(new Long(24));
        contestStatus.setDescription("This is a status");
        contestStatus.setName("name");

        ContestType contestType = new ContestType();
        contestType.setContestType(new Long(234));
        contestType.setDescription("this is a contest type");

        Contest contest = new Contest();
        contest.setContestChannel(channel);
        contest.setContestId(new Long(24));
        contest.setContestType(contestType);
        contest.setCreatedUser(new Long(34654));
        contest.setEndDate(new Date());
        contest.setStatus(contestStatus);
        contest.setStartDate(new Date());

        if (invalid) {
            contestStatus.setContestStatusId(new Long(-1));
        }

        return Arrays.asList(new Contest[] { contest });
    }
}
