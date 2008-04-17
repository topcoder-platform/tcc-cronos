/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManagerRemote;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestTypeConfig;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.EntityAlreadyExistsException;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.submission.Prize;

/**
 * <p>
 * Mock implementation of contest manager remote interface.
 * </p>
 *
 * @author moon.river
 * @version 1.0
 */
public class ContestManagerImpl implements ContestManagerRemote {
    /**
     * <p>Represents the contests.</p>
     */
    public static Map<Long, Contest> contests;
    /**
     * <p>Represents the documents.</p>
     */
    public static Map<Long, Document> documents;
    /**
     * <p>Represents the documents for contest.</p>
     */
    public static Map<Long, Set<Long>> documentsForContest;
    /**
     * <p>Represents the documents contents.</p>
     */
    public static Map<Long, byte[]> documentsContent;

    /**
     * <p>Represents the last call.</p>
     */
    public static String lastCall;

    /**
     * <p>The constructor.</p>
     */
    public ContestManagerImpl() {
        contests = new HashMap<Long, Contest>();
        documents = new HashMap<Long, Document>();
        documentsForContest = new HashMap<Long, Set<Long>>();
        documentsContent = new HashMap<Long, byte[]>();
    }

    /**
     * Create new contest, and return the created contest.
     *
     * @param contest - the contest to create.
     *
     * @return - the created contest.
     *
     * @exception throw IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if the entity
     * already exists in the persistence. throw ContestManagementException if any other error occurs.
     */
    public Contest createContest(Contest contest) throws ContestManagementException {
        contests.put(contest.getContestId(), contest);
        return contest;
    }

    /**
     * Get contest by id, and return the retrieved contest.  If the contest doesn't exist, null is returned.
     *
     * @param contestId - the contest id.
     *
     * @return - the retrieved contest, or null if it doesn't exist.
     *
     * @throws throw ContestManagementException if any error occurs when getting contest.
     */
    public Contest getContest(long contestId) throws ContestManagementException {
        Contest c = contests.get(contestId);
        if (c == null) {
            return c;
        }

        c.setDocuments(new HashSet<Document>());
        if (documentsForContest.get(contestId) == null) {
            documentsForContest.put(contestId, new HashSet<Long>());
        }
        for (Long docId : documentsForContest.get(contestId)) {
            c.getDocuments().add(documents.get(docId));
        }

        return c;
    }

    /**
     * Get projects's contests by the project id. A list of contests associated with the given tcDirectProjectId should
     * be returned.  If there is no such contests, an empty list should be returned.
     *
     * @param tcDirectProjectId - the project id.
     *
     * @return - a list of associated contests.
     *
     * @throws throw ContestManagementException if any error occurs when getting contests.
     */
    public List<Contest> getContestsForProject(long tcDirectProjectId) throws ContestManagementException {
        List<Contest> result = new ArrayList<Contest>();
        for (Contest contest : contests.values()) {
            if (contest.getTcDirectProjectId() == tcDirectProjectId) {
                result.add(contest);
            }
        }
        return result;
    }

    /**
     * Update contest data. Note that all data can be updated only if contest is not active. If contest is active it is
     * possible to increase prize amount and duration.
     *
     * @param contest - the contest to update.
     *
     * @throws throw IllegalArgumentException if the argument is null. throw EntityNotFoundException if the contest
     * doesn't exist in persistence. throw ContestManagementException if any error occurs when updating contest.
     */
    public void updateContest(Contest contest) throws ContestManagementException {
        if (contests.get(contest.getContestId()) == null) {
            throw new EntityNotFoundException("No contest with such id.");
        }
        contests.put(contest.getContestId(), contest);
    }

    /**
     * Update contest status to the given value.
     *
     * @param contestId - the contest id. newStatusId - the new status id.
     *
     * @throws throw EntityNotFoundException if there is no corresponding Contest or ContestStatus in persistence.
     * throw ContestStatusTransitionException if it's not allowed to update the contest to the given status. throw
     * ContestManagementException if any error occurs when updating contest's status.
     */
    public void updateContestStatus(long contestId, long newStatusId) throws ContestManagementException {
        lastCall = "updateContestStatus(" + contestId + ", " + newStatusId + ")";
    }

    /**
     * Get client for contest, the client id is returned.
     *
     * @param contestId - the contest id.
     *
     * @return - the client id.
     *
     * @throws throw EntityNotFoundException if there is no corresponding contest (or project) in persistence. throw
     * ContestManagementException if any error occurs when retrieving the client id.
     */
    public long getClientForContest(long contestId) throws ContestManagementException {
        if (contestId == 101) {
            throw new EntityNotFoundException("");
        }
        if (contestId == 102) {
            throw new ContestManagementException("");
        }
        return 33;
    }

    /**
     * Get client for project, and return the retrieved client id.
     *
     * @param projectId - the project id.
     *
     * @return - the client id.
     *
     * @throws throw EntityNotFoundException if there is no corresponding project in persistence. throw
     * ContestManagementException if any error occurs when retrieving the client id.
     */
    public long getClientForProject(long projectId) throws ContestManagementException {
        if (projectId == 101) {
            throw new EntityNotFoundException("");
        }
        if (projectId == 102) {
            throw new ContestManagementException("");
        }
        return 33;
    }

    /**
     * Add contest status, and return the added contest status.
     *
     * @param contestStatus - the contest status to add.
     *
     * #return - the added contest status.
     *
     * @throws throw IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if the entity
     * already exists in the persistence. throw ContestManagementException if any other error occurs.
     */
    public ContestStatus addContestStatus(ContestStatus contestStatus) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Update contest status.
     *
     * @param contestStatus - the contest status to update.
     *
     * @throws throw IllegalArgumentException if the arg is null. throw EntityNotFoundException if the contestStatus
     * doesn't exist in persistence. throw ContestManagementException if any other error occurs.
     */
    public void updateContestStatus(ContestStatus contestStatus) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Remove contest status, return true if the contest status exists and removed successfully, return false if it
     * doesn't exist.
     *
     * @param contestStatusId - the contest status id.
     *
     * @return -  true if the contest status exists and removed successfully, return false if it doesn't exist.
     *
     * @throws throw ContestManagementException if any error occurs.
     */
    public boolean removeContestStatus(long contestStatusId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Get contest status, and return the retrieved contest status. Return null if it doesn't exist.
     *
     * @param contestStatusId - the contest status id.
     *
     * @return - the retrieved contest status, or null if it doesn't exist.
     *
     * @throws throw ContestManagementException if any error occurs when getting contest status.
     */
    public ContestStatus getContestStatus(long contestStatusId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Add new document, and return the added document.
     *
     * @param document - the document to add.
     *
     * @return - the added document.
     *
     * @throws throw IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if the entity
     * already exists in the persistence. throw ContestManagementException if any other error occurs.
     */
    public Document addDocument(Document document) throws ContestManagementException {
        if (documents.get(document.getDocumentId()) != null) {
            throw new EntityAlreadyExistsException("Duplicated entity.");
        }
        documents.put(document.getDocumentId(), document);
        return document;
    }

    /**
     * Update document.
     *
     * @throws throw IllegalArgumentException if the arg is null. throw EntityNotFoundException if the document
     * doesn't exist in persistence. throw ContestManagementException if any other error occurs.
     *
     * @param document the document to update
     */
    public void updateDocument(Document document) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Get document by id, and return the retrieved document. Return null if the document doesn't exist.
     *
     * @param documentId - the document id.
     *
     * @return - the retrieved document, or null if it doesn't exist.
     *
     * @throws throw ContestManagementException if any error occurs when getting document.
     */
    public Document getDocument(long documentId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Remove document, return true if the document exists and removed successfully, return false if it doesn't exist.
     *
     * @param documentId - the document id.
     *
     * @return -  true if the document exists and removed successfully, return false if it doesn't exist.
     *
     * @throws throw ContestManagementException if any error occurs.
     */
    public boolean removeDocument(long documentId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Add document to contest. Nothing happens if the document already exists in contest.
     *
     * @param documentId - the document id. contestId - the contest id.
     *
     * @throws throw EntityNotFoundException if there is no corresponding document or contest in persistence. throw
     * ContestManagementException if any other error occurs.
     */
    public void addDocumentToContest(long documentId, long contestId) throws ContestManagementException {
        if (documentsForContest.get(contestId) == null) {
            documentsForContest.put(contestId, new HashSet<Long>());
        }
        documentsForContest.get(contestId).add(documentId);
    }

    /**
     * Remove document from contest. Return true if the document exists in the contest and removed successfully, return
     * false if it doesn't exist in contest.
     *
     * @param documentId - the document id. contestId - the contest id.
     *
     * @return - true if the document exists in the contest and removed successfully, return false if it doesn't exist
     * in contest.
     *
     * @throws throw EntityNotFoundException if there is no corresponding document or contest in persistence. throw
     * ContestManagementException if any other error occurs.
     */
    public boolean removeDocumentFromContest(long documentId, long contestId) throws ContestManagementException {
        return documentsForContest.get(contestId).remove(documentId);
    }

    /**
     * Add contest category, and return the added contest category.
     *
     * @param contestCategory - the contest category to add.
     *
     * #return - the added contest category.
     *
     * @throws throw IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if the entity
     * already exists in the persistence. throw ContestManagementException if any other error occurs.
     */
    public ContestChannel addContestCategory(ContestChannel contestCategory) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Update contest category
     *
     * @param contestCategory - the contest category to update.
     *
     * @throws throw IllegalArgumentException if the arg is null. throw EntityNotFoundException if the
     * contestCategory doesn't exist in persistence. throw ContestManagementException if any other error occurs.
     */
    public void updateContestCategory(ContestChannel contestCategory) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Remove contest category, return true if the contest category exists and removed successfully, return false if it
     * doesn't exist.
     *
     * @param contestCategoryId - the contest category id.
     *
     * @return - true if the contest category exists and removed successfully, return false if it doesn't exist.
     *
     * @throws throw ContestManagementException if fail to remove the contest category when it exists.
     */
    public boolean removeContestCategory(long contestCategoryId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Get contest category, and return the retrieved contest category. Return null if it doesn't exist.
     *
     * @param contestCategoryId - the contest category id.
     *
     * @return - the retrieved contest category, or null if it doesn't exist.
     *
     * @throws throw ContestManagementException if any error occurs when getting contest category.
     */
    public ContestChannel getContestCategory(long contestCategoryId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Add contest configuration parameter, and return the added contest configuration parameter.
     *
     * @param contestConfig - the contest configuration parameter to add.
     *
     * #return - the added contest configuration parameter.
     *
     * @throws throw IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if the entity
     * already exists in the persistence. throw ContestManagementException if any other error occurs.
     */
    public ContestConfig addConfig(ContestConfig contestConfig) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Update contest configuration parameter
     *
     * @param contestConfig - the contest configuration parameter to update.
     *
     * @throws throw IllegalArgumentException if the arg is null. throw EntityNotFoundException if the contest
     * configuration parameter doesn't exist in persistence. throw ContestManagementException if any other error
     * occurs.
     */
    public void updateConfig(ContestConfig contestConfig) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Get contest configuration parameter by id, and return the retrieved contest configuration parameter. Return null
     * if it doesn't exist.
     *
     * @param contestConfigId - the contest configuration parameter id.
     *
     * @return - the retrieved contest configuration parameter, or null if it doesn't exist.
     *
     * @throws throw ContestManagementException if any error occurs when getting contest configuration parameter
     */
    public ContestConfig getConfig(long contestConfigId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Save document content in file system. This method should call DocumentContentManager.saveDocumentContent to save
     * the document content.
     *
     * @throws throw IllegalArgumentException if fileData argument is null or empty array. throw
     * EntityNotFoundException if the document doesn't exist in persistence. throw ContestManagementException if any
     * other error occurs. throw - let exceptions from DocumentContentManager.saveDocumentContent propagate.
     */
    public void saveDocumentContent(long documentId, byte[] documentContent) throws ContestManagementException {
        documentsContent.put(documentId, documentContent);
    }

    /**
     * Get document content to return.  If the document is not saved, null is returned.  It will use
     * DocumentContentManager to get document content. It can also return empty array if the document content is empty.
     *
     * @param documentId - the document id.
     *
     * @return - the document content in byte array. If the document is not saved, null is returned.
     *
     * @throws throw EntityNotFoundException if the document doesn't exist in persistence. throw
     * ContestManagementException if any other error occurs. throw - let exceptions from
     * DocumentContentManager.getDocumentContent propagate.
     */
    public byte[] getDocumentContent(long documentId) throws ContestManagementException {
        return documentsContent.get(documentId);
    }

    /**
     * Check the document's content exists or not.  Return true if it exists, return false otherwise.  It will use
     * DocumentContentManager to check document content's existence.
     *
     * @param document - the document.
     *
     * @return - true if the document content exists, return false otherwise.
     *
     * @throws throw EntityNotFoundException if the document doesn't exist in persistence. throw
     * ContestManagementException if any other error occurs. throw - let exceptions from
     * DocumentContentManager.existDocumentContent propagate.
     */
    public boolean existDocumentContent(long documentId) throws ContestManagementException {
        return getDocumentContent(documentId) != null;
    }

    /**
     * Get all contest statuses to return.  If no contest status exists, return an empty list.
     *
     * @return - a list of contest statuses.
     *
     * @throws throw ContestManagementException if any error occurs when getting contest statuses.
     */
    public List<ContestStatus> getAllContestStatuses() throws ContestManagementException {
        List<ContestStatus> result = new ArrayList<ContestStatus>();

        for (int i = 0; i < 3; ++i) {
            ContestStatus st = new ContestStatus();

            st.setContestStatusId(0l + i);
            st.setDescription("description" + i);
            st.setName("test" + i);

            result.add(st);
        }

        return result;
    }

    /**
     * Get all contest categories to return.  If no contest category exists, return an empty list.
     *
     * @return - a list of contest categories.
     *
     * @throws throw ContestManagementException if any error occurs when getting contest categories.
     */
    public List<ContestChannel> getAllContestCategories() throws ContestManagementException {

        List<ContestChannel> result = new ArrayList<ContestChannel>();

        for (int i = 0; i < 3; ++i) {
            ContestChannel ch = new ContestChannel();

            ch.setContestChannelId(0l + i);
            ch.setDescription("description" + i);
            ch.setName("test" + i);
            ch.setParentChannelId(10l + i);

            result.add(ch);
        }

        return result;
    }

    /**
     * Get all studio file types to return.  If no studio file type exists, return an empty list.
     *
     * @return - a list of studio file types
     *
     * @throws throw ContestManagementException if any error occurs when getting studio file types.
     */
    public List<StudioFileType> getAllStudioFileTypes() throws ContestManagementException {
        List<StudioFileType> result = new ArrayList<StudioFileType>();

        for (int i = 0; i < 3; ++i) {
            StudioFileType ft = new StudioFileType();
            ft.setExtension("ex" + i);
            result.add(ft);
        }

        return result;
    }

    /**
     * Add contest type configuration parameter, and return the added contest type configuration parameter.
     *
     * @param contestTypeConfig - the contest type configuration parameter to add.
     *
     * #return - the added contest type configuration parameter.
     *
     * @throws throw IllegalArgumentException if the arg is null. throw EntityAlreadyExistsException if the entity
     * already exists in the persistence. throw ContestManagementException if any other error occurs.
     */
    public ContestTypeConfig addContestTypeConfig(ContestTypeConfig contestTypeConfig) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Update contest type configuration parameter
     *
     * @param contestTypeConfig - the contest type configuration parameter to update.
     *
     * @throws throw IllegalArgumentException if the arg is null. throw EntityNotFoundException if the contest type
     * configuration parameter doesn't exist in persistence. throw ContestManagementException if any other error
     * occurs.
     */
    public void updateContestTypeConfig(ContestTypeConfig contestTypeConfig) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Get contest type configuration parameter by id, and return the retrieved contest type configuration parameter.
     * Return null if it doesn't exist.
     *
     * @param contestTypeConfigId - the contest type configuration parameter id.
     *
     * @return - the retrieved contest type configuration parameter, or null if it doesn't exist.
     *
     * @throws throw ContestManagementException if any error occurs when getting contest type configuration
     * parameter
     */
    public ContestTypeConfig getContestTypeConfig(long contestTypeConfigId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Add prize to the given contest. Nothing happens if the prize already exists in contest.
     *
     * @param contestId - the contest id. prizeId - the prize id.
     *
     * @throws throw EntityNotFoundException if there is no corresponding prize or contest in persistence. throw
     * ContestManagementException if any other error occurs.
     */
    public void addPrizeToContest(long contestId, long prizeId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Remove prize from contest. Return true if the prize exists in the contest and removed successfully, return false
     * if it doesn't exist in contest.
     *
     * @param prizeId - the prize id. contestId - the contest id.
     *
     * @return - true if the prize exists in the contest and removed successfully, return false if it doesn't exist in
     * contest.
     *
     * @throws throw EntityNotFoundException if there is no corresponding prize or contest in persistence. throw
     * ContestManagementException if any other error occurs.
     */
    public boolean removePrizeFromContest(long contestId, long prizeId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Retrieve all prizes in the given contest to return.  An empty list is returned if there is no such prizes.
     *
     * @param contestId - the contest id.
     *
     * @return - a list of prizes.
     *
     * @throws throw EntityNotFoundException if there is no corresponding contest in persistence. throw
     * ContestManagementException if any other error occurs.
     */
    public List<Prize> getContestPrizes(long contestId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }
}
