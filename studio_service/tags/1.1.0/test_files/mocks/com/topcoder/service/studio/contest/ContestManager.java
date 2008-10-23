package com.topcoder.service.studio.contest;

import com.topcoder.service.studio.submission.Prize;

import java.util.List;

/**
 * This is main interface of Contest Manager component. It provides operations on contest like add new contest, get
 * contest, update contest, update contest status; CRUD operations on contest status; CRUD operations on competition
 * document; get client by contest and project; CRUD operations on the contest category; CRUD operations for the
 * configuration parameters; Operations to save, retrieve or check existence of document content; operations to get all
 * contest statues, categories and studio file types.
 *
 * It will use the DocumentContentManager to manage the document content.
 *
 * Thread-safety:  Up to implemenations.
 */
public interface ContestManager {
    /**
     * Create new contest, and return the created contest.
     *
     * #Paramcontest - the contest to create.
     *
     * #Return - the created contest.
     *
     * #exception throw IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if the entity
     * already exists in the persistence. throw ContestManagementException if any other error occurs.
     *
     * @param contest
     * @return
     */
    public Contest createContest(Contest contest) throws ContestManagementException;

    /**
     * Get contest by id, and return the retrieved contest.  If the contest doesn't exist, null is returned.
     *
     * #Param contestId - the contest id.
     *
     * #Return - the retrieved contest, or null if it doesn't exist.
     *
     * #exception throw ContestManagementException if any error occurs when getting contest.
     *
     * @param contestId
     * @return
     */
    public Contest getContest(long contestId) throws ContestManagementException;

    /**
     * Get projects's contests by the project id. A list of contests associated with the given tcDirectProjectId should be
     * returned.  If there is no such contests, an empty list should be returned.
     *
     * #Param tcDirectProjectId - the project id.
     *
     * #Return - a list of associated contests.
     *
     * #exception throw ContestManagementException if any error occurs when getting contests.
     *
     * @param tcDirectProjectId
     * @return
     */
    public List<Contest> getContestsForProject(long tcDirectProjectId) throws ContestManagementException;

    /**
     * Update contest data. Note that all data can be updated only if contest is not active. If contest is active it is
     * possible to increase prize amount and duration.
     *
     * #Param contest - the contest to update.
     *
     * #exception throw IllegalArgumentException if the argument is null. throw EntityNotFoundException if the contest
     * doesn't exist in persistence. throw ContestManagementException if any error occurs when updating contest.
     *
     * @param contest
     */
    public void updateContest(Contest contest) throws ContestManagementException;

    /**
     * Update contest status to the given value.
     *
     * #Param contestId - the contest id. newStatusId - the new status id.
     *
     * #exception throw EntityNotFoundException if there is no corresponding Contest or ContestStatus in persistence. throw
     * ContestStatusTransitionException if it's not allowed to update the contest to the given status. throw
     * ContestManagementException if any error occurs when updating contest's status.
     *
     * @param contestId
     * @param newStatusId
     */
    public void updateContestStatus(long contestId, long newStatusId) throws ContestManagementException;

    /**
     * Get client for contest, the client id is returned.
     *
     * #Param contestId - the contest id.
     *
     * #Return - the client id.
     *
     * #exception throw EntityNotFoundException if there is no corresponding contest (or project) in persistence. throw
     * ContestManagementException if any error occurs when retrieving the client id.
     *
     * @param contestId
     * @return
     */
    public long getClientForContest(long contestId) throws ContestManagementException;

    /**
     * Get client for project, and return the retrieved client id.
     *
     * #Param projectId - the project id.
     *
     * #Return - the client id.
     *
     * #exception throw EntityNotFoundException if there is no corresponding project in persistence. throw
     * ContestManagementException if any error occurs when retrieving the client id.
     *
     * @param projectId
     * @return
     */
    public long getClientForProject(long projectId) throws ContestManagementException;

    /**
     * Add contest status, and return the added contest status.
     *
     * #Param contestStatus - the contest status to add.
     *
     * #return - the added contest status.
     *
     * #exception throw IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if the entity
     * already exists in the persistence. throw ContestManagementException if any other error occurs.
     *
     * @param contestStatus
     * @return
     */
    public ContestStatus addContestStatus(ContestStatus contestStatus) throws ContestManagementException;

    /**
     * Update contest status.
     *
     * #Param contestStatus - the contest status to update.
     *
     * #exception throw IllegalArgumentException if the arg is null. throw EntityNotFoundException if the contestStatus
     * doesn't exist in persistence. throw ContestManagementException if any other error occurs.
     *
     * @param contestStatus
     */
    public void updateContestStatus(ContestStatus contestStatus) throws ContestManagementException;

    /**
     * Remove contest status, return true if the contest status exists and removed successfully, return false if it doesn't
     * exist.
     *
     * #Param contestStatusId - the contest status id.
     *
     * #Return -  true if the contest status exists and removed successfully, return false if it doesn't exist.
     *
     * #exception throw ContestManagementException if any error occurs.
     *
     * @param contestStatusId
     * @return
     */
    public boolean removeContestStatus(long contestStatusId) throws ContestManagementException;

    /**
     * Get contest status, and return the retrieved contest status. Return null if it doesn't exist.
     *
     * #Param contestStatusId - the contest status id.
     *
     * #Return - the retrieved contest status, or null if it doesn't exist.
     *
     * #exception throw ContestManagementException if any error occurs when getting contest status.
     *
     * @param contestStatusId
     * @return
     */
    public ContestStatus getContestStatus(long contestStatusId) throws ContestManagementException;

    /**
     * Add new document, and return the added document.
     *
     * #Param document - the document to add.
     *
     * #Return - the added document.
     *
     * #exception throw IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if the entity
     * already exists in the persistence. throw ContestManagementException if any other error occurs.
     *
     * @param document
     * @return
     */
    public Document addDocument(Document document) throws ContestManagementException;

    /**
     * Update document.
     *
     * #Param document - the document to update.
     *
     * #exception throw IllegalArgumentException if the arg is null. throw EntityNotFoundException if the document doesn't
     * exist in persistence. throw ContestManagementException if any other error occurs.
     *
     * @param document
     */
    public void updateDocument(Document document) throws ContestManagementException;

    /**
     * Get document by id, and return the retrieved document. Return null if the document doesn't exist.
     *
     * #Param documentId - the document id.
     *
     * #Return - the retrieved document, or null if it doesn't exist.
     *
     * #exception throw ContestManagementException if any error occurs when getting document.
     *
     * @param documentId
     * @return
     */
    public Document getDocument(long documentId) throws ContestManagementException;

    /**
     * Remove document, return true if the document exists and removed successfully, return false if it doesn't exist.
     *
     * #Param documentId - the document id.
     *
     * #Return -  true if the document exists and removed successfully, return false if it doesn't exist.
     *
     * #exception throw ContestManagementException if any error occurs.
     *
     * @param documentId
     * @return
     */
    public boolean removeDocument(long documentId) throws ContestManagementException;

    /**
     * Add document to contest. Nothing happens if the document already exists in contest.
     *
     * #Param documentId - the document id. contestId - the contest id.
     *
     * #exception throw EntityNotFoundException if there is no corresponding document or contest in persistence. throw
     * ContestManagementException if any other error occurs.
     *
     * @param contestId
     * @param documentId
     */
    public void addDocumentToContest(long documentId, long contestId) throws ContestManagementException;

    /**
     * Remove document from contest. Return true if the document exists in the contest and removed successfully, return
     * false if it doesn't exist in contest.
     *
     * #Param documentId - the document id. contestId - the contest id.
     *
     * #Return - true if the document exists in the contest and removed successfully, return false if it doesn't exist in
     * contest.
     *
     * #exception throw EntityNotFoundException if there is no corresponding document or contest in persistence. throw
     * ContestManagementException if any other error occurs.
     *
     * @param contestId
     * @param documentId
     * @return
     */
    public boolean removeDocumentFromContest(long documentId, long contestId) throws ContestManagementException;

    /**
     * Add contest category, and return the added contest category.
     *
     * #Param contestCategory - the contest category to add.
     *
     * #return - the added contest category.
     *
     * #exception throw IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if the entity
     * already exists in the persistence. throw ContestManagementException if any other error occurs.
     *
     * @param contestCategory
     * @return
     */
    public ContestChannel addContestCategory(ContestChannel contestCategory) throws ContestManagementException;

    /**
     * Update contest category
     *
     * #Param contestCategory - the contest category to update.
     *
     * #exception throw IllegalArgumentException if the arg is null. throw EntityNotFoundException if the contestCategory
     * doesn't exist in persistence. throw ContestManagementException if any other error occurs.
     *
     * @param contestCategory
     */
    public void updateContestCategory(ContestChannel contestCategory) throws ContestManagementException;

    /**
     * Remove contest category, return true if the contest category exists and removed successfully, return false if it
     * doesn't exist.
     *
     * #Param contestCategoryId - the contest category id.
     *
     * #Return - true if the contest category exists and removed successfully, return false if it doesn't exist.
     *
     * #exception throw ContestManagementException if fail to remove the contest category when it exists.
     *
     * @param contestCategoryId
     */
    public boolean removeContestCategory(long contestCategoryId) throws ContestManagementException;

    /**
     * Get contest category, and return the retrieved contest category. Return null if it doesn't exist.
     *
     * #Param contestCategoryId - the contest category id.
     *
     * #Return - the retrieved contest category, or null if it doesn't exist.
     *
     * #exception throw ContestManagementException if any error occurs when getting contest category.
     *
     * @param contestCategoryId
     * @return
     */
    public ContestChannel getContestCategory(long contestCategoryId) throws ContestManagementException;

    /**
     * Add contest configuration parameter, and return the added contest configuration parameter.
     *
     * #Param contestConfig - the contest configuration parameter to add.
     *
     * #return - the added contest configuration parameter.
     *
     * #exception throw IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if the entity
     * already exists in the persistence. throw ContestManagementException if any other error occurs.
     *
     * @param contestConfig
     * @return
     */
    public ContestConfig addConfig(ContestConfig contestConfig) throws ContestManagementException;

    /**
     * Update contest configuration parameter
     *
     * #Param contestConfig - the contest configuration parameter to update.
     *
     * #exception throw IllegalArgumentException if the arg is null. throw EntityNotFoundException if the contest
     * configuration parameter doesn't exist in persistence. throw ContestManagementException if any other error occurs.
     *
     * @param contestConfig
     */
    public void updateConfig(ContestConfig contestConfig) throws ContestManagementException;

    /**
     * Get contest configuration parameter by id, and return the retrieved contest configuration parameter. Return null if
     * it doesn't exist.
     *
     * #Param contestConfigId - the contest configuration parameter id.
     *
     * #Return - the retrieved contest configuration parameter, or null if it doesn't exist.
     *
     * #exception throw ContestManagementException if any error occurs when getting contest configuration parameter
     *
     * @param contestConfigId
     * @return
     */
    public ContestConfig getConfig(long contestConfigId) throws ContestManagementException;

    /**
     * Save document content in file system. This method should call DocumentContentManager.saveDocumentContent to save the
     * document content.
     *
     * #param documentId - the document documentContent - the file data of the document to save.
     *
     * #exception throw IllegalArgumentException if fileData argument is null or empty array. throw EntityNotFoundException
     * if the document doesn't exist in persistence. throw ContestManagementException if any other error occurs. throw - let
     * exceptions from DocumentContentManager.saveDocumentContent propagate.
     *
     * @param documentContent
     * @param documentId
     */
    public void saveDocumentContent(long documentId, byte[] documentContent) throws ContestManagementException;

    /**
     * Get document content to return.  If the document is not saved, null is returned.  It will use DocumentContentManager
     * to get document content. It can also return empty array if the document content is empty.
     *
     * #Param documentId - the document id.
     *
     * #Return - the document content in byte array. If the document is not saved, null is returned.
     *
     * #exception throw EntityNotFoundException if the document doesn't exist in persistence. throw
     * ContestManagementException if any other error occurs. throw - let exceptions from
     * DocumentContentManager.getDocumentContent propagate.
     *
     * @param documentId
     * @return
     */
    public byte[] getDocumentContent(long documentId) throws ContestManagementException;

    /**
     * Check the document's content exists or not.  Return true if it exists, return false otherwise.  It will use
     * DocumentContentManager to check document content's existence.
     *
     * #Param document - the document.
     *
     * #Return - true if the document content exists, return false otherwise.
     *
     * #exception throw EntityNotFoundException if the document doesn't exist in persistence. throw
     * ContestManagementException if any other error occurs. throw - let exceptions from
     * DocumentContentManager.existDocumentContent propagate.
     *
     * @param documentId
     * @return
     */
    public boolean existDocumentContent(long documentId) throws ContestManagementException;

    /**
     * Get all contest statuses to return.  If no contest status exists, return an empty list.
     *
     * #Return - a list of contest statuses.
     *
     * #exception throw ContestManagementException if any error occurs when getting contest statuses.
     *
     * @return
     */
    public List<ContestStatus> getAllContestStatuses() throws ContestManagementException;

    /**
     * Get all contest categories to return.  If no contest category exists, return an empty list.
     *
     * #Return - a list of contest categories.
     *
     * #exception throw ContestManagementException if any error occurs when getting contest categories.
     *
     * @return
     */
    public List<ContestChannel> getAllContestCategories() throws ContestManagementException;

    /**
     * Get all studio file types to return.  If no studio file type exists, return an empty list.
     *
     * #Return - a list of studio file types
     *
     * #exception throw ContestManagementException if any error occurs when getting studio file types.
     *
     * @return
     */
    public List<StudioFileType> getAllStudioFileTypes() throws ContestManagementException;

    /**
     * Add contest type configuration parameter, and return the added contest type configuration parameter.
     *
     * #Param contestTypeConfig - the contest type configuration parameter to add.
     *
     * #return - the added contest type configuration parameter.
     *
     * #exception throw IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if the entity
     * already exists in the persistence. throw ContestManagementException if any other error occurs.
     *
     * @param contestTypeConfig
     * @return
     */
    public ContestTypeConfig addContestTypeConfig(ContestTypeConfig contestTypeConfig) throws ContestManagementException;

    /**
     * Update contest type configuration parameter
     *
     * #Param contestTypeConfig - the contest type configuration parameter to update.
     *
     * #exception throw IllegalArgumentException if the arg is null. throw EntityNotFoundException if the contest type
     * configuration parameter doesn't exist in persistence. throw ContestManagementException if any other error occurs.
     *
     * @param contestTypeConfig
     */
    public void updateContestTypeConfig(ContestTypeConfig contestTypeConfig) throws ContestManagementException;

    /**
     * Get contest type configuration parameter by id, and return the retrieved contest type configuration parameter. Return
     * null if it doesn't exist.
     *
     * #Param contestTypeConfigId - the contest type configuration parameter id.
     *
     * #Return - the retrieved contest type configuration parameter, or null if it doesn't exist.
     *
     * #exception throw ContestManagementException if any error occurs when getting contest type configuration parameter
     *
     * @param contestTypeConfigId
     * @return
     */
    public ContestTypeConfig getContestTypeConfig(long contestTypeConfigId) throws ContestManagementException;

    /**
     * Add prize to the given contest. Nothing happens if the prize already exists in contest.
     *
     * #Param contestId - the contest id. prizeId - the prize id.
     *
     * #exception throw EntityNotFoundException if there is no corresponding prize or contest in persistence. throw
     * ContestManagementException if any other error occurs.
     *
     * @param contestId
     * @param prizeId
     */
    public void addPrizeToContest(long contestId, long prizeId) throws ContestManagementException;

    /**
     * Remove prize from contest. Return true if the prize exists in the contest and removed successfully, return false if
     * it doesn't exist in contest.
     *
     * #Param prizeId - the prize id. contestId - the contest id.
     *
     * #Return - true if the prize exists in the contest and removed successfully, return false if it doesn't exist in
     * contest.
     *
     * #exception throw EntityNotFoundException if there is no corresponding prize or contest in persistence. throw
     * ContestManagementException if any other error occurs.
     *
     * @param contestId
     * @param prizeId
     * @return
     */
    public boolean removePrizeFromContest(long contestId, long prizeId) throws ContestManagementException;

    /**
     * Retrieve all prizes in the given contest to return.  An empty list is returned if there is no such prizes.
     *
     * #Param contestId - the contest id.
     *
     * #Return - a list of prizes.
     *
     * #exception throw EntityNotFoundException if there is no corresponding contest in persistence. throw
     * ContestManagementException if any other error occurs.
     *
     * @param contestId
     * @return
     */
    public List<Prize> getContestPrizes(long contestId) throws ContestManagementException;
}
