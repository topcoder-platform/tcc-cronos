/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChangeHistory;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManagerLocal;
import com.topcoder.service.studio.contest.ContestPayment;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestStatusTransitionException;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.ContestTypeConfig;
import com.topcoder.service.studio.contest.Document;
import com.topcoder.service.studio.contest.DocumentType;
import com.topcoder.service.studio.contest.EntityAlreadyExistsException;
import com.topcoder.service.studio.contest.EntityNotFoundException;
import com.topcoder.service.studio.contest.Medium;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.SimpleContestData;
import com.topcoder.service.studio.contest.SimpleProjectContestData;
import com.topcoder.service.studio.contest.SimpleProjectPermissionData;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.User;
import com.topcoder.service.studio.contest.ContestConfig.Identifier;
import com.topcoder.service.studio.submission.ContestResult;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * the mock ContestManager.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class MockContestManager implements ContestManagerLocal {
    /**
     * the contests.
     */
    public static Map<Long, Contest> contests;

    /**
     * the documents.
     */
    public static Map<Long, Document> documents;

    /**
     * the documentsForContest.
     */
    public static Map<Long, Set<Long>> documentsForContest;

    /**
     * the documentsContent.
     */
    public static Map<Long, byte[]> documentsContent;

    /**
     * the errorRequested.
     */
    public static boolean errorRequested;

    /**
     * the lastCall.
     */
    public static String lastCall;

    /**
     * Default ctor.
     */
    public MockContestManager() {
        contests = new HashMap<Long, Contest>();
        documents = new HashMap<Long, Document>();
        documentsForContest = new HashMap<Long, Set<Long>>();
        documentsContent = new HashMap<Long, byte[]>();
        lastCall = null;
        errorRequested = false;
    }

    /**
     * check error request.
     *
     * @throws ContestManagementException if error.
     */
    private void checkErrorRequest() throws ContestManagementException {
        if (errorRequested) {
            errorRequested = false;
            throw new ContestManagementException("was requested");
        }
    }

    /**
     * Create new contest, and return the created contest.
     *
     * @param contest the contest to create.
     *
     * @return the created contest.
     *
     * @throw IllegalArgumentException if the arg is null. throw
     *        EntityAlreadyExistsException if the entity already exists in the
     *        persistence. throw ContestManagementException if any other error
     *        occurs.
     */
    public Contest createContest(Contest contest) throws ContestManagementException {
        checkErrorRequest();
        if (contests.get(contest.getContestId()) != null) {
            throw new EntityAlreadyExistsException("");
        }

        contest.setStatus(new ContestStatus());
        contests.put(contest.getContestId(), contest);
        return contest;
    }

    /**
     * Get contest by id, and return the retrieved contest. If the contest
     * doesn't exist, null is returned.
     *
     * @param contestId the contest id.
     *
     * @return the retrieved contest, or null if it doesn't exist.
     *
     * @throw ContestManagementException if any error occurs when getting
     *        contest.
     */
    public Contest getContest(long contestId) throws ContestManagementException {
        checkErrorRequest();
        if (1L == contestId) {
            Contest c = new Contest();
            return c;
        }
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
     * Get projects's contests by the project id. A list of contests associated
     * with the given tcDirectProjectId should be returned. If there is no such
     * contests, an empty list should be returned.
     *
     * @param tcDirectProjectId the project id.
     *
     * @return a list of associated contests.
     *
     * @throw ContestManagementException if any error occurs when getting
     *        contests.
     */
    public List<Contest> getContestsForProject(long tcDirectProjectId) throws ContestManagementException {
        checkErrorRequest();
        List<Contest> result = new ArrayList<Contest>();
        for (Contest contest : contests.values()) {
            if (contest.getTcDirectProjectId() == tcDirectProjectId) {
                result.add(contest);
            }
        }
        return result;
    }

    /**
     * Update contest data. Note that all data can be updated only if contest is
     * not active. If contest is active it is possible to increase prize amount
     * and duration.
     *
     * @param contest the contest to update.
     *
     * @throw IllegalArgumentException if the argument is null. throw
     * @throw EntityNotFoundException if the contest doesn't exist in
     *        persistence.
     * @throw ContestManagementException if any error occurs when updating
     *        contest.
     *
     */
    public void updateContest(Contest contest) throws ContestManagementException {
        checkErrorRequest();
        if (contests.get(contest.getContestId()) == null) {
            throw new EntityNotFoundException("No contest with such id.");
        }
        contests.put(contest.getContestId(), contest);
    }

    /**
     * Update contest status to the given value.
     *
     * @param contestId the contest id. newStatusId the new status id.
     *
     * @throw EntityNotFoundException if there is no corresponding Contest or
     *        ContestStatus in persistence. throw
     * @throw ContestStatusTransitionException if it's not allowed to update the
     *        contest to the given status. throw ContestManagementException if
     *        any error occurs when updating contest's status.
     *
     */
    public void updateContestStatus(long contestId, long newStatusId) throws ContestManagementException {
        checkErrorRequest();
        if (newStatusId % 2 == 0) {
            throw new EntityNotFoundException("No even stutus ids.");
        }
        if (contests.get(contestId) == null) {
            throw new EntityNotFoundException("Contest not found.");
        }
        if (newStatusId % 3 == 0) {
            throw new ContestStatusTransitionException("Can't set such status.");
        }
        lastCall = "updateContestStatus(" + contestId + ", " + newStatusId + ")";
    }

    /**
     * Get client for contest, the client id is returned.
     *
     * @param contestId the contest id.
     *
     * @return the client id.
     *
     * @throw EntityNotFoundException if there is no corresponding contest (or
     *        project) in persistence.
     * @throw ContestManagementException if any error occurs when retrieving the
     *        client id.
     *
     */
    public long getClientForContest(long contestId) throws ContestManagementException {
        checkErrorRequest();
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
     * @param projectId the project id.
     *
     * @return the client id.
     *
     * @throw EntityNotFoundException if there is no corresponding project in
     *        persistence.
     * @throw ContestManagementException if any error occurs when retrieving the
     *        client id.
     */
    public long getClientForProject(long projectId) throws ContestManagementException {
        checkErrorRequest();
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
     * @param contestStatus the contest status to add.
     *
     * @return the added contest status.
     *
     * @throw IllegalArgumentException if the arg is null.
     * @throw EntityAlreadyExistsException if the entity already exists in the
     *        persistence.
     * @throw ContestManagementException if any other error occurs.
     */
    public ContestStatus addContestStatus(ContestStatus contestStatus) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Update contest status.
     *
     * @param contestStatus the contest status to update.
     *
     * @throw IllegalArgumentException if the arg is null.
     * @throw EntityNotFoundException if the contestStatus doesn't exist in
     *        persistence.
     * @throw ContestManagementException if any other error occurs.
     *
     */
    public void updateContestStatus(ContestStatus contestStatus) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Remove contest status, return true if the contest status exists and
     * removed successfully, return false if it doesn't exist.
     *
     * @param contestStatusId the contest status id.
     *
     * @return true if the contest status exists and removed successfully,
     *         return false if it doesn't exist.
     *
     * @throw ContestManagementException if any error occurs.
     */
    public boolean removeContestStatus(long contestStatusId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Get contest status, and return the retrieved contest status. Return null
     * if it doesn't exist.
     *
     * @param contestStatusId the contest status id.
     *
     * @return the retrieved contest status, or null if it doesn't exist.
     *
     * @throw ContestManagementException if any error occurs when getting
     *        contest status.
     */
    public ContestStatus getContestStatus(long contestStatusId) throws ContestManagementException {
        return new ContestStatus();
    }

    /**
     * Add new document, and return the added document.
     *
     * @param document the document to add.
     *
     * @return the added document.
     *
     * @throw IllegalArgumentException if the arg is null.
     * @throw EntityAlreadyExistsException if the entity already exists in the
     *        persistence.
     * @throw ContestManagementException if any other error occurs.
     */
    public Document addDocument(Document document) throws ContestManagementException {
        checkErrorRequest();
        if (documents.get(document.getDocumentId()) != null) {
            throw new EntityAlreadyExistsException("Duplicated entity.");
        }
        documents.put(document.getDocumentId(), document);
        return document;
    }

    /**
     * Update document.
     *
     * @param document the document to update.
     *
     * @throw IllegalArgumentException if the arg is null.
     * @throw EntityNotFoundException if the document doesn't exist in
     *        persistence.
     * @throw ContestManagementException if any other error occurs.
     *
     */
    public void updateDocument(Document document) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Get document by id, and return the retrieved document. Return null if the
     * document doesn't exist.
     *
     * @param documentId the document id.
     *
     * @return the retrieved document, or null if it doesn't exist.
     *
     * @throw ContestManagementException if any error occurs when getting
     *        document.
     */
    public Document getDocument(long documentId) throws ContestManagementException {
        return new Document();
    }

    /**
     * Remove document, return true if the document exists and removed
     * successfully, return false if it doesn't exist.
     *
     * @param documentId the document id.
     *
     * @return true if the document exists and removed successfully, return
     *         false if it doesn't exist.
     *
     * @throw ContestManagementException if any error occurs.
     *
     */
    public boolean removeDocument(long documentId) throws ContestManagementException {
        return true;
    }

    /**
     * Add document to contest. Nothing happens if the document already exists
     * in contest.
     *
     * @param documentId the document id. contestId the contest id.
     *
     * @throw EntityNotFoundException if there is no corresponding document or
     *        contest in persistence.
     * @throw ContestManagementException if any other error occurs.
     */
    public void addDocumentToContest(long documentId, long contestId) throws ContestManagementException {
        checkErrorRequest();
        if (documents.get(documentId) == null) {
            throw new EntityNotFoundException("no such document");
        }
        if (contests.get(contestId) == null) {
            throw new EntityNotFoundException("no such contest");
        }

        if (documentsForContest.get(contestId) == null) {
            documentsForContest.put(contestId, new HashSet<Long>());
        }
        documentsForContest.get(contestId).add(documentId);
    }

    /**
     * Remove document from contest. Return true if the document exists in the
     * contest and removed successfully, return false if it doesn't exist in
     * contest.
     *
     * @param documentId the document id. contestId the contest id.
     *
     * @return true if the document exists in the contest and removed
     *         successfully, return false if it doesn't exist in contest.
     *
     * @throw EntityNotFoundException if there is no corresponding document or
     *        contest in persistence.
     * @throw ContestManagementException if any other error occurs.
     */
    public boolean removeDocumentFromContest(long documentId, long contestId)
        throws ContestManagementException {
        checkErrorRequest();
        if (documents.get(documentId) == null) {
            throw new EntityNotFoundException("no such document");
        }
        if (contests.get(contestId) == null) {
            throw new EntityNotFoundException("no such contest");
        }

        return documentsForContest.get(contestId).remove(documentId);
    }

    /**
     * Add contest category, and return the added contest category.
     *
     * @param contestCategory the contest category to add.
     *
     * @return the added contest category.
     *
     * @throw IllegalArgumentException if the arg is null.
     * @throw EntityAlreadyExistsException if the entity already exists in the
     *        persistence.
     * @throw ContestManagementException if any other error occurs.
     *
     */
    public ContestChannel addContestCategory(ContestChannel contestCategory)
        throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Update contest category
     *
     * @param contestCategory the contest category to update.
     *
     * @throw IllegalArgumentException if the arg is null.
     * @throw EntityNotFoundException if the contestCategory doesn't exist in
     *        persistence.
     * @throw ContestManagementException if any other error occurs.
     *
     * @param contestCategory
     */
    public void updateContestCategory(ContestChannel contestCategory) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Remove contest category, return true if the contest category exists and
     * removed successfully, return false if it doesn't exist.
     *
     * @param contestCategoryId the contest category id.
     *
     * @return true if the contest category exists and removed successfully,
     *         return false if it doesn't exist.
     *
     * @throw ContestManagementException if fail to remove the contest category
     *        when it exists.
     *
     */
    public boolean removeContestCategory(long contestCategoryId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Get contest category, and return the retrieved contest category. Return
     * null if it doesn't exist.
     *
     * @param contestCategoryId the contest category id.
     *
     * @return the retrieved contest category, or null if it doesn't exist.
     *
     * @throw ContestManagementException if any error occurs when getting
     *        contest category.
     *
     */
    public ContestChannel getContestCategory(long contestCategoryId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Add contest configuration parameter, and return the added contest
     * configuration parameter.
     *
     * @param contestConfig the contest configuration parameter to add.
     *
     * @return the added contest configuration parameter.
     *
     * @throw IllegalArgumentException if the arg is null.
     * @throw EntityAlreadyExistsException if the entity already exists in the
     *        persistence.
     * @throw ContestManagementException if any other error occurs.
     *
     * @param contestConfig
     * @return
     */
    public ContestConfig addConfig(ContestConfig contestConfig) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Update contest configuration parameter
     *
     * @param contestConfig the contest configuration parameter to update.
     *
     * @throw IllegalArgumentException if the arg is null.
     * @throw EntityNotFoundException if the contest configuration parameter
     *        doesn't exist in persistence.
     * @throw ContestManagementException if any other error occurs.
     *
     */
    public void updateConfig(ContestConfig contestConfig) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Get contest configuration parameter by id, and return the retrieved
     * contest configuration parameter. Return null if it doesn't exist.
     *
     * @param contestConfigId the contest configuration parameter id.
     *
     * @return the retrieved contest configuration parameter, or null if it
     *         doesn't exist.
     *
     * @throw ContestManagementException if any error occurs when getting
     *        contest configuration parameter
     *
     */
    public ContestConfig getConfig(long contestConfigId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Save document content in file system. This method should call
     * DocumentContentManager.saveDocumentContent to save the document content.
     *
     * @param documentId the document documentContent the file data of the
     *        document to save.
     *
     * @throw IllegalArgumentException if fileData argument is null or empty
     *        array.
     * @throw EntityNotFoundException if the document doesn't exist in
     *        persistence.
     * @throw ContestManagementException if any other error occurs. throw let
     *        exceptions from DocumentContentManager.saveDocumentContent
     *        propagate.
     */
    public void saveDocumentContent(long documentId, byte[] documentContent)
        throws ContestManagementException {
        checkErrorRequest();

        if (documentContent == null || documentContent.length == 0) {
            throw new IllegalArgumentException("Bad content");
        }

        if (documents.get(documentId) == null) {
            throw new EntityNotFoundException("no such document");
        }

        documentsContent.put(documentId, documentContent);
    }

    /**
     * Get document content to return. If the document is not saved, null is
     * returned. It will use DocumentContentManager to get document content. It
     * can also return empty array if the document content is empty.
     *
     * @param documentId the document id.
     *
     * @return the document content in byte array. If the document is not saved,
     *         null is returned.
     *
     * @throw EntityNotFoundException if the document doesn't exist in
     *        persistence.
     * @throw ContestManagementException if any other error occurs. throw let
     *        exceptions from DocumentContentManager.getDocumentContent
     *        propagate.
     */
    public byte[] getDocumentContent(long documentId) throws ContestManagementException {
        checkErrorRequest();
        if (documents.get(documentId) == null) {
            throw new EntityNotFoundException("no such document");
        }
        return documentsContent.get(documentId);
    }

    /**
     * Check the document's content exists or not. Return true if it exists,
     * return false otherwise. It will use DocumentContentManager to check
     * document content's existence.
     *
     * @param document the document.
     *
     * @return true if the document content exists, return false otherwise.
     *
     * @throw EntityNotFoundException if the document doesn't exist in
     *        persistence.
     * @throw ContestManagementException if any other error occurs. throw let
     *        exceptions from DocumentContentManager.existDocumentContent
     *        propagate.
     */
    public boolean existDocumentContent(long documentId) throws ContestManagementException {
        return getDocumentContent(documentId) != null;
    }

    /**
     * Get all contest statuses to return. If no contest status exists, return
     * an empty list.
     *
     * @return a list of contest statuses.
     *
     * @throw ContestManagementException if any error occurs when getting
     *        contest statuses.
     */
    public List<ContestStatus> getAllContestStatuses() throws ContestManagementException {
        checkErrorRequest();

        List<ContestStatus> result = new ArrayList<ContestStatus>();

        for (int i = 0; i < 3; ++i) {
            ContestStatus st = new ContestStatus();

            st.setContestStatusId(0l + i);
            st.setDescription("desc" + i);
            st.setName("name" + i);
            List<ContestStatus> status = new ArrayList<ContestStatus>();
            st.setStatuses(status);
            result.add(st);
        }

        return result;
    }

    /**
     * Get all contest categories to return. If no contest category exists,
     * return an empty list.
     *
     * @return a list of contest categories.
     *
     * @throw ContestManagementException if any error occurs when getting
     *        contest categories.
     */
    public List<ContestChannel> getAllContestCategories() throws ContestManagementException {
        checkErrorRequest();

        List<ContestChannel> result = new ArrayList<ContestChannel>();

        for (int i = 0; i < 3; ++i) {
            ContestChannel ch = new ContestChannel();

            ch.setContestChannelId(0l + i);
            ch.setDescription("desc" + i);

            result.add(ch);
        }

        return result;
    }

    /**
     * Get all studio file types to return. If no studio file type exists,
     * return an empty list.
     *
     * @return a list of studio file types
     *
     * @throw ContestManagementException if any error occurs when getting studio
     *        file types.
     */
    public List<StudioFileType> getAllStudioFileTypes() throws ContestManagementException {
        checkErrorRequest();

        List<StudioFileType> result = new ArrayList<StudioFileType>();

        for (int i = 0; i < 3; ++i) {
            StudioFileType ft = new StudioFileType();
            ft.setExtension("ex" + i);
            result.add(ft);
        }

        StudioFileType type = new StudioFileType();
        type.setExtension("zip");
        result.add(type);

        return result;
    }

    /**
     * Add contest type configuration parameter, and return the added contest
     * type configuration parameter.
     *
     * @throw IllegalArgumentException if the arg is null.
     * @throw EntityAlreadyExistsException if the entity already exists in the
     *        persistence.
     * @throw ContestManagementException if any other error occurs.
     *
     * @param contestTypeConfig the contest type configuration parameter to add.
     * @return the added contest type configuration parameter.
     */
    public ContestTypeConfig addContestTypeConfig(ContestTypeConfig contestTypeConfig)
        throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Update contest type configuration parameter
     *
     * @throw IllegalArgumentException if the arg is null.
     * @throw EntityNotFoundException if the contest type configuration
     *        parameter doesn't exist in persistence.
     * @throw ContestManagementException if any other error occurs.
     *
     * @param contestTypeConfig the contest type configuration parameter to
     *        update.
     */
    public void updateContestTypeConfig(ContestTypeConfig contestTypeConfig)
        throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Get contest type configuration parameter by id, and return the retrieved
     * contest type configuration parameter. Return null if it doesn't exist.
     *
     * @throw ContestManagementException if any error occurs when getting
     *        contest type configuration parameter
     *
     * @param contestTypeConfigId the contest type configuration parameter id.
     * @return the retrieved contest type configuration parameter, or null if it
     *         doesn't exist.
     */
    public ContestTypeConfig getContestTypeConfig(long contestTypeConfigId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Add prize to the given contest. Nothing happens if the prize already
     * exists in contest.
     *
     *
     * @throw EntityNotFoundException if there is no corresponding prize or
     *        contest in persistence.
     * @throw ContestManagementException if any other error occurs.
     *
     * @param contestId the contest id
     * @param prizeId the prize id.
     */
    public void addPrizeToContest(long contestId, long prizeId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Remove prize from contest. Return true if the prize exists in the contest
     * and removed successfully, return false if it doesn't exist in contest.
     *
     * @throw EntityNotFoundException if there is no corresponding prize or
     *        contest in persistence.
     * @throw ContestManagementException if any other error occurs.
     *
     * @param contestId the prize id.
     * @param prizeId the contest id.
     * @return true if the prize exists in the contest and removed successfully,
     *         return false if it doesn't exist in contest.
     */
    public boolean removePrizeFromContest(long contestId, long prizeId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * Retrieve all prizes in the given contest to return. An empty list is
     * returned if there is no such prizes.
     *
     * @throw EntityNotFoundException if there is no corresponding contest in
     *        persistence.
     * @throw ContestManagementException if any other error occurs.
     *
     * @param contestId the contest id.
     * @return a list of prizes.
     */
    public List<Prize> getContestPrizes(long contestId) throws ContestManagementException {
        throw new UnsupportedOperationException("Method is not implemented yet.");
    }

    /**
     * add ChangeHistory.
     *
     * @param history list of history.
     * @throw ContestManagementException if any other error occurs.
     */
    public void addChangeHistory(List<ContestChangeHistory> history) throws ContestManagementException {

    }

    /**
     * add ContestChannel.
     *
     * @param contestChannel the ContestChannel.
     * @return the ContestChannel.
     * @throw ContestManagementException if any other error occurs.
     */
    public ContestChannel addContestChannel(ContestChannel contestChannel) throws ContestManagementException {

        return null;
    }

    /**
     * create ContestPayment.
     *
     * @param contestPayment the ContestPayment.
     * @return the ContestPayment.
     * @throw ContestManagementException if any other error occurs.
     */
    public ContestPayment createContestPayment(ContestPayment contestPayment)
        throws ContestManagementException {

        return null;
    }

    /**
     * create ContestResult.
     *
     * @param contestResult the ContestResult.
     * @return the ContestResult
     * @throw ContestManagementException if any other error occurs.
     */
    public ContestResult createContestResult(ContestResult contestResult) throws ContestManagementException {

        return null;
    }

    /**
     * create Prize.
     *
     * @param prize the Prize.
     * @return the prize.
     * @throw ContestManagementException if any other error occurs.
     */
    public Prize createPrize(Prize prize) throws ContestManagementException {

        return null;
    }

    /**
     * delete Contest.
     *
     * @param contestId the contest Id.
     * @throw ContestManagementException if any other error occurs.
     */
    public void deleteContest(long contestId) throws ContestManagementException {

    }

    /**
     * edit ContestPayment.
     *
     * @param contestPayment the ContestPayment.
     * @throw ContestManagementException if any other error occurs.
     */
    public void editContestPayment(ContestPayment contestPayment) throws ContestManagementException {

    }

    /**
     * find ContestResult.
     *
     * @param submissionId the submission Id.
     * @param contestId the contest Id.
     * @return the ContestResult.
     * @throw ContestManagementException if any other error occurs.
     */
    public ContestResult findContestResult(long submissionId, long contestId)
        throws ContestManagementException {

        return null;
    }

    /**
     * get all ContestChannel.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the list of ContestChannel
     */
    public List<ContestChannel> getAllContestChannels() throws ContestManagementException {

        return null;
    }

    /**
     * get all ContestProperty.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the list of ContestProperty
     */
    public List<ContestProperty> getAllContestProperties() throws ContestManagementException {

        return null;
    }

    /**
     * get all ContestType.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the list of ContestType
     */
    public List<ContestType> getAllContestTypes() throws ContestManagementException {
        List<ContestType> types = new ArrayList<ContestType>();
        return types;
    }

    /**
     * get all Contest.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the list of Contest
     */
    public List<Contest> getAllContests() throws ContestManagementException {

        return new ArrayList<Contest>();
    }

    /**
     * get all DocumentType.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the list of DocumentType
     */
    public List<DocumentType> getAllDocumentTypes() throws ContestManagementException {

        return null;
    }

    /**
     * get all Medium.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the list of Medium
     */
    public List<Medium> getAllMedia() throws ContestManagementException {

        return null;
    }

    /**
     * get all MimeType.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the list of MimeType
     */
    public List<MimeType> getAllMimeTypes() throws ContestManagementException {

        return null;
    }

    /**
     * get all PaymentStatus.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the list of PaymentStatus
     */
    public List<PaymentStatus> getAllPaymentStatuses() throws ContestManagementException {

        return null;
    }

    /**
     * get all PaymentType.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the list of PaymentType
     */
    public List<PaymentType> getAllPaymentTypes() throws ContestManagementException {

        return null;
    }

    /**
     * get all PrizeType.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the list of PrizeType
     */
    public List<PrizeType> getAllPrizeTypes() throws ContestManagementException {

        return null;
    }

    /**
     * get the ContestChangeHistory.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of ContestChangeHistory
     */
    public List<ContestChangeHistory> getChangeHistory(long contestId) throws ContestManagementException {

        return null;
    }

    /**
     * get the ContestChangeHistory.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of ContestChangeHistory
     */
    public List<ContestChangeHistory> getChangeHistory(long contestId, long transactionId)
        throws ContestManagementException {

        return null;
    }

    /**
     * get the ContestConfig.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the ContestConfig
     */
    public ContestConfig getConfig(Identifier compositeId) throws ContestManagementException {

        return null;
    }

    /**
     * get the ContestChannel.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the ContestChannel
     */
    public ContestChannel getContestChannel(long contestChannelId) throws ContestManagementException {

        return null;
    }

    /**
     * get the SimpleContestData.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of SimpleContestData
     */
    public List<SimpleContestData> getContestDataOnly() throws ContestManagementException {

        return null;
    }

    /**
     * get the SimpleContestData.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of SimpleContestData
     */
    public List<SimpleContestData> getContestDataOnly(long createdUser, long pid)
        throws ContestManagementException {

        return null;
    }

    /**
     * get the SimpleContestData.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of SimpleContestData
     */
    public List<SimpleContestData> getContestDataOnlyForUser(long createdUser)
        throws ContestManagementException {

        return null;
    }

    /**
     * get the ContestPayment.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of ContestPayment
     */
    public List<ContestPayment> getContestPayments(long contestId) throws ContestManagementException {

        return null;
    }

    /**
     * get the ContestConfig.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the ContestConfig
     */
    public int getContestPostCount(long forumId) throws ContestManagementException {

        return 0;
    }

    /**
     * get the ContestConfig.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the ContestConfig
     */
    public Map<Long, Long> getContestPostCount(List<Long> forumIds) throws ContestManagementException {
        return new HashMap<Long, Long>();
    }

    /**
     * get the ContestConfig.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the ContestConfig
     */
    public ContestProperty getContestProperty(long contestPropertyId) throws ContestManagementException {
        ContestProperty prop = new ContestProperty();
        prop.setPropertyId(3);
        return prop;
    }

    public List<Contest> getContestsForUser(long createdUser) throws ContestManagementException {
        return new ArrayList<Contest>(contests.values());
    }

    /**
     * get the ContestConfig.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return the ContestConfig
     */
    public DocumentType getDocumentType(long documentTypeId) throws ContestManagementException {

        return new DocumentType();
    }

    /**
     * get the LatestTransactionId.
     * @param contestId the contestId.
     * @return the LatestTransactionId
     * @throw ContestManagementException if any other error occurs.
     *
     */
    public Long getLatestTransactionId(long contestId) throws ContestManagementException {

        return null;
    }

    /**
     * get the MimeType.
     * @param mimeTypeId the mimeTypeId.
     * @return the MimeType
     * @throw ContestManagementException if any other error occurs.
     *
     */
    public MimeType getMimeType(long mimeTypeId) throws ContestManagementException {

        return new MimeType();
    }

    /**
     * get the PaymentStatus.
     * @param paymentStatusId the paymentStatusId.
     * @return the PaymentStatus
     * @throw ContestManagementException if any other error occurs.
     *
     */
    public PaymentStatus getPaymentStatus(long paymentStatusId) throws ContestManagementException {

        return null;
    }

    /**
     * get the PaymentType.
     * @param paymentTypeId the paymentTypeId.
     * @return the PaymentType
     * @throw ContestManagementException if any other error occurs.
     *
     */
    public PaymentType getPaymentType(long paymentTypeId) throws ContestManagementException {

        return null;
    }

    /**
     * get the PrizeType.
     * @param prizeTypeId the prizeTypeId.
     * @return the PrizeType
     * @throw ContestManagementException if any other error occurs.
     *
     */
    public PrizeType getPrizeType(long prizeTypeId) throws ContestManagementException {

        return null;
    }

    /**
     * get the SimpleContestData.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of SimpleContestData
     */
    public List<SimpleContestData> getSimpleContestData() throws ContestManagementException {

        return null;
    }

    /**
     * get the SimpleContestData.
     * @param pid the pid.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of SimpleContestData
     */
    public List<SimpleContestData> getSimpleContestData(long pid) throws ContestManagementException {

        return null;
    }

    /**
     * get the SimpleContestData.
     * @param createdUser the createdUser.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of SimpleContestData
     */
    public List<SimpleContestData> getSimpleContestDataForUser(long createdUser)
        throws ContestManagementException {

        return null;
    }

    /**
     * get the SimpleProjectContestData.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of SimpleProjectContestData
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData() throws ContestManagementException {

        return null;
    }

    /**
     * get the SimpleProjectContestData.
     * @param pid the pid.
     *
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of SimpleProjectContestData
     */
    public List<SimpleProjectContestData> getSimpleProjectContestData(long pid)
        throws ContestManagementException {

        return null;
    }

    /**
     * get the SimpleProjectContestData.
     * @param createdUser the createdUser.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of SimpleProjectContestData
     */
    public List<SimpleProjectContestData> getSimpleProjectContestDataForUser(long createdUser)
        throws ContestManagementException {

        return null;
    }

    /**
     * get the SimpleProjectPermissionData.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of SimpleProjectPermissionData.
     * @param createdUser the created User.
     */
    public List<SimpleProjectPermissionData> getSimpleProjectPermissionDataForUser(long createdUser)
        throws ContestManagementException {

        return null;
    }

    /**
     * get the user Contest.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of Contest
     * @param username the username
     */
    public List<Contest> getUserContests(String username) throws ContestManagementException {
        ExceptionUtils.checkNullOrEmpty(username, null, null, "username can not be null or empty.");
        List<Contest> result = new ArrayList<Contest>();
        if (username.equals("-1")) {
            throw new ContestManagementException("contestId == -1");
        }
        if ("2".equals(username)) {
            Contest c = new Contest();
            c.setContestId(100l);
            c.setContestType(new ContestType());

            Contest cc = new Contest();
            cc.setContestId(100l);
            cc.setContestType(new ContestType());
            result.add(c);
            result.add(cc);
        }
        return result;
    }

    /**
     * remove ContestChannel.
     * @param contestChannelId the contestChannel Id
     * @return remove ContestChannel or not.
     * @throw ContestManagementException if any other error occurs.
     */
    public boolean removeContestChannel(long contestChannelId) throws ContestManagementException {

        return false;
    }

    /**
     * remove ContestPayment.
     * @param contestPaymentId the contestPayment Id
     * @return remove ContestPayment or not.
     * @throw ContestManagementException if any other error occurs.
     */
    public boolean removeContestPayment(long contestPaymentId) throws ContestManagementException {
        return false;
    }

    /**
     * search the Contest.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of Contest
     */
    public List<Contest> searchContests(Filter filter) throws ContestManagementException {

        return null;
    }

    /**
     * search the User.
     *
     * @throw ContestManagementException if any other error occurs.
     * @return list of User
     */
    public List<User> searchUser(String key) throws ContestManagementException {

        return null;
    }

    /**
     * update the contest.
     *
     * @param contest the contest.
     * @param transactionId the transaction Id.
     * @param username the username.
     * @param userAdmin the userAdmin.
     *
     * @throw ContestManagementException if any other error occurs.
     */
    public void updateContest(Contest contest, long transactionId, String username, boolean userAdmin)
        throws ContestManagementException {

    }

    /**
     * update the ContestChannel.
     *
     * @param contestChannel the contestChannel.
     *
     * @throw ContestManagementException if any other error occurs.
     */
    public void updateContestChannel(ContestChannel contestChannel) throws ContestManagementException {

    }
}
