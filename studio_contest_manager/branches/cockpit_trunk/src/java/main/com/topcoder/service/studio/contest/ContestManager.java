/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.ContestConfig.Identifier;
import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;
import com.topcoder.service.studio.PaymentType;

/**
 * <p>
 * This is main interface of <strong>Contest Manager</strong> component. It
 * provides operations on contest like add new contest, get contest, update
 * contest, update contest status; CRUD operations on contest status; CRUD
 * operations on competition document; get client by contest and project; CRUD
 * operations on the contest category; CRUD operations for the configuration
 * parameters; Operations to save, retrieve or check existence of document
 * content; operations to get all contest statues, categories and studio file
 * types.
 * </p>
 *
 * <p>
 * Note: It will use the DocumentContentManager to manage the document content.
 * </p>
 *
 * <p>
 * 1.1 change: 2 new methods <code>searchContests(Filter)</code> and
 * <code>getAllContests()</code> are added.
 * </p>
 *
 * <p>
 * 1.3 change: One method <code>getUserContests(String)</code> is added. the
 * parameter of getConfig is changed from long to Identifier.
 * </p>
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
* <p>
 * Version 1.3.1 (Cockpit Pipeline Release Assembly 1 v1.0) Change Notes:
 *  - Introduced method to retrieve SimplePipelineData for given date range.
 * </p>
  * <p>
 * Version 1.3.2 (Cockpit Pipeline Release Assembly 2 - Capacity) changelog:
 *     - added service that retrieves a list of capacity data (date, number of scheduled contests) starting from
 *       tomorrow for a given contest type
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> It's up to concrete implementations.
 * </p>
 *
 * @author Standlove, pulky
 * @author AleaActaEst, BeBetter
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.3.2
 * @since 1.0
 */
public interface ContestManager {

    /**
     * <p>
     * Creates a new contest and returns the created contest.
     * </p>
     *
     * @param contest the contest to create
     * @return the created contest
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public Contest createContest(Contest contest) throws ContestManagementException;

    /**
     * <p>
     * Gets contest by id, and return the retrieved contest. If the contest
     * doesn't exist, null is returned.
     * </p>
     *
     * @param contestId the contest id
     * @return the retrieved contest, or null if id doesn't exist
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contest.
     */
    public Contest getContest(long contestId) throws ContestManagementException;

    /**
     * <p>
     * Gets projects's contests by the project id. A list of contests associated
     * with the given tcDirectProjectId should be returned. If there is no such
     * contests, an empty list should be returned.
     * </p>
     *
     * @param tcDirectProjectId the project id
     * @return a list of associated contests
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contests
     */
    public List<Contest> getContestsForProject(long tcDirectProjectId) throws ContestManagementException;

    /**
     * <p>
     * Gets contests by the created user. If there is no such contests, an empty
     * list should be returned.
     * </p>
     *
     * @param createdUser the created user.
     * @return a list of associated contests
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contests
     */
    public List<Contest> getContestsForUser(long createdUser) throws ContestManagementException;

    /**
     * <p>
     * Updates contest data. Note that all data can be updated only if contest
     * is not active. If contest is active it is possible to increase prize
     * amount and duration.
     * </p>
     *
     * @param contest the contest to update
     * @param userAdmin
     * @param username
     * @param transactionId
     * @throws IllegalArgumentException if the argument is null.
     * @throws EntityNotFoundException if the contest doesn't exist in
     *         persistence.
     * @throws ContestManagementException if any error occurs when updating
     *         contest.
     */
    public void updateContest(Contest contest, long transactionId, String username, boolean userAdmin)
        throws ContestManagementException;

    /**
     * <p>
     * Updates contest status to the given value.
     * </p>
     *
     * @param contestId the contest id
     * @param newStatusId the new status id
     *
     * @throws EntityNotFoundException if there is no corresponding Contest or
     *         ContestStatus in persistence.
     * @throws ContestStatusTransitionException if it's not allowed to update
     *         the contest to the given status.
     * @throws ContestManagementException if any error occurs when updating
     *         contest's status.
     */
    public void updateContestStatus(long contestId, long newStatusId) throws ContestManagementException;


    /**
     * <p>
     * Adds contest status, and return the added contest status.
     * </p>
     *
     * @param contestStatus the contest status to add
     * @return the added contest status
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public ContestStatus addContestStatus(ContestStatus contestStatus) throws ContestManagementException;

    /**
     * <p>
     * Updates contest status.
     * </p>
     *
     * @param contestStatus the contest status to update
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityNotFoundException if the contestStatus doesn't exist in
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void updateContestStatus(ContestStatus contestStatus) throws ContestManagementException;

    /**
     * <p>
     * Removes contest status, return true if the contest status exists and
     * removed successfully, return false if it doesn't exist.
     * </p>
     *
     * @param contestStatusId the contest status id
     * @return true if the contest status exists and removed successfully,
     *         return false if it doesn't exist
     * @throws ContestManagementException if any error occurs.
     */
    public boolean removeContestStatus(long contestStatusId) throws ContestManagementException;

    /**
     * <p>
     * Gets contest status, and return the retrieved contest status. Return null
     * if it doesn't exist.
     * </p>
     *
     * @param contestStatusId the contest status id
     * @return the retrieved contest status, or null if it doesn't exist
     * @throws ContestManagementException if any error occurs when getting
     *         contest status.
     */
    public ContestStatus getContestStatus(long contestStatusId) throws ContestManagementException;

    /**
     * <p>
     * Adds new document, and return the added document.
     * </p>
     *
     * @param document the document to add
     * @return the added document
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public Document addDocument(Document document) throws ContestManagementException;

    /**
     * <p>
     * Updates the document.
     * </p>
     *
     * @param document the document to update
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityNotFoundException if the document doesn't exist in
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void updateDocument(Document document) throws ContestManagementException;

    /**
     * <p>
     * Gets document by id, and return the retrieved document. Return null if
     * the document doesn't exist.
     * </p>
     *
     * @param documentId the document id
     * @return the retrieved document, or null if it doesn't exist
     * @throws ContestManagementException if any error occurs when getting
     *         document.
     */
    public Document getDocument(long documentId) throws ContestManagementException;

    /**
     * <p>
     * Removes document, return true if the document exists and removed
     * successfully, return false if it doesn't exist.
     * </p>
     *
     * @param documentId the document id
     * @return true if the document exists and removed successfully, return
     *         false if it doesn't exist
     * @throws ContestManagementException if any error occurs.
     */
    public boolean removeDocument(long documentId) throws ContestManagementException;

    /**
     * <p>
     * Adds document to contest. Nothing happens if the document already exists
     * in contest.
     * </p>
     *
     * @param documentId the document id
     * @param contestId the contest id
     * @throws EntityNotFoundException if there is no corresponding document or
     *         contest in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void addDocumentToContest(long documentId, long contestId) throws ContestManagementException;

    /**
     * <p>
     * Removes document from contest. Return true if the document exists in the
     * contest and removed successfully, return false if it doesn't exist in
     * contest.
     * </p>
     *
     * @param documentId the document id
     * @param contestId the contest id
     * @return true if the document exists in the contest and removed
     *         successfully, returns false if it doesn't exist in contest
     * @throws EntityNotFoundException if there is no corresponding document or
     *         contest in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public boolean removeDocumentFromContest(long documentId, long contestId)
        throws ContestManagementException;

    /**
     * <p>
     * Adds contest category, and return the added contest category.
     * </p>
     *
     * @param contestChannel the contest channel to add
     * @return the added contest channel
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public ContestChannel addContestChannel(ContestChannel contestChannel) throws ContestManagementException;

    /**
     * <p>
     * Updates contest channel.
     * </p>
     *
     * @param contestChannel the contest channel to update
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityNotFoundException if the contestChannel doesn't exist in
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void updateContestChannel(ContestChannel contestChannel) throws ContestManagementException;

    /**
     * <p>
     * Removes contest channel, return true if the contest category exists and
     * removed successfully, return false if it doesn't exist.
     * </p>
     *
     * @param contestChannelId the contest channel id
     * @return true if the contest channel exists and removed successfully,
     *         return false if it doesn't exist.
     * @throws ContestManagementException if fail to remove the contest category
     *         when it exists.
     */
    public boolean removeContestChannel(long contestChannelId) throws ContestManagementException;

    /**
     * <p>
     * Gets contest channel, and return the retrieved contest channel. Return
     * null if it doesn't exist.
     * </p>
     *
     * @param contestChannelId the contest channel id
     * @return the retrieved contest channel, or null if it doesn't exist.
     * @throws ContestManagementException if any error occurs when getting
     *         contest channel.
     */
    public ContestChannel getContestChannel(long contestChannelId) throws ContestManagementException;

    /**
     * <p>
     * Adds contest configuration parameter, and return the added contest
     * configuration parameter.
     * </p>
     *
     * @param contestConfig the contest configuration parameter to add
     * @return the added contest configuration parameter
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public ContestConfig addConfig(ContestConfig contestConfig) throws ContestManagementException;

    /**
     * <p>
     * Updates contest configuration parameter.
     * </p>
     *
     * @param contestConfig the contest configuration parameter to update
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityNotFoundException if the contest configuration parameter
     *         doesn't exist in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void updateConfig(ContestConfig contestConfig) throws ContestManagementException;

    /**
     * <p>
     * Gets contest configuration parameter by id, and return the retrieved
     * contest configuration parameter. Return null if it doesn't exist.
     * </p>
     *
     * @param compositeId the composite parameter id.
     * @return the retrieved contest configuration parameter, or null if it
     *         doesn't exist.
     * @throws ContestManagementException if any error occurs when getting
     *         contest configuration parameter
     */
    public ContestConfig getConfig(Identifier compositeId) throws ContestManagementException;

    /**
     * <p>
     * Saves document content in file system. This method should call
     * DocumentContentManager.saveDocumentContent to save the document content.
     * </p>
     *
     * @param documentId the document id
     * @param documentContent the file data of the document to save
     * @throws IllegalArgumentException if fileData argument is null or empty
     *         array.
     * @throws EntityNotFoundException if the document doesn't exist in
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void saveDocumentContent(long documentId, byte[] documentContent)
        throws ContestManagementException;

    /**
     * <p>
     * Gets document content to return. If the document is not saved, null is
     * returned. It will use DocumentContentManager to get document content. It
     * can also return empty array if the document content is empty.
     * </p>
     *
     * @param documentId the document id
     * @return the document content in byte array. If the document is not saved,
     *         null is returned.
     * @throws EntityNotFoundException if the document doesn't exist in
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public byte[] getDocumentContent(long documentId) throws ContestManagementException;

    /**
     * <p>
     * Checks the document's content exists or not. Return true if it exists,
     * return false otherwise. It will use DocumentContentManager to check
     * document content's existence.
     * </p>
     *
     * @param documentId the document id
     * @return true if the document content exists, return false otherwise.
     * @throws EntityNotFoundException if the document doesn't exist in
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public boolean existDocumentContent(long documentId) throws ContestManagementException;

    /**
     * <p>
     * Gets all contest statuses to return. If no contest status exists, return
     * an empty list.
     * </p>
     *
     * @return a list of contest statuses
     * @throws ContestManagementException if any error occurs when getting
     *         contest statuses.
     */
    public List<ContestStatus> getAllContestStatuses() throws ContestManagementException;

    /**
     * <p>
     * Gets all contest channels to return. If no contest category exists,
     * return an empty list.
     * </p>
     *
     * @return a list of contest channels
     * @throws ContestManagementException if any error occurs when getting
     *         contest channels.
     */
    public List<ContestChannel> getAllContestChannels() throws ContestManagementException;

    /**
     * <p>
     * Get all studio file types to return. If no studio file type exists,
     * return an empty list.
     * </p>
     *
     * @return a list of studio file types
     * @throws ContestManagementException if any error occurs when getting
     *         studio file types.
     */
    public List<StudioFileType> getAllStudioFileTypes() throws ContestManagementException;

    /**
     * <p>
     * Adds contest type configuration parameter, and return the added contest
     * type configuration parameter.
     * </p>
     *
     * @param contestTypeConfig the contest type configuration parameter to add
     * @return the added contest type configuration parameter
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public ContestTypeConfig addContestTypeConfig(ContestTypeConfig contestTypeConfig)
        throws ContestManagementException;

    /**
     * <p>
     * Updates contest type configuration parameter.
     * </p>
     *
     * @param contestTypeConfig the contest type configuration parameter to
     *        update
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityNotFoundException if the contest type configuration
     *         parameter doesn't exist in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void updateContestTypeConfig(ContestTypeConfig contestTypeConfig)
        throws ContestManagementException;

    /**
     * <p>
     * Gets contest type configuration parameter by id, and return the retrieved
     * contest type configuration parameter. Return null if it doesn't exist.
     * </p>
     *
     * @param contestTypeConfigId the contest type configuration parameter id
     * @return the retrieved contest type configuration parameter, or null if it
     *         doesn't exist
     * @throws ContestManagementException if any error occurs when getting
     *         contest type configuration parameter
     */
    public ContestTypeConfig getContestTypeConfig(long contestTypeConfigId) throws ContestManagementException;

    /**
     * <p>
     * Adds prize to the given contest. Nothing happens if the prize already
     * exists in contest.
     * </p>
     *
     * @param contestId the contest id
     * @param prizeId the prize id
     * @throws EntityNotFoundException if there is no corresponding prize or
     *         contest in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public void addPrizeToContest(long contestId, long prizeId) throws ContestManagementException;

    /**
     * <p>
     * Removes prize from contest. Return true if the prize exists in the
     * contest and removed successfully, return false if it doesn't exist in
     * contest.
     * </p>
     *
     * @param contestId the contest id
     * @param prizeId the prize id
     * @return true if the prize exists in the contest and removed successfully,
     *         return false if it doesn't exist in contest.
     * @throws EntityNotFoundException if there is no corresponding prize or
     *         contest in persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public boolean removePrizeFromContest(long contestId, long prizeId) throws ContestManagementException;

    /**
     * <p>
     * Retrieves all prizes in the given contest to return. An empty list is
     * returned if there is no such prizes.
     * </p>
     *
     * @param contestId the contest id
     * @return a list of prizes
     *
     * @throws EntityNotFoundException if there is no corresponding contest in
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public List<Prize> getContestPrizes(long contestId) throws ContestManagementException;

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
     * @throws ContestManagementException if any error occurs when getting
     *         contest categories
     */
    public List<Contest> searchContests(Filter filter) throws ContestManagementException;

    /**
     * <p>
     * This is going to fetch all the currently available contests.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contest
     */
    public List<Contest> getAllContests() throws ContestManagementException;

    /**
     * <p>
     * This is going to fetch all the currently available contests for contest
     * monitor widget.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contest
     *
     * @since 1.1
     */
    public List<SimpleContestData> getSimpleContestData() throws ContestManagementException;

    /**
     * <p>
     * This is going to fetch all the currently available contests related to
     * given project.
     * </p>
     *
     * @param pid the given project id;
     * @return the list of all available contents (or empty if none found)
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contest
     *
     * @since 1.1
     */
    public List<SimpleContestData> getSimpleContestData(long pid) throws ContestManagementException;

    /**
     * <p>
     * This is going to fetch user's currently available contests for contest
     * monitor widget.
     * </p>
     *
     * @param createdUser the created user.
     * @return the list of all available contents (or empty if none found)
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contest
     *
     * @since 1.1
     */
    public List<SimpleContestData> getSimpleContestDataForUser(long createdUser)
            throws ContestManagementException;

    /**
     * <p>
     * This is going to fetch user's currently available contests for myproject
     * widget.
     * </p>
     *
     * @param createdUser the created user.
     * @return the list of all available contents (or empty if none found)
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contest
     *
     * @since 1.1
     */
    public List<SimpleProjectContestData> getSimpleProjectContestDataForUser(long createdUser)
            throws ContestManagementException;

    /**
     * <p>
     * This is going to fetch all the currently available contests for myproject
     * widget.
     * </p>
     *
     * @return the list of all available contents (or empty if none found)
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contest
     *
     * @since 1.1
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData() throws ContestManagementException;

    /**
     * <p>
     * This is going to fetch all the currently available contests related the
     * given projects.
     * </p>
     *
     * @param pid - the given project id
     * @return the list of all available contents (or empty if none found)
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contest
     *
     * @since 1.1
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData(long pid)
            throws ContestManagementException;

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
    public List<SimpleContestData> getContestDataOnly() throws ContestManagementException;

    /**
     * <p>
     * This is going to fetch only contestid and contest name for contest
     * related to given project.
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
    public List<SimpleContestData> getContestDataOnly(long createdUser, long pid)
            throws ContestManagementException;

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
    public List<SimpleContestData> getContestDataOnlyForUser(long createdUser)
            throws ContestManagementException;

    /**
     * <p>
     * Gets all the currently available contests types.
     * </p>
     *
     * @return the list of all available contents type (or empty if none found)
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contest types
     */
    public List<ContestType> getAllContestTypes() throws ContestManagementException;

    /**
     * <p>
     * Get all the ContestProperty objects.
     * </p>
     *
     * @return the list of all available ContestProperty
     *
     * @throws ContestManagementException if any error occurs when getting
     *         contest
     *
     * @since 1.1.2
     */
    public List<ContestProperty> getAllContestProperties() throws ContestManagementException;

    /**
     * <p>
     * Get the ContestProperty with the specified id.
     * </p>
     *
     * @param contestPropertyId id to look for
     * @return the ContestProperty with the specified id.
     * @throws ContestManagementException if any error occurs when getting
     *         contest
     * @since 1.1.2
     */
    public ContestProperty getContestProperty(long contestPropertyId) throws ContestManagementException;

    /**
     * <p>
     * Get all the MimeType objects.
     * </p>
     *
     * @return the list of all available MimeType
     *
     * @throws ContestManagementException if any error occurs when getting
     *         MimeType
     *
     * @since 1.1.2
     */
    public List<MimeType> getAllMimeTypes() throws ContestManagementException;

    /**
     * <p>
     * Get the MimeType with the specified id.
     * </p>
     *
     * @param mimeTypeId id to look for
     * @return the MimeType with the specified id.
     * @throws ContestManagementException if any error occurs when getting
     *         contest
     * @since 1.1.2
     */
    public MimeType getMimeType(long mimeTypeId) throws ContestManagementException;

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
     * @since 1.1.2
     */
    public List<DocumentType> getAllDocumentTypes() throws ContestManagementException;

    /**
     * <p>
     * Get the DocumentType with the specified id.
     * </p>
     *
     * @param documentTypeId id to look for
     * @return the DocumentType with the specified id.
     * @throws ContestManagementException if any error occurs when getting
     *         contest
     * @since 1.1.2
     */
    public DocumentType getDocumentType(long documentTypeId) throws ContestManagementException;

    /**
     * <p>
     * Creates a new prize and returns the created prize.
     * </p>
     *
     * @param prize the prize to create
     * @return the created prize
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public Prize createPrize(Prize prize) throws ContestManagementException;

    /**
     * <p>
     * Creates a new contest payment and returns the created contest payment.
     * </p>
     *
     * @param contestPayment the contest payment to create
     * @return the created contest payment.
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public ContestPayment createContestPayment(ContestPayment contestPayment)
            throws ContestManagementException;

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
    public List<ContestPayment> getContestPayments(long contestId) throws ContestManagementException;

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
    public void editContestPayment(ContestPayment contestPayment) throws ContestManagementException;

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
    public boolean removeContestPayment(long contestPaymentId) throws ContestManagementException;

    /**
     * Returns all mediums.
     *
     * @return all mediums.
     * @throws ContestManagementException if any error occurs when getting
     *         contests
     */
    public List<Medium> getAllMedia() throws ContestManagementException;

    /**
     * <p>
     * Get all the PrizeType objects.
     * </p>
     *
     * @return the list of all available PrizeType
     *
     * @throws ContestManagementException if any error occurs when getting
     *         PrizeType.
     * @since TCCC-349
     */
    public List<PrizeType> getAllPrizeTypes() throws ContestManagementException;

    /**
     * <p>
     * Get the PrizeType with the specified id.
     * </p>
     *
     * @param prizeTypeId id to look for
     * @return the PrizeType with the specified id.
     * @throws ContestManagementException if any error occurs when getting
     *         PrizeType.
     * @since TCCC-349
     */
    public PrizeType getPrizeType(long prizeTypeId) throws ContestManagementException;

    /**
     * <p>
     * Get all the PaymentStatus objects.
     * </p>
     *
     * @return the list of all available PaymentStatus
     *
     * @throws ContestManagementException if any error occurs when getting
     *         PaymentStatus.
     *
     * @since TCCC-349
     */
    public List<PaymentStatus> getAllPaymentStatuses() throws ContestManagementException;

    /**
     * <p>
     * Get the PaymentStatus with the specified id.
     * </p>
     *
     * @param paymentStatusId id to look for
     * @return the PaymentStatus with the specified id.
     * @throws ContestManagementException if any error occurs when getting
     *         PaymentStatus
     * @since TCCC-349
     */
    public PaymentStatus getPaymentStatus(long paymentStatusId) throws ContestManagementException;

    /**
     * Returns contest post count.
     *
     * @return contest post count.
     * @throws ContestManagementException if any error occurs when getting
     *         contest post count.
     */
    public int getContestPostCount(long forumId) throws ContestManagementException;

    /**
     * Returns contest post count.
     *
     * @param forumIds forum ids.
     * @return contest post count.
     * @throws ContestManagementException if any error occurs when getting
     *         contest post count.
     */
    public Map<Long, Long> getContestPostCount(List<Long> forumIds) throws ContestManagementException;

    /**
     * <p>
     * Creates a new contest result and returns the created contest result.
     * </p>
     *
     * @param contestResult the contest result to create
     * @return the created contest result.
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws EntityAlreadyExistsException if the entity already exists in the
     *         persistence.
     * @throws ContestManagementException if any other error occurs.
     */
    public ContestResult createContestResult(ContestResult contestResult) throws ContestManagementException;

    /**
     * <p>
     * Returns the contest result associated with submissionId, contestId if
     * any.
     * </p>
     *
     * @param submissionId the submission Id
     * @param contestId the contest Id
     * @return the contest result or null.
     *
     * @throws ContestManagementException if any error occurs.
     */
    public ContestResult findContestResult(long submissionId, long contestId)
            throws ContestManagementException;

    /**
     * Add a change history entity.
     *
     * @param history Change history entity to be added.
     *
     * @throws ContestManagementException if any other error occurs.
     */
    public void addChangeHistory(List<ContestChangeHistory> history) throws ContestManagementException;

    /**
     * Returns change history entity list.
     *
     * @param contestId contest id to search for.
     * @return Change history entities match the contest id.
     * @throws ContestManagementException if any other error occurs.
     */
    public List<ContestChangeHistory> getChangeHistory(long contestId) throws ContestManagementException;

    /**
     * Returns change history entity list.
     *
     * @param contestId contest id to search for.
     * @param transactionId transaction id to search for.
     *
     * @return Change history entities match the contest id and transaction id.
     * @throws ContestManagementException if any other error occurs.
     */
    public List<ContestChangeHistory> getChangeHistory(long contestId, long transactionId)
            throws ContestManagementException;

    /**
     * Returns latest transaction id.
     *
     * @param contestId contest id to search for.
     * @return Transaction id.
     * @throws ContestManagementException if any other error occurs.
     */
    public Long getLatestTransactionId(long contestId) throws ContestManagementException;

    /**
     * Delete contest.
     *
     * @param contestId contest id to search for.
     * @throws ContestManagementException if any other error occurs.
     */
    public void deleteContest(long contestId) throws ContestManagementException;

    /**
     * Loads and returns the list of payment types (currently it is 'Paypal' and
     * 'TC Purchase order')
     *
     * @since BUGR-1067
     * @return list of payment types
     * @throws PersistenceException if any error occurs
     */
    public List<PaymentType> getAllPaymentTypes() throws ContestManagementException;

    /**
     * Loads and returns the concrete payment type
     *
     * @since BUGR-1076
     * @param paymentTypeId  the id of paymentTypeId
     * @return list of payment types
     * @throws PersistenceException if any error occurs
     */
    public PaymentType getPaymentType(long paymentTypeId) throws ContestManagementException;

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
        throws ContestManagementException;

    /**
     * <p>
     * Retrieves the list of users whose handle contains the specified key.
     * </p>
     *
     * Comment added for Cockpit Project Admin Release Assembly v1.0
     *
     * @param key specified key to search for.
     * @return the list of users.
     */
    public List<User> searchUser(String key) throws ContestManagementException;

    /**
     * Retrieves the list of contests for which the user with the given name is
     * a resource. Returns an empty list if no contests are found.
     *
     * @param username the name of the user
     * @return the list of found contests (empty list of none found)
     *
     * @throws IllegalArgumentException if username is null or empty
     * @throws ContestManagementException when any other error occurs
     * @since 1.3
     */
    public List<Contest> getUserContests(String username) throws ContestManagementException;

    /**
     * Gets the list of simple pipeline data for specified user id and between specified start and end date.
     * 
     * @param userId
     *            the user id.
     * @param startDate
     *            the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate
     *            the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests
     *            whether to include overdue contests or not.
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws ContestManagementException
     *             if error during retrieval from database.
     * @since 1.1.1
     */
    public List<SimplePipelineData> getSimplePipelineData(long userId, Date startDate, Date endDate,
            boolean overdueContests) throws ContestManagementException;

    /**
     * Retrieves a list of capacity data (date, number of scheduled contests) for the given contest type starting
     * from tomorrow.
     *
     * @param contestType the contest type
     *
     * @return the list of capacity data
     *
     * @throws ContestManagementException if any error occurs during retrieval of information.
     *
     * @since 1.2
     */
    public List<StudioCapacityData> getCapacity(int contestType) throws ContestManagementException;


    /**
     * check contest permission, check if a user has permission (read or write) on a contest
     *
     * @param contestId the contest id
     * @param projectId tc direct project id
     * @param readonly check read or write permission
     * @param userId user id
     *
     * @return true/false
     *
     */
    public boolean checkContestPermission(long contestId, long projectId, boolean readonly, long userId)  throws ContestManagementException;

     /**
     * check contest permission, check if a user has permission (read or write) on a contest
     *
     * @param contestId the contest id
     * @param readonly check read or write permission
     * @param userId user id
     *
     * @return true/false
     *
     */
    public boolean checkContestPermission(long contestId, boolean readonly, long userId)  throws ContestManagementException;

     /**
     * check submission permission, check if a user has permission (read or write) on a submission's contest
     *
     * @param submissionId the submission id
     * @param readonly check read or write permission
     * @param userId user id
     *
     * @return true/false
     *
     */
    public boolean checkSubmissionPermission(long submissionId, boolean readonly, long userId) throws ContestManagementException;

    /**
     * check contest permission, check if a user has permission (read or write) on a project
     *
     * @param projectId the tc direct project id
     * @param readonly check read or write permission
     * @param userId user id
     *
     * @return true/false
     *
     */
    public boolean checkProjectPermission(long projectId, boolean readonly, long userId) throws ContestManagementException;
}
